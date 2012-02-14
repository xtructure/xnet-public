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

import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.xtructure.xutil.MiscUtils;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.coll.Transform;

/**
 * Plots data from an arbitrary number of sources against the time the data
 * points were measured.
 * <p>
 * The scale of time is determined by the number of data points or the width of
 * the window, whichever is smaller. If there are fewer data points, then they
 * will all be displayed, otherwise only the latest data points will be
 * displayed equal to the width of the graph.
 * <p>
 * The scale of the data values is plotted against it's nearest order of
 * magnitude. For example, if the maximum value for data is 18, then the graph
 * max will be 20; if it's 118, then the graph max will be 200.
 * 
 * @author Luis Guimbarda
 */
public class Graph extends JPanel implements MouseInputListener, MouseWheelListener {
	private static final long			serialVersionUID	= 1L;

	/** the background {@link Color} for {@link Graph}s */
	private static final Color			BACKGROUND			= Color.LIGHT_GRAY;
	/** the title {@link Color} for {@link Graph}s */
	private static final Color			TITLE_COLOR			= Color.BLACK;
	/** the axis label {@link Color} for {@link Graph}s */
	private static final Color			LABEL_COLOR			= Color.GRAY;
	/** the axis {@link Color} for {@link Graph}s */
	private static final Color			AXIS_COLOR			= Color.GRAY;
	/** the list of {@link Color}s for data lines */
	private static final List<Color>	LINE_COLORS;
	/** the {@link Color} of the highlight line */
	private static final Color			HIGHLIGHT_COLOR		= Color.YELLOW;
	/** the height of the top border of {@link Graph}s */
	private static final int			BORDER_T_HEIGHT		= 20;
	/** the height of the bottom border of {@link Graph}s */
	private static final int			BORDER_B_HEIGHT		= 30;
	/** the width of the left border of {@link Graph}s */
	private static final int			BORDER_L_WIDTH		= 20;
	/** the width of the right border of {@link Graph}s */
	private static final int			BORDER_R_WIDTH		= 80;
	static {
		LINE_COLORS = new ListBuilder<Color>()//
				.add(Color.RED.darker())//
				.add(Color.GREEN.darker())//
				.add(Color.BLUE.darker())//
				.add(Color.ORANGE.darker())//
				.add(Color.CYAN.darker())//
				.add(Color.MAGENTA.darker())//
				.add(Color.PINK.darker())//
				.add(Color.YELLOW.darker())//
				.newImmutableInstance();
	}

	/**
	 * Runs a test for graph, using normal random data.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new TestFrame(new Graph("TEST", 100, 2));
	}

	/** test class for {@link Graph} */
	static class TestFrame extends JFrame implements Runnable {
		private static final long	serialVersionUID	= 1L;
		private static final long	DELAY				= 1000;

		private final Graph			graph;
		private Thread				thread;

		TestFrame(Graph graph) {
			this.graph = graph;
			add(this.graph);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setMinimumSize(new Dimension(//
					BORDER_L_WIDTH + BORDER_R_WIDTH + 600,//
					BORDER_T_HEIGHT + BORDER_B_HEIGHT + 400));
			setVisible(true);
		}

		@Override
		public void addNotify() {
			super.addNotify();
			thread = new Thread(this);
			thread.start();
		}

		@Override
		public void run() {
			long d = 0, s = 0;
			long b = System.currentTimeMillis();

			while (true) {
				double[] datas = new double[graph.buffers.size()];
				for (int i = 0; i < datas.length; i++) {
					datas[i] = RandomUtil.nextGaussian(25.0, 10.0);
				}
				graph.addData(datas);
				setTitle(String.format(//
						"Test : (%d, %s)",//
						graph.dataCountStart + graph.buffers.get(0).size(),//
						Arrays.asList(datas)));
				repaint();

				d = System.currentTimeMillis() - b;
				s = Math.max(2, DELAY - d);
				try {
					Thread.sleep(s);
				} catch (InterruptedException e) {}
				b = System.currentTimeMillis();
			}
		}
	}

	/** List of buffers for observed data of this {@link Graph} */
	private ArrayList<LinkedList<Double>>	buffers;
	/** */
	private final int						bufferCount;
	/** title of this {@link Graph} */
	private final String					title;
	/** max number of data points to keep in any {@link #buffers} */
	private final Integer					capacity;

