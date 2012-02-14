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
package com.xtructure.xutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javolution.text.Cursor;
import javolution.text.TextFormat;
import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.xml.ReadAttributes;
import com.xtructure.xutil.xml.ReadElements;
import com.xtructure.xutil.xml.XmlFormat;
import com.xtructure.xutil.xml.XmlUnit;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * The Class ValueMap.
 *
 * @author Luis Guimbarda
 */
public class ValueMap implements Comparable<ValueMap> {
	
	/** The Constant TEXT_FORMAT. */
	public static final TextFormat<ValueMap>	TEXT_FORMAT	= new ValueMapTextFormat();
	/** map from {@link XValId}s to their values */
	protected final HashMap<XValId<?>, Object>	map;

	/**
	 * Creates a new {@link ValueMap}.
	 */
	public ValueMap() {
		this.map = new HashMap<XValId<?>, Object>();
	}

	/**
	 * Instantiates a new value map.
	 *
	 * @param valueMap the value map
	 */
	public ValueMap(ValueMap valueMap) {
		this.map = new HashMap<XValId<?>, Object>(valueMap.map);
	}

	/**
	 * Instantiates a new value map.
	 *
	 * @param jsonObject the json object
	 */
	public ValueMap(JSONObject jsonObject) {
		this();
		for (Object key : jsonObject.keySet()) {
			XValId<?> id = XValId.TEXT_FORMAT.parse(key.toString());
			Object value;
			if (ValueType.STRING.isTypeOf(id)) {
				value = jsonObject.get(key).toString();
			} else {
				TextFormat<?> tf = TextFormat.getInstance(id.getType());
				value = tf.parse(jsonObject.get(key).toString());
			}
			map.put(id, value);
		}
	}

	/**
	 * Gets the value associated with the given {@link XValId} in this
	 * {@link ValueMap}.
	 * 
	 * @param <V>
	 *            the type of value being get
	 * @param valueId the valueId
	 * @return the value associated with the given {@link XValId} in this
	 *         {@link ValueMap}.
	 */
	@SuppressWarnings("unchecked")
	public <V> V get(final XValId<V> valueId) {
		return (V) map.get(valueId);
	}

	/**
	 * Sets the value associated with the given {@link XValId} in this
	 * {@link ValueMap} to that given.
	 * 
	 * @param <V>
	 *            the type of value being set
	 * @param valueId the valueId
	 * @param value the value
	 * @return the previous value of the given {@link XValId} in this
	 *         {@link ValueMap}.
	 */
	@SuppressWarnings("unchecked")
	public <V> V set(final XValId<V> valueId, final V value) {
		return (V) map.put(valueId, value);
	}

	/**
	 * Sets the all.
	 *
	 * @param map the new all
	 */
	public void setAll(final ValueMap map) {
		for (XValId<?> id : map.map.keySet()) {
			this.map.put(id, map.map.get(id));
		}
	}

	/**
	 * Gets the set of {@link XValId}s used in this {@link ValueMap}.
	 * 
	 * @return the set of {@link XValId}s used in this {@link ValueMap}.
	 */
	public Set<XValId<?>> keySet() {
		return map.keySet();
	}

