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

import com.xtructure.xevolution.evolution.impl.AbstractEvolutionObject;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.Operator;

/**
 * {@link AbstractOperator} implements the getters for the {@link Operator}
 * interface.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used by {@link Genome}s on which this operator is
 *            applied.
 */
public abstract class AbstractOperator<D> extends AbstractEvolutionObject implements Operator<D> {
	/** the {@link GeneticsFactory} used by this {@link Operator} */
	private final GeneticsFactory<D>	geneticsFactory;
	/** */
	private AppliedOperatorInfo			appliedOperatorInfo	= null;

	/**
	 * Creates a new {@link AbstractOperator}
	 * 
	 * @param geneticsFactory
	 *            the {@link GeneticsFactory} used by this
	 *            {@link AbstractOperator}
	 */
	public AbstractOperator(GeneticsFactory<D> geneticsFactory) {
		this.geneticsFactory = geneticsFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.Operator#getGeneticsFactory()
	 */
	@Override
	public GeneticsFactory<D> getGeneticsFactory() {
		return geneticsFactory;
	}

	@Override
	public AppliedOperatorInfo getAppliedOperatorInfo() {
		return appliedOperatorInfo;
	}

	protected void setAppliedOperatorInfo(AppliedOperatorInfo appliedOperatorInfo) {
		this.appliedOperatorInfo = appliedOperatorInfo;
	}
}
