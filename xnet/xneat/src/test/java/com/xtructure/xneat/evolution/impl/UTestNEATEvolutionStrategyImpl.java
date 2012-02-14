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
package com.xtructure.xneat.evolution.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.not;

import org.testng.annotations.Test;

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.operator.impl.CopyCrossoverOperator;
import com.xtructure.xevolution.operator.impl.CopyMutateOperator;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.genetics.impl.NEATPopulationImpl;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xneat.operators.impl.NEATCrossoverOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.NEATMutateOperatorSelecterImpl;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.XLogger;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNEATEvolutionStrategyImpl {
	private static final NEATEvolutionFieldMap						EVOLUTION_FIELD_MAP;
	private static final NEATGeneticsFactoryImpl					GENETICS_FACTORY;
	private static final NEATCrossoverOperatorSelecterImpl			CROSSOVER_OPERATOR_SELECTER;
	private static final NEATMutateOperatorSelecterImpl				MUTATE_OPERATOR_SELECTER;
	private static final NEATReproductionStrategyImpl				REPRODUCTION_STRATEGY;
	private static final NEATSurvivalFilterImpl						SURVIVAL_FILTER;
	private static final NEATSpeciationStrategyImpl					SPECIATION_STRATEGY;
	private static final EvaluationStrategy<GeneMap, NeuralNetwork>	EVALUATION_STRATEGY;
	static {
		EVOLUTION_FIELD_MAP = NEATEvolutionConfigurationImpl.DEFAULT_CONFIGURATION.newFieldMap();

		GENETICS_FACTORY = new NEATGeneticsFactoryImpl(EVOLUTION_FIELD_MAP);
		CROSSOVER_OPERATOR_SELECTER = new NEATCrossoverOperatorSelecterImpl();
		CROSSOVER_OPERATOR_SELECTER.add(new CopyCrossoverOperator<GeneMap>(GENETICS_FACTORY, 0.5), 1.0);
		MUTATE_OPERATOR_SELECTER = new NEATMutateOperatorSelecterImpl();
		MUTATE_OPERATOR_SELECTER.add(new CopyMutateOperator<GeneMap>(GENETICS_FACTORY), 1.0);
		REPRODUCTION_STRATEGY = new NEATReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY, CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER);
		SURVIVAL_FILTER = new NEATSurvivalFilterImpl(EVOLUTION_FIELD_MAP);
		SPECIATION_STRATEGY = new NEATSpeciationStrategyImpl(EVOLUTION_FIELD_MAP);
		EVALUATION_STRATEGY = new EvaluationStrategy<GeneMap, NeuralNetwork>() {
			@Override
			public GenomeDecoder<GeneMap, NeuralNetwork> getGenomeDecoder() {
				return null;
			}

			@Override
			public double simulate(Genome<GeneMap> genome) {
				double value = RandomUtil.nextDouble() + 1.0;
				genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, value);
				return value;
			}

			@Override
			public XLogger getLogger() {
				return null;
			}

			@Override
			public void evaluatePopulation(Population<GeneMap> population) {
				for (Genome<GeneMap> genome : population) {
					simulate(genome);
				}
			}
		};
	}

	public void constructorSucceeds() {
		assertThat("",//
				new NEATEvolutionStrategyImpl<NeuralNetwork>(EVOLUTION_FIELD_MAP, REPRODUCTION_STRATEGY, EVALUATION_STRATEGY, SURVIVAL_FILTER, SPECIATION_STRATEGY, null),//
				isNotNull());
	}

	public void initializeAndEpochBehaveAsExpected() {
		NEATEvolutionStrategyImpl<NeuralNetwork> evolutionStrategy = new NEATEvolutionStrategyImpl<NeuralNetwork>(EVOLUTION_FIELD_MAP, REPRODUCTION_STRATEGY, EVALUATION_STRATEGY, SURVIVAL_FILTER, SPECIATION_STRATEGY, null);
		NEATPopulationImpl population = GENETICS_FACTORY.createPopulation(0);

		assertThat("",//
				population.getSpeciesIds(), isEmpty());
		for (Genome<GeneMap> genome : population) {
			assertThat("",//
					genome.getAttribute(Genome.FITNESS_ATTRIBUTE_ID),//
					isEqualTo(0.0));
		}
		evolutionStrategy.initialize(population);
		assertThat("",//
				population.getSpeciesIds(), not(isEmpty()));
		for (Genome<GeneMap> genome : population) {
			assertThat("",//
					genome.getAttribute(Genome.FITNESS_ATTRIBUTE_ID),//
					isNotEqualTo(0.0));
			assertThat("",//
					((NEATGenome<GeneMap>) genome).getSpeciesId(), isNotNull());
			assertThat("",//
					population.getSpecies(((NEATGenome<GeneMap>) genome).getSpeciesId()), isNotNull());
		}
		double avgFitness = population.getAverageGenomeAttribute(Genome.FITNESS_ATTRIBUTE_ID);
		assertThat("",//
				avgFitness, isNotEqualTo(0.0));

		evolutionStrategy.epoch(population);
		assertThat("",//
				population.getAverageGenomeAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNotEqualTo(avgFitness));
	}

	public void getEvolutionFieldMapReturnsExpectedObject() {
		assertThat("",//
				new NEATEvolutionStrategyImpl<NeuralNetwork>(EVOLUTION_FIELD_MAP, REPRODUCTION_STRATEGY, EVALUATION_STRATEGY, SURVIVAL_FILTER, SPECIATION_STRATEGY, null).getEvolutionFieldMap(),//
				isEqualTo(EVOLUTION_FIELD_MAP));
	}

	public void getSpeciationStrategyReturnsExpectedObject() {
		assertThat("",//
				new NEATEvolutionStrategyImpl<NeuralNetwork>(EVOLUTION_FIELD_MAP, REPRODUCTION_STRATEGY, EVALUATION_STRATEGY, SURVIVAL_FILTER, SPECIATION_STRATEGY, null).getSpeciationStrategy(),//
				isEqualTo(SPECIATION_STRATEGY));
	}

	public void getSurvivalFilterReturnsExpectedObject() {
		assertThat("",//
				new NEATEvolutionStrategyImpl<NeuralNetwork>(EVOLUTION_FIELD_MAP, REPRODUCTION_STRATEGY, EVALUATION_STRATEGY, SURVIVAL_FILTER, SPECIATION_STRATEGY, null).getSurvivalFilter(),//
				isEqualTo(SURVIVAL_FILTER));
	}
}
