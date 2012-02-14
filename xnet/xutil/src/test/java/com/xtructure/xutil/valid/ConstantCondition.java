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

package com.xtructure.xutil.valid;

/**
 * A condition that always returns a given boolean constant.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.4
 */
final class ConstantCondition
        implements Condition
{
    /** The value always returned by {@link #isSatisfiedBy(Object)}. */
    private final boolean _constantValue;

    /**
     * Creates a new constant condition.
     * 
     * @param constantValue
     *            the value always returned by {@link #isSatisfiedBy(Object)}
     */
    ConstantCondition(
            final boolean constantValue)
    {
        super();

        _constantValue = constantValue;
    }

    /** {@inheritDoc} */
    @Override
	public final boolean isSatisfiedBy(
            final Object obj)
    {
        return _constantValue;
    }
    
    /** {@inheritDoc} */
    @Override
    public final String toString()
    {
        return String.valueOf(_constantValue);
    }
}
