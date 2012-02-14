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

import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.ArrayList;
import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * A base class for {@link XParameter} implementations.
 * 
 * @param <V>
 *            the type of value of this parameter
 * @author Peter N&uuml;rnberg
 * @version 0.9.6
 */
public abstract class AbstractXParameter<V> extends AbstractXIdObject implements XParameter<V> {
	/** The description of this parameter. */
	private final String				_description;
	/** The vetting groups associated with this parameter. */
	private final List<VettingGroup<V>>	_vettingGroups	= new ArrayList<VettingGroup<V>>();
	/** An indication of whether <code>null</code> values are allowed. */
	private final boolean				_nullable;
	/** An indication of whether values are mutable. */
	private final boolean				_mutable;
	/** The base value strategy associated with this parameter. */
	private VettingStrategy<V>			_baseStrategy	= null;
	/**
	 * An indication of whether {@link #_vettingGroups} has been modified since
	 * {@link #_baseStrategy} has been updated.
	 */
	private boolean						_groupsModified	= false;

	/**
	 * Creates a new parameter.
	 * 
	 * @param id
	 *            the id of this parameter
	 * @param description
	 *            the description of this parameter
	 * @param nullable
	 *            an indication of whether <code>null</code> values are allowed
	 * @param mutable
	 *            an indication of whether values are mutable
	 */
	protected AbstractXParameter(final XId id, final String description, final Boolean nullable, final Boolean mutable) {
		super(id);
		_description = description == null ? DEFAULT_DESCRIPTION : description;
		_nullable = nullable == null ? DEFAULT_NULLABLE : nullable;
		_mutable = mutable == null ? DEFAULT_MUTABLE : mutable;
		final VettingGroup<V> vettingGroup;
		if (_nullable) {
			vettingGroup = VettingGroup.getLenientInstance(null, isNotNull());
		} else {
			vettingGroup = VettingGroup.getStrictInstance(isNotNull());
		}
		addVettingGroup(vettingGroup);
	}

	/**
	 * @param parameter
	 */
	protected AbstractXParameter(AbstractXParameter<V> parameter) {
		this(//
				parameter.getId(),//
				parameter._description,//
				parameter._nullable,//
				parameter._mutable);
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription() {
		return _description;
	}

	/**
	 * Returns an indication of whether <code>null</code> values are allowed.
	 * 
	 * @return an indication of whether <code>null</code> values are allowed
	 */
	@Override
	public final boolean isNullable() {
		return _nullable;
	}

	/**
	 * Returns an indication of whether values are mutable.
	 * 
	 * @return an indication of whether values are mutable
	 */
	@Override
	public final boolean isMutable() {
		return _mutable;
	}

	/**
	 * Adds the given vetting group to this parameter.
	 * 
	 * @param vettingGroup
	 *            the vetting group to add
	 * @return this parameter
	 */
	protected final AbstractXParameter<V> addVettingGroup(final VettingGroup<V> vettingGroup) {
		validateArg("vettingGroup", vettingGroup, isNotNull());
		_vettingGroups.add(vettingGroup);
		_groupsModified = true;
		return this;
	}

	/**
	 * Returns a strategy for a new field.
	 * 
	 * @param additionalGroups
	 *            an additional set of vetting groups specific to this field
	 * @param initialValue
	 *            the initial value of the new field
	 * @return a strategy for a new field
	 */
	protected final VettingStrategy<V> getFieldVettingStrategy(final List<VettingGroup<V>> additionalGroups, final V initialValue) {
		if ((_baseStrategy == null) || (_groupsModified)) {
			_baseStrategy = VettingStrategyImpl.getInstance(_vettingGroups);
		}
		VettingStrategy<V> strategy = _baseStrategy;
		if (additionalGroups != null) {
			strategy = VettingStrategyImpl.getInstance( //
					new ListBuilder<VettingGroup<V>>() //
							.addAll(additionalGroups) //
							.newImmutableInstance(), strategy);
		}
		if (!_mutable) {
			final V vettedValue = _baseStrategy.vetValue(initialValue);
			strategy = VettingStrategyImpl.getInstance( //
					new ListBuilder<VettingGroup<V>>() //
							.add(VettingGroup.getLenientInstance(vettedValue, //
									isEqualTo(vettedValue))) //
							.newImmutableInstance(), strategy);
		}
		return strategy;
	}

	/**
	 * Returns a strategy for a new field.
	 * 
	 * @param initialValue
	 *            the initial value of the new field
	 * @return a strategy for a new field
	 */
	protected final VettingStrategy<V> getFieldVettingStrategy(final V initialValue) {
		return getFieldVettingStrategy(null, initialValue);
	}

	protected static abstract class AbstractXmlFormat<T extends AbstractXParameter<?>> extends XmlFormat<T> {
		protected static final Attribute<String>	ID_ATTRIBUTE			= XmlUnit.newAttribute("id", String.class);
		protected static final Attribute<Boolean>	NULLABLE_ATTRIBUTE		= XmlUnit.newAttribute("nullable", Boolean.class);
		protected static final Attribute<Boolean>	MUTABLE_ATTRIBUTE		= XmlUnit.newAttribute("mutable", Boolean.class);
		protected static final Attribute<String>	DESCRIPTION_ATTRIBUTE	= XmlUnit.newAttribute("description", String.class);

		protected AbstractXmlFormat(Class<T> cls) {
			super(cls);
			addRecognized(ID_ATTRIBUTE);
			addRecognized(NULLABLE_ATTRIBUTE);
			addRecognized(MUTABLE_ATTRIBUTE);
			addRecognized(DESCRIPTION_ATTRIBUTE);
		}

		@Override
		protected void writeAttributes(T obj, OutputElement xml) throws XMLStreamException {
			ID_ATTRIBUTE.write(xml, obj.getId().toString());
			if (obj._description != null && !obj._description.isEmpty()) {
				DESCRIPTION_ATTRIBUTE.write(xml, obj._description);
			}
			NULLABLE_ATTRIBUTE.write(xml, obj.isNullable());
			MUTABLE_ATTRIBUTE.write(xml, obj.isMutable());
		}
	}
}
