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

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.impl.AbstractEvolutionStrategy;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xneat.evolution.NEATEvolutionStrategy;
import com.xtructure.xneat.evolution.NEATReproductionStrategy;
import com.xtructure.xneat.evolution.NEATSpeciationStrategy;
import com.xtructure.xneat.evolution.NEATSurvivalFilter;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.NEATPopulation;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.id.XId;

/**
 * {@link AbstractNEATEvolutionStrategy} implements the
 * {@link NEATEvolutionStrategy} interface and the main NEAT algorithm.
 * 
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link NEATGenome}s
 * @param <T>
 *            phenotype of {@link NEATGenome}s
 */
public abstract class AbstractNEATEvolutionStrategy<D, T> extends AbstractEvolutionStrategy<D, T> implements NEATEvolutionStrategy<D, T> {
	/**
	 * the {@link NEATSpeciationStrategy} used by this
	 * {@link NEATEvolutionStrategy}
	 */
	private final NEATSpeciationStrategy<D>	speciationStrategy;

	/**
	 * Creates a new {@link AbstractEvolutionStrategy}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by this
	 *            {@link NEATEvolutionStrategy}
	 * @param reproductionStrategy
	 *            the {@link NEATReproductionStrategy} used by this
	 *            {@link NEATEvolutionStrategy}
	 * @param evaluationStrategy
	 *            the {@link EvaluationStrategy} used by this
	 *            {@link NEATEvolutionStrategy}
	 * @param survivalFilter
	 *            the {@link NEATSurvivalFilter} used by this
	 *            {@link NEATEvolutionStrategy}
	 * @param speciationStrategy
	 *            the {@link NEATSpeciationStrategy} used by this
	 *            {@link NEATEvolutionStrategy}
	 * @param outputDir
	 *            output directory to which reports are to be written
	 */
	public AbstractNEATEvolutionStrategy(//
			NEATEvolutionFieldMap evolutionFieldMap,//
			NEATReproductionStrategy<D> reproductionStrategy,//
			EvaluationStrategy<D, T> evaluationStrategy,//
			NEATSurvivalFilter survivalFilter,//
			NEATSpeciationStrategy<D> speciationStrategy,//
			File outputDir) {
		super(//
				evolutionFieldMap,//
				reproductionStrategy,//
				evaluationStrategy,//
				survivalFilter,//
				outputDir);
		this.speciationStrategy = speciationStrategy;
	}

	/**
	 * Evolves the given {@link Population} via mutation or crossover,
	 * reevaluates it, and performs any other processing.
	 * <P>
	 * Evolution proceeds as follows:
	 * <ol>
	 * <li>Species are removed from the population as determined by the
	 * {@link NEATSurvivalFilter}; their genomes will not reproduce
	 * <li>Genomes are marked for death by the {@link NEATSurvivalFilter}, but
	 * are still made available for reproduction
	 * <li>A number of children are produced equal to the number genomes dying
	 * this generation, so that the population size remains constant
	 * <li>A round of speciation occurs to place the new genomes into the
	 * appropriate species (or new species as need be)
	 * <li>The population is evaluated
	 * <li>A round of speciation occurs if the number of species is outside the
	 * bounds specified in {@link NEATEvolutionFieldMap}
	 * <li>The population's statistics are updated and a report is made
	 * </ol>
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionStrategy#epoch
	 *      (com.xtructure.xevolution.genetics.Population)
	 */
	@Override
	public void epoch(Population<D> population) {
		getLogger().trace("start %s.epoch(%s)", getClass().getSimpleName(), population);
		// remove species with zero target size and those haven't improved by
		// the species drop-off
		getSurvivalFilter().markDeadSpecies((NEATPopulation<?>) population);
		((NEATPopulation<?>) population).removeDeadSpecies();
		// mark dead genomes (non-elites)
		getSurvivalFilter().markDeadGenomes(population);
		// generate children
		Set<Genome<D>> children = getReproductionStrategy().generateChildren(population);
		// trim each species to elites
		population.removeDeadGenomes();
		// re-populate
		getSpeciationStrategy().speciateChildren(children, population);
		// evaluate population
		getEvaluationStrategy().evaluatePopulation(population);
		// re-speciate if necessary
		getSpeciationStrategy().respeciate(population);
		// update population statistics
		population.incrementAge();
		updateAttributes(population);
		report(population);
		getLogger().trace("end %s.epoch()", getClass().getSimpleName());
	}

