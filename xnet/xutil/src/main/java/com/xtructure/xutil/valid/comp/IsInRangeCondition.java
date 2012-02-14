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
package com.xtructure.xutil.valid.comp;

import com.xtructure.xutil.Range;
import com.xtructure.xutil.valid.AbstractValueCondition;

/**
 * A condition that checks if an object (of type {@link Comparable}) is within a
 * specified {@link Range}.
 * 
 * @author Luis Guimbarda
 * 
 * @param <T>
 *            the type of the value to which object are compared
 */
public class IsInRangeCondition<T extends Comparable<T>> extends AbstractValueCondition<Range<T>> {
	/**
	 * Creates a new condition that's satisfied by objects that are within the
	 * given range.
	 * 
	 * @param range
	 *            the range of objects that satisfy this condition
	 * @throws IllegalArgumentException
	 *             if the given range is null;
	 */
	public IsInRangeCondition(Range<T> range) throws IllegalArgumentException {
		super(range, false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object is in this condition's range, false
	 *         otherwise
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isSatisfiedBy(Object object) {
		if (object != null) {
			try {
				return getValue().contains((T) object);
			} catch (ClassCastException e) {}
		}
		return false;
	}
}
