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

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.impl.AbstractGenomeDecoder;

/**
 * Arithmetic.
 * 
 * @author Luis Guimbarda
 */
public class ArithmeticGenomeDecoder extends
		AbstractGenomeDecoder<String, Integer> implements
		GenomeDecoder<String, Integer> {

	/** The Constant NUMBER_BIT_LENGTH. */
	public static final int NUMBER_BIT_LENGTH = 4;

	/** The Constant OP_BIT_LENGTH. */
	public static final int OP_BIT_LENGTH = 2;// <- 2 for all Ops, 1 for +/-

	/** The Constant EXPRESSION_LENGTH. */
	public static final int EXPRESSION_LENGTH = NUMBER_BIT_LENGTH + (9)
			* (OP_BIT_LENGTH + NUMBER_BIT_LENGTH);

	/**
	 * Translate.
	 * 
	 * @param chromosome
	 *            the chromosome
	 * @return the string
	 */
	public static final String translate(String chromosome) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (true) {
			int numStart = i;
			int numEnd = numStart + NUMBER_BIT_LENGTH;
			if (numEnd <= chromosome.length()) {
				sb.append(String.format("%-" + NUMBER_BIT_LENGTH + "d",
						toNumber(chromosome.substring(numStart, numEnd))));
			} else {
				break;
			}

			int opStart = numEnd;
			int opEnd = opStart + OP_BIT_LENGTH;
			if (opEnd <= chromosome.length()) {
				sb.append(String.format("%-" + OP_BIT_LENGTH + "s",
						toOp(chromosome.substring(opStart, opEnd))));
			} else {
				break;
			}
			i = opEnd;
		}
		return sb.toString();
	}

	/**
	 * To number.
	 * 
	 * @param numGene
	 *            the num gene
	 * @return the int
	 */
	public static final int toNumber(String numGene) {
		validateArg(String.format("numGene is %d bits", NUMBER_BIT_LENGTH),
				numGene.length(), isEqualTo(NUMBER_BIT_LENGTH));
		return Integer.parseInt(numGene, 2);
	}

	/**
	 * To op.
	 * 
	 * @param opGene
	 *            the op gene
	 * @return the op
	 */
	public static final Op toOp(String opGene) {
		validateArg(String.format("opGene is %d bits", OP_BIT_LENGTH),
				opGene.length(), isEqualTo(OP_BIT_LENGTH));
		return Op.values()[Integer.parseInt(opGene, 2)];
	}

	/**
	 * The Enum Op.
	 */
	public enum Op {

		/** The PLUS. */
		PLUS,
		/** The MINUS. */
		MINUS,
		/** The TIMES. */
		TIMES,
		/** The DIVIDE. */
		DIVIDE;

		/**
		 * Apply.
		 * 
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @return the int
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GenomeDecoder#decode(com.xtructure.
	 * xevolution.genetics.Genome)
	 */
	@Override
	public Integer decode(Genome<String> genome) {
		String data = genome.getData();
		validateArg("genome data is acceptible length", data.length(),
				isEqualTo(EXPRESSION_LENGTH));

		int acc = toNumber(data.substring(0, NUMBER_BIT_LENGTH));
		for (int i = NUMBER_BIT_LENGTH; i < data.length(); i += NUMBER_BIT_LENGTH
				+ OP_BIT_LENGTH) {
			int opStart = i;
			int opEnd = opStart + OP_BIT_LENGTH;
			int numStart = opEnd;
			int numEnd = numStart + NUMBER_BIT_LENGTH;

			Op op = toOp(data.substring(opStart, opEnd));
			int num = toNumber(data.substring(numStart, numEnd));
			try {
				acc = op.apply(acc, num);
			} catch (ArithmeticException e) {
				return Integer.MIN_VALUE;
			}
		}
		return acc;
	}
}
