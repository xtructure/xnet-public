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
package com.xtructure.xnet.demos.life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import com.xtructure.xsim.gui.impl.AbstractSimpleStandardXVisualization;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.id.XId;

/**
 * The Class ComponentGridVisualization.
 * 
 * @author Luis Guimbarda
 */
public class ComponentGridVisualization extends
		AbstractSimpleStandardXVisualization {

	/** The Constant SQUARE_SIZE. */
	private static final Dimension SQUARE_SIZE = new Dimension(20, 20);

	/** The Constant BORDER. */
	private static final Color BORDER = Color.BLACK;

	/** The Constant POPULATED. */
	private static final Color POPULATED = Color.GREEN;

	/** The Constant UNPOPULATED. */
	private static final Color UNPOPULATED = Color.WHITE;

	/** The iframe. */
	private final JInternalFrame iframe;

	/** The panels. */
	private final JPanel[][] panels;

	/**
	 * Instantiates a new component grid visualization.
	 * 
	 * @param grid
	 *            the grid
	 */
	public ComponentGridVisualization(final ComponentGrid grid) {
		super(grid.getId().createChild(0), grid.getSourceIds());

		this.panels = new JPanel[grid.getGridWidth()][grid.getGridHeight()];
		for (int x = 0; x < grid.getGridWidth(); x++) {
			for (int y = 0; y < grid.getGridHeight(); y++) {
				this.panels[x][y] = null;
			}
		}

		SimpleXBorder border = new SimpleXBorder();
		border.addComponent(this);
		border.addComponent(grid);

		for (XId id : grid.getSourceIds()) {
			if (ComponentGrid.POPULATION_ID.equals(id)) {
				continue;
			}

			border.associate(//
					new XAddressImpl(grid, id),//
					null,//
					new XAddressImpl(this, id));

			int[] coords = ComponentGrid.parseId(id);
			panels[coords[0]][coords[1]] = new JPanel();
			panels[coords[0]][coords[1]].setBorder(BorderFactory
					.createLineBorder(BORDER));
			panels[coords[0]][coords[1]].setPreferredSize(SQUARE_SIZE);
			setData(coords[0], coords[1], (Integer) grid.getData(id));
		}

		this.iframe = new JInternalFrame(grid.getId().toString());
		this.iframe.setLayout(new GridLayout(grid.getGridHeight(), grid
				.getGridWidth()));
		for (int y = 0; y < grid.getGridHeight(); y++) {
			for (int x = 0; x < grid.getGridWidth(); x++) {
				if (panels[x][y] != null) {
					this.iframe.add(panels[x][y]);
				} else {
					JPanel panel = new JPanel();
					panel.setBackground(Color.RED);
					this.iframe.add(panel);
				}
			}
		}

		this.iframe.pack();
		this.iframe.setVisible(true);

	}

	/**
	 * Sets the data.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param populated
	 *            the populated
	 */
	private void setData(int x, int y, Integer populated) {
		if (populated != null) {
			panels[x][y]
					.setBackground(populated == 1 ? POPULATED : UNPOPULATED);
		}
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
		if (partId != null && !ComponentGrid.POPULATION_ID.equals(partId)
				&& data != null) {
			int[] coords = ComponentGrid.parseId(partId);
			setData(coords[0], coords[1], (Integer) data);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.gui.XVisualization#getInternalFrame()
	 */
	@Override
	public JInternalFrame getInternalFrame() {
		return iframe;
	}

}
