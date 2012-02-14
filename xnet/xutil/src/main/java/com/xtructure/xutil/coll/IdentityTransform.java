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
package com.xtructure.xutil.coll;

/**
 * Transform that simply returns the object given to it.
 * 
 * @author Luis Guimbarda
 * @param <T>
 *            the type of data for which this {@link IdentityTransform} is
 *            defined
 */
public class IdentityTransform<T> implements Transform<T, T> {
	/** the singleton instance of {@link IdentityTransform} */
	private static final IdentityTransform<?>	INSTANCE	= new IdentityTransform<Object>();

	/**
	 * Returns the singleton instance of {@link IdentityTransform}
	 * 
	 * @return the singleton instance of {@link IdentityTransform}
	 */
	@SuppressWarnings("unchecked")
	public static <T> IdentityTransform<T> getInstance() {
		return (IdentityTransform<T>) INSTANCE;
	}

	/**
	 * Creates a new {@link IdentityTransform}
	 */
	private IdentityTransform() {}

	/**
	 * Returns the given object.
	 * 
	 * @return the given object.
	 */
	@Override
	public T transform(T i) {
		return i;
	}
}
