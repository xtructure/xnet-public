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
package com.xtructure.xnet.demos.art;

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

import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.Node.Energies;
import com.xtructure.art.model.node.Node.Fragment;
import com.xtructure.xsim.gui.impl.AbstractSimpleStandardXVisualization;
import com.xtructure.xsim.impl.AbstractStandardXComponent;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.id.XId;

/**
 * A table visualization of nodes in a network. Displays the id, front energy,
 * and back energy of specified nodes.
 * 
 * @author Luis Guimbarda
 * 
 */
public final class NodeVisualization extends
		AbstractSimpleStandardXVisualization {

	/** An array of column name Strings. */
	private static final String[] COLUMN_NAMES;

	/** The index of the id column. */
	private static final int ID_COLUMN_INDEX;

	/** The index of the front energy column. */
	private static final int FRONT_ENERGY_COLUMN_INDEX;

	/** The index of the back energy column. */
	private static final int BACK_ENERGY_COLUMN_INDEX;

	static {
		COLUMN_NAMES = new String[] {//
		//
				"Id",//
				"Front Energy",//
				"Back Energy",//
		};

		ID_COLUMN_INDEX = 0;
		FRONT_ENERGY_COLUMN_INDEX = 1;
		BACK_ENERGY_COLUMN_INDEX = 2;
	}

	/** The frame. */
	private final JInternalFrame frame;

	/** The table. */
	private final JTable table;

	/** The data. */
	private final Object[][] data;

	/** The index map. */
	private final Map<XId, Integer> indexMap;

	/**
	 * Gets the single instance of NodeVisualization.
	 * 
	 * @param id
	 *            the id of the returned NodeVisualization
	 * @param network
	 *            the network on whose nodes the returned NodeVisualization will
	 *            be based
	 * @param targetNodes
	 *            the set of nodes to include in the returned NodeVisualization
	 * @return a new NodeVisualization
	 */
	public static NodeVisualization getInstance(XId id,
			AbstractStandardXComponent network, List<Node> targetNodes) {
		// instantiate visualization
		Map<XId, Integer> indexMap = new HashMap<XId, Integer>();
		Set<XId> targetIds = new HashSet<XId>();
		int index = 0;
		for (Node node : targetNodes) {
			indexMap.put(node.getId(), index++);
			targetIds.add(Fragment.ENERGIES.generateExtendedId(node.getId()));
		}
		NodeVisualization nodeViz = new NodeVisualization(id,
				targetNodes.size(), targetIds, indexMap);

		// define border
		SimpleXBorder border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(nodeViz);

		// associate the network node with the visualization
		for (XId targetId : targetIds) {
			border.associate(//
					new XAddressImpl(network, targetId),//
					new XAddressImpl(nodeViz, targetId));
		}

		// initialize the visualization
		for (Node targetNode : targetNodes) {
			nodeViz.initData(targetNode, network);
		}

		return nodeViz;
	}

	/**
	 * Creates a new NodeVisualization object.
	 * 
	 * @param id
	 *            the id of the new NodeVisualization
	 * @param nodeCount
	 *            the node count
	 * @param targetIds
	 *            the ids of the elements whose values will be displayed in this
	 *            NodeVisualization
	 * @param indexMap
	 *            the index map
	 */
	private NodeVisualization(XId id, int nodeCount, Set<XId> targetIds,
			Map<XId, Integer> indexMap) {
		super(id, targetIds);
		frame = new JInternalFrame(id.getBase(), true, true, true, true);
		table = new JTable(new TableModel());
		data = new Object[nodeCount][COLUMN_NAMES.length];
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
		case ENERGIES: {
			Energies energies = (Energies) data;
			table.setValueAt(//
					energies.getFrontEnergy(),//
					index, FRONT_ENERGY_COLUMN_INDEX);
			table.setValueAt(//
					energies.getBackEnergy(),//
					index, BACK_ENERGY_COLUMN_INDEX);
			break;
		}
		default: {
			break;
		}
		}
	}

	/**
	 * The id column never changes, so it only gets written during
	 * initialization. Otherwise, set data as usual.
	 * 
	 * @param node
	 *            the node
	 * @param network
	 *            the network
	 */
	private void initData(Node node, AbstractStandardXComponent network) {
		int index = indexMap.get(node.getId());
		table.setValueAt(//
				node.getId(),//
				index, ID_COLUMN_INDEX);

		for (Fragment frag : Fragment.values()) {
			if (Fragment.ENERGY.equals(frag)) {
				continue;
			}
			XId partId = frag.generateExtendedId(node.getId());
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

	/**
	 * The table model for NodeVisualization objects.
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
