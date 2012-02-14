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

import org.testng.ITestResult;

/**
 * Information about a package of test classes.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
final class PackageInfo
        extends AbstractEntityInfo<ClassInfo>
{
    /** The detail table header. */
    private static final String DETAIL_TABLE_HEADER = "<tr><th>Class</th>" //
            + "<th>Failed</th>" //
            + "<th>Skipped</th>" //
            + "<th>Passed</th></tr>";

    /**
     * Creates new package information.
     * 
     * @param name
     *            the name of the package described by this information
     */
    PackageInfo(
            final String name)
    {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected final String getChildName(
            final ITestResult result)
    {
        return result.getTestClass().getName();
    }

    /** {@inheritDoc} */
    @Override
    protected final ClassInfo newChildInstance(
            final ITestResult result)
    {
        return new ClassInfo(getChildName(result));
    }

    /** {@inheritDoc} */
    @Override
    protected final String getDetailTableHeader()
    {
        return DETAIL_TABLE_HEADER;
    }
}
