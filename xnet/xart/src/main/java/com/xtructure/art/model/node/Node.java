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

import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * An ART node.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * @version 0.9.6
 */
public interface Node extends XIdObject {
	/** the id for Node energy parameters */
	public static final XValId<Float>	ENERGY_ID						= XValId.newId("energy", Float.class);
	/** the id for Node energy decay parameters */
	public static final XValId<Float>	ENERGY_DECAY_ID					= XValId.newId("energyDecay", Float.class);
	/** the id for Node excitatory scale parameters */
	public static final XValId<Float>	EXCITATORY_SCALE_ID				= XValId.newId("excitatoryScale", Float.class);
	/** the id for Node inhibitory scale parameters */
	public static final XValId<Float>	INHIBITORY_SCALE_ID				= XValId.newId("inhibitoryScale", Float.class);
	/** the id for Node oscillation minimum parameters */
	public static final XValId<Float>	OSCILLATION_MINIMUM_ID			= XValId.newId("oscillationMinimum", Float.class);
	/** the id for Node oscillation maximum parameters */
	public static final XValId<Float>	OSCILLATION_MAXIMUM_ID			= XValId.newId("oscillationMaximum", Float.class);
	/** the id for Node oscillation offset parameters */
	public static final XValId<Integer>	OSCILLATION_OFFSET_ID			= XValId.newId("oscillationOffset", Integer.class);
	/** the id for Node oscillation period parameters */
	public static final XValId<Integer>	OSCILLATION_PERIOD_ID			= XValId.newId("oscillationPeriod", Integer.class);
	/** the id for Node twitch minimum parameters */
	public static final XValId<Float>	TWITCH_MINIMUM_ID				= XValId.newId("twitchMinimum", Float.class);
	/** the id for Node twitch maximum parameters */
	public static final XValId<Float>	TWITCH_MAXIMUM_ID				= XValId.newId("twitchMaximum", Float.class);
	/** the id for Node twitch probability parameters */
	public static final XValId<Float>	TWITCH_PROBABILITY_ID			= XValId.newId("twitchProbability", Float.class);
	/** the id for Node delay parameters */
	public static final XValId<Integer>	DELAY_ID						= XValId.newId("delay", Integer.class);
	/** the id for Node shift parameters */
	public static final XValId<Float>	SHIFT_ID						= XValId.newId("shift", Float.class);
	/** the id for Node scale parameters */
	public static final XValId<Float>	SCALE_ID						= XValId.newId("scale", Float.class);
	/** the id for Node invert flag parameters */
	public static final XValId<Boolean>	INVERT_FLAG_ID					= XValId.newId("invert", Boolean.class);
	/** the description of Node energy parameters */
	public static final String			ENERGY_DESCRIPTION				= "the energy of an ART node";
	/** the description of Node energy decay parameters */
	public static final String			ENERGY_DECAY_DESCRIPTION		= "the energy decay rate of an ART node";
	/** the description of Node excitatory scale parameters */
	public static final String			EXCITATORY_SCALE_DESCRIPTION	= "the scaling factor for excitatory input";
	/** the description of Node inhibitory scale parameters */
	public static final String			INHIBITORY_SCALE_DESCRIPTION	= "the scaling factor for inhibitory input";
	/** the description of Node oscillation minimum parameters */
	public static final String			OSCILLATION_MINIMUM_DESCRIPTION	= "the oscillation minimum of an ART node";
	/** the description of Node oscillation maximum parameters */
	public static final String			OSCILLATION_MAXIMUM_DESCRIPTION	= "the oscillation maximum of an ART node";
	/** the description of Node oscillation offset parameters */
	public static final String			OSCILLATION_OFFSET_DESCRIPTION	= "the oscillation offset of an ART node";
	/** the description of Node oscillation period parameters */
	public static final String			OSCILLATION_PERIOD_DESCRIPTION	= "the oscillation period of an ART node";
	/** the description of Node twitch minimum parameters */
	public static final String			TWITCH_MINIMUM_DESCRIPTION		= "the twitch minimum of an ART node";
	/** the description of Node twitch maximum parameters */
	public static final String			TWITCH_MAXIMUM_DESCRIPTION		= "the twitch maximum of an ART node";
	/** the description of Node twitch probability parameters */
	public static final String			TWITCH_PROBABILITY_DESCRIPTION	= "the twitch probability of an ART node";
	/** the description of Node delay parameters */
	public static final String			DELAY_DESCRIPTION				= "the delay of an ART node";
	/** the description of Node shift parameters */
	public static final String			SHIFT_DESCRIPTION				= "the shift of an ART node";
	/** the description of Node scale parameters */
	public static final String			SCALE_DESCRIPTION				= "the scale of an ART node";
	/** the description of Node invert flag parameters */
	public static final String			INVERT_FLAG_DESCRIPTION			= "the invert flag of an ART node";

