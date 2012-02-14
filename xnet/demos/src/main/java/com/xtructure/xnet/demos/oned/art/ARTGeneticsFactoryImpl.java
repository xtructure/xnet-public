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

import java.util.ArrayList;
import java.util.List;

import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.impl.NEATGenomeImpl;
import com.xtructure.xneat.genetics.impl.NEATPopulationImpl;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.RandomUtil;

/**
 * The Class ARTGeneticsFactoryImpl.
 * 
 * @author Luis Guimbarda
 */
public class ARTGeneticsFactoryImpl extends AbstractARTGeneticsFactory<GeneMap> {

	/**
	 * Instantiates a new aRT genetics factory impl.
	 * 
	 * @param evolutionFieldMap
	 *            the evolution field map
	 * @param linkConfiguration
	 *            the link configuration
	 * @param nodeConfiguration
	 *            the node configuration
	 */
	public ARTGeneticsFactoryImpl(NEATEvolutionFieldMap evolutionFieldMap,
			LinkConfiguration linkConfiguration,
			NodeConfiguration nodeConfiguration) {
		super(evolutionFieldMap, linkConfiguration, nodeConfiguration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createGenome(int)
	 */
	@Override
	public NEATGenomeImpl createGenome(int idNumber) {
		NEATGenomeImpl genome = new NEATGenomeImpl(idNumber, createData());
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);
		genome.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, (double) genome
				.getData().size());
		return genome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createGenome(int,
	 * java.lang.Object)
	 */
	@Override
	public NEATGenomeImpl createGenome(int idNumber, GeneMap data) {
		NEATGenomeImpl genome = new NEATGenomeImpl(idNumber, data);
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);
		genome.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, (double) genome
				.getData().size());
		return genome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#copyGenome(int,
	 * com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public NEATGenomeImpl copyGenome(int idNumber, Genome<GeneMap> genome) {
		NEATGenomeImpl rgenome = new NEATGenomeImpl(idNumber, genome);
		rgenome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);
		rgenome.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, (double) genome
				.getData().size());
		return rgenome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createData()
	 */
	@Override
	public GeneMap createData() {
		List<NodeGene> nodes = new ArrayList<NodeGene>();
		List<LinkGene> links = new ArrayList<LinkGene>();

		// instantiate bias, input, and output genes. bias nodes technically
		// aren't input nodes, but are handled the same way during this
		// construction.
		List<NodeGene> inputs = new ArrayList<NodeGene>();
		List<NodeGene> outputs = new ArrayList<NodeGene>();
		int nodeIdNumber = 0;
		for (int i = 0; i < getEvolutionFieldMap().biasNodeCount(); i++) {
			NodeGene nodeGene = createNodeGene(nodeIdNumber++, NodeType.BIAS);
			nodes.add(nodeGene);
			inputs.add(nodeGene);
		}
		for (int i = 0; i < getEvolutionFieldMap().inputNodeCount(); i++) {
			NodeGene nodeGene = createNodeGene(nodeIdNumber++, NodeType.INPUT);
			nodes.add(nodeGene);
			inputs.add(nodeGene);
		}
		for (int i = 0; i < getEvolutionFieldMap().outputNodeCount(); i++) {
			NodeGene nodeGene = createNodeGene(nodeIdNumber++, NodeType.OUTPUT);
			nodes.add(nodeGene);
			outputs.add(nodeGene);
		}

		// instantiate links connecting each bias and input node to each output
		// node
		int linkIdNumber = 0;
		for (NodeGene input : inputs) {
			for (NodeGene output : outputs) {
				if (RandomUtil.eventOccurs(getEvolutionFieldMap()
						.initialConnectionProbability())) {
					links.add(createLinkGene(linkIdNumber++, input.getId(),
							output.getId()));
				}
			}
		}
		return new GeneMap(nodes, links);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsFactory#copyData(java.lang.
	 * Object)
	 */
	@Override
	public GeneMap copyData(GeneMap data) {
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsFactory#createPopulation(int)
	 */
	@Override
	public NEATPopulationImpl createPopulation(int idNumber) {
		NEATPopulationImpl pop = new NEATPopulationImpl(idNumber);
		for (int i = 0; i < getEvolutionFieldMap().populationSize(); i++) {
			pop.add(createGenome(i));
		}
		return pop;
	}
}
