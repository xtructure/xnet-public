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
package com.xtructure.xevolution.evolution.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.Test;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.genetics.impl.PopulationImpl;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestSurvivalFilterImpl {
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
				new SurvivalFilterImpl(EVOLUTION_FIELD_MAP), isNotNull());
	}

	public void markDeadGenomesBehavesAsExpected() {
		PopulationImpl pop = new PopulationImpl(0);
		GeneticsFactoryImpl gf = new GeneticsFactoryImpl(EVOLUTION_FIELD_MAP, "asdf");
		for (int i = 0; i < 20; i++) {
			pop.add(gf.createGenome(i));
		}
		for (Genome<?> genome : pop) {
			assertThat("",//
					genome.isMarkedForDeath(), isFalse());
		}
		new SurvivalFilterImpl(EVOLUTION_FIELD_MAP).markDeadGenomes(pop);
		assertThat("",//
				pop.size(), isEqualTo(20));
		for (Genome<?> genome : pop) {
			assertThat("",//
					genome.isMarkedForDeath(), isTrue());
		}
	}
}
