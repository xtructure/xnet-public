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

import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.Collections;
import java.util.List;

import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * {@link NeuralNetworkImpl} implements the {@link NeuralNetwork} interface with
 * a concurrent neural network. This implementation is based on
 * FloatFastConcurrentNetwork, found in SharpNEAT 1.04 (April 2nd 2006), Colin
 * Green.
 * <P>
 * Running the neural network assumes that no neuron is connected to an input
 * neuron (as a source).
 * 
 * @author Luis Guimbarda
 */
public class NeuralNetworkImpl implements NeuralNetwork {
	/** */
	public static final XmlFormat<NeuralNetworkImpl>	XML_FORMAT	= new NetworkXmlFormat();
	/** */
	public static final XmlBinding						XML_BINDING	= XmlBinding.builder()//
																			.add(NeuralNetworkImpl.class)//
																			.add(Neuron.XML_BINDING)//
																			.add(Connection.XML_BINDING)//
																			.newInstance();
	/** {@link XLogger} for {@link NeuralNetworkImpl} */
	private static final XLogger						LOGGER		= XLogger.getInstance(NeuralNetworkImpl.class);
	/** number of bias neurons */
	private final int									biasNeuronCount;
	/** number of input neurons */
	private final int									inputNeuronCount;
	/** number of output neurons */
	private final int									outputNeuronCount;
	/** number of hidden neurons */
	private final int									hiddenNeuronCount;
	// private final int biasStart;
	/** index after the last bias neuron */
	private final int									biasEnd;
	/** index of the first input neuron */
	private final int									inputStart;
	/** index after the last input neuron */
	private final int									inputEnd;
	/** index of the first output neuron */
	private final int									outputStart;
	/** index after the last output neuron */
	private final int									outputEnd;
	// private final int hiddenStart;
	/** index after the last hidden neuron */
	private final int									hiddenEnd;
	/**
	 * this network's neurons are arranged as follows: first, bias neurons;
	 * second, input neurons; third, output neurons; and finally the hidden
	 * neurons.
	 */
	private final List<Neuron>							neurons;
	/** this network's connections */
	private final List<Connection>						connections;
	/** the id of the genome on whom this network is based */
	private final XId									genomeId;

	/**
	 * Creates a new {@link NeuralNetworkImpl}.
	 * 
	 * @param genomeId
	 *            the XId of the {@link Genome} that encoded the new
	 *            {@link NeuralNetwork}
	 * @param biasNeuronCount
	 *            the number of {@link NodeType#BIAS} {@link Neuron}s
	 * @param inputNeuronCount
	 *            the number of {@link NodeType#INPUT} {@link Neuron}s
	 * @param outputNeuronCount
	 *            the number of {@link NodeType#OUTPUT} {@link Neuron}s
	 * @param hiddenNeuronCount
	 *            the number of {@link NodeType#HIDDEN} {@link Neuron}s
	 * @param neurons
	 *            the list of neurons in the new {@link NeuralNetwork}
	 * @param connections
	 *            the list of {@link Connection} in the new
	 *            {@link NeuralNetwork}
	 */
	public NeuralNetworkImpl(//
			XId genomeId,//
			int biasNeuronCount,//
			int inputNeuronCount,//
			int outputNeuronCount,//
			int hiddenNeuronCount,//
			List<Neuron> neurons,//
			List<Connection> connections) {
		this.genomeId = genomeId;
		this.biasNeuronCount = biasNeuronCount;
		this.inputNeuronCount = inputNeuronCount;
		this.outputNeuronCount = outputNeuronCount;
		this.hiddenNeuronCount = hiddenNeuronCount;
		// this.biasStart = 0;
		this.biasEnd = this.biasNeuronCount;
		this.inputStart = this.biasEnd;
		this.inputEnd = this.inputStart + this.inputNeuronCount;
		this.outputStart = this.inputEnd;
		this.outputEnd = this.outputStart + this.outputNeuronCount;
		// this.hiddenStart = this.outputEnd;
		this.hiddenEnd = this.outputEnd + this.hiddenNeuronCount;
		this.neurons = neurons;
		this.connections = connections;
		// set signal of bias neurons to 1.0
		for (int i = 0; i < this.biasEnd; i++) {
			this.neurons.get(i).setSignal(1.0);
		}
	}

	/**
	 * Gets a list of {@link Connection}s in this {@link NeuralNetwork}.
	 * 
	 * @return a list of {@link Connection}s in this {@link NeuralNetwork}.
	 */
	public List<Connection> getConnectionList() {
		LOGGER.trace("begin %s.getConnectionList()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", connections);
		LOGGER.trace("end %s.getConnectionList()", getClass().getSimpleName());
		return Collections.unmodifiableList(connections);
	}

