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
package com.xtructure.xneat.evolution;

import com.xtructure.xevolution.evolution.EvolutionExperiment;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;

/**
 * @author Luis Guimbarda
 * 
 * @param <D>
 * @param <T>
 */
public interface NEATEvolutionExperiment<D, T> extends EvolutionExperiment<D, T> {
	public NEATSpeciationStrategy<D> getSpeciationStrategy();

	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap();

	@Override
	public NEATGeneticsFactory<D> getGeneticsFactory();

	@Override
	public NEATReproductionStrategy<D> getReproductionStrategy();

	@Override
	public NEATSurvivalFilter getSurvivalFilter();

	@Override
	public NEATEvolutionStrategy<D, T> getEvolutionStrategy();
}
