/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.xtructure.xnet.demos.oned;

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xnet.demos.oned.components.OneDCritter;
import com.xtructure.xnet.demos.oned.components.OneDWorld;
import com.xtructure.xnet.demos.oned.components.OnedWorldImpl;
import com.xtructure.xutil.id.XValId;

/**
 * The Interface OneDEvaluationStrategy.
 * 
 * @param <D>
 *            the generic type
 * @author Luis Guimbarda
 */
public interface OneDEvaluationStrategy<D> extends
		EvaluationStrategy<D, OneDCritter> {

	/** The Constant DIRECTION_CHANGE_MEASURE_ID. */
	public static final XValId<Double> DIRECTION_CHANGE_MEASURE_ID = XValId
			.newId("directionChangeCount", Double.class);

	/** The Constant DISTANCE_AWAY_FROM_MEASURE_ID. */
	public static final XValId<Double> DISTANCE_AWAY_FROM_MEASURE_ID = XValId
			.newId("distanceMovedAwayFromFood", Double.class);

	/** The Constant DISTANCE_TOWARDS_MEASURE_ID. */
	public static final XValId<Double> DISTANCE_TOWARDS_MEASURE_ID = XValId
			.newId("distanceMovedTowardsFood", Double.class);

	/** The Constant HORIZON_CROSSING_MEASURE_ID. */
	public static final XValId<Double> HORIZON_CROSSING_MEASURE_ID = XValId
			.newId("horizonCrossingCount", Double.class);

	/** The Constant TIME_AT_REST_MEASURE_ID. */
	public static final XValId<Double> TIME_AT_REST_MEASURE_ID = XValId.newId(
			"timeAtRest", Double.class);

	/** The Constant TIME_AT_SPEED_MEASURE_ID. */
	public static final XValId<Double> TIME_AT_SPEED_MEASURE_ID = XValId.newId(
			"timeAtSpeed", Double.class);

	/** The Constant WORLD. */
	public static final OneDWorld WORLD = OnedWorldImpl.getInstance();

	/**
	 * Gets the simulation length.
	 * 
	 * @return the simulation length
	 */
	public Long getSimulationLength();

	/**
	 * Gets the simulation count.
	 * 
	 * @return the simulation count
	 */
	public Integer getSimulationCount();
}
