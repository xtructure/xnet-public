/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.model.link;

import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.not;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.art.model.link.Link.Fragment;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.config.AbstractConfigurationBuilder;
import com.xtructure.xutil.config.AbstractXConfiguration;
import com.xtructure.xutil.config.BooleanXParameter;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.FieldMapImpl;
import com.xtructure.xutil.config.FloatXParameter;
import com.xtructure.xutil.config.XParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManagerImpl;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlReader;

/**
 * An ART link configuration.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbards
 * @version 0.9.6
 */
public final class LinkConfiguration extends AbstractXConfiguration<LinkConfiguration> {
	/** The default id to assign. */
	private static final XId										CONFIG_ID;
	/** The next child number to assign. */
	private static final AtomicInteger								NEXT_CHILD_NUM;
	/** The XML format of link configurations */
	public static final XmlFormat<LinkConfiguration>				XML_FORMAT;
	/** The XML bindings for link configurations */
	public static final XmlBinding									XML_BINDING;
	/** The manager for link configurations */
	private static final XIdObjectManagerImpl<LinkConfiguration>	MANAGER;
	/** The default link configuration */
	public static final LinkConfiguration							DEFAULT_CONFIGURATION;
	static {
		CONFIG_ID = XId.newId("art.link.config");
		NEXT_CHILD_NUM = new AtomicInteger();
		XML_FORMAT = new ConfigurationXmlFormat();
		XML_BINDING = XmlBinding.builder()//
				.add(LinkConfiguration.class, "artLinkConfig")//
				.add(FloatXParameter.class)//
				.add(BooleanXParameter.class)//
				.newInstance();
		MANAGER = new XIdObjectManagerImpl<LinkConfiguration>();
		DEFAULT_CONFIGURATION = XmlReader.read(//
				LinkConfiguration.class.getResourceAsStream(//
						"/com/xtructure/art/model/link/default.art.link.config.xml"),//
				XML_BINDING, null);
	}

	/**
	 * Returns the manager of link configuration instances.
	 * 
	 * @return the manager of link configuration instances
	 */
	public static XIdObjectManagerImpl<LinkConfiguration> getManager() {
		return MANAGER;
	}

	/**
	 * Returns a new id suitable for link configurations.
	 * 
	 * @return a new id suitable for link configurations
	 */
	private static XId generateId() {
		return CONFIG_ID.createChild(NEXT_CHILD_NUM.getAndIncrement());
	}

	/**
	 * Returns a new {@link Builder}.
	 * 
	 * @return a new {@link Builder}.
	 */
	public static Builder builder() {
		return new Builder(generateId());
	}

	/**
	 * Returns a new {@link Builder} set with the given id
	 * 
	 * @param configurationId
	 *            the id of the link configuration to build
	 * @return a new {@link Builder} set with the given id
	 */
	public static Builder builder(XId configurationId) {
		return new Builder(configurationId);
	}

	/**
	 * Creates a new {@link LinkConfiguration}
	 * 
	 * @param id
	 *            the id of the new link configuration
	 * @param parameters
	 *            the parameters of the new link configuration
	 */
	private LinkConfiguration(XId id, Collection<XParameter<?>> parameters) {
		super(id, null, getManager(), parameters);
	}

	/** {@inheritDoc} */
	@Override
	public FieldMap newFieldMap() {
		return new FieldMapImpl(this);
	}

	/** builder for {@link LinkConfiguration} */
	public static final class Builder extends AbstractConfigurationBuilder<LinkConfiguration, Builder> {
		/**
		 * Creates a new {@link Builder}
		 * 
		 * @param configurationId
		 *            the id of the link configuration being built
		 */
		private Builder(XId configurationId) {
			super(configurationId);
			validateArg("configurationId",//
					configurationId, isNotNull());
			validateArg(String.format("%s is already registered", getConfigurationId()),//
					MANAGER.getIds(), not(containsElement(getConfigurationId())));
		}

