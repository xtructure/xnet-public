/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.network.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.xtructure.xutil.RandomUtil;

/**
 * @author Luis Guimbarda
 */
@Test(groups = { "unit:xneat" })
public final class UTestConnection {
	private int			sourceNeuronIndex, targetNeuronIndex;
	private double		weight;
	private Connection	connection;

	@BeforeMethod
	public void setUp() {
		sourceNeuronIndex = RandomUtil.nextInteger();
		targetNeuronIndex = RandomUtil.nextInteger();
		weight = RandomUtil.nextDouble();
		connection = new Connection(sourceNeuronIndex, targetNeuronIndex, weight);
	}

	public void constructorSucceeds() {
		assertThat("",//
				connection, isNotNull());
	}

	public void getSourceNeuronIndexReturnsExpectedInt() {
		assertThat("",//
				connection.getSourceNeuronIndex(), isEqualTo(sourceNeuronIndex));
	}

	public void getTargetNeuronIndexReturnsExpectedInt() {
		assertThat("",//        
				connection.getTargetNeuronIndex(), isEqualTo(targetNeuronIndex));
	}

	public void getWeightReturnsExpectedDouble() {
		assertThat("",//
				connection.getWeight(), isEqualTo(weight));
	}

}
