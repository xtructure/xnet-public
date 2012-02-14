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
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;

/**
 * {@link GeneticsFactoryImpl} implements the {@link GeneticsFactory} interface,
 * where the payload data for {@link Genome}s it produces is of type String.
 * 
 * @author Luis Guimbarda
 * 
 */
public class GeneticsFactoryImpl extends AbstractGeneticsFactory<String> {
	/** default data for new {@link Genome}s */
	private final String	defaultData;

	/**
	 * Creates a new {@link GeneticsFactoryImpl}.
	 * 
	 * @param evolutionFieldMap
	 *            the {@link EvolutionFieldMap} used by this
	 *            {@link GeneticsFactoryImpl}
	 * @param defaultData
	 *            the default data to give to new {@link Genome}s.
	 */
	public GeneticsFactoryImpl(EvolutionFieldMap evolutionFieldMap, String defaultData) {
		super(evolutionFieldMap);
		this.defaultData = defaultData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createData()
	 */
	@Override
	public String createData() {
		getLogger().trace("begin %s.createData()", getClass().getSimpleName());

		String rVal = defaultData;

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.createData()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsFactory#copyData(java.lang.
	 * Object)
	 */
	@Override
	public String copyData(String data) {
		getLogger().trace("begin %s.copyData(%s)", getClass().getSimpleName(), data);

		String rVal = data;

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.copyData()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#copyGenome(int,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public GenomeImpl copyGenome(int idNumber, Genome<String> genome) {
		getLogger().trace("begin %s.copyGenome(%s, %s)", getClass().getSimpleName(), idNumber, genome);

		GenomeImpl rVal = createGenome(idNumber, copyData(genome.getData()));

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.copyGenome()", getClass().getSimpleName());
		return rVal;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createGenome(int)
	 */
	@Override
	public GenomeImpl createGenome(int idNumber) {
		getLogger().trace("begin %s.createGenome(%s)", getClass().getSimpleName(), idNumber);

		GenomeImpl rVal = createGenome(idNumber, createData());

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.createGenome()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createGenome(int,
	 * java.lang.Object)
	 */
	@Override
	public GenomeImpl createGenome(int idNumber, String data) {
		getLogger().trace("begin %s.createGenome(%s, %s)", getClass().getSimpleName(), idNumber, data);

		GenomeImpl genome = new GenomeImpl(idNumber, copyData(data));
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);

		getLogger().trace("will return: %s", genome);
		getLogger().trace("end %s.createGenome()", getClass().getSimpleName());
		return genome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsFactory#createPopulation(int)
	 */
	@Override
	public PopulationImpl createPopulation(int idNumber) {
		getLogger().trace("begin %s.createPopulation(%s)", getClass().getSimpleName(), idNumber);

		PopulationImpl population = new PopulationImpl(idNumber);
		for (int i = 0; i < getEvolutionFieldMap().populationSize(); i++) {
			population.add(createGenome(i));
		}

		getLogger().trace("will return: %s", population);
		getLogger().trace("end %s.createPopulation()", getClass().getSimpleName());
		return population;
	}
}
