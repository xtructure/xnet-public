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
package com.xtructure.xneat.evolution.impl;

import java.io.File;
import java.util.Collection;

import com.xtructure.xevolution.config.EvolutionConfiguration;
import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment;
import com.xtructure.xneat.evolution.NEATEvolutionExperiment;
import com.xtructure.xneat.evolution.NEATEvolutionStrategy;
import com.xtructure.xneat.evolution.NEATReproductionStrategy;
import com.xtructure.xneat.evolution.NEATSpeciationStrategy;
import com.xtructure.xneat.evolution.NEATSurvivalFilter;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xutil.WormField;
import com.xtructure.xutil.opt.XOption;
import com.xtructure.xutil.xml.XmlReader;

/**
 * @author Luis Guimbarda
 * 
 * @param <D>
 * @param <T>
 */
public abstract class AbstractNEATEvolutionExperiment<D, T> extends AbstractEvolutionExperiment<D, T> implements NEATEvolutionExperiment<D, T> {
	public AbstractNEATEvolutionExperiment(Collection<? extends XOption<?>> options) {
		super(options);
	}

	protected abstract NEATSpeciationStrategy<D> createSpeciationStrategy();

	private final WormField<NEATSpeciationStrategy<D>>	speciationStrategy	= new WormField<NEATSpeciationStrategy<D>>();

	@Override
	protected EvolutionFieldMap createEvolutionFieldMap() {
		File configurationFile = (File) (getOption(PARAMETERS_FILE_OPTION).hasValue() ? getOption(PARAMETERS_FILE_OPTION).processValue() : null);
		EvolutionConfiguration config = null;
		if (configurationFile != null) {
			config = XmlReader.read(configurationFile, NEATEvolutionConfigurationImpl.XML_BINDING, NEATEvolutionConfigurationImpl.DEFAULT_CONFIGURATION);
		} else {
			config = NEATEvolutionConfigurationImpl.DEFAULT_CONFIGURATION;
		}
		return config.newFieldMap();
	}

	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap() {
		return (NEATEvolutionFieldMap) super.getEvolutionFieldMap();
	}

	@Override
	public NEATGeneticsFactory<D> getGeneticsFactory() {
		return (NEATGeneticsFactory<D>) super.getGeneticsFactory();
	}

	@Override
	public final NEATReproductionStrategy<D> getReproductionStrategy() {
		return (NEATReproductionStrategy<D>) super.getReproductionStrategy();
	}

	@Override
	public final NEATEvolutionStrategy<D, T> getEvolutionStrategy() {
		return (NEATEvolutionStrategy<D, T>) super.getEvolutionStrategy();
	}

	@Override
	public final NEATSurvivalFilter getSurvivalFilter() {
		return (NEATSurvivalFilter) super.getSurvivalFilter();
	}

	@Override
	public NEATSpeciationStrategy<D> getSpeciationStrategy() {
		if (!speciationStrategy.isInitialized()) {
			speciationStrategy.initValue(createSpeciationStrategy());
		}
		return speciationStrategy.getValue();
	}
}
