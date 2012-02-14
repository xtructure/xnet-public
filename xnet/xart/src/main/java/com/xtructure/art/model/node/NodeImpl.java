/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.model.node;

import java.util.LinkedList;
import java.util.List;

import javolution.xml.XMLBinding;
import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.config.AbstractRangeXParameter;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.FloatXParameter;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * An ART node.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * @version 0.9.6
 */
public final class NodeImpl extends AbstractXIdObject implements Node {
	/** The logger for nodes */
	private static final XLogger					LOGGER			= XLogger.getInstance(NodeImpl.class);
	/** The XML format for nodes */
	public static final XMLFormat<NodeImpl>			XML_FORMAT		= new NodeImplXmlFormat();
	/** The XML bindings for nodes */
	public static final XMLBinding					XML_BINDING		= new XmlBinding(//
																			NodeImpl.class,//
																			Energies.class);
	/** The base string for the default id. */
	private static final String						DEFAULT_BASE	= "default.art.node";
	/** The id of the default node. */
	public static final XId							DEFAULT_ID		= XId.newId(DEFAULT_BASE);
	/** This node's configuration. */
	private NodeConfiguration						_nodeConfiguration;
	/** This node's field map */
	private final FieldMap							_fieldMap;
	/** A queue of internal energies. */
	private final LinkedList<FloatXParameter.Field>	_energyQueue	= new LinkedList<FloatXParameter.Field>();
	/** The oscillation phase of this node. */
	private int										_oscillationPhase;

	/**
	 * Creates a new node.
	 * 
	 * @param id
	 *            this node's id
	 * @param nodeConfiguration
	 *            this node's configuration
	 */
	public NodeImpl(final XId id, final NodeConfiguration nodeConfiguration) {
		super(id);
		_nodeConfiguration = nodeConfiguration;
		_fieldMap = nodeConfiguration.newFieldMap();
		for (int i = -1; i <= getDelay(); i++) {
			_energyQueue.add(//
					new FloatXParameter.Field(//
							(FloatXParameter.Field) _fieldMap.getField(ENERGY_ID)));
		}
	}

