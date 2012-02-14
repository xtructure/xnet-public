/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.model.link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

@Test(groups = { "xml:xart" })
public class UTestLinkConfiguration_XML extends AbstractXmlFormatTest<LinkConfiguration> {
	private static final Object[][]			INSTANCES;
	private static final LinkConfiguration	UNREGISTERED;
	static {
		UNREGISTERED = LinkConfiguration.builder().newInstance();
		LinkConfiguration.getManager().unregister(UNREGISTERED);
		INSTANCES = TestUtils.createData(//
				LinkConfiguration.DEFAULT_CONFIGURATION,//
				UNREGISTERED,//
				LinkConfiguration.builder().newInstance());
	}

	protected UTestLinkConfiguration_XML() {
		super(LinkConfiguration.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(LinkConfiguration t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<artLinkConfig id=\"%s\">\n", t.getId()));
		List<XId> parameterIds = new ArrayList<XId>(t.getParameterIds());
		Collections.sort(parameterIds);
		for (XId id : parameterIds) {
			Wrapper param = new Wrapper(t.getParameter(id));
			String line = XmlWriter.write(param, getXmlBinding()).split("\n")[2];
			sb.append(Wrapper.replaceHook(line, "parameter")).append("\n");
		}
		sb.append("</artLinkConfig>");
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
