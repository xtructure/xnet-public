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

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import com.xtructure.xsim.gui.impl.AbstractSimpleStandardXVisualization;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * The Class WorldVisualization.
 *
 * @author Luis Guimbarda
 */
public class WorldVisualization extends AbstractSimpleStandardXVisualization {
	
	/** the default id for {@link WorldVisualization} objects. */
	private static final XId WORLD_VIZ_ID;
	
	/** the radius of the food sprite. */
	private static final int FOOD_RADIUS;
	
	/** the radius of the critter sprite. */
	private static final int CRIT_RADIUS;
	
	/** space taken by the title bar. */
	private static final int TITLE_HEIGHT;

	static {
		WORLD_VIZ_ID = XId.newId("1D Demo - World Visualization");
		FOOD_RADIUS = 10;
		CRIT_RADIUS = 10;
		TITLE_HEIGHT = 15;
	}

	/**
	 * Gets the single instance of WorldVisualization.
	 *
	 * @param world the world component maintaining the food and critter entities
	 * @param d side length of visualization window
	 * @return a new WorldViz object
	 */
	public static WorldVisualization getInstance(OneDWorld world, int d) {
		validateArg("world", world, isNotNull());

		XId foodId = world.getFoodLocationId();
		XId critId = world.getCritterLocationId();

		WorldVisualization worldViz = new WorldVisualization(foodId, critId, d);

		SimpleXBorder border = new SimpleXBorder();
		border.addComponent(world);
		border.addComponent(worldViz);
		border.associate(//
				new XAddressImpl(world, world.getFoodLocationId()),//
				null,//
				new XAddressImpl(worldViz, foodId));
		border.associate(//
				new XAddressImpl(world, world.getCritterLocationId()),//
				null,//
				new XAddressImpl(worldViz, critId));

		return worldViz;
	}

	/** frame containing the LineWorld visualization. */
	private final JInternalFrame frame;
	
	/** panel in which LineWorld is drawn. */
	private final JPanel panel;

	/** radius of LineWorld. */
	private final double RADIUS;
	
	/** center point of LineWorld. */
	private final Point CENTER;

	/** id of the food. */
	private final XId foodId;
	
	/** id of the critter. */
	private final XId critId;

	/** position of the food. */
	private Double foodPos;
	
	/** position of the critter. */
	private Double critPos;

	/**
	 * Creates a WorldViz object.
	 *
	 * @param foodId the source Id of the food entity
	 * @param critId the source Id of the critter entity
	 * @param d side length of visualization window
	 */
	public WorldVisualization(XId foodId, XId critId, int d) {
		super(WORLD_VIZ_ID, new SetBuilder<XId>().add(foodId, critId)
				.newImmutableInstance());
		this.foodId = foodId;
		this.critId = critId;

		panel = new Viz(this);
		frame = new IFrame(panel, d);

		this.RADIUS = 2 * d / 5;
		this.CENTER = new Point(d / 2, d / 2 - TITLE_HEIGHT);

		foodPos = 0.0;
		critPos = 0.0;
	}

	/**
	 * Gets the crit pos.
	 *
	 * @return the position in line world where the critter is located.
	 */
	public Double getCritPos() {
		return critPos;
	}

	/**
	 * Gets the food pos.
	 *
	 * @return the position in line world where the food is located.
	 */
	public Double getFoodPos() {
		return foodPos;
	}

	/**
	 * Sets the crit pos.
	 *
	 * @param critPos the critPos to set
	 */
	public void setCritPos(Double critPos) {
		this.critPos = critPos;
	}

	/**
	 * Sets the food pos.
	 *
	 * @param foodPos the foodPos to set
	 */
	public void setFoodPos(Double foodPos) {
		this.foodPos = foodPos;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * com.xtructure.xsim.impl.AbstractStandardXComponent#addForeignData(com
	// * .xtructure.xutil.id.XId, com.xtructure.xsim.XAddress, java.lang.Object)
	// */
	// @Override
	// protected void addForeignData(XId targetId, XAddress sourceAddress,
	// Object data) {
	// if (data == null || sourceAddress.getPartId() == null) {
	// return;
	// }
	// if (foodId.equals(sourceAddress.getPartId())) {
	// foodPos = (Double) data;
	// return;
	// }
	// if (critId.equals(sourceAddress.getPartId())) {
	// critPos = (Double) data;
	// return;
	// }
	// }

	@Override
	protected void setData(XId partId, Object data) {
		if (data == null || partId == null) {
			return;
		}
		if (foodId.equals(partId)) {
			foodPos = (Double) data;
			return;
		}
		if (critId.equals(partId)) {
			critPos = (Double) data;
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xsim.impl.AbstractStandardXComponent#update()
	 */
	@Override
	protected void update() {
		super.update();
		panel.repaint();
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

	/** Internal frame subclass for WorldVisualization objects. */
	static class IFrame extends JInternalFrame {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new i frame.
		 *
		 * @param panel the panel
		 * @param d the d
		 */
		public IFrame(JPanel panel, int d) {
			add(panel);
			setPreferredSize(new Dimension(d, d));
			setSize(getPreferredSize());
			setResizable(false);
			setVisible(true);
		}
	}

	/** Viz handles the actual line world drawing. */
	static class Viz extends JPanel {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** local reference to the enclosing WorldViz object. */
		private final WorldVisualization worldViz;

		/**
		 * Creates a new Viz obect.
		 *
		 * @param worldViz the world viz
		 */
		private Viz(final WorldVisualization worldViz) {
			this.worldViz = worldViz;
			setFocusable(true);
			setBackground(Color.BLACK);
			setDoubleBuffered(true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paint(java.awt.Graphics)
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);

			drawLineWorld(g);
			drawFood(g);
			drawCritter(g);

			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}

		/**
		 * Draws line world using the given graphics object.
		 *
		 * @param g the g
		 */
		private void drawLineWorld(Graphics g) {
			g.setColor(Color.WHITE);
			g.drawOval(//
					worldViz.CENTER.x - (int) worldViz.RADIUS,//
					worldViz.CENTER.y - (int) worldViz.RADIUS,//
					(int) worldViz.RADIUS * 2,//
					(int) worldViz.RADIUS * 2);
		}

		/**
		 * Draws the critter entity in line world with the given graphics
		 * object.
		 *
		 * @param g the g
		 */
		private void drawCritter(Graphics g) {
			g.setColor(Color.YELLOW);
			double theta = worldViz.getCritPos() * 2 * Math.PI;
			int x = (int) Math.round(worldViz.RADIUS * Math.sin(theta))
					+ worldViz.CENTER.x;
			int y = (int) Math.round(worldViz.RADIUS * Math.cos(theta))
					+ worldViz.CENTER.y;
			g.fillOval(//
					x - CRIT_RADIUS,//
					y - CRIT_RADIUS,//
					CRIT_RADIUS * 2,//
					CRIT_RADIUS * 2);
		}

		/**
		 * Draws the food entity in line world with the given graphics object.
		 *
		 * @param g the g
		 */
		private void drawFood(Graphics g) {
			g.setColor(Color.GREEN);
			double theta = worldViz.getFoodPos() * 2 * Math.PI;
			int x = (int) Math.round(worldViz.RADIUS * Math.sin(theta))
					+ worldViz.CENTER.x;
			int y = (int) Math.round(worldViz.RADIUS * Math.cos(theta))
					+ worldViz.CENTER.y;
			g.fillOval(//
					x - FOOD_RADIUS,//
					y - FOOD_RADIUS,//
					FOOD_RADIUS * 2,//
					FOOD_RADIUS * 2);
		}
	}

}
