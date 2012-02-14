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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.valid.object.IsSameAsCondition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestIsSameAsCondition {
	private static final Object[][]	VALUE_ARGS;
	private static final Object[][]	SAT_ARGS;
	static {
		VALUE_ARGS = TestUtils.createData(//
				null,//
				"", "asdf",//
				1, 2,//
				1l, 2l,//
				1.0f, 2.0f,//
				1.0, 2.0,//
				new Object(), new Object(),//
				new Object[0], new Object[0]);
		SAT_ARGS = TestUtils.crossData(VALUE_ARGS, VALUE_ARGS);
	}

	@DataProvider
	public Object[][] valueArgs() {
		return VALUE_ARGS;
	}

	@DataProvider
	public Object[][] satArgs() {
		return SAT_ARGS;
	}

	@Test(dataProvider = "valueArgs")
	public void constructorSucceeds(Object value) {
		if (new IsSameAsCondition(value) == null) {
			throw new AssertionError();
		}
	}

	@Test(dataProvider = "satArgs")
	public void isSatisfiedByReturnsExpectedBoolean(Object o1, Object o2) {
		if (new IsSameAsCondition(o1).isSatisfiedBy(o2)) {
			if (o1 != o2) {
				throw new AssertionError();
			}
		} else {
			if (o1 == o2) {
				throw new AssertionError();
			}
		}
	}

	@Test(dataProvider = "valueArgs")
	public void toStringReturnsExpectedString(Object value) {
		if (!String.format("must be same as %s", value).equals(new IsSameAsCondition(value).toString())) {
			throw new AssertionError();
		}
	}
}
