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

package com.xtructure.xsim.gui;

import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XTime;

/**
 * A GUI for an {@link XSimulation}.
 * 
 * @param <F>
 *            the type of phase of the time of this simulation
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XSimulationGui<F extends XTime.Phase<F>>
{
    /**
     * Returns the simulation visualized by this GUI.
     * 
     * @return the simulation visualized by this GUI
     */
    XSimulation<F> getSimulation();
}
