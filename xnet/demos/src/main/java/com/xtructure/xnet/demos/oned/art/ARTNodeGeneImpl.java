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

import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xneat.genetics.node.impl.AbstractNodeGene;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * The Class ARTNodeGeneImpl.
 * 
 * @author Luis Guimbarda
 */
public final class ARTNodeGeneImpl extends AbstractNodeGene implements
		ARTNodeGene {

	/** The Constant XML_FORMAT. */
	public static final XmlFormat<ARTNodeGeneImpl> XML_FORMAT = new GeneXmlFormat();

	/**
	 * Instantiates a new aRT node gene impl.
	 * 
	 * @param idNumber
	 *            the id number
	 * @param nodeType
	 *            the node type
	 * @param configuration
	 *            the configuration
	 */
	public ARTNodeGeneImpl(int idNumber, NodeType nodeType,
			NodeConfiguration configuration) {
		super(idNumber, nodeType, configuration);
	}

	/**
	 * Instantiates a new aRT node gene impl.
	 * 
	 * @param idNumber
	 *            the id number
	 * @param node
	 *            the node
	 */
	public ARTNodeGeneImpl(int idNumber, NodeGene node) {
		super(idNumber, node);
	}

	/**
	 * For xml.
	 * 
	 * @param id
	 *            the id
	 * @param innovation
	 *            the innovation
	 * @param nodeType
	 *            the node type
	 * @param configuration
	 *            the configuration
	 */
	private ARTNodeGeneImpl(XId id, Innovation innovation, NodeType nodeType,
			NodeConfiguration configuration) {
		super(id, innovation, nodeType, configuration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getEnergyDecay()
	 */
	@Override
	public Float getEnergyDecay() {
		return getFieldMap().get(Node.ENERGY_DECAY_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getExcitatoryScale()
	 */
	@Override
	public Float getExcitatoryScale() {
		return getFieldMap().get(Node.EXCITATORY_SCALE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getInhibitoryScale()
	 */
	@Override
	public Float getInhibitoryScale() {
		return getFieldMap().get(Node.INHIBITORY_SCALE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#setEnergyDecay(java.lang.Float)
	 */
	@Override
	public Float setEnergyDecay(Float value) {
		return getFieldMap().set(Node.ENERGY_DECAY_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.node.Node#setExcitatoryScale(java.lang.Float)
	 */
	@Override
	public Float setExcitatoryScale(Float value) {
		return getFieldMap().set(Node.EXCITATORY_SCALE_ID, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.node.Node#setInhibitoryScale(java.lang.Float)
	 */
	@Override
	public Float setInhibitoryScale(Float value) {
		return getFieldMap().set(Node.INHIBITORY_SCALE_ID, value);
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
			AbstractXmlFormat<ARTNodeGeneImpl> {

		/** The Constant ENERGY_DECAY_ELEMENT. */
		private static final Element<Float> ENERGY_DECAY_ELEMENT = XmlUnit
				.newElement("energyDecay", Float.class);

		/** The Constant EXCITATORY_SCALE_ELEMENT. */
		private static final Element<Float> EXCITATORY_SCALE_ELEMENT = XmlUnit
				.newElement("excitatoryScale", Float.class);

		/** The Constant INHIBITORY_SCALE_ELEMENT. */
		private static final Element<Float> INHIBITORY_SCALE_ELEMENT = XmlUnit
				.newElement("inhibitoryScale", Float.class);

		/**
		 * Instantiates a new gene xml format.
		 */
		protected GeneXmlFormat() {
			super(ARTNodeGeneImpl.class);
			addRecognized(ENERGY_DECAY_ELEMENT);
			addRecognized(EXCITATORY_SCALE_ELEMENT);
			addRecognized(INHIBITORY_SCALE_ELEMENT);
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
		protected void writeElements(ARTNodeGeneImpl obj,
				javolution.xml.XMLFormat.OutputElement xml)
				throws XMLStreamException {
			super.writeElements(obj, xml);
			ENERGY_DECAY_ELEMENT.write(xml, obj.getEnergyDecay());
			EXCITATORY_SCALE_ELEMENT.write(xml, obj.getExcitatoryScale());
			INHIBITORY_SCALE_ELEMENT.write(xml, obj.getInhibitoryScale());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xutil.xml.XmlFormat#newInstance(com.xtructure.xutil
		 * .xml.ReadAttributes, com.xtructure.xutil.xml.ReadElements)
		 */
		@Override
		protected ARTNodeGeneImpl newInstance(ReadAttributes readAttributes,
				ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			NodeType nodeType = NodeType.parse(readAttributes
					.getValue(NODE_TYPE_ATTRIBUTE));
			Innovation inn = readAttributes.getValue(INNOVATION_ATTRIBUTE);
			XId configId = readAttributes.getValue(CONFIGURATION_ID_ATTRIBUTE);
			validateArg(String.format("NodeConfiguration with id %s exists",
					configId), NodeConfiguration.getManager().getIds(),
					containsElement(configId));
			NodeConfiguration configuration = NodeConfiguration.getManager()
					.getObject(configId);
			ARTNodeGeneImpl node = new ARTNodeGeneImpl(id, inn, nodeType,
					configuration);
			// get field values
			Float energyDecay = readElements.getValue(ENERGY_DECAY_ELEMENT);
			Float excitatoryScale = readElements
					.getValue(EXCITATORY_SCALE_ELEMENT);
			Float inhibitoryScale = readElements
					.getValue(INHIBITORY_SCALE_ELEMENT);
			// set field values (if they were provided)
			if (energyDecay != null) {
				node.setEnergyDecay(energyDecay);
			}
			if (excitatoryScale != null) {
				node.setExcitatoryScale(excitatoryScale);
			}
			if (inhibitoryScale != null) {
				node.setInhibitoryScale(inhibitoryScale);
			}
			return node;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getEnergies()
	 */
	@Override
	public Energies getEnergies() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getOscillationMinimum()
	 */
	@Override
	public Float getOscillationMinimum() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getOscillationMaximum()
	 */
	@Override
	public Float getOscillationMaximum() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getOscillationOffset()
	 */
	@Override
	public Integer getOscillationOffset() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getOscillationPeriod()
	 */
	@Override
	public Integer getOscillationPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getTwitchMinimum()
	 */
	@Override
	public Float getTwitchMinimum() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getTwitchMaximum()
	 */
	@Override
	public Float getTwitchMaximum() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getTwitchProbability()
	 */
	@Override
	public Float getTwitchProbability() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getDelay()
	 */
	@Override
	public Integer getDelay() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getShift()
	 */
	@Override
	public Float getShift() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#getScale()
	 */
	@Override
	public Float getScale() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#doesInvert()
	 */
	@Override
	public Boolean doesInvert() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.node.Node#setEnergies(com.xtructure.art.model
	 * .node.Node.Energies)
	 */
	@Override
	public Energies setEnergies(Energies e) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.node.Node#setOscillationMinimum(java.lang.Float)
	 */
	@Override
	public Float setOscillationMinimum(Float f) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.node.Node#setOscillationMaximum(java.lang.Float)
	 */
	@Override
	public Float setOscillationMaximum(Float f) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.node.Node#setOscillationOffset(java.lang.Integer)
	 */
	@Override
	public Integer setOscillationOffset(Integer i) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.node.Node#setOscillationPeriod(java.lang.Integer)
	 */
	@Override
	public Integer setOscillationPeriod(Integer i) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#setTwitchMinimum(java.lang.Float)
	 */
	@Override
	public Float setTwitchMinimum(Float f) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#setTwitchMaximum(java.lang.Float)
	 */
	@Override
	public Float setTwitchMaximum(Float f) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.art.model.node.Node#setTwitchProbability(java.lang.Float)
	 */
	@Override
	public Float setTwitchProbability(Float f) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#setDelay(java.lang.Integer)
	 */
	@Override
	public Integer setDelay(Integer i) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#setShift(java.lang.Float)
	 */
	@Override
	public Float setShift(Float f) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#setScale(java.lang.Float)
	 */
	@Override
	public Float setScale(Float f) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#setInvert(java.lang.Boolean)
	 */
	@Override
	public Boolean setInvert(Boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#calculate(java.util.List)
	 */
	@Override
	public void calculate(List<Float> linkInputs) {
		// nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.art.model.node.Node#update()
	 */
	@Override
	public void update() {
		// nothing
	}
}
