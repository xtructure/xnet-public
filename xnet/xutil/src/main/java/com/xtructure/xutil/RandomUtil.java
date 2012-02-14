/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xutil;

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThan;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A collection of utility methods depending on randomness.
 * 
 * @author Luis Guimbarda
 */
public class RandomUtil {
	
	/** The Constant RANDOM. */
	private static final Random	RANDOM	= new Random();

	/**
	 * Instantiates a new random util.
	 */
	private RandomUtil() {}

	/**
	 * Sets the seed of the {@link Random} object.
	 *
	 * @param seed the new seed
	 */
	public static void setSeed(long seed) {
		RANDOM.setSeed(seed);
	}

	/*
	 * uniform random value generators
	 */
	/**
	 * Generates a uniformly random float.
	 * 
	 * @return the generated float.
	 */
	public static float nextFloat() {
		return RANDOM.nextFloat();
	}

	/**
	 * Generates a uniformly random long.
	 *
	 * @return the long
	 */
	public long nextLong() {
		return RANDOM.nextLong();
	}

	/**
	 * Generates a uniformly random long in the range [0,n).
	 *
	 * @param n the n
	 * @return the generated long.
	 */
	public static long nextLong(long n) {
		return nextLong(Range.getInstance(0l, n - 1));
	}

	/**
	 * Generates a uniformly random integer.
	 * 
	 * @return the generated integer.
	 */
	public static int nextInteger() {
		return RANDOM.nextInt();
	}

	/**
	 * Generates a uniformly random integer in the range [0,n).
	 *
	 * @param n the n
	 * @return the generated integer.
	 */
	public static int nextInteger(int n) {
		return RANDOM.nextInt(n);
	}

	/**
	 * Generates a uniformly random short.
	 *
	 * @return the generated integer.
	 */
	public static short nextShort() {
		return (short) nextInteger(Short.MAX_VALUE);
	}

	/**
	 * Generates a uniformly random byte.
	 *
	 * @return the generated integer.
	 */
	public static byte nextByte() {
		return (byte) nextInteger(Byte.MAX_VALUE);
	}

	/**
	 * Generates a uniformly random boolean value.
	 * 
	 * @return the generated boolean.
	 */
	public static boolean nextBoolean() {
		return RANDOM.nextBoolean();
	}

	/**
	 * Generates a uniformly random double.
	 * 
	 * @return the generated double.
	 */
	public static double nextDouble() {
		return RANDOM.nextDouble();
	}

	/*
	 * ranged uniform random value generators
	 */
	/**
	 * Generates a uniformly random value within the given range. The following
	 * types are supported:
	 * <ul>
	 * <li>Boolean
	 * <li>Byte
	 * <li>Double
	 * <li>Float
	 * <li>Integer
	 * <li>Long
	 * <li>Short
	 * </ul>
	 * 
	 * @param <V>
	 *            the type of the range
	 * @param range
	 *            the range from which to select a value
	 * @param cls
	 *            the class of the range
	 * @return the selected value, or null if the type of the range is
	 *         unsupported
	 */
	public static final <V extends Comparable<V>> V next(Range<V> range, Class<V> cls) {
		ValueType type = ValueType.getValueType(cls);
		if (type == null || type.isSTRING() || type.isXID()) {
			return null;
		}
		// range of type from which we know how to select
		V min = range.getMinimum() == null ? type.<V> minValue() : range.getMinimum();
		V max = range.getMaximum() == null ? type.<V> maxValue() : range.getMaximum();
		if (min == max) {
			return max;
		}
		switch (type) {
			case BOOLEAN: {
				// if range is a boolean point, this method would have already
				// returned, so...
				return cls.cast(new Boolean(nextBoolean()));
			}
			case BYTE: {
				return cls.cast(nextByte((Byte) min, (Byte) max));
			}
			case DOUBLE: {
				return cls.cast(nextDouble((Double) min, (Double) max));
			}
			case FLOAT: {
				return cls.cast(nextFloat((Float) min, (Float) max));
			}
			case INTEGER: {
				return cls.cast(nextInteger((Integer) min, (Integer) max));
			}
			case LONG: {
				return cls.cast(nextLong((Long) min, (Long) max));
			}
			case SHORT: {
				return cls.cast(nextShort((Short) min, (Short) max));
			}
			default: {
				// shouldn't happen
				return null;
			}
		}
	}

