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
package com.xtructure.xevolution.genetics.impl;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xutil.XLogger;

/**
 * @author Luis Guimbarda
 * 
 */
public class DummyGenomeDecoder implements GenomeDecoder<String, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GenomeDecoder#decode(com.xtructure.
	 * xevolution.genetics.Genome)
	 */
	@Override
	public String decode(Genome<String> genome) {
		return genome.getData();
	}

	@Override
	public XLogger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

}
