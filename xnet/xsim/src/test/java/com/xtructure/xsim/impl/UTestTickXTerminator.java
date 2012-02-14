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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import org.testng.annotations.Test;

import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestTickXTerminator {
	static final XId							TERM_ID;
	static final AbstractStandardXSimulation	SIM;
	static final long							TICK_BOUND;
	static {
		TERM_ID = XId.newId("term id");
		SIM = new AbstractStandardXSimulation(XId.newId("sim id")) {};
		TICK_BOUND = 10l;
	}

	public final void getInstanceSucceeds() {
		assertThat("",//
				TickXTerminator//
						.getInstance(TERM_ID, SIM, TICK_BOUND),//
				isNotNull());
	}

	public final void getDataReturnsExpectedValue() {
		assertThat("",//
				TickXTerminator.getInstance(TERM_ID, SIM, TICK_BOUND)//
						.getData(TickXTerminator.TICK_BOUND_ID),//
				isEqualTo(TICK_BOUND));
		assertThat("",//
				TickXTerminator.getInstance(TERM_ID, SIM, TICK_BOUND)//
						.getData(null),//
				isNull());
	}

	public final void getTickBoundReturnsExpectedLong() {
		assertThat("",//
				TickXTerminator.getInstance(TERM_ID, SIM, TICK_BOUND)//
						.getTickBound(),//
				isEqualTo(TICK_BOUND));
	}
}
