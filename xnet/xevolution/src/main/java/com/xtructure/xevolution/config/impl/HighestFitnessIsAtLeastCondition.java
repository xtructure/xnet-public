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
package com.xtructure.xevolution.config.impl;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.valid.AbstractValueCondition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;

/**
 * @author Luis Guimbarda
 * 
 */
public class HighestFitnessIsAtLeastCondition extends AbstractValueCondition<Double> {
	public static HighestFitnessIsAtLeastCondition highestFitnessIsAtLeast(double value){
		return new HighestFitnessIsAtLeastCondition(value);
	}
	
	public HighestFitnessIsAtLeastCondition(double value) throws IllegalArgumentException {
		super(value, false);
	}

	@Override
	public boolean isSatisfiedBy(Object obj) {
		if (obj != null && obj instanceof Population<?>) {
			return ((Population<?>) obj).getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID).getFitness() >= getValue();
		}
		return false;
	}

	public static final XmlFormat<HighestFitnessIsAtLeastCondition>	XML_FORMAT	= new ConditionXmlFormat();

	private static final class ConditionXmlFormat extends AbstractXmlFormat<HighestFitnessIsAtLeastCondition> {
		protected ConditionXmlFormat() {
			super(HighestFitnessIsAtLeastCondition.class);
		}

		@Override
		protected HighestFitnessIsAtLeastCondition newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			Double value = (Double) readElements.getValue(VALUE_ELEMENT);
			if (value == null) {
				throw new XMLStreamException();
			}
			return new HighestFitnessIsAtLeastCondition(value);
		}

		@Override
		protected void writeAttributes(HighestFitnessIsAtLeastCondition object, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
