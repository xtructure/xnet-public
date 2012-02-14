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
package com.xtructure.xutil.xml;

import static com.xtructure.xutil.valid.ValidateUtils.everyElement;
import static com.xtructure.xutil.valid.ValidateUtils.isOfCompatibleType;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javolution.xml.XMLFormat.InputElement;
import javolution.xml.sax.Attributes;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * Collects attributes as they are read from an xml {@link InputElement}.
 * 
 * @author Luis Guimbarda
 * 
 */
public class ReadAttributes {
	/** map of attributes read */
	private final Map<String, List<Object>>	gottenAttributes;

	/**
	 * Creates a new {@link ReadAttributes} instance.
	 * 
	 * @param xml
	 *            the {@link InputElement} from which attributes are read
	 * @param attributes
	 *            {@link XIdObjectManager} of attributes to look for
	 * @throws XMLStreamException
	 *             if an error occurred while reading attributes or an
	 *             unrecognized attribute is encountered
	 */
	public ReadAttributes(InputElement xml, XIdObjectManager<Attribute<?>> attributes) throws XMLStreamException {
		this.gottenAttributes = new HashMap<String, List<Object>>();
		for (XId id : attributes.getIds()) {
			gottenAttributes.put(id.getBase(), new ArrayList<Object>());
		}
		Attributes xs = xml.getAttributes();
		for (int i = 0; i < xs.getLength(); i++) {
			String qname = xs.getQName(i).toString();
			String value = xs.getValue(i).toString();
			if (!gottenAttributes.containsKey(qname)) {
				if (!qname.equals("class")) {
					throw new XMLStreamException(String.format("unrecognized attribute : %s=%s", qname, value));
				}
			} else {
				Attribute<?> attribute = attributes.getObject(XId.newId(qname));
				Object object = attribute.parse(value, null);
				if (object != null) {
					gottenAttributes.get(qname).add(object);
				}
			}
		}
	}

	/**
	 * Returns the first attribute read for the given {@link Attribute}
	 * 
	 * @param attribute
	 *            the {@link Attribute} whose data is being looked up
	 * @return the first read attribute pointed to by the given
	 *         {@link Attribute}, or null if no such attributes were read
	 */
	@SuppressWarnings("unchecked")
	public <V> V getValue(Attribute<V> attribute) {
		List<Object> as = gottenAttributes.get(attribute.getName());
		return as == null || as.isEmpty() ? null : (V) as.get(0);
	}

	/**
	 * Returns a list of all attributes read for the given {@link Attribute}
	 * 
	 * @param attribute
	 *            the {@link Attribute} whose data is being looked up
	 * @return the list of read attributes pointed to by the given
	 *         {@link Attribute} , or null if no such attributes were read
	 */
	@SuppressWarnings("unchecked")
	public <V> List<V> getValues(Attribute<V> attribute) {
		List<Object> as = gottenAttributes.get(attribute.getName());
		if (as == null) {
			return null;
		}
		if (attribute.getType() != null) {
			validateState("attributes", as, everyElement(isOfCompatibleType(attribute.getType())));
		}
		return Collections.unmodifiableList((List<V>) as);
	}
}
