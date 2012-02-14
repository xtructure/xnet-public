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
package com.xtructure.xevolution.tool;

import java.io.File;
import java.util.List;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;

/**
 * The Interface GenealogyData.
 *
 * @param <D> the generic type
 * @author Luis Guimbarda
 */
public interface GenealogyData<D extends GenealogyData<D>> extends DataXIdObject<D> {
	
	/** the name of the populationFile data. */
	public static final String	POPULATION_FILE	= "populationFile";
	
	/** the name of the fitness data. */
	public static final String	FITNESS			= "fitness";
	
	/** the name of the parent1 data. */
	public static final String	PARENT1			= "parent1";
	
	/** the name of the parent2 data. */
	public static final String	PARENT2			= "parent2";
	
	/** the name of the appliedOperator data. */
	public static final String	APPLIED_OP		= "appliedOperator";
	
	/** the name of the children data. */
	public static final String	CHILDREN		= "children";

	/**
	 * Returns the fitness of the {@link Genome} represented by this.
	 *
	 * @return the fitness
	 * {@link GenealogyData}
	 */
	public Double getFitness();

	/**
	 * Returns the file of the population in which the {@link Genome}
	 * represented by this {@link GenealogyData} first appears.
	 *
	 * @return the population file
	 */
	public File getPopulationFile();

	/**
	 * Returns the id of the first parent of the {@link Genome} represented by
	 * this {@link GenealogyData}.
	 *
	 * @return the parent1 id
	 */
	public XId getParent1Id();

	/**
	 * Returns the id of the second parent of the {@link Genome} represented by
	 * this {@link GenealogyData}.
	 *
	 * @return the parent2 id
	 */
	public XId getParent2Id();

	/**
	 * Returns the name of the operator that produced the {@link Genome}
	 * represented by this {@link GenealogyData}.
	 *
	 * @return the applied op
	 */
	public String getAppliedOp();

	/**
	 * Returns a list of children of the {@link Genome} represented by this.
	 *
	 * @return the children
	 * {@link GenealogyData}
	 */
	public List<XId> getChildren();

	/**
	 * Adds the given ids to this {@link GenealogyData}'s {@link Genome}'s
	 * children. Any of the ids that do not correspond to a
	 *
	 * @param manager the manager
	 * @param ids the ids
	 * {@link GenealogyData} object in the given manager are ignored.
	 */
	public void addChildren(XIdObjectManager<D> manager, XId... ids);

	/**
	 * A factory for creating GenealogyData objects.
	 *
	 * @param <D> the generic type
	 * {@link GenealogyData} object factory interface
	 */
	public static interface GenealogyDataFactory<D extends GenealogyData<D>> extends DataXIdObjectFactory<D> {
		
		/**
		 * Gets or creates instances of {@link GenealogyData}.
		 *
		 * @param genome the genome
		 * @param populationFile the population file
		 * @param manager the manager
		 * @return the instance
		 */
		public D getOrCreateInstance(Genome<?> genome, File populationFile, XIdObjectManager<D> manager);

		/**
		 * Creates instances of {@link GenealogyData}.
		 *
		 * @param genome the genome
		 * @param populationFile the population file
		 * @param manager the manager
		 * @return the D
		 */
		public D createInstance(Genome<?> genome, File populationFile, XIdObjectManager<D> manager);
	}
}
