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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestAbstractQuantityCondition {
	private static final Object[][]	BAD_ARGS;
	static {
		BAD_ARGS = TestUtils.unionData(//
				TestUtils.createParameters(-2, isNotNull()),//
				TestUtils.createParameters(0, isNotNull()),//
				TestUtils.createParameters(1, (Object) null));
	}

	public void constructorSucceeds() {
		if (new DummyQuantityCondition(1, isNotNull()) == null) {
			throw new AssertionError();
		}
		if (new DummyQuantityCondition(DummyQuantityCondition.ALL, isNotNull()) == null) {
			throw new AssertionError();
		}
	}

	@DataProvider
	public Object[][] badArgs() {
		return BAD_ARGS;
	}

	@Test(dataProvider = "badArgs", expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithBadArgsThrowsException(int quantity, Condition condition) {
		new DummyQuantityCondition(quantity, condition);
	}

	public void gettersBehaveAsExpected() {
		int q = RandomUtil.nextInteger(100);
		Condition c = isNull();
		if (new DummyQuantityCondition(q, c).getQuantity() != q) {
			throw new AssertionError();
		}
		if (new DummyQuantityCondition(q, c).getCondition() != c) {
			throw new AssertionError();
		}
	}

	public void isSatisfiedByBehavesAsExpected() {
		if (new DummyQuantityCondition(DummyQuantityCondition.ALL, isNotNull()).isSatisfiedBy(new Object())) {
			throw new AssertionError();
		}
		IsNotNull isNotNull = new IsNotNull();
		Condition condition = new DummyQuantityCondition(2, isNotNull);
		Object object = Arrays.asList(null, null, null, null);
		if (condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(2, isNotNull);
		object = Arrays.asList(new Object(), null, null, null);
		if (condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(2, isNotNull);
		object = Arrays.asList(new Object(), new Object(), null, null);
		if (!condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(2, isNotNull);
		object = Arrays.asList(new Object(), new Object(), new Object(), null);
		if (!condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(2, isNotNull);
		object = Arrays.asList(new Object(), new Object(), new Object(), new Object());
		if (!condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(DummyQuantityCondition.ALL, isNotNull);
		object = Arrays.asList(null, null, null, null);
		if (condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(DummyQuantityCondition.ALL, isNotNull);
		object = Arrays.asList(new Object(), null, null, null);
		if (condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(DummyQuantityCondition.ALL, isNotNull);
		object = Arrays.asList(new Object(), new Object(), null, null);
		if (condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(DummyQuantityCondition.ALL, isNotNull);
		object = Arrays.asList(new Object(), new Object(), new Object(), null);
		if (condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
		isNotNull = new IsNotNull();
		condition = new DummyQuantityCondition(DummyQuantityCondition.ALL, isNotNull);
		object = Arrays.asList(new Object(), new Object(), new Object(), new Object());
		if (!condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		if (!isNotNull.seenObjects.equals(object)) {
			throw new AssertionError();
		}
	}

	public void toStringReturnsExpectedString() {
		int quantity = 3;
		Condition condition = isNotNull();
		if (!String.format("at least %s members must satisfy the following conditions: %s", quantity, condition).equals(new DummyQuantityCondition(quantity, condition).toString())) {
			throw new AssertionError();
		}
		quantity = DummyQuantityCondition.ALL;
		if (!String.format("all members must satisfy the following conditions: %s", condition).equals(new DummyQuantityCondition(quantity, condition).toString())) {
			throw new AssertionError();
		}
	}

	private static final class IsNotNull implements Condition {
		List<Object>	seenObjects	= new ArrayList<Object>();

		@Override
		public boolean isSatisfiedBy(Object object) {
			seenObjects.add(object);
			return isNotNull().isSatisfiedBy(object);
		}
	}
}
