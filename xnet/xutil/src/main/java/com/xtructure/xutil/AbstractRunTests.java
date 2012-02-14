/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.xtructure.xutil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.regex.Pattern;

import org.apache.commons.lang.ClassUtils;
import org.testng.TestNG;

import com.xtructure.xutil.test.HtmlTestListener;

/**
 * A base class for running all tests in a package and/or subsystem.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
public abstract class AbstractRunTests
{
    /** The default regular expression describing names of tests. */
    private static final String DEFAULT_TEST_NAME_REGEX = "[UIF]?Test[A-Z][A-Za-z0-9]*";

    /** The names of the packages to be searched. */
    private final String[] _packageNames;

    /** The pattern for test class names. */
    private final Pattern _testClassNamePattern;

    /** The pattern for test class file names. */
    private final Pattern _testClassFileNamePattern;

    /** The list of test classes found by this test runner. */
    private final List<Class<?>> _classes = new ArrayList<Class<?>>();

    /** The class loader for this class. */
    private final ClassLoader _classLoader;

    /**
     * Creates a new test runner.
     * 
     * @param testNameRegex
     *            a regular expression describing names of tests
     * 
     * @param packageNames
     *            the names of the packages to search for tests
     */
    protected AbstractRunTests(
            final String testNameRegex,
            final String... packageNames)
    {
        super();

        _packageNames = (((packageNames == null) || (packageNames.length == 0))
                ? new String[] { ClassUtils.getPackageName(getClass()) }
                : packageNames);

        final String actualTestNameRegex = ((testNameRegex != null)
                ? testNameRegex
                : DEFAULT_TEST_NAME_REGEX);

        _testClassNamePattern = Pattern.compile(".*[.]" + actualTestNameRegex);
        _testClassFileNamePattern = Pattern.compile(actualTestNameRegex
                + "[.]class");

        _classLoader = Thread.currentThread().getContextClassLoader();
        if (_classLoader == null)
        {
            throw new RuntimeException("Can't get class loader.");
        }

        findClasses();
    }

    /** Creates a new test runner. */
    protected AbstractRunTests()
    {
        this(null);
    }

    /** Runs the tests found by this runner. */
    public final void run()
    {
        final TestNG testNG = new TestNG();
        testNG.setTestClasses(_classes.toArray(new Class<?>[_classes.size()]));
        testNG.setVerbose(2);
        testNG.setOutputDirectory("docs/test");
        testNG.setUseDefaultListeners(false);
        testNG.addListener(new HtmlTestListener());
        testNG.run();
    }

    /** Finds the classes in the package names processed by this test runner. */
    private final void findClasses()
    {
        for (final String packageName : _packageNames)
        {
            processResources(packageName);
        }
    }

    /**
     * Processes the given resources.
     * 
     * @param packageName
     *            the name of the package currently being processed
     */
    private final void processResources(
            final String packageName)
    {
        for (final URL resourceUrl : getResourceURLs(packageName))
        {
            if (resourceUrl.getProtocol().equalsIgnoreCase("jar"))
            {
                processJarResource(packageName, resourceUrl);
            }
            else if (resourceUrl.getProtocol().equalsIgnoreCase("file"))
            {
                processFileResource(packageName, resourceUrl);
            }
        }
    }

    /**
     * Returns the URLs of the resources associated with the given package name.
     * 
     * @param packageName
     *            the package name associated with the resources URLs to return
     * 
     * @return the URLs of the resources associated with the given package name
     */
    private final List<URL> getResourceURLs(
            final String packageName)
    {
        try
        {
            return Collections.list(_classLoader
                .getResources(makePath(packageName)));
        }
        catch (IOException ioEx)
        {
            throw new RuntimeException("couldn't get resources from package '"
                    + packageName + "': " + ioEx.getMessage(), ioEx);
        }
    }

    /**
     * Processes the given jar resource.
     * 
     * @param packageName
     *            the name of the package currently being processed
     * 
     * @param resourceUrl
     *            the URL of the jar resource to process
     */
    private final void processJarResource(
            final String packageName,
            final URL resourceUrl)
    {
        try
        {
            for (final JarEntry jarEntry : Collections
                .list(((JarURLConnection)resourceUrl.openConnection())
                    .getJarFile().entries()))
            {
                final String className = stripSuffix(makeClassName(jarEntry
                    .getName()));
                if (isTestClassName(packageName, className))
                {
                    addClass(className);
                }
            }
        }
        catch (IOException ioEx)
        {
            throw new RuntimeException("couldn't get entries from jar '"
                    + resourceUrl + "': " + ioEx.getMessage(), ioEx);
        }
    }

    /**
     * Processes the given file resource.
     * 
     * @param packageName
     *            the name of the package currently being processed
     * 
     * @param resourceUrl
     *            the URL of the file resource to process
     */
    private final void processFileResource(
            final String packageName,
            final URL resourceUrl)
    {
        try
        {
            final File dir = new File(URLDecoder.decode(resourceUrl.getPath(),
                "UTF-8"));
            if (!dir.exists())
            {
                throw new RuntimeException("directory for resource '"
                        + resourceUrl + "' doesn't exist");
            }
            for (final File file : dir.listFiles())
            {
                if (file.isDirectory())
                {
                    processResources(String.format( //
                        "%s.%s", packageName, file.getName()));
                }
                else if (isTestClassFileName(file.getName()))
                {
                    addClass(packageName + "." + stripSuffix(file.getName()));
                }
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingEx)
        {
            throw new RuntimeException("couldn't decode resource '"
                    + resourceUrl + "': " + unsupportedEncodingEx.getMessage(),
                unsupportedEncodingEx);
        }
    }

    /**
     * Indicates whether or not the named class is a test class.
     * 
     * @param packageName
     *            the name of the package currently being processed
     * 
     * @param className
     *            the name of the class to test
     * 
     * @return <code>true</code> if the named class is a test class;
     *         <code>false</code> otherwise
     */
    private final boolean isTestClassName(
            final String packageName,
            final String className)
    {
        return (className.startsWith(packageName) && _testClassNamePattern
            .matcher(className).matches());
    }

    /**
     * Indicates whether or not the named file is a test class file.
     * 
     * @param fileName
     *            the name of the file to test
     * 
     * @return <code>true</code> if the named file is a test class file;
     *         <code>false</code> otherwise
     */
    private final boolean isTestClassFileName(
            final String fileName)
    {
        return _testClassFileNamePattern.matcher(fileName).matches();
    }

    /**
     * Attempts to add the named class to the internally maintained list of test
     * classes.
     * 
     * @param className
     *            the name of the class to add
     */
    private final void addClass(
            final String className)
    {
        try
        {
            _classes.add(Class.forName(className));
        }
        catch (ClassNotFoundException classNotFoundEx)
        {
            throw new RuntimeException("couldn't get class '" + className
                    + "': " + classNotFoundEx.getMessage(), classNotFoundEx);
        }
    }

    /**
     * Returns the path version of the given string.
     * 
     * <p>
     * This method replaces dots with slashes.
     * </p>
     * 
     * @param orig
     *            the string to be processed
     * 
     * @return the path version of the given string
     */
    private final String makePath(
            final String orig)
    {
        return orig.replace('.', '/');
    }

    /**
     * Returns the class name version of the given string.
     * 
     * <p>
     * This method replaces slashes with dots.
     * </p>
     * 
     * @param orig
     *            the string to be processed
     * 
     * @return the class name version of the given string
     */
    private final String makeClassName(
            final String orig)
    {
        return orig.replace('/', '.');
    }

    /**
     * Strips the dot delimited suffix from the given string.
     * 
     * @param orig
     *            the string to be processed
     * 
     * @return the original string without its dot delimited suffix
     */
    private final String stripSuffix(
            final String orig)
    {
        final int index = orig.lastIndexOf('.');
        return ((index > 0)
                ? orig.substring(0, index)
                : orig);
    }
}
