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
package com.xtructure.art.examples.twonode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.network.AbstractNetwork;
import com.xtructure.art.model.node.Node;
import com.xtructure.xsim.gui.XVisualization;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * @author Luis Guimbarda
 */
public final class NetworkImpl extends AbstractNetwork {
	/** */
	public static final XmlFormat<NetworkImpl>	XML_FORMAT	= new NetworkXmlFormat();
	/** */
	public static final XmlBinding				XML_BINDING	= XmlBinding.builder()//
																	.add(NetworkImpl.class, "artNetwork")//
																	.newInstance();

	/**
	 * Creates a new Network.
	 * 
	 * @param id
	 *            the id of the Network
	 * @param nodes
	 *            the set of nodes in the Network
	 * @param links
	 *            the set of links in the Network
	 */
	public NetworkImpl(XId id, Set<Node> nodes, Set<Link> links) {
		super(id, nodes, links, null, null);
	}

	/**
	 * @return a NodeVisualization component associated with this Network
	 */
	@Override
	public NodeVisualization getNodeVisualization() {
		XId nodeVizId = XId.newId(getId().getBase() + "_NodeViz");
		List<Node> targetNodes = new ArrayList<Node>();
		for (NodeInfo nodeInfo : _nodes.values()) {
			targetNodes.add(nodeInfo.getNode());
		}
		return NodeVisualization.getInstance(nodeVizId, this, targetNodes);
	}

	/**
	 * @return a LinkVisualization component associated with this Network
	 */
	@Override
	public LinkVisualization getLinkVisualization() {
		XId linkVizId = XId.newId(getId().getBase() + "_LinkViz");
		List<Link> targetLinks = new ArrayList<Link>();
		for (LinkInfo linkInfo : _links.values()) {
			targetLinks.add(linkInfo.getLink());
		}
		return LinkVisualization.getInstance(linkVizId, this, targetLinks);
	}

	/**
	 * @return null
	 */
	@Override
	public XVisualization<StandardTimePhase> getNetworkVisualization() {
		return null;
	}

	/** */
	private static final class NetworkXmlFormat extends AbstractXmlFormat<NetworkImpl> {
		protected NetworkXmlFormat() {
			super(NetworkImpl.class);
		}

		@Override
		protected NetworkImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			return new NetworkImpl(//
					readAttributes.getValue(ID_ATTRIBUTE),//
					new HashSet<Node>(readElements.getValues(NODE_ELEMENT)),//
					new HashSet<Link>(readElements.getValues(LINK_ELEMENT)));
		}
	}
}
