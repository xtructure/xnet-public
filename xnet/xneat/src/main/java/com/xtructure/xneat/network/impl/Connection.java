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

import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * {@link Connection} is the class of edge elements in {@link NeuralNetwork}
 * 
 * @author Luis Guimbarda
 * 
 */
public class Connection {
	/** */
	public static final XmlFormat<Connection>	XML_FORMAT	= new ConnectionXmlFormat();
	/** */
	public static final XmlBinding				XML_BINDING	= new XmlBinding(Connection.class);
	/** {@link XLogger} for {@link Connection} */
	private static final XLogger				LOGGER		= XLogger.getInstance(Connection.class);
	/** index of the source {@link Neuron} of this {@link Connection} */
	private int									sourceNeuronIndex;
	/** index of the target {@link Neuron} of this {@link Connection} */
	private int									targetNeuronIndex;
	/** the weight of this {@link Connection} */
	private double								weight;
	/** energy at the target end of this {@link Connection} */
	private double								signal;

	/**
	 * Creates a new {@link Connection}.
	 * 
	 * @param sourceNeuronIndex
	 *            the index of the source {@link Neuron} for this
	 *            {@link Connection}
	 * @param targetNeuronIndex
	 *            the index of the target {@link Neuron} for this
	 *            {@link Connection}
	 * @param weight
	 *            the value of the weight of this {@link Connection}
	 */
	public Connection(int sourceNeuronIndex, int targetNeuronIndex, double weight) {
		this.sourceNeuronIndex = sourceNeuronIndex;
		this.targetNeuronIndex = targetNeuronIndex;
		this.weight = weight;
		this.setSignal(0.0);
	}

	/**
	 * Gets the index of the source {@link Neuron} of this {@link Connection}.
	 * 
	 * @return the index of the source {@link Neuron} of this {@link Connection}
	 *         .
	 */
	public int getSourceNeuronIndex() {
		LOGGER.trace("begin %s.getSourceNeuronIndex()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", sourceNeuronIndex);
		LOGGER.trace("end %s.getSourceNeuronIndex()", getClass().getSimpleName());
		return sourceNeuronIndex;
	}

	/**
	 * Gets the index of the target {@link Neuron} of this {@link Connection}.
	 * 
	 * @return the index of the target {@link Neuron} of this {@link Connection}
	 *         .
	 */
	public int getTargetNeuronIndex() {
		LOGGER.trace("begin %s.getTargetNeuronIndex()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", targetNeuronIndex);
		LOGGER.trace("end %s.getTargetNeuronIndex()", getClass().getSimpleName());
		return targetNeuronIndex;
	}

	/**
	 * Gets the weight of this {@link Connection}.
	 * 
	 * @return the weight of this {@link Connection}.
	 */
	public double getWeight() {
		LOGGER.trace("begin %s.getWeight()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", weight);
		LOGGER.trace("end %s.getWeight()", getClass().getSimpleName());
		return weight;
	}

	/**
	 * Gets the signal of this {@link Connection}.
	 * 
	 * @return the signal of this {@link Connection}.
	 */
	public double getSignal() {
		LOGGER.trace("begin %s.getSignal()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", signal);
		LOGGER.trace("end %s.getSignal()", getClass().getSimpleName());
		return signal;
	}

	/**
	 * Sets the signal of this {@link Connection} to the given value.
	 * 
	 * @param signal
	 *            the value to set
	 */
	public void setSignal(double signal) {
		LOGGER.trace("begin %s.setSignal(%f)", getClass().getSimpleName(), signal);
		this.signal = signal;
		LOGGER.trace("end %s.setSignal()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)//
				.append("sourceIndex", sourceNeuronIndex)//
				.append("targetIndex", targetNeuronIndex)//
				.append("weight", weight)//
				.toString();
	}

	/** xml format for {@link Connection} */
	private static final class ConnectionXmlFormat extends XmlFormat<Connection> {
		private static final Attribute<Integer>	SOURCE_ATTRIBUTE	= XmlUnit.newAttribute("src", Integer.class);
		private static final Attribute<Integer>	TARGET_ATTRIBUTE	= XmlUnit.newAttribute("tgt", Integer.class);
		private static final Attribute<Double>	WEIGHT_ATTRIBUTE	= XmlUnit.newAttribute("weight", Double.class);

		private ConnectionXmlFormat() {
			super(Connection.class);
			addRecognized(SOURCE_ATTRIBUTE);
			addRecognized(TARGET_ATTRIBUTE);
			addRecognized(WEIGHT_ATTRIBUTE);
		}

		@Override
		protected Connection newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			Integer sourceNeuronIndex = readAttributes.getValue(SOURCE_ATTRIBUTE);
			Integer targetNeuronIndex = readAttributes.getValue(TARGET_ATTRIBUTE);
			Double weight = readAttributes.getValue(WEIGHT_ATTRIBUTE);
			return new Connection(sourceNeuronIndex, targetNeuronIndex, weight);
		}

		@Override
		protected void writeAttributes(Connection obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			SOURCE_ATTRIBUTE.write(xml, obj.sourceNeuronIndex);
			TARGET_ATTRIBUTE.write(xml, obj.targetNeuronIndex);
			WEIGHT_ATTRIBUTE.write(xml, obj.weight);
		}

		@Override
		protected void writeElements(Connection obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
