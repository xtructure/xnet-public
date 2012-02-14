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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * A field derived from a {@link XParameter}.
 * 
 * @param <V>
 *            the type of this field
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * @version 0.9.6
 */
public abstract class AbstractXField<V> implements XField<V> {
	/** The parameter from which this value is derived. */
	private final XParameter<V>			_param;
	/** The vetting strategy used by this field. */
	private final VettingStrategy<V>	_vettingStrategy;
	/** The underlying value. */
	private V							_value;

	/**
	 * Creates a new value.
	 * 
	 * @param param
	 *            the parameter to which this value is bound
	 * @param vettingStrategy
	 *            the vetting strategy used by this field
	 * @param initialValue
	 *            the initial underlying value
	 * @throws IllegalArgumentException
	 *             if the given parameter is <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the given vetting strategy is <code>null</code>
	 */
	protected AbstractXField(final XParameter<V> param, final VettingStrategy<V> vettingStrategy, final V initialValue) {
		super();
		validateArg("param", param, isNotNull());
		validateArg("vettingStrategy", vettingStrategy, isNotNull());
		_param = param;
		_vettingStrategy = vettingStrategy;
		setValue(initialValue);
	}

	/**
	 * @param field
	 */
	protected AbstractXField(final AbstractXField<V> field) {
		_param = field._param;
		_vettingStrategy = field._vettingStrategy;
		setValue(field._value);
	}

	/** {@inheritDoc} */
	@Override
	public XParameter<V> getParameter() {
		return _param;
	}

	/** {@inheritDoc} */
	@Override
	public final VettingStrategy<V> getVettingStrategy() {
		return _vettingStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public final V getValue() {
		return _value;
	}

	/** {@inheritDoc} */
	@Override
	public final V setValue(final V value) {
		return (_value = _vettingStrategy.vetValue(value));
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format(String.format("%s=%s", getParameter().getId(), getValue()));
	}

	protected static abstract class AbstractXmlFormat<V, F extends AbstractXField<V>, P extends AbstractXParameter<V>> extends XmlFormat<F> {
		protected AbstractXmlFormat(Class<F> fieldType) {
			super(fieldType);
		}

		public abstract Attribute<V> getValueAttribute();

		public abstract Element<P> getParameterElement();

		@Override
		protected void writeAttributes(F obj, OutputElement xml) throws XMLStreamException {
			getValueAttribute().write(xml, obj.getValue());
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void writeElements(F obj, OutputElement xml) throws XMLStreamException {
			getParameterElement().write(xml, (P) obj.getParameter());
		}
	}
}
