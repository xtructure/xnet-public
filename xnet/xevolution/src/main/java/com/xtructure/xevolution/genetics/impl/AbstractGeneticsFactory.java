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

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.impl.AbstractEvolutionObject;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;

/**
 * {@link AbstractGeneticsFactory} implements getters for
 * {@link GeneticsFactory}s.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 */
public abstract class AbstractGeneticsFactory<D> extends AbstractEvolutionObject implements GeneticsFactory<D> {
	/** {@link EvolutionFieldMap} used by this {@link GeneticsFactory} */
	private final EvolutionFieldMap	evolutionFieldMap;

	/**
	 * Creates a new {@link AbstractGeneticsFactory}.
	 * 
	 * @param evolutionFieldMap
	 *            the {@link EvolutionFieldMap} used by this
	 *            {@link GeneticsFactory}
	 */
	public AbstractGeneticsFactory(EvolutionFieldMap evolutionFieldMap) {
		this.evolutionFieldMap = evolutionFieldMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsFactory#getEvolutionFieldMap()
	 */
	@Override
	public EvolutionFieldMap getEvolutionFieldMap() {
		return evolutionFieldMap;
	}
}