	/**
	 * Gets the number of values kept in this {@link ValueMap}.
	 * 
	 * @return the number of values kept in this {@link ValueMap}.
	 */
	public int size() {
		return map.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return TEXT_FORMAT.format(this).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && obj instanceof ValueMap) {
			ValueMap that = (ValueMap) obj;
			return new EqualsBuilder()//
					.append(this.map, that.map)//
					.isEquals();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(ValueMap that) {
		if (that == null) {
			return Integer.MAX_VALUE;
		}
		if (this.keySet().equals(that.keySet())) {
			CompareToBuilder ctb = new CompareToBuilder();
			List<XValId<?>> ids = new ArrayList<XValId<?>>(this.keySet());
			Collections.sort(ids);
			for (@SuppressWarnings("rawtypes")
			XValId id : ids) {
				ctb.append(this.get(id), that.get(id));
			}
			return ctb.toComparison();
		} else {
			List<XValId<?>> thisIds = new ArrayList<XValId<?>>(this.keySet());
			List<XValId<?>> thatIds = new ArrayList<XValId<?>>(that.keySet());
			Collections.sort(thisIds);
			Collections.sort(thatIds);
			return new CompareToBuilder()//
					.append(thisIds.toArray(new XValId<?>[0]), thatIds.toArray(new XValId<?>[0]))//
					.toComparison();
		}
	}

	/**
	 * Gets the unmodifiable value map.
	 *
	 * @return the unmodifiable value map
	 */
	public ValueMap getUnmodifiableValueMap() {
		return new UnmodifiableValueMap(this);
	}

	/**
	 * The Class UnmodifiableValueMap.
	 */
	private static final class UnmodifiableValueMap extends ValueMap {
		public UnmodifiableValueMap(ValueMap map) {
			super(map);
		}

		@Override
		public Set<XValId<?>> keySet() {
			return Collections.unmodifiableSet(super.keySet());
		}

		@Override
		public <V> V set(XValId<V> valueId, V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setAll(ValueMap map) {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * The Class ValueMapTextFormat.
	 */
	private static final class ValueMapTextFormat extends TextFormat<ValueMap> {
		private ValueMapTextFormat() {
			super(ValueMap.class);
			setInstance(this, ValueMap.class);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Appendable format(ValueMap map, Appendable appendable) throws IOException {
			JSONObject jsonObject = new JSONObject();
			for (XValId<?> id : map.map.keySet()) {
				switch (ValueType.getValueType(id.getType())) {
					case XID:
						jsonObject.put(id, map.map.get(id).toString());
						break;
					default:
						jsonObject.put(id, map.map.get(id));
						break;
				}
			}
			return appendable.append(jsonObject.toJSONString());
		}

		@Override
		public ValueMap parse(CharSequence chars, Cursor cursor) throws IllegalArgumentException {
			int counter = 1;
			if (cursor.skip('{', chars)) {
				do {
					switch (cursor.nextChar(chars)) {
						case '{':
							counter++;
							break;
						case '}':
							counter--;
							break;
						default:
							break;
					}
				} while (!cursor.atEnd(chars) && counter > 0);
				String s = chars.subSequence(0, cursor.getIndex()).toString();
				try {
					JSONObject jsonObject = (JSONObject) new JSONParser().parse(s);
					return new ValueMap(jsonObject);
				} catch (ParseException e) {}
			}
			throw new IllegalArgumentException();
		}
	}

	static {
		new ValueMapXmlFormat<ValueMap>(ValueMap.class) {
			@Override
			protected ValueMap newInstance(ReadAttributes readAttributes, ReadElements readElements) throws XMLStreamException {
				ValueMap valueMap = new ValueMap();
				List<XMLValue> values = readElements.getValues(VALUE_ELEMENT);
				if (values != null) {
					for (XMLValue value : values) {
						valueMap.map.put(value.id, value.value);
					}
				}
				return valueMap;
			}
		};
	}

	/**
	 * The Class ValueMapXmlFormat.
	 *
	 * @param <V> the value type
	 */
	protected abstract static class ValueMapXmlFormat<V extends ValueMap> extends XmlFormat<V> {
		protected static final Element<XMLValue>	VALUE_ELEMENT	= XmlUnit.newElement("entry", XMLValue.class);

		protected ValueMapXmlFormat(Class<V> cls) {
			super(cls);
			addRecognized(VALUE_ELEMENT);
		}

		@Override
		protected void writeAttributes(V valueMap, OutputElement xml) throws XMLStreamException {
			// nothing
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void writeElements(V valueMap, OutputElement xml) throws XMLStreamException {
			List<XValId<?>> ids = new ArrayList<XValId<?>>(valueMap.keySet());
			Collections.sort(ids);
			for (@SuppressWarnings("rawtypes")
			XValId id : ids) {
				XMLValue value = new XMLValue();
				value.id = id;
				value.value = valueMap.get(id);
				VALUE_ELEMENT.write(xml, value);
			}
		}

		protected static final class XMLValue {
			public XValId<?>	id		= null;
			public Object		value	= null;
			static {
				new XMLFormat<XMLValue>(XMLValue.class) {
					@Override
					public XMLValue newInstance(Class<XMLValue> arg0, javolution.xml.XMLFormat.InputElement arg1) throws XMLStreamException {
						return new XMLValue();
					}

					@Override
					public void read(javolution.xml.XMLFormat.InputElement xml, XMLValue val) throws XMLStreamException {
						val.id = xml.getAttribute("name", XValId.newId("id", String.class));
						if (String.class.equals(val.id.getType())) {
							val.value = xml.getAttribute("value").toString();
						} else {
							val.value = xml.getAttribute("value", ValueType.getValueType(val.id.getType()).getDefaultValue());
						}
					}

					@Override
					public void write(XMLValue val, javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
						xml.setAttribute("name", val.id);
						xml.setAttribute("value", val.value);
					}
				};
			}
		}
	}
}
