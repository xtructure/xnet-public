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

import com.xtructure.xutil.id.XIdObject;

/**
 * A parameter.
 * 
 * @param <V>
 *            the type of value of this parameter
 * @author Peter N&uuml;rnberg
 * @version 0.9.6
 */
public interface XParameter<V> extends XIdObject {
	
	/** The Constant DEFAULT_DESCRIPTION. */
	public static final String	DEFAULT_DESCRIPTION	= "";
	
	/** The Constant DEFAULT_NULLABLE. */
	public static final boolean	DEFAULT_NULLABLE	= false;
	
	/** The Constant DEFAULT_MUTABLE. */
	public static final boolean	DEFAULT_MUTABLE		= true;

	/**
	 * Returns the description of this parameter.
	 * 
	 * @return the description of this parameter
	 */
	String getDescription();

	/**
	 * Returns a new value bound to this parameter.
	 * 
	 * @return a new value bound to this parameter
	 */
	XField<V> newField();

	/**
	 * Checks if is nullable.
	 *
	 * @return true, if is nullable
	 */
	boolean isNullable();

	/**
	 * Checks if is mutable.
	 *
	 * @return true, if is mutable
	 */
	boolean isMutable();
}
