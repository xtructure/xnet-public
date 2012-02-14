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

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * A parameter with a {@link Range} of possible values.
 * 
 * @param <V>
 *            the type of value of this parameter
 * @author Peter N&uuml;rnberg
 * @version 0.9.6
 */
public abstract class AbstractRangeXParameter<V extends Comparable<V>> extends AbstractXParameter<V> implements RangeXParameter<V> {
	/** The lifetime range of this parameter. */
	private final Range<V>	_lifetimeRange;
	/** The initial range of this parameter. */
	private final Range<V>	_initialRange;
	/** The type of this parameter. */
	private final Class<V>	_type;

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
	 * @param lifetimeRange
	 *            the lifetime range of this parameter
	 * @param initialRange
	 *            the initial range of this parameter
	 */
	protected AbstractRangeXParameter(final XId id, final String description, final Boolean nullable, final Boolean mutable, final Range<V> lifetimeRange, final Range<V> initialRange, final Class<V> type) {
		super(id, description, nullable, mutable);
		_lifetimeRange = (lifetimeRange == null ? Range.<V> openRange() : lifetimeRange);
		_initialRange = ((initialRange != null) ? initialRange : _lifetimeRange);
		_type = type;
		if (lifetimeRange != null) {
			if (_lifetimeRange.getMinimum() != null) {
				addVettingGroup(VettingGroup.getLenientInstance(//
						_lifetimeRange.getMinimum(),//
						isGreaterThanOrEqualTo(_lifetimeRange.getMinimum())));
			}
			if (_lifetimeRange.getMaximum() != null) {
				addVettingGroup(VettingGroup.getLenientInstance(//
						_lifetimeRange.getMaximum(),//
						isLessThanOrEqualTo(_lifetimeRange.getMaximum())));
			}
		}
	}

	protected AbstractRangeXParameter(AbstractRangeXParameter<V> parameter) {
		this(//
				parameter.getId(),//
				parameter.getDescription(),//
				parameter.isNullable(),//
				parameter.isMutable(),//
				parameter._lifetimeRange,//
				parameter._initialRange,//
				parameter._type);
	}

	/** {@inheritDoc} */
	@Override
	public final Range<V> getLifetimeRange() {
		return _lifetimeRange;
	}

	/** {@inheritDoc} */
	@Override
	public final Range<V> getInitialRange() {
		return _initialRange;
	}

	/** {@inheritDoc} */
	@Override
	public final V newUniformRandomValue() {
		return RandomUtil.next(_initialRange, _type);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format("%s range=%s; initial=%s", getId(), getLifetimeRange(), getInitialRange());
	}

	/**
	 * Information about the suggested XML format of a ranged parameter.
	 * 
	 * @param <R>
	 *            the type of range of the parameter described by this format
	 * @param <T>
	 *            the type of object described by this format
	 */
	protected static abstract class AbstractXmlFormat<R extends Comparable<R>, T extends AbstractRangeXParameter<R>> extends AbstractXParameter.AbstractXmlFormat<T> {
		protected static final Attribute<String>	LIFETIME_RANGE_ATTRIBUTE	= XmlUnit.newAttribute("range", String.class);
		protected static final Attribute<String>	INITIAL_RANGE_ATTRIBUTE		= XmlUnit.newAttribute("initial", String.class);

		protected AbstractXmlFormat(Class<T> cls) {
			super(cls);
			addRecognized(LIFETIME_RANGE_ATTRIBUTE);
			addRecognized(INITIAL_RANGE_ATTRIBUTE);
		}

		@Override
		protected void writeAttributes(T obj, OutputElement xml) throws XMLStreamException {
			if (obj._lifetimeRange != null) {
				LIFETIME_RANGE_ATTRIBUTE.write(xml, obj._lifetimeRange.toString());
			}
			if ((obj._initialRange != null) && (!obj._initialRange.equals(obj._lifetimeRange))) {
				INITIAL_RANGE_ATTRIBUTE.write(xml, obj._initialRange.toString());
			}
			super.writeAttributes(obj, xml);
		}
	}
}
