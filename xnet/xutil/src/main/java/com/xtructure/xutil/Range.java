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

import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javolution.text.TextFormat;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A range.
 * 
 * @param <T>
 *            the type of members in this range
 * @author Peter N&uuml;rnberg
 * @version 0.9.6
 */
public final class Range<T extends Comparable<T>> {
	/** An open range. */
	@SuppressWarnings("rawtypes")
	private static final Range<?>	OPEN_RANGE	= new Range();

	/**
	 * Returns an open range (i.e., with null min/max value).
	 * 
	 * @param <T>
	 *            the type of the range to return
	 * @return the open range
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Comparable<T>> Range<T> openRange() {
		return (Range<T>) OPEN_RANGE;
	}

	/** The full range of boolean values. */
	public static final Range<Boolean>	FULL_BOOLEAN_RANGE	= Range.getInstance(Boolean.FALSE, Boolean.TRUE);
	/**
	 * A range of boolean values representing the constant {@link Boolean#TRUE}.
	 */
	public static final Range<Boolean>	TRUE_BOOLEAN_RANGE	= Range.getInstance(Boolean.TRUE, Boolean.TRUE);
	/**
	 * A range of boolean values representing the constant {@link Boolean#FALSE}
	 * .
	 */
	public static final Range<Boolean>	FALSE_BOOLEAN_RANGE	= Range.getInstance(Boolean.FALSE, Boolean.FALSE);
	/** A pattern describing a range. */
	private static final Pattern		RANGE_PATTERN		= Pattern.compile("\\[(.*) \\.\\. (.*)\\]");

	/**
	 * A utility to parse ranges.
	 *
	 * @param <T> the generic type
	 * @param text the text to parse
	 * @param rangeType the type of endpoint of the range to parse
	 * @return a parsed range
	 * @throws Exception the exception
	 */
	public static final <T extends Comparable<T>> Range<T> parseRange(final String text, final Class<T> rangeType) throws Exception {
		final TextFormat<T> textFormat = TextFormat.getInstance(rangeType);
		if (textFormat == null) {
			throw new Exception(String.format("no text format for type %s (location %s)", rangeType, text));
		}
		final Matcher matcher = RANGE_PATTERN.matcher(text);
		final String minStr = (matcher.matches() ? matcher.group(1) : text);
		final String maxStr = (matcher.matches() ? matcher.group(2) : text);
		if (String.class.equals(rangeType)) {
			return new Range<T>(rangeType.cast(minStr), rangeType.cast(maxStr));
		}
		final T min = (minStr.equals("*") ? null : textFormat.parse(minStr));
		final T max = (maxStr.equals("*") ? null : textFormat.parse(maxStr));
		return new Range<T>(min, max);
	}

	/**
	 * Gets the single instance of Range.
	 *
	 * @param <V> the value type
	 * @param value the value
	 * @return single instance of Range
	 */
	public static final <V extends Comparable<V>> Range<V> getInstance(V value) {
		return new Range<V>(value);
	}

	/**
	 * Gets the single instance of Range.
	 *
	 * @param <V> the value type
	 * @param min the min
	 * @param max the max
	 * @return single instance of Range
	 */
	public static final <V extends Comparable<V>> Range<V> getInstance(V min, V max) {
		return new Range<V>(min, max);
	}

	/** The open end symbol. */
	private static final String	OPEN_END	= "*";
	/** The minimum element of this range. */
	private final T				_minimum;
	/** The maximum element of this range. */
	private final T				_maximum;

	/**
	 * Creates a new open range.
	 */
	private Range() {
		this(null, null);
	}

	/**
	 * Creates a new single-point range.
	 *
	 * @param value the value
	 */
	private Range(final T value) {
		this(value, value);
	}

	/**
	 * Creates a new range.
	 * 
	 * @param minimum
	 *            the minimum element of this range
	 * @param maximum
	 *            the maximum element of this range
	 */
	private Range(final T minimum, final T maximum) {
		super();
		if ((minimum != null) && (maximum != null)) {
			validateArg("minimum", minimum, isLessThanOrEqualTo(maximum));
		}
		_minimum = minimum;
		_maximum = maximum;
	}

	/**
	 * Returns the minimum element of this range.
	 * 
	 * @return the minimum element of this range
	 */
	public final T getMinimum() {
		return _minimum;
	}

	/**
	 * Returns the maximum element of this range.
	 * 
	 * @return the maximum element of this range
	 */
	public final T getMaximum() {
		return _maximum;
	}

	/**
	 * Indicates whether the given object is contained in this range.
	 * 
	 * @param obj
	 *            the object to be tested
	 * @return true if this object is non-<code>null</code> and contained in
	 *         this range; <code>false</code> otherwise
	 */
	public final boolean contains(final T obj) {
		return ((obj == null) ? false : //
				(((_minimum == null) || (MiscUtils.compare(_minimum, obj) <= 0)) //
				&& ((_maximum == null) || (MiscUtils.compare(obj, _maximum) <= 0))));
	}

	/**
	 * Vets the given object.
	 * 
	 * @param obj
	 *            the object to be vetted
	 * @return the minimum value in this range if the given object is
	 *         <code>null</code>; this object, if it is non-<code>null</code>
	 *         and contained in this range; or, the closest value in this range
	 *         if the given value is non-<code>null</code> and not contained in
	 *         this range
	 */
	public final T vet(final T obj) {
		return ((obj == null) ? _minimum : (contains(obj) ? obj : ((MiscUtils.compare(_minimum, obj) > 0) ? _minimum : _maximum)));
	}

	/** {@inheritDoc} */
	@Override
	public final int hashCode() {
		return new HashCodeBuilder() //
				.append(_minimum) //
				.append(_maximum) //
				.toHashCode();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if ((obj == null) || !(obj instanceof Range<?>)) {
			return false;
		}
		final Range<?> other = (Range<?>) obj;
		return new EqualsBuilder() //
				.append(_minimum, other._minimum) //
				.append(_maximum, other._maximum) //
				.isEquals();
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return _minimum != null && _minimum.equals(_maximum) ? stringifyEnd(_minimum) : //
				String.format("[%s .. %s]", stringifyEnd(_minimum), stringifyEnd(_maximum));
	}

	/**
	 * Returns the string form of an end of this range.
	 * 
	 * @param end
	 *            the end to stringify
	 * @return the string form of an end of this range
	 */
	private final String stringifyEnd(final T end) {
		return ((end == null) ? OPEN_END : String.valueOf(end));
	}
}
