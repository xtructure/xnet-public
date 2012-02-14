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
package com.xtructure.xutil.coll;

/**
 * The {@link Transform} interface describes delegates that can take objects of
 * one type and produce objects of another (or possibly the same) type in a
 * predictable manner.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface Transform<OUT, IN> {
	/**
	 * Performs some calculation using the given IN and returns an appropriate
	 * OUT.
	 * 
	 * @param in
	 *            the object on which calculations are made
	 * @return the result of the calculations
	 */
	public OUT transform(IN in);
}
