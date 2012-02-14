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

import static com.xtructure.xutil.valid.ValidateUtils.*;

import java.util.regex.Pattern;

import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestPatternComparator {
	public UTestPatternComparator() {
	}

	public final void getInstanceReturnsSameInstance() {
		PatternComparator pc1 = PatternComparator.getInstance();
		PatternComparator pc2 = PatternComparator.getInstance();
		assertThat("",//
				pc1, isSameAs(pc2));
	}

	public final void compareWithTwoNullsIsZero() {
		assertThat("",//
				PatternComparator.getInstance().compare(null, null), isEqualTo(0));
	}

	public final void compareWithOneNullIsNotZero() {
		Pattern p = Pattern.compile("2b|[^(2b)]");
		assertThat("",//
				PatternComparator.getInstance().compare(p, null), isLessThan(0));
		assertThat("",//
				PatternComparator.getInstance().compare(null, p), isGreaterThan(0));
	}
	
	public final void compareWithDifferentPatternsReturnsExpected(){
		Pattern p1 = Pattern.compile("2b|[^(2b)]");
		Pattern p2 = Pattern.compile("a*b*");
		assertThat("",//
				PatternComparator.getInstance().compare(p1, p2), isLessThan(0));
		assertThat("",//
				PatternComparator.getInstance().compare(p2, p1), isGreaterThan(0));
		
	}

}
