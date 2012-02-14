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

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

@Test(groups = { "xml:xneat" })
public class UTestGeneMap_XML extends AbstractXmlFormatTest<GeneMap> {
	private static final NodeGeneConfiguration	NODE_CONFIGURATION	= NodeGeneConfiguration.builder(XId.newId("UTestGeneMap_XML", 0)).newInstance();
	private static final LinkGeneConfiguration	LINK_CONFIGURATION	= LinkGeneConfiguration.builder(XId.newId("UTestGeneMap_XML", 1)).newInstance();
	private static final Object[][]				INSTANCES;
	static {
		NodeGene node0 = new NodeGeneImpl(0, NodeType.INPUT, NODE_CONFIGURATION);
		NodeGene node1 = new NodeGeneImpl(1, NodeType.OUTPUT, NODE_CONFIGURATION);
		LinkGene link0 = new LinkGeneImpl(0, node0.getId(), node1.getId(), LINK_CONFIGURATION);
		GeneMap geneMap = new GeneMap(//
				Arrays.asList(node0, node1),//
				Arrays.asList(link0));
		INSTANCES = TestUtils.createData(new GeneMap(), geneMap);
	}

	public UTestGeneMap_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(GeneMap t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s>\n", GeneMap.class.getName()));
		if (t.getNodeCount() == 0) {
			sb.append(INDENT + "<nodes/>\n");
		} else {
			sb.append(INDENT + "<nodes>\n");
			for (NodeGene node : t.getNodes()) {
				String[] lines = XmlWriter.write(node).split("\n");
				for (int i = 1; i < lines.length; i++) {
					sb.append(INDENT + INDENT + lines[i] + "\n");
				}
			}
			sb.append(INDENT + "</nodes>\n");
		}
		if (t.getLinkCount() == 0) {
			sb.append(INDENT + "<links/>\n");
		} else {
			sb.append(INDENT + "<links>\n");
			for (LinkGene link : t.getLinks()) {
				String[] lines = XmlWriter.write(link).split("\n");
				for (int i = 1; i < lines.length; i++) {
					sb.append(INDENT + INDENT + lines[i] + "\n");
				}
			}
			sb.append(INDENT + "</links>\n");
		}
		sb.append(String.format("</%s>", GeneMap.class.getName()));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
