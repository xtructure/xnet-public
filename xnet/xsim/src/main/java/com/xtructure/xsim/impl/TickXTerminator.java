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

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThan;
import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.HashMap;
import java.util.Map;

import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.valid.Condition;

/**
 * @author Luis Guimbarda
 * 
 */
public final class TickXTerminator extends AbstractStandardXTerminator {
	/** Source id for the tick bound */
	public static final XId	TICK_BOUND_ID;
	/** Target id for the tick bound terminal condition */
	public static final XId	TICK_BOUND_CONDITION_ID;

	static {
		TICK_BOUND_ID = XId.newId("tick bound");
		TICK_BOUND_CONDITION_ID = XId.newId("tick bound condition");
	}

	/**
	 * @param id
	 *            the id of this terminator component
	 * @param tickBound
	 *            the max number of ticks the containing simulation should run
	 *            before finishing (>0)
	 * @return a new {@link TickXTerminator} with the given bound
	 */
	public static TickXTerminator getInstance(XId id, AbstractStandardXSimulation sim, long tickBound) {
		validateArg("tickBound", tickBound, isGreaterThan(0l));
		Map<XId, Condition> conditionMap = new HashMap<XId, Condition>();
		conditionMap.put(TICK_BOUND_CONDITION_ID, isGreaterThanOrEqualTo(tickBound));
		return new TickXTerminator(id, sim, conditionMap, tickBound);
	}

	/**
	 * the max number of ticks the containing simulation should run before
	 * finishing
	 */
	private final long							tickBound;
	/** the simulation containing this terminator component */
	private final AbstractStandardXSimulation	sim;

	/**
	 * Creates a new {@link TickXTerminator}.
	 * 
	 * @param id
	 *            the id of this terminator component
	 * @param conditionMap
	 *            a map to the terminal conditions of this terminator component
	 *            from their targetIds
	 * @param tickBound
	 *            the max number of ticks the containing simulation should run
	 *            before finishing
	 */
	protected TickXTerminator(XId id, AbstractStandardXSimulation sim, Map<XId, Condition> conditionMap, long tickBound) {
		super(id, new SetBuilder<XId>().add(TICK_BOUND_ID).newImmutableInstance(), conditionMap);
		this.tickBound = tickBound;
		this.sim = sim;
	}

	/**
	 * @return the tickBound
	 */
	public long getTickBound() {
		return tickBound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.impl.AbstractStandardXComponent#prepare()
	 */
	@Override
	protected void cleanUp() {
		super.cleanUp();
		addForeignData(TICK_BOUND_CONDITION_ID, null, sim.getTime().getTick());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.XComponent#getData(com.xtructure.xutil.id.XId)
	 */
	@Override
	public Object getData(XId partId) {
		if (TICK_BOUND_ID.equals(partId)) {
			return tickBound;
		}
		return null;
	}

}
