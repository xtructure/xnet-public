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
package com.xtructure.xnet.demos.oned.components;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javolution.xml.XMLObjectReader;
import javolution.xml.stream.XMLStreamException;

import com.sun.tools.javac.util.Pair;
import com.xtructure.art.examples.twonode.LinkVisualization;
import com.xtructure.art.examples.twonode.NodeVisualization;
import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.network.AbstractNetwork;
import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlReader;

// TODO: Auto-generated Javadoc
/**
 * The Class Critter.
 * 
 * @author Luis Guimbarda
 */
public class OneDCritterImpl extends AbstractNetwork implements OneDCritter {

	/** The Constant XML_FORMAT. */
	public static final XmlFormat<OneDCritterImpl> XML_FORMAT = new CritterXmlFormat();

	/** The Constant DEFAULT_ID. */
	public static final XId DEFAULT_ID = XId.newId("Critter");

	/** The Constant NUM_PATTERN. */
	private static final Pattern NUM_PATTERN = Pattern
			.compile("^\\D*(\\d+)\\D*$");

	/**
	 * Gets the single instance of Critter.
	 * 
	 * @param id
	 *            the id
	 * @param nodes
	 *            the nodes
	 * @param links
	 *            the links
	 * @return single instance of Critter
	 */
	public static OneDCritterImpl getInstance(XId id, Set<Node> nodes,
			Set<Link> links) {
		return new OneDCritterImpl(id, nodes, links);
	}

	/**
	 * Gets the single instance of Critter.
	 * 
	 * @param configDir
	 *            the config dir
	 * @param networkFile
	 *            the network file
	 * @return single instance of Critter
	 * @throws XMLStreamException
	 *             the xML stream exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static OneDCritterImpl getInstance(File configDir, File networkFile)
			throws XMLStreamException, IOException {
		// load configs (try everything that is not the network file...)
		for (final File file : configDir.listFiles()) {
			if (!file.equals(networkFile)) {
				parseConfig(file);
			}
		}
		// load the network
		FileReader in = null;
		XMLObjectReader reader = null;
		try {
			in = new FileReader(networkFile);
			reader = XMLObjectReader //
					.newInstance(in);
			// return the critter
			OneDCritterImpl critter = reader.read();
			return critter;
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * Parses the config.
	 * 
	 * @param file
	 *            the file
	 */
	private static void parseConfig(File file) {
		try {
			// first try as a node config...
			try {
				// node config?
				final NodeConfiguration nodeConfig = XmlReader.read(file);
				System.out.println("parsed node configuration "
						+ nodeConfig.getId() + " from file " + file.getName());
				return;
			} catch (IOException ioEx) {
				// ignore
			} catch (XMLStreamException xmlStreamEx) {
				// ignore
			}
			// next try as a link config...
			try {
				// node config?
				final LinkConfiguration linkConfig = XmlReader.read(file);
				System.out.println("parsed link configuration "
						+ linkConfig.getId() + " from file " + file.getName());
				return;
			} catch (IOException ioEx) {
				// ignore
			} catch (XMLStreamException xmlStreamEx) {
				// ignore
			}
			// oh well, punt...
			System.err.println("ignoring file " + file);
		} catch (IllegalArgumentException ex) {
			// ignore (config already loaded)
		}
	}

