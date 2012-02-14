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

import static com.xtructure.xutil.valid.ValidateUtils.*;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XClock;
import com.xtructure.xsim.XTime;
import com.xtructure.xutil.coll.ListBuilder;

/**
 * A base class for {@link XClock} implementations.
 * 
 * @param <F>
 *            the type of phase of the time of this clock
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractXClock<F extends XTime.Phase<F>>
        implements XClock<F>
{
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(AbstractXClock.class);

    /**
     * The current time of this clock.
     */
    private SimpleXTime<F> _time;

    /**
     * The lock protecting {@link #_time}.
     */
    private final Lock _timeLock = new ReentrantLock();

    /**
     * A list of all phases of times of this clock.
     */
    private final List<F> _allPhases;

    /**
     * Creates a new clock.
     * 
     * @param tick
     *            the initial tick of this clock
     * 
     * @param phase
     *            the initial phase of this clock
     * 
     * @param allPhases
     *            all the phases of this clock
     * 
     * @throws IllegalArgumentException
     *             if the given phase is <code>null</code>
     * 
     * @throws IllegalArgumentException
     *             if the given phase array is <code>null</code>, contains a
     *             <code>null</code> element, or does not contain the given
     *             phase
     * 
     * @throws IllegalArgumentException
     *             if the given tick is negative
     */
    protected AbstractXClock(
            final long tick,
            final F phase,
            final F[] allPhases)
    {
        super();

        validateArg("phase", phase, isNotNull());
        validateArg("allPhases", allPhases, isNotNull(),
            everyElement(isNotNull()), containsElement(phase));

        _time = new SimpleXTime<F>(tick, phase);
        _allPhases = new ListBuilder<F>() //
            .addAll(allPhases) //
            .newImmutableInstance();
    }

    /**
     * Creates a new clock.
     * 
     * @param allPhases
     *            all the phases of this clock
     * 
     * @throws IllegalArgumentException
     *             if the given phase array is <code>null</code>, contains a
     *             <code>null</code> element, or is empty
     */
    protected AbstractXClock(
            final F[] allPhases)
    {
        this(0L, (((allPhases != null) && (allPhases.length > 0))
                ? allPhases[0]
                : null), allPhases);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final XTime<F> getTime()
    {
        LOGGER.trace("begin {}.getTime()", getClass().getSimpleName());

        _timeLock.lock();
        try
        {
            LOGGER.trace("will return: {}", _time);
            LOGGER.trace("end {}.getTime()", getClass().getSimpleName());
            return _time;
        }
        finally
        {
            _timeLock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final XTime<F> increment()
    {
        LOGGER.trace("begin {}.increment()", getClass().getSimpleName());

        _timeLock.lock();
        try
        {
            final F nextPhase = _allPhases.get( //
                (_allPhases.indexOf(_time.getPhase()) + 1) % _allPhases.size());
            final long tick = _time.getTick()
                    + (nextPhase.equals(_allPhases.get(0))
                            ? 1
                            : 0);
            _time = new SimpleXTime<F>(tick, nextPhase);

            LOGGER.trace("will return: {}", _time);
            LOGGER.trace("end {}.increment()", getClass().getSimpleName());

            return _time;
        }
        finally
        {
            _timeLock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void setTime(
            final XTime<F> time)
    {
        LOGGER.trace("begin {}.setTime({})", new Object[] {
                getClass().getSimpleName(), time });

        _timeLock.lock();
        try
        {
            _time = new SimpleXTime<F>(time.getTick(), time.getPhase());
            LOGGER.trace("end {}.setTime()", getClass().getSimpleName());
        }
        finally
        {
            _timeLock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final int compareTo(
            final XClock<F> other)
    {
        LOGGER.trace("begin {}.compareTo({})", new Object[] {
                getClass().getSimpleName(), other });

        _timeLock.lock();
        try
        {
            final int rval;
            if (other == null)
            {
                throw new NullPointerException("cannot compare to a null");
            }
            rval = _time.compareTo(other.getTime());

            LOGGER.trace("will return: {}", rval);
            LOGGER.trace("end {}.compareTo()", getClass().getSimpleName());

            return rval;
        }
        finally
        {
            _timeLock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(
            final Object obj)
    {
        LOGGER.trace("begin {}.equals({})", new Object[] {
                getClass().getSimpleName(), obj });

        final boolean rval;
        if (obj == this)
        {
            rval = true;
        }
        else if ((obj == null) || !(obj instanceof XClock<?>))
        {
            rval = false;
        }
        else
        {
            _timeLock.lock();
            try
            {
                final XClock<?> other = (XClock<?>)obj;
                rval = _time.equals(other.getTime());
            }
            finally
            {
                _timeLock.unlock();
            }
        }

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.equals()", getClass().getSimpleName());

        return rval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode()
    {
        LOGGER.trace("begin {}.hashCode()", getClass().getSimpleName());

        _timeLock.lock();
        try
        {
            final int rval = _time.hashCode();
            LOGGER.trace("will return: {}", rval);
            LOGGER.trace("end {}.hashCode()", getClass().getSimpleName());
            return rval;
        }
        finally
        {
            _timeLock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString()
    {
        LOGGER.trace("begin {}.toString()", getClass().getSimpleName());

        _timeLock.lock();
        try
        {
            final String rval = _time.toString();
            LOGGER.trace("will return: {}", rval);
            LOGGER.trace("end {}.toString()", getClass().getSimpleName());
            return rval;
        }
        finally
        {
            _timeLock.unlock();
        }
    }
}
