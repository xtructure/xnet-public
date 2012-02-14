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
 * Information about an entity.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
interface EntityInfo
{
    /**
     * Returns the name of the entity described by this information.
     * 
     * @return the name of the entity described by this information
     */
    String getName();

    /**
     * Returns the relative file name corresponding to this information.
     * 
     * @return the relative file name corresponding to this information
     */
    String getRelativeFileName();

    /**
     * Returns the statistics collected by this information.
     * 
     * @return the statistics collected by this information
     */
    Stats getStats();

    /**
     * Processes the given result.
     * 
     * @param result
     *            the result to process
     */
    void process(
            ITestResult result);

    /**
     * Writes this information to a file.
     * 
     * @param rootDir
     *            the root directory in which the written file should be located
     * 
     * @throws IOException
     *             if the writing could not be completed
     */
    void write(
            File rootDir)
        throws IOException;
}
