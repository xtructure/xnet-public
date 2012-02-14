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
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javolution.xml.XMLBinding;
import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

/**
 * A collection of convenience methods for translating an object to its xml
 * representation.
 * 
 * @author Luis Guimbarda
 * 
 */
public final class XmlWriter {
	/** default indentation used when writing xml */
	public static final String		DEFAULT_INDENTATION	= "\t";
	/** default bindings used when writing xml */
	public static final XMLBinding	DEFAULT_XML_BINDING	= new XMLBinding();

	/*
	 * write object
	 */
	public static String write(Object obj) {
		return write(obj, DEFAULT_XML_BINDING, DEFAULT_INDENTATION);
	}

	public static void write(File outFile, Object obj) throws XMLStreamException, IOException {
		write(outFile, obj, DEFAULT_XML_BINDING, DEFAULT_INDENTATION);
	}

	public static void write(Writer out, Object obj) throws XMLStreamException {
		write(out, obj, DEFAULT_XML_BINDING, DEFAULT_INDENTATION);
	}

	/*
	 * write object with indentation
	 */
	public static String write(Object obj, String indentation) {
		return write(obj, DEFAULT_XML_BINDING, indentation);
	}

	public static void write(File outFile, Object obj, String indentation) throws XMLStreamException, IOException {
		write(outFile, obj, DEFAULT_XML_BINDING, indentation);
	}

	public static void write(Writer out, Object obj, String indentation) throws XMLStreamException {
		write(out, obj, DEFAULT_XML_BINDING, indentation);
	}

	/*
	 * write object with binding
	 */
	public static String write(Object obj, XMLBinding xmlBinding) {
		return write(obj, xmlBinding, DEFAULT_INDENTATION);
	}

	public static void write(File outFile, Object obj, XMLBinding binding) throws XMLStreamException, IOException {
		write(outFile, obj, binding, DEFAULT_INDENTATION);
	}

	public static void write(Writer out, Object obj, XMLBinding binding) throws XMLStreamException {
		write(out, obj, binding, DEFAULT_INDENTATION);
	}

	/*
	 * write object with indentation and binding
	 */
	public static String write(Object obj, XMLBinding xmlBinding, String indentation) {
		try {
			StringWriter out = new StringWriter();
			write(out, obj, xmlBinding, indentation);
			return out.toString();
		} catch (XMLStreamException e) {}
		return null;
	}

	public static void write(File outFile, Object obj, XMLBinding binding, String indentation) throws XMLStreamException, IOException {
		FileWriter out = null;
		try {
			write(out = new FileWriter(outFile), obj, binding, indentation);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static void write(Writer out, Object obj, XMLBinding binding, String indentation) throws XMLStreamException {
		XMLObjectWriter writer = null;
		try {
			writer = XMLObjectWriter.newInstance(out).setBinding(binding).setIndentation(indentation);
			writer.write(obj);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private XmlWriter() {}
}
