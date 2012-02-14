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
package com.xtructure.xutil.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.xtructure.xutil.coll.ListBuilder;

/**
 * The Class TestUtils.
 *
 * @author Luis Guimbarda
 */
public final class TestUtils {
	
	/**
	 * Creates the parameters.
	 *
	 * @param param the param
	 * @param params the params
	 * @return the object[][]
	 */
	public static Object[][] createParameters(Object param, Object... params) {
		return new Object[][] { new ListBuilder<Object>().add(param).addAll(params).newImmutableInstance().toArray() };
	}

	/**
	 * Creates an array of parameter arrays for use by a data provider with the
	 * given data. Each parameter array will be of length 1.
	 *
	 * @param datas the datas
	 * @return the object[][]
	 */
	public static Object[][] createData(Object... datas) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();
		for (Object d : datas) {
			result.add(new Object[] { d });
		}
		return result.toArray(new Object[0][0]);
	}

	/**
	 * Creates a parameter array that crosses the data returned by two (possibly
	 * identical) dataProviders.
	 * 
	 * @param data1
	 *            An Object[][] produced by one dataProvider
	 * @param data2
	 *            An Object[][] produced by a second dataProvider
	 * @param dataN
	 *            Other Object[][] to cross
	 * @return An Object[][] such that, for each row, there exists a pair of
	 *         rows from data1 and data2 that were concatenated to produce it.
	 */
	public static Object[][] crossData(Object[][] data1, Object[][] data2, Object[][]... dataN) {
		ArrayList<Object[]> prmsList = new ArrayList<Object[]>();
		for (Object[] a : data1) {
			for (Object[] b : data2) {
				prmsList.add(new ListBuilder<Object>()//
						.addAll(a)//
						.addAll(b)//
						.newImmutableInstance()//
						.toArray(new Object[0]));
			}
		}
		for (Object[][] dataI : dataN) {
			ArrayList<Object[]> newPrmsList = new ArrayList<Object[]>();
			for (Object[] a : prmsList) {
				for (Object[] b : dataI) {
					newPrmsList.add(new ListBuilder<Object>()//
							.addAll(a)//
							.addAll(b)//
							.newImmutableInstance()//
							.toArray(new Object[0]));
				}
			}
			prmsList = newPrmsList;
		}
		return prmsList.toArray(new Object[0][0]);
	}

	/**
	 * Creates a parameter array that adds the data returned by one data
	 * provider to the other. There are no checks that the parameter lists are
	 * consistent.
	 *
	 * @param datas Other Object[][] to union
	 * @return An Object[][] containing the data result of the union
	 */
	public static Object[][] unionData(Object[][]... datas) {
		ListBuilder<Object[]> lb = new ListBuilder<Object[]>();
		for (Object[][] data : datas) {
			lb.addAll(data);
		}
		return lb.newImmutableInstance().toArray(new Object[0][0]);
	}

	/**
	 * Creates an Object[][] such that each parameter array is a combination of
	 * the corresponding parameter arrays in datas. There are no checks that the
	 * parameter lists are consistent.
	 * 
	 * @param datas
	 *            An Object[][] produced by one dataProvider
	 * @return An Object[][] containing the data result of the augmentation
	 */
	public static Object[][] augmentData(Object[][]... datas) {
		List<Object[]> list = new ArrayList<Object[]>();
		if (datas.length > 0) {
			int prmLength = datas[0].length;
			for (int i = 0; i < prmLength; i++) {
				ListBuilder<Object> lb = new ListBuilder<Object>();
				for (Object[][] data : datas) {
					lb.addAll(data[i]);
				}
				list.add(lb.newImmutableInstance().toArray(new Object[0]));
			}
		}
		return list.toArray(new Object[0][0]);
	}

	/**
	 * Construct with data.
	 *
	 * @param <T> the generic type
	 * @param constructor the constructor
	 * @param data the data
	 * @return the object[][]
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	public static <T> Object[][] constructWithData(Constructor<T> constructor, Object[][] data) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		ListBuilder<Object[]> lb = new ListBuilder<Object[]>();
		for (Object[] params : data) {
			lb.add(new Object[] { constructor.newInstance(params) });
		}
		return lb.newImmutableInstance().toArray(new Object[0][0]);
	}

	/**
	 * Creates a parameter array which includes all of data plus an addition
	 * Object[] containing only nulls. Check the first Object[] in data to
	 * determine array length. If there are no parameter arrays, or data is
	 * null, then the returned Object[][] will contain 1 parameter array:
	 * {null}.
	 * 
	 * @param data
	 *            An Object[][] produced by a dataProvider
	 * @return An Object[][] containing the parameter arrays in data plus a new
	 *         parameter array with all nulls.
	 */
	public static Object[][] nullable(Object[][] data) {
		if (data == null || data.length == 0) {
			return new Object[][] { new Object[] { null } };
		}
		Object[] nulls = new Object[data[0].length];
		for (int i = 0; i < nulls.length; i++) {
			nulls[i] = null;
		}
		return unionData(data, new Object[][] { nulls });
	}

	/**
	 * Boolean data.
	 *
	 * @return the object[][]
	 */
	public static Object[][] booleanData() {
		return new Object[][] {//
		//
				new Object[] { Boolean.TRUE },//
				new Object[] { Boolean.FALSE },//
		};
	}

	/**
	 * Integer data.
	 *
	 * @param min the min
	 * @param max the max
	 * @param step the step
	 * @return the object[][]
	 */
	public static Object[][] integerData(Integer min, Integer max, Integer step) {
		ListBuilder<Object[]> lb = new ListBuilder<Object[]>();
		for (Integer x = min; x <= max; x += step) {
			lb.add(new Object[] { x });
		}
		return lb.newImmutableInstance().toArray(new Object[0][0]);
	}

	/**
	 * Long data.
	 *
	 * @param min the min
	 * @param max the max
	 * @param step the step
	 * @return the object[][]
	 */
	public static Object[][] longData(Long min, Long max, Long step) {
		ListBuilder<Object[]> lb = new ListBuilder<Object[]>();
		for (Long x = min; x <= max; x += step) {
			lb.add(new Object[] { x });
		}
		return lb.newImmutableInstance().toArray(new Object[0][0]);
	}

	/**
	 * Float data.
	 *
	 * @param min the min
	 * @param max the max
	 * @param step the step
	 * @return the object[][]
	 */
	public static Object[][] floatData(Float min, Float max, Float step) {
		ListBuilder<Object[]> lb = new ListBuilder<Object[]>();
		for (Float x = min; x <= max; x += step) {
			lb.add(new Object[] { x });
		}
		return lb.newImmutableInstance().toArray(new Object[0][0]);
	}

	/**
	 * Double data.
	 *
	 * @param min the min
	 * @param max the max
	 * @param step the step
	 * @return the object[][]
	 */
	public static Object[][] doubleData(Double min, Double max, Double step) {
		ListBuilder<Object[]> lb = new ListBuilder<Object[]>();
		for (Double x = min; x <= max; x += step) {
			lb.add(new Object[] { x });
		}
		return lb.newImmutableInstance().toArray(new Object[0][0]);
	}

	/**
	 * Instantiates a new test utils.
	 */
	private TestUtils() {}
}
