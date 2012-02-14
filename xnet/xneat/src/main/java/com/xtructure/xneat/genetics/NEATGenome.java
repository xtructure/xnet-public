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
package com.xtructure.xneat.genetics;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.id.XId;

/**
 * The {@link NEATGenome} interface is for individuals on which evolution is to
 * be applied. It carries an arbitrary payload for it's chromosome data. It's
 * primary attributes are fitness, complexity, evaluation count, and death mark.
 * As part of the NEAT algorithm, it also has species id attribute.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface NEATGenome<D> extends Genome<D> {
	/** {@link XValId} of the species id attribute */
	public static final XValId<XId>	SPECIES_ATTRIBUTE_ID	= XValId.newId("speciesId", XId.class);

	/**
	 * Returns the id of the species to which this
	 * {@link NEATGenome} belongs.
	 * 
	 * @return the id of the species to which this
	 *         {@link NEATGenome} belongs, or null if this hasn't been assigned
	 *         to a species
	 */
	public XId getSpeciesId();

	/**
	 * Sets the id of the species to which this {@link NEATGenome}
	 * has been assigned
	 * 
	 * @param speciesId
	 *            the id of the species to which this
	 *            {@link NEATGenome} has been assigned
	 */
	public void setSpeciesId(XId speciesId);
}
