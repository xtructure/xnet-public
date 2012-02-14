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
package com.xtructure.xutil.valid.comp;

import com.xtructure.xutil.valid.comp.AbstractComparisonCondition;


final class DummyComparisonCondition extends AbstractComparisonCondition<String> {
	public DummyComparisonCondition(String value) throws IllegalArgumentException {
		super(value);
	}

	private int		call	= 0;
	private String	last	= null;
	private boolean	rVal	= false;

	@Override
	public boolean makeComparison(String t) {
		this.call++;
		this.last = t;
		return rVal;
	}

	public int getCall() {
		return call;
	}

	public String getLast() {
		return last;
	}

	public boolean getrVal() {
		return rVal;
	}

	public void setrVal(boolean rVal) {
		this.rVal = rVal;
	}
}
