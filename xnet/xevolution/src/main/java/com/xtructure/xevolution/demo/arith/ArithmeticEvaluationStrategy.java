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
package com.xtructure.xevolution.demo.arith;

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.impl.AbstractEvaluationStrategy;
import com.xtructure.xevolution.genetics.Genome;

/**
 * @author Luis Guimbarda
 * 
 */
public class ArithmeticEvaluationStrategy extends AbstractEvaluationStrategy<String, Integer> implements EvaluationStrategy<String, Integer> {
	private final int	target;

	public ArithmeticEvaluationStrategy(int target) {
		super(new ArithmeticGenomeDecoder());
		this.target = target;
	}

	@Override
	public double simulate(Genome<String> genome) {
		int num = getGenomeDecoder().decode(genome);
		double fitness = 1.0 / (1 + Math.abs(target - num));
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, fitness);
		return fitness;
	}
}
