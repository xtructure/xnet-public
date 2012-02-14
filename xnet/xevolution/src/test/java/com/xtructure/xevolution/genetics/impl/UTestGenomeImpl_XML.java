/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.genetics.impl;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xevolution" })
public class UTestGenomeImpl_XML extends AbstractXmlFormatTest<GenomeImpl> {
	private static final Object[][]	INSTANCES;
	static {
		GenomeImpl genome0 = new GenomeImpl(0, "");
		GenomeImpl genome1 = new GenomeImpl(1, "asdf");
		GenomeImpl genome0f = new GenomeImpl(0, "");
		GenomeImpl genome1f = new GenomeImpl(1, "asdf");
		genome0f.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 0.0);
		genome1f.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		INSTANCES = TestUtils.createData(genome0, genome1, genome0f, genome1f);
	}

	public UTestGenomeImpl_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(GenomeImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s id=\"%s\">\n", GenomeImpl.class.getName(), t.getId()));
		if (t.getAttributes().size() == 0) {
			sb.append(INDENT + "<attributes/>\n");
		} else {
			sb.append(INDENT + "<attributes>\n");
			String[] lines = XmlWriter.write(t.getAttributes()).split("\n");
			for (int i = 2; i + 1 < lines.length; i++) {
				sb.append(INDENT + lines[i] + "\n");
			}
			sb.append(INDENT + "</attributes>\n");
		}
		sb.append(INDENT + String.format("<data value=\"%s\"/>\n", t.getData()));
		sb.append(String.format("</%s>", GenomeImpl.class.getName()));
		return sb.toString();
	}

	@DataProvider
	@Override
	protected Object[][] instances() {
		return INSTANCES;
	}
}
