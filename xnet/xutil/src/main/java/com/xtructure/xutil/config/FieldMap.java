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
import com.xtructure.xutil.id.XValId;

/**
 * TODO : rename to something more appropriate (xsettings or something).
 * 
 * @author Luis Guimbarda
 * 
 */
public interface FieldMap {
	/**
	 * Returns the id of the parent of this FieldMap.
	 * 
	 * @return the id of the parent of this FieldMap
	 */
	public XId getConfigurationId();

	/**
	 * Returns the field ids of this FieldMap.
	 * 
	 * @return the field ids of this FieldMap
	 */
	public Set<XId> getFieldIds();

	/**
	 * Returns the identified field from this FieldMap.
	 * 
	 * @param id
	 *            the id of the field to return
	 * 
	 * @return the identified field
	 */
	public XField<?> getField(XId id);

	/**
	 * Sets the value of the identified field from this FieldMap.
	 *
	 * @param <V> the value type
	 * @param id the id
	 * @param v the v
	 * @return the v
	 */
	public <V> V set(XId id, V v);

	/**
	 * Sets the all.
	 *
	 * @param fieldMap the new all
	 */
	public void setAll(FieldMap fieldMap);

	/**
	 * Gets the value of the identified field from this FieldMap.
	 *
	 * @param id the id
	 * @return the object
	 */
	public Object get(XId id);

	/**
	 * Gets the value of the identified field from this FieldMap.
	 *
	 * @param <V> the value type
	 * @param id the id
	 * @return the v
	 */
	public <V> V get(XValId<V> id);
}
