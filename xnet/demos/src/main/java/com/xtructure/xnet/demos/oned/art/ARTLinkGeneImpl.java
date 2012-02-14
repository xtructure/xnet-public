/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.xtructure.xnet.demos.oned.art;

import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.link.impl.AbstractLinkGene;
import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * The Class ARTLinkGeneImpl.
 * 
 * @author Luis Guimbarda
 */
public class ARTLinkGeneImpl extends AbstractLinkGene implements ARTLinkGene {

	/** The Constant XML_FORMAT. */
	public static final XmlFormat<ARTLinkGeneImpl> XML_FORMAT = new GeneXmlFormat();

	/**
	 * Instantiates a new aRT link gene impl.
	 * 
	 * @param idNumber
	 *            the id number
	 * @param sourceId
	 *            the source id
	 * @param targetId
	 *            the target id
	 * @param configuration
	 *            the configuration
	 */
	public ARTLinkGeneImpl(int idNumber, XId sourceId, XId targetId,
			LinkConfiguration configuration) {
		super(idNumber, sourceId, targetId, configuration);
	}

	/**
	 * Instantiates a new aRT link gene impl.
	 * 
	 * @param idNumber
	 *            the id number
	 * @param link
	 *            the link
	 */
	public ARTLinkGeneImpl(int idNumber, LinkGene link) {
		super(idNumber, link);
	}

