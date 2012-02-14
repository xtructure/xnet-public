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
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.testng.annotations.Test;

import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XSimulation.SimulationState;
import com.xtructure.xutil.id.XId;

@Test(groups = { "integration:xsim" })
public final class ITestTerminatingSimulation {
	/** */
	private static final Lock		LOCK			= new ReentrantLock();
	/** */
	private static final Condition	LOCK_CONDITION	= LOCK.newCondition();

	public final void tickTerminatorFinishesSimulation() throws InterruptedException {
		final Long targetTicks = 20l;
		AbstractStandardXSimulation sim = new AbstractStandardXSimulation(XId.newId("SIM")) {};
		TickXTerminator term = TickXTerminator.getInstance(XId.newId("TERMINATOR"), sim, targetTicks);
		sim.addComponent(term);
		sim.setTickDelay(0);
		sim.addListener(new AbstractXSimulationListener() {
			@Override
			public void simulationStateChanged(XSimulation<?> sim, SimulationState state) {
				super.simulationStateChanged(sim, state);
				if (SimulationState.FINISHED.equals(state)) {
					LOCK.lock();
					try {
						LOCK_CONDITION.signalAll();
					} finally {
						LOCK.unlock();
					}
				}
			}
		});
		sim.init();
		sim.run();
		LOCK.lock();
		try {
			LOCK_CONDITION.await();
		} finally {
			LOCK.unlock();
		}
		assertThat("",//
				sim.getTime().getTick(), isEqualTo(targetTicks + 1));
	}

	public void dataTerminatorFinishesSimulation() throws InterruptedException {
		final Object data = "finish";
		final XId compId = XId.newId("test comp");
		final XId dataId = XId.newId("DATA");
		final Set<XId> sourceIds = new HashSet<XId>();
		final Set<XId> targetIds = new HashSet<XId>();
		sourceIds.add(dataId);
		AbstractSimpleStandardXComponent comp = new AbstractSimpleStandardXComponent(compId, sourceIds, targetIds) {
			@Override
			public Object getData(XId partId) {
				if (dataId.equals(partId)) {
					return data;
				}
				return null;
			}

			@Override
			protected void setData(XId partId, Object data) {}
		};
		final XId terminatorId = XId.newId("data term");
		final com.xtructure.xutil.valid.Condition condition = isSameAs(data);
		DataXTerminator dataTerm = DataXTerminator.getInstance(terminatorId, dataId, condition);
		SimpleXBorder border = new SimpleXBorder();
		border.addComponent(comp);
		border.addComponent(dataTerm);
		border.associate(//
				new XAddressImpl(comp, dataId),//
				new XAddressImpl(dataTerm, dataId));

		AbstractStandardXSimulation sim = new AbstractStandardXSimulation(XId.newId("SIM")) {};
		sim.addComponent(comp);
		sim.addComponent(dataTerm);
		sim.setTickDelay(0);
		sim.addListener(new AbstractXSimulationListener() {
			@Override
			public void simulationStateChanged(XSimulation<?> sim, SimulationState state) {
				super.simulationStateChanged(sim, state);
				if (SimulationState.FINISHED.equals(state)) {
					LOCK.lock();
					try {
						LOCK_CONDITION.signalAll();
					} finally {
						LOCK.unlock();
					}
				}
			}
		});
		sim.init();
		sim.run();
		LOCK.lock();
		try {
			LOCK_CONDITION.await();
		} finally {
			LOCK.unlock();
		}
		assertThat("",//
				sim.getTime().getTick(), isEqualTo(1l));
	}
}
