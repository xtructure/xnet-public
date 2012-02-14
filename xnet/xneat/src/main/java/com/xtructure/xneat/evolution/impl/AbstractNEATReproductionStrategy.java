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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.xtructure.xevolution.evolution.impl.AbstractReproductionStrategy;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.operator.impl.CopyCrossoverOperator;
import com.xtructure.xevolution.operator.impl.CopyMutateOperator;
import com.xtructure.xneat.evolution.NEATReproductionStrategy;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.NEATPopulation;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.id.XId;

/**
 * {@link AbstractNEATReproductionStrategy} implements the
 * {@link NEATReproductionStrategy} interface. It orchestrates the creation of
 * child genomes, selecting which genomes will reproduce and via which of
 * {@link MutateOperator} or {@link CrossoverOperator}.
 * <P>
 * Calls to
 * {@link AbstractNEATReproductionStrategy#generateChildren(Population)} ignore
 * the given count, instead producing from each species a number of children
 * determined by that species number of elites and it's target size. Also, when
 * using a {@link CrossoverOperator}, with a certain probability (specified by
 * {@link NEATEvolutionFieldMap#interspeciesCrossoverProbability()}) a genome
 * may reproduce with another in a different species.
 * <P>
 * The {@link NEATReproductionStrategy} with attempt to create children until
 * the requested count has been reached; if no operator with succeed with any
 * genome, the method will loop. It's recommended to use at least one
 * {@link Operator} that will always succeed, e.g., {@link CopyMutateOperator}
 * or {@link CopyCrossoverOperator}.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractNEATReproductionStrategy<D> extends AbstractReproductionStrategy<D> implements NEATReproductionStrategy<D> {
	/**
	 * Creates a new {@link AbstractNEATReproductionStrategy}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by this
	 *            {@link NEATReproductionStrategy}
	 * @param geneticsFactory
	 *            the {@link NEATGeneticsFactory} used by this
	 *            {@link NEATReproductionStrategy}
	 * @param crossoverOperatorSelecter
	 *            the Operator crossover {@link OperatorSelecter} used by this
	 *            {@link NEATReproductionStrategy}
	 * @param mutateOperatorSelecter
	 *            mutate {@link OperatorSelecter} used by this
	 *            {@link NEATReproductionStrategy}
	 */
	public AbstractNEATReproductionStrategy(//
			NEATEvolutionFieldMap evolutionFieldMap,//
			NEATGeneticsFactory<D> geneticsFactory,//
			OperatorSelecter<CrossoverOperator<D>> crossoverOperatorSelecter,//
			OperatorSelecter<MutateOperator<D>> mutateOperatorSelecter) {
		super(evolutionFieldMap, geneticsFactory, crossoverOperatorSelecter, mutateOperatorSelecter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.evolution.impl.AbstractReproductionStrategy#
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
	 * com.xtructure.xevolution.evolution.impl.AbstractReproductionStrategy#
	 * getGeneticsFactory()
	 */
	@Override
	public NEATGeneticsFactory<D> getGeneticsFactory() {
		return (NEATGeneticsFactory<D>) super.getGeneticsFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.impl.AbstractReproductionStrategy#generateChildren
	 * (com.xtructure.xevolution.genetics.Population)
	 */
	@Override
	public Set<Genome<D>> generateChildren(Population<D> population) {
		getLogger().trace("start %s.generateChildren(%s)", getClass().getSimpleName(), population);
		NEATPopulation<D> pop = (NEATPopulation<D>) population;
		Set<Genome<D>> children = new HashSet<Genome<D>>();
		int genomeNumber = pop.getGenomeIdNumber();
		for (XId speciesId : pop.getSpeciesIds()) {
			// generate enough children to fill the species' target
			// early generations may not fill elite size, so use actual size
			int speciesTarget = pop.getSpeciesTargetSize(speciesId) - Math.min(pop.getSpecies(speciesId).size(), pop.getSpeciesEliteSize(speciesId));
			int target = children.size() + speciesTarget;
			while (children.size() < target) {
				// select a genome for reproduction
				Genome<D> genome1 = pop.get(RandomUtil.select(pop.getSpecies(speciesId)));
				Genome<D> child = null;
				// select the type of reproduction
				if (pop.getSpecies(speciesId).size() == 1 || RandomUtil.eventOccurs(getEvolutionFieldMap().mutationProbability())) {
					child = mutate(genomeNumber, genome1);
				} else {
					// select second genome with which to mate
					Genome<D> genome2;
					if (pop.getSpeciesIds().size() > 1 && RandomUtil.eventOccurs(getEvolutionFieldMap().interspeciesCrossoverProbability())) {
						XId otherSpeciesId = RandomUtil.select(pop.getSpeciesIds(), speciesId);
						genome2 = pop.get(RandomUtil.select(pop.getSpecies(otherSpeciesId)));
					} else {
						genome2 = pop.get(RandomUtil.select(pop.getSpecies(speciesId), Collections.singleton(genome1.getId())));
					}
					child = crossover(genomeNumber, genome1, genome2);
				}
				if (child != null) {
					// reproduction succeeded, add child
					children.add(child);
					genomeNumber++;
				} else {
					// // reproduction failed, carry over genome
					// children.add(genome1);
					// reproduction failed, try again
				}
			}
		}
		getLogger().trace("will return: %s", children);
		getLogger().trace("end %s.generateChildren()", getClass().getSimpleName());
		return children;
	}
}
