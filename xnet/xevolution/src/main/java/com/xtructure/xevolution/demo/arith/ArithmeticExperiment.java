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
package com.xtructure.xevolution.demo.arith;

import static com.xtructure.xevolution.config.impl.HighestFitnessIsAtLeastCondition.highestFitnessIsAtLeast;

import java.io.IOException;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment;
import com.xtructure.xevolution.evolution.impl.EvolutionStrategyImpl;
import com.xtructure.xevolution.evolution.impl.ReproductionStrategyImpl;
import com.xtructure.xevolution.evolution.impl.SurvivalFilterImpl;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.operator.impl.CrossoverOperatorSelecterImpl;
import com.xtructure.xevolution.operator.impl.MutateOperatorSelecterImpl;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.opt.IntegerXOption;
import com.xtructure.xutil.opt.XOption;

/**
 * @author Luis Guimbarda
 * 
 */
public class ArithmeticExperiment extends AbstractEvolutionExperiment<String, Integer> {
	private static final String	TARGET_OPTION	= "target";
	private static final int	DEFAULT_TARGET	= 64;

	public static void main(String[] args) throws IOException {
		ArithmeticExperiment experiment = new ArithmeticExperiment();
		experiment.setArgs(args);
		experiment.startExperiment();
		
		Genome<?> fittest = experiment.getPopulation().getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID);
		System.out.println(fittest.getData());
		System.out.println(ArithmeticGenomeDecoder.translate(fittest.getData().toString()));
	}

	public ArithmeticExperiment() {
		super(new ListBuilder<XOption<?>>()//
				.add(new IntegerXOption(TARGET_OPTION, "t", "target", "target sum"))//
				.newImmutableInstance());
	}

	@Override
	protected EvaluationStrategy<String, Integer> createEvaluationStrategy() {
		int target = (Integer) (getOption(TARGET_OPTION).hasValue() ? getOption(TARGET_OPTION).processValue() : DEFAULT_TARGET);
		return new ArithmeticEvaluationStrategy(target);
	}

	@Override
	protected EvolutionStrategy<String, Integer> createEvolutionStrategy() {
		return new EvolutionStrategyImpl<Integer>(//
				getEvolutionFieldMap(),//
				getReproductionStrategy(),//
				getEvaluationStrategy(),//
				getSurvivalFilter(),//
				getOutputDir());
	}

	@Override
	protected GeneticsFactory<String> createGeneticsFactory() {
		return new ArithmeticGeneticsFactory(getEvolutionFieldMap());
	}

	@Override
	protected GenomeDecoder<String, Integer> createGenomeDecoder() {
		return new ArithmeticGenomeDecoder();
	}

	@Override
	protected ReproductionStrategy<String> createReproductionStrategy() {
		OperatorSelecter<CrossoverOperator<String>> crossoverOperatorSelecter = //
		new CrossoverOperatorSelecterImpl(//
				new MapBuilder<CrossoverOperator<String>, Double>()//
						.put(new ArithmeticCrossoverOperator(getGeneticsFactory()), 1.0)//
						.newImmutableInstance());
		OperatorSelecter<MutateOperator<String>> mutateOperatorSelecter = //
		new MutateOperatorSelecterImpl(//
				new MapBuilder<MutateOperator<String>, Double>()//
						.put(new ArithmeticMutateOperator(0.01, getGeneticsFactory()), 1.0)//
						.newImmutableInstance());
		return new ReproductionStrategyImpl(getEvolutionFieldMap(), getGeneticsFactory(), crossoverOperatorSelecter, mutateOperatorSelecter);
	}

	@Override
	protected SurvivalFilter createSurvivalFilter() {
		return new SurvivalFilterImpl(getEvolutionFieldMap());
	}
	
	@Override
	protected EvolutionFieldMap createEvolutionFieldMap() {
		EvolutionFieldMap efm = super.createEvolutionFieldMap();
		efm.setTerminationCondition(highestFitnessIsAtLeast(1.0));
		return efm;
	}
}
