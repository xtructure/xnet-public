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

import com.xtructure.xevolution.config.EvolutionConfiguration;
import com.xtructure.xutil.id.XValId;

/**
 * @author Luis Guimbarda
 * 
 */
public interface NEATEvolutionConfiguration extends EvolutionConfiguration {
	public static final XValId<Integer>	INPUT_NODE_COUNT_ID									= XValId.newId("inputNodeCount", Integer.class);
	public static final XValId<Integer>	OUTPUT_NODE_COUNT_ID								= XValId.newId("outputNodeCount", Integer.class);
	public static final XValId<Integer>	BIAS_NODE_COUNT_ID									= XValId.newId("biasNodeCount", Integer.class);
	public static final XValId<Double>	INITIAL_CONNECTION_PROBABILITY_ID					= XValId.newId("initialConnectionProbability", Double.class);
	public static final XValId<Integer>	TARGET_SPECIES_COUNT_MIN_ID							= XValId.newId("targetSpeciesCountMin", Integer.class);
	public static final XValId<Integer>	TARGET_SPECIES_COUNT_MAX_ID							= XValId.newId("targatSpeciesCountMax", Integer.class);
	public static final XValId<Double>	ELITE_PROPORTION_ID									= XValId.newId("eliteProportion", Double.class);
	public static final XValId<Double>	INTERSPECIES_CROSSOVER_PROBABILITY_ID				= XValId.newId("interspeciesCrossoverProbability", Double.class);
	public static final XValId<Long>	SPECIES_DROPOFF_AGE_ID								= XValId.newId("speciesDropoffAge", Long.class);
	public static final XValId<Double>	COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_ID			= XValId.newId("compatibilityWeightDeltaCoefficient", Double.class);
	public static final XValId<Double>	COMPATIBILITY_DISJOUNT_COEFFICIENT_ID				= XValId.newId("compatibilityDisjointCoefficient", Double.class);
	public static final XValId<Double>	COMPATIBILITY_EXCESS_COEFFICIENT_ID					= XValId.newId("compatibilityExcessCoefficient", Double.class);
	public static final String			INPUT_NODE_COUNT_DESCRIPTION						= "number of input nodes in phenotype networks";
	public static final String			OUTPUT_NODE_COUNT_DESCRIPTION						= "number of output nodes in phenotype networks";
	public static final String			BIAS_NODE_COUNT_DESCRIPTION							= "number of bias nodes in phenotype networks";
	public static final String			INITIAL_CONNECTION_PROBABILITY_DESCRIPTION			= "when creating initial genome, likelihood an input is linked to an output";
	public static final String			TARGET_SPECIES_COUNT_MIN_DESCRIPTION				= "minimum acceptable number of species";
	public static final String			TARGET_SPECIES_COUNT_MAX_DESCRIPTION				= "maximum acceptable number of species";
	public static final String			ELITE_PROPORTION_DESCRIPTION						= "proportion of surviving genomes in a species per generation";
	public static final String			INTERSPECIES_CROSSOVER_PROBABILITY_DESCRIPTION		= "likelihood a genome will crossover with one of a different species";
	public static final String			SPECIES_DROPOFF_AGE_DESCRIPTION						= "number of generations to wait for improvement before species die";
	public static final String			COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_DESCRIPTION	= "gene weight delta coefficient for calculating genome compatibility";
	public static final String			COMPATIBILITY_DISJOUNT_COEFFICIENT_DESCRIPTION		= "disjoint gene coefficient for calculating genome compatibility";
	public static final String			COMPATIBILITY_EXCESS_COEFFICIENT_DESCRIPTION		= "excess gene coefficient for calculating genome compatibility";
	public static final int				INPUT_NODE_COUNT_DEFAULT							= 1;
	public static final int				OUTPUT_NODE_COUNT_DEFAULT							= 1;
	public static final int				BIAS_NODE_COUNT_DEFAULT								= 1;
	public static final double			INITIAL_CONNECTION_PROBABILITY_DEFAULT				= 1.0;
	public static final int				TARGET_SPECIES_COUNT_MIN_DEFAULT					= 6;
	public static final int				TARGET_SPECIES_COUNT_MAX_DEFAULT					= 12;
	public static final double			ELITE_PROPORTION_DEFAULT							= 0.2;
	public static final double			INTERSPECIES_CROSSOVER_PROBABILITY_DEFAULT			= 0.01;
	public static final long			SPECIES_DROPOFF_AGE_DEFAULT							= 100;
	public static final double			COMPATIBILITY_WEIGHT_DELTA_COEFFICIENT_DEFAULT		= 0.1;
	public static final double			COMPATIBILITY_DISJOUNT_COEFFICIENT_DEFAULT			= 1.0;
	public static final double			COMPATIBILITY_EXCESS_COEFFICIENT_DEFAULT			= 1.0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.config.EvolutionConfiguration#newFieldMap()
	 */
	@Override
	public NEATEvolutionFieldMap newFieldMap();
}
