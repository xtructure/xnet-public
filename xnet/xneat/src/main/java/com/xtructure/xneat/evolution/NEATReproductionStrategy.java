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

import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.NEATPopulation;

/**
 * The {@link NEATReproductionStrategy} interface describes the method which
 * produces new child {@link NEATGenome}s for a {@link NEATPopulation}. Classes
 * implementing this interface will orchestrate the creation of child genomes,
 * selecting which genomes will reproduce and via which of
 * {@link MutateOperator} or {@link CrossoverOperator}.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link NEATGenome}s
 */
public interface NEATReproductionStrategy<D> extends ReproductionStrategy<D> {
	/**
	 * Returns the {@link NEATEvolutionFieldMap} used by this
	 * {@link NEATReproductionStrategy}.
	 * 
	 * @return the {@link NEATEvolutionFieldMap} used by this
	 *         {@link NEATReproductionStrategy}.
	 */
	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap();

	/**
	 * Gets the {@link NEATGeneticsFactory} used by this
	 * {@link NEATReproductionStrategy}.
	 * 
	 * @return the {@link NEATGeneticsFactory} used by this
	 *         {@link NEATReproductionStrategy}.
	 * @see com.xtructure.xevolution.evolution.ReproductionStrategy#getGeneticsFactory()
	 */
	@Override
	public NEATGeneticsFactory<D> getGeneticsFactory();
}
