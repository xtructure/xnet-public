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
import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;

/**
 * {@link AbstractSurvivalFilter} implements the {@link SurvivalFilter}
 * interface, where it marks dead {@link Genome}s in a {@link Population}.
 * <P>
 * It marks all {@link Genome}s for death, so that every generation contains
 * only children of the previous generation.
 * 
 * @author Luis Guimbarda
 */
public abstract class AbstractSurvivalFilter extends AbstractEvolutionObject implements SurvivalFilter {
	/** the {@link EvolutionFieldMap} used by this {@link SurvivalFilter} */
	private final EvolutionFieldMap	evolutionFieldMap;

	/**
	 * Creates a new {@link AbstractSurvivalFilter}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link EvolutionFieldMap} used by this
	 *            {@link SurvivalFilter}
	 */
	public AbstractSurvivalFilter(EvolutionFieldMap evolutionFieldMap) {
		this.evolutionFieldMap = evolutionFieldMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.evolution.SurvivalFilter#getEvolutionFieldMap()
	 */
	@Override
	public EvolutionFieldMap getEvolutionFieldMap() {
		return evolutionFieldMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.evolution.SurvivalFilter#markDeadGenomes(com
	 * .xtructure.xevolution.genetics.Population)
	 */
	@Override
	public void markDeadGenomes(Population<?> population) {
		getLogger().trace("begin %s.markDeadGenomes(%s)", getClass().getSimpleName(), population);

		for (Genome<?> genome : population) {
			genome.markForDeath();
		}

		getLogger().trace("end %s.markDeadGenomes()", getClass().getSimpleName());
	}
}
