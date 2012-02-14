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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class UTestPopulationImpl_XML extends AbstractXmlFormatTest<PopulationImpl> {
	private static final Object[][]	INSTANCES;
	static {
		PopulationImpl pop = new PopulationImpl(0);
		GenomeImpl genome = new GenomeImpl(1, "asdf");
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		pop.add(genome);
		pop.incrementAge();
		pop.refreshStats();
		INSTANCES = TestUtils.createData(pop);
	}

	public UTestPopulationImpl_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(PopulationImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s id=\"%s\">\n", PopulationImpl.class.getName(), t.getId()));
		if (t.getAttributes().size() == 0) {
			sb.append(INDENT + "<attributes/>\n");
		} else {
			sb.append(INDENT + "<attributes>\n");
			String[] lines = XmlWriter.write(t.getAttributes()).split("\n");
			for (int i = 2; i < lines.length - 1; i++) {
				sb.append(INDENT + lines[i] + "\n");
			}
			sb.append(INDENT + "</attributes>\n");
		}
		if (!t.isEmpty()) {
			List<Genome<String>> genomes = new ArrayList<Genome<String>>(t);
			Collections.sort(genomes);
			for (Genome<String> genome : genomes) {
				sb.append(INDENT + String.format("<genome id=\"%s\">\n", genome.getId()));
				String[] lines = XmlWriter.write(genome).split("\n");
				for (int i = 2; i < lines.length - 1; i++) {
					sb.append(INDENT + lines[i] + "\n");
				}
				sb.append(INDENT + "</genome>\n");
			}
		}
		sb.append(String.format("</%s>", PopulationImpl.class.getName()));
		return sb.toString();
	}

	@DataProvider
	@Override
	protected Object[][] instances() {
		return INSTANCES;
	}
}
