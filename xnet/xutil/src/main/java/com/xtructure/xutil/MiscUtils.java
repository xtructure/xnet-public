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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Miscellaneous utilities.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.2
 */
public final class MiscUtils {
	/**
	 * Determines if the two given objects are equal in a <code>null</code>-safe
	 * manner.
	 * 
	 * @param obj1
	 *            the first object
	 * 
	 * @param obj2
	 *            the second object
	 * 
	 * @return <code>true</code> if the given objects are equal;
	 *         <code>false</code> otherwise
	 */
	public static final boolean equals(final Object obj1, final Object obj2) {
		return ((obj1 == null) ? (obj2 == null) : ((obj1 == obj2) ? true : obj1.equals(obj2)));
	}

	/**
	 * Compares two objects in a <code>null</code>-safe manner.
	 * 
	 * @param <T>
	 *            the type of objects to compare
	 * 
	 * @param obj1
	 *            the first object
	 * 
	 * @param obj2
	 *            the second object
	 * 
	 * @return an integer less than, equal to, or greater than 1 indicating that
	 *         the first given object is less than, equal to, or greater than
	 *         the second
	 */
	public static final <T extends Comparable<T>> int compare(final T obj1, final T obj2) {
		return ((obj1 == null) ? ((obj2 == null) ? 0 // obj1 == obj2 (== null)
				: -1) // obj1 == null, obj2 != null
				: ((obj2 == null) ? 1 // obj1 != null, obj2 == null
						: ((obj1 == obj2) ? 0 // obj1 == obj2 (!= null)
								: obj1.compareTo(obj2))));
	}

	/**
	 * Returns the minimum member of the given collection in a <code>null</code>
	 * -safe manner.
	 * 
	 * @param <T>
	 *            the type of member to be compared
	 * 
	 * @param coll
	 *            the collection of members to interrogate
	 * 
	 * @return the minimum member of the given collection
	 * 
	 * @throws IllegalArgumentException
	 *             if the given collection is <code>null</code>
	 */
	public static final <T extends Comparable<T>> T min(final Collection<T> coll) {
		validateArg("coll", coll, isNotNull());
		T min = null;
		boolean first = true;
		for (final T member : coll) {
			if (first) {
				min = member;
				first = false;
			} else if (compare(member, min) < 0) {
				min = member;
			}
		}
		return min;
	}

	/**
	 * Returns the maximum member of the given collection in a <code>null</code>
	 * -safe manner.
	 * 
	 * @param <T>
	 *            the type of member to be compared
	 * 
	 * @param coll
	 *            the collection of members to interrogate
	 * 
	 * @return the maximum member of the given collection
	 * 
	 * @throws IllegalArgumentException
	 *             if the given collection is <code>null</code>
	 */
	public static final <T extends Comparable<T>> T max(final Collection<T> coll) {
		validateArg("coll", coll, isNotNull());
		T max = null;
		boolean first = true;
		for (final T member : coll) {
			if (first) {
				max = member;
				first = false;
			} else if (compare(member, max) > 0) {
				max = member;
			}
		}
		return max;
	}

	/**
	 * Returns a formatted string, if possible.
	 * 
	 * @param defaultString
	 *            the default string to return if the formatting fails
	 * 
	 * @param format
	 *            the format of the string to return
	 * 
	 * @param args
	 *            the arguments to the string to be returned
	 * 
	 * @return a formatted string, or the given default if formatting fails
	 */
	public static final String format(final String defaultString, final String format, final Object... args) {
		if (format == null) {
			return defaultString;
		}
		try {
			return String.format(format, args);
		} catch (IllegalFormatException formatEx) {
			return defaultString;
		}
	}

	/**
	 * Reads an input and returns its contents as a string.
	 * 
	 * @param in
	 *            the input to be read
	 * @return the contents of the given input
	 */
	public static final String read(final Reader in) {
		if (in == null) {
			return null;
		}
		final BufferedReader bufferedIn = new BufferedReader(in);
		List<String> lines = new LinkedList<String>();
		while (true) {
			try {
				final String line = bufferedIn.readLine();
				if (line == null) {
					break;
				}
				lines.add(line);
			} catch (IOException ioEx) {
				lines.add(ioEx.getMessage());
				break;
			}
		}
		StringBuilder tmp = new StringBuilder();
		Iterator<String> iter = lines.iterator();
		while (iter.hasNext()) {
			tmp.append(iter.next());
			if (iter.hasNext()) {
				tmp.append("\n");
			}
		}
		return tmp.toString();
	}

	/**
	 * Reads an input and returns its contents as a string.
	 * 
	 * @param in
	 *            the input to be read
	 * 
	 * @return the contents of the given input
	 */
	public static final String read(final InputStream in) {
		return read(new InputStreamReader(in));
	}

	/** Creates a miscellaneous utility. */
	private MiscUtils() {
		super();
	}
}
