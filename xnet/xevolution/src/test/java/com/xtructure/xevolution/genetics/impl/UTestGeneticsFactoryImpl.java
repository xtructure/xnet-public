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
package com.xtructure.xevolution.genetics.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElements;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThan;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestGeneticsFactoryImpl {
	private static final EvolutionFieldMap	EVOLUTION_FIELD_MAP;
	static {
		EVOLUTION_FIELD_MAP = EvolutionConfigurationImpl//
				.builder(XId.newId("default.evolution.config"))//
				.setPopulationSize(1000)//
				.setMutationProbability(1.0)//
				.newInstance().newFieldMap();
	}

	public void constructorSucceeds() {
		assertThat("",//
				new GeneticsFactoryImpl(EVOLUTION_FIELD_MAP, "asdf"), isNotNull());
	}

	public void copyGenomeReturnsExpectedGenome() {
		String defaultData = Integer.toString(RandomUtil.nextInteger());
		GeneticsFactoryImpl geneticsFactory = new GeneticsFactoryImpl(EVOLUTION_FIELD_MAP, defaultData);
		GenomeImpl genome0 = geneticsFactory.createGenome(0);
		XValId<String> valueId = XValId.newId("string", String.class);
		genome0.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		genome0.setAttribute(valueId, "xxxxxxxx");
		genome0.incrementEvaluationCount();
		GenomeImpl genome0Dup = geneticsFactory.copyGenome(1, genome0);
		assertThat("",//
				genome0.getId(), isEqualTo(XId.newId("Genome").createChild(0)));
		assertThat("",//
				genome0Dup.getId(), isEqualTo(XId.newId("Genome").createChild(1)));
		assertThat("",//
				genome0Dup.getData(), isEqualTo(genome0.getData()));
		assertThat("",//
				genome0.getEvaluationCount(), isEqualTo(1l));
		assertThat("",//
				genome0Dup.getEvaluationCount(), isEqualTo(0l));
		assertThat("",//
				genome0.getAttributes().keySet(), hasSize(6), containsElements(Genome.DEATH_MARK_ATTRIBUTE_ID, Genome.FITNESS_ATTRIBUTE_ID, Genome.AGE_ATTRIBUTE_ID, Genome.EVAL_COUNT_ATTRIBUTE_ID, Genome.COMPLEXITY_ATTRIBUTE_ID, valueId));
		assertThat("",//
				genome0Dup.getAttributes().keySet(), hasSize(5), containsElements(Genome.DEATH_MARK_ATTRIBUTE_ID, Genome.FITNESS_ATTRIBUTE_ID, Genome.AGE_ATTRIBUTE_ID, Genome.EVAL_COUNT_ATTRIBUTE_ID, Genome.COMPLEXITY_ATTRIBUTE_ID));
		assertThat("",//
				genome0Dup.getAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(0.0));
	}

	public void createGenomeReturnsExpectedGenome() {
		String defaultData = Integer.toString(RandomUtil.nextInteger());
		GeneticsFactoryImpl geneticsFactory = new GeneticsFactoryImpl(EVOLUTION_FIELD_MAP, defaultData);
		GenomeImpl genome0 = geneticsFactory.createGenome(0);
		assertThat("",//
				genome0.getId(), isEqualTo(XId.newId("Genome").createChild(0)));
		assertThat("",//
				genome0.getData(), isEqualTo(defaultData));
		assertThat("",//
				genome0.getEvaluationCount(), isEqualTo(0l));
		assertThat("",//
				genome0.getAttributes().keySet(), hasSize(5), containsElements(Genome.DEATH_MARK_ATTRIBUTE_ID, Genome.FITNESS_ATTRIBUTE_ID, Genome.EVAL_COUNT_ATTRIBUTE_ID, Genome.DEATH_MARK_ATTRIBUTE_ID, Genome.AGE_ATTRIBUTE_ID));
		assertThat("",//
				genome0.getAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(0.0));
		String newData = defaultData + defaultData;
		GenomeImpl genome1 = geneticsFactory.createGenome(1, newData);
		assertThat("",//
				genome1.getId(), isEqualTo(XId.newId("Genome").createChild(1)));
		assertThat("",//
				genome1.getData(), isEqualTo(newData));
		assertThat("",//
				genome1.getEvaluationCount(), isEqualTo(0l));
		assertThat("",//
				genome1.getAttributes().keySet(), hasSize(5), containsElements(Genome.DEATH_MARK_ATTRIBUTE_ID, Genome.EVAL_COUNT_ATTRIBUTE_ID, Genome.DEATH_MARK_ATTRIBUTE_ID, Genome.AGE_ATTRIBUTE_ID, Genome.FITNESS_ATTRIBUTE_ID));
		assertThat("",//
				genome1.getAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(0.0));
	}

	public void createPopulationReturnsExpectedPopulation() {
		String defaultData = Integer.toString(RandomUtil.nextInteger());
		GeneticsFactoryImpl geneticsFactory = new GeneticsFactoryImpl(EVOLUTION_FIELD_MAP, defaultData);
		Population<String> pop = geneticsFactory.createPopulation(0);
		pop.validate();
		assertThat("",//
				pop.size(), isEqualTo(EVOLUTION_FIELD_MAP.populationSize()));
		assertThat("",//
				pop.getAge(), isEqualTo(0l));
		assertThat("",//
				pop.getAgeLastImproved(), isEqualTo(0l));
		assertThat("",//
				pop.getGenomeIdNumber(), isEqualTo(EVOLUTION_FIELD_MAP.populationSize()));
		for (Genome<String> genome : pop) {
			assertThat("",//
					genome.getId().getInstanceNum(), isGreaterThanOrEqualTo(0), isLessThan(pop.getGenomeIdNumber()));
			assertThat("",//
					genome.getData(), isEqualTo(defaultData));
		}
	}
}
