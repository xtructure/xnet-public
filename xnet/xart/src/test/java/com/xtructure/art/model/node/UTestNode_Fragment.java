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
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.node.Node.Energies;
import com.xtructure.art.model.node.Node.Fragment;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.config.BooleanXParameter;
import com.xtructure.xutil.config.FloatXParameter;
import com.xtructure.xutil.config.IntegerXParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.test.TestUtils;

@Test(groups = { "unit:xart" })
public class UTestNode_Fragment {
	private static final Object[][]	IDS;
	static {
		IDS = TestUtils.createData(//
				XId.newId(),//
				XId.newId(1, 2, 3),//
				XId.newId("string"),//
				XId.newId("string", 1, 2, 3));
	}

	@DataProvider
	public Object[][] ids() {
		return IDS;
	}

	public void constructorSucceeds() {
		for (Fragment frag : Fragment.values()) {
			assertThat("",//
					frag, isNotNull());
		}
	}

	@Test(dataProvider = "ids")
	public void getBaseIdReturnsExpectedObject(XId id) {
		assertThat("",//
				Fragment.getBaseId(null), isNull());
		assertThat("",//
				Fragment.getBaseId(id), isNull());
		assertThat("",//
				Fragment.getBaseId(XId.newId(id.getBase() + "#dummy", id.getInstanceNums())),//
				isNull());
		for (Fragment fragment : Fragment.values()) {
			assertThat("",//
					Fragment.getBaseId(fragment.generateExtendedId(id)),//
					isEqualTo(id));
		}
	}

	@Test(dataProvider = "ids")
	public void getInstanceReturnsExpectedObject(XId id) {
		assertThat("",//
				Fragment.getInstance((XId) null), isNull());
		assertThat("",//
				Fragment.getInstance((XValId<?>) null), isNull());
		assertThat("",//
				Fragment.getInstance(XValId.newId()), isNull());
		assertThat("",//
				Fragment.getInstance(id), isNull());
		assertThat("",//
				Fragment.getInstance(XId.newId(id.getBase() + "#dummy", id.getInstanceNums())),//
				isNull());
		for (Fragment fragment : Fragment.values()) {
			assertThat("",//
					Fragment.getInstance(fragment.generateExtendedId(id)),//
					isEqualTo(fragment));
		}
		assertThat("",//
				Fragment.getInstance(Node.DELAY_ID),//
				isEqualTo(Fragment.DELAY));
		assertThat("",//
				Fragment.getInstance(Node.ENERGY_DECAY_ID),//
				isEqualTo(Fragment.ENERGY_DECAY));
		assertThat("",//
				Fragment.getInstance(Node.ENERGY_ID),//
				isEqualTo(Fragment.ENERGY));
		assertThat("",//
				Fragment.getInstance(Node.EXCITATORY_SCALE_ID),//
				isEqualTo(Fragment.EXCITATORY_SCALE));
		assertThat("",//
				Fragment.getInstance(Node.INHIBITORY_SCALE_ID),//
				isEqualTo(Fragment.INHIBITORY_SCALE));
		assertThat("",//
				Fragment.getInstance(Node.INVERT_FLAG_ID),//
				isEqualTo(Fragment.DOES_INVERT));
		assertThat("",//
				Fragment.getInstance(Node.OSCILLATION_MAXIMUM_ID),//
				isEqualTo(Fragment.OSCILLATION_MAXIMUM));
		assertThat("",//
				Fragment.getInstance(Node.OSCILLATION_MINIMUM_ID),//
				isEqualTo(Fragment.OSCILLATION_MINIMUM));
		assertThat("",//
				Fragment.getInstance(Node.OSCILLATION_OFFSET_ID),//
				isEqualTo(Fragment.OSCILLATION_OFFSET));
		assertThat("",//
				Fragment.getInstance(Node.OSCILLATION_PERIOD_ID),//
				isEqualTo(Fragment.OSCILLATION_PERIOD));
		assertThat("",//
				Fragment.getInstance(Node.SCALE_ID),//
				isEqualTo(Fragment.SCALE));
		assertThat("",//
				Fragment.getInstance(Node.SHIFT_ID),//
				isEqualTo(Fragment.SHIFT));
		assertThat("",//
				Fragment.getInstance(Node.TWITCH_MAXIMUM_ID),//
				isEqualTo(Fragment.TWITCH_MAXIMUM));
		assertThat("",//
				Fragment.getInstance(Node.TWITCH_MINIMUM_ID),//
				isEqualTo(Fragment.TWITCH_MINIMUM));
		assertThat("",//
				Fragment.getInstance(Node.TWITCH_PROBABILITY_ID),//
				isEqualTo(Fragment.TWITCH_PROBABILITY));
		assertThat("",//
				Fragment.getInstance(XValId.newId("energies", Energies.class)),//
				isEqualTo(Fragment.ENERGIES));
	}

