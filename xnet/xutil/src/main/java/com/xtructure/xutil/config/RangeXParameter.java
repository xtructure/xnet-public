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
package com.xtructure.xutil.config;

import com.xtructure.xutil.Range;

/**
 * A parameter with a {@link Range} of possible values.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface RangeXParameter<V extends Comparable<V>> extends XParameter<V> {
	/**
	 * Returns the lifetime range of this parameter.
	 * 
	 * @return the lifetime range of this parameter
	 */
	public Range<V> getLifetimeRange();

	/**
	 * Returns the initial range of this parameter.
	 * 
	 * @return the initial range of this parameter
	 */
	public Range<V> getInitialRange();

	/**
	 * A utility for generating a random number within the initial range of this
	 * parameter.
	 * 
	 * @return a random number within the initial range of this parameter
	 */
	public V newUniformRandomValue();
}
