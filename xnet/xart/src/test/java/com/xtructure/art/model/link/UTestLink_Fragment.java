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
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.link.Link.Fragment;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.config.BooleanXParameter;
import com.xtructure.xutil.config.FloatXParameter;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.test.TestUtils;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xart" })
public class UTestLink_Fragment {
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
				Fragment.getInstance(Link.CAPACITY_ID),//
				isEqualTo(Fragment.CAPACITY));
		assertThat("",//
				Fragment.getInstance(Link.CAPACITY_ATTACK_ID),//
				isEqualTo(Fragment.CAPACITY_ATTACK));
		assertThat("",//
				Fragment.getInstance(Link.CAPACITY_DECAY_ID),//
				isEqualTo(Fragment.CAPACITY_DECAY));
		assertThat("",//
				Fragment.getInstance(Link.STRENGTH_ID),//
				isEqualTo(Fragment.STRENGTH));
		assertThat("",//
				Fragment.getInstance(Link.STRENGTH_ATTACK_ID),//
				isEqualTo(Fragment.STRENGTH_ATTACK));
		assertThat("",//
				Fragment.getInstance(Link.STRENGTH_DECAY_ID),//
				isEqualTo(Fragment.STRENGTH_DECAY));
		assertThat("",//
				Fragment.getInstance(Link.INHIBITORY_FLAG_ID),//
				isEqualTo(Fragment.IS_INHIBITORY));
		assertThat("",//
				Fragment.getInstance(XValId.newId("outputEnergy", Float.class)),//
				isEqualTo(Fragment.OUTPUT_ENERGY));
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

	public void getAndSetValueReturnsExpectedObject() {
		LinkConfiguration linkConfig = LinkConfiguration//
				.builder()//
				.setCapacity(Range.<Float> openRange(), null)//
				.setCapacityAttack(Range.<Float> openRange(), null)//
				.setCapacityDecay(Range.<Float> openRange(), null)//
				.setStrength(Range.<Float> openRange(), null)//
				.setStrengthAttack(Range.<Float> openRange(), null)//
				.setStrengthDecay(Range.<Float> openRange(), null)//
				.setInhibitoryFlag(Range.<Boolean> openRange(), null)//
				.newInstance();
		Link link = new LinkImpl(XId.newId(), XId.newId(), XId.newId(), linkConfig);
		assertThat("",//
				Fragment.CAPACITY.getValue(null),//
				isNull());
		assertThat("",//
				Fragment.CAPACITY.getValue(link),//
				isEqualTo(link.getCapacity()));
		assertThat("",//
				Fragment.CAPACITY_ATTACK.getValue(link),//
				isEqualTo(link.getCapacityAttack()));
		assertThat("",//
				Fragment.CAPACITY_DECAY.getValue(link),//
				isEqualTo(link.getCapacityDecay()));
		assertThat("",//
				Fragment.STRENGTH.getValue(link),//
				isEqualTo(link.getStrength()));
		assertThat("",//
				Fragment.STRENGTH_ATTACK.getValue(link),//
				isEqualTo(link.getStrengthAttack()));
		assertThat("",//
				Fragment.STRENGTH_DECAY.getValue(link),//
				isEqualTo(link.getStrengthDecay()));
		assertThat("",//
				Fragment.IS_INHIBITORY.getValue(link),//
				isEqualTo(link.isInhibitory()));
		assertThat("",//
				Fragment.OUTPUT_ENERGY.getValue(link),//
				isEqualTo(link.getOutputEnergy()));
		float c = ((FloatXParameter) linkConfig.getParameter(Link.CAPACITY_ID)).newUniformRandomValue();
		float ca = ((FloatXParameter) linkConfig.getParameter(Link.CAPACITY_ATTACK_ID)).newUniformRandomValue();
		float cd = ((FloatXParameter) linkConfig.getParameter(Link.CAPACITY_DECAY_ID)).newUniformRandomValue();
		float s = ((FloatXParameter) linkConfig.getParameter(Link.STRENGTH_ID)).newUniformRandomValue();
		float sa = ((FloatXParameter) linkConfig.getParameter(Link.STRENGTH_ATTACK_ID)).newUniformRandomValue();
		float sd = ((FloatXParameter) linkConfig.getParameter(Link.STRENGTH_DECAY_ID)).newUniformRandomValue();
		boolean i = ((BooleanXParameter) linkConfig.getParameter(Link.INHIBITORY_FLAG_ID)).newUniformRandomValue();
		Fragment.CAPACITY.setValue(null, c);
		Fragment.CAPACITY.setValue(link, c);
		Fragment.CAPACITY_ATTACK.setValue(link, ca);
		Fragment.CAPACITY_DECAY.setValue(link, cd);
		Fragment.STRENGTH.setValue(link, s);
		Fragment.STRENGTH_ATTACK.setValue(link, sa);
		Fragment.STRENGTH_DECAY.setValue(link, sd);
		Fragment.IS_INHIBITORY.setValue(link, i);
		assertThat("",//
				Fragment.CAPACITY.getValue(link),//
				isEqualTo(c));
		assertThat("",//
				Fragment.CAPACITY_ATTACK.getValue(link),//
				isEqualTo(ca));
		assertThat("",//
				Fragment.CAPACITY_DECAY.getValue(link),//
				isEqualTo(cd));
		assertThat("",//
				Fragment.STRENGTH.getValue(link),//
				isEqualTo(s));
		assertThat("",//
				Fragment.STRENGTH_ATTACK.getValue(link),//
				isEqualTo(sa));
		assertThat("",//
				Fragment.STRENGTH_DECAY.getValue(link),//
				isEqualTo(sd));
		assertThat("",//
				Fragment.IS_INHIBITORY.getValue(link),//
				isEqualTo(i));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void setOutputEnergyThrowsException() {
		Fragment.OUTPUT_ENERGY.setValue(//
				new LinkImpl(XId.newId(), XId.newId(), XId.newId(),//
						LinkConfiguration.DEFAULT_CONFIGURATION),//
				RandomUtil.nextFloat());
	}

	public void getParameterIdReturnsExpectedObject() {
		assertThat("",//
				Fragment.CAPACITY.getParameterId(),//
				isEqualTo(Link.CAPACITY_ID));
		assertThat("",//
				Fragment.CAPACITY_ATTACK.getParameterId(),//
				isEqualTo(Link.CAPACITY_ATTACK_ID));
		assertThat("",//
				Fragment.CAPACITY_DECAY.getParameterId(),//
				isEqualTo(Link.CAPACITY_DECAY_ID));
		assertThat("",//
				Fragment.STRENGTH.getParameterId(),//
				isEqualTo(Link.STRENGTH_ID));
		assertThat("",//
				Fragment.STRENGTH_ATTACK.getParameterId(),//
				isEqualTo(Link.STRENGTH_ATTACK_ID));
		assertThat("",//
				Fragment.STRENGTH_DECAY.getParameterId(),//
				isEqualTo(Link.STRENGTH_DECAY_ID));
		assertThat("",//
				Fragment.IS_INHIBITORY.getParameterId(),//
				isEqualTo(Link.INHIBITORY_FLAG_ID));
		assertThat("",//
				Fragment.OUTPUT_ENERGY.getParameterId(),//
				isEqualTo(XValId.newId("outputEnergy", Float.class)));
	}

	public void getDescriptionReturnsExpectedObject() {
		assertThat("",//
				Fragment.CAPACITY.getDescription(),//
				isEqualTo(Link.CAPACITY_DESCRIPTION));
		assertThat("",//
				Fragment.CAPACITY_ATTACK.getDescription(),//
				isEqualTo(Link.CAPACITY_ATTACK_DESCRIPTION));
		assertThat("",//
				Fragment.CAPACITY_DECAY.getDescription(),//
				isEqualTo(Link.CAPACITY_DECAY_DESCRIPTION));
		assertThat("",//
				Fragment.STRENGTH.getDescription(),//
				isEqualTo(Link.STRENGTH_DESCRIPTION));
		assertThat("",//
				Fragment.STRENGTH_ATTACK.getDescription(),//
				isEqualTo(Link.STRENGTH_ATTACK_DESCRIPTION));
		assertThat("",//
				Fragment.STRENGTH_DECAY.getDescription(),//
				isEqualTo(Link.STRENGTH_DECAY_DESCRIPTION));
		assertThat("",//
				Fragment.IS_INHIBITORY.getDescription(),//
				isEqualTo(Link.INHIBITORY_FLAG_DESCRIPTION));
		assertThat("",//
				Fragment.OUTPUT_ENERGY.getDescription(),//
				isEqualTo(""));
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
