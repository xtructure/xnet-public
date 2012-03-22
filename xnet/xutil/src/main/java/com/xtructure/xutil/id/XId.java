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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import javolution.text.CharSet;
import javolution.text.Cursor;
import javolution.text.TypeFormat;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.xtructure.xutil.WormField;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.format.XTextFormat;

/**
 * An identifier with hierarchical information in the form of a trailing list of
 * instance numbers.
 * <P>
 * Where "EXAMPLE:[0,1,2]" is an id for some object, its parent's id would be
 * "EXAMPLE:[0,1]", one of its sibling's id might be "EXAMPLE:[0,1,5]", and one
 * of its child's id might be "EXAMPLE:[0,1,2,88]".
 * <P>
 * "EXAMPLE" is still a valid id, representing some root object of the
 * hierarchy.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * 
 */
public class XId implements Comparable<XId> {
	/** The text format of {@link XId} instances. */
	public static final XTextFormat<XId>	TEXT_FORMAT	= new TextFormat();
	/** The delimiter for {@link XId} text format. */
	public static final char				DELIM		= ':';
	/** The regex describing acceptable {@link XId} base strings */
	private static final String				BASE_REGEX	= String.format(//
																"[%s&&[^%s]]+",//
																"\\w\\s~@#&`',;:\"\\Q<([{\\^-=$!|]})?*+.>\\E",//
																DELIM);

	/**
	 * Returns a new {@link XId} with the random UUID derived base string
	 * 
	 * @return the new {@link XId}
	 */
	public static XId newId() {
		return new XId(UUID.randomUUID().toString());
	}

	/**
	 * Returns a new {@link XId} with the random UUID derived base string and
	 * the given instanceNums
	 * 
	 * @param instanceNums
	 *            the instance numbers of the new {@link XId}
	 * @return the new {@link XId}
	 * @throws IllegalArgumentException
	 *             if instanceNums is null or contains any nulls
	 */
	public static XId newId(Integer... instanceNums) throws IllegalArgumentException {
		return new XId(UUID.randomUUID().toString(), instanceNums);
	}

	/**
	 * Returns a new {@link XId} with the random UUID derived base string and
	 * the given instanceNums
	 * 
	 * @param instanceNums
	 *            the instance numbers of the new {@link XId}
	 * @return the new {@link XId}
	 * @throws IllegalArgumentException
	 *             if instanceNums is null or contains any nulls
	 */
	public static XId newId(List<Integer> instanceNums) throws IllegalArgumentException {
		return new XId(UUID.randomUUID().toString(), instanceNums);
	}

	/**
	 * Returns a new {@link XId} with the given idString
	 * 
	 * @param idString
	 *            the base string with which to create the new {@link XId}
	 * @return the new {@link XId}
	 * @throws IllegalArgumentException
	 *             if base does not match {@link #BASE_REGEX}
	 */
	public static XId newId(String idString) throws IllegalArgumentException {
		return new XId(idString);
	}

	/**
	 * Returns a new {@link XId} with the given idString and instanceNums
	 * 
	 * @param idString
	 *            the base string with which to create the new {@link XId}
	 * @param instanceNums
	 *            the instance numbers of the new {@link XId}
	 * @return the new {@link XId}
	 * @throws IllegalArgumentException
	 *             if base does not match {@link #BASE_REGEX} or if instanceNums
	 *             is null or contains any nulls
	 */
	public static XId newId(String idString, Integer... instanceNums) throws IllegalArgumentException {
		return new XId(idString, instanceNums);
	}

	/**
	 * Returns a new {@link XId} with the given idString and instanceNums
	 * 
	 * @param idString
	 *            the base string with which to create the new {@link XId}
	 * @param instanceNums
	 *            the instance numbers of the new {@link XId}
	 * @return the new {@link XId}
	 * @throws IllegalArgumentException
	 *             if base does not match {@link #BASE_REGEX} or if instanceNums
	 *             is null or contains any nulls
	 */
	public static XId newId(String idString, List<Integer> instanceNums) throws IllegalArgumentException {
		return new XId(idString, instanceNums);
	}

	/** base string of this {@link XId} */
	private final String			base;
	/** string representation of this {@link XId} */
	private final WormField<String>	toString	= new WormField<String>();
	/** list of this id's instance numbers */
	private final List<Integer>		instanceNums;

	/**
	 * Creates a new {@link XId} with the given base string and the given
	 * instanceNums
	 * 
	 * @param base
	 *            the base string with which to create the new {@link XId}
	 * @param instanceNums
	 *            the instance numbers of the new {@link XId}
	 * @throws IllegalArgumentException
	 *             if base does not match {@link #BASE_REGEX} or if instanceNums
	 *             is null or contains any nulls
	 */
	protected XId(String base, Integer... instanceNums) throws IllegalArgumentException {
		this(base, Arrays.asList(instanceNums));
	}

