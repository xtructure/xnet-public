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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestAbstractStandardXTerminator {
	static final Object[][]	FOREIGN_DATA;

	static {
		FOREIGN_DATA = TestUtils.unionData(//
//				TestUtils.createParameters(DummyXTerminator.IS_NULL_CONDITION_ID, null, true),//
//				TestUtils.createParameters(DummyXTerminator.IS_NULL_CONDITION_ID, new Object(), false),//
				TestUtils.createParameters(DummyXTerminator.IS_GEQ_0_CONDITION_ID, null, false),//
				TestUtils.createParameters(DummyXTerminator.IS_GEQ_0_CONDITION_ID, -1, false),//
				TestUtils.createParameters(DummyXTerminator.IS_GEQ_0_CONDITION_ID, 0, true),//
				TestUtils.createParameters(DummyXTerminator.IS_GEQ_0_CONDITION_ID, 1, true),//
				TestUtils.createParameters(DummyXTerminator.SAYS_STRING_CONDITION_ID, null, false),//
				TestUtils.createParameters(DummyXTerminator.SAYS_STRING_CONDITION_ID, "", false),//
				TestUtils.createParameters(DummyXTerminator.SAYS_STRING_CONDITION_ID, "string", true));
	}

	public final void constructorSucceeds() {
		assertThat("",//
				new DummyXTerminator(), isNotNull());
	}

	public final void terminalConditionReachedInitiallyReturnsFalse() {
		assertThat("",//
				new DummyXTerminator().terminalConditionReached(), isFalse());
	}

	@Test(dataProvider = "foreignData")
	public final void addForeignDataCausesExpectedTerminalState(XId conditionId, Object data, boolean expected) {
		DummyXTerminator term = new DummyXTerminator();
		term.addForeignData(conditionId, null, data);
		assertThat("",//
				term.terminalConditionReached(), isEqualTo(expected));
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "foreignData")
	private final Object[][] foreignData() {
		return FOREIGN_DATA;
	}

	static final class DummyXTerminator extends AbstractStandardXTerminator {
		static final XId					COMPONENT_ID;
		static final XId					IS_NULL_CONDITION_ID;
		static final XId					IS_GEQ_0_CONDITION_ID;
		static final XId					SAYS_STRING_CONDITION_ID;
		static final Condition				IS_NULL_CONDITION;
		static final Condition				IS_GEQ_0_CONDITION;
		static final Condition				SAYS_STRING_CONDITION;
		static final Map<XId, Condition>	CONDITION_MAP;

		static {
			COMPONENT_ID = XId.newId("TestXTerminator");
			IS_NULL_CONDITION_ID = XId.newId("is null condition");
			IS_GEQ_0_CONDITION_ID = XId.newId("is >= 0 condition");
			SAYS_STRING_CONDITION_ID = XId.newId("is \"string\" condition");
			IS_NULL_CONDITION = isNull();
			IS_GEQ_0_CONDITION = isGreaterThanOrEqualTo(0);
			SAYS_STRING_CONDITION = isEqualTo("string");
			CONDITION_MAP = new MapBuilder<XId, Condition>()//
					.put(IS_NULL_CONDITION_ID, IS_NULL_CONDITION)//
					.put(IS_GEQ_0_CONDITION_ID, IS_GEQ_0_CONDITION)//
					.put(SAYS_STRING_CONDITION_ID, SAYS_STRING_CONDITION)//
					.newImmutableInstance();
		}

		protected DummyXTerminator() {
			super(COMPONENT_ID, CONDITION_MAP);
		}

		@Override
		public Object getData(XId partId) {
			return null;
		}
	}
}
