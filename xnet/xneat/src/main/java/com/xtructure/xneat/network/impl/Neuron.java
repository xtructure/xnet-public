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

import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * {@link Neuron} is a class for vertex elements of a {@link NeuralNetwork}
 * 
 * @author Luis Guimbarda
 * 
 */
public class Neuron {
	/** */
	public static final XmlFormat<Neuron>	XML_FORMAT	= new NeuronXmlFormat();
	/** */
	public static final XmlBinding			XML_BINDING	= new XmlBinding(Neuron.class);
	/** {@link XLogger} for {@link Neuron} */
	private static final XLogger			LOGGER		= XLogger.getInstance(Neuron.class);
	/** the id number of this {@link Neuron} */
	private final int						id;
	/** the node type of this {@link Neuron} */
	private final NodeType					nodeType;
	/** the activation slope of this {@link Neuron} */
	private double							activationSlope;
	/** the signal at the output of this {@link Neuron} */
	private double							signal;
	/** the signal at the input of this {@link Neuron} */
	private double							inputSignal;

	/**
	 * Creates a new {@link Neuron}.
	 * 
	 * @param id
	 *            the index of this {@link Neuron}
	 * @param type
	 *            the {@link NodeType} of this {@link Neuron}
	 * @param activationSlope
	 *            the value of the activation slope for this {@link Neuron}
	 */
	public Neuron(int id, NodeType type, double activationSlope) {
		this(id, type, activationSlope, 0.0);
	}

	/**
	 * Creates a new {@link Neuron}.
	 * 
	 * @param id
	 *            the index of this {@link Neuron}
	 * @param type
	 *            the {@link NodeType} of this {@link Neuron}
	 * @param activationSlope
	 *            the value of the activation slope for this {@link Neuron}
	 * @param signal
	 *            the initual value of the signal in this {@link Neuron}
	 */
	public Neuron(int id, NodeType type, double activationSlope, double signal) {
		this.id = id;
		this.nodeType = type;
		this.activationSlope = activationSlope;
		this.setSignal(signal);
		this.setInputSignal(0.0);
	}

	/**
	 * Gets the index of this {@link Neuron}
	 * 
	 * @return the index of this {@link Neuron}
	 */
	public int getId() {
		LOGGER.trace("begin %s.getId()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", id);
		LOGGER.trace("end %s.getId()", getClass().getSimpleName());
		return id;
	}

	/**
	 * Gets the {@link NodeType} of this {@link Neuron}
	 * 
	 * @return the {@link NodeType} of this {@link Neuron}
	 */
	public NodeType getNodeType() {
		LOGGER.trace("begin %s.getNodeType()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", nodeType);
		LOGGER.trace("end %s.getNodeType()", getClass().getSimpleName());
		return nodeType;
	}

	/**
	 * Updates this {@link Neuron} by passing through its input signal. The new
	 * output signal is becomes:<br>
	 * 1.0 / (1.0 + Math.exp(-activationSlope * inputSignal));
	 * 
	 * @return the difference between this {@link Neuron}'s new output signal
	 *         and its old signal
	 */
	public double calculateSignal() {
		LOGGER.trace("begin %s.calculateSignal()", getClass().getSimpleName());
		double oldSignal = getSignal();
		setSignal(1.0 / (1.0 + Math.exp(-activationSlope * getInputSignal())));
		setInputSignal(0.0);
		double rVal = getSignal() - oldSignal;
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.calculateSignal()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Gets the activation slope of this {@link Neuron}.
	 * 
	 * @return the activation slope of this {@link Neuron}.
	 */
	public double getActivationSlope() {
		LOGGER.trace("begin %s.getActivationSlope()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", activationSlope);
		LOGGER.trace("end %s.getActivationSlope()", getClass().getSimpleName());
		return activationSlope;
	}

	/**
	 * Sets the signal of this {@link Neuron} to the given value
	 * 
	 * @param signal
	 *            the value to set
	 */
	public void setSignal(double signal) {
		LOGGER.trace("begin %s.setActivationSlope(%f)", getClass().getSimpleName(), signal);
		this.signal = signal;
		LOGGER.trace("end %s.setActivationSlope()", getClass().getSimpleName());
	}

	/**
	 * Gets the signal of this {@link Neuron}
	 * 
	 * @return the signal of the {@link Neuron}
	 */
	public double getSignal() {
		LOGGER.trace("begin %s.getSignal()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", signal);
		LOGGER.trace("end %s.getSignal()", getClass().getSimpleName());
		return signal;
	}

	/**
	 * Sets the input signal of this {@link Neuron} to the given value
	 * 
	 * @param inputSignal
	 *            the value to set
	 */
	public void setInputSignal(double inputSignal) {
		LOGGER.trace("begin %s.setInputSignal(%f)", getClass().getSimpleName(), inputSignal);
		this.inputSignal = inputSignal;
		LOGGER.trace("end %s.setInputSignal()", getClass().getSimpleName());
	}

	/**
	 * Gets the input signal of this {@link Neuron}
	 * 
	 * @return the input signal of this {@link Neuron}
	 */
	public double getInputSignal() {
		LOGGER.trace("begin %s.getInputSignal()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", inputSignal);
		LOGGER.trace("end %s.getInputSignal()", getClass().getSimpleName());
		return inputSignal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)//
				.append(getActivationSlope())//
				.toString();
	}

	/** the xml format for {@link Neuron} */
	private static final class NeuronXmlFormat extends XmlFormat<Neuron> {
		private static final Attribute<Integer>	ID_ATTRIBUTE			= XmlUnit.newAttribute("id", Integer.class);
		private static final Attribute<Double>	ACTIVATION_ATTRIBUTE	= XmlUnit.newAttribute("activation", Double.class);
		private static final Attribute<String>	NODE_TYPE_ATTRIBUTE		= XmlUnit.newAttribute("type", String.class);

		private NeuronXmlFormat() {
			super(Neuron.class);
			addRecognized(ID_ATTRIBUTE);
			addRecognized(ACTIVATION_ATTRIBUTE);
			addRecognized(NODE_TYPE_ATTRIBUTE);
		}

		@Override
		protected Neuron newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			Integer id = readAttributes.getValue(ID_ATTRIBUTE);
			NodeType type = NodeType.parse(readAttributes.getValue(NODE_TYPE_ATTRIBUTE));
			Double activationSlope = readAttributes.getValue(ACTIVATION_ATTRIBUTE);
			return new Neuron(id, type, activationSlope);
		}

		@Override
		protected void writeAttributes(Neuron obj, OutputElement xml) throws XMLStreamException {
			ID_ATTRIBUTE.write(xml, obj.getId());
			ACTIVATION_ATTRIBUTE.write(xml, obj.activationSlope);
			NODE_TYPE_ATTRIBUTE.write(xml, obj.getNodeType().name());
		}

		@Override
		protected void writeElements(Neuron obj, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
