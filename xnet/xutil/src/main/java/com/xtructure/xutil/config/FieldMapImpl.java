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

import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.coll.Transform;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

public class FieldMapImpl implements FieldMap {
	/** The id of the configuration of this FieldMap. */
	private final XId					configurationId;
	/** The fields in this FieldMap. */
	private final Map<XId, XField<?>>	fields;

	/**
	 * @param configuration
	 */
	public FieldMapImpl(XConfiguration configuration) {
		this.configurationId = configuration.getId();
		List<XField<?>> fieldList = new ArrayList<XField<?>>();
		for (XId parameterId : configuration.getParameterIds()) {
			XParameter<?> parameter = configuration.getParameter(parameterId);
			XField<?> field = parameter.newField();
			fieldList.add(field);
		}
		this.fields = new MapBuilder<XId, XField<?>>()//
				.putAll(new Transform<XId, XField<?>>() {
					@Override
					public XId transform(XField<?> in) {
						validateArg("value", in, isNotNull());
						return in.getParameter().getId();
					}
				}, fieldList)//
				.newImmutableInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xutil.config.FieldMap#getField(com.xtructure.xutil.id.XId)
	 */
	@Override
	public XField<?> getField(XId id) {
		return fields.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xutil.config.FieldMap#set(com.xtructure.xutil.id.XId,
	 * java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <V> V set(XId id, V v) {
		validateArg("id", id, isNotNull());
		validateArg("fields", fields.keySet(), containsElement(id));
		return ((XField<V>) fields.get(id)).setValue(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xutil.config.FieldMap#setAll(com.xtructure.xutil.config
	 * .FieldMap)
	 */
	@Override
	public void setAll(FieldMap fieldMap) {
		for (XId fieldId : fieldMap.getFieldIds()) {
			set(fieldId, fieldMap.get(fieldId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xutil.config.FieldMap#get(com.xtructure.xutil.id.XId)
	 */
	@Override
	public Object get(XId id) {
		validateArg("id", id, isNotNull());
		validateArg("fields", fields.keySet(), containsElement(id));
		return fields.get(id).getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xutil.config.FieldMap#get(com.xtructure.xutil.id.ValueId)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(XValId<V> id) {
		validateArg("id", id, isNotNull());
		validateArg("fields", fields.keySet(), containsElement(id));
		return (V) fields.get(id).getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xutil.config.FieldMap#getFieldIds()
	 */
	@Override
	public Set<XId> getFieldIds() {
		return Collections.unmodifiableSet(fields.keySet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xutil.config.FieldMap#getConfigurationId()
	 */
	@Override
	public XId getConfigurationId() {
		return configurationId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return fields.values().toString();
	}
}
