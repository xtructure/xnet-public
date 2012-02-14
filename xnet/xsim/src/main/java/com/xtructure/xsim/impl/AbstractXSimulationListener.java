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

import org.slf4j.Logger;

import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XSimulation.Listener;
import com.xtructure.xsim.XSimulation.SimulationState;
import com.xtructure.xsim.XTime;

/**
 * A base implementation of {@link Listener}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractXSimulationListener
        implements Listener
{
    /**
     * The logger to which to log messages.
     */
    private final Logger _logger;

    /**
     * Creates a new simulation listener.
     * 
     * @param logger
     *            the logger to which to log messages, or <code>null</code> to
     *            supress logging
     */
    protected AbstractXSimulationListener(
            final Logger logger)
    {
        super();

        _logger = logger;
    }

    /**
     * Creates a new simulation listener.
     */
    protected AbstractXSimulationListener()
    {
        this(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void simulationComponentAdded(
            final XSimulation<?> sim,
            final XComponent<?> component)
    {
        if (_logger != null)
        {
            _logger.info("simulation {} added component {}", sim.getId(),
                component.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void simulationComponentRemoved(
            final XSimulation<?> sim,
            final XComponent<?> component)
    {
        if (_logger != null)
        {
            _logger.info("simulation {} removed component {}", sim.getId(),
                component.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void simulationTickDelayChanged(
            final XSimulation<?> sim,
            final int delay)
    {
        if (_logger != null)
        {
            _logger.info("simulation {} new tick delay is {}", sim.getId(),
                delay);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void simulationTimeChanged(
            final XSimulation<?> sim,
            final XTime<?> time)
    {
        if (_logger != null)
        {
            _logger.info("simulation {} new time is {}", sim.getId(), time);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void simulationStateChanged(
            final XSimulation<?> sim,
            final SimulationState state)
    {
        if (_logger != null)
        {
            _logger.info("simulation {} new state is {}", sim.getId(), state);
        }
    }
}
