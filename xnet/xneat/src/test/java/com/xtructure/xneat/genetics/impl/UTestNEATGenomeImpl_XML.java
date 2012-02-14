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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.link.impl.LinkGeneImpl;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.genetics.node.impl.NodeGeneImpl;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xneat" })
public class UTestNEATGenomeImpl_XML extends AbstractXmlFormatTest<NEATGenomeImpl> {
	private static final Object[][]	INSTANCES;
	static {
		NodeGene node = new NodeGeneImpl(0, NodeType.HIDDEN, NodeGeneConfiguration.builder(null).newInstance());
		LinkGene link = new LinkGeneImpl(0, node.getId(), node.getId(), LinkGeneConfiguration.builder(null).newInstance());
		GeneMap data = new GeneMap();
		data.add(node);
		data.add(link);
		NEATGenome<GeneMap> genome1 = new NEATGenomeImpl(0, data);
		genome1.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		genome1.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, 2.0);
		NEATGenome<GeneMap> genome2 = new NEATGenomeImpl(0, genome1);
		genome2.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		genome2.setAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID, 2.0);
		genome2.setSpeciesId(XId.newId("species", 1));
		INSTANCES = TestUtils.createData(genome1, genome2);
	}

	public UTestNEATGenomeImpl_XML() {
		super(NEATGenomeImpl.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(NEATGenomeImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s id=\"%s\">\n", NEATGenomeImpl.class.getSimpleName(), t.getId()));
		sb.append(INDENT + "<attributes>\n");
		String[] lines = XmlWriter.write(t.getAttributes()).split("\n");
		for (int i = 2; i + 1 < lines.length; i++) {
			sb.append(INDENT + lines[i]).append("\n");
		}
		sb.append(INDENT + "</attributes>\n");
		sb.append(INDENT + "<data>\n");
		lines = XmlWriter.write(t.getData()).split("\n");
		for (int i = 2; i < lines.length - 1; i++) {
			sb.append(INDENT + lines[i]).append("\n");
		}
		sb.append(INDENT + "</data>\n");
		sb.append(String.format("</%s>", NEATGenomeImpl.class.getSimpleName()));
		return sb.toString();
	}

	@DataProvider
	@Override
	protected Object[][] instances() {
		return INSTANCES;
	}
}