	/**
	 * Instantiates a new aRT link gene impl.
	 * 
	 * @param id
	 *            the id
	 * @param sourceId
	 *            the source id
	 * @param targetId
	 *            the target id
	 * @param innovation
	 *            the innovation
	 * @param configuration
	 *            the configuration
	 */
	private ARTLinkGeneImpl(XId id, XId sourceId, XId targetId,
			Innovation innovation, XConfiguration configuration) {
		super(id, sourceId, targetId, innovation, configuration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#getCapacity()
	 */
	@Override
	public Float getCapacity() {
		return getFieldMap().get(Link.CAPACITY_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#getCapacityAttack()
	 */
	@Override
	public Float getCapacityAttack() {
		return getFieldMap().get(Link.CAPACITY_ATTACK_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#getCapacityDecay()
	 */
	@Override
	public Float getCapacityDecay() {
		return getFieldMap().get(Link.CAPACITY_DECAY_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#getStrength()
	 */
	@Override
	public Float getStrength() {
		return getFieldMap().get(Link.STRENGTH_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#getStrengthAttack()
	 */
	@Override
	public Float getStrengthAttack() {
		return getFieldMap().get(Link.STRENGTH_ATTACK_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#getStrengthDecay()
	 */
	@Override
	public Float getStrengthDecay() {
		return getFieldMap().get(Link.STRENGTH_DECAY_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#isInhibitory()
	 */
	@Override
	public Boolean isInhibitory() {
		return getFieldMap().get(Link.INHIBITORY_FLAG_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#setCapacity(java.lang.Float)
	 */
	@Override
	public Float setCapacity(Float value) {
		return getFieldMap().set(Link.CAPACITY_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#setCapacityAttack(java.lang.Float)
	 */
	@Override
	public Float setCapacityAttack(Float value) {
		return getFieldMap().set(Link.CAPACITY_ATTACK_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#setCapacityDecay(java.lang.Float)
	 */
	@Override
	public Float setCapacityDecay(Float value) {
		return getFieldMap().set(Link.CAPACITY_DECAY_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#setStrength(java.lang.Float)
	 */
	@Override
	public Float setStrength(Float value) {
		return getFieldMap().set(Link.STRENGTH_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#setStrengthAttack(java.lang.Float)
	 */
	@Override
	public Float setStrengthAttack(Float value) {
		return getFieldMap().set(Link.STRENGTH_ATTACK_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#setStrengthDecay(java.lang.Float)
	 */
	@Override
	public Float setStrengthDecay(Float value) {
		return getFieldMap().set(Link.STRENGTH_DECAY_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.link.Link#setInhibitoryFlag(java.lang.Boolean)
	 */
	@Override
	public Boolean setInhibitoryFlag(Boolean value) {
		return getFieldMap().set(Link.INHIBITORY_FLAG_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#validate()
	 */
	@Override
	public void validate() throws IllegalStateException {
		// nothing
	}

	/**
	 * The Class GeneXmlFormat.
	 */
	private static final class GeneXmlFormat extends
			AbstractXmlFormat<ARTLinkGeneImpl> {

		/** The Constant CAPACITY_ELEMENT. */
		private static final Element<Float> CAPACITY_ELEMENT = XmlUnit
				.newElement("capacity", Float.class);

		/** The Constant CAPACITY_ATTACK_ELEMENT. */
		private static final Element<Float> CAPACITY_ATTACK_ELEMENT = XmlUnit
				.newElement("capacityAttack", Float.class);

		/** The Constant CAPACITY_DECAY_ELEMENT. */
		private static final Element<Float> CAPACITY_DECAY_ELEMENT = XmlUnit
				.newElement("capacityDecay", Float.class);

		/** The Constant STRENGTH_ELEMENT. */
		private static final Element<Float> STRENGTH_ELEMENT = XmlUnit
				.newElement("strength", Float.class);

		/** The Constant STRENGTH_ATTACK_ELEMENT. */
		private static final Element<Float> STRENGTH_ATTACK_ELEMENT = XmlUnit
				.newElement("strengthAttack", Float.class);

		/** The Constant STRENGTH_DECAY_ELEMENT. */
		private static final Element<Float> STRENGTH_DECAY_ELEMENT = XmlUnit
				.newElement("strengthDecay", Float.class);

		/** The Constant INHIBITORY_FLAG_ELEMENT. */
		private static final Element<Boolean> INHIBITORY_FLAG_ELEMENT = XmlUnit
				.newElement("inhibitory", Boolean.class);

		/**
		 * Instantiates a new gene xml format.
		 */
		private GeneXmlFormat() {
			super(ARTLinkGeneImpl.class);
			addRecognized(CAPACITY_ELEMENT);
			addRecognized(CAPACITY_ATTACK_ELEMENT);
			addRecognized(CAPACITY_DECAY_ELEMENT);
			addRecognized(STRENGTH_ELEMENT);
			addRecognized(STRENGTH_ATTACK_ELEMENT);
			addRecognized(STRENGTH_DECAY_ELEMENT);
			addRecognized(INHIBITORY_FLAG_ELEMENT);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.xtructure.xevolution.genetics.impl.AbstractGeneticsObject.
		 * AbstractXmlFormat
		 * #writeElements(com.xtructure.xevolution.genetics.impl
		 * .AbstractGeneticsObject, javolution.xml.XMLFormat.OutputElement)
		 */
		@Override
		protected void writeElements(ARTLinkGeneImpl obj,
				javolution.xml.XMLFormat.OutputElement xml)
				throws XMLStreamException {
			super.writeElements(obj, xml);
			CAPACITY_ELEMENT.write(xml, obj.getCapacity());
			CAPACITY_ATTACK_ELEMENT.write(xml, obj.getCapacityAttack());
			CAPACITY_DECAY_ELEMENT.write(xml, obj.getCapacityDecay());
			STRENGTH_ELEMENT.write(xml, obj.getStrength());
			STRENGTH_ATTACK_ELEMENT.write(xml, obj.getStrengthAttack());
			STRENGTH_DECAY_ELEMENT.write(xml, obj.getStrengthDecay());
			INHIBITORY_FLAG_ELEMENT.write(xml, obj.isInhibitory());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xutil.xml.XmlFormat#newInstance(com.xtructure.xutil
		 * .xml.ReadAttributes, com.xtructure.xutil.xml.ReadElements)
		 */
		@Override
		protected ARTLinkGeneImpl newInstance(ReadAttributes readAttributes,
				ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			XId srcId = readAttributes.getValue(SOURCE_ATTRIBUTE);
			XId tgtId = readAttributes.getValue(TARGET_ATTRIBUTE);
			Innovation inn = readAttributes.getValue(INNOVATION_ATTRIBUTE);
			XId configId = readAttributes.getValue(CONFIGURATION_ID_ATTRIBUTE);
			validateArg(String.format("LinkConfiguration with id %s exists",
					configId), LinkConfiguration.getManager().getIds(),
					containsElement(configId));
			LinkConfiguration configuration = LinkConfiguration.getManager()
					.getObject(configId);
			ARTLinkGeneImpl link = new ARTLinkGeneImpl(id, srcId, tgtId, inn,
					configuration);
			// get field values
			Float capacity = readElements.getValue(CAPACITY_ELEMENT);
			Float capacityAttack = readElements
					.getValue(CAPACITY_ATTACK_ELEMENT);
			Float capacityDecay = readElements.getValue(CAPACITY_DECAY_ELEMENT);
			Float strength = readElements.getValue(STRENGTH_ELEMENT);
			Float strengthAttack = readElements
					.getValue(STRENGTH_ATTACK_ELEMENT);
			Float strengthDecay = readElements.getValue(STRENGTH_DECAY_ELEMENT);
			Boolean inhibitoryFlag = readElements
					.getValue(INHIBITORY_FLAG_ELEMENT);
			// set field values (if they were provided)
			if (capacity != null) {
				link.setCapacity(capacity);
			}
			if (capacityAttack != null) {
				link.setCapacityAttack(capacityAttack);
			}
			if (capacityDecay != null) {
				link.setCapacityDecay(capacityDecay);
			}
			if (strength != null) {
				link.setStrength(strength);
			}
			if (strengthAttack != null) {
				link.setStrengthAttack(strengthAttack);
			}
			if (strengthDecay != null) {
				link.setStrengthDecay(strengthDecay);
			}
			if (inhibitoryFlag != null) {
				link.setInhibitoryFlag(inhibitoryFlag);
			}
			return link;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#getOutputEnergy()
	 */
	@Override
	public Float getOutputEnergy() {
		// nothing
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#calculate(java.lang.Float,
	 * java.lang.Float)
	 */
	@Override
	public Float calculate(Float sourceEnergy, Float targetEnergy) {
		// nothing
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.link.Link#update()
	 */
	@Override
	public void update() {
		// nothing
	}
}
