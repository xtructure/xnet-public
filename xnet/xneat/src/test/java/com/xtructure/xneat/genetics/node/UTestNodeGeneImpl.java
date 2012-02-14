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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.genetics.node.impl.AbstractNodeGene;
import com.xtructure.xneat.genetics.node.impl.NodeGeneImpl;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.id.XId;

@Test(groups = { "xml:xneat" })
public class UTestNodeGeneImpl {
	private static final NodeGeneConfiguration	CONFIGURATION;
	static {
		CONFIGURATION = NodeGeneConfiguration.builder(//
				XId.newId("UTestNodeGeneImpl"))//
				.setActivation(//
						Range.getInstance(0.0, 1.0),//
						Range.getInstance(0.0, 1.0))//
				.newInstance();
	}

	public void constructorSucceeded() {
		for (NodeType nodeType : NodeType.values()) {
			NodeGeneImpl nodeGene = new NodeGeneImpl(0, nodeType, CONFIGURATION);
			assertThat("",//
					nodeGene, isNotNull());
			assertThat("",//
					new NodeGeneImpl(0, nodeGene), isNotNull());
		}
	}

	public void getSetActivationBehavesAsExpected() {
		for (NodeType nodeType : NodeType.values()) {
			NodeGeneImpl nodeGene = new NodeGeneImpl(0, nodeType, CONFIGURATION);
			double newActivation = RandomUtil.nextDouble();
			newActivation = nodeGene.setActivation(newActivation);
			assertThat("",//
					nodeGene.getActivation(), isEqualTo(newActivation));
		}
	}

	public void getBaseIdReturnsExpectedId() {
		for (NodeType nodeType : NodeType.values()) {
			NodeGeneImpl nodeGene = new NodeGeneImpl(0, nodeType, CONFIGURATION);
			assertThat("",//
					nodeGene.getBaseId(), isEqualTo(AbstractNodeGene.NODE_BASE_ID));
		}
	}

	public void getNodeTypeReturnsExpectedNodeType() {
		for (NodeType nodeType : NodeType.values()) {
			NodeGeneImpl nodeGene = new NodeGeneImpl(0, nodeType, CONFIGURATION);
			assertThat("",//
					nodeGene.getNodeType(), isEqualTo(nodeType));
		}
	}

	public void toStringReturnsExpectedString() {
		for (NodeType nodeType : NodeType.values()) {
			NodeGeneImpl nodeGene = new NodeGeneImpl(0, nodeType, CONFIGURATION);
			assertThat("",//
					nodeGene.toString(),//
					isEqualTo(new ToStringBuilder(nodeGene, ToStringStyle.SHORT_PREFIX_STYLE)//
							.append("id", nodeGene.getId())//
							.append("nodeType", nodeGene.getNodeType())//
							.append("fields", nodeGene.getFieldMap())//
							.toString()));
		}
	}

	public void validateSucceeds() {
		for (NodeType nodeType : NodeType.values()) {
			NodeGeneImpl nodeGene = new NodeGeneImpl(0, nodeType, CONFIGURATION);
			nodeGene.validate();
		}
	}
}
