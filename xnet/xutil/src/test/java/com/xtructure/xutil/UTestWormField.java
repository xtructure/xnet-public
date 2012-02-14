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

package com.xtructure.xutil;

import static com.xtructure.xutil.valid.ValidateUtils.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for {@link WormField}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
@Test(groups = { "unit:xutil" })
public final class UTestWormField
{
    /** Creates new unit test for {@link WormField}. */
    public UTestWormField()
    {
        super();
    }

    /** Asserts that {@link WormField#WormField()} succeeds. */
    public final void constructorSucceeds()
    {
        assertThat("constructor", new WormField<Object>(), isNotNull());
    }

    /**
     * Asserts that {@link WormField#isInitialized()} returns <code>false</code>
     * before {@link WormField#initValue(Object)} is called.
     */
    public final void initialStateIsUninitialized()
    {
        assertThat("initial state", new WormField<Object>().isInitialized(),
            isFalse());
    }

    /**
     * Asserts that {@link WormField#getValue()} fails before
     * {@link WormField#initValue(Object)} is called.
     */
    @Test(expectedExceptions = { IllegalStateException.class })
    public final void getValueOnUninitializedInstanceFails()
    {
        new WormField<Object>().getValue();
    }

    /**
     * Asserts that {@link WormField#isInitialized()} returns <code>true</code>
     * after {@link WormField#initValue(Object)} is called.
     */
    public final void initValueInitializesInstance()
    {
        final WormField<Object> field = new WormField<Object>();
        field.initValue(new Object());
        assertThat("state after init", field.isInitialized(), isTrue());
    }

    /**
     * Asserts that {@link WormField#getValue()} returns the value provided to
     * {@link WormField#initValue(Object)} on the same field.
     * 
     * @param value
     *            the value to be passed to {@link WormField#initValue(Object)}
     */
    @Test(dataProvider = "value")
    public final void getValueOnInitializedInstanceSucceeds(
            final Object value)
    {
        final WormField<Object> field = new WormField<Object>();
        field.initValue(value);
        assertThat("getValue after init", field.getValue(), isSameAs(value));
    }

    /**
     * Asserts that {@link WormField#initValue(Object)} will fail on the second
     * attempt to call it on the same field.
     */
    @Test(expectedExceptions = { IllegalStateException.class })
    public final void initOnInitializedInstanceFails()
    {
        final Object value = new Object();
        final WormField<Object> field = new WormField<Object>();
        field.initValue(value);
        field.initValue(value);
    }

    /**
     * Provides values.
     * 
     * <p>
     * This method returns an array, each element of which is a subarray. Each
     * subarray is a value.
     * </p>
     * 
     * @return values
     */
    @DataProvider(name = "value")
    @SuppressWarnings("unused")
    private final Object[][] value()
    {
        return new Object[][] { //
        new Object[] { null }, //
                new Object[] { "foo" }, //
                new Object[] { 123 } };
    }
}
