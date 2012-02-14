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

/**
 * @author Luis Guimbarda
 * 
 */
public class RuntimeDuration {
	private final long	duration;

	public RuntimeDuration(long startTime) {
		this(startTime, System.currentTimeMillis());
	}

	public RuntimeDuration(long startTime, long endTime) {
		duration = endTime - startTime;
	}

	public long getDuration() {
		return duration;
	}
}
