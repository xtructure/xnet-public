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

import java.util.Collection;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Attribute;
import com.xtructure.xutil.xml.XmlUnit.Element;

public class CollectionXParameter extends AbstractXParameter<Collection<?>> {
	public static final XmlFormat<CollectionXParameter>	XML_FORMAT	= new ParameterXmlFormat();
	private final Collection<?>							value;

	public CollectionXParameter(XId id, String description, Boolean nullable, Boolean mutable, Collection<?> value) {
		super(id, description, nullable, mutable);
		this.value = value;
	}

	public CollectionXParameter(CollectionXParameter parameter) {
		super(parameter);
		this.value = parameter.getCollection();
	}

	public Collection<?> getCollection() {
		return value;
	}

	@Override
	public Field newField() {
		return new Field(this, getFieldVettingStrategy(value), value);
	}

	public static final class Field extends AbstractXField<Collection<?>> {
		/** The XML format of a float field. */
		public static final XmlFormat<Field>	XML_FORMAT	= new FieldXmlFormat();

		/**
		 * Creates a new field.
		 * 
		 * @param param
		 *            the parameter to which this value is bound
		 * @param vettingStrategy
		 *            the vetting strategy used by this field
		 * @param initialCollection
		 *            the initial value of this variable
		 */
		private Field(final CollectionXParameter param, final VettingStrategy<Collection<?>> vettingStrategy, final Collection<?> initialCollection) {
			super(param, vettingStrategy, initialCollection);
		}

		public Field(Field field) {
			this((CollectionXParameter) field.getParameter(), field.getVettingStrategy(), field.getValue());
		}

		/** The XML format of a ConditionXParameter field. */
		private static final class FieldXmlFormat extends AbstractXmlFormat<Collection<?>, Field, CollectionXParameter> {
			private static final Attribute<Collection<?>>		VALUE_ATTRIBUTE		= XmlUnit.newAttribute("value");
			private static final Element<CollectionXParameter>	PARAMETER_ELEMENT	= XmlUnit.newElement("parameter", CollectionXParameter.class);

			public FieldXmlFormat() {
				super(Field.class);
				addRecognized(VALUE_ATTRIBUTE);
				addRecognized(PARAMETER_ELEMENT);
			}

			@Override
			public Element<CollectionXParameter> getParameterElement() {
				return PARAMETER_ELEMENT;
			}

			@Override
			public Attribute<Collection<?>> getValueAttribute() {
				return VALUE_ATTRIBUTE;
			}

			@Override
			protected Field newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				CollectionXParameter parameter = readElements.getValue(PARAMETER_ELEMENT);
				Field field = parameter.newField();
				field.setValue(readAttributes.getValue(VALUE_ATTRIBUTE));
				return field;
			}
		}
	}

	protected static final class ParameterXmlFormat extends AbstractXmlFormat<CollectionXParameter> {
		protected static final Element<Collection<?>>	VALUE_ELEMENT	= XmlUnit.newElement("value");

		protected ParameterXmlFormat() {
			super(CollectionXParameter.class);
			addRecognized(VALUE_ELEMENT);
		}

		@Override
		protected CollectionXParameter newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
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
			Collection<?> value = readElements.getValue(VALUE_ELEMENT);
			return new CollectionXParameter(id, description, nullable, mutable, value);
		}

		@Override
		protected void writeElements(CollectionXParameter object, OutputElement xml) throws XMLStreamException {
			VALUE_ELEMENT.write(xml, object.getCollection());
		}
	}
}
