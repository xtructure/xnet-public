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
 * A specification for unit tests of implementations of {@link XClock}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XClockTest
{
    /**
     * Asserts that {@link XClock#increment()} on a clock with a non-final phase
     * time will increment the phase and leave the tick unchanged.
     */
    void incrementPhase();

    /**
     * Asserts that {@link XClock#increment()} on a clock with a final phase
     * time will increment the tick and set the phase to the initial phase.
     */
    void incrementTick();

    /**
     * Asserts that {@link XClock#compareTo(Object)} returns zero when given
     * itself.
     */
    void compareToIdentical();

    /**
     * Asserts that {@link XClock#compareTo(Object)} returns zero when given
     * another clock with the same time.
     */
    void compareToSameTime();

    /**
     * Asserts that {@link XClock#compareTo(Object)} returns a positive number
     * when given another clock with a smaller tick value.
     */
    void compareToSmallerTick();

    /**
     * Asserts that {@link XClock#compareTo(Object)} returns a positive number
     * when given another clock with a smaller phase.
     */
    void compareToSmallerPhase();

    /**
     * Asserts that {@link XClock#compareTo(Object)} returns a negative number
     * when given another clock with a larger tick value.
     */
    void compareToLargerTick();

    /**
     * Asserts that {@link XClock#compareTo(Object)} returns a negative number
     * when given another clock with a larger phase.
     */
    void compareToLargerPhase();

    /**
     * Asserts that {@link XClock#compareTo(Object)} fails when given a
     * <code>null</code>.
     */
    void compareToNull();

    /**
     * Asserts that XClock.equals(Object) returns <code>true</code> when
     * given itself.
     */
    void equalsIdentical();

    /**
     * Asserts that XClock.equals(Object) returns <code>true</code> when
     * given another clock with the same time.
     */
    void equalsSameTime();

    /**
     * Asserts that XClock.equals(Object) returns <code>false</code>
     * when given another clock with a different tick value.
     */
    void equalsDifferentTick();

    /**
     * Asserts that XClock.equals(Object) returns <code>false</code>
     * when given another clock with a different phase.
     */
    void equalsDifferentPhase();

    /**
     * Asserts that XClock.equals(Object) returns <code>false</code>
     * when given <code>null</code>.
     */
    void equalsNull();

    /**
     * Asserts that XClock.equals(Object) returns <code>false</code>
     * when given a non-clock object.
     */
    void equalsNonClock();

    /**
     * Asserts that XClock.hashCode() returns the same value on
     * identical instances.
     */
    void hashCodeIdentical();

    /**
     * Asserts that XClock.hashCode() returns the same value on
     * instances with the same time.
     */
    void hashCodeSameTime();

    /**
     * Asserts that XClock.toString() succeeds.
     */
    void toStringSucceeds();
}
