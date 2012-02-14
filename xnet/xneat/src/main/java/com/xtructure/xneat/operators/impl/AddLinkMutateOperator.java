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
package com.xtructure.xneat.operators.impl;

import java.util.ArrayList;
import java.util.List;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xutil.RandomUtil;

/**
 * {@link AddLinkMutateOperator} is a {@link MutateOperator} that attempts to
 * connect a previously unconnected pair of nodes in the {@link GeneMap} of a
 * {@link Genome}.
 * 
 * @author Luis Guimbarda
 * 
 */
public class AddLinkMutateOperator extends AbstractNEATOperator<GeneMap> implements MutateOperator<GeneMap> {
	/**
	 * Creates a new {@link AddLinkMutateOperator}.
	 * 
	 * @param geneticsFactory
	 *            the {@link NEATGeneticsFactory} used by this
	 *            {@link AddLinkMutateOperator}
	 */
	public AddLinkMutateOperator(NEATGeneticsFactory<GeneMap> geneticsFactory) {
		super(geneticsFactory);
	}

	/**
	 * Creates a child {@link NEATGenome} by adding a new {@link LinkGene} to
	 * the given genome's {@link GeneMap} that connects previously unconnected
	 * {@link NodeGene}s. The nodes to connect are chosen at random, but all
	 * pairs are tried. The given {@link Genome} remains unchanged.
	 * 
	 * @throws OperationFailedException
	 *             If there are no non-input nodes (which is non-standard), or
	 *             if the network is fully connected.
	 * 
	 * @see com.xtructure.xevolution.operator.MutateOperator#mutate(int,
	 *      com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public NEATGenome<GeneMap> mutate(int idNumber, Genome<GeneMap> genome) throws OperationFailedException {
		getLogger().trace("begin %s.mutate(%s, %s)", getClass().getSimpleName(), idNumber, genome);

		List<LinkGene> links = genome.getData().getLinks();
		List<NodeGene> nodes = genome.getData().getNodes();

		List<NodeGene> nonInputNodes = new ArrayList<NodeGene>();
		for (NodeGene node : nodes) {
			if (node.getNodeType().isInputOrBias()) {
				continue;
			}
			nonInputNodes.add(node);
		}
		if (nonInputNodes.size() == 0) {
			getLogger().trace("throwing exception: %s(\"%s\")", OperationFailedException.class.getName(), "no non-input nodes");
			throw new OperationFailedException("no non-input nodes");
		}

		// try to find a pair of nodes with which to create a link
		for (NodeGene source : RandomUtil.shuffle(nodes)) {
			for (NodeGene target : RandomUtil.shuffle(nonInputNodes)) {
				if (genome.getData().getOutgoingNodeIds(source.getId()).contains(target.getId())) {
					// these nodes are linked, try again
					continue;
				}

				// these nodes aren't linked, link'em
				int linkNumber = genome.getData().getLinkIdNumber();
				LinkGene newLink = getGeneticsFactory().createLinkGene(linkNumber++, source.getId(), target.getId());
				links.add(newLink);

				NEATGenome<GeneMap> child = (NEATGenome<GeneMap>) getGeneticsFactory().createGenome(idNumber, new GeneMap(nodes, links));
				child.validate();

				getLogger().trace("will return: %s", child);
				getLogger().trace("end %s.mutate()", getClass().getSimpleName());
				return child;
			}
		}

		// couldn't make a new link
		getLogger().trace("throwing exception: %s(\"%s\")", OperationFailedException.class.getName(), "couldn't find pair of unlinked nodes");
		throw new OperationFailedException("couldn't find pair of unlinked nodes");
	}
}
