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

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.Population;

/**
 * {@link AbstractEvaluationStrategy} implements the getters for
 * {@link EvaluationStrategy}s.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 * @param <T>
 *            phenotype of {@link Genome}s
 */
public abstract class AbstractEvaluationStrategy<D, T> extends AbstractEvolutionObject implements EvaluationStrategy<D, T> {
	/** {@link GenomeDecoder} used by this {@link EvolutionStrategy} */
	private final GenomeDecoder<D, T>	genomeDecoder;

	/**
	 * Creates a new {@link AbstractEvolutionStrategy}.
	 * 
	 * @param genomeDecoder
	 *            the {@link GenomeDecoder} used by this
	 *            {@link AbstractEvolutionStrategy}
	 */
	public AbstractEvaluationStrategy(GenomeDecoder<D, T> genomeDecoder) {
		this.genomeDecoder = genomeDecoder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.EvaluationStrategy#getGenomeDecoder()
	 */
	@Override
	public GenomeDecoder<D, T> getGenomeDecoder() {
		return genomeDecoder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.evolution.EvaluationStrategy#evaluatePopulation
	 * (com.xtructure.xevolution.genetics.Population)
	 */
	@Override
	public void evaluatePopulation(Population<D> population) {
		for (Genome<D> genome : population) {
			genome.setFitness(simulate(genome));
		}
	}
}
