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
 * A condition that checks if an object's type is equal to a specified type.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public class IsOfExactTypeCondition extends AbstractValueCondition<Class<?>> {
	/** xml format instance for IsOfExactTypeCondition */
	public static final XmlFormat<IsOfExactTypeCondition>	XML_FORMAT	= new TypeXmlFormat();

	/**
	 * Creates a condition that is satisfied by objects whose class is equal to
	 * that given
	 * 
	 * @param type
	 *            the type of which objects must be to satisfy this condition
	 * @throws IllegalArgumentException
	 *             if the given type is <code>null</code>
	 */
	public IsOfExactTypeCondition(Class<?> type) throws IllegalArgumentException {
		super(type, false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object is not null and is an instance of a
	 *         class equal to this condition's value
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		return (object == null) || getValue().equals(object.getClass());
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("must be of type %s", getValue());
	}

	/** implementation of an xml format for IsOfExactTypeCondition */
	public static final class TypeXmlFormat extends AbstractXmlFormat<IsOfExactTypeCondition> {
		protected TypeXmlFormat() {
			super(IsOfExactTypeCondition.class);
		}

		@Override
		protected IsOfExactTypeCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new IsOfExactTypeCondition((Class<?>) readElements.getValue(VALUE_ELEMENT));
		}

		@Override
		protected void writeAttributes(IsOfExactTypeCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
