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
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestValueType {
	public void getValueTypeReturnsExpectedValueType() {
		assertThat("",//
				ValueType.getValueType(Boolean.class),//
				isEqualTo(ValueType.BOOLEAN));
		assertThat("",//
				ValueType.getValueType(Byte.class),//
				isEqualTo(ValueType.BYTE));
		assertThat("",//
				ValueType.getValueType(Double.class),//
				isEqualTo(ValueType.DOUBLE));
		assertThat("",//
				ValueType.getValueType(Float.class),//
				isEqualTo(ValueType.FLOAT));
		assertThat("",//
				ValueType.getValueType(Integer.class),//
				isEqualTo(ValueType.INTEGER));
		assertThat("",//
				ValueType.getValueType(Long.class),//
				isEqualTo(ValueType.LONG));
		assertThat("",//
				ValueType.getValueType(Short.class),//
				isEqualTo(ValueType.SHORT));
		assertThat("",//
				ValueType.getValueType(String.class),//
				isEqualTo(ValueType.STRING));
		assertThat("",//
				ValueType.getValueType(Object.class),//
				isNull());
	}

	public void getTypeReturnsExpectedClass() {
		assertThat("",//
				ValueType.BOOLEAN.getType(),//
				isEqualTo(Boolean.class));
		assertThat("",//
				ValueType.BYTE.getType(),//
				isEqualTo(Byte.class));
		assertThat("",//
				ValueType.DOUBLE.getType(),//
				isEqualTo(Double.class));
		assertThat("",//
				ValueType.FLOAT.getType(),//
				isEqualTo(Float.class));
		assertThat("",//
				ValueType.INTEGER.getType(),//
				isEqualTo(Integer.class));
		assertThat("",//
				ValueType.LONG.getType(),//
				isEqualTo(Long.class));
		assertThat("",//
				ValueType.SHORT.getType(),//
				isEqualTo(Short.class));
		assertThat("",//
				ValueType.STRING.getType(),//
				isEqualTo(String.class));
	}

	@Test(dataProvider = "classData")
	public <V extends Comparable<V>> void isTypeOfReturnsExpectedBoolean(Class<V> type) {
		ValueType valueType = ValueType.getValueType(type);
		if (valueType != null) {
			XValId<V> valueId = XValId.newId("id", type);
			assertThat("",//
					valueType.isTypeOf(null), isFalse());
			assertThat("",//
					valueType.isTypeOf(valueId), isTrue());
			assertThat("",//
					valueType.isTypeOf(XValId.newId("id", DummyComparable.class)),//
					isFalse());
		}
	}

	@DataProvider(name = "classData")
	public Object[][] classData() {
		return TestUtils.createData(Integer.class, DummyComparable.class, null);
	}

	private static final class DummyComparable implements Comparable<DummyComparable> {
		@Override
		public int compareTo(DummyComparable arg0) {
			return 0;
		}
	}
}
