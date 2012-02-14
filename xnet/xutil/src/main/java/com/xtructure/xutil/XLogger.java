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

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A wrapper for {@link Logger} that adds some useful functionality.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
public final class XLogger
        extends Logger
{
    /** The factory used by this class. */
    private static final XLoggerFactory FACTORY = new XLoggerFactory();

    /** The root logger. */
    static final XLogger LOGGER = (XLogger)LogManager.getLogger(XLogger.class
        .getName(), FACTORY);

    /** The fully-qualified class name of this wrapper. */
    private static final String FQCN = XLogger.class.getName();

    /**
     * Returns the logger with the given name.
     * 
     * @param name
     *            the name of the logger to return
     * 
     * @return the logger with the given name
     */
    public static final XLogger getInstance(
            final String name)
    {
        if (name == null)
        {
            throw new NullArgumentException("name");
        }
        return (XLogger)LogManager.getLogger(name, FACTORY);
    }

    /**
     * Returns the logger with the given name.
     * 
     * @param type
     *            the type specifying the name of the logger to return
     * 
     * @return the logger with the given name
     */
    public static final XLogger getInstance(
            final Class<?> type)
    {
        if (type == null)
        {
            throw new NullArgumentException("type");
        }
        return getInstance(type.getName());
    }

    /**
     * Creates a new logger.
     * 
     * @param loggerName
     *            the name of this logger
     */
    XLogger(
            final String loggerName)
    {
        super(loggerName);
    }

    /**
     * Logs the given message at the {@link Level#FATAL} level.
     * 
     * @param format
     *            the format of the message to log
     * 
     * @param args
     *            the arguments to the format of the message to log
     */
    public final void fatal(
            final String format,
            final Object... args)
    {
        log(Level.FATAL, format, args);
    }

    /**
     * Logs the given message at the {@link Level#ERROR} level.
     * 
     * @param format
     *            the format of the message to log
     * 
     * @param args
     *            the arguments to the format of the message to log
     */
    public final void error(
            final String format,
            final Object... args)
    {
        log(Level.ERROR, format, args);
    }

    /**
     * Logs the given message at the {@link Level#WARN} level.
     * 
     * @param format
     *            the format of the message to log
     * 
     * @param args
     *            the arguments to the format of the message to log
     */
    public final void warn(
            final String format,
            final Object... args)
    {
        log(Level.WARN, format, args);
    }

    /**
     * Logs the given message at the {@link Level#ERROR} level.
     * 
     * @param format
     *            the format of the message to log
     * 
     * @param args
     *            the arguments to the format of the message to log
     */
    public final void info(
            final String format,
            final Object... args)
    {
        log(Level.INFO, format, args);
    }

    /**
     * Logs the given message at the {@link Level#DEBUG} level.
     * 
     * @param format
     *            the format of the message to log
     * 
     * @param args
     *            the arguments to the format of the message to log
     */
    public final void debug(
            final String format,
            final Object... args)
    {
        log(Level.DEBUG, format, args);
    }

    /**
     * Logs the given message at the {@link Level#TRACE} level.
     * 
     * @param format
     *            the format of the message to log
     * 
     * @param args
     *            the arguments to the format of the message to log
     */
    public final void trace(
            final String format,
            final Object... args)
    {
        log(Level.TRACE, format, args);
    }

    /**
     * Logs the given message at the given level.
     * 
     * @param logLevel
     *            the level at which to log the given message
     * 
     * @param format
     *            the format of the message to log
     * 
     * @param args
     *            the arguments to the format of the message to log
     */
    public final void log(
            final Level logLevel,
            final String format,
            final Object... args)
    {
        if ((logLevel != null) && !repository.isDisabled(logLevel.toInt())
                && logLevel.isGreaterOrEqual(getEffectiveLevel()))
        {
            forcedLog(FQCN, logLevel, //
                MiscUtils.format(format, format, args), null);
        }
    }

    /**
     * Verifies the given condition, throwing an instance of the given throwable
     * type if it fails.
     * 
     * @param <T>
     *            the type of throwable to throw if the condition fails
     * 
     * @param logLevel
     *            the level at which the diagnostic should be emitted, or
     *            {@code null} if the diagnostic should be emitted at the
     *            default level for throwables
     * 
     * @param condition
     *            the condition to be checked
     * 
     * @param throwableType
     *            the type of throwable to be instantiated if the condition
     *            fails
     * 
     * @param descriptionFormat
     *            the format of a description of the problem
     * 
     * @param formatArgs
     *            optional arguments to be passed to the description format
     * 
     * @throws T
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    public final <T extends Throwable> void verify(
//            final Level logLevel,
//            final boolean condition,
//            final Class<T> throwableType,
//            final String descriptionFormat,
//            final Object... formatArgs)
//        throws T
//    {
//        verify(logLevel, condition, throwableType, null, descriptionFormat,
//            formatArgs);
//    }

    /**
     * Verifies the given worker does not fail, throwing an instance of the
     * given throwable type if it does.
     * 
     * @param <T>
     *            the type of throwable to throw if the worker fails
     * 
     * @param logLevel
     *            the level at which the diagnostic should be emitted, or
     *            {@code null} if the diagnostic should be emitted at the
     *            default level for throwables
     * 
     * @param worker
     *            the worker to be executed
     * 
     * @param throwableType
     *            the type of throwable to be instantiated if the worker fails
     * 
     * @param descriptionFormat
     *            the format of a description of the problem
     * 
     * @param formatArgs
     *            optional arguments to be passed to the description format
     * 
     * @throws T
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    public final <T extends Throwable> void verify(
//            final Level logLevel,
//            final Worker worker, // NOPMD - deprecated method
//            final Class<T> throwableType,
//            final String descriptionFormat,
//            final Object... formatArgs)
//        throws T
//    {
//        Throwable actual = null;
//        try
//        {
//            worker.execute(); // NOPMD - deprecated method
//        }
//        catch (Throwable genericThr) // NOPMD - will remove this anyway
//        {
//            actual = genericThr;
//        }
//        verify(logLevel, (actual == null), throwableType, actual,
//            descriptionFormat, formatArgs);
//    }

    /**
     * Throws an {@link IllegalArgumentException} after issuing the appropriate
     * diagnostic if the given condition is false.
     * 
     * @param logLevel
     *            the level at which the diagnostic should be emitted, or
     *            {@code null} if the diagnostic should be emitted at the
     *            default level for throwables
     * 
     * @param argName
     *            the name of the argument to be checked
     * 
     * @param arg
     *            the argument to be checked
     * 
     * @param condition
     *            the condition to be checked
     * 
     * @param descriptionFormat
     *            the format of a description of the problem
     * 
     * @param formatArgs
     *            optional arguments to be passed to the description format
     * 
     * @throws IllegalArgumentException
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    public final void verifyArg(
//            final Level logLevel,
//            final String argName,
//            final Object arg,
//            final boolean condition,
//            final String descriptionFormat,
//            final Object... formatArgs)
//        throws IllegalArgumentException
//    {
//        verify(logLevel, condition, IllegalArgumentException.class,
//            "The argument %s with value %s failed to satisfy "
//                    + "the following condition: %s", argName, arg, //
//            MiscUtils.format("", descriptionFormat, formatArgs));
//    }

    /**
     * Throws an {@link IllegalArgumentException} after issuing the appropriate
     * diagnostic if the given condition is false.
     * 
     * @param argName
     *            the name of the argument to be checked
     * 
     * @param arg
     *            the argument to be checked
     * 
     * @param condition
     *            the condition to be checked
     * 
     * @param descriptionFormat
     *            the format of a description of the problem
     * 
     * @param formatArgs
     *            optional arguments to be passed to the description format
     * 
     * @throws IllegalArgumentException
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    public final void verifyArg(
//            final String argName,
//            final Object arg,
//            final boolean condition,
//            final String descriptionFormat,
//            final Object... formatArgs)
//        throws IllegalArgumentException
//    {
//        verifyArg(Level.ERROR, argName, arg, condition, descriptionFormat,
//            formatArgs);
//    }

    /**
     * Throws a {@link NullArgumentException} after issuing the appropriate
     * diagnostic if the given argument is {@code null}.
     * 
     * @param logLevel
     *            the level at which the diagnostic should be emitted, or
     *            {@code null} if the diagnostic should be emitted at the
     *            default level for throwables
     * 
     * @param argName
     *            the name of the argument to be checked
     * 
     * @param arg
     *            the argument to be checked
     * 
     * @throws NullArgumentException
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    public final void verifyArgNonNull(
//            final Level logLevel,
//            final String argName,
//            final Object arg)
//        throws NullArgumentException
//    {
//        verify(logLevel, (arg != null), NullArgumentException.class, argName);
//    }

    /**
     * Throws a {@link NullArgumentException} after issuing the appropriate
     * diagnostic if the given argument is {@code null}.
     * 
     * @param argName
     *            the name of the argument to be checked
     * 
     * @param arg
     *            the argument to be checked
     * 
     * @throws NullArgumentException
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    public final void verifyArgNonNull(
//            final String argName,
//            final Object arg)
//        throws NullArgumentException
//    {
//        verifyArgNonNull(Level.ERROR, argName, arg);
//    }

    /**
     * Verifies the given condition, throwing an {@link IllegalStateException}
     * if it fails.
     * 
     * @param logLevel
     *            the level at which the diagnostic should be emitted, or
     *            {@code null} if the diagnostic should be emitted at the
     *            default level for throwables
     * 
     * @param condition
     *            the condition to be checked
     * 
     * @param descriptionFormat
     *            the format of a description of the problem
     * 
     * @param formatArgs
     *            optional arguments to be passed to the description format
     * 
     * @throws IllegalStateException
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    public final void verifyState(
//            final Level logLevel,
//            final boolean condition,
//            final String descriptionFormat,
//            final Object... formatArgs)
//        throws IllegalStateException
//    {
//        verify(logLevel, condition, IllegalStateException.class, null,
//            descriptionFormat, formatArgs);
//    }

    /**
     * Verifies the given condition, throwing an {@link IllegalStateException}
     * if it fails.
     * 
     * @param condition
     *            the condition to be checked
     * 
     * @param descriptionFormat
     *            the format of a description of the problem
     * 
     * @param formatArgs
     *            optional arguments to be passed to the description format
     * 
     * @throws IllegalStateException
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    public final void verifyState(
//            final boolean condition,
//            final String descriptionFormat,
//            final Object... formatArgs)
//        throws IllegalStateException
//    {
//        verifyState(Level.ERROR, condition, descriptionFormat, formatArgs);
//    }

    /**
     * Verifies the given condition, throwing an instance of the given throwable
     * type if it fails.
     * 
     * @param <T>
     *            the type of throwable to throw if the condition fails
     * 
     * @param logLevel
     *            the level at which the diagnostic should be emitted, or
     *            {@code null} if the diagnostic should be emitted at the
     *            default level for throwables
     * 
     * @param condition
     *            the condition to be checked
     * 
     * @param throwableType
     *            the type of throwable to be instantiated if the condition
     *            fails
     * 
     * @param cause
     *            the underlying cause of the condition failure
     * 
     * @param descriptionFormat
     *            the format of a description of the problem
     * 
     * @param formatArgs
     *            optional arguments to be passed to the description format
     * 
     * @throws T
     *             if the given condition is {@code false}
     */
//    @Deprecated
//    private final <T extends Throwable> void verify(
//            final Level logLevel,
//            final boolean condition,
//            final Class<T> throwableType,
//            final Throwable cause,
//            final String descriptionFormat,
//            final Object... formatArgs)
//        throws T
//    {
//        if (!condition)
//        {
//            final T throwable = ThrowableInfo.getInstance(throwableType)
//                .instantiate(
//                    MiscUtils.format(null, descriptionFormat,
//                        formatArgs), cause);
//            throw log(logLevel, throwable, false, null);
//        }
//    }
}
