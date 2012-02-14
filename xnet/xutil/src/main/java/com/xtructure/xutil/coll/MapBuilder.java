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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.xtructure.xutil.config.XBuilder;

/**
 * A utility for building maps.
 * 
 * @param <K>
 *            the type of key of the map to build
 * @param <V>
 *            the type of value of the map to build
 * @author Peter N&uuml;rnberg
 * @version 0.9.6
 */
public class MapBuilder<K, V> implements XBuilder<Map<K, V>> {
	/** The map built by this builder. */
	private final Map<K, V>	_delegateMap;

	/** Creates a new map builder. */
	public MapBuilder() {
		this(new HashMap<K, V>());
	}

	/** Creates a new map builder with the given map. */
	public MapBuilder(Map<K, V> map) {
		this._delegateMap = map;
	}

	/**
	 * Puts the given key/value pair into the map being built by this builder.
	 * 
	 * @param key
	 *            the key to put into the map being built by this builder
	 * @param value
	 *            the value to put into the map being built by this builder
	 * @return this builder
	 */
	public final MapBuilder<K, V> put(final K key, final V value) {
		_delegateMap.put(key, value);
		return this;
	}

	/**
	 * Puts all the key/value pairs in the given map into the map being built by
	 * this builder.
	 * 
	 * @param map
	 * @return this builder
	 */
	public final MapBuilder<K, V> putAll(Map<K, V> map) {
		_delegateMap.putAll(map);
		return this;
	}

	/**
	 * Puts all of the given values into the map being built by this builder.
	 * 
	 * @param keyGenerator
	 *            the {@link Transform} used to generate a key for each value
	 * @param values
	 *            the values to put into the map being built by this builder
	 * @return this builder
	 */
	public final MapBuilder<K, V> putAll(final Transform<K, ? super V> keyGenerator, final Collection<? extends V> values) {
		if ((values != null) && (keyGenerator != null)) {
			for (final V value : values) {
				_delegateMap.put(keyGenerator.transform(value), value);
			}
		}
		return this;
	}

	/**
	 * Returns the map being built by this builder.
	 * 
	 * @return the map being built by this builder
	 */
	@Override
	public final Map<K, V> newInstance() {
		return _delegateMap;
	}

	/**
	 * Returns an immutable copy of the map being built by this builder.
	 * 
	 * @return an immutable copy of the map being built by this builder
	 */
	public final Map<K, V> newImmutableInstance() {
		return Collections.unmodifiableMap(_delegateMap);
	}
}
