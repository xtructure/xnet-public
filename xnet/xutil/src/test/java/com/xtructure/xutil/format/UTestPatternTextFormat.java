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

import java.io.IOException;
import java.util.regex.Pattern;

import javolution.text.CharArray;
import javolution.text.Cursor;
import javolution.text.TextBuilder;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xutil.PatternComparator;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestPatternTextFormat {
	private static final PatternTextFormat	PTF;
	private static final PatternComparator	PC;

	static {
		PTF = PatternTextFormat.getInstance();
		PC = PatternComparator.getInstance();
	}

	public final void getInstanceReturnsPatternTextFormatObject() {
		assertThat("",//
				PatternTextFormat.getInstance(), isNotNull());
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void formatOnNullPatternThrowsException() throws IOException {
		PTF.format(null, new TextBuilder());
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void formatOnNullAppendableThrowsException() throws IOException {
		PTF.format(Pattern.compile(""), (Appendable) null);
	}

	@Test(dataProvider = "patterns")
	public final void formatOnValidPatternsReturnsExpectedAppendable(Pattern pattern) throws IOException {
		Appendable expected = new TextBuilder().append(pattern.pattern());
		assertThat("",//
				PTF.format(pattern, new TextBuilder()),//
				isEqualTo(expected));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void parseOnNullCharSequenceThrowsException() {
		PTF.parse(new CharArray(), null);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void parseOnNullCursorThrowsException() {
		PTF.parse(null, new Cursor());
	}

	@Test(dataProvider = "patterns")
	public final void parseOnValidArgsReturnsExpectedPattern(Pattern pattern) throws IOException {

		assertThat("",//
				PC.compare(//
						PTF.parse(//
								(TextBuilder) PTF.format(//
										pattern, //
										new TextBuilder()),//
								new Cursor()),//
						pattern),//
				isEqualTo(0));
	}

	@Test(dataProvider = "patterns")
	public final void parseOnValidArgsWithCursorPointingToMiddleReturnsExpectedPattern(Pattern pattern) throws IOException {
		TextBuilder tb = (TextBuilder) PTF.format(//
				pattern, //
				new TextBuilder());
		Cursor cursor = new Cursor();
		cursor.setIndex(tb.length());
		Pattern newPattern = Pattern.compile("manumission");
		tb.append(PTF.format(newPattern, new TextBuilder()));
//		tb = (TextBuilder) PTF.format(newPattern, tb);
		assertThat("",//
				PC.compare(//
						PTF.parse(tb, cursor),//
						newPattern),//
				isEqualTo(0));
	}

	@DataProvider(name = "patterns")
	@SuppressWarnings("unused")
	private final Object[][] patterns() {
		return new Object[][] { //
		//
				new Object[] { Pattern.compile("") },//
				new Object[] { Pattern.compile("alligator") },//
				new Object[] { Pattern.compile("[01]*") },//
				new Object[] { Pattern.compile("\\d\\d\\d") },//
		};
	}

}