	/**
	 * Gets a list of {@link Neuron}s in this {@link NeuralNetwork}.
	 * 
	 * @return a list of {@link Neuron}s in this {@link NeuralNetwork}.
	 */
	public List<Neuron> getNeuronList() {
		LOGGER.trace("begin %s.getNeuronList()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", neurons);
		LOGGER.trace("end %s.getNeuronList()", getClass().getSimpleName());
		return Collections.unmodifiableList(neurons);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.network.NeuralNetwork#clearSignals()
	 */
	@Override
	public void clearSignals() {
		LOGGER.trace("begin %s.clearSignals()", getClass().getSimpleName());
		for (Neuron neuron : neurons.subList(inputStart, hiddenEnd)) {
			neuron.setSignal(0.0);
		}
		LOGGER.trace("end %s.clearSignals()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.network.NeuralNetwork#getOutputSignals()
	 */
	@Override
	public double[] getOutputSignals() {
		LOGGER.trace("begin %s.getOutputSignals()", getClass().getSimpleName());
		double[] outputs = new double[outputNeuronCount];
		int index = 0;
		for (Neuron neuron : neurons.subList(outputStart, outputEnd)) {
			outputs[index++] = neuron.getSignal();
		}
		LOGGER.trace("will return: %s", outputs);
		LOGGER.trace("end %s.getOutputSignals()", getClass().getSimpleName());
		return outputs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.network.NeuralNetwork#relaxNetwork(int, double)
	 */
	@Override
	public boolean relaxNetwork(int maxSteps, double maxDelta) {
		LOGGER.trace("begin %s.relaxNetwork(%d, %f)", getClass().getSimpleName(), maxSteps, maxDelta);
		boolean isRelaxed = false;
		for (int i = 0; i < maxSteps && !isRelaxed; i++) {
			double signalDelta = singleStep();
			isRelaxed = signalDelta <= maxDelta;
		}
		LOGGER.trace("will return: %s", isRelaxed);
		LOGGER.trace("end %s.relaxNetwork()", getClass().getSimpleName());
		return isRelaxed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.network.NeuralNetwork#setInputSignals(double[])
	 */
	@Override
	public void setInputSignals(double[] inputSignals) {
		LOGGER.trace("begin %s.setInputSignals(%s)", getClass().getSimpleName(), inputSignals);
		validateArg("inputSignals is ", inputSignals, isNotNull());
		validateArg("inputSignals has length", inputSignals.length, isEqualTo(inputNeuronCount));
		for (int i = 0; i < inputSignals.length; i++) {
			neurons.get(i + inputStart).setSignal(inputSignals[i]);
		}
		LOGGER.trace("end %s.setInputSignals()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.network.NeuralNetwork#singleStep()
	 */
	@Override
	public double singleStep() {
		LOGGER.trace("begin %s.singleStep()", getClass().getSimpleName());
		double maxSignalDelta = -Double.MAX_VALUE;
		// pass neuron signals through connections
		for (Connection connection : connections) {
			int sourceIndex = connection.getSourceNeuronIndex();
			double weight = connection.getWeight();
			connection.setSignal(neurons.get(sourceIndex).getSignal() * weight);
		}
		// accumulate connection signals for their target neurons
		for (Connection connection : connections) {
			int targetIndex = connection.getTargetNeuronIndex();
			neurons.get(targetIndex).setInputSignal(neurons.get(targetIndex).getInputSignal() + connection.getSignal());
		}
		// pass accumulated input signals through neurons
		// NOTE: assumes recurrent links to inputs aren't allowed
		for (Neuron neuron : neurons.subList(outputStart, hiddenEnd)) {
			double signalDelta = neuron.calculateSignal();
			maxSignalDelta = Math.max(signalDelta, maxSignalDelta);
		}
		LOGGER.trace("will return: %s", maxSignalDelta);
		LOGGER.trace("end %s.singleStep()", getClass().getSimpleName());
		return maxSignalDelta;
	}

	/**
	 * Gets the number of bias neurons in this {@link NeuralNetwork}.
	 * 
	 * @return the number of bias neurons in this {@link NeuralNetwork}.
	 */
	public int getBiasNeuronCount() {
		LOGGER.trace("begin %s.getBiasNeuronCount()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", biasNeuronCount);
		LOGGER.trace("end %s.getBiasNeuronCount()", getClass().getSimpleName());
		return biasNeuronCount;
	}

	/**
	 * Gets the number of input neurons in this {@link NeuralNetwork}.
	 * 
	 * @return the number of input neurons in this {@link NeuralNetwork}.
	 */
	public int getInputNeuronCount() {
		LOGGER.trace("begin %s.getInputNeuronCount()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", inputNeuronCount);
		LOGGER.trace("end %s.getInputNeuronCount()", getClass().getSimpleName());
		return inputNeuronCount;
	}

	/**
	 * Gets the number of output neurons in this {@link NeuralNetwork}.
	 * 
	 * @return the number of output neurons in this {@link NeuralNetwork}.
	 */
	public int getOutputNeuronCount() {
		LOGGER.trace("begin %s.getOutputNeuronCount()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", outputNeuronCount);
		LOGGER.trace("end %s.getOutputNeuronCount()", getClass().getSimpleName());
		return outputNeuronCount;
	}

	/**
	 * Gets the number of hidden neurons in this {@link NeuralNetwork}.
	 * 
	 * @return the number of hidden neurons in this {@link NeuralNetwork}.
	 */
	public int getHiddenNeuronCount() {
		LOGGER.trace("begin %s.getHiddenNeuronCount()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", hiddenNeuronCount);
		LOGGER.trace("end %s.getHiddenNeuronCount()", getClass().getSimpleName());
		return hiddenNeuronCount;
	}

	/**
	 * Gets the id of the {@link Genome} which encoded this
	 * {@link NeuralNetwork}.
	 * 
	 * @return the id of the {@link Genome} which encoded this
	 *         {@link NeuralNetwork}.
	 */
	public XId getGenomeId() {
		LOGGER.trace("begin %s.getGenomeId()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", genomeId);
		LOGGER.trace("end %s.getGenomeId()", getClass().getSimpleName());
		return genomeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)//
				.append("genomeId", genomeId)//
				.append("neurons", neurons)//
				.append("connections", connections)//
				.toString();
	}

	/** xml format for {@link NeuralNetworkImpl} */
	private static final class NetworkXmlFormat extends XmlFormat<NeuralNetworkImpl> {
		private static final Attribute<XId>			GENOME_ID_ATTRIBUTE				= XmlUnit.newAttribute("genomeId", XId.class);
		private static final Attribute<Integer>		BIAS_NEURON_COUNT_ATTRIBUTE		= XmlUnit.newAttribute("biasNeuronCount", Integer.class);
		private static final Attribute<Integer>		INPUT_NEURON_COUNT_ATTRIBUTE	= XmlUnit.newAttribute("inputNeuronCount", Integer.class);
		private static final Attribute<Integer>		OUTPUT_NEURON_COUNT_ATTRIBUTE	= XmlUnit.newAttribute("outputNeuronCount", Integer.class);
		private static final Attribute<Integer>		HIDDEN_NEURON_COUNT_ATTRIBUTE	= XmlUnit.newAttribute("hiddenNeuronCount", Integer.class);
		private static final Element<Neuron>		NEURON_ELEMENT					= XmlUnit.newElement("neuron", Neuron.class);
		private static final Element<Connection>	CONNECTION_ELEMENT				= XmlUnit.newElement("connection", Connection.class);

		private NetworkXmlFormat() {
			super(NeuralNetworkImpl.class);
			addRecognized(GENOME_ID_ATTRIBUTE);
			addRecognized(BIAS_NEURON_COUNT_ATTRIBUTE);
			addRecognized(INPUT_NEURON_COUNT_ATTRIBUTE);
			addRecognized(OUTPUT_NEURON_COUNT_ATTRIBUTE);
			addRecognized(HIDDEN_NEURON_COUNT_ATTRIBUTE);
			addRecognized(NEURON_ELEMENT);
			addRecognized(CONNECTION_ELEMENT);
		}

		@Override
		protected NeuralNetworkImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId genomeId = readAttributes.getValue(GENOME_ID_ATTRIBUTE);
			int biasNeuronCount = readAttributes.getValue(BIAS_NEURON_COUNT_ATTRIBUTE);
			int inputNeuronCount = readAttributes.getValue(INPUT_NEURON_COUNT_ATTRIBUTE);
			int outputNeuronCount = readAttributes.getValue(OUTPUT_NEURON_COUNT_ATTRIBUTE);
			int hiddenNeuronCount = readAttributes.getValue(HIDDEN_NEURON_COUNT_ATTRIBUTE);
			List<Neuron> neurons = readElements.getValues(NEURON_ELEMENT);
			List<Connection> connections = readElements.getValues(CONNECTION_ELEMENT);
			return new NeuralNetworkImpl(genomeId, biasNeuronCount, inputNeuronCount, outputNeuronCount, hiddenNeuronCount, neurons, connections);
		}

		@Override
		protected void writeAttributes(NeuralNetworkImpl obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			GENOME_ID_ATTRIBUTE.write(xml, obj.genomeId);
			BIAS_NEURON_COUNT_ATTRIBUTE.write(xml, obj.biasNeuronCount);
			INPUT_NEURON_COUNT_ATTRIBUTE.write(xml, obj.inputNeuronCount);
			OUTPUT_NEURON_COUNT_ATTRIBUTE.write(xml, obj.outputNeuronCount);
			HIDDEN_NEURON_COUNT_ATTRIBUTE.write(xml, obj.hiddenNeuronCount);
		}

		@Override
		protected void writeElements(NeuralNetworkImpl obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			for (Neuron neuron : obj.neurons) {
				NEURON_ELEMENT.write(xml, neuron);
			}
			for (Connection connection : obj.connections) {
				CONNECTION_ELEMENT.write(xml, connection);
			}
		}
	}
}
