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
package com.xtructure.xneat.genetics;

import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.id.XId;

/**
 * The {@link NEATGeneticsFactory} interface describes methods for creating and
 * copying {@link NEATGenome}s, their payload data, and {@link NEATPopulation}s.
 * {@link NEATPopulation}s are created with a number of new {@link NEATGenome}s
 * specified by {@link NEATEvolutionFieldMap#populationSize()}.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface NEATGeneticsFactory<D> extends GeneticsFactory<D> {
	/**
	 * Creates a new {@link LinkGene}.
	 * 
	 * @param idNumber
	 *            the instance number of the id for the new link gene
	 * @param sourceId
	 *            the {@link XId} of the source node for the new link gene
	 * @param targetId
	 *            the {@link XId} of the target node for the new link gene
	 * @return the new {@link LinkGene}
	 */
	public LinkGene createLinkGene(int idNumber, XId sourceId, XId targetId);

	/**
	 * Creates a new {@link LinkGene} that's a copy of the given link gene
	 * 
	 * @param idNumber
	 *            the instance number of the id for the new link gene
	 * @param linkGene
	 *            the {@link LinkGene} to copy
	 * @return the new {@link LinkGene}
	 */
	public LinkGene copyLinkGene(int idNumber, LinkGene linkGene);

	/**
	 * Creates a new {@link NodeGene}
	 * 
	 * @param idNumber
	 *            the instance number of the id for the new node gene
	 * @param nodeType
	 *            the {@link NodeType} of the new node gene
	 * @return the new {@link NodeGene}
	 */
	public NodeGene createNodeGene(int idNumber, NodeType nodeType);

	/**
	 * Creates a new {@link NodeGene} that's a copy of the given node gene
	 * 
	 * @param idNumber
	 *            the instance number of the id for the new node gene
	 * @param nodeGene
	 *            the {@link NodeGene} to copy
	 * @return the new {@link NodeGene}
	 */
	public NodeGene copyNodeGene(int idNumber, NodeGene nodeGene);

	/**
	 * Returns the {@link XConfiguration} used for {@link LinkGene}s.
	 * 
	 * @return the {@link XConfiguration} used for {@link LinkGene}s.
	 */
	XConfiguration getLinkConfiguration();

	/**
	 * Returns the {@link XConfiguration} used for {@link NodeGene}s.
	 * 
	 * @return the {@link XConfiguration} used for {@link NodeGene}s.
	 */
	XConfiguration getNodeConfiguration();

	/**
	 * Creates a new {@link NEATPopulation} of fresh {@link NEATGenome}s.
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createPopulation(int)
	 */
	@Override
	public Population<D> createPopulation(int idNumber);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsFactory#getEvolutionFieldMap()
	 */
	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap();
}
