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
package com.xtructure.xutil.valid.coll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.valid.coll.HasSizeAtLeastCondition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestHasSizeAtLeastCondition {
	private static final Set<Object>	BAD;
	private static final Set<Object>	ZERO_SIZE;
	private static final Set<Object>	ONE_SIZE;
	private static final Set<Object>	TWO_SIZE;
	private static final Object[][]		ARGS;
	static {
		BAD = new SetBuilder<Object>()//
				.add(null,//
						new Object[0],//
						new char[0],//
						new byte[0],//
						new short[0],//
						new int[0],//
						new long[0],//
						new float[0],//
						new double[0],//
						new boolean[0],//
						0, "string", new Object())//
				.newImmutableInstance();
		ZERO_SIZE = new SetBuilder<Object>()//
				.add(new ArrayList<Object>(),//
						new HashMap<Object, Object>(),//
						new HashSet<Object>())//
				.newImmutableInstance();
		ONE_SIZE = new SetBuilder<Object>()//
				.add(Collections.singleton(new Object()),//
						Collections.singletonList(new Object()),//
						Collections.singletonMap(new Object(), new Object()))//
				.newImmutableInstance();
		TWO_SIZE = new SetBuilder<Object>()//
				.add(new SetBuilder<Object>().add(new Object(), new Object()).newImmutableInstance(),//
						new ListBuilder<Object>().add(new Object(), new Object()).newImmutableInstance(),//
						new MapBuilder<Object, Object>().put(new Object(), new Object()).put(new Object(), new Object()).newImmutableInstance())//
				.newImmutableInstance();
		ARGS = TestUtils.unionData(//
				TestUtils.createData(BAD.toArray()),//
				TestUtils.createData(ZERO_SIZE.toArray()),//
				TestUtils.createData(ONE_SIZE.toArray()),//
				TestUtils.createData(TWO_SIZE.toArray()));
	}

	public void constructorSucceeds() {
		if (new HasSizeAtLeastCondition(RandomUtil.nextInteger(10)) == null) {
			throw new AssertionError();
		}
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorOnNegativeSizeThrowsException() {
		new HasSizeAtLeastCondition(-1);
	}

	@DataProvider
	public Object[][] args() {
		return ARGS;
	}

	@Test(dataProvider = "args")
	public void isSatisfiedByReturnsExpectedBoolean(Object object) {
		HasSizeAtLeastCondition predicate = new HasSizeAtLeastCondition(0);
		if (BAD.contains(object)) {
			if (predicate.isSatisfiedBy(object)) {
				throw new AssertionError();
			}
		} else {
			if (!predicate.isSatisfiedBy(object)) {
				throw new AssertionError();
			}
		}
		predicate = new HasSizeAtLeastCondition(1);
		if (BAD.contains(object) || ZERO_SIZE.contains(object)) {
			if (predicate.isSatisfiedBy(object)) {
				throw new AssertionError();
			}
		} else {
			if (!predicate.isSatisfiedBy(object)) {
				throw new AssertionError();
			}
		}
		predicate = new HasSizeAtLeastCondition(2);
		if (BAD.contains(object) || ZERO_SIZE.contains(object) || ONE_SIZE.contains(object)) {
			if (predicate.isSatisfiedBy(object)) {
				throw new AssertionError();
			}
		} else {
			if (!predicate.isSatisfiedBy(object)) {
				throw new AssertionError();
			}
		}
		predicate = new HasSizeAtLeastCondition(3);
		if (predicate.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
	}

	public void toStringReturnsExpectedString() {
		int size = RandomUtil.nextInteger(10);
		if (!String.format("must have size at least %d", size).equals(new HasSizeAtLeastCondition(size).toString())) {
			throw new AssertionError();
		}
	}
}
