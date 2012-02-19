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

package com.xtructure.xsim.impl;

import com.xtructure.xsim.XTime;
import com.xtructure.xsim.XTime.Phase;

/**
 * A simple implementation of {@link Phase} used for testing.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
enum PhaseImpl
        implements XTime.Phase<PhaseImpl>
{
    /** A phase. */
    PHASE_1,

    /** Another phase. */
    PHASE_2;
}