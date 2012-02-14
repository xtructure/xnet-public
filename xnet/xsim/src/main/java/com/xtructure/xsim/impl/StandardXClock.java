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

package com.xtructure.xsim.impl;

import com.xtructure.xsim.XTime;

/**
 * A &quot;standard&quot; clock.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class StandardXClock
        extends AbstractXClock<StandardXClock.StandardTimePhase>
{
    /**
     * Creates a new standard clock.
     * 
     * @param tick
     *            the initial tick of this clock
     * 
     * @param phase
     *            the initial pahse of this clock
     */
    public StandardXClock(
            final long tick,
            final StandardTimePhase phase)
    {
        super(tick, phase, StandardTimePhase.values());
    }

    /**
     * Creates a new standard clock.
     */
    public StandardXClock()
    {
        super(StandardTimePhase.values());
    }

    /**
     * An enumeration of standard time phases.
     */
    public static enum StandardTimePhase
            implements XTime.Phase<StandardTimePhase>
    {
        /**
         * The preparation phase.
         */
        PREPARE,

        /**
         * The calculate phase.
         */
        CALCULATE,

        /**
         * The update phase.
         */
        UPDATE,

        /**
         * The clean-up phase.
         */
        CLEAN_UP;
    }
}
