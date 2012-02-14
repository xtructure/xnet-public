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

import javolution.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

import com.xtructure.xutil.xml.XmlReader;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestXmlFormat {
	public void constructorSucceeds() {
		assertThat("",//
				Dummy.XML_FORMAT, isNotNull());
	}

	public void newInstanceAndReadBehavesAsExpected() throws XMLStreamException {
		int newInstanceCount = Dummy.XML_FORMAT.newInstanceCount;
		int writeAttributesCount = Dummy.XML_FORMAT.writeAttributesCount;
		int writeElementsCount = Dummy.XML_FORMAT.writeElementsCount;
		Dummy.XML_FORMAT.lastReadAttributes = null;
		Dummy.XML_FORMAT.lastReadElements = null;
		XmlReader.read(new StringReader(Dummy.XML));
		assertThat("",//
				Dummy.XML_FORMAT.newInstanceCount, isEqualTo(newInstanceCount + 1));
		assertThat("",//
				Dummy.XML_FORMAT.writeAttributesCount, isEqualTo(writeAttributesCount));
		assertThat("",//
				Dummy.XML_FORMAT.writeElementsCount, isEqualTo(writeElementsCount));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes, isNotNull());
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements, isNotNull());
	}

	public void writeBehavesAsExpected() {
		int newInstanceCount = Dummy.XML_FORMAT.newInstanceCount;
		int writeAttributesCount = Dummy.XML_FORMAT.writeAttributesCount;
		int writeElementsCount = Dummy.XML_FORMAT.writeElementsCount;
		Dummy.XML_FORMAT.lastReadAttributes = null;
		Dummy.XML_FORMAT.lastReadElements = null;
		XmlWriter.write(new Dummy());
		assertThat("",//
				Dummy.XML_FORMAT.newInstanceCount, isEqualTo(newInstanceCount));
		assertThat("",//
				Dummy.XML_FORMAT.writeAttributesCount, isEqualTo(writeAttributesCount + 1));
		assertThat("",//
				Dummy.XML_FORMAT.writeElementsCount, isEqualTo(writeElementsCount + 1));
		assertThat("",//
				Dummy.XML_FORMAT.lastReadAttributes, isNull());
		assertThat("",//
				Dummy.XML_FORMAT.lastReadElements, isNull());
	}
}
