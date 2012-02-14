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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * {@link GeneMap} is a collection of {@link Gene}s that keeps track of several
 * properties particular to the collection. It maintains:
 * <ol>
 * <li>a mapping to genes from their ids.
 * <li>a mapping from node ids to the set of links incident on it (both incoming
 * and outgoing)
 * <li>a mapping from node ids to the set of nodes connected to it (both
 * incoming and outgoing)
 * </ol>
 * as well as other simple statistics, such as link and node count.
 * 
 * @author Luis Guimbarda
 * 
 */
public class GeneMap implements Collection<Gene> {
	/** */
	public static final XmlFormat<GeneMap>	XML_FORMAT		= new GeneMapXmlFormat();
	/** {@link XLogger} for {@link GeneMap} */
	private static final XLogger			LOGGER			= XLogger.getInstance(GeneMap.class);
	/** gene map */
	private final Map<XId, Gene>			geneMap;
	/** maps a node's id to the set of its incoming links */
	private final Map<XId, Set<XId>>		incomingLinksMap;
	/** maps a node's id to the set of its outgoing links */
	private final Map<XId, Set<XId>>		outgoingLinksMap;
	/** maps a node's id to the set of nodes linked to it */
	private final Map<XId, Set<XId>>		incomingNodesMap;
	/** maps a node's id to the set of nodes its linked to */
	private final Map<XId, Set<XId>>		outgoingNodesMap;
	/** id number for the next link gene to be added to this genome */
	private int								linkIdNumber	= 0;
	/** id number for the next node gene to be added to this genome */
	private int								nodeIdNumber	= 0;
	/** the number of links in this gene map */
	private int								linkCount		= 0;
	/** the number of nodes in this gene map */
	private int								nodeCount		= 0;

	/**
	 * Creates a new, empty {@link GeneMap}
	 */
	public GeneMap() {
		this.geneMap = new HashMap<XId, Gene>();
		this.incomingLinksMap = new HashMap<XId, Set<XId>>();
		this.outgoingLinksMap = new HashMap<XId, Set<XId>>();
		this.incomingNodesMap = new HashMap<XId, Set<XId>>();
		this.outgoingNodesMap = new HashMap<XId, Set<XId>>();
	}

	/**
	 * Creates a new {@link GeneMap}, adding the given collection of
	 * {@link Gene}s.
	 * 
	 * @param genes
	 *            the {@link Gene}s to add to this new {@link GeneMap}
	 */
	public GeneMap(Collection<Gene> genes) {
		this();
		addAll(genes);
	}

	/**
	 * Creates a new {@link GeneMap}, adding the given collections of
	 * {@link NodeGene}s and {@link LinkGene}s.
	 * 
	 * @param nodes
	 *            the {@link NodeGene}s to add to this new {@link GeneMap}
	 * @param links
	 *            the {@link LinkGene}s to add to this new {@link GeneMap}
	 */
	public GeneMap(Collection<NodeGene> nodes, Collection<LinkGene> links) {
		this();
		addAll(nodes);
		addAll(links);
	}

	/**
	 * Returns the next available instance id number for {@link LinkGene}s in
	 * this {@link GeneMap}.
	 * 
	 * @return the next available instance id number for {@link LinkGene}s in
	 *         this {@link GeneMap}.
	 */
	public int getLinkIdNumber() {
		LOGGER.trace("begin %s.getLinkIdNumber()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", linkIdNumber);
		LOGGER.trace("end %s.getLinkIdNumber()", getClass().getSimpleName());
		return linkIdNumber;
	}

	/**
	 * Returns the next available instance id number for {@link NodeGene}s in
	 * this {@link GeneMap}.
	 * 
	 * @return the next available instance id number for {@link NodeGene}s in
	 *         this {@link GeneMap}.
	 */
	public int getNodeIdNumber() {
		LOGGER.trace("begin %s.getNodeIdNumber()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", nodeIdNumber);
		LOGGER.trace("end %s.getNodeIdNumber()", getClass().getSimpleName());
		return nodeIdNumber;
	}

	/**
	 * Returns the number of {@link LinkGene}s in this {@link GeneMap}
	 * 
	 * @return the number of {@link LinkGene}s in this {@link GeneMap}
	 */
	public int getLinkCount() {
		LOGGER.trace("begin %s.getLinkCount()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", linkCount);
		LOGGER.trace("end %s.getLinkCount()", getClass().getSimpleName());
		return linkCount;
	}

