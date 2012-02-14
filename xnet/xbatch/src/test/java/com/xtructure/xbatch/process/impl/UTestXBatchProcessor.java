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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

import com.xtructure.xbatch.batch.XBatch;
import com.xtructure.xsim.XSimulation;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xbatch", "xbatch.process" })
public class UTestXBatchProcessor {

	public final void getInstanceBatchSucceeds() {
		assertThat("",//
				XBatchProcessorImpl.getInstance(), isNotNull());
	}

	public final void getInstanceBatchNumThreadsSucceeds() {
		assertThat("",//
				XBatchProcessorImpl.getInstance(1), isNotNull());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void getInstanceOnNonPositiveNumThreadsThrowsException() {
		XBatchProcessorImpl.getInstance(0);
	}

	public final void startWithBlockingSucceeds() {
		XBatchProcessorImpl.getInstance(1).start(new DummyBatch(), true);
	}

	public final void startWithoutBlockingSucceeds() {
		XBatchProcessorImpl.getInstance(1).start(new DummyBatch(), false);
	}

	static final class DummyBatch implements XBatch {
		@Override
		public XSimulation<?> getNextSimulation() {
			return null;
		}

		@Override
		public boolean hasMoreSimulations() {
			return false;
		}

		@Override
		public int remaining() {
			return 0;
		}

	}

}
