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

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xutil.RandomUtil;

/**
 * {@link CopyCrossoverOperator} is an implementation of the
 * {@link CrossoverOperator} interface that duplicates either one or the other
 * parent's data when creating a child {@link Genome}.
 * <P>
 * Note that {@link CopyCrossoverOperator} never fails to produce a child.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used by {@link Genome}s on which this operator is
 *            applied.
 */
public class CopyCrossoverOperator<D> extends AbstractOperator<D> implements CrossoverOperator<D> {
	/** the probability that the first {@link Genome}'s data will be copied */
	private final double	copyFirst;

	/**
	 * Creates a new {@link CopyCrossoverOperator}.
	 * 
	 * @param geneticsFactory
	 *            the {@link GeneticsFactory} used by this {@link Operator}
	 * @param copyFirst
	 *            the probability that the first parent {@link Genome}'s data
	 *            will be copied
	 */
	public CopyCrossoverOperator(GeneticsFactory<D> geneticsFactory, double copyFirst) {
		super(geneticsFactory);
		validateArg("geneticsFactory", geneticsFactory, isNotNull());
		validateArg("copyFirst", copyFirst, isGreaterThanOrEqualTo(0.0), isLessThanOrEqualTo(1.0));
		this.copyFirst = copyFirst;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.CrossoverOperator#crossover(int,
	 * com.xtructure.xevolution.genetics.Genome,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public Genome<D> crossover(int idNumber, Genome<D> genome1, Genome<D> genome2) throws OperationFailedException {
		getLogger().trace("begin %s.crossover(%s, %s, %s)", getClass().getSimpleName(), idNumber, genome1, genome2);

		Genome<D> rVal;
		if (RandomUtil.eventOccurs(copyFirst)) {
			rVal = getGeneticsFactory().createGenome(idNumber, genome1.getData());
		} else {
			rVal = getGeneticsFactory().createGenome(idNumber, genome2.getData());
		}

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.crossover()", getClass().getSimpleName());
		return rVal;
	}
}
