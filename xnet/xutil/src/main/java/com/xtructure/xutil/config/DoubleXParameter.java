/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xutil.config;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.Range;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

public class DoubleXParameter extends AbstractRangeXParameter<Double> {
	public static final XmlFormat<DoubleXParameter>	XML_FORMAT	= new ParameterXmlFormat();

	public DoubleXParameter(XId id, String description, Boolean nullable, Boolean mutable, Double value) {
		super(id, description, nullable, mutable, Range.getInstance(value), null, Double.class);
	}

	public DoubleXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Double> lifetimeRange, Range<Double> initialRange) {
		super(id, description, nullable, mutable, lifetimeRange, initialRange, Double.class);
	}

	public DoubleXParameter(AbstractRangeXParameter<Double> parameter) {
		super(parameter);
	}

	@Override
	public Field newField() {
		Double initialValue = newUniformRandomValue();
		return new Field(this, getFieldVettingStrategy(initialValue), initialValue);
	}

	public static final class Field extends AbstractXField<Double> {
		/** The XML format of a float field. */
		public static final XmlFormat<Field>	XML_FORMAT	= new FieldXmlFormat();

		/**
		 * Creates a new field.
		 * 
		 * @param param
		 *            the parameter to which this value is bound
		 * @param vettingStrategy
		 *            the vetting strategy used by this field
		 * @param initialValue
		 *            the initial value of this variable
		 */
		private Field(final DoubleXParameter param, final VettingStrategy<Double> vettingStrategy, final Double initialValue) {
			super(param, vettingStrategy, initialValue);
		}

		public Field(Field field) {
			this((DoubleXParameter) field.getParameter(), field.getVettingStrategy(), field.getValue());
		}

		/** The XML format of a DoubleXParameter field. */
		private static final class FieldXmlFormat extends AbstractXmlFormat<Double, Field, DoubleXParameter> {
			private static final Attribute<Double>			VALUE_ATTRIBUTE		= XmlUnit.newAttribute("value", Double.class);
			private static final Element<DoubleXParameter>	PARAMETER_ELEMENT	= XmlUnit.newElement("parameter", DoubleXParameter.class);

			public FieldXmlFormat() {
				super(Field.class);
			}

			@Override
			public Element<DoubleXParameter> getParameterElement() {
				return PARAMETER_ELEMENT;
			}

			@Override
			public Attribute<Double> getValueAttribute() {
				return VALUE_ATTRIBUTE;
			}

			@Override
			protected Field newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				DoubleXParameter parameter = readElements.getValue(PARAMETER_ELEMENT);
				Field field = parameter.newField();
				field.setValue(readAttributes.getValue(VALUE_ATTRIBUTE));
				return field;
			}
		}
	}

	private static final class ParameterXmlFormat extends AbstractXmlFormat<Double, DoubleXParameter> {
		protected ParameterXmlFormat() {
			super(DoubleXParameter.class);
		}

		@Override
		protected DoubleXParameter newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
			String idString = readAttributes.getValue(ID_ATTRIBUTE);
			XId id = null;
			try {
				id = XValId.TEXT_FORMAT.parse(idString);
			} catch (IllegalArgumentException e1) {
				id = XId.TEXT_FORMAT.parse(idString);
			}
			String description = readAttributes.getValue(DESCRIPTION_ATTRIBUTE);
			Boolean nullable = readAttributes.getValue(NULLABLE_ATTRIBUTE);
			Boolean mutable = readAttributes.getValue(MUTABLE_ATTRIBUTE);
			String lifetimeRangeString = readAttributes.getValue(LIFETIME_RANGE_ATTRIBUTE);
			String initialRangeString = readAttributes.getValue(INITIAL_RANGE_ATTRIBUTE);
			Range<Double> lifetimeRange = null, initialRange = null;
			try {
				if (lifetimeRangeString != null) {
					lifetimeRange = Range.parseRange(lifetimeRangeString, Double.class);
				}
				if (initialRangeString != null) {
					initialRange = Range.parseRange(initialRangeString, Double.class);
				}
			} catch (Exception e) {
				throw new XMLStreamException(e.getMessage());
			}
			try {
				return new DoubleXParameter(id, description, nullable, mutable, lifetimeRange, initialRange);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void writeElements(DoubleXParameter object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
