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
package com.xtructure.xneat.genetics;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.containsElements;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;
import static com.xtructure.xutil.valid.ValidateUtils.not;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.link.impl.LinkGeneImpl;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.genetics.node.impl.NodeGeneImpl;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestGeneMap {
	private static final NodeGeneConfiguration	NODE_CONFIGURATION	= NodeGeneConfiguration.builder(XId.newId("UTestGeneMap", 0)).newInstance();
	private static final LinkGeneConfiguration	LINK_CONFIGURATION	= LinkGeneConfiguration.builder(XId.newId("UTestGeneMap", 1)).newInstance();

	public void constructorSucceeds() {
		assertThat("",//
				new GeneMap(), isNotNull());
		assertThat("",//
				new GeneMap(new GeneMap()), isNotNull());
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.HIDDEN, NODE_CONFIGURATION);
		NodeGene node2 = new NodeGeneImpl(2, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node1.getId(), node1.getId(), LINK_CONFIGURATION);
		LinkGene link1 = new LinkGeneImpl(1, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		assertThat("",//
				new GeneMap(//
						new SetBuilder<NodeGene>()//
								.add(node0, node1, node2)//
								.newImmutableInstance(),//
						new SetBuilder<LinkGene>()//
								.add(link0, link1)//
								.newImmutableInstance()),//
				isNotNull());
	}

	public void addLinkAndGetLinksEtcBehaveAsExpected() {
		GeneMap geneMap = new GeneMap();
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.HIDDEN, NODE_CONFIGURATION);
		NodeGene node2 = new NodeGeneImpl(2, NodeType.OUTPUT, NODE_CONFIGURATION);
		geneMap.add(node0);
		geneMap.add(node1);
		geneMap.add(node2);
		assertThat("",//
				geneMap.getLinks(), isEmpty());
		assertThat("",//
				geneMap.getLinkIdNumber(), isEqualTo(0));
		assertThat("",//
				geneMap.getLinkCount(), isEqualTo(0));
		LinkGene link1 = new LinkGeneImpl(1, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		geneMap.add(link1);
		assertThat("",//
				geneMap.getLinks(), hasSize(1), containsElement(link1));
		assertThat("",//
				geneMap.getLinkIdNumber(), isEqualTo(2));
		assertThat("",//
				geneMap.getLinkCount(), isEqualTo(1));
		LinkGene link0 = new LinkGeneImpl(0, node1.getId(), node1.getId(), LINK_CONFIGURATION);
		geneMap.add(link0);
		assertThat("",//
				geneMap.getLinks(), hasSize(2), containsElements(link1, link0));
		assertThat("",//
				geneMap.getLinkIdNumber(), isEqualTo(2));
		assertThat("",//
				geneMap.getLinkCount(), isEqualTo(2));
		LinkGene link1Alt = new LinkGeneImpl(1, node1.getId(), node2.getId(), LINK_CONFIGURATION);
		geneMap.add(link1Alt);
		assertThat("",//
				geneMap.getLinks(), hasSize(2), containsElements(link1Alt, link0));
		assertThat("",//
				geneMap.getLinkIdNumber(), isEqualTo(2));
		assertThat("",//
				geneMap.getLinkCount(), isEqualTo(2));
	}

	public void addNodeAndGetNodesEtcBehaveAsExpected() {
		// gene map from scratch
		GeneMap geneMap = new GeneMap();
		assertThat("",//
				geneMap.getNodes(), isEmpty());
		assertThat("",//
				geneMap.getNodeIdNumber(), isEqualTo(0));
		assertThat("",//
				geneMap.getNodeCount(), isEqualTo(0));
		NodeGene node1 = new NodeGeneImpl(1, NodeType.HIDDEN, NODE_CONFIGURATION);
		geneMap.add(node1);
		assertThat("",//
				geneMap.getNodes(), hasSize(1), containsElement(node1));
		assertThat("",//
				geneMap.getNodeIdNumber(), isEqualTo(2));
		assertThat("",//
				geneMap.getNodeCount(), isEqualTo(1));
		NodeGene node0 = new NodeGeneImpl(0, NodeType.HIDDEN, NODE_CONFIGURATION);
		geneMap.add(node0);
		assertThat("",//
				geneMap.getNodes(), hasSize(2), containsElements(node1, node0));
		assertThat("",//
				geneMap.getNodeIdNumber(), isEqualTo(2));
		assertThat("",//
				geneMap.getNodeCount(), isEqualTo(2));
		NodeGene node1Alt = new NodeGeneImpl(1, NodeType.INPUT, NODE_CONFIGURATION);
		geneMap.add(node1Alt);
		assertThat("",//
				geneMap.getNodes(), hasSize(2), containsElements(node1Alt, node0));
		assertThat("",//
				geneMap.getNodeIdNumber(), isEqualTo(2));
		assertThat("",//
				geneMap.getNodeCount(), isEqualTo(2));
		// genemap with predetermined set
		Set<NodeGene> nodes = new SetBuilder<NodeGene>()//
				.add(node0, node1)//
				.newImmutableInstance();
		geneMap = new GeneMap(nodes, new HashSet<LinkGene>());
		assertThat("",//
				geneMap.getNodes(), hasSize(2), containsElements(node1, node0));
		assertThat("",//
				geneMap.getNodeIdNumber(), isEqualTo(2));
		assertThat("",//
				geneMap.getNodeCount(), isEqualTo(2));
	}

	public void getIncomingAndOutgoingLinksAndNodesReturnsExpectedSet() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap();
		assertThat("",//
				geneMap.getIncomingLinkIds(node0.getId()), isNull());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node0.getId()), isNull());
		assertThat("",//
				geneMap.getIncomingNodeIds(node0.getId()), isNull());
		assertThat("",//
				geneMap.getOutgoingNodeIds(node0.getId()), isNull());
		assertThat("",//
				geneMap.getIncomingLinkIds(node1.getId()), isNull());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node1.getId()), isNull());
		assertThat("",//
				geneMap.getIncomingNodeIds(node1.getId()), isNull());
		assertThat("",//
				geneMap.getOutgoingNodeIds(node1.getId()), isNull());
		geneMap.add(node0);
		geneMap.add(node1);
		assertThat("",//
				geneMap.getIncomingLinkIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getIncomingNodeIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getOutgoingNodeIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getIncomingLinkIds(node1.getId()), isEmpty());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node1.getId()), isEmpty());
		assertThat("",//
				geneMap.getIncomingNodeIds(node1.getId()), isEmpty());
		assertThat("",//
				geneMap.getOutgoingNodeIds(node1.getId()), isEmpty());
		geneMap.add(link0);
		assertThat("",//
				geneMap.getIncomingLinkIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node0.getId()), hasSize(1), containsElement(link0.getId()));
		assertThat("",//
				geneMap.getIncomingNodeIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getOutgoingNodeIds(node0.getId()), hasSize(1), containsElement(node1.getId()));
		assertThat("",//
				geneMap.getIncomingLinkIds(node1.getId()), hasSize(1), containsElement(link0.getId()));
		assertThat("",//
				geneMap.getOutgoingLinkIds(node1.getId()), isEmpty());
		assertThat("",//
				geneMap.getIncomingNodeIds(node1.getId()), hasSize(1), containsElement(node0.getId()));
		assertThat("",//
				geneMap.getOutgoingNodeIds(node1.getId()), isEmpty());
	}

	public void removeLinkBehavesAsExpected() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				new SetBuilder<NodeGene>().add(node0, node1).newImmutableInstance(),//
				new SetBuilder<LinkGene>().add(link0).newImmutableInstance());
		assertThat("",//
				geneMap.contains(node0), isTrue());
		assertThat("",//
				geneMap.contains(node1), isTrue());
		assertThat("",//
				geneMap.contains(link0), isTrue());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node0.getId()), hasSize(1), containsElement(link0.getId()));
		assertThat("",//
				geneMap.getOutgoingNodeIds(node0.getId()), hasSize(1), containsElement(node1.getId()));
		assertThat("",//
				geneMap.getIncomingLinkIds(node1.getId()), hasSize(1), containsElement(link0.getId()));
		assertThat("",//
				geneMap.getIncomingNodeIds(node1.getId()), hasSize(1), containsElement(node0.getId()));
		geneMap.remove(link0);
		assertThat("",//
				geneMap.contains(node0), isTrue());
		assertThat("",//
				geneMap.contains(node1), isTrue());
		assertThat("",//
				geneMap.contains(link0), isFalse());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getOutgoingNodeIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getIncomingLinkIds(node1.getId()), isEmpty());
		assertThat("",//
				geneMap.getIncomingNodeIds(node1.getId()), isEmpty());
	}

	public void removeNodeBehavesAsExpected() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				new SetBuilder<NodeGene>().add(node0, node1).newImmutableInstance(),//
				new SetBuilder<LinkGene>().add(link0).newImmutableInstance());
		assertThat("",//
				geneMap.contains(node0), isTrue());
		assertThat("",//
				geneMap.contains(node1), isTrue());
		assertThat("",//
				geneMap.contains(link0), isTrue());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node0.getId()), hasSize(1), containsElement(link0.getId()));
		assertThat("",//
				geneMap.getOutgoingNodeIds(node0.getId()), hasSize(1), containsElement(node1.getId()));
		assertThat("",//
				geneMap.getIncomingLinkIds(node1.getId()), hasSize(1), containsElement(link0.getId()));
		assertThat("",//
				geneMap.getIncomingNodeIds(node1.getId()), hasSize(1), containsElement(node0.getId()));
		geneMap.remove(node0);
		assertThat("",//
				geneMap.contains(node0), isFalse());
		assertThat("",//
				geneMap.contains(node1), isTrue());
		assertThat("",//
				geneMap.contains(link0), isFalse());
		assertThat("",//
				geneMap.getIncomingLinkIds(node1.getId()), isEmpty());
		assertThat("",//
				geneMap.getIncomingNodeIds(node1.getId()), isEmpty());
		geneMap = new GeneMap(//
				new SetBuilder<NodeGene>().add(node0, node1).newImmutableInstance(),//
				new SetBuilder<LinkGene>().add(link0).newImmutableInstance());
		geneMap.remove(node1);
		assertThat("",//
				geneMap.contains(node0), isTrue());
		assertThat("",//
				geneMap.contains(node1), isFalse());
		assertThat("",//
				geneMap.contains(link0), isFalse());
		assertThat("",//
				geneMap.getOutgoingLinkIds(node0.getId()), isEmpty());
		assertThat("",//
				geneMap.getOutgoingNodeIds(node0.getId()), isEmpty());
	}

	public void toStringReturnsExpectedString() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				new SetBuilder<NodeGene>().add(node0, node1).newImmutableInstance(),//
				new SetBuilder<LinkGene>().add(link0).newImmutableInstance());
		assertThat("",//
				geneMap.toString(),//
				isEqualTo(new ToStringBuilder(geneMap, ToStringStyle.MULTI_LINE_STYLE)//
						.append(node0).append(node1).append(link0).toString()));
	}

	public void clearAndIsEmptyBehavesAsExpected() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				Arrays.asList(node0, node1),//
				Arrays.asList(link0));
		assertThat("",//
				geneMap.isEmpty(), isFalse());
		geneMap.clear();
		assertThat("",//
				geneMap.isEmpty(), isTrue());
	}

	public void containsAllReturnsExpectedBoolean() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				Arrays.asList(node0, node1),//
				Arrays.asList(link0));
		LinkGene link1 = new LinkGeneImpl(1, node1.getId(), node0.getId(), LINK_CONFIGURATION);
		List<Gene> shuffled = RandomUtil.shuffle(geneMap);
		for (int i = 0; i < shuffled.size(); i++) {
			assertThat("",//
					geneMap.containsAll(shuffled.subList(0, i)),//
					isTrue());
			assertThat("",//
					geneMap.containsAll(new SetBuilder<Gene>().addAll(shuffled.subList(0, i)).add(link1).newImmutableInstance()),//
					isFalse());
		}
	}

	public void removeAllBehavesAsExpected() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				Arrays.asList(node0, node1),//
				Arrays.asList(link0));
		int i = RandomUtil.nextInteger(geneMap.size());
		List<Gene> list = RandomUtil.shuffle(geneMap);
		List<Gene> subList = list.subList(0, i);
		list = list.subList(i, list.size());
		for (Gene gene : list) {
			assertThat("",//
					geneMap, containsElement(gene));
		}
		for (Gene gene : subList) {
			assertThat("",//
					geneMap, containsElement(gene));
		}
		geneMap.removeAll(subList);
		for (Gene gene : list) {
			assertThat("",//
					geneMap, containsElement(gene));
		}
		for (Gene gene : subList) {
			assertThat("",//
					geneMap, not(containsElement(gene)));
		}
	}

	public void retainAllBehavesAsExpected() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				Arrays.asList(node0, node1),//
				Arrays.asList(link0));
		int i = RandomUtil.nextInteger(geneMap.size());
		List<Gene> list = RandomUtil.shuffle(geneMap);
		List<Gene> subList = list.subList(0, i);
		list = list.subList(i, list.size());
		for (Gene gene : list) {
			assertThat("",//
					geneMap, containsElement(gene));
		}
		for (Gene gene : subList) {
			assertThat("",//
					geneMap, containsElement(gene));
		}
		geneMap.retainAll(subList);
		for (Gene gene : list) {
			assertThat("",//
					geneMap, not(containsElement(gene)));
		}
		for (Gene gene : subList) {
			assertThat("",//
					geneMap, containsElement(gene));
		}
	}

	public void sizeReturnsExpectedInt() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap();
		assertThat("",//
				geneMap.size(), isEqualTo(0));
		geneMap.add(node0);
		assertThat("",//
				geneMap.size(), isEqualTo(1));
		geneMap.add(node1);
		assertThat("",//
				geneMap.size(), isEqualTo(2));
		geneMap.add(link0);
		assertThat("",//
				geneMap.size(), isEqualTo(3));
	}

	public void toArrayReturnsExpectedArray() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		Set<Gene> genesList = new SetBuilder<Gene>().add(node0, node1, link0).newImmutableInstance();
		GeneMap geneMap = new GeneMap(//
				Arrays.asList(node0, node1),//
				Arrays.asList(link0));
		Object[] genes = geneMap.toArray();
		assertThat("",//
				genesList.containsAll(Arrays.asList(genes)), isTrue());
		assertThat("",//
				Arrays.asList(genes).containsAll(genesList), isTrue());
		genes = geneMap.toArray(new Gene[0]);
		assertThat("",//
				genesList.containsAll(Arrays.asList(genes)), isTrue());
		assertThat("",//
				Arrays.asList(genes).containsAll(genesList), isTrue());
	}

	public void getAndRemoveBehaveAsExpected() {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				Arrays.asList(node0, node1),//
				Arrays.asList(link0));
		assertThat("",//
				geneMap.get(node0.getId()), isEqualTo(node0));
		assertThat("",//
				geneMap.get(node1.getId()), isEqualTo(node1));
		assertThat("",//
				geneMap.get(link0.getId()), isEqualTo(link0));
		geneMap.remove(link0.getId());
		assertThat("",//
				geneMap.get(node0.getId()), isEqualTo(node0));
		assertThat("",//
				geneMap.get(node1.getId()), isEqualTo(node1));
		assertThat("",//
				geneMap.get(link0.getId()), isNull());
	}
}
