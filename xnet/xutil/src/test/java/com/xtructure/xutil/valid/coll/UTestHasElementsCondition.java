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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.valid.coll.HasElementsCondition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestHasElementsCondition {
	private static final Object[][]	BAD_ARGS;
	private static final Object[][]	GOOD_ARGS;
	static {
		BAD_ARGS = TestUtils.createData(//
				null,//
				"string",//
				new HashMap<Object, Object>(),//
				new Object());
		GOOD_ARGS = TestUtils.createData(//
				new ArrayList<Object>(),//
				new HashSet<Object>(),//
				new Object[0],//
				new char[0],//
				new byte[0],//
				new short[0],//
				new int[0],//
				new long[0],//
				new float[0],//
				new double[0],//
				new boolean[0]);
	}

	public void constructorSucceeds() {
		if (new HasElementsCondition(1, isNotNull()) == null) {
			throw new AssertionError();
		}
	}

	@DataProvider
	public Object[][] badArgs() {
		return BAD_ARGS;
	}

	@DataProvider
	public Object[][] goodArgs() {
		return GOOD_ARGS;
	}

	@Test(dataProvider = "badArgs")
	public void getIterableWithBadArgReturnsNull(Object object) {
		HasElementsCondition predicate = new HasElementsCondition(1, isNotNull());
		if (predicate.getIterable(object) != null) {
			throw new AssertionError();
		}
	}

	@Test(dataProvider = "goodArgs")
	public void getIterableWithGoodArgReturnsExpectedObject(Object object) {
		HasElementsCondition predicate = new HasElementsCondition(1, isNotNull());
		if (predicate.getIterable(object) == null) {
			throw new AssertionError();
		}
	}
}
