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

/**
 * The {@link XBuilder} interface describes classes that collect the arguments
 * necessary to construct a specific object, then create it.
 *
 * @param <T> the generic type
 * @author Luis Guimbarda
 */
public interface XBuilder<T> {
	
	/**
	 * Returns a new instance of the class being built by this {@link XBuilder}.
	 *
	 * @return the t
	 */
	public T newInstance();
}
