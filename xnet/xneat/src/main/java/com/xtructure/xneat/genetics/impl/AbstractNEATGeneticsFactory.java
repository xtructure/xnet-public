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
package com.xtructure.xneat.genetics.impl;

import com.xtructure.xevolution.genetics.impl.AbstractGeneticsFactory;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xutil.config.XConfiguration;

/**
 * {@link AbstractNEATGeneticsFactory} implements getters for the
 * {@link NEATGeneticsFactory} interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractNEATGeneticsFactory<D> extends AbstractGeneticsFactory<D> implements NEATGeneticsFactory<D> {
	/**
	 * {@link XConfiguration} for {@link LinkGene}s in this
	 * {@link NEATGeneticsFactory}
	 */
	private final XConfiguration	linkConfiguration;
	/**
	 * {@link XConfiguration} for {@link NodeGene}s in this
	 * {@link NEATGeneticsFactory}
	 */
	private final XConfiguration	nodeConfiguration;

	/**
	 * Creates a new {@link AbstractNEATGeneticsFactory}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} for this
	 *            {@link AbstractNEATGeneticsFactory}
	 * @param linkConfiguration
	 *            the {@link XConfiguration} for {@link LinkGene}s for this
	 *            {@link AbstractNEATGeneticsFactory}
	 * @param nodeConfiguration
	 *            the {@link XConfiguration} for {@link NodeGene}s for this
	 *            {@link AbstractNEATGeneticsFactory}
	 */
	public AbstractNEATGeneticsFactory(//
			NEATEvolutionFieldMap evolutionFieldMap,//
			XConfiguration linkConfiguration,//
			XConfiguration nodeConfiguration) {
		super(evolutionFieldMap);
		this.linkConfiguration = linkConfiguration;
		this.nodeConfiguration = nodeConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xtructure.xevolution.genetics.impl.AbstractGeneticsFactory#
	 * getEvolutionFieldMap()
	 */
	@Override
	public NEATEvolutionFieldMap getEvolutionFieldMap() {
		return (NEATEvolutionFieldMap) super.getEvolutionFieldMap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xneat.genetics.NEATGeneticsFactory#getLinkConfiguration()
	 */
	@Override
	public XConfiguration getLinkConfiguration() {
		getLogger().trace("begin %s.getLinkConfiguration()", getClass().getSimpleName());

		getLogger().trace("will return: %s", linkConfiguration);
		getLogger().trace("end %s.getLinkConfiguration()", getClass().getSimpleName());
		return linkConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xneat.genetics.NEATGeneticsFactory#getNodeConfiguration()
	 */
	@Override
	public XConfiguration getNodeConfiguration() {
		getLogger().trace("begin %s.getNodeConfiguration()", getClass().getSimpleName());

		getLogger().trace("will return: %s", nodeConfiguration);
		getLogger().trace("end %s.getNodeConfiguration()", getClass().getSimpleName());
		return nodeConfiguration;
	}
}
