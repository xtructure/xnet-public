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

import java.util.List;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.RandomUtil;

/**
 * {@link AddNodeMutateOperator} is a {@link MutateOperator} that attempts to
 * "split" a link in the {@link GeneMap} of a {@link NEATGenome} with a new
 * node.
 * <P>
 * Let node C be the new node to add, and link A -> B be the link to split. The
 * link is actually replace with two links, A -> C and C -> B.
 * 
 * @author Luis Guimbarda
 * 
 */
public class AddNodeMutateOperator extends AbstractNEATOperator<GeneMap> implements MutateOperator<GeneMap> {
	/**
	 * Creates a new {@link AddNodeMutateOperator}
	 * 
	 * @param geneticsFactory
	 *            the {@link NEATGeneticsFactory} used by this
	 *            {@link AddNodeMutateOperator}
	 */
	public AddNodeMutateOperator(NEATGeneticsFactory<GeneMap> geneticsFactory) {
		super(geneticsFactory);
	}

	/**
	 * Creates a child {@link NEATGeneticsFactory} by "splitting" a link in the
	 * {@link GeneMap} of the given {@link Genome}. The link to split is
	 * randomly chosen. The split link is just replace with two links, with the
	 * new node in the middle. So if C is the new node and A -> B is the link,
	 * the link is replaced with A -> C -> B.
	 * 
	 * @throws OperationFailedException
	 *             If there are no links to split in the {@link Genome}'s
	 *             {@link GeneMap}.
	 * @see com.xtructure.xevolution.operator.MutateOperator#mutate(int,
	 *      com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public NEATGenome<GeneMap> mutate(int idNumber, Genome<GeneMap> genome) throws OperationFailedException {
		getLogger().trace("begin %s.mutate(%s, %s)", getClass().getSimpleName(), idNumber, genome);

		List<LinkGene> links = genome.getData().getLinks();
		if (links.size() == 0) {
			getLogger().trace("throwing exception: %s(\"%s\")", OperationFailedException.class.getName(), "no links to split with new node");
			throw new OperationFailedException("no links to split with new node");
		}
		List<NodeGene> nodes = genome.getData().getNodes();

		// select a link which to bisect with the new node
		LinkGene link = RandomUtil.select(links);
		NodeGene source = (NodeGene) genome.getData().get(link.getSourceId());
		NodeGene target = (NodeGene) genome.getData().get(link.getTargetId());

		// create a node to be placed between source and target
		int nodeIdNumber = genome.getData().getNodeIdNumber();
		NodeGene newNode = getGeneticsFactory().createNodeGene(nodeIdNumber++, NodeType.HIDDEN);
		// create a link from source to newNode and from newNode to target
		int linkIdNumber = genome.getData().getLinkIdNumber();
		LinkGene preLink = getGeneticsFactory().createLinkGene(linkIdNumber++, source.getId(), newNode.getId());
		LinkGene postLink = getGeneticsFactory().createLinkGene(linkIdNumber++, newNode.getId(), target.getId());

		// replace the link gene with the new genes
		links.remove(link);
		nodes.add(newNode);
		links.add(preLink);
		links.add(postLink);

		NEATGenome<GeneMap> child = (NEATGenome<GeneMap>) getGeneticsFactory().createGenome(idNumber, new GeneMap(nodes, links));
		child.validate();

		getLogger().trace("will return: %s", child);
		getLogger().trace("end %s.mutate()", getClass().getSimpleName());
		return child;
	}
}
