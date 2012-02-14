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
package com.xtructure.xutil.xml;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestXmlUnit {
	private static final Object[][]	BAD_CLASS_ARGS;
	private static final Object[][]	BAD_NAME_ARGS;
	static {
		BAD_CLASS_ARGS = TestUtils.createData(Map.class);
		BAD_NAME_ARGS = TestUtils.createData(null, "");
	}

	public void constructorSucceeds() {
		assertThat("XmlUnit attribute constructor succeeds",//
				XmlUnit.newAttribute("attribute"), isNotNull());
		assertThat("XmlUnit attribute constructor with class succeeds",//
				XmlUnit.newAttribute("attribute", Double.class), isNotNull());
		assertThat("XmlUnit attribute constructor with XValId succeeds",//
				XmlUnit.newAttribute(XValId.newId("attribute", Double.class)), isNotNull());
		assertThat("XmlUnit element constructor succeeds",//
				XmlUnit.newElement("element"), isNotNull());
		assertThat("XmlUnit element constructor with class succeeds",//
				XmlUnit.newElement("element", Double.class), isNotNull());
		assertThat("XmlUnit element constructor with XValId succeeds",//
				XmlUnit.newElement(XValId.newId("element", Double.class)), isNotNull());
	}

	@DataProvider
	public Object[][] badClassArgs() {
		return BAD_CLASS_ARGS;
	}

	@Test(dataProvider = "badClassArgs", expectedExceptions = { IllegalArgumentException.class })
	public void constructorWithBadClassThrowsExpectedException(Class<?> cls) {
		XmlUnit.newAttribute("attribute", cls);
	}

	@DataProvider
	public Object[][] badNameArgs() {
		return BAD_NAME_ARGS;
	}

	@Test(dataProvider = "badNameArgs", expectedExceptions = { IllegalArgumentException.class })
	public void constructorAttrWithBadNameThrowsExpectedException(String badName) {
		XmlUnit.newAttribute(badName);
	}

	@Test(dataProvider = "badNameArgs", expectedExceptions = { IllegalArgumentException.class })
	public void constructorElemWithBadNameThrowsExpectedException(String badName) {
		XmlUnit.newElement(badName);
	}

	public void getNameReturnsExpectedString() {
		String name = RandomStringUtils.randomAlphanumeric(10);
		assertThat("", //
				XmlUnit.newAttribute(name).getName(), isEqualTo(name));
		assertThat("", //
				XmlUnit.newAttribute(name, Double.class).getName(), isEqualTo(name));
		assertThat("", //
				XmlUnit.newElement(name).getName(), isEqualTo(name));
		assertThat("", //
				XmlUnit.newElement(name, Double.class).getName(), isEqualTo(name));
	}

	public void getTypeReturnsExpectedClass() {
		String name = RandomStringUtils.randomAlphanumeric(10);
		assertThat("", //
				XmlUnit.newAttribute(name).getType(), isNull());
		assertThat("", //
				XmlUnit.newAttribute(name, Double.class).getType(), isEqualTo(Double.class));
		assertThat("", //
				XmlUnit.newElement(name).getType(), isNull());
		assertThat("", //
				XmlUnit.newElement(name, Double.class).getType(), isEqualTo(Double.class));
	}

	public void toStringReturnsExpectedString() {
		String name = RandomStringUtils.randomAlphanumeric(10);
		String expectedAttribute = String.format("%s{%s}", Attribute.class.getSimpleName(), name);
		String expectedElement = String.format("%s{%s}", Element.class.getSimpleName(), name);
		assertThat("",//
				XmlUnit.newAttribute(name).toString(), isEqualTo(expectedAttribute));
		assertThat("",//
				XmlUnit.newAttribute(name, Double.class).toString(), isEqualTo(expectedAttribute));
		assertThat("",//
				XmlUnit.newElement(name).toString(), isEqualTo(expectedElement));
		assertThat("",//
				XmlUnit.newElement(name, Double.class).toString(), isEqualTo(expectedElement));
	}
}
