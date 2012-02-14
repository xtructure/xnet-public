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
package com.xtructure.xevolution.tool;

import org.json.simple.JSONAware;

import com.xtructure.xutil.id.XIdObject;
import com.xtructure.xutil.id.XIdObjectManager;

/**
 * The Interface DataXIdObject.
 *
 * @param <D> the generic type
 * @author Luis Guimbarda
 */
public interface DataXIdObject<D extends DataXIdObject<D>> extends XIdObject {
	/**
	 * Indicates that this {@link DataXIdObject} is present an associated data
	 * file.
	 * 
	 * @return true if this is in the data file, false otherwise
	 */
	public boolean isWritten();

	/**
	 * Sets the {@link #isWritten()} indicator.
	 *
	 * @param written the new written
	 */
	public void setWritten(boolean written);

	/**
	 * Returns a {@link JSONAware} representation of this {@link DataXIdObject}.
	 * 
	 * @return a {@link JSONAware} representation of this {@link DataXIdObject}.
	 */
	public JSONAware toJSON();

	/**
	 * A factory for creating DataXIdObject objects.
	 *
	 * @param <D> the generic type
	 * {@link DataXIdObject} object factory interface
	 */
	public interface DataXIdObjectFactory<D extends DataXIdObject<D>> {
		
		/**
		 * Creates instances of {@link DataXIdObject}.
		 *
		 * @param json the json
		 * @param manager the manager
		 * @return the D
		 */
		public D createInstance(JSONAware json, XIdObjectManager<D> manager);
	}
}
