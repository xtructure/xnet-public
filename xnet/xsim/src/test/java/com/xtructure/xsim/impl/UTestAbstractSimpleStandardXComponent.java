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
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Set;

import org.testng.annotations.Test;

import com.xtructure.xsim.XAddress;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestAbstractSimpleStandardXComponent {
	private static final XId							COMP_ID, S_ID, T_ID;
	private static final XAddress						S_ADD;
	private static final Set<XId>						S_IDS, T_IDS;
	private static final TestSimpleStandardXComponent	TEST_COMP;
	static {
		COMP_ID = XId.newId("COMP");
		S_ID = XId.newId("SRC");
		T_ID = XId.newId("TRG");
		S_IDS = new SetBuilder<XId>().add(S_ID).newImmutableInstance();
		T_IDS = new SetBuilder<XId>().add(T_ID).newImmutableInstance();
		TEST_COMP = new TestSimpleStandardXComponent(COMP_ID, S_IDS, T_IDS);
		S_ADD = new XAddressImpl(TEST_COMP, S_ID);
	}

	public final void constuctorSucceeds() {
		assertThat("",//
				new TestSimpleStandardXComponent(COMP_ID, null, null), isNotNull());
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public final void addForeignDataOnNullTargetXIdThrowsException() {
		TEST_COMP.addForeignData(null, S_ADD, new Object());
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public final void addForeignDataOnUnknownTargetXIdThrowsException() {
		TEST_COMP.addForeignData(T_ID.createChild(0), S_ADD, new Object());
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public final void addForeignDataOnNullDataAfterForbidNullThrowsException() {
		TestSimpleStandardXComponent comp = new TestSimpleStandardXComponent(COMP_ID, S_IDS, T_IDS);
		comp.forbidNull(T_ID);
		comp.addForeignData(T_ID, S_ADD, null);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public final void addForeignDataOnWrongDataThrowsException() {
		TestSimpleStandardXComponent comp = new TestSimpleStandardXComponent(COMP_ID, S_IDS, T_IDS);
		comp.setDataType(T_ID, Integer.class);
		comp.addForeignData(T_ID, S_ADD, Double.valueOf(0.0));
	}

	public final void allowNullSucceeds() {
		TestSimpleStandardXComponent comp = new TestSimpleStandardXComponent(COMP_ID, S_IDS, T_IDS);
		comp.forbidNull(T_ID);
		comp.allowNull(T_ID);
		comp.addForeignData(T_ID, S_ADD, null);
	}

	public final void clearDataTypeSucceeds() {
		TestSimpleStandardXComponent comp = new TestSimpleStandardXComponent(COMP_ID, S_IDS, T_IDS);
		comp.setDataType(T_ID, Integer.class);
		comp.clearDataType(T_ID);
		comp.addForeignData(T_ID, S_ADD, Double.valueOf(0.0));
	}

	public final void getForeignDataSucceeds() {
		TEST_COMP.getForeignData(T_ID);
	}

	public final void informBlankTargetsSucceeds() {
		TestSimpleStandardXComponent comp = new TestSimpleStandardXComponent(COMP_ID, S_IDS, T_IDS);
		comp.blankTarget(T_ID);
		comp.setInformBlankTargets();
		comp.informBlankTargets();
	}

	public final void informNonBlankTargetsSucceeds() {
		TestSimpleStandardXComponent comp = new TestSimpleStandardXComponent(COMP_ID, S_IDS, T_IDS);
		comp.clearInformBlankTargets();
		comp.addForeignData(T_ID, S_ADD, new Object());
		comp.informNonBlankTargets();
	}

	public final void prepareBeforeHookClearsForeignData() {
		TEST_COMP.addForeignData(T_ID, null, Integer.valueOf(1));
		assertThat("",//
				TEST_COMP.hasForeignData(T_ID), isTrue());
		TEST_COMP.prepareBeforeHook();
		assertThat("",//
				TEST_COMP.hasForeignData(T_ID), isFalse());
	}

	public final void updateSucceeds() {
		new TestSimpleStandardXComponent(COMP_ID, null, null).update();
	}

	static final class TestSimpleStandardXComponent extends AbstractSimpleStandardXComponent {
		protected TestSimpleStandardXComponent(XId id, Set<XId> sourceIds, Set<XId> targetIds) {
			super(id, sourceIds, targetIds);
			data = null;
		}

		private Object	data;

		@Override
		protected void setData(XId partId, Object data) {
			this.data = data;
		}

		@Override
		public Object getData(XId partId) {
			return data;
		}

		@Override
		public void update() {
			super.update();
		}
	}
}
