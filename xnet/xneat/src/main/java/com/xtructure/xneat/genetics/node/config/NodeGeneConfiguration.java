/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.genetics.node.config;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.coll.Transform;
import com.xtructure.xutil.config.AbstractConfigurationBuilder;
import com.xtructure.xutil.config.AbstractXConfiguration;
import com.xtructure.xutil.config.DoubleXParameter;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.FieldMapImpl;
import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.config.XParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.id.XIdObjectManagerImpl;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * {@link NodeGeneConfiguration} is an {@link XConfiguration} for
 * {@link NodeGene}s
 * 
 * @author Luis Guimbarda
 * 
 */
public class NodeGeneConfiguration extends AbstractXConfiguration<NodeGeneConfiguration> {
	/** */
	public static final XmlFormat<NodeGeneConfiguration>			XML_FORMAT					= new ConfigurationXmlFormat();
	/** */
	public static final XmlBinding									XML_BINDING					= XmlBinding.builder()//
																										.add(NodeGeneConfiguration.class)//
																										.add(DoubleXParameter.class)//
																										.newInstance();
	/** {@link XLogger} for {@link NodeGeneConfiguration} */
	private static final XLogger									LOGGER						= XLogger.getInstance(NodeGeneConfiguration.class);
	/** manager for {@link NodeGeneConfiguration} */
	private static final XIdObjectManager<NodeGeneConfiguration>	MANAGER						= new XIdObjectManagerImpl<NodeGeneConfiguration>() {};
	/** default {@link XId} for {@link NodeGeneConfiguration}s */
	public static final XId											DEFAULT_CONFIGURATION_ID	= XId.newId("defaultNEATNodeConfiguration");
	/** The next child number to assign. */
	private static final AtomicInteger								NEXT_CHILD_NUM				= new AtomicInteger(0);
	private static final double										ACTIVATION_DEFAULT			= 1.0;
	/** default {@link NodeGeneConfiguration} */
	public static final NodeGeneConfiguration						DEFAULT_CONFIGURATION		= new Builder(DEFAULT_CONFIGURATION_ID).newInstance();

	/**
	 * Returns the {@link XIdObjectManager} for {@link NodeGeneConfiguration}s.
	 * 
	 * @return the {@link XIdObjectManager} for {@link NodeGeneConfiguration}s.
	 */
	public static XIdObjectManager<NodeGeneConfiguration> getManager() {
		return MANAGER;
	}

	/**
	 * Returns a new id suitable for node configurations.
	 * 
	 * @return a new id suitable for node configurations
	 */
	private static XId generateId() {
		return DEFAULT_CONFIGURATION_ID.createChild(NEXT_CHILD_NUM.getAndIncrement());
	}

	public static Builder builder(XId id) {
		return new Builder(id == null ? generateId() : id);
	}

	/**
	 * Creates a new {@link NodeGeneConfiguration} with the given id and
	 * parameters.
	 * 
	 * @param id
	 *            the XId of the new {@link NodeGeneConfiguration}
	 * @param parameters
	 */
	private NodeGeneConfiguration(XId id, Collection<XParameter<?>> parameters) {
		super(id, null, MANAGER, parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xutil.config.XConfiguration#newFieldMap()
	 */
	@Override
	public FieldMap newFieldMap() {
		LOGGER.trace("begin %s.newFieldMap()", getClass().getSimpleName());
		FieldMap rVal = new FieldMapImpl(this);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.newFieldMap()", getClass().getSimpleName());
		return rVal;
	}

	public static final class Builder extends AbstractConfigurationBuilder<NodeGeneConfiguration, Builder> {
		private Builder(XId configurationId) {
			super(configurationId);
		}

		public Builder setActivation(Range<Double> lifetimeRange, Range<Double> initialRange) {
			return setDoubleXParameter(NodeGene.ACTIVATION_ID, NodeGene.ACTIVATION_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		public Builder setActivation(double activation) {
			return setDoubleXParameter(NodeGene.ACTIVATION_ID, NodeGene.ACTIVATION_DESCRIPTION, false, true, activation);
		}

		@Override
		protected Builder addParameter(XParameter<?> parameter) {
			return super.addParameter(parameter);
		}

		@Override
		public NodeGeneConfiguration newInstance() {
			setRemainingToDefaults();
			return new NodeGeneConfiguration(getConfigurationId(), getParameters());
		}

		@Override
		protected void setRemainingToDefaults() {
			if (!isSet(NodeGene.ACTIVATION_ID)) {
				setActivation(Range.getInstance(0.0, 10.0), Range.getInstance(ACTIVATION_DEFAULT));
			}
		}
	}

	private static final class ConfigurationXmlFormat extends AbstractXmlFormat<NodeGeneConfiguration> {
		private ConfigurationXmlFormat() {
			super(NodeGeneConfiguration.class);
		}

		@Override
		protected NodeGeneConfiguration newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			NodeGeneConfiguration config = getManager().getObject(id);
			if (config != null) {
				return config;
			}
			Map<XId, XParameter<?>> params = new MapBuilder<XId, XParameter<?>>()//
					.putAll(new Transform<XId, XParameter<?>>() {
						@Override
						public XId transform(XParameter<?> value) {
							return value.getId();
						}
					}, readElements.getValues(PARAMETER_ELEMENT))//
					.newImmutableInstance();
			Builder builder = new Builder(id);
			if (params.containsKey(NodeGene.ACTIVATION_ID)) {
				builder.addParameter(params.get(NodeGene.ACTIVATION_ID));
			}
			return builder.newInstance();
		}
	}
}
