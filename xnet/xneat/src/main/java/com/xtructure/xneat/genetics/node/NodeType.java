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

/**
 * enumeration of the different node types.
 *
 * @author Luis Guimbarda
 */
public enum NodeType {
	
	/** The INPUT. {@link NodeType} for input nodes */
	INPUT, //
	/** The BIAS. {@link NodeType} for bias nodes */
	BIAS, //
	/** The HIDDEN. {@link NodeType} for hidden nodes */
	HIDDEN, //
	/** The OUTPUT. {@link NodeType} for output nodes */
	OUTPUT;
	
	/**
	 * Checks if the node type of the given {@link NodeGene} is equal to
	 * this.
	 *
	 * @param nodeGene the node gene
	 * @return true if the node type of the given {@link NodeGene} is equal
	 * to this, false otherwise
	 */
	public boolean isNodeTypeOf(NodeGene nodeGene) {
		return this.equals(nodeGene.getNodeType());
	}
	
	/**
	 * Checks if is input or bias.
	 *
	 * @return true, if is input or bias
	 */
	public boolean isInputOrBias() {
		return this.equals(INPUT) || this.equals(BIAS);
	}
	
	/**
	 * Checks if is output.
	 *
	 * @return true, if is output
	 */
	public boolean isOutput() {
		return this.equals(OUTPUT);
	}
	
	/**
	 * Gets the {@link NodeType} represented by the given string.
	 *
	 * @param s the s
	 * @return the {@link NodeType} represented by the given string.
	 */
	public static NodeType parse(String s) {
		String ss = s.toUpperCase();
		for (NodeType nodeType : values()) {
			if (nodeType.name().equals(ss)) {
				return nodeType;
			}
		}
		return null;
	}
}
