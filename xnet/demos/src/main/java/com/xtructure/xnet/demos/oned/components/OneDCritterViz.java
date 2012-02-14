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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javolution.xml.XMLObjectReader;
import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.PortView;

import com.sun.tools.javac.util.Pair;
import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.network.Network;
import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.Node.Energies;
import com.xtructure.xsim.gui.impl.AbstractSimpleStandardXVisualization;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * The Class OneDCritterViz.
 *
 * @author Luis Guimbarda
 */
public class OneDCritterViz extends AbstractSimpleStandardXVisualization {
	
	/** Id for instantiations of NetworkVisualization. */
	private static final XId NET_VIZ_ID;
	
	/** The Constant ENERGY_MAX. */
	private static final Float ENERGY_MAX;
	
	/** The Constant ENERGY_CENTER. */
	private static final Float ENERGY_CENTER;
	
	/** The Constant STRENGTH_MAX. */
	private static final Float STRENGTH_MAX;
	static {
		NET_VIZ_ID = XId.newId("Network Visualization");
		ENERGY_MAX = 10.0f;
		ENERGY_CENTER = ENERGY_MAX / 2.0f;
		STRENGTH_MAX = 6.0f;
	}
	
	/** The inhibitory endpoint map. */
	private final HashMap<XId, Pair<XId, XId>> inhibitoryEndpointMap;
	
	/** The excitatory endpoint map. */
	private final HashMap<XId, Pair<XId, XId>> excitatoryEndpointMap;
	
	/** The frag map. */
	private final HashMap<XId, Float> fragMap;
	
	/** The panel. */
	private final Panel panel;
	
	/** The frame. */
	private final IFrame frame;

	/**
	 * Instantiates a new one d critter viz.
	 *
	 * @param network the network
	 * @param excitatoryEndpointMap the excitatory endpoint map
	 * @param inhibitoryEndpointMap the inhibitory endpoint map
	 * @param inputIds the input ids
	 * @param hiddenIds the hidden ids
	 * @param outputIds the output ids
	 */
	public OneDCritterViz(Network network,
			Map<XId, Pair<XId, XId>> excitatoryEndpointMap,
			Map<XId, Pair<XId, XId>> inhibitoryEndpointMap, List<XId> inputIds,
			List<XId> hiddenIds, List<XId> outputIds) {
		this(network, NET_VIZ_ID, excitatoryEndpointMap, inhibitoryEndpointMap,
				inputIds, hiddenIds, outputIds);
	}

	/**
	 * Instantiates a new one d critter viz.
	 *
	 * @param network the network
	 * @param id the id
	 * @param excitatoryEndpointMap the excitatory endpoint map
	 * @param inhibitoryEndpointMap the inhibitory endpoint map
	 * @param inputIds the input ids
	 * @param hiddenIds the hidden ids
	 * @param outputIds the output ids
	 */
	public OneDCritterViz(Network network, XId id,
			Map<XId, Pair<XId, XId>> excitatoryEndpointMap,
			Map<XId, Pair<XId, XId>> inhibitoryEndpointMap, List<XId> inputIds,
			List<XId> hiddenIds, List<XId> outputIds) {
		super(//
				id,//
				new SetBuilder<XId>()//
						.addAll(excitatoryEndpointMap.keySet())//
						.addAll(inhibitoryEndpointMap.keySet())//
						.addAll(inputIds)//
						.addAll(hiddenIds)//
						.addAll(outputIds)//
						.newImmutableInstance());
		this.excitatoryEndpointMap = new HashMap<XId, Pair<XId, XId>>(
				excitatoryEndpointMap);
		this.inhibitoryEndpointMap = new HashMap<XId, Pair<XId, XId>>(
				inhibitoryEndpointMap);
		this.fragMap = new HashMap<XId, Float>();
		SimpleXBorder border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(this);
		for (XId targetId : getTargetIds()) {
			border.associate(new XAddressImpl(network, targetId),
					new XAddressImpl(this, targetId));
		}
		initLinks(network, excitatoryEndpointMap.keySet());
		initLinks(network, inhibitoryEndpointMap.keySet());
		initNodes(network, inputIds);
		initNodes(network, hiddenIds);
		initNodes(network, outputIds);
		Net net = new Net(//
				this,//
				inputIds,//
				hiddenIds,//
				outputIds,//
				new SetBuilder<XId>()//
						.addAll(excitatoryEndpointMap.keySet())//
						.addAll(inhibitoryEndpointMap.keySet())//
						.newImmutableInstance());
		this.panel = new Panel(net);
		this.frame = new IFrame(panel);
	}

