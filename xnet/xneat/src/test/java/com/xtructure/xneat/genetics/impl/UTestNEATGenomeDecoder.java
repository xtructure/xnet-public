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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.link.impl.LinkGeneImpl;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.genetics.node.impl.NodeGeneImpl;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xutil.coll.SetBuilder;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNEATGenomeDecoder {
	private static final LinkGeneConfiguration	LINK_CONFIGURATION;
	private static final NodeGeneConfiguration	NODE_CONFIGURATION;
	static {
		LINK_CONFIGURATION = LinkGeneConfiguration.builder(null).newInstance();
		NODE_CONFIGURATION = NodeGeneConfiguration.builder(null).newInstance();
	}

	public void constructorSucceeds() {
		assertThat("",//
				NEATGenomeDecoder.getInstance(), isNotNull());
	}

	public void decodeReturnsExpectObject() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		NodeGene node2 = new NodeGeneImpl(2, NodeType.BIAS, NODE_CONFIGURATION);
		NodeGene node3 = new NodeGeneImpl(3, NodeType.HIDDEN, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				new SetBuilder<NodeGene>().add(node0, node1, node2, node3).newImmutableInstance(),//
				new SetBuilder<LinkGene>().add(link0).newImmutableInstance());
		NEATGenome<GeneMap> genome = new NEATGenomeImpl(0, geneMap);
		NeuralNetwork network = NEATGenomeDecoder.getInstance().decode(genome);
		assertThat("",//
				network, isNotNull());
	}
}
