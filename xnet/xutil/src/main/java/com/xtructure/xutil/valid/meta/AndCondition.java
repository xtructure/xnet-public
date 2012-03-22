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

import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * A condition that logically conjuncts the results of other condition.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public final class AndCondition extends AbstractMetaCondition {
	/** xml format instance for AndCondition */
	public static final XmlFormat<AndCondition>	XML_FORMAT	= new AndXmlFormat();

	/**
	 * Creates a condition that logically conjuncts the results of other
	 * conditions
	 * 
	 * @param condition1
	 *            the first condition to check
	 * @param condition2
	 *            the second condition to check
	 * @throws IllegalArgumentException
	 *             if any of the given conditions are <code>null</code>
	 */
	public AndCondition(Condition condition1, Condition condition2) throws IllegalArgumentException {
		this(//
				new ListBuilder<Condition>()//
						.add(condition1, condition2)//
						.newInstance());
	}

	/**
	 * Creates a condition that logically conjuncts the results of other
	 * conditions
	 * 
	 * @param condition1
	 *            the first condition to check
	 * @param condition2
	 *            the second condition to check
	 * @param conditions
	 *            the other conditions to check
	 * @throws IllegalArgumentException
	 *             if any of the given conditions are <code>null</code>
	 */
	public AndCondition(Condition condition1, Condition condition2, Condition... conditions) throws IllegalArgumentException {
		this(//
				new ListBuilder<Condition>()//
						.add(condition1, condition2)//
						.addAll(conditions)//
						.newInstance());
	}

	/**
	 * Creates a condition that logically conjuncts the results of other
	 * conditions
	 * 
	 * @param conditions
	 *            the list of conditions to check
	 * @throws IllegalArgumentException
	 *             if the given list is null or if any of the its conditions are
	 *             <code>null</code>
	 */
	public AndCondition(List<Condition> conditions) throws IllegalArgumentException {
		super(conditions);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return true if all of this meta condition's conditions are satisfied,
	 *         false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Object object) {
		for (Condition conditions : getConditions()) {
			if (!conditions.isSatisfiedBy(object)) {
				return false;
			}
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder().append("(");
		sb.append(getCondition(0));
		for (int i = 1; i < getConditions().size(); i++) {
			sb.append(" and ").append(getCondition(i));
		}
		return sb.append(")").toString();
	}

	/** implementation of an xml format for AndCondition */
	private static final class AndXmlFormat extends AbstractXmlFormat<AndCondition> {
		protected AndXmlFormat() {
			super(AndCondition.class);
		}

		@Override
		protected AndCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			List<Condition> conditions = readElements.getValues(PREDICATE_ELEMENT);
			if (conditions.size() < 2) {
				throw new XMLStreamException("AndCondition must have at least 2 conditions");
			}
			return new AndCondition(//
					conditions.get(0),//
					conditions.get(1),//
					conditions.subList(2, conditions.size()).toArray(new Condition[0]));
		}

		@Override
		protected void writeAttributes(AndCondition object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
