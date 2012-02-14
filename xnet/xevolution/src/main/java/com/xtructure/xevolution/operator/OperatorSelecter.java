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

import java.util.Set;

import com.xtructure.xevolution.evolution.EvolutionObject;
import com.xtructure.xevolution.operator.impl.AbstractOperatorSelecter;

/**
 * The {@link OperatorSelecter} describes the methods for a collection of
 * {@link Operator}s that are retrieved probabilistically, without replacement.
 * It includes a reset() method to return the {@link OperatorSelecter} to it
 * full state.
 * 
 * @author Luis Guimbarda
 * 
 * @param <O>
 *            type of {@link Operator}
 */
public interface OperatorSelecter<O extends Operator<?>> extends EvolutionObject {
	/**
	 * Makes the given {@link Operator} available for selection with the given
	 * probability.
	 * 
	 * @param operator
	 *            the {@link Operator} to add to this {@link OperatorSelecter}
	 * @param probability
	 *            the probability the given operator will be selected
	 */
	public void add(O operator, double probability);

	/**
	 * Removes the given {@link Operator} from this
	 * {@link AbstractOperatorSelecter}.
	 * 
	 * @param operator
	 *            the {@link Operator} to remove from this
	 *            {@link OperatorSelecter}
	 */
	public void remove(O operator);

	/**
	 * Makes all previously selected operators available again for selection.
	 */
	public void reset();

	/**
	 * Selects an available {@link Operator} from this
	 * {@link AbstractOperatorSelecter} with their associated probability
	 * adjusted by the remaining probability mass of those available
	 * {@link Operator}.
	 * 
	 * @return the selected {@link Operator}.
	 */
	public O select();

	/**
	 * Gets the number of {@link Operator}s available for selection.
	 * 
	 * @return the number of {@link Operator}s available for selection.
	 */
	public int available();

	/**
	 * Gets the total number of {@link Operator}s in this selecter (both
	 * available for selection and not)
	 * 
	 * @return the total number of {@link Operator}s in this selecter
	 */
	public int size();

	/**
	 * Gets set of the {@link Operator}s in this selecter
	 * 
	 * @return a set of the {@link Operator}s in this selecter
	 */
	public Set<O> opSet();
}
