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
package com.xtructure.xutil.id;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import javolution.text.Cursor;

import com.xtructure.xutil.WormField;
import com.xtructure.xutil.format.XTextFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * The Class XValId.
 * 
 * {@link XValId} instances identify values by name and type. Unlike {@link XId}
 * , {@link XValId} doesn't support instance numbers.
 * 
 * @param <V>
 *            the type of value identified.
 * @author Luis Guimbarda
 */
public class XValId<V> extends XId {
	/** The text format of {@link XValId} instances. */
	@SuppressWarnings("rawtypes")
	public static final XTextFormat<XValId>	TEXT_FORMAT	= new TextFormat();

	/**
	 * Creates a new {@link XValId} with a unique base for the {@link String}
	 * type. Any given instanceNums are ignored.
	 *
	 * @param instanceNums the instance nums
	 * @return the XValId
	 * @see XId#newId(String, Integer...)
	 */
	public static XValId<String> newId(Integer... instanceNums) {
		return newId();
	}

	/**
	 * Creates a new {@link XValId} with a unique base for the {@link String}
	 * type.
	 *
	 * @return the XValId
	 */
	public static XValId<String> newId() {
		return newId(UUID.randomUUID().toString(), String.class);
	}

	/**
	 * Creates a new {@link XValId} with the given base for the {@link String}
	 * type. Any given instanceNums are ignored.
	 *
	 * @param base the base
	 * @param instanceNums the instance nums
	 * @return the XValId
	 * @see XId#newId(String, Integer...)
	 */
	public static XValId<String> newId(String base, Integer... instanceNums) {
		return newId(base);
	}

	/**
	 * Creates a new XValId with the given base for the {@link String} type.
	 *
	 * @param base the base
	 * @return the XValId
	 */
	public static XValId<String> newId(String base) {
		return newId(base, String.class);
	}

	/**
	 * Creates a new {@link XValId} with a unique base and the given type.
	 *
	 * @param <V> the value type
	 * @param type the type
	 * @return the XValId
	 */
	public static <V> XValId<V> newId(Class<V> type) {
		return newId(UUID.randomUUID().toString(), type);
	}

	/**
	 * Creates a new {@link XValId} with the given base and type.
	 *
	 * @param <B> the generic type
	 * @param <V> the value type
	 * @param base the base
	 * @param type the type
	 * @return the XValId
	 */
	public static <B, V extends B> XValId<B> newId(String base, Class<V> type) {
		return new XValId<B>(base, type);
	}

	/** the {@link Class} of value identified by this {@link XValId}. */
	private final Class<V>					type;
	
	/** this id as an {@link Attribute}. */
	private final WormField<Attribute<V>>	attribute	= new WormField<Attribute<V>>();
	
	/** this id as an {@link Element}. */
	private final WormField<Element<V>>		element		= new WormField<Element<V>>();

	/**
	 * Creates a new {@link XValId} with the given base and type.
	 *
	 * @param base the base
	 * @param type the type
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	@SuppressWarnings("unchecked")
	protected XValId(String base, Class<?> type) throws IllegalArgumentException {
		super(base, Collections.<Integer> emptyList());
		if (type == null) {
			throw new IllegalArgumentException("type must not be null");
		}
		this.type = (Class<V>) type;
	}

	/**
	 * Gets the {@link Class} of value identified by this {@link XValId}.
	 * 
	 * @return the {@link Class} of value identified by this {@link XValId}.
	 */
	public Class<V> getType() {
		return type;
	}

	/**
	 * Creates the child.
	 *
	 * @param newInstanceNum the new instance num
	 * @return the XId
	 * {@link #createChild(int)} is not supported by {@link XValId}
	 */
	@Override
	public XId createChild(int newInstanceNum) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns an {@link Attribute} based on this id.
	 * 
	 * @return an {@link Attribute} based on this id.
	 */
	public Attribute<V> toAttribute() {
		if (!attribute.isInitialized()) {
			attribute.initValue(XmlUnit.newAttribute(this));
		}
		return attribute.getValue();
	}

	/**
	 * Returns an {@link Element} based on this id.
	 * 
	 * @return an {@link Element} based on this id.
	 */
	public Element<V> toElement() {
		if (!element.isInitialized()) {
			element.initValue(XmlUnit.newElement(this));
		}
		return element.getValue();
	}

	/** The text format of {@link XValId} instances. */
	@SuppressWarnings("rawtypes")
	private static final class TextFormat extends XTextFormat<XValId> {
		
		/**
		 * Instantiates a new text format.
		 */
		protected TextFormat() {
			super(XValId.class);
		}

		/* (non-Javadoc)
		 * @see javolution.text.TextFormat#format(java.lang.Object, java.lang.Appendable)
		 */
		@Override
		public Appendable format(XValId id, Appendable appendable) throws IOException {
			return appendable.append(String.format("%s:[%s]", id.getBase(), id.getType().getName()));
		}

		/* (non-Javadoc)
		 * @see javolution.text.TextFormat#parse(java.lang.CharSequence, javolution.text.Cursor)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public XValId parse(CharSequence chars, Cursor cursor) throws IllegalArgumentException {
			String base = cursor.nextToken(chars, DELIM).toString();
			cursor.skip(DELIM, chars);
			cursor.skip('[', chars);
			String typeString = cursor.nextToken(chars, ']').toString();
			cursor.skip(']', chars);
			Class<?> type;
			try {
				type = Class.forName(typeString);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
			return new XValId(base, type);
		}
	}
}
