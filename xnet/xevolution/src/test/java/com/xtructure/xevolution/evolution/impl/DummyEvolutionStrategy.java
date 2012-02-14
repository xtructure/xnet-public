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
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.genetics.Population;

/**
 * @author Luis Guimbarda
 * 
 */
public class DummyEvolutionStrategy extends AbstractEvolutionStrategy<String, String> {
	public DummyEvolutionStrategy(//
			EvolutionFieldMap evolutionFieldMap,//
			ReproductionStrategyImpl reproductionStrategy,//
			EvaluationStrategy<String, String> evaluationStrategy,//
			SurvivalFilterImpl survivalFilter) {
		super(//
				evolutionFieldMap,//
				reproductionStrategy,//
				evaluationStrategy,//
				survivalFilter,//
				null);
	}

	private String	trace	= "";

	/**
	 * @return the trace
	 */
	public String getTrace() {
		return trace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.EvolutionStrategy#epoch(com.xtructure.xevolution
	 * .genetics.Population)
	 */
	@Override
	public void epoch(Population<String> population) {
		trace += "e";
		super.epoch(population);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xtructure.xevolution.EvolutionStrategy#initialize(com.xtructure.
	 * xevolution.genetics.Population)
	 */
	@Override
	public void initialize(Population<String> population) {
		trace += "i";
		super.initialize(population);
	}

	@Override
	public void report(Population<String> population) {
	// nothing
	}
}
