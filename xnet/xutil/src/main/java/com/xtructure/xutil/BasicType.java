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
package com.xtructure.xutil;

import java.util.regex.Pattern;

/**
 * An enumeration of &quot;basic&quot; types (primitives and strings).
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.2
 */
public enum BasicType {
	/** The boolean type. */
	BOOLEAN("(?i:bool(ean)?)", Boolean.class),
	/** The byte type. */
	BYTE("(?i:byte)", Byte.class),
	/** The character type. */
	CHARACTER("(?i:char(acter)?)", Character.class),
	/** The double type. */
	DOUBLE("(?i:double)", Double.class),
	/** The float type. */
	FLOAT("(?i:float)", Float.class),
	/** The integer type. */
	INTEGER("(?i:int(eger)?)", Integer.class),
	/** The long type. */
	LONG("(?i:long)", Long.class),
	/** The short type. */
	SHORT("(?i:short)", Short.class),
	/** The string type. */
	STRING("(?i:str(ing)?)", String.class);
	/**
	 * Returns the type with the given name, if any.
	 * 
	 * @param name
	 *            the name of the type to return
	 * 
	 * @return the type with the given name, or {@code null} if no type has the
	 *         given name
	 */
	public static final BasicType getInstance(final String name) {
		for (final BasicType type : values()) {
			if (type._namePattern.matcher(name).matches()) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Returns the type with the given equivalent java type, if any.
	 * 
	 * @param javaType
	 *            a java type equivalent to the type to return
	 * 
	 * @return the type with the given equivalent java type, or {@code null} if
	 *         no type has the given equivalent java type
	 */
	public static final BasicType getInstance(final Class<?> javaType) {
		for (final BasicType type : values()) {
			if (type._javaType.equals(javaType)) {
				return type;
			}
		}
		return null;
	}

	/** The pattern of the name(s) of this type. */
	private final Pattern	_namePattern;
	/** A java type equivalent to this type. */
	private final Class<?>	_javaType;

	/**
	 * Creates a new type.
	 * 
	 * @param nameRegex
	 *            a regular expression describing the name(s) of this type
	 * 
	 * @param javaType
	 *            a java type equivalent to this type
	 */
	private BasicType(final String nameRegex, final Class<?> javaType) {
		_namePattern = Pattern.compile(nameRegex);
		_javaType = javaType;
	}

	/**
	 * Returns a java type equivalent to this type.
	 * 
	 * @return a java type equivalent to this type
	 */
	public final Class<?> getJavaType() {
		return _javaType;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return name().replaceAll("_", " ").toLowerCase();
	}
}
