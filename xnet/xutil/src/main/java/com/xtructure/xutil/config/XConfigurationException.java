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

package com.xtructure.xutil.config;

import com.xtructure.xutil.XRuntimeException;

/**
 * A configuration exception.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class XConfigurationException
        extends XRuntimeException
{
    /** The serial version UID of this class. */
    private static final long serialVersionUID = -6841693191462208936L;

    /**
     * Creates a new configuration exception.
     * 
     * @param cause
     *            the underlying cause of this exception
     * 
     * @param format
     *            the format of a message describing this exception
     * 
     * @param args
     *            the arguments to the format of a message describing this
     *            exception
     */
    public XConfigurationException(
            final Throwable cause,
            final String format,
            final Object... args)
    {
        super(cause, format, args);
    }

    /**
     * Creates a new configuration exception.
     * 
     * @param format
     *            the format of a message describing this exception
     * 
     * @param args
     *            the arguments to the format of a message describing this
     *            exception
     */
    public XConfigurationException(
            final String format,
            final Object... args)
    {
        super(format, args);
    }
}
