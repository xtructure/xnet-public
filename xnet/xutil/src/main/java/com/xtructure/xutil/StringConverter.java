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

import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.matches;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

/**
 * A set of utilities for converting between strings and primitive types.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.4
 */
public final class StringConverter {
	/** A regular expression describing the valid string forms of booleans. */
	public static final String				BOOLEAN_VALUE_REGEX	= "(true|false)";
	/** The singleton instance of this class. */
	private static final StringConverter	INSTANCE			= new StringConverter();

	/**
	 * Returns a string converter.
	 * 
	 * @return a string converter
	 */
	public static final StringConverter getInstance() {
		return INSTANCE;
	}

	/** Creates a new string converter. */
	private StringConverter() {
		super();
	}

	/**
	 * Converts the given string to a boolean.
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the boolean equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} does not match {@link #BOOLEAN_VALUE_REGEX}
	 */
	public final Boolean toBoolean(final String str) {
		validateArg("str", str, isNotNull(), matches(BOOLEAN_VALUE_REGEX));
		// LOGGER.verifyArgNonNull("str", str);
		// LOGGER.verifyArg("str", str,
		// BOOLEAN_VALUE_REGEX.matcher(str).matches(),
		// "must match regex '%s'", BOOLEAN_VALUE_REGEX);
		return Boolean.valueOf(str);
	}

	/**
	 * Converts the given string to a byte.
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the byte equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} does not have the correct form
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} under- or overflows a byte
	 */
	public final Byte toByte(final String str) {
		validateArg("str", str, isNotNull());
		// LOGGER.verifyArgNonNull("str", str);
		/*
		 * FIXME - add check against a regex to differentiate between bad format
		 * and under-/overflow
		 */
		try {
			return Byte.decode(str);
		} catch (NumberFormatException numberFormatEx) {
			throw new IllegalArgumentException(String.format("string '%s' cannot be converted to byte", str), numberFormatEx);
			// throw LOGGER.errorMessage(new IllegalArgumentException(String
			// .format("string '%s' cannot be converted to byte", str),
			// numberFormatEx));
		}
	}

	/**
	 * Converts the given string to a character.
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the character equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} does not have length 1
	 */
	public final Character toCharacter(final String str) {
		validateArg("str", str, isNotNull());
		validateArg("str (length)", str.length(), isEqualTo(1));
		// LOGGER.verifyArgNonNull("str", str);
		// LOGGER.verifyArg("str", str, (str.length() == 1),
		// "must have length 1");
		return Character.valueOf(str.charAt(0));
	}

	/**
	 * Converts the given string to a double.
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the double equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} does not have the correct form
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} under- or overflows a double
	 */
	public final Double toDouble(final String str) {
		validateArg("str", str, isNotNull());
		// LOGGER.verifyArgNonNull("str", str);
		/*
		 * FIXME - add check against a regex to differentiate between bad format
		 * and under-/overflow
		 */
		try {
			return Double.valueOf(str);
		} catch (NumberFormatException numberFormatEx) {
			throw new IllegalArgumentException(String.format("string '%s' cannot be converted to double", str), numberFormatEx);
			// throw LOGGER.errorMessage(new IllegalArgumentException(String
			// .format("string '%s' cannot be converted to double", str),
			// numberFormatEx));
		}
	}

	/**
	 * Converts the given string to a float.
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the float equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} does not have the correct form
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} under- or overflows a float
	 */
	public final Float toFloat(final String str) {
		validateArg("str", str, isNotNull());
		// LOGGER.verifyArgNonNull("str", str);
		/*
		 * FIXME - add check against a regex to differentiate between bad format
		 * and under-/overflow.
		 */
		try {
			return Float.valueOf(str);
		} catch (NumberFormatException numberFormatEx) {
			throw new IllegalArgumentException(String.format("string '%s' cannot be converted to float", str), numberFormatEx);
			// throw LOGGER.errorMessage(new IllegalArgumentException(String
			// .format("string '%s' cannot be converted to float", str),
			// numberFormatEx));
		}
	}

