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

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.genetics.impl.GenomeImpl;
import com.xtructure.xutil.RandomUtil;

/**
 * A factory for creating ArithmeticGenetics objects.
 * 
 * @author Luis Guimbarda
 */
public class ArithmeticGeneticsFactory extends GeneticsFactoryImpl {

	/**
	 * Instantiates a new arithmetic genetics factory.
	 * 
	 * @param evolutionFieldMap
	 *            the evolution field map
	 */
	public ArithmeticGeneticsFactory(EvolutionFieldMap evolutionFieldMap) {
		super(evolutionFieldMap, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createGenome(int)
	 */
	@Override
	public GenomeImpl createGenome(int idNumber) {
		return super.createGenome(idNumber,
				newBitString(ArithmeticGenomeDecoder.EXPRESSION_LENGTH));
	}

	/**
	 * New bit string.
	 * 
	 * @param length
	 *            the length
	 * @return the string
	 */
	public static String newBitString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(RandomUtil.nextBoolean() ? "0" : "1");
		}
		return sb.toString();
	}
}
