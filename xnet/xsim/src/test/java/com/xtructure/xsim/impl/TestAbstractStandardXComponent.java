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
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.NotImplementedException;
import org.testng.annotations.Test;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XBorder;
import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

@Test(groups = { "unit:xsim" })
public class TestAbstractStandardXComponent {
	private static final XId		COMPONENT_ID	= XId.newId("component");

	private static final XId		SOURCE_ID		= XId.newId("source");

	private static final XId		TARGET_ID		= XId.newId("target");

	private static final XAddress	XADDRESS		= new MockXAddress();

	private static final Object		DATA			= new Object();

	// @BeforeClass
	// public void setUp() {
	// // code that will be invoked when this test is instantiated
	// }

	@Test
	public void testRetrieveForeignData() {
		final AbstractStandardXComponent component = new MockComponent(COMPONENT_ID, Collections.singleton(SOURCE_ID), Collections.singleton(TARGET_ID));

		final XBorder border = new MockBorder((MockComponent) component);

		component.addBorder(border);

		component.retrieveForeignData();
	}

	public final void equalsOnNullReturnsFalse() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		assertThat("",//
				MC.equals(null), isFalse());
	}

	public final void equalsOnNonComponenentReturnsFalse() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		assertThat("",//
				MC.equals(Integer.valueOf(0)), isFalse());
	}

	public final void equalsOnSelfReturnsTrue() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		assertThat("",//
				MC.equals(MC), isTrue());
	}

	public final void equalsOnOtherComponentReturnsExpectedBoolean() {
		final MockComponent MC1 = new MockComponent(COMPONENT_ID, null, null);
		final MockComponent MC2 = new MockComponent(COMPONENT_ID.createChild(0), null, null);
		assertThat("",//
				MC1.equals(MC2), isFalse());
	}

	public final void getBordersReturnsExpectedSet() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		XBorder border = new MockBorder(MC);
		Set<XBorder> borders = new SetBuilder<XBorder>().add(border).newImmutableInstance();
		MC.addBorder(border);
		assertThat("",//
				MC.getBorders(), isEqualTo(borders));
	}

	public final void getIdReturnsExpectedXId() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		assertThat("",//
				MC.getId(), isEqualTo(COMPONENT_ID));
	}

	public final void getSourceIdsReturnsExpectedSet() {
		Set<XId> ids = new SetBuilder<XId>()//
				.add(COMPONENT_ID.createChild(0)).newImmutableInstance();
		final MockComponent MC = new MockComponent(COMPONENT_ID, ids, null);
		assertThat("",//
				MC.getSourceIds(), isEqualTo(ids));
	}

	public final void getTargetIdsReturnsExpectedSet() {
		Set<XId> ids = new SetBuilder<XId>()//
				.add(COMPONENT_ID.createChild(0)).newImmutableInstance();
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, ids);
		assertThat("",//
				MC.getTargetIds(), isEqualTo(ids));
	}

	public final void gethashCodeExpectedInt() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		assertThat("",//
				MC.hashCode(), isEqualTo(COMPONENT_ID.hashCode()));
	}

	public final void logSourceDataSucceeds() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		MC.logSourceData();
	}

	public final void logTargetDataSucceeds() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		MC.logTargetData();
	}

	public final void logDataSucceeds() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		Set<XId> ids = new SetBuilder<XId>()//
				.add(COMPONENT_ID.createChild(0)).newImmutableInstance();
		MC.logData("label", ids);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void removeBorderOnNullThrowsException() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		MC.removeBorder(null);
	}

	public final void removeBorderSucceeds() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		XBorder border = new MockBorder(MC);
		Set<XBorder> expected = new HashSet<XBorder>();
		MC.addBorder(border);
		MC.removeBorder(border);
		assertThat("",//
				MC.getBorders(), isEqualTo(expected));
	}

	public final void toStringReturnsExpectedString() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		String expected = String.format("%s %s", //
				ClassUtils.getShortClassName(MockComponent.class.getSimpleName()), COMPONENT_ID);
		assertThat("",//
				MC.toString(), isEqualTo(expected));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void updateXTimeOnNullThrowsException() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		MC.update(null);
	}

	public final void updateXTimeSucceeds() {
		final MockComponent MC = new MockComponent(COMPONENT_ID, null, null);
		for (StandardTimePhase phase : StandardTimePhase.values()) {
			final XTime<StandardTimePhase> time = new SimpleXTime<StandardTimePhase>(0, phase);
			MC.update(time);
		}
	}

	private class MockComponent extends AbstractStandardXComponent {
		protected MockComponent(XId id, Set<XId> sourceIds, Set<XId> targetIds) {
			super(id, sourceIds, targetIds);
		}

		@Override
		protected void addForeignData(XId targetId, XAddress sourceAddress, Object data) {

			assert (TARGET_ID.equals(targetId));
			assert (XADDRESS.equals(sourceAddress));
			assert (DATA.equals(data));

		}

		@Override
		public Object getData(XId partId) {
			return new Object();
			// throw new NotImplementedException();
		}
	}

	private class MockBorder implements XBorder {
		private final Map<XId, Map<XAddress, Object>>	_data	= new HashMap<XId, Map<XAddress, Object>>();

		/**
		 * @param component
		 */
		public MockBorder(final MockComponent component) {
			final Map<XAddress, Object> map = new HashMap<XAddress, Object>();
			map.put(XADDRESS, DATA);
			_data.put(TARGET_ID, map);
		}

		@Override
		public XBorder associate(XAddress source, Transform transform, XAddress target) {
			throw new NotImplementedException();
		}

		@Override
		public Map<XId, Map<XAddress, Object>> getData(XComponent<?> targetComponent) {

			return _data;
		}
	}

	private static class MockXAddress implements XAddress {
		@Override
		public XComponent<?> getComponent() {

			throw new NotImplementedException();
		}

		@Override
		public XId getPartId() {

			throw new NotImplementedException();
		}

		@Override
		public boolean matches(XComponent<?> component, XId partId) {

			throw new NotImplementedException();
		}

	}
}
