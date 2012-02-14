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

/**
 * A strategy for validating objects using conditions and dealing with the
 * success or failure of those conditions.
 * 
 * @author Luis Guimbarda
 * @author Peter N&uuml;rnberg
 * @version 0.9.5
 * 
 * @param <T>
 *            type of objects to validate
 */
public interface ValidationStrategy<T> {
	/** default message string used when validating */
	public static final String	DEFAULT_MSG	= ".";

	/**
	 * Validate the given object using the conditions in this
	 * {@link ValidationStrategy}.
	 * 
	 * @param object
	 *            the object to validate
	 * @return true if the given object is valid with respect to this
	 *         {@link ValidationStrategy}, false otherwise
	 */
	public boolean validate(T object);

	/**
	 * Validate the given object using the conditions in this
	 * {@link ValidationStrategy}.
	 * 
	 * @param object
	 *            the object to validate
	 * @param objectName
	 *            the name of the object to validate
	 * @return true if the given object is valid with respect to this
	 *         {@link ValidationStrategy}, false otherwise
	 */
	public boolean validate(T object, String objectName);

	/**
	 * Process the success of the given condition on the given object.
	 * 
	 * @param object
	 *            the object that satisfied the condition
	 * @param msg
	 *            a message
	 * @param condition
	 *            the condition that was satisfied
	 */
	void processSuccess(T object, String msg, Condition condition);

	/**
	 * Process the failure of the given condition on the given object.
	 * 
	 * @param object
	 *            the object that did not satisfy the condition
	 * @param msg
	 *            a message
	 * @param condition
	 *            the condition that was not satisfied
	 */
	void processFailure(T object, String msg, Condition condition);
}
