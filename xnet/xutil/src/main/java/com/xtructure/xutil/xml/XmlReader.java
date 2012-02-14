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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javolution.xml.XMLBinding;
import javolution.xml.XMLObjectReader;
import javolution.xml.stream.XMLStreamException;

/**
 * A collection of convenience methods for translating an xml source to its
 * represented object.
 * 
 * @author Luis Guimbarda
 * 
 */
public final class XmlReader {
	/*
	 * read()s with xml source and a default object; no thrown exceptions
	 */
	public static <V> V read(String xmlString, V defaultObject) {
		return read(xmlString, null, defaultObject);
	}

	public static <V> V read(File inFile, V defaultObject) {
		return read(inFile, null, defaultObject);
	}

	public static <V> V read(InputStream in, V defaultObject) {
		return read(in, null, defaultObject);
	}

	public static <V> V read(Reader in, V defaultObject) {
		return read(in, null, defaultObject);
	}

	/*
	 * read()s with xml source
	 */
	public static <V> V read(String xmlString) throws XMLStreamException {
		return read(xmlString, null);
	}

	public static <V> V read(File inFile) throws IOException, XMLStreamException {
		return read(inFile, null);
	}

	public static <V> V read(InputStream in) throws XMLStreamException {
		return read(in, null);
	}

	public static <V> V read(Reader in) throws XMLStreamException {
		return read(in, null);
	}

	/*
	 * read()s with xml source, xml binding, and default object; no thrown
	 * exceptions
	 */
	public static <V> V read(String xmlString, XMLBinding binding, V defaultObject) {
		try {
			return read(xmlString, binding);
		} catch (XMLStreamException e) {}
		return defaultObject;
	}

	public static <V> V read(File inFile, XMLBinding binding, V defaultObject) {
		try {
			return read(inFile, binding);
		} catch (IOException e) {//
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return defaultObject;
	}

	public static <V> V read(InputStream in, XMLBinding binding, V defaultObject) {
		try {
			return read(in, binding);
		} catch (XMLStreamException e) {}
		return defaultObject;
	}

	public static <V> V read(Reader in, XMLBinding binding, V defaultObject) {
		try {
			return read(in, binding);
		} catch (XMLStreamException e) {}
		return defaultObject;
	}

	/*
	 * read()s with xml source and xml binding
	 */
	public static <V> V read(String xmlString, XMLBinding binding) throws XMLStreamException {
		StringReader in = null;
		try {
			return read(in = new StringReader(xmlString), binding);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public static <V> V read(File inFile, XMLBinding binding) throws IOException, XMLStreamException {
		FileReader in = null;
		try {
			return read(in = new FileReader(inFile), binding);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public static <V> V read(InputStream in, XMLBinding binding) throws XMLStreamException {
		XMLObjectReader reader = null;
		try {
			reader = XMLObjectReader.newInstance(in);
			if (binding != null) {
				reader.setBinding(binding);
			}
			return reader.read();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static <V> V read(Reader in, XMLBinding binding) throws XMLStreamException {
		XMLObjectReader reader = null;
		try {
			reader = XMLObjectReader.newInstance(in);
			if (binding != null) {
				reader.setBinding(binding);
			}
			return reader.read();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	private XmlReader() {}
}
