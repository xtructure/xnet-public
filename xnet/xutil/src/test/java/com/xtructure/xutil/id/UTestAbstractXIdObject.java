/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xutil.id;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestAbstractXIdObject {
	private static final Object[][]	IDS;
	private static final Object[][]	IDS_IDS;
	static {
		IDS = TestUtils.createData(XId.newId(),//
				XId.newId("string1"),//
				XId.newId("string1"),//
				XId.newId("string2"));
		IDS_IDS = TestUtils.crossData(IDS, IDS);
	}

	@DataProvider
	public Object[][] ids() {
		return IDS;
	}

	@DataProvider
	public Object[][] idsids() {
		return IDS_IDS;
	}

	@Test(dataProvider = "ids")
	public void constructorSucceeds(XId id) {
		assertThat("",//
				new DummyXIdObject(id), isNotNull());
		assertThat("",//
				new DummyXIdObject(id, new XIdObjectManagerImpl<DummyXIdObject>()), isNotNull());
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithNullIdThrowsException() {
		new DummyXIdObject(null);
	}

	@Test(dataProvider = "idsids")
	public void compareToReturnsExpectedObject(XId id1, XId id2) {
		XIdObject d1 = new DummyXIdObject(id1);
		XIdObject d2 = new DummyXIdObject(id2);
		assertThat("",//
				d1.compareTo(d1), isEqualTo(0));
		assertThat("",//
				d1.compareTo(null), isEqualTo(1));
		assertThat("",//
				d1.compareTo(d2), isEqualTo(id1.compareTo(id2)));
	}

	@Test(dataProvider = "idsids")
	public void equalsReturnsExpectedBoolean(XId id1, XId id2) {
		XIdObject d1 = new DummyXIdObject(id1);
		XIdObject d2 = new DummyXIdObject(id2);
		assertThat("",//
				d1.equals(d1), isTrue());
		assertThat("",//
				d1.equals(null), isFalse());
		assertThat("",//
				d1.equals(d2), id1.equals(id2) ? isTrue() : isFalse());
	}

	@Test(dataProvider = "ids")
	public void getIdReturnsExpectedObject(XId id) {
		assertThat("",//
				new DummyXIdObject(id).getId(), isSameAs(id));
	}

	@Test(dataProvider = "ids")
	public void hashCodeReturnsExpectedInt(XId id) {
		assertThat("",//
				new DummyXIdObject(id).hashCode(), isEqualTo(id.hashCode()));
	}
}
