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

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThan;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PageablePanel extends JPanel {
	private static final long		serialVersionUID	= 1L;
	private final List<Component>	components			= new ArrayList<Component>();
	private final List<JPanel>		pages				= new ArrayList<JPanel>();
	private int						pageIndex			= -1;
	private final LayoutManager		pageLayoutManager;

	public PageablePanel(Component... components) {
		this(new GridLayout(0, 1), components);
	}

	public PageablePanel(LayoutManager pageLayoutManager, Component... components) {
		this(pageLayoutManager, Arrays.asList(components));
	}

	public PageablePanel(LayoutManager pageLayoutManager, Collection<? extends Component> components) {
		setLayout(new GridLayout());
		this.pageLayoutManager = pageLayoutManager;
		this.components.addAll(components);
		paginate(1);
	}

	public void paginate(int count) {
		validateArg("count", count, isGreaterThan(0));
		removeAll();
		pages.clear();
		Iterator<Component> iter = components.iterator();
		double maxWidth = 0.0;
		double maxHeight = 0.0;
		JPanel page = new JPanel();
		page.setLayout(pageLayoutManager);
		while (iter.hasNext()) {
			Component comp = iter.next();
			page.add(comp);
			if (page.getComponents().length == count || !iter.hasNext()) {
				page.setSize(page.getPreferredSize());
				page.addNotify();
				page.validate();
				maxWidth = Math.max(maxWidth, page.getPreferredSize().getWidth());
				maxHeight = Math.max(maxHeight, page.getPreferredSize().getHeight());
				pages.add(page);
				page = new JPanel();
				page.setLayout(pageLayoutManager);
			}
		}
		setPreferredSize(new Dimension((int) maxWidth, (int) maxHeight));
		validate();
		repaint();
		pageIndex = -1;
		validateState("page set", setPageIndex(0), isTrue());
	}

	public int getPageCount() {
		return pages.size();
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public boolean setPageIndex(int pageIndex) {
		if (this.pageIndex != pageIndex && 0 <= pageIndex && pageIndex < pages.size()) {
			removeAll();
			add(pages.get(pageIndex));
			this.pageIndex = pageIndex;
			validate();
			repaint();
			return true;
		}
		return false;
	}

	public void saveAsPNG(File file) {
		saveAsImage(file, "png");
	}

	public void saveAsGIF(File file) {
		saveAsImage(file, "gif");
	}

	private void saveAsImage(File file, String formatName) {
		try {
			String ext = file.getName().substring(file.getName().lastIndexOf('.'));
			String name = file.getAbsolutePath();
			name = name.substring(0, name.length() - ext.length());
			if (pages.size() == 1) {
				writeImage(formatName, file, 0);
			} else {
				for (int i = 0; i < pages.size(); i++) {
					writeImage(formatName, new File(String.format("%s-%d%s", name, i, ext)), i);
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private void writeImage(String formatName, File pageFile, int index) throws IOException {
		Component page = pages.get(index);
		page.addNotify();
		BufferedImage img = new BufferedImage(page.getWidth(), page.getHeight(), BufferedImage.TYPE_INT_RGB);
		if (index != pageIndex) {
			page.removeNotify();
		}
		page.print(img.getGraphics());
		ImageIO.write(img, formatName, pageFile);
	}
}
