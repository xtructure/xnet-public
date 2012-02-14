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
package com.xtructure.xutil;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.Test;

import com.xtructure.xutil.id.XValId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestValueMap {
	private static final XValId<Double>	ID	= XValId.newId("ID", Double.class);

	public void constructorSucceeds() {
		ValueMap map = new ValueMap();
		assertThat("",//
				map, isNotNull());
		ValueMap mapDup = new ValueMap(map);
		assertThat("",//
				mapDup, isEqualTo(map));
	}

	public void compareToReturnsExpectedInt() {
		ValueMap map1 = new ValueMap();
		ValueMap map2 = new ValueMap();
		assertThat("",//
				map1.compareTo(null), isEqualTo(Integer.MAX_VALUE));
		assertThat("",//
				map1.compareTo(map1), isEqualTo(0));
		assertThat("",//
				map1.compareTo(map2), isEqualTo(0));

		map1.set(ID, 0.5);
		assertThat("",//
				map1.compareTo(map1), isEqualTo(0));
		assertThat("",//
				map1.compareTo(map2), isEqualTo(1));

		map2.set(ID, 0.5);
		assertThat("",//
				map1.compareTo(map2), isEqualTo(0));

		map1.set(ID, -1.0);
		assertThat("",//
				map1.compareTo(map2), isEqualTo(-1));
	}

	public void equalsReturnsExpectedBoolean() {
		ValueMap map1 = new ValueMap();
		ValueMap map2 = new ValueMap();
		assertThat("",//
				map1.equals(null), isFalse());
		assertThat("",//
				map1.equals(map1), isTrue());
		assertThat("",//
				map1.equals(map2), isTrue());

		map1.set(ID, 0.5);
		assertThat("",//
				map1.equals(map1), isTrue());
		assertThat("",//
				map1.equals(map2), isFalse());

		map2.set(ID, 0.5);
		assertThat("",//
				map1.equals(map2), isTrue());

		map1.set(ID, -1.0);
		assertThat("",//
				map1.equals(map2), isFalse());
	}

	public void getAndSetBehaveAsExpected() {
		ValueMap map = new ValueMap();
		assertThat("",//
				map.get(ID), isNull());
		map.set(ID, 0.5);
		assertThat("",//
				map.get(ID), isEqualTo(0.5));
	}

	public void keySetReturnsExpectedSet() {
		ValueMap map = new ValueMap();
		map.set(ID, 1.0);
		assertThat("",//
				map.keySet(), hasSize(1), containsElement(ID));
	}

	public void sizeReturnsExpectedInt() {
		ValueMap map = new ValueMap();
		assertThat("",//
				map.size(), isEqualTo(0));
		map.set(ID, 0.5);
		assertThat("",//
				map.size(), isEqualTo(1));
		map.set(ID, 1.0);
		assertThat("",//
				map.size(), isEqualTo(1));
	}

	public void toStringReturnsExpectedString() {
		ValueMap map = new ValueMap();
		assertThat("",//
				map.toString(), isEqualTo("{}"));
		map.set(ID, 1.0);
		assertThat("",//
				map.toString(), isEqualTo("{\"ID:[java.lang.Double]\":1.0}"));
	}
}
