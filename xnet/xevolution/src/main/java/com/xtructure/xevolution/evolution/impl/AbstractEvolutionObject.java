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
package com.xtructure.xevolution.evolution.impl;

import com.xtructure.xevolution.evolution.EvolutionObject;
import com.xtructure.xutil.XLogger;

/**
 * {@link AbstractEvolutionObject} implements the {@link EvolutionObject}
 * interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractEvolutionObject implements EvolutionObject {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.EvolutionObject#getLogger()
	 */
	@Override
	public final XLogger getLogger() {
		return XLogger.getInstance(getClass());
	}
}
