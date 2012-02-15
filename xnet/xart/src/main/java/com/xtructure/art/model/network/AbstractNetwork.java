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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.link.LinkImpl;
import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.NodeImpl;
import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.impl.AbstractStandardXComponent;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * An ART network.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * @version 0.9.6
 */
public abstract class AbstractNetwork extends AbstractStandardXComponent implements Network {
	/** the logger for abstract networks */
	private static final XLogger	LOGGER	= XLogger.getInstance(AbstractNetwork.class);

	/**
	 * Returns the ids of the given nodes and links.
	 * 
	 * @param nodes
	 *            the nodes, the ids of which should be returned
	 * @param links
	 *            the links, the ids of which should be returned
	 * @return the ids of the given nodes and links
	 */
	protected static final Set<XId> getIds(final Set<? extends Node> nodes, final Set<? extends Link> links) {
		final Set<XId> rval = new HashSet<XId>();
		if (nodes != null) {
			for (final Node node : nodes) {
				for (final Node.Fragment nodeFragment : Node.Fragment.values()) {
					rval.add(nodeFragment.generateExtendedId(node.getId()));
				}
				rval.add(node.getId());
			}
		}
		if (links != null) {
			for (final Link link : links) {
				for (final Link.Fragment linkFragment : Link.Fragment.values()) {
					rval.add(linkFragment.generateExtendedId(link.getId()));
				}
				rval.add(link.getId());
			}
		}
		return rval;
	}

	/** A map to node info from the associated node ids. */
	protected final Map<XId, NodeInfo>	_nodes;
	/** A map to link info from the associated link ids. */
	protected final Map<XId, LinkInfo>	_links;
	/** Foreign data passed to this component. */
	private final Map<XId, List<Float>>	_foreignData;

	/**
	 * Creates a new {@link AbstractNetwork}
	 * 
	 * @param id
	 *            this network's id
	 * @param nodes
	 *            the nodes in this network
	 * @param links
	 *            the links in this network
	 */
	protected AbstractNetwork(final XId id, final Set<? extends Node> nodes, final Set<? extends Link> links) {
		this(id, nodes, links, getIds(nodes, links));
	}

	/**
	 * Creates a new network.
	 * 
	 * @param id
	 *            the id of this network
	 * @param nodes
	 *            the nodes of this network
	 * @param links
	 *            the links of this network
	 * @param fragmentIds
	 *            the ids of the fragments of the nodes and links of this
	 *            network
	 */
	protected AbstractNetwork(final XId id, final Set<? extends Node> nodes, final Set<? extends Link> links, final Set<XId> fragmentIds) {
		this(id, nodes, links, fragmentIds, fragmentIds);
	}

	/**
	 * Creates a new network.
	 * 
	 * @param id
	 *            the id of this network
	 * @param nodes
	 *            the nodes of this network
	 * @param links
	 *            the links of this network
	 * @param outputSourceIds
	 *            the ids of the nodes in this network that are visible outside
	 * @param inputTargetIds
	 *            the ids of the nodes in this network that are expected to
	 *            receive signals from outside
	 */
	protected AbstractNetwork(//
			final XId id,//
			final Set<? extends Node> nodes,//
			final Set<? extends Link> links,//
			final Set<XId> outputSourceIds,//
			final Set<XId> inputTargetIds) {
		super(id, outputSourceIds, inputTargetIds);
		final Map<XId, NodeInfo> tmpNodes = new TreeMap<XId, NodeInfo>();
		final Map<XId, List<Float>> tmpForeignData = new TreeMap<XId, List<Float>>();
		for (final Node node : nodes) {
			tmpNodes.put(node.getId(), new NodeInfo(node));
			tmpForeignData.put(node.getId(), new ArrayList<Float>());
		}
		_nodes = Collections.unmodifiableMap(tmpNodes);
		_foreignData = Collections.unmodifiableMap(tmpForeignData);
		final Map<XId, LinkInfo> tmpLinks = new TreeMap<XId, LinkInfo>();
		for (final Link link : links) {
			tmpLinks.put(link.getId(), new LinkInfo(link));
			_nodes.get(link.getTargetId())._inputLinkIds.add(link.getId());
		}
		_links = Collections.unmodifiableMap(tmpLinks);
	}

	/** {@inheritDoc} */
	@Override
	public Set<XId> getLinkIds() {
		return Collections.unmodifiableSet(_links.keySet());
	}

	/** {@inheritDoc} */
	@Override
	public Set<XId> getNodeIds() {
		return Collections.unmodifiableSet(_nodes.keySet());
	}

