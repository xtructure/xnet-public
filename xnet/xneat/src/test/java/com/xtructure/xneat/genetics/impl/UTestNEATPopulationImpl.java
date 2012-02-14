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
package com.xtructure.xneat.genetics.impl;

import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNEATPopulationImpl {
//	public void constructorSucceeds() {
//		assertThat("",//
//				new NEATPopulationImpl(0), isNotNull());
//	}
//
//	public void addAndGetAndClearSpeciesBehaveAsExpected() {
//		NEATGenomeImpl genome0 = new NEATGenomeImpl(0, new GeneMap());
//		NEATGenomeImpl genome1 = new NEATGenomeImpl(1, new GeneMap());
//		NEATSpeciesImpl species = new NEATSpeciesImpl(0);
//		species.add(genome0);
//		species.add(genome1);
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		assertThat("",//
//				population.size(), isEqualTo(0));
//		assertThat("",//
//				population.getSpecies(species.getId()), isNull());
//		population.addSpecies(species);
//		assertThat("",//
//				population.size(), isEqualTo(2));
//		assertThat("",//
//				population.getSpecies(species.getId()), isEqualTo(species));
//		assertThat("",//
//				population.getAllSpecies(), hasSize(1), containsMember(species));
//		assertThat("",//
//				population, hasSize(2), containsMembers(genome0, genome1));
//		population.clear();
//		assertThat("",//
//				population, isEmpty());
//		assertThat("",//
//				species, isEmpty());
//		assertThat("",//
//				population.getAllSpecies(), hasSize(1), containsMember(species));
//		population = new NEATPopulationImpl(0);
//		species = new NEATSpeciesImpl(0);
//		species.add(genome0);
//		species.add(genome1);
//		population.addSpecies(species);
//		population.clearSpecies();
//		assertThat("",//
//				population.getAllSpecies(), isEmpty());
//		assertThat("",//
//				population, hasSize(2), containsMembers(genome0, genome1));
//
//	}
//
//	public void getSpeciesIdNumberReturnsExpectedInteger() {
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		assertThat("",//
//				population.getSpeciesIdNumber(), isEqualTo(0));
//		population.addSpecies(new NEATSpeciesImpl(2));
//		assertThat("",//
//				population.getSpeciesIdNumber(), isEqualTo(3));
//		population.addSpecies(new NEATSpeciesImpl(1));
//		assertThat("",//
//				population.getSpeciesIdNumber(), isEqualTo(3));
//		population.clearSpecies();
//		assertThat("",//
//				population.getSpeciesIdNumber(), isEqualTo(3));
//	}
//
//	public void removeGenomeBehavesAsExpected() {
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		NEATGenomeImpl genome = new NEATGenomeImpl(0, new GeneMap());
//		population.add(genome);
//		assertThat("",//
//				population.get(genome.getId()), isEqualTo(genome));
//		population.remove(genome);
//		assertThat("",//
//				population.get(genome.getId()), isNull());
//
//		NEATSpeciesImpl species = new NEATSpeciesImpl(0);
//		species.add(genome);
//		population.addSpecies(species);
//		assertThat("",//
//				species.get(genome.getId()), isEqualTo(genome));
//		assertThat("",//
//				population.get(genome.getId()), isEqualTo(genome));
//		population.remove(genome);
//		assertThat("",//
//				species.get(genome.getId()), isNull());
//		assertThat("",//
//				population.get(genome.getId()), isNull());
//
//		species.clear();
//		population.clearSpecies();
//		population.clear();
//
//		species.add(genome);
//		population.addSpecies(species);
//		assertThat("",//
//				species.get(genome.getId()), isEqualTo(genome));
//		assertThat("",//
//				population.get(genome.getId()), isEqualTo(genome));
//		population.remove(genome.getId());
//		assertThat("",//
//				species.get(genome.getId()), isNull());
//		assertThat("",//
//				population.get(genome.getId()), isNull());
//	}
//
//	public void removeSpeciesBehavesAsExpected() {
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		NEATGenomeImpl genome = new NEATGenomeImpl(0, new GeneMap());
//		NEATSpeciesImpl species = new NEATSpeciesImpl(0);
//		species.add(genome);
//		population.addSpecies(species);
//		assertThat("",//
//				population.get(genome.getId()), isEqualTo(genome));
//		assertThat("",//
//				population.getSpecies(species.getId()), isEqualTo(species));
//		population.removeSpecies(species.getId());
//		assertThat("",//
//				population.get(genome.getId()), isNull());
//		assertThat("",//
//				population.getSpecies(species.getId()), isNull());
//
//		species.add(genome);
//		population.addSpecies(species);
//		assertThat("",//
//				population.get(genome.getId()), isEqualTo(genome));
//		assertThat("",//
//				population.getSpecies(species.getId()), isEqualTo(species));
//		population.removeSpecies(species);
//		assertThat("",//
//				population.get(genome.getId()), isNull());
//		assertThat("",//
//				population.getSpecies(species.getId()), isNull());
//
//		species.add(genome);
//		population.addSpecies(species);
//		assertThat("",//
//				population.get(genome.getId()), isEqualTo(genome));
//		assertThat("",//
//				population.getSpecies(species.getId()), isEqualTo(species));
//		population.removeAllSpecies(Collections.singleton(species.getId()));
//		assertThat("",//
//				population.get(genome.getId()), isNull());
//		assertThat("",//
//				population.getSpecies(species.getId()), isNull());
//	}
//
//	public void validateBehavesAsExpected() {
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		NEATGenomeImpl genome = new NEATGenomeImpl(0, new GeneMap());
//		NEATSpeciesImpl species = new NEATSpeciesImpl(0);
//		species.add(genome);
//		population.addSpecies(species);
//		population.validate();
//	}
//
//	@Test(expectedExceptions = { IllegalStateException.class })
//	public void validateWithInvalidSpeciesThrowsException() {
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		NEATGenomeImpl genome = new NEATGenomeImpl(0, new GeneMap());
//		NEATSpeciesImpl species = new NEATSpeciesImpl(0);
//		species.add(genome);
//		population.addSpecies(species);
//		genome.setSpeciesId(null);
//		population.validate();
//	}
//
//	public void incrementAgeBehavesAsExpected() {
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		assertThat("",//
//				population.getAge(), isEqualTo(0l));
//		population.incrementAge();
//		assertThat("",//
//				population.getAge(), isEqualTo(1l));
//	}
//
//	public void refreshStatsBehavesAsExpected() {
//	// TODO
//	}
//
//	public void removeDeadSpeciesBehavesAsExpected() {
//	// TODO
//	}
}