	@Test(dataProvider = "ids")
	public void generateExtendedIdReturnsExpectedObject(XId id) {
		for (Fragment frag : Fragment.values()) {
			assertThat("",//
					frag.generateExtendedId(null), isNull());
			XId extId = frag.generateExtendedId(id);
			assertThat("",//
					extId.getBase(), //
					isEqualTo(String.format("%s#%s", id.getBase(), frag)));
			assertThat("",//
					extId.getInstanceNums(),//
					isEqualTo(id.getInstanceNums()));
		}
	}

	public void getAndSetValueBehaveAsExpected() {
		NodeConfiguration nodeConfig = NodeConfiguration//
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
		Node node = new NodeImpl(XId.newId(), nodeConfig);
		assertThat("",//
				Fragment.DELAY.getValue(null),//
				isNull());
		assertThat("",//
				Fragment.DELAY.getValue(node),//
				isEqualTo(node.getDelay()));
		assertThat("",//
				Fragment.DOES_INVERT.getValue(node),//
				isEqualTo(node.doesInvert()));
		assertThat("",//
				Fragment.ENERGY_DECAY.getValue(node),//
				isEqualTo(node.getEnergyDecay()));
		assertThat("",//
				Fragment.EXCITATORY_SCALE.getValue(node),//
				isEqualTo(node.getExcitatoryScale()));
		assertThat("",//
				Fragment.INHIBITORY_SCALE.getValue(node),//
				isEqualTo(node.getInhibitoryScale()));
		assertThat("",//
				Fragment.OSCILLATION_MINIMUM.getValue(node),//
				isEqualTo(node.getOscillationMinimum()));
		assertThat("",//
				Fragment.OSCILLATION_MAXIMUM.getValue(node),//
				isEqualTo(node.getOscillationMaximum()));
		assertThat("",//
				Fragment.OSCILLATION_OFFSET.getValue(node),//
				isEqualTo(node.getOscillationOffset()));
		assertThat("",//
				Fragment.OSCILLATION_PERIOD.getValue(node),//
				isEqualTo(node.getOscillationPeriod()));
		assertThat("",//
				Fragment.SCALE.getValue(node),//
				isEqualTo(node.getScale()));
		assertThat("",//
				Fragment.SHIFT.getValue(node),//
				isEqualTo(node.getShift()));
		assertThat("",//
				Fragment.TWITCH_MINIMUM.getValue(node),//
				isEqualTo(node.getTwitchMinimum()));
		assertThat("",//
				Fragment.TWITCH_MAXIMUM.getValue(node),//
				isEqualTo(node.getTwitchMaximum()));
		assertThat("",//
				Fragment.TWITCH_PROBABILITY.getValue(node),//
				isEqualTo(node.getTwitchProbability()));
		int delay = ((IntegerXParameter) nodeConfig.getParameter(Node.DELAY_ID)).newUniformRandomValue();
		boolean invert = ((BooleanXParameter) nodeConfig.getParameter(Node.INVERT_FLAG_ID)).newUniformRandomValue();
		float energy = ((FloatXParameter) nodeConfig.getParameter(Node.ENERGY_ID)).newUniformRandomValue();
		Energies energies = new Energies(energy, energy);
		float energyDecay = ((FloatXParameter) nodeConfig.getParameter(Node.ENERGY_DECAY_ID)).newUniformRandomValue();
		float excit = ((FloatXParameter) nodeConfig.getParameter(Node.EXCITATORY_SCALE_ID)).newUniformRandomValue();
		float inhib = ((FloatXParameter) nodeConfig.getParameter(Node.INHIBITORY_SCALE_ID)).newUniformRandomValue();
		float oscmin = ((FloatXParameter) nodeConfig.getParameter(Node.OSCILLATION_MINIMUM_ID)).newUniformRandomValue();
		float oscmax = ((FloatXParameter) nodeConfig.getParameter(Node.OSCILLATION_MAXIMUM_ID)).newUniformRandomValue();
		int oscoff = ((IntegerXParameter) nodeConfig.getParameter(Node.OSCILLATION_OFFSET_ID)).newUniformRandomValue();
		int oscper = ((IntegerXParameter) nodeConfig.getParameter(Node.OSCILLATION_PERIOD_ID)).newUniformRandomValue();
		float scale = ((FloatXParameter) nodeConfig.getParameter(Node.SCALE_ID)).newUniformRandomValue();
		float shift = ((FloatXParameter) nodeConfig.getParameter(Node.SHIFT_ID)).newUniformRandomValue();
		float twimin = ((FloatXParameter) nodeConfig.getParameter(Node.TWITCH_MINIMUM_ID)).newUniformRandomValue();
		float twimax = ((FloatXParameter) nodeConfig.getParameter(Node.TWITCH_MAXIMUM_ID)).newUniformRandomValue();
		float twiprob = ((FloatXParameter) nodeConfig.getParameter(Node.TWITCH_PROBABILITY_ID)).newUniformRandomValue();
		Fragment.DELAY.setValue(node, delay);
		Fragment.DOES_INVERT.setValue(node, invert);
		Fragment.ENERGIES.setValue(node, energies);
		Fragment.ENERGY_DECAY.setValue(node, energyDecay);
		Fragment.EXCITATORY_SCALE.setValue(node, excit);
		Fragment.INHIBITORY_SCALE.setValue(node, inhib);
		Fragment.OSCILLATION_MINIMUM.setValue(node, oscmin);
		Fragment.OSCILLATION_MAXIMUM.setValue(node, oscmax);
		Fragment.OSCILLATION_OFFSET.setValue(node, oscoff);
		Fragment.OSCILLATION_PERIOD.setValue(node, oscper);
		Fragment.SCALE.setValue(node, scale);
		Fragment.SHIFT.setValue(node, shift);
		Fragment.TWITCH_MINIMUM.setValue(node, twimin);
		Fragment.TWITCH_MAXIMUM.setValue(node, twimax);
		Fragment.TWITCH_PROBABILITY.setValue(node, twiprob);
		assertThat("",//
				Fragment.DELAY.getValue(node),//
				isEqualTo(delay));
		assertThat("",//
				Fragment.DOES_INVERT.getValue(node),//
				isEqualTo(invert));
		assertThat("",//
				Fragment.ENERGIES.getValue(node),//
				isEqualTo(energies));
		assertThat("",//
				Fragment.ENERGY_DECAY.getValue(node),//
				isEqualTo(energyDecay));
		assertThat("",//
				Fragment.EXCITATORY_SCALE.getValue(node),//
				isEqualTo(excit));
		assertThat("",//
				Fragment.INHIBITORY_SCALE.getValue(node),//
				isEqualTo(inhib));
		assertThat("",//
				Fragment.OSCILLATION_MINIMUM.getValue(node),//
				isEqualTo(oscmin));
		assertThat("",//
				Fragment.OSCILLATION_MAXIMUM.getValue(node),//
				isEqualTo(oscmax));
		assertThat("",//
				Fragment.OSCILLATION_OFFSET.getValue(node),//
				isEqualTo(oscoff));
		assertThat("",//
				Fragment.OSCILLATION_PERIOD.getValue(node),//
				isEqualTo(oscper));
		assertThat("",//
				Fragment.SCALE.getValue(node),//
				isEqualTo(scale));
		assertThat("",//
				Fragment.SHIFT.getValue(node),//
				isEqualTo(shift));
		assertThat("",//
				Fragment.TWITCH_MINIMUM.getValue(node),//
				isEqualTo(twimin));
		assertThat("",//
				Fragment.TWITCH_MAXIMUM.getValue(node),//
				isEqualTo(twimax));
		assertThat("",//
				Fragment.TWITCH_PROBABILITY.getValue(node),//
				isEqualTo(twiprob));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void getOnEnergyThrowsException() {
		Fragment.ENERGY.getValue(new NodeImpl(XId.newId(),//
				NodeConfiguration.DEFAULT_CONFIGURATION));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void setOnEnergyThrowsException() {
		Fragment.ENERGY.setValue(new NodeImpl(XId.newId(),//
				NodeConfiguration.DEFAULT_CONFIGURATION),//
				RandomUtil.nextFloat());
	}

	public void getParameterIdReturnsExpectedObject() {
		assertThat("",//
				Fragment.DELAY.getParameterId(),//
				isEqualTo(Node.DELAY_ID));
		assertThat("",//
				Fragment.DOES_INVERT.getParameterId(),//
				isEqualTo(Node.INVERT_FLAG_ID));
		assertThat("",//
				Fragment.ENERGY.getParameterId(),//
				isEqualTo(Node.ENERGY_ID));
		assertThat("",//
				Fragment.ENERGY_DECAY.getParameterId(),//
				isEqualTo(Node.ENERGY_DECAY_ID));
		assertThat("",//
				Fragment.ENERGIES.getParameterId(),//
				isEqualTo(XValId.newId("energies", Energies.class)));
		assertThat("",//
				Fragment.EXCITATORY_SCALE.getParameterId(),//
				isEqualTo(Node.EXCITATORY_SCALE_ID));
		assertThat("",//
				Fragment.INHIBITORY_SCALE.getParameterId(),//
				isEqualTo(Node.INHIBITORY_SCALE_ID));
		assertThat("",//
				Fragment.OSCILLATION_MINIMUM.getParameterId(),//
				isEqualTo(Node.OSCILLATION_MINIMUM_ID));
		assertThat("",//
				Fragment.OSCILLATION_MAXIMUM.getParameterId(),//
				isEqualTo(Node.OSCILLATION_MAXIMUM_ID));
		assertThat("",//
				Fragment.OSCILLATION_OFFSET.getParameterId(),//
				isEqualTo(Node.OSCILLATION_OFFSET_ID));
		assertThat("",//
				Fragment.OSCILLATION_PERIOD.getParameterId(),//
				isEqualTo(Node.OSCILLATION_PERIOD_ID));
		assertThat("",//
				Fragment.SCALE.getParameterId(),//
				isEqualTo(Node.SCALE_ID));
		assertThat("",//
				Fragment.SHIFT.getParameterId(),//
				isEqualTo(Node.SHIFT_ID));
		assertThat("",//
				Fragment.TWITCH_MINIMUM.getParameterId(),//
				isEqualTo(Node.TWITCH_MINIMUM_ID));
		assertThat("",//
				Fragment.TWITCH_MAXIMUM.getParameterId(),//
				isEqualTo(Node.TWITCH_MAXIMUM_ID));
		assertThat("",//
				Fragment.TWITCH_PROBABILITY.getParameterId(),//
				isEqualTo(Node.TWITCH_PROBABILITY_ID));
	}

	public void getDescriptionReturnsExpectedObject() {
		assertThat("",//
				Fragment.DELAY.getDescription(),//
				isEqualTo(Node.DELAY_DESCRIPTION));
		assertThat("",//
				Fragment.DOES_INVERT.getDescription(),//
				isEqualTo(Node.INVERT_FLAG_DESCRIPTION));
		assertThat("",//
				Fragment.ENERGY.getDescription(),//
				isEqualTo(Node.ENERGY_DESCRIPTION));
		assertThat("",//
				Fragment.ENERGY_DECAY.getDescription(),//
				isEqualTo(Node.ENERGY_DECAY_DESCRIPTION));
		assertThat("",//
				Fragment.ENERGIES.getDescription(),//
				isEqualTo(""));
		assertThat("",//
				Fragment.EXCITATORY_SCALE.getDescription(),//
				isEqualTo(Node.EXCITATORY_SCALE_DESCRIPTION));
		assertThat("",//
				Fragment.INHIBITORY_SCALE.getDescription(),//
				isEqualTo(Node.INHIBITORY_SCALE_DESCRIPTION));
		assertThat("",//
				Fragment.OSCILLATION_MINIMUM.getDescription(),//
				isEqualTo(Node.OSCILLATION_MINIMUM_DESCRIPTION));
		assertThat("",//
				Fragment.OSCILLATION_MAXIMUM.getDescription(),//
				isEqualTo(Node.OSCILLATION_MAXIMUM_DESCRIPTION));
		assertThat("",//
				Fragment.OSCILLATION_OFFSET.getDescription(),//
				isEqualTo(Node.OSCILLATION_OFFSET_DESCRIPTION));
		assertThat("",//
				Fragment.OSCILLATION_PERIOD.getDescription(),//
				isEqualTo(Node.OSCILLATION_PERIOD_DESCRIPTION));
		assertThat("",//
				Fragment.SCALE.getDescription(),//
				isEqualTo(Node.SCALE_DESCRIPTION));
		assertThat("",//
				Fragment.SHIFT.getDescription(),//
				isEqualTo(Node.SHIFT_DESCRIPTION));
		assertThat("",//
				Fragment.TWITCH_MINIMUM.getDescription(),//
				isEqualTo(Node.TWITCH_MINIMUM_DESCRIPTION));
		assertThat("",//
				Fragment.TWITCH_MAXIMUM.getDescription(),//
				isEqualTo(Node.TWITCH_MAXIMUM_DESCRIPTION));
		assertThat("",//
				Fragment.TWITCH_PROBABILITY.getDescription(),//
				isEqualTo(Node.TWITCH_PROBABILITY_DESCRIPTION));
	}

	public void getAttributeReturnsExpectedObject() {
		for (Fragment fragment : Fragment.values()) {
			assertThat("",//
					fragment.getAttribute(), isSameAs(fragment.getParameterId().toAttribute()));
		}
	}

	public void getElementReturnsExpectedObject() {
		for (Fragment fragment : Fragment.values()) {
			assertThat("",//
					fragment.getElement(), isSameAs(fragment.getParameterId().toElement()));
		}
	}

	public void toStringReturnsExpectedObject() {
		for (Fragment fragment : Fragment.values()) {
			assertThat("",//
					fragment.toString(),//
					isEqualTo(fragment.getParameterId().getBase()));
		}
	}
}
