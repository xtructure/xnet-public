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
package com.xtructure.xevolution.operator.impl;

import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.Operator;

/**
 * {@link CopyMutateOperator} is an implementation of the {@link MutateOperator}
 * interface that duplicates the parent's data when creating a child
 * {@link Genome}.
 * <P>
 * Note that {@link CopyMutateOperator} never fails to produce a child.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used by {@link Genome}s on which this operator is
 *            applied.
 */
public class CopyMutateOperator<D> extends AbstractOperator<D> implements MutateOperator<D> {
	/**
	 * Creates a new {@link CopyMutateOperator}.
	 * 
	 * @param geneticsFactory
	 *            the {@link GeneticsFactory} used by this {@link Operator}
	 */
	public CopyMutateOperator(GeneticsFactory<D> geneticsFactory) {
		super(geneticsFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.MutateOperator#mutate(int,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public Genome<D> mutate(int idNumber, Genome<D> genome) throws OperationFailedException {
		getLogger().trace("begin %s.mutate(%s, %s)", getClass().getSimpleName(), idNumber, genome);

		Genome<D> rVal = getGeneticsFactory().createGenome(idNumber, genome.getData());

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.mutate()", getClass().getSimpleName());
		return rVal;
	}
}