	/**
	 * Returns the energy at the front of this node.
	 * <p>
	 * This is the energy seen by outputs of this node.
	 * </p>
	 * 
	 * @return the energy at the front of this node
	 */
	public Energies getEnergies();

	/**
	 * Returns the energy decay of this node.
	 * 
	 * @return the energy decay of this node
	 */
	public Float getEnergyDecay();

	/**
	 * Returns the excitatory scaling factor of this node.
	 * 
	 * @return the excitatory scaling factor of this node
	 */
	public Float getExcitatoryScale();

	/**
	 * Returns the inhibitory scaling factor attack of this node.
	 * 
	 * @return the inhibitory scaling factor attack of this node
	 */
	public Float getInhibitoryScale();

	/**
	 * Returns the oscillation minimum of this node.
	 * 
	 * @return the oscillation minimum of this node
	 */
	public Float getOscillationMinimum();

	/**
	 * Returns the oscillation maximum of this node.
	 * 
	 * @return the oscillation maximum of this node
	 */
	public Float getOscillationMaximum();

	/**
	 * Returns the oscillation offset of this node.
	 * 
	 * @return the oscillation offset of this node
	 */
	public Integer getOscillationOffset();

	/**
	 * Returns the oscillation period of this node.
	 * 
	 * @return the oscillation period of this node
	 */
	public Integer getOscillationPeriod();

	/**
	 * Returns the twitch minimum of this node.
	 * 
	 * @return the twitch minimum of this node
	 */
	public Float getTwitchMinimum();

	/**
	 * Returns the twitch maximum of this node.
	 * 
	 * @return the twitch maximum of this node
	 */
	public Float getTwitchMaximum();

	/**
	 * Returns the twitch probability of this node.
	 * 
	 * @return the twitch probability of this node
	 */
	public Float getTwitchProbability();

	/**
	 * Returns the delay of this blackbox.
	 * 
	 * @return the delay of this blackbox
	 */
	public Integer getDelay();

	/**
	 * Returns the shift of this blackbox.
	 * 
	 * @return the shift of this blackbox
	 */
	public Float getShift();

	/**
	 * Returns the scale of this blackbox.
	 * 
	 * @return the scale of this blackbox
	 */
	public Float getScale();

	/**
	 * Returns an indication of whether this blackbox inverts its output.
	 * 
	 * @return <code>true</code> if this blackbox inverts its output;
	 *         <code>false</code> otherwise
	 */
	public Boolean doesInvert();

	/**
	 * Sets the energy at the front of this node.
	 * <p>
	 * This is the energy seen by outputs of this node.
	 * </p>
	 * 
	 * @param e
	 *            the energies to set
	 * @return the actual energies set (after parameter vetting)
	 */
	public Energies setEnergies(Energies e);

	/**
	 * Sets the energy decay of this node
	 * 
	 * @param f
	 *            the energy decay to set
	 * @return the actual energy decay set (after parameter vetting)
	 */
	public Float setEnergyDecay(Float f);