	/**
	 * Instantiates a new nEAT critter.
	 * 
	 * @param id
	 *            the id
	 * @param nodes
	 *            the nodes
	 * @param links
	 *            the links
	 */
	private OneDCritterImpl(XId id, Set<Node> nodes, Set<Link> links) {
		super(id, nodes, links, getIds(nodes, links));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.projects.xevo.oned.sim.Critter#getClockwiseFootId()
	 */
	@Override
	public XId getClockwiseFootId() {
		return CLOCKWISE_FOOT_ENERGY_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.projects.xevo.oned.sim.Critter#getClockwiseUtricleId()
	 */
	@Override
	public XId getClockwiseUtricleId() {
		return CLOCKWISE_UTRICLE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.oned.sim.Critter#getCounterClockwiseFootId()
	 */
	@Override
	public XId getCounterClockwiseFootId() {
		return COUNTER_CLOCKWISE_FOOT_ENERGY_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.oned.sim.Critter#getCounterClockwiseUtricleId
	 * ()
	 */
	@Override
	public XId getCounterClockwiseUtricleId() {
		return COUNTER_CLOCKWISE_UTRICLE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.projects.xevo.oned.sim.Critter#getNoseId()
	 */
	@Override
	public XId getNoseId() {
		return NOSE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.network.Network#getNetworkVisualization()
	 */
	@Override
	public OneDCritterViz getNetworkVisualization() {
		Map<XId, Pair<XId, XId>> excitatoryEndpointMap = new HashMap<XId, Pair<XId, XId>>();
		Map<XId, Pair<XId, XId>> inhibitoryEndpointMap = new HashMap<XId, Pair<XId, XId>>();
		for (XId linkId : _links.keySet()) {
			Link link = _links.get(linkId).getLink();
			Pair<XId, XId> endpoints = Pair.of(link.getSourceId(),
					link.getTargetId());
			if (link.isInhibitory()) {
				inhibitoryEndpointMap.put(linkId, endpoints);
			} else {
				excitatoryEndpointMap.put(linkId, endpoints);
			}
		}
		List<XId> inputIds = new ArrayList<XId>(3);
		inputIds.add(COUNTER_CLOCKWISE_UTRICLE_ID);
		inputIds.add(NOSE_ID);
		inputIds.add(CLOCKWISE_UTRICLE_ID);
		List<XId> hiddenIds = new ArrayList<XId>(_nodes.size() - 5);
		List<XId> outputIds = new ArrayList<XId>(2);
		outputIds.add(CLOCKWISE_FOOT_ID);
		outputIds.add(COUNTER_CLOCKWISE_FOOT_ID);
		for (XId nodeId : _nodes.keySet()) {
			if (inputIds.contains(nodeId) || outputIds.contains(nodeId)) {
				continue;
			}
			hiddenIds.add(nodeId);
		}
		return new OneDCritterViz(this, excitatoryEndpointMap, inhibitoryEndpointMap,
				inputIds, hiddenIds, outputIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.network.Network#getNodeVisualization()
	 */
	@Override
	public NodeVisualization getNodeVisualization() {
		XId nodeVizId = XId.newId(getId().getBase() + "_NodeViz", getId()
				.getInstanceNums());
		List<Node> targetNodes = new ArrayList<Node>();
		for (NodeInfo nodeInfo : _nodes.values()) {
			targetNodes.add(nodeInfo.getNode());
		}
		Collections.sort(targetNodes, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return getIndex(o1) - getIndex(o2);
			}

			private int getIndex(Node n) {
				if (n.getId().getBase().equals("cc-utricle")) {
					return 0;
				} else if (n.getId().getBase().equals("nose")) {
					return 1;
				} else if (n.getId().getBase().equals("c-utricle")) {
					return 2;
				} else if (n.getId().getBase().equals("cc-foot")) {
					return 3;
				} else if (n.getId().getBase().equals("c-foot")) {
					return 4;
				} else if (!n.getId().getInstanceNums().isEmpty()) {
					return n.getId().getInstanceNum();
				} else {
					Matcher matcher = NUM_PATTERN.matcher(n.getId().getBase());
					return matcher.find() ? Integer.parseInt(matcher.group(1))
							: 999;
				}
			}
		});
		return NodeVisualization.getInstance(nodeVizId, this, targetNodes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.network.Network#getLinkVisualization()
	 */
	@Override
	public LinkVisualization getLinkVisualization() {
		XId linkVizId = XId.newId(getId().getBase() + "_LinkViz", getId()
				.getInstanceNums());
		List<Link> targetLinks = new ArrayList<Link>();
		for (LinkInfo linkInfo : _links.values()) {
			targetLinks.add(linkInfo.getLink());
		}
		Collections.sort(targetLinks, new Comparator<Link>() {
			@Override
			public int compare(Link o1, Link o2) {
				Matcher matcher = NUM_PATTERN.matcher(o1.getId().getBase());
				int i1 = matcher.find() ? Integer.parseInt(matcher.group(1))
						: 999;
				matcher = NUM_PATTERN.matcher(o2.getId().getBase());
				int i2 = matcher.find() ? Integer.parseInt(matcher.group(1))
						: 999;
				return i1 - i2;
			}
		});
		return LinkVisualization.getInstance(linkVizId, this, targetLinks);
	}

	/**
	 * The Class CritterXmlFormat.
	 */
	private static final class CritterXmlFormat extends
			AbstractXmlFormat<OneDCritterImpl> {

		/**
		 * Instantiates a new critter xml format.
		 */
		private CritterXmlFormat() {
			super(OneDCritterImpl.class);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xutil.xml.XmlFormat#newInstance(com.xtructure.xutil
		 * .xml.ReadAttributes, com.xtructure.xutil.xml.ReadElements)
		 */
		@Override
		protected OneDCritterImpl newInstance(ReadAttributes readAttributes,
				ReadElements readElements) throws XMLStreamException {
			return new OneDCritterImpl(//
					readAttributes.getValue(ID_ATTRIBUTE),//
					new HashSet<Node>(readElements.getValues(NODE_ELEMENT)),//
					new HashSet<Link>(readElements.getValues(LINK_ELEMENT)));
		}
	}
}
