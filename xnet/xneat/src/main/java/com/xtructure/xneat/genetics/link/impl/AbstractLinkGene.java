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

import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.impl.AbstractGene;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * {@link AbstractLinkGene} implements the {@link LinkGene} interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractLinkGene extends AbstractGene implements LinkGene {
	/** the base {@link XId} for {@link LinkGene}s */
	public static final XId	LINK_BASE_ID	= XId.newId("LinkGene");
	/** the {@link XId} of the source node of this {@link LinkGene} */
	private final XId		sourceId;
	/** the {@link XId} of the target node of this {@link LinkGene} */
	private final XId		targetId;

	/**
	 * Creates a new {@link AbstractLinkGene}
	 * 
	 * @param idNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link AbstractLinkGene}
	 * @param sourceId
	 *            the {@link XId} of the source node of the new
	 *            {@link AbstractLinkGene}
	 * @param targetId
	 *            the {@link XId} of the target node of the new
	 *            {@link AbstractLinkGene}
	 * @param configuration
	 *            the {@link XConfiguration} of the new {@link AbstractLinkGene}
	 */
	public AbstractLinkGene(int idNumber, XId sourceId, XId targetId, XConfiguration configuration) {
		super(LINK_BASE_ID.createChild(idNumber), Innovation.generate(sourceId.getInstanceNum(), targetId.getInstanceNum()), configuration);
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	/**
	 * Creates a new {@link AbstractLinkGene} that a copy of the given link
	 * 
	 * @param idNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link AbstractLinkGene}
	 * @param linkGene
	 *            the {@link LinkGene} to copy
	 */
	public AbstractLinkGene(int idNumber, LinkGene linkGene) {
		super(LINK_BASE_ID.createChild(idNumber), linkGene.getInnovation(), linkGene);
		this.sourceId = linkGene.getSourceId();
		this.targetId = linkGene.getTargetId();
	}

	/**
	 * For xml.
	 * 
	 * @param id
	 *            {@link XId} of the new {@link AbstractLinkGene}
	 * @param sourceId
	 *            {@link XId} of the source node of the new
	 *            {@link AbstractLinkGene}
	 * @param targetId
	 *            {@link XId} of the target node of the new
	 *            {@link AbstractLinkGene}
	 * @param innovation
	 *            {@link Innovation} of the new {@link AbstractLinkGene}
	 * @param configuration
	 *            {@link XConfiguration} of the new {@link AbstractLinkGene}
	 */
	protected AbstractLinkGene(XId id, XId sourceId, XId targetId, Innovation innovation, XConfiguration configuration) {
		super(id, innovation, configuration);
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#getBaseId()
	 */
	@Override
	public XId getBaseId() {
		getLogger().trace("begin %s.getBaseId()", getClass().getSimpleName());
		getLogger().trace("will return: %s", LINK_BASE_ID);
		getLogger().trace("end %s.getBaseId()", getClass().getSimpleName());
		return LINK_BASE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.LinkGene#getSourceId()
	 */
	@Override
	public XId getSourceId() {
		getLogger().trace("begin %s.getSourceId()", getClass().getSimpleName());
		getLogger().trace("will return: %s", sourceId);
		getLogger().trace("end %s.getSourceId()", getClass().getSimpleName());
		return sourceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.LinkGene#getTargetId()
	 */
	@Override
	public XId getTargetId() {
		getLogger().trace("begin %s.getTargetId()", getClass().getSimpleName());
		getLogger().trace("will return: %s", targetId);
		getLogger().trace("end %s.getTargetId()", getClass().getSimpleName());
		return targetId;
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
				.append("source", getSourceId())//
				.append("target", getTargetId())//
				.append("inn", getInnovation())//
				.append("fields", getFieldMap())//
				.toString();
	}

	/** xml format for {@link AbstractLinkGene} */
	protected static abstract class AbstractXmlFormat<G extends AbstractLinkGene> extends AbstractGene.AbstractXmlFormat<G> {
		protected static final Attribute<XId>	SOURCE_ATTRIBUTE	= XmlUnit.newAttribute("sourceId", XId.class);
		protected static final Attribute<XId>	TARGET_ATTRIBUTE	= XmlUnit.newAttribute("targetId", XId.class);

		protected AbstractXmlFormat(Class<G> cls) {
			super(cls);
			addRecognized(SOURCE_ATTRIBUTE);
			addRecognized(TARGET_ATTRIBUTE);
		}

		@Override
		protected void writeAttributes(G obj, OutputElement xml) throws XMLStreamException {
			super.writeAttributes(obj, xml);
			SOURCE_ATTRIBUTE.write(xml, obj.getSourceId());
			TARGET_ATTRIBUTE.write(xml, obj.getTargetId());
		}
	}
}
