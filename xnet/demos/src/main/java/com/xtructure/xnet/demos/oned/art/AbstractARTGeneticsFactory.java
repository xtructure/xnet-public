/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.xtructure.xnet.demos.oned.art;

import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.impl.AbstractNEATGeneticsFactory;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.id.XId;

/**
 * A factory for creating AbstractARTGenetics objects.
 * 
 * @param <D>
 *            the generic type
 * @author Luis Guimbarda
 */
public abstract class AbstractARTGeneticsFactory<D> extends
		AbstractNEATGeneticsFactory<D> implements ARTGeneticsFactory<D> {

	/**
	 * Instantiates a new abstract art genetics factory.
	 * 
	 * @param evolutionFieldMap
	 *            the evolution field map
	 * @param linkConfiguration
	 *            the link configuration
	 * @param nodeConfiguration
	 *            the node configuration
	 */
	public AbstractARTGeneticsFactory(NEATEvolutionFieldMap evolutionFieldMap,
			LinkConfiguration linkConfiguration,
			NodeConfiguration nodeConfiguration) {
		super(evolutionFieldMap, linkConfiguration, nodeConfiguration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.impl.AbstractNEATGeneticsFactory#
	 * getLinkConfiguration()
	 */
	@Override
	public LinkConfiguration getLinkConfiguration() {
		return (LinkConfiguration) super.getLinkConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.impl.AbstractNEATGeneticsFactory#
	 * getNodeConfiguration()
	 */
	@Override
	public NodeConfiguration getNodeConfiguration() {
		return (NodeConfiguration) super.getNodeConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.art.genetics.ARTGeneticsFactory#copyLinkGene
	 * (int, com.xtructure.xneat.genetics.LinkGene)
	 */
	@Override
	public ARTLinkGene copyLinkGene(int idNumber, LinkGene link) {
		return new ARTLinkGeneImpl(idNumber, link);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.art.genetics.ARTGeneticsFactory#copyNodeGene
	 * (int, com.xtructure.xneat.genetics.NodeGene)
	 */
	@Override
	public ARTNodeGene copyNodeGene(int idNumber, NodeGene nodeGene) {
		return new ARTNodeGeneImpl(idNumber, nodeGene);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.art.genetics.ARTGeneticsFactory#createLinkGene
	 * (int, com.xtructure.xutil.id.XId, com.xtructure.xutil.id.XId)
	 */
	@Override
	public ARTLinkGene createLinkGene(int idNumber, XId sourceId, XId targetId) {
		return new ARTLinkGeneImpl(idNumber, sourceId, targetId,
				getLinkConfiguration());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.art.genetics.ARTGeneticsFactory#createNodeGene
	 * (int, com.xtructure.xneat.genetics.NodeType)
	 */
	@Override
	public ARTNodeGene createNodeGene(int idNumber, NodeType nodeType) {
		return new ARTNodeGeneImpl(idNumber, nodeType, getNodeConfiguration());
	}

}
