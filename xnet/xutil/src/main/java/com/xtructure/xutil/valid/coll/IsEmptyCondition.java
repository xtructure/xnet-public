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

import java.util.Collection;
import java.util.Map;

import com.xtructure.xutil.valid.Condition;

/**
 * A condition that checks if a given object is empty. It calls
 * {@link String#isEmpty()}, {@link Collection#isEmpty()}, or
 * {@link Map#isEmpty()} as appropriate, or if the object is an array, checks
 * that its length is 0.
 * 
 * @author Luis Guimbarda
 * 
 */
public class IsEmptyCondition implements Condition {
	/**
	 * Creates a new {@link IsEmptyCondition}
	 */
	public IsEmptyCondition() {
		// nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object has size 0 (whether it's a
	 *         {@link String}, {@link Collection}, {@link Map}, or an array),
	 *         false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		if (object != null) {
			if (object instanceof String) {
				return ((String) object).isEmpty();
			}
			if (object instanceof Collection<?>) {
				return ((Collection<?>) object).isEmpty();
			}
			if (object instanceof Map<?, ?>) {
				return ((Map<?, ?>) object).isEmpty();
			}
			if (object instanceof Object[]) {
				return ((Object[]) object).length == 0;
			}
			if (object instanceof char[]) {
				return ((char[]) object).length == 0;
			}
			if (object instanceof byte[]) {
				return ((byte[]) object).length == 0;
			}
			if (object instanceof short[]) {
				return ((short[]) object).length == 0;
			}
			if (object instanceof int[]) {
				return ((int[]) object).length == 0;
			}
			if (object instanceof long[]) {
				return ((long[]) object).length == 0;
			}
			if (object instanceof boolean[]) {
				return ((boolean[]) object).length == 0;
			}
			if (object instanceof float[]) {
				return ((float[]) object).length == 0;
			}
			if (object instanceof double[]) {
				return ((double[]) object).length == 0;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "must be empty";
	}
}
