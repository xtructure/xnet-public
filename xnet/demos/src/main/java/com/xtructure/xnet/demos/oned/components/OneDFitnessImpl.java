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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Set;

import com.xtructure.xbatch.world.Fitness;
import com.xtructure.xsim.impl.AbstractStandardXReporter;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * The Class OneDFitnessImpl.
 *
 * @author Luis Guimbarda
 */
public class OneDFitnessImpl extends AbstractStandardXReporter implements
		OneDFitness {
	
	/** TODO: implement code to derive max_speed. */
	public static final double MAX_SPEED = 0.01;
	
	/** lower bound on speed to consider for TIME_AT_SPEED. */
	public static final double HIGH_SPEED = MAX_SPEED * 0.99;
	
	/** upper bound on speed to consider for TIME_AT_REST. */
	public static final double LOW_SPEED = MAX_SPEED - HIGH_SPEED;

	/*
	 * food independent statistics
	 */

	/** Source id for the count of direction changes running statistic. */
	public static final XId DIRECTION_CHANGE_COUNT_ID;
	
	/** Source id for the number of timesteps at or above HIGH_SPEED. */
	public static final XId TIME_AT_SPEED_ID;
	
	/** Source id for the number of timesteps at or below LOW_SPEED. */
	public static final XId TIME_AT_REST_ID;

	/*
	 * food dependent statistics
	 */

	/** Source id for the catch count running statistic. */
	public static final XId CATCH_COUNT_ID;
	
	/** Source id for the total distance traveled towards food running statistic. */
	public static final XId DISTANCE_TOWARDS_FOOD_ID;
	
	/** Source id for the total distance traveled away from food running statistic. */
	public static final XId DISTANCE_AWAY_FROM_FOOD_ID;
	
	/** Source id for the count of horizon crossings running statistic (the point opposite the food location). */
	public static final XId HORIZON_CROSSING_COUNT_ID;

	/** Collection of source ids for OneDNoveltyFitness objects. */
	private static final Set<XId> SOURCES;
	
	/** Collection of target ids for OneDNoveltyFitness objects. */
	private static final Set<XId> TARGETS;

	static {
		CATCH_COUNT_ID = XId.newId("CATCH_COUNT_ID");
		DISTANCE_TOWARDS_FOOD_ID = XId.newId("DISTANCE_TOWARDS_FOOD");
		DISTANCE_AWAY_FROM_FOOD_ID = XId.newId("DISTANCE_AWAY_FROM_FOOD");
		HORIZON_CROSSING_COUNT_ID = XId.newId("HORIZON_CROSSING_COUNT");
		DIRECTION_CHANGE_COUNT_ID = XId.newId("CHANGE_DIRECTION_COUNT");
		TIME_AT_SPEED_ID = XId.newId("TIME_AT_SPEED");
		TIME_AT_REST_ID = XId.newId("TIME_AT_REST");

		TARGETS = new SetBuilder<XId>()//
				.add(OBSERVED_FOOD_LOCATION_ID)//
				.add(OBSERVED_CRITTER_LOCATION_ID)//
				.add(OBSERVED_CATCH_ID)//
				.add(OBSERVED_DISTANCE_ID)//
				.newImmutableInstance();

		SOURCES = new SetBuilder<XId>().add(CATCH_COUNT_ID)//
				.add(DISTANCE_TOWARDS_FOOD_ID)//
				.add(DISTANCE_AWAY_FROM_FOOD_ID)//
				.add(HORIZON_CROSSING_COUNT_ID)//
				.add(DIRECTION_CHANGE_COUNT_ID)//
				.add(TIME_AT_SPEED_ID)//
				.add(TIME_AT_REST_ID)//
				.newImmutableInstance();
	}

	/**
	 * Creates a new {@link OneDFitnessImpl} with the given id.
	 *
	 * @param id the id
	 */
	public OneDFitnessImpl(XId id) {
		super(id, SOURCES, TARGETS);
		updateStats(0, 0, 0, 0, 0, 0, 0);
	}

	/** observed clockwise distance to food at last timestep. */
	private Double lastCWDistance = null;
	
	/** observed counterclockwise distance to food at last timestep. */
	private Double lastCCWDistance = null;
	
	/** indicator that food was closer clockwise at last timestep. */
	private Boolean lastFoodIsClockwise = null;
	
	/** sign indicating direction of critter movement at last timestep. */
	private Double lastDirection = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.impl.AbstractStandardXComponent#update()
	 */
	@Override
	protected void update() {
		super.update();

		// get observations
		Double observedFoodLocation = (Double) observations
				.get(OBSERVED_FOOD_LOCATION_ID);
		Double observedCritterLocation = (Double) observations
				.get(OBSERVED_CRITTER_LOCATION_ID);
		Boolean observedCatch = (Boolean) observations.get(OBSERVED_CATCH_ID);
		Double observedDistanceRaw = (Double) observations
				.get(OBSERVED_DISTANCE_ID);

		// validate observations
		validateState("observations.get(OBSERVED_FOOD_LOCATION_ID)",
				observedFoodLocation, isNotNull());
		validateState("observations.get(OBSERVED_CRITTER_LOCATION_ID)",
				observedCritterLocation, isNotNull());
		validateState("observations.get(OBSERVED_CATCH_ID)", observedCatch,
				isNotNull());
		validateState("observations.get(OBSERVED_DISTANCE_ID)",
				observedDistanceRaw, isNotNull());

		// determine distance traveled
		Double distanceTraveled = Math.abs(observedDistanceRaw);
		Double direction = Math.signum(observedDistanceRaw);

		// determine orientation
		double clockwiseDistance = normalize(observedCritterLocation
				- observedFoodLocation);
		double counterClockwiseDistance = 1 - clockwiseDistance;
		boolean foodIsClockwise = clockwiseDistance <= counterClockwiseDistance;

		// get current counts
		long catchCount = (Long) statistics.get(CATCH_COUNT_ID);
		long timeAtSpeed = (Long) statistics.get(TIME_AT_SPEED_ID);
		long timeAtRest = (Long) statistics.get(TIME_AT_REST_ID);
		double distanceTowardsFood = (Double) statistics
				.get(DISTANCE_TOWARDS_FOOD_ID);
		double distanceAwayFromFood = (Double) statistics
				.get(DISTANCE_AWAY_FROM_FOOD_ID);
		long horizonCrossings = (Long) statistics
				.get(HORIZON_CROSSING_COUNT_ID);
		long directionChanges = (Long) statistics
				.get(DIRECTION_CHANGE_COUNT_ID);

		if (lastCWDistance != null) {
			// update food independent stats (and catch count)
			catchCount += (observedCatch ? 1 : 0);
			directionChanges += (distanceTraveled > 0 && direction != lastDirection) ? 1
					: 0;
			timeAtSpeed += (distanceTraveled > HIGH_SPEED ? 1 : 0);
			timeAtRest += (distanceTraveled < LOW_SPEED ? 1 : 0);
			if (!observedCatch) {
				// update food dependent stats
				double distanceToFood = Math.min(clockwiseDistance,
						counterClockwiseDistance);
				double lastDistanceToFood = Math.min(lastCWDistance,
						lastCCWDistance);
				boolean crossedHorizen = !observedCatch
						&& (lastFoodIsClockwise != foodIsClockwise);
				boolean movingTowardsFood = !crossedHorizen
						&& (distanceToFood < lastDistanceToFood);
				horizonCrossings += crossedHorizen ? 1 : 0;
				distanceTowardsFood += movingTowardsFood ? distanceTraveled : 0;
				distanceAwayFromFood += movingTowardsFood ? 0
						: distanceTraveled;
			}
		}

		updateStats(catchCount, distanceTowardsFood, distanceAwayFromFood,
				horizonCrossings, directionChanges, timeAtSpeed, timeAtRest);

		// remember observations
		lastCWDistance = clockwiseDistance;
		lastCCWDistance = counterClockwiseDistance;
		lastFoodIsClockwise = foodIsClockwise;
		lastDirection = lastDirection == null || distanceTraveled > 0 ? direction
				: lastDirection;
	}

	/**
	 * Normalize.
	 *
	 * @param raw the raw
	 * @return the double
	 */
	private final double normalize(final double raw) {
		return (raw - Math.floor(raw));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xbatch.world.Fitness#addStats(com.xtructure.xbatch.world
	 * .Fitness)
	 */
	@Override
	public void addStats(Fitness<?> fitness) {
		OneDFitnessImpl f = (OneDFitnessImpl) fitness;
		long catchCount = (Long) statistics.get(CATCH_COUNT_ID) //
				+ (Long) f.statistics.get(CATCH_COUNT_ID);
		double distanceTowardsFood = (Double) statistics
				.get(DISTANCE_TOWARDS_FOOD_ID)//
				+ (Double) f.statistics.get(DISTANCE_TOWARDS_FOOD_ID);
		double distanceAwayFromFood = (Double) statistics
				.get(DISTANCE_AWAY_FROM_FOOD_ID)//
				+ (Double) f.statistics.get(DISTANCE_AWAY_FROM_FOOD_ID);
		long horizonCrossings = (Long) statistics
				.get(HORIZON_CROSSING_COUNT_ID)//
				+ (Long) f.statistics.get(HORIZON_CROSSING_COUNT_ID);
		long directionChanges = (Long) statistics
				.get(DIRECTION_CHANGE_COUNT_ID)//
				+ (Long) f.statistics.get(DIRECTION_CHANGE_COUNT_ID);
		long timeAtSpeed = (Long) statistics.get(TIME_AT_SPEED_ID)//
				+ (Long) f.statistics.get(TIME_AT_SPEED_ID);
		long timeAtRest = (Long) statistics.get(TIME_AT_REST_ID)//
				+ (Long) f.statistics.get(TIME_AT_REST_ID);
		updateStats(catchCount, distanceTowardsFood, distanceAwayFromFood,
				horizonCrossings, directionChanges, timeAtSpeed, timeAtRest);
	}

	/**
	 * Update stats.
	 *
	 * @param catchCount the catch count
	 * @param distanceTowardsFood the distance towards food
	 * @param distanceAwayFromFood the distance away from food
	 * @param horizonCrossings the horizon crossings
	 * @param directionChanges the direction changes
	 * @param timeAtSpeed the time at speed
	 * @param timeAtRest the time at rest
	 */
	private void updateStats(//
			long catchCount,//
			double distanceTowardsFood,//
			double distanceAwayFromFood,//
			long horizonCrossings,//
			long directionChanges,//
			long timeAtSpeed,//
			long timeAtRest) {
		statistics.put(CATCH_COUNT_ID, catchCount);
		statistics.put(DISTANCE_TOWARDS_FOOD_ID, distanceTowardsFood);
		statistics.put(DISTANCE_AWAY_FROM_FOOD_ID, distanceAwayFromFood);
		statistics.put(HORIZON_CROSSING_COUNT_ID, horizonCrossings);
		statistics.put(DIRECTION_CHANGE_COUNT_ID, directionChanges);
		statistics.put(TIME_AT_SPEED_ID, timeAtSpeed);
		statistics.put(TIME_AT_REST_ID, timeAtRest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.XReporter#write(java.io.Writer)
	 */
	@Override
	public void write(Writer writer) throws IOException {
		// TODO: construct a more informative report
		writer.write(this.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.XReporter#write(java.io.PrintStream)
	 */
	@Override
	public void write(PrintStream stream) {
		// TODO: construct a more informative report
		stream.println(this.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.impl.AbstractStandardXComponent#toString()
	 */
	@Override
	public String toString() {
		return statistics.toString();
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xbatch.world.Fitness#evaluate()
	 */
	@Override
	public Double evaluate() {
		return ((Long) statistics.get(CATCH_COUNT_ID)).doubleValue();
	}

	/**
	 * A factory for creating OneDFitness objects.
	 */
	public static class OneDFitnessFactory implements
			FitnessFactory<StandardTimePhase> {
		
		/* (non-Javadoc)
		 * @see com.xtructure.xbatch.world.Fitness.FitnessFactory#newInstance(com.xtructure.xutil.id.XId)
		 */
		@Override
		public OneDFitnessImpl newInstance(XId id) {
			return new OneDFitnessImpl(id);
		}
	}
}
