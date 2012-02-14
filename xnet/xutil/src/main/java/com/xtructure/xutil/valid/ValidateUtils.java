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

import java.util.Collection;

import com.xtructure.xutil.Range;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.coll.Transform;
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
import com.xtructure.xutil.valid.comp.IsInRangeCondition;
import com.xtructure.xutil.valid.comp.IsLessThanCondition;
import com.xtructure.xutil.valid.comp.IsLessThanOrEqualToCondition;
import com.xtructure.xutil.valid.comp.IsTrueCondition;
import com.xtructure.xutil.valid.meta.AndCondition;
import com.xtructure.xutil.valid.meta.NotCondition;
import com.xtructure.xutil.valid.meta.OrCondition;
import com.xtructure.xutil.valid.object.IsEqualToCondition;
import com.xtructure.xutil.valid.object.IsNullCondition;
import com.xtructure.xutil.valid.object.IsOfCompatibleTypeCondition;
import com.xtructure.xutil.valid.object.IsOfExactTypeCondition;
import com.xtructure.xutil.valid.object.IsSameAsCondition;
import com.xtructure.xutil.valid.strategy.ArgumentValidationStrategy;
import com.xtructure.xutil.valid.strategy.StateValidationStrategy;
import com.xtructure.xutil.valid.strategy.TestValidationStrategy;
import com.xtructure.xutil.valid.string.MatchesCondition;

/**
 * A set of utilities to validate arguments, states, or test assertions.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * 
 */
public final class ValidateUtils {
	/**
	 * Ensures that the given object is valid with respect to the given
	 * {@link ValidationStrategy}.
	 * 
	 * @param objectName
	 *            the name of the object to validate
	 * @param object
	 *            the object to validate
	 * @param validationStrategy
	 *            the validation strategy by which the given object is validated
	 * @return true if the given object is valid, false otherwise
	 */
	public static <T> boolean validate(final String objectName, final T object, final ValidationStrategy<T> validationStrategy) {
		if (validationStrategy == null) {
			throw new IllegalArgumentException("validationStrategy: must not be null");
		}
		return validationStrategy.validate(object, objectName);
	}

	/**
	 * Ensures that the given argument is valid with respect to the given
	 * {@link Condition}.
	 * 
	 * @param argName
	 *            the name of the argument to validate
	 * @param arg
	 *            the argument to validate
	 * @param condition
	 *            the condition by which the given argument is validated
	 * @return true if the argument is valid, false otherwise
	 */
	public static <T> boolean validateArg(final String argName, final T arg, Condition condition) {
		return validate(argName, arg, new ArgumentValidationStrategy<T>(condition));
	}

	/**
	 * Ensures that the given argument is valid with respect to the given
	 * {@link Condition}s.
	 * 
	 * @param argName
	 *            the name of the argument to validate
	 * 
	 * @param arg
	 *            the argument to validate
	 * @param condition
	 *            the first condition by which the given argument is validated
	 * @param otherConditions
	 *            the other conditions by which the given argument is validated
	 * @return true if the given argument is valid, false otherwise
	 */
	public static <T> boolean validateArg(final String argName, final T arg, Condition condition, Condition... otherConditions) {
		return validate(argName, arg, new ArgumentValidationStrategy<T>(new ListBuilder<Condition>().add(condition).addAll(otherConditions).newImmutableInstance()));
	}

	/**
	 * Ensures that the given argument is valid with respect to the given
	 * {@link ValidationStrategy}.
	 * 
	 * @param argName
	 *            the name of the argument to validate
	 * 
	 * @param arg
	 *            the argument to validate
	 * @param validationStrategy
	 *            the validation strategy by which the argument is validated
	 * @return true if the given {@link ArgumentValidationStrategy} is null or
	 *         if the given argument is valid, false otherwise
	 */
	public static <T> boolean validateArg(final String argName, final T arg, final ArgumentValidationStrategy<T> validationStrategy) {
		return validate(argName, arg, validationStrategy);
	}

	/**
	 * Ensures that the given object is valid with respect to the given
	 * {@link Condition}.
	 * 
	 * @param objectName
	 *            the name of the object to validate
	 * @param object
	 *            the object to validate
	 * @param condition
	 *            the condition by which the given object is validated
	 * @return true if the given object is valid, false otherwise
	 */
	public static <T> boolean validateState(final String objectName, final T object, Condition condition) {
		return validate(objectName, object, new StateValidationStrategy<T>(condition));
	}

