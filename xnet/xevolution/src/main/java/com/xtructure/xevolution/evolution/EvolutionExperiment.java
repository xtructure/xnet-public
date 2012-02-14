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
package com.xtructure.xevolution.evolution;

import java.io.File;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.opt.XOption;

/**
 * @author Luis Guimbarda
 * 
 * @param <D>
 * @param <T>
 */
public interface EvolutionExperiment<D, T> {
	public XOption<?> getOption(String name);

	public void setArgs(String[] args);

	public void startExperiment();

	public EvolutionFieldMap getEvolutionFieldMap();

	public File getOutputDir();

	public GenomeDecoder<D, T> getGenomeDecoder();

	public GeneticsFactory<D> getGeneticsFactory();

	public ReproductionStrategy<D> getReproductionStrategy();

	public EvaluationStrategy<D, T> getEvaluationStrategy();

	public SurvivalFilter getSurvivalFilter();

	public EvolutionStrategy<D, T> getEvolutionStrategy();

	public Population<D> getPopulation();
}
