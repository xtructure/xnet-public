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

import static com.xtructure.xutil.valid.ValidateUtils.and;
import static com.xtructure.xutil.valid.ValidateUtils.anyElement;
import static com.xtructure.xutil.valid.ValidateUtils.anyKey;
import static com.xtructure.xutil.valid.ValidateUtils.anyValue;
import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.containsElements;
import static com.xtructure.xutil.valid.ValidateUtils.containsKey;
import static com.xtructure.xutil.valid.ValidateUtils.containsKeys;
import static com.xtructure.xutil.valid.ValidateUtils.containsValue;
import static com.xtructure.xutil.valid.ValidateUtils.containsValues;
import static com.xtructure.xutil.valid.ValidateUtils.everyElement;
import static com.xtructure.xutil.valid.ValidateUtils.everyKey;
import static com.xtructure.xutil.valid.ValidateUtils.everyValue;
import static com.xtructure.xutil.valid.ValidateUtils.hasElements;
import static com.xtructure.xutil.valid.ValidateUtils.hasKeys;
import static com.xtructure.xutil.valid.ValidateUtils.hasLength;
import static com.xtructure.xutil.valid.ValidateUtils.hasLengthAtLeast;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.hasSizeAtLeast;
import static com.xtructure.xutil.valid.ValidateUtils.hasValues;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThan;
import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThan;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isNotEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNotSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isNothing;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isOfCompatibleType;
import static com.xtructure.xutil.valid.ValidateUtils.isOfExactType;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;
import static com.xtructure.xutil.valid.ValidateUtils.matches;
import static com.xtructure.xutil.valid.ValidateUtils.not;
import static com.xtructure.xutil.valid.ValidateUtils.or;
import static com.xtructure.xutil.valid.ValidateUtils.validate;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.Arrays;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.valid.coll.HasElementsCondition;
import com.xtructure.xutil.valid.coll.HasKeysCondition;
import com.xtructure.xutil.valid.coll.HasLengthAtLeastCondition;
import com.xtructure.xutil.valid.coll.HasLengthCondition;
import com.xtructure.xutil.valid.coll.HasSizeAtLeastCondition;
import com.xtructure.xutil.valid.coll.HasSizeCondition;
import com.xtructure.xutil.valid.coll.HasValuesCondition;
import com.xtructure.xutil.valid.coll.IsEmptyCondition;
import com.xtructure.xutil.valid.comp.IsGreaterThanCondition;
import com.xtructure.xutil.valid.comp.IsGreaterThanOrEqualToCondition;
import com.xtructure.xutil.valid.comp.IsLessThanCondition;
import com.xtructure.xutil.valid.comp.IsLessThanOrEqualToCondition;
import com.xtructure.xutil.valid.comp.IsTrueCondition;
import com.xtructure.xutil.valid.meta.AndCondition;
import com.xtructure.xutil.valid.meta.NotCondition;
import com.xtructure.xutil.valid.meta.OrCondition;
import com.xtructure.xutil.valid.object.IsEqualToCondition;
import com.xtructure.xutil.valid.object.IsOfCompatibleTypeCondition;
import com.xtructure.xutil.valid.object.IsOfExactTypeCondition;
import com.xtructure.xutil.valid.object.IsSameAsCondition;
import com.xtructure.xutil.valid.strategy.ArgumentValidationStrategy;
import com.xtructure.xutil.valid.strategy.StateValidationStrategy;
import com.xtructure.xutil.valid.strategy.TestValidationStrategy;
import com.xtructure.xutil.valid.string.MatchesCondition;

@Test(groups = { "unit:xutil" })
public final class UTestValidateUtils {
	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void validateWithNullValidationStrategyThrowsException() {
		validate("", new Object(), null);
	}

	public void validateBehavesAsExpected() {
		String objectName = RandomStringUtils.randomAlphanumeric(10);
		Object object = new Object();
		DummyValidationStrategy vs = new DummyValidationStrategy();
		validate(objectName, object, vs);
		if (vs.calls != 1 || vs.method != "validateOS" || vs.object != object || vs.objectName != objectName || vs.predicate != null) {
			throw new AssertionError();
		}
	}

	private static final class DummyValidationStrategy implements ValidationStrategy<Object> {
		private int			calls		= 0;
		private String		method		= null;
		private Object		object		= null;
		private String		objectName	= null;
		private Condition	predicate	= null;

		@Override
		public boolean validate(Object object) {
			this.calls++;
			this.method = "validateO";
			this.object = object;
			return false;
		}

