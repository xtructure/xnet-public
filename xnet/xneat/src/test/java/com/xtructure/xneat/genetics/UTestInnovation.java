/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.genetics;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.TestUtils;

@Test(groups = { "unit:xneat" })
public class UTestInnovation {
	private static final Object[][]	INNOVATION_DATA;
	private static final Object[][]	INNOVATION_INNOVATION_DATA;
	static {
		List<Innovation> list = new ArrayList<Innovation>();
		for (int i = 0; i < 3; i++) {
			list.add(Innovation.generate(i));
			for (int j = 0; j < 3; j++) {
				list.add(Innovation.generate(i, j));
			}
		}
		INNOVATION_DATA = TestUtils.createData(list.toArray());
		INNOVATION_INNOVATION_DATA = TestUtils.crossData(INNOVATION_DATA, INNOVATION_DATA);
	}

	@DataProvider(name = "innovationData")
	public Object[][] innovationData() {
		return INNOVATION_DATA;
	}

	@DataProvider(name = "innovationInnovationData")
	public Object[][] innovationInnovationData() {
		return INNOVATION_INNOVATION_DATA;
	}

	@Test(dataProvider = "innovationData")
	public void constructorSucceeds(Innovation inn1) {
		assertThat("",//
				inn1, isNotNull());
	}

	@Test(dataProvider = "innovationInnovationData")
	public void compareToReturnsExpectedInt(Innovation inn1, Innovation inn2) {
		assertThat("",//
				inn1.compareTo(inn2),//
				isEqualTo(inn1.getId().compareTo(inn2.getId())));
		assertThat("",//
				inn1.compareTo(null),//
				isEqualTo(1));
	}

	@Test(dataProvider = "innovationInnovationData")
	public void equalsReturnsExpectedBoolean(Innovation inn1, Innovation inn2) {
		assertThat("",//
				inn1.equals(inn2),//
				isEqualTo(inn1.getId().equals(inn2.getId())));
		assertThat("",//
				inn1.equals(inn1),//
				isTrue());
		assertThat("",//
				inn1.equals(null),//
				isFalse());
		assertThat("",//
				inn1.equals(new Object()),//
				isFalse());
	}

	@Test(dataProvider = "innovationData")
	public void hashCodeReturnsExpectedInt(Innovation inn1) {
		assertThat("",//
				inn1.hashCode(), isEqualTo(inn1.getId().hashCode()));
	}

	@Test(dataProvider = "innovationData")
	public void toStringReturnsExpectedString(Innovation inn1) {
		assertThat("",//
				inn1.toString(), isEqualTo(inn1.getId().toString()));
	}
}
