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
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.link.Link.Fragment;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.config.FloatXParameter;
import com.xtructure.xutil.config.RangeXParameter;
import com.xtructure.xutil.config.XField;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xart" })
public class UTestLinkImpl {
	private static final Object[][]			VALID_CONSTRUCTOR_ARGS;
	private static final Object[][]			VALID_CALCULATE_ARGS;
	private static final XId				LINK_ID;
	private static final XId				SRC_ID;
	private static final XId				TGT_ID;
	private static final LinkConfiguration	TEST_CONFIG;
	static {
		LINK_ID = XId.newId("linkId");
		SRC_ID = XId.newId("SRC");
		TGT_ID = XId.newId("TGT");
		TEST_CONFIG = LinkConfiguration//
				.builder()//
				.setCapacity(Range.<Float> openRange(), null)//
				.setCapacityAttack(Range.<Float> openRange(), null)//
				.setCapacityDecay(Range.<Float> openRange(), null)//
				.setStrength(Range.<Float> openRange(), null)//
				.setStrengthAttack(Range.<Float> openRange(), null)//
				.setStrengthDecay(Range.<Float> openRange(), null)//
				.setInhibitoryFlag(Range.<Boolean> openRange(), null)//
				.newInstance();
		VALID_CONSTRUCTOR_ARGS = TestUtils.crossData(//
				TestUtils.createData(LINK_ID),//
				TestUtils.nullable(TestUtils.createData(SRC_ID)),//
				TestUtils.nullable(TestUtils.createData(TGT_ID)),//
				TestUtils.createData(//
						TEST_CONFIG,//
						LinkConfiguration.DEFAULT_CONFIGURATION));
		VALID_CALCULATE_ARGS = TestUtils.crossData(//
				TestUtils.floatData(0.0f, 1.0f, 0.5f),//
				TestUtils.floatData(0.0f, 1.0f, 0.5f));
	}

