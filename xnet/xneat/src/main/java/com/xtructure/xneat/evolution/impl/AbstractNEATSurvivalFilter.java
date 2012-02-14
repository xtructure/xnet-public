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

import static com.xtructure.xutil.valid.ValidateUtils.isNotEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.evolution.impl.AbstractSurvivalFilter;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xneat.evolution.NEATSurvivalFilter;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.NEATPopulation;
import com.xtructure.xutil.id.XId;

/**
 * An implementation of {@link NEATSurvivalFilter}, where it marks dead
 * {@link NEATGenome}s and species in a {@link NEATPopulation} with a
 * {@link GeneMap} type data.
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
public abstract class AbstractNEATSurvivalFilter extends AbstractSurvivalFilter implements NEATSurvivalFilter {
	/**
	 * Creates a new {@link AbstractNEATSurvivalFilter}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by this
	 *            {@link NEATSurvivalFilter}
	 */
	public AbstractNEATSurvivalFilter(NEATEvolutionFieldMap evolutionFieldMap) {
		super(evolutionFieldMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xtructure.xevolution.evolution.impl.AbstractSurvivalFilter#
	 * getEvolutionFieldMap()
	 */
	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap() {
		return (NEATEvolutionFieldMap) super.getEvolutionFieldMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xneat.evolution.NEATSurvivalFilter#markDeadSpecies(com.
	 * xtructure.xneat.genetics.NEATPopulation)
	 */
	@Override
	public void markDeadSpecies(NEATPopulation<?> population) {
		getLogger().trace("start %s.markDeadSpecies(%s)", getClass().getSimpleName(), population);
		XId fittestSpecies = ((NEATGenome<?>) population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID)).getSpeciesId();
		for (XId speciesId : population.getSpeciesIds()) {
			if (fittestSpecies.equals(speciesId)) {
				// species contains population's best, keep it.
				continue;
			}
			if (population.getSpeciesTargetSize(speciesId) == 0) {
				// species has zero target size; it dies of weakness
				population.markSpeciesDead(speciesId);
				continue;
			}
			// TODO : implement species dropoff
			// long gensWithNoImprovement = specie.getAge() -
			// specie.getAgeLastImproved();
			// if (gensWithNoImprovement >=
			// getEvolutionFieldMap().speciesDropoffAge()) {
			// // species has gone too long without improvement; it dies of
			// // stagnation
			// specie.markForDeath();
			// continue;
			// }
		}
		getLogger().trace("end %s.markDeadSpecies()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.evolution.SurvivalFilter#markDeadGenomes(com
	 * .xtructure.xevolution.genetics.Population)
	 */
	@Override
	public void markDeadGenomes(Population<?> population) {
		getLogger().trace("start %s.markDeadGenomes(%s)", getClass().getSimpleName(), population);
		NEATPopulation<?> pop = (NEATPopulation<?>) population;
		for (XId speciesId : pop.getSpeciesIds()) {
			List<Genome<?>> genomes = new ArrayList<Genome<?>>();
			for (XId genomeId : pop.getSpecies(speciesId)) {
				genomes.add(population.get(genomeId));
			}
			Collections.sort(genomes, Genome.BY_FITNESS_DESC);
			int size = pop.getSpecies(speciesId).size();
			int elite = pop.getSpeciesEliteSize(speciesId);
			if (size > elite) {
				validateState("elites is not 0", pop.getSpeciesEliteSize(speciesId), isNotEqualTo(0));
				for (Genome<?> genome : genomes.subList(elite, size)) {
					genome.markForDeath();
				}
			}
		}
		getLogger().trace("end %s.markDeadGenomess()", getClass().getSimpleName());
	}
}
