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

import com.xtructure.art.model.network.Network;
import com.xtructure.art.model.node.Node;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * A 1-D demo critter.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * 
 * @version 0.9.6
 */
public interface OneDCritter extends Network {
	/*
	 * sensor/input ids
	 */
	/** The Constant CLOCKWISE_UTRICLE_ID. */
	public static final XId CLOCKWISE_UTRICLE_ID = XId.newId("c-utricle");

	/** The Constant COUNTER_CLOCKWISE_UTRICLE_ID. */
	public static final XId COUNTER_CLOCKWISE_UTRICLE_ID = XId
			.newId("cc-utricle");

	/** The Constant NOSE_ID. */
	public static final XId NOSE_ID = XId.newId("nose");
	/*
	 * actuator/output ids
	 */
	/** The Constant CLOCKWISE_FOOT_ID. */
	public static final XId CLOCKWISE_FOOT_ID = XId.newId("c-foot");

	/** The Constant COUNTER_CLOCKWISE_FOOT_ID. */
	public static final XId COUNTER_CLOCKWISE_FOOT_ID = XId.newId("cc-foot");

	/** The Constant CLOCKWISE_FOOT_ENERGY_ID. */
	public static final XId CLOCKWISE_FOOT_ENERGY_ID = Node.Fragment.ENERGIES
			.generateExtendedId(CLOCKWISE_FOOT_ID);

	/** The Constant COUNTER_CLOCKWISE_FOOT_ENERGY_ID. */
	public static final XId COUNTER_CLOCKWISE_FOOT_ENERGY_ID = Node.Fragment.ENERGIES
			.generateExtendedId(COUNTER_CLOCKWISE_FOOT_ID);

	/**
	 * Returns the id of the nose cell.
	 * 
	 * @return the id of the nose cell
	 */
	XId getNoseId();

	/**
	 * Returns the id of the counter clockwise utricle cell.
	 * 
	 * @return the id of the counter clockwise utricle cell
	 */
	XId getCounterClockwiseUtricleId();

	/**
	 * Returns the id of the clockwise utricle cell.
	 * 
	 * @return the id of the clockwise utricle cell
	 */
	XId getClockwiseUtricleId();

	/*
	 * --- world targets
	 */

	/**
	 * Returns the id of the counter clockwise foot cell.
	 * 
	 * @return the id of the counter clockwise foot cell
	 */
	XId getCounterClockwiseFootId();

	/**
	 * Returns the id of the clockwise foot cell.
	 * 
	 * @return the id of the clockwise foot cell
	 */
	XId getClockwiseFootId();
}
