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
package com.xtructure.xnet.demos.art;

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.xtructure.art.model.node.Node;
import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.impl.AbstractStandardXComponent;
import com.xtructure.xsim.impl.AbstractStandardXSimulation;
import com.xtructure.xutil.id.XId;

/**
 * The Class NodeTracer.
 * 
 * @author Luis Guimbarda
 */
public class NodeTracer extends AbstractStandardXComponent {

	/** The EXPECTED values. */
	private final List<Float> EXPECTED_VALUES;

	/** The ITERATOR. */
	private final Iterator<Float> ITERATOR;

	/** The EPSILON. */
	private final float EPSILON;

	/** The SIM. */
	private final AbstractStandardXSimulation SIM;

	/**
	 * Gets the single instance of NodeTracer.
	 * 
	 * @param id
	 *            the id
	 * @param sim
	 *            the sim
	 * @param node
	 *            the node
	 * @param expectedValues
	 *            the expected values
	 * @param epsilon
	 *            the epsilon
	 * @return single instance of NodeTracer
	 */
	public static NodeTracer getInstance(XId id,
			AbstractStandardXSimulation sim, Node node,
			List<Float> expectedValues, Float epsilon) {
		return new NodeTracer(id, sim, Collections.singleton(node.getId()),
				expectedValues, epsilon);
	}

	/**
	 * Instantiates a new node tracer.
	 * 
	 * @param id
	 *            the id
	 * @param sim
	 *            the sim
	 * @param targetIds
	 *            the target ids
	 * @param expectedValues
	 *            the expected values
	 * @param epsilon
	 *            the epsilon
	 */
	private NodeTracer(XId id, AbstractStandardXSimulation sim,
			Set<XId> targetIds, List<Float> expectedValues, Float epsilon) {
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
	protected void addForeignData(XId targetId, XAddress sourceAddress,
			Object data) {
		if (ITERATOR.hasNext()) {
			Float backEnergy = ((Node.Energies) data).getBackEnergy();
			Float expected = ITERATOR.next();
			// System.out.println(String.format("Tracer %s => Checking %s at time %s: expected(%f) seen(%f)",
			// getId(), sourceAddress.getPartId(), SIM.getTime(), backEnergy,
			// expected));
			validateState(sourceAddress.getPartId().toString(), backEnergy,
					isGreaterThanOrEqualTo(expected - EPSILON),
					isLessThanOrEqualTo(expected + EPSILON));
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