	/**
	 * Gets the panel.
	 *
	 * @return the panel
	 */
	public Panel getPanel() {
		return this.panel;
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xsim.gui.XVisualization#getInternalFrame()
	 */
	@Override
	public JInternalFrame getInternalFrame() {
		return frame;
	}

	/**
	 * Inits the links.
	 *
	 * @param network the network
	 * @param linkIds the link ids
	 */
	private void initLinks(Network network, Set<XId> linkIds) {
		for (XId linkId : linkIds) {
			XId linkCapId = Link.Fragment.CAPACITY.generateExtendedId(linkId);
			XId linkStrId = Link.Fragment.STRENGTH.generateExtendedId(linkId);
			fragMap.put(linkCapId, 0f);
			fragMap.put(linkStrId, 0f);
			setCapacity(linkCapId, (Float) network.getData(linkCapId));
			setStrength(linkCapId, (Float) network.getData(linkStrId));
		}
	}

	/**
	 * Inits the nodes.
	 *
	 * @param network the network
	 * @param nodeIds the node ids
	 */
	private void initNodes(Network network, List<XId> nodeIds) {
		for (XId nodeId : nodeIds) {
			XId nodeEnId = Node.Fragment.ENERGIES.generateExtendedId(nodeId);
			fragMap.put(nodeEnId, 0f);
			setEnergy(nodeEnId,
					((Energies) network.getData(nodeEnId)).getFrontEnergy());
		}
	}

	/**
	 * Gets the source id.
	 *
	 * @param linkId the link id
	 * @return the source id
	 */
	private XId getSourceId(XId linkId) {
		if (excitatoryEndpointMap.containsKey(linkId)) {
			return excitatoryEndpointMap.get(linkId).fst;
		}
		if (inhibitoryEndpointMap.containsKey(linkId)) {
			return inhibitoryEndpointMap.get(linkId).fst;
		}
		return null;
	}

	/**
	 * Gets the target id.
	 *
	 * @param linkId the link id
	 * @return the target id
	 */
	private XId getTargetId(XId linkId) {
		if (excitatoryEndpointMap.containsKey(linkId)) {
			return excitatoryEndpointMap.get(linkId).snd;
		}
		if (inhibitoryEndpointMap.containsKey(linkId)) {
			return inhibitoryEndpointMap.get(linkId).snd;
		}
		return null;
	}

	/**
	 * Checks if is inhibitory.
	 *
	 * @param linkId the link id
	 * @return true, if is inhibitory
	 */
	private boolean isInhibitory(XId linkId) {
		return inhibitoryEndpointMap.containsKey(linkId);
	}

	/**
	 * Gets the capacity.
	 *
	 * @param linkId the link id
	 * @return the capacity
	 */
	private Float getCapacity(XId linkId) {
		return fragMap.get(Link.Fragment.CAPACITY.generateExtendedId(linkId));
	}

	/**
	 * Gets the strength.
	 *
	 * @param linkId the link id
	 * @return the strength
	 */
	private Float getStrength(XId linkId) {
		return fragMap.get(Link.Fragment.STRENGTH.generateExtendedId(linkId));
	}

	/**
	 * Gets the energy.
	 *
	 * @param nodeId the node id
	 * @return the energy
	 */
	private Float getEnergy(XId nodeId) {
		return fragMap.get(Node.Fragment.ENERGIES.generateExtendedId(nodeId));
	}

	/**
	 * Sets the capacity.
	 *
	 * @param linkId the link id
	 * @param capacity the capacity
	 */
	private void setCapacity(XId linkId, Float capacity) {
		XId fragId = Link.Fragment.CAPACITY.generateExtendedId(linkId);
		if (fragMap.containsKey(fragId)) {
			fragMap.put(fragId, capacity);
		}
	}

	/**
	 * Sets the strength.
	 *
	 * @param linkId the link id
	 * @param strength the strength
	 */
	private void setStrength(XId linkId, Float strength) {
		XId fragId = Link.Fragment.STRENGTH.generateExtendedId(linkId);
		if (fragMap.containsKey(fragId)) {
			fragMap.put(fragId, strength);
		}
	}

	/**
	 * Sets the energy.
	 *
	 * @param nodeId the node id
	 * @param energy the energy
	 */
	private void setEnergy(XId nodeId, Float energy) {
		XId fragId = Node.Fragment.ENERGIES.generateExtendedId(nodeId);
		if (fragMap.containsKey(fragId)) {
			fragMap.put(fragId, energy);
		}
	}

	/**
	 * Load layout.
	 *
	 * @param layoutFile the layout file
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XMLStreamException the xML stream exception
	 */
	public void loadLayout(File layoutFile) throws IOException,
			XMLStreamException {
		panel.net.loadLayout(layoutFile);
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xsim.impl.AbstractSimpleStandardXComponent#setData(com.xtructure.xutil.id.XId, java.lang.Object)
	 */
	@Override
	protected void setData(XId partId, Object data) {
		XId nodeEnId = Node.Fragment.ENERGIES.generateExtendedId(partId);
		XId linkCapId = Link.Fragment.CAPACITY.generateExtendedId(partId);
		XId linkStrId = Link.Fragment.STRENGTH.generateExtendedId(partId);
		if (fragMap.containsKey(nodeEnId)) {
			fragMap.put(nodeEnId, ((Node) data).getEnergies().getFrontEnergy());
		}
		if (fragMap.containsKey(linkCapId)) {
			fragMap.put(linkCapId, ((Link) data).getCapacity());
		}
		if (fragMap.containsKey(linkStrId)) {
			fragMap.put(linkStrId, ((Link) data).getStrength());
		}
	}

	/* (non-Javadoc)
	 * @see com.xtructure.xsim.gui.impl.AbstractSimpleStandardXVisualization#update()
	 */
	@Override
	protected void update() {
		super.update();
		frame.repaint();
	}

	/**
	 * The Class Net.
	 */
	private static final class Net extends JGraph {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		// private static final double EAST = 0.0;
		/** The Constant SOUTH. */
		private static final double SOUTH = Math.PI / 2.0;
		// private static final double WEST = 2.0 * SOUTH;
		/** The Constant NORTH. */
		private static final double NORTH = 3.0 * SOUTH;
		
		/** The Constant MIN_ENERGY. */
		private static final float MIN_ENERGY = 0.01f;
		
		/** The vertices. */
		final List<DefaultGraphCell> vertices;
		
		/** The input vertices. */
		final List<DefaultGraphCell> inputVertices;
		
		/** The output vertices. */
		final List<DefaultGraphCell> outputVertices;
		
		/** The hidden vertices. */
		final List<DefaultGraphCell> hiddenVertices;
		
		/** The vertex map. */
		final Map<XId, DefaultGraphCell> vertexMap;
		
		/** The edge map. */
		final Map<XId, DefaultEdge> edgeMap;
		
		/** The net viz. */
		final OneDCritterViz netViz;
		
		/** The node width. */
		double nodeWidth = 40;
		
		/** The node height. */
		double nodeHeight = 20;
		
		/** The radius x1. */
		double radiusX1 = 150;
		
		/** The radius y1. */
		double radiusY1 = 150;
		
		/** The radius x2. */
		double radiusX2 = 150;
		
		/** The radius y2. */
		double radiusY2 = 150;
		
		/** The center x. */
		double centerX = 150;
		
		/** The center y. */
		double centerY = 150;

		/**
		 * Instantiates a new net.
		 *
		 * @param netViz the net viz
		 * @param inputIds the input ids
		 * @param hiddenIds the hidden ids
		 * @param outputIds the output ids
		 * @param linkIds the link ids
		 */
		Net(OneDCritterViz netViz, List<XId> inputIds, List<XId> hiddenIds,
				List<XId> outputIds, Set<XId> linkIds) {
			this(new DefaultGraphModel(), netViz, inputIds, hiddenIds,
					outputIds, linkIds);
		}

		/**
		 * Instantiates a new net.
		 *
		 * @param model the model
		 * @param netViz the net viz
		 * @param inputIds the input ids
		 * @param hiddenIds the hidden ids
		 * @param outputIds the output ids
		 * @param linkIds the link ids
		 */
		Net(GraphModel model, OneDCritterViz netViz, List<XId> inputIds,
				List<XId> hiddenIds, List<XId> outputIds, Set<XId> linkIds) {
			super(model, new GraphLayoutCache(model,
					new DefaultCellViewFactory()));
			setBackground(Color.DARK_GRAY);
			this.vertices = new ArrayList<DefaultGraphCell>();
			this.vertexMap = new HashMap<XId, DefaultGraphCell>();
			this.edgeMap = new HashMap<XId, DefaultEdge>();
			this.netViz = netViz;
			this.inputVertices = buildVertices(inputIds, Color.CYAN);
			this.outputVertices = buildVertices(outputIds, Color.PINK);
			this.hiddenVertices = buildVertices(hiddenIds, Color.BLACK);
			List<DefaultGraphCell> cells = new ArrayList<DefaultGraphCell>();
			cells.addAll(inputVertices);
			cells.addAll(outputVertices);
			cells.addAll(hiddenVertices);
			for (DefaultGraphCell cell : cells) {
				vertices.add(cell);
				vertexMap.put((XId) cell.getUserObject(), cell);
			}
			List<DefaultEdge> edges = buildEdges(linkIds);
			for (DefaultEdge edge : edges) {
				edgeMap.put((XId) edge.getUserObject(), edge);
			}
			cells.addAll(edges);
			getGraphLayoutCache().insert(//
					cells.toArray(new DefaultGraphCell[0]));
			layoutVertices(Layout.CIRCLE);
		}

		/**
		 * Builds the vertices.
		 *
		 * @param ids the ids
		 * @param borderColor the border color
		 * @return the list
		 */
		List<DefaultGraphCell> buildVertices(List<XId> ids, Color borderColor) {
			List<DefaultGraphCell> cells = new ArrayList<DefaultGraphCell>();
			for (XId id : ids) {
				DefaultGraphCell cell = new DefaultGraphCell(id);
				GraphConstants.setLabelEnabled(//
						cell.getAttributes(),//
						false);
				GraphConstants.setOpaque(//
						cell.getAttributes(),//
						true);
				GraphConstants.setBorderColor(//
						cell.getAttributes(),//
						borderColor);
				GraphConstants.setBackground(//
						cell.getAttributes(),//
						cellColor((XId) cell.getUserObject()));
				cell.addPort();
				cells.add(cell);
			}
			return cells;
		}

		/**
		 * Builds the edges.
		 *
		 * @param ids the ids
		 * @return the list
		 */
		List<DefaultEdge> buildEdges(Set<XId> ids) {
			List<DefaultEdge> cells = new ArrayList<DefaultEdge>();
			for (XId id : ids) {
				DefaultEdge edge = new DefaultEdge(id);
				edge.setSource(vertexMap.get(netViz.getSourceId(id))
						.getChildAt(0));
				edge.setTarget(vertexMap.get(netViz.getTargetId(id))
						.getChildAt(0));
				GraphConstants.setLabelEnabled(//
						edge.getAttributes(),//
						false);
				GraphConstants.setLineEnd(//
						edge.getAttributes(),//
						GraphConstants.ARROW_CLASSIC);
				GraphConstants.setEndFill(//
						edge.getAttributes(),//
						true);
				GraphConstants.setLineStyle(//
						edge.getAttributes(),//
						GraphConstants.STYLE_BEZIER);
				GraphConstants.setLineColor(//
						edge.getAttributes(),//
						edgeColor((XId) edge.getUserObject()));
				GraphConstants.setLineWidth(//
						edge.getAttributes(),//
						edgeWidth((XId) edge.getUserObject()));
				cells.add(edge);
			}
			return cells;
		}

		/**
		 * Edge width.
		 *
		 * @param linkId the link id
		 * @return the float
		 */
		float edgeWidth(XId linkId) {
			float capacity = netViz.getCapacity(linkId);
			return Math.max(1.0f, capacity);
		}

		/**
		 * Edge color.
		 *
		 * @param linkId the link id
		 * @return the color
		 */
		Color edgeColor(XId linkId) {
			float strength = netViz.getStrength(linkId);
			boolean inhibitory = netViz.isInhibitory(linkId);
			float comp = Math.min(Math.max(//
					0.0f,//
					STRENGTH_MAX - strength),//
					STRENGTH_MAX) / STRENGTH_MAX;
			comp = Math.min(comp, 0.75f);
			return inhibitory ? new Color(comp, comp, 1.0f)//
					: new Color(comp, 1.0f, comp);
		}

		/**
		 * Cell color.
		 *
		 * @param cellId the cell id
		 * @return the color
		 */
		Color cellColor(XId cellId) {
			float energy = netViz.getEnergy(cellId);
			if (energy > ENERGY_CENTER) {
				float comp = 1.0f - (energy - ENERGY_CENTER)
						/ (ENERGY_MAX - ENERGY_CENTER);
				return new Color(1.0f, comp, comp);
			} else {
				float comp = (energy / ENERGY_CENTER) / 2.0f + 0.5f;
				return new Color(comp, comp, comp);
			}
		}

		/**
		 * Gets the node width.
		 *
		 * @return the node width
		 */
		double getNodeWidth() {
			return nodeWidth;
		}

		/**
		 * Gets the node height.
		 *
		 * @return the node height
		 */
		double getNodeHeight() {
			return nodeHeight;
		}

		/**
		 * Sets the node size.
		 *
		 * @param width the width
		 * @param height the height
		 */
		void setNodeSize(double width, double height) {
			setNodeWidth(width);
			setNodeHeight(height);
		}

		/**
		 * Sets the node width.
		 *
		 * @param nodeWidth the new node width
		 */
		void setNodeWidth(double nodeWidth) {
			this.nodeWidth = nodeWidth;
		}

		/**
		 * Sets the node height.
		 *
		 * @param nodeHeight the new node height
		 */
		void setNodeHeight(double nodeHeight) {
			this.nodeHeight = nodeHeight;
		}

		/**
		 * Gets the radius x1.
		 *
		 * @return the radius x1
		 */
		double getRadiusX1() {
			return radiusX1;
		}

		/**
		 * Gets the radius y1.
		 *
		 * @return the radius y1
		 */
		double getRadiusY1() {
			return radiusY1;
		}

		/**
		 * Gets the radius x2.
		 *
		 * @return the radius x2
		 */
		public double getRadiusX2() {
			return radiusX2;
		}

		/**
		 * Gets the radius y2.
		 *
		 * @return the radius y2
		 */
		public double getRadiusY2() {
			return radiusY2;
		}

		/**
		 * Sets the radius1.
		 *
		 * @param radius the new radius1
		 */
		void setRadius1(double radius) {
			setRadius1(radius, radius);
		}

		/**
		 * Sets the radius1.
		 *
		 * @param radiusX the radius x
		 * @param radiusY the radius y
		 */
		void setRadius1(double radiusX, double radiusY) {
			this.radiusX1 = radiusX;
			this.radiusY1 = radiusY;
		}

		/**
		 * Sets the radius2.
		 *
		 * @param radius the new radius2
		 */
		void setRadius2(double radius) {
			setRadius2(radius, radius);
		}

		/**
		 * Sets the radius2.
		 *
		 * @param radiusX the radius x
		 * @param radiusY the radius y
		 */
		void setRadius2(double radiusX, double radiusY) {
			this.radiusX2 = radiusX;
			this.radiusY2 = radiusY;
		}

		/**
		 * Gets the center x.
		 *
		 * @return the center x
		 */
		double getCenterX() {
			return centerX;
		}

		/**
		 * Gets the center y.
		 *
		 * @return the center y
		 */
		double getCenterY() {
			return centerY;
		}

		/**
		 * Sets the center.
		 *
		 * @param x the x
		 * @param y the y
		 */
		void setCenter(double x, double y) {
			centerX = x;
			centerY = y;
		}

		/**
		 * Save layout.
		 *
		 * @param layoutFile the layout file
		 * @throws IOException Signals that an I/O exception has occurred.
		 * @throws XMLStreamException the xML stream exception
		 */
		void saveLayout(File layoutFile) throws IOException, XMLStreamException {
			BoundsList boundsList = new BoundsList();
			for (DefaultGraphCell cell : vertices) {
				boundsList.add(//
						new IDBoundsPair(//
								(XId) cell.getUserObject(),//
								GraphConstants.getBounds(cell.getAttributes())));
			}
			FileWriter out = null;
			XMLObjectWriter writer = null;
			try {
				out = new FileWriter(layoutFile, false);
				writer = XMLObjectWriter.newInstance(out).setIndentation("\t");
				writer.write(boundsList);
			} finally {
				if (writer != null) {
					writer.close();
				}
				if (out != null) {
					out.close();
				}
			}
		}

		/**
		 * Load layout.
		 *
		 * @param layoutFile the layout file
		 * @throws IOException Signals that an I/O exception has occurred.
		 * @throws XMLStreamException the xML stream exception
		 */
		void loadLayout(File layoutFile) throws IOException, XMLStreamException {
			BoundsList boundsList = BoundsList.getInstance(layoutFile);
			if (boundsList == null) {
				return;
			}
			Map<XId, Rectangle2D> boundsMap = new HashMap<XId, Rectangle2D>();
			for (IDBoundsPair pair : boundsList.boundsList) {
				boundsMap.put(pair.id, pair.rectangle);
			}
			for (DefaultGraphCell cell : vertices) {
				if (boundsMap.containsKey(cell.getUserObject())) {
					GraphConstants.setBounds(//
							cell.getAttributes(),//
							boundsMap.get(cell.getUserObject()));
				}
			}
		}

		/**
		 * The Enum Layout.
		 */
		static enum Layout {
			
			/** The CIRCLE. */
			CIRCLE, 
 /** The DUA l_ circle. */
 DUAL_CIRCLE, 
 /** The ELLIPSE. */
 ELLIPSE, 
 /** The DUA l_ ellipse. */
 DUAL_ELLIPSE, 
 /** The FILE. */
 FILE
		}

		/**
		 * Layout vertices.
		 *
		 * @param layout the layout
		 */
		void layoutVertices(Layout layout) {
			switch (layout) {
			case CIRCLE:
			case ELLIPSE: {
				// input/output node arcs are centered at the south/north
				// regions of the circle
				double delta = 2.0 * Math.PI / vertices.size();
				// arcs begin and end with vertex
				double inputArcLength = delta * (inputVertices.size() - 1);
				double outputArcLength = delta * (outputVertices.size() - 1);
				double inputTheta0 = SOUTH - inputArcLength / 2.0;
				double outputTheta0 = NORTH - outputArcLength / 2.0;
				placeCells(radiusX1, radiusY1, centerX, centerY, delta,
						inputTheta0, inputVertices);
				placeCells(radiusX1, radiusY1, centerX, centerY, delta,
						outputTheta0, outputVertices);
				// hidden nodes are split between the east and west regions
				double deltaH = (2.0 * Math.PI - inputArcLength - outputArcLength) / 2.0;
				List<DefaultGraphCell> hiddenWest = hiddenVertices.subList(0,
						hiddenVertices.size() / 2);
				List<DefaultGraphCell> hiddenEast = hiddenVertices.subList(
						hiddenVertices.size() / 2, hiddenVertices.size());
				// arcs begin before and end after hidden vertices
				double deltaW = deltaH / (hiddenWest.size() + 1);
				double deltaE = deltaH / (hiddenEast.size() + 1);
				double hiddenTheta0West = inputTheta0 + inputArcLength + deltaW;
				double hiddenTheta0East = outputTheta0 + outputArcLength
						+ deltaE;
				placeCells(radiusX1, radiusY1, centerX, centerY, deltaW,
						hiddenTheta0West, hiddenWest);
				placeCells(radiusX1, radiusY1, centerX, centerY, deltaE,
						hiddenTheta0East, hiddenEast);
				break;
			}
			case DUAL_ELLIPSE:
			case DUAL_CIRCLE: {
				// conic 1 includes input/output nodes
				// select hidden nodes for conic 1 & 2
				List<DefaultGraphCell> hiddenVertices1 = new ArrayList<DefaultGraphCell>();
				List<DefaultGraphCell> hiddenVertices2 = new ArrayList<DefaultGraphCell>();
				for (DefaultGraphCell hiddenVertex : hiddenVertices) {
					// TODO: decide base on user input
					XId id = (XId) hiddenVertex.getUserObject();
					float energy = netViz.getEnergy(id);
					if (energy > MIN_ENERGY) {
						hiddenVertices1.add(hiddenVertex);
					} else {
						hiddenVertices2.add(hiddenVertex);
					}
				}
				// layout conic 1
				double delta1 = 2.0
						* Math.PI
						/ (inputVertices.size() + outputVertices.size() + hiddenVertices1
								.size());
				double inputArcLength = delta1 * (inputVertices.size() - 1);
				double outputArcLength = delta1 * (outputVertices.size() - 1);
				double inputTheta0 = SOUTH - inputArcLength / 2.0;
				double outputTheta0 = NORTH - outputArcLength / 2.0;
				placeCells(radiusX1, radiusY1, centerX, centerY, delta1,
						inputTheta0, inputVertices);
				placeCells(radiusX1, radiusY1, centerX, centerY, delta1,
						outputTheta0, outputVertices);
				double deltaH = (2.0 * Math.PI - inputArcLength - outputArcLength) / 2.0;
				List<DefaultGraphCell> hiddenWest = hiddenVertices1.subList(0,
						hiddenVertices1.size() / 2);
				List<DefaultGraphCell> hiddenEast = hiddenVertices1.subList(
						hiddenVertices1.size() / 2, hiddenVertices1.size());
				double deltaW = deltaH / (hiddenWest.size() + 1);
				double deltaE = deltaH / (hiddenEast.size() + 1);
				double hiddenTheta0West = inputTheta0 + inputArcLength + deltaW;
				double hiddenTheta0East = outputTheta0 + outputArcLength
						+ deltaE;
				placeCells(radiusX1, radiusY1, centerX, centerY, deltaW,
						hiddenTheta0West, hiddenWest);
				placeCells(radiusX1, radiusY1, centerX, centerY, deltaE,
						hiddenTheta0East, hiddenEast);
				// layout conic 2
				double delta2 = 2.0 * Math.PI / hiddenVertices2.size();
				placeCells(radiusX2, radiusY2, centerX, centerY, delta2, NORTH,
						hiddenVertices2);
				break;
			}
			default:
				break;
			}
		}

		/**
		 * Place cells.
		 *
		 * @param radiusX the radius x
		 * @param radiusY the radius y
		 * @param centerX the center x
		 * @param centerY the center y
		 * @param delta the delta
		 * @param theta the theta
		 * @param cells the cells
		 */
		private void placeCells(//
				double radiusX, double radiusY,//
				double centerX, double centerY,//
				double delta, double theta,//
				List<DefaultGraphCell> cells) {
			for (DefaultGraphCell cell : cells) {
				double x = Math.cos(theta) * radiusX + centerX;
				double y = Math.sin(theta) * radiusY + centerY;
				GraphConstants.setBounds(//
						cell.getAttributes(),//
						new Rectangle2D.Double(//
								x, y,//
								nodeWidth,//
								nodeHeight));
				theta += delta;
			}
		}

		/**
		 * Update graph.
		 */
		void updateGraph() {
			GraphLayoutCache cache = getGraphLayoutCache();
			for (CellView view : cache.getCellViews()) {
				if (view instanceof PortView) {
					continue;
				}
				if (view.getCell() instanceof DefaultEdge) {
					DefaultEdge edge = (DefaultEdge) view.getCell();
					GraphConstants.setLineColor(//
							view.getAttributes(),//
							edgeColor((XId) edge.getUserObject()));
					GraphConstants.setLineWidth(//
							view.getAttributes(),//
							edgeWidth((XId) edge.getUserObject()));
					cache.editCell(edge, view.getAttributes());
				} else if (view.getCell() instanceof DefaultGraphCell) {
					DefaultGraphCell cell = (DefaultGraphCell) view.getCell();
					GraphConstants.setBackground(//
							view.getAttributes(),//
							cellColor((XId) cell.getUserObject()));
					cache.editCell(cell, view.getAttributes());
				}
				view.update(cache);
			}
		}
	}

	/**
	 * The Class Panel.
	 */
	private static final class Panel extends JPanel {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The net. */
		final Net net;

		/**
		 * Instantiates a new panel.
		 *
		 * @param net the net
		 */
		Panel(Net net) {
			this.net = net;
			new MenuBar(this);
			setLayout(new BorderLayout());
			add(net);
			setBackground(Color.WHITE);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paint(java.awt.Graphics)
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			net.updateGraph();
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
	}

	/**
	 * The Class IFrame.
	 */
	private static final class IFrame extends JInternalFrame {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new i frame.
		 *
		 * @param panel the panel
		 */
		IFrame(Panel panel) {
			add(new JScrollPane(panel));
			int s = (int) (panel.net.getRadiusX1() + Math.max(
					panel.net.getWidth(), panel.net.getHeight())) * 2 + 80;
			setPreferredSize(new Dimension(s, s));
			setJMenuBar(new MenuBar(panel));
			setResizable(true);
			setVisible(true);
		}
	}

	/**
	 * The Class MenuBar.
	 */
	private static final class MenuBar extends JMenuBar {
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The file dialog dir. */
		private File fileDialogDir = new File(".");
		
		/** The current layout. */
		private Net.Layout currentLayout = null;

		/**
		 * Instantiates a new menu bar.
		 *
		 * @param panel the panel
		 */
		public MenuBar(final Panel panel) {
			JMenu fileMenu = new JMenu("File");
			fileMenu.add(new JMenuItem(new AbstractAction("Save layout...") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fc = new JFileChooser(fileDialogDir);
					int val = fc.showSaveDialog(panel);
					if (val == JFileChooser.APPROVE_OPTION) {
						try {
							panel.net.saveLayout(fc.getSelectedFile());
							currentLayout = Net.Layout.FILE;
							fileDialogDir = fc.getSelectedFile()
									.getParentFile();
						} catch (Exception exception) {
							JOptionPane.showMessageDialog(//
									null,//
									exception.getMessage(),//
									exception.getClass().getName(),//
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}));
			fileMenu.add(new JMenuItem(new AbstractAction("Load layout...") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fc = new JFileChooser(fileDialogDir);
					int val = fc.showOpenDialog(panel);
					if (val == JFileChooser.APPROVE_OPTION) {
						try {
							panel.net.loadLayout(fc.getSelectedFile());
							currentLayout = Net.Layout.FILE;
							panel.repaint();
							fileDialogDir = fc.getSelectedFile()
									.getParentFile();
						} catch (Exception exception) {
							JOptionPane.showMessageDialog(//
									null,//
									exception.getMessage(),//
									exception.getClass().getName(),//
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}));
			JMenu layoutMenu = new JMenu("Layout");
			layoutMenu.add(new JMenuItem(new AbstractAction(
					"Set Circular Layout...") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField radiusTextField = new JTextField();
					JTextField centerXTextField = new JTextField();
					JTextField centerYTextField = new JTextField();
					radiusTextField.setText(Double.toString(Math.min(
							panel.net.getRadiusX1(), panel.net.getRadiusY1())));
					centerXTextField.setText(Double.toString(panel.net
							.getCenterX()));
					centerYTextField.setText(Double.toString(panel.net
							.getCenterY()));
					final JComponent[] inputs = new JComponent[] {//
					//
							new JLabel("Center X"),//
							centerXTextField,//
							new JLabel("Center Y"),//
							centerYTextField,//
							new JLabel("Radius"),//
							radiusTextField,//
					};
					JOptionPane.showMessageDialog(//
							null,//
							inputs,//
							"Enter circle layout center and radius:",//
							JOptionPane.PLAIN_MESSAGE);
					try {
						double centerX = Double.parseDouble(centerXTextField
								.getText());
						double centerY = Double.parseDouble(centerYTextField
								.getText());
						double radius = Double.parseDouble(radiusTextField
								.getText());
						panel.net.setCenter(centerX, centerY);
						panel.net.setRadius1(radius);
						panel.net.layoutVertices(Net.Layout.CIRCLE);
						currentLayout = Net.Layout.CIRCLE;
						panel.repaint();
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(//
								null,//
								exception.getMessage(),//
								exception.getClass().getName(),//
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}));
			layoutMenu.add(new JMenuItem(new AbstractAction(
					"Set Dual Circular Layout") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField centerXTextField = new JTextField();
					JTextField centerYTextField = new JTextField();
					JTextField radiusX1TextField = new JTextField();
					JTextField radiusX2TextField = new JTextField();
					centerXTextField.setText(Double.toString(panel.net
							.getCenterX()));
					centerYTextField.setText(Double.toString(panel.net
							.getCenterY()));
					radiusX1TextField.setText(Double.toString(panel.net
							.getRadiusX1()));
					radiusX2TextField.setText(Double.toString(panel.net
							.getRadiusX2()));
					final JComponent[] inputs = new JComponent[] {//
					//
							new JLabel("Center X"),//
							centerXTextField,//
							new JLabel("Center Y"),//
							centerYTextField,//
							new JLabel("Radius 1"),//
							radiusX1TextField,//
							new JLabel("Radius 2"),//
							radiusX2TextField,//
					};
					JOptionPane.showMessageDialog(//
							null,//
							inputs,//
							"Enter dual circle layout radii:",//
							JOptionPane.PLAIN_MESSAGE);
					try {
						double centerX = Double.parseDouble(centerXTextField
								.getText());
						double centerY = Double.parseDouble(centerYTextField
								.getText());
						double radiusX1 = Double.parseDouble(radiusX1TextField
								.getText());
						double radiusX2 = Double.parseDouble(radiusX2TextField
								.getText());
						panel.net.setCenter(centerX, centerY);
						panel.net.setRadius1(radiusX1);
						panel.net.setRadius2(radiusX2);
						panel.net.layoutVertices(Net.Layout.DUAL_CIRCLE);
						currentLayout = Net.Layout.DUAL_CIRCLE;
						panel.repaint();
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(//
								null,//
								exception.getMessage(),//
								exception.getClass().getName(),//
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}));
			layoutMenu.add(new JMenuItem(new AbstractAction(
					"Set Elliptical Layout...") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField centerXTextField = new JTextField();
					JTextField centerYTextField = new JTextField();
					JTextField radiusXTextField = new JTextField();
					JTextField radiusYTextField = new JTextField();
					radiusXTextField.setText(Double.toString(panel.net
							.getRadiusX1()));
					radiusYTextField.setText(Double.toString(panel.net
							.getRadiusY1()));
					centerXTextField.setText(Double.toString(panel.net
							.getCenterX()));
					centerYTextField.setText(Double.toString(panel.net
							.getCenterY()));
					final JComponent[] inputs = new JComponent[] {//
					//
							new JLabel("Center X"),//
							centerXTextField,//
							new JLabel("Center Y"),//
							centerYTextField,//
							new JLabel("X Radius"),//
							radiusXTextField,//
							new JLabel("Y Radius"),//
							radiusYTextField,//
					};
					JOptionPane.showMessageDialog(//
							null,//
							inputs,//
							"Enter elliptical layout radii:",//
							JOptionPane.PLAIN_MESSAGE);
					try {
						double centerX = Double.parseDouble(centerXTextField
								.getText());
						double centerY = Double.parseDouble(centerYTextField
								.getText());
						double radiusX = Double.parseDouble(radiusXTextField
								.getText());
						double radiusY = Double.parseDouble(radiusYTextField
								.getText());
						panel.net.setCenter(centerX, centerY);
						panel.net.setRadius1(radiusX, radiusY);
						panel.net.layoutVertices(Net.Layout.ELLIPSE);
						currentLayout = Net.Layout.ELLIPSE;
						panel.repaint();
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(//
								null,//
								exception.getMessage(),//
								exception.getClass().getName(),//
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}));
			layoutMenu.add(new JMenuItem(new AbstractAction(
					"Set Dual Elliptical Layout...") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField centerXTextField = new JTextField();
					JTextField centerYTextField = new JTextField();
					JTextField radiusX1TextField = new JTextField();
					JTextField radiusY1TextField = new JTextField();
					JTextField radiusX2TextField = new JTextField();
					JTextField radiusY2TextField = new JTextField();
					centerXTextField.setText(Double.toString(panel.net
							.getCenterX()));
					centerYTextField.setText(Double.toString(panel.net
							.getCenterY()));
					radiusX1TextField.setText(Double.toString(panel.net
							.getRadiusX1()));
					radiusY1TextField.setText(Double.toString(panel.net
							.getRadiusY1()));
					radiusX2TextField.setText(Double.toString(panel.net
							.getRadiusX2()));
					radiusY2TextField.setText(Double.toString(panel.net
							.getRadiusY2()));
					final JComponent[] inputs = new JComponent[] {//
					//
							new JLabel("Center X"),//
							centerXTextField,//
							new JLabel("Center Y"),//
							centerYTextField,//
							new JLabel("RadiusX 1"),//
							radiusX1TextField,//
							new JLabel("RadiusY 1"),//
							radiusY1TextField,//
							new JLabel("RadiusX 2"),//
							radiusX2TextField,//
							new JLabel("RadiusY 2"),//
							radiusY2TextField,//
					};
					JOptionPane.showMessageDialog(//
							null,//
							inputs,//
							"Enter dual elliptical layout radii:",//
							JOptionPane.PLAIN_MESSAGE);
					try {
						double centerX = Double.parseDouble(centerXTextField
								.getText());
						double centerY = Double.parseDouble(centerYTextField
								.getText());
						double radiusX1 = Double.parseDouble(radiusX1TextField
								.getText());
						double radiusY1 = Double.parseDouble(radiusY1TextField
								.getText());
						double radiusX2 = Double.parseDouble(radiusX2TextField
								.getText());
						double radiusY2 = Double.parseDouble(radiusY2TextField
								.getText());
						panel.net.setCenter(centerX, centerY);
						panel.net.setRadius1(radiusX1, radiusY1);
						panel.net.setRadius2(radiusX2, radiusY2);
						panel.net.layoutVertices(Net.Layout.DUAL_ELLIPSE);
						currentLayout = Net.Layout.DUAL_ELLIPSE;
						panel.repaint();
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(//
								null,//
								exception.getMessage(),//
								exception.getClass().getName(),//
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}));
			layoutMenu.add(new JMenuItem(new AbstractAction("Set Node Size") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField widthTextField = new JTextField();
					JTextField heightTextField = new JTextField();
					widthTextField.setText(Double.toString(panel.net
							.getNodeWidth()));
					heightTextField.setText(Double.toString(panel.net
							.getNodeHeight()));
					final JComponent[] inputs = new JComponent[] {//
					//
							new JLabel("Width"),//
							widthTextField,//
							new JLabel("Height"),//
							heightTextField,//
					};
					JOptionPane.showMessageDialog(//
							null,//
							inputs,//
							"Enter node dimensions:",//
							JOptionPane.PLAIN_MESSAGE);
					try {
						double width = Double.parseDouble(widthTextField
								.getText());
						double height = Double.parseDouble(heightTextField
								.getText());
						panel.net.setNodeSize(width, height);
						panel.net.layoutVertices(currentLayout);
						panel.repaint();
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(//
								null,//
								exception.getMessage(),//
								exception.getClass().getName(),//
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}));
			add(fileMenu);
			add(layoutMenu);
		}
	}

	/**
	 * The Class BoundsList.
	 */
	public static final class BoundsList {
		
		/** The bounds list. */
		List<IDBoundsPair> boundsList = new LinkedList<IDBoundsPair>();

		/**
		 * Gets the single instance of BoundsList.
		 *
		 * @param layoutFile the layout file
		 * @return single instance of BoundsList
		 * @throws IOException Signals that an I/O exception has occurred.
		 * @throws XMLStreamException the xML stream exception
		 */
		public static BoundsList getInstance(File layoutFile)
				throws IOException, XMLStreamException {
			if (layoutFile == null || !layoutFile.exists()) {
				return null;
			}
			FileReader in = null;
			XMLObjectReader reader = null;
			try {
				in = new FileReader(layoutFile);
				reader = XMLObjectReader.newInstance(in);
				return reader.read();
			} finally {
				if (reader != null) {
					reader.close();
				}
				if (in != null) {
					in.close();
				}
			}
		}

		/**
		 * Instantiates a new bounds list.
		 */
		public BoundsList() {
		}

		/**
		 * Adds the.
		 *
		 * @param pair the pair
		 */
		void add(IDBoundsPair pair) {
			boundsList.add(pair);
		}
		// static final XMLFormat<BoundsList> XML_FORMAT = new XmlFormat();
		// static final class XmlFormat extends XmlFormat<BoundsList> {
		// private static final Element<IDBoundsPair> CELL_ELEMENT = new
		// Element<IDBoundsPair>("cell", IDBoundsPair.class);
		// private static final Set<Attribute<?>> ATTRIBUTES;
		// private static final Set<Element<?>> ELEMENTS;
		// static {
		// ATTRIBUTES = new HashSet<Attribute<?>>();
		// ELEMENTS = new
		// SetBuilder<Element<?>>().add(CELL_ELEMENT).getImmutableCollection();
		// }
		//
		// protected XmlFormat() {
		// super(BoundsList.class, ATTRIBUTES, ELEMENTS);
		// }
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * com.xtructure.xutil.xml.AbstractXmlFormat#newInstance(java.lang
		// * .Class, java.util.Map, java.util.Map)
		// */
		// @Override
		// protected BoundsList newInstance(Class<BoundsList> obj, Map<String,
		// List<Object>> attributes, Map<String, List<Object>> elements) throws
		// XMLStreamException {
		// BoundsList boundsList = new BoundsList();
		// boundsList.boundsList.addAll(getElements(CELL_ELEMENT, elements));
		// return boundsList;
		// }
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * com.xtructure.xutil.xml.AbstractXmlFormat#writeAttributes(java
		// * .lang.Object, javolution.xml.XMLFormat.OutputElement)
		// */
		// @Override
		// protected void writeAttributes(BoundsList obj,
		// javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException
		// {
		// // nothing
		// }
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * com.xtructure.xutil.xml.AbstractXmlFormat#writeElements(java.
		// * lang.Object, javolution.xml.XMLFormat.OutputElement)
		// */
		// @Override
		// protected void writeElements(BoundsList obj,
		// javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException
		// {
		// for (IDBoundsPair pair : obj.boundsList) {
		// CELL_ELEMENT.setValue(xml, pair);
		// }
		// }
		// }
	}

	/**
	 * The Class IDBoundsPair.
	 */
	public static final class IDBoundsPair {
		
		/** The id. */
		XId id;
		
		/** The rectangle. */
		Rectangle2D rectangle;

		/**
		 * Instantiates a new iD bounds pair.
		 */
		public IDBoundsPair() {
		}

		/**
		 * Instantiates a new iD bounds pair.
		 *
		 * @param id the id
		 * @param rectangle the rectangle
		 */
		IDBoundsPair(XId id, Rectangle2D rectangle) {
			this.id = id;
			this.rectangle = rectangle;
		}
		// static final XMLFormat<IDBoundsPair> XML_FORMAT = new XmlFormat();
		// static final class XmlFormat extends XmlFormat<IDBoundsPair> {
		// private static final Attribute<XId> ID_ATTRIBUTE = new
		// Attribute<XId>("id", XId.class);
		// private static final Attribute<Double> X_ATTRIBUTE = new
		// Attribute<Double>("x", Double.class);
		// private static final Attribute<Double> Y_ATTRIBUTE = new
		// Attribute<Double>("y", Double.class);
		// private static final Attribute<Double> W_ATTRIBUTE = new
		// Attribute<Double>("w", Double.class);
		// private static final Attribute<Double> H_ATTRIBUTE = new
		// Attribute<Double>("h", Double.class);
		// private static final Set<Attribute<?>> ATTRIBUTES;
		// private static final Set<Element<?>> ELEMENTS;
		// static {
		// ATTRIBUTES = new SetBuilder<Attribute<?>>()//
		// .add(ID_ATTRIBUTE)//
		// .add(X_ATTRIBUTE)//
		// .add(Y_ATTRIBUTE)//
		// .add(W_ATTRIBUTE)//
		// .add(H_ATTRIBUTE)//
		// .getImmutableCollection();
		// ELEMENTS = new HashSet<Element<?>>();
		// }
		//
		// protected XmlFormat() {
		// super(IDBoundsPair.class, ATTRIBUTES, ELEMENTS);
		// }
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * com.xtructure.xutil.xml.AbstractXmlFormat#newInstance(java.lang
		// * .Class, java.util.Map, java.util.Map)
		// */
		// @Override
		// protected IDBoundsPair newInstance(Class<IDBoundsPair> obj,
		// Map<String, List<Object>> attributes, Map<String, List<Object>>
		// elements) throws XMLStreamException {
		// IDBoundsPair idBoundsPair = new IDBoundsPair();
		// idBoundsPair.id = getAttribute(ID_ATTRIBUTE, attributes);
		// idBoundsPair.rectangle = new Rectangle2D.Double(//
		// getAttribute(X_ATTRIBUTE, attributes),//
		// getAttribute(Y_ATTRIBUTE, attributes),//
		// getAttribute(W_ATTRIBUTE, attributes),//
		// getAttribute(H_ATTRIBUTE, attributes));
		// return idBoundsPair;
		// }
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * com.xtructure.xutil.xml.AbstractXmlFormat#writeAttributes(java
		// * .lang.Object, javolution.xml.XMLFormat.OutputElement)
		// */
		// @Override
		// protected void writeAttributes(IDBoundsPair obj,
		// javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException
		// {
		// ID_ATTRIBUTE.setValue(xml, obj.id);
		// X_ATTRIBUTE.setValue(xml, obj.rectangle.getX());
		// Y_ATTRIBUTE.setValue(xml, obj.rectangle.getY());
		// W_ATTRIBUTE.setValue(xml, obj.rectangle.getWidth());
		// H_ATTRIBUTE.setValue(xml, obj.rectangle.getHeight());
		// }
		//
		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// * com.xtructure.xutil.xml.AbstractXmlFormat#writeElements(java.
		// * lang.Object, javolution.xml.XMLFormat.OutputElement)
		// */
		// @Override
		// protected void writeElements(IDBoundsPair obj,
		// javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException
		// {
		// // nothing
		// }
		// }
	}
}
