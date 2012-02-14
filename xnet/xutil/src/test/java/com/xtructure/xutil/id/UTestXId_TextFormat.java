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

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.AbstractTextFormatTest;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "text:xutil" })
public final class UTestXId_TextFormat extends AbstractTextFormatTest<XId> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				XId.newId(),//
				XId.newId(1, 2, 3, 4),//
				XId.newId(RandomStringUtils.randomAlphanumeric(20)),//
				XId.newId(RandomStringUtils.randomAlphanumeric(20), 1, 2, 3, 4));
	}

	public UTestXId_TextFormat() {
		super(XId.class);
	}

	@Override
	public String generateExpectedString(XId t) {
		if (t.getInstanceNums().isEmpty()) {
			return t.getBase();
		} else {
			return String.format("%s:%s", t.getBase(), t.getInstanceNums());
		}
	}

	@Override
	@DataProvider(name = "instances")
	public Object[][] instances() {
		return INSTANCES;
	}
}
