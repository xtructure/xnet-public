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
package com.xtructure.xevolution.operator;

import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.GenomeImpl;
import com.xtructure.xevolution.operator.impl.AbstractOperator;

/**
 * @author Luis Guimbarda
 * 
 */
public class DummyMutateOperator extends AbstractOperator<String> implements MutateOperator<String> {

	private boolean	fail	= false;

	public void failOnNext() {
		this.fail = true;
	}

	/**
	 * @param geneticsFactory
	 */
	public DummyMutateOperator(GeneticsFactory<String> geneticsFactory) {
		super(geneticsFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.MutateOperator#mutate(int,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public Genome<String> mutate(int idNumber, Genome<String> genome) throws OperationFailedException {
		if (fail) {
			fail = false;
			throw new OperationFailedException("");
		}
		return new GenomeImpl(idNumber, genome.getData());
	}
}
