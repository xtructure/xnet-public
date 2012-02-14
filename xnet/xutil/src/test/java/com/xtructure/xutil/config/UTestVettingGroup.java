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
package com.xtructure.xutil.config;

import static com.xtructure.xutil.valid.ValidateUtils.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestVettingGroup {

	public UTestVettingGroup() {
	}

	public final void getLenientInstanceSucceeds() {
		assertThat("", //
				VettingGroup.getLenientInstance(Integer.valueOf(0), isNotNull()), isNotNull());
	}

	public final void getStrictInstanceClassSucceeds() {
		assertThat("", //
				VettingGroup.getStrictInstance(Integer.class, isNotNull()), isNotNull());
	}

	public final void getStrictInstanceSucceeds() {
		assertThat("", //
				VettingGroup.getStrictInstance(isNotNull()), isNotNull());
	}

	public final void getDefaultValueOnLenientReturnsExpectedValue() {
		assertThat("", //
				VettingGroup.getLenientInstance(Integer.MAX_VALUE, isNotNull()).getDefaultValue(), isEqualTo(Integer.MAX_VALUE));
	}

	public final void getDefaultValueOnStrictReturnsNull() {
		assertThat("", //
				VettingGroup.getStrictInstance(Integer.class, isNotNull()).getDefaultValue(), isNull());
		assertThat("", //
				VettingGroup.getStrictInstance(isNotNull()).getDefaultValue(), isNull());
	}

	@Test(dataProvider = "lenientAcceptTests")
	public final void lenientAcceptReturnsExpectedBoolean(VettingGroup<Integer> vg, Integer in, Integer out) {
		assertThat("",//
				vg.accept(in), isTrue());
		assertThat("", //
				vg.accept(out), isFalse());
	}

	/**
	 * @param vg
	 * @param in
	 * @param out
	 */
	@Test(dataProvider = "strictClassAcceptTests")
	public final void strictClassAcceptInValuesIsTrue(VettingGroup<Integer> vg, Integer in, Integer out) {
		assertThat("",//
				vg.accept(in), isTrue());
	}

	/**
	 * @param vg
	 * @param in
	 * @param out
	 */
	@Test(dataProvider = "strictAcceptTests")
	public final void strictAcceptInValuesIsTrue(VettingGroup<Integer> vg, Integer in, Integer out) {
		assertThat("",//
				vg.accept(in), isTrue());
	}

	/**
	 * @param vg
	 * @param in
	 * @param out
	 */
	@Test(dataProvider = "strictClassAcceptTests", expectedExceptions = XConfigurationException.class)
	public final void strictClassAcceptOutValuesThrowsException(VettingGroup<Integer> vg, Integer in, Integer out) {
		vg.accept(out);
	}

	/**
	 * @param vg
	 * @param in
	 * @param out
	 */
	@Test(dataProvider = "strictAcceptTests", expectedExceptions = XConfigurationException.class)
	public final void strictAcceptOutValuesThrowsException(VettingGroup<Integer> vg, Integer in, Integer out) {
		vg.accept(out);
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "lenientAcceptTests")
	private final Object[][] lenientAcceptTests() {
		return new Object[][] {
		//
				new Object[] {//
				VettingGroup.getLenientInstance(Integer.MIN_VALUE, //
						isNotNull()), // accept non-null Integers
						Integer.valueOf(15), // in
						null // out
				}, //
				new Object[] { //
				VettingGroup.getLenientInstance(Integer.MAX_VALUE, //
						isGreaterThan(0), //
						isLessThan(10), //
						isNotNull()), // accept non-null Integers in [1-9]
						Integer.valueOf(5), // in
						Integer.valueOf(44) // out
				} //
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "strictClassAcceptTests")
	private final Object[][] strictClassAcceptTests() {
		return new Object[][] {
		//
				new Object[] { //
				VettingGroup.getStrictInstance(Integer.class,//
						isNotNull()), // accept non-null Integers
						Integer.valueOf(15), // in
						null // out
				}, //
				new Object[] { //
				VettingGroup.getStrictInstance(Integer.class,//
						isGreaterThan(0), //
						isLessThan(10), //
						isNotNull()), // accept non-null Integers in [1-9]
						Integer.valueOf(5), // in
						Integer.valueOf(44) // out
				} //
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "strictAcceptTests")
	private final Object[][] strictAcceptTests() {
		return new Object[][] {
		//
				new Object[] { //
				VettingGroup.getStrictInstance(//
						isNotNull()), // accept non-null Integers
						Integer.valueOf(15), // in
						null // out
				}, //
				new Object[] { //
				VettingGroup.getStrictInstance(//
						isGreaterThan(0), //
						isLessThan(10), //
						isNotNull()), // accept non-null Integers in [1-9]
						Integer.valueOf(5), // in
						Integer.valueOf(44) // out
				} //
		};
	}

}
