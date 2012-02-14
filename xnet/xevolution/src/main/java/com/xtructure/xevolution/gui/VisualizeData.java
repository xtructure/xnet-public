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
package com.xtructure.xevolution.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.tool.data.DataTracker;

/**
 * The Interface VisualizeData.
 *
 * @author Luis Guimbarda
 */
public interface VisualizeData {
	
	/**
	 * Creates the sim visualization.
	 *
	 * @param genome the genome
	 * @return the JFrame
	 */
	public JFrame createSimVisualization(Genome<?> genome);

	/**
	 * Creates the genome visualization.
	 *
	 * @param genomes the genomes
	 * @return the JPanel
	 */
	public JPanel createGenomeVisualization(Genome<?>... genomes);

	/**
	 * Creates the sim visualization.
	 *
	 * @param dataTracker the data tracker
	 * @param genome the genome
	 * @return the JFrame
	 */
	public JFrame createSimVisualization(DataTracker<?, ?> dataTracker, Genome<?> genome);

	/**
	 * Creates the genome visualization.
	 *
	 * @param dataTracker the data tracker
	 * @param genomes the genomes
	 * @return the JPanel
	 */
	public JPanel createGenomeVisualization(DataTracker<?, ?> dataTracker, Genome<?>... genomes);
}
