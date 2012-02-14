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
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestXGenId {
	private static final Object[][]	BAD_ARGS;
	private static final Object[][]	PARENT_CHILD;
	static {
		BAD_ARGS = TestUtils.unionData(//
				TestUtils.createParameters(null, Collections.EMPTY_LIST),//
				TestUtils.createParameters("", Collections.EMPTY_LIST),//
				TestUtils.createParameters("asdf", (Object) null),//
				TestUtils.createParameters("asdf", Arrays.asList(1, null, 3)));
		PARENT_CHILD = TestUtils.unionData(//
				TestUtils.createParameters(XId.newId("A"), XId.newId("A", 1)),//
				TestUtils.createParameters(XId.newId("A"), XId.newId("A", 2)),//
				TestUtils.createParameters(XId.newId("A", 1), XId.newId("A", 1, 2)),//
				TestUtils.createParameters(XId.newId("A", 1), XId.newId("A", 1, 1)));
	}

	public void constructorSucceeds() {
		assertThat("",//
				XId.newId(), isNotNull());
		assertThat("",//
				XId.newId("string"), isNotNull());
		assertThat("",//
				XId.newId(1, 2, 3), isNotNull());
		assertThat("",//
				XId.newId("string", 1, 2, 3), isNotNull());
	}

	@DataProvider(name = "badArgs")
	public Object[][] badArgs() {
		return BAD_ARGS;
	}

	@Test(dataProvider = "badArgs", expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithBadBaseThrowsException(String base, List<Integer> nums) {
		XId.newId(base, nums);
	}

	@DataProvider(name = "parentChild")
	public Object[][] parentChild() {
		return PARENT_CHILD;
	}

	@Test(dataProvider = "parentChild")
	public void createChildReturnsExpectedObject(XId parent, XId child) {
		assertThat("",//
				parent.createChild(child.getInstanceNum()), isEqualTo(child));
	}

	@Test(dataProvider = "parentChild")
	public void createParentReturnsExpectedObject(XId parent, XId child) {
		assertThat("",//
				child.createParent(), isEqualTo(parent));
	}

	public void createParentOnRootIdReturnsNull() {
		assertThat("",//
				XId.newId().createParent(), isNull());
	}

	public void equalsReturnsExpectedBoolean() {
		XId id1 = XId.newId();
		XId id1Dup = XId.newId(id1.getBase());
		XId id2 = XId.newId();
		assertThat("",//
				id1.equals(null), isFalse());
		assertThat("",//
				id1.equals(id1Dup), isTrue());
		assertThat("",//
				id1.equals(id2), isFalse());
		id1 = id1.createChild(RandomUtil.nextInteger());
		id1Dup = id1Dup.createChild(id1.getInstanceNum());
		id2 = id2.createChild(id1.getInstanceNum());
		assertThat("",//
				id1.equals(null), isFalse());
		assertThat("",//
				id1.equals(id1Dup), isTrue());
		assertThat("",//
				id1.equals(id2), isFalse());
		id1 = id1.createChild(RandomUtil.nextInteger());
		id2 = id1.createChild(RandomUtil.nextInteger());
		assertThat("",//
				id1.equals(id2), isFalse());
	}

	public void getInstanceNumReturnsExpectedObject() {
		XId id = XId.newId();
		assertThat("",//
				id.getInstanceNum(), isNull());
		int num = RandomUtil.nextInteger();
		id = id.createChild(num);
		assertThat("",//
				id.getInstanceNum(), isEqualTo(num));
		num = RandomUtil.nextInteger();
		id = id.createChild(num);
		assertThat("",//
				id.getInstanceNum(), isEqualTo(num));
	}

	public void getInstanceNumsReturnsExpectedObject() {
		XId id = XId.newId();
		assertThat("",//
				id.getInstanceNums(), isEmpty());
		List<Integer> nums = new ArrayList<Integer>();
		nums.add(RandomUtil.nextInteger());
		id = id.createChild(nums.get(0));
		assertThat("",//
				id.getInstanceNums(), isEqualTo(nums));
		nums.add(RandomUtil.nextInteger());
		id = id.createChild(nums.get(1));
		assertThat("",//
				id.getInstanceNums(), isEqualTo(nums));
	}

	@Test(dataProvider = "parentChild")
	public void isAncestorOfReturnsExpectedBoolean(XId parent, XId child) {
		assertThat("",//
				child.isAncestorOf(XId.newId()), isFalse());
		assertThat("",//
				child.isAncestorOf(parent), isFalse());
		assertThat("",//
				parent.isAncestorOf(child), isTrue());
		assertThat("",//
				parent.isAncestorOf(child.createChild(RandomUtil.nextInteger())), isTrue());
		XId p = parent.createParent();
		if (p != null) {
			p = p.createChild(RandomUtil.nextInteger());
			assertThat("",//
					p.isAncestorOf(child), isFalse());
		}
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void isAncestorOfNullThrowsException() {
		XId.newId().isAncestorOf(null);
	}

	@Test(dataProvider = "parentChild")
	public void isDescendentOfReturnsExpectedBoolean(XId parent, XId child) {
		assertThat("",//
				parent.isDescendentOf(XId.newId()), isFalse());
		assertThat("",//
				parent.isDescendentOf(child), isFalse());
		assertThat("",//
				child.isDescendentOf(parent), isTrue());
		assertThat("",//
				child.createChild(RandomUtil.nextInteger()).isDescendentOf(parent), isTrue());
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void isDescendentOfNullThrowsException() {
		XId.newId().isDescendentOf(null);
	}
}
