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
package com.xtructure.xutil.valid.strategy;

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.valid.strategy.StateValidationStrategy;

/**
 * @author Luis Guimbarda
 */
@Test(groups = { "unit:xutil" })
public final class UTestStateValidationStrategy {
	public void constructorSucceeds() {
		if (new StateValidationStrategy<Object>(Arrays.asList(isNotNull(), isTrue())) == null) {
			throw new AssertionError();
		}
		if (new StateValidationStrategy<Object>(isNotNull(), isTrue()) == null) {
			throw new AssertionError();
		}
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithNullListThrowsException() {
		new StateValidationStrategy<Object>((List<Condition>) null);
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void constructorWithNullVarArgsThrowsException() {
		new StateValidationStrategy<Object>((Condition[]) null);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithNoPredicatesThrowsException() {
		List<Condition> empty = Collections.emptyList();
		new StateValidationStrategy<Object>(empty);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithListContainingNullThrowsException() {
		new StateValidationStrategy<Object>(Arrays.asList(isNotNull(), null));
	}

	public void processFailureBehavesAsExpected() {
		Condition predicate = isNotNull();
		Object object = null;
		String msg = RandomStringUtils.randomAlphanumeric(10);
		StateValidationStrategy<Object> vs = new StateValidationStrategy<Object>(predicate);
		try {
			vs.validate(object);
		} catch (IllegalStateException e) {
			if (!String.format("%s (%s): %s", StateValidationStrategy.DEFAULT_MSG, object, predicate).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
		try {
			vs.validate(object, msg);
		} catch (IllegalStateException e) {
			if (!String.format("%s (%s): %s", msg, object, predicate).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void processSuccessBehavesAsExpected() {
		Condition predicate = isNotNull();
		Object object = new Object();
		String msg = String.format("%s %s", object, predicate);
		StateValidationStrategy<Object> vs = new StateValidationStrategy<Object>(predicate);
		vs.validate(object);
		vs.validate(object, msg);
	}
}
