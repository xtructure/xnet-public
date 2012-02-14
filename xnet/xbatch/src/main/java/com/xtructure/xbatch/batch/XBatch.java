/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xbatch.
 *
 * xbatch is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xbatch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xbatch.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xbatch.batch;

import com.xtructure.xsim.XSimulation;

/**
 * @author Luis Guimbarda
 * 
 */
public interface XBatch {
	/**
	 * @return the next simulation in this batch.
	 */
	XSimulation<?> getNextSimulation();

	/**
	 * @return true if this batch has more simulations, false otherwise.
	 */
	boolean hasMoreSimulations();

	/**
	 * @return the number of simulations remaining in this batch.
	 */
	int remaining();
}
