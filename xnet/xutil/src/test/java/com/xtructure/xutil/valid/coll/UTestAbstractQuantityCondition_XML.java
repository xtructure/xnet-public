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
package com.xtructure.xutil.valid.coll;

import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.matches;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xutil" })
public class UTestAbstractQuantityCondition_XML extends AbstractXmlFormatTest<DummyQuantityCondition> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				new DummyQuantityCondition(DummyQuantityCondition.ALL, isNotNull()),//
				new DummyQuantityCondition(5, matches("asdf")),//
				new DummyQuantityCondition(1, isLessThanOrEqualTo(5)));
	}

	protected UTestAbstractQuantityCondition_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(DummyQuantityCondition t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s quantity=\"%s\">\n", DummyQuantityCondition.class.getName(), t.getQuantity()));
		String[] lines = XmlWriter.write(new Wrapper(t.getCondition()), INDENT).split("\n");
		for (int i = 2; i < lines.length - 1; i++) {
			sb.append(Wrapper.replaceHook(lines[i], "condition") + "\n");
		}
		sb.append(String.format("</%s>", DummyQuantityCondition.class.getName()));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
