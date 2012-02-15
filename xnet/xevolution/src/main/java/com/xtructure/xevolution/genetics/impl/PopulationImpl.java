/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.genetics.impl;

import java.util.List;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * An implementation of {@link Population}, for containing {@link Genome}s with
 * String type data. It adds to the functionality provided by
 * {@link AbstractPopulation} the ability to read a {@link PopulationImpl} from
 * a file.
 * 
 * @author Luis Guimbarda
 * 
 */
public class PopulationImpl extends AbstractPopulation<String> {
	/** */
	public static final XmlFormat<PopulationImpl>	XML_FORMAT	= new PopulationImplXmlFormat();

	/**
	 * Creates a new {@link PopulationImpl}.
	 * 
	 * @param instanceNumber
	 *            the instance number of the id for this {@link PopulationImpl}
	 */
	public PopulationImpl(int instanceNumber) {
		super(instanceNumber);
	}

	private static final class PopulationImplXmlFormat extends AbstractXmlFormat<String, PopulationImpl> {
		private static final Element<Genome<String>>	GENOME_ELEMENT	= XmlUnit.<Genome<String>,GenomeImpl>newElement("genome", GenomeImpl.class);

		protected PopulationImplXmlFormat() {
			super(PopulationImpl.class);
			addRecognized(GENOME_ELEMENT);
		}

		@Override
		protected Element<Genome<String>> getGenomeElement() {
			return GENOME_ELEMENT;
		}

		@Override
		protected PopulationImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			ValueMap map = readElements.getValue(ATTRIBUTES_ELEMENT);
			List<Genome<String>> genomes = readElements.getValues(GENOME_ELEMENT);
			PopulationImpl pop = new PopulationImpl(id.getInstanceNum());
			pop.getAttributes().setAll(map);
			if (genomes != null) {
				pop.addAll(genomes);
			}
			return pop;
		}
	}
}
