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

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * A comparator for {@link Pattern} instances.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.2
 */
public final class PatternComparator
        implements Comparator<Pattern>
{
    /** The singleton instance of this class. */
    private static final PatternComparator INSTANCE = new PatternComparator();

    /**
     * Returns a pattern comparator.
     *
     * @return a pattern comparator
     */
    public static final PatternComparator getInstance()
    {
        return INSTANCE;
    }

    /** Creates a new pattern comparator. */
    private PatternComparator()
    {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public final int compare(
            final Pattern pattern1,
            final Pattern pattern2)
    {
        if (pattern1 == null)
        {
            return ((pattern2 == null)
                    ? 0
                    : 1);
        }
        if (pattern2 == null)
        {
            return -1;
        }
        return pattern1.pattern().compareTo(pattern2.pattern());
    }
}
