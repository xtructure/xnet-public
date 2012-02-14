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
package com.xtructure.xevolution.tool.impl;

import java.util.List;

import com.xtructure.xevolution.evolution.EvolutionExperiment;
import com.xtructure.xevolution.tool.XEvoTool;
import com.xtructure.xutil.opt.XOption;

/**
 * ExperimentTool is a simple {@link XEvoTool} that starts an
 * {@link EvolutionExperiment}. Experiment tool doesn't accept command line
 * arguments itself. Rather any command line arguments are passed on to the
 * experiment.
 * 
 * @author Luis Guimbarda
 * 
 */
public class ExperimentTool extends XEvoTool {
	private final EvolutionExperiment<?, ?>	experiment;

	public ExperimentTool(String name, EvolutionExperiment<?, ?> experiment, List<XOption<?>> options) {
		super(name, options);
		this.experiment = experiment;
	}

	@Override
	public void launch(String[] args) {
		experiment.setArgs(args);
		experiment.startExperiment();
	}
}
