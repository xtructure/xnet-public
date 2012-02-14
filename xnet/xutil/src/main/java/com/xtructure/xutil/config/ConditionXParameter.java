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

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.valid.Condition;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

public class ConditionXParameter extends AbstractXParameter<Condition> {
	public static final XmlFormat<ConditionXParameter>	XML_FORMAT	= new ParameterXmlFormat();
	private final Condition								value;

	public ConditionXParameter(XId id, String description, Boolean nullable, Boolean mutable, Condition value) {
		super(id, description, nullable, mutable);
		this.value = value;
	}

	public ConditionXParameter(ConditionXParameter parameter) {
		super(parameter);
		this.value = parameter.getCondition();
	}

	public Condition getCondition() {
		return value;
	}

	@Override
	public Field newField() {
		return new Field(this, getFieldVettingStrategy(value), value);
	}

	public static final class Field extends AbstractXField<Condition> {
		/** The XML format of a float field. */
		public static final XmlFormat<Field>	XML_FORMAT	= new FieldXmlFormat();

		/**
		 * Creates a new field.
		 * 
		 * @param param
		 *            the parameter to which this value is bound
		 * @param vettingStrategy
		 *            the vetting strategy used by this field
		 * @param initialCondition
		 *            the initial value of this variable
		 */
		private Field(final ConditionXParameter param, final VettingStrategy<Condition> vettingStrategy, final Condition initialCondition) {
			super(param, vettingStrategy, initialCondition);
		}

		public Field(Field field) {
			this((ConditionXParameter) field.getParameter(), field.getVettingStrategy(), field.getValue());
		}

		/** The XML format of a ConditionXParameter field. */
		private static final class FieldXmlFormat extends AbstractXmlFormat<Condition, Field, ConditionXParameter> {
			private static final Attribute<Condition>			VALUE_ATTRIBUTE		= XmlUnit.newAttribute("value");
			private static final Element<ConditionXParameter>	PARAMETER_ELEMENT	= XmlUnit.newElement("parameter", ConditionXParameter.class);

			public FieldXmlFormat() {
				super(Field.class);
			}

			@Override
			public Element<ConditionXParameter> getParameterElement() {
				return PARAMETER_ELEMENT;
			}

			@Override
			public Attribute<Condition> getValueAttribute() {
				return VALUE_ATTRIBUTE;
			}

			@Override
			protected Field newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				ConditionXParameter parameter = readElements.getValue(PARAMETER_ELEMENT);
				Field field = parameter.newField();
				field.setValue(readAttributes.getValue(VALUE_ATTRIBUTE));
				return field;
			}
		}
	}

	protected static final class ParameterXmlFormat extends AbstractXmlFormat<ConditionXParameter> {
		protected static final Element<Condition>	VALUE_ELEMENT	= XmlUnit.newElement("value");

		protected ParameterXmlFormat() {
			super(ConditionXParameter.class);
			addRecognized(VALUE_ELEMENT);
		}

		@Override
		protected ConditionXParameter newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
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
			Condition value = readElements.getValue(VALUE_ELEMENT);
			return new ConditionXParameter(id, description, nullable, mutable, value);
		}

		@Override
		protected void writeElements(ConditionXParameter object, OutputElement xml) throws XMLStreamException {
			VALUE_ELEMENT.write(xml, object.getCondition());
		}
	}
}
