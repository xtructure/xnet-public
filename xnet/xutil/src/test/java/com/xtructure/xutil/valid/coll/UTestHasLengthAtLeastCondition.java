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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Collections;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.TestUtils;

@Test(groups = { "unit:xutil" })
public final class UTestHasLengthAtLeastCondition {
	private static final int		LENGTH;
	private static final Object[][]	SAT;
	private static final Object[][]	UNSAT;
	static {
		Object[][] bad = TestUtils.createData(//
				null,//
				Collections.singleton(1),//
				new Object());
		int length = 0;
		Object[][] zero = TestUtils.createData(//
				"",//
				new Object[length],//
				new char[length],//
				new byte[length],//
				new short[length],//
				new int[length],//
				new long[length],//
				new boolean[length],//
				new float[length],//
				new double[length]);
		length = 1;
		Object[][] one = TestUtils.createData(//
				"a",//
				new Object[length],//
				new char[length],//
				new byte[length],//
				new short[length],//
				new int[length],//
				new long[length],//
				new boolean[length],//
				new float[length],//
				new double[length]);
		length = 2;
		Object[][] two = TestUtils.createData(//
				"ab",//
				new Object[length],//
				new char[length],//
				new byte[length],//
				new short[length],//
				new int[length],//
				new long[length],//
				new boolean[length],//
				new float[length],//
				new double[length]);
		LENGTH = 1;
		SAT = TestUtils.unionData(one, two);
		UNSAT = TestUtils.unionData(bad, zero);
	}

	@DataProvider
	public Object[][] sat() {
		return SAT;
	}

	@DataProvider
	public Object[][] unsat() {
		return UNSAT;
	}

	public void constructorSucceds() {
		assertThat("",//
				new HasLengthAtLeastCondition(0), isNotNull());
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorOnNegativeThrowsException() {
		new HasLengthAtLeastCondition(-1);
	}

	@Test(dataProvider = "sat")
	public void isSatisfiedByReturnsTrue(Object object) {
		assertThat("",//
				new HasLengthAtLeastCondition(LENGTH).isSatisfiedBy(object),//
				isTrue());
	}

	@Test(dataProvider = "unsat")
	public void isSatisfiedByReturnsFalse(Object object) {
		assertThat("",//
				new HasLengthAtLeastCondition(LENGTH).isSatisfiedBy(object),//
				isFalse());
	}

	public void toStringReturnsExpectedObject() {
		assertThat("",//
				new HasLengthAtLeastCondition(LENGTH).toString(),//
				isEqualTo(String.format("must have length at least %d", LENGTH)));
	}
}
