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

import java.io.File;
import java.io.IOException;

import org.testng.ITestResult;

/**
 * Information about an invocation.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
final class InvocationInfo
        implements EntityInfo
{
    /** The name of the entity described by this information. */
    private final String _name;

    /** The statistics about the entity described by this information. */
    private final Stats _stats = Stats.getInstance();
    
    /** The result upon which this information is based. */
    private final ITestResult _result;

    /**
     * Creates new invocation information.
     * 
     * @param name
     *            the name of this invocation
     * 
     * @param result
     *            the result upon which this information is based
     */
    InvocationInfo(
            final String name,
            final ITestResult result)
    {
        super();

        _name = name;
        _result = result;
    }

    /**
     * Returns the result upon which this information is based.
     *
     * @return the result upon which this information is based
     */
    public final ITestResult getResult()
    {
        return _result;
    }

    /** {@inheritDoc} */
    @Override
    public final String getName()
    {
        return _name;
    }

    /** {@inheritDoc} */
    @Override
    public final String getRelativeFileName()
    {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public final Stats getStats()
    {
        return _stats;
    }

    /** {@inheritDoc} */
    @Override
    public final void process(
            final ITestResult result)
    {
        switch (result.getStatus())
        {
            case ITestResult.SUCCESS:
                _stats.incrementNumPassed();
                break;

            case ITestResult.SKIP:
                _stats.incrementNumSkipped();
                break;

            case ITestResult.FAILURE:
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
                _stats.incrementNumFailed();
                break;

            default:
                // don't know what to do...
                return;
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void write(
            final File rootDir)
        throws IOException
    {
        throw new UnsupportedOperationException(
            "invocation files not supported");
    }
}
