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
public final class UTestReadAttributes {
	public void constructorSucceeds() throws XMLStreamException {
		Dummy.XML_FORMAT.lastReadAttributes = null;
		XmlReader.read(new StringReader(Dummy.XML));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes, isNotNull());
	}

	@Test(expectedExceptions = { XMLStreamException.class })
	public void constructorOnXmlWithUnrecognizedAttributeThrowsException() throws XMLStreamException {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s unrecognizedAttribute=\"attr1\"/>", Dummy.class.getName());
		XmlReader.read(new StringReader(xml));
	}

	public void getValueReturnsExpectedObject() throws XMLStreamException {
		XmlReader.read(new StringReader(Dummy.XML));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes.getValue(Dummy.DummyXmlFormat.DUMMY_ATTRIBUTE1),//
				isEqualTo("attr1"));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes.getValue(Dummy.DummyXmlFormat.DUMMY_ATTRIBUTE2),//
				isEqualTo("attr2"));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes.getValue(Dummy.DummyXmlFormat.DUMMY_ATTRIBUTE3),//
				isEqualTo(3));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes.getValue(XmlUnit.newAttribute("unrecognizedAttribute")),//
				isNull());
	}

	public void getValuesReturnsExpectedList() throws XMLStreamException {
		XmlReader.read(new StringReader(Dummy.XML));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes.getValues(Dummy.DummyXmlFormat.DUMMY_ATTRIBUTE1),//
				isEqualTo(Collections.singletonList("attr1")));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes.getValues(Dummy.DummyXmlFormat.DUMMY_ATTRIBUTE2),//
				isEqualTo(Collections.singletonList("attr2")));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes.getValues(Dummy.DummyXmlFormat.DUMMY_ATTRIBUTE3),//
				isEqualTo(Collections.singletonList(3)));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes.getValues(XmlUnit.newAttribute("unrecognizedAttribute")),//
				isNull());
	}
}
