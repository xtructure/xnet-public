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
package com.xtructure.xnet.demos.oned.art;

import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlReader;

/**
 * A factory for creating ARTGenetics objects.
 * 
 * @param <D>
 *            the generic type
 * @author Luis Guimbarda
 */
public interface ARTGeneticsFactory<D> extends NEATGeneticsFactory<D> {

	/** The Constant RANDOM_NODE_CONFIGURATION. */
	public static final NodeConfiguration RANDOM_NODE_CONFIGURATION = XmlReader
			.read(//
			ARTGeneticsFactory.class
					.getResourceAsStream("default.open.node.config.xml"),//
					NodeConfiguration.XML_BINDING,//
					null);

	/** The Constant RANDOM_LINK_CONFIGURATION. */
	public static final LinkConfiguration RANDOM_LINK_CONFIGURATION = XmlReader
			.read(//
			ARTGeneticsFactory.class
					.getResourceAsStream("default.open.link.config.xml"),//
					LinkConfiguration.XML_BINDING,//
					null);

	/** The Constant LOOSE_NODE_CONFIGURATION. */
	public static final NodeConfiguration LOOSE_NODE_CONFIGURATION = XmlReader
			.read(//
			ARTGeneticsFactory.class
					.getResourceAsStream("default.loose.node.config.xml"),//
					NodeConfiguration.XML_BINDING,//
					null);

	/** The Constant LOOSE_LINK_CONFIGURATION. */
	public static final LinkConfiguration LOOSE_LINK_CONFIGURATION = XmlReader
			.read(//
			ARTGeneticsFactory.class
					.getResourceAsStream("default.loose.link.config.xml"),//
					LinkConfiguration.XML_BINDING,//
					null);

	/** The Constant FIXED_NODE_CONFIGURATION. */
	public static final NodeConfiguration FIXED_NODE_CONFIGURATION = XmlReader
			.read(//
			ARTGeneticsFactory.class
					.getResourceAsStream("default.fixed.node.config.xml"),//
					NodeConfiguration.XML_BINDING,//
					null);

	/** The Constant FIXED_LINK_CONFIGURATION. */
	public static final LinkConfiguration FIXED_LINK_CONFIGURATION = XmlReader
			.read(//
			ARTGeneticsFactory.class
					.getResourceAsStream("default.fixed.link.config.xml"),//
					LinkConfiguration.XML_BINDING,//
					null);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.NEATGeneticsFactory#createLinkGene(int,
	 * com.xtructure.xutil.id.XId, com.xtructure.xutil.id.XId)
	 */
	@Override
	public ARTLinkGene createLinkGene(int idNumber, XId sourceId, XId targetId);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.NEATGeneticsFactory#copyLinkGene(int,
	 * com.xtructure.xneat.genetics.LinkGene)
	 */
	@Override
	public ARTLinkGene copyLinkGene(int idNumber, LinkGene link);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.NEATGeneticsFactory#createNodeGene(int,
	 * com.xtructure.xneat.genetics.NodeType)
	 */
	@Override
	public ARTNodeGene createNodeGene(int idNumber, NodeType nodeType);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.NEATGeneticsFactory#copyNodeGene(int,
	 * com.xtructure.xneat.genetics.NodeGene)
	 */
	@Override
	public ARTNodeGene copyNodeGene(int idNumber, NodeGene nodeGene);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xneat.genetics.NEATGeneticsFactory#getLinkConfiguration()
	 */
	@Override
	public LinkConfiguration getLinkConfiguration();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xneat.genetics.NEATGeneticsFactory#getNodeConfiguration()
	 */
	@Override
	public NodeConfiguration getNodeConfiguration();
}
