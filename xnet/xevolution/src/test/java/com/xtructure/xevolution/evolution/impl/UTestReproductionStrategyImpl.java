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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.genetics.impl.PopulationImpl;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.impl.CrossoverOperatorSelecterImpl;
import com.xtructure.xevolution.operator.impl.MutateOperatorSelecterImpl;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestReproductionStrategyImpl {
	private static final EvolutionFieldMap				EVOLUTION_FIELD_MAP;
	private static final GeneticsFactoryImpl			GENETICS_FACTORY;
	private static final CrossoverOperatorSelecterImpl	CROSSOVER_OPERATOR_SELECTER;
	private static final MutateOperatorSelecterImpl		MUTATE_OPERATOR_SELECTER;
	private static final ReproductionStrategyImpl		REPRODUCTION_STRATEGY;
	static {
		EVOLUTION_FIELD_MAP = EvolutionConfigurationImpl//
				.builder(XId.newId("default.evolution.config"))//
				.setPopulationSize(1000)//
				.setMutationProbability(1.0)//
				.newInstance().newFieldMap();
		GENETICS_FACTORY = new GeneticsFactoryImpl(EVOLUTION_FIELD_MAP, "asdf");
		CROSSOVER_OPERATOR_SELECTER = new CrossoverOperatorSelecterImpl();
		CROSSOVER_OPERATOR_SELECTER.add(//
				new CrossoverOperator<String>() {
					@Override
					public Genome<String> crossover(int idNumber, Genome<String> genome1, Genome<String> genome2) throws OperationFailedException {
						if (RandomUtil.nextBoolean()) {
							return getGeneticsFactory().copyGenome(idNumber, genome1);
						} else {
							return getGeneticsFactory().copyGenome(idNumber, genome2);
						}
					}

					@Override
					public GeneticsFactory<String> getGeneticsFactory() {
						return GENETICS_FACTORY;
					}

					@Override
					public XLogger getLogger() {
						return null;
					}

					@Override
					public com.xtructure.xevolution.operator.Operator.AppliedOperatorInfo getAppliedOperatorInfo() {
						// TODO Auto-generated method stub
						return null;
					}
				},//
				1.0);
		CROSSOVER_OPERATOR_SELECTER.add(//
				new CrossoverOperator<String>() {
					@Override
					public Genome<String> crossover(int idNumber, Genome<String> genome1, Genome<String> genome2) throws OperationFailedException {
						throw new OperationFailedException(null);
					}

					@Override
					public GeneticsFactory<String> getGeneticsFactory() {
						return null;
					}

					@Override
					public XLogger getLogger() {
						return null;
					}

					@Override
					public com.xtructure.xevolution.operator.Operator.AppliedOperatorInfo getAppliedOperatorInfo() {
						// TODO Auto-generated method stub
						return null;
					}
				},//
				99.0);
		MUTATE_OPERATOR_SELECTER = new MutateOperatorSelecterImpl();
		MUTATE_OPERATOR_SELECTER.add(//
				new MutateOperator<String>() {
					@Override
					public Genome<String> mutate(int idNumber, Genome<String> genome) throws OperationFailedException {
						return getGeneticsFactory().copyGenome(idNumber, genome);
					}

					@Override
					public GeneticsFactory<String> getGeneticsFactory() {
						return GENETICS_FACTORY;
					}

					@Override
					public XLogger getLogger() {
						return null;
					}

					@Override
					public com.xtructure.xevolution.operator.Operator.AppliedOperatorInfo getAppliedOperatorInfo() {
						// TODO Auto-generated method stub
						return null;
					}
				},//
				1.0);
		MUTATE_OPERATOR_SELECTER.add(//
				new MutateOperator<String>() {
					@Override
					public Genome<String> mutate(int idNumber, Genome<String> genome) throws OperationFailedException {
						throw new OperationFailedException(null);
					}

					@Override
					public GeneticsFactory<String> getGeneticsFactory() {
						return null;
					}

					@Override
					public XLogger getLogger() {
						return null;
					}

					@Override
					public com.xtructure.xevolution.operator.Operator.AppliedOperatorInfo getAppliedOperatorInfo() {
						// TODO Auto-generated method stub
						return null;
					}
				}, 99.0);
		REPRODUCTION_STRATEGY = new ReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY, CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER);
	}

	public void constructorSucceeds() {
		assertThat("",//
				REPRODUCTION_STRATEGY, isNotNull());
	}

	public void generateChildrenReturnsExpectedSet() {
		PopulationImpl pop = new PopulationImpl(0);
		Set<String> expectedDatas = new HashSet<String>();
		for (int i = 0; i < EVOLUTION_FIELD_MAP.populationSize(); i++) {
			String data = Integer.toString(RandomUtil.nextInteger());
			pop.add(GENETICS_FACTORY.createGenome(i, data));
			expectedDatas.add(data);
		}
		Set<Genome<String>> children = REPRODUCTION_STRATEGY.generateChildren(pop);
		assertThat("",//
				children, isEmpty());
		for (Genome<String> genome : pop) {
			genome.markForDeath();
		}
		children = REPRODUCTION_STRATEGY.generateChildren(pop);
		assertThat("",//
				children, hasSize(EVOLUTION_FIELD_MAP.populationSize()));
		Set<String> datas = new HashSet<String>();
		for (Genome<String> child : children) {
			datas.add(child.getData());
		}
		Set<String> diff = new HashSet<String>(datas);
		diff.removeAll(expectedDatas);
		assertThat("datas is a subset of expectedDatas",//
				diff, isEmpty());
		for (Genome<String> genome : pop) {
			if (RandomUtil.nextBoolean()) {
				genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, RandomUtil.nextDouble());
			}
			if (genome.getAttribute(Genome.FITNESS_ATTRIBUTE_ID) == 0.0) {
				expectedDatas.remove(genome.getData());
			}
		}
		children = REPRODUCTION_STRATEGY.generateChildren(pop);
		assertThat("",//
				children, hasSize(EVOLUTION_FIELD_MAP.populationSize()));
		datas = new HashSet<String>();
		for (Genome<String> child : children) {
			datas.add(child.getData());
		}
		diff = new HashSet<String>(datas);
		diff.removeAll(expectedDatas);
		assertThat("datas is a subset of expectedDatas",//
				diff, isEmpty());
	}
}
