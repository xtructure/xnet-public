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
package com.xtructure.xutil.valid;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * Base implementation of a condition that uses a specified value.
 * 
 * @author Luis Guimbarda
 * 
 * @param <T>
 *            type of the value used by this condition
 */
public abstract class AbstractValueCondition<T> implements Condition {
	/** value used by this condition */
	private final T	value;

	/**
	 * Creates a new AbstractValueCondition with the given value.
	 * 
	 * @param value
	 *            the value used by this condition
	 * @param allowNull
	 *            indicates whether to allow the given value to be null
	 * @throws IllegalArgumentException
	 *             if nulls aren't allowed and the given value is null
	 */
	public AbstractValueCondition(T value, boolean allowNull) throws IllegalArgumentException {
		if (!allowNull && value == null) {
			throw new IllegalArgumentException("value : must not be null");
		}
		this.value = value;
	}

	/**
	 * Returns the value used by this condition
	 * 
	 * @return the value used by this condition
	 */
	public T getValue() {
		return value;
	}

	/** base xml format for AbstractValueCondition */
	@SuppressWarnings("rawtypes")
	public static abstract class AbstractXmlFormat<P extends AbstractValueCondition> extends XmlFormat<P> {
		protected static final Element<Object>	VALUE_ELEMENT	= XmlUnit.newElement("value");

		protected AbstractXmlFormat(Class<P> cls) {
			super(cls);
			addRecognized(VALUE_ELEMENT);
		}

		@Override
		protected void writeElements(P object, OutputElement xml) throws XMLStreamException {
			VALUE_ELEMENT.write(xml, object.value);
		}
	}
}
