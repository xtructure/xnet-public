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
package com.xtructure.xevolution.tool;

import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;

/**
 * The Interface PopulationData.
 *
 * @param <D> the generic type
 * @author Luis Guimbarda
 */
public interface PopulationData<D extends PopulationData<D>> extends DataXIdObject<D> {
	
	/** the name of the fittest genome data. */
	public static final String	FITTEST_GENOME	= "fittest";

	/**
	 * Returns the id of the fittest genome in the {@link PopulationData}.
	 *
	 * @return the fittest genome id
	 */
	public XId getFittestGenomeId();

	/**
	 * A factory for creating PopulationData objects.
	 *
	 * @param <D> the generic type
	 * {@link PopulationData} object factory interface
	 */
	public static interface PopulationDataFactory<D extends PopulationData<D>> extends DataXIdObjectFactory<D> {
		
		/**
		 * Gets or creates instances of {@link PopulationData}.
		 *
		 * @param population the population
		 * @param manager the manager
		 * @return the instance
		 */
		public D getOrCreateInstance(Population<?> population, XIdObjectManager<D> manager);

		/**
		 * Creates instances of {@link PopulationData}.
		 *
		 * @param population the population
		 * @param manager the manager
		 * @return the D
		 */
		public D createInstance(Population<?> population, XIdObjectManager<D> manager);
	}
}
