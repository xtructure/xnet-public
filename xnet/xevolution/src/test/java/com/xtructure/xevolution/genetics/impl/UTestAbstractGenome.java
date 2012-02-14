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
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestAbstractGenome {
	public void constructorSucceeds() {
		assertThat("",//
				new DummyGenome(1, ""), isNotNull());
		assertThat("",//
				new DummyGenome(2, new DummyGenome(1, "")), isNotNull());
	}

	public void getBaseIdReturnsExpectedId() {
		assertThat("",//
				new DummyGenome(0, "").getBaseId(), isEqualTo(XId.newId("Genome")));
	}

	public void getComplexityReturnsExpectedInt() {
		Genome<?> genome = new DummyGenome(0, "");
		double value = RandomUtil.nextDouble();
		genome.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, value);
		assertThat("",//
				genome.getComplexity(), isEqualTo(genome.getAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID)));
	}

	public void getDataReturnsExpectedObject() {
		String data = Integer.toString(RandomUtil.nextInteger());
		assertThat("",//
				new DummyGenome(0, data).getData(), isEqualTo(data));
		assertThat("",//
				new DummyGenome(1, new DummyGenome(0, data)).getData(), isEqualTo(data));
	}

	public void getEvaluationCountAndIncrementEvaluationCountBehaveAsExpected() {
		Genome<?> genome = new DummyGenome(0, "");
		for (long i = 0; i < 10; i++) {
			assertThat("",//
					genome.getEvaluationCount(), isEqualTo(i));
			genome.incrementEvaluationCount();
		}
	}

	public void getFitnessReturnsExpectedDouble() {
		Genome<?> genome = new DummyGenome(0, "");
		assertThat("",//
				genome.getFitness(), isEqualTo(0.0));
		double fitness = RandomUtil.nextDouble();
		genome.setFitness(fitness);
		assertThat("",//
				genome.getFitness(), isEqualTo(fitness));
		assertThat("",//
				genome.getAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(fitness));
	}

	public void getIdReturnsExpectedId() {
		assertThat("",//
				new DummyGenome(0, "").getId(), isEqualTo(XId.newId("Genome").createChild(0)));
	}

	public void isMarkedAndMarkForDeathBehaveAsExpected() {
		Genome<?> genome = new DummyGenome(0, "");
		assertThat("",//
				genome.isMarkedForDeath(), isFalse());
		genome.markForDeath();
		assertThat("",//
				genome.isMarkedForDeath(), isTrue());
	}

	public void toStringReturnsExpectedString() {
		assertThat("",//
				new DummyGenome(0, "").toString(), isEqualTo(XId.newId("Genome").createChild(0).toString()));
	}
}
