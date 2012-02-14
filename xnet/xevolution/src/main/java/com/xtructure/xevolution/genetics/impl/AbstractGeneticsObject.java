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

import com.xtructure.xevolution.genetics.GeneticsObject;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * {@link AbstractGeneticsObject} implements the {@link GeneticsObject}
 * interface, providing logged access to the object's attributes.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractGeneticsObject extends AbstractXIdObject implements GeneticsObject {
	/** the attributes for this {@link GeneticsObject} */
	private final ValueMap	attributes	= new ValueMap();

	/**
	 * Creates a new {@link AbstractGeneticsObject}
	 * 
	 * @param id
	 *            the id of this {@link AbstractGeneticsObject}
	 */
	protected AbstractGeneticsObject(XId id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsObject#getAttribute(com.xtructure
	 * .xutil.id.ValueId)
	 */
	@Override
	public <V> V getAttribute(XValId<V> valueId) {
		getLogger().trace("begin %s.getAttribute(%s)", getClass().getSimpleName(), valueId);
		V rVal = attributes.get(valueId);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getAttribute()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#getAttributes()
	 */
	@Override
	public ValueMap getAttributes() {
		getLogger().trace("begin %s.getAttributes()", getClass().getSimpleName());
		ValueMap rVal = attributes;
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getAttributes()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#getAge()
	 */
	@Override
	public long getAge() {
		return getAttribute(AGE_ATTRIBUTE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#incrementAge()
	 */
	@Override
	public void incrementAge() {
		setAttribute(AGE_ATTRIBUTE_ID, getAge() + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.EvolutionObject#getLogger()
	 */
	@Override
	public final XLogger getLogger() {
		return XLogger.getInstance(getClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsObject#setAttribute(com.xtructure
	 * .xutil.id.ValueId, java.lang.Comparable)
	 */
	@Override
	public <V> V setAttribute(XValId<V> valueId, V value) {
		getLogger().trace("begin %s.setAttribute(%s, %s)", getClass().getSimpleName(), valueId, value);
		V rVal = attributes.set(valueId, value);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.setAttribute()", getClass().getSimpleName());
		return rVal;
	}

	/** xml format for {@link AbstractGeneticsObject}s */
	protected static abstract class AbstractXmlFormat<O extends AbstractGeneticsObject> extends AbstractXIdObject.AbstractXmlFormat<O> {
		protected static final Element<ValueMap>	ATTRIBUTES_ELEMENT	= XmlUnit.newElement("attributes", ValueMap.class);

		protected AbstractXmlFormat(Class<O> cls) {
			super(cls);
			addRecognized(ATTRIBUTES_ELEMENT);
		}

		@Override
		protected void writeElements(O obj, OutputElement xml) throws XMLStreamException {
			if (obj.attributes.size() > 0) {
				ATTRIBUTES_ELEMENT.write(xml, obj.attributes);
			}
		}
	}
}
