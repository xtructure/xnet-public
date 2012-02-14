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
package com.xtructure.xevolution.tool.data;

import static com.xtructure.xutil.valid.ValidateUtils.containsKey;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isOfExactType;
import static com.xtructure.xutil.valid.ValidateUtils.not;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.xtructure.xevolution.tool.DataXIdObject;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;

/**
 * The Class AbstractDataXIdObject.
 *
 * @param <D> the generic type
 * @author Luis Guimbarda
 */
public abstract class AbstractDataXIdObject<D extends AbstractDataXIdObject<D>> extends AbstractXIdObject implements DataXIdObject<D> {
	
	/**
	 * Extracts the id for the {@link DataXIdObject} from the given json.
	 *
	 * @param json the json
	 * @return the XId
	 */
	protected static XId extractId(Object json) {
		validateArg("json", json, isOfExactType(JSONArray.class), hasSize(2));
		String idString = ((JSONArray) json).get(0).toString();
		return XId.TEXT_FORMAT.parse(idString);
	}

	/** indicates whether the data has been written. */
	private boolean				written;
	
	/** contains the data. */
	private final JSONObject	jsonObject	= new JSONObject();

	/**
	 * Creates a new {@link AbstractDataXIdObject}. The created data is
	 * considered unwritten.
	 * 
	 * @param id
	 *            id of this {@link DataXIdObject}
	 * @param manager
	 *            the manager with which the data is to be registered
	 * @param observed
	 *            the object from which the data is derived
	 */
	protected AbstractDataXIdObject(XId id, XIdObjectManager<D> manager, Object... observed) {
		super(id, manager);
		this.written = false;
		processObserved(manager, observed);
	}

	/**
	 * Creates a new {@link AbstractDataXIdObject}. The created data is
	 * considered written.
	 * 
	 * @param json
	 *            a {@link JSONAware} representation of the data
	 * @param manager
	 *            the manager with which the data is to be registered
	 */
	@SuppressWarnings("unchecked")
	protected AbstractDataXIdObject(JSONAware json, XIdObjectManager<D> manager) {
		super(extractId(json), manager);
		this.written = true;
		jsonObject.putAll((JSONObject) ((JSONArray) json).get(1));
	}

	/**
	 * Process the observed data. Called by
	 *
	 * @param manager the manager
	 * @param observed the data to process.
	 * {@link #AbstractDataXIdObject(XId, XIdObjectManager, Object...)}.
	 */
	protected abstract void processObserved(XIdObjectManager<D> manager, Object... observed);

	/**
	 * Adds the given key/value pair to this {@link DataXIdObject}.
	 *
	 * @param key data key
	 * @param value data value
	 * @throws IllegalArgumentException if data has already been added with the given key
	 */
	@SuppressWarnings("unchecked")
	protected final void put(Object key, Object value) throws IllegalArgumentException {
		validateArg(String.format("%s doesn't alread have %s", getId(), key), jsonObject, not(containsKey(key)));
		jsonObject.put(key, value);
	}

	/**
	 * Returns the data value in this {@link DataXIdObject} with the given key.
	 * 
	 * @param key
	 *            data key
	 * @return the data value in this {@link DataXIdObject} with the given key.
	 */
	public final Object get(Object key) {
		return jsonObject.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.tool.DataXIdObject#isWritten()
	 */
	@Override
	public final boolean isWritten() {
		return written;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.tool.DataXIdObject#setWritten(boolean)
	 */
	@Override
	public final void setWritten(boolean written) {
		this.written = written;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.tool.DataXIdObject#toJSON()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final JSONAware toJSON() {
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(getId().toString());
		jsonArray.add(jsonObject);
		return jsonArray;
	}
}
