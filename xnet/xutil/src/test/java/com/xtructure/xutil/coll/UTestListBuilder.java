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
import static com.xtructure.xutil.valid.ValidateUtils.hasLength;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNotSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import com.xtructure.xutil.coll.IdentityTransform;
import com.xtructure.xutil.coll.ListBuilder;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestListBuilder {
	public void constructorSucceeds() {
		assertThat("",//
				new ListBuilder<String>(), isNotNull());
		assertThat("",//
				new ListBuilder<String>(new ArrayList<String>()), isNotNull());
	}

	public void addAndAddAllBehaveAsExpected() {
		ListBuilder<String> lb = new ListBuilder<String>();
		assertThat("",//
				lb.newInstance(), isEmpty());
		ListBuilder<String> got = lb.add("one");
		assertThat("",//
				got, isSameAs(lb));
		assertThat("",//
				lb.newInstance(), isEqualTo(Arrays.asList("one")));
		got = lb.add("two", "three");
		assertThat("",//
				got, isSameAs(lb));
		assertThat("",//
				lb.newInstance(), isEqualTo(Arrays.asList("one", "two", "three")));
		got = lb.addAll("one", "two");
		assertThat("",//
				got, isSameAs(lb));
		assertThat("",//
				lb.newInstance(), isEqualTo(Arrays.asList("one", "two", "three", "one", "two")));
		got = lb.addAll(Collections.singleton("three"));
		assertThat("",//
				got, isSameAs(lb));
		assertThat("",//
				lb.newInstance(), isEqualTo(Arrays.asList("one", "two", "three", "one", "two", "three")));
	}

	public void addAllTransformBehavesAsExpected() {
		ListBuilder<String> lb = new ListBuilder<String>();
		assertThat("",//
				lb.newInstance(), isEmpty());
		ListBuilder<String> got = lb.addAll(IdentityTransform.<String> getInstance(), "one", "two", "three");
		assertThat("",//
				got, isSameAs(lb));
		assertThat("",//
				lb.newInstance(), isEqualTo(Arrays.asList("one", "two", "three")));
		got = lb.addAll(IdentityTransform.<String> getInstance(), Arrays.asList("one", "two", "three"));
		assertThat("",//
				got, isSameAs(lb));
		assertThat("",//
				lb.newInstance(), isEqualTo(Arrays.asList("one", "two", "three", "one", "two", "three")));
	}

	public void addAllPredicateBehavesAsExpected() {
		ListBuilder<String> lb = new ListBuilder<String>();
		assertThat("",//
				lb.newInstance(), isEmpty());
		ListBuilder<String> got = lb.addAll(hasLength(3), "one", "two", "three");
		assertThat("",//
				got, isSameAs(lb));
		assertThat("",//
				lb.newInstance(), isEqualTo(Arrays.asList("one", "two")));
		got = lb.addAll(hasLength(5), Arrays.asList("one", "two", "three"));
		assertThat("",//
				got, isSameAs(lb));
		assertThat("",//
				lb.newInstance(), isEqualTo(Arrays.asList("one", "two", "three")));
	}

	public void getCollectionReturnsExpectedObject() {
		List<String> list = new ArrayList<String>();
		assertThat("",//
				new ListBuilder<String>(list).newInstance(), isSameAs(list));
	}

	public void getImmutableCollectionReturnsExpectedObject() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add(RandomStringUtils.randomAlphanumeric(10));
		}
		List<String> newList = new ListBuilder<String>(list).newImmutableInstance();
		assertThat("",//
				list, isNotSameAs(newList));
		assertThat("",//
				list, isEqualTo(newList));
	}
}
