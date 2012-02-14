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
package com.xtructure.xneat.genetics.impl;

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xtructure.xevolution.genetics.impl.AbstractGeneticsObject;
import com.xtructure.xneat.genetics.Gene;
import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * {@link AbstractGene} implements the {@link Gene} interface and provides a
 * base {@link XMLFormat}.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractGene extends AbstractGeneticsObject implements Gene {
	/** the {@link Innovation} instance for this {@link Gene} */
	private final Innovation		innovation;
	/** the {@link XConfiguration} of this {@link Gene} */
	private final XConfiguration	configuration;
	/** the {@link FieldMap} of this {@link Gene} */
	private final FieldMap			fields;

	/**
	 * Creates a new {@link AbstractGene} using the given configuration
	 * 
	 * @param id
	 *            the {@link XId} of the new {@link AbstractGene}
	 * @param innovation
	 *            the {@link Innovation} of the new {@link AbstractGene}
	 * @param configuration
	 *            the {@link XConfiguration} of the new {@link AbstractGene}
	 */
	public AbstractGene(XId id, Innovation innovation, XConfiguration configuration) {
		super(id);
		validateArg("configuration", configuration, isNotNull());
		this.innovation = innovation;
		this.configuration = configuration;
		this.fields = configuration.newFieldMap();
	}

	/**
	 * Creates a new {@link AbstractGene} that's a copy of the given gene.
	 * 
	 * @param id
	 *            the {@link XId} of the new {@link AbstractGene}
	 * @param innovation
	 *            the {@link Innovation} of the new {@link AbstractGene}
	 * @param gene
	 *            the {@link Gene} to copy
	 */
	public AbstractGene(XId id, Innovation innovation, Gene gene) {
		this(id, innovation, gene.getConfiguration());
		getFieldMap().setAll(gene.getFieldMap());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.Gene#getInnovation()
	 */
	@Override
	public Innovation getInnovation() {
		getLogger().trace("begin %s.getInnovation()", getClass().getSimpleName());
		getLogger().trace("will return: %s", innovation);
		getLogger().trace("end %s.getInnovation()", getClass().getSimpleName());
		return innovation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.Gene#getConfiguration()
	 */
	@Override
	public XConfiguration getConfiguration() {
		getLogger().trace("begin %s.getConfiguration()", getClass().getSimpleName());
		getLogger().trace("will return: %s", configuration);
		getLogger().trace("end %s.getConfiguration()", getClass().getSimpleName());
		return configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.genetics.Gene#getFieldMap()
	 */
	@Override
	public FieldMap getFieldMap() {
		getLogger().trace("begin %s.getFieldMap()", getClass().getSimpleName());
		getLogger().trace("will return: %s", fields);
		getLogger().trace("end %s.getFieldMap()", getClass().getSimpleName());
		return fields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xneat.genetics.Gene#getParameter(com.xtructure.xutil.id
	 * .XId)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V> V getParameter(XId parameterId) {
		getLogger().trace("begin %s.getParameter()", getClass().getSimpleName());
		V rVal = (V) fields.get(parameterId);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getParameter()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xneat.genetics.Gene#setParameter(com.xtructure.xutil.id
	 * .XId, java.lang.Object)
	 */
	@Override
	public <V> V setParameter(XId parameterId, V value) {
		getLogger().trace("begin %s.setParameter()", getClass().getSimpleName());
		V rVal = fields.set(parameterId, value);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.setParameter()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xutil.id.AbstractXIdObject#compareTo(com.xtructure.xutil
	 * .id.XIdObject)
	 */
	@Override
	public int compareTo(XIdObject that) {
		if (that != null && that instanceof Gene) {
			return new CompareToBuilder()//
					.append(this.getInnovation(), ((Gene) that).getInnovation())//
					.toComparison();
		}
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)//
				.append("id", getId())//
				.append("inn", getInnovation())//
				.append("fields", getFieldMap())//
				.toString();
	}

	/** xml format for {@link AbstractGene} */
	protected static abstract class AbstractXmlFormat<G extends AbstractGene> extends AbstractGeneticsObject.AbstractXmlFormat<G> {
		protected static final Attribute<Innovation>	INNOVATION_ATTRIBUTE		= XmlUnit.newAttribute("innovation", Innovation.class);
		protected static final Attribute<XId>			CONFIGURATION_ID_ATTRIBUTE	= XmlUnit.newAttribute("configurationId", XId.class);

		protected AbstractXmlFormat(Class<G> cls) {
			super(cls);
			addRecognized(INNOVATION_ATTRIBUTE);
			addRecognized(CONFIGURATION_ID_ATTRIBUTE);
		}

		@Override
		protected void writeAttributes(G obj, OutputElement xml) throws XMLStreamException {
			super.writeAttributes(obj, xml);
			INNOVATION_ATTRIBUTE.write(xml, obj.getInnovation());
			CONFIGURATION_ID_ATTRIBUTE.write(xml, obj.getConfiguration().getId());
		}
	}
}
