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
import static com.xtructure.xutil.valid.ValidateUtils.isNotSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import org.testng.annotations.Test;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.genetics.impl.GenomeImpl;
import com.xtructure.xevolution.operator.DummyCrossoverOperator;
import com.xtructure.xevolution.operator.DummyMutateOperator;
import com.xtructure.xevolution.operator.impl.CrossoverOperatorSelecterImpl;
import com.xtructure.xevolution.operator.impl.MutateOperatorSelecterImpl;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestAbstractReproductionStrategy {
	private static final EvolutionFieldMap				EVOLUTION_FIELD_MAP;
	private static final GeneticsFactoryImpl			GENETICS_FACTORY;
	private static final DummyCrossoverOperator			CROSSOVER_OPERATOR;
	private static final DummyMutateOperator			MUTATE_OPERATOR;
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
		CROSSOVER_OPERATOR = new DummyCrossoverOperator(GENETICS_FACTORY);
		MUTATE_OPERATOR = new DummyMutateOperator(GENETICS_FACTORY);
		CROSSOVER_OPERATOR_SELECTER = new CrossoverOperatorSelecterImpl();
		CROSSOVER_OPERATOR_SELECTER.add(CROSSOVER_OPERATOR, 1.0);
		MUTATE_OPERATOR_SELECTER = new MutateOperatorSelecterImpl();
		MUTATE_OPERATOR_SELECTER.add(MUTATE_OPERATOR, 1.0);
		REPRODUCTION_STRATEGY = new ReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY, CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER);
	}

	public void constructorSucceeds() {
		assertThat("",//
				REPRODUCTION_STRATEGY, isNotNull());
	}

	public void gettersReturnExpectedObjects() {
		assertThat("",//
				REPRODUCTION_STRATEGY.getEvolutionFieldMap(), isSameAs(EVOLUTION_FIELD_MAP));
		assertThat("",//
				REPRODUCTION_STRATEGY.getGeneticsFactory(), isSameAs(GENETICS_FACTORY));
		assertThat("",//
				REPRODUCTION_STRATEGY.getCrossoverOperators(), isSameAs(CROSSOVER_OPERATOR_SELECTER));
		assertThat("",//
				REPRODUCTION_STRATEGY.getMutateOperators(), isSameAs(MUTATE_OPERATOR_SELECTER));
	}

	public void crossoverReturnsExpectedGenome() {
		GenomeImpl genome1 = new GenomeImpl(1, "1");
		GenomeImpl genome2 = new GenomeImpl(2, "2");
		assertThat("",//
				genome1.getData(), isNotNull());
		assertThat("",//
				genome2.getData(), isNotNull(), isNotSameAs(genome1.getData()));
		assertThat("",//
				CROSSOVER_OPERATOR_SELECTER.available(), isEqualTo(1));
		Genome<String> genome3 = REPRODUCTION_STRATEGY.crossover(3, genome1, genome2);
		assertThat("",//
				genome3, isNotNull());
		assertThat("",//
				genome3.getId(), isEqualTo(XId.newId("Genome").createChild(3)));
		assertThat("",//
				genome3.getData(), isNotNull(), isSameAs(genome1.getData()));
		assertThat("",//
				CROSSOVER_OPERATOR_SELECTER.available(), isEqualTo(1));
		CROSSOVER_OPERATOR.failOnNext();
		genome3 = REPRODUCTION_STRATEGY.crossover(3, genome1, genome2);
		assertThat("",//
				genome3, isNull());
		assertThat("",//
				CROSSOVER_OPERATOR_SELECTER.available(), isEqualTo(1));
	}

	public void mutateReturnsExpectedGenome() {
		GenomeImpl genome1 = new GenomeImpl(1, "1");
		assertThat("",//
				genome1.getData(), isNotNull());
		assertThat("",//
				MUTATE_OPERATOR_SELECTER.available(), isEqualTo(1));
		Genome<String> genome2 = REPRODUCTION_STRATEGY.mutate(2, genome1);
		assertThat("",//
				genome2, isNotNull());
		assertThat("",//
				genome2.getId(), isEqualTo(XId.newId("Genome").createChild(2)));
		assertThat("",//
				genome2.getData(), isNotNull(), isSameAs(genome1.getData()));
		assertThat("",//
				MUTATE_OPERATOR_SELECTER.available(), isEqualTo(1));
		MUTATE_OPERATOR.failOnNext();
		genome2 = REPRODUCTION_STRATEGY.mutate(2, genome1);
		assertThat("",//
				genome2, isNull());
		assertThat("",//
				MUTATE_OPERATOR_SELECTER.available(), isEqualTo(1));
	}

	public void generateChildrenReturnsExpectedSet() {
		// done in ReproductionStrategyImpl
	}
}
