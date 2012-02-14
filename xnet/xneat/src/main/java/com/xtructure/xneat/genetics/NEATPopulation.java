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

import java.util.Set;

import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * @author Luis Guimbarda
 *
 * @param <D>
 */
public interface NEATPopulation<D> extends Population<D> {
	/** the {@link XValId} for the compatibility threshold attribute */
	public static final XValId<Double>	COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID	= XValId.newId("compatibilityThreshold", Double.class);
	/** the {@link XValId} for the next species instance number attribute */
	public static final XValId<Integer>	SPECIES_NUM_ATTRIBUTE_ID				= XValId.newId("speciesIdNum", Integer.class);

	/**
	 * Creates a new species in this {@link NEATPopulation}
	 * 
	 * @return the id of the new species
	 */
	public XId newSpecies();

	/**
	 * Returns the set of genome ids in the species with the given speciesId
	 * 
	 * @param speciesId
	 *            the id of the species
	 * @return the set of genome ids in the species
	 */
	public Set<XId> getSpecies(XId speciesId);

	/**
	 * Returns the set of ids of species in this {@link NEATPopulation}
	 * 
	 * @return the set of ids of species in this {@link NEATPopulation}
	 */
	public Set<XId> getSpeciesIds();

	/**
	 * Removes the species in this {@link NEATPopulation} with the given id, as
	 * well as all of its genomes
	 * 
	 * @param speciesId
	 *            the {@link XId} of the species to remove
	 */
	public void removeSpecies(XId speciesId);

	/**
	 * Removes the species in this {@link NEATPopulation} that have
	 * been marked for death. Also removes their genomes, whether they've been
	 * marked or not.
	 */
	public void removeDeadSpecies();

	/**
	 * Removes all species from this {@link NEATPopulation}, but
	 * leaves their genomes.
	 */
	public void clearSpecies();

	void addToSpecies(XId genomeId, XId speciesId);

	void addToSpecies(NEATGenome<D> genome, XId speciesId);

	void setSpeciesTargetSize(XId speciesId, int targetSize);

	void setSpeciesEliteSize(XId speciesId, int eliteSize);

	int getSpeciesTargetSize(XId speciesId);

	int getSpeciesEliteSize(XId speciesId);

	void markSpeciesDead(XId speciesId);
}
