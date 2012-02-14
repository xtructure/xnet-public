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
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.genetics.impl.NEATPopulationImpl;
import com.xtructure.xneat.operators.impl.NEATCrossoverOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.NEATMutateOperatorSelecterImpl;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNEATSpeciationStrategyImpl {
	private static final NEATEvolutionFieldMap				EVOLUTION_FIELD_MAP;
	private static final NEATGeneticsFactoryImpl			GENETICS_FACTORY;
	private static final NEATCrossoverOperatorSelecterImpl	CROSSOVER_OPERATOR_SELECTER;
	private static final NEATMutateOperatorSelecterImpl		MUTATE_OPERATOR_SELECTER;
	// private static final NEATReproductionStrategyImpl REPRODUCTION_STRATEGY;
	static {
		EVOLUTION_FIELD_MAP = NEATEvolutionConfigurationImpl//
				.builder(XId.newId("xor.neat.evolution.config"))//
				.setInitialConnectionProbability(0.5)//
				.newInstance().newFieldMap();
		GENETICS_FACTORY = new NEATGeneticsFactoryImpl(EVOLUTION_FIELD_MAP);
		CROSSOVER_OPERATOR_SELECTER = new NEATCrossoverOperatorSelecterImpl();
		CROSSOVER_OPERATOR_SELECTER.add(new CrossoverOperator<GeneMap>() {
			@Override
			public NEATGenome<GeneMap> crossover(int idNumber, Genome<GeneMap> genome1, Genome<GeneMap> genome2) throws com.xtructure.xevolution.operator.Operator.OperationFailedException {
				return (NEATGenome<GeneMap>) getGeneticsFactory().copyGenome(100 + genome1.getId().getInstanceNum(), genome1);
			}

			@Override
			public GeneticsFactory<GeneMap> getGeneticsFactory() {
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
		}, 1.0);
		MUTATE_OPERATOR_SELECTER = new NEATMutateOperatorSelecterImpl();
		MUTATE_OPERATOR_SELECTER.add(new MutateOperator<GeneMap>() {
			@Override
			public NEATGenome<GeneMap> mutate(int idNumber, Genome<GeneMap> genome) throws com.xtructure.xevolution.operator.Operator.OperationFailedException {
				return (NEATGenome<GeneMap>) getGeneticsFactory().copyGenome(100 + genome.getId().getInstanceNum(), genome);
			}

			@Override
			public GeneticsFactory<GeneMap> getGeneticsFactory() {
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
		}, 1.0);
		// REPRODUCTION_STRATEGY = new
		// NEATReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY,
		// CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER);
	}

	public void constructorSucceeds() {
		assertThat("",//
				new NEATSpeciationStrategyImpl(EVOLUTION_FIELD_MAP), isNotNull());
	}

	public void getEvolutionFieldMapReturnsExpectedObject() {
		assertThat("",//
				new NEATSpeciationStrategyImpl(EVOLUTION_FIELD_MAP).getEvolutionFieldMap(),//
				isEqualTo(EVOLUTION_FIELD_MAP));
	}

	public void speciateWithEmptyPopulationDoesNothing() {
		new NEATSpeciationStrategyImpl(EVOLUTION_FIELD_MAP).speciate(new NEATPopulationImpl(0));
	}
	// public void speciateWithLowThreshholdBehavesAsExpected() {
	// EVOLUTION_FIELD_MAP.targetSpeciesCountMax = 5;
	// EVOLUTION_FIELD_MAP.targetSpeciesCountMin = 5;
	// NEATPopulation<GeneMap> population =
	// GENETICS_FACTORY.createPopulation(0);
	// population.setAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID,
	// 0.0);
	// NEATSpeciationStrategyImpl speciationStrategy = new
	// NEATSpeciationStrategyImpl(EVOLUTION_FIELD_MAP);
	// speciationStrategy.speciate(population);
	// assertThat("",//
	// population.getSpecies(), hasSize(population.size()));
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// assertThat("",//
	// species, hasSize(1));
	// }
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// species.setEliteSize(1);
	// species.setTargetSize(2);
	// }
	//
	// population.refreshStats();
	// Set<Genome<GeneMap>> children = REPRODUCTION_STRATEGY.generateChildren(0,
	// population);
	// speciationStrategy.speciateChildren(children, population);
	//
	// assertThat("",//
	// population.size(), isEqualTo(EVOLUTION_FIELD_MAP.populationSize +
	// children.size()));
	// assertThat("",//
	// population.getSpecies(), hasSize(population.size()));
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// assertThat("",//
	// species, hasSize(1));
	// }
	//
	// population.refreshStats();
	// speciationStrategy.respeciate(population);
	// assertThat("",//
	// population.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID),//
	// isEqualTo(0.1));
	// population.refreshStats();
	// speciationStrategy.respeciate(population);
	// assertThat("",//
	// population.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID),//
	// isEqualTo(0.20500000000000002));
	// }
	// public void speciateWithHighThreshholdBehavesAsExpected() {
	// EVOLUTION_FIELD_MAP.targetSpeciesCountMax = 5;
	// EVOLUTION_FIELD_MAP.targetSpeciesCountMin = 5;
	// NEATPopulation<GeneMap> population =
	// GENETICS_FACTORY.createPopulation(0);
	// population.setAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID,
	// 10.0);
	// NEATSpeciationStrategyImpl speciationStrategy = new
	// NEATSpeciationStrategyImpl(EVOLUTION_FIELD_MAP);
	// speciationStrategy.speciate(population);
	// assertThat("",//
	// population.getSpecies(), hasSize(1));
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// assertThat("",//
	// species, hasSize(population.size()));
	// }
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// species.setEliteSize(1);
	// species.setTargetSize(2);
	// }
	//
	// population.refreshStats();
	// Set<Genome<GeneMap>> children = REPRODUCTION_STRATEGY.generateChildren(0,
	// population);
	// speciationStrategy.speciateChildren(children, population);
	//
	// assertThat("",//
	// population.size(), isEqualTo(EVOLUTION_FIELD_MAP.populationSize +
	// children.size()));
	// assertThat("",//
	// population.getSpecies(), hasSize(1));
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// assertThat("",//
	// species, hasSize(population.size()));
	// }
	//
	// population.refreshStats();
	// speciationStrategy.respeciate(population);
	// assertThat("",//
	// population.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID),//
	// isEqualTo(9.9));
	// population.refreshStats();
	// speciationStrategy.respeciate(population);
	// assertThat("",//
	// population.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID),//
	// isEqualTo(9.795));
	// }
	// public void speciateWithGoodSpeciesCountBehavesAsExpected() {
	// EVOLUTION_FIELD_MAP.targetSpeciesCountMax = 100;
	// EVOLUTION_FIELD_MAP.targetSpeciesCountMin = 1;
	// NEATPopulation<GeneMap> population =
	// GENETICS_FACTORY.createPopulation(0);
	// population.setAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID,
	// 10.0);
	// NEATSpeciationStrategyImpl speciationStrategy = new
	// NEATSpeciationStrategyImpl(EVOLUTION_FIELD_MAP);
	// speciationStrategy.speciate(population);
	// assertThat("",//
	// population.getSpecies(), hasSize(1));
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// assertThat("",//
	// species, hasSize(population.size()));
	// }
	//
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// species.setEliteSize(1);
	// species.setTargetSize(2);
	// }
	//
	// population.refreshStats();
	// Set<Genome<GeneMap>> children = REPRODUCTION_STRATEGY.generateChildren(0,
	// population);
	// speciationStrategy.speciateChildren(children, population);
	//
	// assertThat("",//
	// population.size(), isEqualTo(EVOLUTION_FIELD_MAP.populationSize +
	// children.size()));
	// assertThat("",//
	// population.getSpecies(), hasSize(1));
	// for (NEATSpecies<GeneMap> species : population.getSpecies()) {
	// assertThat("",//
	// species, hasSize(population.size()));
	// }
	//
	// population.refreshStats();
	// speciationStrategy.respeciate(population);
	// assertThat("",//
	// population.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID),//
	// isEqualTo(10.0));
	// population.refreshStats();
	// speciationStrategy.respeciate(population);
	// assertThat("",//
	// population.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID),//
	// isEqualTo(10.0));
	// }
}
