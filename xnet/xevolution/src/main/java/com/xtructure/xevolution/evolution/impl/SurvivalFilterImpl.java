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

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;

/**
 * An implementation of {@link SurvivalFilter}, where it marks dead
 * {@link Genome}s in a {@link Population} with a String type data.
 * <P>
 * It marks all {@link Genome}s for death, so that every generation contains
 * only children of the previous generation.
 * <P>
 * {@link SurvivalFilterImpl} instances will not remove genomes, leaving them
 * for removal at the discretion of the {@link EvolutionStrategy}.
 * 
 * @author Luis Guimbarda
 * 
 */
public final class SurvivalFilterImpl extends AbstractSurvivalFilter {
	/**
	 * Creates a new {@link SurvivalFilterImpl}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link EvolutionFieldMap} used by this
	 *            {@link SurvivalFilter}
	 */
	public SurvivalFilterImpl(EvolutionFieldMap evolutionFieldMap) {
		super(evolutionFieldMap);
	}
}
