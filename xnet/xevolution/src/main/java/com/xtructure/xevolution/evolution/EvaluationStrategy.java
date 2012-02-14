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

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.Population;

/**
 * The {@link EvaluationStrategy} interface describes the method for evaluating
 * genomes. Classes implementing this interface will decode a given
 * {@link Genome} into an appropriate phenotype for simulation in a problem
 * domain. The result of the simulation will be the double value fitness
 * calculation for that genome.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 * @param <T>
 *            phenotype of {@link Genome}s
 */
public interface EvaluationStrategy<D, T> extends EvolutionObject {
	/**
	 * @param population
	 */
	public void evaluatePopulation(Population<D> population);

	/**
	 * Simulates the phenotype of the given {@link Genome} in the problem
	 * domain, returning its calculated fitness.
	 * 
	 * @param genome
	 * @return the fitness of the genome
	 */
	public double simulate(Genome<D> genome);

	/**
	 * Gets the {@link GenomeDecoder} used by this {@link EvaluationStrategy}.
	 * 
	 * @return the {@link GenomeDecoder} used by this {@link EvaluationStrategy}
	 *         .
	 */
	public GenomeDecoder<D, T> getGenomeDecoder();
}
