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

package com.xtructure.xutil.valid;

/**
 * A condition against which values may be tested.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.4
 */
public interface Condition
{
    /**
     * Checks if the given object satisfies this condition.
     * 
     * @param obj
     *            the object to be tested
     * 
     * @return <code>true</code> if the given object satisfies this condition;
     *         <code>false</code> otherwise
     */
    boolean isSatisfiedBy(
            Object obj);
}