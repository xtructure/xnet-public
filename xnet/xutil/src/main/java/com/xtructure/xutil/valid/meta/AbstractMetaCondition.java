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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * A base implementation for meta conditions.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractMetaCondition implements Condition {
	/** the list of conditions used by this meta condition */
	private final List<Condition>	conditions;

	/**
	 * Creates a new meta condition that is satisfied objects depending on how
	 * they cause the given conditions to behave
	 * 
	 * @param conditions
	 *            the conditions used by this meta condition
	 * @throws IllegalArgumentException
	 *             if any of the given conditions are null
	 */
	protected AbstractMetaCondition(Condition... conditions) throws IllegalArgumentException {
		this(Arrays.asList(conditions));
	}

	/**
	 * Creates a new meta condition that is satisfied objects depending on how
	 * they cause the given conditions to behave
	 * 
	 * @param conditions
	 *            the conditions used by this meta condition
	 * @throws IllegalArgumentException
	 *             if the given list is null or any of its conditions are null
	 */
	protected AbstractMetaCondition(List<Condition> conditions) throws IllegalArgumentException {
		if (conditions == null) {
			throw new IllegalArgumentException("conditions: must not be null");
		}
		for (Condition condition : conditions) {
			if (condition == null) {
				throw new IllegalArgumentException("condition: must not be null");
			}
		}
		this.conditions = Collections.unmodifiableList(new ArrayList<Condition>(conditions));
	}

	/**
	 * Returns a specific condition used by this meta condition
	 * 
	 * @param index
	 *            the index in this meta condition's list of conditions to get
	 * @return the specified condition
	 */
	public Condition getCondition(int index) {
		return conditions.get(index);
	}

	/**
	 * Returns the list of conditions used by this meta condition
	 * 
	 * @return the list of conditions used by this meta condition
	 */
	public List<Condition> getConditions() {
		return conditions;
	}

	/** base implementation of an xml format for AbstractMetaCondition */
	protected static abstract class AbstractXmlFormat<P extends AbstractMetaCondition> extends XmlFormat<P> {
		protected static final Element<Condition>	PREDICATE_ELEMENT	= XmlUnit.newElement("condition");

		protected AbstractXmlFormat(Class<P> cls) {
			super(cls);
			addRecognized(PREDICATE_ELEMENT);
		}

		@Override
		protected void writeElements(P object, OutputElement xml) throws XMLStreamException {
			for (Condition condition : object.getConditions()) {
				PREDICATE_ELEMENT.write(xml, condition);
			}
		}
	}
}
