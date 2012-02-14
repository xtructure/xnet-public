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

import com.xtructure.xevolution.config.impl.AbstractEvolutionConfiguration;
import com.xtructure.xneat.evolution.config.NEATEvolutionConfiguration;
import com.xtructure.xutil.config.XParameter;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 * @param <S>
 */
public abstract class AbstractNEATEvolutionConfiguration<S extends AbstractNEATEvolutionConfiguration<S>> extends AbstractEvolutionConfiguration<S> implements NEATEvolutionConfiguration {
	protected AbstractNEATEvolutionConfiguration(XId id, Collection<XParameter<?>> parameters) {
		super(id, parameters);
	}

	public static abstract class AbstractBuilder<S extends AbstractNEATEvolutionConfiguration<S>, B extends AbstractBuilder<S, B>> extends AbstractEvolutionConfiguration.AbstractBuilder<S, B> {
		protected AbstractBuilder(XId id) {
			super(id);
		}

		public B setInputNodeCount(int inputNodeCount) {
			return setIntegerXParameter(//
					INPUT_NODE_COUNT_ID, INPUT_NODE_COUNT_DESCRIPTION,//
					false, true, inputNodeCount);
		}

		public B setOutputNodeCount(int outputNodeCount) {
			return setIntegerXParameter(//
					OUTPUT_NODE_COUNT_ID, OUTPUT_NODE_COUNT_DESCRIPTION,//
					false, true, outputNodeCount);
		}

		public B setBiasNodeCount(int biasNodeCount) {
			return setIntegerXParameter(//
					BIAS_NODE_COUNT_ID, BIAS_NODE_COUNT_DESCRIPTION,//
					false, true, biasNodeCount);
		}

		public B setInitialConnectionProbability(double initialConnectionProbability) {
			return setDoubleXParameter(//
					INITIAL_CONNECTION_PROBABILITY_ID, INITIAL_CONNECTION_PROBABILITY_DESCRIPTION,//
					false, true, initialConnectionProbability);
		}

		public B setTargetSpeciesCountMin(int targetSpeciesCountMin) {
			return setIntegerXParameter(//
					TARGET_SPECIES_COUNT_MIN_ID, TARGET_SPECIES_COUNT_MIN_DESCRIPTION,//
					false, true, targetSpeciesCountMin);
		}

		public B setTargetSpeciesCountMax(int targetSpeciesCountMax) {
			return setIntegerXParameter(//
					TARGET_SPECIES_COUNT_MAX_ID, TARGET_SPECIES_COUNT_MAX_DESCRIPTION,//
					false, true, targetSpeciesCountMax);
		}

		public B setEliteProportion(double eliteProportion) {
			return setDoubleXParameter(//
					ELITE_PROPORTION_ID, ELITE_PROPORTION_DESCRIPTION,//
					false, true, eliteProportion);
		}

		public B setInterspeciesCrossoverProbability(double interspeciesCrossoverProbability) {
			return setDoubleXParameter(//
					INTERSPECIES_CROSSOVER_PROBABILITY_ID, INTERSPECIES_CROSSOVER_PROBABILITY_DESCRIPTION,//
					false, true, interspeciesCrossoverProbability);
		}

		public B setSpeciesDropoffAge(long speciesDropoffAge) {
			return setLongXParameter(//
					SPECIES_DROPOFF_AGE_ID, SPECIES_DROPOFF_AGE_DESCRIPTION,//
					false, true, speciesDropoffAge);
		}

		public B setCompatibilityWeightDeltaCoefficient(double compatibilityWeightDeltaCoefficient) {
			return setDoubleXParameter(//
					COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_ID, COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_DESCRIPTION,//
					false, true, compatibilityWeightDeltaCoefficient);
		}

		public B setCompatibilityDisjointCoefficient(double compatibilityDisjointCoefficient) {
			return setDoubleXParameter(//
					COMPATIBILITY_DISJOUNT_COEFFICIENT_ID, COMPATIBILITY_DISJOUNT_COEFFICIENT_DESCRIPTION,//
					false, true, compatibilityDisjointCoefficient);
		}

		public B setCompatibilityExcessCoefficient(double compatibilityExcessCoefficient) {
			return setDoubleXParameter(//
					COMPATIBILITY_EXCESS_COEFFICIENT_ID, COMPATIBILITY_EXCESS_COEFFICIENT_DESCRIPTION,//
					false, true, compatibilityExcessCoefficient);
		}

		@Override
		protected void setRemainingToDefaults() {
			super.setRemainingToDefaults();
			if (!isSet(INPUT_NODE_COUNT_ID)) {
				setInputNodeCount(INPUT_NODE_COUNT_DEFAULT);
			}
			if (!isSet(OUTPUT_NODE_COUNT_ID)) {
				setOutputNodeCount(OUTPUT_NODE_COUNT_DEFAULT);
			}
			if (!isSet(BIAS_NODE_COUNT_ID)) {
				setBiasNodeCount(BIAS_NODE_COUNT_DEFAULT);
			}
			if (!isSet(INITIAL_CONNECTION_PROBABILITY_ID)) {
				setInitialConnectionProbability(INITIAL_CONNECTION_PROBABILITY_DEFAULT);
			}
			if (!isSet(TARGET_SPECIES_COUNT_MIN_ID)) {
				setTargetSpeciesCountMin(TARGET_SPECIES_COUNT_MIN_DEFAULT);
			}
			if (!isSet(TARGET_SPECIES_COUNT_MAX_ID)) {
				setTargetSpeciesCountMax(TARGET_SPECIES_COUNT_MAX_DEFAULT);
			}
			if (!isSet(ELITE_PROPORTION_ID)) {
				setEliteProportion(ELITE_PROPORTION_DEFAULT);
			}
			if (!isSet(INTERSPECIES_CROSSOVER_PROBABILITY_ID)) {
				setInterspeciesCrossoverProbability(INTERSPECIES_CROSSOVER_PROBABILITY_DEFAULT);
			}
			if (!isSet(SPECIES_DROPOFF_AGE_ID)) {
				setSpeciesDropoffAge(SPECIES_DROPOFF_AGE_DEFAULT);
			}
			if (!isSet(COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_ID)) {
				setCompatibilityWeightDeltaCoefficient(COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_DEFAULT);
			}
			if (!isSet(COMPATIBILITY_DISJOUNT_COEFFICIENT_ID)) {
				setCompatibilityDisjointCoefficient(COMPATIBILITY_DISJOUNT_COEFFICIENT_DEFAULT);
			}
			if (!isSet(COMPATIBILITY_EXCESS_COEFFICIENT_ID)) {
				setCompatibilityExcessCoefficient(COMPATIBILITY_EXCESS_COEFFICIENT_DEFAULT);
			}
		}
	}
}
