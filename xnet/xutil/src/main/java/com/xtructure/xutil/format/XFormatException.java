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

package com.xtructure.xutil.format;

import com.xtructure.xutil.XRuntimeException;

/**
 * An exception signalling a problem with a formatting operation.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class XFormatException
        extends XRuntimeException
{
    /** The serial version UID of this class. */
    private static final long serialVersionUID = -8654856660471394657L;

    /**
     * Creates a new formatting exception.
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
    public XFormatException(
            final Throwable cause,
            final String format,
            final Object... args)
    {
        // TODO Auto-generated constructor stub
        super(cause, format, args);

    }

    /**
     * Creates a new formatting exception.
     * 
     * @param format
     *            the format of a message describing this exception
     * 
     * @param args
     *            the arguments to the format of a message describing this
     *            exception
     */
    public XFormatException(
            final String format,
            final Object... args)
    {
        // TODO Auto-generated constructor stub
        super(format, args);

    }
}
