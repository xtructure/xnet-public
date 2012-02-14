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
package com.xtructure.xevolution.genetics.impl;

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlUnit.Element;

/**
 * {@link AbstractGenome} implements the getters for the {@link Genome}
 * interface, including the {@link Genome}'s chromosome payload.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 */
public abstract class AbstractGenome<D> extends AbstractGeneticsObject implements Genome<D> {
	/** the base id for {@link Genome} */
	public static final XId	GENOME_BASE_ID	= XId.newId("Genome");
	/** chromosome data for this {@link Genome} */
	protected final D		data;

	/**
	 * Creates a new {@link AbstractGenome}.
	 * 
	 * @param idNumber
	 *            the instance number for this {@link Genome}'s id
	 * @param data
	 *            the chromosome payload for this {@link Genome}
	 */
	public AbstractGenome(int idNumber, D data) {
		super(GENOME_BASE_ID.createChild(idNumber));
		this.data = data;
		validate();
		setAttribute(AGE_ATTRIBUTE_ID, 0l);
		setAttribute(EVAL_COUNT_ATTRIBUTE_ID, 0l);
		setAttribute(DEATH_MARK_ATTRIBUTE_ID, false);
		setAttribute(FITNESS_ATTRIBUTE_ID, 0.0);
	}

	/**
	 * Creates a {@link AbstractGenome} that's a copy of the given
	 * {@link Genome}.
	 * 
	 * @param idNumber
	 *            the instance number for this {@link Genome}'s id
	 * @param genome
	 *            the {@link Genome} whose chromosome data is to be copied
	 */
	public AbstractGenome(int idNumber, Genome<D> genome) {
		this(idNumber, genome.getData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.EvoObject#getBaseId()
	 */
	@Override
	public XId getBaseId() {
		return GENOME_BASE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Genome#getData()
	 */
	@Override
	public D getData() {
		getLogger().trace("begin %s.getData(%s)", getClass().getSimpleName());
		D rVal = data;
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getData()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Genome#getFitness()
	 */
	@Override
	public double getFitness() {
		return getAttribute(FITNESS_ATTRIBUTE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Genome#setFitness(double)
	 */
	@Override
	public void setFitness(double fitness) {
		setAttribute(FITNESS_ATTRIBUTE_ID, fitness);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Genome#getComplexity()
	 */
	@Override
	public double getComplexity() {
		return getAttribute(COMPLEXITY_ATTRIBUTE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Genome#getEvaluationCount()
	 */
	@Override
	public long getEvaluationCount() {
		return getAttribute(EVAL_COUNT_ATTRIBUTE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Genome#incrementEvaluationCount()
	 */
	@Override
	public void incrementEvaluationCount() {
		setAttribute(EVAL_COUNT_ATTRIBUTE_ID, getEvaluationCount() + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Genome#markForDeath()
	 */
	@Override
	public void markForDeath() {
		setAttribute(DEATH_MARK_ATTRIBUTE_ID, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Genome#isMarkedForDeath()
	 */
	@Override
	public Boolean isMarkedForDeath() {
		return getAttribute(DEATH_MARK_ATTRIBUTE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#validate()
	 */
	@Override
	public void validate() {
		validateState("data", data, isNotNull());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getId().toString();
	}

	/** xml format for {@link AbstractGenome} */
	protected static abstract class AbstractXmlFormat<D, G extends AbstractGenome<D>> extends AbstractGeneticsObject.AbstractXmlFormat<G> {
		protected AbstractXmlFormat(Class<G> cls) {
			super(cls);
		}

		protected abstract Element<D> getDataElement();

		@Override
		protected void writeElements(G obj, OutputElement xml) throws XMLStreamException {
			super.writeElements(obj, xml);
			getDataElement().write(xml, obj.getData());
		}
	}
}
