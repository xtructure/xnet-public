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
import static com.xtructure.xutil.valid.ValidateUtils.containsKey;
import static com.xtructure.xutil.valid.ValidateUtils.containsValue;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNotSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import com.xtructure.xutil.coll.IdentityTransform;
import com.xtructure.xutil.coll.MapBuilder;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestMapBuilder {
	public void constructorSucceeds() {
		assertThat("",//
				new MapBuilder<String, String>(), isNotNull());
		assertThat("",//
				new MapBuilder<String, String>(new HashMap<String, String>()), isNotNull());
	}

	public void getImmutableMapReturnsExpectedObject() {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 10; i++) {
			map.put(RandomStringUtils.randomAlphanumeric(10),//
					RandomStringUtils.randomAlphanumeric(10));
		}
		Map<String, String> newMap = new MapBuilder<String, String>(map).newImmutableInstance();
		assertThat("",//
				map, isNotSameAs(newMap));
		assertThat("",//
				map, isEqualTo(newMap));
	}

	public void getMapReturnsExpectedObject() {
		Map<String, String> map = new HashMap<String, String>();
		assertThat("",//
				new MapBuilder<String, String>(map).newInstance(), isSameAs(map));
	}

	public void putBehavesAsExpected() {
		MapBuilder<String, String> mb = new MapBuilder<String, String>();
		assertThat("",//
				mb.newInstance(), isEmpty());
		MapBuilder<String, String> got = mb.put("one", "1");
		assertThat("",//
				got, isSameAs(mb));
		assertThat("",//
				mb.newInstance(), hasSize(1), containsKey("one"), containsValue("1"));
	}

	public void putAllBehavesAsExpected() {
		MapBuilder<String, String> mb = new MapBuilder<String, String>();
		assertThat("",//
				mb.newInstance(), isEmpty());
		MapBuilder<String, String> got = mb.putAll(Collections.singletonMap("one", "1"));
		assertThat("",//
				got, isSameAs(mb));
		assertThat("",//
				mb.newInstance(), hasSize(1), containsKey("one"), containsValue("1"));
	}

	public void putAllTransformBehavesAsExpected() {
		MapBuilder<String, String> mb = new MapBuilder<String, String>();
		assertThat("",//
				mb.newInstance(), isEmpty());
		MapBuilder<String, String> got = mb.putAll(IdentityTransform.<String> getInstance(), Collections.singleton("one"));
		assertThat("",//
				got, isSameAs(mb));
		assertThat("",//
				mb.newInstance(), hasSize(1), containsKey("one"), containsValue("one"));
	}
}
