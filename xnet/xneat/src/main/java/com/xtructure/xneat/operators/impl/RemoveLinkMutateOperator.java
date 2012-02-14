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
 * {@link RemoveLinkMutateOperator} is a {@link MutateOperator} that removes a
 * single link from a {@link Genome}'s {@link GeneMap}. If any hidden nodes are
 * left disconnect in the {@link GeneMap} as a result of the removal, they are
 * removed as well.
 * 
 * @author Luis Guimbarda
 * 
 */
public class RemoveLinkMutateOperator extends AbstractNEATOperator<GeneMap> implements MutateOperator<GeneMap> {
	/**
	 * Creates a new {@link RemoveLinkMutateOperator}.
	 * 
	 * @param geneticsFactory
	 *            the {@link NEATGeneticsFactory} used by this
	 *            {@link RemoveLinkMutateOperator}
	 */
	public RemoveLinkMutateOperator(NEATGeneticsFactory<GeneMap> geneticsFactory) {
		super(geneticsFactory);
	}

	/**
	 * Creates a child {@link NEATGenome} by removing a link from the given
	 * {@link Genome}'s {@link GeneMap}. The link to remove is chosen at random.
	 * If removing the link causes any hidden nodes to be disconnected, those
	 * nodes are removed as well.
	 * 
	 * @throws OperationFailedException
	 *             If there are no links in the given {@link Genome}'s
	 *             {@link GeneMap}
	 * @see com.xtructure.xevolution.operator.MutateOperator#mutate(int,
	 *      com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public NEATGenome<GeneMap> mutate(int idNumber, Genome<GeneMap> genome) throws OperationFailedException {
		getLogger().trace("begin %s.mutate(%s, %s)", getClass().getSimpleName(), idNumber, genome);

		GeneMap geneMap = new GeneMap(genome.getData());
		if (geneMap.getLinkCount() == 0) {
			getLogger().trace("throwing exception: %s(\"%s\")", OperationFailedException.class.getName(), "no link to remove");
			throw new OperationFailedException("no link to remove");
		}

		// select link and remove it
		LinkGene link = RandomUtil.select(geneMap.getLinks());
		geneMap.remove(link);

		// remove floating nodes left by the removed link
		NodeGene source = (NodeGene) geneMap.get(link.getSourceId());
		NodeGene target = (NodeGene) geneMap.get(link.getTargetId());
		if (NodeType.HIDDEN.isNodeTypeOf(source)) {
			boolean noIncomingLinks = geneMap.getIncomingLinkIds(source.getId()).isEmpty();
			boolean noOutgoingLinks = geneMap.getOutgoingLinkIds(source.getId()).isEmpty();
			if (noIncomingLinks && noOutgoingLinks) {
				geneMap.remove(source);
			}
		}
		if (source != target && NodeType.HIDDEN.isNodeTypeOf(target)) {
			boolean noIncomingLinks = geneMap.getIncomingLinkIds(target.getId()).isEmpty();
			boolean noOutgoingLinks = geneMap.getOutgoingLinkIds(target.getId()).isEmpty();
			if (noIncomingLinks && noOutgoingLinks) {
				geneMap.remove(target);
			}
		}

		NEATGenome<GeneMap> child = (NEATGenome<GeneMap>) getGeneticsFactory().createGenome(idNumber, geneMap);
		child.validate();

		getLogger().trace("will return: %s", child);
		getLogger().trace("end %s.mutate()", getClass().getSimpleName());
		return child;
	}
}
