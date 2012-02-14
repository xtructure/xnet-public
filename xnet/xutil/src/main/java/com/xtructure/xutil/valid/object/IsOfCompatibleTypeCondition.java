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
 * A condition that checks if an object's type is a subtype of a specified type.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public class IsOfCompatibleTypeCondition extends AbstractValueCondition<Class<?>> {
	/** xml format instance for IsOfCompatibleTypeCondition */
	public static final XmlFormat<IsOfCompatibleTypeCondition>	XML_FORMAT	= new TypeXmlFormat();

	/**
	 * Creates a condition that is satisfied by objects whose class is a subtype
	 * of that given
	 * 
	 * @param type
	 *            the type of which objects must be a subtype to satisfy this
	 *            condition
	 * @throws IllegalArgumentException
	 *             if the given type is <code>null</code>
	 */
	public IsOfCompatibleTypeCondition(Class<?> type) throws IllegalArgumentException {
		super(type, false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object is not null and is an instance of a
	 *         class that is a subtype of this condition's value
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		return (object == null) || getValue().isAssignableFrom(object.getClass());
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("must be compatible with type %s", getValue());
	}

	/** implementation of an xml format for IsOfCompatibleTypeCondition */
	public static final class TypeXmlFormat extends AbstractXmlFormat<IsOfCompatibleTypeCondition> {
		protected TypeXmlFormat() {
			super(IsOfCompatibleTypeCondition.class);
		}

		@Override
		protected IsOfCompatibleTypeCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new IsOfCompatibleTypeCondition((Class<?>) readElements.getValue(VALUE_ELEMENT));
		}

		@Override
		protected void writeAttributes(IsOfCompatibleTypeCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
