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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.Arrays;
import java.util.Collection;

import com.xtructure.xutil.config.XBuilder;
import com.xtructure.xutil.valid.Condition;

/**
 * A base class for utilities to build collections.
 * 
 * @param <M>
 *            the type of member of the collection to be built
 * @param <C>
 *            the type of collection to be built
 * @param <S>
 *            the type of collection builder
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * @version 0.9.6
 */
public abstract class AbstractCollectionBuilder<M, C extends Collection<M>, S extends AbstractCollectionBuilder<M, C, S>> implements XBuilder<C> {
	/** The delegate collection being built. */
	private final C	_delegateCollection;

	/**
	 * Creates a new collection builder.
	 * 
	 * @param collection
	 *            the delegate collection being built
	 */
	public AbstractCollectionBuilder(final C collection) {
		validateArg("collection", collection, isNotNull());
		_delegateCollection = collection;
	}

	/**
	 * Adds the given member to this collection.
	 * 
	 * @param member
	 *            a member to be added
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public final S add(final M member) {
		_delegateCollection.add(member);
		return (S) this;
	}

	/**
	 * Adds the given member to this collection.
	 * 
	 * @param member
	 *            a member to be added
	 * @param otherMembers
	 *            other members to be added
	 * @return this builder
	 */
	public final S add(final M member, final M... otherMembers) {
		_delegateCollection.add(member);
		return addAll(otherMembers);
	}

	/**
	 * Adds the given members to this collection.
	 * 
	 * @param members
	 *            the members to be added
	 * @return this builder
	 */
	public final S addAll(final M... members) {
		return addAll(Arrays.asList(members));
	}

	/**
	 * Adds the given members to this collection.
	 * 
	 * @param members
	 *            the members to be added
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public final S addAll(final Collection<? extends M> members) {
		if (members != null) {
			_delegateCollection.addAll(members);
		}
		return (S) this;
	}

	/**
	 * Adds the transform of the given members to this collection.
	 * 
	 * @param <N>
	 *            the type of objects being transformed
	 * @param transform
	 *            the {@link Transform} to apply to the given data
	 * @param members
	 *            the data to transform, then add to this collection
	 * @return this builder
	 */
	public final <N> S addAll(final Transform<M, N> transform, final N... members) {
		return addAll(transform, Arrays.asList(members));
	}

	/**
	 * Adds the transform of the given members to this collection.
	 * 
	 * @param <N>
	 *            the type of objects being transformed
	 * @param transform
	 *            the {@link Transform} to apply to the given data
	 * @param members
	 *            the data to transform, then add to this collection
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public final <N> S addAll(final Transform<M, N> transform, final Collection<? extends N> members) {
		if (members != null) {
			for (N member : members) {
				add(transform.transform(member));
			}
		}
		return (S) this;
	}

	/**
	 * Adds the given members that satisfy the given condition to this
	 * collection.
	 * 
	 * @param condition
	 *            the {@link Condition} used to select data
	 * @param members
	 *            the data from which selections are added to this collection
	 * @return this builder
	 */
	public final S addAll(final Condition condition, final M... members) {
		return addAll(condition, Arrays.asList(members));
	}

	/**
	 * Adds the given members that satisfy the given condition to this
	 * collection.
	 * 
	 * @param condition
	 *            the {@link Condition} used to select data
	 * @param members
	 *            the data from which selections are added to this collection
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public final S addAll(final Condition condition, final Collection<? extends M> members) {
		if (members != null) {
			for (M member : members) {
				if (condition.isSatisfiedBy(member)) {
					add(member);
				}
			}
		}
		return (S) this;
	}

	/**
	 * Returns the collection built so far.
	 * 
	 * @return the collection built so far.
	 */
	@Override
	public final C newInstance() {
		return _delegateCollection;
	}

	/**
	 * Returns an immutable view the collection built so far.
	 * 
	 * @return an immutable view the collection built so far.
	 */
	public abstract C newImmutableInstance();
}
