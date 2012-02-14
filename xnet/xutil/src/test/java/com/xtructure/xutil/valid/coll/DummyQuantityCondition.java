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

import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.valid.coll.AbstractQuantityCondition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

final class DummyQuantityCondition extends AbstractQuantityCondition {
	public DummyQuantityCondition(int quantity, Condition predicate) {
		super(quantity, predicate);
	}

	@Override
	protected Iterable<?> getIterable(Object object) {
		if (object instanceof List) {
			return (List<?>) object;
		}
		return null;
	}

	public static final XmlFormat<DummyQuantityCondition>	XML_FORMAT	= new DummyXmlFormat();

	private static final class DummyXmlFormat extends AbstractXmlFormat<DummyQuantityCondition> {
		protected DummyXmlFormat() {
			super(DummyQuantityCondition.class);
		}

		@Override
		protected DummyQuantityCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			Integer quantity = readAttributes.getValue(QUANTITY_ATTRIBUTE);
			Condition predicate = readElements.getValue(PREDICATE_ELEMENT);
			if (quantity == null || predicate == null) {
				throw new XMLStreamException();
			}
			return new DummyQuantityCondition(quantity, predicate);
		}
	}
}
