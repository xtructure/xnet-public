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

public class IntegerXParameter extends AbstractRangeXParameter<Integer> {
	public static final XmlFormat<IntegerXParameter>	XML_FORMAT	= new ParameterXmlFormat();

	public IntegerXParameter(XId id, String description, Boolean nullable, Boolean mutable, Integer value) {
		super(id, description, nullable, mutable, Range.getInstance(value), null, Integer.class);
	}

	public IntegerXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Integer> lifetimeRange, Range<Integer> initialRange) {
		super(id, description, nullable, mutable, lifetimeRange, initialRange, Integer.class);
	}

	public IntegerXParameter(AbstractRangeXParameter<Integer> parameter) {
		super(parameter);
	}

	@Override
	public Field newField() {
		Integer initialValue = newUniformRandomValue();
		return new Field(this, getFieldVettingStrategy(initialValue), initialValue);
	}

	public static final class Field extends AbstractXField<Integer> {
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
		private Field(final IntegerXParameter param, final VettingStrategy<Integer> vettingStrategy, final Integer initialValue) {
			super(param, vettingStrategy, initialValue);
		}

		public Field(Field field) {
			this((IntegerXParameter) field.getParameter(), field.getVettingStrategy(), field.getValue());
		}

		/** The XML format of a IntegerXParameter field. */
		private static final class FieldXmlFormat extends AbstractXmlFormat<Integer, Field, IntegerXParameter> {
			private static final Attribute<Integer>			VALUE_ATTRIBUTE		= XmlUnit.newAttribute("value", Integer.class);
			private static final Element<IntegerXParameter>	PARAMETER_ELEMENT	= XmlUnit.newElement("parameter", IntegerXParameter.class);

			public FieldXmlFormat() {
				super(Field.class);
			}

			@Override
			public Element<IntegerXParameter> getParameterElement() {
				return PARAMETER_ELEMENT;
			}

			@Override
			public Attribute<Integer> getValueAttribute() {
				return VALUE_ATTRIBUTE;
			}

			@Override
			protected Field newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				IntegerXParameter parameter = readElements.getValue(PARAMETER_ELEMENT);
				Field field = parameter.newField();
				field.setValue(readAttributes.getValue(VALUE_ATTRIBUTE));
				return field;
			}
		}
	}

	private static final class ParameterXmlFormat extends AbstractXmlFormat<Integer, IntegerXParameter> {
		protected ParameterXmlFormat() {
			super(IntegerXParameter.class);
		}

		@Override
		protected IntegerXParameter newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
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
			Range<Integer> lifetimeRange = null, initialRange = null;
			try {
				if (lifetimeRangeString != null) {
					lifetimeRange = Range.parseRange(lifetimeRangeString, Integer.class);
				}
				if (initialRangeString != null) {
					initialRange = Range.parseRange(initialRangeString, Integer.class);
				}
			} catch (Exception e) {
				throw new XMLStreamException(e.getMessage());
			}
			return new IntegerXParameter(id, description, nullable, mutable, lifetimeRange, initialRange);
		}

		@Override
		protected void writeElements(IntegerXParameter object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
