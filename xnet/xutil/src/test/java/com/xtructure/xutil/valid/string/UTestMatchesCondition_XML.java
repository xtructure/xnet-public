/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xutil.valid.string;

import java.util.regex.Pattern;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlBinding;

@Test(groups = { "xml:xutil" })
public final class UTestMatchesCondition_XML extends AbstractXmlFormatTest<MatchesCondition> {
	private static final XmlBinding	BINDING;
	private static final Object[][]	INSTANCES;
	static {
		BINDING = new XmlBinding(MatchesCondition.class, Pattern.class);
		INSTANCES = TestUtils.createData(new MatchesCondition("regex"));
	}

	protected UTestMatchesCondition_XML() {
		super(BINDING);
	}

	@Override
	protected String generateExpectedXMLString(MatchesCondition t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append("<MatchesCondition>\n");
		sb.append(INDENT + String.format("<value class=\"java.lang.String\" value=\"%s\"/>\n", t.getValue()));
		sb.append("</MatchesCondition>\n");
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
