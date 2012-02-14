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

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang.NotImplementedException;
import org.testng.annotations.Test;

import com.xtructure.xsim.XClock;
import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XSimulation.SimulationState;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.impl.UTestAbstractXSimulationListener.TestXSimulationListener;
import com.xtructure.xsim.impl.UTestXAddressImpl.DummyXComponent;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@SuppressWarnings("unchecked")
@Test(groups = { "unit:xsim" })
public final class UTestAbstractXSimulation {
	private static final XId								SIM_ID;
	private static final XClock<PhaseImpl>					CLOCK;
	private static final DummyXComponent					COMP_1, COMP_2;
	private static final Collection<XComponent<PhaseImpl>>	COMPS;
	static {
		SIM_ID = XId.newId("SIM");
		CLOCK = new TestAbstractXClock.AbstractXClockImpl(PhaseImpl.values());
		COMP_1 = new DummyXComponent();
		COMP_2 = new DummyXComponent();
		COMPS = new SetBuilder<XComponent<PhaseImpl>>()//
				.add(COMP_1, COMP_2).newImmutableInstance();
	}

	public final void constructorXIdXClockSucceeds() {
		assertThat("",//
				new DummyXSimulation(SIM_ID, CLOCK), isNotNull());
	}

	public final void constructorXIdXClockComponentsSucceeds() {
		assertThat("",//
				new DummyXSimulation(SIM_ID, CLOCK, COMPS), isNotNull());
	}

	public final void addComponentSucceeds() {
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, CLOCK);
		Collection<XComponent<PhaseImpl>> expected = new SetBuilder<XComponent<PhaseImpl>>().add(COMP_1).newImmutableInstance();
		SIM.addComponent(COMP_1);
		assertThat("",//
				SIM.getComponents(), isEqualTo(expected));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void addListenerOnNullThrowsException() {
		new DummyXSimulation(SIM_ID, CLOCK).addListener(null);
	}

	public final void addListenerSucceeds() {
		DummyXSimulation sim = new DummyXSimulation(SIM_ID, CLOCK);
		sim.addListener(new TestXSimulationListener());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void removeListenerOnNullThrowsException() {
		new DummyXSimulation(SIM_ID, CLOCK).removeListener(null);
	}

	public final void removeListenerSucceeds() {
		DummyXSimulation sim = new DummyXSimulation(SIM_ID, CLOCK);
		sim.removeListener(new TestXSimulationListener());
	}

	public final void getComponentsReturnsExpectedSet() {
		assertThat("",//
				new DummyXSimulation(SIM_ID, CLOCK, COMPS).getComponents(), isEqualTo(COMPS));
	}

	public final void getIdReturnsExpectedXId() {
		assertThat("",//
				new DummyXSimulation(SIM_ID, CLOCK).getId(), isEqualTo(SIM_ID));
	}

	public final void getSimulationStateReturnsExpectedState() throws InterruptedException {
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, CLOCK);
		assertThat("",//
				SIM.getSimulationState(), isEqualTo(SimulationState.INITIAL));
		SIM.init();
		assertThat("",//
				SIM.getSimulationState(), isEqualTo(SimulationState.READY));
		SIM.step();
		while (SimulationState.STEPPING.equals(SIM.getSimulationState())) {}
		assertThat("",//
				SIM.getSimulationState(), isEqualTo(SimulationState.READY));
		SIM.run();
		assertThat("",//
				SIM.getSimulationState(), isEqualTo(SimulationState.RUNNING));
		SIM.step();
		while (SimulationState.STEPPING.equals(SIM.getSimulationState())) {}
		assertThat("",//
				SIM.getSimulationState(), isEqualTo(SimulationState.READY));
		SIM.pause();
		assertThat("",//
				SIM.getSimulationState(), isEqualTo(SimulationState.READY));
		SIM.finish();
		assertThat("",//
				SIM.getSimulationState(), isEqualTo(SimulationState.FINISHED));
	}

