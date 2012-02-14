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
package com.xtructure.xnet.demos.oned;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.link.LinkImpl;
import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.art.model.node.NodeImpl;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xnet.demos.oned.components.OneDCritter;
import com.xtructure.xnet.demos.oned.components.OneDCritterImpl;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.id.XId;

/**
 * The Class OneDGenomeDecoderImpl.
 * 
 * @author Luis Guimbarda
 */
public class OneDGenomeDecoderImpl implements OneDGenomeDecoder<GeneMap> {

	/** The Constant ID_MAP. */
	private static final Map<XId, XId> ID_MAP;
	static {
		// maps from gene ids to phenotype ids
		ID_MAP = new HashMap<XId, XId>();
		XId node0Id = XId.newId("NodeGene", 0);
		XId node1Id = XId.newId("NodeGene", 1);
		XId node2Id = XId.newId("NodeGene", 2);
		XId node3Id = XId.newId("NodeGene", 3);
		XId node4Id = XId.newId("NodeGene", 4);
		ID_MAP.put(node0Id, OneDCritter.COUNTER_CLOCKWISE_UTRICLE_ID);
		ID_MAP.put(node1Id, OneDCritter.NOSE_ID);
		ID_MAP.put(node2Id, OneDCritter.CLOCKWISE_UTRICLE_ID);
		ID_MAP.put(node3Id, OneDCritter.COUNTER_CLOCKWISE_FOOT_ID);
		ID_MAP.put(node4Id, OneDCritter.CLOCKWISE_FOOT_ID);
	}

	/** The is loose. */
	private final boolean isLoose;

	/**
	 * Instantiates a new one d genome decoder impl.
	 * 
	 * @param loose
	 *            the loose
	 */
	public OneDGenomeDecoderImpl(boolean loose) {
		this.isLoose = loose;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xnet.demos.oned.OneDGenomeDecoder#isLoose()
	 */
	@Override
	public boolean isLoose() {
		return isLoose;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.GenomeDecoder#decode(com.xtructure.
	 * xevolution.genetics.Genome)
	 */
	@Override
	public OneDCritter decode(Genome<GeneMap> genome) {
		List<NodeGene> nodeGenes = genome.getData().getNodes();
		List<LinkGene> linkGenes = genome.getData().getLinks();
		Set<Node> nodes = new HashSet<Node>();
		Set<Link> links = new HashSet<Link>();
		for (NodeGene nodeGene : nodeGenes) {
			XId id = null;
			if (ID_MAP.containsKey(nodeGene.getId())) {
				id = ID_MAP.get(nodeGene.getId());
			} else {
				id = nodeGene.getId();
			}
			Node node = new NodeImpl(id,
					(NodeConfiguration) nodeGene.getConfiguration());
			if (OneDCritter.CLOCKWISE_FOOT_ID.equals(id)) {
				node.setOscillationMaximum(1f);
				node.setOscillationMinimum(1f);
				node.setOscillationOffset(5);
				node.setOscillationPeriod(10);
			}
			if (OneDCritter.COUNTER_CLOCKWISE_FOOT_ID.equals(id)) {
				node.setOscillationMaximum(1f);
				node.setOscillationMinimum(1f);
				node.setOscillationOffset(0);
				node.setOscillationPeriod(10);
			}
			if (!isLoose()) {
				node.setEnergyDecay((nodeGene.getFieldMap()
						.get(Node.ENERGY_DECAY_ID)));
				node.setExcitatoryScale((nodeGene.getFieldMap()
						.get(Node.EXCITATORY_SCALE_ID)));
				node.setInhibitoryScale((nodeGene.getFieldMap()
						.get(Node.INHIBITORY_SCALE_ID)));
			}
			nodes.add(node);
		}
		for (LinkGene linkGene : linkGenes) {
			XId sourceId = null;
			XId targetId = null;
			if (ID_MAP.containsKey(linkGene.getSourceId())) {
				sourceId = ID_MAP.get(linkGene.getSourceId());
			} else {
				sourceId = linkGene.getSourceId();
			}
			if (ID_MAP.containsKey(linkGene.getTargetId())) {
				targetId = ID_MAP.get(linkGene.getTargetId());
			} else {
				targetId = linkGene.getTargetId();
			}
			Link link = new LinkImpl(linkGene.getId(), sourceId, targetId,
					(LinkConfiguration) linkGene.getConfiguration());
			if (!isLoose()) {
				link.setCapacity(linkGene.getFieldMap().get(Link.CAPACITY_ID));
				link.setCapacityAttack(linkGene.getFieldMap().get(
						Link.CAPACITY_ATTACK_ID));
				link.setCapacityDecay(linkGene.getFieldMap().get(
						Link.CAPACITY_DECAY_ID));
				link.setStrength(linkGene.getFieldMap().get(Link.STRENGTH_ID));
				link.setStrengthAttack(linkGene.getFieldMap().get(
						Link.STRENGTH_ATTACK_ID));
				link.setStrengthDecay(linkGene.getFieldMap().get(
						Link.STRENGTH_DECAY_ID));
				link.setInhibitoryFlag(linkGene.getFieldMap().get(
						Link.INHIBITORY_FLAG_ID));
			}
			links.add(link);
		}
		return OneDCritterImpl.getInstance(genome.getId(), nodes, links);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.EvolutionObject#getLogger()
	 */
	@Override
	public XLogger getLogger() {
		return XLogger.getInstance(getClass());
	}
}
