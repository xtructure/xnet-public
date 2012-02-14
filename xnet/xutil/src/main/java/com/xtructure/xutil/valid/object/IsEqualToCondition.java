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

import org.apache.commons.lang.ObjectUtils;

import com.xtructure.xutil.valid.AbstractValueCondition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A condition that checks if an object is equal to another specified object.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public class IsEqualToCondition extends AbstractValueCondition<Object> {
	/** xml format instance for IsEqualToCondition */
	public static final XmlFormat<IsEqualToCondition>	XML_FORMAT	= new ConditionXmlFormat();

	/**
	 * Creates a new condition that's satisfied by objects that are equal to the
	 * given value
	 * 
	 * @param value
	 *            the value to which objects must be equal to satisfy this
	 *            condition
	 */
	public IsEqualToCondition(Object value) {
		super(value, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object is equal to this condition's value,
	 *         false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		return ObjectUtils.equals(getValue(), object);
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("must be equal to %s", getValue());
	}

	/** implementation of an xml format for IsEqualToCondition */
	public static final class ConditionXmlFormat extends AbstractXmlFormat<IsEqualToCondition> {
		protected ConditionXmlFormat() {
			super(IsEqualToCondition.class);
		}

		@Override
		protected IsEqualToCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new IsEqualToCondition(readElements.getValue(VALUE_ELEMENT));
		}

		@Override
		protected void writeAttributes(IsEqualToCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
