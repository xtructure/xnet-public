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

import com.xtructure.xbatch.batch.XBatch;
import com.xtructure.xbatch.batch.XBatchGenerator;
import com.xtructure.xbatch.process.XBatchSupervisor;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
public class XBatchSupervisorImpl implements XBatchSupervisor {
	/** */
	private static final XLogger	LOGGER	= XLogger.getInstance(XBatchSupervisorImpl.class);

	/**
	 * @param batchProcessor
	 *            the batch processor for the new supervisor.
	 * @param terminalCondition
	 *            the terminal condition for the new supervisor.
	 * @return a new XBatchSupervisor
	 */
	public static final XBatchSupervisorImpl getInstance(XBatchProcessorImpl batchProcessor, Condition terminalCondition) {
		return new XBatchSupervisorImpl(batchProcessor, terminalCondition);
	}

	/** this supervisor's batch processor */
	private final XBatchProcessorImpl	batchProcessor;
	/** this supervisor's terminal condition */
	private final Condition				terminalCondition;

	/**
	 * Creates a new XBatchSupervisor with the given generator.
	 * 
	 * @param batchProcessor
	 *            the batch processor for the new supervisor.
	 * @param terminalCondition
	 *            the terminal condition for the new supervisor.
	 */
	private XBatchSupervisorImpl(XBatchProcessorImpl batchProcessor, Condition terminalCondition) {
		this.batchProcessor = batchProcessor;
		this.terminalCondition = terminalCondition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xbatch.process.XBatchSupervisor#start(com.xtructure.xbatch
	 * .batch.XBatchGenerator)
	 */
	@Override
	public void start(XBatchGenerator batchGenerator) {
		LOGGER.trace("begin %s.start(%s)", getClass().getSimpleName(), batchGenerator);

		while (!terminalCondition.isSatisfiedBy(batchGenerator)) {
			XBatch batch = batchGenerator.generateBatch();
			batchProcessor.start(batch, true);
			batchGenerator.updateGenerator(batch);
		}

		LOGGER.trace("end %s.start()", getClass().getSimpleName());
	}
}
