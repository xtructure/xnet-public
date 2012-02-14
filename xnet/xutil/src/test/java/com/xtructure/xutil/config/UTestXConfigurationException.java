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
package com.xtructure.xutil.config;

import static com.xtructure.xutil.valid.ValidateUtils.*;
import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestXConfigurationException {
	public UTestXConfigurationException() {}

	public final void constructorSucceedsOnNull() {
		XConfigurationException xce = new XConfigurationException(null);
		assertThat("",//
				xce, isNotNull());
	}

	public final void constructorSucceedsOnString() {
		XConfigurationException xce = new XConfigurationException("");
		assertThat("",//
				xce, isNotNull());
	}

	public final void constructorSucceedsOnFormatString() {
		XConfigurationException xce = new XConfigurationException("msg %d%d%d", 1, 2, 3);
		assertThat("",//
				xce, isNotNull());
	}

	public final void constructorSucceedsOnNullWithCause() {
		XConfigurationException xce = new XConfigurationException(new IllegalArgumentException(), null);
		assertThat("",//
				xce, isNotNull());
	}

	public final void constructorSucceedsOnStringWithCause() {
		XConfigurationException xce = new XConfigurationException(new IllegalArgumentException(), "");
		assertThat("",//
				xce, isNotNull());
	}

	public final void constructorSucceedsOnFormatStringWithCause() {
		XConfigurationException xce = new XConfigurationException(new IllegalArgumentException(), "msgmsg %d%d%d", 1, 2, 3);
		assertThat("",//
				xce, isNotNull());
	}

	public final void constructorSucceedsOnNullWithNullCause() {
		XConfigurationException xce = new XConfigurationException((Throwable) null, null);
		assertThat("",//
				xce, isNotNull());
	}

	public final void constructorSucceedsOnStringWithNullCause() {
		XConfigurationException xce = new XConfigurationException((Throwable) null, "");
		assertThat("",//
				xce, isNotNull());
	}

	public final void constructorSucceedsOnFormatStringWithNullCause() {
		XConfigurationException xce = new XConfigurationException((Throwable) null, "msgmsg %d%d%d", 1, 2, 3);
		assertThat("",//
				xce, isNotNull());
	}
}