	/**
	 * Ensures that the given object is valid with respect to the given
	 * {@link Condition}s.
	 * 
	 * @param objectName
	 *            the name of the object to validate
	 * @param object
	 *            the object to validate
	 * @param condition
	 *            the first condition by which the given object is validated
	 * @param otherConditions
	 *            the other conditions by which the given object is validated
	 * @return true if the object is valid, false otherwise
	 */
	public static <T> boolean validateState(final String objectName, final T object, Condition condition, Condition... otherConditions) {
		return validate(objectName, object, new StateValidationStrategy<T>(new ListBuilder<Condition>().add(condition).addAll(otherConditions).newImmutableInstance()));
	}

	/**
	 * Ensures that the given object is valid with respect to the give
	 * {@link ValidationStrategy}.
	 * 
	 * @param objectName
	 *            the name of the object to validate
	 * @param object
	 *            the object to validate
	 * @param validationStrategy
	 *            the validation strategy by which the object is validated
	 * @return true if the given {@link StateValidationStrategy} is null or if
	 *         the given object is valid, false otherwise
	 */
	public static <T> boolean validateState(final String objectName, final T object, final StateValidationStrategy<T> validationStrategy) {
		return validate(objectName, object, validationStrategy);
	}

	/**
	 * Ensures that the given object is valid with respect to the given
	 * {@link Condition}.
	 * 
	 * @param assertionName
	 *            the name of the assertion to be tested
	 * @param object
	 *            the object to test
	 * @param condition
	 *            the condition by which the given object is validated
	 * @return true if the given object is valid, false otherwise
	 */
	public static <T> boolean assertThat(final String assertionName, final T object, final Condition condition) {
		return validate(assertionName, object, new TestValidationStrategy<T>(condition));
	}

	/**
	 * Ensures that the given object is valid with respect to the given
	 * {@link Condition}.
	 * 
	 * @param assertionName
	 *            the name of the assertion to be tested
	 * @param object
	 *            the object to test
	 * @param condition
	 *            the first condition against which the object should be tested
	 * @param otherConditions
	 *            the other conditions against which the object should be tested
	 * @return true if the given object is valid, false otherwise
	 */
	public static <T> boolean assertThat(final String assertionName, final T object, final Condition condition, final Condition... otherConditions) {
		return validate(assertionName, object, new TestValidationStrategy<T>(new ListBuilder<Condition>().add(condition).addAll(otherConditions).newImmutableInstance()));
	}

	/**
	 * Ensures that the given object is valid with respect to the given
	 * {@link Condition}.
	 * 
	 * @param assertionName
	 *            the name of the assertion to be tested
	 * @param object
	 *            the object to test
	 * @param validationStrategy
	 *            the validation strategy by which the object is tested
	 * @return true if the given {@link TestValidationStrategy} is null or if
	 *         the given object is valid, false otherwise
	 */
	public static <T> boolean assertThat(final String assertionName, final T object, final TestValidationStrategy<T> validationStrategy) {
		return validate(assertionName, object, validationStrategy);
	}

	/**
	 * Returns a condition that negates the given condition.
	 * 
	 * @param condition
	 *            the condition to be negated
	 * @return a condition that negates the given condition
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition not(final Condition condition) {
		return new NotCondition(condition);
	}

	/**
	 * Returns a condition that ensures all of the given conditions hold.
	 * 
	 * @param condition1
	 *            the first condition to be checked
	 * @param condition2
	 *            the second condition to be checked
	 * @return a condition that ensures all of the given conditions hold
	 * @throws IllegalArgumentException
	 *             if any of the given conditions is <code>null</code>
	 */
	public static Condition and(final Condition condition1, final Condition condition2) {
		return new AndCondition(condition1, condition2);
	}

	/**
	 * Returns a condition that ensures all of the given conditions hold.
	 * 
	 * @param condition1
	 *            the first condition to be checked
	 * @param condition2
	 *            the second condition to be checked
	 * @param otherConditions
	 *            the other conditions to be checked
	 * @return a condition that ensures all of the given conditions hold
	 * @throws IllegalArgumentException
	 *             if any of the given conditions is <code>null</code>
	 */
	public static Condition and(final Condition condition1, final Condition condition2, final Condition... otherConditions) {
		return new AndCondition(condition1, condition2, otherConditions);
	}

