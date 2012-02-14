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
package com.xtructure.xutil.valid.meta;

import org.testng.annotations.Test;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.valid.meta.NotCondition;
import com.xtructure.xutil.valid.object.IsSameAsCondition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestNotCondition {
	private static final Condition	IS_NULL_PREDICATE	= new IsSameAsCondition(null);

	public void constructorSucceeds() {
		if (new NotCondition(IS_NULL_PREDICATE) == null) {
			throw new AssertionError();
		}
	}

	public void isSatisfiedByReturnsExpectedBoolean() {
		if (IS_NULL_PREDICATE.isSatisfiedBy(new Object())) {
			throw new AssertionError();
		}
		if (!new NotCondition(IS_NULL_PREDICATE).isSatisfiedBy(new Object())) {
			throw new AssertionError();
		}
		if (!IS_NULL_PREDICATE.isSatisfiedBy(null)) {
			throw new AssertionError();
		}
		if (new NotCondition(IS_NULL_PREDICATE).isSatisfiedBy(null)) {
			throw new AssertionError();
		}
	}

	public void toStringReturnsExpectedString() {
		if (!String.format("(not: %s)", IS_NULL_PREDICATE).equals(new NotCondition(IS_NULL_PREDICATE).toString())) {
			throw new AssertionError();
		}
	}
}
