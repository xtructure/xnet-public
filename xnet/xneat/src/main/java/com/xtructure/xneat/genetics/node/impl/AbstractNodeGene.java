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
package com.xtructure.xneat.genetics.node.impl;

import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.impl.AbstractGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * {@link AbstractNodeGene} implements the {@link NodeGene} interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractNodeGene extends AbstractGene implements NodeGene {
	/** the base {@link XId} for {@link NodeGene}s */
	public static final XId	NODE_BASE_ID	= XId.newId("NodeGene");
	/** the {@link NodeType} of this {@link NodeGene} */
	private final NodeType	nodeType;

	/**
	 * Creates a new {@link AbstractNodeGene}
	 * 
	 * @param idNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link AbstractNodeGene}
	 * @param nodeType
	 *            the {@link NodeType} of the new {@link AbstractNodeGene}
	 * @param configuration
	 *            the {@link XConfiguration} of the new {@link AbstractNodeGene}
	 */
	public AbstractNodeGene(int idNumber, NodeType nodeType, XConfiguration configuration) {
		super(NODE_BASE_ID.createChild(idNumber), Innovation.generate(idNumber), configuration);
		this.nodeType = nodeType;
	}

	/**
	 * Creates a new {@link AbstractNodeGene} that's a copy of the given
	 * {@link NodeGene}
	 * 
	 * @param idNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link AbstractNodeGene}
	 * @param nodeGene
	 *            the {@link NodeGene} to copy
	 */
	public AbstractNodeGene(int idNumber, NodeGene nodeGene) {
		super(NODE_BASE_ID.createChild(idNumber), Innovation.generate(idNumber), nodeGene);
		this.nodeType = nodeGene.getNodeType();
	}

	/**
	 * For xml.
	 * 
	 * @param id
	 *            the {@link XId} for the new {@link AbstractNodeGene}
	 * @param innovation
	 *            the {@link Innovation} for the new {@link AbstractNodeGene}
	 * @param nodeType
	 *            the {@link NodeType} for the new {@link AbstractNodeGene}
	 * @param configuration
	 *            the {@link XConfiguration} for the new
	 *            {@link AbstractNodeGene}
	 */
	protected AbstractNodeGene(XId id, Innovation innovation, NodeType nodeType, XConfiguration configuration) {
		super(id, innovation, configuration);
		this.nodeType = nodeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#getBaseId()
	 */
	@Override
	public XId getBaseId() {
		getLogger().trace("begin %s.getBaseId()", getClass().getSimpleName());
		getLogger().trace("will return: %s", NODE_BASE_ID);
		getLogger().trace("end %s.getBaseId()", getClass().getSimpleName());
		return NODE_BASE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.NodeGene#getNodeType()
	 */
	@Override
	public NodeType getNodeType() {
		getLogger().trace("begin %s.getNodeType()", getClass().getSimpleName());
		getLogger().trace("will return: %s", nodeType);
		getLogger().trace("end %s.getNodeType()", getClass().getSimpleName());
		return nodeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.neat.genetics.impl.AbstractGene#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)//
				.append("id", getId())//
				.append("nodeType", getNodeType())//
				.append("fields", getFieldMap())//
				.toString();
	}

	/** xml format for {@link AbstractNodeGene} */
	protected static abstract class AbstractXmlFormat<G extends AbstractNodeGene> extends AbstractGene.AbstractXmlFormat<G> {
		protected static final Attribute<String>	NODE_TYPE_ATTRIBUTE	= XmlUnit.newAttribute("nodeType", String.class);

		protected AbstractXmlFormat(Class<G> cls) {
			super(cls);
			addRecognized(NODE_TYPE_ATTRIBUTE);
		}

		@Override
		protected void writeAttributes(G obj, OutputElement xml) throws XMLStreamException {
			super.writeAttributes(obj, xml);
			NODE_TYPE_ATTRIBUTE.write(xml, obj.getNodeType().toString());
		}
	}
}
