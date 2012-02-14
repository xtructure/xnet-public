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
package com.xtructure.xutil.xml;

import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

final class Dummy {
	public static final String			XML;
	static {
		XML = "<?xml version=\"1.0\" ?>\n" + //
				String.format("<%s dummyAttribute1=\"attr1\" dummyAttribute2=\"attr2\" dummyAttribute3=\"3\">\n", Dummy.class.getName()) + //
				"	<dummyElement1 class=\"java.lang.String\" value=\"elem1\"/>\n" + //
				"	<dummyElement2 value=\"elem2\"/>\n" + //
				"	<dummyElement3 value=\"3\"/>\n" + //
				String.format("</%s>", Dummy.class.getName());
	}
	public static final DummyXmlFormat	XML_FORMAT	= new DummyXmlFormat();
	
	private final boolean writable;
	public Dummy(){
		this(true);
	}
	public Dummy(boolean writable){
		this.writable = writable;
	}

	static final class DummyXmlFormat extends XmlFormat<Dummy> {
		static final Attribute<Object>	DUMMY_ATTRIBUTE1		= XmlUnit.newAttribute("dummyAttribute1");
		static final Element<Object>	DUMMY_ELEMENT1			= XmlUnit.newElement("dummyElement1");
		static final Attribute<String>	DUMMY_ATTRIBUTE2		= XmlUnit.newAttribute("dummyAttribute2", String.class);
		static final Element<String>	DUMMY_ELEMENT2			= XmlUnit.newElement("dummyElement2", String.class);
		static final Attribute<Integer>	DUMMY_ATTRIBUTE3		= XmlUnit.newAttribute("dummyAttribute3", Integer.class);
		static final Element<Integer>	DUMMY_ELEMENT3			= XmlUnit.newElement("dummyElement3", Integer.class);
		int								newInstanceCount		= 0;
		int								writeAttributesCount	= 0;
		int								writeElementsCount		= 0;
		ReadAttributes					lastReadAttributes		= null;
		ReadElements					lastReadElements		= null;

		public DummyXmlFormat() {
			super(Dummy.class);
			addRecognized(DUMMY_ATTRIBUTE1);
			addRecognized(DUMMY_ATTRIBUTE2);
			addRecognized(DUMMY_ATTRIBUTE3);
			addRecognized(DUMMY_ELEMENT1);
			addRecognized(DUMMY_ELEMENT2);
			addRecognized(DUMMY_ELEMENT3);
		}

		@Override
		protected Dummy newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			lastReadAttributes = readAttributes;
			lastReadElements = readElements;
			try {
				validateState("attr1", readAttributes.getValue(DUMMY_ATTRIBUTE1), isEqualTo("attr1"));
				validateState("attr2", readAttributes.getValue(DUMMY_ATTRIBUTE2), isEqualTo("attr2"));
				validateState("attr3", readAttributes.getValue(DUMMY_ATTRIBUTE3), isEqualTo(3));
				validateState("elem1", readElements.getValue(DUMMY_ELEMENT1), isEqualTo("elem1"));
				validateState("elem2", readElements.getValue(DUMMY_ELEMENT2), isEqualTo("elem2"));
				validateState("elem3", readElements.getValue(DUMMY_ELEMENT3), isEqualTo(3));
			} catch (IllegalStateException e) {
				throw new XMLStreamException(e.getMessage());
			}
			newInstanceCount++;
			return new Dummy();
		}

		@Override
		protected void writeAttributes(Dummy object, OutputElement xml) throws XMLStreamException {
			if(!object.writable){
				throw new XMLStreamException();
			}
			writeAttributesCount++;
			DUMMY_ATTRIBUTE1.write(xml, "attr1");
			DUMMY_ATTRIBUTE2.write(xml, "attr2");
			DUMMY_ATTRIBUTE3.write(xml, 3);
		}

		@Override
		protected void writeElements(Dummy object, OutputElement xml) throws XMLStreamException {
			if(!object.writable){
				throw new XMLStreamException();
			}
			writeElementsCount++;
			DUMMY_ELEMENT1.write(xml, "elem1");
			DUMMY_ELEMENT2.write(xml, "elem2");
			DUMMY_ELEMENT3.write(xml, 3);
		}
	}
}
