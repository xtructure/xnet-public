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
package com.xtructure.xevolution.evolution.impl;

import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.HashSet;
import java.util.Set;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xevolution.operator.Operator.AppliedOperatorInfo;
import com.xtructure.xevolution.operator.Operator.OperationFailedException;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.operator.impl.CopyCrossoverOperator;
import com.xtructure.xevolution.operator.impl.CopyMutateOperator;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.id.XId;

/**
 * {@link AbstractReproductionStrategy} implements the
 * {@link ReproductionStrategy} interface.
 * <P>
 * It orchestrates the creation of child genomes, selecting which genomes will
 * reproduce probabilistically, weighted by their fitness, and via which of
 * {@link MutateOperator} or {@link CrossoverOperator} as selected by this
 * {@link ReproductionStrategy}'s {@link OperatorSelecter}s.
 * <P>
 * The {@link ReproductionStrategy} with attempt to create children until the
 * requested count has been reached; if no operator with succeed with any
 * genome, the method will loop. It's recommended to use at least one
 * {@link Operator} that will always succeed, e.g., {@link CopyMutateOperator}
 * or {@link CopyCrossoverOperator}.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used by {@link Genome}s
 */
public abstract class AbstractReproductionStrategy<D> extends AbstractEvolutionObject implements ReproductionStrategy<D> {
	/**
	 * crossover {@link OperatorSelecter} used by this
	 * {@link ReproductionStrategy}
	 */
	private final OperatorSelecter<CrossoverOperator<D>>	crossoverOperatorSelecter;
	/** mutate {@link OperatorSelecter} used by this {@link ReproductionStrategy} */
	private final OperatorSelecter<MutateOperator<D>>		mutateOperatorSelecter;
	/** {@link GeneticsFactory} used by this {@link ReproductionStrategy} */
	private final GeneticsFactory<D>						geneticsFactory;
	/** {@link EvolutionFieldMap} used by this {@link ReproductionStrategy} */
	private final EvolutionFieldMap							evolutionFieldMap;

