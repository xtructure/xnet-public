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
package com.xtructure.xutil.valid.coll;

import java.util.Arrays;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A condition that checks if an object (of type array or Collections) has
 * specified elements.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public class HasElementsCondition extends AbstractQuantityCondition {
	/**
	 * Creates a new condition with the given quantity and condition.
	 * 
	 * This condition is specialized to look at array or collection elements
	 * 
	 * @param quantity
	 *            the number of objects in an array or a collection that must
	 *            satisfy the given condition
	 * @param condition
	 *            the condition with which to test an array or a collection's
	 *            objects
	 * @throws IllegalArgumentException
	 *             if the given condition is null
	 */
	public HasElementsCondition(int quantity, Condition condition) {
		super(quantity, condition);
	}

	/** {@inheritDoc} */
	@Override
	protected Iterable<?> getIterable(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Iterable) {
			return (Iterable<?>) object;
		}
		if (object instanceof Object[]) {
			return Arrays.asList((Object[]) object);
		}
		if (object instanceof char[]) {
			return Arrays.asList((char[]) object);
		}
		if (object instanceof byte[]) {
			return Arrays.asList((byte[]) object);
		}
		if (object instanceof short[]) {
			return Arrays.asList((short[]) object);
		}
		if (object instanceof int[]) {
			return Arrays.asList((int[]) object);
		}
		if (object instanceof long[]) {
			return Arrays.asList((long[]) object);
		}
		if (object instanceof boolean[]) {
			return Arrays.asList((boolean[]) object);
		}
		if (object instanceof float[]) {
			return Arrays.asList((float[]) object);
		}
		if (object instanceof double[]) {
			return Arrays.asList((double[]) object);
		}
		return null;
	}

	/** xml format instance for HasElementsCondition */
	public static final XmlFormat<HasElementsCondition>	XML_FORMAT	= new ConditionXmlFormat();

	/** implementation of an xml format for HasElementsCondition */
	private static final class ConditionXmlFormat extends AbstractXmlFormat<HasElementsCondition> {
		protected ConditionXmlFormat() {
			super(HasElementsCondition.class);
		}

		@Override
		protected HasElementsCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			int q = readAttributes.getValue(QUANTITY_ATTRIBUTE);
			Condition p = readElements.getValue(PREDICATE_ELEMENT);
			return new HasElementsCondition(q, p);
		}
	}
}