	/**
	 * Creates a new {@link XId} with the given base string and the given
	 * instanceNums
	 * 
	 * @param base
	 *            the base string with which to create the new {@link XId}
	 * @param instanceNums
	 *            the instance numbers of the new {@link XId}
	 * @throws IllegalArgumentException
	 *             if base does not match {@link #BASE_REGEX} or if instanceNums
	 *             is null or contains any nulls
	 */
	protected XId(String base, List<Integer> instanceNums) throws IllegalArgumentException {
		if (base == null) {
			throw new IllegalArgumentException("base must not be null");
		}
		if (instanceNums == null) {
			throw new IllegalArgumentException("instanceNums must not be null");
		}
		if (!Pattern.matches(BASE_REGEX, base)) {
			throw new IllegalArgumentException(String.format("%s must match regex %s", base, BASE_REGEX));
		}
		for (Integer i : instanceNums) {
			if (i == null) {
				throw new IllegalArgumentException("instanceNums must not contain nulls");
			}
		}
		this.base = base;
		this.instanceNums = Collections.unmodifiableList(new ArrayList<Integer>(instanceNums));
	}

	/**
	 * Returns the base string for this {@link XId}
	 * 
	 * @return the base string for this {@link XId}
	 */
	public String getBase() {
		return base;
	}

	private static final ReentrantLock WORM_LOCK = new ReentrantLock();
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		WORM_LOCK.lock();
		try {
			if (!toString.isInitialized()) {
				toString.initValue(TextFormat.getDefault(getClass()).format(this).toString());
			}
			return toString.getValue();
		} finally {
			WORM_LOCK.unlock();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(XId o) {
		if (o == null) {
			return 1;
		}
		return new CompareToBuilder()//
		.append(base, o.base) //
		.append(instanceNums.toArray(), o.instanceNums.toArray()) //
		.toComparison();
	}

	/**
	 * Returns the instance numbers of this identifier.
	 * 
	 * @return the instance numbers of this identifier
	 */
	public List<Integer> getInstanceNums() {
		return instanceNums;
	}

	/**
	 * Returns the last instance number of this identifier.
	 * 
	 * @return the last instance number of this identifier
	 */
	public Integer getInstanceNum() {
		return (instanceNums.isEmpty() ? null : instanceNums.get(instanceNums.size() - 1));
	}

	/**
	 * Indicates whether this identifier is an ancestor of the given identifier.
	 * 
	 * @param other
	 *            the possible descendent
	 * @return <code>true</code> if this identifier is an ancestor of the given
	 *         identifier; <code>false</code> otherwise
	 * @throws IllegalArgumentException
	 *             if the given object is <code>null</code>
	 */
	public boolean isAncestorOf(final XId other) {
		if (other == null) {
			throw new IllegalArgumentException("other must not be null");
		}
		if (!getBase().equals(other.getBase())) {
			return false;
		}
		if (instanceNums.size() >= other.instanceNums.size()) {
			return false;
		}
		for (int index = 0; index < instanceNums.size(); index++) {
			if (!instanceNums.get(index).equals(other.instanceNums.get(index))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Indicates whether this identifier is a descendant of the given
	 * identifier.
	 * 
	 * @param other
	 *            the possible ancestor
	 * @return <code>true</code> if this identifier is a descendant of the given
	 *         identifier; <code>false</code> otherwise
	 * @throws IllegalArgumentException
	 *             if the given object is <code>null</code>
	 */
	public boolean isDescendentOf(final XId other) {
		if (other == null) {
			throw new IllegalArgumentException("other must not be null");
		}
		return other.isAncestorOf(this);
	}

	/**
	 * Creates a child of this identifier.
	 * 
	 * @param newInstanceNum
	 *            the instance number of the identifier to be returned
	 * @return a child identifier of this identifier
	 */
	public XId createChild(final int newInstanceNum) {
		return new XId(//
				getBase(),//
				new ListBuilder<Integer>()//
						.addAll(instanceNums)//
						.add(newInstanceNum)//
						.newImmutableInstance());
	}

	/**
	 * Creates the parent of this identifier.
	 * 
	 * @return the parent identifier of this identifier, or null if it has no
	 *         parent
	 */
	public XId createParent() {
		if (instanceNums.isEmpty()) {
			return null;
		}
		return new XId(//
				getBase(),//
				instanceNums.subList(0, instanceNums.size() - 1));
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && obj instanceof XId) {
			XId that = (XId) obj;
			return new EqualsBuilder()//
					.append(this.getBase(), that.getBase())//
					.append(this.instanceNums, that.instanceNums)//
					.isEquals();
		}
		return false;
	}

	/** The text format of {@link XId} instances. */
	private static final class TextFormat extends XTextFormat<XId> {
		private static final CharSet	ARRAY_BITS	= CharSet.valueOf('[', ',', ' ', ']');

		protected TextFormat() {
			super(XId.class);
		}

		@Override
		public Appendable format(XId id, Appendable appendable) throws IOException {
			if (id.getInstanceNums().isEmpty()) {
				return appendable.append(id.getBase());
			} else {
				return appendable.append(String.format("%s%c%s", id.getBase(), DELIM, id.getInstanceNums()));
			}
		}

		@Override
		public XId parse(CharSequence chars, Cursor cursor) throws IllegalArgumentException {
			String base = cursor.nextToken(chars, DELIM).toString();
			cursor.skip(DELIM, chars);
			List<Integer> instanceNums = new ArrayList<Integer>();
			while (!cursor.atEnd(chars)) {
				CharSequence cs = cursor.nextToken(chars, ARRAY_BITS);
				instanceNums.add(TypeFormat.parseInt(cs));
				cursor.skipAny(ARRAY_BITS, chars);
			}
			return new XId(base, instanceNums);
		}
	}
}
