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
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import org.testng.annotations.Test;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.genetics.impl.DummyGenomeDecoder;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.impl.CrossoverOperatorSelecterImpl;
import com.xtructure.xevolution.operator.impl.MutateOperatorSelecterImpl;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestAbstractEvolutionStrategy {
	private static final EvolutionFieldMap					EVOLUTION_FIELD_MAP;
	private static final GeneticsFactoryImpl				GENETICS_FACTORY;
	private static final CrossoverOperatorSelecterImpl		CROSSOVER_OPERATOR_SELECTER;
	private static final MutateOperatorSelecterImpl			MUTATE_OPERATOR_SELECTER;
	private static final ReproductionStrategyImpl			REPRODUCTION_STRATEGY;
	private static final EvaluationStrategy<String, String>	EVALUATION_STRATEGY;
	private static final SurvivalFilterImpl					SURVIVAL_FILTER;
	private static final DummyEvolutionStrategy				EVOLUTION_STRATEGY;
	static {
		EVOLUTION_FIELD_MAP = EvolutionConfigurationImpl//
				.builder(XId.newId())//
				.setPopulationSize(500)//
				.setMutationProbability(1.0)//
				.setTerminationCondition(new Condition() {
					@Override
					public boolean isSatisfiedBy(Object obj) {
						if (obj instanceof Population<?>) {
							return ((Population<?>) obj).getAge() >= 10;
						}
						return false;
					}
				}).newInstance().newFieldMap();
		GENETICS_FACTORY = new GeneticsFactoryImpl(EVOLUTION_FIELD_MAP, "");
		CROSSOVER_OPERATOR_SELECTER = new CrossoverOperatorSelecterImpl();
		CROSSOVER_OPERATOR_SELECTER.add(new CrossoverOperator<String>() {
			@Override
			public GeneticsFactory<String> getGeneticsFactory() {
				return GENETICS_FACTORY;
			}

			@Override
			public Genome<String> crossover(int idNumber, Genome<String> genome1, Genome<String> genome2) throws OperationFailedException {
				return GENETICS_FACTORY.copyGenome(idNumber, genome1);
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
		}, 1.0);
		MUTATE_OPERATOR_SELECTER = new MutateOperatorSelecterImpl();
		MUTATE_OPERATOR_SELECTER.add(new MutateOperator<String>() {
			@Override
			public GeneticsFactory<String> getGeneticsFactory() {
				return GENETICS_FACTORY;
			}

			@Override
			public Genome<String> mutate(int idNumber, Genome<String> genome) throws OperationFailedException {
				return GENETICS_FACTORY.copyGenome(idNumber, genome);
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
		}, 1.0);
		REPRODUCTION_STRATEGY = new ReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY, CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER);
		EVALUATION_STRATEGY = new DummyEvaluationStrategy(new DummyGenomeDecoder());
		SURVIVAL_FILTER = new SurvivalFilterImpl(EVOLUTION_FIELD_MAP);
		EVOLUTION_STRATEGY = new DummyEvolutionStrategy(EVOLUTION_FIELD_MAP, REPRODUCTION_STRATEGY, EVALUATION_STRATEGY, SURVIVAL_FILTER);
	}

	public void constructorSucceeds() {
		assertThat("",//
				EVOLUTION_STRATEGY, isNotNull());
	}

	public void gettersReturnExpectedObjects() {
		assertThat("",//
				EVOLUTION_STRATEGY, isNotNull());
		assertThat("",//
				EVOLUTION_STRATEGY.getEvaluationStrategy(), isSameAs(EVALUATION_STRATEGY));
		assertThat("",//
				EVOLUTION_STRATEGY.getEvolutionFieldMap(), isSameAs(EVOLUTION_FIELD_MAP));
		assertThat("",//
				EVOLUTION_STRATEGY.getReproductionStrategy(), isSameAs(REPRODUCTION_STRATEGY));
		assertThat("",//
				EVOLUTION_STRATEGY.getSurvivalFilter(), isSameAs(SURVIVAL_FILTER));
		assertThat("",//
				EVOLUTION_STRATEGY.getOutputDir(), isNull());
	}

	public void startBehavesAsExpected() {
		assertThat("",//
				EVOLUTION_STRATEGY.getTrace(), isEqualTo(""));
		Population<String> population = GENETICS_FACTORY.createPopulation(0);
		EVOLUTION_STRATEGY.start(population);
		assertThat("",//
				EVOLUTION_STRATEGY.getTrace(), isEqualTo("ieeeeeeeeee"));
	}
}
