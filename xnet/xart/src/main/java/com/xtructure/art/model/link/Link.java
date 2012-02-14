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

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * An ART link.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * @version 0.9.6
 */
public interface Link extends XIdObject {
	/** the id for Link capacity parameters */
	public static final XValId<Float>	CAPACITY_ID					= XValId.newId("capacity", Float.class);
	/** the id for Link capacity attack parameters */
	public static final XValId<Float>	CAPACITY_ATTACK_ID			= XValId.newId("capacityAttack", Float.class);
	/** the id for Link capacity decay parameters */
	public static final XValId<Float>	CAPACITY_DECAY_ID			= XValId.newId("capacityDecay", Float.class);
	/** the id for Link strength parameters */
	public static final XValId<Float>	STRENGTH_ID					= XValId.newId("strength", Float.class);
	/** the id for Link strength attack parameters */
	public static final XValId<Float>	STRENGTH_ATTACK_ID			= XValId.newId("strengthAttack", Float.class);
	/** the id for Link strength decay parameters */
	public static final XValId<Float>	STRENGTH_DECAY_ID			= XValId.newId("strengthDecay", Float.class);
	/** the id for Link inhibitory flag parameters */
	public static final XValId<Boolean>	INHIBITORY_FLAG_ID			= XValId.newId("inhibitoryFlag", Boolean.class);
	/** the description of Link capacity parameters */
	public static final String			CAPACITY_DESCRIPTION		= "the capacity of an ART link";
	/** the description of Link capacity attack parameters */
	public static final String			CAPACITY_ATTACK_DESCRIPTION	= "the attack rate of the capacity of an ART link";
	/** the description of Link capacity decay parameters */
	public static final String			CAPACITY_DECAY_DESCRIPTION	= "the decay rate of the capacity of an ART link";
	/** the description of Link strength parameters */
	public static final String			STRENGTH_DESCRIPTION		= "the strength of an ART link";
	/** the description of Link strength attack parameters */
	public static final String			STRENGTH_ATTACK_DESCRIPTION	= "the attack rate of the strength of an ART link";
	/** the description of Link strength decay parameters */
	public static final String			STRENGTH_DECAY_DESCRIPTION	= "the decay rate of the strength of an ART link";
	/** the description of Link inhibitory flag parameters */
	public static final String			INHIBITORY_FLAG_DESCRIPTION	= "an indicator that an ART link is inhibitory";

	/**
	 * Returns the id of the source node of this link.
	 * 
	 * @return the id of the source node of this link
	 */
	public XId getSourceId();

	/**
	 * Returns the id of the target node of this link.
	 * 
	 * @return the id of the target node of this link
	 */
	public XId getTargetId();

	/**
	 * Returns the capacity of this link.
	 * 
	 * @return the capacity of this link
	 */
	public Float getCapacity();

	/**
	 * Returns the capacity attack of this link.
	 * 
	 * @return the capacity attack of this link
	 */
	public Float getCapacityAttack();

	/**
	 * Returns the capacity decay of this link.
	 * 
	 * @return the capacity decay of this link
	 */
	public Float getCapacityDecay();

	/**
	 * Returns the strength of this link.
	 * 
	 * @return the strength of this link
	 */
	public Float getStrength();

	/**
	 * Returns the strength attack of this link.
	 * 
	 * @return the strength attack of this link
	 */
	public Float getStrengthAttack();

	/**
	 * Returns the strength decay of this link.
	 * 
	 * @return the strength decay of this link
	 */
	public Float getStrengthDecay();

	/**
	 * Returns an indication of whether this link is inhibitory.
	 * 
	 * @return <code>true</code> if this link is inhibitory, <code>false</code>
	 *         otherwise
	 */
	public Boolean isInhibitory();

	/**
	 * Returns the energy output by this link.
	 * 
	 * @return the energy output by this link.
	 */
	public Float getOutputEnergy();

	/**
	 * Sets the capacity of this link.
	 * 
	 * @param f
	 *            the new capacity to set
	 * @return the actual capacity set (after parameter vetting)
	 */
	public Float setCapacity(Float f);

	/**
	 * Sets the capacity attack of this link.
	 * 
	 * @param f
	 *            the new capacity attack to set
	 * @return the actual capacity attack set (after parameter vetting)
	 */
	public Float setCapacityAttack(Float f);

	/**
	 * Sets the capacity decay of this link.
	 * 
	 * @param f
	 *            the new capacity decay to set
	 * @return the actual capacity decay set (after parameter vetting)
	 */
	public Float setCapacityDecay(Float f);

	/**
	 * Sets the strength of this link.
	 * 
	 * @param f
	 *            the new strength to set
	 * @return the actual strength set (after parameter vetting)
	 */
	public Float setStrength(Float f);

	/**
	 * Sets the strength attack of this link.
	 * 
	 * @param f
	 *            the new strength attack to set
	 * @return the actual strength attack set (after parameter vetting)
	 */
	public Float setStrengthAttack(Float f);

	/**
	 * Sets the strength decay of this link.
	 * 
	 * @param f
	 *            the new strength decay to set
	 * @return the actual strength decay set (after parameter vetting)
	 */
	public Float setStrengthDecay(Float f);

