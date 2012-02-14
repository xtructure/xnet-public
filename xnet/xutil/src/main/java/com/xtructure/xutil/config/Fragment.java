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

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * The {@link Fragment} interface describes delegate methods for use by objects,
 * their {@link XConfiguration}s, and serialization in a parameter-centric
 * manner.
 * 
 * @author Luis Guimbarda
 * 
 * @param <T>
 *            the type whose parameters are described by this fragment.
 */
public interface Fragment<T> {
	/**
	 * Returns the id of the parameter associated with this fragment.
	 * 
	 * @return the id of the parameter associated with this fragment.
	 */
	public <V> XValId<V> getParameterId();

	/**
	 * Returns the description of the parameter associated with this fragment.
	 * 
	 * @return the description of the parameter associated with this fragment.
	 */
	public String getDescription();

	/**
	 * Creates an extended XId that identifies the field associated with this
	 * fragment in the object with the given id.
	 * 
	 * @param baseId
	 * @return the extended id
	 */
	public XId generateExtendedId(final XId baseId);

	/**
	 * Returns the value of the field associated with this fragment in the given
	 * object.
	 * 
	 * @param object
	 *            the object to be interrogated
	 * @return the value of this fragment on the given node
	 */
	public <V> V getValue(T object);

	/**
	 * Sets the field associated with this fragment in the given object to the
	 * given value.
	 * 
	 * @param object
	 *            the object to be set
	 * @param value
	 *            the value to set
	 */
	public void setValue(T object, Object value);

	/**
	 * Returns an {@link Attribute} for the parameter associated with this
	 * fragment
	 * 
	 * @return an {@link Attribute} for the parameter associated with this
	 *         fragment
	 */
	public <V> Attribute<V> getAttribute();

	/**
	 * Returns an {@link Element} for the parameter associated with this
	 * fragment
	 * 
	 * @return an {@link Element} for the parameter associated with this
	 *         fragment
	 */
	public <V> Element<V> getElement();
}
