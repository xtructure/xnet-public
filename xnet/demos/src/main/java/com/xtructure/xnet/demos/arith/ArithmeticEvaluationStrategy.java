/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.xtructure.xnet.demos.arith;

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.impl.AbstractEvaluationStrategy;
import com.xtructure.xevolution.genetics.Genome;

/**
 * The Class ArithmeticEvaluationStrategy.
 * 
 * @author Luis Guimbarda
 */
public class ArithmeticEvaluationStrategy extends
		AbstractEvaluationStrategy<String, Integer> implements
		EvaluationStrategy<String, Integer> {

	/** The target. */
	private final int target;

	/**
	 * Instantiates a new arithmetic evaluation strategy.
	 * 
	 * @param target
	 *            the target
	 */
	public ArithmeticEvaluationStrategy(int target) {
		super(new ArithmeticGenomeDecoder());
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.evolution.EvaluationStrategy#simulate(com.xtructure
	 * .xevolution.genetics.Genome)
	 */
	@Override
	public double simulate(Genome<String> genome) {
		int num = getGenomeDecoder().decode(genome);
		double fitness = 1.0 / (1 + Math.abs(target - num));
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, fitness);
		return fitness;
	}
}
