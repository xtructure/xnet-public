/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.operators.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import java.util.Collections;

import org.testng.annotations.Test;

@Test(groups = { "unit:xneat" })
public class UTestNEATMutatorOperatorSelecterImpl {
	@SuppressWarnings("unchecked")
	public void constructorSucceeds() {
		NEATMutateOperatorSelecterImpl selecter = new NEATMutateOperatorSelecterImpl();
		assertThat("",//
				selecter, isNotNull());
		selecter = new NEATMutateOperatorSelecterImpl(Collections.EMPTY_MAP);
		assertThat("",//
				new NEATMutateOperatorSelecterImpl(selecter), isNotNull());
		selecter = new NEATMutateOperatorSelecterImpl(selecter);
		assertThat("",//
				new NEATMutateOperatorSelecterImpl(selecter), isNotNull());
	}
}
