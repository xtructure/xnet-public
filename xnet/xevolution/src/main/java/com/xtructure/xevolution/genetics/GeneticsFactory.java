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
package com.xtructure.xevolution.genetics;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.EvolutionObject;

/**
 * The {@link GeneticsFactory} interface describes methods for creating and
 * copying {@link Genome}s, their payload data, and {@link Population}s.
 * {@link Population}s are created with a number of new {@link Genome}s
 * specified by {@link EvolutionFieldMap#populationSize()}.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 */
public interface GeneticsFactory<D> extends EvolutionObject {
	/**
	 * Creates a new data for fresh {@link Genome}s.
	 * 
	 * @return the new data
	 */
	public D createData();

	/**
	 * Copies the given data to be used by another {@link Genome}.
	 * 
	 * @return the copy of the given data
	 */
	public D copyData(D data);

	/**
	 * Creates a new {@link Genome}.
	 * 
	 * @param idNumber
	 *            the instance number of the id for the new genome
	 * @return the new {@link Genome}
	 */
	public Genome<D> createGenome(int idNumber);

	/**
	 * Creates a new {@link Genome} with the given data.
	 * 
	 * @param idNumber
	 *            the instance number of the id for the new genome.
	 * @param data
	 *            the data for the new genome.
	 * @return the new {@link Genome}
	 */
	public Genome<D> createGenome(int idNumber, D data);

	/**
	 * Creates a new {@link Genome} that's a copy of the given genome.
	 * 
	 * @param idNumber
	 *            the instance number of the id for the new genome.
	 * @param genome
	 *            the genome whose data is to be copied
	 * @return the new {@link Genome}
	 */
	public Genome<D> copyGenome(int idNumber, Genome<D> genome);

	/**
	 * Creates a new {@link Population} of fresh {@link Genome}s.
	 * 
	 * @param idNumber
	 *            the instance number of the id for the new population
	 * @return the new {@link Population} with a number of {@link Genome}s as
	 *         specified by this {@link GeneticsFactory}'s
	 *         {@link EvolutionFieldMap}.
	 */
	public Population<D> createPopulation(int idNumber);

	/**
	 * Returns the {@link EvolutionFieldMap} used by this
	 * {@link GeneticsFactory}.
	 * 
	 * @return the {@link EvolutionFieldMap} used by this
	 *         {@link GeneticsFactory}.
	 */
	public EvolutionFieldMap getEvolutionFieldMap();
}
