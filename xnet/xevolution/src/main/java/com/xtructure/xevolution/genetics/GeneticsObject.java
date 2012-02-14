/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.genetics;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

import com.xtructure.xevolution.evolution.EvolutionObject;
import com.xtructure.xevolution.genetics.impl.GenomeImpl;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;
import com.xtructure.xutil.id.XValId;

/**
 * Interface for entities or collections of entities involved in evolution
 * (e.g., {@link Genome}s and {@link Population}). They all have ids and base
 * ids. The latter should be common among all {@link EvolutionObject}s of the
 * same type. For example, all {@link GenomeImpl}s have the base id "Genome",
 * though different instances may have ids "Genome:[1]" and "Genome:[2]". Also,
 * {@link EvolutionObject}'s have a set of attributes, which may be used to hold
 * statistics, measurements, or any kind of observation, so long as it's
 * represented with a {@link Comparable} value.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface GeneticsObject extends XIdObject, EvolutionObject {
	/** the {@link XValId} for the age attribute */
	public static final XValId<Long>	AGE_ATTRIBUTE_ID	= XValId.newId("age", Long.class);
	/** the {@link XValId} for the age of last improvement attribute */
	public static final XValId<Long>	AGE_LI_ATTRIBUTE_ID	= XValId.newId("ageLastImproved", Long.class);

	/**
	 * Returns the common base {@link XId} used this type of
	 * {@link GeneticsObject}. For example, all {@link Genome}s may share the
	 * base id "Genome" (while being differentiated by instance number).
	 * 
	 * @return the base {@link XId} for this type of
	 */
	public XId getBaseId();

	/**
	 * Returns the attributes of this {@link GeneticsObject}.
	 * 
	 * @return the attributes of this {@link GeneticsObject}.
	 */
	public ValueMap getAttributes();

	/**
	 * Returns the age of this {@link GeneticsObject}.
	 * 
	 * @return the age of this {@link GeneticsObject}.
	 */
	long getAge();

	/**
	 * Increments the age of this {@link GeneticsObject}.
	 */
	void incrementAge();

	/**
	 * Returns the attribute of this {@link GeneticsObject} with the given
	 * valueId.
	 * 
	 * @param <V>
	 *            the type of the requested attribute
	 * @param valueId
	 *            the {@link XValId} for the requested attribute
	 * @return the requested attribute
	 */
	public <V> V getAttribute(XValId<V> valueId);

	/**
	 * Sets the attribute identified by the given {@link XValId} of this
	 * {@link GeneticsObject} with the given value.
	 * 
	 * @param <V>
	 *            the type of the attribute to set
	 * @param valueId
	 *            the ValueId for the attribute to set
	 * @param value
	 *            the value to which the attribute is set
	 * @return the previous value of the attribute
	 */
	public <V> V setAttribute(XValId<V> valueId, V value);

	/**
	 * Checks the state of this {@link GeneticsObject}, throwing an
	 * {@link IllegalStateException} if something is wrong.
	 * 
	 * @throws IllegalStateException
	 *             If this {@link GeneticsObject} is in an invalid state.
	 */
	public void validate() throws IllegalStateException;

	/** comparator for sorting {@link GeneticsObject}s by attribute */
	public static final class ByAttribute<T extends GeneticsObject, V extends Comparable<V>> implements Comparator<T> {
		private final boolean	descending;
		private final XValId<V>	valueId;

		public ByAttribute(XValId<V> valueId, boolean descending) {
			this.descending = descending;
			this.valueId = valueId;
		}

		@Override
		public int compare(T o1, T o2) {
			CompareToBuilder ctb = new CompareToBuilder();
			if (descending) {
				ctb.append(o2.getAttribute(valueId), o1.getAttribute(valueId));
			} else {
				ctb.append(o1.getAttribute(valueId), o2.getAttribute(valueId));
			}
			return ctb.append(o1.getId(), o2.getId())//
					.toComparison();
		}
	}
}
