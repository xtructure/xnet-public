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

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

public class DummyGenome extends AbstractGenome<String> {
	public DummyGenome(int idNumber, String data) {
		super(idNumber, data);
	}

	public DummyGenome(int idNumber, Genome<String> genome) {
		super(idNumber, genome);
	}

	@Override
	public void validate() throws IllegalStateException {}

	public static final XmlFormat<DummyGenome>	XML_FORMAT	= new GenomeXmlFormat();

	private static final class GenomeXmlFormat extends AbstractXmlFormat<String, DummyGenome> {
		private static final Element<String>	DATA_ELEMENT	= XmlUnit.newElement("data", String.class);

		protected GenomeXmlFormat() {
			super(DummyGenome.class);
			addRecognized(DATA_ELEMENT);
		}

		@Override
		protected DummyGenome newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			String data = readElements.getValue(getDataElement());
			ValueMap attrs = readElements.getValue(ATTRIBUTES_ELEMENT);
			DummyGenome genome = new DummyGenome(id.getInstanceNum(), data);
			genome.getAttributes().setAll(attrs);
			return genome;
		}

		@Override
		protected Element<String> getDataElement() {
			return DATA_ELEMENT;
		}
	}
}
