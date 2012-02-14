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

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.gui.XEvolutionGui;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.coll.Transform;
import com.xtructure.xutil.id.XValId;

/**
 * {@link GenomePanel} holds the genomes list and genome data for the
 * "Generations" tab of {@link XEvolutionGui}. The list represents all the
 * {@link Genome}s in the selected {@link Population} in the
 * {@link PopulationPanel}, and the data displayed is for the seleted
 * {@link Genome} here. Genomes can be sorted by any of their attributes.
 * 
 * @author Luis Guimbarda
 * 
 */
public class GenomePanel extends JPanel {
	private static final long			serialVersionUID	= 1L;
	/** the base title string for this {@link GenomePanel} */
	private final String				titleBase			= "Genomes";
	/** the border for this {@link GenomePanel} */
	private final TitledBorder			border				= BorderFactory.createTitledBorder(titleBase);
	/** the list of aliased {@link Genome}s navigable through this panel */
	private final List<AliasedGenome>	genomeList;
	/** the component to display the list of {@link Genome}s */
	private final JComboBox				comboBox;
	/**
	 * the list of aliased {@link XValId}s for attributes by which
	 * {@link Genome}s may be sorted
	 */
	private final List<AliasedValueId>	sortByList;
	/** the component to display the list of {@link XValId}s */
	private final JComboBox				sortComboBox;
	/** the component to display the data for the selected {@link Genome} */
	private final GenomeDataPanel		dataPanel;
	/**
	 * the button that starts a gui simulation with the selected {@link Genome}
	 * 's data
	 */
	private final JButton				simButton;

