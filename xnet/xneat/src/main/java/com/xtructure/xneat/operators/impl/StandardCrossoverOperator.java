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
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.RandomUtil;

/**
 * {@link StandardCrossoverOperator} implements the crossover operation in the
 * NEAT algorithm that exploits {@link Innovation}s to find genetic components
 * to swap.
 * 
 * @author Luis Guimbarda
 * 
 */
public class StandardCrossoverOperator extends AbstractNEATOperator<GeneMap> implements CrossoverOperator<GeneMap> {
	/**
	 * Creates a new {@link StandardCrossoverOperator}
	 * 
	 * @param geneticsFactory
	 *            the {@link NEATGeneticsFactory} used by this
	 *            {@link StandardCrossoverOperator}
	 */
	public StandardCrossoverOperator(NEATGeneticsFactory<GeneMap> geneticsFactory) {
		super(geneticsFactory);
	}

	/**
	 * Creates a child {@link NEATGenome} using the NEAT algorithm's crossover
	 * operation. Essential, the child genome is equal to the more fit of
	 * genome1 or genome2, with the genes from the fitter parent replaced by
	 * those in the less fit parent with 0.5 probability;
	 * 
	 * @see com.xtructure.xevolution.operator.CrossoverOperator#crossover(int,
	 *      com.xtructure.xevolution.genetics.Genome,
	 *      com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public NEATGenome<GeneMap> crossover(int idNumber, Genome<GeneMap> genome1, Genome<GeneMap> genome2) throws OperationFailedException {
		getLogger().trace("begin %s.crossover(%s, %s, %s)", getClass().getSimpleName(), idNumber, genome1, genome2);

		Genome<GeneMap> g1 = genome1, g2 = genome2;
		if (Genome.BY_FITNESS_DESC.compare(g1, g2) > 0) {
			// g1 is less fit than g2, swap
			g1 = genome2;
			g2 = genome1;
		}

		GeneMap geneMap = new GeneMap();

		List<LinkGene> links1 = g1.getData().getLinks();
		List<LinkGene> links2 = g2.getData().getLinks();

		int linkIndex1 = 0, linkIndex2 = 0;
		int linkNumber = 0;

		while (linkIndex1 < links1.size() || linkIndex2 < links2.size()) {
			if (linkIndex1 >= links1.size()) {
				// reached the end of links1, what remains are the weaker
				// excess genes, so break
				break;
			}

			if (linkIndex2 >= links2.size()) {
				// reached the end of links2, what remains are the stronger
				// excess genes, so add them and break
				for (LinkGene link : links1.subList(linkIndex1, links1.size())) {
					addLinkAndConnectedNodes(link, linkNumber++, geneMap, g1);
				}
				break;
			}

			// compare the innovation numbers of the current links
			LinkGene thisLinkGene = links1.get(linkIndex1);
			LinkGene thatLinkGene = links2.get(linkIndex2);
			Innovation thisInnovation = thisLinkGene.getInnovation();
			Innovation thatInnovation = thatLinkGene.getInnovation();
			if (thisInnovation.compareTo(thatInnovation) > 0) {
				// thatLinkGene is a weak offset gene, skip it
				linkIndex2++;
			} else if (thisInnovation.compareTo(thatInnovation) < 0) {
				// thisLinkGene is a strong offset gene, add it
				addLinkAndConnectedNodes(thisLinkGene, linkNumber++, geneMap, g1);
				linkIndex1++;
			} else {
				// both genes' innovation match, add one and skip the other,
				// chosen randomly
				if (RandomUtil.nextBoolean()) {
					addLinkAndConnectedNodes(thisLinkGene, linkNumber++, geneMap, g1);
				} else {
					addLinkAndConnectedNodes(thatLinkGene, linkNumber++, geneMap, g2);
				}
				linkIndex1++;
				linkIndex2++;
			}
		}

		// even if they're disconnected, we still want the bias/input/output
		// nodes.
		for (NodeGene node : g1.getData().getNodes()) {
			if (!NodeType.HIDDEN.isNodeTypeOf(node)) {
				if (geneMap.get(node.getId()) == null) {
					geneMap.add(node);
				}
			}
		}

		NEATGenome<GeneMap> newGenome = (NEATGenome<GeneMap>) getGeneticsFactory().createGenome(idNumber, geneMap);
		newGenome.validate();

		getLogger().trace("will return: %s", newGenome);
		getLogger().trace("end %s.crossover()", getClass().getSimpleName());
		return newGenome;
	}

	private void addLinkAndConnectedNodes(LinkGene link, int linkNumber, GeneMap geneMap, Genome<GeneMap> genome) {
		getLogger().trace("begin %s.addLinkAndConnectedNodes(%s, %s, %s, %s)", getClass().getSimpleName(), link, linkNumber, geneMap, genome);

		LinkGene linkGene = getGeneticsFactory().copyLinkGene(linkNumber, link);
		NodeGene source = (NodeGene) genome.getData().get(link.getSourceId());
		NodeGene target = (NodeGene) genome.getData().get(link.getTargetId());
		if (!geneMap.contains(source) || RandomUtil.nextBoolean()) {
			geneMap.add(source);
		}
		if (!geneMap.contains(target) || RandomUtil.nextBoolean()) {
			geneMap.add(target);
		}
		geneMap.add(linkGene);

		getLogger().trace("end %s.addLinkAndConnectedNodes()", getClass().getSimpleName());
	}
}
