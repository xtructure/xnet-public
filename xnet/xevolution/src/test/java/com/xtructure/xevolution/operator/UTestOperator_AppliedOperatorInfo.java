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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

import com.xtructure.xevolution.operator.Operator.AppliedOperatorInfo;

@Test(groups = { "unit:xevolution" })
public class UTestOperator_AppliedOperatorInfo {
	public void constructorSucceeds() {
		assertThat("",//
				new AppliedOperatorInfo(DummyMutateOperator.class),//
				isNotNull());
	}

	public void getOperatorClassReturnsExpectedObject() {
		assertThat("",//
				new AppliedOperatorInfo(DummyMutateOperator.class).getOperatorClass(),//
				isEqualTo(DummyMutateOperator.class));
	}

	public void toStringReturnsExpectedObject() {
		AppliedOperatorInfo info = new AppliedOperatorInfo(DummyMutateOperator.class);
		assertThat("",//
				info.toString(),//
				isEqualTo(AppliedOperatorInfo.TEXT_FORMAT.format(info).toString()));
	}
}