	/**
	 * Generates a uniformly random double value within the given range.
	 *
	 * @param range the range
	 * @return the generated double.
	 */
	public static double nextDouble(Range<Double> range) {
		return nextDouble(//
				range.getMinimum() == null ? -Double.MAX_VALUE : range.getMinimum(),//
				range.getMaximum() == null ? Double.MAX_VALUE : range.getMaximum());
	}

	/**
	 * Generates a uniformly random double value within the given range.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the double
	 */
	public static double nextDouble(double min, double max) {
		if (max - min == Double.POSITIVE_INFINITY) {
			double center = (max + min) / 2.0;
			double o = nextDouble(0.0, max - center);
			return nextBoolean() ? center + o : center - o;
		}
		double x = nextDouble();
		return (max - min) * x + min;
	}

	/**
	 * Generates a uniformly random long value within the given range.
	 *
	 * @param range the range
	 * @return the generated long value.
	 */
	public static long nextLong(Range<Long> range) {
		return nextLong(//
				range.getMinimum() == null ? Long.MIN_VALUE : range.getMinimum(),//
				range.getMaximum() == null ? Long.MAX_VALUE : range.getMaximum());
	}

	/**
	 * Generates a uniformly random long value within the given range.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the generated long value.
	 */
	public static long nextLong(long min, long max) {
		long r = max - min;
		if (subtractionOverflowed(max, min, r)) {
			long s = max + min;
			long center = s / 2;
			long o = nextLong(0, max - center);
			if (s % 2 == 0) {
				return nextBoolean() ? center + o : center - (o + 1);
			} else {
				// not strictly uniform, since the center value is twice is
				// likely to be picked, but within a large range, the likelihood
				// difference becomes negligible
				return nextBoolean() ? center + o : center - o;
			}
		}
		double x = nextDouble();
		return (long) (r * x + min);
	}

	/** sign bit in 2's complement longs. */
	private static final long	SIGN_BIT	= (1l << Long.SIZE - 1);

	/**
	 * Subtraction overflowed.
	 *
	 * @param original the original
	 * @param other the other
	 * @param result the result
	 * @return true, if successful
	 * @see
	 * <a href="
	 * http://grepcode.com/file/repo1.maven.org/maven2/org.jruby/jruby-core/1.6.3/org/jruby/RubyFixnum.java#RubyFixnum.subtractionOverflowed%28long%2Clong%2Clong%29
	 * ">
	 * org.jruby.RubyFixnum.subtractionOverflowed(long, long, long)
	 * </a>
	 */
	private static boolean subtractionOverflowed(long original, long other, long result) {
		return (~(original ^ ~other) & (original ^ result) & SIGN_BIT) != 0;
	}

	/**
	 * Generates a uniformly random integer value within the given range.
	 *
	 * @param range the range
	 * @return the generated integer value.
	 */
	public static int nextInteger(Range<Integer> range) {
		return nextInteger(//
				range.getMinimum() == null ? Integer.MIN_VALUE : range.getMinimum(),//
				range.getMaximum() == null ? Integer.MAX_VALUE : range.getMaximum());
	}

	/**
	 * Generates a uniformly random integer value within the given range.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the generated integer value.
	 */
	public static int nextInteger(int min, int max) {
		return (int) nextLong(min, max);
	}

	/**
	 * Generates a uniformly random short value within the given range.
	 *
	 * @param range the range
	 * @return the generated short value.
	 */
	public static short nextShort(Range<Short> range) {
		return nextShort(//
				range.getMinimum() == null ? Short.MIN_VALUE : range.getMinimum(),//
				range.getMaximum() == null ? Short.MAX_VALUE : range.getMaximum());
	}

	/**
	 * Generates a uniformly random short value within the given range.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the generated short value.
	 */
	public static short nextShort(short min, short max) {
		return (short) nextLong(min, max);
	}

	/**
	 * Generates a uniformly random byte value within the given range.
	 *
	 * @param range the range
	 * @return the generated byte value.
	 */
	public static byte nextByte(Range<Byte> range) {
		return nextByte(//
				range.getMinimum() == null ? Byte.MIN_VALUE : range.getMinimum(),//
				range.getMaximum() == null ? Byte.MAX_VALUE : range.getMaximum());
	}

	/**
	 * Generates a uniformly random byte value within the given range.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the generated byte value.
	 */
	public static byte nextByte(byte min, byte max) {
		return (byte) nextLong(min, max);
	}

	/**
	 * Generates a uniformly random float value within the given range.
	 *
	 * @param range the range
	 * @return the generated float value.
	 */
	public static float nextFloat(Range<Float> range) {
		return nextFloat(//
				range.getMinimum() == null ? -Float.MAX_VALUE : range.getMinimum(),//
				range.getMaximum() == null ? Float.MAX_VALUE : range.getMaximum());
	}

