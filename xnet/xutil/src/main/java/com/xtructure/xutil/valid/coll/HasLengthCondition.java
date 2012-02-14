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
package com.xtructure.xutil.valid.coll;

import com.xtructure.xutil.valid.AbstractValueCondition;

/**
 * A condition that checks if a given object (of type {@link String} or array)
 * has a given length.
 * 
 * @author Luis Guimbarda
 * 
 */
public class HasLengthCondition extends AbstractValueCondition<Integer> {
	/**
	 * Creates a new {@link HasSizeCondition} that is satisfied by strings or
	 * arrays with the given size.
	 * 
	 * @param length
	 *            the length of strings or arrays that satisfy this condition
	 */
	public HasLengthCondition(Integer length) throws IllegalArgumentException {
		super(length, false);
		if (length < 0) {
			throw new IllegalArgumentException("length: must be non-negative");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object is string or array and has length equal
	 *         to this condition's value, false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		if (object instanceof String) {
			return ((String) object).length() == getValue();
		}
		if (object instanceof Object[]) {
			return ((Object[]) object).length == getValue();
		}
		if (object instanceof char[]) {
			return ((char[]) object).length == getValue();
		}
		if (object instanceof byte[]) {
			return ((byte[]) object).length == getValue();
		}
		if (object instanceof short[]) {
			return ((short[]) object).length == getValue();
		}
		if (object instanceof int[]) {
			return ((int[]) object).length == getValue();
		}
		if (object instanceof long[]) {
			return ((long[]) object).length == getValue();
		}
		if (object instanceof boolean[]) {
			return ((boolean[]) object).length == getValue();
		}
		if (object instanceof float[]) {
			return ((float[]) object).length == getValue();
		}
		if (object instanceof double[]) {
			return ((double[]) object).length == getValue();
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format("must have length %d", getValue());
	}
}
