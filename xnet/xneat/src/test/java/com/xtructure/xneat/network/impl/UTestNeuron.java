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

import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.node.NodeType;

/**
 * @author Luis Guimbarda
 */
@Test(groups = { "unit:xneat" })
public class UTestNeuron {
	private static final Random	RANDOM	= new Random();

	private double				activation;
	private Neuron				neuron;

	@BeforeMethod
	public void setUp() {
		activation = RANDOM.nextDouble();
		neuron = new Neuron(1, NodeType.HIDDEN, activation);
	}

	public void constructorSucceeds() {
		assertThat("",//
				neuron, isNotNull());
	}

	public void getActivationSlopeReturnsExpectedDouble() {
		assertThat("",//
				neuron.getActivationSlope(), isEqualTo(activation));
	}

	public void calculateSignalReturnsExpectedDouble() {
		double oldSignal = RANDOM.nextDouble();
		neuron.setSignal(oldSignal);
		neuron.setInputSignal(RANDOM.nextDouble());
		double newSignal = 1.0 / (1.0 + Math.exp(-neuron.getActivationSlope() * neuron.getInputSignal()));
		assertThat("",//
				neuron.calculateSignal(),//
				isEqualTo(newSignal - oldSignal));
	}

}