		@Override
		public boolean validate(Object object, String objectName) {
			this.calls++;
			this.method = "validateOS";
			this.object = object;
			this.objectName = objectName;
			return false;
		}

		@Override
		public void processSuccess(Object object, String msg, Condition predicate) {
			this.calls++;
			this.method = "processSuccess";
			this.object = object;
			this.objectName = msg;
			this.predicate = predicate;
		}

		@Override
		public void processFailure(Object object, String msg, Condition predicate) {
			this.calls++;
			this.method = "processFailure";
			this.object = object;
			this.objectName = msg;
			this.predicate = predicate;
		}
	}

	public void validateArgWithOnePredicateBehavesAsExpected() {
		String argName = RandomStringUtils.randomAlphanumeric(10);
		boolean valid = validateArg(argName, new Object(), isNotNull());
		if (!valid) {
			throw new AssertionError();
		}
		try {
			validateArg(argName, null, isNotNull());
		} catch (IllegalArgumentException e) {
			if (!String.format("%s (%s): %s", argName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void validateArgWithMultiplePredicatesBehavesAsExpected() {
		String argName = RandomStringUtils.randomAlphanumeric(10);
		boolean valid = validateArg(argName, new Object(), isNotNull(), isNotNull());
		if (!valid) {
			throw new AssertionError();
		}
		try {
			validateArg(argName, null, isNotNull(), isNotNull());
		} catch (IllegalArgumentException e) {
			if (!String.format("%s (%s): %s", argName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void validateArgWithValidationStrategyBehavesAsExpected() {
		String argName = RandomStringUtils.randomAlphanumeric(10);
		ArgumentValidationStrategy<Object> validationStrategy = new ArgumentValidationStrategy<Object>(isNotNull());
		boolean valid = validateArg(argName, new Object(), validationStrategy);
		if (!valid) {
			throw new AssertionError();
		}
		try {
			validateArg(argName, null, validationStrategy);
		} catch (IllegalArgumentException e) {
			if (!String.format("%s (%s): %s", argName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void validateStateWithOnePredicateBehavesAsExpected() {
		String objectName = RandomStringUtils.randomAlphanumeric(10);
		boolean valid = validateState(objectName, new Object(), isNotNull());
		if (!valid) {
			throw new AssertionError();
		}
		try {
			validateState(objectName, null, isNotNull());
		} catch (IllegalStateException e) {
			if (!String.format("%s (%s): %s", objectName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void validateStateWithMultiplePredicatesBehavesAsExpected() {
		String objectName = RandomStringUtils.randomAlphanumeric(10);
		boolean valid = validateState(objectName, new Object(), isNotNull(), isNotNull());
		if (!valid) {
			throw new AssertionError();
		}
		try {
			validateState(objectName, null, isNotNull(), isNotNull());
		} catch (IllegalStateException e) {
			if (!String.format("%s (%s): %s", objectName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void validateStateWithValidationStrategyBehavesAsExpected() {
		String objectName = RandomStringUtils.randomAlphanumeric(10);
		StateValidationStrategy<Object> validationStrategy = new StateValidationStrategy<Object>(isNotNull());
		boolean valid = validateState(objectName, new Object(), validationStrategy);
		if (!valid) {
			throw new AssertionError();
		}
		try {
			validateState(objectName, null, validationStrategy);
		} catch (IllegalStateException e) {
			if (!String.format("%s (%s): %s", objectName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void assertThatWithOnePredicateBehavesAsExpected() {
		String assertionName = RandomStringUtils.randomAlphanumeric(10);
		boolean valid = assertThat(assertionName, new Object(), isNotNull());
		if (!valid) {
			throw new AssertionError();
		}
		try {
			assertThat(assertionName, null, isNotNull());
		} catch (AssertionError e) {
			if (!String.format("%s (%s): %s", assertionName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void assertThatWithMultiplePredicatesBehavesAsExpected() {
		String assertionName = RandomStringUtils.randomAlphanumeric(10);
		boolean valid = assertThat(assertionName, new Object(), isNotNull(), isNotNull());
		if (!valid) {
			throw new AssertionError();
		}
		try {
			assertThat(assertionName, null, isNotNull(), isNotNull());
		} catch (AssertionError e) {
			if (!String.format("%s (%s): %s", assertionName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void assertThatWithValidationStrategyBehavesAsExpected() {
		String assertionName = RandomStringUtils.randomAlphanumeric(10);
		TestValidationStrategy<Object> validationStrategy = new TestValidationStrategy<Object>(isNotNull());
		boolean valid = assertThat(assertionName, new Object(), validationStrategy);
		if (!valid) {
			throw new AssertionError();
		}
		try {
			assertThat(assertionName, null, validationStrategy);
		} catch (AssertionError e) {
			if (!String.format("%s (%s): %s", assertionName, null, isNotNull()).equals(e.getMessage())) {
				throw new AssertionError();
			}
		}
	}

	public void andReturnsExpectedObject() {
		Condition conjunct1 = new HasLengthAtLeastCondition(2);
		Condition conjunct2 = new HasLengthAtLeastCondition(1);
		Condition conjunct3 = new HasLengthAtLeastCondition(3);
		Condition conjunction = and(conjunct1, conjunct2);
		if (conjunction != null && conjunction instanceof AndCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
		conjunction = and(conjunct1, conjunct2, conjunct3);
		if (conjunction != null && conjunction instanceof AndCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void orReturnsExpectedObject() {
		Condition disjunct1 = new HasLengthAtLeastCondition(3);
		Condition disjunct2 = new HasLengthAtLeastCondition(2);
		Condition disjunct3 = new HasLengthAtLeastCondition(1);
		Condition disjunction = or(disjunct1, disjunct2);
		if (disjunction != null && disjunction instanceof OrCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
		disjunction = or(disjunct1, disjunct2, disjunct3);
		if (disjunction != null && disjunction instanceof OrCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void notReturnsExpectedObject() {
		Condition condition = new HasLengthAtLeastCondition(1);
		Condition negation = not(condition);
		if (negation != null && negation instanceof NotCondition) {
			checkNot(condition, "");
			check(negation, "");
			check(condition, "a");
			checkNot(negation, "a");
		} else {
			throw new AssertionError();
		}
	}

	public void isEmptyReturnsExpectedObject() {
		Condition condition = isEmpty();
		if (condition != null && condition instanceof IsEmptyCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isNotEmptyReturnsExpectedObject() {
		Condition condition = isNotEmpty();
		if (condition != null && condition instanceof NotCondition) {
			checkNot(condition, "");
			check(condition, "test");
		} else {
			throw new AssertionError();
		}
	}

	public void isTrueReturnsExpectedObject() {
		Condition condition = isTrue();
		if (condition != null && condition instanceof IsTrueCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isFalseReturnsExpectedObject() {
		Condition condition = isFalse();
		if (condition != null && condition instanceof NotCondition) {
			checkNot(condition, true);
			check(condition, false);
		} else {
			throw new AssertionError();
		}
	}

	public void isNullReturnsExpectedObject() {
		Condition condition = isNull();
		if (condition != null && condition instanceof IsSameAsCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isNotNullReturnsExpectedObject() {
		Condition condition = isNotNull();
		if (condition != null && condition instanceof NotCondition) {
			check(condition, new Object());
			checkNot(condition, null);
		} else {
			throw new AssertionError();
		}
	}

	public void isGreaterThanReturnsExpectedObject() {
		Condition condition = isGreaterThan(0l);
		if (condition != null && condition instanceof IsGreaterThanCondition<?>) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isGreaterThanOrEqualToReturnsExpectedObject() {
		Condition condition = isGreaterThanOrEqualTo(0l);
		if (condition != null && condition instanceof IsGreaterThanOrEqualToCondition<?>) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isLessThanReturnsExpectedObject() {
		Condition condition = isLessThan(0l);
		if (condition != null && condition instanceof IsLessThanCondition<?>) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isLessThanOrEqualToReturnsExpectedObject() {
		Condition condition = isLessThanOrEqualTo(0l);
		if (condition != null && condition instanceof IsLessThanOrEqualToCondition<?>) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isEqualToReturnsExpectedObject() {
		Condition condition = isEqualTo(1l);
		if (condition != null && condition instanceof IsEqualToCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isNotEqualToReturnsExpectedObject() {
		Condition condition = isNotEqualTo(1l);
		if (condition != null && condition instanceof NotCondition) {
			checkNot(condition, 1l);
			check(condition, 1);
		} else {
			throw new AssertionError();
		}
	}

	public void isSameAsReturnsExpectedObject() {
		Condition condition = isSameAs(1l);
		if (condition != null && condition instanceof IsSameAsCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isNotSameAsReturnsExpectedObject() {
		Condition condition = isNotSameAs(1l);
		if (condition != null && condition instanceof NotCondition) {
			checkNot(condition, 1l);
			check(condition, new Long(1));
		} else {
			throw new AssertionError();
		}
	}

	public void isOfCompatibleTypeReturnsExpectedObject() {
		Condition condition = isOfCompatibleType(Number.class);
		if (condition != null && condition instanceof IsOfCompatibleTypeCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isOfExactTypeReturnsExpectedObject() {
		Condition condition = isOfExactType(String.class);
		if (condition != null && condition instanceof IsOfExactTypeCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void matchesReturnsExpectedObject() {
		Condition condition = matches("regex");
		if (condition != null && condition instanceof MatchesCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void anyElementReturnsExpectedCondition() {
		Condition condition = anyElement(isNotNull());
		if (condition != null && condition instanceof HasElementsCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void anyKeyReturnsExpectedCondition() {
		Condition condition = anyKey(isNotNull());
		if (condition != null && condition instanceof HasKeysCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void anyValueReturnsExpectedCondition() {
		Condition condition = anyValue(isNotNull());
		if (condition != null && condition instanceof HasValuesCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void everyElementReturnsExpectedCondition() {
		Condition condition = everyElement(isNotNull());
		if (condition != null && condition instanceof HasElementsCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void everyKeyReturnsExpectedCondition() {
		Condition condition = everyKey(isNotNull());
		if (condition != null && condition instanceof HasKeysCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void everyValueReturnsExpectedCondition() {
		Condition condition = everyValue(isNotNull());
		if (condition != null && condition instanceof HasValuesCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void hasElementReturnsExpectedCondition() {
		Condition condition = hasElements(2, isNotNull());
		if (condition != null && condition instanceof HasElementsCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void hasKeyReturnsExpectedCondition() {
		Condition condition = hasKeys(2, isNotNull());
		if (condition != null && condition instanceof HasKeysCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void hasValueReturnsExpectedCondition() {
		Condition condition = hasValues(2, isNotNull());
		if (condition != null && condition instanceof HasValuesCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void containsElementReturnsExpectedCondition() {
		Condition condition = containsElement(new Object());
		if (condition != null && condition instanceof HasElementsCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void containsKeyReturnsExpectedCondition() {
		Condition condition = containsKey(new Object());
		if (condition != null && condition instanceof HasKeysCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void containsValueReturnsExpectedCondition() {
		Condition condition = containsValue(new Object());
		if (condition != null && condition instanceof HasValuesCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void containsElementsReturnsExpectedCondition() {
		Condition condition = containsElements(1l, 3l);
		if (condition != null && condition instanceof AndCondition) {
			check(condition, Arrays.asList(1l, 2l, 3l));
			checkNot(condition, Arrays.asList(2l, 3l, 4l));
		} else {
			throw new AssertionError();
		}
	}

	public void containsKeysReturnsExpectedCondition() {
		Condition condition = containsKeys("A", "B");
		if (condition != null && condition instanceof AndCondition) {
			check(condition, new MapBuilder<String, String>().put("A", "a").put("B", "b").newImmutableInstance());
			checkNot(condition, new MapBuilder<String, String>().put("B", "b").put("C", "c").newImmutableInstance());
		} else {
			throw new AssertionError();
		}
	}

	public void containsValuesReturnsExpectedCondition() {
		Condition condition = containsValues("a", "b");
		if (condition != null && condition instanceof AndCondition) {
			check(condition, new MapBuilder<String, String>().put("A", "a").put("B", "b").newImmutableInstance());
			checkNot(condition, new MapBuilder<String, String>().put("B", "b").put("C", "c").newImmutableInstance());
		} else {
			throw new AssertionError();
		}
	}

	public void hasLengthReturnsExpectedCondition() {
		Condition condition = hasLength(1);
		if (condition != null && condition instanceof HasLengthCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void hasLengthAtLeastReturnsExpectedCondition() {
		Condition condition = hasLengthAtLeast(1);
		if (condition != null && condition instanceof HasLengthAtLeastCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void hasSizeReturnsExpectedCondition() {
		Condition condition = hasSize(1);
		if (condition != null && condition instanceof HasSizeCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void hasSizeAtLeastReturnsExpectedCondition() {
		Condition condition = hasSizeAtLeast(1);
		if (condition != null && condition instanceof HasSizeAtLeastCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	public void isNothingReturnsExpectedCondition() {
		Condition condition = isNothing();
		if (condition != null && condition instanceof IsNothingCondition) {
			// nothing
		} else {
			throw new AssertionError();
		}
	}

	private void check(Condition condition, Object object) throws AssertionError {
		if (!condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
	}

	private void checkNot(Condition condition, Object object) throws AssertionError {
		if (condition.isSatisfiedBy(object)) {
			throw new AssertionError();
		}
	}
}
