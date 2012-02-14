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

import com.xtructure.xutil.Range;
import com.xtructure.xutil.valid.comp.IsInRangeCondition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestIsInRangeCondition {
	public void constructorSucceeds() {
		if (new IsInRangeCondition<Double>(Range.getInstance(0.0, 1.0)) == null) {
			throw new AssertionError();
		}
	}

	public void isSatisfiedByReturnsExpectedBoolean() {
		if (new IsInRangeCondition<Double>(Range.getInstance(0.0, 1.0)).isSatisfiedBy(-1.0)) {
			throw new AssertionError();
		}
		if (!new IsInRangeCondition<Double>(Range.getInstance(0.0, 1.0)).isSatisfiedBy(0.01)) {
			throw new AssertionError();
		}
		if (new IsInRangeCondition<Double>(Range.getInstance(0.0, 1.0)).isSatisfiedBy(2.0)) {
			throw new AssertionError();
		}
		if (new IsInRangeCondition<Double>(Range.getInstance(0.0, 1.0)).isSatisfiedBy(new Object())) {
			throw new AssertionError();
		}
		if (new IsInRangeCondition<Double>(Range.getInstance(0.0, 1.0)).isSatisfiedBy(null)) {
			throw new AssertionError();
		}
	}
}
