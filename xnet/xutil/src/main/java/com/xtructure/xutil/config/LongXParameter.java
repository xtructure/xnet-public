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

public class LongXParameter extends AbstractRangeXParameter<Long> {
	public static final XmlFormat<LongXParameter>	XML_FORMAT	= new ParameterXmlFormat();

	public LongXParameter(XId id, String description, Boolean nullable, Boolean mutable, Long value) {
		super(id, description, nullable, mutable, Range.getInstance(value), null, Long.class);
	}

	public LongXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Long> lifetimeRange, Range<Long> initialRange) {
		super(id, description, nullable, mutable, lifetimeRange, initialRange, Long.class);
	}

	public LongXParameter(AbstractRangeXParameter<Long> parameter) {
		super(parameter);
	}

	@Override
	public Field newField() {
		Long initialValue = newUniformRandomValue();
		return new Field(this, getFieldVettingStrategy(initialValue), initialValue);
	}

	public static final class Field extends AbstractXField<Long> {
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
		private Field(final LongXParameter param, final VettingStrategy<Long> vettingStrategy, final Long initialValue) {
			super(param, vettingStrategy, initialValue);
		}

		public Field(Field field) {
			this((LongXParameter) field.getParameter(), field.getVettingStrategy(), field.getValue());
		}

		/** The XML format of a LongXParameter field. */
		private static final class FieldXmlFormat extends AbstractXmlFormat<Long, Field, LongXParameter> {
			private static final Attribute<Long>			VALUE_ATTRIBUTE		= XmlUnit.newAttribute("value", Long.class);
			private static final Element<LongXParameter>	PARAMETER_ELEMENT	= XmlUnit.newElement("parameter", LongXParameter.class);

			public FieldXmlFormat() {
				super(Field.class);
			}

			@Override
			public Element<LongXParameter> getParameterElement() {
				return PARAMETER_ELEMENT;
			}

			@Override
			public Attribute<Long> getValueAttribute() {
				return VALUE_ATTRIBUTE;
			}

			@Override
			protected Field newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				LongXParameter parameter = readElements.getValue(PARAMETER_ELEMENT);
				Field field = parameter.newField();
				field.setValue(readAttributes.getValue(VALUE_ATTRIBUTE));
				return field;
			}
		}
	}

	private static final class ParameterXmlFormat extends AbstractXmlFormat<Long, LongXParameter> {
		protected ParameterXmlFormat() {
			super(LongXParameter.class);
		}

		@Override
		protected LongXParameter newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
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
			Range<Long> lifetimeRange = null, initialRange = null;
			try {
				if (lifetimeRangeString != null) {
					lifetimeRange = Range.parseRange(lifetimeRangeString, Long.class);
				}
				if (initialRangeString != null) {
					initialRange = Range.parseRange(initialRangeString, Long.class);
				}
			} catch (Exception e) {
				throw new XMLStreamException(e.getMessage());
			}
			return new LongXParameter(id, description, nullable, mutable, lifetimeRange, initialRange);
		}

		@Override
		protected void writeElements(LongXParameter object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
