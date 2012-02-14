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

import java.io.File;

import com.xtructure.xevolution.config.EvolutionConfiguration;
import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;

/**
 * The {@link EvolutionStrategy} interface describes the methods used at the
 * highest level of evolution. Classes implementing this interface run a
 * population of {@link Genome}s through the evolutionary process, using the
 * given {@link EvolutionConfiguration}, {@link EvaluationStrategy},
 * {@link ReproductionStrategy}, and {@link SurvivalFilter}.
 * <P>
 * The evolutionary process begins with a single initialization step, following
 * by repeated epoch steps, until the termination condition in
 * {@link EvolutionConfiguration} is satisfied by the {@link Population} or the
 * {@link EvolutionStrategy} instance itself.
 * <P>
 * At the end of initialization and each epoch, a report is made of the state of
 * the population at that time, which may include writing files to the give
 * output directory.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 * @param <T>
 *            phenotype of {@link Genome}s
 */
public interface EvolutionStrategy<D, T> extends EvolutionObject {
	/**
	 * Performs an initial evaluation and any pre-evolution processing on the
	 * given {@link Population}. This method is intended to be called once,
	 * before any evolution takes place.
	 * 
	 * @param population
	 *            the collection of genomes to initialize
	 */
	public void initialize(Population<D> population);

	/**
	 * Evolves the given {@link Population} via mutation or crossover,
	 * reevaluates it, and performs any other processing.
	 * 
	 * @param population
	 *            the collection of genomes to evolve for one generation
	 */
	public void epoch(Population<D> population);

	/**
	 * Initializes the given {@link Population}, and repeatedly evolves it until
	 * the {@link EvolutionConfiguration}'s termination condition is satisfied by
	 * this {@link EvolutionStrategy} or the given {@link Population}.
	 * 
	 * @param population
	 *            the collection of genomes to evolve
	 */
	public void start(Population<D> population);

	/**
	 * Produces a report of the state of the given {@link Population} at the end
	 * of initialization or any epoch. If this {@link EvolutionStrategy}'s
	 * output directory is non-null, then logs may be written there.
	 * 
	 * @param population
	 *            the population whose statistics are being reported
	 */
	public void report(Population<D> population);

	/**
	 * Gets the {@link EvaluationStrategy} used by this
	 * {@link EvolutionStrategy}.
	 * 
	 * @return the {@link EvaluationStrategy} used by this
	 *         {@link EvolutionStrategy}.
	 */
	public EvaluationStrategy<D, T> getEvaluationStrategy();

	/**
	 * Gets the {@link EvolutionFieldMap} used by this {@link EvolutionStrategy}
	 * .
	 * 
	 * @return the {@link EvolutionFieldMap} used by this
	 *         {@link EvolutionStrategy}.
	 */
	public EvolutionFieldMap getEvolutionFieldMap();

	/**
	 * Gets the {@link ReproductionStrategy} used by this
	 * {@link EvolutionStrategy}.
	 * 
	 * @return the {@link ReproductionStrategy} used by this
	 *         {@link EvolutionStrategy}.
	 */
	public ReproductionStrategy<D> getReproductionStrategy();

	/**
	 * Gets the {@link SurvivalFilter} used by this {@link EvolutionStrategy}.
	 * 
	 * @return the {@link SurvivalFilter} used by this {@link EvolutionStrategy}
	 *         .
	 */
	public SurvivalFilter getSurvivalFilter();

	/**
	 * Gets the {@link File} pointing to the directory to which the population
	 * being evolved will be written after initialization and each epoch.
	 * 
	 * @return the {@link File} pointing to the directory to which the
	 *         population being evolved will be written after initialization and
	 *         each epoch.
	 */
	public File getOutputDir();
}
