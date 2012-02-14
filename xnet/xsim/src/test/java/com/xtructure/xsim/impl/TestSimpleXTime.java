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
import com.xtructure.xsim.XTime;

/**
 * Unit test for {@link SimpleXTime}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
@Test(groups = { "unit:xsim" })
public final class TestSimpleXTime
{
    /** Creates a new unit test for {@link SimpleXTime}. */
    public TestSimpleXTime()
    {
        super();
    }

    /**
     * Asserts that {@link SimpleXTime#SimpleXTime(long, XTime.Phase)} succeeds
     * when given reasonable arguments.
     */
    @Test
    public final void constructorSucceeds()
    {
        assertThat(new SimpleXTime<PhaseImpl>(1, PhaseImpl.PHASE_1),
            is(notNullValue()));
    }

    /**
     * Asserts that {@link SimpleXTime#SimpleXTime(long, XTime.Phase)} fails
     * when given a negative tick.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void constructorNegativeTickFails()
    {
        new SimpleXTime<PhaseImpl>(-1, PhaseImpl.PHASE_1);
    }

    /**
     * Asserts that {@link SimpleXTime#SimpleXTime(long, XTime.Phase)} fails
     * when given a <code>null</code> phase.
     */
    @Test(expectedExceptions = { IllegalArgumentException.class })
    public final void constructorNullPhaseFails()
    {
        new SimpleXTime<PhaseImpl>(1, null);
    }

    /**
     * Asserts that {@link SimpleXTime#compareTo(Object)} returns zero when
     * given itself.
     */
    @Test
    public final void compareToIdentical()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);

        assertThat(time.compareTo(time), is(0));
    }

    /**
     * Asserts that {@link SimpleXTime#compareTo(Object)} returns zero when
     * given another time with the same time.
     */
    @Test
    public final void compareToSameTime()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);
        final SimpleXTime<PhaseImpl> sameTime = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);

        assertThat(time.compareTo(sameTime), is(0));
    }

    /**
     * Asserts that {@link SimpleXTime#compareTo(Object)} returns a positive
     * number when given another time with a smaller tick value.
     */
    @Test
    public final void compareToSmallerTick()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);
        final SimpleXTime<PhaseImpl> smallerTickTime = new SimpleXTime<PhaseImpl>(
            0, PhaseImpl.PHASE_1);

        assertThat(time.compareTo(smallerTickTime), is(greaterThan(0)));
    }

    /**
     * Asserts that {@link SimpleXTime#compareTo(Object)} returns a positive
     * number when given another time with a smaller phase.
     */
    @Test
    public final void compareToSmallerPhase()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_2);
        final SimpleXTime<PhaseImpl> smallerPhaseTime = new SimpleXTime<PhaseImpl>(
            1, PhaseImpl.PHASE_1);

        assertThat(time.compareTo(smallerPhaseTime), is(greaterThan(0)));
    }

    /**
     * Asserts that {@link SimpleXTime#compareTo(Object)} returns a negative
     * number when given another time with a larger tick value.
     */
    @Test
    public final void compareToLargerTick()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);
        final SimpleXTime<PhaseImpl> largerTickTime = new SimpleXTime<PhaseImpl>(
            2, PhaseImpl.PHASE_1);

        assertThat(time.compareTo(largerTickTime), is(lessThan(0)));
    }

    /**
     * Asserts that {@link SimpleXTime#compareTo(Object)} returns a negative
     * number when given another time with a larger phase.
     */
    @Test
    public final void compareToLargerPhase()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);
        final SimpleXTime<PhaseImpl> largerPhaseTime = new SimpleXTime<PhaseImpl>(
            1, PhaseImpl.PHASE_2);

        assertThat(time.compareTo(largerPhaseTime), is(lessThan(0)));
    }

    /**
     * Asserts that {@link SimpleXTime#compareTo(Object)} fails when given a
     * <code>null</code>.
     */
    @Test(expectedExceptions = { NullPointerException.class })
    public final void compareToNull()
    {
        new SimpleXTime<PhaseImpl>(1, PhaseImpl.PHASE_1).compareTo(null);
    }

    /**
     * Asserts that {@link SimpleXTime#equals(Object)} returns <code>true</code>
     * when given itself.
     */
    @Test
    public final void equalsIdentical()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);

        assertThat(time.equals(time), is(true));
    }

    /**
     * Asserts that {@link SimpleXTime#equals(Object)} returns <code>true</code>
     * when given another time with the same tick and phase.
     */
    @Test
    public final void equalsSameTickAndPhase()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);
        final SimpleXTime<PhaseImpl> sameTime = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);

        assertThat(time.equals(sameTime), is(true));
    }

    /**
     * Asserts that {@link SimpleXTime#equals(Object)} returns
     * <code>false</code> when given another time with a different tick value.
     */
    @Test
    public final void equalsDifferentTick()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);
        final SimpleXTime<PhaseImpl> differentTickTime = new SimpleXTime<PhaseImpl>(
            0, PhaseImpl.PHASE_1);

        assertThat(time.equals(differentTickTime), is(false));
    }

    /**
     * Asserts that {@link SimpleXTime#equals(Object)} returns
     * <code>false</code> when given another time with a different phase.
     */
    @Test
    public final void equalsDifferentPhase()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_2);
        final SimpleXTime<PhaseImpl> differentPhaseTime = new SimpleXTime<PhaseImpl>(
            1, PhaseImpl.PHASE_1);

        assertThat(time.equals(differentPhaseTime), is(false));
    }

    /**
     * Asserts that {@link SimpleXTime#equals(Object)} returns
     * <code>false</code> when given <code>null</code>.
     */
    @Test
    public final void equalsNull()
    {
        assertThat(new SimpleXTime<PhaseImpl>(1, PhaseImpl.PHASE_1)
            .equals((XClock<?>)null), is(false));
    }

    /**
     * Asserts that {@link SimpleXTime#equals(Object)} returns
     * <code>false</code> when given a non-time object.
     */
    @Test
    public final void equalsNonTime()
    {
        assertThat(new SimpleXTime<PhaseImpl>(1, PhaseImpl.PHASE_1)
            .equals("not a time"), is(false));
    }

    /**
     * Asserts that {@link SimpleXTime#hashCode()} returns the same value on
     * identical instances.
     */
    @Test
    public final void hashCodeIdentical()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);

        assertThat(time.hashCode(), is(time.hashCode()));
    }

    /**
     * Asserts that {@link SimpleXTime#hashCode()} returns the same value on
     * instances with the same tick and phase.
     */
    @Test
    public final void hashCodeSameTime()
    {
        final SimpleXTime<PhaseImpl> time = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);
        final SimpleXTime<PhaseImpl> sameTime = new SimpleXTime<PhaseImpl>(1,
            PhaseImpl.PHASE_1);

        assertThat(time.hashCode(), is(sameTime.hashCode()));
    }

    /**
     * Asserts that {@link SimpleXTime#toString()} succeeds.
     */
    @Test
    public final void toStringSucceeds()
    {
        assertThat(new SimpleXTime<PhaseImpl>(1, PhaseImpl.PHASE_1).toString(),
            is(notNullValue()));
    }
}
