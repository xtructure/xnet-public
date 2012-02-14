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

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xneat.evolution.NEATEvolutionStrategy;
import com.xtructure.xneat.evolution.NEATReproductionStrategy;
import com.xtructure.xneat.evolution.NEATSpeciationStrategy;
import com.xtructure.xneat.evolution.NEATSurvivalFilter;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;

/**
 * {@link AbstractNEATEvolutionStrategy} implements the
 * {@link NEATEvolutionStrategy} interface and the main NEAT algorithm for use
 * with {@link NEATGenome}s whose data payload is of type {@link GeneMap}.
 * 
 * @author Luis Guimbarda
 * 
 * @param <T>
 *            phenotype of {@link NEATGenome}s
 */
public class NEATEvolutionStrategyImpl<T> extends AbstractNEATEvolutionStrategy<GeneMap, T> {
	/**
	 * Creates a new {@link NEATEvolutionStrategyImpl}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by this
	 *            {@link NEATEvolutionStrategyImpl}
	 * @param reproductionStrategy
	 *            the {@link NEATReproductionStrategy} used by this
	 *            {@link NEATEvolutionStrategyImpl}
	 * @param evaluationStrategy
	 *            the {@link EvaluationStrategy} used by this
	 *            {@link NEATEvolutionStrategyImpl}
	 * @param survivalFilter
	 *            the {@link NEATSurvivalFilter} used by this
	 *            {@link NEATEvolutionStrategyImpl}
	 * @param speciationStrategy
	 *            the {@link NEATSpeciationStrategy} used by this
	 *            {@link NEATEvolutionStrategyImpl}
	 * @param outputDir
	 *            output directory to which reports are to be written
	 */
	public NEATEvolutionStrategyImpl(//
			NEATEvolutionFieldMap evolutionFieldMap,//
			NEATReproductionStrategy<GeneMap> reproductionStrategy,//
			EvaluationStrategy<GeneMap, T> evaluationStrategy,//
			NEATSurvivalFilter survivalFilter,//
			NEATSpeciationStrategy<GeneMap> speciationStrategy,//
			File outputDir) {
		super(//
				evolutionFieldMap,//
				reproductionStrategy,//
				evaluationStrategy,//
				survivalFilter,//
				speciationStrategy,//
				outputDir);
	}
}
