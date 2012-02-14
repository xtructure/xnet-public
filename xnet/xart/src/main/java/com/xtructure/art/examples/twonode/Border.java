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

import java.util.Map;

import com.xtructure.art.model.network.AbstractNetwork;
import com.xtructure.art.model.node.Node;
import com.xtructure.xsim.impl.AbstractXBorder;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
public final class Border extends AbstractXBorder {

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
