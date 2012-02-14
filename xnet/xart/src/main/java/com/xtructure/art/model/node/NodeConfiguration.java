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
package com.xtructure.art.model.node;

import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.not;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.art.model.node.Node.Fragment;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.config.AbstractConfigurationBuilder;
import com.xtructure.xutil.config.AbstractXConfiguration;
import com.xtructure.xutil.config.BooleanXParameter;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.FieldMapImpl;
import com.xtructure.xutil.config.FloatXParameter;
import com.xtructure.xutil.config.IntegerXParameter;
import com.xtructure.xutil.config.XParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManagerImpl;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlReader;

/**
 * An ART node configuration.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbards
 * @version 0.9.6
 */
public class NodeConfiguration extends AbstractXConfiguration<NodeConfiguration> {
	/** The default id to assign. */
	private static final XId										CONFIG_ID;
	/** The next child number to assign. */
	private static final AtomicInteger								NEXT_CHILD_NUM;
	/** The xml format for node configurations */
	public static final XmlFormat<NodeConfiguration>				XML_FORMAT;
	/** The xml bindings for node configurations */
	public static final XmlBinding									XML_BINDING;
	/** The manager for node configurations */
	private static final XIdObjectManagerImpl<NodeConfiguration>	MANAGER;
	/** The default node configuration */
	public static final NodeConfiguration							DEFAULT_CONFIGURATION;
	static {
		CONFIG_ID = XId.newId("art.node.config");
		NEXT_CHILD_NUM = new AtomicInteger();
		XML_FORMAT = new ConfigurationXmlFormat();
		XML_BINDING = XmlBinding.builder()//
				.add(NodeConfiguration.class, "artNodeConfig")//
				.add(IntegerXParameter.class)//
				.add(FloatXParameter.class)//
				.add(BooleanXParameter.class)//
				.newInstance();
		MANAGER = new XIdObjectManagerImpl<NodeConfiguration>();
		DEFAULT_CONFIGURATION = XmlReader.read(//
				NodeConfiguration.class.getResourceAsStream(//
						"/com/xtructure/art/model/node/default.art.node.config.xml"),//
				XML_BINDING, null);
	}

	/**
	 * Returns the manager of node configuration instances.
	 * 
	 * @return the manager of node configuration instances
	 */
	public static XIdObjectManagerImpl<NodeConfiguration> getManager() {
		return MANAGER;
	}

	/**
	 * Returns a new id suitable for node configurations.
	 * 
	 * @return a new id suitable for node configurations
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
	 * @param id
	 *            the id of the node configuration to build
	 * @return a new {@link Builder} set with the given id
	 */
	public static Builder builder(XId id) {
		return new Builder(id);
	}

	/**
	 * Creates a new {@link NodeConfiguration}
	 * 
	 * @param id
	 *            the id of the new node configuration
	 * @param parameters
	 *            the parameters of the new node configuration
	 */
	private NodeConfiguration(XId id, Collection<XParameter<?>> parameters) {
		super(id, null, getManager(), parameters);
	}

	/** {@inheritDoc} */
	@Override
	public FieldMap newFieldMap() {
		return new FieldMapImpl(this);
	}

	/** the builder for {@link NodeConfiguration} */
	public static final class Builder extends AbstractConfigurationBuilder<NodeConfiguration, Builder> {
		/**
		 * Creates a new {@link Builder}
		 * 
		 * @param configurationId
		 *            the id of the node configuration being built
		 */
		private Builder(XId configurationId) {
			super(configurationId);
			validateArg("configurationId", configurationId, isNotNull());
			validateArg(String.format("%s is already registered", getConfigurationId()), MANAGER.getIds(), not(containsElement(getConfigurationId())));
		}

