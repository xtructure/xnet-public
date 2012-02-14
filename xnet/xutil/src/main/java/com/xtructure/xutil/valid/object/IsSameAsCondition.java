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
package com.xtructure.xutil.valid.object;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.AbstractValueCondition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A condition that checks if an object is the same as another specified object.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public class IsSameAsCondition extends AbstractValueCondition<Object> {
	/** xml format instance for IsSameAsCondition */
	public static final XmlFormat<IsSameAsCondition>	XML_FORMAT	= new ConditionXmlFormat();

	/**
	 * Creates a new condition that's satisfied by objects that are the same as
	 * the given value
	 * 
	 * @param value
	 *            the value with which objects must be the same to satisfy this
	 *            condition
	 */
	public IsSameAsCondition(Object value) {
		super(value, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object is the same as this condition's value,
	 *         false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		return getValue() == object;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("must be same as %s", getValue());
	}

	/** implementation of an xml format for IsSameAsCondition */
	public static final class ConditionXmlFormat extends AbstractXmlFormat<IsSameAsCondition> {
		protected ConditionXmlFormat() {
			super(IsSameAsCondition.class);
		}

		@Override
		protected IsSameAsCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new IsSameAsCondition(readElements.getValue(VALUE_ELEMENT));
		}

		@Override
		protected void writeAttributes(IsSameAsCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
