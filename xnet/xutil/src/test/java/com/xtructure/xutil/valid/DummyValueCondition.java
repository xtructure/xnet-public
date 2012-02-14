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
package com.xtructure.xutil.valid;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.AbstractValueCondition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

final class DummyValueCondition extends AbstractValueCondition<Object> {
	public DummyValueCondition(Object value, boolean allowNull) {
		super(value, allowNull);
	}

	@Override
	public boolean isSatisfiedBy(Object object) {
		return false;
	}

	@Override
	public String toString() {
		if (getValue() == null) {
			return "null";
		}
		return getValue().toString();
	}

	public static final XmlFormat<DummyValueCondition>	XML_FORMAT	= new DummyXmlFormat();

	private static final class DummyXmlFormat extends AbstractXmlFormat<DummyValueCondition> {
		protected DummyXmlFormat() {
			super(DummyValueCondition.class);
		}

		@Override
		protected DummyValueCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new DummyValueCondition(readElements.getValue(VALUE_ELEMENT), true);
		}

		@Override
		protected void writeAttributes(DummyValueCondition object, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
