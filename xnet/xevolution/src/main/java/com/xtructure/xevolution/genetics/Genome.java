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

import java.util.Comparator;

import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * The {@link Genome} interface is for individuals on which evolution is to be
 * applied. It carries an arbitrary payload for it's chromosome data. It's
 * primary attributes are fitness, complexity, evaluation count, and death mark.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 */
public interface Genome<D> extends GeneticsObject {
	/**
	 * {@link XValId} of the first parent (or only parent if produced by
	 * mutation)
	 */
	public static final XValId<XId>				PARENT1_ID				= XValId.newId("parent1", XId.class);
	/** {@link XValId} of the second parent */
	public static final XValId<XId>				PARENT2_ID				= XValId.newId("parent2", XId.class);
	/** {@link XValId} of the applied {@link Operator} */
	public static final XValId<String>			APPLIED_OP_ID			= XValId.newId("appliedOp", String.class);
	/** {@link XValId} of the fitness attribute */
	public static final XValId<Double>			FITNESS_ATTRIBUTE_ID	= XValId.newId("fitness", Double.class);
	/** {@link XValId} of the complexity attribute */
	public static final XValId<Double>			COMPLEXITY_ATTRIBUTE_ID	= XValId.newId("complexity", Double.class);
	/** {@link XValId} of the evaluation count attribute */
	public static final XValId<Long>			EVAL_COUNT_ATTRIBUTE_ID	= XValId.newId("evaluationCount", Long.class);
	/** the {@link XValId} for the deathMark attribute */
	public static final XValId<Boolean>			DEATH_MARK_ATTRIBUTE_ID	= XValId.newId("deathMark", Boolean.class);
	/** a {@link Comparator} for sorting genomes by fitness */
	public static final Comparator<Genome<?>>	BY_FITNESS				= new ByAttribute<Genome<?>, Double>(FITNESS_ATTRIBUTE_ID, false);
	/** a {@link Comparator} for sorting genomes by fitness in descending order */
	public static final Comparator<Genome<?>>	BY_FITNESS_DESC			= new ByAttribute<Genome<?>, Double>(FITNESS_ATTRIBUTE_ID, true);

	/**
	 * Gets this {@link Genome}'s chromosome data.
	 * 
	 * @return this {@link Genome}'s chromosome data.
	 */
	public D getData();

	/**
	 * Increments the evaluation count for this {@link Genome}.
	 */
	public void incrementEvaluationCount();

	/**
	 * Returns the evaluation count for this {@link Genome}.
	 * 
	 * @return the evaluation count for this {@link Genome}.
	 */
	public long getEvaluationCount();

	/**
	 * Returns the fitness for this genome.
	 * 
	 * @return the fitness for this genome.
	 */
	public double getFitness();

	/**
	 * Sets the finess for this genome.
	 * 
	 * @param fitness
	 *            the fitness to set
	 */
	public void setFitness(double fitness);

	/**
	 * Returns the complexity of this genome.
	 * 
	 * @return the complexity of this genome.
	 */
	public double getComplexity();

	/**
	 * Marks this {@link Genome} for death, i.e., this {@link Genome} is not
	 * intended to survive to the next generation.
	 */
	public void markForDeath();

	/**
	 * Indicates if this {@link Genome} is marked for death, i.e., if this
	 * {@link Genome} is not intended to survive to the next generation.
	 * 
	 * @return true if this {@link Genome} is marked for death, false otherwise.
	 */
	public Boolean isMarkedForDeath();
}
