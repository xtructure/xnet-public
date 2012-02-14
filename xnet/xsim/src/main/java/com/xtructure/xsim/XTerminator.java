/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xsim.
 *
 * xsim is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xsim is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xsim.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xsim;

/**
 * A specialized component of {@link XSimulation}s that indicate finishing
 * conditions for the simulation.
 * 
 * @param <F>
 *            the type of phase of this terminator
 * 
 * @author Luis Guimbarda
 * 
 */
public interface XTerminator<F extends XTime.Phase<F>> extends XComponent<F> {
	/**
	 * Indicates that the containing simulation should finish.
	 * 
	 * @return true if the containing simulation should finish, false otherwise.
	 */
	boolean terminalConditionReached();
}