	/**
	 * Generates a uniformly random float value within the given range.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the generated float value.
	 */
	public static float nextFloat(float min, float max) {
		return (float) nextDouble(min, max);
	}

	/*
	 * Gaussian random value generators
	 */
	/**
	 * Generates a random double drawn from Gaussian (0.0, 1.0).
	 *
	 * @return the double
	 */
	public static double nextGaussian() {
		return RANDOM.nextGaussian();
	}

	/*
	 * ranged Gaussian random value generators
	 */
	/**
	 * Generates a random double from the (nearly) Gaussian distribution with
	 * the given mu and sigma, within the range [-1,1].
	 *
	 * @param mu the mu
	 * @param sigma the sigma
	 * @return the generated double.
	 */
	public static double nextGaussian(double mu, double sigma) {
		return nextGaussian() * sigma + mu;
	}

	/**
	 * Generates a random double from the (nearly) Gaussian distribution with
	 * the given mu and sigma, within the range [min,max].
	 *
	 * @param mu the mu
	 * @param sigma the sigma
	 * @param min the min
	 * @param max the max
	 * @return the generated double.
	 */
	public static double nextGaussian(double mu, double sigma, double min, double max) {
		double d = 0.0;
		Range<Double> range = Range.getInstance(min, max);
		final int RETRIES = 10;
		int counter = 0;
		do {
			d = nextGaussian(mu, sigma);
		} while (counter++ < RETRIES && !range.contains(d));
		return range.vet(d);
	}

	/*
	 * random adjusters
	 */
	/**
	 * Adjusts the given double by adding a uniformly random generated double
	 * drawn from the [-1,1].
	 *
	 * @param parameter the parameter
	 * @return the adjusted value
	 */
	public static double randomlyAdjustParameterUniform(double parameter) {
		return randomlyAdjustParameterUniform(parameter, Range.getInstance((Double) null, null), 1.0);
	}

	/**
	 * Adjusts the given double by adding a uniformly random generated double
	 * drawn from the [-1,1]. The adjusted value is within the given range.
	 *
	 * @param parameter the parameter
	 * @param range the range
	 * @return the adjusted value
	 */
	public static double randomlyAdjustParameterUniform(double parameter, Range<Double> range) {
		return randomlyAdjustParameterUniform(parameter, range, 1.0);
	}

	/**
	 * Adjusts the given double by adding a uniformly random generated double
	 * drawn from the [-1,1] times the given perturbFactor. The adjusted value
	 * is within the given range.
	 *
	 * @param parameter the parameter
	 * @param range the range
	 * @param perturbFactor the perturb factor
	 * @return the adjusted value
	 */
	public static double randomlyAdjustParameterUniform(double parameter, Range<Double> range, double perturbFactor) {
		double next = 0.0;
		final int RETRIES = 10;
		int counter = 0;
		do {
			next = parameter + nextDouble(-1, 1) * perturbFactor;
		} while (counter++ < RETRIES && !range.contains(next));
		return range.vet(next);
	}

	/**
	 * Adjusts the given double by adding a randomly generated double from the
	 * Gaussian (0,1/3).
	 *
	 * @param parameter the parameter
	 * @return the adjusted value
	 */
	public static double randomlyAdjustParameterGaussian(double parameter) {
		return randomlyAdjustParameterGaussian(parameter, Range.getInstance((Double) null, null), 1.0);
	}

	/**
	 * Adjusts the given double by adding a randomly generated double from the
	 * Gaussian (0,1/3). The adjusted value is within the given range.
	 *
	 * @param parameter the parameter
	 * @param range the range
	 * @return the adjusted value
	 */
	public static double randomlyAdjustParameterGaussian(double parameter, Range<Double> range) {
		return randomlyAdjustParameterGaussian(parameter, range, 1.0);
	}

	/**
	 * Adjusts the given double by adding a randomly generated double from the
	 * Gaussian (0,1/3) times the given perturbFactor. The adjusted value is
	 * within the given range.
	 *
	 * @param parameter the parameter
	 * @param range the range
	 * @param perturbFactor the perturb factor
	 * @return the adjusted value
	 */
	public static double randomlyAdjustParameterGaussian(double parameter, Range<Double> range, double perturbFactor) {
		double next = 0.0;
		final int RETRIES = 10;
		int counter = 0;
		do {
			next = parameter + nextGaussian(0.0, 1.0 / 3.0) * perturbFactor;
		} while (counter++ < RETRIES && !range.contains(next));
		return range.vet(next);
	}

