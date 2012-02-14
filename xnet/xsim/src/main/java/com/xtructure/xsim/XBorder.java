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

import java.util.Map;

import com.xtructure.xutil.id.XId;

/**
 * A border between {@link XComponent}s.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XBorder
{
    /**
     * Associates the given source and transform to the given target.
     * 
     * @param source
     *            the source to be associated
     * 
     * @param transform
     *            the transform to be associated
     * 
     * @param target
     *            the target to be associated
     * 
     * @return this border
     */
    XBorder associate(
            XAddress source,
            Transform transform,
            XAddress target);

    /**
     * Returns data for parts in the identified component.
     * 
     * <p>
     * This method returns a map, in which each entry associates a map that
     * associates a target id with a map from a source address to the data from
     * that part.
     * </p>
     * 
     * @param targetComponent
     *            the component for which data should be returned
     * 
     * @return data for the identified target
     */
    Map<XId, Map<XAddress, Object>> getData(
            XComponent<?> targetComponent);

    /** A transform. */
    interface Transform
    {
        /**
         * Transforms the given object.
         * 
         * @param orig
         *            the object to be transformed
         * 
         * @return the result of the transform
         */
        Object transform(
                Object orig);
    }
}
