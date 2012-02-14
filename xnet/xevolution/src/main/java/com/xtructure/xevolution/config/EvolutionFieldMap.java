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
package com.xtructure.xevolution.config;

import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.valid.Condition;

/**
 * The Interface EvolutionFieldMap.
 *
 * @author Luis Guimbarda
 */
public interface EvolutionFieldMap extends FieldMap {
	
	/**
	 * Population size.
	 *
	 * @return the int
	 */
	public int populationSize();

	/**
	 * Mutation probability.
	 *
	 * @return the double
	 */
	public double mutationProbability();

	/**
	 * Termination condition.
	 *
	 * @return the condition
	 */
	public Condition terminationCondition();

	/**
	 * Sets the population size.
	 *
	 * @param populationSize the new population size
	 */
	public void setPopulationSize(int populationSize);

	/**
	 * Sets the mutation probability.
	 *
	 * @param mutationProbability the new mutation probability
	 */
	public void setMutationProbability(double mutationProbability);

	/**
	 * Sets the termination condition.
	 *
	 * @param terminationCondition the new termination condition
	 */
	public void setTerminationCondition(Condition terminationCondition);
}
