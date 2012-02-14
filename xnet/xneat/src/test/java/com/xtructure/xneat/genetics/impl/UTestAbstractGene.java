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
package com.xtructure.xneat.genetics.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestAbstractGene {
	private static final XId					ID1				= XId.newId("ID").createChild(1);
	private static final XId					ID2				= XId.newId("ID").createChild(2);
	private static final NodeGeneConfiguration	CONFIGURATION	= NodeGeneConfiguration.builder(null).newInstance();

	public void constructorSucceeds() {
		assertThat("",//
				new DummyGene(ID1, CONFIGURATION), isNotNull());
		assertThat("",//
				new DummyGene(ID1, new DummyGene(ID2, CONFIGURATION)), isNotNull());
	}

	public void compareToReturnsExpectedInt() {
		DummyGene gene1 = new DummyGene(ID1, CONFIGURATION);
		DummyGene gene2 = new DummyGene(ID2, CONFIGURATION);
		assertThat("",//
				gene1.compareTo(gene1), isEqualTo(0));
		assertThat("",//
				gene2.compareTo(gene2), isEqualTo(0));
		assertThat("",//
				gene1.compareTo(null), isEqualTo(1));
		assertThat("",//
				gene1.compareTo(gene2), isEqualTo(gene1.getInnovation().compareTo(gene2.getInnovation())));
		assertThat("",//
				gene2.compareTo(gene1), isEqualTo(gene2.getInnovation().compareTo(gene1.getInnovation())));
	}

	public void getConfigurationAndFieldMapBehaveAsExpected() {
		DummyGene gene = new DummyGene(ID1, CONFIGURATION);
		assertThat("",//
				gene.getConfiguration(), isEqualTo(CONFIGURATION));
		assertThat("",//
				gene.getFieldMap().getFieldIds(), isEqualTo(gene.getConfiguration().getParameterIds()));
	}

	public void getIdReturnsExpectedObject() {
		XId id = ID1.createChild(RandomUtil.nextInteger());
		assertThat("",//
				new DummyGene(id, CONFIGURATION).getId(), isEqualTo(id));
	}

	public void getInnovationNumberReturnsExpectedInt() {
		XId id = ID1.createChild(RandomUtil.nextInteger());
		assertThat("",//
				new DummyGene(id, CONFIGURATION).getInnovation(), isEqualTo(Innovation.generate(id.getInstanceNum())));
	}

	public void toStringReturnsExpectedString() {
		DummyGene gene = new DummyGene(ID1, CONFIGURATION);
		assertThat("",//
				gene.toString(),//
				isEqualTo(new ToStringBuilder(gene, ToStringStyle.SHORT_PREFIX_STYLE)//
						.append("id", gene.getId())//
						.append("inn", gene.getInnovation())//
						.append("fields", gene.getFieldMap())//
						.toString()));
	}

	public void getAndSetAttributeBehavesAndExpected() {
		DummyGene gene = new DummyGene(ID1, NodeGeneConfiguration.builder(null).newInstance());
		double value = RandomUtil.nextDouble();
		double expectedValue = gene.setParameter(NodeGene.ACTIVATION_ID, value);
		assertThat("",//
				gene.getParameter(NodeGene.ACTIVATION_ID), isEqualTo(expectedValue));
	}
}