	/**
	 * Creates a new {@link AbstractReproductionStrategy}.
	 * 
	 * @param evolutionFieldMap
	 *            {@link EvolutionFieldMap} used by this
	 *            {@link ReproductionStrategy}
	 * @param geneticsFactory
	 *            {@link GeneticsFactory} used by this
	 *            {@link ReproductionStrategy}
	 * @param crossoverOperatorSelecter
	 *            crossover {@link OperatorSelecter} used by this
	 *            {@link ReproductionStrategy}
	 * @param mutateOperatorSelecter
	 *            mutate {@link OperatorSelecter} used by this
	 *            {@link ReproductionStrategy}
	 */
	public AbstractReproductionStrategy(//
			EvolutionFieldMap evolutionFieldMap,//
			GeneticsFactory<D> geneticsFactory,//
			OperatorSelecter<CrossoverOperator<D>> crossoverOperatorSelecter,//
			OperatorSelecter<MutateOperator<D>> mutateOperatorSelecter) {
		this.evolutionFieldMap = evolutionFieldMap;
		this.crossoverOperatorSelecter = crossoverOperatorSelecter;
		this.mutateOperatorSelecter = mutateOperatorSelecter;
		this.geneticsFactory = geneticsFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.ReproductionStrategy#getCrossoverOperators()
	 */
	@Override
	public OperatorSelecter<CrossoverOperator<D>> getCrossoverOperators() {
		return crossoverOperatorSelecter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.ReproductionStrategy#getMutateOperators()
	 */
	@Override
	public OperatorSelecter<MutateOperator<D>> getMutateOperators() {
		return mutateOperatorSelecter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.ReproductionStrategy#getGeneticsFactory()
	 */
	@Override
	public GeneticsFactory<D> getGeneticsFactory() {
		return geneticsFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.evolution.ReproductionStrategy#getEvolutionFieldMap
	 * ()
	 */
	@Override
	public EvolutionFieldMap getEvolutionFieldMap() {
		return evolutionFieldMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.ReproductionStrategy#mutate(int,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public Genome<D> mutate(int idNumber, Genome<D> genome) {
		getLogger().trace("begin %s.mutate(%s, %s)", getClass().getSimpleName(), idNumber, genome);
		Genome<D> child = null;
		while (getMutateOperators().available() > 0) {
			MutateOperator<D> op = getMutateOperators().select();
			try {
				child = op.mutate(idNumber, genome);
				child.setAttribute(Genome.PARENT1_ID, genome.getId());
				AppliedOperatorInfo info = op.getAppliedOperatorInfo();
				child.setAttribute(Genome.APPLIED_OP_ID, info == null ? op.toString() : info.toString());
				getLogger().info("%s : %s : %s", genome.getId(), child.getId(), op.toString());
				break;
			} catch (OperationFailedException e) {
				// nothing; try again with a different operator
				getLogger().info("%s : %s : %s : %s", e.getClass().getSimpleName(), genome.getId(), op.toString(), e.getMessage());
			}
		}
		getMutateOperators().reset();
		getLogger().trace("will return: %s", child);
		getLogger().trace("end %s.mutate()", getClass().getSimpleName());
		return child;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.ReproductionStrategy#crossover(int,
	 * com.xtructure.xevolution.genetics.Genome,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public Genome<D> crossover(int idNumber, Genome<D> genome1, Genome<D> genome2) {
		getLogger().trace("begin %s.crossover(%s, %s, %s)", getClass().getSimpleName(), idNumber, genome1, genome2);
		Genome<D> child = null;
		while (getCrossoverOperators().available() > 0) {
			CrossoverOperator<D> op = getCrossoverOperators().select();
			try {
				child = op.crossover(idNumber, genome1, genome2);
				child.setAttribute(Genome.PARENT1_ID, genome1.getId());
				child.setAttribute(Genome.PARENT2_ID, genome2.getId());
				AppliedOperatorInfo info = op.getAppliedOperatorInfo();
				child.setAttribute(Genome.APPLIED_OP_ID, info == null ? op.toString() : info.toString());
				getLogger().info("%s : %s : %s : %s", genome1.getId(), genome2.getId(), child.getId(), op.toString());
				break;
			} catch (OperationFailedException e) {
				// nothing
				getLogger().info("%s : %s : %s : %s : %s", e.getClass().getSimpleName(), genome1.getId(), genome2.getId(), op.toString(), e.getMessage());
			}
		}
		getCrossoverOperators().reset();
		getLogger().trace("will return: %s", child);
		getLogger().trace("end %s.crossover()", getClass().getSimpleName());
		return child;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.ReproductionStrategy#generateChildren(int,
	 * com.xtructure.xevolution.genetics.Population)
	 */
	@Override
	public Set<Genome<D>> generateChildren(Population<D> population) {
		getLogger().trace("begin %s.generateChildren(%s)", getClass().getSimpleName(), population);
		// count the number of genomes marked for death
		int count = getEvolutionFieldMap().populationSize();
		validateState("", population.size(), isLessThanOrEqualTo(count));
		for (Genome<D> genome : population) {
			if (!genome.isMarkedForDeath()) {
				count--;
			}
		}
		// get new unique id number
		int idNumber = 0;
		for (Genome<D> genome : population) {
			idNumber = Math.max(idNumber, genome.getId().getInstanceNum() + 1);
		}
		// build weights for roulette
		XId[] ids = population.keySet().toArray(new XId[0]);
		double[] weights = new double[ids.length];
		for (int i = 0; i < ids.length; i++) {
			weights[i] = population.get(ids[i]).getAttribute(Genome.FITNESS_ATTRIBUTE_ID);
		}
		boolean normalized = RandomUtil.normalizeWeights(weights);
		// create children
		Set<Genome<D>> children = new HashSet<Genome<D>>();
		int index = 0;
		while (children.size() < count) {
			Genome<D> child = null;
			boolean doMutate = RandomUtil.eventOccurs(getEvolutionFieldMap().mutationProbability());
			if (doMutate) {
				index = normalized ? RandomUtil.rouletteSelect(index, weights) : RandomUtil.nextInteger(ids.length);
				Genome<D> genome = population.get(ids[index]);
				child = mutate(idNumber, genome);
			} else {
				index = normalized ? RandomUtil.rouletteSelect(index, weights) : RandomUtil.nextInteger(ids.length);
				Genome<D> genome1 = population.get(ids[index]);
				index = normalized ? RandomUtil.rouletteSelect(index, weights) : RandomUtil.nextInteger(ids.length);
				Genome<D> genome2 = population.get(ids[index]);
				child = crossover(idNumber, genome1, genome2);
			}
			if (child == null) {
				continue;
			}
			idNumber++;
			children.add(child);
		}
		getLogger().trace("will return: %s", children);
		getLogger().trace("end %s.generateChildren()", getClass().getSimpleName());
		return children;
	}
}
