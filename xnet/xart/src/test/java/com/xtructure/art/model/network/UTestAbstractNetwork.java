/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.model.network;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.link.LinkImpl;
import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.art.model.node.NodeImpl;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.coll.XIdObjectTransform;
import com.xtructure.xutil.config.FloatXParameter;
import com.xtructure.xutil.config.VettingStrategy;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.TestUtils;

@Test(groups = { "unit:xart" })
public final class UTestAbstractNetwork {
	private static final XId					NETWORK_ID;
	private static final XId					SRC_NODE_ID;
	private static final XId					TGT_NODE_ID;
	private static final XId					LINK_ID;
	private static final VettingStrategy<Float>	SRC_ENERGY_VS;
	private static final VettingStrategy<Float>	TGT_ENERGY_VS;
	private static final VettingStrategy<Float>	LINK_STRENGTH_VS;
	private static final VettingStrategy<Float>	LINK_CAPACITY_VS;
	private static final Set<Node>				NODES;
	private static final Set<Link>				LINKS;
	private static final Object[][]				VALID_CONSTRUCTOR_ARGS;
	static {
		NETWORK_ID = XId.newId("testNetworkId");
		SRC_NODE_ID = XId.newId("testSourceNodeId");
		TGT_NODE_ID = XId.newId("testTargetNodeId");
		LINK_ID = XId.newId("testLinkId");
		LinkConfiguration linkConfig = LinkConfiguration.builder()//
				.setInhibitoryFlag(Range.FALSE_BOOLEAN_RANGE, Range.FALSE_BOOLEAN_RANGE)//
				.newInstance();
		NodeConfiguration nodeConfig = NodeConfiguration.DEFAULT_CONFIGURATION;
		SRC_ENERGY_VS = TGT_ENERGY_VS = ((FloatXParameter) nodeConfig.getParameter(Node.ENERGY_ID)).newField().getVettingStrategy();
		LINK_STRENGTH_VS = ((FloatXParameter) linkConfig.getParameter(Link.STRENGTH_ID)).newField().getVettingStrategy();
		LINK_CAPACITY_VS = ((FloatXParameter) linkConfig.getParameter(Link.CAPACITY_ID)).newField().getVettingStrategy();
		NODES = new SetBuilder<Node>()//
				.add(new NodeImpl(SRC_NODE_ID, nodeConfig))//
				.add(new NodeImpl(TGT_NODE_ID, nodeConfig))//
				.newImmutableInstance();
		LINKS = new SetBuilder<Link>()//
				.add(new LinkImpl(LINK_ID, SRC_NODE_ID, TGT_NODE_ID, linkConfig))//
				.newImmutableInstance();
		VALID_CONSTRUCTOR_ARGS = TestUtils.crossData(//
				TestUtils.createData(//
						NETWORK_ID,//
						NETWORK_ID.createChild(0)),//
				TestUtils.createData(//
						NODES),//
				TestUtils.createData(//
						LINKS));
	}

