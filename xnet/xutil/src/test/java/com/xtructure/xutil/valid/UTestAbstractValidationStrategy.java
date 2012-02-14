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
package com.xtructure.xutil.valid;

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.valid.ValidationStrategy;
import com.xtructure.xutil.valid.strategy.AbstractValidationStrategy;

/**
 * @author Luis Guimbarda
 */
@Test(groups = { "unit:xutil" })
public class UTestAbstractValidationStrategy {
	public void constructorSucceeds() {
		if (new DummyValidationStrategy(Arrays.asList(isNotNull(), isTrue())) == null) {
			throw new AssertionError();
		}
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithNullThrowsException() {
		new DummyValidationStrategy(null);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithNoPredicatesThrowsException() {
		List<Condition> empty = Collections.emptyList();
		new DummyValidationStrategy(empty);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithListContainingNullThrowsException() {
		new DummyValidationStrategy(Arrays.asList(isNotNull(), null));
	}

	public void validateBehavesAsExpectedWhen_NonNullObjectIsNotNull() {
		Condition predicate = isNotNull();
		Object object = new Object();
		String msg = String.format("%s %s", object, predicate);
		DummyValidationStrategy vs = new DummyValidationStrategy(Collections.singletonList(predicate));
		if (vs.success != null || vs.object != null || vs.msg != null || vs.predicate != null) {
			throw new AssertionError();
		}
		boolean valid = vs.validate(object);
		if (valid == false) {
			throw new AssertionError();
		}
		if (vs.success != Boolean.TRUE || vs.object != object || vs.msg != ValidationStrategy.DEFAULT_MSG || vs.predicate != predicate) {
			throw new AssertionError();
		}
		valid = vs.validate(object, msg);
		if (valid == false) {
			throw new AssertionError();
		}
		if (vs.success != Boolean.TRUE || vs.object != object || vs.msg != msg || vs.predicate != predicate) {
			throw new AssertionError();
		}
	}

	public void validateBehavesAsExpectedWhen_NullObjectIsNull() {
		Condition predicate = isNull();
		Object object = null;
		String msg = String.format("%s %s", object, predicate);
		DummyValidationStrategy vs = new DummyValidationStrategy(Collections.singletonList(predicate));
		if (vs.success != null || vs.object != null || vs.msg != null || vs.predicate != null) {
			throw new AssertionError();
		}
		boolean valid = vs.validate(object);
		if (valid == false) {
			throw new AssertionError();
		}
		if (vs.success != Boolean.TRUE || vs.object != object || vs.msg != ValidationStrategy.DEFAULT_MSG || vs.predicate != predicate) {
			throw new AssertionError();
		}
		valid = vs.validate(object, msg);
		if (valid == false) {
			throw new AssertionError();
		}
		if (vs.success != Boolean.TRUE || vs.object != object || vs.msg != msg || vs.predicate != predicate) {
			throw new AssertionError();
		}
	}

	public void validateBehavesAsExpectedWhen_NullObjectIsNotNull() {
		Condition predicate = isNotNull();
		Object object = null;
		String msg = String.format("%s %s", object, predicate);
		DummyValidationStrategy vs = new DummyValidationStrategy(Collections.singletonList(predicate));
		if (vs.success != null || vs.object != null || vs.msg != null || vs.predicate != null) {
			throw new AssertionError();
		}
		boolean valid = vs.validate(object);
		if (valid == true) {
			throw new AssertionError();
		}
		if (vs.success != Boolean.FALSE || vs.object != object || vs.msg != ValidationStrategy.DEFAULT_MSG || vs.predicate != predicate) {
			throw new AssertionError();
		}
		valid = vs.validate(object, msg);
		if (valid == true) {
			throw new AssertionError();
		}
		if (vs.success != Boolean.FALSE || vs.object != object || vs.msg != msg || vs.predicate != predicate) {
			throw new AssertionError();
		}
	}

	public void validateBehavesAsExpectedWhen_NonNullObjectIsNull() {
		Condition predicate = isNull();
		Object object = new Object();
		String msg = String.format("%s %s", object, predicate);
		DummyValidationStrategy vs = new DummyValidationStrategy(Collections.singletonList(predicate));
		if (vs.success != null || vs.object != null || vs.msg != null || vs.predicate != null) {
			throw new AssertionError();
		}
		boolean valid = vs.validate(object);
		if (valid == true) {
			throw new AssertionError();
		}
		if (vs.success != Boolean.FALSE || vs.object != object || vs.msg != ValidationStrategy.DEFAULT_MSG || vs.predicate != predicate) {
			throw new AssertionError();
		}
		valid = vs.validate(object, msg);
		if (valid == true) {
			throw new AssertionError();
		}
		if (vs.success != Boolean.FALSE || vs.object != object || vs.msg != msg || vs.predicate != predicate) {
			throw new AssertionError();
		}
	}

	private static final class DummyValidationStrategy extends AbstractValidationStrategy<Object> {
		private Boolean		success;
		private Object		object;
		private String		msg;
		private Condition	predicate;

		public DummyValidationStrategy(List<Condition> predicates) {
			super(predicates);
			reset();
		}

		public void reset() {
			set(null, null, null, null);
		}

		public void set(Boolean success, Object object, String msg, Condition predicate) {
			this.success = success;
			this.object = object;
			this.msg = msg;
			this.predicate = predicate;
		}

		@Override
		public void processSuccess(Object object, String msg, Condition predicate) {
			set(true, object, msg, predicate);
		}

		@Override
		public void processFailure(Object object, String msg, Condition predicate) {
			set(false, object, msg, predicate);
		}
	}
}
