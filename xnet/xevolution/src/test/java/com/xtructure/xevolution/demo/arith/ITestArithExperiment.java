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
package com.xtructure.xevolution.demo.arith;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;

import java.io.IOException;

import org.apache.log4j.Level;
import org.testng.annotations.Test;

import com.xtructure.xevolution.config.impl.HighestFitnessIsAtLeastCondition;
import com.xtructure.xevolution.genetics.Genome;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "integration:xevolution" })
public class ITestArithExperiment {
	public void experimentSucceeds() throws IOException {
		ArithmeticExperiment experiment = new ArithmeticExperiment();
		experiment.getReproductionStrategy().getLogger().setLevel(Level.WARN);
		experiment.getEvolutionStrategy().getLogger().setLevel(Level.WARN);
		experiment.getEvolutionFieldMap().setTerminationCondition(new HighestFitnessIsAtLeastCondition(1.0));
		String[] args = new String[] { "--target", "100" };
		experiment.setArgs(args);
		experiment.startExperiment();
		Genome<String> bestGenome = experiment.getPopulation().getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID);
		assertThat("",//
				bestGenome.getFitness(), isEqualTo(1.0));
	}
}
