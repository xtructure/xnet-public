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
 * A base class for unit tests for conditions based on identity.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.4
 */
abstract class AbstractIdentityTest
{
    /** Creates a new identity test. */
    protected AbstractIdentityTest()
    {
        super();
    }

    /**
     * Provides an object.
     * 
     * <p>
     * This method returns an array, each element of which is a subarray. Each
     * subarray is a single object.
     * </p>
     * 
     * @return an object
     */
    @DataProvider(name = "object")
    protected final Object[][] object()
    {
        return new Object[][] { //
        new Object[] { "foo" }, //
                new Object[] { "foo" }, // another "copy" of foo
                new Object[] { 123 }, //
                new Object[] { true }, //
                new Object[] { null } };
    }

    /**
     * Provides a pair of objects.
     * 
     * <p>
     * This method returns an array, each element of which is a subarray. Each
     * subarray is a pair of objects.
     * </p>
     * 
     * @return a pair of objects
     */
    @DataProvider(name = "objectpair")
    protected final Object[][] objectpair()
    {
        final Object[][] objectArray = object();
        final Object[][] rval = new Object[objectArray.length
                * objectArray.length][];
        for (int i = 0, index = 0; i < objectArray.length; i++)
        {
            for (int j = 0; j < objectArray.length; j++, index++)
            {
                rval[index] = new Object[] { objectArray[i][0],
                        objectArray[j][0] };
            }
        }
        return rval;
    }
}