	/** {@inheritDoc} */
	@Override
	public final Object getData(final XId partId) {
		LOGGER.trace("begin %s.getData(%s)", getClass().getSimpleName(), partId);
		Object rVal = null;
		if (_nodes.containsKey(partId)) {
			rVal = _nodes.get(partId).getNode();
		} else if (_links.containsKey(partId)) {
			rVal = _links.get(partId).getLink();
		} else if (partId != null) {
			final XId baseNodeId = Node.Fragment.getBaseId(partId);
			if (baseNodeId != null) {
				rVal = Node.Fragment.getInstance(partId).getValue(_nodes.get(baseNodeId)._node);
			} else {
				final XId baseLinkId = Link.Fragment.getBaseId(partId);
				if (baseLinkId != null) {
					rVal = Link.Fragment.getInstance(partId).getValue(_links.get(baseLinkId)._link);
				}
			}
		}
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getData()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * A pre-processing hook for {@link #prepare()}.
	 * <p>
	 * This method clears the data map. If a specialization of this type
	 * overrides this method, it should take care to call the overriden version.
	 * </p>
	 */
	@Override
	protected void prepareBeforeHook() {
		LOGGER.trace("begin %s.prepareBeforeHook()", getClass().getSimpleName());
		// LOGGER.trace("clearing foreign data");
		for (final XId nodeId : _foreignData.keySet()) {
			_foreignData.get(nodeId).clear();
		}
		LOGGER.trace("end %s.prepareBeforeHook()", getClass().getSimpleName());
	}

	/** {@inheritDoc} */
	@Override
	public final void calculate() {
		LOGGER.trace("begin %s.calculate()", getClass().getSimpleName());
		// LOGGER.trace("calculating links");
		for (final XId linkId : _links.keySet()) {
			final LinkInfo info = _links.get(linkId);
			info._outputEnergy = info._link.calculate( //
					_nodes.get(info._link.getSourceId())._node.getEnergies().getFrontEnergy(), //
					_nodes.get(info._link.getTargetId())._node.getEnergies().getBackEnergy());
		}
		// LOGGER.trace("calculating nodes");
		for (final XId nodeId : _nodes.keySet()) {
			final NodeInfo info = _nodes.get(nodeId);
			final List<Float> linkEnergies = new ArrayList<Float>();
			for (final XId linkId : info._inputLinkIds) {
				linkEnergies.add(_links.get(linkId)._outputEnergy);
			}
			for (final Float foreignData : _foreignData.get(nodeId)) {
				linkEnergies.add(foreignData);
			}
			info._node.calculate(linkEnergies);
		}
		LOGGER.trace("end %s.calculate()", getClass().getSimpleName());
	}

	/** {@inheritDoc} */
	@Override
	public final void update() {
		LOGGER.trace("begin %s.update()", getClass().getSimpleName());
		// LOGGER.trace("updating links");
		for (final XId linkId : _links.keySet()) {
			_links.get(linkId)._link.update();
		}
		// LOGGER.trace("updating nodes");
		for (final XId nodeId : _nodes.keySet()) {
			_nodes.get(nodeId)._node.update();
		}
		LOGGER.trace("end %s.update()", getClass().getSimpleName());
	}

	/** {@inheritDoc} */
	@Override
	protected final void addForeignData(final XId targetId, final XAddress sourceAddress, final Object data) {
		LOGGER.trace("begin %s.addForeignData(%s, %s, %s)", getClass().getSimpleName(), targetId, sourceAddress, data);
		_foreignData.get(targetId).add(((Number) data).floatValue());
		LOGGER.trace("end %s.addForeignData()", getClass().getSimpleName());
	}

	/** Information about a node. */
	protected static final class NodeInfo {
		/** The node. */
		private final Node	_node;

		/**
		 * Returns the node in this {@link NodeInfo}
		 * 
		 * @return the node in this {@link NodeInfo}
		 */
		public Node getNode() {
			return _node;
		}

		/** The ids of the input links into this node. */
		private final Set<XId>	_inputLinkIds	= new HashSet<XId>();

		/**
		 * Creates new node information.
		 * 
		 * @param node
		 *            the node
		 */
		private NodeInfo(final Node node) {
			super();
			_node = node;
		}
	}

	/** Information about a link. */
	protected static final class LinkInfo {
		/** The link. */
		private final Link	_link;

		/**
		 * Returns the link in this {@link LinkInfo}
		 * 
		 * @return the link in this {@link LinkInfo}
		 */
		public Link getLink() {
			return _link;
		}

		/** The output energy of this link. */
		private Float	_outputEnergy	= null;

		/**
		 * Returns the output energy in this {@link LinkInfo}
		 * 
		 * @return the output energy in this {@link LinkInfo}
		 */
		public Float getOutputEnergy() {
			return _outputEnergy;
		}

		/**
		 * Sets the output energy in this {@link LinkInfo};
		 * 
		 * @param outputEnergy
		 *            the output energy to set
		 */
		public void setOutputEnergy(Float outputEnergy) {
			_outputEnergy = outputEnergy;
		}

		/**
		 * Creates new information about a link.
		 * 
		 * @param link
		 *            the link
		 */
		private LinkInfo(final Link link) {
			super();
			_link = link;
		}
	}

	/** base xml format for abstract networks */
	protected static abstract class AbstractXmlFormat<T extends AbstractNetwork> extends AbstractXIdObject.AbstractXmlFormat<T> {
		protected static final Element<Node>	NODE_ELEMENT	= XmlUnit.<Node,NodeImpl>newElement("node", NodeImpl.class);
		protected static final Element<Link>	LINK_ELEMENT	= XmlUnit.<Link,LinkImpl>newElement("link", LinkImpl.class);

		protected AbstractXmlFormat(Class<T> cls) {
			super(cls);
			addRecognized(NODE_ELEMENT);
			addRecognized(LINK_ELEMENT);
		}

		@Override
		protected void writeElements(T obj, OutputElement xml) throws XMLStreamException {
			List<Node> nodes = new ArrayList<Node>(obj._nodes.size());
			List<Link> links = new ArrayList<Link>(obj._links.size());
			for (NodeInfo node : obj._nodes.values()) {
				nodes.add(node.getNode());
			}
			for (LinkInfo link : obj._links.values()) {
				links.add(link.getLink());
			}
			Collections.sort(nodes);
			Collections.sort(links);
			for (Node node : nodes) {
				NODE_ELEMENT.write(xml, node);
			}
			for (Link link : links) {
				LINK_ELEMENT.write(xml, link);
			}
		}
	}
}
