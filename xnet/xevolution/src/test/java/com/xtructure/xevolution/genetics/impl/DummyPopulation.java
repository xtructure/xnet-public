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
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

public class DummyPopulation extends AbstractPopulation<String> {
	public DummyPopulation(int instanceNum) {
		super(instanceNum);
	}

	public static final XmlFormat<DummyPopulation>	XML_FORMAT	= new PopulationXmlFormat();

	private static final class PopulationXmlFormat extends AbstractXmlFormat<String, DummyPopulation> {
		private static final Element<Genome<String>>	GENOME_ELEMENT	= XmlUnit.newElement("genome", DummyGenome.class);

		protected PopulationXmlFormat() {
			super(DummyPopulation.class);
			addRecognized(GENOME_ELEMENT);
		}

		@Override
		protected Element<Genome<String>> getGenomeElement() {
			return GENOME_ELEMENT;
		}

		@Override
		protected DummyPopulation newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			List<Genome<String>> genomes = readElements.getValues(getGenomeElement());
			ValueMap attrs = readElements.getValue(ATTRIBUTES_ELEMENT);
			DummyPopulation pop = new DummyPopulation(id.getInstanceNum());
			pop.getAttributes().setAll(attrs);
			if (genomes != null) {
				pop.addAll(genomes);
			}
			pop.refreshStats();
			return pop;
		}
	}
}
