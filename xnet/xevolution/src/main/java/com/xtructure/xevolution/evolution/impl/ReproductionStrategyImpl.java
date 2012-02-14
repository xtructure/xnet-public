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
package com.xtructure.xevolution.evolution.impl;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.operator.impl.CopyCrossoverOperator;
import com.xtructure.xevolution.operator.impl.CopyMutateOperator;

/**
 * An implementation of {@link ReproductionStrategy}, producing children from
 * {@link Genome}s with String type data.
 * <P>
 * It orchestrates the creation of child genomes, selecting which genomes will
 * reproduce probabilistically, weighted by their fitness, and via which of
 * {@link MutateOperator} or {@link CrossoverOperator} as selected by this
 * {@link ReproductionStrategy}'s {@link OperatorSelecter}s.
 * <P>
 * The {@link ReproductionStrategy} with attempt to create children until the
 * requested count has been reached; if no operator with succeed with any
 * genome, the method will loop. It's recommended to use at least one
 * {@link Operator} that will always succeed, e.g., {@link CopyMutateOperator}
 * or {@link CopyCrossoverOperator}.
 * 
 * @author Luis Guimbarda
 * 
 */
public final class ReproductionStrategyImpl extends AbstractReproductionStrategy<String> {
	/**
	 * Creates a new {@link ReproductionStrategyImpl}
	 * 
	 * @param evolutionFieldMap
	 *            {@link EvolutionFieldMap} used by this
	 *            {@link ReproductionStrategy}
	 * @param geneticsFactory
	 *            {@link GeneticsFactory} used by this
	 *            {@link ReproductionStrategy}
	 * @param crossoverOperatorSelecter
	 *            crossover {@link OperatorSelecter} used by this
	 *            {@link ReproductionStrategy}
	 * @param mutateOperatorSelecter
	 *            mutate {@link OperatorSelecter} used by this
	 *            {@link ReproductionStrategy}
	 */
	public ReproductionStrategyImpl(//
			EvolutionFieldMap evolutionFieldMap,//
			GeneticsFactory<String> geneticsFactory,//
			OperatorSelecter<CrossoverOperator<String>> crossoverOperatorSelecter,//
			OperatorSelecter<MutateOperator<String>> mutateOperatorSelecter) {
		super(//
				evolutionFieldMap,//
				geneticsFactory,//
				crossoverOperatorSelecter,//
				mutateOperatorSelecter);
	}
}
