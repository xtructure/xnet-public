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
package com.xtructure.xnet.demos.oned.components;

import com.xtructure.xbatch.world.Fitness;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * The Interface OneDFitness.
 *
 * @author Luis Guimbarda
 */
public interface OneDFitness extends Fitness<StandardTimePhase> {
	
	/** Target id for the catch observation. */
	public static final XId	OBSERVED_CATCH_ID				= XId.newId("fitnessObservedCatch");
	
	/** Target id for the distance traveled observation. */
	public static final XId	OBSERVED_DISTANCE_ID			= XId.newId("fitnessObservedDistance");
	
	/** The Constant OBSERVED_FOOD_LOCATION_ID. */
	public static final XId	OBSERVED_FOOD_LOCATION_ID		= XId.newId("fitnessFoodLocation");
	
	/** The Constant OBSERVED_CRITTER_LOCATION_ID. */
	public static final XId	OBSERVED_CRITTER_LOCATION_ID	= XId.newId("fitnessCritterLocation");

}
