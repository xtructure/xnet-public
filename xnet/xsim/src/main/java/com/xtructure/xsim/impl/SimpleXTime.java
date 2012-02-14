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

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XTime;

/**
 * A simple, non-negative time.
 * 
 * @param <F>
 *            the type of phase of this time
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class SimpleXTime<F extends XTime.Phase<F>>
        implements XTime<F>
{
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(SimpleXTime.class);

    /**
     * The tick of this time.
     */
    private final long _tick;

    /**
     * The phase of this time.
     */
    private final F _phase;

    /**
     * Creates a new time.
     * 
     * @param tick
     *            the tick of this time
     * 
     * @param phase
     *            the phase of this time
     * 
     * @throws IllegalArgumentException
     *             if the given tick is negative
     * 
     * @throws IllegalArgumentException
     *             if the given phase is <code>null</code>
     */
    public SimpleXTime(
            final long tick,
            final F phase)
    {
        super();

        validateArg("tick", tick, isGreaterThanOrEqualTo(0L));
        validateArg("phase", phase, isNotNull());

        _tick = tick;
        _phase = phase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final long getTick()
    {
        LOGGER.trace("begin {}.getTick()", getClass().getSimpleName());
        LOGGER.trace("will return: {}", _tick);
        LOGGER.trace("end {}.getTick()", getClass().getSimpleName());
        return _tick;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final F getPhase()
    {
        LOGGER.trace("begin {}.getPhase()", getClass().getSimpleName());
        LOGGER.trace("will return: {}", _phase);
        LOGGER.trace("end {}.getPhase()", getClass().getSimpleName());
        return _phase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final int compareTo(
            final XTime<F> other)
    {
        LOGGER.trace("begin {}.compareTo({})", new Object[] {
                getClass().getSimpleName(), other });

        final int rval;
        if (other == null)
        {
            throw new NullPointerException("cannot compare to a null");
        }
        rval = new CompareToBuilder() //
            .append(_tick, other.getTick()) //
            .append(_phase, other.getPhase()) //
            .toComparison();

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.compareTo()", getClass().getSimpleName());

        return rval;
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
        else if ((obj == null) || !(obj instanceof XTime<?>))
        {
            rval = false;
        }
        else
        {
            final XTime<?> other = (XTime<?>)obj;
            rval = new EqualsBuilder() //
                .append(_tick, other.getTick()) //
                .append(_phase, other.getPhase()) //
                .isEquals();
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

        final int rval = new HashCodeBuilder() //
            .append(_tick) //
            .append(_phase) //
            .toHashCode();

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.hashCode()", getClass().getSimpleName());

        return rval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString()
    {
        LOGGER.trace("begin {}.toString()", getClass().getSimpleName());

        final String rval = String.format("%d (%s)", _tick, _phase);

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.toString()", getClass().getSimpleName());

        return rval;
    }
}
