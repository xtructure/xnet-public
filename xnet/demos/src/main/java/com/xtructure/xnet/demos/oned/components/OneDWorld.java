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

import com.xtructure.xbatch.world.World;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * A 1-D demo world.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface OneDWorld extends World<StandardTimePhase> {
	/*
	 * --- critter sources
	 */

	/**
	 * Returns the id of the movement feedback.
	 * 
	 * @return the id of the movement feedback
	 */
	XId getMovementFeedbackId();

	/**
	 * Returns the id of the food smell.
	 * 
	 * @return the id of the food smell
	 */
	XId getFoodSmellId();

	/*
	 * --- viz sources
	 */

	/**
	 * Returns the id of the critter location.
	 * 
	 * @return the id of the critter location
	 */
	XId getCritterLocationId();

	/**
	 * Returns the id of the food location.
	 * 
	 * @return the id of the food location
	 */
	XId getFoodLocationId();

	/*
	 * --- fitness sources
	 */

	/**
	 * Gets the critter movement id.
	 *
	 * @return the id of the critter movement
	 */
	XId getCritterMovementId();

	/*
	 * --- critter targets
	 */

	/**
	 * Returns the id of the positive movement stimulus.
	 * 
	 * @return the id of the positive movement stimulus
	 */
	XId getMovementStimulusId();

}
