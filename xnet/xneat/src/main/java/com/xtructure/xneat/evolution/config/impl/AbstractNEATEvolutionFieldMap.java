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

import com.xtructure.xevolution.config.impl.AbstractEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.NEATEvolutionConfiguration;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;

/**
 * The Class AbstractNEATEvolutionFieldMap.
 *
 * @author Luis Guimbarda
 */
public abstract class AbstractNEATEvolutionFieldMap extends AbstractEvolutionFieldMap implements NEATEvolutionFieldMap {
	
	/**
	 * Instantiates a new abstract neat evolution field map.
	 *
	 * @param configuration the configuration
	 */
	public AbstractNEATEvolutionFieldMap(NEATEvolutionConfiguration configuration) {
		super(configuration);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#biasNodeCount()
	 */
	@Override
	public int biasNodeCount() {
		return get(NEATEvolutionConfiguration.BIAS_NODE_COUNT_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#compatibilityDisjointCoefficient()
	 */
	@Override
	public double compatibilityDisjointCoefficient() {
		return get(NEATEvolutionConfiguration.COMPATIBILITY_DISJOUNT_COEFFICIENT_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#compatibilityExcessCoefficient()
	 */
	@Override
	public double compatibilityExcessCoefficient() {
		return get(NEATEvolutionConfiguration.COMPATIBILITY_EXCESS_COEFFICIENT_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#compatibilityWeightDeltaCoefficient()
	 */
	@Override
	public double compatibilityWeightDeltaCoefficient() {
		return get(NEATEvolutionConfiguration.COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#eliteProportion()
	 */
	@Override
	public double eliteProportion() {
		return get(NEATEvolutionConfiguration.ELITE_PROPORTION_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#initialConnectionProbability()
	 */
	@Override
	public double initialConnectionProbability() {
		return get(NEATEvolutionConfiguration.INITIAL_CONNECTION_PROBABILITY_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#inputNodeCount()
	 */
	@Override
	public int inputNodeCount() {
		return get(NEATEvolutionConfiguration.INPUT_NODE_COUNT_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#interspeciesCrossoverProbability()
	 */
	@Override
	public double interspeciesCrossoverProbability() {
		return get(NEATEvolutionConfiguration.INTERSPECIES_CROSSOVER_PROBABILITY_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#outputNodeCount()
	 */
	@Override
	public int outputNodeCount() {
		return get(NEATEvolutionConfiguration.OUTPUT_NODE_COUNT_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#speciesDropoffAge()
	 */
	@Override
	public long speciesDropoffAge() {
		return get(NEATEvolutionConfiguration.SPECIES_DROPOFF_AGE_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#targetSpeciesCountMax()
	 */
	@Override
	public int targetSpeciesCountMax() {
		return get(NEATEvolutionConfiguration.TARGET_SPECIES_COUNT_MAX_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#targetSpeciesCountMin()
	 */
	@Override
	public int targetSpeciesCountMin() {
		return get(NEATEvolutionConfiguration.TARGET_SPECIES_COUNT_MIN_ID);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setCompatibilityWeightDeltaCoefficient(double)
	 */
	@Override
	public void setCompatibilityWeightDeltaCoefficient(double coefficient) {
		set(NEATEvolutionConfiguration.COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_ID, coefficient);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setCompatibilityDisjointCoefficient(double)
	 */
	@Override
	public void setCompatibilityDisjointCoefficient(double coefficient) {
		set(NEATEvolutionConfiguration.COMPATIBILITY_DISJOUNT_COEFFICIENT_ID, coefficient);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setCompatibilityExcessCoefficient(double)
	 */
	@Override
	public void setCompatibilityExcessCoefficient(double coefficient) {
		set(NEATEvolutionConfiguration.COMPATIBILITY_EXCESS_COEFFICIENT_ID, coefficient);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setEliteProportion(double)
	 */
	@Override
	public void setEliteProportion(double eliteProportion) {
		set(NEATEvolutionConfiguration.ELITE_PROPORTION_ID, eliteProportion);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setTargetSpeciesCountMin(int)
	 */
	@Override
	public void setTargetSpeciesCountMin(int min) {
		set(NEATEvolutionConfiguration.TARGET_SPECIES_COUNT_MIN_ID, min);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setTargetSpeciesCountMax(int)
	 */
	@Override
	public void setTargetSpeciesCountMax(int max) {
		set(NEATEvolutionConfiguration.TARGET_SPECIES_COUNT_MAX_ID, max);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setInputNodeCount(int)
	 */
	@Override
	public void setInputNodeCount(int count) {
		set(NEATEvolutionConfiguration.INPUT_NODE_COUNT_ID, count);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setOutputNodeCount(int)
	 */
	@Override
	public void setOutputNodeCount(int count) {
		set(NEATEvolutionConfiguration.OUTPUT_NODE_COUNT_ID, count);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setBiasNodeCount(int)
	 */
	@Override
	public void setBiasNodeCount(int count) {
		set(NEATEvolutionConfiguration.BIAS_NODE_COUNT_ID, count);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setInterspeciesCrossoverProbability(double)
	 */
	@Override
	public void setInterspeciesCrossoverProbability(double probability) {
		set(NEATEvolutionConfiguration.INTERSPECIES_CROSSOVER_PROBABILITY_ID, probability);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setInitialConnectionProbability(double)
	 */
	@Override
	public void setInitialConnectionProbability(double probability) {
		set(NEATEvolutionConfiguration.INITIAL_CONNECTION_PROBABILITY_ID, probability);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap#setSpeciesDropoffAge(long)
	 */
	@Override
	public void setSpeciesDropoffAge(long age) {
		set(NEATEvolutionConfiguration.SPECIES_DROPOFF_AGE_ID, age);
	}
}
