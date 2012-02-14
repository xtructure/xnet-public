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
 * A field bound to a {@link XParameter}.
 * 
 * @param <V>
 *            the type of this value
 * @author Peter N&uuml;rnberg
 * @version 0.9.6
 */
public interface XField<V> {
	/**
	 * Returns the parameter from which this field is derived.
	 * 
	 * @return the parameter from which this field is derived
	 */
	public XParameter<V> getParameter();

	/**
	 * Returns the value of this field.
	 * 
	 * @return the value of this field
	 */
	public V getValue();

	/**
	 * Returns the vetting strategy used by this field.
	 * 
	 * @return the vetting strategy used by this field
	 */
	public VettingStrategy<V> getVettingStrategy();

	/**
	 * Sets the value of this field.
	 * 
	 * @param value
	 *            a proposed value for this field
	 * @return the actual new value of this field
	 */
	public V setValue(V value);
}
