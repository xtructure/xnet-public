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

import static com.xtructure.xutil.valid.ValidateUtils.hasValues;
import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThan;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.valid.Condition;

/**
 * The Class DataXTerminator.
 *
 * @author Luis Guimbarda
 */
public class DataXTerminator extends AbstractStandardXTerminator {

	/**
	 * Gets the single instance of DataXTerminator.
	 *
	 * @param terminatorId the terminator id
	 * @param conditionMap the condition map
	 * @return single instance of DataXTerminator
	 */
	public static DataXTerminator getInstance(XId terminatorId, Map<XId, Condition> conditionMap) {
		validateArg("conditionMap", conditionMap, isNotNull());
		validateArg("conditionMap", conditionMap.size(), isGreaterThan(0));
		validateArg("conditionMap", conditionMap, hasValues(-1, isNotNull()));
		return new DataXTerminator(terminatorId, conditionMap.keySet(), conditionMap);
	}

	/**
	 * Gets the single instance of DataXTerminator.
	 *
	 * @param terminatorId the terminator id
	 * @param dataId the data id
	 * @param condition the condition
	 * @return single instance of DataXTerminator
	 */
	public static DataXTerminator getInstance(XId terminatorId, XId dataId, Condition condition) {
		validateArg("dataId", dataId, isNotNull());
		validateArg("condition", condition, isNotNull());
		Map<XId, Condition> conditionMap = new HashMap<XId, Condition>();
		conditionMap.put(dataId, condition);
		return new DataXTerminator(terminatorId, conditionMap.keySet(), conditionMap);
	}

	/**
	 * Instantiates a new data x terminator.
	 *
	 * @param id the id
	 * @param sourceIds the source ids
	 * @param conditionMap the condition map
	 */
	protected DataXTerminator(XId id, Set<XId> sourceIds, Map<XId, Condition> conditionMap) {
		super(id, sourceIds, conditionMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.XComponent#getData(com.xtructure.xutil.id.XId)
	 */
	@Override
	public Object getData(XId partId) {
		if (satisfiedMap.containsKey(partId)) {
			return satisfiedMap.get(partId);
		}
		return null;
	}

}
