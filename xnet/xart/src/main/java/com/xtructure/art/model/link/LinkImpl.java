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
package com.xtructure.art.model.link;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;

/**
 * An ART link.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * @version 0.9.6
 */
public final class LinkImpl extends AbstractXIdObject implements Link {
	/** The logger for links. */
	private static final XLogger			LOGGER			= XLogger.getInstance(LinkImpl.class);
	/** The XML format for links */
	public static final XMLFormat<LinkImpl>	XML_FORMAT		= new LinkImplXmlFormat();
	/** The XML bindings for links */
	public static final XmlBinding			XML_BINDING		= new XmlBinding(LinkImpl.class);
	/** The base string for the default id. */
	private static final String				DEFAULT_BASE	= "default.art.link";
	/** The id of the default link. */
	public static final XId					DEFAULT_ID		= XId.newId(DEFAULT_BASE);
	/** The id of the source node of this link. */
	private final XId						_sourceId;
	/** The id of the target node of this link. */
	private final XId						_targetId;
	/** This link's configuration. */
	private final LinkConfiguration			_linkConfiguration;
	/** This link's field map */
	private final FieldMap					_fieldMap;
	/** The strength at the next update. */
	private float							_nextStrength	= 0.0F;
	/** The capacity at the next update. */
	private float							_nextCapacity	= 0.0F;
	/** Then energy delivered to this link's target node. */
	private float							_outputEnergy	= 0.0f;

	/**
	 * Creates a new link.
	 * 
	 * @param id
	 *            this link's id
	 * @param sourceId
	 *            the id of the this link's source node
	 * @param targetId
	 *            the id of the this link's target node
	 * @param config
	 *            this link's configuration
	 */
	public LinkImpl(//
			final XId id,//
			final XId sourceId,//
			final XId targetId,//
			final LinkConfiguration config) {
		super(id);
		_sourceId = sourceId;
		_targetId = targetId;
		_linkConfiguration = config;
		_fieldMap = config.newFieldMap();
	}

	/**
	 * Returns the configuration this link.
	 * 
	 * @return the configuration of this link.
	 */
	public final LinkConfiguration getConfiguration() {
		LOGGER.trace("begin %s.getConfiguration()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", _linkConfiguration);
		LOGGER.trace("end %s.getConfiguration()", getClass().getSimpleName());
		return _linkConfiguration;
	}

	/** {@inheritDoc} */
	@Override
	public final XId getSourceId() {
		LOGGER.trace("begin %s.getSourceId()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", _sourceId);
		LOGGER.trace("end %s.getSourceId()", getClass().getSimpleName());
		return _sourceId;
	}

