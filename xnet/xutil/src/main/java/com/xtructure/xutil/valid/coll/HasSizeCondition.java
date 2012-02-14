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

import java.util.Collection;
import java.util.Map;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.AbstractValueCondition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A condition that checks if a given object (of type {@link Collection} or
 * {@link Map}) has a given size.
 * 
 * @author Luis Guimbarda
 * 
 */
public class HasSizeCondition extends AbstractValueCondition<Integer> {
	/**
	 * Creates a new {@link HasSizeCondition} that is satisfied by collections
	 * or maps with the given size.
	 * 
	 * @param size
	 *            the size of collections or maps that satisfy this condition
	 */
	public HasSizeCondition(int size) {
		super(size, false);
		if (size < 0) {
			throw new IllegalArgumentException("size: must be non-negative");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object is a collection or map and has size
	 *         equal to this condition's value, false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		if (object != null) {
			if (object instanceof Collection<?>) {
				return ((Collection<?>) object).size() == getValue();
			}
			if (object instanceof Map<?, ?>) {
				return ((Map<?, ?>) object).size() == getValue();
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format("must have size %d", getValue());
	}

	/** xml format instance for HasSizeCondition */
	public static final XmlFormat<HasSizeCondition>	XML_FORMAT	= new ConditionXmlFormat();

	/** implementation of an xml format for HasSizeCondition */
	public static final class ConditionXmlFormat extends AbstractXmlFormat<HasSizeCondition> {
		protected ConditionXmlFormat() {
			super(HasSizeCondition.class);
		}

		@Override
		protected HasSizeCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new HasSizeCondition((Integer) readElements.getValue(VALUE_ELEMENT));
		}

		@Override
		protected void writeAttributes(HasSizeCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
