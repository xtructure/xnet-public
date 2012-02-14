/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.demo.xor;

import static com.xtructure.xutil.valid.ValidateUtils.or;

import java.io.IOException;
import java.util.Collections;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.gui.XEvolutionGui;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.tool.data.DataTracker;
import com.xtructure.xneat.evolution.NEATSpeciationStrategy;
import com.xtructure.xneat.evolution.impl.AbstractNEATEvolutionExperiment;
import com.xtructure.xneat.evolution.impl.NEATEvolutionStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATReproductionStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATSpeciationStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATSurvivalFilterImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.genetics.impl.NEATGenomeDecoder;
import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xneat.operators.impl.AddLinkMutateOperator;
import com.xtructure.xneat.operators.impl.AddNodeMutateOperator;
import com.xtructure.xneat.operators.impl.AdjustAttributesMutateOperator;
import com.xtructure.xneat.operators.impl.NEATCrossoverOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.NEATMutateOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.RemoveLinkMutateOperator;
import com.xtructure.xneat.operators.impl.StandardCrossoverOperator;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.opt.BooleanXOption;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
public class XORExperiment extends AbstractNEATEvolutionExperiment<GeneMap, NeuralNetwork> {
	public static final String GUI_OPTION_NAME = "gui";
	public static void main(String[] args) throws IOException {
		XORExperiment experiment = new XORExperiment();
		experiment.setArgs(args);
		experiment.startExperiment();
		
		Genome<?> fittest = experiment.getPopulation().getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID);
		System.out.println(fittest.getData());
	}
	
	public XORExperiment() {
		super(Collections.singleton(new BooleanXOption(GUI_OPTION_NAME, "g", "gui", "show gui")));
	}
	
	@Override
	public void startExperiment() {
		BooleanXOption guiOption = (BooleanXOption) getOption(GUI_OPTION_NAME);
		if(guiOption != null && guiOption.processValue()){
			new XEvolutionGui("XOR Experiment", null, DataTracker.newInstance());
		}
		super.startExperiment();
	}

	@Override
	protected NEATSpeciationStrategy<GeneMap> createSpeciationStrategy() {
		return new NEATSpeciationStrategyImpl(getEvolutionFieldMap());
	}

	@Override
	protected GenomeDecoder<GeneMap, NeuralNetwork> createGenomeDecoder() {
		return NEATGenomeDecoder.getInstance();
	}

	@Override
	protected GeneticsFactory<GeneMap> createGeneticsFactory() {
		return new NEATGeneticsFactoryImpl(//
				getEvolutionFieldMap(),//
				LinkGeneConfiguration.builder(null)//
						.setWeight(Range.getInstance(-10.0, 10.0), Range.getInstance(1.0))//
						.newInstance(),//
				NodeGeneConfiguration.builder(null)//
						.setActivation(Range.getInstance(-10.0, 10.0), Range.getInstance(2.0))//
						.newInstance());
	}

	@Override
	protected ReproductionStrategy<GeneMap> createReproductionStrategy() {
		OperatorSelecter<MutateOperator<GeneMap>> mutateOperatorSelecter = new NEATMutateOperatorSelecterImpl(//
				new MapBuilder<MutateOperator<GeneMap>, Double>()//
						.put(//
						new AddLinkMutateOperator(getGeneticsFactory()),//
						0.05)//
						.put(//
						new AddNodeMutateOperator(getGeneticsFactory()),//
						0.05)//
						.put(//
						new AdjustAttributesMutateOperator(//
								true, false,//
								1.0, 1.0, getGeneticsFactory()),//
						0.8)//
						.put(//
						new RemoveLinkMutateOperator(getGeneticsFactory()),//
						0.1)//
						.newImmutableInstance());
		OperatorSelecter<CrossoverOperator<GeneMap>> crossoverOperatorSelecter = new NEATCrossoverOperatorSelecterImpl(//
				new MapBuilder<CrossoverOperator<GeneMap>, Double>()//
						.put(//
						new StandardCrossoverOperator(getGeneticsFactory()),//
						1.0)//
						.newImmutableInstance());
		return new NEATReproductionStrategyImpl(//
				getEvolutionFieldMap(),//
				getGeneticsFactory(),//
				crossoverOperatorSelecter,//
				mutateOperatorSelecter);
	}

	@Override
	protected EvaluationStrategy<GeneMap, NeuralNetwork> createEvaluationStrategy() {
		return XOREvaluationStrategy.getInstance();
	}

	@Override
	protected SurvivalFilter createSurvivalFilter() {
		return new NEATSurvivalFilterImpl(getEvolutionFieldMap());
	}

	@Override
	protected EvolutionStrategy<GeneMap, NeuralNetwork> createEvolutionStrategy() {
		return new NEATEvolutionStrategyImpl<NeuralNetwork>(//
				getEvolutionFieldMap(),//
				getReproductionStrategy(),//
				getEvaluationStrategy(),//
				getSurvivalFilter(),//
				getSpeciationStrategy(), 
				getOutputDir());
	}
	
	@Override
	protected EvolutionFieldMap createEvolutionFieldMap() {
		EvolutionFieldMap efm = super.createEvolutionFieldMap();
		efm.setTerminationCondition(//
				or(efm.terminationCondition(),//
						new Condition() {
							@Override
							public boolean isSatisfiedBy(Object obj) {
								if(obj != null && obj instanceof Population<?>){
									return ((Population<?>)obj).getAge() >= 300;
								}
								return false;
							}
						}));
		return efm;
	}
}
