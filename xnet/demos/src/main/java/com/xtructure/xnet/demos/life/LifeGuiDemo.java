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

import java.awt.Dimension;

import com.xtructure.xsim.gui.XVisualization;
import com.xtructure.xsim.gui.impl.AbstractXSimulationGui;
import com.xtructure.xsim.impl.StandardXClock;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;

/**
 * The Class LifeGuiDemo.
 * 
 * @author Luis Guimbarda
 */
public class LifeGuiDemo extends
		AbstractXSimulationGui<StandardXClock.StandardTimePhase> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if (args.length > 1) {
			int r = Integer.parseInt(args[0]);
			int c = Integer.parseInt(args[1]);
			getInstance(XId.newId("Game of Life Simulation Gui"), r, c);
		} else {
			getInstance(XId.newId("Game of Life Simulation Gui"));
		}
	}

	/**
	 * Gets the single instance of LifeGuiDemo.
	 * 
	 * @param id
	 *            the id
	 * @param r
	 *            the r
	 * @param c
	 *            the c
	 * @return single instance of LifeGuiDemo
	 */
	public static LifeGuiDemo getInstance(XId id, int r, int c) {
		LifeSim sim = new LifeSim(id, r, c);

		XVisualization<StandardTimePhase> topViz = new ComponentGridVisualization(
				sim.getTop());
		XVisualization<StandardTimePhase> bottomViz = new ComponentGridVisualization(
				sim.getBottom());

		sim.addComponent(topViz);
		sim.addComponent(bottomViz);

		return new LifeGuiDemo(sim);
	}

	/**
	 * Gets the single instance of LifeGuiDemo.
	 * 
	 * @param id
	 *            the id
	 * @return single instance of LifeGuiDemo
	 */
	public static LifeGuiDemo getInstance(XId id) {
		LifeSim sim = new LifeSim(id);

		XVisualization<StandardTimePhase> topViz = new ComponentGridVisualization(
				sim.getTop());
		XVisualization<StandardTimePhase> bottomViz = new ComponentGridVisualization(
				sim.getBottom());

		sim.addComponent(topViz);
		sim.addComponent(bottomViz);

		return new LifeGuiDemo(sim);
	}

	/**
	 * Instantiates a new life gui demo.
	 * 
	 * @param sim
	 *            the sim
	 */
	private LifeGuiDemo(final LifeSim sim) {
		super(sim);

		setTitle("Game of Life Simulation");
		setPreferredSize(new Dimension(600, 400));
		setSize(getPreferredSize());

		setVisible(true);
	}

}
