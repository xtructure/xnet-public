/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xbatch.
 *
 * xbatch is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xbatch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xbatch.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xbatch.world;

import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XTime;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 * @param <F>
 */
public interface World<F extends XTime.Phase<F>> extends XComponent<F> {
	public interface WorldFactory<F extends XTime.Phase<F>> {
		World<F> newInstance(XId id);
	}
}
