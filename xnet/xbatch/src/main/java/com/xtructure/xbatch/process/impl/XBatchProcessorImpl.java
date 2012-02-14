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

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThan;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.xtructure.xbatch.batch.XBatch;
import com.xtructure.xbatch.process.XBatchProcessor;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XSimulation.SimulationState;
import com.xtructure.xsim.impl.AbstractXSimulationListener;
import com.xtructure.xutil.RuntimeDuration;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.format.RuntimeTextFormat;

/**
 * @author Luis Guimbarda
 * 
 */
public final class XBatchProcessorImpl implements XBatchProcessor {
	/** */
	private static final XLogger			LOGGER	= XLogger.getInstance(XBatchProcessorImpl.class);
	/** default number of sim threads to use */
	private static final int				DEFAULT_MAX_THREADS;
	/** lock used while checking for more simulations in the batch */
	private static final Lock				BATCH_LOCK;
	/** lock used while running the processor and blocking */
	private static final Lock				RUNNING_LOCK;
	/** condition used to block while processing the batch */
	private static final Condition			FINISHED_RUNNING;
	/** text formatter for runtime durations */
	private static final RuntimeTextFormat	RTF;
	static {
		DEFAULT_MAX_THREADS = Runtime.getRuntime().availableProcessors();
		BATCH_LOCK = new ReentrantLock();
		RUNNING_LOCK = new ReentrantLock();
		FINISHED_RUNNING = RUNNING_LOCK.newCondition();
		RTF = RuntimeTextFormat.getInstance();
	}

	/**
	 * @param maxThreads
	 *            number of consumer threads with which to consume batch
	 *            simulations
	 * @return a new XBatchProcessor
	 */
	public static XBatchProcessorImpl getInstance(int maxThreads) {
		return new XBatchProcessorImpl(maxThreads);
	}

	/**
	 * @return a new XBatchProcessor
	 */
	public static XBatchProcessorImpl getInstance() {
		return new XBatchProcessorImpl(DEFAULT_MAX_THREADS);
	}

	/** number of consumer threads with which to consume batch simulations */
	private final int			numThreads;
	/** an array of threads to consume batch simulations */
	private SimConsumerThread[]	simThreads;
	/** a counter for running threads */
	private int					runningThreads;
	/** */
	XBatch						batch;

	/**
	 * Creates a new XBatchProcessor object.
	 * 
	 * @param maxThreads
	 *            number of consumer threads with which to consume batch
	 *            simulations
	 */
	private XBatchProcessorImpl(int maxThreads) {
		validateArg("maxThreads", maxThreads, isGreaterThan(0));
		this.numThreads = maxThreads;
		this.simThreads = null;
		this.runningThreads = 0;
		this.batch = null;
	}

	/**
	 * Creates the array of SimConsumerThreads.
	 */
	private void buildSimThreads() {
		LOGGER.trace("begin %s.buildSimThreads()", getClass().getSimpleName());

		this.simThreads = new SimConsumerThread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			this.simThreads[i] = new SimConsumerThread(this);
		}

		LOGGER.trace("begin %s.buildSimThreads()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xbatch.process.XBatchProcessor#start(com.xtructure.xbatch
	 * .batch.XBatch, boolean)
	 */
	@Override
	public void start(XBatch batch, boolean blocking) {
		LOGGER.trace("begin %s.start(%s, %s)", getClass().getSimpleName(), batch, blocking);

		validateArg("batch", batch, isNotNull());
		buildSimThreads();
		this.batch = batch;
		RUNNING_LOCK.lock();
		try {
			for (SimConsumerThread simThread : simThreads) {
				simThread.start();
			}
			if (blocking) {
				FINISHED_RUNNING.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			RUNNING_LOCK.unlock();
		}

		LOGGER.trace("end %s.start()", getClass().getSimpleName());
	}

	/** Individual thread to consume batch simulations */
	static final class SimConsumerThread implements Runnable {
		private final Lock					simLock;
		private final Condition				simFinished;
		private final Thread				thread;
		private final XBatchProcessorImpl	processor;

		private XSimulation<?>				sim	= null;

		public SimConsumerThread(XBatchProcessorImpl processor) {
			this.simLock = new ReentrantLock();
			this.simFinished = simLock.newCondition();
			this.thread = new Thread(this);
			this.processor = processor;
		}

		public void start() {
			LOGGER.trace("begin %s.start()", getClass().getSimpleName());

			RUNNING_LOCK.lock();
			try {
				processor.runningThreads++;
				thread.start();
			} finally {
				RUNNING_LOCK.unlock();
			}

			LOGGER.trace("end %s.start()", getClass().getSimpleName());
		}

		@Override
		public void run() {
			LOGGER.trace("begin %s.run()", getClass().getSimpleName());

			final long batchStartTime = System.currentTimeMillis();
			while (true) {
				// keep grabbing simulations until there are no more
				BATCH_LOCK.lock();
				try {
					if (processor.batch.hasMoreSimulations()) {
						sim = processor.batch.getNextSimulation();
					} else {
						break;
					}
				} finally {
					BATCH_LOCK.unlock();
				}

				// notify simFinished condition when simulation finished
				// output sim start and finish runtime to console
				sim.addListener(new AbstractXSimulationListener() {
					private long	simStartTime	= 0;

					@Override
					public void simulationStateChanged(XSimulation<?> sim, SimulationState state) {
						LOGGER.trace("begin %s.simulationStateChanged()", getClass().getSimpleName());

						if (SimulationState.RUNNING.equals(state)) {
							LOGGER.info("%s started (remaining: %d)",//
									sim.getId().toString(),//
									processor.batch.remaining());
							simStartTime = System.currentTimeMillis();
						}
						if (SimulationState.FINISHED.equals(state)) {
							long currentTime = System.currentTimeMillis();
							LOGGER.info("%s finished (simTime: %s, batchTime: %s)",//
									sim.getId().toString(),//
									RTF.format(new RuntimeDuration(simStartTime, currentTime)),//
									RTF.format(new RuntimeDuration(batchStartTime, currentTime)));
						}
						simLock.lock();
						try {
							if (SimulationState.FINISHED.equals(state)) {
								simFinished.signalAll();
							}
						} finally {
							simLock.unlock();
						}

						LOGGER.trace("end %s.simulationStateChanged()", getClass().getSimpleName());
					}
				});

				// run simulation
				simLock.lock();
				sim.init();
				sim.run();
				try {
					simFinished.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					simLock.unlock();
				}
			}
			// no more simulations. if this is that last running simThread,
			// notify FINISHED_RUNNING condition
			RUNNING_LOCK.lock();
			try {
				processor.runningThreads--;
				if (processor.runningThreads == 0) {
					// this is the last running thread
					FINISHED_RUNNING.signalAll();
				}
			} finally {
				RUNNING_LOCK.unlock();
			}

			LOGGER.trace("end %s.run()", getClass().getSimpleName());
		}
	}
}
