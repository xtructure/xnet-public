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

import java.util.Map;

import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.operator.impl.AbstractOperatorSelecter;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;

/**
 * {@link NEATCrossoverOperatorSelecterImpl} implements the
 * {@link OperatorSelecter} interface for {@link CrossoverOperator}s of
 * {@link NEATGenome}s with {@link GeneMap} data.
 * 
 * @author Luis Guimbarda
 * 
 */
public class NEATCrossoverOperatorSelecterImpl extends AbstractOperatorSelecter<CrossoverOperator<GeneMap>> {
	/**
	 * Creates a new {@link NEATCrossoverOperatorSelecterImpl}.
	 */
	public NEATCrossoverOperatorSelecterImpl() {
		super();
	}

	/**
	 * Creates a new {@link NEATCrossoverOperatorSelecterImpl} that's a copy of
	 * the given selecter.
	 * 
	 * @param selecter
	 *            the {@link NEATCrossoverOperatorSelecterImpl} to copy
	 */
	public NEATCrossoverOperatorSelecterImpl(NEATCrossoverOperatorSelecterImpl selecter) {
		super(selecter);
	}

	/**
	 * Creates a new {@link NEATCrossoverOperatorSelecterImpl} as specified by
	 * the given map
	 * 
	 * @param probabilityMap
	 *            map from {@link CrossoverOperator}s to their probabilities
	 */
	public NEATCrossoverOperatorSelecterImpl(Map<CrossoverOperator<GeneMap>, Double> probabilityMap) {
		super(probabilityMap);
	}
}
