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
package com.xtructure.xneat.genetics.link;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

@Test(groups = { "xml:xneat" })
public class UTestLinkGeneConfiguration_XML extends AbstractXmlFormatTest<LinkGeneConfiguration> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(LinkGeneConfiguration.builder(XId.newId("UTestLinkGeneConfiguration_XML")).newInstance());
	}

	public UTestLinkGeneConfiguration_XML() {
		super(LinkGeneConfiguration.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(LinkGeneConfiguration t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s id=\"%s\">\n", LinkGeneConfiguration.class.getSimpleName(), t.getId()));
		for (XId parameterId : t.getParameterIds()) {
			Wrapper wrapper = new Wrapper(t.getParameter(parameterId));
			String line = XmlWriter.write(wrapper, getXmlBinding()).split("\n")[2];
			sb.append(Wrapper.replaceHook(line, "parameter") + "\n");
		}
		sb.append(String.format("</%s>", LinkGeneConfiguration.class.getSimpleName()));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
