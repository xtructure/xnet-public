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

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestVettingStrategyImpl {
	/** a vetting group for single digit integers */
	private static final VettingGroup<Integer> singleDigitVG;
	/** a strict vetting group for double digit integers */
	private static final VettingGroup<Integer> doubleDigitStrictVG;
	/** a vetting group for single or double digit integers */
	private static final VettingGroup<Integer> singleOrDoubleDigitVG;
	/** a strict vetting group for double or triple digit integers */
	private static final VettingGroup<Integer> doubleOrTripleDigitStrictVG;

	/** list with no vetting groups */
	private final static List<VettingGroup<Integer>> emptyVGList;
	/** list with a single member : singleDigitVG */
	private final static List<VettingGroup<Integer>> singleDigitVGList;
	/** list with a single member : doubleDigitStrictVG */
	private final static List<VettingGroup<Integer>> doubleDigitStrictVGList;
	/** list with a single member : singleOrDoubleDigitVG */
	private final static List<VettingGroup<Integer>> singleOrDoubleDigitVGList;
	/** list with a single member : doubleOrTripleDigitStrictVG */
	private final static List<VettingGroup<Integer>> doubleOrTripleDigitStrictVGList;
	/** list with several vetting groups */
	private final static List<VettingGroup<Integer>> multipleVGList;

	/** VettingStrategyImpl that only includes single digit vetting */
	private static final VettingStrategyImpl<Integer> singleDigitVS;
	/** VettingStrategyImpl that only includes double digit strict vetting */
	private static final VettingStrategyImpl<Integer> doubleDigitStrictVS;
	/** VettingStrategyImpl that only includes single or double digit vetting */
	private static final VettingStrategyImpl<Integer> singleOrDoubleDigitVS;
	/** VettingStrategyImpl only includes double or triple digit strict vetting */
	private static final VettingStrategyImpl<Integer> doubleOrTripleDigitStrictVS;
	/** VettingStrategyImpl where lenient vetting is succeeded by strict vetting */
	private static final VettingStrategyImpl<Integer> lenientFirstMixedVS;
	/** VettingStrategyImpl where lenient vetting succeeds strict vetting */
	private static final VettingStrategyImpl<Integer> strictFirstMixedVS;

	static {
		singleDigitVG = VettingGroup.getLenientInstance(-9,//
				isNotNull(), //
				isGreaterThanOrEqualTo(-9),//
				isLessThanOrEqualTo(9));
		doubleDigitStrictVG = VettingGroup.getStrictInstance(Integer.class,//
				isNotNull(), //
				isGreaterThanOrEqualTo(-99),//
				isLessThanOrEqualTo(99), //
				not(and(isGreaterThan(-10), isLessThan(10))));

		singleOrDoubleDigitVG = VettingGroup.getLenientInstance(-99,//
				isNotNull(),//
				isGreaterThanOrEqualTo(-99),//
				isLessThanOrEqualTo(99));
		doubleOrTripleDigitStrictVG = VettingGroup.getStrictInstance(Integer.class,//
				isNotNull(),//
				isGreaterThanOrEqualTo(-999),//
				isLessThanOrEqualTo(999), //
				not(and(isGreaterThan(-10), isLessThan(10))));

		emptyVGList = new ArrayList<VettingGroup<Integer>>();
		singleDigitVGList = new ArrayList<VettingGroup<Integer>>();
		doubleDigitStrictVGList = new ArrayList<VettingGroup<Integer>>();
		singleOrDoubleDigitVGList = new ArrayList<VettingGroup<Integer>>();
		doubleOrTripleDigitStrictVGList = new ArrayList<VettingGroup<Integer>>();
		multipleVGList = new ArrayList<VettingGroup<Integer>>();

		singleDigitVGList.add(singleDigitVG);
		doubleDigitStrictVGList.add(doubleDigitStrictVG);
		singleOrDoubleDigitVGList.add(singleOrDoubleDigitVG);
		doubleOrTripleDigitStrictVGList.add(doubleOrTripleDigitStrictVG);
		multipleVGList.add(singleDigitVG);
		multipleVGList.add(doubleDigitStrictVG);

		singleDigitVS = VettingStrategyImpl.getInstance(singleDigitVGList);
		doubleDigitStrictVS = VettingStrategyImpl.getInstance(doubleDigitStrictVGList);
		singleOrDoubleDigitVS = VettingStrategyImpl.getInstance(singleOrDoubleDigitVGList);
		doubleOrTripleDigitStrictVS = VettingStrategyImpl.getInstance(doubleOrTripleDigitStrictVGList);
		lenientFirstMixedVS = VettingStrategyImpl.getInstance(singleOrDoubleDigitVGList, doubleOrTripleDigitStrictVS);
		strictFirstMixedVS = VettingStrategyImpl.getInstance(doubleOrTripleDigitStrictVGList, singleOrDoubleDigitVS);
	}

	public UTestVettingStrategyImpl() {}

	@Test(dataProvider = "vettingGroups")
	public final void getInstanceVettingGroupsSucceeds(List<VettingGroup<Integer>> vettingGroups) {
		assertThat("",//
				VettingStrategyImpl.getInstance(vettingGroups), isNotNull());
	}

	@Test(dataProvider = "vettingGroupsSuccessor")
	public final void getInstanceVettingGroupsSuccessorSucceeds(List<VettingGroup<Integer>> vettingGroups, VettingStrategyImpl<Integer> successor) {
		assertThat("",//
				VettingStrategyImpl.getInstance(vettingGroups, successor), isNotNull());
	}

	@Test(dataProvider = "vettingStrategiesIn")
	public final void vetValueOnAcceptedValuesReturnGivenValue(VettingStrategy<Integer> vettingStrategy, Integer in) {
		assertThat("",//
				vettingStrategy.vetValue(in), isSameAs(in));
	}

	@Test(dataProvider = "vettingStrategiesOutDef")
	public final void vetValueOnLenientUnacceptedValuesReturnDefaultValue(VettingStrategy<Integer> vettingStrategy, Integer out, Integer defValue) {
		assertThat("", //
				vettingStrategy.vetValue(out), isSameAs(defValue));
	}

	@Test(dataProvider = "vettingStrategiesOutStrict", expectedExceptions = XConfigurationException.class)
	public final void vetValueOnStrictUnacceptedValuesThrowsException(VettingStrategy<Integer> vettingStrategy, Integer out) {
		vettingStrategy.vetValue(out);
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "vettingGroups")
	private final Object[][] vettingGroups() {
		return new Object[][] {
		//
				new Object[] { null }, //
				new Object[] { emptyVGList }, //
				new Object[] { singleDigitVGList }, //
				new Object[] { singleOrDoubleDigitVGList }, //
				new Object[] { doubleDigitStrictVGList }, //
				new Object[] { doubleOrTripleDigitStrictVGList }, //
				new Object[] { multipleVGList } //
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "vettingGroupsSuccessor")
	private final Object[][] vettingGroupsSuccessor() {
		return new Object[][] {
		//
				new Object[] { null, null }, //
				new Object[] { null, singleDigitVS }, //
				new Object[] { singleDigitVGList, singleDigitVS }, //
				new Object[] { multipleVGList, strictFirstMixedVS }, //
				new Object[] { singleDigitVGList, doubleDigitStrictVS }, //
				new Object[] { multipleVGList, lenientFirstMixedVS } //
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "vettingStrategiesIn")
	private final Object[][] vettingStrategiesIn() {
		return new Object[][] {
		//
				new Object[] { singleDigitVS, -1 }, //
				new Object[] { singleOrDoubleDigitVS, 5 }, //
				new Object[] { singleOrDoubleDigitVS, 54 }, //
				new Object[] { doubleDigitStrictVS, 12 }, //
				new Object[] { doubleOrTripleDigitStrictVS, 12 }, //
				new Object[] { doubleOrTripleDigitStrictVS, -999 }, //
				new Object[] { strictFirstMixedVS, -10 }, //
				new Object[] { lenientFirstMixedVS, -10 } //
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "vettingStrategiesOutDef")
	private final Object[][] vettingStrategiesOutDef() {
		return new Object[][] {
		//
				new Object[] { singleDigitVS, 20, singleDigitVG.getDefaultValue() }, //
				new Object[] { singleDigitVS, -11, singleDigitVG.getDefaultValue() }, //
				new Object[] { singleOrDoubleDigitVS, -123, singleOrDoubleDigitVG.getDefaultValue() }, //
				new Object[] { singleOrDoubleDigitVS, 10000, singleOrDoubleDigitVG.getDefaultValue() }, //
				new Object[] { lenientFirstMixedVS, 999, singleOrDoubleDigitVG.getDefaultValue() }, //
				new Object[] { lenientFirstMixedVS, -999, singleOrDoubleDigitVG.getDefaultValue() }, //
				new Object[] { strictFirstMixedVS, -132, singleOrDoubleDigitVG.getDefaultValue() }, //
				new Object[] { strictFirstMixedVS, 515, singleOrDoubleDigitVG.getDefaultValue() } //
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "vettingStrategiesOutStrict")
	private final Object[][] vettingStrategiesOutStrict() {
		return new Object[][] {
		//
				new Object[] { doubleDigitStrictVS, 520 }, //
				new Object[] { doubleDigitStrictVS, -119 }, //
				new Object[] { doubleOrTripleDigitStrictVS, -1234 }, //
				new Object[] { doubleOrTripleDigitStrictVS, 10000 }, //
				new Object[] { lenientFirstMixedVS, 9 }, //
				new Object[] { lenientFirstMixedVS, -5 }, //
				new Object[] { strictFirstMixedVS, 0 }, //
				new Object[] { strictFirstMixedVS, 999999 } //
		};
	}
}