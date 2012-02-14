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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import javolution.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

@Test(groups = { "unit:xutil" })
public final class UTestXmlReader {
	public void readWithXMLSourceSucceeds() throws XMLStreamException, IOException {
		String xml = Dummy.XML;
		assertThat("",//
				XmlReader.read(xml), isNotNull());
		StringReader in = new StringReader(xml);
		assertThat("",//
				XmlReader.read(in), isNotNull());
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.write(xml);
			out.close();
			assertThat("",//
					XmlReader.read(file), isNotNull());
			assertThat("",//
					XmlReader.read(new FileInputStream(file)), isNotNull());
		} finally {
			file.delete();
		}
	}

	public void readWithXMLSourceAndDefaultSucceeds() throws IOException {
		String xml = Dummy.XML;
		Dummy defaultValue = null;
		assertThat("",//
				XmlReader.read(xml, defaultValue), isNotNull());
		StringReader in = new StringReader(xml);
		assertThat("",//
				XmlReader.read(in, defaultValue), isNotNull());
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.write(xml);
			out.close();
			assertThat("",//
					XmlReader.read(file, defaultValue), isNotNull());
			assertThat("",//
					XmlReader.read(new FileInputStream(file), defaultValue), isNotNull());
		} finally {
			file.delete();
		}
	}

	public void readWithXMLSourceAndBindingSucceeds() throws IOException, XMLStreamException {
		String xml = Dummy.XML.replaceAll(Dummy.class.getName(), Dummy.class.getSimpleName());
		XmlBinding binding = new XmlBinding(Dummy.class);
		assertThat("",//
				XmlReader.read(xml, binding), isNotNull());
		StringReader in = new StringReader(xml);
		assertThat("",//
				XmlReader.read(in, binding), isNotNull());
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.write(xml);
			out.close();
			assertThat("",//
					XmlReader.read(file, binding), isNotNull());
			assertThat("",//
					XmlReader.read(new FileInputStream(file), binding), isNotNull());
		} finally {
			file.delete();
		}
	}

	public void readWithXMLSourceAndBindingAndDefaultSucceeds() throws IOException {
		String xml = Dummy.XML.replaceAll(Dummy.class.getName(), Dummy.class.getSimpleName());
		XmlBinding binding = new XmlBinding(Dummy.class);
		Dummy defaultValue = null;
		assertThat("",//
				XmlReader.read(xml, binding, defaultValue), isNotNull());
		StringReader in = new StringReader(xml);
		assertThat("",//
				XmlReader.read(in, binding, defaultValue), isNotNull());
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.write(xml);
			out.close();
			assertThat("",//
					XmlReader.read(file, binding, defaultValue), isNotNull());
			assertThat("",//
					XmlReader.read(new FileInputStream(file), binding, defaultValue), isNotNull());
		} finally {
			file.delete();
		}
	}

	@Test(expectedExceptions = { XMLStreamException.class })
	public void readWithBadXMLStringThrowsException() throws XMLStreamException {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		XmlReader.read(xml);
	}

	public void readWithBadXMLStringReturnsDefault() {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		Dummy defaultValue = new Dummy();
		assertThat("",//
				XmlReader.read(xml, defaultValue), isSameAs(defaultValue));
	}

	@Test(expectedExceptions = { XMLStreamException.class })
	public void readWithBadXMLReaderThrowsException() throws XMLStreamException {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		StringReader in = new StringReader(xml);
		XmlReader.read(in);
	}

	public void readWithBadXMLReaderReturnsDefault() {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		StringReader in = new StringReader(xml);
		Dummy defaultValue = new Dummy();
		assertThat("",//
				XmlReader.read(in, defaultValue), isSameAs(defaultValue));
	}

	@Test(expectedExceptions = { XMLStreamException.class })
	public void readWithBadXMLStreamReaderThrowsException() throws XMLStreamException, IOException {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.write(xml);
			out.close();
			XmlReader.read(new FileInputStream(file));
		} finally {
			file.delete();
		}
	}

	public void readWithBadXMLStreamReaderReturnsDefault() throws IOException {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		Dummy defaultValue = new Dummy();
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.write(xml);
			out.close();
			assertThat("",//
					XmlReader.read(new FileInputStream(file), defaultValue), isSameAs(defaultValue));
		} finally {
			file.delete();
		}
	}

	@Test(expectedExceptions = { XMLStreamException.class })
	public void readWithBadXMLFileThrowsException() throws IOException, XMLStreamException {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.write(xml);
			out.close();
			XmlReader.read(file);
		} finally {
			file.delete();
		}
	}

	public void readWithBadXMLFileReturnsDefault() throws IOException {
		String xml = "<?xml version=\"1.0\" ?>" + //
				String.format("<%s>", Dummy.class.getName()) + //
				"	<unrecognizedElement class=\"java.lang.Integer\" value=\"3\"/>" + //
				String.format("</%s>", Dummy.class.getName());
		Dummy defaultValue = new Dummy();
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, false));
			out.write(xml);
			out.close();
			assertThat("",// )
					XmlReader.read(file, defaultValue), isSameAs(defaultValue));
		} finally {
			file.delete();
		}
	}

	@Test(expectedExceptions = { FileNotFoundException.class })
	public void readWithNoFileThrowsException() throws IOException, XMLStreamException {
		XmlReader.read(new File(UUID.randomUUID().toString()));
	}

	public void readWithNoFileReturnsDefault() {
		Dummy defaultValue = new Dummy();
		assertThat("",//
				XmlReader.read(new File(UUID.randomUUID().toString()), defaultValue), isSameAs(defaultValue));
	}
}
