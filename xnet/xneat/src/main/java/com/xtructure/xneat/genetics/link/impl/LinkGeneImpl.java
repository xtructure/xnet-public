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
package com.xtructure.xneat.genetics.link.impl;

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * {@link LinkGeneImpl} implements the {@link LinkGene} interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public final class LinkGeneImpl extends AbstractLinkGene {
	/** */
	public static final XmlFormat<LinkGeneImpl>	XML_FORMAT	= new GeneXmlFormat();
	/** */
	public static final XmlBinding				XML_BINDING	= new XmlBinding(LinkGeneImpl.class);

	/**
	 * Creates a new {@link LinkGeneImpl} with the given configuration
	 * 
	 * @param idNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link LinkGeneImpl}
	 * @param sourceId
	 *            the {@link XId} of the source node of the new
	 *            {@link LinkGeneImpl}
	 * @param targetId
	 *            the {@link XId} of the target node of the new
	 *            {@link LinkGeneImpl}
	 * @param configuration
	 *            the {@link LinkGeneConfiguration} of the new
	 *            {@link LinkGeneImpl}
	 */
	public LinkGeneImpl(int idNumber, XId sourceId, XId targetId, LinkGeneConfiguration configuration) {
		super(idNumber, sourceId, targetId, configuration);
	}

	/**
	 * Creates a new {@link LinkGeneImpl} that's a copy of the given link gene
	 * 
	 * @param idNumber
	 *            instance number for the {@link XId} of the new
	 *            {@link LinkGeneImpl}
	 * @param linkGene
	 *            the {@link LinkGene} to copy
	 */
	public LinkGeneImpl(int idNumber, LinkGene linkGene) {
		super(idNumber, linkGene);
	}

	/**
	 * For xml.
	 * 
	 * @param id
	 *            {@link XId} for the new {@link LinkGeneImpl}
	 * @param srcId
	 *            the {@link XId} of the source node for the new
	 *            {@link LinkGeneImpl}
	 * @param tgtId
	 *            the {@link XId} of the target node for the new
	 *            {@link LinkGeneImpl}
	 * @param innovation
	 *            the {@link Innovation} for the new {@link LinkGeneImpl}
	 * @param configuration
	 *            the {@link LinkGeneConfiguration} for the new
	 *            {@link LinkGeneImpl}
	 */
	private LinkGeneImpl(XId id, XId srcId, XId tgtId, Innovation innovation, LinkGeneConfiguration configuration) {
		super(id, srcId, tgtId, innovation, configuration);
	}

	/**
	 * Returns the value of the weight in this {@link LinkGeneImpl}
	 * 
	 * @return the value of the weight in this {@link LinkGeneImpl}
	 */
	public double getWeight() {
		return getParameter(LinkGene.WEIGHT_ID);
	}

	/**
	 * Sets the value of the weight in this {@link LinkGeneImpl}
	 * 
	 * @param weight
	 *            the value to set
	 * @return the previous value of the weight in this {@link LinkGeneImpl}
	 */
	public double setWeight(double weight) {
		return setParameter(LinkGene.WEIGHT_ID, weight);
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

	private static final class GeneXmlFormat extends AbstractXmlFormat<LinkGeneImpl> {
		private static final Attribute<Double>	WEIGHT_ATTRIBUTE	= XmlUnit.newAttribute("weight", Double.class);

		private GeneXmlFormat() {
			super(LinkGeneImpl.class);
			addRecognized(WEIGHT_ATTRIBUTE);
		}

		@Override
		protected void writeAttributes(LinkGeneImpl obj, OutputElement xml) throws XMLStreamException {
			super.writeAttributes(obj, xml);
			WEIGHT_ATTRIBUTE.write(xml, obj.getWeight());
		}

		@Override
		protected LinkGeneImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			XId srcId = readAttributes.getValue(SOURCE_ATTRIBUTE);
			XId tgtId = readAttributes.getValue(TARGET_ATTRIBUTE);
			Innovation innovation = readAttributes.getValue(INNOVATION_ATTRIBUTE);
			XId configId = readAttributes.getValue(CONFIGURATION_ID_ATTRIBUTE);
			LinkGeneConfiguration configuration = LinkGeneConfiguration.getManager().getObject(configId);
			validateArg(String.format("LinkGeneConfiguration with id %s must exist", configId), configuration, isNotNull());
			LinkGeneImpl link = new LinkGeneImpl(id, srcId, tgtId, innovation, configuration);
			Double weight = readAttributes.getValue(WEIGHT_ATTRIBUTE);
			link.setWeight(weight);
			ValueMap map = readElements.getValue(ATTRIBUTES_ELEMENT);
			if (map != null) {
				link.getAttributes().setAll(map);
			}
			return link;
		}
	}
}
