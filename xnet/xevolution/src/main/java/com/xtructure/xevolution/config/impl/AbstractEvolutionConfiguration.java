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

import com.xtructure.xevolution.config.EvolutionConfiguration;
import com.xtructure.xutil.config.AbstractConfigurationBuilder;
import com.xtructure.xutil.config.AbstractXConfiguration;
import com.xtructure.xutil.config.XParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractEvolutionConfiguration<S extends AbstractEvolutionConfiguration<S>> extends AbstractXConfiguration<S> implements EvolutionConfiguration {
	protected AbstractEvolutionConfiguration(XId id, Collection<XParameter<?>> parameters) {
		super(id, parameters);
	}

	public static abstract class AbstractBuilder<S extends AbstractEvolutionConfiguration<S>, B extends AbstractBuilder<S, B>> extends AbstractConfigurationBuilder<S, B> {
		protected AbstractBuilder(XId configurationId) {
			super(configurationId);
		}

		public B setPopulationSize(int populationSize) {
			return setIntegerXParameter(POPULATION_SIZE_ID, POPULATION_SIZE_DESCRIPTION, false, true, populationSize);
		}

		public B setMutationProbability(double mutationProbability) {
			return setDoubleXParameter(MUTATION_PROBABILITY_ID, MUTATION_PROBABILITY_DESCRIPTION, false, true, mutationProbability);
		}

		public B setTerminationCondition(Condition terminationCondition) {
			return setConditionXParameter(TERMINATION_CONDITION_ID, TERMINATION_CONDITION_DESCRIPTION, false, true, terminationCondition);
		}

		@Override
		protected void setRemainingToDefaults() {
			if (!isSet(POPULATION_SIZE_ID)) {
				setPopulationSize(POPULATION_SIZE_DEFAULT);
			}
			if (!isSet(MUTATION_PROBABILITY_ID)) {
				setMutationProbability(MUTATION_PROBABILITY_DEFAULT);
			}
			if (!isSet(TERMINATION_CONDITION_ID)) {
				setTerminationCondition(TERMINATION_CONDITION_DEFAULT);
			}
		}
	}
}
