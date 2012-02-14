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

import com.xtructure.xsim.impl.AbstractXBorder;
import com.xtructure.xsim.impl.XAddressImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class FitnessWorldBorder.
 *
 * @author Luis Guimbarda
 */
public class FitnessWorldBorder extends AbstractXBorder {

	/**
	 * Gets the single instance of FitnessWorldBorder.
	 *
	 * @param fitness the fitness
	 * @param world the world
	 * @return single instance of FitnessWorldBorder
	 */
	public static FitnessWorldBorder getInstance(OneDFitness fitness,
			OneDWorld world) {
		return new FitnessWorldBorder(fitness, world);
	}

	/**
	 * Instantiates a new fitness world border.
	 *
	 * @param fitness the fitness
	 * @param world the world
	 */
	private FitnessWorldBorder(OneDFitness fitness, OneDWorld world) {
		super();

		associate(//
				new XAddressImpl(world, world.getFoodLocationId()),//
				new FoodCaughtTransform(),//
				new XAddressImpl(fitness, OneDFitness.OBSERVED_CATCH_ID));

		associate(//
				new XAddressImpl(world, world.getCritterMovementId()),//
				new DistanceTransform(),//
				new XAddressImpl(fitness, OneDFitness.OBSERVED_DISTANCE_ID));

		associate(
				//
				new XAddressImpl(world, world.getFoodLocationId()),//
				new XAddressImpl(fitness, OneDFitness.OBSERVED_FOOD_LOCATION_ID));

		associate(//
				new XAddressImpl(world, world.getCritterLocationId()),//
				new XAddressImpl(fitness,
						OneDFitness.OBSERVED_CRITTER_LOCATION_ID));

		fitness.addBorder(this);
		world.addBorder(this);
	}

	/**
	 * The Class FoodCaughtTransform.
	 */
	static final class FoodCaughtTransform implements Transform {
		
		/** The last food location. */
		private Double lastFoodLocation = null;

		/* (non-Javadoc)
		 * @see com.xtructure.xsim.XBorder.Transform#transform(java.lang.Object)
		 */
		@Override
		public Object transform(Object orig) {
			if (orig != null && orig instanceof Double) {
				Double d = (Double) orig;
				if (lastFoodLocation == null) {
					lastFoodLocation = d;
					return false;
				}
				if (!lastFoodLocation.equals(d)) {
					lastFoodLocation = d;
					return true;
				}
				return false;
			}
			return orig;
		}
	}

	/**
	 * The Class DistanceTransform.
	 */
	static final class DistanceTransform implements Transform {
		
		/* (non-Javadoc)
		 * @see com.xtructure.xsim.XBorder.Transform#transform(java.lang.Object)
		 */
		@Override
		public Object transform(Object orig) {
			if (orig != null && orig instanceof Double) {
				return Math.abs((Double) orig);
			}
			return orig;
		}
	}
}
