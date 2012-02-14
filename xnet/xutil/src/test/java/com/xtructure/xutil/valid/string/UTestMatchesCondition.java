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
package com.xtructure.xutil.valid.string;

import org.testng.annotations.Test;

import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestMatchesCondition {
	public void constructorSucceeds() {
		if (new MatchesCondition("regex") == null) {
			throw new AssertionError();
		}
	}

	public void isSatisfiedByReturnsExpectedBoolean() {
		String regex = "regex";
		Condition predicate = new MatchesCondition(regex);
		if (!predicate.isSatisfiedBy("regex")) {
			throw new AssertionError();
		}
		if (predicate.isSatisfiedBy("asdf")) {
			throw new AssertionError();
		}
		if (predicate.isSatisfiedBy(null)) {
			throw new AssertionError();
		}
		if (predicate.isSatisfiedBy(new Object())) {
			throw new AssertionError();
		}
	}

	public void toStringReturnsExpectedString() {
		String regex = "regex";
		if (!String.format("must match %s", regex).equals(new MatchesCondition(regex).toString())) {
			throw new AssertionError();
		}
	}
}
