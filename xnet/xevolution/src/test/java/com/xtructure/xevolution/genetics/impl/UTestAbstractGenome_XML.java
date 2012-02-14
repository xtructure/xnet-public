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

import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestAbstractGenome_XML extends AbstractXmlFormatTest<DummyGenome> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(new DummyGenome(0, "DATA"));
	}

	public UTestAbstractGenome_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(DummyGenome t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s id=\"%s\">\n", DummyGenome.class.getName(), t.getId()));
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
		sb.append(INDENT + String.format("<data value=\"%s\"/>\n", t.getData()));
		sb.append(String.format("</%s>", DummyGenome.class.getName()));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
