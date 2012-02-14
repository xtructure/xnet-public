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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * The {@link Population} interface is for collections of {@link Genome}s and
 * maintaining group statistics as they evolve. The primary attributes of
 * {@link Population}s are age, age of last improvement, and next available
 * genome instance number.
 * 
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 */
public interface Population<D> extends GeneticsObject, Collection<Genome<D>> {
	/** the {@link XValId} for the next genome instance number attribute */
	public static final XValId<Integer>	GENOME_NUM_ATTRIBUTE_ID	= XValId.newId("genomeIdNum", Integer.class);

	/**
	 * Updates the population's statistics based on the current crop of
	 * {@link Genome}s and their attributes.
	 */
	public void refreshStats();

	/**
	 * Returns the {@link Genome} in this population with the given id.
	 * 
	 * @param id
	 *            the id of the {@link Genome} being requested
	 * @return the requested {@link Genome}
	 */
	public Genome<D> get(XId id);

	/**
	 * Returns the set of {@link Genome}s in this population with ids in the
	 * given set.
	 * 
	 * @param ids
	 *            the set ids of genomes to get
	 * @return the set of genomes
	 */
	public Set<Genome<D>> getAll(Collection<XId> ids);

	/**
	 * Returns the set of ids of all {@link Genome}s in this population
	 * 
	 * @return the set of ids of all {@link Genome}s in this population
	 */
	public Set<XId> keySet();

	/**
	 * Returns the age when this population last experienced improvement, i.e.,
	 * the age this population saw a new fittest ever genome.
	 * 
	 * @return the age when this population last experienced improvement
	 */
	long getAgeLastImproved();

	/**
	 * Returns the average of the attribute in this {@link Population}'s
	 * {@link Genome}s with the given {@link XValId}.
	 * 
	 * @param valueId
	 *            the id of the attribute whose average is requested
	 * @return the attribute average over the population
	 */
	public Double getAverageGenomeAttribute(XValId<?> valueId);

	/**
	 * Returns the next available instance id number for {@link Genome}s in this
	 * {@link Population}.
	 * 
	 * @return the next available instance id number
	 */
	public int getGenomeIdNumber();

	/**
	 * Returns the set of attribute ids used by {@link Genome} in this
	 * {@link Population}.
	 * 
	 * @return the set of attribute ids used by {@link Genome} in this
	 *         {@link Population}.
	 */
	public Set<XValId<?>> getGenomeAttributeIds();

	/**
	 * Remove all {@link Genome}s in this {@link Population} that have been
	 * marked for death.
	 */
	public void removeDeadGenomes();

	/**
	 * Returns the {@link Genome} in this {@link Population} with the highest
	 * attribute identified by the given {@link XValId}
	 * 
	 * @param valueId
	 *            the id of the attribute in question
	 * @return the {@link Genome} with the highest such attribute
	 */
	public Genome<D> getHighestGenomeByAttribute(XValId<?> valueId);

	/**
	 * Returns the {@link Genome} in this {@link Population} with the lowest
	 * attribute identified by the given {@link XValId}
	 * 
	 * @param valueId
	 *            the id of the attribute in question
	 * @return the {@link Genome} with the lowest such attribute
	 */
	public Genome<D> getLowestGenomeByAttribute(XValId<?> valueId);

	/**
	 * Returns the {@link Genome} that has the highest attribute ever in this
	 * {@link Population}. The genome is not necessarily still in the
	 * {@link Population}.
	 * 
	 * @param valueId
	 *            the id of the attribute in question
	 * @return the {@link Genome} with the highest ever such attribute
	 */
	public Genome<D> getHighestEverGenomeByAttribute(XValId<?> valueId);

	/**
	 * Returns the {@link Genome} that has the lowest attribute ever in this
	 * {@link Population}. The genome is not necessarily still in this
	 * {@link Population}.
	 * 
	 * @param valueId
	 *            the id of the attribute in question
	 * @return the {@link Genome} with the lowest ever such attribute
	 */
	public Genome<D> getLowestEverGenomeByAttribute(XValId<?> valueId);

	/**
	 * Writes this {@link Population} to file in the given output directory.
	 * 
	 * @param outputDir
	 *            the directory to which this {@link Population} is written.
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	public void write(File outputDir) throws IOException, XMLStreamException;
}
