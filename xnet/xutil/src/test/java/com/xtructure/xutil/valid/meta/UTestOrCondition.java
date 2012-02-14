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

import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.valid.meta.OrCondition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestOrCondition {
	private static final Condition	PS[];
	static {
		PS = new Condition[] { isNull(), isEmpty(), isEqualTo(Boolean.TRUE) };
	}

	public void constructorSucceeds() {
		if (new OrCondition(PS[0], PS[1]) == null) {
			throw new AssertionError();
		}
		if (new OrCondition(PS[0], PS[1], PS[2]) == null) {
			throw new AssertionError();
		}
		if (new OrCondition(Arrays.asList(PS[0])) == null) {
			throw new AssertionError();
		}
		if (new OrCondition(Arrays.asList(PS)) == null) {
			throw new AssertionError();
		}
	}

	public void isSatisfiedByReturnsExpectedBoolean() {
		Condition predicate = new OrCondition(Arrays.asList(PS));
		Object object = null;
		if (!predicate.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		object = Arrays.asList();
		if (!predicate.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		object = Arrays.asList(new Object());
		if (predicate.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		object = false;
		if (predicate.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
		object = true;
		if (!predicate.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
	}

	public void toStringReturnsExpectedString() {
		if (!String.format("(%s or %s or %s)", (Object[]) PS).equals(new OrCondition(Arrays.asList(PS)).toString())) {
			throw new AssertionError();
		}
	}
}
