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
import com.xtructure.xsim.XTerminator;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.valid.Condition;

import static com.xtructure.xutil.valid.ValidateUtils.*;

/**
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractStandardXTerminator //
		extends AbstractStandardXComponent //
		implements XTerminator<StandardXClock.StandardTimePhase> {
	/** A map to conditions from their targetIds */
	private final Map<XId, Condition>	conditionMap;
	/** A map to satisfied conditions from their targetIds */
	protected final Map<XId, Boolean>	satisfiedMap;

	/**
	 * Creates a new standard terminator component.
	 * 
	 * @param id
	 *            the id of this terminator component
	 * @param conditionMap
	 *            a map to the terminal conditions of this terminator component
	 *            from their targetIds
	 */
	protected AbstractStandardXTerminator(XId id, Map<XId, Condition> conditionMap) {
		this(id, null, conditionMap);
	}

	/**
	 * Creates a new standard terminator component.
	 * 
	 * @param id
	 *            the id of this terminator component
	 * @param sourceIds
	 *            the set of source ids in this terminator component
	 * @param conditionMap
	 *            a map to the terminal conditions of this terminator component
	 *            from their targetIds
	 */
	protected AbstractStandardXTerminator(XId id, Set<XId> sourceIds, Map<XId, Condition> conditionMap) {
		super(id, sourceIds, conditionMap.keySet());
		this.conditionMap = new HashMap<XId, Condition>();
		this.conditionMap.putAll(conditionMap);
		this.satisfiedMap = new HashMap<XId, Boolean>();
		for (XId xid : conditionMap.keySet()) {
			satisfiedMap.put(xid, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.impl.AbstractStandardXComponent#prepare()
	 */
	@Override
	protected void prepare() {
	// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.impl.AbstractStandardXComponent#cleanUp()
	 */
	@Override
	protected void cleanUp() {
		retrieveForeignData();
	}

	@Override
	protected void addForeignData(XId targetId, XAddress sourceAddress, Object data) {
		Condition terminalCondition = conditionMap.get(targetId);
		validateState("terminalCondition", terminalCondition, isNotNull());
		satisfiedMap.put(targetId, terminalCondition.isSatisfiedBy(data));
	}

	@Override
	public boolean terminalConditionReached() {
		for (XId id : satisfiedMap.keySet()) {
			if (satisfiedMap.get(id)) {
				return true;
			}
		}
		return false;
	}
}
