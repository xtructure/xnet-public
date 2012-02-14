/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.operators.impl;

import com.xtructure.xevolution.operator.impl.AbstractOperator;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.operators.NEATOperator;

/**
 * {@link AbstractNEATOperator} implements the {@link NEATOperator} interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractNEATOperator<D> extends AbstractOperator<D> {
	/**
	 * Creates a new {@link AbstractNEATOperator}
	 * 
	 * @param geneticsFactory
	 *            the {@link NEATGeneticsFactory} used by this
	 *            {@link AbstractNEATOperator}
	 */
	public AbstractNEATOperator(NEATGeneticsFactory<D> geneticsFactory) {
		super(geneticsFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.operator.impl.AbstractOperator#getGeneticsFactory
	 * ()
	 */
	@Override
	public NEATGeneticsFactory<D> getGeneticsFactory() {
		return (NEATGeneticsFactory<D>) super.getGeneticsFactory();
	}
}
