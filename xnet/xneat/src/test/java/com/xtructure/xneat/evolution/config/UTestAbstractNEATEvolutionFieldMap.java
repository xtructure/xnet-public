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
package com.xtructure.xneat.evolution.config;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isInRange;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.config.DoubleXParameter;
import com.xtructure.xutil.config.IntegerXParameter;
import com.xtructure.xutil.config.LongXParameter;

@Test(groups = { "unit:xevolution" })
public final class UTestAbstractNEATEvolutionFieldMap {
	private static final NEATEvolutionConfiguration	CONFIG;
	private static final NEATEvolutionFieldMap		FIELD_MAP;
	static {
		CONFIG = NEATEvolutionConfigurationImpl.DEFAULT_CONFIGURATION;
		FIELD_MAP = CONFIG.newFieldMap();
	}

	public void constructorSucceeds() {
		assertThat("",//
				FIELD_MAP, isNotNull());
	}

	public void biasNodeCountBehavesAsExpected() {
		IntegerXParameter prm = (IntegerXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.BIAS_NODE_COUNT_ID);
		assertThat("",//
				FIELD_MAP.biasNodeCount(), isInRange(prm.getInitialRange()));
		int next = RandomUtil.nextInteger(prm.getLifetimeRange());
		FIELD_MAP.setBiasNodeCount(next);
		assertThat("",//
				FIELD_MAP.biasNodeCount(), isEqualTo(next));
	}

	public void compatibilityDisjointCoefficientBehavesAsExpected() {
		DoubleXParameter prm = (DoubleXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.COMPATIBILITY_DISJOUNT_COEFFICIENT_ID);
		assertThat("",//
				FIELD_MAP.compatibilityDisjointCoefficient(), isInRange(prm.getInitialRange()));
		double next = RandomUtil.nextDouble(prm.getLifetimeRange());
		FIELD_MAP.setCompatibilityDisjointCoefficient(next);
		assertThat("",//
				FIELD_MAP.compatibilityDisjointCoefficient(), isEqualTo(next));
	}

	public void compatibilityExcessCoefficientBehavesAsExpected() {
		DoubleXParameter prm = (DoubleXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.COMPATIBILITY_EXCESS_COEFFICIENT_ID);
		assertThat("",//
				FIELD_MAP.compatibilityExcessCoefficient(), isInRange(prm.getInitialRange()));
		double next = RandomUtil.nextDouble(prm.getLifetimeRange());
		FIELD_MAP.setCompatibilityExcessCoefficient(next);
		assertThat("",//
				FIELD_MAP.compatibilityExcessCoefficient(), isEqualTo(next));
	}

	public void compatibilityWeightDeltaCoefficientBehavesAsExpected() {
		DoubleXParameter prm = (DoubleXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_ID);
		assertThat("",//
				FIELD_MAP.compatibilityWeightDeltaCoefficient(), isInRange(prm.getInitialRange()));
		double next = RandomUtil.nextDouble(prm.getLifetimeRange());
		FIELD_MAP.setCompatibilityWeightDeltaCoefficient(next);
		assertThat("",//
				FIELD_MAP.compatibilityWeightDeltaCoefficient(), isEqualTo(next));
	}

	public void eliteProportionBehavesAsExpected() {
		DoubleXParameter prm = (DoubleXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.ELITE_PROPORTION_ID);
		assertThat("",//
				FIELD_MAP.eliteProportion(), isInRange(prm.getInitialRange()));
		double next = RandomUtil.nextDouble(prm.getLifetimeRange());
		FIELD_MAP.setEliteProportion(next);
		assertThat("",//
				FIELD_MAP.eliteProportion(), isEqualTo(next));
	}

	public void initialConnectionProbabilityBehavesAsExpected() {
		DoubleXParameter prm = (DoubleXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.INITIAL_CONNECTION_PROBABILITY_ID);
		assertThat("",//
				FIELD_MAP.initialConnectionProbability(), isInRange(prm.getInitialRange()));
		double next = RandomUtil.nextDouble(prm.getLifetimeRange());
		FIELD_MAP.setInitialConnectionProbability(next);
		assertThat("",//
				FIELD_MAP.initialConnectionProbability(), isEqualTo(next));
	}

	public void inputNodeCountBehavesAsExpected() {
		IntegerXParameter prm = (IntegerXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.INPUT_NODE_COUNT_ID);
		assertThat("",//
				FIELD_MAP.inputNodeCount(), isInRange(prm.getInitialRange()));
		int next = RandomUtil.nextInteger(prm.getLifetimeRange());
		FIELD_MAP.setInputNodeCount(next);
		assertThat("",//
				FIELD_MAP.inputNodeCount(), isEqualTo(next));
	}

	public void interspeciesCrossoverProbabilityBehavesAsExpected() {
		DoubleXParameter prm = (DoubleXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.INTERSPECIES_CROSSOVER_PROBABILITY_ID);
		assertThat("",//
				FIELD_MAP.interspeciesCrossoverProbability(), isInRange(prm.getInitialRange()));
		double next = RandomUtil.nextDouble(prm.getLifetimeRange());
		FIELD_MAP.setInitialConnectionProbability(next);
		assertThat("",//
				FIELD_MAP.interspeciesCrossoverProbability(), isEqualTo(next));
	}

	public void outputNodeCountBehavesAsExpected() {
		IntegerXParameter prm = (IntegerXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.OUTPUT_NODE_COUNT_ID);
		assertThat("",//
				FIELD_MAP.outputNodeCount(), isInRange(prm.getInitialRange()));
		int next = RandomUtil.nextInteger(prm.getLifetimeRange());
		FIELD_MAP.setOutputNodeCount(next);
		assertThat("",//
				FIELD_MAP.outputNodeCount(), isEqualTo(next));
	}

	public void speciesDropoffAgeBehavesAsExpected() {
		LongXParameter prm = (LongXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.SPECIES_DROPOFF_AGE_ID);
		assertThat("",//
				FIELD_MAP.speciesDropoffAge(), isInRange(prm.getInitialRange()));
		long next = RandomUtil.nextLong(prm.getLifetimeRange());
		FIELD_MAP.setSpeciesDropoffAge(next);
		assertThat("",//
				FIELD_MAP.speciesDropoffAge(), isEqualTo(next));
	}

	public void targetSpeciesCountMaxBehavesAsExpected() {
		IntegerXParameter prm = (IntegerXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.TARGET_SPECIES_COUNT_MAX_ID);
		assertThat("",//
				FIELD_MAP.targetSpeciesCountMax(), isInRange(prm.getInitialRange()));
		int next = RandomUtil.nextInteger(prm.getLifetimeRange());
		FIELD_MAP.setTargetSpeciesCountMax(next);
		assertThat("",//
				FIELD_MAP.targetSpeciesCountMax(), isEqualTo(next));
	}

	public void targetSpeciesCountMinBehavesAsExpected() {
		IntegerXParameter prm = (IntegerXParameter) CONFIG.getParameter(NEATEvolutionConfiguration.TARGET_SPECIES_COUNT_MIN_ID);
		assertThat("",//
				FIELD_MAP.targetSpeciesCountMin(), isInRange(prm.getInitialRange()));
		int next = RandomUtil.nextInteger(prm.getLifetimeRange());
		FIELD_MAP.setTargetSpeciesCountMin(next);
		assertThat("",//
				FIELD_MAP.targetSpeciesCountMin(), isEqualTo(next));
	}
}
