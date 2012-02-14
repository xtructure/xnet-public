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

import org.json.simple.JSONAware;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.id.XIdObjectManager;

public final class GenealogyDataImpl extends AbstractGenealogyData<GenealogyDataImpl> {
	public static final GenealogyDataFactoryImpl	FACTORY	= new GenealogyDataFactoryImpl();

	private GenealogyDataImpl(JSONAware json, XIdObjectManager<GenealogyDataImpl> manager) {
		super(json, manager);
	}

	private GenealogyDataImpl(Genome<?> genome, File populationFile, XIdObjectManager<GenealogyDataImpl> manager) {
		super(genome, populationFile, manager);
	}

	public static final class GenealogyDataFactoryImpl extends AbstractGenealogyDataFactory<GenealogyDataImpl> {
		@Override
		public GenealogyDataImpl createInstance(JSONAware json, XIdObjectManager<GenealogyDataImpl> manager) {
			return new GenealogyDataImpl(json, manager);
		}

		@Override
		public GenealogyDataImpl createInstance(Genome<?> genome, File populationFile, XIdObjectManager<GenealogyDataImpl> manager) {
			return new GenealogyDataImpl(genome, populationFile, manager);
		}
	}
}
