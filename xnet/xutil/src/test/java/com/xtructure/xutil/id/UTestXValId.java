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
package com.xtructure.xutil.id;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

import com.xtructure.xutil.xml.XmlUnit;

@Test(groups = { "unit:xutil" })
public class UTestXValId {
	public void constructorSucceeds() {
		assertThat("",//
				XValId.newId(), isNotNull());
		assertThat("",//
				XValId.newId(UTestXValId.class), isNotNull());
		assertThat("",//
				XValId.newId(1, 2, 3), isNotNull());
		assertThat("",//
				XValId.newId("base"), isNotNull());
		assertThat("",//
				XValId.newId("base", UTestXValId.class), isNotNull());
		assertThat("",//
				XValId.newId("base", 1, 2, 3), isNotNull());
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithNullTypeThrowsException() {
		XValId.newId((Class<?>) null);
	}

	@Test(expectedExceptions = { UnsupportedOperationException.class })
	public void createChildThrowsException() {
		XValId.newId().createChild(1);
	}

	public void getTypeReturnsExpectedObject() {
		assertThat("",//
				XValId.newId().getType(), isEqualTo(String.class));
		assertThat("",//
				XValId.newId(UTestXValId.class).getType(), isEqualTo(UTestXValId.class));
	}

	public void toAttributeReturnsExpectedObject() {
		XValId<?> id = XValId.newId();
		assertThat("",//
				id.toAttribute().toString(),//
				isEqualTo(XmlUnit.newAttribute(id.getBase(), String.class).toString()));
		id = XValId.newId(UTestXValId.class);
		assertThat("",//
				id.toAttribute().toString(),//
				isEqualTo(XmlUnit.newAttribute(id.getBase(), UTestXValId.class).toString()));
	}

	public void toElementReturnsExpectedObject() {
		XValId<?> id = XValId.newId();
		assertThat("",//
				id.toElement().toString(),//
				isEqualTo(XmlUnit.newElement(id.getBase(), String.class).toString()));
		id = XValId.newId(UTestXValId.class);
		assertThat("",//
				id.toElement().toString(),//
				isEqualTo(XmlUnit.newElement(id.getBase(), UTestXValId.class).toString()));
	}
}
