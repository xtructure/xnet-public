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
package com.xtructure.xutil.valid;

import java.util.ArrayList;
import java.util.TreeSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xutil" })
public final class UTestAbstractValueCondition_XML extends AbstractXmlFormatTest<DummyValueCondition> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				new DummyValueCondition(null, true),//
				new DummyValueCondition(new ArrayList<Object>(), true),//
				new DummyValueCondition(new SetBuilder<Integer>(new TreeSet<Integer>()).add(1, 2, 3).newInstance(), true),//
				new DummyValueCondition("string", true));
	}

	protected UTestAbstractValueCondition_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(DummyValueCondition t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		if (t.getValue() != null) {
			sb.append(String.format("<%s>\n", DummyValueCondition.class.getName()));
			String[] lines = XmlWriter.write(new Wrapper(t.getValue()), INDENT).split("\n");
			for (int i = 2; i < lines.length - 1; i++) {
				sb.append(Wrapper.replaceHook(lines[i], "value") + "\n");
			}
			sb.append(String.format("</%s>", DummyValueCondition.class.getName()));
		} else {
			sb.append(String.format("<%s/>\n", DummyValueCondition.class.getName()));
		}
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