	/**
	 * Creates a new {@link GenomePanel}
	 */
	public GenomePanel() {
		setBorder(border);
		setLayout(new GridBagLayout());
		this.genomeList = new ArrayList<AliasedGenome>();
		this.sortByList = new ArrayList<AliasedValueId>();
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 2, 2, 2);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		comboBox = new JComboBox();
		add(comboBox, c);
		c.weightx = 0.0;
		c.gridx = 2;
		c.gridy = 0;
		sortComboBox = new JComboBox();
		add(sortComboBox, c);
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.5;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		dataPanel = new GenomeDataPanel();
		add(dataPanel, c);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.fill = GridBagConstraints.NONE;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 2;
		add(buttonPanel, c);
		simButton = new JButton("Sim");
		buttonPanel.add(simButton);
	}

	/**
	 * Adds all of the given {@link Genome}s to this {@link GenomePanel}'s list
	 * of navigable genomes.
	 * 
	 * @param genomes
	 *            the {@link Genome}s to add
	 */
	public void addAllItems(List<Genome<?>> genomes) {
		List<AliasedGenome> aliasedGenomes = new ListBuilder<AliasedGenome>()//
				.addAll(new Transform<AliasedGenome, Genome<?>>() {
					@Override
					public AliasedGenome transform(Genome<?> value) {
						return new AliasedGenome(value, GenomePanel.this);
					}
				},//
				genomes)//
				.newImmutableInstance();
		genomeList.addAll(aliasedGenomes);
		sort();
		border.setTitle(String.format("%s (%d)", titleBase, comboBox.getItemCount()));
		repaint();
	}

	/**
	 * Sorts this {@link GenomePanel}'s list of genomes by the attribute whose
	 * {@link XValId} is selected in the sortComboBox.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sort() {
		XValId<?> valueId = ((AliasedValueId) sortComboBox.getSelectedItem()).getId();
		Collections.sort(genomeList, AliasedGenome.getComparator(new Genome.ByAttribute(valueId, true)));
		comboBox.removeAllItems();
		for (AliasedGenome genome : genomeList) {
			comboBox.addItem(genome);
		}
	}

	public void sortBy(XValId<?> valueId) {
		if (valueId == null) {
			return;
		}
		for (AliasedValueId aliasedValueId : sortByList) {
			if (valueId.equals(aliasedValueId.getId())) {
				sortComboBox.setSelectedItem(aliasedValueId);
				return;
			}
		}
	}

	/**
	 * Adds the given {@link XValId} to the list of available attribute ids by
	 * which genomes may be sorted
	 * 
	 * @param valueId
	 *            the {@link XValId} to add
	 */
	public void addSortByAttributeId(XValId<?> valueId) {
		AliasedValueId aliasedValueId = new AliasedValueId(valueId);
		if (!sortByList.contains(aliasedValueId)) {
			sortByList.add(aliasedValueId);
			sortComboBox.addItem(aliasedValueId);
		}
	}

	/**
	 * Selects the item in this {@link GenomePanel}'s comboBox that corresponds
	 * to the given {@link Genome}
	 * 
	 * @param genome
	 *            the {@link Genome} to select
	 */
	public void selectItem(Genome<?> genome) {
		AliasedGenome aliasedGenome = new AliasedGenome(genome, this);
		comboBox.setSelectedItem(aliasedGenome);
	}

	/**
	 * Clears this {@link GenomePanel} of all navigable {@link Genome}s
	 */
	public void removeAllItems() {
		comboBox.removeAllItems();
		genomeList.clear();
		border.setTitle(String.format("%s (%d)", titleBase, comboBox.getItemCount()));
		repaint();
	}

	/**
	 * Updates this {@link GenomePanel} data panel with the data from the
	 * selected {@link Genome}
	 */
	public void setLabels() {
		Genome<?> genome = ((AliasedGenome) comboBox.getSelectedItem()).getGenome();
		if (genome != null) {
			dataPanel.setLabels(genome);
		}
	}

	/**
	 * Returns the sortComboBox component of this {@link GenomePanel}
	 * 
	 * @return the sortComboBox component of this {@link GenomePanel}
	 */
	public JComboBox getSortComboBox() {
		return sortComboBox;
	}

	/**
	 * Returns the comboBox component of this {@link GenomePanel}
	 * 
	 * @return the comboBox component of this {@link GenomePanel}
	 */
	public JComboBox getComboBox() {
		return comboBox;
	}

	/**
	 * Returns the simButton component of this {@link GenomePanel}
	 * 
	 * @return the simButton component of this {@link GenomePanel}
	 */
	public JButton getSimButton() {
		return simButton;
	}

	/**
	 * Returns the selected {@link Genome} of this {@link GenomePanel}
	 * 
	 * @return the selected {@link Genome} of this {@link GenomePanel}
	 */
	public Genome<?> getGenome() {
		return ((AliasedGenome) comboBox.getSelectedItem()).getGenome();
	}

	/** the data panel class for {@link GenomePanel} */
	private static final class GenomeDataPanel extends JPanel {
		private static final long		serialVersionUID	= 1L;
		/** the {@link JLabel} for the {@link Genome}'s id */
		private final JLabel			idLabel				= new JLabel("-");
		/** the {@link JLabel} for the {@link Genome}'s fitness */
		private final JLabel			fitnessLabel		= new JLabel("-");
		/** the {@link JLabel} for the {@link Genome}'s link count */
		private final JLabel			linkCountLabel		= new JLabel("-");
		/** the {@link JLabel} for the {@link Genome}'s node count */
		private final JLabel			nodeCountLabel		= new JLabel("-");
		/** a text panel for other information about the {@link Genome} */
		private final NumberedTextPanel	textPanel			= new NumberedTextPanel();

		/**
		 * Creates a new {@link GenomeDataPanel}
		 */
		public GenomeDataPanel() {
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(0, 2, 2, 2);
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			int row = 0;
			row = addLabel(c, row, "Id: ", idLabel);
			row = addLabel(c, row, "Fitness: ", fitnessLabel);
			row = addLabel(c, row, "Link count: ", linkCountLabel);
			row = addLabel(c, row, "Node count: ", nodeCountLabel);
			row = addPanel(c, row, textPanel);
		}

		private int addPanel(GridBagConstraints c, int row, JPanel panel) {
			c.fill = GridBagConstraints.BOTH;
			c.weighty = 0.5;
			c.weightx = 0.0;
			c.gridx = 0;
			c.gridy = row;
			c.gridwidth = 2;
			add(new JScrollPane(panel), c);
			return row + 1;
		}

		private int addLabel(GridBagConstraints c, int row, String labelName, JLabel label) {
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = Double.MIN_VALUE;
			c.weighty = 0.0;
			c.gridx = 0;
			c.gridy = row;
			add(new JLabel(labelName), c);
			c.weightx = 0.5;
			c.gridx = 1;
			c.gridy = row;
			add(label, c);
			return row + 1;
		}

		/**
		 * Sets this {@link GenomeDataPanel}'s data according to the given
		 * genome
		 * 
		 * @param genome
		 *            the {@link Genome} whose data to set
		 */
		@SuppressWarnings("unchecked")
		public void setLabels(Genome<?> genome) {
			idLabel.setText(genome.getId().toString());
			fitnessLabel.setText(Double.toString(genome.getAttribute(Genome.FITNESS_ATTRIBUTE_ID)));
			idLabel.setToolTipText(genome.getId().toString());
			fitnessLabel.setToolTipText(Double.toString(genome.getAttribute(Genome.FITNESS_ATTRIBUTE_ID)));
			StringBuilder sb = new StringBuilder();
			for (@SuppressWarnings("rawtypes")
			XValId valueId : genome.getAttributes().keySet()) {
				sb.append(String.format("%s : %s\n", valueId, genome.getAttribute(valueId)));
			}
			textPanel.setText(sb.toString());
		}
	}
}
