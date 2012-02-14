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
	 * @param evolutionFieldMap the evolution field map
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
		return super.createGenome(idNumber, newBitString(ArithmeticGenomeDecoder.EXPRESSION_LENGTH));
	}

	/**
	 * New bit string.
	 *
	 * @param length the length
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
