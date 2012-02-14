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
package com.xtructure.xnet.demos.oned;

import com.xtructure.xbatch.process.impl.XBatchProcessorImpl;
import com.xtructure.xbatch.world.Fitness;
import com.xtructure.xbatch.world.Fitness.FitnessFactory;
import com.xtructure.xbatch.world.World.WorldFactory;
import com.xtructure.xevolution.evolution.impl.AbstractEvaluationStrategy;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xnet.demos.oned.OneDExperiment.OneDPopulationBatch;
import com.xtructure.xnet.demos.oned.components.OneDCritter;
import com.xtructure.xnet.demos.oned.components.OneDFitnessImpl;
import com.xtructure.xutil.id.XId;

/**
 * The Class OneDEvaluationStrategyImpl.
 * 
 * @author Luis Guimbarda
 */
public class OneDEvaluationStrategyImpl extends
		AbstractEvaluationStrategy<GeneMap, OneDCritter> implements
		OneDEvaluationStrategy<GeneMap> {

	/** The fitness factory. */
	private final FitnessFactory<?> fitnessFactory;

	/** The world factory. */
	private final WorldFactory<?> worldFactory;

	/** The simulation count. */
	private final int simulationCount;

	/** The simulation length. */
	private final long simulationLength;

	/**
	 * Instantiates a new one d evaluation strategy impl.
	 * 
	 * @param genomeDecoder
	 *            the genome decoder
	 * @param fitnessFactory
	 *            the fitness factory
	 * @param worldFactory
	 *            the world factory
	 * @param simulationCount
	 *            the simulation count
	 * @param simulationLength
	 *            the simulation length
	 */
	public OneDEvaluationStrategyImpl(
			GenomeDecoder<GeneMap, OneDCritter> genomeDecoder,
			FitnessFactory<?> fitnessFactory,//
			WorldFactory<?> worldFactory, int simulationCount,
			long simulationLength) {
		super(genomeDecoder);
		this.fitnessFactory = fitnessFactory;
		this.worldFactory = worldFactory;
		this.simulationCount = simulationCount;
		this.simulationLength = simulationLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvaluationStrategy#
	 * evaluatePopulation(com.xtructure.xevolution.genetics.Population)
	 */
	@Override
	public void evaluatePopulation(Population<GeneMap> population) {
		OneDPopulationBatch<GeneMap> popBatch = new OneDPopulationBatch<GeneMap>(
				getSimulationCount(), getSimulationLength(), population,
				getGenomeDecoder(), fitnessFactory, worldFactory);
		XBatchProcessorImpl.getInstance().start(popBatch, true);
		for (XId id : popBatch.getFitnessMap().keySet()) {
			Genome<GeneMap> genome = population.get(id);
			Fitness<?> fitness = popBatch.getFitnessMap().get(genome.getId());
			genome.setFitness((Long) fitness
					.getData(OneDFitnessImpl.CATCH_COUNT_ID)
					/ getSimulationCount().doubleValue());

			double dirChangeCount = (Long) fitness
					.getData(OneDFitnessImpl.DIRECTION_CHANGE_COUNT_ID)
					/ getSimulationCount().doubleValue();
			double distAway = (Double) fitness
					.getData(OneDFitnessImpl.DISTANCE_AWAY_FROM_FOOD_ID)
					/ getSimulationCount().doubleValue();
			double distTowards = (Double) fitness
					.getData(OneDFitnessImpl.DISTANCE_TOWARDS_FOOD_ID)
					/ getSimulationCount().doubleValue();
			double horXing = (Long) fitness
					.getData(OneDFitnessImpl.HORIZON_CROSSING_COUNT_ID)
					/ getSimulationCount().doubleValue();
			double timeRest = (Long) fitness
					.getData(OneDFitnessImpl.TIME_AT_REST_ID)
					/ getSimulationCount().doubleValue();
			double timeSpeed = (Long) fitness
					.getData(OneDFitnessImpl.TIME_AT_SPEED_ID)
					/ getSimulationCount().doubleValue();

			genome.setAttribute(DIRECTION_CHANGE_MEASURE_ID, dirChangeCount);
			genome.setAttribute(DISTANCE_AWAY_FROM_MEASURE_ID, distAway);
			genome.setAttribute(DISTANCE_TOWARDS_MEASURE_ID, distTowards);
			genome.setAttribute(HORIZON_CROSSING_MEASURE_ID, horXing);
			genome.setAttribute(TIME_AT_REST_MEASURE_ID, timeRest);
			genome.setAttribute(TIME_AT_SPEED_MEASURE_ID, timeSpeed);

			genome.incrementEvaluationCount();
		}
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
		// do nothing; evaluation handled in evaluatePopulation()
		return 0.0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xnet.demos.oned.OneDEvaluationStrategy#getSimulationLength
	 * ()
	 */
	@Override
	public Long getSimulationLength() {
		return simulationLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xnet.demos.oned.OneDEvaluationStrategy#getSimulationCount()
	 */
	@Override
	public Integer getSimulationCount() {
		return simulationCount;
	}
}
