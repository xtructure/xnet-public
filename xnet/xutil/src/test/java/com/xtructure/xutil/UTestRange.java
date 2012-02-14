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
package com.xtructure.xutil;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestRange {
	private static final Range<Integer>	pointRange	= Range.getInstance(0, 0);
	private static final Range<Integer>	posRange	= Range.getInstance(1, null);
	private static final Range<Integer>	negRange	= Range.getInstance(null, -1);
	private static final Range<Integer>	closedRange	= Range.getInstance(-10, 10);
	private static final Range<Integer>	openRange	= Range.getInstance(null, null);

	public UTestRange() {
		super();
	}

	public final void constructorSucceedsWithTwoNulls() {
		Integer i1 = null, i2 = null;
		assertThat("",//
				Range.getInstance(i1, i2), isNotNull());
	}

	public final void constructorSucceedsWithOneNull() {
		Integer i1 = null, i2 = Integer.valueOf(0);
		assertThat("",//
				Range.getInstance(i1, i2), isNotNull());
		assertThat("",//
				Range.getInstance(i2, i1), isNotNull());
	}

	public final void constructorSucceedsWithSameValue() {
		assertThat("", //
				Range.getInstance(Integer.MAX_VALUE, Integer.MAX_VALUE), isNotNull());
	}

	public final void constructorSucceedsWithDifferentValuesInOrder() {
		assertThat("",//
				Range.getInstance(Integer.MIN_VALUE, Integer.MAX_VALUE), isNotNull());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void constructorThrowsExceptionWithDifferentValuesOutOfOrder() {
		Range.getInstance(Integer.MAX_VALUE, Integer.MIN_VALUE);
	}

	@Test(dataProvider = "rangesInOut")
	public final void containsBehavesAsExpected(Range<Integer> r, Integer i, Integer o) {
		assertThat("", //
				r.contains(null), isFalse());
		assertThat("", //
				r.contains(i), isTrue());
		assertThat("", //
				r.contains(o), isFalse());
	}

	@Test(dataProvider = "rangesMinMax")
	public final void getMinMaxReturnsExpectedValue(Range<Integer> r, Integer min, Integer max) {
		assertThat("", //
				r.getMaximum(), isEqualTo(max));
		assertThat("", //
				r.getMinimum(), isEqualTo(min));
	}

	@Test(dataProvider = "ranges")
	public final void equalsOnNullOrNotRangeIsFalse(Range<Integer> r) {
		assertThat("",//
				r.equals(null), isFalse());
		assertThat("",//
				r.equals(Integer.valueOf(0)), isFalse());
	}

	@Test(dataProvider = "ranges")
	public final void equalsOnSelfIsTrue(Range<Integer> r) {
		assertThat("",//
				r.equals(r), isTrue());
	}

	@Test(dataProvider = "ranges")
	public final void equalsOnDifferentButEqualIsTrue(Range<Integer> r) {
		assertThat("",//
				r.equals(Range.getInstance(r.getMinimum(), r.getMaximum())), isTrue());
	}

	@Test(dataProvider = "ranges")
	public final void equalsOnDifferentIsFalse(Range<Integer> r) {
		int newMin = Integer.MIN_VALUE, newMax = Integer.MAX_VALUE;
		if (r.getMinimum() != null && r.getMaximum() != null) {
			if (r.getMaximum() == Integer.MAX_VALUE && r.getMinimum() == Integer.MIN_VALUE) {
				newMin++;
			}
		}

		assertThat("",//
				r.equals(Range.getInstance(newMin, newMax)), isFalse());
	}

	public final void hashCodeReturnsExpectedInt() {}

	@Test(dataProvider = "rangesString")
	public final void toStringReturnsExpectedString(Range<Integer> r, String expected) {
		assertThat("",//
				r.toString(), isEqualTo(expected));
	}

	@Test(dataProvider = "rangesVet")
	public final void vetReturnExpectedValue(Range<Integer> r, Integer below, Integer in, Integer above) {
		assertThat("",//
				r.vet(null), isEqualTo(r.getMinimum()));
		assertThat("", //
				r.vet(below), isEqualTo(r.getMinimum()));
		assertThat("", //
				r.vet(in), isEqualTo(in));
		assertThat("", //
				r.vet(above), isEqualTo(above == null ? r.getMinimum() : r.getMaximum()));
	}

	@DataProvider(name = "ranges")
	@SuppressWarnings("unused")
	private final Object[][] ranges() {
		return new Object[][] { //
		//
				new Object[] { pointRange }, //
				new Object[] { posRange }, //
				new Object[] { negRange }, //
				new Object[] { closedRange }, //
				new Object[] { openRange } //
		};
	}

	@DataProvider(name = "rangesInOut")
	@SuppressWarnings("unused")
	private final Object[][] rangesInOut() {
		return new Object[][] { //
		//
				new Object[] { pointRange, 0, 1 }, //
				new Object[] { posRange, 4, -1 }, //
				new Object[] { negRange, -3, 33 }, //
				new Object[] { closedRange, -3, 55 }, //
				new Object[] { openRange, 99999, null } //
		};
	}

	@DataProvider(name = "rangesMinMax")
	@SuppressWarnings("unused")
	private final Object[][] rangesMinMax() {
		return new Object[][] { //
		//
				new Object[] { pointRange, 0, 0 }, //
				new Object[] { posRange, 1, null }, //
				new Object[] { negRange, null, -1 }, //
				new Object[] { closedRange, -10, 10 }, //
				new Object[] { openRange, null, null } //
		};
	}

	@DataProvider(name = "rangesString")
	@SuppressWarnings("unused")
	private final Object[][] rangesString() {
		return new Object[][] { //
		//
				new Object[] { pointRange, "0" }, //
				new Object[] { posRange, "[1 .. *]" }, //
				new Object[] { negRange, "[* .. -1]" }, //
				new Object[] { closedRange, "[-10 .. 10]" }, //
				new Object[] { openRange, "[* .. *]" } //
		};
	}

	@DataProvider(name = "rangesVet")
	@SuppressWarnings("unused")
	private final Object[][] rangesVet() {
		return new Object[][] { //
		//
				new Object[] { pointRange, -1, 0, 1 }, //
				new Object[] { posRange, -8, 8, null }, //
				new Object[] { negRange, null, -1, 15 }, //
				new Object[] { closedRange, -11, 0, 12 }, //
				new Object[] { openRange, null, null, null } //
		};
	}
}