	/** lock for adding or drawing data */
	private final ReentrantLock				dataLock		= new ReentrantLock();
	/** lists of visible sub-buffers */
	private ArrayList<List<Double>>			dataPoints;
	/** point in graph panel that corresponds to data origin */
	private int								originX, originY;
	/** point in graph panel that corresponds to endpoint of the x axis */
	private int								xAxisEndX, xAxisEndY;
	/** point in graph panel that corresponds to endpoint of the y axis */
	private int								yAxisEndX, yAxisEndY;
	/** values of the data at the ends of the x axis */
	private long							xAxisLabelStart, xAxisLabelEnd;
	/** values of the data at the ends of the y axis */
	private double							yAxisLabelStart, yAxisLabelEnd;
	/** distance between data points */
	private double							dataPointWidth;
	/** indicates whether value is fixed */
	// private boolean dataMinValueFixed = false, dataMaxValueFixed = false;
	/** lowest visible data value */
	private double							dataMinValue;
	/** highest visible data value */
	private double							dataMaxValue;
	/** scale for the data in this graph */
	private double							dataScale;
	/** index into buffer containing first visible data point */
	private int								dataOffset;
	/** count as of the first data in buffer */
	private int								dataCountStart;
	/** time (x value) for which data points are highlighted */
	private int								highlightIndex	= -1;
	/** x position in the graph for which data points are highlighted */
	private int								highlightX		= -1;

	public int getHighlightIndex() {
		return this.highlightIndex;
	}

	public Double getHighlightData(int bufferIndex) {
		if (highlightIndex == -1) {
			return null;
		}
		return dataPoints.get(bufferIndex).get(highlightIndex);
	}

	/**
	 * Creates a new {@link Graph}. Each buffer represents a different data
	 * source.
	 * 
	 * @param title
	 *            the title for the new {@link Graph}
	 * @param capacity
	 *            the max number of data points to keep for each buffer
	 * @param bufferCount
	 *            the number of buffers to keep
	 */
	public Graph(String title, Integer capacity, int bufferCount) {
		setName(title);
		this.title = title;
		this.capacity = capacity;
		this.bufferCount = bufferCount;
		reset();

		addMouseMotionListener(this);

		setBackground(BACKGROUND);
	}

	public void reset() {
		highlightIndex = -1;
		buffers = new ArrayList<LinkedList<Double>>(bufferCount);
		for (int i = 0; i < bufferCount; i++) {
			buffers.add(new LinkedList<Double>());
		}
		dataPoints = new ArrayList<List<Double>>();
	}

