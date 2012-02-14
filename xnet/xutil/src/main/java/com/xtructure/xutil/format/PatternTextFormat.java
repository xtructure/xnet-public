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

import java.io.IOException;
import java.util.regex.Pattern;

import javolution.text.Cursor;

/**
 * A text format for {@link Pattern}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class PatternTextFormat
        extends XTextFormat<Pattern>
{
    /** The singleton instance of this class. */
    private static final PatternTextFormat INSTANCE = new PatternTextFormat();

    /**
     * Returns a text format for {@link Pattern}.
     * 
     * @return a text format for {@link Pattern}
     */
    public static final PatternTextFormat getInstance()
    {
        return INSTANCE;
    }

    /** Creates a new text format for {@link Pattern} instances. */
    private PatternTextFormat()
    {
        super(Pattern.class);
        setInstance(this, Pattern.class);
    }

    /** {@inheritDoc} */
    @Override
    public final Appendable format(
            final Pattern pattern,
            final Appendable str)
        throws IOException
    {
        return str.append(pattern.pattern());
    }

    /** {@inheritDoc} */
    @Override
    public final Pattern parse(
            final CharSequence seq,
            final Cursor cursor)
    {
        final Pattern rval = Pattern.compile(seq.subSequence( //
            cursor.getIndex(), seq.length()).toString());
        cursor.increment(seq.length());
        return rval;
    }
}
