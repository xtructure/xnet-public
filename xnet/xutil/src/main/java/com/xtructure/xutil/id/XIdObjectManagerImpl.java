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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * A manager of {@link XIdObject}s.
 * 
 * @param <V>
 *            the type of object managed by this manager
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.4
 */
public class XIdObjectManagerImpl<V extends XIdObject> implements XIdObjectManager<V> {
	/** A map from ids to their respective objects. */
	private final Map<XId, V>	_idToObjectMap;

	/**
	 * Creates a new manager.
	 */
	public XIdObjectManagerImpl() {
		this(new HashMap<XId, V>());
	}

	/**
	 * Creates a new manager with the given map.
	 * 
	 * @param map
	 */
	public XIdObjectManagerImpl(Map<XId, V> map) {
		this._idToObjectMap = map;
	}

	/** {@inheritDoc} */
	@Override
	public final Set<XId> getIds() {
		Set<XId> ids = _idToObjectMap.keySet();
		if (ids instanceof SortedSet<?>) {
			return Collections.unmodifiableSortedSet((SortedSet<XId>) ids);
		} else {
			return Collections.unmodifiableSet(_idToObjectMap.keySet());
		}
	}

	/** {@inheritDoc} */
	@Override
	public final V getObject(final XId id) {
		return _idToObjectMap.get(id);
	}

	/** {@inheritDoc} */
	@Override
	public void register(final V object) {
		if (object == null) {
			throw new IllegalArgumentException("object must not be null");
		}
		if (_idToObjectMap.containsKey(object.getId())) {
			throw new IllegalArgumentException(String.format("this manager already contains %s", object.getId()));
		}
		_idToObjectMap.put(object.getId(), object);
	}

	/** {@inheritDoc} */
	@Override
	public void unregister(final XId id) {
		if (id == null) {
			throw new IllegalArgumentException("id must not be null");
		}
		_idToObjectMap.keySet().remove(id);
	}

	/** {@inheritDoc} */
	@Override
	public void unregister(final V object) {
		if (object != null) {
			_idToObjectMap.keySet().remove(object.getId());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void clear() {
		_idToObjectMap.clear();
	}

	/** {@inheritDoc} */
	@Override
	public final Set<V> getAncestors(final XId descendentId) {
		if (descendentId == null) {
			throw new IllegalArgumentException("descendentId must not be null");
		}
		final Set<V> rval = new HashSet<V>();
		for (final XId id : getIds()) {
			if (descendentId.isDescendentOf(id)) {
				rval.add(getObject(id));
			}
		}
		return rval;
	}

	/** {@inheritDoc} */
	@Override
	public final Set<V> getAncestors(final V descendent) {
		if (descendent == null) {
			throw new IllegalArgumentException("descendent must not be null");
		}
		return getAncestors(descendent.getId());
	}

	/** {@inheritDoc} */
	@Override
	public final Set<V> getDescendents(final XId ancestorId) {
		if (ancestorId == null) {
			throw new IllegalArgumentException("ancestorId must not be null");
		}
		final Set<V> rval = new HashSet<V>();
		for (final XId id : getIds()) {
			if (ancestorId.isAncestorOf(id)) {
				rval.add(getObject(id));
			}
		}
		return rval;
	}

	/** {@inheritDoc} */
	@Override
	public final Set<V> getDescendents(final V ancestor) {
		if (ancestor == null) {
			throw new IllegalArgumentException("ancestor must not be null");
		}
		return getDescendents(ancestor.getId());
	}
}
