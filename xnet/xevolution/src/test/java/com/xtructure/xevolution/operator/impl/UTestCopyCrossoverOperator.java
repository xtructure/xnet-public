/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.operator.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.genetics.impl.GenomeImpl;
import com.xtructure.xevolution.operator.Operator.OperationFailedException;
import com.xtructure.xutil.RandomUtil;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestCopyCrossoverOperator {
	private static final GeneticsFactory<String>	GENETICS_FACTORY	= new GeneticsFactoryImpl(null, "asdf");

	public void constructorSucceeds() {
		assertThat("",//
				new CopyCrossoverOperator<String>(GENETICS_FACTORY, RandomUtil.nextDouble()), isNotNull());
	}

	public void crossoverReturnsExpectedGenome() throws OperationFailedException {
		Genome<String> g1 = new GenomeImpl(1, RandomStringUtils.randomAlphanumeric(10));
		Genome<String> g2 = new GenomeImpl(2, RandomStringUtils.randomAlphanumeric(10));

		Genome<String> child = new CopyCrossoverOperator<String>(GENETICS_FACTORY, 1.0).crossover(99, g1, g2);
		assertThat("",//
				child.getId(), isEqualTo(g1.getBaseId().createChild(99)));
		assertThat("",//
				child.getData(), isEqualTo(g1.getData()));

		child = new CopyCrossoverOperator<String>(GENETICS_FACTORY, 0.0).crossover(88, g1, g2);
		assertThat("",//
				child.getId(), isEqualTo(g1.getBaseId().createChild(88)));
		assertThat("",//
				child.getData(), isEqualTo(g2.getData()));
	}
}
