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
package com.xtructure.xutil.coll;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;

/**
 * Transform from {@link XIdObject}s to their {@link XId}s
 * 
 * @author Luis Guimbarda
 * 
 */
public class XIdObjectTransform implements Transform<XId, XIdObject> {
	/** singleton instance for {@link XIdObjectTransform} */
	private static final XIdObjectTransform	INSTANCE	= new XIdObjectTransform();

	/**
	 * Returns the singleton instance of {@link XIdObjectTransform}
	 * 
	 * @return the singleton instance of {@link XIdObjectTransform}
	 */
	public static XIdObjectTransform getInstance() {
		return INSTANCE;
	}

	/**
	 * Creates a new {@link XIdObjectTransform}
	 */
	private XIdObjectTransform() {}

	/**
	 * Returns the id of the given {@link XIdObject}
	 * 
	 * @return the id of the given {@link XIdObject}, or null of the given
	 *         object is null
	 */
	@Override
	public XId transform(XIdObject i) {
		if (i == null) {
			return null;
		}
		return i.getId();
	}
}
