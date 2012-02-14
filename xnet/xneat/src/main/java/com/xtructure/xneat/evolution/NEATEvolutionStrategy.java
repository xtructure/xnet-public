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
package com.xtructure.xneat.evolution;

import com.xtructure.xevolution.evolution.EvolutionObject;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.NEATGenome;

/**
 * The {@link NEATEvolutionStrategy} interface extends the
 * {@link EvolutionStrategy} interface, and represents the main
 * {@link EvolutionObject} for the NEAT algorithm.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link NEATGenome}s
 * @param <T>
 *            phenotype of {@link NEATGenome}s
 */
public interface NEATEvolutionStrategy<D, T> extends EvolutionStrategy<D, T> {
	/**
	 * Returns the {@link NEATSpeciationStrategy} used by this
	 * {@link NEATEvolutionStrategy}
	 * 
	 * @return the {@link NEATSpeciationStrategy} used by this
	 *         {@link NEATEvolutionStrategy}
	 */
	public NEATSpeciationStrategy<D> getSpeciationStrategy();

	/**
	 * Returns the {@link NEATEvolutionFieldMap} used by this
	 * {@link NEATEvolutionStrategy}
	 * 
	 * @return the {@link NEATEvolutionFieldMap} used by this
	 *         {@link NEATEvolutionStrategy}
	 */
	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap();

	/**
	 * Returns the {@link NEATReproductionStrategy} used by this
	 * {@link NEATEvolutionStrategy}
	 * 
	 * @return the {@link NEATReproductionStrategy} used by this
	 *         {@link NEATEvolutionStrategy}
	 */
	@Override
	public NEATReproductionStrategy<D> getReproductionStrategy();

	/**
	 * Returns the {@link NEATSurvivalFilter} used by this
	 * {@link NEATEvolutionStrategy}
	 * 
	 * @return the {@link NEATSurvivalFilter} used by this
	 *         {@link NEATEvolutionStrategy}
	 */
	@Override
	public NEATSurvivalFilter getSurvivalFilter();
}
