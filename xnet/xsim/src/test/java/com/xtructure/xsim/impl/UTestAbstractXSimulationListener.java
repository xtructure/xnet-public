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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.xtructure.xsim.XClock;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XSimulation.SimulationState;
import com.xtructure.xsim.impl.UTestAbstractXSimulation.DummyXSimulation;
import com.xtructure.xsim.impl.UTestXAddressImpl.DummyXComponent;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestAbstractXSimulationListener {
	private static final Logger					LOGGER;
	private static final DummyXComponent			COMP;
	private static final XSimulation<PhaseImpl>	SIM;
	private static final XId					SIM_ID;
	private static final XClock<PhaseImpl>		CLOCK;
	static {
		SIM_ID = XId.newId("SIM");
		CLOCK = new TestAbstractXClock.AbstractXClockImpl(PhaseImpl.values());
		LOGGER = LoggerFactory.getLogger("UTestAbstractXSimulationListener");
		SIM = new DummyXSimulation(SIM_ID, CLOCK);
		COMP = new DummyXComponent();
	}

	public final void constructorSucceeds() {
		assertThat("",//
				new TestXSimulationListener(), isNotNull());
	}

	public final void constructorLoggerSucceeds() {
		assertThat("",//
				new TestXSimulationListener(LOGGER), isNotNull());
	}

	public final void simulationComponentAddedSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener(LOGGER);
		SIM_LISTENER.simulationComponentAdded(SIM, COMP);
	}

	public final void simulationComponentAddedWithoutLoggerSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener();
		SIM_LISTENER.simulationComponentAdded(SIM, COMP);
	}

	public final void simulationComponentRemovedSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener(LOGGER);
		SIM_LISTENER.simulationComponentRemoved(SIM, COMP);
	}

	public final void simulationComponentRemovedWithoutLoggerSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener();
		SIM_LISTENER.simulationComponentRemoved(SIM, COMP);
	}

	public final void simulationStateChangedSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener(LOGGER);
		SIM_LISTENER.simulationStateChanged(SIM, SimulationState.INITIAL);
	}

	public final void simulationStateChangedWithoutLoggerSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener();
		SIM_LISTENER.simulationStateChanged(SIM, SimulationState.INITIAL);
	}

	public final void simulationTickDelayChangedSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener(LOGGER);
		SIM_LISTENER.simulationTickDelayChanged(SIM, 0);
	}

	public final void simulationTickDelayChangedWithoutLoggerSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener();
		SIM_LISTENER.simulationTickDelayChanged(SIM, 0);
	}

	public final void simulationTimeChangedChangedSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener(LOGGER);
		SIM_LISTENER.simulationTimeChanged(SIM, null);
	}

	public final void simulationTimeChangedChangedWithoutLoggerSucceeded() {
		final AbstractXSimulationListener SIM_LISTENER = new TestXSimulationListener();
		SIM_LISTENER.simulationTimeChanged(SIM, null);
	}

	static final class TestXSimulationListener extends AbstractXSimulationListener {
		public TestXSimulationListener() {
			super();
		}

		public TestXSimulationListener(Logger logger) {
			super(logger);
		}
	}
}
