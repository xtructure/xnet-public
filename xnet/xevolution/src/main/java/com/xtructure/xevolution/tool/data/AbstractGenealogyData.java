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
package com.xtructure.xevolution.tool.data;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.tool.GenealogyData;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;

/**
 * The Class AbstractGenealogyData.
 *
 * @param <D> the generic type
 * @author Luis Guimbarda
 */
public abstract class AbstractGenealogyData<D extends AbstractGenealogyData<D>> extends AbstractDataXIdObject<D> implements GenealogyData<D> {
	
	/**
	 * Generates the id for this {@link GenealogyData} of the given genome.
	 *
	 * @param genome the genome
	 * @return the XId
	 */
	public static XId generateId(Genome<?> genome) {
		return genome.getId();
	}

	/**
	 * Creates a new {@link AbstractGenealogyData}.
	 *
	 * @param genome the genome
	 * @param populationFile the population file
	 * @param manager the manager
	 */
	protected AbstractGenealogyData(Genome<?> genome, File populationFile, XIdObjectManager<D> manager) {
		super(generateId(genome), manager, genome, populationFile);
	}

	/**
	 * Creates a new {@link AbstractGenealogyData}.
	 *
	 * @param json the json
	 * @param manager the manager
	 */
	protected AbstractGenealogyData(JSONAware json, XIdObjectManager<D> manager) {
		super(json, manager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.tool.data.AbstractDataXIdObject#processObserved
	 * (com.xtructure.xutil.id.XIdObjectManager, java.lang.Object[])
	 */
	@Override
	protected final void processObserved(XIdObjectManager<D> manager, Object... observed) {
		Genome<?> genome = (Genome<?>) observed[0];
		File populationFile = (File) observed[1];
		processObserved(manager, genome, populationFile);
	}

	/**
	 * Process the observed data.
	 *
	 * @param manager the manager
	 * @param genome the genome
	 * @param populationFile the population file
	 */
	protected void processObserved(XIdObjectManager<D> manager, Genome<?> genome, File populationFile) {
		put(POPULATION_FILE, populationFile.toString());
		put(FITNESS, genome.getFitness());
		XId parent1Id = genome.getAttribute(Genome.PARENT1_ID);
		XId parent2Id = genome.getAttribute(Genome.PARENT2_ID);
		if (parent1Id != null) {
			put(PARENT1, genome.getAttribute(Genome.PARENT1_ID).toString());
			GenealogyData<D> parent1Data = manager.getObject(getParent1Id());
			if (parent1Data != null) {
				parent1Data.addChildren(manager, getId());
			}
		}
		if (parent2Id != null) {
			put(PARENT2, genome.getAttribute(Genome.PARENT2_ID).toString());
			GenealogyData<D> parent2Data = manager.getObject(getParent2Id());
			if (parent2Data != null) {
				parent2Data.addChildren(manager, getId());
			}
		}
		put(APPLIED_OP, genome.getAttribute(Genome.APPLIED_OP_ID));
		put(CHILDREN, new JSONArray());
	}

	/**
	 * Returns the fitness of the {@link Genome} represented by this.
	 *
	 * @return the fitness
	 * {@link AbstractGenealogyData}
	 */
	@Override
	public Double getFitness() {
		return (Double) get(FITNESS);
	}

	/**
	 * Returns the file of the population in which the {@link Genome}
	 * represented by this {@link AbstractGenealogyData} first appears.
	 *
	 * @return the population file
	 */
	@Override
	public File getPopulationFile() {
		return new File(get(POPULATION_FILE).toString());
	}

	/**
	 * Returns the id of the first parent of the {@link Genome} represented by
	 * this {@link AbstractGenealogyData}.
	 *
	 * @return the parent1 id
	 */
	@Override
	public XId getParent1Id() {
		Object parent1 = get(PARENT1);
		if (parent1 != null) {
			return XId.TEXT_FORMAT.parse(parent1.toString());
		}
		return null;
	}

	/**
	 * Returns the id of the second parent of the {@link Genome} represented by
	 * this {@link AbstractGenealogyData}.
	 *
	 * @return the parent2 id
	 */
	@Override
	public XId getParent2Id() {
		Object parent2 = get(PARENT2);
		if (parent2 != null) {
			return XId.TEXT_FORMAT.parse(parent2.toString());
		}
		return null;
	}

	/**
	 * Returns the name of the operator that produced the {@link Genome}
	 * represented by this {@link AbstractGenealogyData}.
	 *
	 * @return the applied op
	 */
	@Override
	public String getAppliedOp() {
		return (String) get(APPLIED_OP);
	}

	/**
	 * Returns a list of children of the {@link Genome} represented by this.
	 *
	 * @return the children
	 * {@link AbstractGenealogyData}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<XId> getChildren() {
		return Collections.unmodifiableList((List<XId>) get(CHILDREN));
	}

	/**
	 * Adds the given ids to this {@link AbstractGenealogyData}'s {@link Genome}
	 * 's children. Any of the ids that do not correspond to a
	 *
	 * @param manager the manager
	 * @param ids the ids
	 * {@link AbstractGenealogyData} object in the given manager are ignored.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addChildren(XIdObjectManager<D> manager, XId... ids) {
		// FIXME : children aren't being added
		for (XId id : ids) {
			GenealogyData<D> childData = manager.getObject(id);
			if (childData != null) {
				((JSONArray) get(CHILDREN)).add(id.toString());
			}
		}
	}

	/**
	 * A factory for creating AbstractGenealogyData objects.
	 *
	 * @param <D> the generic type
	 */
	public static abstract class AbstractGenealogyDataFactory<D extends GenealogyData<D>> implements GenealogyDataFactory<D> {
		
		/* (non-Javadoc)
		 * @see com.xtructure.xevolution.tool.GenealogyData.GenealogyDataFactory#getOrCreateInstance(com.xtructure.xevolution.genetics.Genome, java.io.File, com.xtructure.xutil.id.XIdObjectManager)
		 */
		@Override
		public D getOrCreateInstance(Genome<?> genome, File populationFile, XIdObjectManager<D> manager) {
			D data = manager.getObject(generateId(genome));
			if (data == null) {
				return createInstance(genome, populationFile, manager);
			}
			return data;
		}
	}
}