	/** {@inheritDoc} */
	@Override
	public final XId getTargetId() {
		LOGGER.trace("begin %s.getTargetId()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", _targetId);
		LOGGER.trace("end %s.getTargetId()", getClass().getSimpleName());
		return _targetId;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getCapacity() {
		LOGGER.trace("begin %s.getCapacity()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(CAPACITY_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getCapacity()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getCapacityAttack() {
		LOGGER.trace("begin %s.getCapacityAttack()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(CAPACITY_ATTACK_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getCapacityAttack()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getCapacityDecay() {
		LOGGER.trace("begin %s.getCapacityDecay()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(CAPACITY_DECAY_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getCapacityDecay()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getStrength() {
		LOGGER.trace("begin %s.getStrength()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(STRENGTH_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getStrength()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getStrengthAttack() {
		LOGGER.trace("begin %s.getStrengthAttack()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(STRENGTH_ATTACK_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getStrengthAttack()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Float getStrengthDecay() {
		LOGGER.trace("begin %s.getStrengthDecay()", getClass().getSimpleName());
		Float rVal = _fieldMap.get(STRENGTH_DECAY_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.getStrengthDecay()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public final Boolean isInhibitory() {
		LOGGER.trace("begin %s.isInhibitory()", getClass().getSimpleName());
		Boolean rVal = _fieldMap.get(INHIBITORY_FLAG_ID);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.isInhibitory()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Returns the energy output by this link.
	 * 
	 * @return the energy output by this link.
	 */
	@Override
	public final Float getOutputEnergy() {
		LOGGER.trace("begin %s.getOutputEnergy()", getClass().getSimpleName());
		LOGGER.trace("will return: %s", _outputEnergy);
		LOGGER.trace("end %s.getOutputEnergy()", getClass().getSimpleName());
		return _outputEnergy;
	}

	/** {@inheritDoc} */
	@Override
	public final Float calculate(final Float sourceEnergy, final Float targetEnergy) {
		LOGGER.trace("begin %s.calculate(%f, %f)", getClass().getSimpleName(), sourceEnergy, targetEnergy);
		_nextStrength = (float) Math.min(Math.max(0.0,//
				getStrength() //
						- getStrengthDecay() * getStrength() * sourceEnergy //
						+ getStrengthAttack() * getCapacity()), //
				getCapacity());
		_nextCapacity = getCapacity() //
				- getCapacityDecay() * getCapacity() //
				+ getCapacityAttack() * sourceEnergy * targetEnergy;
		_outputEnergy = (isInhibitory() ? (-sourceEnergy * getStrength()) : (sourceEnergy * getStrength()));
		LOGGER.trace("will return: %s", _outputEnergy);
		LOGGER.trace("end %s.calculate()", getClass().getSimpleName());
		return _outputEnergy;
	}

	/** {@inheritDoc} */
	@Override
	public final void update() {
		LOGGER.trace("begin %s.update()", getClass().getSimpleName());
		setCapacity(_nextCapacity);
		setStrength(_nextStrength);
		LOGGER.trace("end %s.update()", getClass().getSimpleName());
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return String.format("link %s; " //
				+ "capacity=(%f +%f -%f); " //
				+ "strength=(%f +%f -%f); " //
				+ "inhibitory=%b", //
				getId(), //
				getCapacity(), getCapacityAttack(), getCapacityDecay(), //
				getStrength(), getStrengthAttack(), getStrengthDecay(), //
				isInhibitory());
	}

	/** {@inheritDoc} */
	@Override
	public Float setCapacity(Float f) {
		LOGGER.trace("begin %s.setCapacity(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(CAPACITY_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setCapacity()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setCapacityAttack(Float f) {
		LOGGER.trace("begin %s.setCapacityAttack(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(CAPACITY_ATTACK_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setCapacityAttack()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setCapacityDecay(Float f) {
		LOGGER.trace("begin %s.setCapacityDecay(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(CAPACITY_DECAY_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setCapacityDecay()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setStrength(Float f) {
		LOGGER.trace("begin %s.setStrength(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(STRENGTH_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setStrength()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setStrengthAttack(Float f) {
		LOGGER.trace("begin %s.setStrengthAttack(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(STRENGTH_ATTACK_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setStrengthAttack()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Float setStrengthDecay(Float f) {
		LOGGER.trace("begin %s.setStrengthDecay(%s)", getClass().getSimpleName(), f);
		Float rVal = _fieldMap.set(STRENGTH_DECAY_ID, f);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setStrengthDecay()", getClass().getSimpleName());
		return rVal;
	}

	/** {@inheritDoc} */
	@Override
	public Boolean setInhibitoryFlag(Boolean inhibitoryFlag) {
		LOGGER.trace("begin %s.setInhibitoryFlag(%s)", getClass().getSimpleName(), inhibitoryFlag);
		Boolean rVal = _fieldMap.set(INHIBITORY_FLAG_ID, inhibitoryFlag);
		LOGGER.trace("will return: %s", rVal);
		LOGGER.trace("end %s.setInhibitoryFlag()", getClass().getSimpleName());
		return rVal;
	}

	/** The XML format of a {@link LinkImpl}. */
	private static final class LinkImplXmlFormat extends AbstractXmlFormat<LinkImpl> {
		private static final Attribute<XId>	SOURCE_ID_ATTRIBUTE	= XmlUnit.newAttribute("source", XId.class);
		private static final Attribute<XId>	TARGET_ID_ATTRIBUTE	= XmlUnit.newAttribute("target", XId.class);
		private static final Attribute<XId>	CONFIG_ID_ATTRIBUTE	= XmlUnit.newAttribute("config", XId.class);

		protected LinkImplXmlFormat() {
			super(LinkImpl.class);
			addRecognized(SOURCE_ID_ATTRIBUTE);
			addRecognized(TARGET_ID_ATTRIBUTE);
			addRecognized(CONFIG_ID_ATTRIBUTE);
			for (Fragment fragment : Fragment.values()) {
				if (!Fragment.OUTPUT_ENERGY.equals(fragment)) {
					addRecognized(fragment.getElement());
				}
			}
		}

		@Override
		protected LinkImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			XId src = readAttributes.getValue(SOURCE_ID_ATTRIBUTE);
			XId tgt = readAttributes.getValue(TARGET_ID_ATTRIBUTE);
			XId configId = readAttributes.getValue(CONFIG_ID_ATTRIBUTE);
			LinkConfiguration config = LinkConfiguration.getManager().getObject(configId);
			if (config == null) {
				System.err.println("LinkImpl.XmlFormat.newInstance() WARNING: no config with id " + configId + " found (referenced by link " + id + ")");
				// construct link using default parameters
				config = LinkConfiguration.DEFAULT_CONFIGURATION;
				System.err.println("LinkImpl.XmlFormat.newInstance() WARNING: using " + config + ")");
			}
			LinkImpl link = new LinkImpl(id, src, tgt, config);
			// get field values
			for (Fragment fragment : Fragment.values()) {
				if (!Fragment.OUTPUT_ENERGY.equals(fragment)) {
					Object value = readElements.getValue(fragment.getElement());
					if (value != null) {
						fragment.setValue(link, value);
					}
				}
			}
			return link;
		}

		@Override
		protected void writeAttributes(LinkImpl obj, OutputElement xml) throws XMLStreamException {
			super.writeAttributes(obj, xml);
			SOURCE_ID_ATTRIBUTE.write(xml, obj._sourceId);
			TARGET_ID_ATTRIBUTE.write(xml, obj._targetId);
			CONFIG_ID_ATTRIBUTE.write(xml, obj._linkConfiguration.getId());
		}

		@Override
		protected void writeElements(LinkImpl obj, OutputElement xml) throws XMLStreamException {
			for (Fragment fragment : Fragment.values()) {
				if (!Fragment.OUTPUT_ENERGY.equals(fragment)) {
					fragment.getElement().write(xml, fragment.getValue(obj));
				}
			}
		}
	}
}
