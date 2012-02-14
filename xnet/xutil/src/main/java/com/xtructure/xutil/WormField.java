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

import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

/**
 * A write-once, read-many field.
 * 
 * @param <T>
 *            the type of value of this field
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
public final class WormField<T>
{
    /** The value of this field. */
    private T _value = null;

    /** An indication of whether this field has been initialized. */
    private boolean _initialized = false;

    /** Creates a new WORM field. */
    public WormField()
    {
        super();
    }

    /**
     * Returns the value of this field.
     * 
     * @return the value of this field
     * 
     * @throws IllegalStateException
     *             if this field has not been initialized
     */
    public final T getValue()
    {
        validateState("initialized", _initialized, isTrue());

        return _value;
    }

    /**
     * Initializes the value of this field.
     * 
     * @param value
     *            the value of this field
     * 
     * @throws IllegalStateException
     *             if this field has already been initialized
     */
    public final void initValue(
            final T value)
    {
        validateState("initialized", _initialized, isFalse());

        _initialized = true;
        _value = value;
    }

    /**
     * Indicates whether this field has been initialized.
     * 
     * @return <code>true</code> if this field has been initialized;
     *         <code>false</code> otherwise
     */
    public final boolean isInitialized()
    {
        return _initialized;
    }

	/** {@inheritDoc} */
    @Override
    public String toString() {
    	if(isInitialized()){
    		return _value.toString();
    	}
    	return super.toString();
    }
}
