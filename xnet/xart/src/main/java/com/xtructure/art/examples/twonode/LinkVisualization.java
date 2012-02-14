/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.examples.twonode;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.link.Link.Fragment;
import com.xtructure.xsim.gui.impl.AbstractSimpleStandardXVisualization;
import com.xtructure.xsim.impl.AbstractStandardXComponent;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.id.XId;

/**
 * The Class LinkVisualization.
 *
 * @author Luis Guimbarda
 */
public class LinkVisualization extends AbstractSimpleStandardXVisualization {
	
	/** The Constant COLUMN_NAMES. */
	private static final String[]	COLUMN_NAMES;
	
	/** The Constant ID_COLUMN_INDEX. */
	private static final int		ID_COLUMN_INDEX;
	
	/** The Constant SOURCE_ID_COLUMN_INDEX. */
	private static final int		SOURCE_ID_COLUMN_INDEX;
	
	/** The Constant TARGET_ID_COLUMN_INDEX. */
	private static final int		TARGET_ID_COLUMN_INDEX;
	
	/** The Constant OUTPUT_ENERGY_COLUMN_INDEX. */
	private static final int		OUTPUT_ENERGY_COLUMN_INDEX;
	
	/** The Constant STRENGTH_COLUMN_INDEX. */
	private static final int		STRENGTH_COLUMN_INDEX;
	
	/** The Constant CAPACITY_COLUMN_INDEX. */
	private static final int		CAPACITY_COLUMN_INDEX;
	static {
		COLUMN_NAMES = new String[] {//
		//
			"Id",//
			"SourceId",//
			"TargetId",//
			"Output Energy",//
			"Strength",//
			"Capacity",//
		};
		ID_COLUMN_INDEX = 0;
		SOURCE_ID_COLUMN_INDEX = 1;
		TARGET_ID_COLUMN_INDEX = 2;
		OUTPUT_ENERGY_COLUMN_INDEX = 3;
		STRENGTH_COLUMN_INDEX = 4;
		CAPACITY_COLUMN_INDEX = 5;
	}
	
	/** The frame. */
	private final JInternalFrame	frame;
	
	/** The table. */
	private final JTable			table;
	
	/** The data. */
	private final Object[][]		data;
	
	/** The index map. */
	private final Map<XId, Integer>	indexMap;

	/**
	 * Gets the single instance of LinkVisualization.
	 *
	 * @param id the id
	 * @param network the network
	 * @param targetLinks the target links
	 * @return single instance of LinkVisualization
	 */
	public static LinkVisualization getInstance(XId id, AbstractStandardXComponent network, List<Link> targetLinks) {
		// instantiate visualization
		Map<XId, Integer> indexMap = new HashMap<XId, Integer>();
		Set<XId> targetIds = new HashSet<XId>();
		int index = 0;
		for (Link link : targetLinks) {
			indexMap.put(link.getId(), index++);
			targetIds.add(Fragment.CAPACITY.generateExtendedId(link.getId()));
			targetIds.add(Fragment.OUTPUT_ENERGY.generateExtendedId(link.getId()));
			targetIds.add(Fragment.STRENGTH.generateExtendedId(link.getId()));
		}
		LinkVisualization linkViz = new LinkVisualization(id, targetLinks.size(), targetIds, indexMap);
		// define border
		SimpleXBorder border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(linkViz);
		for (XId linkId : targetIds) {
			// associate the network link with the visualization
			border.associate(new XAddressImpl(network, linkId), new XAddressImpl(linkViz, linkId));
		}
		// initialize visualization
		for (Link link : targetLinks) {
			linkViz.initData(link, network);
		}
		return linkViz;
	}

	/**
	 * Instantiates a new link visualization.
	 *
	 * @param id the id
	 * @param linkCount the link count
	 * @param targetIds the target ids
	 * @param indexMap the index map
	 */
	public LinkVisualization(XId id, int linkCount, Set<XId> targetIds, Map<XId, Integer> indexMap) {
		super(id, targetIds);
		frame = new JInternalFrame(id.getBase(), true, true, true, true);
		table = new JTable(new TableModel());
		data = new Object[linkCount][COLUMN_NAMES.length];
		this.indexMap = new HashMap<XId, Integer>(indexMap);
		JPanel jp = new JPanel(new GridLayout(1, 1));
		table.setFillsViewportHeight(true);
		jp.add(new JScrollPane(table));
		frame.setContentPane(jp);
		frame.setTitle(id.getBase());
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
		int index = indexMap.get(Fragment.getBaseId(partId));
		switch (Fragment.getInstance(partId)) {
			case CAPACITY: {
				table.setValueAt(data, index, CAPACITY_COLUMN_INDEX);
				break;
			}
			case OUTPUT_ENERGY: {
				table.setValueAt(data, index, OUTPUT_ENERGY_COLUMN_INDEX);
				break;
			}
			case STRENGTH: {
				table.setValueAt(data, index, STRENGTH_COLUMN_INDEX);
				break;
			}
			default: {
				break;
			}
		}
	}

	/**
	 * The id, source id, and target id columns never change, so they only get
	 * written during initialization. Otherwise, set data as usual.
	 * 
	 * @param link
	 *            the link
	 * @param network
	 *            the network
	 */
	private void initData(Link link, AbstractStandardXComponent network) {
		int index = indexMap.get(link.getId());
		table.setValueAt(//
				link.getId(),//
				index, ID_COLUMN_INDEX);
		table.setValueAt(//
				link.getSourceId(),//
				index, SOURCE_ID_COLUMN_INDEX);
		table.setValueAt(//
				link.getTargetId(),//
				index, TARGET_ID_COLUMN_INDEX);
		for (Fragment frag : Fragment.values()) {
			XId partId = frag.generateExtendedId(link.getId());
			setData(partId, network.getData(partId));
		}
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

	/** The table model for LinkVisualization objects. */
	private final class TableModel extends AbstractTableModel {
		
		/** The Constant serialVersionUID. */
		private static final long	serialVersionUID	= 1L;

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
