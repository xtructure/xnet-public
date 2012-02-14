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
package com.xtructure.xneat.evolution.impl;

import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xneat.evolution.NEATSpeciationStrategy;
import com.xtructure.xneat.evolution.config.NEATEvolutionFieldMap;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.Innovation;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.NEATPopulation;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xutil.id.XId;

/**
 * {@link NEATSpeciationStrategyImpl} implements the
 * {@link NEATSpeciationStrategy} interface for use on {@link NEATGenome}s with
 * data payloads of type {@link GeneMap}, providing functionality for
 * partitioning a given {@link NEATPopulation} into species.
 * <P>
 * Calls to {@link NEATSpeciationStrategyImpl#respeciate(Population)} will adjust the
 * {@link NEATPopulation#COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID} attribute,
 * increasing it if there are too many species and decreasing it if there are
 * too few.
 * <P>
 * Compatibility is calculated between two {@link GeneMap}s as follows:
 * <ol>
 * <li>the weights of links with matching {@link Innovation}s are summed.
 * <li>the number of non-matching interior links are counted. interior links are
 * those not at the end of the list of links sorted by {@link Innovation}.
 * <li>the number of non-matching end links are counted. exterior links are
 * those at the end of the list of links started by {@link Innovation}.
 * <li>those sums are multiplied by
 * {@link NEATEvolutionFieldMap#compatibilityWeightDeltaCoefficient()},
 * {@link NEATEvolutionFieldMap#compatibilityDisjointCoefficient()}, and
 * {@link NEATEvolutionFieldMap#compatibilityExcessCoefficient()}, respectively.
 * <li>the sum of those products is the compatibility of the two {@link GeneMap}
 * s.
 * </ol>
 * 
 * @author Luis Guimbarda
 * 
 */
public class NEATSpeciationStrategyImpl extends AbstractNEATSpeciationStrategy<GeneMap> {
	/** factor by which to change the compatibility threshold delta */
	private static final double	COMP_THRESH_DELTA_ACCELERATION			= 1.05;
	/** indicates that the compatibility threshold is being adjusted */
	private boolean				speciationThresholdAdjustmentInProgress	= false;
	/** the amount by which to shift the compatibility threshold */
	private double				compatibilityThresholdDelta				= 0.0;

	/**
	 * Creates a new {@link NEATSpeciationStrategyImpl}
	 * 
	 * @param evolutionFieldMap
	 *            the {@link NEATEvolutionFieldMap} used by this
	 *            {@link NEATSpeciationStrategyImpl}
	 */
	public NEATSpeciationStrategyImpl(NEATEvolutionFieldMap evolutionFieldMap) {
		super(evolutionFieldMap);
	}

