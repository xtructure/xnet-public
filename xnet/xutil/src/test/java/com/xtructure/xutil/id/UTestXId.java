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
package com.xtructure.xutil.id;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;
import static com.xtructure.xutil.valid.ValidateUtils.matches;

import java.util.Arrays;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestXId {
	private static final String	HEX_CHAR_CLASS	= "[a-fA-F0-9]";
	private static final String	UUID_REGEX		= String.format("%1$s{8}-%1$s{4}-%1$s{4}-%1$s{4}-%1$s{12}", HEX_CHAR_CLASS);

	public void constructorSucceeds() {
		assertThat("",//
				XId.newId(), isNotNull());
		assertThat("",//
				XId.newId("string"), isNotNull());
		assertThat("",//
				XId.newId(1, 2, 3), isNotNull());
		assertThat("",//
				XId.newId("string", 1, 2, 3), isNotNull());
		assertThat("",//
				XId.newId(Arrays.asList(1, 2, 3)), isNotNull());
		assertThat("",//
				XId.newId("string", Arrays.asList(1, 2, 3)), isNotNull());
	}
	
//	public void constructorWithBadBaseThrowsException(String base){
//		
//	}

	public void compareToReturnsExpectedInt() {
		XId id1 = XId.newId();
		XId id1Dup = XId.newId(id1.getBase());
		XId id2 = XId.newId();
		assertThat("",//
				id1.compareTo(null), isEqualTo(1));
		assertThat("",//
				id1.compareTo(id1Dup), isEqualTo(0));
		assertThat("",//
				id1.compareTo(id2), isEqualTo(new CompareToBuilder().append(id1.toString(), id2.toString()).toComparison()));
	}

	public void equalsReturnsExpectedBoolean() {
		XId id1 = XId.newId();
		XId id1Dup = XId.newId(id1.getBase());
		XId id2 = XId.newId();
		assertThat("",//
				id1.equals(id1), isTrue());
		assertThat("",//
				id1.equals(id1Dup), isTrue());
		assertThat("",//
				id1.equals(null), isFalse());
		boolean eq = new EqualsBuilder().append(id1.toString(), id2.toString()).isEquals();
		assertThat("",//
				id1.equals(id2), eq ? isTrue() : isFalse());
	}

	public void getBaseReturnsExpectedString() {
		assertThat("",//
				XId.newId().getBase(), matches(UUID_REGEX));
		String idString = RandomStringUtils.randomAlphanumeric(20);
		assertThat("",//
				XId.newId(idString).getBase(), matches(idString));
	}

	public void hashCodeReturnsExpectedInt() {
		XId id = XId.newId();
		assertThat("",//
				id.hashCode(), isEqualTo(id.toString().hashCode()));
	}

	public void toStringReturnsExpectedString() {
		XId id = XId.newId();
		assertThat("",//
				id.toString(), isEqualTo(id.getBase()));
	}
}
