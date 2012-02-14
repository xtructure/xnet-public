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
public class ArithmeticMutateOperator extends AbstractOperator<String> implements MutateOperator<String> {
	
	/** The mutate rate. */
	private final double	mutateRate;

	/**
	 * Instantiates a new arithmetic mutate operator.
	 *
	 * @param mutateRate the mutate rate
	 * @param geneticsFactory the genetics factory
	 */
	public ArithmeticMutateOperator(double mutateRate, GeneticsFactory<String> geneticsFactory) {
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
	public Genome<String> mutate(int idNumber, Genome<String> genome) throws OperationFailedException {
		validateArg("genome2 data is expected length", genome.getData().length(), isEqualTo(ArithmeticGenomeDecoder.EXPRESSION_LENGTH));
		String chromosome = genome.getData();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chromosome.length(); i++) {
			char bit = chromosome.charAt(i);
			sb.append(RandomUtil.eventOccurs(mutateRate) ? flip(bit) : bit);
		}
		Genome<String> child = getGeneticsFactory().createGenome(idNumber, sb.toString());
		child.validate();
		return child;
	}

	/**
	 * Flip.
	 *
	 * @param ch the ch
	 * @return the string
	 */
	private String flip(char ch) {
		return ch == '0' ? "1" : "0";
	}
}
