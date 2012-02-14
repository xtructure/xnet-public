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

import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.operator.impl.CopyCrossoverOperator;
import com.xtructure.xevolution.operator.impl.CopyMutateOperator;
import com.xtructure.xneat.evolution.NEATReproductionStrategy;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.NEATGenome;

/**
 * {@link NEATReproductionStrategyImpl} implements the
 * {@link NEATReproductionStrategy} interface for use with {@link NEATGenome}s
 * with a data payload of type {@link GeneMap}. It orchestrates the creation of
 * child genomes, selecting which genomes will reproduce and via which of
 * {@link MutateOperator} or {@link CrossoverOperator}.
 * <P>
 * Calls to {@link NEATReproductionStrategyImpl#generateChildren(Population)}
 * ignore the given count, instead producing from each species a number of
 * children determined by that species number of elites and it's target size.
 * Also, when using a {@link CrossoverOperator}, with a certain probability
 * (specified by
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
public class NEATReproductionStrategyImpl extends AbstractNEATReproductionStrategy<GeneMap> {
	/**
	 * Creates a new {@link NEATReproductionStrategyImpl}
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
	public NEATReproductionStrategyImpl(//
			NEATEvolutionFieldMap evolutionFieldMap,//
			NEATGeneticsFactory<GeneMap> geneticsFactory,//
			OperatorSelecter<CrossoverOperator<GeneMap>> crossoverOperatorSelecter,//
			OperatorSelecter<MutateOperator<GeneMap>> mutateOperatorSelecter) {
		super(evolutionFieldMap, geneticsFactory, crossoverOperatorSelecter, mutateOperatorSelecter);
	}
}
