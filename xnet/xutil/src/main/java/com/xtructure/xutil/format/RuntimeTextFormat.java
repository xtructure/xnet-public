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

import java.io.IOException;
import java.text.DecimalFormat;

import javolution.text.Cursor;

import com.xtructure.xutil.RuntimeDuration;

/**
 * @author Luis Guimbarda
 * 
 */
public class RuntimeTextFormat extends XTextFormat<RuntimeDuration> {
	private static final DecimalFormat		SECONDS_FORMAT				= new DecimalFormat("00.000");
	private static final DecimalFormat		MINUTES_FORMAT				= new DecimalFormat("00");
	private static final String				HOURS_TIME_FORMAT_STRING	= "%s%dh%sm%ss";
	private static final String				MINUTES_TIME_FORMAT_STRING	= "%s%sm%ss";
	private static final String				SECONDS_TIME_FORMAT_STRING	= "%s%ss";
	private static final long				MILLISECONDS_PER_HOUR		= 3600000;
	private static final long				MILLISECONDS_PER_MINUTE		= 60000;

	private static final RuntimeTextFormat	INSTANCE					= new RuntimeTextFormat();

	public static RuntimeTextFormat getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 */
	public RuntimeTextFormat() {
		super(RuntimeDuration.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javolution.text.TextFormat#format(java.lang.Object,
	 * java.lang.Appendable)
	 */
	@Override
	public Appendable format(RuntimeDuration milliseconds, Appendable str) throws IOException {
		if (milliseconds == null) {
			return str;
		}
		String sign = milliseconds.getDuration() >= 0 ? "" : "-";
		long ms = Math.abs(milliseconds.getDuration());
		long h = ms / MILLISECONDS_PER_HOUR;
		ms -= h * MILLISECONDS_PER_HOUR;
		long m = ms / MILLISECONDS_PER_MINUTE;
		ms -= m * MILLISECONDS_PER_MINUTE;
		double s = ms / 1000.0;
		if (h > 0) {
			return str.append(String.format(//
					HOURS_TIME_FORMAT_STRING,//
					sign,//
					h,//
					MINUTES_FORMAT.format(m),//
					SECONDS_FORMAT.format(s)));
		} else if (m > 0) {
			return str.append(String.format(//
					MINUTES_TIME_FORMAT_STRING,//
					sign,//
					MINUTES_FORMAT.format(m),//
					SECONDS_FORMAT.format(s)));
		} else {
			return str.append(String.format(//
					SECONDS_TIME_FORMAT_STRING,//
					sign,//
					SECONDS_FORMAT.format(s)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javolution.text.TextFormat#parse(java.lang.CharSequence,
	 * javolution.text.Cursor)
	 */
	@Override
	public RuntimeDuration parse(CharSequence arg0, Cursor arg1) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
