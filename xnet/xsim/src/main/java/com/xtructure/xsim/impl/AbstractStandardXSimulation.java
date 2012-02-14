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

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XComponent;
import com.xtructure.xutil.id.XId;

/**
 * A base class for simulations that use a {@link StandardXClock}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractStandardXSimulation
        extends AbstractXSimulation<StandardXClock.StandardTimePhase>
{
    /**
     * The logger for this class.
     */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory
        .getLogger(AbstractStandardXSimulation.class);

    /**
     * Creates a new simulation.
     * 
     * @param id
     *            the id of this simulation
     * 
     * @param clock
     *            the clock used by this simulation
     * 
     * @param components
     *            the components in this simulation
     */
    protected AbstractStandardXSimulation(
            final XId id,
            final StandardXClock clock,
            final Collection<XComponent<StandardXClock.StandardTimePhase>> components)
    {
        super(id, clock, components);
    }

    /**
     * Creates a new simulation.
     * 
     * @param id
     *            the id of this simulation
     * 
     * @param clock
     *            the clock used by this simulation
     */
    protected AbstractStandardXSimulation(
            final XId id,
            final StandardXClock clock)
    {
        super(id, clock);
    }

    /**
     * Creates a new simulation.
     * 
     * @param id
     *            the id of this simulation
     * 
     * @param components
     *            the components in this simulation
     */
    protected AbstractStandardXSimulation(
            final XId id,
            final Collection<XComponent<StandardXClock.StandardTimePhase>> components)
    {
        super(id, new StandardXClock(), components);
    }

    /**
     * Creates a new simulation.
     * 
     * @param id
     *            the id of this simulation
     */
    protected AbstractStandardXSimulation(
            final XId id)
    {
        super(id, new StandardXClock());
    }
}
