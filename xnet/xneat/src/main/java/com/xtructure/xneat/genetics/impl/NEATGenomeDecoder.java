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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.AbstractGenomeDecoder;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.link.impl.LinkGeneImpl;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xneat.network.impl.Connection;
import com.xtructure.xneat.network.impl.NeuralNetworkImpl;
import com.xtructure.xneat.network.impl.Neuron;
import com.xtructure.xutil.id.XId;

/**
 * {@link NEATGenomeDecoder} creates phenotype {@link NeuralNetwork} from
 * {@link NEATGenome} with {@link GeneMap} data.
 * 
 * @author Luis Guimbarda
 * 
 */
public class NEATGenomeDecoder extends AbstractGenomeDecoder<GeneMap, NeuralNetwork> {
	/** the singleton instance for this {@link NEATGenomeDecoder} */
	private static final NEATGenomeDecoder	INSTANCE	= new NEATGenomeDecoder();

	/**
	 * Returns the singleton instance {@link NEATGenomeDecoder}.
	 *
	 * @return single instance of NEATGenomeDecoder
	 */
	public static NEATGenomeDecoder getInstance() {
		return INSTANCE;
	}

	/**
	 * Creates a new {@link NEATGenomeDecoder}
	 */
	private NEATGenomeDecoder() {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GenomeDecoder#getPhenotype(com.xtructure
	 * .xevolution.genetics.Genome)
	 */
	@Override
	public NeuralNetwork decode(Genome<GeneMap> genome) {
		getLogger().trace("begin %s.decode(%s)", getClass().getSimpleName(), genome);
		List<NodeGene> nodes = genome.getData().getNodes();
		List<LinkGene> links = genome.getData().getLinks();
		List<NodeGene> biasNodes = new ArrayList<NodeGene>();
		List<NodeGene> inputNodes = new ArrayList<NodeGene>();
		List<NodeGene> outputNodes = new ArrayList<NodeGene>();
		List<NodeGene> hiddenNodes = new ArrayList<NodeGene>();
		// bin nodes by type
		for (NodeGene node : nodes) {
			switch (node.getNodeType()) {
				case BIAS: {
					biasNodes.add(node);
					break;
				}
				case HIDDEN: {
					hiddenNodes.add(node);
					break;
				}
				case INPUT: {
					inputNodes.add(node);
					break;
				}
				case OUTPUT: {
					outputNodes.add(node);
					break;
				}
			}
		}
		// build neurons, remembering nodeId-to-index relations
		Map<XId, Integer> indexMap = new HashMap<XId, Integer>();
		List<Neuron> neurons = new ArrayList<Neuron>();
		buildNeurons(neurons, indexMap, biasNodes);
		buildNeurons(neurons, indexMap, inputNodes);
		buildNeurons(neurons, indexMap, outputNodes);
		buildNeurons(neurons, indexMap, hiddenNodes);
		// build connections
		List<Connection> connections = new ArrayList<Connection>();
		for (LinkGene link : links) {
			connections.add(new Connection(//
					indexMap.get(link.getSourceId()),//
					indexMap.get(link.getTargetId()),//
					((LinkGeneImpl) link).getWeight()));
		}
		NeuralNetworkImpl rVal = new NeuralNetworkImpl(genome.getId(), biasNodes.size(), inputNodes.size(), outputNodes.size(), hiddenNodes.size(), neurons, connections);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.decode()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Adds to the given neurons list {@link Neuron}s created using the
	 * corresponding {@link NodeGene}s. Assumes that all of the given
	 * {@link NodeGene} are have the same {@link NodeType}
	 * 
	 * @param neurons
	 *            the {@link Neuron} list being built
	 * @param indexMap
	 *            map from {@link NodeGene} id to their corresponding
	 *            {@link Neuron}'s index in the neuron list
	 * @param nodes
	 *            the {@link NodeGene}s used to create neurons
	 */
	private void buildNeurons(List<Neuron> neurons, Map<XId, Integer> indexMap, List<NodeGene> nodes) {
		getLogger().trace("begin %s.buildNeurons(%s, %s, %s)", getClass().getSimpleName(), neurons, indexMap, nodes);
		for (NodeGene node : nodes) {
			int id = neurons.size();
			indexMap.put(node.getId(), id);
			Double activation = node.getFieldMap().get(NodeGene.ACTIVATION_ID);
			neurons.add(new Neuron(id, node.getNodeType(), activation));
		}
		getLogger().trace("end %s.buildNeurons()", getClass().getSimpleName());
	}
}
