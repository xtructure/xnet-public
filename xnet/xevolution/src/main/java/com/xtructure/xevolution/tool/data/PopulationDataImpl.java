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

import org.json.simple.JSONAware;

import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.id.XIdObjectManager;

public final class PopulationDataImpl extends AbstractPopulationData<PopulationDataImpl> {
	public static final PopulationDataFactoryImpl	FACTORY	= new PopulationDataFactoryImpl();

	private PopulationDataImpl(Population<?> population, XIdObjectManager<PopulationDataImpl> manager) {
		super(population, manager);
	}

	private PopulationDataImpl(JSONAware json, XIdObjectManager<PopulationDataImpl> manager) {
		super(json, manager);
	}

	public static final class PopulationDataFactoryImpl extends AbstractPopulationDataFactory<PopulationDataImpl> {
		@Override
		public PopulationDataImpl createInstance(Population<?> population, XIdObjectManager<PopulationDataImpl> manager) {
			return new PopulationDataImpl(population, manager);
		}

		@Override
		public PopulationDataImpl createInstance(JSONAware json, XIdObjectManager<PopulationDataImpl> manager) {
			return new PopulationDataImpl(json, manager);
		}
	}
}
