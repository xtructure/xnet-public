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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.xtructure.xutil.valid.Condition;

@Test(groups = { "unit:xutil" })
public final class UTestAbstractValidationStrategy {
	private static final Condition	ACCEPT, REJECT;
	static {
		ACCEPT = new Condition() {
			@Override
			public boolean isSatisfiedBy(Object obj) {
				return true;
			}
		};
		REJECT = new Condition() {
			@Override
			public boolean isSatisfiedBy(Object obj) {
				return false;
			}
		};
	}

	public void validateBehavesAsExpected() {
		assertThat("",//
				new DummyValidationStrategy(Arrays.asList(ACCEPT)).validate(null),//
				isTrue());
		assertThat("",//
				new DummyValidationStrategy(Arrays.asList(REJECT)).validate(null),//
				isFalse());
	}
}