	/**
	 * Returns the number of {@link NodeGene}s in this {@link GeneMap}
	 * 
	 * @return the number of {@link NodeGene}s in this {@link GeneMap}
	 */
	public int getNodeCount() {
		LOGGER.trace("begin %s.getNodeCount()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", nodeCount);
		LOGGER.trace("end %s.getNodeCount()", getClass().getSimpleName());
		return nodeCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(Gene gene) {
		LOGGER.trace("begin %s.add(%s)", getClass().getSimpleName(), gene);
		validateArg("gene", gene, isNotNull());
		boolean changed = false;
		if (gene instanceof LinkGene) {
			LinkGene linkGene = (LinkGene) gene;
			if (geneMap.containsKey(linkGene.getId())) {
				remove(geneMap.get(linkGene.getId()));
			}
			outgoingLinksMap.get(linkGene.getSourceId()).add(linkGene.getId());
			outgoingNodesMap.get(linkGene.getSourceId()).add(linkGene.getTargetId());
			incomingLinksMap.get(linkGene.getTargetId()).add(linkGene.getId());
			incomingNodesMap.get(linkGene.getTargetId()).add(linkGene.getSourceId());
			linkIdNumber = Math.max(linkIdNumber, linkGene.getId().getInstanceNum() + 1);
			linkCount++;
			geneMap.put(linkGene.getId(), linkGene);
			changed = true;
		} else if (gene instanceof NodeGene) {
			NodeGene nodeGene = (NodeGene) gene;
			if (!geneMap.containsKey(nodeGene.getId())) {
				outgoingLinksMap.put(nodeGene.getId(), new HashSet<XId>());
				outgoingNodesMap.put(nodeGene.getId(), new HashSet<XId>());
				incomingLinksMap.put(nodeGene.getId(), new HashSet<XId>());
				incomingNodesMap.put(nodeGene.getId(), new HashSet<XId>());
				nodeIdNumber = Math.max(nodeIdNumber, nodeGene.getId().getInstanceNum() + 1);
				nodeCount++;
			}
			geneMap.put(nodeGene.getId(), nodeGene);
			changed = true;
		}
		LOGGER.trace("will return: %s", changed);
		LOGGER.trace("end %s.add()", getClass().getSimpleName());
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Gene> c) {
		LOGGER.trace("begin %s.addAll(%s)", getClass().getSimpleName(), c);
		boolean changed = false;
		LinkedList<Gene> links = new LinkedList<Gene>();
		// add nodes first
		for (Gene gene : c) {
			if (gene instanceof LinkGene) {
				links.add(gene);
			} else {
				changed |= add(gene);
			}
		}
		// then add links
		for (Gene gene : links) {
			changed |= add(gene);
		}
		LOGGER.trace("will return: %s", changed);
		LOGGER.trace("end %s.add()", getClass().getSimpleName());
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		LOGGER.trace("begin %s.clear()", getClass().getSimpleName());
		geneMap.clear();
		LOGGER.trace("end %s.clear()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		LOGGER.trace("begin %s.contains(%s)", getClass().getSimpleName(), o);
		boolean rVal = geneMap.values().contains(o);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.contains()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		LOGGER.trace("begin %s.containsAll(%s)", getClass().getSimpleName(), c);
		boolean rVal = geneMap.values().containsAll(c);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.containsAll()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		LOGGER.trace("begin %s.isEmpty()", getClass().getSimpleName());
		boolean rVal = geneMap.isEmpty();
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.isEmpty()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<Gene> iterator() {
		LOGGER.trace("begin %s.iterator()", getClass().getSimpleName());
		Iterator<Gene> rVal = geneMap.values().iterator();
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.iterator()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object gene) {
		LOGGER.trace("begin %s.remove(%s)", getClass().getSimpleName(), gene);
		boolean changed = false;
		if (gene instanceof LinkGene) {
			LinkGene linkGene = (LinkGene) gene;
			if (geneMap.containsKey(linkGene.getId())) {
				outgoingLinksMap.get(linkGene.getSourceId()).remove(linkGene.getId());
				outgoingNodesMap.get(linkGene.getSourceId()).remove(linkGene.getTargetId());
				incomingLinksMap.get(linkGene.getTargetId()).remove(linkGene.getId());
				incomingNodesMap.get(linkGene.getTargetId()).remove(linkGene.getSourceId());
				linkCount--;
				geneMap.remove(linkGene.getId());
				changed = true;
			}
		} else if (gene instanceof NodeGene) {
			NodeGene nodeGene = (NodeGene) gene;
			if (geneMap.containsKey(nodeGene.getId())) {
				List<XId> linksToRemove = new LinkedList<XId>();
				linksToRemove.addAll(outgoingLinksMap.get(nodeGene.getId()));
				linksToRemove.addAll(incomingLinksMap.get(nodeGene.getId()));
				linkCount -= linksToRemove.size();
				for (XId id : linksToRemove) {
					remove(geneMap.get(id));
				}
				outgoingLinksMap.remove(nodeGene.getId());
				outgoingNodesMap.remove(nodeGene.getId());
				incomingLinksMap.remove(nodeGene.getId());
				incomingNodesMap.remove(nodeGene.getId());
				nodeCount--;
				geneMap.remove(nodeGene.getId());
				changed = true;
			}
		}
		LOGGER.trace("will return: %s", changed);
		LOGGER.trace("end %s.remove()", getClass().getSimpleName());
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		LOGGER.trace("begin %s.removeAll(%s)", getClass().getSimpleName(), c);
		boolean rVal = geneMap.values().removeAll(c);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.removeAll()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		LOGGER.trace("begin %s.retainAll(%s)", getClass().getSimpleName(), c);
		boolean rVal = geneMap.values().retainAll(c);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.retainAll()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		LOGGER.trace("begin %s.retainAll()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", geneMap.size());
		LOGGER.trace("end %s.retainAll()", getClass().getSimpleName());
		return geneMap.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		LOGGER.trace("begin %s.toArray()", getClass().getSimpleName());
		Object[] rVal = geneMap.values().toArray();
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.toArray()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		LOGGER.trace("begin %s.toArray(%s)", getClass().getSimpleName(), a);
		T[] rVal = geneMap.values().toArray(a);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.toArray()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Returns the {@link Gene} in this {@link GeneMap} with the given id
	 * 
	 * @param id
	 *            the {@link XId} of the requested gene
	 * @return the requested gene, or null if no gene has the given id
	 */
	public Gene get(XId id) {
		LOGGER.trace("begin %s.get(%s)", getClass().getSimpleName(), id);
		Gene rVal = geneMap.get(id);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.get()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Removes the {@link Gene} in this {@link GeneMap} with the given id
	 * 
	 * @param id
	 *            the {@link XId} of the gene to remove
	 * @return true if an element was removed as a result of this call
	 */
	public boolean remove(XId id) {
		LOGGER.trace("begin %s.remove(%s)", getClass().getSimpleName(), id);
		boolean rVal = remove(get(id));
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.remove()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Returns a list of {@link LinkGene}s in this {@link GeneMap}, sorted by
	 * {@link XId}
	 * 
	 * @return a list of {@link LinkGene}s in this {@link GeneMap}
	 */
	public List<LinkGene> getLinks() {
		LOGGER.trace("begin %s.getLinks()", getClass().getSimpleName());
		List<LinkGene> links = new ArrayList<LinkGene>(linkCount);
		for (Gene gene : geneMap.values()) {
			if (gene instanceof LinkGene) {
				links.add((LinkGene) gene);
			}
		}
		Collections.sort(links);
		LOGGER.trace("will return: %s", links);
		LOGGER.trace("end %s.getLinks()", getClass().getSimpleName());
		return links;
	}

	/**
	 * Returns a list of {@link NodeGene}s in this {@link GeneMap}, sorted by
	 * {@link XId}
	 * 
	 * @return a list of {@link NodeGene}s in this {@link GeneMap}
	 */
	public List<NodeGene> getNodes() {
		LOGGER.trace("begin %s.getNodes()", getClass().getSimpleName());
		List<NodeGene> nodes = new ArrayList<NodeGene>(nodeCount);
		for (Gene gene : geneMap.values()) {
			if (gene instanceof NodeGene) {
				nodes.add((NodeGene) gene);
			}
		}
		Collections.sort(nodes);
		LOGGER.trace("will return: %s", nodes);
		LOGGER.trace("end %s.getNodes()", getClass().getSimpleName());
		return nodes;
	}

	/**
	 * For every {@link LinkGene} in this {@link GeneMap} with the
	 * {@link NodeGene} with the given id at its source, returns the set of ids
	 * of target {@link NodeGene}s of those links.
	 * 
	 * @param id
	 *            the {@link XId} of the source node gene
	 * @return the set of ids of target node genes with that source
	 */
	public Set<XId> getOutgoingNodeIds(XId id) {
		LOGGER.trace("begin %s.getOutgoingNodeIds(%s)", getClass().getSimpleName(), id);
		Set<XId> rVal = outgoingNodesMap.get(id);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getOutgoingNodeIds()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * For every {@link LinkGene} in this {@link GeneMap} with the
	 * {@link NodeGene} with the given id at its target, returns the set of ids
	 * of source {@link NodeGene}s of those links.
	 * 
	 * @param id
	 *            the {@link XId} of the target node gene
	 * @return the set of ids of source node genes with that target
	 */
	public Set<XId> getIncomingNodeIds(XId id) {
		LOGGER.trace("begin %s.getIncomingNodeIds(%s)", getClass().getSimpleName(), id);
		Set<XId> rVal = incomingNodesMap.get(id);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getIncomingNodeIds()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Returns the set of ids of {@link LinkGene}s in this {@link GeneMap} that
	 * have the {@link NodeGene} with the given id as its source.
	 * 
	 * @param id
	 *            the {@link XId} of the source node gene
	 * @return the set of ids of link genes with that source
	 */
	public Set<XId> getOutgoingLinkIds(XId id) {
		LOGGER.trace("begin %s.getOutgoingLinkIds(%s)", getClass().getSimpleName(), id);
		Set<XId> rVal = outgoingLinksMap.get(id);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getOutgoingLinkIds()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Returns the set of ids of {@link LinkGene}s in this {@link GeneMap} that
	 * have the NodeGene with the given id as its target.
	 * 
	 * @param id
	 *            the XId of the target node gene
	 * @return the set of ids of link genes with that target
	 */
	public Set<XId> getIncomingLinkIds(XId id) {
		LOGGER.trace("begin %s.getIncomingLinkIds(%s)", getClass().getSimpleName(), id);
		Set<XId> rVal = incomingLinksMap.get(id);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getIncomingLinkIds()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		for (Gene g : getNodes()) {
			tsb.append(g);
		}
		for (Gene g : getLinks()) {
			tsb.append(g);
		}
		return tsb.toString();
	}

	/** xml format for {@link GeneMap} */
	private static final class GeneMapXmlFormat extends XmlFormat<GeneMap> {
		@SuppressWarnings("unchecked")
		private static final Element<List<NodeGene>>	NODE_GENE_ELEMENTS	= XmlUnit.newElement("nodes", ArrayList.class);
		@SuppressWarnings("unchecked")
		private static final Element<List<LinkGene>>	LINK_GENE_ELEMENTS	= XmlUnit.newElement("links", ArrayList.class);

		private GeneMapXmlFormat() {
			super(GeneMap.class);
			addRecognized(NODE_GENE_ELEMENTS);
			addRecognized(LINK_GENE_ELEMENTS);
		}

		@Override
		protected GeneMap newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			ArrayList<NodeGene> nodes = new ArrayList<NodeGene>();
			ArrayList<LinkGene> links = new ArrayList<LinkGene>();
			for (NodeGene node : readElements.getValue(NODE_GENE_ELEMENTS)) {
				nodes.add(node);
			}
			for (LinkGene link : readElements.getValue(LINK_GENE_ELEMENTS)) {
				links.add(link);
			}
			return new GeneMap(nodes, links);
		}

		@Override
		protected void writeAttributes(GeneMap obj, OutputElement xml) throws XMLStreamException {
			// nothing
		}

		@Override
		protected void writeElements(GeneMap obj, OutputElement xml) throws XMLStreamException {
			NODE_GENE_ELEMENTS.write(xml, obj.getNodes());
			LINK_GENE_ELEMENTS.write(xml, obj.getLinks());
		}
	}
}
