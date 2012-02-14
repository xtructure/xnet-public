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
package com.xtructure.xutil.valid.comp;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import com.xtructure.xutil.RandomUtil;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestAbstractComparisonCondition {
	public void constructorSucceeds() {
		if (new DummyComparisonCondition("asdf") == null) {
			throw new AssertionError();
		}
	}

	public void isSatisfiedByBehavesAsExpected() {
		boolean valid = RandomUtil.nextBoolean();
		String value = RandomStringUtils.randomAlphanumeric(10);
		String object = RandomStringUtils.randomAlphanumeric(10);
		DummyComparisonCondition predicate = new DummyComparisonCondition(value);
		predicate.setrVal(valid);
		if (valid != predicate.isSatisfiedBy(object) || predicate.getCall() != 1 || !predicate.getLast().equals(object)) {
			throw new AssertionError();
		}
		if (predicate.isSatisfiedBy(new Object())) {
			throw new AssertionError();
		}
	}
}
