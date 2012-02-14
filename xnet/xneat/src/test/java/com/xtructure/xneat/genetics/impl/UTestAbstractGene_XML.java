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

import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xneat" })
public class UTestAbstractGene_XML extends AbstractXmlFormatTest<DummyGene> {
	private static final Object[][]	INSTANCES;
	static {
		NodeGeneConfiguration configuration = NodeGeneConfiguration.DEFAULT_CONFIGURATION;
		DummyGene gene1 = new DummyGene(XId.newId("dummy").createChild(1), configuration);
		DummyGene gene2 = new DummyGene(XId.newId("dummy").createChild(2), configuration);
		gene2.setAttribute(XValId.newId("attr", Double.class), 0.0);
		INSTANCES = TestUtils.createData(gene1, gene2);
	}

	public UTestAbstractGene_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(DummyGene t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		if (t.getAttributes().size() > 0) {
			sb.append(String.format("<%s id=\"%s\" innovation=\"%s\" configurationId=\"%s\">\n", DummyGene.class.getName(), t.getId(), t.getInnovation(), t.getConfiguration().getId()));
			{
				sb.append(INDENT + "<attributes>\n");
				String[] lines = XmlWriter.write(t.getAttributes()).split("\n");
				for (int i = 2; i < lines.length - 1; i++) {
					sb.append(INDENT + lines[i]).append("\n");
				}
				sb.append(INDENT + "</attributes>\n");
			}
			sb.append(String.format("</%s>", DummyGene.class.getName()));
			return sb.toString();
		}
		return sb.append(String.format("<%s id=\"%s\" innovation=\"%s\" configurationId=\"%s\"/>\n", DummyGene.class.getName(), t.getId(), t.getInnovation(), t.getConfiguration().getId())).toString();
	}

	@DataProvider
	@Override
	protected Object[][] instances() {
		return INSTANCES;
	}
}
