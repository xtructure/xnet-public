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

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xneat.genetics.Gene;
import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;

/**
 * @author Luis Guimbarda
 * 
 */
public class DummyGene extends AbstractGene {
	public DummyGene(XId id, Gene gene) {
		super(id, Innovation.generate(id.getInstanceNum()), gene);
	}

	public DummyGene(XId id, NodeGeneConfiguration configuration) {
		super(id, Innovation.generate(id.getInstanceNum()), configuration);
	}

	@Override
	public XId getBaseId() {
		return null;
	}

	@Override
	public void validate() throws IllegalStateException {}

	static {
		new AbstractXmlFormat<DummyGene>(DummyGene.class) {
			@Override
			protected DummyGene newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				XId id = readAttributes.getValue(ID_ATTRIBUTE);
				XId configId = readAttributes.getValue(CONFIGURATION_ID_ATTRIBUTE);
				NodeGeneConfiguration configuration = NodeGeneConfiguration.getManager().getObject(configId);
				DummyGene gene = new DummyGene(id, configuration);
				ValueMap map = readElements.getValue(ATTRIBUTES_ELEMENT);
				if (map != null) {
					gene.getAttributes().setAll(map);
				}
				return gene;
			}
		};
	}
}
