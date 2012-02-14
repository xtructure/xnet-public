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
package com.xtructure.xneat.genetics;

import java.io.IOException;

import javolution.text.Cursor;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xutil.format.XTextFormat;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;
import com.xtructure.xutil.id.XIdObjectManagerImpl;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * The {@link Innovation} class represents the NEAT concept of innovation which
 * tracks changes to the evolving networks.
 * 
 * @author Luis Guimbarda
 * 
 */
public class Innovation extends AbstractXIdObject {
	/** */
	public static final XmlFormat<Innovation>				XML_FORMAT			= new InnovationXmlFormat();
	/** */
	public static final XTextFormat<Innovation>				TEXT_FORMAT			= new InnovationTextFormat();
	/** the base id for {@link Innovation} instances */
	public static final XId									INNOVATION_BASE_ID	= XId.newId("Innovation");
	/** the manager for {@link Innovation} instances */
	private static final XIdObjectManagerImpl<Innovation>	MANAGER;
	static {
		MANAGER = new XIdObjectManagerImpl<Innovation>() {};
	}

	/**
	 * Gets or creates an {@link Innovation} instance for a {@link NodeGene}
	 * with the given idNumber.
	 * 
	 * @param idNumber
	 *            instance number of the id of the node gene
	 * @return the {@link Innovation} for that node gene
	 */
	public static Innovation generate(int idNumber) {
		XId innovationId = INNOVATION_BASE_ID.createChild(idNumber);
		Innovation innovation = MANAGER.getObject(innovationId);
		if (innovation == null) {
			return new Innovation(innovationId);
		}
		return innovation;
	}

	/**
	 * Gets or creates an {@link Innovation} instance for a LinkGene with the
	 * given sourceIdNumber and targetIdNumber
	 * 
	 * @param sourceIdNumber
	 *            instance number of the id of the source of the link gene
	 * @param targetIdNumber
	 *            instance number of the id of the target of the link gene
	 * @return the {@link Innovation} for that link gene
	 */
	public static Innovation generate(int sourceIdNumber, int targetIdNumber) {
		XId innovationId = INNOVATION_BASE_ID//
				.createChild(sourceIdNumber)//
				.createChild(targetIdNumber);
		Innovation innovation = MANAGER.getObject(innovationId);
		if (innovation == null) {
			return new Innovation(innovationId);
		}
		return innovation;
	}

	/**
	 * Creates a new {@link Innovation} with the given id
	 * 
	 * @param id
	 *            the {@link XId} for the new {@link Innovation}
	 */
	private Innovation(XId id) {
		super(id, MANAGER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xutil.id.AbstractXIdObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xutil.id.AbstractXIdObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Innovation) {
			return getId().equals(((Innovation) obj).getId());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xutil.id.AbstractXIdObject#compareTo(com.xtructure.xutil
	 * .id.XIdObject)
	 */
	@Override
	public int compareTo(XIdObject other) {
		if (other != null) {
			return getId().compareTo(other.getId());
		}
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getId().toString();
	}

	/** */
	private static final class InnovationTextFormat extends XTextFormat<Innovation> {
		protected InnovationTextFormat() {
			super(Innovation.class);
		}

		@Override
		public Appendable format(Innovation innovation, Appendable appendable) throws IOException {
			return appendable.append(innovation.getId().toString());
		}

		@Override
		public Innovation parse(CharSequence chars, Cursor cursor) throws IllegalArgumentException {
			XId id = XId.TEXT_FORMAT.parse(chars, cursor);
			switch (id.getInstanceNums().size()) {
				case 1: {
					return Innovation.generate(id.getInstanceNum());
				}
				case 2: {
					return Innovation.generate(id.getInstanceNums().get(0), id.getInstanceNums().get(1));
				}
				default: {
					throw new IllegalArgumentException(String.format("Innovation %s not recognized", id));
				}
			}
		}
	}

	/** xml format for {@link Innovation} */
	private static final class InnovationXmlFormat extends AbstractXmlFormat<Innovation> {
		protected InnovationXmlFormat() {
			super(Innovation.class);
		}

		@Override
		protected Innovation newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			XId id = readAttributes.getValue(ID_ATTRIBUTE);
			Innovation innovation = MANAGER.getObject(id);
			if (innovation != null) {
				return innovation;
			}
			return new Innovation(id);
		}

		@Override
		protected void writeElements(Innovation obj, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
