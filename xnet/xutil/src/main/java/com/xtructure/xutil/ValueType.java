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

import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * The Enum ValueType.
 *
 * {@link ValueType} enumerates the different types of values that can be
 * identified by {@link XValId}s, and thus what types can be used with
 * {@link ValueMap}.
 * @author Luis Guimbarda
 */
public enum ValueType {
	
	/** The DOUBLE. {@link ValueType} for {@link Double} typed values */
	DOUBLE(Double.class, Double.valueOf(0)), //
	/** The FLOAT. {@link ValueType} for {@link Float} typed values */
	FLOAT(Float.class, Float.valueOf(0)), //
	/** The LONG. {@link ValueType} for {@link Long} typed values */
	LONG(Long.class, Long.valueOf(0)), //
	/** The INTEGER. {@link ValueType} for {@link Integer} typed values */
	INTEGER(Integer.class, Integer.valueOf(0)), //
	/** The SHORT. {@link ValueType} for {@link Short} typed values */
	SHORT(Short.class, Integer.valueOf(0).shortValue()), //
	/** The BYTE. {@link ValueType} for {@link Byte} typed values */
	BYTE(Byte.class, Integer.valueOf(0).byteValue()), //
	/** The STRING. {@link ValueType} for {@link String} typed values */
	STRING(String.class, ""), //
	/** The BOOLEAN. {@link ValueType} for {@link Boolean} typed values */
	BOOLEAN(Boolean.class, false), //
	/** The XID. */
	XID(XId.class, XId.newId());
	
	/** the {@link Class} this {@link ValueType} represents. */
	private final Class<?>	type;
	
	/** a default value for this {@link ValueType}. */
	private final Object	defaultValue;

	/**
	 * Instantiates a new value type.
	 *
	 * @param type the type
	 * @param defaultValue the default value
	 */
	private ValueType(Class<?> type, Object defaultValue) {
		this.type = type;
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the {@link Class} this {@link ValueType} represents.
	 * 
	 * @return the {@link Class} this {@link ValueType} represents.
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Checks if is STRING.
	 *
	 * @return true, if is STRING
	 */
	public boolean isSTRING() {
		return STRING.equals(this);
	}

	/**
	 * Checks if is XID.
	 *
	 * @return true, if is XID
	 */
	public boolean isXID() {
		return XID.equals(this);
	}

	/**
	 * Gets the default value.
	 *
	 * @return the defaultValue
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Gets the {@link ValueType} that represents the given {@link Class}, or
	 * null if no such ValueType exists.
	 *
	 * @param cls the cls
	 * @return the representative ValueType.
	 */
	public static ValueType getValueType(Class<?> cls) {
		for (ValueType valueType : values()) {
			if (valueType.type.equals(cls)) {
				return valueType;
			}
		}
		return null;
	}

	/**
	 * Indicates whether the given {@link XValId} identifies values of the type
	 * represented by this {@link ValueType}.
	 *
	 * @param valueId the value id
	 * @return true if the given ValueId has this ValueType for a type,
	 * otherwise false.
	 */
	public boolean isTypeOf(XValId<?> valueId) {
		if (valueId == null) {
			return false;
		}
		return this.equals(getValueType(valueId.getType()));
	}

	/**
	 * To value id.
	 *
	 * @param <V> the value type
	 * @param base the base
	 * @return the XValId
	 */
	@SuppressWarnings("unchecked")
	public <V extends Comparable<V>> XValId<V> toValueId(String base) {
		return XValId.newId(base, (Class<V>) getType());
	}

	/**
	 * Min value.
	 *
	 * @param <V> the value type
	 * @return the v
	 */
	@SuppressWarnings("unchecked")
	public <V extends Comparable<?>> V minValue() {
		switch (this) {
			case BOOLEAN:
				return (V) new Boolean(false);
			case BYTE:
				return (V) new Byte(Byte.MIN_VALUE);
			case DOUBLE:
				return (V) new Double(Double.MIN_VALUE);
			case FLOAT:
				return (V) new Float(Float.MIN_VALUE);
			case INTEGER:
				return (V) new Integer(Integer.MIN_VALUE);
			case LONG:
				return (V) new Long(Long.MIN_VALUE);
			case SHORT:
				return (V) new Short(Short.MIN_VALUE);
			case STRING:
			case XID:
			default:
				return null;
		}
	}

	/**
	 * Max value.
	 *
	 * @param <V> the value type
	 * @return the v
	 */
	@SuppressWarnings("unchecked")
	public <V extends Comparable<?>> V maxValue() {
		switch (this) {
			case BOOLEAN:
				return (V) new Boolean(true);
			case BYTE:
				return (V) new Byte(Byte.MAX_VALUE);
			case DOUBLE:
				return (V) new Double(Double.MAX_VALUE);
			case FLOAT:
				return (V) new Float(Float.MAX_VALUE);
			case INTEGER:
				return (V) new Integer(Integer.MAX_VALUE);
			case LONG:
				return (V) new Long(Long.MAX_VALUE);
			case SHORT:
				return (V) new Short(Short.MAX_VALUE);
			case STRING:
			case XID:
			default:
				return null;
		}
	}
}
