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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThan;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.coll.ListBuilder;

/**
 * @author Luis Guimbarda
 */
@Test(groups = { "unit:xneat" })
public class UTestNeuralNetworkImpl {
	private static final Random	RANDOM	= new Random();

	private int					biasNeuronCount;
	private int					inputNeuronCount;
	private int					outputNeuronCount;
	private int					hiddenNeuronCount;
	private int					totalNeuronCount;
	private List<Double>		outputSignals;
	private List<Neuron>		neurons;
	private List<Connection>	connections;
	private NeuralNetworkImpl	network;

	@BeforeMethod
	public void setUp() {
		biasNeuronCount = RANDOM.nextInt(5) + 1;
		inputNeuronCount = RANDOM.nextInt(5) + 1;
		outputNeuronCount = RANDOM.nextInt(5) + 1;
		hiddenNeuronCount = RANDOM.nextInt(5) + 1;

		totalNeuronCount = biasNeuronCount + inputNeuronCount + outputNeuronCount + hiddenNeuronCount;

		neurons = new ArrayList<Neuron>();
		for (int i = 0; i < biasNeuronCount; i++) {
			neurons.add(new Neuron(1, NodeType.HIDDEN, 0.0, 1.0));
		}
		for (int i = biasNeuronCount; i < biasNeuronCount + inputNeuronCount; i++) {
			neurons.add(new Neuron(1, NodeType.HIDDEN, 1.0, Double.MIN_VALUE));
		}
		outputSignals = new ArrayList<Double>();
		for (//
		int i = biasNeuronCount + inputNeuronCount; //
		i < biasNeuronCount + inputNeuronCount + outputNeuronCount; //
		i++) {
			Neuron neuron = new Neuron(1, NodeType.HIDDEN, 1.0, Double.MIN_VALUE);
			outputSignals.add(neuron.getSignal());
			neurons.add(neuron);
		}
		for (//
		int i = biasNeuronCount + inputNeuronCount + outputNeuronCount; //
		i < biasNeuronCount + inputNeuronCount + outputNeuronCount + hiddenNeuronCount; //
		i++) {
			neurons.add(new Neuron(1, NodeType.HIDDEN, 1.0, Double.MIN_VALUE));
		}

		connections = new ArrayList<Connection>();
		for (int i = RANDOM.nextInt(5); i > 0; i--) {
			int src = RANDOM.nextInt(totalNeuronCount - biasNeuronCount) + biasNeuronCount;
			int tgt = RANDOM.nextInt(totalNeuronCount - biasNeuronCount) + biasNeuronCount;
			double weight = RANDOM.nextDouble() + Double.MIN_VALUE;
			connections.add(new Connection(src, tgt, weight));
		}

		network = new NeuralNetworkImpl(null, biasNeuronCount, inputNeuronCount, outputNeuronCount, hiddenNeuronCount, neurons, connections);
	}

	public void constructorSucceeds() {
		assertThat("",//
				network, isNotNull());
	}

	public void clearSignalsBehavesAsExpected() {
		for (Neuron neuron : network.getNeuronList()) {
			assertThat("",//
					neuron.getSignal(), isGreaterThan(0.0));
		}
		network.clearSignals();
		for (Neuron neuron : network.getNeuronList().subList(biasNeuronCount, totalNeuronCount)) {
			assertThat("",//
					neuron.getSignal(), isEqualTo(0.0));
		}
	}

	public void getConnectionListReturnsExpectedList() {
		assertThat("",//
				network.getConnectionList(), isNotNull(), hasSize(connections.size()));
		for (Connection connection : connections) {
			assertThat("",//
					network.getConnectionList(), containsElement(connection));
		}
	}

	public void getNeuronListReturnsExpectedList() {
		assertThat("",//
				network.getNeuronList(), isNotNull(), hasSize(neurons.size()));
		for (Neuron neuron : neurons) {
			assertThat("",//
					network.getNeuronList(), containsElement(neuron));
		}
	}

	public void getOutputSignalsReturnsExpectedArray() {
		double[] outputs = network.getOutputSignals();
		assertThat("",//
				outputs.length, isEqualTo(outputSignals.size()));
		for (int i = 0; i < outputs.length; i++) {
			assertThat("",//
					outputs[i], isEqualTo(outputSignals.get(i)));
		}
	}

	public void setInputSignalsBehavesAsExpected() {
		network.clearSignals();
		double[] inputs = new double[inputNeuronCount];
		Arrays.fill(inputs, 1.0);
		network.setInputSignals(inputs);

		List<Neuron> inputNeurons = network.getNeuronList().subList(biasNeuronCount, biasNeuronCount + inputNeuronCount);
		List<Neuron> otherNeurons = new ArrayList<Neuron>(network.getNeuronList());
		// inputs should be 1.0
		otherNeurons.removeAll(inputNeurons);
		// bias neurons don't get cleared
		otherNeurons.removeAll(network.getNeuronList().subList(0, biasNeuronCount));

		for (Neuron neuron : inputNeurons) {
			assertThat("",//
					neuron.getSignal(), isEqualTo(1.0));
		}
		for (Neuron neuron : otherNeurons) {
			assertThat("",//
					neuron.getSignal(), isEqualTo(0.0));
		}
	}

	public void singleStepAndRelaxNetworkBehavesAsExpected() {
		Neuron bias = new Neuron(1, NodeType.HIDDEN, 1.0, 1.0);
		Neuron input = new Neuron(1, NodeType.HIDDEN, 1.0);
		Neuron hidden = new Neuron(1, NodeType.HIDDEN, 1.0);
		Neuron output = new Neuron(1, NodeType.HIDDEN, 1.0);
		List<Neuron> neurons = new ListBuilder<Neuron>()//
				.add(bias, input, output, hidden)//
				.newImmutableInstance();
		Connection biasToHidden = new Connection(0, 3, 1.0);
		Connection inputToHidden = new Connection(1, 3, 1.0);
		Connection hiddenToOutput = new Connection(3, 2, 1.0);
		List<Connection> connections = new ListBuilder<Connection>()//
				.add(biasToHidden, inputToHidden, hiddenToOutput)//
				.newImmutableInstance();
		NeuralNetworkImpl network = new NeuralNetworkImpl(null, 1, 1, 1, 1, neurons, connections);
		network.setInputSignals(new double[] { 1.0 });

		// calc one step
		Neuron dummy = new Neuron(1, NodeType.HIDDEN, 1.0);
		double expected = 0.0;
		dummy.setInputSignal(bias.getSignal() + input.getSignal()); // ->
																	// hidden.inputSignal
		expected = dummy.calculateSignal();
		dummy.setInputSignal(hidden.getSignal());// -> output.inputSignal
		expected = Math.max(expected, dummy.calculateSignal());
		double outputSignal = dummy.getSignal();

		double delta = network.singleStep();
		assertThat("",//
				delta, isEqualTo(expected));
		assertThat("",//
				network.getOutputSignals()[0], isEqualTo(outputSignal));

		// calc to relax
		dummy.setInputSignal(bias.getSignal() + input.getSignal()); // ->
																	// hidden.inputSignal
		dummy.calculateSignal();
		dummy.setInputSignal(dummy.getSignal());// -> output.inputSignal
		dummy.calculateSignal();
		outputSignal = dummy.getSignal();

		boolean relaxed = network.relaxNetwork(4, 0);
		assertThat("",//
				relaxed, isTrue());
		assertThat("",//
				network.getOutputSignals()[0], isEqualTo(outputSignal));
	}

	public void toStringIsNotNull() {
		assertThat("",//
				network.toString(), isNotNull());
	}
}