	/**
	 * Sets the excitatory scale of this node
	 * 
	 * @param f
	 *            the excitatory scale to set
	 * @return the actual excitatory scale set (after parameter vetting)
	 */
	public Float setExcitatoryScale(Float f);

	/**
	 * Sets the inhibitory scale of this node
	 * 
	 * @param f
	 *            the inhibitory scale to set
	 * @return the actual inhibitory scale set (after parameter vetting)
	 */
	public Float setInhibitoryScale(Float f);

	/**
	 * Sets the oscillation minimum of this node
	 * 
	 * @param f
	 *            the oscillation minimum to set
	 * @return the actual oscillation minimum set (after parameter vetting)
	 */
	public Float setOscillationMinimum(Float f);

	/**
	 * Sets the oscillation maximum of this node
	 * 
	 * @param f
	 *            the oscillation maximum to set
	 * @return the actual oscillation maximum set (after parameter vetting)
	 */
	public Float setOscillationMaximum(Float f);

	/**
	 * Sets the oscillation offset of this node
	 * 
	 * @param i
	 *            the oscillation offset to set
	 * @return the actual oscillation offset set (after parameter vetting)
	 */
	public Integer setOscillationOffset(Integer i);

	/**
	 * Sets the oscillation period of this node
	 * 
	 * @param i
	 *            the oscillation period to set
	 * @return the actual oscillation period set (after parameter vetting)
	 */
	public Integer setOscillationPeriod(Integer i);

	/**
	 * Sets the twitch minimum of this node
	 * 
	 * @param f
	 *            the twitch minimum to set
	 * @return the actual twitch minimum set (after parameter vetting)
	 */
	public Float setTwitchMinimum(Float f);

	/**
	 * Sets the twitch maximum of this node
	 * 
	 * @param f
	 *            the twitch maximum to set
	 * @return the actual twitch maximum set (after parameter vetting)
	 */
	public Float setTwitchMaximum(Float f);

	/**
	 * Sets the twitch probability of this node
	 * 
	 * @param f
	 *            the twitch probability to set
	 * @return the actual twitch probability set (after parameter vetting)
	 */
	public Float setTwitchProbability(Float f);

	/**
	 * Sets the delay of this node
	 * 
	 * @param i
	 *            the delay to set
	 * @return the actual delay set (after parameter vetting)
	 */
	public Integer setDelay(Integer i);

	/**
	 * Sets the shift of this node
	 * 
	 * @param f
	 *            the shift to set
	 * @return the actual shift set (after parameter vetting)
	 */
	public Float setShift(Float f);

	/**
	 * Sets the scale of this node
	 * 
	 * @param f
	 *            the scale to set
	 * @return the actual scale set (after parameter vetting)
	 */
	public Float setScale(Float f);

	/**
	 * Sets the invert flag of this node
	 * 
	 * @param b
	 *            the invert flag to set
	 * @return the actual invert flag set (after parameter vetting)
	 */
	public Boolean setInvert(Boolean b);

	/**
	 * Calculates the new state for this node.
	 * 
	 * @param linkInputs
	 *            the inputs from the links to this node
	 */
	public void calculate(List<Float> linkInputs);

	/** Installs the new state calculated by {@link #calculate(List)}. */
	public void update();

	/** Energies of a node from various perspectives. */
	public final class Energies {
		/** The energy at the "front" of a node (i.e., seen by outputs). */
		private final Float	_frontEnergy;
		/** The energy at the "back" of a node (i.e., seen by inputs). */
		private final Float	_backEnergy;

		/**
		 * Creates new energies.
		 * 
		 * @param frontEnergy
		 *            the energy at the "front" of a node (i.e., seen by
		 *            outputs)
		 * @param backEnergy
		 *            the energy at the "back" of a node (i.e., seen by inputs)
		 */
		public Energies(final Float frontEnergy, final Float backEnergy) {
			super();
			_frontEnergy = frontEnergy;
			_backEnergy = backEnergy;
		}

