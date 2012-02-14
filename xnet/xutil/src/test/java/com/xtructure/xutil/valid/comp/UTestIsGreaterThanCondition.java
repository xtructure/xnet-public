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

import org.testng.annotations.Test;

import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.valid.comp.IsGreaterThanCondition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestIsGreaterThanCondition {
	public void constructorSucceeds() {
		if (new IsGreaterThanCondition<Double>(RandomUtil.nextDouble()) == null) {
			throw new AssertionError();
		}
	}

	public void makeComparisonReturnsExpectedBoolean() {
		double value = RandomUtil.nextDouble();
		double object = 1 + RandomUtil.nextDouble();
		if (!new IsGreaterThanCondition<Double>(value).makeComparison(object)) {
			throw new AssertionError();
		}
		if (new IsGreaterThanCondition<Double>(object).makeComparison(value)) {
			throw new AssertionError();
		}
	}

	public void toStringReturnsExpectedString() {
		double value = RandomUtil.nextDouble();
		if (!String.format("must be greater than to %s", value).equals(new IsGreaterThanCondition<Double>(value).toString())) {
			throw new AssertionError();
		}
	}
}
