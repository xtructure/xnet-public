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
package com.xtructure.xneat.evolution.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

@Test(groups = { "xml:xneat" })
public class UTestNEATEvolutionConfigurationImpl_XML extends AbstractXmlFormatTest<NEATEvolutionConfigurationImpl> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				NEATEvolutionConfigurationImpl.builder(null)//
						.newInstance());
	}

	public UTestNEATEvolutionConfigurationImpl_XML() {
		super(NEATEvolutionConfigurationImpl.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(NEATEvolutionConfigurationImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s id=\"%s\">\n", NEATEvolutionConfigurationImpl.class.getSimpleName(), t.getId()));
		List<XId> ids = new ArrayList<XId>(t.getParameterIds());
		Collections.sort(ids);
		for (XId id : ids) {
			Wrapper wrapper = new Wrapper(t.getParameter(id));
			String[] lines = XmlWriter.write(wrapper, getXmlBinding()).split("\n");
			for (int i = 2; i < lines.length - 1; i++) {
				sb.append(Wrapper.replaceHook(lines[i], "parameter")).append("\n");
			}
		}
		sb.append(String.format("</%s>", NEATEvolutionConfigurationImpl.class.getSimpleName()));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