	@Test(dataProvider = "validConstructorArgs")
	public void constructorSucceeds(XId id, Set<Node> nodes, Set<Link> links) {
		assertThat("",//
				new DummyNetwork(id, nodes, links), isNotNull());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void constructorWithNullIdThrowsException() {
		new DummyNetwork(null, NODES, LINKS);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void constructorWithNullNodesThrowsException() {
		new DummyNetwork(NETWORK_ID, null, LINKS);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void constructorWithNullLinksThrowsException() {
		new DummyNetwork(NETWORK_ID, NODES, null);
	}

	public void getDataOnNodeFragmentReturnsExpectedObject() {
		DummyNetwork net = new DummyNetwork(NETWORK_ID, NODES, LINKS);
		Node.Fragment frag = Node.Fragment.DELAY;
		for (Node node : NODES) {
			assertThat("",//
					net.getData(frag.generateExtendedId(node.getId())),//
					isEqualTo(node.getDelay()));
		}
	}

	public void getDataOnLinkFragmentReturnsExpectedObject() {
		DummyNetwork net = new DummyNetwork(NETWORK_ID, NODES, LINKS);
		Link.Fragment frag = Link.Fragment.CAPACITY;
		for (Link link : LINKS) {
			assertThat("",//
					net.getData(frag.generateExtendedId(link.getId())),//
					isEqualTo(link.getCapacity()));
		}
	}

	public void getDataOnNodeReturnsExpectedNode() {
		DummyNetwork net = new DummyNetwork(NETWORK_ID, NODES, LINKS);
		for (Node node : NODES) {
			assertThat("",//
					net.getData(node.getId()),//
					isSameAs(node));
		}
	}

	public void getDataOnLinkReturnsExpectedLink() {
		DummyNetwork net = new DummyNetwork(NETWORK_ID, NODES, LINKS);
		for (Link link : LINKS) {
			assertThat("",//
					net.getData(link.getId()),//
					isSameAs(link));
		}
	}

	public void getLinksReturnsExpectedObject() {
		assertThat("",//
				new DummyNetwork(NETWORK_ID, NODES, LINKS).getLinkIds(),//
				isEqualTo(new SetBuilder<XId>().addAll(XIdObjectTransform.getInstance(), LINKS).newInstance()));
	}

	public void getNodesReturnsExpectedObject() {
		assertThat("",//
				new DummyNetwork(NETWORK_ID, NODES, LINKS).getNodeIds(),//
				isEqualTo(new SetBuilder<XId>().addAll(XIdObjectTransform.getInstance(), NODES).newInstance()));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void getDataWithNullIdThrowException() {
		new DummyNetwork(NETWORK_ID, NODES, LINKS).getData(null);
	}

	public void calculateAndUpdateSucceed() {
		DummyNetwork net = new DummyNetwork(NETWORK_ID, NODES, LINKS);
		Node src = (Node) net.getData(SRC_NODE_ID);
		Node tgt = (Node) net.getData(TGT_NODE_ID);
		Link link = (Link) net.getData(LINK_ID);
		Float initLinkEnergy = link.getOutputEnergy();
		Float initLinkStrength = link.getStrength();
		Float initLinkCapacity = link.getCapacity();
		Float initSrcEnergy = src.getEnergies().getBackEnergy();
		Float initTgtEnergy = tgt.getEnergies().getBackEnergy();
		Float expectedLinkStrength = LINK_STRENGTH_VS.vetValue(//
				(float) Math.min(Math.max(0.0,//
						link.getStrength() //
								- link.getStrengthDecay() * link.getStrength() * initSrcEnergy //
								+ link.getStrengthAttack() * link.getCapacity()), //
						link.getCapacity()));
		Float expectedLinkCapacity = LINK_CAPACITY_VS.vetValue(//
				link.getCapacity() //
						- link.getCapacityDecay() * link.getCapacity() //
						+ link.getCapacityAttack() * initSrcEnergy * initTgtEnergy);
		Float expectedLinkEnergy = link.isInhibitory() ? (-initSrcEnergy * link.getStrength()) : (initSrcEnergy * link.getStrength());
		Float expectedSrcEnergy = SRC_ENERGY_VS.vetValue(//
				src.getEnergies().getBackEnergy()//
						- src.getEnergies().getBackEnergy() * src.getEnergyDecay());
		Float expectedTgtEnergy = TGT_ENERGY_VS.vetValue(//
				tgt.getEnergies().getBackEnergy()//
						- tgt.getEnergies().getBackEnergy() * tgt.getEnergyDecay()//
						+ tgt.getExcitatoryScale() * expectedLinkEnergy);
		net.calculate();
		Float interLinkEnergy = link.getOutputEnergy();
		Float interLinkStrength = link.getStrength();
		Float interLinkCapacity = link.getCapacity();
		Float interSrcEnergy = src.getEnergies().getBackEnergy();
		Float interTgtEnergy = tgt.getEnergies().getBackEnergy();
		net.update();
		Float finalLinkEnergy = link.getOutputEnergy();
		Float finalLinkStrength = link.getStrength();
		Float finalLinkCapacity = link.getCapacity();
		Float finalSrcEnergy = src.getEnergies().getBackEnergy();
		Float finalTgtEnergy = tgt.getEnergies().getBackEnergy();
		assertThat("",//
				initLinkEnergy, isEqualTo(0f));
		assertThat("",//
				initLinkStrength, isEqualTo(interLinkStrength));
		assertThat("",//
				initLinkCapacity, isEqualTo(interLinkCapacity));
		assertThat("",//
				initSrcEnergy, isEqualTo(interSrcEnergy));
		assertThat("",//
				initTgtEnergy, isEqualTo(interTgtEnergy));
		assertThat("",//
				interLinkEnergy, isEqualTo(expectedLinkEnergy));
		assertThat("",//
				finalLinkEnergy, isEqualTo(expectedLinkEnergy));
		assertThat("",//
				finalLinkStrength, isEqualTo(expectedLinkStrength));
		assertThat("",//
				finalLinkCapacity, isEqualTo(expectedLinkCapacity));
		assertThat("",//
				finalSrcEnergy, isEqualTo(expectedSrcEnergy));
		assertThat("",//
				finalTgtEnergy, isEqualTo(expectedTgtEnergy));
	}

	@DataProvider
	public Object[][] validConstructorArgs() {
		return VALID_CONSTRUCTOR_ARGS;
	}
}
