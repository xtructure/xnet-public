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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.xtructure.xevolution.tool.GenealogyData;
import com.xtructure.xevolution.tool.data.DataTracker;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.coll.Transform;
import com.xtructure.xutil.id.XId;

public class GenealogyPanel extends JPanel {
	private static final long		serialVersionUID	= 1L;
	private static final String[]	COLUMN_NAMES;
	static {
		COLUMN_NAMES = new String[] {//
		//
			"Id",//
			"Fitness",//
			"Parent1Id",//
			"Parent2Id",//
			"AppliedOp",//
			"PopulationFile"//
		};
	}
	private final JTable			table;
	private Object[][]				tableData;
	private JPanel					view;
	private JButton					simButton;
	private JButton					actButton;
	private final DataTracker<?, ?> dataTracker;

	public GenealogyPanel(DataTracker<?, ?> dataTracker) {
		this.dataTracker = dataTracker;
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		table = new JTable(new TableModel(this));
		tableData = new Object[0][];
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.weightx = Double.MIN_VALUE;
		gbc.weighty = Double.MIN_VALUE * 2;
		add(new JScrollPane(table), gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = Double.MIN_VALUE * 2;
		gbc.weighty = Double.MIN_VALUE;
		view = new JPanel();
		view.setLayout(new GridLayout());
		view.setBackground(Color.BLACK);
		add(view, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_END;
		simButton = new JButton("Sim");
		actButton = new JButton("Act");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = Double.MIN_VALUE * 2;
		add(simButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 0.0;
		add(actButton, gbc);
	}

	public JButton getSimButton() {
		return this.simButton;
	}

	public JButton getActButton() {
		return actButton;
	}

	public JTable getTable() {
		return this.table;
	}

	public Object[][] getTableData() {
		return this.tableData;
	}

	public boolean setData(XId id) {
		GenealogyData<?> data = (GenealogyData<?>) dataTracker.getGenealogyDataManager().getObject(id);
		if (data == null) {
			return false;
		}
		LinkedList<GenealogyData<?>> gds = new LinkedList<GenealogyData<?>>();
		gds.addFirst(data);
		while (true) {
			XId parent1Id = data.getParent1Id();
			if(parent1Id == null){
				break;
			}
			data = (GenealogyData<?>) dataTracker.getGenealogyDataManager().getObject(parent1Id);
			if (data == null) {
				break;
			}
			gds.addFirst(data);
		}
		tableData = new ListBuilder<Object[]>()//
				.addAll(new Transform<Object[], GenealogyData<?>>() {
					@Override
					public Object[] transform(GenealogyData<?> value) {
						return new Object[] {//
						//
								value.getId(),//
								value.getFitness(),//
								value.getParent1Id(),//
								value.getParent2Id(),//
								value.getAppliedOp(),//
								value.getPopulationFile(),//
						};
					}
				}, gds)//
				.newImmutableInstance().toArray(new Object[0][]);
		((TableModel) table.getModel()).fireTableStructureChanged();
		return true;
	}

	public void replaceView(JPanel... vs) {
		view.removeAll();
		for (JPanel v : vs) {
			view.add(new JScrollPane(v));
		}
		validate();
	}

	private final class TableModel extends AbstractTableModel {
		private static final long		serialVersionUID	= 1L;
		private final GenealogyPanel	panel;

		public TableModel(GenealogyPanel panel) {
			this.panel = panel;
		}

		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}

		@Override
		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		@Override
		public int getRowCount() {
			return panel.tableData.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return panel.tableData[rowIndex][columnIndex];
		}
	}
}
