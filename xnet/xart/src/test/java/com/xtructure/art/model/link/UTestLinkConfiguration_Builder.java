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
package com.xtructure.art.model.link;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.link.Link.Fragment;
import com.xtructure.art.model.link.LinkConfiguration.Builder;
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
public class UTestLinkConfiguration_Builder {
	private static final Object[][]			FLAGS;
	private static final Range<?>[]			RANGES;
	private static final LinkConfiguration	TEMPLATE;
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
				case IS_INHIBITORY:
					ranges.add(Range.getInstance(false, true));
					ranges.add(Range.getInstance(false, true));
					break;
				default:
					ranges.add(Range.getInstance(RandomUtils.nextFloat()));
					ranges.add(Range.getInstance(RandomUtils.nextFloat()));
					break;
			}
		}
		RANGES = ranges.toArray(new Range<?>[0]);
		TEMPLATE = LinkConfiguration//
				.builder()//
				.setCapacity(Range.<Float> openRange(), null)//
				.setCapacityAttack(Range.<Float> openRange(), null)//
				.setCapacityDecay(Range.<Float> openRange(), null)//
				.setStrength(Range.<Float> openRange(), null)//
				.setStrengthAttack(Range.<Float> openRange(), null)//
				.setStrengthDecay(Range.<Float> openRange(), null)//
				.setInhibitoryFlag(Range.<Boolean> openRange(), null)//
				.newInstance();
	}

	@DataProvider
	public Object[][] flags() {
		return FLAGS;
	}

	public void constructorSucceeds() {
		assertThat("",//
				LinkConfiguration.builder(), isNotNull());
		assertThat("",//
				LinkConfiguration.builder(XId.newId()), isNotNull());
	}

	@Test(dataProvider = "flags")
	public void setXAndGetConfigurationBehaveAsExpeced(List<Boolean> flags) {
		// builder with no template
		XId linkConfigId = XId.newId();
		Builder builder = LinkConfiguration.builder(linkConfigId);
		for (Fragment fragment : Fragment.values()) {
			setConfigParameter(flags, builder, fragment);
		}
		LinkConfiguration linkConfig = builder.newInstance();
		assertThat("",//
				linkConfig.getId(), isEqualTo(linkConfigId));
		for (Fragment fragment : Fragment.values()) {
			testConfigsParameter(flags, linkConfig, fragment, LinkConfiguration.DEFAULT_CONFIGURATION);
		}
		// builder with template
		linkConfigId = XId.newId();
		builder = LinkConfiguration.builder(linkConfigId).setTemplate(TEMPLATE);
		for (Fragment fragment : Fragment.values()) {
			setConfigParameter(flags, builder, fragment);
		}
		linkConfig = builder.newInstance();
		assertThat("",//
				linkConfig.getId(), isEqualTo(linkConfigId));
		for (Fragment fragment : Fragment.values()) {
			testConfigsParameter(flags, linkConfig, fragment, TEMPLATE);
		}
	}

	@SuppressWarnings("unchecked")
	private void setConfigParameter(List<Boolean> flags, Builder builder, Fragment fragment) {
		int index = fragment.ordinal();
		if (flags.get(index)) {
			Builder b = null;
			switch (fragment) {
				case CAPACITY:
					b = builder.setCapacity(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case CAPACITY_ATTACK:
					b = builder.setCapacityAttack(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case CAPACITY_DECAY:
					b = builder.setCapacityDecay(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case STRENGTH:
					b = builder.setStrength(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case STRENGTH_ATTACK:
					b = builder.setStrengthAttack(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case STRENGTH_DECAY:
					b = builder.setStrengthDecay(//
							(Range<Float>) RANGES[2 * index],//
							(Range<Float>) RANGES[2 * index + 1]);
					break;
				case IS_INHIBITORY:
					b = builder.setInhibitoryFlag(//
							(Range<Boolean>) RANGES[2 * index],//
							(Range<Boolean>) RANGES[2 * index + 1]);
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

	private void testConfigsParameter(List<Boolean> flags, LinkConfiguration linkConfig, Fragment fragment, LinkConfiguration template) {
		if (!Fragment.OUTPUT_ENERGY.equals(fragment)) {
			int index = fragment.ordinal();
			if (flags.get(index)) {
				assertThat(fragment.getParameterId().toString(),//
						((RangeXParameter<?>) linkConfig.getParameter(fragment.getParameterId())).getLifetimeRange(),//
						isEqualTo(RANGES[2 * index]));
				assertThat(fragment.getParameterId().toString(),//
						((RangeXParameter<?>) linkConfig.getParameter(fragment.getParameterId())).getInitialRange(),//
						isEqualTo(RANGES[2 * index + 1]));
			} else {
				assertThat(fragment.getParameterId().toString(),//
						((RangeXParameter<?>) linkConfig.getParameter(fragment.getParameterId())).getLifetimeRange(),//
						isEqualTo(((RangeXParameter<?>) template.getParameter(fragment.getParameterId())).getLifetimeRange()));
				assertThat(fragment.getParameterId().toString(),//
						((RangeXParameter<?>) linkConfig.getParameter(fragment.getParameterId())).getInitialRange(),//
						isEqualTo(((RangeXParameter<?>) template.getParameter(fragment.getParameterId())).getInitialRange()));
			}
		}
	}
}
