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
package com.xtructure.xevolution.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

@Test(groups = { "xml:xevolution" })
public final class UTestEvolutionConfigurationImpl_XML extends AbstractXmlFormatTest<EvolutionConfigurationImpl> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				EvolutionConfigurationImpl.builder().newInstance(),//
				EvolutionConfigurationImpl.builder(XId.newId()).newInstance());
	}

	protected UTestEvolutionConfigurationImpl_XML() {
		super(EvolutionConfigurationImpl.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(EvolutionConfigurationImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<EvolutionConfigurationImpl id=\"%s\">\n", t.getId()));
		List<XId> ids = new ArrayList<XId>(t.getParameterIds());
		Collections.sort(ids);
		for (XId id : ids) {
			Wrapper wrapper = new Wrapper(t.getParameter(id));
			String[] lines = XmlWriter.write(wrapper, getXmlBinding()).split("\n");
			for (int i = 2; i < lines.length - 1; i++) {
				sb.append(Wrapper.replaceHook(lines[i], "parameter") + "\n");
			}
		}
		sb.append("</EvolutionConfigurationImpl>");
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
