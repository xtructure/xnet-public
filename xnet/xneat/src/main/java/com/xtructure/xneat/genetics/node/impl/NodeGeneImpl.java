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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * {@link NodeGeneImpl} implements the {@link NodeGene} interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public class NodeGeneImpl extends AbstractNodeGene {
	/** */
	public static final XmlFormat<NodeGeneImpl>	XML_FORMAT	= new GeneXmlFormat();
	/** */
	public static final XmlBinding				XML_BINDING	= new XmlBinding(NodeGeneImpl.class);

	/**
	 * Creates a new {@link NodeGeneImpl} with the given configuration
	 * 
	 * @param idNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link NodeGeneImpl}
	 * @param nodeType
	 *            the {@link NodeType} of the new {@link NodeGeneImpl}
	 * @param configuration
	 *            the {@link NodeGeneConfiguration} of the new
	 *            {@link NodeGeneImpl}
	 */
	public NodeGeneImpl(int idNumber, NodeType nodeType, NodeGeneConfiguration configuration) {
		super(idNumber, nodeType, configuration);
	}

	/**
	 * Creates a new {@link NodeGeneImpl} that's a copy of the given
	 * {@link NodeGene}
	 * 
	 * @param idNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link NodeGeneImpl}
	 * @param nodeGene
	 *            the {@link NodeGene} to copy
	 */
	public NodeGeneImpl(int idNumber, NodeGene nodeGene) {
		super(idNumber, nodeGene);
	}

	/**
	 * For xml.
	 * 
	 * @param id
	 *            {@link XId} for the new {@link NodeGeneImpl}
	 * @param innovation
	 *            the {@link Innovation} for the new {@link NodeGeneImpl}
	 * @param nodeType
	 *            the {@link NodeType} of the new {@link NodeGeneImpl}
	 * @param configuration
	 *            the {@link NodeGeneConfiguration} for the new
	 *            {@link NodeGeneImpl}
	 */
	private NodeGeneImpl(XId id, Innovation innovation, NodeType nodeType, NodeGeneConfiguration configuration) {
		super(id, innovation, nodeType, configuration);
	}

	/**
	 * Returns the value of the activation for this {@link NodeGeneImpl}
	 * .
	 * 
	 * @return the value of the activation for this {@link NodeGeneImpl}
	 *         .
	 */
	public double getActivation() {
		return (Double) getParameter(NodeGene.ACTIVATION_ID);
	}

	/**
	 * Sets the value of the activation for this {@link NodeGeneImpl}.
	 * 
	 * @param activation
	 *            the value to set
	 * @return the actual new value of the activation for this
	 *         {@link NodeGeneImpl}.
	 */
	public double setActivation(double activation) {
		return setParameter(NodeGene.ACTIVATION_ID, activation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#validate()
	 */
	@Override
	public void validate() throws IllegalStateException {
		// nothing
	}

	private static final class GeneXmlFormat extends AbstractXmlFormat<NodeGeneImpl> {
		private static final Attribute<Double>	ACTIVATION_ATTRIBUTE	= XmlUnit.newAttribute("activation", Double.class);

		private GeneXmlFormat() {
			super(NodeGeneImpl.class);
			addRecognized(ACTIVATION_ATTRIBUTE);
		}

		@Override
		protected void writeAttributes(NodeGeneImpl obj, OutputElement xml) throws XMLStreamException {
			super.writeAttributes(obj, xml);
			ACTIVATION_ATTRIBUTE.write(xml, obj.getActivation());
		}

		@Override
		protected NodeGeneImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			Innovation innovation = readAttributes.getValue(INNOVATION_ATTRIBUTE);
			NodeType nodeType = NodeType.parse(readAttributes.getValue(NODE_TYPE_ATTRIBUTE));
			XId configId = readAttributes.getValue(CONFIGURATION_ID_ATTRIBUTE);
			NodeGeneConfiguration configuration = NodeGeneConfiguration.getManager().getObject(configId);
			validateArg(String.format("NodeGeneConfiguration with id %s must exist", configId), configuration, isNotNull());
			NodeGeneImpl node = new NodeGeneImpl(id, innovation, nodeType, configuration);
			Double activation = readAttributes.getValue(ACTIVATION_ATTRIBUTE);
			node.setActivation(activation);
			ValueMap map = readElements.getValue(ATTRIBUTES_ELEMENT);
			if (map != null) {
				node.getAttributes().setAll(map);
			}
			return node;
		}
	}
}
