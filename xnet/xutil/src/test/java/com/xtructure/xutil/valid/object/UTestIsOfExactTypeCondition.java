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
package com.xtructure.xutil.valid.object;

import org.testng.annotations.Test;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.valid.object.IsOfExactTypeCondition;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestIsOfExactTypeCondition {
	public void constructorSucceeds() {
		if (new IsOfExactTypeCondition(XId.class) == null) {
			throw new AssertionError();
		}
	}

	public void isSatisfiedByReturnsExpectedBoolean() {
		Condition predicate = new IsOfExactTypeCondition(XId.class);
		if (!predicate.isSatisfiedBy(null)) {
			throw new AssertionError();
		}
		if (!predicate.isSatisfiedBy(XId.newId())) {
			throw new AssertionError();
		}
		if (predicate.isSatisfiedBy(XValId.newId())) {
			throw new AssertionError();
		}
		if (predicate.isSatisfiedBy(new Object())) {
			throw new AssertionError();
		}
	}

	public void toStringReturnsExpectedString() {
		Class<?> cls = XId.class;
		if (!String.format("must be of type %s", cls).equals(new IsOfExactTypeCondition(cls).toString())) {
			throw new AssertionError();
		}
	}
}
