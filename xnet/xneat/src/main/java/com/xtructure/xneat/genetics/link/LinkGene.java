/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.genetics.link;

import com.xtructure.xneat.genetics.Gene;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * {@link LinkGene} extends the {@link Gene} interface to describe network
 * edges, which have a source and target node.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface LinkGene extends Gene {
	public static final XValId<Double>	WEIGHT_ID			= XValId.newId("weight", Double.class);
	public static final String			WEIGHT_DESCRIPTION	= "weight";

	/**
	 * Gets this link gene's source node's id.
	 * 
	 * @return this link gene's source node's id.
	 */
	public XId getSourceId();

	/**
	 * Gets this link gene's target node's id.
	 * 
	 * @return this link gene's target node's id.
	 */
	public XId getTargetId();
}