	/*
	 * random selecters
	 */
	/**
	 * Alters the given array of weights so that their sum is 1.0 while
	 * maintaining their relative proportions (i.e., divide each by the sum
	 * weight).
	 * 
	 * @param weights
	 *            the array to normalize
	 * @return true if the weights were adjusted, false if any weight is
	 *         negative or their sum is 0.0.
	 */
	public static boolean normalizeWeights(double[] weights) {
		return normalizeWeights(weights, 0.0);
	}

	/**
	 * Normalize weights.
	 *
	 * @param weights the weights
	 * @param smoothingFactor the smoothing factor
	 * @return true, if successful
	 */
	public static boolean normalizeWeights(double[] weights, double smoothingFactor) {
		double sumWeights = 0.0;
		for (double weight : weights) {
			if (weight < 0) {
				return false;
			}
			sumWeights += weight;
		}
		if (sumWeights == 0.0) {
			return false;
		}
		double smooth = sumWeights * smoothingFactor;
		sumWeights += weights.length * smooth;
		for (int i = 0; i < weights.length; i++) {
			weights[i] = (weights[i] + smooth) / sumWeights;
		}
		return true;
	}

	/**
	 * Performs a roulette selection on the given weight array, started with the
	 * given index. It is assumed that the given weight array is non-negative
	 * and has sum 1.0, as produced by {@link #normalizeWeights(double[])}. If
	 * the array is not normalized, this method may not terminate.
	 *
	 * @param index the index on which roulette selection begins
	 * @param normalizedWeights the weights used for roulette selection
	 * @return the selected index
	 */
	public static int rouletteSelect(int index, double[] normalizedWeights) {
		validateArg("normalizedWeights", normalizedWeights, isNotNull());
		validateArg("index", index, isGreaterThanOrEqualTo(0), isLessThan(normalizedWeights.length));
		double target = nextDouble();
		while (true) {
			if (target <= normalizedWeights[index]) {
				return index;
			}
			target -= normalizedWeights[index];
			index = (index + 1) % normalizedWeights.length;
		}
	}

	/**
	 * Performs a roulette selection on the given weight array, started with the
	 * given index. The given weight array is normalized here, and if any weight
	 * is negative or their sum is 0.0, an index is selected uniformly.
	 *
	 * @param index the index on which roulette selection begins
	 * @param weights the weights used for roulette selection
	 * @return the selected index
	 */
	public static int rouletteSelectUnnormalized(int index, double[] weights) {
		validateArg("weights", weights, isNotNull());
		validateArg("index", index, isGreaterThanOrEqualTo(0), isLessThan(weights.length));
		double sumWeights = 0.0;
		boolean negativeFlag = false;
		for (double weight : weights) {
			if (weight < 0) {
				negativeFlag = true;
				break;
			}
			sumWeights += weight;
		}
		if (negativeFlag && sumWeights == 0.0) {
			return nextInteger(weights.length);
		}
		double target = nextDouble(0.0, sumWeights);
		while (true) {
			if (target <= weights[index]) {
				return index;
			}
			target -= weights[index];
			index = (index + 1) % weights.length;
		}
	}

	/**
	 * Normalize weights.
	 *
	 * @param <V> the value type
	 * @param <N> the number type
	 * @param weights the map to normalize
	 * @return the map
	 */
	public static <V, N extends Number> Map<V, Double> normalizeWeights(Map<V, N> weights) {
		return normalizeWeights(weights, 0.0);
	}

	/**
	 * Normalize weights.
	 *
	 * @param <V> the value type
	 * @param <N> the number type
	 * @param weights the weights
	 * @param smoothingFactor the smoothing factor
	 * @return the map
	 */
	public static <V, N extends Number> Map<V, Double> normalizeWeights(Map<V, N> weights, double smoothingFactor) {
		// validateArg("weights", weights, isNotNull(), hasValues(-1,
		// isNotNull(), isGreaterThanOrEqualTo(0.0)));
		double sumWeights = 0.0;
		for (N n : weights.values()) {
			sumWeights += n.doubleValue();
		}
		if (sumWeights == 0.0) {
			return null;
		}
		double smooth = sumWeights * smoothingFactor;
		sumWeights += weights.size() * smooth;
		Map<V, Double> newWeights = new HashMap<V, Double>();
		for (V v : weights.keySet()) {
			newWeights.put(v, (weights.get(v).doubleValue() + smooth) / sumWeights);
		}
		return newWeights;
	}

