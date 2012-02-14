/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.model.node;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.node.Node.Energies;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = "unit:xart")
public final class UTestNode_Enegies {
	private static final Object[][]	FLOATS;
	private static final Object[][]	FLOATS_FLOATS;
	static {
		FLOATS = new Object[][] {//
		//
			new Object[] { Float.MIN_VALUE },//
			new Object[] { Float.MAX_VALUE },//
			new Object[] { Float.MIN_NORMAL },//
			new Object[] { Float.NEGATIVE_INFINITY },//
			new Object[] { Float.POSITIVE_INFINITY },//
			new Object[] { Float.NaN },//
			new Object[] { null },//
		};
		FLOATS_FLOATS = TestUtils.crossData(FLOATS, FLOATS);
	}

	@Test(dataProvider = "floatsFloats")
	public void constructorSucceeds(Float f1, Float f2) {
		assertThat("",//
				new Energies(f1, f2), isNotNull());
	}

	public void equalsReturnsExpecetBoolean() {
		Energies e1 = new Energies(1f, 2f);
		Energies e2 = new Energies(2f, 1f);
		Energies e2Dup = new Energies(2f, 1f);
		assertThat("",//
				e1.equals(null), isFalse());
		assertThat("",//
				e1.equals(Integer.valueOf(0)), isFalse());
		assertThat("",//
				e1.equals(e1), isTrue());
		assertThat("",//
				e1.equals(e2), isFalse());
		assertThat("",//
				e2.equals(e1), isFalse());
		assertThat("",//
				e2.equals(e2Dup), isTrue());
		assertThat("",//
				e2Dup.equals(e2), isTrue());
	}

	@Test(dataProvider = "floatsFloats")
	public void getBackEnergyReturnsExpectedEnergy(Float f1, Float f2) {
		assertThat("",//
				new Energies(f1, f2).getBackEnergy(), isEqualTo(f2));
	}

	@Test(dataProvider = "floatsFloats")
	public void getFrontEnergyReturnsExpectedEnergy(Float f1, Float f2) {
		assertThat("",//
				new Energies(f1, f2).getFrontEnergy(), isEqualTo(f1));
	}

	@Test(dataProvider = "floatsFloats")
	public void toStringReturnsExpectedString(Float f1, Float f2) {
		String expected;
		if (f1 == f2) {
			expected = String.valueOf(f1);
		} else {
			expected = String.format("%f->%f", f2, f1);
		}
		assertThat("",//
				new Energies(f1, f2).toString(), isEqualTo(expected));
	}

	@DataProvider
	public Object[][] floats() {
		return FLOATS;
	}

	@DataProvider
	public Object[][] floatsFloats() {
		return FLOATS_FLOATS;
	}
}