	/**
	 * Converts the given string to an integer.
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the integer equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} does not have the correct form
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} under- or overflows an integer
	 */
	public final Integer toInteger(final String str) {
		validateArg("str", str, isNotNull());
		// LOGGER.verifyArgNonNull("str", str);
		/*
		 * FIXME - add check against a regex to differentiate between bad format
		 * and under-/overflow
		 */
		try {
			return Integer.decode(str);
		} catch (NumberFormatException numberFormatEx) {
			throw new IllegalArgumentException(String.format("string '%s' cannot be converted to integer", str), numberFormatEx);
			// throw LOGGER.errorMessage(new IllegalArgumentException(String
			// .format("string '%s' cannot be converted to integer", str),
			// numberFormatEx));
		}
	}

	/**
	 * Converts the given string to a long.
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the long equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} does not have the correct form
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} under- or overflows a long
	 */
	public final Long toLong(final String str) {
		validateArg("str", str, isNotNull());
		// LOGGER.verifyArgNonNull("str", str);
		/*
		 * FIXME - add check against a regex to differentiate between bad format
		 * and under-/overflow
		 */
		try {
			return Long.decode(str);
		} catch (NumberFormatException numberFormatEx) {
			throw new IllegalArgumentException(String.format("string '%s' cannot be converted to long", str), numberFormatEx);
			// throw LOGGER.errorMessage(new IllegalArgumentException(String
			// .format("string '%s' cannot be converted to long", str),
			// numberFormatEx));
		}
	}

	/**
	 * Converts the given string to a short.
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the short equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} does not have the correct form
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} under- or overflows a short
	 */
	public final Short toShort(final String str) {
		validateArg("str", str, isNotNull());
		// LOGGER.verifyArgNonNull("str", str);
		/*
		 * FIXME - add check against a regex to differentiate between bad format
		 * and under-/overflow
		 */
		try {
			return Short.decode(str);
		} catch (NumberFormatException numberFormatEx) {
			throw new IllegalArgumentException(String.format("string '%s' cannot be converted to short", str), numberFormatEx);
			// throw LOGGER.errorMessage(new IllegalArgumentException(String
			// .format("string '%s' cannot be converted to short", str),
			// numberFormatEx));
		}
	}

	/**
	 * Converts the given string to the given basic type.
	 * 
	 * @param type
	 *            the type to which the string is to be converted
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the short equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given type is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} if the conversion failed
	 */
	public final Object toBasic(final BasicType type, final String str) {
		validateArg("type", type, isNotNull());
		validateArg("str", str, isNotNull());
		// LOGGER.verifyArgNonNull("type", type);
		// LOGGER.verifyArgNonNull("str", str);
		switch (type) {
			case BOOLEAN:
				return toBoolean(str);
			case BYTE:
				return toByte(str);
			case CHARACTER:
				return toCharacter(str);
			case DOUBLE:
				return toDouble(str);
			case FLOAT:
				return toFloat(str);
			case INTEGER:
				return toInteger(str);
			case LONG:
				return toLong(str);
			case SHORT:
				return toShort(str);
			case STRING:
				return str;
			default: // should never happen
				// throw LOGGER.abort("unknown basic type '%s'", type);
				throw new AssertionError(String.format("unknown basic type '%s'", type));
		}
	}

	/**
	 * Converts the given string to the given basic type.
	 * 
	 * @param <T>
	 *            the type to be returned
	 * 
	 * @param type
	 *            the type to which the string is to be converted
	 * 
	 * @param str
	 *            the string to be converted
	 * 
	 * @return the short equivalent to the given string
	 * 
	 * @throws IllegalArgumentException
	 *             if the given type is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if the given string is <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             if {@code str} if the conversion failed
	 */
	public final <T> T toBasic(final Class<T> type, final String str) {
		validateArg("type", type, isNotNull());
		validateArg("str", str, isNotNull());
		// LOGGER.verifyArgNonNull("type", type);
		// LOGGER.verifyArgNonNull("str", str);
		final BasicType basicType = BasicType.getInstance(type);
		validateArg("type (basic type equivalent)", basicType, isNotNull());
		// LOGGER.verifyArg("type", type, (basicType != null),
		// "do not know how to convert this type");
		return type.cast(toBasic(basicType, str));
	}
}
