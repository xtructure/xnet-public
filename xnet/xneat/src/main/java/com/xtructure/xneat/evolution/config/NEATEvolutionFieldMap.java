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
package com.xtructure.xneat.evolution.config;

import com.xtructure.xevolution.config.EvolutionFieldMap;

/**
 * The Interface NEATEvolutionFieldMap.
 *
 * @author Luis Guimbarda
 */
public interface NEATEvolutionFieldMap extends EvolutionFieldMap {
	
	/**
	 * Compatibility weight delta coefficient.
	 *
	 * @return the double
	 */
	public double compatibilityWeightDeltaCoefficient();

	/**
	 * Compatibility disjoint coefficient.
	 *
	 * @return the double
	 */
	public double compatibilityDisjointCoefficient();

	/**
	 * Compatibility excess coefficient.
	 *
	 * @return the double
	 */
	public double compatibilityExcessCoefficient();

	/**
	 * Elite proportion.
	 *
	 * @return the double
	 */
	public double eliteProportion();

	/**
	 * Target species count min.
	 *
	 * @return the int
	 */
	public int targetSpeciesCountMin();

	/**
	 * Target species count max.
	 *
	 * @return the int
	 */
	public int targetSpeciesCountMax();

	/**
	 * Input node count.
	 *
	 * @return the int
	 */
	public int inputNodeCount();

	/**
	 * Output node count.
	 *
	 * @return the int
	 */
	public int outputNodeCount();

	/**
	 * Bias node count.
	 *
	 * @return the int
	 */
	public int biasNodeCount();

	/**
	 * Interspecies crossover probability.
	 *
	 * @return the double
	 */
	public double interspeciesCrossoverProbability();

	/**
	 * Initial connection probability.
	 *
	 * @return the double
	 */
	public double initialConnectionProbability();

	/**
	 * Species dropoff age.
	 *
	 * @return the long
	 */
	public long speciesDropoffAge();

	/**
	 * Sets the compatibility weight delta coefficient.
	 *
	 * @param coefficient the new compatibility weight delta coefficient
	 */
	public void setCompatibilityWeightDeltaCoefficient(double coefficient);

	/**
	 * Sets the compatibility disjoint coefficient.
	 *
	 * @param coefficient the new compatibility disjoint coefficient
	 */
	public void setCompatibilityDisjointCoefficient(double coefficient);

	/**
	 * Sets the compatibility excess coefficient.
	 *
	 * @param coefficient the new compatibility excess coefficient
	 */
	public void setCompatibilityExcessCoefficient(double coefficient);

	/**
	 * Sets the elite proportion.
	 *
	 * @param eliteProportion the new elite proportion
	 */
	public void setEliteProportion(double eliteProportion);

	/**
	 * Sets the target species count min.
	 *
	 * @param min the new target species count min
	 */
	public void setTargetSpeciesCountMin(int min);

	/**
	 * Sets the target species count max.
	 *
	 * @param max the new target species count max
	 */
	public void setTargetSpeciesCountMax(int max);

	/**
	 * Sets the input node count.
	 *
	 * @param count the new input node count
	 */
	public void setInputNodeCount(int count);

	/**
	 * Sets the output node count.
	 *
	 * @param count the new output node count
	 */
	public void setOutputNodeCount(int count);

	/**
	 * Sets the bias node count.
	 *
	 * @param count the new bias node count
	 */
	public void setBiasNodeCount(int count);

	/**
	 * Sets the interspecies crossover probability.
	 *
	 * @param probability the new interspecies crossover probability
	 */
	public void setInterspeciesCrossoverProbability(double probability);

	/**
	 * Sets the initial connection probability.
	 *
	 * @param probability the new initial connection probability
	 */
	public void setInitialConnectionProbability(double probability);

	/**
	 * Sets the species dropoff age.
	 *
	 * @param age the new species dropoff age
	 */
	public void setSpeciesDropoffAge(long age);
}
