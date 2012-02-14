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
package com.xtructure.xneat.network;

/**
 * The {@link NeuralNetwork} interface describes the methods used to run a
 * neural network.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface NeuralNetwork {
	
	/**
	 * Sets the energies to use as inputs in the {@link NeuralNetwork}.
	 *
	 * @param inputSignals the new input signals
	 */
	public void setInputSignals(double[] inputSignals);
	
	/**
	 * Gets the energies of the the outputs in the {@link NeuralNetwork}.
	 * 
	 * @return the energies of the the outputs in the {@link NeuralNetwork}.
	 */
	public double[] getOutputSignals();
	
	/**
	 * Propagates the energies through the network for one step. (A single step
	 * allows each neuron processes its input energies once.)
	 *
	 * @return the max signal delta
	 */
	public double singleStep();
	
	/**
	 * Propagates the energies through the network until the network reaches
	 * equilibrium or for the given number of steps. (Equilibrium is reached
	 * when the total change in energy with the network is less then the given
	 * maxDelta. A single step allows each neuron processes its input energies
	 * once.) The returned boolean indicates whether the network reached
	 * equilibrium before the maxSteps.
	 *
	 * @param maxSteps the max steps
	 * @param maxDelta the max delta
	 * @return true if the network reach equilibrium within maxSteps, false
	 * otherwise.
	 */
	public boolean relaxNetwork(int maxSteps, double maxDelta);
	
	/**
	 * Sets the energies of neurons in the network to 0.0.
	 */
	public void clearSignals();
	
}
