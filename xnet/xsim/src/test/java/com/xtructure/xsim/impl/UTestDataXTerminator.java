/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xsim.
 *
 * xsim is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xsim is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xsim.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xsim.impl;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.valid.Condition;

import static com.xtructure.xutil.valid.ValidateUtils.*;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestDataXTerminator {
	private static final XId					TERM_ID;
	private static final XId					DATA_ID;
	private static final Condition				CONDITION;
	private static final Map<XId, Condition>	CONDITION_MAP;
	static {
		TERM_ID = XId.newId("TERM");
		DATA_ID = XId.newId("DATA");
		CONDITION = isNotNull();
		CONDITION_MAP = new HashMap<XId, Condition>();
		CONDITION_MAP.put(DATA_ID, CONDITION);
	}

	public void getInstanceWithConditionMapSucceeds() {
		assertThat("",//
				DataXTerminator.getInstance(TERM_ID, CONDITION_MAP),//
				isNotNull());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getInstanceWithNullConditionMapThrowsException() {
		DataXTerminator.getInstance(TERM_ID, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getInstanceWithEmptyConditionMapThrowsException() {
		DataXTerminator.getInstance(TERM_ID, new HashMap<XId, Condition>());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getInstanceWithConditionMapWithNullConditionThrowsException() {
		Map<XId, Condition> conditionMap = new HashMap<XId, Condition>();
		conditionMap.put(DATA_ID, null);
		DataXTerminator.getInstance(TERM_ID, conditionMap);
	}

	public void getInstanceWithXIdAndConditionSucceeds() {
		assertThat("",//
				DataXTerminator.getInstance(TERM_ID, DATA_ID, CONDITION),//
				isNotNull());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getInstanceWithNullDataIdThrowsException() {
		DataXTerminator.getInstance(TERM_ID, null, CONDITION);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void getInstanceWithNullConditionThrowsException() {
		DataXTerminator.getInstance(TERM_ID, DATA_ID, null);
	}

	public void getDataReturnsExpectedData() {
		DataXTerminator dataTerm = DataXTerminator.getInstance(TERM_ID, DATA_ID, CONDITION);
		assertThat("",//
				dataTerm.getData(DATA_ID),//
				isEqualTo(false));
		assertThat("",//
				dataTerm.getData(TERM_ID),//
				isNull());
	}
}
