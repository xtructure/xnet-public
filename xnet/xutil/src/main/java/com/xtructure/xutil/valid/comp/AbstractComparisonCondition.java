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

import com.xtructure.xutil.valid.AbstractValueCondition;

/**
 * A base implementation for comparison-based conditions.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * @param <T>
 *            the type of the value to which object are compared
 */
public abstract class AbstractComparisonCondition<T extends Comparable<T>> extends AbstractValueCondition<T> {
	/**
	 * Creates a new condition with the given value.
	 * 
	 * @param value
	 *            the value with which this condition will make comparisons
	 * @throws IllegalArgumentException
	 *             if the given condition is null
	 */
	public AbstractComparisonCondition(T value) throws IllegalArgumentException {
		super(value, false);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public final boolean isSatisfiedBy(Object object) {
		try {
			return makeComparison((T) object);
		} catch (ClassCastException e) {}
		return false;
	}

	/**
	 * Makes the comparison between this condition's value and that given.
	 * 
	 * @param t
	 *            the value with which to compare this condition's value
	 * @return <code>true</code> if this condition is satisfied by the
	 *         comparison; <code>false</code> otherwise
	 */
	public abstract boolean makeComparison(T t);
}
