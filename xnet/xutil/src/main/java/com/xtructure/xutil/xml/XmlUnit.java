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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import javolution.text.TextFormat;
import javolution.xml.XMLFormat.InputElement;
import javolution.xml.XMLFormat.OutputElement;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * An xml unit.
 * 
 * @author Luis Guimbarda
 * 
 * @param <T>
 *            type pointed to by this unit instance
 */
public abstract class XmlUnit<T> extends AbstractXIdObject {
	/**
	 * Creates a new {@link Attribute} unit with the given name
	 * 
	 * @param name
	 *            the new {@link Attribute}'s name
	 * @return the new {@link Attribute}
	 */
	public static <T> Attribute<T> newAttribute(String name) {
		return newAttribute(name, (Class<T>) null);
	}

	/**
	 * Creates a new {@link Attribute} unit with the given name, pointing to the
	 * data of the given class
	 * 
	 * @param name
	 *            the new {@link Attribute}'s name
	 * @param cls
	 *            class of data pointed to by the new {@link Attribute}
	 * @return the new {@link Attribute}
	 * @throws IllegalArgumentException
	 *             if the given type is not null or String.class but has no
	 *             {@link TextFormat}
	 */
	public static <B, T extends B> Attribute<B> newAttribute(String name, Class<T> cls) throws IllegalArgumentException {
		return new Attribute<B>(name, cls);
	}

	/**
	 * Creates a new {@link Attribute} unit as specified by the given id
	 * 
	 * @param id
	 *            the id on which the new {@link Attribute} is based
	 * @return the new {@link Attribute}
	 */
	public static <B, T extends B> Attribute<B> newAttribute(XValId<T> id) {
		validateArg("id", id, isNotNull());
		return new Attribute<B>(id.getBase(), id.getType());
	}

	/**
	 * Creates a new {@link Element} unit with the given name
	 * 
	 * @param name
	 *            the new {@link Element}'s name
	 * @return the new {@link Element}
	 */
	public static <T> Element<T> newElement(String name) {
		return newElement(name, (Class<T>) null);
	}

	/**
	 * Creates a new {@link Element} unit with the given name, pointing to the
	 * data of the given class
	 * 
	 * @param name
	 *            the new {@link Element}'s name
	 * @param cls
	 *            class of data pointed to by the new {@link Element}
	 * @return the new {@link Element}
	 */
	public static <B, T extends B> Element<B> newElement(String name, Class<T> cls) {
		return new Element<B>(name, cls);
	}

	/**
	 * Creates a new {@link Element} unit as specified by the given id
	 * 
	 * @param id
	 *            the id on which the new {@link Element} is based
	 * @return the new {@link Element}
	 */
	public static <B, T extends B> Element<B> newElement(XValId<T> id) {
		validateArg("id", id, isNotNull());
		return new Element<B>(id.getBase(), id.getType());
	}

	/** The name of this unit. */
	private final String	name;
	/** The class of data pointed to by this unit. */
	private final Class<?>	type;

	/**
	 * Creates a new unit
	 * 
	 * @param name
	 *            the name of this unit
	 * @param type
	 *            the type of this unit
	 */
	private XmlUnit(final String name, final Class<?> type) {
		super(XId.newId(name));
		this.name = name;
		this.type = type;
	}

	/**
	 * Returns the name of this unit
	 * 
	 * @return the name of this unit
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Returns the type of data pointed to by this unit
	 * 
	 * @return the type of data pointed to by this unit
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getType() {
		return (Class<T>) type;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("%s{%s}", getClass().getSimpleName(), getName());
	}

	/**
	 * An xml attribute.
	 * 
	 * @param <B>
	 *            type pointed to by this {@link Attribute}
	 */
	public static final class Attribute<B> extends XmlUnit<B> {
		/**
		 * Creates a new {@link Attribute} with the given name, pointing to the
		 * data of the given class
		 * 
		 * @param name
		 *            the new {@link Attribute}'s name
		 * @param type
		 *            class of data pointed to by the new {@link Attribute}
		 * @throws IllegalArgumentException
		 *             if the given type is not null or String.class but has no
		 *             {@link TextFormat}
		 */
		private <T extends B> Attribute(String name, Class<T> type) throws IllegalArgumentException {
			super(name, type);
			if (type != null && !type.equals(String.class)) {
				validateArg("type must have a text format", TextFormat.getInstance(type), isNotNull());
			}
		}

		/**
		 * Parses the string of a read xml attribute.
		 * 
		 * @param valueString
		 *            the string read
		 * @param defaultValue
		 *            value to return if valueString can't be parsed
		 * @return the parsed value
		 */
		public final B parse(String valueString, B defaultValue) {
			try {
				return parse(valueString);
			} catch (XMLStreamException e) {
				return defaultValue;
			}
		}

		/**
		 * Parses the string of a read xml attribute.
		 * 
		 * @param valueString
		 *            the string read
		 * @return the parsed value
		 * @throws XMLStreamException
		 *             if the given valueString can't be parsed
		 */
		@SuppressWarnings("unchecked")
		public final B parse(String valueString) throws XMLStreamException {
			if (getType() == null || getType() == String.class) {
				return (B) valueString;
			}
			try {
				TextFormat<B> format = TextFormat.getInstance(getType());
				return format.parse(valueString);
			} catch (IllegalArgumentException e) {
				throw new XMLStreamException(e.getMessage());
			}
		}

		/**
		 * Writes the value of this attribute to the given output element.
		 * 
		 * @param xml
		 *            the output element to which the value of this attribute
		 *            should be written
		 * @param value
		 *            the value of this attribute
		 * @throws XMLStreamException
		 *             if the write failed
		 */
		public final void write(final OutputElement xml, final B value) throws XMLStreamException {
			xml.setAttribute(getName(), value);
		}
	}

	/**
	 * An xml element.
	 * 
	 * @param <B>
	 *            type pointed to by this {@link Element}
	 */
	public static final class Element<B> extends XmlUnit<B> {
		/**
		 * Creates a new {@link Element} with the given name, pointing to the
		 * data of the given class
		 * 
		 * @param name
		 *            the new {@link Element}'s name
		 * @param type
		 *            class of data pointed to by the new {@link Element}
		 */
		private <T extends B> Element(String name, Class<T> type) {
			super(name, type);
		}

		/**
		 * Reads the next xml element
		 * 
		 * @param xml
		 *            the {@link InputElement} from which the element is read
		 * @param defaultValue
		 *            the value to return if no element is read
		 * @return the read element
		 */
		public B read(InputElement xml, B defaultValue) {
			try {
				return read(xml);
			} catch (XMLStreamException e) {
				return defaultValue;
			}
		}

		/**
		 * Reads the next xml element
		 * 
		 * @param xml
		 *            the {@link InputElement} from which the element is read
		 * @return the read element
		 * @throws XMLStreamException
		 *             if no element is read
		 */
		public B read(InputElement xml) throws XMLStreamException {
			return getType() != null ? xml.get(getName(), getType()) : xml.<B> get(getName());
		}

		/**
		 * Writes the value of this element to the given output element.
		 * 
		 * @param xml
		 *            the output element to which the value of this element
		 *            should be written
		 * @param value
		 *            the value of this element
		 * @throws XMLStreamException
		 *             if the write failed
		 */
		public void write(OutputElement xml, B value) throws XMLStreamException {
			if (getType() != null) {
				xml.add(value, getName(), getType());
			} else {
				xml.add(value, getName());
			}
		}
	}
}