	/**
	 * Returns the configuration this node.
	 * 
	 * @return the configuration of this node.
	 */
	public final NodeConfiguration getConfiguration() {
		LOGGER.trace("begin %s.getConfiguration()", getClass().getSimpleName());
		NodeConfiguration rVal = _nodeConfiguration;
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getConfiguration()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Energies getEnergies() {
		LOGGER.trace("begin %s.getEnergies()", getClass().getSimpleName());
		Energies rVal = new Energies( //
				_energyQueue.getLast().getValue(), //
				_energyQueue.get(1).getValue());
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getEnergies()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getEnergyDecay() {
		LOGGER.trace("begin %s.getEnergyDecay()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(ENERGY_DECAY_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getEnergyDecay()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getExcitatoryScale() {
		LOGGER.trace("begin %s.getExcitatoryScale()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(EXCITATORY_SCALE_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getExcitatoryScale()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getInhibitoryScale() {
		LOGGER.trace("begin %s.getInhibitoryScale()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(INHIBITORY_SCALE_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getInhibitoryScale()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getOscillationMinimum() {
		LOGGER.trace("begin %s.getOscillationMinimum()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(OSCILLATION_MINIMUM_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getOscillationMinimum()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getOscillationMaximum() {
		LOGGER.trace("begin %s.getOscillationMaximum()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(OSCILLATION_MAXIMUM_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getOscillationMaximum()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Integer getOscillationOffset() {
		LOGGER.trace("begin %s.getOscillationOffset()", getClass().getSimpleName());
		Integer rVal = _fieldMap.get(OSCILLATION_OFFSET_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getOscillationOffset()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Integer getOscillationPeriod() {
		LOGGER.trace("begin %s.getOscillationPeriod()", getClass().getSimpleName());
		Integer rVal = _fieldMap.get(OSCILLATION_PERIOD_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getOscillationPeriod()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getTwitchMinimum() {
		LOGGER.trace("begin %s.getTwitchMinimum()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(TWITCH_MINIMUM_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getTwitchMinimum()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getTwitchMaximum() {
		LOGGER.trace("begin %s.getTwitchMaximum()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(TWITCH_MAXIMUM_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getTwitchMaximum()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getTwitchProbability() {
		LOGGER.trace("begin %s.getTwitchProbability()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(TWITCH_PROBABILITY_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getTwitchProbability()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Integer getDelay() {
		LOGGER.trace("begin %s.getDelay()", getClass().getSimpleName());
		Integer rVal = _fieldMap.get(DELAY_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getDelay()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getShift() {
		LOGGER.trace("begin %s.getShift()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(SHIFT_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getShift()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getScale() {
		LOGGER.trace("begin %s.getScale()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(SCALE_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getScale()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Boolean doesInvert() {
		LOGGER.trace("begin %s.doesInvert()", getClass().getSimpleName());
		Boolean rVal = _fieldMap.get(INVERT_FLAG_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.doesInvert()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final void calculate(final List<Float> linkInputs) {
		LOGGER.trace("begin %s.calculate(%s)", getClass().getSimpleName(), linkInputs);
		float excitatoryInput = 0.0F;
		float inhibitoryInput = 0.0F;
		for (final Float input : linkInputs) {
			if (input > 0.0F) {
				excitatoryInput += input;
			} else {
				inhibitoryInput += input;
			}
		}
		// next energy is current (back) energy...
		float nextEnergyValue = getEnergies().getBackEnergy();
		// decay
		nextEnergyValue -= getEnergies().getBackEnergy() * getEnergyDecay();
		// plus inputs...
		nextEnergyValue += (getExcitatoryScale() * excitatoryInput);
		nextEnergyValue += (getInhibitoryScale() * inhibitoryInput);
		// plus twitch / oscillation
		nextEnergyValue += twitch();
		nextEnergyValue += oscillation();
		// shifted, scaled, inverted
		nextEnergyValue += getShift();
		nextEnergyValue *= getScale();
		if (doesInvert()) {
			final Range<Float> range = ((AbstractRangeXParameter<Float>) (_energyQueue.getFirst().getParameter())).getLifetimeRange();
			nextEnergyValue = range.getMaximum() - nextEnergyValue + range.getMinimum();
		}
		_energyQueue.get(0).setValue(nextEnergyValue);
		LOGGER.trace("end %s.calculate()", getClass().getSimpleName());
	}

	/** {@inheritDoc} */
	@Override
	public final void update() {
		LOGGER.trace("begin %s.update()", getClass().getSimpleName());
		// bubble values forward
		for (int i = (_energyQueue.size() - 1); i > 0; i--) {
			_energyQueue.get(i).setValue( //
					_energyQueue.get(i - 1).getValue());
		}
		LOGGER.trace("end %s.update()", getClass().getSimpleName());
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("node %s; " //
				+ "energy=(%s -%f); " //
				+ "input (+%f, -%f); " //
				+ "osc (@%d+%d, [%f .. %f]); " //
				+ "twitch (%f [%f .. %f]); " //
				+ "bb ~%d +%f *%f inv? %b", //
				getId(), //
				getEnergies(), getEnergyDecay(), //
				getExcitatoryScale(), getInhibitoryScale(), //
				getOscillationOffset(), getOscillationPeriod(), //
				getOscillationMinimum(), getOscillationMaximum(), //
				getTwitchProbability(), //
				getTwitchMinimum(), getTwitchMaximum(), //
				getDelay(), getShift(), getScale(), doesInvert());
	}

	/**
	 * Returns the twitch for this node.
	 * 
	 * @return the twitch for this node
	 */
	private final float twitch() {
		LOGGER.trace("begin %s.twitch()", getClass().getSimpleName());
		if (RandomUtil.nextFloat() > getTwitchProbability()) {
			return 0.0F;
		}
		final float range = getTwitchMaximum() - getTwitchMinimum();
		final float rVal = RandomUtil.nextFloat() * range + getTwitchMinimum();
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.twitch()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Returns the oscillation for this node.
	 * 
	 * @return the oscillation for this node
	 */
	private final float oscillation() {
		LOGGER.trace("begin %s.oscillation()", getClass().getSimpleName());
		if (getOscillationPeriod() == 0) {
			return 0.0f;
		}
		final float rVal;
		int oscillationPhase = _oscillationPhase - getOscillationOffset();
		if ((oscillationPhase < 0) || ((oscillationPhase % getOscillationPeriod()) != 0)) {
			rVal = 0.0F;
		} else {
			final float range = getOscillationMaximum() - getOscillationMinimum();
			rVal = (RandomUtil.nextFloat() * range + getOscillationMinimum());
		}
		_oscillationPhase++;
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.twitch()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Integer setDelay(Integer i) {
		LOGGER.trace("begin %s.setDelay(%s)", getClass().getSimpleName(), i);
		Integer rVal = _fieldMap.set(DELAY_ID, i);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setDelay()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Energies setEnergies(Energies e) {
		LOGGER.trace("begin %s.setEnergies(%s)", getClass().getSimpleName(), e);
		Energies rVal = new Energies(//
				_energyQueue.getFirst().setValue(e.getFrontEnergy()),//
				_energyQueue.getLast().setValue(e.getBackEnergy()));
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setEnergies()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setEnergyDecay(Float f) {
		LOGGER.trace("begin %s.setEnergyDecay(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(ENERGY_DECAY_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setEnergyDecay()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setExcitatoryScale(Float f) {
		LOGGER.trace("begin %s.setExcitatoryScale(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(EXCITATORY_SCALE_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setExcitatoryScale()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setInhibitoryScale(Float f) {
		LOGGER.trace("begin %s.setInhibitoryScale(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(INHIBITORY_SCALE_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setInhibitoryScale()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Boolean setInvert(Boolean b) {
		LOGGER.trace("begin %s.setInvert(%s)", getClass().getSimpleName(), b);
		Boolean rVal = _fieldMap.set(INVERT_FLAG_ID, b);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setInvert()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setOscillationMaximum(Float f) {
		LOGGER.trace("begin %s.setOscillationMaximum(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(OSCILLATION_MAXIMUM_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setOscillationMaximum()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setOscillationMinimum(Float f) {
		LOGGER.trace("begin %s.setOscillationMinimum(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(OSCILLATION_MINIMUM_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setOscillationMinimum()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Integer setOscillationOffset(Integer i) {
		LOGGER.trace("begin %s.setOscillationOffset(%s)", getClass().getSimpleName(), i);
		Integer rVal = _fieldMap.set(OSCILLATION_OFFSET_ID, i);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setOscillationOffset()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Integer setOscillationPeriod(Integer i) {
		LOGGER.trace("begin %s.setOscillationPeriod(%s)", getClass().getSimpleName(), i);
		Integer rVal = _fieldMap.set(OSCILLATION_PERIOD_ID, i);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setOscillationPeriod()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setScale(Float f) {
		LOGGER.trace("begin %s.setScale(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(SCALE_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setScale()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setShift(Float f) {
		LOGGER.trace("begin %s.setShift(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(SHIFT_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setShift()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setTwitchMaximum(Float f) {
		LOGGER.trace("begin %s.setTwitchMaximum(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(TWITCH_MAXIMUM_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setTwitchMaximum()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setTwitchMinimum(Float f) {
		LOGGER.trace("begin %s.setTwitchMinimum(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(TWITCH_MINIMUM_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setTwitchMinimum()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setTwitchProbability(Float f) {
		LOGGER.trace("begin %s.setTwitchProbability(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(TWITCH_PROBABILITY_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setTwitchProbability()", getClass().getSimpleName());
		return rVal;
	}

	/** The XML format of a node. */
	private static final class NodeImplXmlFormat extends AbstractXmlFormat<NodeImpl> {
		private static final Attribute<XId>	CONFIG_ATTRIBUTE	= XmlUnit.newAttribute("config", XId.class);

		private NodeImplXmlFormat() {
			super(NodeImpl.class);
			addRecognized(CONFIG_ATTRIBUTE);
			for (Fragment fragment : Fragment.values()) {
				switch (fragment) {
					case ENERGY: {
						break;
					}
					default: {
						addRecognized(fragment.getParameterId().toElement());
					}
				}
			}
		}

		@Override
		protected NodeImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			XId configId = readAttributes.getValue(CONFIG_ATTRIBUTE);
			NodeConfiguration config = NodeConfiguration.getManager().getObject(configId);
			if (config == null) {
				System.err.println("NodeImpl.XmlFormat.newInstance() WARNING: no config with id " + configId + " found (referenced by node " + id + ")");
				// construct node using or default parameters
				config = NodeConfiguration.DEFAULT_CONFIGURATION;
				System.err.println("NodeImpl.XmlFormat.newInstance() WARNING: using " + config + ")");
			}
			NodeImpl node = new NodeImpl(id, config);
			// get field values
			for (Fragment fragment : Fragment.values()) {
				switch (fragment) {
					case ENERGY: {
						break;
					}
					default: {
						Object value = readElements.getValue(fragment.getParameterId().toElement());
						if (value != null) {
							fragment.setValue(node, value);
						}
					}
				}
			}
			return node;
		}

		@Override
		protected void writeAttributes(NodeImpl node, OutputElement xml) throws XMLStreamException {
			super.writeAttributes(node, xml);
			CONFIG_ATTRIBUTE.write(xml, node._nodeConfiguration.getId());
		}

		@Override
		protected void writeElements(NodeImpl node, OutputElement xml) throws XMLStreamException {
			for (Fragment fragment : Fragment.values()) {
				switch (fragment) {
					case ENERGY: {
						break;
					}
					default: {
						fragment.getParameterId().toElement().write(xml, fragment.getValue(node));
					}
				}
			}
		}
	}
}
