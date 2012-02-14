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

import java.util.List;

import com.xtructure.xutil.valid.Condition;

final class DummyValidationStrategy extends AbstractValidationStrategy<Object> {
	public DummyValidationStrategy(List<Condition> conditions) {
		super(conditions);
	}

	@Override
	public void processSuccess(Object object, String msg, Condition condition) {
		// nothing
	}

	@Override
	public void processFailure(Object object, String msg, Condition condition) {
		// nothing
	}
}
