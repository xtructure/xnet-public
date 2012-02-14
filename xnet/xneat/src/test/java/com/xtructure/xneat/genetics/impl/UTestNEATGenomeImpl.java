/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.genetics.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;

import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xneat" })
public class UTestNEATGenomeImpl {
	public void constructorSucceeds() {
		assertThat("",//
				new NEATGenomeImpl(0, new GeneMap()), isNotNull());
		assertThat("",//
				new NEATGenomeImpl(0, new NEATGenomeImpl(1, new GeneMap())), isNotNull());
	}

	public void getAndSetSpeciesIdBehaveAsExpected() {
		NEATGenomeImpl genome = new NEATGenomeImpl(0, new GeneMap());
		assertThat("",//
				genome.getSpeciesId(), isNull());
		XId id = XId.newId("species");
		genome.setSpeciesId(id);
		assertThat("",//
				genome.getSpeciesId(), isEqualTo(id));
	}

	public void validateBehavesAsExpected() {
	// TODO
	}
}
