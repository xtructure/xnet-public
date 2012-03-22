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

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.xtructure.xevolution.gui.XEvolutionGui;
import com.xtructure.xutil.opt.XOption;

/**
 * {@link MenuBar} is the menu bar class for {@link XEvolutionGui}
 * 
 * @author Luis Guimbarda
 * 
 */
public class MenuBar extends JMenuBar {
	private static final long	serialVersionUID	= 1L;

	/** the file menu in this {@link MenuBar} */
	private final JMenu			fileMenu;
	private final JMenuItem loadMenuItem;
	/** the view menu in this {@link MenuBar} */
	private final JMenu			viewMenu;
	/** the list of graphs whose visibility is toggled in the view menu */
	private final List<Graph>	graphList;

	/**
	 * Creates a new {@link MenuBar}
	 * 
	 * @param gui
	 *            the {@link XEvolutionGui} for which the new {@link MenuBar} is
	 *            created
	 * @param frame
	 *            the frame of the gui
	 * @param title
	 *            the title of the gui
	 */
	public MenuBar(final XEvolutionGui gui, final JFrame frame, final String title) {
		fileMenu = new JMenu("File");
		loadMenuItem = new JMenuItem(new AbstractAction("Load...") {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				CollectArgsDialog cad = new CollectArgsDialog(frame, null, title, XEvolutionGui.OPTIONS);
				if (cad.succeeded()) {
					XOption<?> outputOption = XEvolutionGui.OUTPUT_OPTION;
					frame.setTitle(title + " - " + outputOption.getValue());
					gui.watchDir();
				}
			}
		});
		fileMenu.add(loadMenuItem);
		add(fileMenu);

		viewMenu = new JMenu("View");
		add(viewMenu);
		graphList = new LinkedList<Graph>();
	}

	/**
	 * Adds a {@link JCheckBox} the this {@link MenuBar}'s view menu to toggle
	 * the visibility of the given {@link Graph}.
	 * 
	 * @param graph
	 *            the graph for which the check box is added
	 * @param graphPanel
	 *            the panel containing the graph
	 * @param startVisible
	 *            indicates the initial visibility of the given graph
	 */
	public void addGraphCheckbox(final Graph graph, final JPanel graphPanel, boolean startVisible) {
		graphList.add(graph);
		final JCheckBoxMenuItem showGraphMenuItem = new JCheckBoxMenuItem(new AbstractAction(String.format("Show %s graph", graph.getName())) {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				graph.setVisible(((JCheckBoxMenuItem) e.getSource()).isSelected());
				graphPanel.removeAll();
				for (Graph graph : graphList) {
					if (graph.isVisible()) {
						graphPanel.add(graph);
					}
				}
				graphPanel.validate();
				graphPanel.repaint();
			}
		});
		showGraphMenuItem.setSelected(startVisible);
		graph.setVisible(startVisible);
		if (startVisible) {
			graphPanel.add(graph);
		}
		viewMenu.add(showGraphMenuItem);
	}
	
	public void setLoadable(boolean loadable){
		loadMenuItem.setEnabled(loadable);
	}
}
