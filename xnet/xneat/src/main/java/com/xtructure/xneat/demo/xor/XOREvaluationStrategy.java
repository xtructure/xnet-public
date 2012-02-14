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
package com.xtructure.xneat.demo.xor;

import java.util.Arrays;

import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.impl.AbstractEvaluationStrategy;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.impl.NEATGenomeDecoder;
import com.xtructure.xneat.network.NeuralNetwork;
import com.xtructure.xutil.RandomUtil;

/**
 * @author Luis Guimbarda
 * 
 */
public class XOREvaluationStrategy extends AbstractEvaluationStrategy<GeneMap, NeuralNetwork> implements EvaluationStrategy<GeneMap, NeuralNetwork> {
	private enum Inputs {
		TT(new double[] { 1.0, 0.1 }), //
		TF(new double[] { 1.0, 0.0 }), //
		FT(new double[] { 0.0, 0.1 }), //
		FF(new double[] { 0.0, 0.0 });
		private final double[]	inputs;

		Inputs(double[] inputs) {
			this.inputs = inputs;
		}
	}

	/** */
	private static final XOREvaluationStrategy	INSTANCE		= new XOREvaluationStrategy();
	/** get all outputs exactly right */
	private static final double					CORRECT_BONUS	= Inputs.values().length;

	public static XOREvaluationStrategy getInstance() {
		return INSTANCE;
	}

	private XOREvaluationStrategy() {
		super(NEATGenomeDecoder.getInstance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.EvaluationStrategy#simulate(com.xtructure.xevolution
	 * .genetics.Genome)
	 */
	@Override
	public double simulate(Genome<GeneMap> genome) {
		if (genome.getEvaluationCount() == 0) {
			NeuralNetwork network = getGenomeDecoder().decode(genome);
			double fitness = applyNetwork(network);
			genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, fitness);
			genome.incrementEvaluationCount();
			return fitness;
		}
		return genome.getFitness();
	}

	private double applyNetwork(NeuralNetwork network) {
		// sum of closeness to perfect outputs
		double fitness = 0.0;
		// indicates that the network outputs correctly on all inputs
		boolean passed = true;
		// shuffle inputs so network learns function and not output sequence
		for (Inputs input : RandomUtil.shuffle(Arrays.asList(Inputs.values()))) {
			double[] outputs = runNetwork(network, input.inputs);
			if (outputs == null) {
				// network didn't settle
				return 0.0;
			}
			switch (input) {
				case TT:
				case FF: {
					// inputs same => 0.0 output
					fitness += 1.0 - outputs[0];
					passed = passed && outputs[0] < 0.5;
					break;
				}
				case TF:
				case FT: {
					// inputs differ => 1.0 output
					fitness += outputs[0];
					passed = passed && outputs[0] >= 0.5;
					break;
				}
			}
		}
		return fitness + (passed ? CORRECT_BONUS : 0.0);
	}

	private double[] runNetwork(NeuralNetwork network, double[] inputSignals) {
		network.clearSignals();
		network.setInputSignals(inputSignals);
		if (!network.relaxNetwork(10, 0.01)) {
			return null;
		} else {
			return network.getOutputSignals();
		}
	}
}
