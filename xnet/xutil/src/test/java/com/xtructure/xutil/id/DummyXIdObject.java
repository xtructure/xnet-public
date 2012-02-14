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
package com.xtructure.xutil.id;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

final class DummyXIdObject extends AbstractXIdObject {
	public DummyXIdObject(XId id) {
		super(id);
	}

	public DummyXIdObject(XId id, XIdObjectManager<DummyXIdObject> manager) {
		super(id, manager);
	}

	public static final XmlFormat<DummyXIdObject>	XML_FORMAT	= new DummyXmlFormat();

	private static final class DummyXmlFormat extends AbstractXmlFormat<DummyXIdObject> {
		protected DummyXmlFormat() {
			super(DummyXIdObject.class);
		}

		@Override
		protected DummyXIdObject newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new DummyXIdObject(readAttributes.getValue(ID_ATTRIBUTE));
		}

		@Override
		protected void writeElements(DummyXIdObject object, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
