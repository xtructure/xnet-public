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

import java.util.Map;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A condition that checks if a {@link Map} contains particular keys.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public class HasKeysCondition extends AbstractQuantityCondition {
	/**
	 * Creates a new condition with the given quantity and condition.
	 * 
	 * This condition is specialized to look at {@link Map} keys
	 * 
	 * @param quantity
	 *            the number of objects in a collection that must satisfy the
	 *            given condition
	 * @param condition
	 *            the condition with which to test a collection's objects
	 * @throws IllegalArgumentException
	 *             if the given condition is null
	 */
	public HasKeysCondition(int quantity, Condition condition) throws IllegalArgumentException {
		super(quantity, condition);
	}

	/** {@inheritDoc} */
	@Override
	protected Iterable<?> getIterable(Object object) {
		if (object != null && object instanceof Map<?, ?>) {
			return ((Map<?, ?>) object).keySet();
		}
		return null;
	}

	/** xml format instance for HasKeysCondition */
	public static final XmlFormat<HasKeysCondition>	XML_FORMAT	= new ConditionXmlFormat();

	/** implementation of an xml format for HasKeysCondition */
	private static final class ConditionXmlFormat extends AbstractXmlFormat<HasKeysCondition> {
		protected ConditionXmlFormat() {
			super(HasKeysCondition.class);
		}

		@Override
		protected HasKeysCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			int q = readAttributes.getValue(QUANTITY_ATTRIBUTE);
			Condition p = readElements.getValue(PREDICATE_ELEMENT);
			return new HasKeysCondition(q, p);
		}
	}
}
