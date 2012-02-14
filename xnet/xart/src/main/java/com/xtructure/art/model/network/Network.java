/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.model.network;

import java.util.Set;

import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.gui.XVisualization;
import com.xtructure.xsim.impl.StandardXClock;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;

/**
 * An ART network.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * @version 0.9.6
 */
public interface Network extends XComponent<StandardXClock.StandardTimePhase> {
	/**
	 * Returns the ids of links in this network
	 * 
	 * @return the ids of links in this network
	 */
	public Set<XId> getLinkIds();

	/**
	 * Returns the ids of nodes in this network
	 * 
	 * @return the ids of nodes in this network
	 */
	public Set<XId> getNodeIds();

	/**
	 * Returns a visualization for this network
	 * 
	 * @return a visualization for this network
	 */
	public XVisualization<StandardTimePhase> getNetworkVisualization();

	/**
	 * Returns a visualization for the links in this network
	 * 
	 * @return a visualization for the links in this network
	 */
	public XVisualization<StandardTimePhase> getLinkVisualization();

	/**
	 * Returns a visualization for the nodes in this network
	 * 
	 * @return a visualization for the nodes in this network
	 */
	public XVisualization<StandardTimePhase> getNodeVisualization();
}