		/**
		 * Creates a new energy parameter with the given lifetime and initial
		 * ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the energy parameter
		 * @param initialRange
		 *            the initial range of the energy parameter
		 * @return this {@link Builder}
		 */
		public Builder setEnergy(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.ENERGY_ID, Node.ENERGY_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new energy decay parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the energy decay parameter
		 * @param initialRange
		 *            the initial range of the energy decay parameter
		 * @return this {@link Builder}
		 */
		public Builder setEnergyDecay(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.ENERGY_DECAY_ID, Node.ENERGY_DECAY_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new excitatory scale parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the excitatory scale parameter
		 * @param initialRange
		 *            the initial range of the excitatory scale parameter
		 * @return this {@link Builder}
		 */
		public Builder setExcitatoryScale(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.EXCITATORY_SCALE_ID, Node.EXCITATORY_SCALE_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new inhibitory scale parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the inhibitory scale parameter
		 * @param initialRange
		 *            the initial range of the inhibitory scale parameter
		 * @return this {@link Builder}
		 */
		public Builder setInhibitoryScale(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.INHIBITORY_SCALE_ID, Node.INHIBITORY_SCALE_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new oscillation minimum parameter with the given lifetime
		 * and initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the oscillation minimum parameter
		 * @param initialRange
		 *            the initial range of the oscillation minimum parameter
		 * @return this {@link Builder}
		 */
		public Builder setOscillationMinimum(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.OSCILLATION_MINIMUM_ID, Node.OSCILLATION_MINIMUM_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new oscillation maximum parameter with the given lifetime
		 * and initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the oscillation maximum parameter
		 * @param initialRange
		 *            the initial range of the oscillation maximum parameter
		 * @return this {@link Builder}
		 */
		public Builder setOscillationMaximum(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.OSCILLATION_MAXIMUM_ID, Node.OSCILLATION_MAXIMUM_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new oscillation offset parameter with the given lifetime
		 * and initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the oscillation offset parameter
		 * @param initialRange
		 *            the initial range of the oscillation offset parameter
		 * @return this {@link Builder}
		 */
		public Builder setOscillationOffset(Range<Integer> lifetimeRange, Range<Integer> initialRange) {
			return setIntegerXParameter(Node.OSCILLATION_OFFSET_ID, Node.OSCILLATION_OFFSET_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new oscillation period parameter with the given lifetime
		 * and initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the oscillation period parameter
		 * @param initialRange
		 *            the initial range of the oscillation period parameter
		 * @return this {@link Builder}
		 */
		public Builder setOscillationPeriod(Range<Integer> lifetimeRange, Range<Integer> initialRange) {
			return setIntegerXParameter(Node.OSCILLATION_PERIOD_ID, Node.OSCILLATION_PERIOD_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new twitch minimum parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the twitch minimum parameter
		 * @param initialRange
		 *            the initial range of the twitch minimum parameter
		 * @return this {@link Builder}
		 */
		public Builder setTwitchMinimum(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.TWITCH_MINIMUM_ID, Node.TWITCH_MINIMUM_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new twitch maximum parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the twitch maximum parameter
		 * @param initialRange
		 *            the initial range of the twitch maximum parameter
		 * @return this {@link Builder}
		 */
		public Builder setTwitchMaximum(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.TWITCH_MAXIMUM_ID, Node.TWITCH_MAXIMUM_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new twitch probability parameter with the given lifetime
		 * and initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the twitch probability parameter
		 * @param initialRange
		 *            the initial range of the twitch probability parameter
		 * @return this {@link Builder}
		 */
		public Builder setTwitchProbability(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.TWITCH_PROBABILITY_ID, Node.TWITCH_PROBABILITY_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new delay parameter with the given lifetime and initial
		 * ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the delay parameter
		 * @param initialRange
		 *            the initial range of the delay parameter
		 * @return this {@link Builder}
		 */
		public Builder setDelay(Range<Integer> lifetimeRange, Range<Integer> initialRange) {
			return setIntegerXParameter(Node.DELAY_ID, Node.DELAY_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new scale parameter with the given lifetime and initial
		 * ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the scale parameter
		 * @param initialRange
		 *            the initial range of the scale parameter
		 * @return this {@link Builder}
		 */
		public Builder setShift(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.SHIFT_ID, Node.SHIFT_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new shift parameter with the given lifetime and initial
		 * ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the shift parameter
		 * @param initialRange
		 *            the initial range of the shift parameter
		 * @return this {@link Builder}
		 */
		public Builder setScale(Range<Float> lifetimeRange, Range<Float> initialRange) {
			return setFloatXParameter(Node.SCALE_ID, Node.SCALE_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/**
		 * Creates a new invert flag parameter with the given lifetime and
		 * initial ranges.
		 * 
		 * @param lifetimeRange
		 *            the lifetime range of the invert flag parameter
		 * @param initialRange
		 *            the initial range of the invert flag parameter
		 * @return this {@link Builder}
		 */
		public Builder setInvertFlag(Range<Boolean> lifetimeRange, Range<Boolean> initialRange) {
			return setBooleanXParameter(Node.INVERT_FLAG_ID, Node.INVERT_FLAG_DESCRIPTION, false, true, lifetimeRange, initialRange);
		}

		/** {@inheritDoc} */
		@Override
		protected Builder addParameter(XParameter<?> parameter) {
			return super.addParameter(parameter);
		}

		/** {@inheritDoc} */
		@Override
		public NodeConfiguration newInstance() {
			setRemainingToDefaults();
			return new NodeConfiguration(getConfigurationId(), getParameters());
		}

		/** {@inheritDoc} */
		@Override
		protected void setRemainingToDefaults() {
			NodeConfiguration template = getTemplate() == null ? DEFAULT_CONFIGURATION : getTemplate();
			for (Fragment fragment : Fragment.values()) {
				switch (fragment) {
					case ENERGIES:
						break;
					default:
						if (!isSet(fragment.getParameterId())) {
							addParameter(template.getParameter(fragment.getParameterId()));
						}
				}
			}
		}
	}

	/** the xml format for {@link NodeConfiguration} */
	private static final class ConfigurationXmlFormat extends AbstractXmlFormat<NodeConfiguration> {
		private ConfigurationXmlFormat() {
			super(NodeConfiguration.class);
		}

		@Override
		protected NodeConfiguration newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			if (getManager().getObject(id) != null) {
				return getManager().getObject(id);
			}
			Builder builder = new Builder(id);
			for (XParameter<?> parameter : collectParameters(readElements,//
					Node.ENERGY_ID, Node.ENERGY_DECAY_ID,//
					Node.EXCITATORY_SCALE_ID, Node.INHIBITORY_SCALE_ID,//
					Node.OSCILLATION_MINIMUM_ID, Node.OSCILLATION_MAXIMUM_ID,//
					Node.OSCILLATION_OFFSET_ID, Node.OSCILLATION_PERIOD_ID,//
					Node.TWITCH_MINIMUM_ID, Node.TWITCH_MAXIMUM_ID, Node.TWITCH_PROBABILITY_ID,//
					Node.DELAY_ID, Node.SHIFT_ID,//
					Node.SCALE_ID, Node.INVERT_FLAG_ID)) {
				builder.addParameter(parameter);
			}
			return builder.newInstance();
		}
	}
}
