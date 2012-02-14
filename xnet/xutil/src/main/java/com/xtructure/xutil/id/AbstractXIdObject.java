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

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * A base implementation of {@link XIdObject}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractXIdObject implements XIdObject {
	/** The id of this object. */
	private final XId	_id;

	/**
	 * Creates a new object.
	 * 
	 * @param id
	 *            the id of this object
	 * @param manager
	 *            the manager with which to register this object, if any
	 * @throws IllegalArgumentException
	 *             if the given id is <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	protected <S extends AbstractXIdObject> AbstractXIdObject(final XId id, final XIdObjectManager<S> manager) {
		super();
		if(id == null){
			throw new IllegalArgumentException("id must not be null");
		}
		_id = id;
		if (manager != null) {
			manager.register((S) this);
		}
	}

	/**
	 * Creates a new object.
	 * 
	 * @param id
	 *            the id of this object
	 * @throws IllegalArgumentException
	 *             if the given id is <code>null</code>
	 */
	protected AbstractXIdObject(final XId id) {
		this(id, null);
	}

	/** {@inheritDoc} */
	@Override
	public XId getId() {
		return _id;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(final XIdObject other) {
		if (other == this) {
			return 0;
		}
		if (other == null) {
			return 1;
		}
		return _id.compareTo(other.getId());
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return _id.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if ((obj == null) || !(obj instanceof XIdObject)) {
			return false;
		}
		return _id.equals(((XIdObject) obj).getId());
	}

	/** The base XML format of {@link XIdObject} instances. */
	protected static abstract class AbstractXmlFormat<T extends AbstractXIdObject> extends XmlFormat<T> {
		protected static final Attribute<XId>	ID_ATTRIBUTE	= XmlUnit.newAttribute("id", XId.class);

		protected AbstractXmlFormat(Class<T> cls) {
			super(cls);
			addRecognized(ID_ATTRIBUTE);
		}

		@Override
		protected void writeAttributes(T obj, OutputElement xml) throws XMLStreamException {
			ID_ATTRIBUTE.write(xml, obj.getId());
		}
	}
}
