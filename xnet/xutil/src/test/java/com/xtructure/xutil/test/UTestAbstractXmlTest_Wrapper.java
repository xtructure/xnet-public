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

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import com.xtructure.xutil.test.AbstractXmlFormatTest.Wrapper;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestAbstractXmlTest_Wrapper {
	public void replaceHookReturnsAsExpected() {
		String format = RandomStringUtils.randomAlphanumeric(10) + "%s" + RandomStringUtils.randomAlphanumeric(10);
		String replacement = RandomStringUtils.randomAlphanumeric(10);
		String hook = String.format(format, Wrapper.ELEMENT_HOOK_NAME);
		String replace = String.format(format, replacement);
		assertThat("",//
				Wrapper.replaceHook(hook, replacement), isEqualTo(replace));
	}

	public void constructorSucceeds() {
		assertThat("",//
				new Wrapper(new Object()), isNotNull());
	}
}
