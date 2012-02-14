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

import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.containsKey;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.AbstractPopulation;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.NEATPopulation;
import com.xtructure.xutil.id.XId;

public class AbstractNEATPopulation<D> extends AbstractPopulation<D> implements NEATPopulation<D> {
	/** the base {@link XId} for species */
	public static final XId				SPECIES_BASE_ID	= XId.newId("Species");
	private static final int			TARGET			= 0;
	private static final int			ELITE			= 1;
	private static final int			STATS_LENGTH	= 2;
	private final Map<XId, Set<XId>>	speciesMap		= new HashMap<XId, Set<XId>>();
	private final Map<XId, int[]>		speciesStats	= new HashMap<XId, int[]>();
	private final Set<XId>				deadSpecies		= new HashSet<XId>();

	public AbstractNEATPopulation(int instanceNum) {
		this(POPULATION_BASE_ID.createChild(instanceNum));
	}

	public AbstractNEATPopulation(XId id) {
		super(id);
		setAttribute(SPECIES_NUM_ATTRIBUTE_ID, 0);
		setAttribute(COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID, 0.1);
	}

	@Override
	public boolean add(Genome<D> genome) {
		boolean changed = super.add(genome);
		((NEATGenome<D>) genome).setSpeciesId(null);
		return changed;
	}

	@Override
	public boolean remove(Object o) {
		NEATGenome<D> genome = null;
		if (o instanceof XId) {
			genome = (NEATGenome<D>) get((XId) o);
		} else if (o instanceof Genome) {
			genome = (NEATGenome<D>) get(((Genome<?>) o).getId());
		}
		if (genome != null && genome.getSpeciesId() != null) {
			speciesMap.get(genome.getSpeciesId()).remove(genome.getId());
		}
		return super.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object o : c) {
			if (o instanceof Genome) {
				NEATGenome<D> genome = (NEATGenome<D>) get(((Genome<?>) o).getId());
				if (genome != null && genome.getSpeciesId() != null) {
					speciesMap.get(genome.getSpeciesId()).remove(genome.getId());
				}
			}
		}
		return super.removeAll(c);
	}

	@Override
	public void addToSpecies(XId genomeId, XId speciesId) {
		validateArg("genomeId", genomeId, isNotNull());
		validateArg("speciesId", speciesMap, containsKey(speciesId));
		NEATGenome<D> genome = (NEATGenome<D>) get(genomeId);
		addToSpecies(genome, speciesId);
	}

	@Override
	public void addToSpecies(NEATGenome<D> genome, XId speciesId) {
		validateArg("genome", this, containsElement(genome));
		validateArg("speciesId", speciesMap, containsKey(speciesId));
		if (genome.getSpeciesId() != null && speciesMap.containsKey(genome.getSpeciesId())) {
			speciesMap.get(genome.getSpeciesId()).remove(genome.getId());
		}
		speciesMap.get(speciesId).add(genome.getId());
		genome.setSpeciesId(speciesId);
	}

	@Override
	public void setSpeciesTargetSize(XId speciesId, int targetSize) {
		validateArg("speciesId", speciesId, isNotNull());
		speciesStats.get(speciesId)[TARGET] = targetSize;
	}

	@Override
	public void setSpeciesEliteSize(XId speciesId, int eliteSize) {
		validateArg("speciesId", speciesId, isNotNull());
		speciesStats.get(speciesId)[ELITE] = eliteSize;
	}

	@Override
	public int getSpeciesTargetSize(XId speciesId) {
		validateArg("speciesId", speciesId, isNotNull());
		return speciesStats.get(speciesId)[TARGET];
	}

	@Override
	public int getSpeciesEliteSize(XId speciesId) {
		validateArg("speciesId", speciesId, isNotNull());
		return speciesStats.get(speciesId)[ELITE];
	}

	@Override
	public XId newSpecies() {
		XId newId = SPECIES_BASE_ID.createChild(getAttribute(SPECIES_NUM_ATTRIBUTE_ID));
		setAttribute(SPECIES_NUM_ATTRIBUTE_ID, newId.getInstanceNum() + 1);
		speciesMap.put(newId, new HashSet<XId>());
		speciesStats.put(newId, new int[STATS_LENGTH]);
		return newId;
	}

	@Override
	public Set<XId> getSpecies(XId speciesId) {
		validateArg("speciesId", speciesId, isNotNull());
		if (!speciesMap.containsKey(speciesId)) {
			Set<XId> empty = Collections.emptySet();
			return empty;
		}
		return Collections.unmodifiableSet(speciesMap.get(speciesId));
	}

	@Override
	public Set<XId> getSpeciesIds() {
		return Collections.unmodifiableSet(speciesMap.keySet());
	}

	@Override
	public void markSpeciesDead(XId speciesId) {
		deadSpecies.add(speciesId);
	}

	@Override
	public void removeSpecies(XId speciesId) {
		if (speciesMap.containsKey(speciesId)) {
			removeAll(getAll(speciesMap.get(speciesId)));
			speciesMap.remove(speciesId);
			speciesStats.remove(speciesId);
		}
	}

	@Override
	public void removeDeadGenomes() {
		getLogger().trace("begin %s.removeDeadGenomes()", getClass().getSimpleName());
		Iterator<Genome<D>> iter = iterator();
		while (iter.hasNext()) {
			NEATGenome<D> genome = (NEATGenome<D>) iter.next();
			if (genome.isMarkedForDeath()) {
				if (genome.getSpeciesId() != null && speciesMap.containsKey(genome.getSpeciesId())) {
					speciesMap.get(genome.getSpeciesId()).remove(genome.getId());
					genome.setSpeciesId(null);
				}
				iter.remove();
			}
		}
		getLogger().trace("end %s.removeDeadGenomes()", getClass().getSimpleName());
	}

	@Override
	public void removeDeadSpecies() {
		for (XId id : deadSpecies) {
			removeSpecies(id);
		}
		deadSpecies.clear();
	}

	@Override
	public void clearSpecies() {
		for (Genome<D> genome : this) {
			((NEATGenome<D>) genome).setSpeciesId(null);
		}
		speciesMap.clear();
		speciesStats.clear();
	}
}
