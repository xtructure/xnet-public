/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.xtructure.xutil.test;

/**
 * Statistics about passed, failed and skipped tests.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
final class Stats
{
    /**
     * Returns a new set of statistics.
     * 
     * @return a new set of statistics
     */
    static final Stats getInstance()
    {
        return new Stats();
    }

    /** The number of passed invocations. */
    private int _numPassed = 0;

    /** The number of skipped invocations. */
    private int _numSkipped = 0;

    /** The number of failed invocations. */
    private int _numFailed = 0;

    /** Creates a new set of statistics. */
    private Stats()
    {
        super();
    }

    /**
     * Returns the number of all invocations.
     * 
     * @return the number of all invocations
     */
    private final int getNumInvocations()
    {
        return (_numPassed + _numSkipped + _numFailed);
    }

    /**
     * Returns the number of passed invocations.
     * 
     * @return the number of passed invocations
     */
    final int getNumPassed()
    {
        return _numPassed;
    }

    /**
     * Returns the percentage of passed invocations.
     * 
     * @return the percentage of passed invocations
     */
    final Float getPercentPassed()
    {
        return getPercent(_numPassed);
    }

    /**
     * Increments the number of passed invocations.
     * 
     * @param delta
     *            the number by which to increment the number of passed
     *            invocations
     */
    final void incrementNumPassed(
            final int delta)
    {
        _numPassed += delta;
    }

    /** Increments the number of passed invocations. */
    final void incrementNumPassed()
    {
        incrementNumPassed(1);
    }

    /**
     * Returns the number of skipped invocations.
     * 
     * @return the number of skipped invocations
     */
    final int getNumSkipped()
    {
        return _numSkipped;
    }

    /**
     * Returns the percentage of skipped invocations.
     * 
     * @return the percentage of skipped invocations
     */
    final Float getPercentSkipped()
    {
        return getPercent(_numSkipped);
    }

    /**
     * Increments the number of skipped invocations.
     * 
     * @param delta
     *            the number by which to increment the number of skipped
     *            invocations
     */
    final void incrementNumSkipped(
            final int delta)
    {
        _numSkipped += delta;
    }

    /** Increments the number of skipped invocations. */
    final void incrementNumSkipped()
    {
        incrementNumSkipped(1);
    }

    /**
     * Returns the number of failed invocations.
     * 
     * @return the number of failed invocations
     */
    final int getNumFailed()
    {
        return _numFailed;
    }

    /**
     * Returns the percentage of failed invocations.
     * 
     * @return the percentage of failed invocations
     */
    final Float getPercentFailed()
    {
        return getPercent(_numFailed);
    }

    /**
     * Increments the number of failed invocations.
     * 
     * @param delta
     *            the number by which to increment the number of failed
     *            invocations
     */
    final void incrementNumFailed(
            final int delta)
    {
        _numFailed += delta;
    }

    /** Increments the number of failed invocations. */
    final void incrementNumFailed()
    {
        incrementNumFailed(1);
    }

    /**
     * Returns the percentage of invocations represented by the given number.
     * 
     * @param num
     *            the number, the corresponding percentage of which should be
     *            returned
     * 
     * @return the percentage of invocations
     */
    private final Float getPercent(
            final int num)
    {
        return ((getNumInvocations() != 0)
                ? (((float)num) / ((float)getNumInvocations()))
                : null);
    }
}
