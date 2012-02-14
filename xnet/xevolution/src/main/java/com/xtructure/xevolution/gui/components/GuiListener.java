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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.gui.VisualizeData;
import com.xtructure.xevolution.gui.XEvolutionGui;
import com.xtructure.xevolution.tool.data.DataTracker;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlReader;

/**
 * {@link GuiListener} is an {@link ActionListener} and {@link MouseListener}
 * that updates the appropriate components in the "Generations" and "Genealogy"
 * tab of the {@link XEvolutionGui} when the user interacts with it.
 * 
 * @author Luis Guimbarda
 * 
 */
public class GuiListener implements ActionListener, MouseListener {
	/** the {@link PopulationPanel} for this {@link GuiListener} */
	private final PopulationPanel	populationPanel;
	/** the {@link GenomePanel} for this {@link GuiListener} */
	private final GenomePanel		genomePanel;
	/** the {@link GenealogyPanel} for this {@link GuiListener} */
	private final GenealogyPanel	genealogyPanel;
	/** */
	private final VisualizeData		visualizeData;
	/** */
	private final DataTracker<?, ?>	dataTracker;

	/**
	 * Creates a new {@link GuiListener} and adds it as listener to the
	 * appropriate components in the given {@link PopulationPanel} and
	 * {@link GenomePanel}
	 * 
	 * @param populationPanel
	 *            the {@link PopulationPanel} for this {@link GuiListener}
	 * @param genomePanel
	 *            the {@link GenomePanel} for this {@link GuiListener}
	 * @param genealogyPanel
	 *            the {@link GenealogyPanel} for this {@link GuiListener}
	 * @param visualizeData
	 * @param dataTracker
	 */
	public GuiListener(PopulationPanel populationPanel, GenomePanel genomePanel, GenealogyPanel genealogyPanel, VisualizeData visualizeData, DataTracker<?, ?> dataTracker) {
		this.populationPanel = populationPanel;
		this.genomePanel = genomePanel;
		this.genealogyPanel = genealogyPanel;
		this.visualizeData = visualizeData;
		this.dataTracker = dataTracker;
		populationPanel.getComboBox().addActionListener(this);
		genomePanel.getComboBox().addActionListener(this);
		genomePanel.getSortComboBox().addActionListener(this);
		genomePanel.getSimButton().addActionListener(this);
		genealogyPanel.getTable().addMouseListener(this);
		genealogyPanel.getSimButton().addActionListener(this);
		genealogyPanel.getActButton().addActionListener(this);
	}

	/** flag indicating that an action is already being performed */
	private boolean	performingAction	= false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void actionPerformed(ActionEvent e) {
		if (performingAction) {
			return;
		}
		performingAction = true;
		if (populationPanel.getComboBox().equals(e.getSource())) {
			populationPanel.setLabels();
			// populate and select genome
			Population<?> pop = populationPanel.getPopulation();
			genomePanel.removeAllItems();
			List<Genome<?>> genomes = new ArrayList<Genome<?>>(pop);
			Collections.sort(genomes, new Genome.ByAttribute(((AliasedValueId) genomePanel.getSortComboBox().getSelectedItem()).getId(), true));
			genomePanel.addAllItems(genomes);
			performingAction = false;
			genomePanel.selectItem(genomes.get(0));
		} else if (genomePanel.getComboBox().equals(e.getSource())) {
			genomePanel.setLabels();
			AliasedGenome aliasedGenome = (AliasedGenome) genomePanel.getComboBox().getSelectedItem();
			if (aliasedGenome != null) {
				if (genealogyPanel.setData(aliasedGenome.getGenome().getId())) {
					cache.clear();
					int index = genealogyPanel.getTable().getRowCount() - 1;
					genealogyPanel.getTable().setRowSelectionInterval(index, index);
					updatedGenealogyView();
				}
			}
		} else if (genomePanel.getSortComboBox().equals(e.getSource())) {
			int index = genomePanel.getComboBox().getSelectedIndex();
			genomePanel.sort();
			genomePanel.getComboBox().setSelectedIndex(-1);
			performingAction = false;
			genomePanel.getComboBox().setSelectedIndex(index);
		} else if (genomePanel.getSimButton().equals(e.getSource())) {
			JFrame frame = visualizeData.createSimVisualization(genomePanel.getGenome());
			if (frame != null) {
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		} else if (genealogyPanel.getSimButton().equals(e.getSource())) {
			int selectedRow = genealogyPanel.getTable().getSelectedRow();
			Genome<?> genome = getGenealogyGenome(selectedRow);
			if (genome != null) {
				JFrame frame = visualizeData.createSimVisualization(genome);
				if (frame != null) {
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			}
		} else if (genealogyPanel.getActButton().equals(e.getSource())) {
			List<Genome<?>> genomes = new ArrayList<Genome<?>>();
			for (int i = 0; i < genealogyPanel.getTable().getRowCount(); i++) {
				genomes.add(getGenealogyGenome(i));
			}
			JPanel panel = visualizeData.createGenomeVisualization(dataTracker, genomes.toArray(new Genome<?>[0]));
			if (panel != null) {
				ScreenShotFrame frame = new ScreenShotFrame((PageablePanel) panel);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setPreferredSize(new Dimension(600, 400));
				frame.pack();
				frame.setVisible(true);
			}
		}
		performingAction = false;
	}

	private final Map<XId, Genome<?>>	cache	= new HashMap<XId, Genome<?>>();

	@Override
	public void mouseClicked(MouseEvent e) {
		if (genealogyPanel.getTable().equals(e.getSource())) {
			updatedGenealogyView();
		}
	}

	private Genome<?> getGenealogyGenome(int selectedRow) {
		if (selectedRow > -1) {
			XId id = (XId) genealogyPanel.getTableData()[selectedRow][0];
			Genome<?> genome = populationPanel.getPopulation().get(id);
			if (genome == null) {
				genome = cache.get(id);
				if (genome == null) {
					try {
						Population<Genome<?>> population = XmlReader.read((File) genealogyPanel.getTableData()[selectedRow][5]);
						genome = population.get(id);
					} catch (IOException e1) {
						e1.printStackTrace();
						return null;
					} catch (XMLStreamException e1) {
						e1.printStackTrace();
						return null;
					}
					cache.put(id, genome);
				}
			}
			return genome;
		}
		return null;
	}

	private void updatedGenealogyView() {
		int selectedRow = genealogyPanel.getTable().getSelectedRow();
		Genome<?> genome = getGenealogyGenome(selectedRow);
		Genome<?> parent = getGenealogyGenome(selectedRow - 1);
		if(visualizeData != null){			
			if (genome != null) {
				if (parent != null) {
					genealogyPanel.replaceView(visualizeData.createGenomeVisualization(genome, parent));
				} else {
					genealogyPanel.replaceView(visualizeData.createGenomeVisualization(genome));
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
