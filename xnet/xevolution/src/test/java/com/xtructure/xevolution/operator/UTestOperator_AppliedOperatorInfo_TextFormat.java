/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.operator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xevolution.operator.Operator.AppliedOperatorInfo;
import com.xtructure.xutil.test.AbstractTextFormatTest;
import com.xtructure.xutil.test.TestUtils;

@Test(groups = { "text:xevolution" })
public class UTestOperator_AppliedOperatorInfo_TextFormat extends AbstractTextFormatTest<AppliedOperatorInfo> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				new AppliedOperatorInfo(DummyMutateOperator.class),//
				new AppliedOperatorInfo(DummyCrossoverOperator.class));
	}

	public UTestOperator_AppliedOperatorInfo_TextFormat() {
		super(AppliedOperatorInfo.class);
	}

	@Override
	public String generateExpectedString(AppliedOperatorInfo t) {
		return t.getOperatorClass().getName();
	}

	@Override
	@DataProvider
	public Object[][] instances() {
		return INSTANCES;
	}
}
