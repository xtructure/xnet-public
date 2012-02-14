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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.impl.UTestXAddressImpl.DummyXComponent;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xsim" })
public final class UTestXAddressSet {
	private static final XId	ONE_ID, TWO_ID;
	private static final DummyXComponent	ONE_COMPONENT, TWO_COMPONENT;
	private static final XAddress			ONE_XADDRESS, TWO_XADDRESS;
	private static final Collection<XAddress>	//
			EMPTY_COLLECTION,
			ONE_COLLECTION,
			TWO_COLLECTION;
	private static final List<XAddress>		//
			EMPTY_ARRAY,
			ONE_ARRAY, TWO_ARRAY;
	private static final XAddressSet			EMPTY_XSET, ONE_XSET, TWO_XSET;
	static {
		ONE_ID = XId.newId("ONE").createChild(1);
		TWO_ID = XId.newId("TWO").createChild(1);
		ONE_COMPONENT = new DummyXComponent();
		TWO_COMPONENT = new DummyXComponent();
		ONE_XADDRESS = new XAddressImpl(ONE_COMPONENT, ONE_ID);
		TWO_XADDRESS = new XAddressImpl(TWO_COMPONENT, TWO_ID);
		EMPTY_COLLECTION = new SetBuilder<XAddress>().newImmutableInstance();
		ONE_COLLECTION = new SetBuilder<XAddress>()//
				.add(ONE_XADDRESS)//
				.newImmutableInstance();
		TWO_COLLECTION = new SetBuilder<XAddress>()//
				.add(ONE_XADDRESS, TWO_XADDRESS)//
				.newImmutableInstance();
		EMPTY_ARRAY = Collections.emptyList();
		ONE_ARRAY = Arrays.asList(ONE_XADDRESS);
		TWO_ARRAY = Arrays.asList(ONE_XADDRESS, TWO_XADDRESS);
		EMPTY_XSET = new XAddressSet();
		ONE_XSET = new XAddressSet(ONE_ARRAY);
		TWO_XSET = new XAddressSet(TWO_ARRAY);
	}

	public final void constructorSucceeds() {
		assertThat("",//
				new XAddressSet(), isNotNull());
	}

	@Test(dataProvider = "collections")
	public final void constructorCollectionSucceeds(Collection<? extends XAddress> addresses) {
		assertThat("",//
				new XAddressSet(addresses), isNotNull());
	}

	@Test(dataProvider = "arrays")
	public final void constructorArraySucceeds(List<XAddress> addresses) {
		assertThat("",//
				new XAddressSet(addresses), isNotNull());
		if (addresses != null) {
			assertThat("",//
					new XAddressSet(addresses.toArray(new XAddress[0])), isNotNull());
		}
	}

	@Test(dataProvider = "arraysSubsetArgs")
	public final void subsetReturnsExpectedXAddressSet(List<XAddress> addresses, DummyXComponent component, XId partId, XAddressSet expected) {
		final XAddressSet XAS = new XAddressSet(addresses);
		assertThat("",//
				XAS.subset(component, partId), isEqualTo(expected));
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "collections")
	private final Object[][] collections() {
		return new Object[][] {//
		//
				new Object[] { null },//
				new Object[] { EMPTY_COLLECTION },//
				new Object[] { ONE_COLLECTION },//
				new Object[] { TWO_COLLECTION },//
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "arrays")
	private final Object[][] arrays() {
		return new Object[][] {//
		//
				new Object[] { null },//
				new Object[] { EMPTY_ARRAY },//
				new Object[] { ONE_ARRAY },//
				new Object[] { TWO_ARRAY },//
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "arraysSubsetArgs")
	private final Object[][] arraysSubsetArgs() {
		return new Object[][] {//
		//
				new Object[] { null, null, null, EMPTY_XSET },//
				new Object[] { EMPTY_ARRAY, null, null, EMPTY_XSET },//
				new Object[] { ONE_ARRAY, null, null, ONE_XSET },//
				new Object[] { TWO_ARRAY, null, null, TWO_XSET },//
				new Object[] { null, ONE_COMPONENT, null, EMPTY_XSET },//
				new Object[] { EMPTY_ARRAY, ONE_COMPONENT, null, EMPTY_XSET },//
				new Object[] { ONE_ARRAY, ONE_COMPONENT, null, ONE_XSET },//
				new Object[] { TWO_ARRAY, ONE_COMPONENT, null, ONE_XSET },//
				new Object[] { null, null, ONE_ID, EMPTY_XSET },//
				new Object[] { EMPTY_ARRAY, null, ONE_ID, EMPTY_XSET },//
				new Object[] { ONE_ARRAY, null, ONE_ID, ONE_XSET },//
				new Object[] { TWO_ARRAY, null, ONE_ID, ONE_XSET },//
				new Object[] { TWO_ARRAY, TWO_COMPONENT, ONE_ID, EMPTY_XSET },//
				new Object[] { TWO_ARRAY, ONE_COMPONENT, TWO_ID, EMPTY_XSET },//
		};
	}
}
