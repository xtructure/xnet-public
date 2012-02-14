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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.valid.ValidationStrategy;

/**
 * @author Luis Guimbarda
 * 
 * @param <T>
 */
public abstract class AbstractValidationStrategy<T> implements ValidationStrategy<T> {
	private final List<Condition>	conditions;

	public AbstractValidationStrategy(List<Condition> conditions) {
		if (conditions == null || conditions.isEmpty()) {
			throw new IllegalArgumentException("conditions: must not be null or empty");
		}
		for (Condition condition : conditions) {
			if (condition == null) {
				throw new IllegalArgumentException("conditions: must not contain null condition");
			}
		}
		this.conditions = Collections.unmodifiableList(new ArrayList<Condition>(conditions));
	}

	@Override
	public boolean validate(T object) {
		return validate(object, DEFAULT_MSG);
	}

	@Override
	public boolean validate(T object, String objectName) {
		boolean success = true;
		for (Condition condition : conditions) {
			if (condition.isSatisfiedBy(object)) {
				processSuccess(object, objectName, condition);
			} else {
				processFailure(object, objectName, condition);
				success = false;
			}
		}
		return success;
	}
}
