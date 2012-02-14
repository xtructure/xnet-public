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
package com.xtructure.xneat.genetics.node;

import com.xtructure.xneat.genetics.Gene;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xutil.id.XValId;

/**
 * {@link NodeGene} extends the {@link Gene} interface to describe network
 * vertices, which have a {@link NodeType} in the context of
 * {@link NeuralNetwork}s
 * 
 * @author Luis Guimbarda
 * 
 */
public interface NodeGene extends Gene {
	public static final XValId<Double>	ACTIVATION_ID			= XValId.newId("activation", Double.class);
	public static final String			ACTIVATION_DESCRIPTION	= "activation";

	/**
	 * Gets the node type of this node gene.
	 * 
	 * @return the node type of this node gene.
	 */
	public NodeType getNodeType();
}
