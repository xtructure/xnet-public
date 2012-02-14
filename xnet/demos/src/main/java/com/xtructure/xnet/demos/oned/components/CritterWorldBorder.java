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

import com.xtructure.art.model.node.Node;
import com.xtructure.xbatch.world.World;
import com.xtructure.xsim.impl.AbstractXBorder;
import com.xtructure.xsim.impl.XAddressImpl;

// TODO: Auto-generated Javadoc
/**
 * A border between a {@link Critter} and a {@link World}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class CritterWorldBorder extends AbstractXBorder {
	/**
	 * Returns a border between the given critter and given world.
	 * 
	 * @param critter
	 *            the critter to be bordered
	 * 
	 * @param world
	 *            the world to be bordered
	 * 
	 * @return a border between the given critter and given world
	 */
	public static final CritterWorldBorder getInstance(
			final OneDCritter critter, final OneDWorld world) {
		return new CritterWorldBorder(critter, world);
	}

	/**
	 * Creates a new border between the given critter and given world.
	 * 
	 * @param critter
	 *            the critter to be bordered
	 * 
	 * @param world
	 *            the world to be bordered
	 */
	private CritterWorldBorder(final OneDCritter critter, final OneDWorld world) {
		super();

		// critter -> world
		associate(
				new XAddressImpl(critter, critter.getCounterClockwiseFootId()), //
				CounterClockwiseFootToMovementStimulusTransform.INSTANCE, //
				new XAddressImpl(world, world.getMovementStimulusId()));

		associate(new XAddressImpl(critter, critter.getClockwiseFootId()), //
				ClockwiseFootToMovementStimulusTransform.INSTANCE, //
				new XAddressImpl(world, world.getMovementStimulusId()));

		// world -> critter
		associate(new XAddressImpl(world, world.getFoodSmellId()), //
				FoodSmellToNoseTransform.INSTANCE, //
				new XAddressImpl(critter, critter.getNoseId()));

		associate(
				new XAddressImpl(world, world.getMovementFeedbackId()), //
				MovementFeedbackToCounterClockwiseUtricleTransform.INSTANCE, //
				new XAddressImpl(critter, critter
						.getCounterClockwiseUtricleId()));

		associate(new XAddressImpl(world, world.getMovementFeedbackId()), //
				MovementFeedbackToClockwiseUtricleTransform.INSTANCE, //
				new XAddressImpl(critter, critter.getClockwiseUtricleId()));

		world.addBorder(this);
		critter.addBorder(this);
	}

	/** A counter clockwise foot to movement stimulus transform. */
	static final class CounterClockwiseFootToMovementStimulusTransform
			implements Transform {
		/** The singleton instance of this class. */
		static final CounterClockwiseFootToMovementStimulusTransform INSTANCE = new CounterClockwiseFootToMovementStimulusTransform();

		/** Creates a new counter clockwise foot to movement stimulus transform. */
		private CounterClockwiseFootToMovementStimulusTransform() {
			super();
		}

		/** {@inheritDoc} */
		@Override
		public final Object transform(final Object orig) {
			return ((Node.Energies) orig).getFrontEnergy().doubleValue();
		}
	}

	/** A clockwise foot to movement stimulus transform. */
	static final class ClockwiseFootToMovementStimulusTransform implements
			Transform {
		/** The singleton instance of this class. */
		static final ClockwiseFootToMovementStimulusTransform INSTANCE = new ClockwiseFootToMovementStimulusTransform();

		/** Creates a new clockwise foot to movement stimulus transform. */
		private ClockwiseFootToMovementStimulusTransform() {
			super();
		}

		/** {@inheritDoc} */
		@Override
		public final Object transform(final Object orig) {
			return -((Node.Energies) orig).getFrontEnergy().doubleValue();
		}
	}

	/** A food smell to nose transform. */
	static final class FoodSmellToNoseTransform implements Transform {
		/** The singleton instance of this class. */
		static final FoodSmellToNoseTransform INSTANCE = new FoodSmellToNoseTransform();

		/** Creates a new food smell to nose transform. */
		private FoodSmellToNoseTransform() {
			super();
		}

		/** {@inheritDoc} */
		@Override
		public final Object transform(final Object orig) {
			return ((Double) orig).floatValue();
		}
	}

	/** A movement feedback to counter clockwise utricle transform. */
	static final class MovementFeedbackToCounterClockwiseUtricleTransform
			implements Transform {
		/** The singleton instance of this class. */
		static final MovementFeedbackToCounterClockwiseUtricleTransform INSTANCE = new MovementFeedbackToCounterClockwiseUtricleTransform();

		/**
		 * Creates a new movement feedback to counter clockwise utricle
		 * transform.
		 */
		private MovementFeedbackToCounterClockwiseUtricleTransform() {
			super();
		}

		/** {@inheritDoc} */
		@Override
		public final Object transform(final Object orig) {
			final Double feedback = ((Double) orig);
			return ((feedback > 0) ? feedback.floatValue() : 0);
		}
	}

	/** A movement feedback to clockwise utricle transform. */
	static final class MovementFeedbackToClockwiseUtricleTransform implements
			Transform {
		/** The singleton instance of this class. */
		static final MovementFeedbackToClockwiseUtricleTransform INSTANCE = new MovementFeedbackToClockwiseUtricleTransform();

		/** Creates a new movement feedback to clockwise utricle transform. */
		private MovementFeedbackToClockwiseUtricleTransform() {
			super();
		}

		/** {@inheritDoc} */
		@Override
		public final Object transform(final Object orig) {
			final Double feedback = ((Double) orig);
			return ((feedback < 0) ? -feedback.floatValue() : 0);
		}
	}
}