		/**
		 * Returns the energy at the &quot;front&quot; of a node (i.e., seen by
		 * outputs).
		 * 
		 * @return the energy at the &quot;front&quot; of a node (i.e., seen by
		 *         outputs)
		 */
		public final Float getFrontEnergy() {
			return _frontEnergy;
		}

		/**
		 * Returns the energy at the &quot;back&quot; of a node (i.e., seen by
		 * inputs).
		 * 
		 * @return the energy at the &quot;back&quot; of a node (i.e., seen by
		 *         inputs)
		 */
		public final Float getBackEnergy() {
			return _backEnergy;
		}

		/** {@inheritDoc} */
		@Override
		public final String toString() {
			return ((_frontEnergy == _backEnergy) ? String.valueOf(_frontEnergy) : String.format("%f->%f", _backEnergy, _frontEnergy));
		}

		/** {@inheritDoc} */
		@Override
		public boolean equals(Object that) {
			if (that == null) {
				return false;
			}
			if (!(that instanceof Energies)) {
				return false;
			}
			Energies thatEnergies = (Energies) that;
			return _frontEnergy.equals(thatEnergies._frontEnergy) && //
					_backEnergy.equals(thatEnergies._backEnergy);
		}

		/** The XML format of an Energies object. */
		public static final XmlFormat<Energies>	XML_FORMAT	= new EnergiesXmlFormat();

		/** The XML format of an Energies object. */
		private static final class EnergiesXmlFormat extends XmlFormat<Energies> {
			private static final Attribute<Float>	FRONT_ENERGY_ATTRIBUTE	= XmlUnit.newAttribute("frontEnergy", Float.class);
			private static final Attribute<Float>	BACK_ENERGY_ATTRIBUTE	= XmlUnit.newAttribute("backEnergy", Float.class);

			protected EnergiesXmlFormat() {
				super(Energies.class);
				addRecognized(FRONT_ENERGY_ATTRIBUTE);
				addRecognized(BACK_ENERGY_ATTRIBUTE);
			}

			@Override
			protected Energies newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				return new Energies(//
						readAttributes.getValue(FRONT_ENERGY_ATTRIBUTE),//
						readAttributes.getValue(BACK_ENERGY_ATTRIBUTE));
			}

			@Override
			protected void writeAttributes(Energies object, OutputElement xml) throws XMLStreamException {
				FRONT_ENERGY_ATTRIBUTE.write(xml, object.getFrontEnergy());
				BACK_ENERGY_ATTRIBUTE.write(xml, object.getBackEnergy());
			}

