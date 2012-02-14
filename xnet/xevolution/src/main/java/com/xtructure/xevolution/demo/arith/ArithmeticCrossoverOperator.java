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
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.impl.AbstractOperator;
import com.xtructure.xutil.RandomUtil;

/**
 * @author Luis Guimbarda
 * 
 */
public class ArithmeticCrossoverOperator extends AbstractOperator<String> implements CrossoverOperator<String> {

	/**
	 * @param geneticsFactory
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
	public Genome<String> crossover(int idNumber, Genome<String> genome1, Genome<String> genome2) throws OperationFailedException {
		validateArg("genome1 data is expected length", genome1.getData().length(), isEqualTo(ArithmeticGenomeDecoder.EXPRESSION_LENGTH));
		validateArg("genome2 data is expected length", genome2.getData().length(), isEqualTo(ArithmeticGenomeDecoder.EXPRESSION_LENGTH));
		String chromosome1 = genome1.getData();
		String chromosome2 = genome2.getData();
		int index = RandomUtil.nextInteger(ArithmeticGenomeDecoder.EXPRESSION_LENGTH);
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