	/**
	 * Returns a condition that ensures any of the given conditions hold.
	 * 
	 * @param condition1
	 *            the first condition to be checked
	 * @param condition2
	 *            the second condition to be checked
	 * @return a condition that ensures all of the given conditions hold
	 * @throws IllegalArgumentException
	 *             if any of the given conditions is <code>null</code>
	 */
	public static Condition or(final Condition condition1, final Condition condition2) {
		return new OrCondition(condition1, condition2);
	}

	/**
	 * Returns a condition that ensures any of the given conditions hold.
	 * 
	 * @param condition1
	 *            the first condition to be checked
	 * @param condition2
	 *            the second condition to be checked
	 * @param otherConditions
	 *            the other conditions to be checked
	 * @return a condition that ensures all of the given conditions hold
	 * @throws IllegalArgumentException
	 *             if any of the given conditions is <code>null</code>
	 */
	public static Condition or(final Condition condition1, final Condition condition2, final Condition... otherConditions) {
		return new OrCondition(condition1, condition2, otherConditions);
	}

	/**
	 * Returns a condition that checks if an object is the same as a specified
	 * object.
	 * 
	 * @param value
	 *            the value to which the tested object must be identical to
	 * @return a condition that checks if an object is the same as a specified
	 *         object
	 */
	public static Condition isSameAs(final Object value) {
		return new IsSameAsCondition(value);
	}

	/**
	 * Returns a condition that checks if an object is not the same as another
	 * specified object.
	 * 
	 * @param value
	 *            the value to which the tested object must not be identical to
	 * @return a condition that checks if an object is not the same as another
	 *         specified object
	 */
	public static Condition isNotSameAs(final Object value) {
		return not(isSameAs(value));
	}

	/**
	 * Returns a condition that ensures that the tested object equals to given
	 * value.
	 * 
	 * @param value
	 *            the value to which the tested object must be equal
	 * @return a condition that ensures that the tested object equals to given
	 *         value
	 */
	public static Condition isEqualTo(final Object value) {
		return new IsEqualToCondition(value);
	}

	/**
	 * Returns a condition that ensures that the tested object is not equal to
	 * given value.
	 * 
	 * @param value
	 *            the value to which the tested object must not equal
	 * @return a condition that ensures that the tested object is not equal to
	 *         given value
	 */
	public static Condition isNotEqualTo(final Object value) {
		return not(isEqualTo(value));
	}

	/**
	 * Returns a condition that ensures that the tested object is
	 * <code>true</code>.
	 * 
	 * @return a condition that ensures that the tested object is
	 *         <code>true</code>
	 */
	public static Condition isTrue() {
		return new IsTrueCondition();
	}

	/**
	 * Returns a condition that ensures that the tested object is
	 * <code>false</code>.
	 * 
	 * @return a condition that ensures that the tested object is
	 *         <code>false</code>
	 */
	public static Condition isFalse() {
		return not(isTrue());
	}

	/**
	 * Returns a condition that ensures that the tested object is
	 * <code>null</code>.
	 * 
	 * @return a condition that ensures that the tested object is
	 *         <code>null</code>
	 */
	public static Condition isNull() {
		return new IsNullCondition();
	}

	/**
	 * Returns a condition that ensures that the tested object is not
	 * <code>null</code>.
	 * 
	 * @return a condition that ensures that the tested object is not
	 *         <code>null</code>
	 */
	public static Condition isNotNull() {
		return not(isNull());
	}

	/**
	 * Returns a condition that ensures that the tested object is of the given
	 * type.
	 * 
	 * @param type
	 *            the type which objects must be to satisfy the returned
	 *            condition
	 * @return a condition that ensures that the tested object is of the given
	 *         type
	 */
	public static Condition isOfCompatibleType(final Class<?> type) {
		return new IsOfCompatibleTypeCondition(type);
	}

