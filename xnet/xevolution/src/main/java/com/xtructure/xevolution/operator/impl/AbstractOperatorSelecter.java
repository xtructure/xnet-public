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

import static com.xtructure.xutil.valid.ValidateUtils.containsValue;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.not;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.xtructure.xevolution.evolution.impl.AbstractEvolutionObject;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.coll.SetBuilder;

/**
 * {@link AbstractOperatorSelecter} implements the {@link OperatorSelecter}
 * interface. It uses roulette selection to choose an operator. Selected
 * operators are made unavailable for selection again until the reset method is
 * called.
 * 
 * @author Luis Guimbarda
 * 
 * @param <O>
 *            type of {@link Operator}
 */
public abstract class AbstractOperatorSelecter<O extends Operator<?>> extends AbstractEvolutionObject implements OperatorSelecter<O> {
	/** map of available {@link Operator}s and their probabilities */
	private final Map<O, Double>	probabilityMap;
	/** map of already selected {@link Operator}s and their probabilities */
	private final Map<O, Double>	removedMap;
	/** sum of probabilities of available {@link Operator}s */
	private double					totalProbability;

	/**
	 * Creates a new {@link AbstractOperatorSelecter}.
	 */
	public AbstractOperatorSelecter() {
		probabilityMap = new HashMap<O, Double>();
		removedMap = new HashMap<O, Double>();
		totalProbability = 0.0;
	}

	/**
	 * Creates a new {@link AbstractOperatorSelecter} as specified by the given
	 * map.
	 * 
	 * @param probabilityMap
	 */
	public AbstractOperatorSelecter(Map<O, Double> probabilityMap) {
		this();

		validateArg("probabilityMap is not null", probabilityMap, isNotNull());
		validateArg("probabilityMap doesn't contain null", probabilityMap, not(containsValue(null)));

		for (O operator : probabilityMap.keySet()) {
			add(operator, probabilityMap.get(operator));
		}
	}

	/**
	 * Creates a new {@link AbstractOperatorSelecter} that is a duplicate of
	 * that given.
	 * 
	 * @param selecter
	 */
	public AbstractOperatorSelecter(AbstractOperatorSelecter<O> selecter) {
		this();

		validateArg("selector is not null", selecter, isNotNull());

		this.probabilityMap.putAll(selecter.probabilityMap);
		this.probabilityMap.putAll(selecter.removedMap);
		this.totalProbability = selecter.totalProbability;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.operator.OperatorSelecter#add(com.xtructure.
	 * xevolution.operator.Operator, double)
	 */
	@Override
	public void add(O operator, double probability) {
		getLogger().trace("begin %s.add(%s, %s)", getClass().getSimpleName(), operator, probability);

		remove(operator);
		probabilityMap.put(operator, probability);
		totalProbability += probability;

		getLogger().trace("end %s.add()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.operator.OperatorSelecter#remove(com.xtructure
	 * .xevolution.operator.Operator)
	 */
	@Override
	public void remove(O operator) {
		getLogger().trace("begin %s.remove(%s)", getClass().getSimpleName(), operator);

		if (probabilityMap.containsKey(operator)) {
			totalProbability -= probabilityMap.get(operator);
			probabilityMap.remove(operator);
		} else if (removedMap.containsKey(operator)) {
			removedMap.remove(operator);
		}

		getLogger().trace("end %s.remove()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.OperatorSelecter#reset()
	 */
	@Override
	public void reset() {
		getLogger().trace("begin %s.reset()", getClass().getSimpleName());

		for (O op : removedMap.keySet()) {
			double probability = removedMap.get(op);
			probabilityMap.put(op, probability);
			totalProbability += probability;
		}
		removedMap.clear();

		getLogger().trace("end %s.reset()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.OperatorSelecter#select()
	 */
	@Override
	public O select() {
		getLogger().trace("begin %s.select()", getClass().getSimpleName());

		double target = RandomUtil.nextDouble(0, totalProbability);
		O rVal = null;
		for (O operator : probabilityMap.keySet()) {
			double current = probabilityMap.get(operator);
			if (target <= current) {
				remove(operator);
				removedMap.put(operator, current);
				rVal = operator;
				break;
			}
			target -= current;
		}

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.select()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.OperatorSelecter#size()
	 */
	@Override
	public int available() {
		getLogger().trace("begin %s.available()", getClass().getSimpleName());

		int rVal = probabilityMap.size();

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.available()", getClass().getSimpleName());
		return rVal;
	}

	@Override
	public int size() {
		getLogger().trace("begin %s.size()", getClass().getSimpleName());

		int rVal = probabilityMap.size() + removedMap.size();

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.size()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.operator.OperatorSelecter#opSet()
	 */
	@Override
	public Set<O> opSet() {
		return new SetBuilder<O>()//
				.addAll(probabilityMap.keySet())//
				.addAll(removedMap.keySet())//
				.newImmutableInstance();
	}
}
