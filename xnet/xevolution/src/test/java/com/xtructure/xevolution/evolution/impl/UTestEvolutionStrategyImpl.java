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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import java.io.File;

import org.testng.annotations.Test;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestEvolutionStrategyImpl {
	private static final EvolutionFieldMap				EVOLUTION_FIELD_MAP;
	private static final ReproductionStrategyImpl		REPRODUCTION_STRATEGY;
	private static final EvolutionStrategyImpl<String>	EVOLUTION_STRATEGY;
	static {
		EVOLUTION_FIELD_MAP = EvolutionConfigurationImpl//
				.builder(XId.newId("default.evolution.config"))//
				.setPopulationSize(1000)//
				.setMutationProbability(1.0)//
				.newInstance().newFieldMap();
		EvaluationStrategy<String, String> evaluationStrategy = null;
		SurvivalFilterImpl survivalFilter = null;
		File outputDir = null;
		GeneticsFactoryImpl geneticsFactory = null;
		OperatorSelecter<CrossoverOperator<String>> crossoverOperatorSelecter = null;
		OperatorSelecter<MutateOperator<String>> mutateOperatorSelecter = null;
		REPRODUCTION_STRATEGY = new ReproductionStrategyImpl(//
				EVOLUTION_FIELD_MAP,//
				geneticsFactory,//
				crossoverOperatorSelecter,//
				mutateOperatorSelecter);
		EVOLUTION_STRATEGY = new EvolutionStrategyImpl<String>(//
				EVOLUTION_FIELD_MAP,//
				REPRODUCTION_STRATEGY,//
				evaluationStrategy,//
				survivalFilter,//
				outputDir);
	}

	public void constructorSucceeds() {
		assertThat("",//
				EVOLUTION_STRATEGY, isNotNull());
	}

	public void getReproductionStrategyReturnsExpectedObject() {
		assertThat("",//
				EVOLUTION_STRATEGY.getReproductionStrategy(), isSameAs(REPRODUCTION_STRATEGY));
	}
}
