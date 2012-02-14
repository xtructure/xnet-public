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
package com.xtructure.xutil.valid.meta;

import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.valid.meta.AbstractMetaCondition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

final class DummyMetaCondition extends AbstractMetaCondition {
	public DummyMetaCondition(Condition... predicates) {
		super(predicates);
	}

	public DummyMetaCondition(List<Condition> predicates) {
		super(predicates);
	}

	@Override
	public boolean isSatisfiedBy(Object object) {
		return false;
	}

	public static final XmlFormat<DummyMetaCondition>	XML_FORMAT	= new DummyXmlFormat();

	private static final class DummyXmlFormat extends AbstractXmlFormat<DummyMetaCondition> {
		protected DummyXmlFormat() {
			super(DummyMetaCondition.class);
		}

		@Override
		protected DummyMetaCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new DummyMetaCondition(readElements.getValues(PREDICATE_ELEMENT));
		}

		@Override
		protected void writeAttributes(DummyMetaCondition object, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
