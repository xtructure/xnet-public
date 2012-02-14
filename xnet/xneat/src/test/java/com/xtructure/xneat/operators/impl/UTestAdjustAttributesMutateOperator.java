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
package com.xtructure.xneat.operators.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import java.util.Collections;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xevolution.operator.Operator.OperationFailedException;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.Gene;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.genetics.node.impl.AbstractNodeGene;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestAdjustAttributesMutateOperator {
	private static final NEATGeneticsFactoryImpl	GENETICS_FACTORY;
	static {
		NEATEvolutionFieldMap evolutionFieldMap = NEATEvolutionConfigurationImpl//
				.builder(null)//
				.setInputNodeCount(3)//
				.setOutputNodeCount(3)//
				.setBiasNodeCount(0)//
				.setInitialConnectionProbability(1.0)//
				.newInstance().newFieldMap();
		GENETICS_FACTORY = new NEATGeneticsFactoryImpl(//
				evolutionFieldMap,//
				LinkGeneConfiguration.builder(XId.newId("UTestAdjustAttributesMutateOperator")).setWeight(Range.getInstance(0.0, 1.0), Range.getInstance(0.0, 1.0)).newInstance(),//
				NodeGeneConfiguration.builder(XId.newId("UTestAdjustAttributesMutateOperator")).setActivation(Range.getInstance(0.0, 1.0), Range.getInstance(0.0, 1.0)).newInstance());
	}

	@DataProvider(name = "flags")
	public Object[][] flags() {
		return TestUtils.crossData(TestUtils.booleanData(), TestUtils.booleanData());
	}

	@Test(dataProvider = "flags")
	public void constructorSucceeds(boolean mutateLink, boolean mutateNode) {
		XValId<Double> fieldId = XValId.newId("fieldId", Double.class);
		double mutateAttributeProbability = RandomUtil.nextDouble();
		double mutateGeneProbability = RandomUtil.nextDouble();
		if (mutateLink || mutateNode) {
			assertThat("",//
					new AdjustAttributesMutateOperator(mutateLink, mutateNode, fieldId, GENETICS_FACTORY),//
					isNotNull());
			assertThat("",//
					new AdjustAttributesMutateOperator(mutateLink, mutateNode, mutateAttributeProbability, mutateGeneProbability, GENETICS_FACTORY),//
					isNotNull());
		}
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorForSpecificAttributeOnFalseMutateLinkAndNodeThrowsException() {
		new AdjustAttributesMutateOperator(false, false, LinkGene.WEIGHT_ID, GENETICS_FACTORY);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void constructorForRandomAttributeOnFalseMutateLinkAndNodeThrowsException() {
		new AdjustAttributesMutateOperator(false, false, 0.0, 0.0, GENETICS_FACTORY);
	}

	@Test(expectedExceptions = { OperationFailedException.class })
	public void mutateWithNoLinksOrNodesToAdjustThrowsExeption() throws OperationFailedException {
		new AdjustAttributesMutateOperator(true, true, 0.0, 0.0, GENETICS_FACTORY).mutate(1, GENETICS_FACTORY.createGenome(0, new GeneMap()));
	}

	public void mutateReturnsExpectedGenome() throws OperationFailedException {
		NEATGenome<GeneMap> genome = GENETICS_FACTORY.createGenome(0);
		NEATGenome<GeneMap> child = new AdjustAttributesMutateOperator(true, true, 1.0, 1.0, GENETICS_FACTORY).mutate(1, genome);
		// mutate all genes
		assertThat("",//
				genome.getData(), hasSize(child.getData().size()));
		for (Gene gene : genome.getData()) {
			Gene childGene = child.getData().get(gene.getId());
			if (gene instanceof LinkGene) {
				assertThat("",//
						childGene.getParameter(LinkGene.WEIGHT_ID),//
						isNotEqualTo(gene.getParameter(LinkGene.WEIGHT_ID)));
			}
			if (gene instanceof NodeGene) {
				assertThat("",//
						childGene.getParameter(NodeGene.ACTIVATION_ID),//
						isNotEqualTo(gene.getParameter(NodeGene.ACTIVATION_ID)));
			}
		}
		// mutate one gene
		child = new AdjustAttributesMutateOperator(true, true, 0.0, 0.0, GENETICS_FACTORY).mutate(1, genome);
		assertThat("",//
				genome.getData(), hasSize(child.getData().size()));
		XId mutatedLinkGeneId = null;
		XId mutatedNodeGeneId = null;
		for (Gene gene : genome.getData()) {
			Gene childGene = child.getData().get(gene.getId());
			for (XId fieldId : gene.getFieldMap().getFieldIds()) {
				Object value = gene.getFieldMap().get(fieldId);
				Object childValue = childGene.getFieldMap().get(fieldId);
				if (value != null && !value.equals(childValue)) {
					if (childGene instanceof LinkGene) {
						assertThat("",//
								mutatedLinkGeneId, isNull());
						mutatedLinkGeneId = gene.getId();
					} else if (childGene instanceof NodeGene) {
						assertThat("",//
								mutatedNodeGeneId, isNull());
						mutatedNodeGeneId = gene.getId();
					}
				}
			}
		}
		assertThat("",//
				mutatedLinkGeneId, isNotNull());
		assertThat("",//
				mutatedNodeGeneId, isNotNull());
		// mutate genomes of number/boolean types
		Gene gene = new AbstractNodeGene(0, NodeType.HIDDEN, DummyConfiguration.INSTANCE) {
			@Override
			public void validate() throws IllegalStateException {}
		};
		genome = GENETICS_FACTORY.createGenome(0, new GeneMap(Collections.singleton(gene)));
		child = new AdjustAttributesMutateOperator(true, true, 1.0, 1.0, GENETICS_FACTORY).mutate(0, genome);
		assertThat("",//
				genome.getData(), hasSize(child.getData().size()));
		Gene childGene = child.getData().iterator().next();
		for (XId fieldId : gene.getFieldMap().getFieldIds()) {
			Object value = gene.getFieldMap().get(fieldId);
			assertThat("",//
					value, isNotNull());
			if (value instanceof Float || value instanceof Double || value instanceof Boolean) {
				assertThat(value.getClass().getName(),//
						value, isNotEqualTo(childGene.getFieldMap().get(fieldId)));
			} else {
				assertThat("",//
						value, isEqualTo(childGene.getFieldMap().get(fieldId)));
			}
		}
	}

	@Test(expectedExceptions = { OperationFailedException.class })
	public void mutateFailsOnNoAdjustmentsAndThrowsExpectedException() throws OperationFailedException {
		new AdjustAttributesMutateOperator(true, true, XValId.newId("not there", Double.class), GENETICS_FACTORY).mutate(1, GENETICS_FACTORY.createGenome(0));
	}

	@Test(expectedExceptions = { OperationFailedException.class })
	public void mutateFailsOnEmptyGeneMapAndThrowsExpectedException() throws OperationFailedException {
		new AdjustAttributesMutateOperator(true, true, 1.0, 1.0, GENETICS_FACTORY).mutate(0, GENETICS_FACTORY.createGenome(0, new GeneMap()));
	}
}
