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

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.AbstractGenome;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xutil.id.XId;

/**
 * {@link AbstractNEATGenome} implements the getters for the {@link NEATGenome}
 * interface.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class AbstractNEATGenome<D> extends AbstractGenome<D> implements NEATGenome<D> {
	/**
	 * Creates a new {@link AbstractNEATGenome} with the given data.
	 * 
	 * @param idNumber
	 *            the instance number for the id of the new
	 *            {@link AbstractNEATGenome}
	 * @param data
	 *            the data of the new {@link AbstractGenome}
	 */
	public AbstractNEATGenome(int idNumber, D data) {
		super(idNumber, data);
	}

	/**
	 * Creates a new {@link AbstractGenome} using the given genome's data.
	 * 
	 * @param idNumber
	 *            the instance number for the id of the new
	 *            {@link AbstractGenome}
	 * @param genome
	 *            the {@link Genome} whose data is to be used in this
	 *            {@link AbstractGenome}
	 */
	public AbstractNEATGenome(int idNumber, Genome<D> genome) {
		super(idNumber, genome);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.projects.xevo.neat.genetics.NEATGenome#getSpeciesId()
	 */
	@Override
	public XId getSpeciesId() {
		getLogger().trace("begin %s.getInnovation()", getClass().getSimpleName());

		XId rVal = getAttribute(SPECIES_ATTRIBUTE_ID);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getInnovation()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.projects.xevo.neat.genetics.NEATGenome#setSpeciesId(com
	 * .xtructure.xutil.id.XId)
	 */
	@Override
	public void setSpeciesId(XId speciesId) {
		getLogger().trace("begin %s.setSpeciesId()", getClass().getSimpleName());

		setAttribute(SPECIES_ATTRIBUTE_ID, speciesId);

		getLogger().trace("end %s.setSpeciesId()", getClass().getSimpleName());
	}
}
