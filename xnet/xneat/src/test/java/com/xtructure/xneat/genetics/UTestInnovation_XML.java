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
package com.xtructure.xneat.genetics;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;

@Test(groups = { "xml:xneat" })
public class UTestInnovation_XML extends AbstractXmlFormatTest<Innovation> {
	private static final Object[][]	INSTANCES;
	static {
		List<Innovation> list = new ArrayList<Innovation>();
		for (int i = 0; i < 3; i++) {
			list.add(Innovation.generate(i));
			for (int j = 0; j < 3; j++) {
				list.add(Innovation.generate(i, j));
			}
		}
		INSTANCES = TestUtils.createData(list.toArray());
	}

	public UTestInnovation_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(Innovation t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s id=\"%s\"/>", Innovation.class.getName(), t.getId()));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
