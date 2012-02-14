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
package com.xtructure.xutil.valid.meta;

import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A condition that logically negates the result of another condition.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public final class NotCondition extends AbstractMetaCondition {
	/** xml format instance for NotCondition */
	public static final XmlFormat<NotCondition>	XML_FORMAT	= new NotXmlFormat();

	/**
	 * Creates a condition that logically negates the result of another
	 * condition.
	 * 
	 * @param condition
	 *            the condition to be negated
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public NotCondition(final Condition condition) {
		super(condition);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if this meta condition's condition is not satisfied, false
	 *         otherwise
	 */
	@Override
	public final boolean isSatisfiedBy(final Object object) {
		return !getCondition(0).isSatisfiedBy(object);
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("(not: %s)", getCondition(0));
	}

	/** implementation of an xml format for NotCondition */
	private static final class NotXmlFormat extends AbstractXmlFormat<NotCondition> {
		protected NotXmlFormat() {
			super(NotCondition.class);
		}

		@Override
		protected NotCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			List<Condition> conditions = readElements.getValues(PREDICATE_ELEMENT);
			if (conditions.size() != 1) {
				throw new XMLStreamException("NotCondition must have exactly 1 condition");
			}
			return new NotCondition(conditions.get(0));
		}

		@Override
		protected void writeAttributes(NotCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
