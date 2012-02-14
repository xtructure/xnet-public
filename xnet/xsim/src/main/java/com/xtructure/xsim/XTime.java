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
 * A simulation time.
 * 
 * @param <F>
 *            the type of phase of this time
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XTime<F extends XTime.Phase<F>>
        extends Comparable<XTime<F>>
{
    /**
     * Returns the virtual time of this clock.
     * 
     * @return the virtual time of this clock
     */
    long getTick();

    /**
     * Returns the phase of this time.
     * 
     * @return the phase of this time
     */
    F getPhase();

    /**
     * A time phase.
     * 
     * @param <F>
     *            this type
     */
    public interface Phase<F extends Phase<F>>
    {
        // empty interface
    }
}
