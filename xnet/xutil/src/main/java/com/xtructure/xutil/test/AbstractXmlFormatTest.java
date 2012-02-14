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
package com.xtructure.xutil.test;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;

import java.io.StringReader;
import java.util.regex.Pattern;

import javolution.xml.XMLBinding;
import javolution.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlReader;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * The Class AbstractXmlFormatTest.
 *
 * @param <T> the class whose xml format is being tested
 * @author Luis Guimbarda
 */
@Test(groups = { "xml:base" })
public abstract class AbstractXmlFormatTest<T> {
	
	/** The Constant XML_HEADER. */
	protected static final String	XML_HEADER			= "<?xml version=\"1.0\" ?>\n";
	
	/** The Constant INDENT. */
	protected static final String	INDENT				= "\t";
	
	/** The Constant DEFAULT_XML_BINDING. */
	private static final XMLBinding	DEFAULT_XML_BINDING	= new XMLBinding();
	
	/** The xml binding. */
	private final XMLBinding		xmlBinding;

	/**
	 * Creates a new {@link AbstractXmlFormatTest}.
	 * 
	 * @param xmlBinding
	 *            the xml binding to use in testing; if null, an empty binding
	 *            is used
	 */
	public AbstractXmlFormatTest(XMLBinding xmlBinding) {
		this.xmlBinding = xmlBinding == null ? DEFAULT_XML_BINDING : xmlBinding;
	}

	/**
	 * Write xml writes expected xml.
	 *
	 * @param t the t
	 * @throws XMLStreamException the xML stream exception
	 */
	@Test(dataProvider = "instances")
	public final void writeXMLWritesExpectedXML(T t) throws XMLStreamException {
		// generate xml strings
		String outString = XmlWriter.write(t, xmlBinding, INDENT);
		String expectedXMLString = generateExpectedXMLString(t);
		// split strings at new lines
		String[] gotLines = outString.split("\n");
		String[] expectedLines = expectedXMLString.split("\n");
		// compare xml
		assertThat("writeXMLWritesExpectedXML: must have same line count",//
				gotLines.length, isEqualTo(expectedLines.length));
		for (int i = 0; i < expectedLines.length; i++) {
			assertThat("writeXMLWritesExpectedXML: must have same line " + i,//
					gotLines[i], isEqualTo(expectedLines[i]));
		}
	}

	/**
	 * New instance xml returns expected instance.
	 *
	 * @param t the t
	 * @throws XMLStreamException the xML stream exception
	 */
	@Test(dataProvider = "instances")
	public final void newInstanceXMLReturnsExpectedInstance(T t) throws XMLStreamException {
		// generate xml strings
		String outXmlString = XmlWriter.write(t, xmlBinding, INDENT);
		T newT = XmlReader.read(new StringReader(outXmlString), xmlBinding);
		outXmlString = XmlWriter.write(newT, xmlBinding, INDENT);
		String expectedXmlString = generateExpectedXMLString(t);
		// split strings at new lines
		String[] expectedLines = expectedXmlString.split("\n");
		String[] gotLines = outXmlString.split("\n");
		// compare xml
		for (int i = 0; i < Math.min(expectedLines.length, gotLines.length); i++) {
			assertThat("newInstanceXMLReturnsExpectedInstance: comparing line " + i,//
					gotLines[i], isEqualTo(expectedLines[i]));
		}
		assertThat("newInstanceXMLReturnsExpectedInstance: comparing line count",//
				gotLines.length, isEqualTo(expectedLines.length));
	}

	/**
	 * Gets the xml binding.
	 *
	 * @return the xml binding
	 */
	public XMLBinding getXmlBinding() {
		return xmlBinding;
	}

	/**
	 * Create the expected xml string for the given instance.
	 * 
	 * @param t
	 *            the instance for which an xml string is created
	 * @return the created xml string
	 */
	protected abstract String generateExpectedXMLString(T t);

	/**
	 * Data provider that produces parameter lists containing:<br>
	 * 1) an instance of the tested class<br>
	 * 2) a string of the expected XML output that that instance would produce.
	 * <p>
	 * Implementations of the method must add the annotation:<br>
	 * \@DataProvider
	 *
	 * @return the object[][]
	 */
	protected abstract Object[][] instances();

	/**
	 * class to help with somewhat obfuscated elements.
	 */
	protected static final class Wrapper {
		
		/** The Constant ELEMENT_HOOK_NAME. */
		public static final String		ELEMENT_HOOK_NAME	= "__HOOK__";
		
		/** The Constant HOOK_PATTERN. */
		private static final Pattern	HOOK_PATTERN		= Pattern.compile(ELEMENT_HOOK_NAME);
		
		/** The Constant TYPE_PATTERN. */
		private static final Pattern	TYPE_PATTERN		= Pattern.compile(" class=\"[^\"]*\"");

		/**
		 * Replace hook.
		 *
		 * @param line the line
		 * @param replacement the replacement
		 * @return the string
		 */
		public static String replaceHook(String line, String replacement) {
			return HOOK_PATTERN.matcher(line).replaceFirst(replacement);
		}

		/**
		 * Removes the class attribute.
		 *
		 * @param line the line
		 * @return the string
		 */
		public static String removeClassAttribute(String line) {
			return TYPE_PATTERN.matcher(line).replaceFirst("");
		}

		/** The object. */
		private final Object	object;

		/**
		 * Instantiates a new wrapper.
		 *
		 * @param object the object
		 */
		public Wrapper(Object object) {
			this.object = object;
		}

		/** The Constant XML_FORMAT. */
		public static final XmlFormat<Wrapper>	XML_FORMAT	= new WrapperXmlFormat();

		/**
		 * The Class WrapperXmlFormat.
		 */
		private static final class WrapperXmlFormat extends XmlFormat<Wrapper> {
			
			/** The Constant OBJECT_ELEMENT. */
			private static final Element<Object>	OBJECT_ELEMENT	= XmlUnit.newElement(ELEMENT_HOOK_NAME);

			/**
			 * Instantiates a new wrapper xml format.
			 */
			protected WrapperXmlFormat() {
				super(Wrapper.class);
			}

			/* (non-Javadoc)
			 * @see com.xtructure.xutil.xml.XmlFormat#newInstance(com.xtructure.xutil.xml.ReadAttributes, com.xtructure.xutil.xml.ReadElements)
			 */
			@Override
			protected Wrapper newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				throw new XMLStreamException("Wrapper not intended for reading.");
			}

			/* (non-Javadoc)
			 * @see com.xtructure.xutil.xml.XmlFormat#writeAttributes(java.lang.Object, javolution.xml.XMLFormat.OutputElement)
			 */
			@Override
			protected void writeAttributes(Wrapper object, OutputElement xml) throws XMLStreamException {
				// nothing
			}

			/* (non-Javadoc)
			 * @see com.xtructure.xutil.xml.XmlFormat#writeElements(java.lang.Object, javolution.xml.XMLFormat.OutputElement)
			 */
			@Override
			protected void writeElements(Wrapper object, OutputElement xml) throws XMLStreamException {
				OBJECT_ELEMENT.write(xml, object.object);
			}
		}
	}
}
