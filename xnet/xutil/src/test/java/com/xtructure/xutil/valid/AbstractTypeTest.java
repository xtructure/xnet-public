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
 * A base class for unit test for conditions based on types.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.4
 */
abstract class AbstractTypeTest
{
    /** Creates a new type test. */
    protected AbstractTypeTest()
    {
        super();
    }

    /**
     * Provides a type.
     * 
     * <p>
     * This method returns an array, each element of which is a subarray. Each
     * subarray is a single type.
     * </p>
     * 
     * @return an object
     */
    @DataProvider(name = "type")
    protected final Object[][] type()
    {
        return new Object[][] { //
        new Object[] { String.class }, //
                new Object[] { Number.class }, //
                new Object[] { Object.class } };
    }

    /**
     * Provides an type/object pair.
     * 
     * <p>
     * This method returns an array, each element of which is a subarray. Each
     * subarray is a type and an object.
     * </p>
     * 
     * @return an type/object pair
     */
    @DataProvider(name = "typeobject")
    protected final Object[][] typeobject()
    {
        final Object[] objects = new Object[] { "abc", 123, 7.8, true, null };
        final Object[][] typeArrays = type();
        final Object[][] rval = new Object[objects.length * typeArrays.length][];

        int index = 0;
        for (int i = 0; i < objects.length; i++)
        {
            for (int j = 0; j < typeArrays.length; j++)
            {
                final Object[] tmp = new Object[2];
                tmp[0] = typeArrays[j][0];
                tmp[1] = objects[i];
                rval[index++] = tmp;
            }
        }

        return rval;
    }
}
