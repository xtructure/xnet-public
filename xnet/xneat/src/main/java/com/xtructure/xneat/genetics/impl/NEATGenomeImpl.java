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

import javax.transaction.xa.Xid;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * {@link NEATGenomeImpl} implements the {@link NEATGenome} interface, where the
 * payload data is of type {@link GeneMap}.
 * 
 * @author Luis Guimbarda
 * 
 */
public class NEATGenomeImpl extends AbstractNEATGenome<GeneMap> {
	/** */
	public static final XmlFormat<NEATGenomeImpl>	XML_FORMAT	= new NEATGenomeImplXmlFormat();
	/** */
	public static final XmlBinding					XML_BINDING	= new XmlBinding(NEATGenomeImpl.class);

	/**
	 * Creates a new {@link NEATGenomeImpl} with the given data
	 * 
	 * @param idNumber
	 *            the instance number for the {@link XId} of the new
	 *            {@link NEATGenomeImpl}
	 * @param data
	 *            the {@link GeneMap} to use in this {@link NEATGenomeImpl}
	 */
	public NEATGenomeImpl(int idNumber, GeneMap data) {
		super(idNumber, data);
	}

	/**
	 * Creates a new {@link NEATGenomeImpl} with the data in the given
	 * {@link Genome}
	 * 
	 * @param idNumber
	 *            the instance number of the {@link Xid} of the new
	 *            {@link NEATGenomeImpl}
	 * @param genome
	 *            the {@link Genome} whose data is to be used in this
	 *            {@link NEATGenomeImpl}
	 */
	public NEATGenomeImpl(int idNumber, Genome<GeneMap> genome) {
		super(idNumber, genome);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.impl.AbstractGenome#validate()
	 */
	@Override
	public void validate() {
		getLogger().trace("begin %s.validate()", getClass().getSimpleName());
		super.validate();
		getLogger().trace("begin %s.validate()", getClass().getSimpleName());
	}

	private static final class NEATGenomeImplXmlFormat extends AbstractXmlFormat<GeneMap, NEATGenomeImpl> {
		private static final Element<GeneMap>	DATA_ELEMENT	= XmlUnit.newElement("data", GeneMap.class);

		protected NEATGenomeImplXmlFormat() {
			super(NEATGenomeImpl.class);
			addRecognized(DATA_ELEMENT);
		}

		@Override
		public Element<GeneMap> getDataElement() {
			return DATA_ELEMENT;
		}

		@Override
		protected NEATGenomeImpl newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			GeneMap data = readElements.getValue(DATA_ELEMENT);
			ValueMap measures = readElements.getValue(ATTRIBUTES_ELEMENT);
			NEATGenomeImpl genome = new NEATGenomeImpl(id.getInstanceNum(), data);
			genome.getAttributes().setAll(measures);
			return genome;
		}
	}
}
