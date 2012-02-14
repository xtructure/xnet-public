/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xsim.
 *
 * xsim is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xsim is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xsim.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.xtructure.xsim.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XComponent;
import com.xtructure.xutil.id.XId;

/**
 * A simple border.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class SimpleXBorder
        extends AbstractXBorder
{
    /**
     * The pattern for parsable lines.
     */
    private static final Pattern LINE_PATTERN = Pattern.compile("(.*)->(.*)");

    /**
     * The pattern for parsable addresses.
     */
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("(.*):(.*)");

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(SimpleXBorder.class);

    /**
     * The known components to this border.
     */
    private final Map<String, XComponent<?>> _components = new HashMap<String, XComponent<?>>();

    /**
     * Creates a new simple border.
     */
    public SimpleXBorder()
    {
        super();
    }

    /**
     * Adds a component to this border.
     * 
     * @param name
     *            the name of the component to add
     * 
     * @param component
     *            the component to add
     */
    public final void nameComponent(
            final String name,
            final XComponent<?> component)
    {
        _components.put(name, component);
        component.addBorder(this);
    }

    /**
     * Adds a component to this border.
     * 
     * @param component
     *            the component to add
     */
    public final void addComponent(
            final XComponent<?> component)
    {
        LOGGER.trace("begin {}.addComponent({})", new Object[] {
                getClass().getSimpleName(), component });

        _components.put(component.getId().toString(), component);
        component.addBorder(this);

        LOGGER.trace("end {}.addComponent()", getClass().getSimpleName());
    }

    /**
     * Loads the named file.
     * 
     * @param filename
     *            the name of a file containing associations
     * 
     * @throws IOException
     *             if the file could not be read
     */
    public final void loadAssociations(
            final String filename)
        throws IOException
    {
        LOGGER.trace("begin {}.loadAssociations({})", new Object[] {
                getClass().getSimpleName(), filename });

        final BufferedReader in = new BufferedReader(new FileReader(filename));
        while (true)
        {
            final String line = in.readLine();
            if (line == null)
            {
                break;
            }
            final Matcher matcher = LINE_PATTERN.matcher(line);
            if (line.trim().startsWith("#") || line.isEmpty())
            {
                continue;
            }
            if (!matcher.matches())
            {
                LOGGER.warn("could not parse line '{}'", line);
                continue;
            }

            final Set<XAddress> sourceAddresses = parseAddresses(matcher
                .group(1));
            if (sourceAddresses.isEmpty())
            {
                LOGGER.warn("could not parse any addresses from '{}'", //
                    matcher.group(1));
                continue;
            }

            final Set<XAddress> targetAddresses = parseAddresses(matcher
                .group(2));
            if (targetAddresses.isEmpty())
            {
                LOGGER.warn("could not parse any addresses from '{}'", //
                    matcher.group(2));
                continue;
            }

            for (final XAddress sourceAddress : sourceAddresses)
            {
                for (final XAddress targetAddress : targetAddresses)
                {
                    LOGGER.debug("associating source {} with target {}",
                        sourceAddress, targetAddress);
                    associate(sourceAddress, targetAddress);
                }
            }
        }
        in.close();

        LOGGER.trace("end {}.loadAssociations()", getClass().getSimpleName());
    }

    /**
     * Parses an address set from the given string.
     * 
     * @param side
     *            the string to be parsed
     * 
     * @return the address set parsed
     */
    private final Set<XAddress> parseAddresses(
            final String side)
    {
        LOGGER.trace("begin {}.parseAddresses({})", new Object[] {
                getClass().getSimpleName(), side });

        final Set<XAddress> addresses = new HashSet<XAddress>();
        for (final String addrStr : side.split(","))
        {
            if ((addrStr == null) || addrStr.isEmpty())
            {
                continue;
            }
            final Matcher matcher = ADDRESS_PATTERN.matcher(addrStr.trim());
            if (!matcher.matches())
            {
                LOGGER.warn("could not parse address '{}'", addrStr);
                continue;
            }
            final XComponent<?> comp = _components //
                .get(matcher.group(1).trim());
            if (comp == null)
            {
                LOGGER.warn("no component with name '{}'", matcher.group(1)
                    .trim());
                continue;
            }
            // final XId partId = XId.parse(matcher.group(2).trim());
            final XId partId = XId.TEXT_FORMAT.parse(matcher.group(2).trim());
            final XAddress addr = new XAddressImpl(comp, partId);
            LOGGER.debug("parsed {}", addr);
            addresses.add(addr);
        }

        LOGGER.trace("will return: {}", addresses);
        LOGGER.trace("end {}.parseAddresses()", getClass().getSimpleName());
        return addresses;
    }
}
