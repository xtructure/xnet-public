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

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * A base class for conditions based on the contents of collections.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public abstract class AbstractQuantityCondition implements Condition {
	/**
	 * value that indicates that an entire collection must satisfy the given
	 * condition
	 */
	public static final Integer	ALL	= -1;
	/**
	 * the number of objects in a collection that must satisfy the given
	 * condition
	 */
	protected final int			quantity;
	/** the condition with which to test a collection's objects */
	protected final Condition	condition;

	/**
	 * Creates a new condition with the given quantity and condition.
	 * 
	 * @param quantity
	 *            the number of objects in a collection that must satisfy the
	 *            given condition
	 * @param condition
	 *            the condition with which to test a collection's objects
	 * @throws IllegalArgumentException
	 *             if the given condition is null
	 */
	public AbstractQuantityCondition(int quantity, Condition condition) throws IllegalArgumentException {
		if (!(quantity == ALL || quantity >= 1)) {
			throw new IllegalArgumentException("quantity: must be -1 or positive");
		}
		if (condition == null) {
			throw new IllegalArgumentException("condition: must not be null");
		}
		this.quantity = quantity;
		this.condition = condition;
	}

	/**
	 * Returns the number of objects in a collection that must satisfy this
	 * condition's sub-condition
	 * 
	 * @return the number of objects in a collection that must satisfy this
	 *         condition's sub-condition
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Returns the condition with which to test a collection's objects
	 * 
	 * @return the condition with which to test a collection's objects
	 */
	public Condition getCondition() {
		return condition;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isSatisfiedBy(Object object) {
		int counter = 0, sat = 0;
		Iterable<?> iterable = getIterable(object);
		if (iterable == null) {
			return false;
		}
		for (Object o : iterable) {
			if (condition.isSatisfiedBy(o)) {
				sat++;
			}
			counter++;
		}
		return quantity == ALL ? sat == counter : sat >= quantity;
	}

	/**
	 * Returns an iterable associated with the given object.
	 * 
	 * @param object
	 *            the object from which the iterable should be derived
	 * 
	 * @return an iterable associated with the given object, or
	 *         <code>null</code> if the given object has no associated iterable
	 */
	protected abstract Iterable<?> getIterable(Object object);

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format(//
				"%s members must satisfy the following conditions: %s",//
				(quantity == ALL) ? "all" : String.format("at least %d", quantity),//
				condition);
	}

	/** base implemention of an xml format for AbstractQuantityCondition */
	protected static abstract class AbstractXmlFormat<T extends AbstractQuantityCondition> extends XmlFormat<T> {
		protected static final Attribute<Integer>	QUANTITY_ATTRIBUTE	= XmlUnit.newAttribute("quantity", Integer.class);
		protected static final Element<Condition>	PREDICATE_ELEMENT	= XmlUnit.newElement("condition");

		protected AbstractXmlFormat(Class<T> cls) {
			super(cls);
			addRecognized(QUANTITY_ATTRIBUTE);
			addRecognized(PREDICATE_ELEMENT);
		}

		@Override
		protected void writeAttributes(T object, OutputElement xml) throws XMLStreamException {
			QUANTITY_ATTRIBUTE.write(xml, object.quantity);
		}

		@Override
		protected void writeElements(T object, OutputElement xml) throws XMLStreamException {
			PREDICATE_ELEMENT.write(xml, object.condition);
		}
	}
}
