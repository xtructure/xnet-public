/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xsim.
 *
 * xsim is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xsim is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xsim.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.xtructure.xsim.gui.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.gui.XSimulationGui;
import com.xtructure.xsim.gui.XVisualization;
import com.xtructure.xutil.id.XId;

/**
 * A base implementation of a GUI for an {@link XSimulation}.
 * 
 * @param <F>
 *            the type of phase of the time of this simulation
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractXSimulationGui<F extends XTime.Phase<F>> extends JFrame implements XSimulationGui<F>, WindowListener {
	/** The text of the question posed in the exit dialog. */
	private static final String				EXIT_QUESTION_TEXT			= "Are you sure you want to exit?";
	
	/** The title of the exit dialog. */
	private static final String				EXIT_DIALOG_TITLE			= "Confirm exit";
	
	/** The options in the exit dialog. */
	private static final Object[]			EXIT_DIALOG_OPTIONS			= { "Yes, exit", "No, cancel" };
	
	/** The serial version UID of this class (FIXME). */
	private static final long				serialVersionUID			= 1L;
	
	/** The offset factor in the x dimension for new visualization frames. */
	private static final int				VIZ_FRAME_X_OFFSET_FACTOR	= 25;
	
	/** The offset factor in the y dimension for new visualization frames. */
	private static final int				VIZ_FRAME_Y_OFFSET_FACTOR	= 25;
	
	/** The simulation visualized by this GUI. */
	private final XSimulation<F>			_sim;
	
	/** The visualizations in the simulation visualized by this GUI. */
	private final Map<XId, JInternalFrame>	_visualizationMap;
	
	/** The desktop pane of this frame. */
	private final JDesktopPane				_desktopPane				= new JDesktopPane();
	
	/**
	 * Creates a new simulation GUI.
	 * 
	 * @param sim
	 *            the simulation visualized by this GUI
	 */
	protected AbstractXSimulationGui(final XSimulation<F> sim) {
		super(String.format("XSim v0.9.6 - %s", sim.getId()));
		
		_sim = sim;
		
		final ImageIcon imageIcon = GuiUtils.loadImageIconResource("x-16");
		if (imageIcon != null) {
			setIconImage(imageIcon.getImage());
		}
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		
		final JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		_desktopPane.setPreferredSize(new Dimension(600, 600));
		contentPane.add(_desktopPane, BorderLayout.CENTER);
		contentPane.add(new SimToolBar(this), BorderLayout.PAGE_START);
		
		_visualizationMap = new HashMap<XId, JInternalFrame>();
		for (XComponent<F> comp : sim.getComponents()) {
			if (comp instanceof XVisualization<?>) {
				addVisualization((XVisualization<F>) comp);
			}
		}
		
		setJMenuBar(new MainMenuBar(this));
	}
	
	/** {@inheritDoc} */
	@Override
	public final XSimulation<F> getSimulation() {
		return _sim;
	}
	
	/** {@inheritDoc} */
	@Override
	public final void windowClosing(final WindowEvent event) {
		displayExitDialog();
	}
	
	/** {@inheritDoc} */
	@Override
	public final void windowActivated(final WindowEvent event) {
	// do nothing
	}
	
	/** {@inheritDoc} */
	@Override
	public final void windowClosed(final WindowEvent event) {
	// do nothing
	}
	
	/** {@inheritDoc} */
	@Override
	public final void windowDeactivated(final WindowEvent event) {
	// do nothing
	}
	
	/** {@inheritDoc} */
	@Override
	public final void windowDeiconified(final WindowEvent event) {
	// do nothing
	}
	
	/** {@inheritDoc} */
	@Override
	public final void windowIconified(final WindowEvent event) {
	// do nothing
	}
	
	/** {@inheritDoc} */
	@Override
	public final void windowOpened(final WindowEvent event) {
	// do nothing
	}
	
	/**
	 * Adds the given visualization to this GUI to manage.
	 * 
	 * @param visualization
	 */
	protected void addVisualization(XVisualization<F> visualization) {
		if (!_sim.getComponents().contains(visualization)) {
			_sim.addComponent(visualization);
		}
		final JInternalFrame internalFrame = visualization.getInternalFrame();
		_visualizationMap.put(visualization.getId(), internalFrame);
		_desktopPane.add(internalFrame);
		internalFrame.show();
		internalFrame.setLocation(//
				VIZ_FRAME_X_OFFSET_FACTOR * _visualizationMap.size(),//
				VIZ_FRAME_Y_OFFSET_FACTOR * _visualizationMap.size());
	}
	
	/**
	 * Returns the ids of the visualizations managed by this GUI.
	 * 
	 * @return the ids of the visualizations managed by this GUI
	 */
	final Set<XId> getVisualizationIds() {
		return Collections.unmodifiableSet(_visualizationMap.keySet());
	}
	
	/**
	 * Shows the visualization with the given id.
	 * 
	 * @param id
	 *            the id of the visualization to show
	 */
	final void showVisualization(final XId id) {
		final JInternalFrame frame = _visualizationMap.get(id);
		if (frame != null) {
			frame.setVisible(true);
		}
	}
	
	/**
	 * Hides the visualization with the given id.
	 * 
	 * @param id
	 *            the id of the visualization to hide
	 */
	final void hideVisualization(final XId id) {
		final JInternalFrame frame = _visualizationMap.get(id);
		if (frame != null) {
			frame.setVisible(false);
		}
	}
	
	/** Display and process an exit dialog. */
	final void displayExitDialog() {
		switch (_sim.getSimulationState()) {
			case INITIAL:
				System.exit(0);
				//$FALL-THROUGH$
			case READY:
			case STEPPING:
			case RUNNING:
				final int opt = JOptionPane.showOptionDialog(this, EXIT_QUESTION_TEXT, EXIT_DIALOG_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, EXIT_DIALOG_OPTIONS, EXIT_DIALOG_OPTIONS[0]);
				if (opt != JOptionPane.YES_OPTION) {
					break;
				}
				_sim.finish();
				//$FALL-THROUGH$
			case FINISHED:
				if(getDefaultCloseOperation() == DO_NOTHING_ON_CLOSE){
					System.exit(0);
				}
				
		}
	}
}
