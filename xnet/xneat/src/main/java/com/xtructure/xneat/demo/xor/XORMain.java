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

import javolution.xml.stream.XMLStreamException;

import org.apache.log4j.BasicConfigurator;

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.evolution.impl.NEATEvolutionStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATReproductionStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATSpeciationStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATSurvivalFilterImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATPopulation;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xneat.operators.impl.AddLinkMutateOperator;
import com.xtructure.xneat.operators.impl.AddNodeMutateOperator;
import com.xtructure.xneat.operators.impl.AdjustAttributesMutateOperator;
import com.xtructure.xneat.operators.impl.NEATCrossoverOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.NEATMutateOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.RemoveLinkMutateOperator;
import com.xtructure.xneat.operators.impl.StandardCrossoverOperator;
import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
public class XORMain {
	public static void main(String[] args) throws XMLStreamException {
		BasicConfigurator.configure();
		// specify parameters
		NEATEvolutionFieldMap evolutionFieldMap = NEATEvolutionConfigurationImpl//
				.builder(XId.newId("xor.neat.evolution.config"))//
				.setPopulationSize(100)//
				.setMutationProbability(0.5)//
				.setInputNodeCount(2)//
				.setOutputNodeCount(1)//
				.setBiasNodeCount(0)//
				.newInstance().newFieldMap();
		// define operator distribution and build reproduction strategy
		NEATGeneticsFactoryImpl geneticsFactory = new NEATGeneticsFactoryImpl(evolutionFieldMap);
		OperatorSelecter<MutateOperator<GeneMap>> mutateOperatorSelecter = new NEATMutateOperatorSelecterImpl(//
				new MapBuilder<MutateOperator<GeneMap>, Double>()//
						.put(//
						new AddLinkMutateOperator(geneticsFactory),//
						0.01)//
						.put(//
						new AddNodeMutateOperator(geneticsFactory),//
						0.02)//
						.put(// mutate few links, one parameter at random
						new AdjustAttributesMutateOperator(//
								true, false,//
								0.0, 0.5, geneticsFactory),//
						0.95)//
						.put(//
						new RemoveLinkMutateOperator(geneticsFactory),//
						0.01)//
						.newImmutableInstance());
		OperatorSelecter<CrossoverOperator<GeneMap>> crossoverOperatorSelecter = new NEATCrossoverOperatorSelecterImpl(//
				new MapBuilder<CrossoverOperator<GeneMap>, Double>()//
						.put(//
						new StandardCrossoverOperator(geneticsFactory),//
						1.0)//
						.newImmutableInstance());
		NEATReproductionStrategyImpl reproductionStrategy = new NEATReproductionStrategyImpl(//
				evolutionFieldMap,//
				geneticsFactory,//
				crossoverOperatorSelecter,//
				mutateOperatorSelecter);
		// create evaluationStrategy
		EvaluationStrategy<GeneMap, NeuralNetwork> evaluationStrategy = XOREvaluationStrategy.getInstance();
		// create survival filter
		NEATSurvivalFilterImpl survivalFilter = new NEATSurvivalFilterImpl(evolutionFieldMap);
		// create speciation strategy
		NEATSpeciationStrategyImpl speciationStrategy = new NEATSpeciationStrategyImpl(evolutionFieldMap);
		// create evolution strategy
		NEATEvolutionStrategyImpl<NeuralNetwork> evolutionStrategy = new NEATEvolutionStrategyImpl<NeuralNetwork>(//
				evolutionFieldMap,//
				reproductionStrategy,//
				evaluationStrategy,//
				survivalFilter,//
				speciationStrategy, null);
		// start evolution
		NEATPopulation<GeneMap> population = geneticsFactory.createPopulation(0);
		evolutionStrategy.start(population);
		System.out.println(population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID).getData());
	}
}
