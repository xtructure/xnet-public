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

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Set;

import org.testng.annotations.Test;

import com.xtructure.xsim.XAddress;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;
import static com.xtructure.xutil.valid.ValidateUtils.*;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestAbstractXReporter {
	private static final XId		REPORTER_ID;
	private static final XId		INTEGER_STAT_ID;
	private static final XId		DOUBLE_STAT_ID;
	private static final Set<XId>	STAT_IDS;
	static {
		REPORTER_ID = XId.newId("reporter");
		INTEGER_STAT_ID = XId.newId("integer");
		DOUBLE_STAT_ID = XId.newId("double");
		STAT_IDS = new SetBuilder<XId>()//
				.add(INTEGER_STAT_ID, DOUBLE_STAT_ID)//
				.newImmutableInstance();
	}

	public void constructorSucceeds() {
		assertThat("",//
				new TestReporter(), isNotNull());
	}

	public void addForeignDataAndGetDataBehaveAsExpected() {
		TestReporter reporter = new TestReporter();
		for (XId id : STAT_IDS) {
			assertThat("",//
					reporter.getData(id), isNull());
		}
		reporter.addForeignData(INTEGER_STAT_ID, null, Integer.valueOf(10));
		for (XId id : STAT_IDS) {
			assertThat("",//
					reporter.getData(id), isNull());
		}
		reporter.update();
		assertThat("",//
				reporter.getData(INTEGER_STAT_ID), isEqualTo(10));
		assertThat("",//
				reporter.getData(DOUBLE_STAT_ID), isNull());
	}

	public void prepareClearsObservations() {
		TestReporter reporter = new TestReporter();
		for (XId id : STAT_IDS) {
			assertThat("",//
					reporter.getData(id), isNull());
		}
		reporter.addForeignData(INTEGER_STAT_ID, null, Integer.valueOf(10));
		for (XId id : STAT_IDS) {
			assertThat("",//
					reporter.getData(id), isNull());
		}
		reporter.prepare();
		reporter.update();
		for (XId id : STAT_IDS) {
			assertThat("",//
					reporter.getData(id), isNull());
		}
	}

	static final class TestReporter extends AbstractStandardXReporter {
		protected TestReporter() {
			super(REPORTER_ID, STAT_IDS, STAT_IDS);
		}

		@Override
		public void update() {
			for (XId id : STAT_IDS) {
				if (observations.containsKey(id)) {
					statistics.put(id, observations.get(id));
				}
			}
		}

		@Override
		public void addForeignData(XId targetId, XAddress sourceAddress, Object data) {
			super.addForeignData(targetId, sourceAddress, data);
		}

		@Override
		public void write(Writer writer) throws IOException {}

		@Override
		public void write(PrintStream stream) {}

	}
}