	/**
	 * Returns a condition that ensures that the tested object is exactly of the
	 * given type.
	 * 
	 * @param type
	 *            the type which objects must be to satisfy the returned
	 *            condition
	 * @return a condition that ensures that the tested object is exactly of the
	 *         given type
	 */
	public static Condition isOfExactType(final Class<?> type) {
		return new IsOfExactTypeCondition(type);
	}

	/**
	 * Returns a condition that checks if an object (of type comparable) is less
	 * than a specified object.
	 * 
	 * @param <T>
	 *            the type of the value to which object are compared
	 * @param value
	 *            the value to which objects must be less than to satisfy this
	 *            condition
	 * @return a condition that checks if an object (of type comparable) is less
	 *         than a specified object
	 * @throws IllegalArgumentException
	 *             if the given value is <code>null</code>
	 */
	public static <T extends Comparable<T>> Condition isLessThan(final T value) {
		return new IsLessThanCondition<T>(value);
	}

	/**
	 * Returns a condition that checks if an object (of type comparable) is less
	 * than or equal to a specified object.
	 * 
	 * @param <T>
	 *            the type of the value to which object are compared
	 * @param value
	 *            the value to which objects must be less than or equal to in
	 *            order to satisfy this condition
	 * @return a condition that checks if an object (of type comparable) is less
	 *         than or equal to a specified object
	 * @throws IllegalArgumentException
	 *             if the given value is <code>null</code>
	 */
	public static <T extends Comparable<T>> Condition isLessThanOrEqualTo(final T value) {
		return new IsLessThanOrEqualToCondition<T>(value);
	}

	/**
	 * Returns a condition that checks if an object (of type comparable) is
	 * greater than a specified object.
	 * 
	 * @param <T>
	 *            the type of the value to which object are compared
	 * @param value
	 *            the value to which objects must be greater than to satisfy
	 *            this condition
	 * @return a condition that checks if an object (of type comparable) is
	 *         greater than a specified object
	 * @throws IllegalArgumentException
	 *             if the given value is <code>null</code>
	 */
	public static <T extends Comparable<T>> Condition isGreaterThan(final T value) {
		return new IsGreaterThanCondition<T>(value);
	}

	/**
	 * Returns a condition that checks if an object (of type comparable) is
	 * greater than or equal to a specified object.
	 * 
	 * @param <T>
	 *            the type of the value to which object are compared
	 * @param value
	 *            the value to which objects must be greater than or equal to in
	 *            order to satisfy this condition
	 * @return a condition that checks if an object (of type comparable) is
	 *         greater than or equal to a specified object
	 * @throws IllegalArgumentException
	 *             if the given value is <code>null</code>
	 */
	public static <T extends Comparable<T>> Condition isGreaterThanOrEqualTo(final T value) {
		return new IsGreaterThanOrEqualToCondition<T>(value);
	}

	/**
	 * Returns a condition that ensures that the tested object matches the given
	 * regular expression.
	 * 
	 * @param regex
	 *            the regular expression which the tested object must match
	 * @return a condition that ensures that the tested object matches the given
	 *         regular expression
	 */
	public static Condition matches(final String regex) {
		return new MatchesCondition(regex);
	}

	/**
	 * Returns a condition that checks if an object (of type array or string)
	 * has the given length.
	 * 
	 * @param length
	 *            the length that an object must have to pass this condition
	 * @return a condition that checks if an object (of type array or string)
	 *         has the given length.
	 */
	public static Condition hasLength(final int length) {
		return new HasLengthCondition(length);
	}

	/**
	 * Returns a condition that checks if an object (of type array or string)
	 * has at least the given length.
	 * 
	 * @param length
	 *            the minimum length that an object must have to pass this
	 *            condition
	 * @return a condition that checks if an object (of type array or string)
	 *         has at least the given length.
	 */
	public static Condition hasLengthAtLeast(final int length) {
		return new HasLengthAtLeastCondition(length);
	}

