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

import com.xtructure.xutil.valid.Condition;

/**
 * A condition that checks if an object (of type boolean) is
 * {@link Boolean#TRUE}.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public class IsTrueCondition implements Condition {
	/**
	 * Creates a new condition that is satisfied by objects that are equal to
	 * {@link Boolean#TRUE}
	 */
	public IsTrueCondition() {
		// nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the object is equal to {@link Boolean#TRUE}, false
	 *         otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		return Boolean.TRUE.equals(object);
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("must be true");
	}
}
