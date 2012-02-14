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
package com.xtructure.xevolution.config.impl;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xutil.config.ConditionXParameter;
import com.xtructure.xutil.config.DoubleXParameter;
import com.xtructure.xutil.config.IntegerXParameter;
import com.xtructure.xutil.config.XParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlReader;

public class EvolutionConfigurationImpl extends AbstractEvolutionConfiguration<EvolutionConfigurationImpl> {
	public static final XmlFormat<EvolutionConfigurationImpl>	XML_FORMAT				= new ConfigurationXmlFormat();
	public static final XmlBinding								XML_BINDING				= XmlBinding.builder()//
																								.add(EvolutionConfigurationImpl.class)//
																								.add(IntegerXParameter.class)//
																								.add(DoubleXParameter.class)//
																								.add(ConditionXParameter.class)//
																								.newInstance();
	public static final EvolutionConfigurationImpl				DEFAULT_CONFIGURATION	= XmlReader.read(//
																								EvolutionConfigurationImpl.class//
																										.getResourceAsStream("/com/xtructure/xevolution/config/impl/default.evolution.config.xml"),//
																								XML_BINDING, null);
	/** The next child number to assign. */
	private static final AtomicInteger							NEXT_CHILD_NUM			= new AtomicInteger(0);

	/**
	 * Returns a new id suitable for node configurations.
	 * 
	 * @return a new id suitable for node configurations
	 */
	private static XId generateId() {
		return XId.newId("evolutionConfiguration", NEXT_CHILD_NUM.getAndIncrement());
	}

	public static Builder builder() {
		return builder(null);
	}

	public static Builder builder(XId id) {
		return new Builder(id == null ? generateId() : id);
	}

	private EvolutionConfigurationImpl(XId id, Collection<XParameter<?>> parameters) {
		super(id, parameters);
	}

	@Override
	public EvolutionFieldMap newFieldMap() {
		return new EvolutionFieldMapImpl(this);
	}

	private static final class EvolutionFieldMapImpl extends AbstractEvolutionFieldMap {
		private EvolutionFieldMapImpl(EvolutionConfigurationImpl configuration) {
			super(configuration);
		}
	}

	public static final class Builder extends AbstractBuilder<EvolutionConfigurationImpl, Builder> {
		private Builder(XId id) {
			super(id);
		}

		@Override
		protected Builder addParameter(XParameter<?> parameter) {
			return super.addParameter(parameter);
		}

		@Override
		public EvolutionConfigurationImpl newInstance() {
			setRemainingToDefaults();
			return new EvolutionConfigurationImpl(getConfigurationId(), getParameters());
		}
	}

	private static final class ConfigurationXmlFormat extends AbstractXmlFormat<EvolutionConfigurationImpl> {
		protected ConfigurationXmlFormat() {
			super(EvolutionConfigurationImpl.class);
		}

		@Override
		protected EvolutionConfigurationImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			Builder builder = new Builder(id);
			for (XParameter<?> parameter : collectParameters(readElements,//
					POPULATION_SIZE_ID,//
					MUTATION_PROBABILITY_ID,//
					TERMINATION_CONDITION_ID)) {
				builder.addParameter(parameter);
			}
			return builder.newInstance();
		}
	}
}
