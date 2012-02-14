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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility to build sets.
 * 
 * @param <M>
 *            the type of member of the set to be built
 * @author Luis Guimbarda
 */
public class SetBuilder<M> extends AbstractCollectionBuilder<M, Set<M>, SetBuilder<M>> {
	/**
	 * Creates a new {@link SetBuilder}
	 */
	public SetBuilder() {
		this(new HashSet<M>());
	}

	/**
	 * Creates a new {@link SetBuilder} using this given collection
	 * 
	 * @param collection
	 *            the collection on which this {@link SetBuilder} will build
	 */
	public SetBuilder(Set<M> collection) {
		super(collection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xutil.coll.alt.CollectionBuilder#getImmutableCollection()
	 */
	@Override
	public Set<M> newImmutableInstance() {
		return Collections.unmodifiableSet(newInstance());
	}
}
