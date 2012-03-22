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
package com.xtructure.xevolution.tool.data;

import java.io.File;
import java.util.Arrays;

import org.json.simple.JSONAware;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.tool.PopulationData;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.id.XValId;

/**
 * The Class AbstractPopulationData.
 * 
 * @param <D>
 *            the generic type
 * @author Luis Guimbarda
 */
public abstract class AbstractPopulationData<D extends AbstractPopulationData<D>>
		extends AbstractDataXIdObject<D> implements PopulationData<D> {

	/**
	 * Generates an {@link XId} for a {@link PopulationData} instance
	 * representing the given {@link Population}.
	 * 
	 * @param population
	 *            the population
	 * @return the XId
	 */
	public static XId generateId(Population<?> population) {
		return population.getId().createChild((int) population.getAge());
	}

	/**
	 * Creates a new {@link AbstractPopulationData}.
	 * 
	 * @param population
	 *            the population
	 * @param manager
	 *            the manager
	 */
	protected AbstractPopulationData(Population<?> population,
			XIdObjectManager<D> manager) {
		super(generateId(population), manager, population);
	}

	/**
	 * Creates a new {@link AbstractPopulationData}.
	 * 
	 * @param json
	 *            the json
	 * @param manager
	 *            the manager
	 */
	protected AbstractPopulationData(JSONAware json, XIdObjectManager<D> manager) {
		super(json, manager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.tool.PopulationData#getFittestGenomeId()
	 */
	@Override
	public XId getFittestGenomeId() {
		return XId.TEXT_FORMAT.parse(get(FITTEST_GENOME).toString());
	}

	@Override
	public File getPopulationFile() {
		Object val = get(FILENAME);
		if (val == null) {
			return null;
		}
		return new File(val.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.tool.AbstractDataXIdObject#processObserved(com
	 * .xtructure.xutil.id.XIdObjectManager, java.lang.Object[])
	 */
	@Override
	protected void processObserved(XIdObjectManager<D> manager,
			Object... observed) {
		Population<?> population = (Population<?>) observed[0];
		processObserved(manager, population);
	}

	/**
	 * Process the observed data.
	 * 
	 * @param manager
	 *            the manager
	 * @param population
	 *            the population
	 */
	protected void processObserved(XIdObjectManager<D> manager,
			Population<?> population) {
		put(FITTEST_GENOME,
				population.getHighestGenomeByAttribute(
						Genome.FITNESS_ATTRIBUTE_ID).toString());
		for (XValId<?> valueId : population.getGenomeAttributeIds()) {
			// add graph-able data
			put(valueId, Arrays.asList(//
					((Number) population.getHighestGenomeByAttribute(valueId)
							.getAttribute(valueId)).doubleValue(),//
					population.getAverageGenomeAttribute(valueId),//
					((Number) population.getLowestGenomeByAttribute(valueId)
							.getAttribute(valueId)).doubleValue()));
		}
	}

	/**
	 * A factory for creating AbstractPopulationData objects.
	 * 
	 * @param <D>
	 *            the generic type
	 */
	public static abstract class AbstractPopulationDataFactory<D extends PopulationData<D>>
			implements PopulationDataFactory<D> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xevolution.tool.PopulationData.PopulationDataFactory
		 * #getOrCreateInstance(com.xtructure.xevolution.genetics.Population,
		 * com.xtructure.xutil.id.XIdObjectManager)
		 */
		@Override
		public D getOrCreateInstance(Population<?> population,
				XIdObjectManager<D> manager) {
			D data = manager.getObject(generateId(population));
			if (data == null) {
				return createInstance(population, manager);
			}
			return data;
		}
	}
}
