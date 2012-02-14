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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import com.xtructure.xutil.opt.IntegerXOption;
import com.xtructure.xutil.opt.XOption;

public class ScreenShotFrame extends JFrame {
	private static final long	serialVersionUID	= 1L;
	private final PageablePanel	panel;

	public ScreenShotFrame(PageablePanel panel) {
		this.panel = panel;
		setContentPane(new JScrollPane(panel));
		final JMenu fileMenu = new JMenu("File");
		JMenuItem saveAsMenuItem = new JMenuItem();
		saveAsMenuItem.setAction(new AbstractAction("Save as image...") {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return pathname.isDirectory() || pathname.getName().endsWith(".png");
					}

					@Override
					public String getDescription() {
						return "*.png";
					}
				});
				switch (fc.showSaveDialog(ScreenShotFrame.this)) {
					case JFileChooser.APPROVE_OPTION: {
						final String EXT = "png";
						final String DOT_EXT = "." + EXT;
						File file = fc.getSelectedFile();
						if (!file.getName().endsWith(DOT_EXT)) {
							file = new File(file.getAbsolutePath() + DOT_EXT);
						}
						ScreenShotFrame.this.panel.saveAsPNG(file);
						break;
					}
					default: {
						// nothing
					}
				}
			}
		});
		fileMenu.add(saveAsMenuItem);
		JMenu pageMenu = new JMenu("Page");
		JMenuItem nextMenuItem = new JMenuItem("Next Page");
		JMenuItem previousMenuItem = new JMenuItem("Previous Page");
		JMenuItem resizePageMenuItem = new JMenuItem("Resize Page...");
		pageMenu.add(nextMenuItem);
		pageMenu.add(previousMenuItem);
		pageMenu.add(resizePageMenuItem);
		nextMenuItem.setAction(new AbstractAction("Next Page") {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ScreenShotFrame.this.panel.setPageIndex(ScreenShotFrame.this.panel.getPageIndex() + 1)) {
					JOptionPane.showMessageDialog(ScreenShotFrame.this, "already on the last page");
				}
			}
		});
		nextMenuItem.setAccelerator(KeyStroke.getKeyStroke('n'));
		previousMenuItem.setAction(new AbstractAction("Previous Page") {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ScreenShotFrame.this.panel.setPageIndex(ScreenShotFrame.this.panel.getPageIndex() - 1)) {
					JOptionPane.showMessageDialog(ScreenShotFrame.this, "already on the first page");
				}
			}
		});
		previousMenuItem.setAccelerator(KeyStroke.getKeyStroke('p'));
		resizePageMenuItem.setAction(new AbstractAction("Resize Page...") {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				IntegerXOption countOption = new IntegerXOption("Component count", "c", "count", "components per page");
				CollectArgsDialog cad = new CollectArgsDialog(ScreenShotFrame.this, null, null, Arrays.<XOption<?>> asList(countOption));
				if (cad.succeeded()) {
					ScreenShotFrame.this.panel.paginate(countOption.processValue());
				}
				// TODO : get user input to resize
			}
		});
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(pageMenu);
		setJMenuBar(menuBar);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ScreenShotFrame.this.setTitle(ScreenShotFrame.this.getSize().toString());
			}
		});
	}
}
