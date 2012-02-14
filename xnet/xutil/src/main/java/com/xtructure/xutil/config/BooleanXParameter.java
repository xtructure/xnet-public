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

/**
 * @author Luis Guimbarda
 * 
 */
public final class BooleanXParameter extends AbstractRangeXParameter<Boolean> {
	public static final XmlFormat<BooleanXParameter>	XML_FORMAT	= new ParameterXmlFormat();

	public BooleanXParameter(XId id, String description, Boolean nullable, Boolean mutable, Boolean value) {
		super(id, description, nullable, mutable, Range.getInstance(value), null, Boolean.class);
	}

	public BooleanXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Boolean> lifetimeRange, Range<Boolean> initialRange) {
		super(id, description, nullable, mutable, lifetimeRange, initialRange, Boolean.class);
	}

	public BooleanXParameter(AbstractRangeXParameter<Boolean> parameter) {
		super(parameter);
	}

	@Override
	public Field newField() {
		final boolean initialValue = getInitialRange().vet(newUniformRandomValue());
		return new Field(this, getFieldVettingStrategy(initialValue), initialValue);
	}

	public static final class Field extends AbstractXField<Boolean> {
		/** The XML format of a boolean field. */
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
		private Field(final BooleanXParameter param, final VettingStrategy<Boolean> vettingStrategy, final Boolean initialValue) {
			super(param, vettingStrategy, initialValue);
		}

		public Field(Field field) {
			this((BooleanXParameter) field.getParameter(), field.getVettingStrategy(), field.getValue());
		}

		/** The XML format of a BooleanXParameter field. */
		private static final class FieldXmlFormat extends AbstractXmlFormat<Boolean, Field, BooleanXParameter> {
			private static final Attribute<Boolean>			VALUE_ATTRIBUTE		= XmlUnit.newAttribute("value", Boolean.class);
			private static final Element<BooleanXParameter>	PARAMETER_ELEMENT	= XmlUnit.newElement("parameter", BooleanXParameter.class);

			public FieldXmlFormat() {
				super(Field.class);
			}

			@Override
			public Element<BooleanXParameter> getParameterElement() {
				return PARAMETER_ELEMENT;
			}

			@Override
			public Attribute<Boolean> getValueAttribute() {
				return VALUE_ATTRIBUTE;
			}

			@Override
			protected Field newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				BooleanXParameter parameter = readElements.getValue(PARAMETER_ELEMENT);
				Field field = parameter.newField();
				field.setValue(readAttributes.getValue(VALUE_ATTRIBUTE));
				return field;
			}
		}
	}

	private static final class ParameterXmlFormat extends AbstractXmlFormat<Boolean, BooleanXParameter> {
		protected ParameterXmlFormat() {
			super(BooleanXParameter.class);
		}

		@Override
		protected BooleanXParameter newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
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
			Range<Boolean> lifetimeRange = null, initialRange = null;
			try {
				if (lifetimeRangeString != null) {
					lifetimeRange = Range.parseRange(lifetimeRangeString, Boolean.class);
				}
				if (initialRangeString != null) {
					initialRange = Range.parseRange(initialRangeString, Boolean.class);
				}
			} catch (Exception e) {
				throw new XMLStreamException(e.getMessage());
			}
			return new BooleanXParameter(id, description, nullable, mutable, lifetimeRange, initialRange);
		}

		@Override
		protected void writeElements(BooleanXParameter object, OutputElement xml) throws XMLStreamException {
			// nothing
		}
	}
}
