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

import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNEATSurvivalFilterImpl {
//	private static final NEATEvolutionFieldMap	EVOLUTION_FIELD_MAP;
//	static {
//		EVOLUTION_FIELD_MAP = NEATEvolutionConfigurationImpl.DEFAULT_CONFIGURATION.newFieldMap();
//	}
//
//	public void constructorSucceeds() {
//		assertThat("",//
//				new NEATSurvivalFilterImpl(EVOLUTION_FIELD_MAP), isNotNull());
//	}
//
//	public void getEvolutionFieldMapReturnsExpectedObject() {
//		assertThat("",//
//				new NEATSurvivalFilterImpl(EVOLUTION_FIELD_MAP).getEvolutionFieldMap(),//
//				isEqualTo(EVOLUTION_FIELD_MAP));
//	}
//
//	public void markDeadGenomesBehavesAsExpected() {
//		NEATGenomeImpl genome0 = new NEATGenomeImpl(0, new GeneMap());
//		NEATGenomeImpl genome1 = new NEATGenomeImpl(1, new GeneMap());
//		genome0.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);
//		genome1.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
//		NEATSpeciesImpl species = new NEATSpeciesImpl(0);
//		species.add(genome0);
//		species.add(genome1);
//		species.setEliteSize(1);
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		population.addSpecies(species);
//		new NEATSurvivalFilterImpl(EVOLUTION_FIELD_MAP).markDeadGenomes(population);
//		assertThat("",//
//				population, hasSize(2), containsMembers(genome0, genome1));
//		assertThat("",//
//				species, hasSize(2), containsMembers(genome1, genome0));
//		assertThat("",//
//				genome0.isMarkedForDeath(), isTrue());
//		assertThat("",//
//				genome1.isMarkedForDeath(), isFalse());
//	}
//
//	public void markDeadSpeciesBehavesAsExpected() {
//		NEATGenomeImpl bestGenome = new NEATGenomeImpl(0, new GeneMap());
//		bestGenome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 100.0);
//		NEATSpeciesImpl bestSpecies = new NEATSpeciesImpl(0);
//		bestSpecies.add(bestGenome);
//		bestSpecies.setTargetSize(1);
//		NEATSpeciesImpl zeroTargetSpecies = new NEATSpeciesImpl(1);
//		zeroTargetSpecies.add(new NEATGenomeImpl(1, new GeneMap()));
//		zeroTargetSpecies.setTargetSize(0);
//		NEATSpeciesImpl stagnantSpecies = new NEATSpeciesImpl(2);
//		stagnantSpecies.add(new NEATGenomeImpl(2, new GeneMap()));
//		stagnantSpecies.setTargetSize(1);
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		population.addSpecies(bestSpecies);
//		population.addSpecies(zeroTargetSpecies);
//		population.addSpecies(stagnantSpecies);
//		population.refreshStats();
//		stagnantSpecies.setAttribute(Population.AGE_ATTRIBUTE_ID, 1000l);
//
//		new NEATSurvivalFilterImpl(EVOLUTION_FIELD_MAP).markDeadSpecies(population);
//		assertThat("",//
//				population.size(), isEqualTo(3));
//		assertThat("",//
//				population.getAllSpecies(), hasSize(3), containsMembers(bestSpecies, zeroTargetSpecies, stagnantSpecies));
//		population.removeDeadSpecies();
//		assertThat("",//
//				population.size(), isEqualTo(1));
//		assertThat("",//
//				population.getAllSpecies(), hasSize(1), containsMember(bestSpecies));
//	}
}
