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

import java.util.Set;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;

/**
 * A configuration.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XConfiguration extends XIdObject {
	/**
	 * Returns the id of the parent of this configuration.
	 * 
	 * @return the id of the parent of this configuration
	 */
	public XId getParentId();

	/**
	 * Returns the parameter ids of this configuration.
	 * 
	 * @return the parameter ids of this configuration
	 */
	public Set<XId> getParameterIds();

	/**
	 * Returns the identified parameter from this configuration.
	 * 
	 * @param id
	 *            the id of the parameter to return
	 * @return the identified parameter
	 */
	public XParameter<?> getParameter(XId id);

	/**
	 * New field map.
	 *
	 * @return the field map
	 */
	public FieldMap newFieldMap();
}