		/**
		 * Creates a new capacity parameter with the given lifetime and initial
		 * ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the capacity parameter
		 * @param initialRange
		 *            the initial range of the capacity parameter
		 * @return this {@link Builder}
		 */
		public Builder setCapacity(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Link.CAPACITY_ID, Link.CAPACITY_DESCRIPTION,//
					false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new capacity attack parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the capacity attack parameter
		 * @param initialRange
		 *            the initial range of the capacity attack parameter
		 * @return this {@link Builder}
		 */
		public Builder setCapacityAttack(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Link.CAPACITY_ATTACK_ID, Link.CAPACITY_ATTACK_DESCRIPTION,//
					false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new capacity decay parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the capacity decay parameter
		 * @param initialRange
		 *            the initial range of the capacity decay parameter
		 * @return this {@link Builder}
		 */
		public Builder setCapacityDecay(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Link.CAPACITY_DECAY_ID, Link.CAPACITY_DECAY_DESCRIPTION,//
					false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new strength parameter with the given lifetime and initial
		 * ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the strength parameter
		 * @param initialRange
		 *            the initial range of the strength parameter
		 * @return this {@link Builder}
		 */
		public Builder setStrength(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Link.STRENGTH_ID, Link.STRENGTH_DESCRIPTION,//
					false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new strength attack parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the strength attack parameter
		 * @param initialRange
		 *            the initial range of the strength attack parameter
		 * @return this {@link Builder}
		 */
		public Builder setStrengthAttack(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Link.STRENGTH_ATTACK_ID, Link.STRENGTH_ATTACK_DESCRIPTION,//
					false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new strength decay parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the strength decay parameter
		 * @param initialRange
		 *            the initial range of the strength decay parameter
		 * @return this {@link Builder}
		 */
		public Builder setStrengthDecay(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Link.STRENGTH_DECAY_ID, Link.STRENGTH_DECAY_DESCRIPTION,//
					false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new inhibitory flag parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the strength parameter
		 * @param initialRange
		 *            the initial range of the strength parameter
		 * @return this {@link Builder}
		 */
		public Builder setInhibitoryFlag(Range<Boolean> lifetimeRange, Range<Boolean> initialRange) {
			return setBooleanXParameter(Link.INHIBITORY_FLAG_ID, Link.INHIBITORY_FLAG_DESCRIPTION,//
					false, true, lifetimeRange, initialRange);
		}

		/** {@inheritDoc} */
		@Override
		protected Builder addParameter(XParameter<?> parameter) {
			return super.addParameter(parameter);
		}

		/** {@inheritDoc} */
		@Override
		public LinkConfiguration newInstance() {
			setRemainingToDefaults();
			return new LinkConfiguration(getConfigurationId(), getParameters());
		}

		/** {@inheritDoc} */
		@Override
		protected void setRemainingToDefaults() {
			LinkConfiguration template = getTemplate() == null ? DEFAULT_CONFIGURATION : getTemplate();
			for (Fragment fragment : Fragment.values()) {
				switch (fragment) {
					case OUTPUT_ENERGY:
						break;
					default:
						if (!isSet(fragment.getParameterId())) {
							addParameter(template.getParameter(fragment.getParameterId()));
						}
				}
			}
		}
	}

	/** xml format for link configurations */
	private static final class ConfigurationXmlFormat extends AbstractXmlFormat<LinkConfiguration> {
		private ConfigurationXmlFormat() {
			super(LinkConfiguration.class);
		}

		@Override
		protected LinkConfiguration newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			if (getManager().getObject(id) != null) {
				return getManager().getObject(id);
			}
			Builder builder = new Builder(id);
			for (XParameter<?> parameter : collectParameters(readElements,//
					Link.CAPACITY_ID, Link.CAPACITY_ATTACK_ID, Link.CAPACITY_DECAY_ID,//
					Link.STRENGTH_ID, Link.STRENGTH_ATTACK_ID, Link.STRENGTH_DECAY_ID,//
					Link.INHIBITORY_FLAG_ID)) {
				builder.addParameter(parameter);
			}
			return builder.newInstance();
		}
	}
}
