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
package com.xtructure.xutil.valid.object;

import static com.xtructure.xutil.valid.ValidateUtils.isOfExactType;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.valid.object.IsOfExactTypeCondition;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xutil" })
public class UTestIsOfExactTypeCondition_XML extends AbstractXmlFormatTest<IsOfExactTypeCondition> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(isOfExactType(Object.class));
	}

	protected UTestIsOfExactTypeCondition_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(IsOfExactTypeCondition t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		if (t.getValue() != null) {
			sb.append(String.format("<%s>\n", t.getClass().getName()));
			String[] lines = XmlWriter.write(new Wrapper(t.getValue()), INDENT).split("\n");
			for (int i = 2; i < lines.length - 1; i++) {
				sb.append(Wrapper.replaceHook(lines[i], "value") + "\n");
			}
			sb.append(String.format("</%s>", t.getClass().getName()));
		} else {
			sb.append(String.format("<%s/>\n", t.getClass().getName()));
		}
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
