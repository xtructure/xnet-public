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

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestAddNodeMutateOperator {
	private static final NEATGeneticsFactoryImpl	GENETICS_FACTORY;
	private static final AddNodeMutateOperator		ADD_NODE_MUTATE_OPERATOR;
	static {
		NEATEvolutionFieldMap evolutionFieldMap = NEATEvolutionConfigurationImpl//
				.builder(null)//
				.setInputNodeCount(1)//
				.setOutputNodeCount(1)//
				.setBiasNodeCount(0)//
				.newInstance().newFieldMap();
		GENETICS_FACTORY = new NEATGeneticsFactoryImpl(evolutionFieldMap);
		ADD_NODE_MUTATE_OPERATOR = new AddNodeMutateOperator(GENETICS_FACTORY);
	}

	public void constructorSucceeds() {
		assertThat("",//
				ADD_NODE_MUTATE_OPERATOR, isNotNull());
	}

	public void mutateReturnsExpectedObject() throws OperationFailedException {
		NEATGenome<GeneMap> genome = GENETICS_FACTORY.createGenome(0);
		NEATGenome<GeneMap> child = ADD_NODE_MUTATE_OPERATOR.mutate(1, genome);
		assertThat("",//
				child, isNotNull());
		Set<NodeGene> nodes = new HashSet<NodeGene>(child.getData().getNodes());
		nodes.removeAll(genome.getData().getNodes());
		assertThat("",//
				child.getData().getNodeCount(), isEqualTo(genome.getData().getNodeCount() + 1));
		assertThat("",//
				nodes, hasSize(1));
		Set<LinkGene> links = new HashSet<LinkGene>(child.getData().getLinks());
		links.removeAll(genome.getData().getLinks());
		assertThat("",//
				child.getData().getLinkCount(), isEqualTo(genome.getData().getLinkCount() + 1));
		assertThat("",//
				links, hasSize(2));
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
	public void mutateOnGenomeWithNoLinksThrowsException() throws OperationFailedException {
		ADD_NODE_MUTATE_OPERATOR.mutate(1, GENETICS_FACTORY.createGenome(0, new GeneMap()));
	}
}
