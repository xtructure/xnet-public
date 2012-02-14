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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

import com.xtructure.xutil.xml.XmlUnit;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestXmlUnit_Attribute {
	public void constructorSucceeds() {
		assertThat("",//
				XmlUnit.newAttribute("attr"), isNotNull());
		assertThat("",//
				XmlUnit.newAttribute("attr", String.class), isNotNull());
	}

	/**
	 * @see UTestReadAttributes
	 */
	public void parseReturnsExpectedObject() {
		// nothing
	}

	/**
	 * @see UTestXmlFormat
	 */
	public void writeSucceeds() {
		// nothing
	}
}
