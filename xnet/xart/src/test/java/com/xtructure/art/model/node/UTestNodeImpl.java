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
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.node.Node.Energies;
import com.xtructure.art.model.node.Node.Fragment;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.config.FloatXParameter;
import com.xtructure.xutil.config.RangeXParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.TestUtils;

@Test(groups = { "unit:xart" })
public class UTestNodeImpl {
	private static final String				NODE_BASE;
	private static final XId				NODE_ID;
	private static final NodeConfiguration	TEST_CONFIG;
	private static final NodeConfiguration	COVER_CONFIG;
	private static final Object[][]			INPUTS;
	private static final Object[][]			VALID_IDS;
	static {
		NODE_BASE = "node";
		NODE_ID = XId.newId(NODE_BASE);
		Range<Float> nonZeroPositiveFloatRange = Range.getInstance(Float.MIN_VALUE, Float.MAX_VALUE);
		Range<Float> onePointFloatRange = Range.getInstance(1.0f, 1.0f);
		Range<Integer> nonZeroPositiveIntegerRange = Range.getInstance(1, Integer.MAX_VALUE);
		Range<Integer> onePointIntegerRange = Range.getInstance(1, 1);
		TEST_CONFIG = NodeConfiguration.builder()//
				.setDelay(nonZeroPositiveIntegerRange, onePointIntegerRange)//
				.setInvertFlag(Range.TRUE_BOOLEAN_RANGE, null)//
				.setEnergy(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setEnergyDecay(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setExcitatoryScale(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setInhibitoryScale(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setOscillationMaximum(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setOscillationMinimum(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setOscillationOffset(nonZeroPositiveIntegerRange, onePointIntegerRange)//
				.setOscillationPeriod(nonZeroPositiveIntegerRange, onePointIntegerRange)//
				.setScale(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setShift(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setTwitchMaximum(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setTwitchMinimum(nonZeroPositiveFloatRange, onePointFloatRange)//
				.setTwitchProbability(nonZeroPositiveFloatRange, onePointFloatRange)//
				.newInstance();
		Range<Float> zeroFloat = Range.getInstance(0.0f, 0.0f);
		Range<Float> pointOneFloat = Range.getInstance(0.1f, 0.1f);
		Range<Float> pointNineFloat = Range.getInstance(0.9f, 0.9f);
		Range<Integer> oneInteger = Range.getInstance(1, 1);
		COVER_CONFIG = NodeConfiguration.builder()//
				.setTemplate(TEST_CONFIG)//
				.setOscillationMinimum(pointOneFloat, null)//
				.setOscillationMaximum(pointNineFloat, null)//
				.setOscillationPeriod(oneInteger, null)//
				.setTwitchProbability(zeroFloat, null)//
				.newInstance();
		INPUTS = TestUtils.createData(//
				Collections.<Float> emptyList(),//
				Arrays.asList(0.0f),//
				Arrays.asList(1.0f),//
				Arrays.asList(-1.0f),//
				Arrays.asList(-0.5f, 0.5f),//
				Arrays.asList(-0.1f, 0.5f),//
				Arrays.asList(-0.5f, 0.1f));
		VALID_IDS = TestUtils.createData(//
				XId.newId(NODE_BASE, 0, 1, 2, -1),//
				NODE_ID.createChild(0),//
				NODE_ID);
	}

	@DataProvider
	public Object[][] validIds() {
		return VALID_IDS;
	}

	@DataProvider
	public Object[][] inputs() {
		return INPUTS;
	}

	@Test(dataProvider = "validIds")
	public final void constructorSucceeds(XId id) {
		assertThat("",//
				new NodeImpl(id, TEST_CONFIG), isNotNull());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void constructorWithNullIdThrowsException() {
		new NodeImpl(null, TEST_CONFIG);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void constructorWithNullConfigurationThrowsNullException() {
		new NodeImpl(NODE_ID, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void getAndSetFieldsBehaveAsExpected() {
		Node node = new NodeImpl(NODE_ID, TEST_CONFIG);
		for (Fragment fragment : Fragment.values()) {
			switch (fragment) {
				case ENERGY: {
					break;
				}
				case ENERGIES: {
					FloatXParameter parameter = (FloatXParameter) TEST_CONFIG.getParameter(Fragment.ENERGY.getParameterId());
					Energies energies = Fragment.ENERGIES.getValue(node);
					assertThat(fragment.toString(),//
							parameter.getInitialRange().contains(energies.getFrontEnergy()),//
							isTrue());
					assertThat(fragment.toString(),//
							parameter.getInitialRange().contains(energies.getBackEnergy()),//
							isTrue());
					Energies newEnergies = new Energies(parameter.newUniformRandomValue(), parameter.newUniformRandomValue());
					fragment.setValue(node, newEnergies);
					assertThat("",//
							fragment.getValue(node), isEqualTo(newEnergies));
					break;
				}
				default: {
					RangeXParameter parameter = (RangeXParameter) TEST_CONFIG.getParameter(fragment.getParameterId());
					assertThat(fragment.toString(),//
							parameter.getInitialRange().contains((Comparable) fragment.getValue(node)),//
							isTrue());
					Object value = parameter.newUniformRandomValue();
					fragment.setValue(node, value);
					assertThat("",//
							fragment.getValue(node), isEqualTo(value));
				}
			}
		}
	}

	public final void toStringReturnsExpectedString() {
		NodeImpl node = new NodeImpl(NODE_ID, TEST_CONFIG);
		String got = node.toString();
		String expected = String.format(//
				"node %s; energy=(%s -%f); input (+%f, -%f); osc (@%d+%d, [%f .. %f]); twitch (%f [%f .. %f]); bb ~%d +%f *%f inv? %b",
				//
				node.getId(), //
				node.getEnergies(), node.getEnergyDecay(), //
				node.getExcitatoryScale(), node.getInhibitoryScale(), //
				node.getOscillationOffset(), node.getOscillationPeriod(), //
				node.getOscillationMinimum(), node.getOscillationMaximum(), //
				node.getTwitchProbability(), //
				node.getTwitchMinimum(), node.getTwitchMaximum(), //
				node.getDelay(), node.getShift(), node.getScale(), node.doesInvert());
		assertThat("",//
				got, isEqualTo(expected));
	}

	@Test(dataProvider = "inputs")
	public final void calculateAndUpdateSucceed(List<Float> inputs) {
		NodeImpl node = new NodeImpl(NODE_ID, TEST_CONFIG);
		Float expectedBackEnergy = calculate(inputs, node);
		Float initBackEnergy = node.getEnergies().getBackEnergy();
		node.calculate(inputs);
		Float interBackEnergy = node.getEnergies().getBackEnergy();
		node.update();
		Float finalBackEnergy = node.getEnergies().getBackEnergy();
		assertThat("calculate doesn't affect back energies",//
				initBackEnergy, isEqualTo(interBackEnergy));
		assertThat("update changes back energies",//
				finalBackEnergy, isEqualTo(expectedBackEnergy));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void calculateWithNullInputsThrowsException() {
		new NodeImpl(NODE_ID, TEST_CONFIG).calculate(null);
	}

	@Test(dataProvider = "inputs")
	public final void calculateWithOscillationSucceeds(List<Float> inputs) {
		NodeImpl node = new NodeImpl(NODE_ID, COVER_CONFIG);
		for (int i = 0; i < node.getOscillationPeriod() + node.getOscillationOffset(); i++) {
			node.calculate(inputs);
		}
	}

	@SuppressWarnings("unchecked")
	private static Float calculate(List<Float> list, final NodeImpl node) {
		Float nextEnergy = node.getEnergies().getBackEnergy() * node.getEnergyDecay();
		for (Float f : list) {
			if (f < 0f) {
				nextEnergy += f * node.getInhibitoryScale();
			} else {
				nextEnergy += f * node.getExcitatoryScale();
			}
		}
		nextEnergy += node.getShift();
		nextEnergy *= node.getScale();
		nextEnergy++; // twitch always occurs with test config
		if (node.doesInvert()) {
			final Range<Float> r = ((RangeXParameter<Float>) node.getConfiguration().getParameter(Node.ENERGY_ID)).getLifetimeRange();
			nextEnergy = r.getMaximum() - nextEnergy + r.getMinimum();
		}
		return nextEnergy;
	}
}
