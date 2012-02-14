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
package com.xtructure.xutil.valid.comp;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A condition that checks if an object (of type {@link Comparable}) is less
 * than a specified object.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * @param <T>
 *            the type of the value to which object are compared
 */
public class IsLessThanCondition<T extends Comparable<T>> extends AbstractComparisonCondition<T> {
	/**
	 * Creates a new condition that is satisfied by objects that are less than
	 * the given {@link Comparable} value
	 * 
	 * @param value
	 *            the value to which objects must be less than to satisfy this
	 *            condition
	 */
	public IsLessThanCondition(T value) {
		super(value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean makeComparison(T t) {
		return t != null && t.compareTo(getValue()) < 0;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("must be less than to %s", getValue());
	}

	/** xml format instance for IsLessThanCondition */
	@SuppressWarnings("rawtypes")
	public static final XmlFormat<IsLessThanCondition>	XML_FORMAT	= new ConditionXmlFormat();

	/** implementation of an xml format for IsLessThanCondition */
	@SuppressWarnings("rawtypes")
	public static final class ConditionXmlFormat extends AbstractXmlFormat<IsLessThanCondition> {
		protected ConditionXmlFormat() {
			super(IsLessThanCondition.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected IsLessThanCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new IsLessThanCondition((Comparable) readElements.getValue(VALUE_ELEMENT));
		}

		@Override
		protected void writeAttributes(IsLessThanCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
