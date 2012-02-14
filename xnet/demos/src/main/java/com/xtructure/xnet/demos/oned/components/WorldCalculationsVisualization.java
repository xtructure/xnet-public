/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.xtructure.xnet.demos.oned.components;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.xtructure.xsim.gui.impl.AbstractSimpleStandardXVisualization;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * The Class WorldCalculationsVisualization.
 *
 * @author Luis Guimbarda
 */
public class WorldCalculationsVisualization extends
		AbstractSimpleStandardXVisualization {
	
	/** array of names for columns in {@link WorldCalculationsVisualization} objects' data tables. */
	private static final String[] COLUMN_NAMES;
	
	/** default id for {@link WorldCalculationsVisualization} objects. */
	private static final XId VIZ_ID;
	static {
		COLUMN_NAMES = new String[] {//
		//
				"Id",//
				"Current Value",//
		};
		VIZ_ID = XId.newId("WorldCalculationsVisualization");
	}

	/**
	 * Gets the single instance of WorldCalculationsVisualization.
	 *
	 * @param rowIds a list of indicating the row order in which object data is
	 * displayed
	 * @param world the world from which data is taken
	 * @return a new {@link WorldCalculationsVisualization} object
	 */
	public static WorldCalculationsVisualization getInstance(List<XId> rowIds,
			OneDWorld world) {
		Set<XId> targetIds = new SetBuilder<XId>()//
				.addAll(rowIds)//
				.newImmutableInstance();
		WorldCalculationsVisualization viz = new WorldCalculationsVisualization(
				targetIds, rowIds);

		SimpleXBorder border = new SimpleXBorder();
		for (XId id : rowIds) {
			viz.initData(id, world.getData(id));

			border.addComponent(viz);
			border.addComponent(world);
			border.associate(//
					new XAddressImpl(world, id),//
					null,//
					new XAddressImpl(viz, id));
		}

		return viz;
	}

	/** the frame containing this visualization's data table. */
	private final JInternalFrame frame;
	
	/** table for displaying the visualization's data. */
	private final JTable table;
	
	/** map from an object index to its row in the data table. */
	private final Map<XId, Integer> rowIndexMap;
	
	/** the array containing the table's data. */
	private final Object[][] data;

	/**
	 * Creates a new {@link WorldCalculationsVisualization} object.
	 * 
	 * @param targetIds
	 *            the id's of data consumed by this component
	 * @param rowIds
	 *            the order of the rows
	 */
	private WorldCalculationsVisualization(Set<XId> targetIds, List<XId> rowIds) {
		super(VIZ_ID, targetIds);
		this.frame = new JInternalFrame(VIZ_ID.getBase(), true, true, true,
				true);
		this.table = new JTable(new TableModel());
		this.data = new Object[rowIds.size()][COLUMN_NAMES.length];
		this.rowIndexMap = new HashMap<XId, Integer>();
		for (int i = 0; i < rowIds.size(); i++) {
			rowIndexMap.put(rowIds.get(i), i);
		}

		JPanel jp = new JPanel(new GridLayout(1, 1));
		table.setFillsViewportHeight(true);
		jp.add(new JScrollPane(table));
		frame.setContentPane(jp);
		frame.setTitle(VIZ_ID.getBase());
		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xsim.impl.AbstractSimpleStandardXComponent#setData(com.
	 * xtructure.xutil.id.XId, java.lang.Object)
	 */
	@Override
	protected void setData(XId partId, Object data) {
		if (partId == null || data == null) {
			return;
		}

		int index = rowIndexMap.get(partId);
		table.setValueAt(data, index, 1);
	}

	/**
	 * Performs a one time set of data that doesn't change during simulation
	 * (e.g., object id's)
	 * 
	 * @param partId
	 *            the id of the part, the data of which should be set
	 * @param data
	 *            the data to be set
	 */
	private void initData(XId partId, Object data) {
		int index = rowIndexMap.get(partId);
		table.setValueAt(partId, index, 0);
		setData(partId, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.gui.XVisualization#getInternalFrame()
	 */
	@Override
	public JInternalFrame getInternalFrame() {
		return frame;
	}

	/**
	 * Model for WorldCalculationVisualization tables.
	 */
	private final class TableModel extends AbstractTableModel {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return data.length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
		 * int, int)
		 */
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = aValue;
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}
}
