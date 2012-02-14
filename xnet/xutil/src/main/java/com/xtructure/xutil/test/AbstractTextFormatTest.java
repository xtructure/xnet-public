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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import javolution.text.TextFormat;

import org.testng.annotations.Test;

/**
 * The Class AbstractTextFormatTest.
 *
 * @param <T> the generic type
 * @author Luis Guimbarda
 */
@Test(groups = { "text:xutil" })
public abstract class AbstractTextFormatTest<T> {
	
	/** The cls. */
	private final Class<T>	cls;

	/**
	 * Instantiates a new abstract text format test.
	 *
	 * @param cls the cls
	 */
	public AbstractTextFormatTest(Class<T> cls) {
		this.cls = cls;
	}

	/**
	 * Generate expected string.
	 *
	 * @param t the t
	 * @return the string
	 */
	public abstract String generateExpectedString(T t);

	/**
	 * Instances.
	 *
	 * @return the object[][]
	 */
	public abstract Object[][] instances();

	/**
	 * Constructor succeeds.
	 */
	public void constructorSucceeds() {
		TextFormat<T> tf = TextFormat.getInstance(cls);
		assertThat("",//
				tf, isNotNull());
	}

	/**
	 * Parses the returns expected instance.
	 *
	 * @param t the t
	 */
	@Test(dataProvider = "instances")
	public final void parseReturnsExpectedInstance(T t) {
		TextFormat<T> tf = TextFormat.getInstance(cls);
		assertThat("",//
				tf, isNotNull());
		String format = generateExpectedString(t);
		T newT = tf.parse(format);
		assertThat("",//
				tf.format(newT), isEqualTo(tf.format(t)));
	}

	/**
	 * Format returns expected text.
	 *
	 * @param t the t
	 */
	@Test(dataProvider = "instances")
	public final void formatReturnsExpectedText(T t) {
		TextFormat<T> tf = TextFormat.getInstance(cls);
		assertThat("",//
				tf, isNotNull());
		assertThat("",//
				tf.format(t).toString(), isEqualTo(generateExpectedString(t)));
	}
}
