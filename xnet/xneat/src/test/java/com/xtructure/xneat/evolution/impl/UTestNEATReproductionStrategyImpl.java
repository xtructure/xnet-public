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

import com.xtructure.xevolution.operator.impl.CopyCrossoverOperator;
import com.xtructure.xevolution.operator.impl.CopyMutateOperator;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.operators.impl.NEATCrossoverOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.NEATMutateOperatorSelecterImpl;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNEATReproductionStrategyImpl {
	private static final NEATEvolutionFieldMap				EVOLUTION_FIELD_MAP;
	private static final NEATGeneticsFactoryImpl			GENETICS_FACTORY;
	private static final NEATCrossoverOperatorSelecterImpl	CROSSOVER_OPERATOR_SELECTER;
	private static final NEATMutateOperatorSelecterImpl		MUTATE_OPERATOR_SELECTER;
	static {
		EVOLUTION_FIELD_MAP = NEATEvolutionConfigurationImpl//
				.builder(XId.newId("xor.neat.evolution.config"))//
				.setPopulationSize(10)//
				.setMutationProbability(0.5)//
				.setInterspeciesCrossoverProbability(0.5)//
				.newInstance().newFieldMap();
		GENETICS_FACTORY = new NEATGeneticsFactoryImpl(EVOLUTION_FIELD_MAP);
		CROSSOVER_OPERATOR_SELECTER = new NEATCrossoverOperatorSelecterImpl();
		CROSSOVER_OPERATOR_SELECTER.add(new CopyCrossoverOperator<GeneMap>(GENETICS_FACTORY, 0.5), 1.0);
		MUTATE_OPERATOR_SELECTER = new NEATMutateOperatorSelecterImpl();
		MUTATE_OPERATOR_SELECTER.add(new CopyMutateOperator<GeneMap>(GENETICS_FACTORY), 1.0);
	}

	public void constructorSucceeds() {
		assertThat("",//
				new NEATReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY, CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER),//
				isNotNull());
	}

	// public void generateChildrenReturnsExpectedSet() {
	// NEATReproductionStrategyImpl reproductionStrategy = new
	// NEATReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY,
	// CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER);
	// NEATSpeciesImpl species0 = new NEATSpeciesImpl(0);
	// NEATSpeciesImpl species1 = new NEATSpeciesImpl(1);
	// for (Genome<GeneMap> genome : GENETICS_FACTORY.createPopulation(0)) {
	// if (genome.getId().getInstanceNum() % 2 == 0) {
	// species0.add(genome);
	// } else {
	// species1.add(genome);
	// }
	// }
	// NEATPopulationImpl population = new NEATPopulationImpl(0);
	// population.addSpecies(species0);
	// population.addSpecies(species1);
	// species0.setEliteSize(1);
	// species0.setTargetSize(11);
	// species1.setEliteSize(1);
	// species1.setTargetSize(11);
	//
	// population.setAttribute(Population.GENOME_NUM_ATTRIBUTE_ID, 100);
	// Set<Genome<GeneMap>> genomes =
	// reproductionStrategy.generateChildren(population);
	// assertThat("",//
	// genomes, hasSize(20));// 11-1 + 11-1
	// for (Genome<GeneMap> genome : genomes) {
	// assertThat("",//
	// population, not(containsMember(genome)));
	// assertThat("",//
	// genome.getId().getInstanceNum(), isGreaterThanOrEqualTo(100));
	// }
	// }
	public void getEvolutionFieldMapReturnsExpectedObject() {
		assertThat("",//
				new NEATReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY, CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER).getEvolutionFieldMap(),//
				isEqualTo(EVOLUTION_FIELD_MAP));
	}

	public void getGeneticsFactoryReturnsExpectedObject() {
		assertThat("",//
				new NEATReproductionStrategyImpl(EVOLUTION_FIELD_MAP, GENETICS_FACTORY, CROSSOVER_OPERATOR_SELECTER, MUTATE_OPERATOR_SELECTER).getGeneticsFactory(),//
				isEqualTo(GENETICS_FACTORY));
	}
}
