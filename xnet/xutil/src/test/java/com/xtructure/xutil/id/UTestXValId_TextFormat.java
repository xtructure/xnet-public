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

import javolution.text.TextFormat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.AbstractTextFormatTest;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@SuppressWarnings("rawtypes")
@Test(groups = { "text:xutil" })
public class UTestXValId_TextFormat extends AbstractTextFormatTest<XValId> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				XValId.newId(),//
				XValId.newId("base"),//
				XValId.newId(Integer.class),//
				XValId.newId(DummyXIdObject.class));
	}

	public UTestXValId_TextFormat() {
		super(XValId.class);
	}

	@Override
	public String generateExpectedString(XValId t) {
		return String.format("%s:[%s]", t.getBase(), t.getType().getName());
	}

	@Override
	@DataProvider
	public Object[][] instances() {
		return INSTANCES;
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void parseBadStringThrowsException() {
		TextFormat.getDefault(XValId.class).parse("base:[badclass]");
	}
}
