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

import com.xtructure.xevolution.evolution.impl.AbstractEvolutionObject;
import com.xtructure.xneat.evolution.NEATSpeciationStrategy;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;

/**
 * {@link AbstractNEATSpeciationStrategy} implements the getters for the
 * {@link NEATSpeciationStrategy} interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractNEATSpeciationStrategy<D> extends AbstractEvolutionObject implements NEATSpeciationStrategy<D> {
	/**
	 * the {@link NEATEvolutionFieldMap} used by this
	 * {@link AbstractNEATSpeciationStrategy}
	 */
	private final NEATEvolutionFieldMap	evolutionFieldMap;

	/**
	 * Creates a new {@link AbstractNEATSpeciationStrategy}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by this
	 *            {@link AbstractNEATSpeciationStrategy}
	 */
	public AbstractNEATSpeciationStrategy(NEATEvolutionFieldMap evolutionFieldMap) {
		this.evolutionFieldMap = evolutionFieldMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xneat.evolution.NEATSpeciationStrategy#getEvolutionFieldMap
	 * ()
	 */
	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap() {
		return evolutionFieldMap;
	}
}
