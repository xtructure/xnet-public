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

import java.io.File;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;

/**
 * An implementation of {@link EvolutionStrategy}, where the data of the
 * {@link Genome}s are of type String.
 * <P>
 * The evolutionary process begins with a single initialization step, evaluating
 * each genome in the population. This is followed by repeated epoch steps,
 * during which all genomes may participate in reproduction to population the
 * next generation (though they likelihood of their reproducing is weighted by
 * their fitness). Enough children are produced to replace those genomes that
 * didn't survive the last generation. The population is evalutated again, and
 * the process is repeated until the termination condition in
 * {@link EvolutionFieldMap} is satisfied by the {@link Population} or the
 * {@link EvolutionStrategy} instance itself.
 * <P>
 * At the end of initialization and each epoch, a report is made of the state of
 * the population at that time, which may include writing files to the give
 * output directory.
 * 
 * @author Luis Guimbarda
 * 
 * @param <T>
 *            phenotype of {@link Genome}s
 */
public final class EvolutionStrategyImpl<T> extends AbstractEvolutionStrategy<String, T> {
	/**
	 * Creates a new {@link EvolutionStrategyImpl}.
	 * 
	 * @param evolutionFieldMap
	 *            the {@link EvolutionFieldMap} used by this
	 *            {@link EvolutionStrategy}
	 * @param reproductionStrategy
	 *            the {@link ReproductionStrategy} used by this
	 *            {@link EvolutionStrategy}
	 * @param evaluationStrategy
	 *            the {@link EvaluationStrategy} used by this
	 *            {@link EvolutionStrategy}
	 * @param survivalFilter
	 *            the {@link SurvivalFilter} used by this
	 *            {@link EvolutionStrategy}
	 */
	public EvolutionStrategyImpl(//
			EvolutionFieldMap evolutionFieldMap,//
			ReproductionStrategy<String> reproductionStrategy,//
			EvaluationStrategy<String, T> evaluationStrategy,//
			SurvivalFilter survivalFilter,//
			File outputDir) {
		super(//
				evolutionFieldMap,//
				reproductionStrategy,//
				evaluationStrategy,//
				survivalFilter,//
				outputDir);
	}
}
