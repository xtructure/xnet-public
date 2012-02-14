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
import static com.xtructure.xutil.valid.ValidateUtils.containsElements;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNotSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNEATGeneticsFactoryImpl {
	private static final NEATEvolutionFieldMap		EVOLUTION_FIELD_MAP;
	private static final NEATGeneticsFactoryImpl	GENETICS_FACTORY;
	static {
		EVOLUTION_FIELD_MAP = NEATEvolutionConfigurationImpl.DEFAULT_CONFIGURATION.newFieldMap();
		GENETICS_FACTORY = new NEATGeneticsFactoryImpl(EVOLUTION_FIELD_MAP);
	}

	public void constructorSucceeds() {
		assertThat("",//
				GENETICS_FACTORY, isNotNull());
	}

	public void copyGenomeReturnsExpectedObject() {
		int num = RandomUtil.nextInteger();
		int num2 = RandomUtil.nextInteger();
		NEATGenome<GeneMap> genome = GENETICS_FACTORY.createGenome(num);
		NEATGenome<GeneMap> genomeDup = GENETICS_FACTORY.copyGenome(num2, genome);
		assertThat("",//
				genomeDup.getId(), isEqualTo(XId.newId("Genome", num2)));
		assertThat("",//
				genomeDup.getData(), isEqualTo(genome.getData()));
		assertThat("",//
				genomeDup.getAttributes().keySet(), hasSize(5), containsElements(Genome.FITNESS_ATTRIBUTE_ID, Genome.COMPLEXITY_ATTRIBUTE_ID, Genome.EVAL_COUNT_ATTRIBUTE_ID, Genome.DEATH_MARK_ATTRIBUTE_ID, Genome.AGE_ATTRIBUTE_ID));
		assertThat("",//
				genomeDup.getAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(0.0));
		assertThat("",//
				genomeDup.getAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID), isEqualTo((double) genomeDup.getData().size()));
	}

	public void copyLinkGeneReturnsExpectedObject() {
		XId srcId = XId.newId("NodeGene", RandomUtil.nextInteger());
		XId tgtId = XId.newId("NodeGene", RandomUtil.nextInteger());
		int num = RandomUtil.nextInteger();
		int num2 = RandomUtil.nextInteger();
		LinkGene link = GENETICS_FACTORY.createLinkGene(num, srcId, tgtId);
		LinkGene linkDup = GENETICS_FACTORY.copyLinkGene(num2, link);
		assertThat("",//
				linkDup.getId(), isEqualTo(XId.newId("LinkGene", num2)));
		assertThat("",//
				linkDup.getInnovation(), isEqualTo(Innovation.generate(srcId.getInstanceNum(), tgtId.getInstanceNum())));
		assertThat("",//
				linkDup.getSourceId(), isEqualTo(srcId));
		assertThat("",//
				linkDup.getTargetId(), isEqualTo(tgtId));
	}

	public void copyNodeGeneReturnsExpectedObject() {
		NodeType nodeType = RandomUtil.select(Arrays.asList(NodeType.values()));
		int num = RandomUtil.nextInteger();
		int num2 = RandomUtil.nextInteger();
		NodeGene node = GENETICS_FACTORY.createNodeGene(num, nodeType);
		NodeGene nodeDup = GENETICS_FACTORY.copyNodeGene(num2, node);
		assertThat("",//
				nodeDup.getId(), isEqualTo(XId.newId("NodeGene").createChild(num2)));
		assertThat("",//
				nodeDup.getInnovation(), isEqualTo(Innovation.generate(num2)));
		assertThat("",//
				nodeDup.getNodeType(), isEqualTo(nodeType));
	}

	public void createGenomeReturnsExpectedObject() {
		int num = RandomUtil.nextInteger();
		NEATGenome<GeneMap> genome = GENETICS_FACTORY.createGenome(num);
		assertThat("",//
				genome.getId(), isEqualTo(XId.newId("Genome", num)));
		int expectedNodeCount = EVOLUTION_FIELD_MAP.biasNodeCount() + EVOLUTION_FIELD_MAP.inputNodeCount() + EVOLUTION_FIELD_MAP.outputNodeCount();
		int expectedLinkCount = (EVOLUTION_FIELD_MAP.biasNodeCount() + EVOLUTION_FIELD_MAP.inputNodeCount()) * EVOLUTION_FIELD_MAP.outputNodeCount();
		assertThat("",//
				genome.getData().getNodeCount(), isEqualTo(expectedNodeCount));
		assertThat("",//
				genome.getData().getLinkCount(), isEqualTo(expectedLinkCount));
		assertThat("",//
				genome.getAttributes().keySet(), hasSize(5), containsElements(Genome.AGE_ATTRIBUTE_ID, Genome.FITNESS_ATTRIBUTE_ID, Genome.COMPLEXITY_ATTRIBUTE_ID, Genome.EVAL_COUNT_ATTRIBUTE_ID, Genome.DEATH_MARK_ATTRIBUTE_ID));
		assertThat("",//
				genome.getAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(0.0));
		assertThat("",//
				genome.getAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID), isEqualTo((double) genome.getData().size()));
		GeneMap data = new GeneMap();
		genome = GENETICS_FACTORY.createGenome(num, data);
		assertThat("",//
				genome.getId(), isEqualTo(XId.newId("Genome", num)));
		assertThat("",//
				genome.getData(), isEqualTo(data));
		assertThat("",//
				genome.getAttributes().keySet(), hasSize(5), containsElements(Genome.AGE_ATTRIBUTE_ID, Genome.FITNESS_ATTRIBUTE_ID, Genome.COMPLEXITY_ATTRIBUTE_ID, Genome.EVAL_COUNT_ATTRIBUTE_ID, Genome.DEATH_MARK_ATTRIBUTE_ID));
		assertThat("",//
				genome.getAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(0.0));
		assertThat("",//
				genome.getAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID), isEqualTo((double) genome.getData().size()));
	}

	public void createLinkReturnsExpectedObject() {
		XId srcId = XId.newId("NodeGene", RandomUtil.nextInteger());
		XId tgtId = XId.newId("NodeGene", RandomUtil.nextInteger());
		int num = RandomUtil.nextInteger();
		LinkGene link = GENETICS_FACTORY.createLinkGene(num, srcId, tgtId);
		assertThat("",//
				link.getId(), isEqualTo(XId.newId("LinkGene", num)));
		assertThat("",//
				link.getInnovation(), isEqualTo(Innovation.generate(srcId.getInstanceNum(), tgtId.getInstanceNum())));
		assertThat("",//
				link.getSourceId(), isEqualTo(srcId));
		assertThat("",//
				link.getTargetId(), isEqualTo(tgtId));
	}

	public void createNodeReturnsExpectedObject() {
		NodeType nodeType = RandomUtil.select(Arrays.asList(NodeType.values()));
		int num = RandomUtil.nextInteger();
		NodeGene node = GENETICS_FACTORY.createNodeGene(num, nodeType);
		assertThat("",//
				node.getId(), isEqualTo(XId.newId("NodeGene").createChild(num)));
		assertThat("",//
				node.getInnovation(), isEqualTo(Innovation.generate(num)));
		assertThat("",//
				node.getNodeType(), isEqualTo(nodeType));
	}

	public void getEvolutionFieldMapReturnsExpectedObject() {
		assertThat("",//
				GENETICS_FACTORY.getEvolutionFieldMap(), isEqualTo(EVOLUTION_FIELD_MAP));
	}

	public void copyDataReturnsExpectedObject() {
		GeneMap map = GENETICS_FACTORY.createData();
		GeneMap dup = GENETICS_FACTORY.copyData(map);
		assertThat("",//
				dup.containsAll(map), isTrue());
		assertThat("",//
				map.containsAll(dup), isTrue());
		assertThat("",//
				map, isNotSameAs(dup));
	}

	public void createPopulationReturnsExpectedPopulation() {
		NEATPopulationImpl pop = GENETICS_FACTORY.createPopulation(0);
		assertThat("",//
				pop, hasSize(EVOLUTION_FIELD_MAP.populationSize()));
	}
}
