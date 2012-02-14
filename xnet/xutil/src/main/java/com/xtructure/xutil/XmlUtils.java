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

import javolution.text.CharArray;
import javolution.text.TextFormat;
import javolution.xml.XMLFormat.InputElement;
import javolution.xml.XMLFormat.OutputElement;
import javolution.xml.stream.XMLStreamException;

/**
 * XML utilities.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class XmlUtils
{
    /**
     * Returns the value of the named attribute on the given element.
     * 
     * @param <T>
     *            the type of value to return
     * 
     * @param xml
     *            the element from which to read the value of the named
     *            attribute
     * 
     * @param name
     *            the name of the attribute, the value of which to read
     * 
     * @param type
     *            the type of value to return
     * 
     * @param defaultValue
     *            the value to be returned if the named attribute was not
     *            present on the given element
     * 
     * @return the value of the named attribute on the given element
     * 
     * @throws XMLStreamException
     *             if the read failed
     */
    public static final <T> T getAttribute(
            final InputElement xml,
            final String name,
            final Class<T> type,
            final T defaultValue)
        throws XMLStreamException
    {
        return getAttribute(xml, name, type, defaultValue, false);
    }

    /**
     * Returns the value of the named attribute on the given element.
     * 
     * @param <T>
     *            the type of value to return
     * 
     * @param xml
     *            the element from which to read the value of the named
     *            attribute
     * 
     * @param name
     *            the name of the attribute, the value of which is to be read
     * 
     * @param type
     *            the type of value to return
     * 
     * @return the value of the named attribute on the given element
     * 
     * @throws XMLStreamException
     *             if the read failed or the named attribute was not present on
     *             the given element
     */
    public static final <T> T getAttribute(
            final InputElement xml,
            final String name,
            final Class<T> type)
        throws XMLStreamException
    {
        return getAttribute(xml, name, type, null, true);
    }

    /**
     * Writes the given value to the named attribute on the given element.
     * 
     * @param xml
     *            the element to which the given value should be written
     * 
     * @param name
     *            the name of the attribute, the value of which is to be written
     * 
     * @param value
     *            the value to write
     * 
     * @throws XMLStreamException
     *             if the write failed
     */
    public static final void setAttribute(
            final OutputElement xml,
            final String name,
            final Object value)
        throws XMLStreamException
    {
        if (value != null)
        {
            xml.setAttribute(name, value);
        }
    }

    /**
     * Returns the value of the named attribute on the given element.
     * 
     * @param <T>
     *            the type of value to return
     * 
     * @param xml
     *            the element from which to read the value of the named
     *            attribute
     * 
     * @param name
     *            the name of the attribute, the value of which is to be read
     * 
     * @param type
     *            the type of value to return
     * 
     * @param defaultValue
     *            the value to be returned if the named attribute was not
     *            present on the given element
     * 
     * @param required
     *            an indication of whether this attribute must be present
     * 
     * @return the value of the named attribute on the given element
     * 
     * @throws XMLStreamException
     *             if the read failed or the named attribute was not present on
     *             the given element
     */
    private static final <T> T getAttribute(
            final InputElement xml,
            final String name,
            final Class<T> type,
            final T defaultValue,
            final boolean required)
        throws XMLStreamException
    {
        final CharArray str = xml.getAttribute(name);
        if (str == null)
        {
            if (required)
            {
                throw new XMLStreamException( //
                    String.format("missing required attribute %s", name), //
                    xml.getStreamReader().getLocation());
            }
            return defaultValue;
        }
        if (type.equals(String.class))
        {
            return type.cast(str.toString());
        }
        final TextFormat<T> format = TextFormat.getInstance(type);
        if (format == null)
        {
            throw new XMLStreamException("No TextFormat instance for " + type);
        }
        return format.parse(str);
    }

    /** Creates XML utilities. */
    private XmlUtils()
    {
        super();
    }
}
