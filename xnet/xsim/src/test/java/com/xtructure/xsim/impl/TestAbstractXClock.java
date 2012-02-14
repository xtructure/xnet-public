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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.xtructure.xsim.XClock;
import com.xtructure.xsim.XClockTest;
import com.xtructure.xsim.XTime;

/**
 * Unit test for {@link AbstractXClock}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
@Test(groups = { "unit:xsim" })
public final class TestAbstractXClock
        implements XClockTest
{
    /** Creates a new unit test for {@link AbstractXClock}. */
    public TestAbstractXClock()
    {
        super();
    }

    /**
     * Asserts that
     * {@link AbstractXClock#AbstractXClock(long, XTime.Phase, XTime.Phase[])}
     * succeeds when given reasonable arguments.
     */
    @Test
    public final void longConstructorSucceeds()
    {
        assertThat(new AbstractXClockImpl(1, PhaseImpl.PHASE_1, PhaseImpl
            .values()), is(notNullValue()));
    }

    /**
     * Asserts that
     * {@link AbstractXClock#AbstractXClock(long, XTime.Phase, XTime.Phase[])}
     * fails when given a negative tick.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void longConstructorNegativeTickFails()
    {
        new AbstractXClockImpl(-1, PhaseImpl.PHASE_1, PhaseImpl.values());
    }

    /**
     * Asserts that
     * {@link AbstractXClock#AbstractXClock(long, XTime.Phase, XTime.Phase[])}
     * fails when given a <code>null</code> phase.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void longConstructorNullPhaseFails()
    {
        new AbstractXClockImpl(1, null, PhaseImpl.values());
    }

    /**
     * Asserts that
     * {@link AbstractXClock#AbstractXClock(long, XTime.Phase, XTime.Phase[])}
     * fails when given a <code>null</code> phase array.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void longConstructorNullPhaseArrayFails()
    {
        new AbstractXClockImpl(1, PhaseImpl.PHASE_1, null);
    }

    /**
     * Asserts that
     * {@link AbstractXClock#AbstractXClock(long, XTime.Phase, XTime.Phase[])}
     * fails when given a phase array with a <code>null</code> element.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void longConstructorNullPhaseArrayElementFails()
    {
        new AbstractXClockImpl(1, PhaseImpl.PHASE_1, new PhaseImpl[] {
                PhaseImpl.PHASE_2, null });
    }

    /**
     * Asserts that
     * {@link AbstractXClock#AbstractXClock(long, XTime.Phase, XTime.Phase[])}
     * fails when given an unknown phase.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void longConstructorUnknownPhaseFails()
    {
        new AbstractXClockImpl(1, PhaseImpl.PHASE_1,
            new PhaseImpl[] { PhaseImpl.PHASE_2 });
    }

    /**
     * Asserts that {@link AbstractXClock#AbstractXClock(XTime.Phase[])}
     * succeeds when given reasonable arguments.
     */
    @Test
    public final void shortConstructorSucceeds()
    {
        assertThat(new AbstractXClockImpl(PhaseImpl.values()),
            is(notNullValue()));
    }

    /**
     * Asserts that {@link AbstractXClock#AbstractXClock(XTime.Phase[])} fails
     * when given a <code>null</code> phase array.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void shortConstructorNullPhaseArrayFails()
    {
        new AbstractXClockImpl(null);
    }

    /**
     * Asserts that {@link AbstractXClock#AbstractXClock(XTime.Phase[])} fails
     * when given a phase array with a <code>null</code> element.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void shortConstructorNullPhaseArrayElementFails()
    {
        new AbstractXClockImpl(new PhaseImpl[] { PhaseImpl.PHASE_2, null });
    }

    /**
     * Asserts that {@link AbstractXClock#AbstractXClock(XTime.Phase[])} fails
     * when given an empty phrase array.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void shortConstructorEmptyPhaseArrayFails()
    {
        new AbstractXClockImpl(new PhaseImpl[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void incrementPhase()
    {
        final XTime<PhaseImpl> incrementedTime = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values()).increment();

        assertThat(incrementedTime.getTick(), is(1L));
        assertThat(incrementedTime.getPhase(), is(PhaseImpl.PHASE_2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void incrementTick()
    {
        final XTime<PhaseImpl> incrementedTime = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_2, PhaseImpl.values()).increment();

        assertThat(incrementedTime.getTick(), is(2L));
        assertThat(incrementedTime.getPhase(), is(PhaseImpl.PHASE_1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToIdentical()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(PhaseImpl
            .values());

        assertThat(clock.compareTo(clock), is(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToSameTime()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());
        final AbstractXClockImpl sameClock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());

        assertThat(clock.compareTo(sameClock), is(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToSmallerTick()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());
        final AbstractXClockImpl smallerTickClock = new AbstractXClockImpl(0,
            PhaseImpl.PHASE_1, PhaseImpl.values());

        assertThat(clock.compareTo(smallerTickClock), is(greaterThan(0)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToSmallerPhase()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_2, PhaseImpl.values());
        final AbstractXClockImpl smallerPhaseClock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());

        assertThat(clock.compareTo(smallerPhaseClock), is(greaterThan(0)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToLargerTick()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());
        final AbstractXClockImpl largerTickClock = new AbstractXClockImpl(2,
            PhaseImpl.PHASE_1, PhaseImpl.values());

        assertThat(clock.compareTo(largerTickClock), is(lessThan(0)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void compareToLargerPhase()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());
        final AbstractXClockImpl largerPhaseClock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_2, PhaseImpl.values());

        assertThat(clock.compareTo(largerPhaseClock), is(lessThan(0)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test(expectedExceptions = { NullPointerException.class })
    public final void compareToNull()
    {
        new AbstractXClockImpl(1, PhaseImpl.PHASE_1, PhaseImpl.values())
            .compareTo(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsIdentical()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(PhaseImpl
            .values());

        assertThat(clock.equals(clock), is(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsSameTime()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());
        final AbstractXClockImpl sameClock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());

        assertThat(clock.equals(sameClock), is(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsDifferentTick()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());
        final AbstractXClockImpl differentTickClock = new AbstractXClockImpl(0,
            PhaseImpl.PHASE_1, PhaseImpl.values());

        assertThat(clock.equals(differentTickClock), is(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsDifferentPhase()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_2, PhaseImpl.values());
        final AbstractXClockImpl differentPhaseClock = new AbstractXClockImpl(
            1, PhaseImpl.PHASE_1, PhaseImpl.values());

        assertThat(clock.equals(differentPhaseClock), is(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsNull()
    {
        assertThat(new AbstractXClockImpl(1, PhaseImpl.PHASE_1, PhaseImpl
            .values()).equals((XClock<?>)null), is(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void equalsNonClock()
    {
        assertThat(new AbstractXClockImpl(1, PhaseImpl.PHASE_1, PhaseImpl
            .values()).equals("not a clock"), is(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void hashCodeIdentical()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(PhaseImpl
            .values());

        assertThat(clock.hashCode(), is(clock.hashCode()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void hashCodeSameTime()
    {
        final AbstractXClockImpl clock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());
        final AbstractXClockImpl sameClock = new AbstractXClockImpl(1,
            PhaseImpl.PHASE_1, PhaseImpl.values());

        assertThat(clock.hashCode(), is(sameClock.hashCode()));
    }
    
    public final void setTimeSucceeds(){
    	final AbstractXClock<PhaseImpl> CLOCK = new AbstractXClockImpl(PhaseImpl.values());
    	final XTime<PhaseImpl> NEW_TIME = new SimpleXTime<PhaseImpl>(999l, PhaseImpl.PHASE_2);
    	assertThat("",//
    			CLOCK.getTime(), is(not(NEW_TIME)));
    	
    	CLOCK.setTime(NEW_TIME);
    	assertThat("",//
    			CLOCK.getTime(), is(NEW_TIME));
    }   
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public final void setTimeOnBadTimeDoesNothing(){
    	final AbstractXClock<PhaseImpl> CLOCK = new AbstractXClockImpl(PhaseImpl.values());
    	final XTime<PhaseImpl> NEW_TIME = new XTime<PhaseImpl>(){
			@Override
			public PhaseImpl getPhase() {return null;}
			@Override
			public long getTick() {return 0;}
			@Override
			public int compareTo(XTime<PhaseImpl> o) {return 0;}
    	};
    	CLOCK.setTime(NEW_TIME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	@Test
    public final void toStringSucceeds()
    {
        assertThat(new AbstractXClockImpl(PhaseImpl.values()).toString(),
            is(notNullValue()));
    }

    /** A simple implementation of {@link AbstractXClock}. */
    static final class AbstractXClockImpl
            extends AbstractXClock<PhaseImpl>
    {
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
         */
        protected AbstractXClockImpl(
                final long tick,
                final PhaseImpl phase,
                final PhaseImpl[] allPhases)
        {
            super(tick, phase, allPhases);
        }

        /**
         * Creates a new clock.
         * 
         * @param allPhases
         *            all the phases of this clock
         */
        protected AbstractXClockImpl(
                final PhaseImpl[] allPhases)
        {
            super(allPhases);
        }
    }
}
