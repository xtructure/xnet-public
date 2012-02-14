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
package com.xtructure.xutil.id;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xutil" })
public class UTestAbstractXIdObject_XML extends AbstractXmlFormatTest<DummyXIdObject> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				new DummyXIdObject(XId.newId(1, 2, 3)));
	}

	protected UTestAbstractXIdObject_XML() {
		super(null);
	}

	@Override
	protected String generateExpectedXMLString(DummyXIdObject t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<%s id=\"%s\"/>", t.getClass().getName(), t.getId()));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
