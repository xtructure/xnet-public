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
package com.xtructure.xutil.opt;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

@Test(groups = { "unit:xutil" })
public class UTestXOption {
	public void getOptionReturnsExpectedOption() {
		DummyXOption option = new DummyXOption("name", "opt", "longOpt", true, "description");
		Options options = new Options();
		options.addOption(option);
		assertThat("",//
				XOption.getOption("asdf", options), isNull());
		assertThat("",//
				XOption.getOption("name", options), isSameAs(option));
	}

	public void constructorSucceeds() {
		assertThat("",//
				new DummyXOption("name", "opt", "longOpt", true, "description"),//
				isNotNull());
	}

	public void getNameReturnsExpectedObject() {
		String name = RandomStringUtils.randomAlphanumeric(10);
		assertThat("",//
				new DummyXOption(name, "opt", "longOpt", true, "description").getName(),//
				isEqualTo(name));
	}

	public void hasValueReturnsExpectedBoolean() throws ParseException {
		DummyXOption option = new DummyXOption("name", "opt", "longOpt", true, "description");
		assertThat("",//
				option.hasValue(), isFalse());
		Options options = new Options();
		options.addOption(option);
		String[] args = new String[0];
		XOption.parseArgs(options, args);
		assertThat("",//
				option.hasValue(), isFalse());
		args = "-opt arg".split("\\s+");
		XOption.parseArgs(options, args);
		assertThat("",//
				option.hasValue(), isTrue());
	}
}
