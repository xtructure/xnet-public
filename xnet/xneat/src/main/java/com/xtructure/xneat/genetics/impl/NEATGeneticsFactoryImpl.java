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
import java.util.List;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.link.impl.LinkGeneImpl;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.genetics.node.impl.NodeGeneImpl;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.id.XId;

/**
 * {@link NEATGeneticsFactoryImpl} is an implementation of the
 * {@link NEATGeneticsFactory} interface. It's a factory for {@link NEATGenome}s
 * with data payloads of type {@link GeneMap}, consisting of
 * {@link LinkGeneImpl} and {@link NodeGeneImpl} genes.
 * 
 * @author Luis Guimbarda
 * 
 */
public class NEATGeneticsFactoryImpl extends AbstractNEATGeneticsFactory<GeneMap> {
	/**
	 * Creates a new {@link NEATGeneticsFactoryImpl}, using
	 * {@link LinkGeneConfiguration#DEFAULT_CONFIGURATION} and
	 * {@link NodeGeneConfiguration#DEFAULT_CONFIGURATION} gene configurations
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by the new
	 *            {@link NEATGeneticsFactoryImpl}
	 */
	public NEATGeneticsFactoryImpl(NEATEvolutionFieldMap evolutionFieldMap) {
		this(//
				evolutionFieldMap,//
				LinkGeneConfiguration.DEFAULT_CONFIGURATION,//
				NodeGeneConfiguration.DEFAULT_CONFIGURATION);
	}

	/**
	 * Creates a new {@link NEATGeneticsFactoryImpl}, using the given gene
	 * configurations
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by the new
	 *            {@link NEATGeneticsFactoryImpl}
	 * @param linkConfiguration
	 *            the {@link LinkGeneConfiguration} for {@link LinkGene}s
	 *            produced by this {@link NEATGeneticsFactoryImpl}
	 * @param nodeConfiguration
	 *            the {@link NodeGeneConfiguration} for {@link NodeGene}s
	 *            produced by this {@link NEATGeneticsFactoryImpl}
	 */
	public NEATGeneticsFactoryImpl(//
			NEATEvolutionFieldMap evolutionFieldMap,//
			LinkGeneConfiguration linkConfiguration,//
			NodeGeneConfiguration nodeConfiguration) {
		super(evolutionFieldMap,//
				linkConfiguration,//
				nodeConfiguration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xtructure.xneat.genetics.impl.AbstractNEATGeneticsFactory#
	 * getLinkConfiguration()
	 */
	@Override
	public LinkGeneConfiguration getLinkConfiguration() {
		return (LinkGeneConfiguration) super.getLinkConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xtructure.xneat.genetics.impl.AbstractNEATGeneticsFactory#
	 * getNodeConfiguration()
	 */
	@Override
	public NodeGeneConfiguration getNodeConfiguration() {
		return (NodeGeneConfiguration) super.getNodeConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.neat.genetics.NEATGeneticsFactory#copyLinkGene
	 * (int, com.xtructure.projects.xevo.neat.genetics.LinkGene)
	 */
	@Override
	public LinkGeneImpl copyLinkGene(int idNumber, LinkGene link) {
		getLogger().trace("begin %s.copyLinkGene(%d, %s)", getClass().getSimpleName(), idNumber, link);

		LinkGeneImpl rVal = new LinkGeneImpl(idNumber, link);

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.copyLinkGene()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.neat.genetics.NEATGeneticsFactory#copyNodeGene
	 * (int, com.xtructure.projects.xevo.neat.genetics.NodeGene)
	 */
	@Override
	public NodeGeneImpl copyNodeGene(int idNumber, NodeGene nodeGene) {
		getLogger().trace("begin %s.copyNodeGene(%d, %s)", getClass().getSimpleName(), idNumber, nodeGene);

		NodeGeneImpl rVal = new NodeGeneImpl(idNumber, nodeGene);

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.copyNodeGene()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.neat.genetics.NEATGeneticsFactory#createLinkGene
	 * (int, com.xtructure.xutil.id.XId, com.xtructure.xutil.id.XId)
	 */
	@Override
	public LinkGeneImpl createLinkGene(int idNumber, XId sourceId, XId targetId) {
		getLogger().trace("begin %s.createLinkGene(%d, %s, %s)", getClass().getSimpleName(), idNumber, sourceId, targetId);

		LinkGeneImpl rVal = new LinkGeneImpl(idNumber, sourceId, targetId, getLinkConfiguration());

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.createLinkGene()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.neat.genetics.NEATGeneticsFactory#createNodeGene
	 * (int, com.xtructure.projects.xevo.neat.genetics.NodeGene.NodeType)
	 */
	@Override
	public NodeGeneImpl createNodeGene(int idNumber, NodeType nodeType) {
		getLogger().trace("begin %s.createNodeGene(%d, %s)", getClass().getSimpleName(), idNumber, nodeType);

		NodeGeneImpl rVal = new NodeGeneImpl(idNumber, nodeType, getNodeConfiguration());

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.createNodeGene()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createGenome(int)
	 */
	@Override
	public NEATGenomeImpl createGenome(int idNumber) {
		getLogger().trace("begin %s.createGenome(%d)", getClass().getSimpleName(), idNumber);

		NEATGenomeImpl genome = new NEATGenomeImpl(idNumber, createData());
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);
		genome.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, (double) genome.getData().size());

		getLogger().trace("will return: %s", genome);
		getLogger().trace("end %s.createGenome()", getClass().getSimpleName());
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
		getLogger().trace("begin %s.createGenome(%d, %s)", getClass().getSimpleName(), idNumber, data);

		NEATGenomeImpl genome = new NEATGenomeImpl(idNumber, data);
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);
		genome.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, (double) genome.getData().size());

		getLogger().trace("will return: %s", genome);
		getLogger().trace("end %s.createGenome()", getClass().getSimpleName());
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
		getLogger().trace("begin %s.copyGenome(%d, %s)", getClass().getSimpleName(), idNumber, genome);

		NEATGenomeImpl rgenome = new NEATGenomeImpl(idNumber, genome);
		rgenome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);
		rgenome.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, (double) genome.getData().size());

		getLogger().trace("will return: %s", rgenome);
		getLogger().trace("end %s.copyGenome()", getClass().getSimpleName());
		return rgenome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsFactory#createData()
	 */
	@Override
	public GeneMap createData() {
		getLogger().trace("begin %s.createData()", getClass().getSimpleName());

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
				if (RandomUtil.eventOccurs(getEvolutionFieldMap().initialConnectionProbability())) {
					links.add(createLinkGene(linkIdNumber++, input.getId(), output.getId()));
				}
			}
		}
		GeneMap rVal = new GeneMap(nodes, links);

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.createData()", getClass().getSimpleName());
		return rVal;
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
		getLogger().trace("begin %s.copyData()", getClass().getSimpleName());

		GeneMap rVal = new GeneMap(data);

		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.copyData()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GeneticsFactory#createPopulation(int)
	 */
	@Override
	public NEATPopulationImpl createPopulation(int idNumber) {
		getLogger().trace("begin %s.createPopulation()", getClass().getSimpleName());

		NEATPopulationImpl population = new NEATPopulationImpl(idNumber);
		for (int i = 0; i < getEvolutionFieldMap().populationSize(); i++) {
			population.add(createGenome(i));
		}

		getLogger().trace("will return: %s", population);
		getLogger().trace("end %s.createPopulation()", getClass().getSimpleName());
		return population;
	}
}
