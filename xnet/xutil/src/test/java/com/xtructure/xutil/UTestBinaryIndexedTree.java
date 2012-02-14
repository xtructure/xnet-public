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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.testng.annotations.Test;

@Test(groups = { "unit:xutil" })
public class UTestBinaryIndexedTree {
	public void constructorSucceeds() {
		assertThat("",//
				new BinaryIndexedTree(), isNotNull());
		for (int i = 1; i < 16; i++) {
			assertThat("",//
					new BinaryIndexedTree(i), isNotNull());
		}
	}

	public void ensureIndexAndGetMaxIndexBehaveAsExpected() {
		BinaryIndexedTree bit = new BinaryIndexedTree(1);
		assertThat("",//
				bit.getMaxIndex(), isEqualTo(1));
		for (int i = 1; i < 100; i++) {
			int expected = Integer.bitCount(i) == 1 ? i : Integer.highestOneBit(i) << 1;
			bit.ensureIndex(i);
			assertThat("",//
					bit.getMaxIndex(), isEqualTo(expected));
		}
	}

	public void updateBehavesAsExpected() {
		int size = 8;
		BinaryIndexedTree bit = new BinaryIndexedTree(size);
		long[] freq = new long[size + 1];
		for (int i = 1; i <= size; i++) {
			freq[i] = RandomUtil.nextLong(4);
		}
		long[] cumm = new long[size + 1];
		for (int i = 1; i < freq.length; i++) {
			long updated = bit.update(i, freq[i]);
			assertThat("",//
					updated, isEqualTo(freq[i]));
		}
		int sum = updateCum(freq, cumm);
		test(size, bit, freq, cumm, sum);
		for (int i = 1; i <= size; i++) {
			int delta;
			if (freq[i] == 0) {
				delta = 1;
			} else {
				delta = RandomUtil.nextBoolean() ? 1 : -1;
			}
			long updated = bit.update(i, delta);
			freq[i] += delta;
			assertThat("",//
					updated, isEqualTo(freq[i]));
		}
		sum = updateCum(freq, cumm);
		test(size, bit, freq, cumm, sum);
	}

	private int updateCum(long[] freq, long[] cum) {
		int sum = 0;
		for (int i = 0; i < cum.length; i++) {
			sum += freq[i];
			cum[i] = sum;
		}
		return sum;
	}

	private void test(int size, BinaryIndexedTree bit, long[] freq, long[] cumm, long sum) {
		for (int i = 0; i <= size; i++) {
			assertThat("",//
					bit.getFrequency(i), isEqualTo(freq[i]));
			assertThat("",//
					bit.getCumulativeFrequency(i), isEqualTo(cumm[i]));
		}
		for (int c = 0; c <= sum; c++) {
			int expected = 0;
			while (cumm[expected] < c) {
				expected++;
			}
			if (cumm[expected] == c) {
				while (expected < size && freq[expected + 1] == 0) {
					expected++;
				}
			} else {
				expected *= -1;
			}
			assertThat("",//
					bit.getIndex(c), isEqualTo(expected));
		}
		assertThat("",//
				bit.getTotalFrequency(), isEqualTo(sum));
	}
}
