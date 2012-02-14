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
package com.xtructure.xevolution.genetics;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;

import java.util.Comparator;

import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.impl.GenomeImpl;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestGeneticsObject_ByMeasure {
	public void constructorSucceeds() {
		assertThat("",//
				new GeneticsObject.ByAttribute<Genome<?>, Double>(Genome.FITNESS_ATTRIBUTE_ID, false),//
				isNotNull());
	}

	public void compareReturnsExpectedInt() {
		Genome<?> genome1 = new GenomeImpl(1, "");
		Genome<?> genome2 = new GenomeImpl(2, "");
		Comparator<Genome<?>> byFitnessAsc = new GeneticsObject.ByAttribute<Genome<?>, Double>(Genome.FITNESS_ATTRIBUTE_ID, false);
		Comparator<Genome<?>> byFitnessDesc = new GeneticsObject.ByAttribute<Genome<?>, Double>(Genome.FITNESS_ATTRIBUTE_ID, true);
		genome1.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		genome2.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		assertThat("",//
				byFitnessAsc.compare(genome1, genome2), isEqualTo(-1));
		assertThat("",//
				byFitnessDesc.compare(genome1, genome2), isEqualTo(-1));
		genome1.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 2.0);
		genome2.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		assertThat("",//
				byFitnessAsc.compare(genome1, genome2), isEqualTo(1));
		assertThat("",//
				byFitnessDesc.compare(genome1, genome2), isEqualTo(-1));
		genome1.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 2.0);
		genome2.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 3.0);
		assertThat("",//
				byFitnessAsc.compare(genome1, genome2), isEqualTo(-1));
		assertThat("",//
				byFitnessDesc.compare(genome1, genome2), isEqualTo(1));
	}
}
