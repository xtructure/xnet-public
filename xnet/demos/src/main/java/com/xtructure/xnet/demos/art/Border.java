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

import java.util.Map;

import com.xtructure.art.model.network.AbstractNetwork;
import com.xtructure.art.model.node.Node;
import com.xtructure.xsim.impl.AbstractXBorder;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.id.XId;

/**
 * The Class Border.
 * 
 * @author Luis Guimbarda
 */
public final class Border extends AbstractXBorder {

	/**
	 * Instantiates a new border.
	 * 
	 * @param producer
	 *            the producer
	 * @param consumer
	 *            the consumer
	 * @param outputInputMap
	 *            the output input map
	 */
	public Border(//
			AbstractNetwork producer,//
			AbstractNetwork consumer, //
			Map<XId, XId> outputInputMap) {
		super();
		Transform transform = new Transform() {
			@Override
			public Object transform(Object orig) {
				Node outputSource = (Node) orig;
				return outputSource.getEnergies().getFrontEnergy();
			}
		};

		for (Object key : outputInputMap.keySet()) {
			XId outputSourceId = (XId) key;
			XId inputTargetId = outputInputMap.get(key);

			associate(//
					new XAddressImpl(producer, outputSourceId),//
					transform,//
					new XAddressImpl(consumer, inputTargetId));
		}
		producer.addBorder(this);
		consumer.addBorder(this);
	}
}
