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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.genetics.node.impl.NodeGeneImpl;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNodeType {
	public void parseReturnsExpectedObject() {
		for (NodeType nodeType : NodeType.values()) {
			assertThat("",//
					NodeType.parse(nodeType.toString()), isEqualTo(nodeType));
		}
		assertThat("",//
				NodeType.parse("not a node type"), isNull());
	}

	public void isInputOrBiasReturnsExpectedBoolean() {
		for (NodeType nodeType : NodeType.values()) {
			if (NodeType.INPUT.equals(nodeType) || NodeType.BIAS.equals(nodeType)) {
				assertThat("",//
						nodeType.isInputOrBias(), isTrue());
			} else {
				assertThat("",//
						nodeType.isInputOrBias(), isFalse());
			}
		}
	}

	public void isNodeTypeOfReturnsExpectedBoolean() {
		for (NodeType nodeType : NodeType.values()) {
			NodeGene node = new NodeGeneImpl(0, nodeType, NodeGeneConfiguration.DEFAULT_CONFIGURATION);
			for (NodeType nt : NodeType.values()) {
				if (nodeType.equals(nt)) {
					assertThat("",//
							nt.isNodeTypeOf(node), isTrue());
				} else {
					assertThat("",//
							nt.isNodeTypeOf(node), isFalse());
				}
			}
		}
	}

	public void isOutputReturnsExpectedBoolean() {
		for (NodeType nodeType : NodeType.values()) {
			if (NodeType.OUTPUT.equals(nodeType)) {
				assertThat("",//
						nodeType.isOutput(), isTrue());
			} else {
				assertThat("",//
						nodeType.isOutput(), isFalse());
			}
		}
	}
}
