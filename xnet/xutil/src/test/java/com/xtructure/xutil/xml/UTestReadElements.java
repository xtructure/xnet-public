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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import java.io.StringReader;
import java.util.Collections;

import javolution.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestReadElements {
	public void constructorSucceeds() throws XMLStreamException {
		Dummy.XML_FORMAT.lastReadAttributes = null;
		XmlReader.read(new StringReader(Dummy.XML));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes, isNotNull());
	}

	@Test(expectedExceptions = { XMLStreamException.class })
	public void constructorOnXmlWithUnrecognizedElementThrowsException() throws XMLStreamException {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		XmlReader.read(new StringReader(xml));
	}

	public void getValueReturnsExpectedObject() throws XMLStreamException {
		XmlReader.read(new StringReader(Dummy.XML));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements.getValue(Dummy.DummyXmlFormat.DUMMY_ELEMENT1),//
				isEqualTo("elem1"));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements.getValue(Dummy.DummyXmlFormat.DUMMY_ELEMENT2),//
				isEqualTo("elem2"));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements.getValue(Dummy.DummyXmlFormat.DUMMY_ELEMENT3),//
				isEqualTo(3));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements.getValue(XmlUnit.newElement("unrecognizedElement")),//
				isNull());
	}

	public void getValuesReturnsExpectedList() throws XMLStreamException {
		XmlReader.read(new StringReader(Dummy.XML));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements.getValues(Dummy.DummyXmlFormat.DUMMY_ELEMENT1),//
				isEqualTo(Collections.singletonList("elem1")));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements.getValues(Dummy.DummyXmlFormat.DUMMY_ELEMENT2),//
				isEqualTo(Collections.singletonList("elem2")));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements.getValues(Dummy.DummyXmlFormat.DUMMY_ELEMENT3),//
				isEqualTo(Collections.singletonList(3)));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements.getValues(XmlUnit.newElement("unrecognizedElement")),//
				isNull());
	}
}