	@Override
	public void speciate(Population<GeneMap> population) {
		getLogger().trace("start %s.speciate(%s)", getClass().getSimpleName(), population);
		NEATPopulation<GeneMap> pop = (NEATPopulation<GeneMap>) population;
		pop.clearSpecies();
		if (!pop.isEmpty()) {
			// add genomes to closest species
			for (Genome<GeneMap> genome : population) {
				XId speciesId = findClosestSpecies(genome, pop, pop.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID));
				if (speciesId == null) {
					speciesId = pop.newSpecies();
				}
				pop.addToSpecies((NEATGenome<GeneMap>) genome, speciesId);
			}
		}
		getLogger().trace("end %s.speciate()", getClass().getSimpleName());
	}

	@Override
	public void speciateChildren(Set<Genome<GeneMap>> children, Population<GeneMap> population) {
		getLogger().trace("start %s.speciateChildren(%s, %s)", getClass().getSimpleName(), children, population);
		population.addAll(children);
		NEATPopulation<GeneMap> pop = (NEATPopulation<GeneMap>) population;
		// place the children with their closest species
		for (Genome<GeneMap> genome : children) {
			XId speciesId = findClosestSpecies(genome, pop, pop.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID));
			if (speciesId == null) {
				speciesId = pop.newSpecies();
			}
			pop.addToSpecies((NEATGenome<GeneMap>) genome, speciesId);
		}
		getLogger().trace("end %s.speciateChildren()", getClass().getSimpleName());
	}

	@Override
	public void respeciate(Population<GeneMap> population) {
		getLogger().trace("start %s.respeciate(%s)", getClass().getSimpleName(), population);
		NEATPopulation<GeneMap> pop = (NEATPopulation<GeneMap>) population;
		boolean respeciate = false;
		double compatabilityThreshold = pop.getAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID);
		if (pop.getSpeciesIds().size() < getEvolutionFieldMap().targetSpeciesCountMin()) {
			// too few species, decrease threshold
			if (speciationThresholdAdjustmentInProgress) {
				if (compatibilityThresholdDelta < 0.0) {
					// correct direction, increase delta
					compatibilityThresholdDelta *= COMP_THRESH_DELTA_ACCELERATION;
				} else {
					// wrong direction, reduce and flip
					compatibilityThresholdDelta *= -0.5;
				}
			} else {
				// start new adjustment phase
				speciationThresholdAdjustmentInProgress = true;
				compatibilityThresholdDelta = -Math.max(0.1, compatabilityThreshold * 0.01);
			}
			// adjust speciation threshold
			compatabilityThreshold = Math.max(0.01, compatabilityThreshold + compatibilityThresholdDelta);
			respeciate = true;
		} else if (pop.getSpeciesIds().size() > getEvolutionFieldMap().targetSpeciesCountMax()) {
			// too many species, increase threshold
			if (speciationThresholdAdjustmentInProgress) {
				// start new adjustment phase
				if (compatibilityThresholdDelta < 0.0) {
					// wrong direction, reduce and flip
					compatibilityThresholdDelta *= -0.5;
				} else {
					// correct direction, increase delta
					compatibilityThresholdDelta *= COMP_THRESH_DELTA_ACCELERATION;
				}
			} else {
				// start new adjustment phase
				speciationThresholdAdjustmentInProgress = true;
				compatibilityThresholdDelta = Math.max(0.1, compatabilityThreshold * 0.01);
			}
			// adjust speciation threshold
			compatabilityThreshold = Math.max(0.01, compatabilityThreshold + compatibilityThresholdDelta);
			respeciate = true;
		} else {
			// number of species ok
			speciationThresholdAdjustmentInProgress = false;
		}
		if (!respeciate) {
			// TODO: implement complexity check
		}
		if (respeciate) {
			pop.setAttribute(NEATPopulation.COMPATIBILITY_THRESHOLD_ATTRIBUTE_ID, compatabilityThreshold);
			speciate(population);
			// TODO: implement pruning check
		}
		getLogger().trace("end %s.respeciate()", getClass().getSimpleName());
	}

	private static final double	BOOL_VAL	= 10.0;

	/**
	 * Calculates the compatibility of the two given genomes.
	 * 
	 * @param genome1
	 *            the first genome to compare
	 * @param genome2
	 *            the second genome to compare
	 * @return the compatibility of the two given genomes
	 */
	private double getCompatability(Genome<GeneMap> genome1, Genome<GeneMap> genome2) {
		getLogger().trace("start %s.getCompatability(%s, %s)", getClass().getSimpleName(), genome1, genome2);
		List<LinkGene> links1 = genome1.getData().getLinks();
		List<LinkGene> links2 = genome2.getData().getLinks();
		if (links1.isEmpty() && links2.isEmpty()) {
			return 0.0;
		}
		Map<XId, Double> weightDiffMap = new HashMap<XId, Double>();
		int linksIndex1 = 0;
		int linksIndex2 = 0;
		int disjointCount = 0;
		int excessCount = 0;
		while (linksIndex1 <= links1.size() && linksIndex2 <= links2.size()) {
			if (linksIndex1 == links1.size()) {
				// rest are excess
				excessCount += links2.size() - linksIndex2;
				break;
			}
			if (linksIndex2 == links2.size()) {
				// rest are excess
				excessCount += links1.size() - linksIndex1;
				break;
			}
			Innovation inn1 = links1.get(linksIndex1).getInnovation();
			Innovation inn2 = links2.get(linksIndex2).getInnovation();
			if (inn1.compareTo(inn2) < 0) {
				disjointCount++;
				linksIndex1++;
			} else if (inn1.compareTo(inn2) > 0) {
				disjointCount++;
				linksIndex2++;
			} else {
				LinkGene link1 = links1.get(linksIndex1);
				LinkGene link2 = links2.get(linksIndex2);
				validateState("links have same config", link1.getFieldMap().getFieldIds(), isEqualTo(link2.getFieldMap().getFieldIds()));
				for (XId id : link1.getFieldMap().getFieldIds()) {
					Object attr1 = link1.getFieldMap().getField(id).getValue();
					Object attr2 = link2.getFieldMap().getField(id).getValue();
					double weightDiff = 0.0;
					if (Number.class.isAssignableFrom(attr1.getClass())) {
						weightDiff = ((Number) attr1).doubleValue() - ((Number) attr2).doubleValue();
					} else if (id.getClass().equals(Boolean.class)) {
						weightDiff = (((Boolean) attr1) ? BOOL_VAL : -BOOL_VAL) + (((Boolean) attr2) ? BOOL_VAL : -BOOL_VAL);
					}
					Double acc = weightDiffMap.get(id);
					acc = acc == null ? 0.0 : acc;
					weightDiffMap.put(id, acc + weightDiff * weightDiff);
				}
				linksIndex1++;
				linksIndex2++;
			}
		}
		int norm = Math.max(links1.size(), links2.size());
		double weightDiffMag = 0.0;
		for (XId id : weightDiffMap.keySet()) {
			weightDiffMag += weightDiffMap.get(id);
		}
		weightDiffMag = Math.sqrt(weightDiffMag);
		double matchScore = weightDiffMag * getEvolutionFieldMap().compatibilityWeightDeltaCoefficient();
		double disjointScore = disjointCount * getEvolutionFieldMap().compatibilityDisjointCoefficient();
		double excessScore = excessCount * getEvolutionFieldMap().compatibilityExcessCoefficient();
		double rVal = (matchScore + disjointScore + excessScore) / norm;
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getCompatability()", getClass().getSimpleName());
		return rVal;
	}

	/**
	 * Returns the species from among the given set of species whose
	 * compatibility is most below the compatibility threshold. The comparisons
	 * are made with a species most fit genome (or a random genome of the
	 * fittest is not known).
	 * 
	 * @param genome
	 *            the genome to compare
	 * @param population
	 *            the population containing the collection of species to compare
	 * @param compatibilityThreshold
	 *            the threshold for compatibility, below which a genome is
	 *            considered compatible with a species
	 * @return the most compatible species, or null if no species is compatible
	 */
	private XId findClosestSpecies(Genome<GeneMap> genome, NEATPopulation<GeneMap> population, double compatibilityThreshold) {
		getLogger().trace("start %s.findClosestSpecies(%s, %s, %f)", getClass().getSimpleName(), genome, population, compatibilityThreshold);
		XId bestSpecies = null;
		double bestCompatibility = compatibilityThreshold;
		for (XId speciesId : population.getSpeciesIds()) {
			double compatibility;
			if (population.getSpecies(speciesId).isEmpty()) {
				continue;
			}
			List<Genome<GeneMap>> genomes = new ArrayList<Genome<GeneMap>>(population.getAll(population.getSpecies(speciesId)));
			Collections.sort(genomes, Genome.BY_FITNESS_DESC);
			compatibility = getCompatability(genome, genomes.get(0));
			if (compatibility < bestCompatibility) {
				bestSpecies = speciesId;
				bestCompatibility = compatibility;
			}
		}
		getLogger().trace("will return: %s", bestSpecies);
		getLogger().trace("end %s.findClosestSpecies()", getClass().getSimpleName());
		return bestSpecies;
	}
}
