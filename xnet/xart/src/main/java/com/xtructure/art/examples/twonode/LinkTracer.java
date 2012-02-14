/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.examples.twonode;

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.xtructure.art.model.link.Link;
import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.impl.AbstractStandardXComponent;
import com.xtructure.xsim.impl.AbstractStandardXSimulation;
import com.xtructure.xutil.id.XId;

/**
 * The Class LinkTracer.
 *
 * @author Luis Guimbarda
 */
public class LinkTracer extends AbstractStandardXComponent {
	
	/** The EXPECTED values. */
	private final List<Float>					EXPECTED_VALUES;
	
	/** The ITERATOR. */
	private final Iterator<Float>				ITERATOR;
	
	/** The EPSILON. */
	private final float							EPSILON;
	
	/** The SIM. */
	private final AbstractStandardXSimulation	SIM;

	/**
	 * Gets the single instance of LinkTracer.
	 *
	 * @param id the id
	 * @param sim the sim
	 * @param link the link
	 * @param expectedValues the expected values
	 * @param epsilon the epsilon
	 * @return single instance of LinkTracer
	 */
	public static LinkTracer getInstance(XId id, AbstractStandardXSimulation sim, Link link, List<Float> expectedValues, Float epsilon) {
		return new LinkTracer(id, sim, Collections.singleton(link.getId()), expectedValues, epsilon);
	}

	/**
	 * Instantiates a new link tracer.
	 *
	 * @param id the id
	 * @param sim the sim
	 * @param targetIds the target ids
	 * @param expectedValues the expected values
	 * @param epsilon the epsilon
	 */
	private LinkTracer(XId id, AbstractStandardXSimulation sim, Set<XId> targetIds, List<Float> expectedValues, Float epsilon) {
		super(id, null, targetIds);
		EXPECTED_VALUES = new ArrayList<Float>();
		EXPECTED_VALUES.addAll(expectedValues);
		ITERATOR = EXPECTED_VALUES.iterator();
		EPSILON = epsilon == null ? 0.0f : epsilon;
		SIM = sim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xsim.impl.AbstractStandardXComponent#addForeignData(com
	 * .xtructure.xutil.id.XId, com.xtructure.xsim.XAddress, java.lang.Object)
	 */
	@Override
	protected void addForeignData(XId targetId, XAddress sourceAddress, Object data) {
		if (ITERATOR.hasNext()) {
			Float expected = ITERATOR.next();
			// System.out.println(String.format("Tracer %s => Checking %s at time %s: expected(%f) seen(%f)", getId(), sourceAddress.getPartId(), SIM.getTime(), data, expected));
			validateState(sourceAddress.getPartId().toString(), data, isGreaterThanOrEqualTo(expected - EPSILON), isLessThanOrEqualTo(expected + EPSILON));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.XComponent#getData(com.xtructure.xutil.id.XId)
	 */
	@Override
	public Object getData(XId partId) {
		return null;
	}

	/**
	 * Gets the sim.
	 *
	 * @return the sim
	 */
	public AbstractStandardXSimulation getSIM() {
		return SIM;
	}
}
