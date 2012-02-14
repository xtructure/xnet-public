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

import static com.xtructure.xutil.valid.ValidateUtils.everyElement;
import static com.xtructure.xutil.valid.ValidateUtils.isNotEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestAbstractMetaCondition {
	private static final Condition[]	PS;
	static {
		PS = new Condition[] { isNotNull(), isNotEmpty(), everyElement(isNotNull()) };
	}

	public void constructorSucceeds() {
		if (new DummyMetaCondition(PS) == null) {
			throw new AssertionError();
		}
		if (new DummyMetaCondition(Arrays.asList(PS)) == null) {
			throw new AssertionError();
		}
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void constructorWithNullVarArgsThrowsException() {
		new DummyMetaCondition((Condition[]) null);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithNullListThrowsException() {
		new DummyMetaCondition((List<Condition>) null);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithListWithNullPredicateThrowsException() {
		new DummyMetaCondition(Collections.singletonList((Condition) null));
	}

	public void getPredicateReturnsExpectedPredicate() {
		for (int i = 0; i < PS.length; i++) {
			if (new DummyMetaCondition(PS).getCondition(i) != PS[i]) {
				throw new AssertionError();
			}
		}
	}

	public void getPredicatesReturnsExpectedList() {
		if (!Arrays.asList(PS).equals(new DummyMetaCondition(PS).getConditions())) {
			throw new AssertionError();
		}
	}
}
