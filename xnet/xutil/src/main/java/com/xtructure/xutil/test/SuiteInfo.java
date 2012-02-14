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

import org.apache.commons.lang.ClassUtils;
import org.testng.ITestResult;

/**
 * Information about a test suite.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
final class SuiteInfo
        extends AbstractEntityInfo<PackageInfo>
{
    /** The file name for all suite files. */
    private static final String FILE_NAME = "index.html";

    /** The detail table header. */
    private static final String DETAIL_TABLE_HEADER = "<tr><th>Package</th>" //
            + "<th>Failed</th>" //
            + "<th>Skipped</th>" //
            + "<th>Passed</th></tr>";

    /**
     * Creates new suite information.
     * 
     * @param name
     *            the name of this suite
     */
    SuiteInfo(
            final String name)
    {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    public final String getRelativeFileName()
    {
        return FILE_NAME;
    }

    /** {@inheritDoc} */
    @Override
    protected final String getChildName(
            final ITestResult result)
    {
        return ClassUtils.getPackageName(result.getTestClass().getName());
    }

    /** {@inheritDoc} */
    @Override
    protected final PackageInfo newChildInstance(
            final ITestResult result)
    {
        return new PackageInfo(getChildName(result));
    }

    /** {@inheritDoc} */
    @Override
    protected final String getDetailTableHeader()
    {
        return DETAIL_TABLE_HEADER;
    }
}
