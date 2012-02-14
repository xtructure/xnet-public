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

import java.awt.Color;
import java.io.IOException;

import javolution.text.CharArray;
import javolution.text.Cursor;
import javolution.text.TextBuilder;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public class UTestColorTextFormat {

	public final void getInstanceReturnsColorTextFormatObject() {
		assertThat("",//
				ColorTextFormat.getInstance(), isNotNull());
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void formatOnNullAppendableThrowsException() throws IOException {
		Appendable str = null;
		ColorTextFormat.getInstance().format(Color.WHITE, str);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void formatOnNullColorThrowsException() throws IOException {
		Appendable str = new TextBuilder();
		ColorTextFormat.getInstance().format(null, str);
	}

	@Test(dataProvider = "colorsAppendable")
	public final void formatReturnsExpectedAppendable(Color color, Appendable str) throws IOException {
		int r = color.getRed(), g = color.getGreen(), b = color.getBlue(), a = color.getAlpha();
		String colorString = String.format("rgba=[%d,%d,%d,%d]", r, g, b, a);
		Appendable expected = str.append(colorString.subSequence(0, colorString.length()));
		assertThat("",//
				ColorTextFormat.getInstance().format(color, str), isEqualTo(expected));
	}

	/**
	 * @param color
	 * @param str
	 * @throws IOException
	 */
	@Test(dataProvider = "colorsAppendable")
	public final void parseOnColorTextFormatOutputReturnsExpectedColor(Color color, Appendable str) throws IOException {
		Appendable newStr = new TextBuilder();
		CharArray colorString = ((TextBuilder) ColorTextFormat//
				.getInstance().format(color, newStr))//
				.toCharArray();
		assertThat("",//
				ColorTextFormat.getInstance().parse(colorString, Cursor.newInstance()),//
				isEqualTo(color));
	}

	@Test(dataProvider = "colorNames")
	public final void parseOnColorNameReturnsExpectedColor(Color color, Appendable str) {
		CharArray colorString = ((TextBuilder) str).toCharArray();
		assertThat("",//
				ColorTextFormat.getInstance().parse(colorString, Cursor.newInstance()),//
				isEqualTo(color));
	}

	@Test(dataProvider = "colorNames")
	public final void parseOnColorWithCursorNotAtBeginningReturnsExpectedColor(Color color, Appendable str) throws IOException {
		Appendable newStr = new TextBuilder();
		Color newColor = color == Color.RED ? Color.PINK : Color.RED;
		Cursor cursor = Cursor.newInstance();
		cursor.setIndex(((TextBuilder) str).length());

		assertThat("",//
				ColorTextFormat.getInstance()//
						.parse((TextBuilder) str//
								.append((TextBuilder) ColorTextFormat//
										.getInstance()//
										.format(newColor, newStr)), //
								cursor),//
				isEqualTo(newColor));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void parseWithNullColorStringThrowsException() {
		ColorTextFormat.getInstance().parse(null, Cursor.newInstance());
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void parseWithNullCursorThrowsException() throws IOException {
		Appendable newStr = new TextBuilder();
		ColorTextFormat.getInstance().parse(//
				(TextBuilder) ColorTextFormat//
						.getInstance()//
						.format(Color.WHITE, newStr),//
				null);
	}

	@Test(expectedExceptions = XFormatException.class)
	public final void parseOnNonColorStringThrowsException() {
		ColorTextFormat.getInstance().parse(//
				new TextBuilder("!@#$^$^& "), //
				Cursor.newInstance());
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "colorsAppendable")
	private final Object[][] colorsAppendable() {
		return new Object[][] {//
		//	
				new Object[] { Color.BLACK, new TextBuilder() },//
				new Object[] { Color.BLUE, new TextBuilder() },//
				new Object[] { Color.CYAN, new TextBuilder() },//
				new Object[] { Color.DARK_GRAY, new TextBuilder() },//
				new Object[] { Color.RED, new TextBuilder() },//
				new Object[] { Color.WHITE, new TextBuilder().append("huh? ") },//
				new Object[] { Color.BLACK, new TextBuilder("color ") },//
				new Object[] { Color.BLUE, new TextBuilder("266 ") },//
				new Object[] { Color.CYAN, new TextBuilder("!@#$^$^& ") },//
				new Object[] { Color.DARK_GRAY, new TextBuilder("ok ") },//
				new Object[] { Color.RED, new TextBuilder("done ") },//
				new Object[] { Color.WHITE, new TextBuilder("done ").append("and done. ") },//
		};
	}

	@SuppressWarnings("unused")
	@DataProvider(name = "colorNames")
	private final Object[][] colorNames() {
		return new Object[][] {//
		//	
				new Object[] { Color.BLACK, new TextBuilder("BLACK") },//
				new Object[] { Color.BLUE, new TextBuilder("BLUE") },//
				new Object[] { Color.CYAN, new TextBuilder("CYAN") },//
				new Object[] { Color.DARK_GRAY, new TextBuilder("DARK_GRAY") },//
				new Object[] { Color.RED, new TextBuilder("RED") },//
				new Object[] { Color.WHITE, new TextBuilder("WHITE") },//
		};
	}
}