	public final void getTickDelayReturnsExpectedInt() {
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, CLOCK);
		assertThat("",//
				SIM.getTickDelay(), isEqualTo(DummyXSimulation.DEFAULT_TICK_DELAY));
		SIM.setTickDelay(DummyXSimulation.DEFAULT_TICK_DELAY / 2);
		assertThat("",//
				SIM.getTickDelay(), isEqualTo(DummyXSimulation.DEFAULT_TICK_DELAY / 2));
	}

	public final void simRunTest() throws InterruptedException {
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, CLOCK);
		SIM.addListener(new TestXSimulationListener());
		SIM.init();
		SIM.run();
		SIM.addComponent(COMP_1);
		SIM.setTickDelay(DummyXSimulation.DEFAULT_TICK_DELAY / 2);
		SIM.removeComponent(COMP_1);
		Thread.sleep(200);
		SIM.step();
		Thread.sleep(200);
		SIM.finish();
	}

	public final void setTickDelayToNegDoesDefault() {
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, CLOCK);
		assertThat("",//
				SIM.getTickDelay(), isEqualTo(DummyXSimulation.DEFAULT_TICK_DELAY));
		SIM.setTickDelay(-1);
		assertThat("",//
				SIM.getTickDelay(), isEqualTo(DummyXSimulation.DEFAULT_TICK_DELAY));
		SIM.setTickDelay(DummyXSimulation.DEFAULT_TICK_DELAY);
	}

	public final void getTimeReturnsExpectedTime() {
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, CLOCK);
		assertThat("",//
				SIM.getTime(), isEqualTo(CLOCK.getTime()));
		final XClock<PhaseImpl> NEW_CLOCK = new TestAbstractXClock.AbstractXClockImpl(PhaseImpl.values());
		SIM.setTime(NEW_CLOCK.increment());
		assertThat("",//
				SIM.getTime(), isEqualTo(NEW_CLOCK.getTime()));
	}

	public final void removeComponentSucceeds() {
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, CLOCK, COMPS);
		SIM.removeComponent(COMP_1);
		SIM.removeComponent(COMP_2);
		final Collection<XComponent<PhaseImpl>> EMPTY = new HashSet<XComponent<PhaseImpl>>();
		assertThat("", //
				SIM.getComponents(), isEqualTo(EMPTY));
	}

	@Test(expectedExceptions = NotImplementedException.class)
	public final void runWithBadListenerFails() {
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, CLOCK);
		AbstractXSimulationListener l = new AbstractXSimulationListener() {
			@Override
			public void simulationStateChanged(final XSimulation<?> sim, final SimulationState state) {
				throw new NotImplementedException();
			}
		};
		SIM.addListener(l);
		SIM.init();
		SIM.run();
	}

	@Test(expectedExceptions = NotImplementedException.class)
	public final void setTimeWithBackClockFails() {
		XClock<PhaseImpl> clock = new XClock<PhaseImpl>() {
			private XTime<PhaseImpl>	time;

			@Override
			public XTime<PhaseImpl> getTime() {
				throw new NotImplementedException();
			}

			@Override
			public XTime<PhaseImpl> increment() {
				return time;
			}

			@Override
			public void setTime(XTime<PhaseImpl> time) {
				throw new IllegalArgumentException();
			}

			@Override
			public int compareTo(XClock<PhaseImpl> o) {
				return 0;
			}
		};
		final AbstractXSimulation<PhaseImpl> SIM = new DummyXSimulation(SIM_ID, clock);
		SIM.getTime();
	}

	static final class DummyXSimulation extends AbstractXSimulation<PhaseImpl> {
		protected DummyXSimulation(XId id, XClock<PhaseImpl> clock) {
			super(id, clock);
		}

		protected DummyXSimulation(XId id, XClock<PhaseImpl> clock, Collection<XComponent<PhaseImpl>> components) {
			super(id, clock, components);
		}
	}
}
