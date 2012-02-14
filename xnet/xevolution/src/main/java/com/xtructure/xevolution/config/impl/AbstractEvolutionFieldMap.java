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

import com.xtructure.xevolution.config.EvolutionConfiguration;
import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xutil.config.FieldMapImpl;
import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractEvolutionFieldMap extends FieldMapImpl implements EvolutionFieldMap {
	/**
	 * @param configuration
	 */
	public AbstractEvolutionFieldMap(XConfiguration configuration) {
		super(configuration);
	}

	@Override
	public int populationSize() {
		return get(EvolutionConfiguration.POPULATION_SIZE_ID);
	}

	@Override
	public double mutationProbability() {
		return get(EvolutionConfiguration.MUTATION_PROBABILITY_ID);
	}

	@Override
	public Condition terminationCondition() {
		return get(EvolutionConfiguration.TERMINATION_CONDITION_ID);
	}

	@Override
	public void setPopulationSize(int populationSize) {
		set(EvolutionConfiguration.POPULATION_SIZE_ID, populationSize);
	}

	@Override
	public void setMutationProbability(double mutationProbability) {
		set(EvolutionConfiguration.MUTATION_PROBABILITY_ID, mutationProbability);
	}

	@Override
	public void setTerminationCondition(Condition terminationCondition) {
		set(EvolutionConfiguration.TERMINATION_CONDITION_ID, terminationCondition);
	}
}
