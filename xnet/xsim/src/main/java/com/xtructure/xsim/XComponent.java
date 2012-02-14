/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xsim.
 *
 * xsim is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xsim is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xsim.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.xtructure.xsim;

import java.util.Set;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;

/**
 * A component of a simulation.
 * 
 * @param <F>
 *            the type of phase of this time
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XComponent<F extends XTime.Phase<F>>
        extends XIdObject
{
    /**
     * Returns the ids of all sources managed by this component.
     * 
     * @return the ids of all sources managed by this component
     */
    Set<XId> getSourceIds();

    /**
     * Returns the ids of all targets managed by this component.
     * 
     * @return the ids of all targets managed by this component
     */
    Set<XId> getTargetIds();

    /**
     * Adds the given border to this component.
     * 
     * @param border
     *            the border to be added
     * 
     * @throws IllegalArgumentException
     *             if the given border is <code>null</code>
     */
    void addBorder(
            XBorder border);

    /**
     * Removes the given border from this component.
     * 
     * @param border
     *            the border to be removed
     * 
     * @throws IllegalArgumentException
     *             if the given border is <code>null</code>
     */
    void removeBorder(
            XBorder border);

    /**
     * Returns data from the identified part.
     * 
     * @param partId
     *            the id of the part, the data for which should be returned
     * 
     * @return data from the identified part
     */
    Object getData(
            XId partId);

    /**
     * Updates this component to the given time.
     * 
     * @param time
     *            the time to which to update this component
     * 
     * @throws IllegalArgumentException
     *             if the given time is <code>null</code>
     */
    void update(
            XTime<F> time);
}
