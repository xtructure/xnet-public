/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.xtructure.xnet.demos.arith;

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
 * The Class ArithmeticExperiment.
 * 
 * @author Luis Guimbarda
 */
public class ArithmeticExperiment extends
		AbstractEvolutionExperiment<String, Integer> {

	/** The Constant TARGET_OPTION. */
	private static final String TARGET_OPTION = "target";

	/** The Constant DEFAULT_TARGET. */
	private static final int DEFAULT_TARGET = 64;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		ArithmeticExperiment experiment = new ArithmeticExperiment();
		experiment.setArgs(args);
		experiment.startExperiment();

		Genome<?> fittest = experiment.getPopulation()
				.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID);
		System.out.println(fittest.getData());
		System.out.println(ArithmeticGenomeDecoder.translate(fittest.getData()
				.toString()));
	}

	/**
	 * Instantiates a new arithmetic experiment.
	 */
	public ArithmeticExperiment() {
		super(new ListBuilder<XOption<?>>()//
				.add(new IntegerXOption(TARGET_OPTION, "t", "target",
						"target sum"))//
				.newImmutableInstance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createEvaluationStrategy()
	 */
	@Override
	protected EvaluationStrategy<String, Integer> createEvaluationStrategy() {
		int target = (Integer) (getOption(TARGET_OPTION).hasValue() ? getOption(
				TARGET_OPTION).processValue()
				: DEFAULT_TARGET);
		return new ArithmeticEvaluationStrategy(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createEvolutionStrategy()
	 */
	@Override
	protected EvolutionStrategy<String, Integer> createEvolutionStrategy() {
		return new EvolutionStrategyImpl<Integer>(//
				getEvolutionFieldMap(),//
				getReproductionStrategy(),//
				getEvaluationStrategy(),//
				getSurvivalFilter(),//
				getOutputDir());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createGeneticsFactory()
	 */
	@Override
	protected GeneticsFactory<String> createGeneticsFactory() {
		return new ArithmeticGeneticsFactory(getEvolutionFieldMap());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createGenomeDecoder()
	 */
	@Override
	protected GenomeDecoder<String, Integer> createGenomeDecoder() {
		return new ArithmeticGenomeDecoder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createReproductionStrategy()
	 */
	@Override
	protected ReproductionStrategy<String> createReproductionStrategy() {
		OperatorSelecter<CrossoverOperator<String>> crossoverOperatorSelecter = //
		new CrossoverOperatorSelecterImpl(//
				new MapBuilder<CrossoverOperator<String>, Double>()//
						.put(new ArithmeticCrossoverOperator(
								getGeneticsFactory()), 1.0)//
						.newImmutableInstance());
		OperatorSelecter<MutateOperator<String>> mutateOperatorSelecter = //
		new MutateOperatorSelecterImpl(//
				new MapBuilder<MutateOperator<String>, Double>()//
						.put(new ArithmeticMutateOperator(0.01,
								getGeneticsFactory()), 1.0)//
						.newImmutableInstance());
		return new ReproductionStrategyImpl(getEvolutionFieldMap(),
				getGeneticsFactory(), crossoverOperatorSelecter,
				mutateOperatorSelecter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createSurvivalFilter()
	 */
	@Override
	protected SurvivalFilter createSurvivalFilter() {
		return new SurvivalFilterImpl(getEvolutionFieldMap());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createEvolutionFieldMap()
	 */
	@Override
	protected EvolutionFieldMap createEvolutionFieldMap() {
		EvolutionFieldMap efm = super.createEvolutionFieldMap();
		efm.setTerminationCondition(highestFitnessIsAtLeast(1.0));
		return efm;
	}
}
