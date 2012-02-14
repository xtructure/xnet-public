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

import java.util.Map;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xevolution.operator.OperatorSelecter;

/**
 * {@link CrossoverOperatorSelecterImpl} implements the {@link OperatorSelecter}
 * interface for {@link CrossoverOperator}s on {@link Genome}s with String type
 * data.
 * 
 * @author Luis Guimbarda
 * 
 */
public class CrossoverOperatorSelecterImpl extends AbstractOperatorSelecter<CrossoverOperator<String>> {
	/**
	 * Creates a new {@link CrossoverOperatorSelecterImpl}
	 */
	public CrossoverOperatorSelecterImpl() {
		super();
	}

	/**
	 * Creates a new {@link CrossoverOperatorSelecterImpl} that's a copy of the
	 * given {@link OperatorSelecter}.
	 * 
	 * @param selecter
	 *            the {@link OperatorSelecter} whose {@link Operator}s are to be
	 *            copied
	 */
	public CrossoverOperatorSelecterImpl(AbstractOperatorSelecter<CrossoverOperator<String>> selecter) {
		super(selecter);
	}

	/**
	 * Creates a new {@link CrossoverOperatorSelecterImpl} as specified by the
	 * given probability map.
	 * 
	 * @param probabilityMap
	 *            map describing the probabilities of the {@link Operator}s to
	 *            be used in this {@link OperatorSelecter}
	 */
	public CrossoverOperatorSelecterImpl(Map<CrossoverOperator<String>, Double> probabilityMap) {
		super(probabilityMap);
	}
}
