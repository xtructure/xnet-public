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

import com.xtructure.xevolution.evolution.EvolutionObject;
import com.xtructure.xevolution.evolution.impl.AbstractEvolutionObject;

/**
 * The {@link GenomeDecoder} interface describes the method for decoding a
 * {@link Genome} for simulation in the problem domain. Classes implementing
 * this interface create instance of the phenotype represented by a given
 * {@link Genome}'s data.
 * <P>
 * Subclasses should extend {@link AbstractEvolutionObject}, unless users wish
 * to provide their own logging object.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}
 * @param <T>
 *            phenotype of {@link Genome}
 */
public interface GenomeDecoder<D, T> extends EvolutionObject {
	/**
	 * Creates an instance of the phenotype represented by the given
	 * {@link Genome}'s data, which is appropriate for simulating in the problem
	 * domain.
	 * 
	 * @return the created phenotype instance
	 */
	public T decode(Genome<D> genome);
}
