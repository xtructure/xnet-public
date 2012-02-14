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

package com.xtructure.xutil.format;

import java.awt.Color;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javolution.text.Cursor;

/**
 * A text format for {@link Color}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class ColorTextFormat
        extends XTextFormat<Color>
{
    /** The pattern describing this text format. */
    private static final Pattern PATTERN = Pattern
        .compile("rgba=\\[(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\]");

    /** The singleton instance of this class. */
    private static final ColorTextFormat INSTANCE = new ColorTextFormat();

    /**
     * Returns a text format for {@link Color}.
     * 
     * @return a text format for {@link Color}
     */
    public static final ColorTextFormat getInstance()
    {
        return INSTANCE;
    }

    /** Creates a new text format for {@link Color} instances. */
    private ColorTextFormat()
    {
        super(Color.class);
    }

    /** {@inheritDoc} */
    @Override
    public final Appendable format(
            final Color color,
            final Appendable str)
        throws IOException
    {
        return str.append(String.format("rgba=[%d,%d,%d,%d]", color.getRed(),
            color.getGreen(), color.getBlue(), color.getAlpha()));
    }

    /** {@inheritDoc} */
    @Override
    public final Color parse(
            final CharSequence seq,
            final Cursor cursor)
    {
        final CharSequence str = seq.subSequence( //
            cursor.getIndex(), seq.length());
        final Matcher matcher = PATTERN.matcher(str);
        if (!matcher.matches())
        {
            // maybe this is a color name?
            try
            {
                final Color rval = (Color)Color.class.getField(
                    str.toString().toUpperCase()).get(null);
                cursor.increment(str.length());
                return rval;
            }
            catch (SecurityException ignoredEx)
            {
                // ignore...
            }
            catch (NoSuchFieldException ignoredEx)
            {
                // ignore...
            }
            catch (IllegalArgumentException ignoredEx)
            {
                // ignore...
            }
            catch (IllegalAccessException ignoredEx)
            {
                // ignore...
            }

            // nope, just don't know what this is...
            throw new XFormatException(
                "given string '%s' cannot be parsed as a color", str);
        }
        cursor.increment(str.length());
        return new Color( //
            Integer.parseInt(matcher.group(1)), //
            Integer.parseInt(matcher.group(2)), //
            Integer.parseInt(matcher.group(3)), //
            Integer.parseInt(matcher.group(4)));
    }
}
