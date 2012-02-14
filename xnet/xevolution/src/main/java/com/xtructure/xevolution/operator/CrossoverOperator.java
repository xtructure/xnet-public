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

import com.xtructure.xevolution.genetics.Genome;

/**
 * The {@link CrossoverOperator} interface describes the methods used in
 * reproduction via crossover, or simply, with a pair of parents.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used by {@link Genome}s on which this operator is
 *            applied.
 */
public interface CrossoverOperator<D> extends Operator<D> {
	/**
	 * Create a child {@link Genome} using the given parent {@link Genome}s.
	 * 
	 * @param idNumber
	 *            the instance number for the id of the child {@link Genome}
	 * @param genome1
	 *            the first parent {@link Genome}
	 * @param genome2
	 *            the second parent {@link Genome}
	 * @return the child {@link Genome}
	 * @throws OperationFailedException
	 *             If this crossover operation cannot be applied to the given
	 *             {@link Genome}s.
	 */
	public Genome<D> crossover(int idNumber, Genome<D> genome1, Genome<D> genome2) throws OperationFailedException;
}