	/**
	 * Performs a roulette selection on the given weight map. It is assumed that
	 * the values of the given map are normalized, i.e., are non-negative and
	 * have sum 1.0. If the values are not, the behavior of this method is
	 * undefined.
	 *
	 * @param <V> the value type
	 * @param normalizedWeights the weights used for roulette selection
	 * @return the selected key
	 */
	public static <V> V rouletteSelect(Map<V, Double> normalizedWeights) {
		validateArg("weights", normalizedWeights, isNotNull());
		double targetWeight = nextDouble();
		for (V v : normalizedWeights.keySet()) {
			targetWeight -= normalizedWeights.get(v);
			if (targetWeight <= 0) {
				return v;
			}
		}
		throw new IllegalArgumentException("weights are non-positive or sum to less than 1.0");
	}

	/**
	 * Roulette select unnormalized.
	 *
	 * @param <V> the value type
	 * @param <N> the number type
	 * @param weights the weights used for roulette selection
	 * @return the selected key
	 */
	public static <V, N extends Number> V rouletteSelectUnnormalized(Map<V, N> weights) {
		validateArg("weights", weights, isNotNull());
		double maxWeight = 0.0;
		for (N n : weights.values()) {
			maxWeight += n.doubleValue();
		}
		if (maxWeight == 0.0) {
			return select(weights.keySet());
		}
		double targetWeight = nextDouble(0, maxWeight);
		for (V v : weights.keySet()) {
			targetWeight -= weights.get(v).doubleValue();
			if (targetWeight <= 0) {
				return v;
			}
		}
		throw new IllegalArgumentException("weights are non-positive or sum to less than 1.0");
	}

	/**
	 * Selects an uniformly random object from the given collection.
	 *
	 * @param <T> the type of object being selected
	 * @param objects the objects
	 * @return the selected object
	 */
	public static <T> T select(Collection<? extends T> objects) {
		return select(objects, Collections.<T> emptyList());
	}

	/**
	 * Selects an uniformly random object from the given collection, minus those
	 * in the ignores.
	 *
	 * @param <T> the type of object being selected
	 * @param objects the objects
	 * @param ignores the ignores
	 * @return the selected object
	 */
	public static <T> T select(Collection<? extends T> objects, T... ignores) {
		return select(objects, Arrays.asList(ignores));
	}

	/**
	 * Selects an uniformly random object from the given collection, minus those
	 * in the ignores.
	 *
	 * @param <T> the type of object being selected
	 * @param objects the objects
	 * @param ignores the ignores
	 * @return the selected object
	 */
	public static <T> T select(Collection<? extends T> objects, Collection<? extends T> ignores) {
		List<T> ok = new ArrayList<T>(objects);
		ok.removeAll(ignores);
		if (ok.size() == 0) {
			throw new IllegalArgumentException("cannot select one from no objects");
		}
		return ok.get(nextInteger(ok.size()));
	}

	/*
	 * misc random methods
	 */
	/**
	 * Creates a new shuffled list containing the objects in the given
	 * collection.
	 *
	 * @param <T> the type of objects being shuffled
	 * @param objects the objects
	 * @return the new shuffled list
	 */
	public static <T> List<T> shuffle(Collection<T> objects) {
		return shuffle(objects, Collections.<T> emptyList());
	}

	/**
	 * Creates a new shuffled list containing the objects in the given
	 * collection, minus those in the ignores.
	 *
	 * @param <T> the type of objects being shuffled
	 * @param objects the objects
	 * @param ignores the ignores
	 * @return the new shuffled list
	 */
	public static <T> List<T> shuffle(Collection<T> objects, T... ignores) {
		return shuffle(objects, Arrays.asList(ignores));
	}

	/**
	 * Creates a new shuffled list containing the objects in the given
	 * collection, minus those in the ignores.
	 *
	 * @param <T> the type of objects being shuffled
	 * @param objects the objects
	 * @param ignores the ignores
	 * @return the new shuffled list
	 */
	public static <T> List<T> shuffle(Collection<T> objects, Collection<T> ignores) {
		List<T> ok = new ArrayList<T>(objects);
		ok.removeAll(ignores);
		Collections.shuffle(ok, RANDOM);
		return ok;
	}

	/**
	 * Checks if the event with the given probability occurs.
	 *
	 * @param eventProbability the event probability
	 * @return true if the event occurs, otherwise false.
	 */
	public static boolean eventOccurs(double eventProbability) {
		return nextDouble() < eventProbability;
	}
}
