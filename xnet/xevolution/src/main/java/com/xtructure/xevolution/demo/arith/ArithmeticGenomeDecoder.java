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

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.impl.AbstractGenomeDecoder;

/**
 * Arithmetic 
 * @author Luis Guimbarda
 * 
 */
public class ArithmeticGenomeDecoder extends AbstractGenomeDecoder<String, Integer> implements GenomeDecoder<String, Integer> {
	public static final int	NUMBER_BIT_LENGTH	= 4;
	public static final int	OP_BIT_LENGTH		= 1;
	public static final int	EXPRESSION_LENGTH	= //
												NUMBER_BIT_LENGTH + (9) * (OP_BIT_LENGTH + NUMBER_BIT_LENGTH);

	public static final String translate(String chromosome) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (true) {
			int numStart = i;
			int numEnd = numStart + NUMBER_BIT_LENGTH;
			if (numEnd <= chromosome.length()) {
				sb.append(String.format("%-" + NUMBER_BIT_LENGTH + "d", toNumber(chromosome.substring(numStart, numEnd))));
			} else {
				break;
			}

			int opStart = numEnd;
			int opEnd = opStart + OP_BIT_LENGTH;
			if (opEnd <= chromosome.length()) {
				sb.append(String.format("%-" + OP_BIT_LENGTH + "s", toOp(chromosome.substring(opStart, opEnd))));
			} else {
				break;
			}
			i = opEnd;
		}
		return sb.toString();
	}

	public static final int toNumber(String numGene) {
		validateArg(String.format("numGene is %d bits", NUMBER_BIT_LENGTH), numGene.length(), isEqualTo(NUMBER_BIT_LENGTH));
		return Integer.parseInt(numGene, 2);
	}

	public static final Op toOp(String opGene) {
		validateArg(String.format("opGene is %d bits", OP_BIT_LENGTH), opGene.length(), isEqualTo(OP_BIT_LENGTH));
		return Op.values()[Integer.parseInt(opGene, 2)];
	}

	public enum Op {
		PLUS, MINUS, TIMES, DIVIDE;
		public int apply(int x, int y) {
			switch (this) {
				case PLUS: {
					return x + y;
				}
				case MINUS: {
					return x - y;
				}
				case TIMES: {
					return x * y;
				}
				case DIVIDE: {
					return x / y;
				}
			}
			return 0;
		}

		@Override
		public String toString() {
			switch (this) {
				case PLUS: {
					return "+";
				}
				case MINUS: {
					return "-";
				}
				case TIMES: {
					return "*";
				}
				case DIVIDE: {
					return "/";
				}
			}
			return null;
		}
	}

	@Override
	public Integer decode(Genome<String> genome) {
		String data = genome.getData();
		validateArg("genome data is acceptible length", (data.length() - 4) % 5, isEqualTo(0));

		int acc = toNumber(data.substring(0, NUMBER_BIT_LENGTH));
		for (int i = 4; i < data.length(); i += 5) {
			int opStart = i;
			int opEnd = opStart + OP_BIT_LENGTH;
			int numStart = opEnd;
			int numEnd = numStart + NUMBER_BIT_LENGTH;

			Op op = toOp(data.substring(opStart, opEnd));
			int num = toNumber(data.substring(numStart, numEnd));
			acc = op.apply(acc, num);
		}
		return acc;
	}
}
