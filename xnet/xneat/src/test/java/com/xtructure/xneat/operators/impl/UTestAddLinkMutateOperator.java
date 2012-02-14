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
import static com.xtructure.xutil.valid.ValidateUtils.containsElements;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.Operator.OperationFailedException;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.coll.SetBuilder;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestAddLinkMutateOperator {
	private static final NEATGeneticsFactoryImpl	GENETICS_FACTORY;
	private static final AddLinkMutateOperator		ADD_LINK_MUTATE_OPERATOR;
	static {
		NEATEvolutionFieldMap evolutionFieldMap = NEATEvolutionConfigurationImpl//
				.builder(null)//
				.setInputNodeCount(2)//
				.setOutputNodeCount(1)//
				.setBiasNodeCount(1)//
				.newInstance().newFieldMap();
		GENETICS_FACTORY = new NEATGeneticsFactoryImpl(evolutionFieldMap);
		ADD_LINK_MUTATE_OPERATOR = new AddLinkMutateOperator(GENETICS_FACTORY);
	}

	public void constructorSucceeds() {
		assertThat("",//
				ADD_LINK_MUTATE_OPERATOR, isNotNull());
	}

	public void mutateReturnsExpectedObject() throws OperationFailedException {
		NEATGenome<GeneMap> genome = GENETICS_FACTORY.createGenome(0);
		NEATGenome<GeneMap> child = ADD_LINK_MUTATE_OPERATOR.mutate(1, genome);
		assertThat("",//
				child, isNotNull());
		assertThat("",//
				child.getData().getNodes(), isEqualTo(genome.getData().getNodes()));
		Set<LinkGene> links = new HashSet<LinkGene>(child.getData().getLinks());
		links.removeAll(genome.getData().getLinks());
		assertThat("",//
				child.getData().getLinkCount(), isEqualTo(genome.getData().getLinkCount() + 1));
		assertThat("",//
				links, hasSize(1));
		assertThat("",//
				child.getId(), isEqualTo(genome.getBaseId().createChild(1)));
		assertThat("",//
				child.getEvaluationCount(), isEqualTo(0l));
		assertThat("",//
				child.getAttributes().keySet(), hasSize(5), containsElements(Genome.AGE_ATTRIBUTE_ID, Genome.FITNESS_ATTRIBUTE_ID, Genome.COMPLEXITY_ATTRIBUTE_ID, Genome.EVAL_COUNT_ATTRIBUTE_ID, Genome.DEATH_MARK_ATTRIBUTE_ID));
		assertThat("",//
				child.getAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(0.0));
		assertThat("",//
				child.getAttribute(Genome.COMPLEXITY_ATTRIBUTE_ID), isEqualTo((double) child.getData().size()));
	}

	@Test(expectedExceptions = { OperationFailedException.class })
	public void mutateOnGenomeWithNoInputNodeGenesThrowsException() throws OperationFailedException {
		ADD_LINK_MUTATE_OPERATOR.mutate(0, GENETICS_FACTORY.createGenome(1, new GeneMap()));
	}

	@Test(expectedExceptions = { OperationFailedException.class })
	public void mutateOnFullyConnectedGenomeThrowsException() throws OperationFailedException {
		NodeGene node0 = GENETICS_FACTORY.createNodeGene(0, NodeType.INPUT);
		NodeGene node1 = GENETICS_FACTORY.createNodeGene(1, NodeType.OUTPUT);
		LinkGene link0 = GENETICS_FACTORY.createLinkGene(0, node0.getId(), node1.getId());
		LinkGene link1 = GENETICS_FACTORY.createLinkGene(1, node1.getId(), node1.getId());
		ADD_LINK_MUTATE_OPERATOR.mutate(0, GENETICS_FACTORY.createGenome(1, new GeneMap(//
				new SetBuilder<NodeGene>()//
						.add(node0, node1)//
						.newImmutableInstance(),//
				new SetBuilder<LinkGene>()//
						.add(link0, link1)//
						.newImmutableInstance())));
	}
}
