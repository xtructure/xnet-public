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
package com.xtructure.xneat.network.impl;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.genetics.impl.NEATGeneticsFactoryImpl;
import com.xtructure.xneat.genetics.impl.NEATGenomeDecoder;
import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 */
@Test(groups = { "xml:xneat" })
public final class UTestNeuralNetworkImpl_XML extends AbstractXmlFormatTest<NeuralNetworkImpl> {
	private static final Object[][]	INSTANCES;
	static {
		NEATEvolutionFieldMap evolutionFieldMap = NEATEvolutionConfigurationImpl//
				.builder(null)//
				.setPopulationSize(20)//
				.setInputNodeCount(3)//
				.setOutputNodeCount(3)//
				.setBiasNodeCount(1)//
				.setInitialConnectionProbability(0.5).newInstance().newFieldMap();
		LinkGeneConfiguration linkConfig = LinkGeneConfiguration.builder(null).setWeight(2.0).newInstance();
		NodeGeneConfiguration nodeConfig = NodeGeneConfiguration.builder(null).setActivation(1.0).newInstance();
		NEATGeneticsFactoryImpl geneticsFactory = new NEATGeneticsFactoryImpl(evolutionFieldMap, linkConfig, nodeConfig);
		List<NeuralNetwork> networks = new ArrayList<NeuralNetwork>();
		for (int i = 0; i < 5; i++) {
			networks.add(NEATGenomeDecoder.getInstance().decode(geneticsFactory.createGenome(i)));
		}
		INSTANCES = TestUtils.createData(networks.toArray());
	}

	public UTestNeuralNetworkImpl_XML() {
		super(NeuralNetworkImpl.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(NeuralNetworkImpl t) {
		StringBuilder sb = new StringBuilder()//
				.append(XML_HEADER)//
				.append(String.format("<%s genomeId=\"%s\" biasNeuronCount=\"%d\" inputNeuronCount=\"%d\"" + " outputNeuronCount=\"%d\" hiddenNeuronCount=\"%d\">\n",//
						NeuralNetworkImpl.class.getSimpleName(), t.getGenomeId(), t.getBiasNeuronCount(), t.getInputNeuronCount(),//
						t.getOutputNeuronCount(), t.getHiddenNeuronCount()));
		for (Neuron neuron : t.getNeuronList()) {
			Wrapper wrapper = new Wrapper(neuron);
			String line = XmlWriter.write(wrapper).split("\n")[2];
			sb.append(Wrapper.removeClassAttribute(Wrapper.replaceHook(line, "neuron")) + "\n");
		}
		for (Connection connection : t.getConnectionList()) {
			Wrapper wrapper = new Wrapper(connection);
			String line = XmlWriter.write(wrapper).split("\n")[2];
			sb.append(Wrapper.removeClassAttribute(Wrapper.replaceHook(line, "connection")) + "\n");
		}
		sb.append(String.format("</%s>", NeuralNetworkImpl.class.getSimpleName()));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
