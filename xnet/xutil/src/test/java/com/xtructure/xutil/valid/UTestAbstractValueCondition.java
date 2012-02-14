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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestAbstractValueCondition {
	private static final Object[][]	OBJ_ARGS;
	static {
		OBJ_ARGS = TestUtils.unionData(//
				TestUtils.crossData(//
						TestUtils.createData(//
								1, 1L, "asdf", new Object(), new Object[0]),//
						TestUtils.createData(false)),//
				TestUtils.crossData(//
						TestUtils.createData(//
								null, 1, 1L, "asdf", new Object(), new Object[0]),//
						TestUtils.createData(true)));
	}

	@DataProvider
	public Object[][] objArgs() {
		return OBJ_ARGS;
	}

	@Test(dataProvider = "objArgs")
	public void constructorSucceeds(Object value, boolean allowNull) {
		if (new DummyValueCondition(value, allowNull) == null) {
			throw new AssertionError();
		}
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithNullAndNotNullableThrowException() {
		new DummyValueCondition(null, false);
	}

	@Test(dataProvider = "objArgs")
	public void getValueReturnsExpectedObject(Object value, boolean allowNull) {
		if (new DummyValueCondition(value, allowNull).getValue() != value) {
			throw new AssertionError();
		}
	}
}
