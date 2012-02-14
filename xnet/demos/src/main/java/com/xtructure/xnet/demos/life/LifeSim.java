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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.impl.AbstractStandardXSimulation;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.StandardXClock;
import com.xtructure.xsim.impl.TickXTerminator;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.id.XId;

/**
 * The Class LifeSim.
 * 
 * @author Luis Guimbarda
 */
public class LifeSim extends AbstractStandardXSimulation {
	/** The Constant DEFAULT_BORDER_DEF. */
	private static final String DEFAULT_BORDER_DEF = LifeSim.class.getResource(
			"life-border-def.txt").getFile();
	/** The id of the top grid in this simulation. */
	public static final XId TOP_GRID_ID = XId.newId("TOP");
	/** The id of the bottom grid in this simulation. */
	public static final XId BOTTOM_GRID_ID = XId.newId("BOTTOM");
	/** The id of the TickXTerminator in this simulation. */
	public static final XId TICK_TERMINATOR_ID = XId.newId("tick terminator");

	/** The top grid. */
	private final ComponentGrid _top;
	/** The bottom grid. */
	private final ComponentGrid _bottom;

	/**
	 * Instantiates a new life sim.
	 * 
	 * @param id
	 *            the id
	 */
	public LifeSim(XId id) {
		super(id);

		_top = ComponentGrid.getInstance(TOP_GRID_ID, 10, 5, 0.5F);
		_bottom = ComponentGrid.getInstance(BOTTOM_GRID_ID, 10, 5, 0.5F);

		addComponent(_top);
		addComponent(_bottom);

		final SimpleXBorder border = new SimpleXBorder();
		border.nameComponent("top", _top);
		border.nameComponent("bottom", _bottom);
		try {
			border.loadAssociations(DEFAULT_BORDER_DEF);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTickDelay(250);
	}

	/**
	 * Instantiates a new life sim.
	 * 
	 * @param id
	 *            the id
	 * @param rows
	 *            the rows
	 * @param cols
	 *            the cols
	 */
	public LifeSim(XId id, int rows, int cols) {
		super(id);

		_top = ComponentGrid.getInstance(TOP_GRID_ID, cols, rows, 0.5F);
		_bottom = ComponentGrid.getInstance(BOTTOM_GRID_ID, cols, rows, 0.5F);

		addComponent(_top);
		addComponent(_bottom);

		final SimpleXBorder border = new SimpleXBorder();
		border.nameComponent("top", _top);
		border.nameComponent("bottom", _bottom);
		for (int i = 0; i < cols; i++) {
			// top to bottom
			XId sourceId = XId.newId(String.format("%d %d", i, rows - 1));
			for (int j = i - 1; j <= i + 1; j++) {
				if (0 <= j && j < cols) {
					XId targetId = XId.newId(String.format("%d %d", j, 0));
					border.associate(//
							new XAddressImpl(_top, sourceId),//
							new XAddressImpl(_bottom, targetId));
				}
			}
			// bottom to top
			sourceId = XId.newId(String.format("%d %d", i, 0));
			for (int j = i - 1; j <= i + 1; j++) {
				if (0 <= j && j < cols) {
					XId targetId = XId.newId(String
							.format("%d %d", j, rows - 1));
					border.associate(//
							new XAddressImpl(_bottom, sourceId),//
							new XAddressImpl(_top, targetId));
				}
			}
		}
		setTickDelay(250);
	}

	/**
	 * Sets the sim tick bound.
	 * 
	 * @param tickBound
	 *            the new sim tick bound
	 */
	public void setSimTickBound(long tickBound) {
		// remove previous tickBound
		List<XComponent<StandardXClock.StandardTimePhase>> ts = new ArrayList<XComponent<StandardXClock.StandardTimePhase>>();
		for (XComponent<StandardXClock.StandardTimePhase> comp : getComponents()) {
			if (TICK_TERMINATOR_ID.equals(comp.getId())) {
				ts.add(comp);
			}
		}
		for (XComponent<StandardXClock.StandardTimePhase> comp : ts) {
			removeComponent(comp);
		}
		// add new tickBound
		addComponent(TickXTerminator.getInstance(TICK_TERMINATOR_ID, this,
				tickBound));
	}

	/**
	 * Gets the top.
	 * 
	 * @return the top
	 */
	public ComponentGrid getTop() {
		return _top;
	}

	/**
	 * Gets the bottom.
	 * 
	 * @return the bottom
	 */
	public ComponentGrid getBottom() {
		return _bottom;
	}

}
