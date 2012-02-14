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
import static com.xtructure.xutil.valid.ValidateUtils.containsElements;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.testng.annotations.Test;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XBorder;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestAbstractXBorder {
	private static final TestXComponent	S_COMP;
	private static final TestXComponent	T_COMP;
	private static final String			S_BASE;
	private static final String			T_BASE;
	private static final XAddress		S1, S2;
	private static final XAddress		T1, T2;
	private static final Set<XAddress>	SOURCES;
	private static final Set<XAddress>	TARGETS;
	static {
		S_COMP = new TestXComponent();
		T_COMP = new TestXComponent();
		S_BASE = "source";
		T_BASE = "target";
		S1 = new XAddressImpl(S_COMP, XId.newId(S_BASE).createChild(1));
		S2 = new XAddressImpl(S_COMP, XId.newId(S_BASE).createChild(2));
		T1 = new XAddressImpl(T_COMP, XId.newId(T_BASE).createChild(1));
		T2 = new XAddressImpl(T_COMP, XId.newId(T_BASE).createChild(2));
		SOURCES = new SetBuilder<XAddress>()//
				.add(S1, S2).newImmutableInstance();
		TARGETS = new SetBuilder<XAddress>()//
				.add(T1, T2).newImmutableInstance();
	}

	public final void constructorSucceeds() {
		assertThat("",//
				new TestXBorder(), isNotNull());
	}

	public final void getDataOnNewBorderReturnsEmptyMap() {
		final Map<XId, Map<XAddress, Object>> expected = new HashMap<XId, Map<XAddress, Object>>();

		assertThat("",//
				new TestXBorder().getData(T_COMP), isEqualTo(expected));
	}

	public final void associateXAddressXAddressSucceeds() {
		final TestXBorder TXB = new TestXBorder();
		final Map<XId, Map<XAddress, Object>> expected = new HashMap<XId, Map<XAddress, Object>>();
		final Map<XAddress, Object> target = new HashMap<XAddress, Object>();
		target.put(S1, null);
		expected.put(T1.getPartId(), target);

		assertThat("",//
				TXB.associate(S1, T1).getData(T_COMP), isEqualTo(expected));
	}

	public final void associateXAddressTransformXAddressSucceeds() {
		final TestXBorder TXB = new TestXBorder();
		final XBorder.Transform TRANS = new XBorder.Transform() {
			@Override
			public Object transform(Object orig) {
				return orig;
			}
		};
		final Map<XId, Map<XAddress, Object>> expected = new HashMap<XId, Map<XAddress, Object>>();
		final Map<XAddress, Object> target = new HashMap<XAddress, Object>();
		target.put(S1, null);
		expected.put(T1.getPartId(), target);

		assertThat("",//
				TXB.associate(S1, TRANS, T1).getData(T_COMP), isEqualTo(expected));
	}

	public final void associateXAddressSetXAddressSetSucceeds() {
		final TestXBorder TXB = new TestXBorder();
		final Map<XId, Map<XAddress, Object>> expected = new HashMap<XId, Map<XAddress, Object>>();
		for (XAddress t : TARGETS) {
			final Map<XAddress, Object> target = new HashMap<XAddress, Object>();
			for (XAddress s : SOURCES) {
				target.put(s, null);
			}
			expected.put(t.getPartId(), target);
		}

		assertThat("",//
				TXB.associate(SOURCES, TARGETS).getData(T_COMP), isEqualTo(expected));
	}

	public final void associateTwoAddressesWithOneTargetWorksAsExpected() {
		XId sId1 = XId.newId(S_BASE).createChild(1);
		XId sId2 = XId.newId(S_BASE).createChild(2);
		XId tId1 = XId.newId(T_BASE).createChild(1);
		AbstractStandardXComponent sourceComp = new TestXComponent(//
				new SetBuilder<XId>().add(sId1, sId2).newImmutableInstance(),//
				new HashSet<XId>());
		AbstractStandardXComponent targetComp = new TestXComponent(//
				new HashSet<XId>(),//
				new SetBuilder<XId>().add(tId1).newImmutableInstance());

		AbstractXBorder border = new TestXBorder();
		border.associate(//
				new XAddressImpl(sourceComp, sId1), new XAddressImpl(targetComp, tId1));
		border.associate(//
				new XAddressImpl(sourceComp, sId2), new XAddressImpl(targetComp, tId1));
		sourceComp.addBorder(border);
		targetComp.addBorder(border);

		// needed to prime sourcesSendingData list
		SimpleXTime<StandardTimePhase> time = new SimpleXTime<StandardTimePhase>(0, StandardTimePhase.PREPARE);
		sourceComp.update(time);
		targetComp.update(time);

		assertThat("",//
				((TestXComponent)targetComp).sourcesSendingData, containsElements(sId1, sId2));
	}

	static final class TestXBorder extends AbstractXBorder {}

	static final class TestXComponent extends AbstractStandardXComponent {
		static final XId	COMP_ID;
		static int			instanceCount;
		static {
			COMP_ID = XId.newId("dummyComponent");
			instanceCount = 0;
		}

		private List<XId>	sourcesSendingData;

		protected TestXComponent(Set<XId> sourceIds, Set<XId> targetIds) {
			super(COMP_ID.createChild(instanceCount++), sourceIds, targetIds);
			sourcesSendingData = new ArrayList<XId>();
		}

		public TestXComponent() {
			this(null, null);
		}

		@Override
		protected void addForeignData(XId targetId, XAddress sourceAddress, Object data) {
			sourcesSendingData.add(sourceAddress.getPartId());
		}

		@Override
		public Object getData(XId partId) {
			return null;
		}
	}

	static final class TestXSimulation extends AbstractStandardXSimulation {
		private static final Lock		PROGRESS_LOCK;
		private static final Condition	DEMO_COMPLETE;
		static {
			PROGRESS_LOCK = new ReentrantLock();
			DEMO_COMPLETE = PROGRESS_LOCK.newCondition();
		}

		static final XId				SIM_ID	= XId.newId("dummySim");

		protected TestXSimulation() {
			super(SIM_ID);
		}

		public void boundedRun(final long steps) {
			addListener(new AbstractXSimulationListener() {
				@Override
				public final void simulationTimeChanged(final XSimulation<?> sim, final XTime<?> time) {
					PROGRESS_LOCK.lock();
					try {
						if (time.getTick() >= steps) {
							pause();
							DEMO_COMPLETE.signalAll();
						}
					} finally {
						PROGRESS_LOCK.unlock();
					}
				}
			});

			init();
			run();
			PROGRESS_LOCK.lock();
			try {
				DEMO_COMPLETE.await();
			} catch (InterruptedException interruptedEx) {
				interruptedEx.printStackTrace();
			} finally {
				finish();
			}
		}

	}
}
