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
package com.xtructure.xneat.evolution.config.impl;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xneat.evolution.config.NEATEvolutionConfiguration;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xutil.config.ConditionXParameter;
import com.xtructure.xutil.config.DoubleXParameter;
import com.xtructure.xutil.config.IntegerXParameter;
import com.xtructure.xutil.config.LongXParameter;
import com.xtructure.xutil.config.XParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlReader;

public class NEATEvolutionConfigurationImpl extends AbstractNEATEvolutionConfiguration<NEATEvolutionConfigurationImpl> {
	public static final XmlFormat<NEATEvolutionConfigurationImpl>	XML_FORMAT				= new ConfigurationXmlFormat();
	public static final XmlBinding									XML_BINDING				= XmlBinding.builder()//
																									.add(NEATEvolutionConfigurationImpl.class)//
																									.add(IntegerXParameter.class)//
																									.add(DoubleXParameter.class)//
																									.add(LongXParameter.class)//
																									.add(ConditionXParameter.class)//
																									.newInstance();
	public static final NEATEvolutionConfigurationImpl				DEFAULT_CONFIGURATION	= XmlReader.read(//
																									NEATEvolutionConfigurationImpl.class//
																											.getResourceAsStream("/com/xtructure/xneat/evolution/config/default.neat.evolution.config.xml"),//
																									XML_BINDING, null);
	/** The next child number to assign. */
	private static final AtomicInteger								NEXT_CHILD_NUM			= new AtomicInteger(0);

	/**
	 * Returns a new id suitable for node configurations.
	 * 
	 * @return a new id suitable for node configurations
	 */
	private static XId generateId() {
		return XId.newId("neatEvolutionConfiguration", NEXT_CHILD_NUM.getAndIncrement());
	}

	public static Builder builder(XId id) {
		return new Builder(id == null ? generateId() : id);
	}

	private NEATEvolutionConfigurationImpl(XId id, Collection<XParameter<?>> parameters) {
		super(id, parameters);
	}

	@Override
	public NEATEvolutionFieldMap newFieldMap() {
		return new NEATEvolutionFieldMapImpl(this);
	}

	private static final class NEATEvolutionFieldMapImpl extends AbstractNEATEvolutionFieldMap {
		public NEATEvolutionFieldMapImpl(NEATEvolutionConfiguration configuration) {
			super(configuration);
		}
	}

	public static final class Builder extends AbstractBuilder<NEATEvolutionConfigurationImpl, Builder> {
		private Builder(XId id) {
			super(id);
		}

		@Override
		protected Builder addParameter(XParameter<?> parameter) {
			return super.addParameter(parameter);
		}

		@Override
		public NEATEvolutionConfigurationImpl newInstance() {
			setRemainingToDefaults();
			return new NEATEvolutionConfigurationImpl(getConfigurationId(), getParameters());
		}
	}

	private static final class ConfigurationXmlFormat extends AbstractXmlFormat<NEATEvolutionConfigurationImpl> {
		protected ConfigurationXmlFormat() {
			super(NEATEvolutionConfigurationImpl.class);
		}

		@Override
		protected NEATEvolutionConfigurationImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			Builder builder = new Builder(id);
			for (XParameter<?> parameter : collectParameters(readElements,//
					POPULATION_SIZE_ID, MUTATION_PROBABILITY_ID, TERMINATION_CONDITION_ID,//
					INPUT_NODE_COUNT_ID, OUTPUT_NODE_COUNT_ID, BIAS_NODE_COUNT_ID,//
					INITIAL_CONNECTION_PROBABILITY_ID, TARGET_SPECIES_COUNT_MIN_ID, TARGET_SPECIES_COUNT_MAX_ID,//
					ELITE_PROPORTION_ID, INTERSPECIES_CROSSOVER_PROBABILITY_ID, SPECIES_DROPOFF_AGE_ID,//
					COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_ID, COMPATIBILITY_DISJOUNT_COEFFICIENT_ID, COMPATIBILITY_EXCESS_COEFFICIENT_ID)) {
				builder.addParameter(parameter);
			}
			return builder.newInstance();
		}
	}
}
