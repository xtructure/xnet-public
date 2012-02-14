/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.genetics.impl;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;

public class DummyGeneticsObject extends AbstractGeneticsObject {
	static {
		new AbstractXmlFormat<DummyGeneticsObject>(DummyGeneticsObject.class) {
			@Override
			protected DummyGeneticsObject newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				XId id = readAttributes.getValue(ID_ATTRIBUTE);
				ValueMap attrs = readElements.getValue(ATTRIBUTES_ELEMENT);
				DummyGeneticsObject go = new DummyGeneticsObject(id);
				if (attrs != null) {
					go.getAttributes().setAll(attrs);
				}
				return go;
			}
		};
	}

	protected DummyGeneticsObject(XId id) {
		super(id);
	}

	@Override
	public XId getBaseId() {
		return null;
	}

	@Override
	public void validate() throws IllegalStateException {}
}
