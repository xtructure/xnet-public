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
package com.xtructure.xutil.id;

import java.util.Set;

/**
 * A manager of {@link XIdObject}s.
 * 
 * @param <V>
 *            the type of object managed by this manager
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XIdObjectManager<V extends XIdObject> {
	/**
	 * Returns the ids of all registered objects.
	 * 
	 * @return the ids of all registered objects
	 */
	public Set<XId> getIds();

	/**
	 * Returns the object registered with this manager with the given id.
	 * 
	 * @param id
	 *            the id of the object to be returned
	 * @return the object registered with this manager with the given id
	 */
	public V getObject(XId id);

	/**
	 * Registers the given object with this manager.
	 * 
	 * @param object
	 *            the object to register
	 * @throws IllegalArgumentException
	 *             if the given object is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if an object with the given id is currently registered with
	 *             this manager
	 */
	public void register(V object);

	/**
	 * Unregisters an id from this manager.
	 * 
	 * @param id
	 *            the id of the object to be unregistered
	 * @throws IllegalArgumentException
	 *             if the given id is <code>null</code>
	 */
	public void unregister(XId id);

	/**
	 * Unregisters an object from this manager.
	 * 
	 * @param object
	 *            the object to be unregistered
	 */
	public void unregister(V object);

	/**
	 * Clears all registered objects.
	 */
	public void clear();

	/**
	 * Returns the objects registered with this manager that are ancestors of
	 * the object with the given id.
	 * 
	 * @param descendentId
	 *            the id of the descendant of the objects to be returned
	 * @return the objects registered with this manager that are ancestors of
	 *         the object with the given id
	 * @throws IllegalArgumentException
	 *             if the given descendant id is <code>null</code>
	 */
	public Set<V> getAncestors(XId descendentId);

	/**
	 * Returns the objects registered with this manager that are ancestors of
	 * the given object.
	 * 
	 * @param descendent
	 *            the descendant of the objects to be returned
	 * @return the objects registered with this manager that are ancestors of
	 *         the given object
	 * @throws IllegalArgumentException
	 *             if the given descendant is <code>null</code>
	 */
	public Set<V> getAncestors(V descendent);

	/**
	 * Returns the objects registered with this manager that are descendants of
	 * the object with the given id.
	 * 
	 * @param ancestorId
	 *            the id of the ancestor of the objects to be returned
	 * @return the objects registered with this manager that are descendants of
	 *         the object with the given id
	 * @throws IllegalArgumentException
	 *             if the given ancestor id is <code>null</code>
	 */
	public Set<V> getDescendents(XId ancestorId);

	/**
	 * Returns the objects registered with this manager that are descendants of
	 * the given object.
	 * 
	 * @param ancestor
	 *            the ancestor of the objects to be returned
	 * @return the objects registered with this manager that are descendants of
	 *         the given object
	 * @throws IllegalArgumentException
	 *             if the given ancestor is <code>null</code>
	 */
	public Set<V> getDescendents(V ancestor);
}
