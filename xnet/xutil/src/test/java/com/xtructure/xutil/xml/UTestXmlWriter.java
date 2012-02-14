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
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

import javolution.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

import com.xtructure.xutil.MiscUtils;

@Test(groups = { "unit:xutil" })
public final class UTestXmlWriter {
	public void writeObjectSucceeds() throws XMLStreamException, IOException {
		String expected = Dummy.XML;
		String xml = XmlWriter.write(new Dummy());
		assertThat("",//
				xml, isEqualTo(expected));
		StringWriter out = new StringWriter();
		XmlWriter.write(out, new Dummy());
		assertThat("",//
				out.toString(), isEqualTo(expected));
		File file = new File(UUID.randomUUID().toString());
		try {
			XmlWriter.write(file, new Dummy());
			xml = MiscUtils.read(new FileReader(file));
			assertThat("",//
					xml, isEqualTo(expected));
		} finally {
			file.delete();
		}
	}

	public void writeObjectWithIndentationSucceeds() throws XMLStreamException, IOException {
		String oldIndent = XmlWriter.DEFAULT_INDENTATION;
		String newIndent = "  ";
		String expected = Dummy.XML.replaceAll(oldIndent, newIndent);
		String xml = XmlWriter.write(new Dummy(), newIndent);
		assertThat("",//
				xml, isEqualTo(expected));
		StringWriter out = new StringWriter();
		XmlWriter.write(out, new Dummy(), newIndent);
		assertThat("",//
				out.toString(), isEqualTo(expected));
		File file = new File(UUID.randomUUID().toString());
		try {
			XmlWriter.write(file, new Dummy(), newIndent);
			xml = MiscUtils.read(new FileReader(file));
			assertThat("",//
					xml, isEqualTo(expected));
		} finally {
			file.delete();
		}
	}

	public void writeObjectWithBindingSucceeds() throws XMLStreamException, IOException {
		XmlBinding binding = new XmlBinding(Dummy.class);
		String expected = Dummy.XML.replaceAll(Dummy.class.getName(), Dummy.class.getSimpleName());
		String xml = XmlWriter.write(new Dummy(), binding);
		assertThat("",//
				xml, isEqualTo(expected));
		StringWriter out = new StringWriter();
		XmlWriter.write(out, new Dummy(), binding);
		assertThat("",//
				out.toString(), isEqualTo(expected));
		File file = new File(UUID.randomUUID().toString());
		try {
			XmlWriter.write(file, new Dummy(), binding);
			xml = MiscUtils.read(new FileReader(file));
			assertThat("",//
					xml, isEqualTo(expected));
		} finally {
			file.delete();
		}
	}

	public void writeObjectWithIndentationAndBindingsSucceeds() throws XMLStreamException, IOException {
		String oldIndent = XmlWriter.DEFAULT_INDENTATION;
		String newIndent = "  ";
		XmlBinding binding = new XmlBinding(Dummy.class);
		String expected = Dummy.XML.replaceAll(oldIndent, newIndent).replaceAll(Dummy.class.getName(), Dummy.class.getSimpleName());
		String xml = XmlWriter.write(new Dummy(), binding, newIndent);
		assertThat("",//
				xml, isEqualTo(expected));
		StringWriter out = new StringWriter();
		XmlWriter.write(out, new Dummy(), binding, newIndent);
		assertThat("",//
				out.toString(), isEqualTo(expected));
		File file = new File(UUID.randomUUID().toString());
		try {
			XmlWriter.write(file, new Dummy(), binding, newIndent);
			xml = MiscUtils.read(new FileReader(file));
			assertThat("",//
					xml, isEqualTo(expected));
		} finally {
			file.delete();
		}
	}

	public void writeStringWithBadObjectReturnsNull() {
		assertThat("",//
				XmlWriter.write(new Dummy(false)), isNull());
	}

	@Test(expectedExceptions = {FileNotFoundException.class})
	public void writeWithBadFileThrowsException() throws XMLStreamException, IOException {
		File file = new File(UUID.randomUUID().toString());
		try {
			file.createNewFile();
			file.setWritable(false);
			XmlWriter.write(file, new Dummy(false));
		} finally {
			file.delete();
		}
	}
}
