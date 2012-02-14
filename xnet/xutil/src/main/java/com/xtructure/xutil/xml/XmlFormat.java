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

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.id.XIdObjectManagerImpl;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * Base generic implementation for xml formats.
 * 
 * @author Luis Guimbarda
 * 
 * @param <T>
 *            the type for which this format is defined
 */
public abstract class XmlFormat<T> extends XMLFormat<T> {
	/** this {@link XmlFormat}'s {@link Attribute}s */
	private final XIdObjectManager<Attribute<?>>	recognizedAttributes;
	/** this {@link XmlFormat}'s {@link Element}s */
	private final XIdObjectManager<Element<?>>		recognizedElements;

	/**
	 * Creates a new {@link XmlFormat} for the given class, using the given
	 * lists of attributes and elements.
	 * 
	 * @param cls
	 *            class for which this {@link XmlFormat} is defined
	 */
	protected XmlFormat(Class<T> cls) {
		super(cls);
		this.recognizedAttributes = new XIdObjectManagerImpl<Attribute<?>>();
		this.recognizedElements = new XIdObjectManagerImpl<Element<?>>();
	}

	/**
	 * Adds the given attribute to this xml format's recognized attribute
	 * manager.
	 * 
	 * @param attribute
	 *            the attribute to recognize
	 */
	protected void addRecognized(Attribute<?> attribute) {
		recognizedAttributes.register(attribute);
	}

	/**
	 * Adds the given element to this xml format's recognized element manager.
	 * 
	 * @param element
	 *            the element to recognize
	 */
	protected void addRecognized(Element<?> element) {
		recognizedElements.register(element);
	}

	/** {@inheritDoc} */
	@Override
	public final void read(InputElement xml, T obj) throws XMLStreamException {
		// nothing, handled in newInstance()
	}

	/** {@inheritDoc} */
	@Override
	public final void write(T obj, OutputElement xml) throws XMLStreamException {
		writeAttributes(obj, xml);
		writeElements(obj, xml);
	}

	/** {@inheritDoc} */
	@Override
	public final T newInstance(Class<T> cls, InputElement xml) throws XMLStreamException {
		ReadAttributes readAttributes = new ReadAttributes(xml, recognizedAttributes);
		ReadElements readElements = new ReadElements(xml, recognizedElements);
		return newInstance(readAttributes, readElements);
	}

	/**
	 * Creates a new instance of the class for which this {@link XmlFormat} is
	 * defined, using the given read attributes and elements.
	 * 
	 * @param readAttributes
	 *            the attributes that have been read
	 * @param readElements
	 *            the elements that have been read
	 * @return the new instance
	 * @throws XMLStreamException
	 */
	protected abstract T newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException;

	/**
	 * Writes attributes to the given OutputElement derived from the
	 * given object.
	 * 
	 * @param object
	 *            the instance from which attributes are written
	 * @param xml
	 *            the OutputElement to which attributes are written
	 * @throws XMLStreamException
	 */
	protected abstract void writeAttributes(T object, OutputElement xml) throws XMLStreamException;

	/**
	 * Writes elements to the given OutputElement derived from the given
	 * object.
	 * 
	 * @param object
	 *            the instance from which elements are written
	 * @param xml
	 *            the OutputElement to which elements are written
	 * @throws XMLStreamException
	 */
	protected abstract void writeElements(T object, OutputElement xml) throws XMLStreamException;
}