	/**
	 * Performs an initial evaluation and any pre-evolution processing on the
	 * given Population. This method is intended to be called once, before any
	 * evolution takes place.
	 * <p>
	 * Initialization proceeds as follows:
	 * <ol>
	 * <li>The population is evaluated
	 * <li>A round of speciation occurs to determine the population's initial
	 * species assignment
	 * <li>The population's statistics are updated and a report is made
	 * </ol>
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionStrategy#initialize
	 *      (com.xtructure.xevolution.genetics.Population)
	 */
	@Override
	public void initialize(Population<D> population) {
		getLogger().trace("start %s.initialize(%s)", getClass().getSimpleName(), population);
		// evaluate fitness of population
		getEvaluationStrategy().evaluatePopulation(population);
		// initial speciation
		getSpeciationStrategy().speciate(population);
		// update stats
		updateAttributes(population);
		report(population);
		getLogger().trace("end %s.initialize()", getClass().getSimpleName());
	}

	/**
	 * Refreshes the given populations statistics and validates it. Also, the
	 * target sizes and elite counts for each species is determined.
	 *
	 * @param population the population
	 */
	@Override
	protected void updateAttributes(Population<D> population) {
		getLogger().trace("start %s.updateAttributes(%s)", getClass().getSimpleName(), population);
		super.updateAttributes(population);
		NEATPopulation<D> pop = (NEATPopulation<D>) population;
		// SET SPECIES TARGET SIZES
		// total of average fitness among species for use in calculating
		// target size
		Map<XId, Double> speciesAverages = new HashMap<XId, Double>();
		double sumAverageFitness = 0.0;
		for (XId speciesId : pop.getSpeciesIds()) {
			double sumFitness = 0.0;
			for (XId genomeId : pop.getSpecies(speciesId)) {
				sumFitness += pop.get(genomeId).getFitness();
			}
			double avg = sumFitness / pop.getSpecies(speciesId).size();
			speciesAverages.put(speciesId, avg);
			sumAverageFitness += avg;
		}
		// assign species' target sizes
		int populationTargetSize = getEvolutionFieldMap().populationSize();
		for (XId speciesId : pop.getSpeciesIds()) {
			int targetSize = (int) ((speciesAverages.get(speciesId) / sumAverageFitness) * getEvolutionFieldMap().populationSize());
			pop.setSpeciesTargetSize(speciesId, targetSize);
			populationTargetSize -= targetSize;
		}
		// ensure consistent population size
		if (populationTargetSize > 0) {
			// some children remain to be assigned; give them to the best
			// species
			XId bestSpeciesId = ((NEATGenome<D>) pop.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID)).getSpeciesId();
			int newTargetSize = pop.getSpeciesTargetSize(bestSpeciesId) + populationTargetSize;
			pop.setSpeciesTargetSize(bestSpeciesId, newTargetSize);
		}
		// SET SPECIES ELITE SIZES
		for (XId speciesId : pop.getSpeciesIds()) {
			if (pop.getSpeciesTargetSize(speciesId) == 0) {
				pop.setSpeciesEliteSize(speciesId, 0);
			} else {
				pop.setSpeciesEliteSize(speciesId,//
						Range.getInstance(1, pop.getSpeciesTargetSize(speciesId)).vet(//
								(int) (pop.getSpeciesTargetSize(speciesId) * getEvolutionFieldMap().eliteProportion())));
			}
		}
		getLogger().trace("end %s.updateAttributes()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xtructure.xevolution.evolution.impl.AbstractEvolutionStrategy#
	 * getSurvivalFilter()
	 */
	@Override
	public NEATSurvivalFilter getSurvivalFilter() {
		return (NEATSurvivalFilter) super.getSurvivalFilter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xtructure.xevolution.evolution.impl.AbstractEvolutionStrategy#
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
	 * com.xtructure.xneat.evolution.NEATEvolutionStrategy#getSpeciationStrategy
	 * ()
	 */
	@Override
	public NEATSpeciationStrategy<D> getSpeciationStrategy() {
		return speciationStrategy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xtructure.xevolution.evolution.impl.AbstractEvolutionStrategy#
	 * getReproductionStrategy()
	 */
	@Override
	public NEATReproductionStrategy<D> getReproductionStrategy() {
		return (NEATReproductionStrategy<D>) super.getReproductionStrategy();
	}
}