	/**
	 * Sets whether this link is inhibitory.
	 * 
	 * @param inhibitoryFlag
	 *            the new inhibitory flag to set
	 * @return the actual inhibitory flag set (after parameter vetting)
	 */
	public Boolean setInhibitoryFlag(Boolean inhibitoryFlag);

	/**
	 * Calculates the new state for this link.
	 * 
	 * @param sourceEnergy
	 *            the energy of the source node of this link
	 * @param targetEnergy
	 *            the energy of the target node of this link
	 * @return the output energy of this link
	 */
	public Float calculate(Float sourceEnergy, Float targetEnergy);

	/** Installs the new state calculated by {@link #calculate(Float, Float)}. */
	public void update();

	/** A fragment of an extended link id. */
	public enum Fragment implements com.xtructure.xutil.config.Fragment<Link> {
		/** The fragment denoting {@link Link#getCapacity()}. */
		CAPACITY(CAPACITY_ID, CAPACITY_DESCRIPTION),
		/** The fragment denoting {@link Link#getCapacityAttack()}. */
		CAPACITY_ATTACK(CAPACITY_ATTACK_ID, CAPACITY_ATTACK_DESCRIPTION),
		/** The fragment denoting {@link Link#getCapacityDecay()}. */
		CAPACITY_DECAY(CAPACITY_DECAY_ID, CAPACITY_DECAY_DESCRIPTION),
		/** The fragment denoting {@link Link#getStrength()}. */
		STRENGTH(STRENGTH_ID, STRENGTH_DESCRIPTION),
		/** The fragment denoting {@link Link#getStrengthAttack()}. */
		STRENGTH_ATTACK(STRENGTH_ATTACK_ID, STRENGTH_ATTACK_DESCRIPTION),
		/** The fragment denoting {@link Link#getStrengthDecay()}. */
		STRENGTH_DECAY(STRENGTH_DECAY_ID, STRENGTH_DECAY_DESCRIPTION),
		/** The fragment denoting {@link Link#isInhibitory()}. */
		IS_INHIBITORY(INHIBITORY_FLAG_ID, INHIBITORY_FLAG_DESCRIPTION),
		/** The fragment denoting {@link Link#getOutputEnergy()}. */
		OUTPUT_ENERGY(XValId.newId("outputEnergy", Float.class), "");
		/**
		 * Returns the fragment embedded in the given extended link id.
		 * 
		 * @param extendedLinkId
		 *            the link id to be parsed
		 * @return the fragment embedded in the given extended link id
		 */
		public static final Fragment getInstance(final XId extendedLinkId) {
			if (extendedLinkId != null) {
				final String[] frags = extendedLinkId.getBase().split("#");
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
		public static final Fragment getInstance(final XValId<?> parameterId) {
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
		 * Returns the base of the given extended link id.
		 * 
		 * @param extendedLinkId
		 *            the link id to be parsed
		 * @return the base of the given extended link id
		 */
		public static final XId getBaseId(final XId extendedLinkId) {
			if (extendedLinkId != null) {
				final String[] frags = extendedLinkId.getBase().split("#");
				if (frags != null && frags.length == 2) {
					final String name = frags[1];
					for (final Fragment fragment : values()) {
						if (fragment.toString().equals(name)) {
							return XId.newId(frags[0], //
									extendedLinkId.getInstanceNums());
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
		public final XId generateExtendedId(final XId baseLinkId) {
			if (baseLinkId != null) {
				return XId.newId( //
						String.format("%s#%s", baseLinkId.getBase(), toString()), //
						baseLinkId.getInstanceNums());
			}
			return null;
		}

		/** {@inheritDoc} */
		@SuppressWarnings("unchecked")
		@Override
		public final <V> V getValue(final Link link) {
			if (link != null) {
				switch (this) {
					case CAPACITY:
						return (V) link.getCapacity();
					case CAPACITY_ATTACK:
						return (V) link.getCapacityAttack();
					case CAPACITY_DECAY:
						return (V) link.getCapacityDecay();
					case STRENGTH:
						return (V) link.getStrength();
					case STRENGTH_ATTACK:
						return (V) link.getStrengthAttack();
					case STRENGTH_DECAY:
						return (V) link.getStrengthDecay();
					case IS_INHIBITORY:
						return (V) link.isInhibitory();
					case OUTPUT_ENERGY:
						return (V) link.getOutputEnergy();
				}
			}
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public final void setValue(Link link, Object value) {
			if (link != null) {
				switch (this) {
					case CAPACITY:
						link.setCapacity((Float) value);
						break;
					case CAPACITY_ATTACK:
						link.setCapacityAttack((Float) value);
						break;
					case CAPACITY_DECAY:
						link.setCapacityDecay((Float) value);
						break;
					case STRENGTH:
						link.setStrength((Float) value);
						break;
					case STRENGTH_ATTACK:
						link.setStrengthAttack((Float) value);
						break;
					case STRENGTH_DECAY:
						link.setStrengthDecay((Float) value);
						break;
					case IS_INHIBITORY:
						link.setInhibitoryFlag((Boolean) value);
						break;
					case OUTPUT_ENERGY:
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
		public String toString() {
			return parameterId.getBase();
		}
	}
}