	/**
	 * Adds the given values to this {@link Graph} as the latest data points for
	 * the corresponding buffers.
	 * 
	 * @param ds
	 *            the data points to add
	 */
	public void addData(double... ds) {
		validateArg("ds", ds, isNotNull());
		validateArg("ds.length", ds.length, isEqualTo(buffers.size()));
		dataLock.lock();
		try {
			for (int i = 0; i < ds.length; i++) {
				LinkedList<Double> buffer = buffers.get(i);
				buffer.addLast(ds[i]);
			}
			while (capacity != null && buffers.get(0).size() > capacity) {
				for (LinkedList<Double> buffer : buffers) {
					buffer.removeFirst();
				}
				dataCountStart++;
			}
		} finally {
			dataLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		originX = BORDER_L_WIDTH;
		originY = Math.max(BORDER_T_HEIGHT, getHeight() - BORDER_B_HEIGHT);

		xAxisEndX = Math.max(BORDER_L_WIDTH, getWidth() - BORDER_R_WIDTH);
		xAxisEndY = originY;

		yAxisEndX = originX;
		yAxisEndY = BORDER_T_HEIGHT;

		double graphWidth = xAxisEndX - originX;
		double graphHeight = originY - yAxisEndY;

		dataLock.lock();
		try {
			dataPoints.clear();
			if (buffers.get(0).size() - 1 < graphWidth) {
				xAxisLabelStart = 0;
				dataPointWidth = buffers.get(0).size() < 2 ? 2 : graphWidth / (buffers.get(0).size() - 1);
				for (List<Double> buffer : buffers) {
					dataPoints.add(Collections.unmodifiableList(buffer));
				}
			} else {
				xAxisLabelStart = dataOffset;
				dataPointWidth = 1;
				dataOffset = (int) (buffers.get(0).size() - graphWidth);
				for (List<Double> buffer : buffers) {
					dataPoints.add(Collections.unmodifiableList(buffer.subList(dataOffset, buffer.size())));
				}
			}
			xAxisLabelEnd = xAxisLabelStart + dataPoints.get(0).size() - 1;

			dataMinValue = min(dataPoints);
			dataMaxValue = max(dataPoints);
			double mag = Math.max(//
					calculateOrderOfMagnitude(dataMinValue),//
					calculateOrderOfMagnitude(dataMaxValue));
			// dataMinValue = Math.floor(dataMinValue / mag) * mag;
			// dataMaxValue = Math.ceil(dataMaxValue / mag) * mag;
			if (dataMinValue == dataMaxValue) {
				dataMaxValue += mag;
			}
			yAxisLabelStart = dataMinValue;
			yAxisLabelEnd = dataMaxValue;

			// if (!dataMinValueFixed) {
			// dataMinValue = min(dataPoints);
			// if (dataMinValue != 0.0) {
			// double mag = calculateOrderOfMagnitude(dataMinValue);
			// dataMinValue = Math.floor(dataMinValue / mag) * mag;
			// }
			// yAxisLabelStart = dataMinValue;
			// }
			// if (!dataMaxValueFixed) {
			// dataMaxValue = max(dataPoints);
			// if (dataMaxValue != 0.0) {
			// double mag = calculateOrderOfMagnitude(dataMaxValue);
			// dataMaxValue = Math.ceil(dataMaxValue / mag) * mag;
			// }
			// if (dataMinValue == dataMaxValue) {
			// dataMaxValue += calculateOrderOfMagnitude(dataMaxValue);
			// }
			// yAxisLabelEnd = dataMaxValue;
			// }
		} finally {
			dataLock.unlock();
		}
		dataScale = graphHeight / (dataMaxValue - dataMinValue);

		drawTitleAndLabels(g);
		for (int i = 0; i < dataPoints.size(); i++) {
			List<Double> datas = dataPoints.get(i);
			plotData(g, datas, i);
		}
	}

	/**
	 * Calculates the order of magnitude for the given value.
	 * 
	 * @param value
	 *            the value to calculate
	 * @return the calculated order of magnitude
	 */
	private static double calculateOrderOfMagnitude(double value) {
		double mag = Math.pow(10, Math.floor(Math.log10(Math.abs(value))));
		return mag;
	}

	// /**
	// * Sets the dataMinValue to the given value.
	// *
	// * @param value
	// * the value to set
	// */
	// public void fixDataMinValue(double value) {
	// dataMinValue = value;
	// dataMinValueFixed = true;
	// }
	//
	// /**
	// * Sets the dataMaxValue to the given value.
	// *
	// * @param value
	// * the value to set
	// */
	// public void fixDataMaxValue(double value) {
	// dataMaxValue = value;
	// dataMaxValueFixed = true;
	// }
	//
	// /**
	// * Unsets the dataMinValue
	// */
	// public void unfixDataMinValue() {
	// dataMinValueFixed = false;
	// }
	//
	// /**
	// * Unsets the dataMaxValue
	// */
	// public void unfixDataMaxValue() {
	// dataMaxValueFixed = false;
	// }

	/** the {@link DecimalFormat} for displaying data values in this graph */
	private static final DecimalFormat	DOUBLE_FORMAT	= new DecimalFormat("0.####");

	/**
	 * Draws the graph title and axis labels.
	 * 
	 * @param g
	 *            the {@link Graphics} object with which to draw
	 */
	private void drawTitleAndLabels(Graphics g) {
		final int fontHeight = g.getFontMetrics().getHeight();
		g.setColor(TITLE_COLOR);
		g.drawString(title, BORDER_L_WIDTH, 15);

		// draw x-axis labels
		g.setColor(LABEL_COLOR);
		int xLabelY = getHeight() - BORDER_B_HEIGHT + 18;
		String xLabelStart = Long.toString(xAxisLabelStart);
		String xLabelEnd = Long.toString(xAxisLabelEnd);
		int xLabelStartOffset = g.getFontMetrics().stringWidth(xLabelStart) / 2;
		int xLabelEndOffset = g.getFontMetrics().stringWidth(xLabelEnd) / 2;
		g.drawString(xLabelStart, BORDER_L_WIDTH - xLabelStartOffset, xLabelY);
		g.drawString(xLabelEnd, getWidth() - BORDER_R_WIDTH - xLabelEndOffset, xLabelY);

		// draw y-axis labels
		int yLabelX = getWidth() - BORDER_R_WIDTH + 8;
		String yLabelStart = DOUBLE_FORMAT.format(yAxisLabelStart);
		String yLabelEnd = DOUBLE_FORMAT.format(yAxisLabelEnd);
		int yLabelOffset = fontHeight / 2;
		int yLabelEndY = BORDER_T_HEIGHT + yLabelOffset;
		int yLabelStartY = getHeight() - BORDER_B_HEIGHT + yLabelOffset;
		g.drawString(yLabelEnd, yLabelX, yLabelEndY);
		g.drawString(yLabelStart, yLabelX, yLabelStartY);

		// TODO : tune labels

		// TODO: draw legend

		drawGraphBorder(g);
	}

	/**
	 * Draws the axes of this {@link Graph}
	 * 
	 * @param g
	 *            the {@link Graphics} object with which to draw
	 */
	private void drawGraphBorder(Graphics g) {
		g.setColor(AXIS_COLOR);

		// x axis
		g.drawLine(originX, originY, xAxisEndX, xAxisEndY);
		// y axis
		g.drawLine(originX, originY, yAxisEndX, yAxisEndY);
		// top border
		g.drawLine(originX, yAxisEndY, xAxisEndX, yAxisEndY);
		// right border
		g.drawLine(xAxisEndX, originY, xAxisEndX, yAxisEndY);
	}

	/**
	 * Draws the given data points using the {@link Color} with the given index.
	 * 
	 * @param g
	 *            the {@link Graphics} object with which to draw
	 * @param dataPoints
	 *            the buffer whose data points are to be drawn
	 * @param colorIndex
	 *            the index of the color to draw
	 */
	private void plotData(Graphics g, List<Double> dataPoints, int colorIndex) {
		if (dataPoints == null || dataPoints.isEmpty()) {
			return;
		}
		dataLock.lock();
		try {
			int[] dataXs = new int[dataPoints.size()];
			int[] dataYs = new int[dataPoints.size()];
			double x = BORDER_L_WIDTH;
			for (int i = 0; i < dataXs.length; i++) {
				dataXs[i] = (int) x;
				x += dataPointWidth;
			}
			int index = 0;
			for (Double d : dataPoints) {
				dataYs[index++] = (int) (originY - (d - dataMinValue) * dataScale);
			}

			Color color = LINE_COLORS.get(colorIndex % LINE_COLORS.size());
			g.setColor(HIGHLIGHT_COLOR);

			final int fontHeight = g.getFontMetrics().getHeight();
			Integer yLabelX = getWidth() - BORDER_R_WIDTH + 8;
			Integer yLabelY = fontHeight / 2;
			String label = null;
			highlightIndex = highlightX < 0 ? -1 : (int) Math.round(highlightX / dataPointWidth);
			if (0 <= highlightIndex && highlightIndex < dataPoints.size()) {
				// draw highlight lines
				g.drawLine(dataXs[highlightIndex], getHeight() - BORDER_B_HEIGHT,//
						dataXs[highlightIndex], BORDER_T_HEIGHT);
				String highlightString = Long.toString(highlightIndex);
				int highlightY = getHeight() - BORDER_B_HEIGHT + 18;
				int highlightX = dataXs[highlightIndex] - g.getFontMetrics().stringWidth(highlightString) / 2;
				g.drawString(Integer.toString(highlightIndex), highlightX, highlightY);
				g.setColor(color);
				g.drawLine(getWidth() - BORDER_R_WIDTH, dataYs[highlightIndex],//
						BORDER_T_HEIGHT, dataYs[highlightIndex]);
				// draw value labels
				yLabelY += dataYs[highlightIndex];
				label = DOUBLE_FORMAT.format(dataPoints.get(highlightIndex));
			} else {
				// no highlight lines to draw, just draw labels
				yLabelY += dataYs[dataYs.length - 1];
				label = DOUBLE_FORMAT.format(dataPoints.get(dataYs.length - 1));
			}

			g.setColor(color);
			g.drawString(label, yLabelX, yLabelY);
			g.drawPolyline(dataXs, dataYs, dataPoints.size());
		} finally {
			dataLock.unlock();
		}
	}

	/**
	 * Returns the maximum of all values in the given 2-dimensional list
	 * 
	 * @param dss
	 *            the 2-dimensional list to check
	 * @return the maximum value
	 */
	private static double max(List<List<Double>> dss) {
		List<Double> maxes = new ListBuilder<Double>()//
				.addAll(new Transform<Double, List<Double>>() {
					@Override
					public Double transform(List<Double> value) {
						return MiscUtils.max(value);
					}
				}, dss)//
				.newImmutableInstance();
		Double max = MiscUtils.max(maxes);
		return max == null ? 0.0 : max;
	}

	/**
	 * Returns the minimum of all values in the given 2-dimensional list
	 * 
	 * @param dss
	 *            the 2-dimensional list to check
	 * @return the minimum value
	 */
	private static double min(List<List<Double>> dss) {
		List<Double> mins = new ListBuilder<Double>()//
				.addAll(new Transform<Double, List<Double>>() {
					@Override
					public Double transform(List<Double> value) {
						return MiscUtils.min(value);
					}
				}, dss)//
				.newImmutableInstance();
		Double min = MiscUtils.min(mins);
		return min == null ? 0.0 : min;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() < BORDER_L_WIDTH || e.getX() > getWidth() - BORDER_R_WIDTH || //
				e.getY() < BORDER_T_HEIGHT || e.getY() > getHeight() - BORDER_B_HEIGHT) {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			highlightX = -1;
			repaint();
		} else {
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			highlightX = e.getX() - BORDER_L_WIDTH;
			repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent
	 * )
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
	// nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	// nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	// nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	// nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	// nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	// nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejava.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.
	 * MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	// nothing
	}
}
