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
package com.xtructure.xevolution.evolution.impl;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;

/**
 * @author Luis Guimbarda
 * 
 */
public class DummyEvaluationStrategy extends AbstractEvaluationStrategy<String, String> {

	/**
	 * @param genomeDecoder
	 */
	public DummyEvaluationStrategy(GenomeDecoder<String, String> genomeDecoder) {
		super(genomeDecoder);
	}

	@Override
	public double simulate(Genome<String> genome) {
		// TODO Auto-generated method stub
		return 0;
	}
}
