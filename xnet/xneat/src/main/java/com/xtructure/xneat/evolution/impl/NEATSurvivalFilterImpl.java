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
package com.xtructure.xneat.evolution.impl;

import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xneat.evolution.NEATSurvivalFilter;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.NEATPopulation;

/**
 * An implementation of {@link NEATSurvivalFilter}, where it marks dead
 * {@link NEATGenome}s and species in a {@link NEATPopulation} with
 * a {@link GeneMap} type data.
 * <P>
 * When marking genomes, all but the fittest genomes (the number as derived from
 * {@link NEATEvolutionFieldMap#eliteProportion()}) in each species are marked.
 * When marking species, stagnant species (those who haven't experience fitness
 * improvement after {@link NEATEvolutionFieldMap#speciesDropoffAge()}
 * generations) and terminally unfit species (those whom fitness sharing causes
 * to have a 0 target size) are marked, excepting the species that contains the
 * populations most fit genome.
 * <P>
 * {@link NEATSurvivalFilterImpl} instances will not remove genomes, leaving
 * them for removal at the discretion of the {@link EvolutionStrategy}.
 * 
 * @author Luis Guimbarda
 * 
 */
public class NEATSurvivalFilterImpl extends AbstractNEATSurvivalFilter {
	/**
	 * Creates a new {@link NEATSurvivalFilterImpl}.
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by this
	 *            {@link NEATSurvivalFilterImpl}
	 */
	public NEATSurvivalFilterImpl(NEATEvolutionFieldMap evolutionFieldMap) {
		super(evolutionFieldMap);
	}
}
