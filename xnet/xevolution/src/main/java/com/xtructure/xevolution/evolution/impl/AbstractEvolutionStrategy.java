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
import java.io.IOException;
import java.util.Set;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;

/**
 * {@link AbstractEvolutionStrategy} implements the {@link EvolutionStrategy}
 * interface. It runs a population of {@link Genome}s through the evolutionary
 * process, using the given {@link EvolutionFieldMap},
 * {@link EvaluationStrategy}, {@link ReproductionStrategy}, and
 * {@link SurvivalFilter}.
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
 * <P>
 * Subclasses may override this basic evolutionary behavior.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used by {@link Genome}s
 * @param <T>
 *            phenotype of {@link Genome}s
 */
public abstract class AbstractEvolutionStrategy<D, T> extends AbstractEvolutionObject implements EvolutionStrategy<D, T> {
	/** */
	private final EvolutionFieldMap			evolutionFieldMap;
	/** {@link ReproductionStrategy} used by this {@link EvolutionStrategy} */
	private final ReproductionStrategy<D>	reproductionStrategy;
	/** {@link EvaluationStrategy} used by this {@link EvolutionStrategy} */
	private final EvaluationStrategy<D, T>	evaluationStrategy;
	/** {@link SurvivalFilter} used by this {@link EvolutionStrategy} */
	private final SurvivalFilter			survivalFilter;
	/** output directory to which reports are to be written */
	private final File						outputDir;

	/**
	 * Creates a new {@link AbstractEvolutionStrategy}
	 * 
	 * @param evolutionFieldMap
	 *            {@link EvolutionFieldMap} used by this
	 *            {@link EvolutionStrategy}
	 * @param reproductionStrategy
	 *            {@link ReproductionStrategy} used by this
	 *            {@link EvolutionStrategy}
	 * @param evaluationStrategy
	 *            {@link EvaluationStrategy} used by this
	 *            {@link EvolutionStrategy}
	 * @param survivalFilter
	 *            {@link SurvivalFilter} used by this {@link EvolutionStrategy}
	 * @param outputDir
	 *            output directory to which reports are to be written
	 */
	public AbstractEvolutionStrategy(//
			EvolutionFieldMap evolutionFieldMap,//
			ReproductionStrategy<D> reproductionStrategy,//
			EvaluationStrategy<D, T> evaluationStrategy,//
			SurvivalFilter survivalFilter,//
			File outputDir) {
		this.evolutionFieldMap = evolutionFieldMap;
		this.reproductionStrategy = reproductionStrategy;
		this.evaluationStrategy = evaluationStrategy;
		this.survivalFilter = survivalFilter;
		this.outputDir = outputDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.EvolutionStrategy#start(com.xtructure.xevolution
	 * .genetics.Population)
	 */
	@Override
	public void start(Population<D> population) {
		getLogger().trace("begin %s.start()", getClass().getSimpleName());

		initialize(population);
		while (!getEvolutionFieldMap().terminationCondition().isSatisfiedBy(this)//
				&& !getEvolutionFieldMap().terminationCondition().isSatisfiedBy(population)) {
			epoch(population);
		}

		getLogger().trace("end %s.start()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.EvolutionStrategy#report(com.xtructure.xevolution
	 * .genetics.Population)
	 */
	@Override
	public void report(Population<D> population) {
		getLogger().trace("begin %s.report()", getClass().getSimpleName());

		getLogger().info(population.toString());
		getLogger().info("fittest : " + population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID));
		getLogger().info("fitness : " + population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID).getFitness());
		if (getOutputDir() != null) {
			try {
				population.write(outputDir);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}

		getLogger().trace("end %s.report()", getClass().getSimpleName());
	}

	/**
	 * Refreshes the given populations statistics and validates it.
	 * 
	 * @param population
	 *            the population whose statistics are being updated
	 */
	protected void updateAttributes(Population<D> population) {
		getLogger().trace("begin %s.updateAttributes()", getClass().getSimpleName());

		population.refreshStats();
		population.validate();

		getLogger().trace("end %s.updateAttributes()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.EvolutionStrategy#getEvaluationStrategy()
	 */
	@Override
	public EvaluationStrategy<D, T> getEvaluationStrategy() {
		return evaluationStrategy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.evolution.EvolutionStrategy#getEvolutionFieldMap
	 * ()
	 */
	@Override
	public EvolutionFieldMap getEvolutionFieldMap() {
		return evolutionFieldMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.EvolutionStrategy#getReproductionStrategy()
	 */
	@Override
	public ReproductionStrategy<D> getReproductionStrategy() {
		return reproductionStrategy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.EvolutionStrategy#getSurvivalFilter()
	 */
	@Override
	public SurvivalFilter getSurvivalFilter() {
		return survivalFilter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.EvolutionStrategy#getOutputDir()
	 */
	@Override
	public File getOutputDir() {
		return outputDir;
	}

	/**
	 * Evolves the given {@link Population} via mutation or crossover,
	 * reevaluates it, and performs any other processing.
	 * <p>
	 * Evolution proceeds as follows:
	 * <ol>
	 * <li>Genomes are marked for death by the {@link SurvivalFilter}, but
	 * remain available for reproduction
	 * <li>A number of children are produced equal to the number of genomes not
	 * surviving this generation
	 * <li>The marked genomes are removed from the population and replaced by
	 * the children
	 * <li>The population is evaluated
	 * <li>The population's statis are updated and a report is made
	 * </ol>
	 * 
	 * @see EvolutionStrategy#epoch(Population)
	 */
	@Override
	public void epoch(Population<D> population) {
		getLogger().trace("begin %s.epoch(%s)", getClass().getSimpleName(), population);

		// mark genomes for death
		getSurvivalFilter().markDeadGenomes(population);

		// produce children
		Set<Genome<D>> children = getReproductionStrategy().generateChildren(population);

		// replace dead genomes
		population.removeDeadGenomes();
		population.addAll(children);

		// evaluate population
		getEvaluationStrategy().evaluatePopulation(population);

		// update stats and report
		updateAttributes(population);

		// next generation
		population.incrementAge();

		report(population);

		getLogger().trace("end %s.epoch()", getClass().getSimpleName());
	}

	/**
	 * Performs an initial evaluation and any pre-evolution processing on the
	 * given {@link Population}. This method is intended to be called once,
	 * before any evolution takes place.
	 * <p>
	 * Initialization proceeds as follows:
	 * <ol>
	 * <li>The population is evaluated
	 * <li>The population's statistics are updated and a report is made.
	 * </ol>
	 * 
	 * @see com.xtructure.xevolution.evolution.EvolutionStrategy#initialize(com.xtructure.xevolution.genetics.Population)
	 */
	@Override
	public void initialize(Population<D> population) {
		getLogger().trace("begin %s.initialize(%s)", getClass().getSimpleName(), population);

		// evaluate population
		getEvaluationStrategy().evaluatePopulation(population);

		// update stats and report
		updateAttributes(population);

		report(population);

		getLogger().trace("end %s.initialize()", getClass().getSimpleName());
	}
}
