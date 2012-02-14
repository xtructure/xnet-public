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

import static com.xtructure.xutil.valid.ValidateUtils.and;
import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isOfExactType;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestIntegerXOption {
	public void constructorSucceeds() {
		assertThat("",//
				new IntegerXOption("name", "opt", "longOpt", "description"),//
				isNotNull());
	}

	public void processValueReturnsExpectedObject() throws ParseException {
		XOption<?> opt = new IntegerXOption("name", "opt", "longOpt", "description");
		Options options = new Options();
		options.addOption(opt);
		String[] args = new String[] { "-opt", "1" };
		XOption.parseArgs(options, args);
		assertThat("",//
				opt.processValue(), and(//
						isNotNull(),//
						isOfExactType(Integer.class),//
						isEqualTo(1)));
	}
}
