/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xsim.
 *
 * xsim is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xsim is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xsim.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xsim.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XReporter;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractStandardXReporter //
		extends AbstractStandardXComponent //
		implements XReporter<StandardTimePhase> {

	/** */
	protected final Map<XId, Object>	observations;
	/** */
	protected final Map<XId, Object>	statistics;

	/**
	 * @param id
	 *            the id for this XReporter component
	 * @param sourceIds
	 *            the ids for statistics reported by this XReporter component
	 * @param targetIds
	 *            the ids for events this XReporter component observes
	 */
	protected AbstractStandardXReporter(XId id, Set<XId> sourceIds, Set<XId> targetIds) {
		super(id, sourceIds, targetIds);
		this.observations = new HashMap<XId, Object>();
		this.statistics = new HashMap<XId, Object>();
	}

	@Override
	protected void prepare() {
		observations.clear();
		super.prepare();
	}

	@Override
	protected void addForeignData(XId targetId, XAddress sourceAddress, Object data) {
		observations.put(targetId, data);
	}

	@Override
	public Object getData(XId partId) {
		if (statistics.containsKey(partId)) {
			return statistics.get(partId);
		}
		return null;
	}
}
