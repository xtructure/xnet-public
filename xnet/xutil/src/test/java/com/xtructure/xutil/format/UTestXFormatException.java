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
package com.xtructure.xutil.format;
import static com.xtructure.xutil.valid.ValidateUtils.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 *
 */
@Test(groups = { "unit:xutil" })
public class UTestXFormatException {

	@Test(dataProvider = "formatArgs")
	public final void constructorSucceeds(String format, Object[] args) {
		XFormatException ex = new XFormatException(format, args);
		assertThat("", //
				ex, isNotNull());
	}

	@Test(dataProvider = "causeFormatArgs")
	public final void constructorSucceeds(Throwable cause, String format, Object[] args) {
		XFormatException ex = new XFormatException(cause, format, args);
		assertThat("", //
				ex, isNotNull());
	}

	@DataProvider(name = "formatArgs")
	@SuppressWarnings("unused")
	private final Object[][] formatArgs() {
		return new Object[][] { //
		//
				new Object[] { null, null }, //
				new Object[] { null, new Object[] {} }, //
				new Object[] { null, new Object[] { "" } }, //
				new Object[] { null, new Object[] { 12, 2.16, "asdf" } }, //
				new Object[] { "", null }, //
				new Object[] { "", new Object[] {} }, //
				new Object[] { "", new Object[] { "" } }, //
				new Object[] { "", new Object[] { 12, 2.16, "asdf" } }, //
				new Object[] { "%d %f %s", null }, //
				new Object[] { "%d %f %s", new Object[] {} }, //
				new Object[] { "%d %f %s", new Object[] { "" } }, //
				new Object[] { "%d %f %s", new Object[] { 12, 2.16, "asdf" } } //
		};
	}

	@DataProvider(name = "causeFormatArgs")
	@SuppressWarnings("unused")
	private final Object[][] causeFormatArgs() {
		return new Object[][] { //
		//
				new Object[] { null, null, null }, //
				new Object[] { null, null, new Object[] {} }, //
				new Object[] { null, null, new Object[] { "" } }, //
				new Object[] { null, null, new Object[] { 12, 2.16, "asdf" } }, //
				new Object[] { null, "", null }, //
				new Object[] { null, "", new Object[] {} }, //
				new Object[] { null, "", new Object[] { "" } }, //
				new Object[] { null, "", new Object[] { 12, 2.16, "asdf" } }, //
				new Object[] { null, "%d %f %s", null }, //
				new Object[] { null, "%d %f %s", new Object[] {} }, //
				new Object[] { null, "%d %f %s", new Object[] { "" } }, //
				new Object[] { null, "%d %f %s", new Object[] { 12, 2.16, "asdf" } }, //
				new Object[] { new IllegalArgumentException(), null, null }, //
				new Object[] { new IllegalArgumentException(), null, new Object[] {} }, //
				new Object[] { new IllegalArgumentException(), null, new Object[] { "" } }, //
				new Object[] { new IllegalArgumentException(), null, new Object[] { 12, 2.16, "asdf" } }, //
				new Object[] { new IllegalArgumentException(), "", null }, //
				new Object[] { new IllegalArgumentException(), "", new Object[] {} }, //
				new Object[] { new IllegalArgumentException(), "", new Object[] { "" } }, //
				new Object[] { new IllegalArgumentException(), "", new Object[] { 12, 2.16, "asdf" } }, //
				new Object[] { new IllegalArgumentException(), "%d %f %s", null }, //
				new Object[] { new IllegalArgumentException(), "%d %f %s", new Object[] {} }, //
				new Object[] { new IllegalArgumentException(), "%d %f %s", new Object[] { "" } }, //
				new Object[] { new IllegalArgumentException(), "%d %f %s", new Object[] { 12, 2.16, "asdf" } } //
		};
	}


}
