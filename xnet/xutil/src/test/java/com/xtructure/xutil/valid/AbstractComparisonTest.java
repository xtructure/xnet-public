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

package com.xtructure.xutil.valid;

import org.testng.annotations.DataProvider;

/**
 * A base class for unit tests for conditions based on comparisons.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.4
 */
abstract class AbstractComparisonTest
{
    /** Creates a new comparison test. */
    protected AbstractComparisonTest()
    {
        super();
    }

    /**
     * Provides a comparable.
     * 
     * <p>
     * This method returns an array, each element of which is a subarray. Each
     * subarray is a single comparable.
     * </p>
     * 
     * @return a comparable
     */
    @DataProvider(name = "comparable")
    protected final Object[][] comparable()
    {
        return new Object[][] { //
        new Object[] { "foo" }, //
                new Object[] { "foo" }, // another "copy" of foo
                new Object[] { "bar" }, //
                new Object[] { 123 }, //
                new Object[] { 456 }, //
                new Object[] { 456.0 }, //
                new Object[] { 456.0 } };
    }

    /**
     * Provides a pair of objects.
     * 
     * <p>
     * This method returns an array, each element of which is a subarray. Each
     * subarray is a pair of objects, the first of which will be comparable.
     * </p>
     * 
     * @return a pair of objects
     */
    @DataProvider(name = "objectpair")
    protected final Object[][] objectpair()
    {
        final Object[][] objectArray = comparable();
        final Object[][] rval = new Object[objectArray.length
                * objectArray.length + 2][];
        rval[0] = new Object[] { "foo", Object.class };
        rval[1] = new Object[] { "foo", null };
        int index = 2;
        for (int i = 0; i < objectArray.length; i++)
        {
            for (int j = 0; j < objectArray.length; j++)
            {
                rval[index++] = new Object[] { objectArray[i][0],
                        objectArray[j][0] };
            }
        }
        return rval;
    }

    /**
     * A type-safe and <code>null</code>-safe compare.
     * 
     * @param <T>
     *            the type of given comparable
     * 
     * @param comparable
     *            the comparable to compare
     * 
     * @param object
     *            the object to compare
     * 
     * @return the result of comparing the given comparable to the given object,
     *         or <code>null</code> if the comparison could not be completed
     */
    @SuppressWarnings("unchecked")
    protected final <T> Integer compare(
            final Comparable<T> comparable,
            final Object object)
    {
        if (object == null)
        {
            return null;
        }
        try
        {
            return ((Comparable<T>)object).compareTo((T)comparable);
        }
        catch (ClassCastException classCastEx)
        {
            return null;
        }
    }
}
