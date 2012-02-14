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
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;

import org.testng.annotations.Test;

import com.xtructure.xbatch.batch.XBatch;
import com.xtructure.xbatch.batch.XBatchGenerator;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "integration:xbatch", "xbatch.process" })
public class ITestXBatchSupervisor {

	public void testSupervisorRunSuccessful() {
		TestBatchGenerator batchGen = new TestBatchGenerator();
		XBatchSupervisorImpl supervisor = XBatchSupervisorImpl.getInstance(//
				XBatchProcessorImpl.getInstance(1),//
				new TestBatchGenerator.TestTerminalCondition(10));

		supervisor.start(batchGen);
		assertThat("",//
				batchGen.batchesGenerated, isEqualTo(10));

		batchGen.reset();
		assertThat("",//
				batchGen.batchesGenerated, isEqualTo(0));

		supervisor.start(batchGen);
		assertThat("",//
				batchGen.batchesGenerated, isEqualTo(10));
	}

	static final class TestBatchGenerator implements XBatchGenerator {
		private int	batchesGenerated;

		public TestBatchGenerator() {
			batchesGenerated = 0;
		}

		@Override
		public XBatch generateBatch() {
			batchesGenerated++;
			return new XBatch() {
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
			};
		}

		@Override
		public void reset() {
			batchesGenerated = 0;
		}

		static final class TestTerminalCondition implements Condition {
			private final int	maxBatchCount;

			public TestTerminalCondition(int maxBatchCount) {
				this.maxBatchCount = maxBatchCount;
			}

			@Override
			public boolean isSatisfiedBy(Object obj) {
				return ((TestBatchGenerator) obj).batchesGenerated >= maxBatchCount;
			}
		}

		@Override
		public void updateGenerator(XBatch batch) {}
	}
}