	/**
	 * Returns a condition that checks if an object (of type array or
	 * Collection) has the specified elements.
	 * 
	 * @param num
	 *            the number of elements that must pass this condition's
	 *            condition
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type array or
	 *         Collection) has the specified elements
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition hasElements(final int num, final Condition condition) {
		return new HasElementsCondition(num, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type array or
	 * Collection) has all such specified elements.
	 * 
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type array or
	 *         Collection) has all such specified elements
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition everyElement(final Condition condition) {
		return new HasElementsCondition(HasElementsCondition.ALL, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type array or
	 * Collection) has one such specified elements.
	 * 
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type array or
	 *         Collection) has one such specified elements
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition anyElement(final Condition condition) {
		return new HasElementsCondition(1, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type array or
	 * Collection) contains the given element.
	 * 
	 * @param element
	 *            the element that must be present to pass the returned
	 *            condition
	 * @return a condition that checks if an object (of type array or
	 *         Collection) has the given element
	 */
	public static Condition containsElement(final Object element) {
		return anyElement(isEqualTo(element));
	}

	/**
	 * Returns a condition that checks if an object (of type array or
	 * Collection) contains the given elements.
	 * 
	 * @param element
	 *            the first element that must be present to pass the returned
	 *            condition
	 * @param otherElements
	 *            the other elements that must be present to pass the returned
	 *            condition
	 * @return a condition that checks if an object (of type array or
	 *         Collection) has the given elements
	 */
	public static Condition containsElements(final Object element, final Object... otherElements) {
		return containsElements(new ListBuilder<Object>()//
				.add(element).addAll(otherElements)//
				.newInstance());
	}

	/**
	 * Returns a condition that checks if an object (of type array or
	 * Collection) contains the given elements.
	 * 
	 * @param elements
	 *            the elements that must be present to pass the returned
	 *            condition
	 * @return a condition that checks if an object (of type array or
	 *         Collection) has the given elements
	 */
	public static Condition containsElements(final Collection<?> elements) {
		return new AndCondition(new ListBuilder<Condition>()//
				.addAll(ContainsElementTransform.INSTANCE, elements)//
				.newImmutableInstance());
	}

	/** transform used in containsElements() */
	private static final class ContainsElementTransform implements Transform<Condition, Object> {
		private static final ContainsElementTransform	INSTANCE	= new ContainsElementTransform();

		@Override
		public Condition transform(Object in) {
			return containsElement(in);
		}
	}

	/**
	 * Returns a condition that checks if an object (of type map) has the
	 * specified keys.
	 * 
	 * @param num
	 *            the number of keys that must pass this condition's condition
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type map) has the
	 *         specified keys
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition hasKeys(final int num, final Condition condition) {
		return new HasKeysCondition(num, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type map) has all such
	 * specified keys.
	 * 
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type map) has all such
	 *         specified keys
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition everyKey(final Condition condition) {
		return new HasKeysCondition(HasElementsCondition.ALL, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type map) has one such
	 * specified keys.
	 * 
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type map) has one such
	 *         specified keys
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition anyKey(final Condition condition) {
		return new HasKeysCondition(1, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type map) contains the
	 * given key.
	 * 
	 * @param key
	 *            the key that must be present to pass the returned condition
	 * @return a condition that checks if an object (of type map) has the given
	 *         key
	 */
	public static Condition containsKey(final Object key) {
		return anyKey(isEqualTo(key));
	}

	/**
	 * Returns a condition that checks if an object (of type map) contains the
	 * given keys.
	 * 
	 * @param key
	 *            the first key that must be present to pass the returned
	 *            condition
	 * @param otherKeys
	 *            the other keys that must be present to pass the returned
	 *            condition
	 * @return a condition that checks if an object (of type map) has the given
	 *         keys
	 */
	public static Condition containsKeys(final Object key, final Object... otherKeys) {
		return containsKeys(new ListBuilder<Object>()//
				.add(key).addAll(otherKeys)//
				.newInstance());
	}

	/**
	 * Returns a condition that checks if an object (of type map) contains the
	 * given keys.
	 * 
	 * @param keys
	 *            the keys that must be present to pass the returned condition
	 * @return a condition that checks if an object (of type map) has the given
	 *         keys
	 */
	public static Condition containsKeys(final Collection<?> keys) {
		return new AndCondition(new ListBuilder<Condition>()//
				.addAll(ContainsKeyTransform.INSTANCE, keys)//
				.newImmutableInstance());
	}

	/** transform used in containsKeys() */
	private static final class ContainsKeyTransform implements Transform<Condition, Object> {
		private static final ContainsKeyTransform	INSTANCE	= new ContainsKeyTransform();

