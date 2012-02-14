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
package com.xtructure.xnet.demos.art;

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
 * The Class NetworkImpl.
 * 
 * @author Luis Guimbarda
 */
public final class NetworkImpl extends AbstractNetwork {

	/** The Constant XML_FORMAT. */
	public static final XmlFormat<NetworkImpl> XML_FORMAT = new NetworkXmlFormat();

	/** The Constant XML_BINDING. */
	public static final XmlBinding XML_BINDING = XmlBinding.builder()//
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
	 * Gets the node visualization.
	 * 
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
	 * Gets the link visualization.
	 * 
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
	 * Gets the network visualization.
	 * 
	 * @return null
	 */
	@Override
	public XVisualization<StandardTimePhase> getNetworkVisualization() {
		return null;
	}

	/**
	 * The Class NetworkXmlFormat.
	 */
	private static final class NetworkXmlFormat extends
			AbstractXmlFormat<NetworkImpl> {

		/**
		 * Instantiates a new network xml format.
		 */
		protected NetworkXmlFormat() {
			super(NetworkImpl.class);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xutil.xml.XmlFormat#newInstance(com.xtructure.xutil
		 * .xml.ReadAttributes, com.xtructure.xutil.xml.ReadElements)
		 */
		@Override
		protected NetworkImpl newInstance(ReadAttributes readAttributes,
				ReadElements readElements) throws XMLStreamException {
			return new NetworkImpl(//
					readAttributes.getValue(ID_ATTRIBUTE),//
					new HashSet<Node>(readElements.getValues(NODE_ELEMENT)),//
					new HashSet<Link>(readElements.getValues(LINK_ELEMENT)));
		}
	}
}
