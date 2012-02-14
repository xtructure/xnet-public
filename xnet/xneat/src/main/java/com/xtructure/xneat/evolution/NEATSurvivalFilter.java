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

import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.NEATPopulation;

/**
 * The {@link NEATSurvivalFilter} interface describes the methods for marking
 * dead {@link NEATGenome}s and species in a population. Classes implementing
 * this interface should mark {@link NEATGenome}s for death, essentially
 * selecting which genomes will survive into the next generation.
 * <P>
 * {@link NEATSurvivalFilter} instances should not remove genomes or species,
 * leaving them for removal at the discretion of the
 * {@link NEATEvolutionStrategy}.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface NEATSurvivalFilter extends SurvivalFilter {
	/**
	 * Marks for death the species from the given {@link NEATPopulation} which
	 * won't survive into the next generation of that population.
	 * 
	 * @param population
	 *            the collection of genomes and species to judge
	 */
	public void markDeadSpecies(NEATPopulation<?> population);

	/**
	 * Returns the {@link NEATEvolutionFieldMap} used by this
	 * {@link NEATSurvivalFilter}.
	 * 
	 * @return the {@link NEATEvolutionFieldMap} used by this
	 *         {@link NEATSurvivalFilter}.
	 */
	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap();
}
