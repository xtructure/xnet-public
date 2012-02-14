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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.art.examples.twonode.LinkVisualization;
import com.xtructure.art.examples.twonode.NodeVisualization;
import com.xtructure.art.model.link.Link;
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
 * 
 */
public class DummyNetwork extends AbstractNetwork {
	protected DummyNetwork(XId id, Set<? extends Node> nodes, Set<? extends Link> links) {
		super(id, nodes, links);
	}

	@Override
	public XVisualization<StandardTimePhase> getNetworkVisualization() {
		return null;
	}

	@Override
	public LinkVisualization getLinkVisualization() {
		return null;
	}

	@Override
	public NodeVisualization getNodeVisualization() {
		return null;
	}

	public static final XmlFormat<DummyNetwork>	XML_FORMAT	= new DummyXmlFormat();
	public static final XmlBinding				XML_BINDING	= XmlBinding.builder()//
																	.add(DummyNetwork.class)//
																	.newInstance();

	private static final class DummyXmlFormat extends AbstractXmlFormat<DummyNetwork> {
		protected DummyXmlFormat() {
			super(DummyNetwork.class);
		}

		@Override
		protected DummyNetwork newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			List<Node> nodes = readElements.getValues(NODE_ELEMENT);
			List<Link> links = readElements.getValues(LINK_ELEMENT);
			return new DummyNetwork(id, new HashSet<Node>(nodes), new HashSet<Link>(links));
		}
	}
}
