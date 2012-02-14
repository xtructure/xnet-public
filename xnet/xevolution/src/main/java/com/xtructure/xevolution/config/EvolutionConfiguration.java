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
package com.xtructure.xevolution.config;

import static com.xtructure.xutil.valid.ValidateUtils.isNothing;

import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.valid.Condition;

/**
 * The Interface EvolutionConfiguration.
 *
 * @author Luis Guimbarda
 */
public interface EvolutionConfiguration extends XConfiguration {
	
	/** The Constant POPULATION_SIZE_ID. */
	public static final XValId<Integer>		POPULATION_SIZE_ID					= XValId.newId("populationSize", Integer.class);
	
	/** The Constant MUTATION_PROBABILITY_ID. */
	public static final XValId<Double>		MUTATION_PROBABILITY_ID				= XValId.newId("mutationProbability", Double.class);
	
	/** The Constant TERMINATION_CONDITION_ID. */
	public static final XValId<Condition>	TERMINATION_CONDITION_ID			= XValId.newId("terminationCondition", Condition.class);
	
	/** The Constant POPULATION_SIZE_DESCRIPTION. */
	public static final String				POPULATION_SIZE_DESCRIPTION			= "the number of genomes in the population";
	
	/** The Constant MUTATION_PROBABILITY_DESCRIPTION. */
	public static final String				MUTATION_PROBABILITY_DESCRIPTION	= "the probability a selected genome will be mutated";
	
	/** The Constant TERMINATION_CONDITION_DESCRIPTION. */
	public static final String				TERMINATION_CONDITION_DESCRIPTION	= "the condition which, when satisfied, indicates the end of evolution";
	
	/** The Constant POPULATION_SIZE_DEFAULT. */
	public static final int					POPULATION_SIZE_DEFAULT				= 100;
	
	/** The Constant MUTATION_PROBABILITY_DEFAULT. */
	public static final double				MUTATION_PROBABILITY_DEFAULT		= 0.5;
	
	/** The Constant TERMINATION_CONDITION_DEFAULT. */
	public static final Condition			TERMINATION_CONDITION_DEFAULT		= isNothing();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xutil.config.XConfiguration#newFieldMap()
	 */
	@Override
	public EvolutionFieldMap newFieldMap();
}
