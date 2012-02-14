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

/**
 * {@link GenomeImpl} implements the {@link Genome} interface, where the payload
 * data is of type String.
 * 
 * @author Luis Guimbarda
 * 
 */
public final class GenomeImpl extends AbstractGenome<String> {
	/** */
	public static final XmlFormat<GenomeImpl>	XML_FORMAT	= new GenomeImplXmlFormat();

	/**
	 * Creates a new {@link GenomeImpl}.
	 * 
	 * @param idNumber
	 *            the instance number of the id for this {@link GenomeImpl}
	 * @param data
	 *            the chromosome payload for this {@link GenomeImpl}
	 */
	public GenomeImpl(int idNumber, String data) {
		super(idNumber, data);
		setAttribute(COMPLEXITY_ATTRIBUTE_ID, (double) data.length());
	}

	/**
	 * Creates a new {@link GenomeImpl} whose data is a copy of the given
	 * genome's.
	 * 
	 * @param idNumber
	 *            the instance number of the id for this {@link GenomeImpl}
	 * @param genome
	 *            the {@link Genome} whose chromosome payload is to be used
	 */
	public GenomeImpl(int idNumber, Genome<String> genome) {
		this(idNumber, genome.getData());
	}

	private static final class GenomeImplXmlFormat extends AbstractXmlFormat<String, GenomeImpl> {
		private static final Element<String>	DATA_ELEMENT	= XmlUnit.newElement("data", String.class);

		protected GenomeImplXmlFormat() {
			super(GenomeImpl.class);
			addRecognized(DATA_ELEMENT);
		}

		@Override
		protected Element<String> getDataElement() {
			return DATA_ELEMENT;
		}

		@Override
		protected GenomeImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			String data = readElements.getValue(DATA_ELEMENT);
			ValueMap measures = readElements.getValue(ATTRIBUTES_ELEMENT);
			GenomeImpl genome = new GenomeImpl(id.getInstanceNum(), data);
			genome.getAttributes().setAll(measures);
			return genome;
		}
	}
}
