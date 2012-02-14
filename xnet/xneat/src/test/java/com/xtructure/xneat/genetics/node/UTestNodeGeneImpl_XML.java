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
package com.xtructure.xneat.genetics.node;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.genetics.node.impl.NodeGeneImpl;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

@Test(groups = { "xml:xneat" })
public class UTestNodeGeneImpl_XML extends AbstractXmlFormatTest<NodeGeneImpl> {
	private static final Object[][]	INSTANCES;
	static {
		NodeGeneConfiguration config = NodeGeneConfiguration.builder(XId.newId("UTestNodeGeneImpl_XML")).newInstance();
		List<NodeGeneImpl> nodes = new ArrayList<NodeGeneImpl>();
		for (NodeType nodeType : NodeType.values()) {
			nodes.add(new NodeGeneImpl(0, nodeType, config));
		}
		INSTANCES = TestUtils.createData(nodes.toArray());
	}

	public UTestNodeGeneImpl_XML() {
		super(NodeGeneImpl.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(NodeGeneImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		if (t.getAttributes().size() > 0) {
			sb.append(String.format("<%s id=\"%s\" innovation=\"%s\" configurationId=\"%s\" nodeType=\"%s\" activation=\"%s\">\n", NodeGeneImpl.class.getSimpleName(), t.getId(), t.getInnovation(), t.getConfiguration().getId(), t.getNodeType(), t.getActivation()));
			sb.append(INDENT + "<attributes>\n");
			String[] lines = XmlWriter.write(t.getAttributes()).split("\n");
			for (int i = 2; i < lines.length - 1; i++) {
				sb.append(INDENT + lines[i]).append("\n");
			}
			sb.append(INDENT + "</attributes>\n");
			sb.append(String.format("</%s>", NodeGeneImpl.class.getSimpleName()));
		} else {
			sb.append(String.format("<%s id=\"%s\" innovation=\"%s\" configurationId=\"%s\" nodeType=\"%s\" activation=\"%s\"/>\n", NodeGeneImpl.class.getSimpleName(), t.getId(), t.getInnovation(), t.getConfiguration().getId(), t.getNodeType(), t.getActivation()));
		}
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
