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

import static com.xtructure.xutil.valid.ValidateUtils.*;
import java.util.Collection;

import org.testng.annotations.Test;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestAbstractStandardXSimulation {
	private static final XId										SIM_ID, COMP_ID;
	private static final StandardXClock								CLOCK;
	private static final Collection<XComponent<StandardTimePhase>>	COMPS;
	static {
		SIM_ID = XId.newId("SIM");
		COMP_ID = XId.newId("COMP");
		CLOCK = new StandardXClock();
		AbstractStandardXComponent comp = new AbstractStandardXComponent(COMP_ID, null, null) {
			@Override
			protected void addForeignData(XId targetId, XAddress sourceAddress, Object data) {}

			@Override
			public Object getData(XId partId) {
				return null;
			}
		};
		COMPS = new SetBuilder<XComponent<StandardTimePhase>>()//
				.add(comp)//
				.newImmutableInstance();
	}

	public final void constructorXIdSucceeds() {
		assertThat("",//
				new DummyStandardXSimulation(SIM_ID), isNotNull());
	}

	public final void constructorXIdCollectionSucceeds() {
		assertThat("",//
				new DummyStandardXSimulation(SIM_ID, COMPS), isNotNull());
	}

	public final void constructorXIdClockSucceeds() {
		assertThat("",//
				new DummyStandardXSimulation(SIM_ID, CLOCK), isNotNull());
	}

	public final void constructorXIdClockCollectionSucceeds() {
		assertThat("",//
				new DummyStandardXSimulation(SIM_ID, CLOCK, COMPS), isNotNull());
	}

	static final class DummyStandardXSimulation extends AbstractStandardXSimulation {

		protected DummyStandardXSimulation(//
				XId id) {
			super(id);
		}

		protected DummyStandardXSimulation(//
				XId id,//
				Collection<XComponent<StandardTimePhase>> components) {
			super(id, components);
		}

		protected DummyStandardXSimulation(//
				XId id,//
				StandardXClock clock) {
			super(id, clock);
		}

		protected DummyStandardXSimulation(//
				XId id,//
				StandardXClock clock,//
				Collection<XComponent<StandardTimePhase>> components) {
			super(id, clock, components);
		}

	}
}
