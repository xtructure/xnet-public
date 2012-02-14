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
package com.xtructure.xutil.coll;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNotSameAs;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import com.xtructure.xutil.coll.SetBuilder;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestSetBuilder {
	public void constructorSucceeds() {
		assertThat("",//
				new SetBuilder<String>(), isNotNull());
		assertThat("",//
				new SetBuilder<String>(new HashSet<String>()), isNotNull());
	}

	public void getImmutableCollectionReturnsExpectedObject() {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < 10; i++) {
			set.add(RandomStringUtils.randomAlphanumeric(10));
		}
		Set<String> newSet = new SetBuilder<String>(set).newImmutableInstance();
		assertThat("",//
				set, isNotSameAs(newSet));
		assertThat("",//
				set, isEqualTo(newSet));
	}
}
