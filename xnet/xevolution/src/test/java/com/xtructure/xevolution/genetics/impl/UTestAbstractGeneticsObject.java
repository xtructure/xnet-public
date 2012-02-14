/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.genetics.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.GeneticsObject;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestAbstractGeneticsObject {
	public void constructorSucceeds() {
		assertThat("",//
				new DummyGeneticsObject(XId.newId()),//
				isNotNull());
	}

	public void getAndSetAttributeBehaveAsExpected() {
		GeneticsObject go = new DummyGeneticsObject(XId.newId());
		XValId<Double> doubleId = XValId.newId("doubleId", Double.class);
		assertThat("",//
				go.getAttribute(doubleId), isNull());
		Double oldValue = go.setAttribute(doubleId, 1.2);
		assertThat("",//
				oldValue, isNull());
		assertThat("",//
				go.getAttribute(doubleId), isEqualTo(1.2));
		oldValue = go.setAttribute(doubleId, 3.4);
		assertThat("",//
				oldValue, isEqualTo(1.2));
		assertThat("",//
				go.getAttribute(doubleId), isEqualTo(3.4));
	}

	public void getAttributesReturnsExepctedObject() {
		GeneticsObject go = new DummyGeneticsObject(XId.newId());
		XValId<Double> doubleId = XValId.newId("doubleId", Double.class);
		XValId<String> stringId = XValId.newId("stringId", String.class);
		ValueMap expected = new ValueMap();
		go.setAttribute(doubleId, 1.0);
		go.setAttribute(stringId, "string");
		expected.set(doubleId, 1.0);
		expected.set(stringId, "string");
		assertThat("",//
				go.getAttributes(), isEqualTo(expected));
	}
}
