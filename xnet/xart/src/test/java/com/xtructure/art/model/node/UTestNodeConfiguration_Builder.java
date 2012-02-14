/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.model.node;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.node.Node.Fragment;
import com.xtructure.art.model.node.NodeConfiguration.Builder;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.config.RangeXParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xart" })
public class UTestNodeConfiguration_Builder {
	private static final Object[][]			FLAGS;
	private static final Range<?>[]			RANGES;
	private static final NodeConfiguration	TEMPLATE;
	static {
		List<Boolean> addFlags = new ArrayList<Boolean>();
		for (int i = 0; i < Fragment.values().length; i++) {
			addFlags.add(RandomUtil.nextBoolean());
		}
		List<List<Boolean>> testFlags = new ArrayList<List<Boolean>>();
		for (int i = 0; i < Fragment.values().length; i++) {
			Collections.rotate(addFlags, 1);
			testFlags.add(new ArrayList<Boolean>(addFlags));
		}
		FLAGS = TestUtils.createData(testFlags.toArray());
		List<Range<?>> ranges = new ArrayList<Range<?>>();
		for (Fragment fragment : Fragment.values()) {
			switch (fragment) {
				case DOES_INVERT:
					ranges.add(Range.getInstance(false, true));
					ranges.add(Range.getInstance(false, true));
					break;
				case DELAY:
				case OSCILLATION_OFFSET:
				case OSCILLATION_PERIOD:
					ranges.add(Range.getInstance(RandomUtil.nextInteger()));
					ranges.add(Range.getInstance(RandomUtil.nextInteger()));
					break;
				default:
					ranges.add(Range.getInstance(RandomUtil.nextFloat()));
					ranges.add(Range.getInstance(RandomUtil.nextFloat()));
					break;
			}
		}
		RANGES = ranges.toArray(new Range<?>[0]);
		TEMPLATE = NodeConfiguration//
				.builder()//
				.setDelay(Range.getInstance(0), null)//
				.setInvertFlag(Range.<Boolean> openRange(), null)//
				.setEnergy(Range.<Float> openRange(), null)//
				.setEnergyDecay(Range.<Float> openRange(), null)//
				.setExcitatoryScale(Range.<Float> openRange(), null)//
				.setInhibitoryScale(Range.<Float> openRange(), null)//
				.setOscillationMaximum(Range.<Float> openRange(), null)//
				.setOscillationMinimum(Range.<Float> openRange(), null)//
				.setOscillationOffset(Range.<Integer> openRange(), null)//
				.setOscillationPeriod(Range.<Integer> openRange(), null)//
				.setScale(Range.<Float> openRange(), null)//
				.setShift(Range.<Float> openRange(), null)//
				.setTwitchMaximum(Range.<Float> openRange(), null)//
				.setTwitchMinimum(Range.<Float> openRange(), null)//
				.setTwitchProbability(Range.<Float> openRange(), null)//
				.newInstance();
	}

	@DataProvider
	public Object[][] flags() {
		return FLAGS;
	}

	public void constructorSucceeds() {
		assertThat("",//
				NodeConfiguration.builder(), isNotNull());
		assertThat("",//
				NodeConfiguration.builder(XId.newId()), isNotNull());
	}

	@Test(dataProvider = "flags")
	public void setXAndGetConfigurationBehaveAsExpeced(List<Boolean> flags) {
		// builder with no template
		XId nodeConfigId = XId.newId();
		Builder builder = NodeConfiguration.builder(nodeConfigId);
		for (Fragment fragment : Fragment.values()) {
			setConfigParameter(flags, builder, fragment);
		}
		NodeConfiguration nodeConfig = builder.newInstance();
		assertThat("",//
				nodeConfig.getId(), isEqualTo(nodeConfigId));
		for (Fragment fragment : Fragment.values()) {
			testConfigsParameter(flags, nodeConfig, fragment, NodeConfiguration.DEFAULT_CONFIGURATION);
		}
		// builder with template
		nodeConfigId = XId.newId();
		builder = NodeConfiguration.builder(nodeConfigId).setTemplate(TEMPLATE);
		for (Fragment fragment : Fragment.values()) {
			setConfigParameter(flags, builder, fragment);
		}
		nodeConfig = builder.newInstance();
		assertThat("",//
				nodeConfig.getId(), isEqualTo(nodeConfigId));
		for (Fragment fragment : Fragment.values()) {
			testConfigsParameter(flags, nodeConfig, fragment, TEMPLATE);
		}
	}

