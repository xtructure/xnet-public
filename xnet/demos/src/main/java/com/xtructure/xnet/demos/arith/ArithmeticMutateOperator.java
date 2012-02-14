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
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.impl.AbstractOperator;
import com.xtructure.xutil.RandomUtil;

/**
 * The Class ArithmeticMutateOperator.
 * 
 * @author Luis Guimbarda
 */
public class ArithmeticMutateOperator extends AbstractOperator<String>
		implements MutateOperator<String> {

	/** The mutate rate. */
	private final double mutateRate;

	/**
	 * Instantiates a new arithmetic mutate operator.
	 * 
	 * @param mutateRate
	 *            the mutate rate
	 * @param geneticsFactory
	 *            the genetics factory
	 */
	public ArithmeticMutateOperator(double mutateRate,
			GeneticsFactory<String> geneticsFactory) {
		super(geneticsFactory);
		this.mutateRate = mutateRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.MutateOperator#mutate(int,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public Genome<String> mutate(int idNumber, Genome<String> genome)
			throws OperationFailedException {
		validateArg("genome2 data is expected length", genome.getData()
				.length(), isEqualTo(ArithmeticGenomeDecoder.EXPRESSION_LENGTH));
		String chromosome = genome.getData();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chromosome.length(); i++) {
			char bit = chromosome.charAt(i);
			sb.append(RandomUtil.eventOccurs(mutateRate) ? flip(bit) : bit);
		}
		Genome<String> child = getGeneticsFactory().createGenome(idNumber,
				sb.toString());
		child.validate();
		return child;
	}

	/**
	 * Flip.
	 * 
	 * @param ch
	 *            the ch
	 * @return the string
	 */
	private String flip(char ch) {
		return ch == '0' ? "1" : "0";
	}
}
