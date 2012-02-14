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
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.or;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.Operator.OperationFailedException;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestStandardCrossoverOperator {
	private static final NEATGeneticsFactoryImpl	GENETICS_FACTORY0;
	private static final NEATGeneticsFactoryImpl	GENETICS_FACTORY1;
	private static final NEATGeneticsFactoryImpl	GENETICS_FACTORY2;
	private static final StandardCrossoverOperator	STANDARD_CROSSOVER_OPERATOR;
	private static final Object[][]					PARENTS;
	static {
		NEATEvolutionFieldMap evolutionFieldMap = NEATEvolutionConfigurationImpl//
				.builder(null)//
				.setInputNodeCount(5)//
				.setOutputNodeCount(5)//
				.setBiasNodeCount(0)//
				.newInstance().newFieldMap();
		GENETICS_FACTORY0 = new NEATGeneticsFactoryImpl(evolutionFieldMap);
		STANDARD_CROSSOVER_OPERATOR = new StandardCrossoverOperator(GENETICS_FACTORY0);
		evolutionFieldMap = NEATEvolutionConfigurationImpl//
				.builder(null)//
				.setInputNodeCount(5)//
				.setOutputNodeCount(5)//
				.setBiasNodeCount(0)//
				.setInitialConnectionProbability(0.5)//
				.newInstance().newFieldMap();
		GENETICS_FACTORY1 = new NEATGeneticsFactoryImpl(evolutionFieldMap);
		GENETICS_FACTORY2 = new NEATGeneticsFactoryImpl(evolutionFieldMap);
		PARENTS = TestUtils.unionData(//
				TestUtils.createParameters(//
						GENETICS_FACTORY0.createGenome(0),//
						GENETICS_FACTORY0.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY0.createGenome(0),//
						GENETICS_FACTORY0.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY0.createGenome(0),//
						GENETICS_FACTORY1.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY0.createGenome(0),//
						GENETICS_FACTORY1.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY0.createGenome(0),//
						GENETICS_FACTORY2.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY0.createGenome(0),//
						GENETICS_FACTORY2.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY1.createGenome(0),//
						GENETICS_FACTORY0.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY1.createGenome(0),//
						GENETICS_FACTORY0.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY2.createGenome(0),//
						GENETICS_FACTORY0.createGenome(1)),//
				TestUtils.createParameters(//
						GENETICS_FACTORY2.createGenome(0),//
						GENETICS_FACTORY0.createGenome(1)));
	}

	public void constructorSucceeds() {
		assertThat("",//
				STANDARD_CROSSOVER_OPERATOR, isNotNull());
	}

	@Test(dataProvider = "parents")
	public void crossoverReturnsExpectedObject(NEATGenome<GeneMap> genome0, NEATGenome<GeneMap> genome1) throws OperationFailedException {
		genome1.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		NEATGenome<GeneMap> child = STANDARD_CROSSOVER_OPERATOR.crossover(2, genome0, genome1);
		assertThat("",//
				child.getData().getNodeCount(), isEqualTo(genome1.getData().getNodeCount()));
		assertThat("",//
				child.getData().getLinkCount(), isEqualTo(genome1.getData().getLinkCount()));
		for (NodeGene node : child.getData().getNodes()) {
			assertThat("",//
					node,//
					or(//
					isEqualTo(genome0.getData().get(node.getId())),//
							isEqualTo(genome1.getData().get(node.getId()))));
			assertThat("",//
					child.getData().getOutgoingLinkIds(node.getId()),//
					hasSize(genome1.getData().getOutgoingLinkIds(node.getId()).size()));
			assertThat("",//
					child.getData().getIncomingLinkIds(node.getId()),//
					hasSize(genome1.getData().getIncomingLinkIds(node.getId()).size()));
		}
	}

	@DataProvider(name = "parents")
	public Object[][] parents() {
		return PARENTS;
	}
}
