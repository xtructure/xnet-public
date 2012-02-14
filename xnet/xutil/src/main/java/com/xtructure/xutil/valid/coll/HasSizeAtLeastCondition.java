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
 * A condition that checks if a given object (of type collection or map) has at
 * least given size.
 * 
 * @author Luis Guimbarda
 * 
 */
public class HasSizeAtLeastCondition extends AbstractValueCondition<Integer> {
	/**
	 * Creates a new {@link HasSizeAtLeastCondition} that is satisfied by
	 * collections or maps with at least the given size
	 * 
	 * @param size
	 *            the minimum size of collections or maps that satisfy this
	 *            condition
	 */
	public HasSizeAtLeastCondition(int size) {
		super(size, false);
		if (size < 0) {
			throw new IllegalArgumentException("size: must be non-negative");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object (of type collection or map) has size at
	 *         least this condition's value , false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		if (object != null) {
			if (object instanceof Collection<?>) {
				return ((Collection<?>) object).size() >= getValue();
			}
			if (object instanceof Map<?, ?>) {
				return ((Map<?, ?>) object).size() >= getValue();
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return String.format("must have size at least %d", getValue());
	}

	/** xml format instance for HasSizeAtLeastCondition */
	public static final XmlFormat<HasSizeAtLeastCondition>	XML_FORMAT	= new ConditionXmlFormat();

	/** implementation of an xml format for HasSizeAtLeastCondition */
	public static final class ConditionXmlFormat extends AbstractXmlFormat<HasSizeAtLeastCondition> {
		protected ConditionXmlFormat() {
			super(HasSizeAtLeastCondition.class);
		}

		@Override
		protected HasSizeAtLeastCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new HasSizeAtLeastCondition((Integer) readElements.getValue(VALUE_ELEMENT));
		}

		@Override
		protected void writeAttributes(HasSizeAtLeastCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
