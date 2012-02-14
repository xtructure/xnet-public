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

import java.util.Set;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.NEATGenome;

/**
 * The {@link NEATSpeciationStrategy} interface describes methods for
 * partitioning a given {@link Population} into species.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link NEATGenome}s
 */
public interface NEATSpeciationStrategy<D> {
	/**
	 * Removes any previous speciation and determines a new partition of the
	 * given population.
	 * 
	 * @param population
	 *            the population to speciate
	 */
	public void speciate(Population<D> population);

	/**
	 * Respeciates the given population if the current speciation is not
	 * acceptable (e.g., too many/few species).
	 * 
	 * @param population
	 *            the population to speciate
	 */
	public void respeciate(Population<D> population);

	/**
	 * Adds the given children the population, and to the most appropriate
	 * species in that population.
	 * 
	 * @param children
	 *            the genomes to add to the population
	 * @param population
	 *            the population to which the children are added
	 */
	public void speciateChildren(Set<Genome<D>> children, Population<D> population);

	/**
	 * Returns the {@link NEATEvolutionFieldMap} used by this
	 * {@link NEATSpeciationStrategy}.
	 * 
	 * @return the {@link NEATEvolutionFieldMap} used by this
	 *         {@link NEATSpeciationStrategy}.
	 */
	public NEATEvolutionFieldMap getEvolutionFieldMap();
}
