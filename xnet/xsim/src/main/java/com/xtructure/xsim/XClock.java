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
 * A simulation clock.
 * 
 * @param <F>
 *            the type of phase of the time of this clock
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XClock<F extends XTime.Phase<F>>
        extends Comparable<XClock<F>>
{
    /**
     * Returns the current time.
     * 
     * @return the current time
     */
    XTime<F> getTime();

    /**
     * Increments the time and returns the new value.
     * 
     * <p>
     * Incrementing the time will cause the time to advance to the next clock
     * phase if possible. If the current time phase is the last available time
     * phase, the tick will be incremented, and the phase will be reset to the
     * first available time phase.
     * </p>
     * 
     * @return the newly incremented time
     */
    XTime<F> increment();

    /**
     * Sets the current time.
     * 
     * <p>
     * Implementations may refuse to allow certain time manipulations, such as
     * setting times with negative tick values, moving backwards in time, etc.
     * In these cases, this method will throw an
     * {@link IllegalArgumentException}.
     * </p>
     * 
     * @param time
     *            the time to which to set this clock
     * 
     * @throws IllegalArgumentException
     *             if the given time is <code>null</code>
     * 
     * @throws IllegalArgumentException
     *             if the given time is unacceptable
     */
    void setTime(
            XTime<F> time);
}