	@Test(dataProvider = "validConstructorArgs")
	public final void getInstanceSucceeds(XId id, XId sourceId, XId targetId, LinkConfiguration config) {
		assertThat("",//
				new LinkImpl(id, sourceId, targetId, config), isNotNull());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public final void getInstanceWithNullIdThrowsException() {
		new LinkImpl(null, SRC_ID, TGT_ID, LinkConfiguration.DEFAULT_CONFIGURATION);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void getInstanceWithNullConfigThrowsException() {
		new LinkImpl(LINK_ID, SRC_ID, TGT_ID, null);
	}

	public final void calculateReturnsExpectedFloat() {}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void getAndSetFieldsBehaveAsExpected() {
		LinkImpl link = new LinkImpl(LINK_ID, SRC_ID, TGT_ID, TEST_CONFIG);
		for (Fragment fragment : Fragment.values()) {
			if (!Fragment.OUTPUT_ENERGY.equals(fragment)) {
				RangeXParameter parameter = (RangeXParameter) TEST_CONFIG.getParameter(fragment.getParameterId());
				assertThat(fragment.toString(),//
						parameter.getInitialRange().contains((Comparable) fragment.getValue(link)),//
						isTrue());
				Object value = parameter.newUniformRandomValue();
				fragment.setValue(link, value);
				assertThat("",//
						fragment.getValue(link), isEqualTo(value));
			}
		}
	}

	public final void getConfigurationReturnsExpectedConfiguration() {
		LinkImpl link = new LinkImpl(LINK_ID, SRC_ID, TGT_ID, TEST_CONFIG);
		assertThat("",//
				link.getConfiguration(), isSameAs(TEST_CONFIG));
	}

	public final void getSourceIdReturnsExpectedXId() {
		LinkImpl link = new LinkImpl(LINK_ID, SRC_ID, TGT_ID, TEST_CONFIG);
		assertThat("",//
				link.getSourceId(), isEqualTo(SRC_ID));
	}

	public final void getTargetIdReturnsExpectedId() {
		LinkImpl link = new LinkImpl(LINK_ID, SRC_ID, TGT_ID, TEST_CONFIG);
		assertThat("",//
				link.getTargetId(), isEqualTo(TGT_ID));
	}

	public final void toStringReturnsExpectedString() {
		LinkImpl link = new LinkImpl(LINK_ID, SRC_ID, TGT_ID, TEST_CONFIG);
		String expected = String.format(//
				"link %s; capacity=(%f +%f -%f); strength=(%f +%f -%f); inhibitory=%b", //
				link.getId(), //
				link.getCapacity(),//
				link.getCapacityAttack(),//
				link.getCapacityDecay(), //
				link.getStrength(), //
				link.getStrengthAttack(), //
				link.getStrengthDecay(), //
				link.isInhibitory());
		assertThat("",//
				link.toString(), isEqualTo(expected));
	}

	@Test(dataProvider = "validCalculateArgs")
	public final void calculateAndUpdateSucceeds(Float sourceEnergy, Float targetEnergy) {
		LinkImpl link = new LinkImpl(LINK_ID, SRC_ID, TGT_ID, TEST_CONFIG);
		Float expectedCapacity = calculateCapacity(sourceEnergy, targetEnergy, link);
		Float expectedStrength = calculateStrength(sourceEnergy, targetEnergy, link);
		Float expectedEnergy = calculateEnergy(sourceEnergy, targetEnergy, link);
		Float initCapacity = link.getCapacity();
		Float initStrength = link.getStrength();
		Float energy = link.calculate(sourceEnergy, targetEnergy);
		assertThat("",//
				link.getOutputEnergy(), isEqualTo(energy));
		Float interCapacity = link.getCapacity();
		Float interStrength = link.getStrength();
		link.update();
		Float finalCapacity = link.getCapacity();
		Float finalStrength = link.getStrength();
		assertThat("", //
				energy, isEqualTo(expectedEnergy));
		assertThat("",//
				initCapacity, isEqualTo(interCapacity));
		assertThat("",//
				finalCapacity, isEqualTo(expectedCapacity));
		assertThat("",//
				initStrength, isEqualTo(interStrength));
		assertThat("",//
				finalStrength, isEqualTo(expectedStrength));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void calculateWithNullSourceThrowsException() {
		LinkImpl link = new LinkImpl(LINK_ID, SRC_ID, TGT_ID, TEST_CONFIG);
		link.calculate(null, Float.MAX_VALUE);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public final void calculateWithNullTargetThrowsException() {
		LinkImpl link = new LinkImpl(LINK_ID, SRC_ID, TGT_ID, TEST_CONFIG);
		link.calculate(Float.MAX_VALUE, null);
	}

	private static final Float calculateCapacity(Float sourceEnergy, Float targetEnergy, LinkImpl link) {
		XField<Float> cap = ((FloatXParameter) link.getConfiguration().getParameter(Link.CAPACITY_ID)).newField();
		cap.setValue(link.getCapacity() //
				- link.getCapacityDecay() * link.getCapacity() //
				+ link.getCapacityAttack() * sourceEnergy * targetEnergy);
		return cap.getValue();
	}

	private static final Float calculateStrength(Float sourceEnergy, Float targetEnergy, LinkImpl link) {
		XField<Float> str = ((FloatXParameter) link.getConfiguration().getParameter(Link.STRENGTH_ID)).newField();
		str.setValue((float) Math.min(Math.max(0.0,//
				link.getStrength() //
						- link.getStrengthDecay() * link.getStrength() * sourceEnergy //
						+ link.getStrengthAttack() * link.getCapacity()), //
				link.getCapacity()));
		return str.getValue();
	}

	private static final Float calculateEnergy(Float sourceEnergy, Float targetEnergy, LinkImpl link) {
		return link.isInhibitory() ? (-sourceEnergy * link.getStrength()) : (sourceEnergy * link.getStrength());
	}

	@DataProvider
	public final Object[][] validConstructorArgs() {
		return VALID_CONSTRUCTOR_ARGS;
	}

	@DataProvider
	public final Object[][] validCalculateArgs() {
		return VALID_CALCULATE_ARGS;
	}
}