	@SuppressWarnings("unchecked")
	private void setConfigParameter(List<Boolean> flags, Builder builder, Fragment fragment) {
		int index = fragment.ordinal();
		if (flags.get(index)) {
			Builder b = null;
			switch (fragment) {
				case DELAY:
					b = builder.setDelay(//
							(Range<Integer>) RANGES[2 * index],//
							(Range<Integer>) RANGES[2 * index + 1]);
					break;
				case ENERGY_DECAY:
					b = builder.setEnergyDecay(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case ENERGY:
					b = builder.setEnergy(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case EXCITATORY_SCALE:
					b = builder.setExcitatoryScale(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case INHIBITORY_SCALE:
					b = builder.setInhibitoryScale(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case DOES_INVERT:
					b = builder.setInvertFlag(//
							(Range<Boolean>) RANGES[2 * index],//
							(Range<Boolean>) RANGES[2 * index + 1]);
					break;
				case OSCILLATION_MAXIMUM:
					b = builder.setOscillationMaximum(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case OSCILLATION_MINIMUM:
					b = builder.setOscillationMinimum(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case OSCILLATION_OFFSET:
					b = builder.setOscillationOffset(//
							(Range<Integer>) RANGES[2 * index],//
							(Range<Integer>) RANGES[2 * index + 1]);
					break;
				case OSCILLATION_PERIOD:
					b = builder.setOscillationPeriod(//
							(Range<Integer>) RANGES[2 * index],//
							(Range<Integer>) RANGES[2 * index + 1]);
					break;
				case SCALE:
					b = builder.setScale(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case SHIFT:
					b = builder.setShift(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case TWITCH_MAXIMUM:
					b = builder.setTwitchMaximum(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case TWITCH_MINIMUM:
					b = builder.setTwitchMinimum(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case TWITCH_PROBABILITY:
					b = builder.setTwitchProbability(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				default:
					// nothing to set, just assign for post set test
					b = builder;
					break;
			}
			assertThat("",//
					b, isSameAs(builder));
		}
	}

	private void testConfigsParameter(List<Boolean> flags, NodeConfiguration nodeConfig, Fragment fragment, NodeConfiguration template) {
		if (!Fragment.ENERGIES.equals(fragment)) {
			int index = fragment.ordinal();
			if (flags.get(index)) {
				assertThat(fragment.getParameterId().toString(),//
						((RangeXParameter<?>) nodeConfig.getParameter(fragment.getParameterId())).getLifetimeRange(),//
						isEqualTo(RANGES[2 * index]));
				assertThat(fragment.getParameterId().toString(),//
						((RangeXParameter<?>) nodeConfig.getParameter(fragment.getParameterId())).getInitialRange(),//
						isEqualTo(RANGES[2 * index + 1]));
			} else {
				assertThat(fragment.getParameterId().toString(),//
						((RangeXParameter<?>) nodeConfig.getParameter(fragment.getParameterId())).getLifetimeRange(),//
						isEqualTo(((RangeXParameter<?>) template.getParameter(fragment.getParameterId())).getLifetimeRange()));
				assertThat(fragment.getParameterId().toString(),//
						((RangeXParameter<?>) nodeConfig.getParameter(fragment.getParameterId())).getInitialRange(),//
						isEqualTo(((RangeXParameter<?>) template.getParameter(fragment.getParameterId())).getInitialRange()));
			}
		}
	}
}
