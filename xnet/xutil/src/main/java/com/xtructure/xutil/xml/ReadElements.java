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
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * Collects elements as they are read from an xml {@link InputElement}.
 * 
 * @author Luis Guimbarda
 * 
 */
public class ReadElements {
	/** map of elements read */
	private final Map<String, List<Object>>	gottenElements;

	/**
	 * Creates a new {@link ReadElements} instance.
	 * 
	 * @param xml
	 *            the {@link InputElement} from which elements are read
	 * @param recognizedElements
	 *            manager of elements to look for
	 * @throws XMLStreamException
	 *             if an error occurred while reading elements or an
	 *             unrecognized element is encountered
	 */
	public ReadElements(InputElement xml, XIdObjectManager<Element<?>> recognizedElements) throws XMLStreamException {
		this.gottenElements = new HashMap<String, List<Object>>();
		for (XId id : recognizedElements.getIds()) {
			gottenElements.put(id.getBase(), new ArrayList<Object>());
		}
		NEXT_READ: while (xml.hasNext()) {
			for (XId id : recognizedElements.getIds()) {
				Element<?> element = recognizedElements.getObject(id);
				Object val = element.read(xml, null);
				if (val != null) {
					gottenElements.get(element.getName()).add(val);
					continue NEXT_READ;
				}
			}
			// no element matched xml
			throw new XMLStreamException(String.format("unrecognized element: %s (%s)", xml.getStreamReader().getLocalName(), xml.getStreamReader().getLocation()));
		}
	}

	/**
	 * Returns the first element read for the given {@link Element}
	 * 
	 * @param element
	 *            the {@link Element} whose data is being looked up
	 * @return the first read element pointed to by the given {@link Element},
	 *         or null if no such elements were read
	 */
	@SuppressWarnings("unchecked")
	public <V> V getValue(Element<V> element) {
		List<Object> es = gottenElements.get(element.getName());
		return es == null || es.isEmpty() ? null : (V) es.get(0);
	}

	/**
	 * Returns a list of all elements read for the given {@link Element}
	 * 
	 * @param element
	 *            the {@link Element} whose data is being looked up
	 * @return the list of read elements pointed to by the given {@link Element}
	 *         , or null if no such elements were read
	 */
	@SuppressWarnings("unchecked")
	public <V> List<V> getValues(Element<V> element) {
		List<Object> es = gottenElements.get(element.getName());
		if (es == null) {
			return null;
		}
		if (element.getType() != null) {
			validateState("elements", es, everyElement(isOfCompatibleType(element.getType())));
		}
		return Collections.unmodifiableList((List<V>) es);
	}
}