		@Override
		public Condition transform(Object in) {
			return containsKey(in);
		}
	}

	/**
	 * Returns a condition that checks if an object (of type map) has the
	 * specified values.
	 * 
	 * @param num
	 *            the number of values that must pass this condition's condition
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type map) has the
	 *         specified values
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition hasValues(final int num, final Condition condition) {
		return new HasValuesCondition(num, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type map) has all such
	 * specified values.
	 * 
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type map) has all such
	 *         specified values
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition everyValue(final Condition condition) {
		return new HasValuesCondition(HasElementsCondition.ALL, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type map) has one such
	 * specified values.
	 * 
	 * @param condition
	 *            the condition to check
	 * @return a condition that checks if an object (of type map) has one such
	 *         specified values
	 * @throws IllegalArgumentException
	 *             if the given condition is <code>null</code>
	 */
	public static Condition anyValue(final Condition condition) {
		return new HasValuesCondition(1, condition);
	}

	/**
	 * Returns a condition that checks if an object (of type map) contains the
	 * given value.
	 * 
	 * @param value
	 *            the values that must be present to pass the returned condition
	 * @return a condition that checks if an object (of type map) has the given
	 *         value
	 */
	public static Condition containsValue(final Object value) {
		return anyValue(isEqualTo(value));
	}

	/**
	 * Returns a condition that checks if an object (of type map) contains the
	 * given values.
	 * 
	 * @param value
	 *            the first value that must be present to pass the returned
	 *            condition
	 * @param otherValues
	 *            the other values that must be present to pass the returned
	 *            condition
	 * @return a condition that checks if an object (of type map) has the given
	 *         values
	 */
	public static Condition containsValues(final Object value, final Object... otherValues) {
		return containsValues(new ListBuilder<Object>()//
				.add(value).addAll(otherValues)//
				.newInstance());
	}

	/**
	 * Returns a condition that checks if an object (of type map) contains the
	 * given values.
	 * 
	 * @param values
	 *            the values that must be present to pass the returned condition
	 * @return a condition that checks if an object (of type map) has the given
	 *         values
	 */
	public static Condition containsValues(final Collection<?> values) {
		return new AndCondition(new ListBuilder<Condition>()//
				.addAll(ContainsValueTransform.INSTANCE, values)//
				.newImmutableInstance());
	}

	/** transform used in containsValue() */
	private static final class ContainsValueTransform implements Transform<Condition, Object> {
		private static final ContainsValueTransform	INSTANCE	= new ContainsValueTransform();

		@Override
		public Condition transform(Object in) {
			return containsValue(in);
		}
	}

	/**
	 * Returns a condition that checks if an object (of collection or map) has
	 * at least the given size.
	 * 
	 * @param size
	 *            the size that an object must have to pass this condition
	 * @return a condition that checks if an object (of collection or map) has
	 *         at least the given size.
	 */
	public static Condition hasSizeAtLeast(int size) {
		return new HasSizeAtLeastCondition(size);
	}

	/**
	 * Returns a condition that checks if an object (of collection or map) has
	 * the given size.
	 * 
	 * @param size
	 *            the size that an object must have to pass this condition
	 * @return a condition that checks if an object (of collection or map) has
	 *         the given size.
	 */
	public static Condition hasSize(int size) {
		return new HasSizeCondition(size);
	}

	/**
	 * Returns a condition that checks if an object (of collection or map) is
	 * empty.
	 * 
	 * @return a condition that checks if an object (of collection or map) is
	 *         empty.
	 */
	public static Condition isEmpty() {
		return new IsEmptyCondition();
	}

	/**
	 * Returns a condition that checks if an object (of type array, collection,
	 * or map) is not empty.
	 * 
	 * @return a condition that checks if an object (of type array, collection,
	 *         or map) is not empty.
	 */
	public static Condition isNotEmpty() {
		return not(isEmpty());
	}

	/**
	 * Returns a condition that is satisfied by nothing.
	 * 
	 * @return a condition that is satisfied by nothing.
	 */
	public static Condition isNothing() {
		return new IsNothingCondition();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Condition isInRange(Range<?> range) {
		return new IsInRangeCondition(range);
	}

	/** Creates new argument validation utilities. */
	private ValidateUtils() {}
}