			@Override
			protected void writeElements(Energies object, OutputElement xml) throws XMLStreamException {
				// nothing
			}
		}
	}

	/** A fragment of an extended node id. */
	public enum Fragment implements com.xtructure.xutil.config.Fragment<Node> {
		/** The fragment denoting {@link Node#getEnergies()}. */
		ENERGIES(XValId.newId("energies", Energies.class), ""),
		/** The fragment denoting energy. */
		ENERGY(ENERGY_ID, ENERGY_DESCRIPTION), //
		/** The fragment denoting {@link Node#getEnergyDecay()}. */
		ENERGY_DECAY(ENERGY_DECAY_ID, ENERGY_DECAY_DESCRIPTION),
		/** The fragment denoting {@link Node#getExcitatoryScale()}. */
		EXCITATORY_SCALE(EXCITATORY_SCALE_ID, EXCITATORY_SCALE_DESCRIPTION),
		/** The fragment denoting {@link Node#getInhibitoryScale()}. */
		INHIBITORY_SCALE(INHIBITORY_SCALE_ID, INHIBITORY_SCALE_DESCRIPTION),
		/** The fragment denoting {@link Node#getOscillationMinimum()}. */
		OSCILLATION_MINIMUM(OSCILLATION_MINIMUM_ID, OSCILLATION_MINIMUM_DESCRIPTION),
		/** The fragment denoting {@link Node#getOscillationMaximum()}. */
		OSCILLATION_MAXIMUM(OSCILLATION_MAXIMUM_ID, OSCILLATION_MAXIMUM_DESCRIPTION),
		/** The fragment denoting {@link Node#getOscillationOffset()}. */
		OSCILLATION_OFFSET(OSCILLATION_OFFSET_ID, OSCILLATION_OFFSET_DESCRIPTION),
		/** The fragment denoting {@link Node#getOscillationPeriod()}. */
		OSCILLATION_PERIOD(OSCILLATION_PERIOD_ID, OSCILLATION_PERIOD_DESCRIPTION),
		/** The fragment denoting {@link Node#getTwitchMinimum()}. */
		TWITCH_MINIMUM(TWITCH_MINIMUM_ID, TWITCH_MINIMUM_DESCRIPTION),
		/** The fragment denoting {@link Node#getTwitchMaximum()}. */
		TWITCH_MAXIMUM(TWITCH_MAXIMUM_ID, TWITCH_MAXIMUM_DESCRIPTION),
		/** The fragment denoting {@link Node#getTwitchProbability()}. */
		TWITCH_PROBABILITY(TWITCH_PROBABILITY_ID, TWITCH_PROBABILITY_DESCRIPTION),
		/** The fragment denoting {@link Node#getDelay()}. */
		DELAY(DELAY_ID, DELAY_DESCRIPTION),
		/** The fragment denoting {@link Node#getShift()}. */
		SHIFT(SHIFT_ID, SHIFT_DESCRIPTION),
		/** The fragment denoting {@link Node#getScale()}. */
		SCALE(SCALE_ID, SCALE_DESCRIPTION),
		/** The fragment denoting {@link Node#doesInvert()}. */
		DOES_INVERT(INVERT_FLAG_ID, INVERT_FLAG_DESCRIPTION);
		/**
		 * Returns the fragment embedded in the given extended node id.
		 * 
		 * @param extendedNodeId
		 *            the node id to be parsed
		 * 
		 * @return the fragment embedded in the given extended node id
		 */
		public static Fragment getInstance(XId extendedNodeId) {
			if (extendedNodeId != null) {
				final String[] frags = extendedNodeId.getBase().split("#");
				if (frags != null && frags.length == 2) {
					final String name = frags[1];
					for (final Fragment fragment : values()) {
						if (fragment.toString().equals(name)) {
							return fragment;
						}
					}
				}
			}
			return null;
		}

		/**
		 * Returns the fragment associated with the given parameter id
		 * 
		 * @param parameterId
		 *            the parameter id to check
		 * @return the fragment associated with the given parameter id
		 */
		public static Fragment getInstance(XValId<?> parameterId) {
			if (parameterId != null) {
				for (Fragment fragment : values()) {
					if (fragment.getParameterId().equals(parameterId)) {
						return fragment;
					}
				}
			}
			return null;
		}

		/**
		 * Returns the base of the given extended node id.
		 * 
		 * @param extendedNodeId
		 *            the node id to be parsed
		 * 
		 * @return the base of the given extended node id
		 */
		public static XId getBaseId(XId extendedNodeId) {
			if (extendedNodeId != null) {
				final String[] frags = extendedNodeId.getBase().split("#");
				if (frags != null && frags.length == 2) {
					final String name = frags[1];
					for (final Fragment fragment : values()) {
						if (fragment.toString().equals(name)) {
							return XId.newId(frags[0], //
									extendedNodeId.getInstanceNums());
						}
					}
				}
			}
			return null;
		}

		/** the id of the parameter associated with this fragment */
		private final XValId<?>	parameterId;
		/** the description of the parameter associated with this fragment */
		private final String	description;

		/**
		 * Creates a new fragment.
		 * 
		 * @param parameterId
		 *            the id of the parameter associated with this fragment
		 * @param description
		 *            the description of the parameter associated with this
		 *            fragment
		 */
		private Fragment(final XValId<?> parameterId, final String description) {
			this.parameterId = parameterId;
			this.description = description;
		}

		/** {@inheritDoc} */
		@Override
		@SuppressWarnings("unchecked")
		public <V> XValId<V> getParameterId() {
			return (XValId<V>) parameterId;
		}

		/** {@inheritDoc} */
		@Override
		public String getDescription() {
			return description;
		}

		/** {@inheritDoc} */
		@Override
		public XId generateExtendedId(final XId baseNodeId) {
			if (baseNodeId != null) {
				return XId.newId( //
						String.format("%s#%s", baseNodeId.getBase(), toString()), //
						baseNodeId.getInstanceNums());
			}
			return null;
		}

		/** {@inheritDoc} */
		@SuppressWarnings("unchecked")
		@Override
		public <V> V getValue(final Node node) {
			if (node != null) {
				switch (this) {
					case ENERGIES:
						return (V) node.getEnergies();
					case ENERGY_DECAY:
						return (V) node.getEnergyDecay();
					case EXCITATORY_SCALE:
						return (V) node.getExcitatoryScale();
					case INHIBITORY_SCALE:
						return (V) node.getInhibitoryScale();
					case OSCILLATION_MINIMUM:
						return (V) node.getOscillationMinimum();
					case OSCILLATION_MAXIMUM:
						return (V) node.getOscillationMaximum();
					case OSCILLATION_OFFSET:
						return (V) node.getOscillationOffset();
					case OSCILLATION_PERIOD:
						return (V) node.getOscillationPeriod();
					case TWITCH_MINIMUM:
						return (V) node.getTwitchMinimum();
					case TWITCH_MAXIMUM:
						return (V) node.getTwitchMaximum();
					case TWITCH_PROBABILITY:
						return (V) node.getTwitchProbability();
					case DELAY:
						return (V) node.getDelay();
					case SHIFT:
						return (V) node.getShift();
					case SCALE:
						return (V) node.getScale();
					case DOES_INVERT:
						return (V) node.doesInvert();
					case ENERGY:
						throw new AssertionError("cannot get '" + this + "'");
				}
			}
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public void setValue(Node node, Object value) {
			if (node != null) {
				switch (this) {
					case ENERGIES:
						node.setEnergies((Energies) value);
						break;
					case ENERGY_DECAY:
						node.setEnergyDecay((Float) value);
						break;
					case EXCITATORY_SCALE:
						node.setExcitatoryScale((Float) value);
						break;
					case INHIBITORY_SCALE:
						node.setInhibitoryScale((Float) value);
						break;
					case OSCILLATION_MINIMUM:
						node.setOscillationMinimum((Float) value);
						break;
					case OSCILLATION_MAXIMUM:
						node.setOscillationMaximum((Float) value);
						break;
					case OSCILLATION_OFFSET:
						node.setOscillationOffset((Integer) value);
						break;
					case OSCILLATION_PERIOD:
						node.setOscillationPeriod((Integer) value);
						break;
					case TWITCH_MINIMUM:
						node.setTwitchMinimum((Float) value);
						break;
					case TWITCH_MAXIMUM:
						node.setTwitchMaximum((Float) value);
						break;
					case TWITCH_PROBABILITY:
						node.setTwitchProbability((Float) value);
						break;
					case DELAY:
						node.setDelay((Integer) value);
						break;
					case SHIFT:
						node.setShift((Float) value);
						break;
					case SCALE:
						node.setScale((Float) value);
						break;
					case DOES_INVERT:
						node.setInvert((Boolean) value);
						break;
					case ENERGY:
						throw new AssertionError("cannot set '" + this + "'");
				}
			}
		}

		/** {@inheritDoc} */
		@SuppressWarnings("unchecked")
		@Override
		public <V> Attribute<V> getAttribute() {
			return (Attribute<V>) getParameterId().toAttribute();
		}

		/** {@inheritDoc} */
		@SuppressWarnings("unchecked")
		@Override
		public <V> Element<V> getElement() {
			return (Element<V>) getParameterId().toElement();
		}

		/** {@inheritDoc} */
		@Override
		public final String toString() {
			return parameterId.getBase();
		}
	}
}
