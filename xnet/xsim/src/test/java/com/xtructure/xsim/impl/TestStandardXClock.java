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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import com.xtructure.xsim.XClock;
import com.xtructure.xsim.XClockTest;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;

/**
 * Unit test for {@link StandardXClock}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
@Test(groups = { "unit:xsim" })
public final class TestStandardXClock
        implements XClockTest
{
    /** Creates a new unit test for {@link StandardXClock}. */
    public TestStandardXClock()
    {
        super();
    }

    /**
     * Asserts that
     * {@link StandardXClock#StandardXClock(long, StandardTimePhase)} succeeds
     * when given reasonable arguments.
     */
    @Test
    public final void longConstructorSucceeds()
    {
        assertThat(new StandardXClock(1, StandardTimePhase.PREPARE),
            is(notNullValue()));
    }

    /**
     * Asserts that
     * {@link StandardXClock#StandardXClock(long, StandardTimePhase)} fails when
     * given a negative tick.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void longConstructorNegativeTickFails()
    {
        new StandardXClock(-1, StandardTimePhase.PREPARE);
    }

    /**
     * Asserts that
     * {@link StandardXClock#StandardXClock(long, StandardTimePhase)} fails when
     * given a <code>null</code> phase.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void longConstructorNullPhaseFails()
    {
        new StandardXClock(1, null);
    }

    /**
     * Asserts that {@link StandardXClock#StandardXClock()} succeeds.
     */
    @Test
    public final void shortConstructorSucceeds()
    {
        assertThat(new StandardXClock(), is(notNullValue()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void incrementPhase()
    {
        final XTime<StandardTimePhase> incrementedTime = new StandardXClock(1,
            StandardTimePhase.PREPARE).increment();

        assertThat(incrementedTime.getTick(), is(1L));
        assertThat(incrementedTime.getPhase(), is(StandardTimePhase.CALCULATE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void incrementTick()
    {
        final XTime<StandardTimePhase> incrementedTime = new StandardXClock(1,
            StandardTimePhase.CLEAN_UP).increment();

        assertThat(incrementedTime.getTick(), is(2L));
        assertThat(incrementedTime.getPhase(), is(StandardTimePhase.PREPARE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToIdentical()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.PREPARE);

        assertThat(clock.compareTo(clock), is(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToSameTime()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.PREPARE);
        final StandardXClock sameClock = new StandardXClock(1,
            StandardTimePhase.PREPARE);

        assertThat(clock.compareTo(sameClock), is(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToSmallerTick()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.PREPARE);
        final StandardXClock smallerTickClock = new StandardXClock(0,
            StandardTimePhase.PREPARE);

        assertThat(clock.compareTo(smallerTickClock), is(greaterThan(0)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToSmallerPhase()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.CALCULATE);
        final StandardXClock smallerPhaseClock = new StandardXClock(1,
            StandardTimePhase.PREPARE);

        assertThat(clock.compareTo(smallerPhaseClock), is(greaterThan(0)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToLargerTick()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.PREPARE);
        final StandardXClock largerTickClock = new StandardXClock(2,
            StandardTimePhase.PREPARE);

        assertThat(clock.compareTo(largerTickClock), is(lessThan(0)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToLargerPhase()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.PREPARE);
        final StandardXClock largerPhaseClock = new StandardXClock(1,
            StandardTimePhase.CALCULATE);

        assertThat(clock.compareTo(largerPhaseClock), is(lessThan(0)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test(expectedExceptions = { NullPointerException.class })
    public final void compareToNull()
    {
        new StandardXClock(1, StandardTimePhase.PREPARE).compareTo(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsIdentical()
    {
        final StandardXClock clock = new StandardXClock();

        assertThat(clock.equals(clock), is(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsSameTime()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.PREPARE);
        final StandardXClock sameClock = new StandardXClock(1,
            StandardTimePhase.PREPARE);

        assertThat(clock.equals(sameClock), is(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsDifferentTick()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.PREPARE);
        final StandardXClock differentTickClock = new StandardXClock(0,
            StandardTimePhase.PREPARE);

        assertThat(clock.equals(differentTickClock), is(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsDifferentPhase()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.CALCULATE);
        final StandardXClock differentPhaseClock = new StandardXClock(1,
            StandardTimePhase.PREPARE);

        assertThat(clock.equals(differentPhaseClock), is(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsNull()
    {
        assertThat(new StandardXClock().equals((XClock<?>)null), is(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsNonClock()
    {
        assertThat(new StandardXClock().equals("not a clock"), is(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void hashCodeIdentical()
    {
        final StandardXClock clock = new StandardXClock();

        assertThat(clock.hashCode(), is(clock.hashCode()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void hashCodeSameTime()
    {
        final StandardXClock clock = new StandardXClock(1,
            StandardTimePhase.PREPARE);
        final StandardXClock sameClock = new StandardXClock(1,
            StandardTimePhase.PREPARE);

        assertThat(clock.hashCode(), is(sameClock.hashCode()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void toStringSucceeds()
    {
        assertThat(new StandardXClock(1, StandardTimePhase.PREPARE).toString(),
            is(notNullValue()));
    }
}
