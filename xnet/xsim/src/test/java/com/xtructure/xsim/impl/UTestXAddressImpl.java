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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNotSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XBorder;
import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XTime;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestXAddressImpl {
	private static final XAddressImpl	XADD;
	static {
		XADD = new XAddressImpl(new DummyXComponent(), XId.newId("XADD", 1));
	}

	@Test(dataProvider = "constructorArgs")
	public final void constructorSucceeds(XComponent<?> component, XId partId) {
		XAddressImpl xadd = new XAddressImpl(component, partId);
		assertThat("",//
				xadd, isNotNull());
	}

	@Test(dataProvider = "constructorArgs")
	public final void getComponentReturnsExpectedComponent(XComponent<?> component, XId partId) {
		XAddressImpl xadd = new XAddressImpl(component, partId);
		assertThat("",//
				xadd.getComponent(), isEqualTo(component));
	}

	@Test(dataProvider = "constructorArgs")
	public final void getPartIdReturnsExpectedXId(XComponent<?> component, XId partId) {
		XAddressImpl xadd = new XAddressImpl(component, partId);
		assertThat("",//
				xadd.getPartId(), isEqualTo(partId));
	}

	@Test(dataProvider = "matchArgs")
	public final void matchesReturnsExpectedBoolean(XComponent<?> component, XId partId, boolean expected) {
		assertThat("",//
				XADD.matches(component, partId), isEqualTo(expected));
	}
	
	public final void toStringReturnsExpectedString(){
		String expected = String.format("%s.%s", XADD.getComponent().getId(), XADD.getPartId());
		assertThat("",//
				XADD.toString(), isEqualTo(expected));
	}

	public final void equalsReturnsExpectedBoolean(){
		XAddress addr1 = new XAddressImpl(XADD.getComponent(), XADD.getPartId());
		assertThat("",//
				addr1, isNotSameAs(XADD));
		assertThat("",//
				addr1.equals(XADD), isTrue());
		XAddress addr2 = new XAddressImpl(XADD.getComponent(), XADD.getPartId().createChild(0));
		assertThat("",//
				addr1.equals(addr2), isFalse());
		XAddress addr3 = new XAddressImpl(new DummyXComponent(), XADD.getPartId());
		assertThat("",//
				addr1.equals(addr3), isFalse());
		XAddress addr4 = new XAddressImpl(XADD.getComponent(), XADD.getPartId());
		assertThat("",//
				addr1.equals(addr4), isTrue());
		
		assertThat("",//
				addr1.equals(3), isFalse());
		assertThat("",//
				addr1.equals(null), isFalse());
		
	}
	
	public final void hashCodeOfTwoEqualXAddressImplAreEqual(){
		XAddress xaddr = new XAddressImpl(XADD.getComponent(), XADD.getPartId());
		assertThat("",//
				xaddr, isEqualTo(XADD));
		assertThat("",//
				xaddr.hashCode(), isEqualTo(XADD.hashCode()));
	}
	
	@DataProvider(name = "constructorArgs")
	@SuppressWarnings("unused")
	private final Object[][] constructorArgs() {
		return new Object[][] {//
		//
				new Object[] { null, null },//
				new Object[] { new DummyXComponent(), null },//
				new Object[] { null, XId.newId("base") },//
				new Object[] { new DummyXComponent(), XId.newId("base") },//
		};
	}

	@DataProvider(name = "matchArgs")
	@SuppressWarnings("unused")
	private final Object[][] matchArgs() {
		return new Object[][] {//
		//
				new Object[] { XADD.getComponent(), XADD.getPartId(), true },//
				new Object[] { XADD.getComponent(), null, true },//
				new Object[] { null, XADD.getPartId(), true },//
				new Object[] { null, null, true },//
				new Object[] { null, XId.newId("random"), false },//
				new Object[] { null, XADD.getPartId().createParent(), false },//
				new Object[] { null, XADD.getPartId().createChild(99), false },//
				new Object[] { new DummyXComponent(), null, false },//
				new Object[] { new DummyXComponent(), XId.newId("random"), false },//
				new Object[] { new DummyXComponent(), XADD.getPartId(), false },//
				new Object[] { new DummyXComponent(), XADD.getPartId().createParent(), false },//
				new Object[] { new DummyXComponent(), XADD.getPartId().createChild(99), false },//
				new Object[] { XADD.getComponent(), XId.newId("random"), false },//
				new Object[] { XADD.getComponent(), XADD.getPartId().createParent(), false },//
				new Object[] { XADD.getComponent(), XADD.getPartId().createChild(99), false },//
		};
	}

	static final class DummyXComponent implements XComponent<PhaseImpl> {
		
		private XId id;
		public DummyXComponent(){
			id = XId.newId("TEST_COMPONENT_ID");
		}

		@Override
		public void addBorder(XBorder border) {}

		@Override
		public Object getData(XId partId) {
			return null;
		}

		@Override
		public Set<XId> getSourceIds() {
			return null;
		}

		@Override
		public Set<XId> getTargetIds() {
			return null;
		}

		@Override
		public void removeBorder(XBorder border) {}

		@Override
		public void update(XTime<PhaseImpl> time) {}

		@Override
		public XId getId() {
			return id;
		}

		@Override
		public int compareTo(XIdObject o) {
			return 0;
		}
	}
}
