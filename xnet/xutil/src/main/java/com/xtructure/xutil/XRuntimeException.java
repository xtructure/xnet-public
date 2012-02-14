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

/**
 * A base implementation for unchecked Xtructure exceptions.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class XRuntimeException
        extends RuntimeException
{
    /** The default message for Xtructure exceptions. */
    private static final String DEFAULT_MESSAGE = "XException";

    /** The serial version UID of this class. */
    private static final long serialVersionUID = -4502182686686974645L;

    /**
     * Creates a new Xtructure exception.
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
    protected XRuntimeException(
            final Throwable cause,
            final String format,
            final Object... args)
    {
        super(MiscUtils.format(DEFAULT_MESSAGE, format, args), cause);
    }

    /**
     * Creates a new Xtructure exception.
     * 
     * @param format
     *            the format of a message describing this exception
     * 
     * @param args
     *            the arguments to the format of a message describing this
     *            exception
     */
    protected XRuntimeException(
            final String format,
            final Object... args)
    {
        super(MiscUtils.format(DEFAULT_MESSAGE, format, args));
    }
}
