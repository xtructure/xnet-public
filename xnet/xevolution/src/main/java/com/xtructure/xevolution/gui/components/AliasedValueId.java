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
package com.xtructure.xevolution.gui.components;

import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.id.XId;

/**
 * {@link AliasedValueId} provides an alternate {@link #toString()} for
 * {@link XValId}s.
 * 
 * @author Luis Guimbarda
 * 
 */
public class AliasedValueId {
	/** the aliased {@link XValId} */
	private final XValId<?>	id;

	/**
	 * Creates a new {@link AliasedValueId}
	 * 
	 * @param id
	 *            the {@link XValId} to alias
	 */
	public AliasedValueId(XValId<?> id) {
		this.id = id;
	}

	/**
	 * Returns the aliased {@link XValId}
	 * 
	 * @return the aliased {@link XValId}
	 */
	public XValId<?> getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return XId.TEXT_FORMAT.format(id).toString();
	}
}
