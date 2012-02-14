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

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.impl.AbstractStandardXComponent;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * A simple {@link OneDWorld} implementation.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * 
 * @version 0.9.6
 */
public final class OnedWorldImpl extends AbstractStandardXComponent implements
		OneDWorld {
	/** The default id of a critter. */
	private static final XId DEFAULT_ID = XId.newId("1-D demo world");

	/** The id of the movement feedback source. */
	private static final XId MOVEMENT_FEEDBACK_ID = XId
			.newId("movement feedback");

	/** The id of the food smell source. */
	private static final XId FOOD_SMELL_ID = XId.newId("food smell");

	/** The id of the previous food smell. */
	private static final XId PREV_FOOD_SMELL_ID = XId
			.newId("previous food smell");

	/** The id of the critter location source. */
	private static final XId CRITTER_LOCATION_ID = XId
			.newId("critter location");

	/** The id of the critter next location. */
	private static final XId NEXT_CRITTER_LOCATION_ID = XId
			.newId("next critter location");

	/** The id of the food location source. */
	private static final XId FOOD_LOCATION_ID = XId.newId("food location");

	/** The id of the food next location. */
	private static final XId NEXT_FOOD_LOCATION_ID = XId
			.newId("next food location");

	/** The id of the nose stimulus. */
	private static final XId NOSE_STIMULUS_ID = XId.newId("nose stimulus");

	/** The id of the movement stimulus target. */
	private static final XId MOVEMENT_STIMULUS_ID = XId
			.newId("movement stimulus");

	/** The id of the critter movement. */
	private static final XId CRITTER_MOVEMENT_ID = XId
			.newId("critter movement");

	/** The sources of this component. */
	private static final Set<XId> SOURCES = new SetBuilder<XId>() //
			.add(MOVEMENT_FEEDBACK_ID) //
			.add(FOOD_SMELL_ID) //
			.add(PREV_FOOD_SMELL_ID)//
			.add(CRITTER_LOCATION_ID) //
			.add(NEXT_CRITTER_LOCATION_ID)//
			.add(FOOD_LOCATION_ID) //
			.add(NEXT_FOOD_LOCATION_ID)//
			.add(CRITTER_MOVEMENT_ID) //
			.add(NOSE_STIMULUS_ID)//
			.newImmutableInstance();

	/** The targets of this component. */
	private static final Set<XId> TARGETS = new SetBuilder<XId>() //
			.add(MOVEMENT_STIMULUS_ID) //
			.newImmutableInstance();

	/**
	 * Gets the single instance of OnedWorldImpl.
	 *
	 * @return a world
	 */
	public static final OnedWorldImpl getInstance() {
		return new OnedWorldImpl(10.0, 1.0, 2.0, 0.0015);
	}

	/**
	 * Gets the single instance of OnedWorldImpl.
	 *
	 * @param world the world to copy
	 * @return a copy of the given world
	 */
	public static final OnedWorldImpl getInstance(OnedWorldImpl world) {
		return new OnedWorldImpl(//
				world._movementFeedbackScale,//
				world._noseStimulusScale,//
				world._noseStimulusPower,//
				world._movementStimulusScale);
	}

	/** The factor by which to scale movement feedback. */
	private final double _movementFeedbackScale;

	/** The factor by which to scale nose stimuli. */
	private final double _noseStimulusScale;

	/** The power by which to raise nose stimuli. */
	private final double _noseStimulusPower;

	/** The factor by which to scale movement stimuli. */
	private final double _movementStimulusScale;

	/** The _one way capture. */
	private final boolean _oneWayCapture;
	
	/** The _relocation type. */
	private final FoodRelocationType _relocationType;

	/** The movement feedback to be sent to the critter. */
	private double _movementFeedback = 0.0;

	/** The food smell at the last time step. */
	private double _previousFoodSmell = Double.MAX_VALUE;

	/** The food smell at this time step. */
	private double _currentFoodSmell = Double.MAX_VALUE;

	/** The nose stimulus to be sent to the critter. */
	private double _noseStimulus = 0.0;

	/** The current critter location [0..1). */
	private double _currentCritterLocation = 0.75;

	/** The next critter location [0..1). */
	private double _nextCritterLocation = 0.75;

	/** The _critter movement. */
	private double _critterMovement = 0.0;

	/** The current food location [0..1). */
	private double _currentFoodLocation = 0.5;

	/** The next food location [0..1). */
	private double _nextFoodLocation = 0.5;

	/** The movement stimulus read from the critter. */
	private double _movementStimulus = 0.0;

	/** The _clockwise capture. */
	private boolean _clockwiseCapture = true;

	/**
	 * Creates a new world.
	 * 
	 * @param movementFeedbackScale
	 *            the factor by which to scale movement feedback
	 * 
	 * @param noseStimulusScale
	 *            the factor by which to scale nose stimuli
	 * 
	 * @param noseStimulusPower
	 *            the power by which to raise nose stimuli
	 * 
	 * @param movementStimulusScale
	 *            the factor by which to scale movement stimuli
	 */
	private OnedWorldImpl(final double movementFeedbackScale,
			final double noseStimulusScale, final double noseStimulusPower,
			final double movementStimulusScale) {
		super(DEFAULT_ID, SOURCES, TARGETS);

		_movementFeedbackScale = movementFeedbackScale;
		_noseStimulusScale = noseStimulusScale;
		_noseStimulusPower = noseStimulusPower;
		_movementStimulusScale = movementStimulusScale;

		this._relocationType = FoodRelocationType.UNIFORM;
		this._oneWayCapture = true;
	}

	/** {@inheritDoc} */
	@Override
	public final Object getData(final XId partId) {
		if (MOVEMENT_FEEDBACK_ID.equals(partId)) {
			return _movementFeedback;
		}
		if (FOOD_SMELL_ID.equals(partId)) {
			return _noseStimulus;
		}
		if (PREV_FOOD_SMELL_ID.equals(partId)) {
			return _previousFoodSmell;
		}
		if (CRITTER_LOCATION_ID.equals(partId)) {
			return _currentCritterLocation;
		}
		if (NEXT_CRITTER_LOCATION_ID.equals(partId)) {
			return _nextCritterLocation;
		}
		if (FOOD_LOCATION_ID.equals(partId)) {
			return _currentFoodLocation;
		}
		if (NEXT_FOOD_LOCATION_ID.equals(partId)) {
			return _nextFoodLocation;
		}
		if (CRITTER_MOVEMENT_ID.equals(partId)) {
			return _critterMovement;
		}
		if (NOSE_STIMULUS_ID.equals(partId)) {
			return _noseStimulus;
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public final void prepareBeforeHook() {
		super.prepareBeforeHook();

		_movementStimulus = 0.0;
		_previousFoodSmell = _currentFoodSmell;
	}

	/** {@inheritDoc} */
	@Override
	public final void calculate() {
		super.calculate();

		// calculate critter movement
		_critterMovement = _movementStimulus * _movementStimulusScale;
		_nextCritterLocation = normalize(_currentCritterLocation
				+ _critterMovement);

		// calculate food movement
		// _nextFoodLocation = _currentFoodLocation;
		if (foodEaten(_critterMovement)) {
			final double center = _relocationType.isFixed() ? 0.5
					: _nextCritterLocation;
			final double delta = _relocationType.isGlobal() ? 0.5 : 0.25;
			final double lowerBound = Math.min(//
					center - delta,//
					center + delta);
			final double upperBound = Math.max(//
					center - delta,//
					center + delta);
			if (_relocationType.isUniform()) {
				_nextFoodLocation = normalize((upperBound - lowerBound)
						* Math.random() + lowerBound);
			} else {
				final double mu = (lowerBound + upperBound) / 2;
				final double sigma = (mu - lowerBound) / 3;
				_nextFoodLocation = normalize(nextNormal(mu, sigma, lowerBound,
						upperBound));
			}
		}

		// calculate movement feedback
		_movementFeedback = _critterMovement * _movementFeedbackScale;

		// calculate nose stimulus...
		_currentFoodSmell = smell();
		_noseStimulus = (_currentFoodSmell > _previousFoodSmell) ? _currentFoodSmell
				: 0.0;
	}

	/** {@inheritDoc} */
	@Override
	public final void update() {
		super.update();

		_currentCritterLocation = _nextCritterLocation;
		_currentFoodLocation = _nextFoodLocation;
	}

	/*
	 * --- critter sources
	 */

	/** {@inheritDoc} */
	@Override
	public final XId getMovementFeedbackId() {
		return MOVEMENT_FEEDBACK_ID;
	}

	/** {@inheritDoc} */
	@Override
	public final XId getFoodSmellId() {
		return FOOD_SMELL_ID;
	}

	/** {@inheritDoc} */
	@Override
	public final XId getCritterLocationId() {
		return CRITTER_LOCATION_ID;
	}

	/** {@inheritDoc} */
	@Override
	public final XId getFoodLocationId() {
		return FOOD_LOCATION_ID;
	}

	/*
	 * --- critter targets
	 */

	/** {@inheritDoc} */
	@Override
	public final XId getMovementStimulusId() {
		return MOVEMENT_STIMULUS_ID;
	}

	/*
	 * --- fitness sources
	 */

	/** {@inheritDoc} */
	@Override
	public XId getCritterMovementId() {
		return CRITTER_MOVEMENT_ID;
	}

	/*
	 * --- utils
	 */

	/** {@inheritDoc} */
	@Override
	protected final void addForeignData(final XId targetId,
			final XAddress sourceAddress, final Object data) {
		if (MOVEMENT_STIMULUS_ID.equals(targetId)) {
			_movementStimulus += (Double) data;
		}
	}

	/**
	 * Gets the world visualization.
	 *
	 * @param i side length of visualization window
	 * @return a visualization for this WorldImpl object
	 */
	public WorldVisualization getWorldVisualization(int i) {
		WorldVisualization worldViz = WorldVisualization.getInstance(this, i);
		worldViz.setCritPos(_currentCritterLocation);
		worldViz.setFoodPos(_currentFoodLocation);
		return worldViz;
	}

	/**
	 * Gets the world calculations visualization.
	 *
	 * @return the world calculations visualization
	 */
	public WorldCalculationsVisualization getWorldCalculationsVisualization() {
		List<XId> rowIds = new ListBuilder<XId>()//
				.add(//
				getFoodLocationId(),//
				getCritterLocationId(),//
						getMovementFeedbackId(),//
						getFoodSmellId())//
				.newImmutableInstance();

		return WorldCalculationsVisualization.getInstance(rowIds, this);
	}

	/**
	 * Indicates whether the food will be eaten by the critter after the given
	 * move.
	 * 
	 * @param critterMovement
	 *            the calculated movement of the critter
	 * 
	 * @return <code>true</code> if the critter will eat the food;
	 *         <code>false</code> otherwise
	 */
	private final boolean foodEaten(final double critterMovement) {
		/*
		 * We calculate how far we would need to move forward or backward to
		 * reach the food. If we will move at least that far, we'll eat the
		 * food!
		 */

		final double distanceToFood = Math.min(//
				normalize(_currentCritterLocation - _currentFoodLocation),//
				normalize(_currentFoodLocation - _currentCritterLocation));
		boolean foodIsEaten = (Math.abs(critterMovement) >= distanceToFood);

		if (_oneWayCapture) {
			boolean movingClockwise = critterMovement < 0.0F;
			if (foodIsEaten && (movingClockwise != _clockwiseCapture)) {
				foodIsEaten = false;
			}
			_clockwiseCapture = foodIsEaten ? !_clockwiseCapture
					: _clockwiseCapture;
		}

		return foodIsEaten;
	}

	/**
	 * Calculates the smell of the food.
	 * 
	 * @return the smell of the food
	 */
	private final double smell() {
		final double counterClockwiseDistance = Math.min(//
				normalize(_nextFoodLocation - _nextCritterLocation),//
				normalize(_nextCritterLocation - _nextFoodLocation));
		final double clockwiseDistance = 1.0 - counterClockwiseDistance;

		return _noseStimulusScale //
				* (Math.pow(counterClockwiseDistance, _noseStimulusPower) //
				+ Math.pow(clockwiseDistance, _noseStimulusPower));
	}

	/**
	 * Returns a normalized location in [0 .. 1).
	 * 
	 * @param raw
	 *            a raw location
	 * 
	 * @return a normalized locations
	 */
	private final double normalize(final double raw) {
		return (raw - Math.floor(raw));
	}

	/**
	 * Returns a double strictly in [lowerBound .. upperBound) drawn from N(mu,
	 * sigma). Given bounds, suggest that: mu = (lowerBound+upperBound)/2 and
	 * sigma = (mu-lowerBound) / 3.
	 *
	 * @param mu the mu
	 * @param sigma the sigma
	 * @param lowerBound the lower bound
	 * @param upperBound the upper bound
	 * @return a double strictly in [lowerBound .. upperBound) drawn from N(mu,
	 * sigma)
	 */
	private double nextNormal(final double mu, final double sigma,
			final double lowerBound, final double upperBound) {
		double d;
		do {
			d = (new Random()).nextGaussian() * sigma + mu;
		} while (d < lowerBound || upperBound <= d);
		return d;
	}

	/**
	 * The Enum FoodRelocationType.
	 */
	static enum FoodRelocationType {
		/** position is drawn from [0.0 .. 1.0) uniformly */
		UNIFORM, //
		/** position is drawn from [0.25 .. 0.75) uniformly */
		UNIFORM_FIXED, //
		/** position is drawn from _critterPosition +/- 0.25 uniformly */
		UNIFORM_DYNAMIC, //
		/** position is drawn from [0.0 .. 1.0) normally (Gaussian) */
		NORMAL, //
		/** position is drawn from [0.25 .. 0.75) normally (Gaussian) */
		NORMAL_FIXED, //
		/** position is drawn from _critterPosition +/- 0.25 normally (Gaussian) */
		NORMAL_DYNAMIC;

		/**
		 * Checks if is global.
		 *
		 * @return true, if is global
		 */
		public boolean isGlobal() {
			return UNIFORM.equals(this) || NORMAL.equals(this);
		}

		/**
		 * Checks if is fixed.
		 *
		 * @return true, if is fixed
		 */
		public boolean isFixed() {
			return UNIFORM_FIXED.equals(this) || NORMAL_FIXED.equals(this);
		}

		/**
		 * Checks if is dynamic.
		 *
		 * @return true, if is dynamic
		 */
		public boolean isDynamic() {
			return UNIFORM_DYNAMIC.equals(this) || NORMAL_DYNAMIC.equals(this);
		}

		/**
		 * Checks if is uniform.
		 *
		 * @return true, if is uniform
		 */
		public boolean isUniform() {
			return UNIFORM.equals(this) || UNIFORM_FIXED.equals(this)
					|| UNIFORM_DYNAMIC.equals(this);
		}

		/**
		 * Checks if is normal.
		 *
		 * @return true, if is normal
		 */
		public boolean isNormal() {
			return NORMAL.equals(this) || NORMAL_FIXED.equals(this)
					|| NORMAL_DYNAMIC.equals(this);
		}
	}

	/**
	 * A factory for creating WorldImpl objects.
	 */
	public static final class WorldImplFactory implements
			WorldFactory<StandardTimePhase> {
		
		/** The movement feedback scale. */
		private final double movementFeedbackScale;
		
		/** The nose stimulus scale. */
		private final double noseStimulusScale;
		
		/** The nose stimulus power. */
		private final double noseStimulusPower;
		
		/** The movement stimulus scale. */
		private final double movementStimulusScale;

		/**
		 * Instantiates a new world impl factory.
		 *
		 * @param movementFeedbackScale the movement feedback scale
		 * @param noseStimulusScale the nose stimulus scale
		 * @param noseStimulusPower the nose stimulus power
		 * @param movementStimulusScale the movement stimulus scale
		 */
		public WorldImplFactory(final double movementFeedbackScale,
				final double noseStimulusScale, final double noseStimulusPower,
				final double movementStimulusScale) {
			this.movementFeedbackScale = movementFeedbackScale;
			this.noseStimulusScale = noseStimulusScale;
			this.noseStimulusPower = noseStimulusPower;
			this.movementStimulusScale = movementStimulusScale;
		}

		/* (non-Javadoc)
		 * @see com.xtructure.xbatch.world.World.WorldFactory#newInstance(com.xtructure.xutil.id.XId)
		 */
		@Override
		public OnedWorldImpl newInstance(XId id) {
			return new OnedWorldImpl(movementFeedbackScale, noseStimulusScale,
					noseStimulusPower, movementStimulusScale);
		}
	}
}
