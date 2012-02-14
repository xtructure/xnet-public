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
package com.xtructure.xutil.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.coll.XIdObjectTransform;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * A base implementation of {@link XConfiguration}.
 * 
 * @param <S>
 *            this type
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * @version 0.9.6
 */
public abstract class AbstractXConfiguration<S extends AbstractXConfiguration<S>> extends AbstractXIdObject implements XConfiguration {
	/** The id of the parent of this configuration. */
	private final XId						_parentId;
	/** The manager of instances of this type of configuration. */
	private final XIdObjectManager<S>		_manager;
	/** The parameters of this configuration. */
	private final Map<XId, XParameter<?>>	_parameters;

	/**
	 * Creates a new configuration.
	 * 
	 * @param id
	 *            the id of this configuration
	 * @param parentId
	 *            the id of the parent of this configuration
	 * @param manager
	 *            the manager of instances of this type of configuration
	 * @param parameters
	 *            the parameters of this configuration
	 * @throws IllegalArgumentException
	 *             if the given id is <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	protected AbstractXConfiguration(final XId id, final XId parentId, final XIdObjectManager<S> manager, final Collection<XParameter<?>> parameters) {
		super(id);
		_parentId = parentId;
		_manager = manager;
		_parameters = new MapBuilder<XId, XParameter<?>>()//
				.putAll(XIdObjectTransform.getInstance(), parameters)//
				.newImmutableInstance();
		if (_manager != null) {
			_manager.register((S) this);
		}
	}

	/**
	 * Creates a new configuration.
	 * 
	 * @param id
	 *            the id of this configuration
	 * @param parameters
	 *            the parameters of this configuration
	 * @throws IllegalArgumentException
	 *             if the given id is <code>null</code>
	 */
	protected AbstractXConfiguration(final XId id, final Collection<XParameter<?>> parameters) {
		this(id, null, null, parameters);
	}

	/** {@inheritDoc} */
	@Override
	public final XId getParentId() {
		return _parentId;
	}

	/** {@inheritDoc} */
	@Override
	public final Set<XId> getParameterIds() {
		return Collections.unmodifiableSet(_parameters.keySet());
	}

	/** {@inheritDoc} */
	@Override
	public final XParameter<?> getParameter(final XId id) {
		return _parameters.get(id);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " id=" + getId().toString();
	}

	/**
	 * A base class for the XML format of a configuration.
	 * 
	 * @param <T>
	 *            the type associated with this format
	 */
	protected static abstract class AbstractXmlFormat<T extends AbstractXConfiguration<T>> extends XmlFormat<T> {
		protected static final Attribute<XId>			ID_ATTRIBUTE		= XmlUnit.newAttribute("id", XId.class);
		protected static final Attribute<XId>			PARENT_ID_ATTRIBUTE	= XmlUnit.newAttribute("parentId", XId.class);
		protected static final Element<XParameter<?>>	PARAMETER_ELEMENT	= XmlUnit.newElement("parameter");

		protected AbstractXmlFormat(Class<T> cls) {
			super(cls);
			addRecognized(ID_ATTRIBUTE);
			addRecognized(PARENT_ID_ATTRIBUTE);
			addRecognized(PARAMETER_ELEMENT);
		}

		@Override
		protected void writeAttributes(T obj, OutputElement xml) throws XMLStreamException {
			ID_ATTRIBUTE.write(xml, obj.getId());
			if (obj.getParentId() != null) {
				PARENT_ID_ATTRIBUTE.write(xml, obj.getParentId());
			}
		}

		@Override
		protected void writeElements(T obj, OutputElement xml) throws XMLStreamException {
			List<XId> parameterIds = new ArrayList<XId>(obj.getParameterIds());
			Collections.sort(parameterIds);
			for (XId parameterId : parameterIds) {
				PARAMETER_ELEMENT.write(xml, obj.getParameter(parameterId));
			}
		}

		protected Collection<XParameter<?>> collectParameters(ReadElements readElements, XId... parameterIds) {
			Map<XId, XParameter<?>> parameterMap = new MapBuilder<XId, XParameter<?>>()//
					.putAll(XIdObjectTransform.getInstance(), readElements.getValues(PARAMETER_ELEMENT))//
					.newImmutableInstance();
			List<XParameter<?>> parameters = new ArrayList<XParameter<?>>();
			for (XId parameterId : parameterIds) {
				if (parameterMap.containsKey(parameterId)) {
					parameters.add(parameterMap.get(parameterId));
				}
			}
			return parameters;
		}
	}
}
