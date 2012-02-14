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

import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.impl.AbstractOperator;
import com.xtructure.xutil.RandomUtil;

/**
 * The Class ArithmeticCrossoverOperator.
 * 
 * @author Luis Guimbarda
 */
public class ArithmeticCrossoverOperator extends AbstractOperator<String>
		implements CrossoverOperator<String> {

	/**
	 * Instantiates a new arithmetic crossover operator.
	 * 
	 * @param geneticsFactory
	 *            the genetics factory
	 */
	public ArithmeticCrossoverOperator(GeneticsFactory<String> geneticsFactory) {
		super(geneticsFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.CrossoverOperator#crossover(int,
	 * com.xtructure.xevolution.genetics.Genome,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public Genome<String> crossover(int idNumber, Genome<String> genome1,
			Genome<String> genome2) throws OperationFailedException {
		validateArg("genome1 data is expected length", genome1.getData()
				.length(), isEqualTo(ArithmeticGenomeDecoder.EXPRESSION_LENGTH));
		validateArg("genome2 data is expected length", genome2.getData()
				.length(), isEqualTo(ArithmeticGenomeDecoder.EXPRESSION_LENGTH));
		String chromosome1 = genome1.getData();
		String chromosome2 = genome2.getData();
		int index = RandomUtil
				.nextInteger(ArithmeticGenomeDecoder.EXPRESSION_LENGTH);
		String pre1 = chromosome1.substring(0, index);
		String post1 = chromosome1.substring(index);
		String pre2 = chromosome2.substring(0, index);
		String post2 = chromosome2.substring(index);
		Genome<String> child = null;
		if (RandomUtil.eventOccurs(0.5)) {
			child = getGeneticsFactory().createGenome(idNumber, pre1 + post2);
		} else {
			child = getGeneticsFactory().createGenome(idNumber, pre2 + post1);
		}
		child.validate();
		return child;
	}
}
