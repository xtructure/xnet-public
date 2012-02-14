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

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThan;
import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isLessThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The Class BinaryIndexedTree.
 *
 * @author Luis Guimbarda
 */
public final class BinaryIndexedTree {
	
	/** The Constant DEFAULT_SIZE. */
	private static final int	DEFAULT_SIZE	= 1 << 4;
	
	/** maximum available index. */
	private int					maxIndex;
	
	/** array of tree frequencies. */
	private long[]				tree;
	
	/** array of individual frequencies. */
	private long[]				freq;

	/**
	 * Creates a new {@link BinaryIndexedTree} with {@link #DEFAULT_SIZE}.
	 */
	public BinaryIndexedTree() {
		this(DEFAULT_SIZE);
	}

	/**
	 * Creates a new {@link BinaryIndexedTree} with max index the smallest power
	 * of 2 larger than the given size.
	 * 
	 * @param size the size
	 * @throws IllegalArgumentException
	 *             if size is not greater than 0
	 */
	public BinaryIndexedTree(int size) {
		validateArg("size", size, isGreaterThan(0));
		this.maxIndex = Integer.bitCount(size) == 1 ? size : Integer.highestOneBit(size) << 1;
		this.tree = new long[maxIndex + 1];
		this.freq = new long[maxIndex + 1];
	}

	/**
	 * If this {@link BinaryIndexedTree} doesn't maintain a frequency for the
	 * given index, it is expanded to do so.
	 * 
	 * @param index the index
	 */
	public void ensureIndex(int index) {
		if (maxIndex < index) {
			maxIndex <<= 1;
			long[] newTree = new long[maxIndex + 1];
			long[] newFreq = new long[maxIndex + 1];
			System.arraycopy(tree, 0, newTree, 0, tree.length);
			System.arraycopy(freq, 0, newFreq, 0, freq.length);
			newTree[maxIndex] = newTree[maxIndex >> 1];
			tree = newTree;
			freq = newFreq;
		}
	}

	/**
	 * Returns the highest index whose frequency is maintained by this.
	 *
	 * @return the max index.
	 */
	public int getMaxIndex() {
		return maxIndex;
	}

	/**
	 * Returns the sum of all frequency counts in this {@link BinaryIndexedTree}
	 * .
	 *
	 * @return the total frequency
	 */
	public long getTotalFrequency() {
		return tree[maxIndex];
	}

	/**
	 * Returns the cumulative frequency at the given index.
	 *
	 * @param index the index
	 * @return the frequency
	 */
	public long getFrequency(int index) {
		return freq[index];
	}

	/**
	 * Returns the cumulative frequency up to the given index, inclusive.
	 *
	 * @param index the index
	 * @return the cumulative frequency
	 */
	public long getCumulativeFrequency(int index) {
		long sum = 0;
		int i = index;
		while (i > 0) {
			sum += tree[i];
			i -= Integer.lowestOneBit(i);
		}
		return sum;
	}

	/**
	 * Returns the highest index with the given cumulative frequency (i.e., the
	 * last index with non-zero frequency). If no index has exactly the given
	 * frequency, then if x is the index with the next highest cumulative
	 * frequency, -(x+1) is returned.
	 * 
	 * @param cumulativeFrequency the cumulative frequency
	 * @return the index
	 */
	public int getIndex(long cumulativeFrequency) {
		validateArg("cumulativeFrequency", cumulativeFrequency, isGreaterThanOrEqualTo(0l), isLessThanOrEqualTo(tree[maxIndex]));
		long sum = cumulativeFrequency;
		int index = 0;
		int bitmask = maxIndex;
		while (bitmask > 0 && index < maxIndex) {
			int i = index + bitmask;
			if (sum >= tree[i]) {
				index = i;
				sum -= tree[i];
			}
			bitmask >>= 1;
		}
		if (sum > 0) {
			return -(index + 1);
		}
		return index;
	}

	/**
	 * Updates the frequency of the given index in this
	 * {@link BinaryIndexedTree} by adding the given count.
	 * 
	 * @param index the index
	 * @param count the count
	 * @return the new frequency at the given index
	 * @throws IllegalArgumentException
	 *             if update would introduce a negative frequency to this
	 *             {@link BinaryIndexedTree}
	 */
	public long update(int index, long count) {
		validateArg("index", index, isGreaterThanOrEqualTo(1), isLessThanOrEqualTo(maxIndex));
		validateArg("count+freq[index]", count + freq[index], isGreaterThanOrEqualTo(0l));
		freq[index] += count;
		int i = index;
		while (i <= maxIndex) {
			tree[i] += count;
			i += Integer.lowestOneBit(i);
		}
		return freq[index];
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)//
				.append("freq", freq)//
				.append("tree", tree)//
				.toString();
	}
}
