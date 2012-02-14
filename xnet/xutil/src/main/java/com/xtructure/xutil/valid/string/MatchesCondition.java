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
package com.xtructure.xutil.valid.string;

import java.util.regex.Pattern;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.AbstractValueCondition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A pattern that ensures that a string matches a particular regular expression.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * @version 0.9.4
 */
public class MatchesCondition extends AbstractValueCondition<String> {
	/** xml format instance for IsSameAsCondition */
	public static final XmlFormat<MatchesCondition>	XML_FORMAT	= new ConditionXmlFormat();

	/**
	 * Creates a condition that is satisfied by a string matching a particular
	 * regular expression.
	 * 
	 * @param regex
	 *            the regular expression to match
	 * @throws IllegalArgumentException
	 *             if the given regex is <code>null</code>
	 */
	public MatchesCondition(String regex) {
		super(regex, false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if the given object is a {@link CharSequence} and matches
	 *         this condition's regular expression, false otherwise
	 */
	@Override
	public final boolean isSatisfiedBy(final Object object) {
		if (object != null && object instanceof CharSequence) {
			return Pattern.compile(getValue()).matcher(object.toString()).matches();
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("must match %s", getValue());
	}

	/** implementation of an xml format for IsSameAsCondition */
	private static final class ConditionXmlFormat extends AbstractXmlFormat<MatchesCondition> {
		protected ConditionXmlFormat() {
			super(MatchesCondition.class);
		}

		@Override
		protected MatchesCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new MatchesCondition(readElements.getValue(VALUE_ELEMENT).toString());
		}

		@Override
		protected void writeAttributes(MatchesCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
