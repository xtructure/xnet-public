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
package com.xtructure.xevolution.evolution;

import java.util.Set;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xevolution.operator.OperatorSelecter;

/**
 * The {@link ReproductionStrategy} interface describes the method which
 * produces new child {@link Genome}s for a {@link Population}. Classes
 * implementing this interface will orchestrate the creation of child genomes,
 * selecting which genomes will reproduce and via which of
 * {@link MutateOperator} or {@link CrossoverOperator}.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 */
public interface ReproductionStrategy<D> extends EvolutionObject {
	/**
	 * Creates a set of {@link Genome} children based on the given
	 * {@link Population}. {@link Genome}s are selected for reproduction via
	 * roulette selection, weighted by fitness.
	 * <P>
	 * If when attempting to produce a child no {@link Operator} succeeds, then
	 * a different reproduction attempt is made; all of the returned children
	 * are products of an {@link Operator}.
	 * <P>
	 * The number of children produced is enough to bring the population size to
	 * that specified by the the {@link EvolutionFieldMap}, after dead genomes
	 * have been removed.
	 * 
	 * @param population
	 *            the pool of potential parent genomes
	 * @return the set of {@link Genome} children.
	 */
	public Set<Genome<D>> generateChildren(Population<D> population);

	/**
	 * Gets the {@link OperatorSelecter} of {@link MutateOperator}s used by this
	 * {@link ReproductionStrategy}.
	 * 
	 * @return the {@link OperatorSelecter} of {@link MutateOperator}s.
	 */
	public OperatorSelecter<? extends MutateOperator<D>> getMutateOperators();

	/**
	 * Gets the {@link OperatorSelecter} of {@link CrossoverOperator}s used by
	 * this {@link ReproductionStrategy}.
	 * 
	 * @return the {@link OperatorSelecter} of {@link CrossoverOperator} s.
	 */
	public OperatorSelecter<? extends CrossoverOperator<D>> getCrossoverOperators();

	/**
	 * Creates a new child {@link Genome} by using one of this
	 * {@link ReproductionStrategy}'s {@link MutateOperator}s on the given
	 * genome.
	 * 
	 * @param idNumber
	 *            the instance number of the id for the child genome
	 * @param genome
	 *            the parent genome
	 * @return the child genome, or null if no child could be produced by any
	 *         {@link MutateOperator}
	 */
	public Genome<D> mutate(int idNumber, Genome<D> genome);

	/**
	 * Creates a new child {@link Genome} by using one of this
	 * {@link ReproductionStrategy}'s {@link CrossoverOperator}s on the two
	 * given genomes.
	 * 
	 * @param idNumber
	 *            the instance number of the id for the child genome
	 * @param genome1
	 *            the first parent genome
	 * @param genome2
	 *            the second parent genome
	 * @return the child genome, or null of no child could be produced by any
	 *         {@link CrossoverOperator}
	 */
	public Genome<D> crossover(int idNumber, Genome<D> genome1, Genome<D> genome2);

	/**
	 * Gets the {@link GeneticsFactory} used by this
	 * {@link ReproductionStrategy}.
	 * 
	 * @return the {@link GeneticsFactory} used by this
	 *         {@link ReproductionStrategy}.
	 */
	public GeneticsFactory<D> getGeneticsFactory();

	/**
	 * Gets the {@link EvolutionFieldMap} used by this
	 * {@link ReproductionStrategy} .
	 * 
	 * @return the {@link EvolutionFieldMap} used by this
	 *         {@link ReproductionStrategy} .
	 */
	public EvolutionFieldMap getEvolutionFieldMap();
}
