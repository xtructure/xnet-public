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
package com.xtructure.xevolution.gui.components;

import java.util.Comparator;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.id.XValId;

/**
 * {@link AliasedGenome} provides an alternate {@link #toString()} for
 * {@link Genome}s.
 * 
 * @author Luis Guimbarda
 * 
 */
public class AliasedGenome {
	/**
	 * Creates a {@link Comparator} for {@link AliasedGenome}s that wraps the
	 * given {@link Genome} {@link Comparator}
	 * 
	 * @param comparator
	 *            the {@link Genome} {@link Comparator} to wrap
	 * @return the new {@link AliasedGenome} {@link Comparator}
	 */
	public static Comparator<AliasedGenome> getComparator(final Comparator<Genome<?>> comparator) {
		return new Comparator<AliasedGenome>() {
			@Override
			public int compare(AliasedGenome o1, AliasedGenome o2) {
				return comparator.compare(o1.getGenome(), o2.getGenome());
			}
		};
	}

	/** the aliased {@link Genome} */
	private final Genome<?>		genome;
	/** the {@link GenomePanel} to which the aliased {@link Genome} belongs */
	private final GenomePanel	genomePanel;

	/**
	 * Creates a new {@link AliasedGenome}
	 * 
	 * @param genome
	 *            the aliased {@link Genome}
	 * @param genomePanel
	 *            the {@link GenomePanel} to which the aliased {@link Genome}
	 *            belongs
	 */
	public AliasedGenome(Genome<?> genome, GenomePanel genomePanel) {
		this.genome = genome;
		this.genomePanel = genomePanel;
	}

	/**
	 * Returns the aliased {@link Genome}
	 * 
	 * @return the aliased {@link Genome}
	 */
	public Genome<?> getGenome() {
		return genome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj != null && obj instanceof AliasedGenome) {
			return this.getGenome().getId().equals(((AliasedGenome) obj).getGenome().getId());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		@SuppressWarnings("rawtypes")
		XValId id = ((AliasedValueId) genomePanel.getSortComboBox().getSelectedItem()).getId();
		return String.format("%s    (%s)", genome.getId(), genome.getAttribute(id));
	}
}
