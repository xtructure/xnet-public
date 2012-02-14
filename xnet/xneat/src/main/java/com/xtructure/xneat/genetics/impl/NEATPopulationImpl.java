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
package com.xtructure.xneat.genetics.impl;

import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.NEATPopulation;
import com.xtructure.xneat.genetics.link.impl.LinkGeneImpl;
import com.xtructure.xneat.genetics.node.impl.NodeGeneImpl;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * An implementation of {@link NEATPopulation}, for containing
 * {@link NEATGenome}s with {@link GeneMap} type data.
 * 
 * @author Luis Guimbarda
 * 
 */
public class NEATPopulationImpl extends AbstractNEATPopulation<GeneMap> {
	/** */
	public static final XmlFormat<NEATPopulationImpl>	XML_FORMAT	= new NEATPopulationImplXmlFormat();
	/** */
	public static final XmlBinding						XML_BINDING	= XmlBinding.builder()//
																			.add(NEATPopulationImpl.class)//
																			.add(NEATGenomeImpl.class, "genome")//
																			.add(LinkGeneImpl.class)//
																			.add(NodeGeneImpl.class)//
																			.newInstance();

	/**
	 * Creates a new {@link NEATPopulationImpl}
	 * 
	 * @param instanceNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link NEATPopulationImpl}
	 */
	public NEATPopulationImpl(int instanceNumber) {
		super(instanceNumber);
	}

	private static final class NEATPopulationImplXmlFormat extends AbstractXmlFormat<GeneMap, NEATPopulationImpl> {
		private static final Element<Genome<GeneMap>>	GENOME_ELEMENT	= XmlUnit.newElement("genome", NEATGenomeImpl.class);

		protected NEATPopulationImplXmlFormat() {
			super(NEATPopulationImpl.class);
			addRecognized(GENOME_ELEMENT);
		}

		@Override
		protected Element<Genome<GeneMap>> getGenomeElement() {
			return GENOME_ELEMENT;
		}

		@Override
		protected NEATPopulationImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			ValueMap attrs = readElements.getValue(ATTRIBUTES_ELEMENT);
			List<Genome<GeneMap>> genomes = readElements.getValues(getGenomeElement());
			NEATPopulationImpl population = new NEATPopulationImpl(id.getInstanceNum());
			population.getAttributes().setAll(attrs);
			if (genomes != null) {
				population.addAll(genomes);
			}
			return population;
		}
	}
}
