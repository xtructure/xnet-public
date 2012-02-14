/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xbatch.
 *
 * xbatch is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xbatch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xbatch.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xbatch.process.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.testng.annotations.Test;

import com.xtructure.xbatch.batch.XBatch;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.impl.AbstractStandardXSimulation;
import com.xtructure.xsim.impl.TickXTerminator;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xbatch", "xbatch.process" })
public class UTestXBatchProcessor_SimThread {

	public final void constructorSucceeds() {
		assertThat("",//
				new XBatchProcessorImpl.SimConsumerThread(XBatchProcessorImpl.getInstance()), isNotNull());
	}

	public final void runSuccessfullyConsumesAllBatchedSimulations() {
		TestBatch batch = new TestBatch(20, 100);
		assertThat(batch.toString(),//
				batch.hasMoreSimulations(), isTrue());
		XBatchProcessorImpl proc = XBatchProcessorImpl.getInstance();
		proc.batch = batch;
		XBatchProcessorImpl.SimConsumerThread consumerThread = new XBatchProcessorImpl.SimConsumerThread(proc);
		consumerThread.run();
		assertThat(batch.toString(),//
				batch.hasMoreSimulations(), isFalse());
	}

	static class TestBatch implements XBatch {
		private static final Lock	SIM_LOCK	= new ReentrantLock();

		private final int			simCount;
		private final int			timeBound;
		private int					currentSim;

		public TestBatch(int simCount, int timeBound) {
			this.simCount = simCount;
			this.timeBound = timeBound;
			this.currentSim = 0;
		}

		@Override
		public XSimulation<?> getNextSimulation() {
			SIM_LOCK.lock();
			try {
				XId simId = XId.newId("SIM" + currentSim);
				AbstractStandardXSimulation sim = new AbstractStandardXSimulation(simId) {};
				sim.addComponent(TickXTerminator.getInstance(XId.newId("TERM" + currentSim), sim, timeBound));
				sim.setTickDelay(0);

				currentSim++;
				return sim;
			} finally {
				SIM_LOCK.unlock();
			}
		}

		@Override
		public boolean hasMoreSimulations() {
			SIM_LOCK.lock();
			try {
				return simCount - currentSim > 0;
			} finally {
				SIM_LOCK.unlock();
			}
		}

		@Override
		public int remaining() {
			SIM_LOCK.lock();
			try {
				return simCount - currentSim;
			} finally {
				SIM_LOCK.unlock();
			}
		}
	}
}
