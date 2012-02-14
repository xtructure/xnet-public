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
package com.xtructure.xneat.genetics.link.config;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xneat.genetics.link.LinkGene;
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
 * {@link LinkGeneConfiguration} is an {@link XConfiguration} for
 * {@link LinkGene}s
 * 
 * @author Luis Guimbarda
 * 
 */
public class LinkGeneConfiguration extends AbstractXConfiguration<LinkGeneConfiguration> {
	/** */
	public static final XmlFormat<LinkGeneConfiguration>			XML_FORMAT					= new ConfigurationXmlFormat();
	/** */
	public static final XmlBinding									XML_BINDING					= XmlBinding.builder()//
																										.add(LinkGeneConfiguration.class)//
																										.add(DoubleXParameter.class)//
																										.newInstance();
	/** {@link XLogger} for {@link LinkGeneConfiguration} */
	private static final XLogger									LOGGER						= XLogger.getInstance(LinkGeneConfiguration.class);
	/** manager for {@link LinkGeneConfiguration}s */
	private static final XIdObjectManager<LinkGeneConfiguration>	MANAGER						= new XIdObjectManagerImpl<LinkGeneConfiguration>() {};
	/** default {@link XId} for {@link LinkGeneConfiguration} */
	public static final XId											DEFAULT_CONFIGURATION_ID	= XId.newId("defaultNEATLinkConfiguration");
	/** The next child number to assign. */
	private static final AtomicInteger								NEXT_CHILD_NUM				= new AtomicInteger(0);
	private static final double										WEIGHT_DEFAULT				= 1.0;
	/** default {@link LinkGeneConfiguration} */
	public static final LinkGeneConfiguration						DEFAULT_CONFIGURATION		= new Builder(DEFAULT_CONFIGURATION_ID).newInstance();

	/**
	 * Returns the {@link XIdObjectManager} for {@link LinkGeneConfiguration}s.
	 * 
	 * @return the {@link XIdObjectManager} for {@link LinkGeneConfiguration}s.
	 */
	public static XIdObjectManager<LinkGeneConfiguration> getManager() {
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
	 * Creates a new {@link LinkGeneConfiguration} with the given id and
	 * parameters.
	 * 
	 * @param id
	 *            the XId of the new {@link LinkGeneConfiguration}
	 * @param parameters
	 */
	private LinkGeneConfiguration(XId id, Collection<XParameter<?>> parameters) {
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

	public static final class Builder extends AbstractConfigurationBuilder<LinkGeneConfiguration, Builder> {
		private Builder(XId configurationId) {
			super(configurationId);
		}

		public Builder setWeight(Range<Double> lifetimeRange, Range<Double> initialRange) {
			return setDoubleXParameter(LinkGene.WEIGHT_ID, LinkGene.WEIGHT_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		public Builder setWeight(double weight) {
			return setDoubleXParameter(LinkGene.WEIGHT_ID, LinkGene.WEIGHT_DESCRIPTION, false, true, weight);
		}

		@Override
		protected Builder addParameter(XParameter<?> parameter) {
			return super.addParameter(parameter);
		}

		@Override
		public LinkGeneConfiguration newInstance() {
			setRemainingToDefaults();
			return new LinkGeneConfiguration(getConfigurationId(), getParameters());
		}

		@Override
		protected void setRemainingToDefaults() {
			if (!isSet(LinkGene.WEIGHT_ID)) {
				setWeight(Range.getInstance(0.0, 10.0), Range.getInstance(WEIGHT_DEFAULT));
			}
		}
	}

	private static final class ConfigurationXmlFormat extends AbstractXmlFormat<LinkGeneConfiguration> {
		private ConfigurationXmlFormat() {
			super(LinkGeneConfiguration.class);
		}

		@Override
		protected LinkGeneConfiguration newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			LinkGeneConfiguration config = getManager().getObject(id);
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
			if (params.containsKey(LinkGene.WEIGHT_ID)) {
				builder.addParameter(params.get(LinkGene.WEIGHT_ID));
			}
			return builder.newInstance();
		}
	}
}
