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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.xtructure.xutil.id.XId;

/**
 * The menu bar of an {@link AbstractXSimulationGui}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class MainMenuBar
        extends JMenuBar
{
    /** The serial version UID of this class (FIXME). */
    private static final long serialVersionUID = 1L;

    /** The GUI using this menu bar. */
    private final AbstractXSimulationGui<?> _gui;

    /**
     * Creates a new menu bar.
     * 
     * @param gui
     *            the GUI using this menu bar
     */
    public MainMenuBar(
            final AbstractXSimulationGui<?> gui)
    {
        super();

        _gui = gui;

        add(new FileMenu());
        add(new VizMenu());
        add(new HelpMenu());
    }

    /** A file menu. */
    private final class FileMenu
            extends JMenu
    {
        /** The serial version UID of this class (FIXME). */
        private static final long serialVersionUID = 1L;

        /** Creates a new file menu. */
        private FileMenu()
        {
            super("File");

            setMnemonic(KeyEvent.VK_F);

            // exit
            final JMenuItem exitItem = new JMenuItem("Exit");
            exitItem.setMnemonic(KeyEvent.VK_X);
            exitItem.addActionListener(new ActionListener()
            {
                @Override
                public final void actionPerformed(
                        final ActionEvent event)
                {
                    _gui.displayExitDialog();
                }
            });
            add(exitItem);
        }
    }

    /** A visualization menu. */
    private final class VizMenu
            extends JMenu
            implements ItemListener
    {
        /** The serial version UID of this class (FIXME). */
        private static final long serialVersionUID = 1L;

        /** A map of visualziation buttons to visualziation ids. */
        private final Map<JCheckBoxMenuItem, XId> _vizButtons;

        /** Creates a new help menu. */
        private VizMenu()
        {
            super("Visualizations");
            
            setMnemonic(KeyEvent.VK_V);

            final Map<JCheckBoxMenuItem, XId> tmpVizButtons = new HashMap<JCheckBoxMenuItem, XId>();
            
            if (_gui.getVisualizationIds().isEmpty())
            {
                setEnabled(false);
            }
            else
            {
                int index = 1;
                for (final XId id : _gui.getVisualizationIds())
                {
                    final JCheckBoxMenuItem vizItem = new JCheckBoxMenuItem(id
                        .toString());
                    if (index <= 9)
                    {
                        vizItem.setMnemonic(KeyEvent.VK_0 + (index++));
                    }
                    vizItem.setSelected(true);
                    vizItem.addItemListener(this);
                    tmpVizButtons.put(vizItem, id);
                    add(vizItem);
                }
            }
            _vizButtons = Collections.unmodifiableMap(tmpVizButtons);
        }

        /** {@inheritDoc} */
        @Override
        public final void itemStateChanged(
                final ItemEvent event)
        {
            if (event.getStateChange() == ItemEvent.SELECTED)
            {
                _gui.showVisualization(_vizButtons.get(event.getSource()));
            }
            else
            {
                _gui.hideVisualization(_vizButtons.get(event.getSource()));
            }
        }
    }

    /** A help menu. */
    private final class HelpMenu
            extends JMenu
    {
        /** The serial version UID of this class (FIXME). */
        private static final long serialVersionUID = 1L;

        /** Creates a new help menu. */
        private HelpMenu()
        {
            super("Help");

            setMnemonic(KeyEvent.VK_H);

            // about
            final JMenuItem aboutItem = new JMenuItem("About");
            aboutItem.setMnemonic(KeyEvent.VK_A);
            aboutItem.addActionListener(new ActionListener()
            {
                @Override
                public final void actionPerformed(
                        final ActionEvent event)
                {
                    JOptionPane.showMessageDialog(_gui,
                        "XSimulation v.0.9.6 Graphical User Interface\n"
                                + "(c) 2008 Xtructure, LLC\n"
                                + "http://www.xtructure.com", "About XSim GUI",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
            add(aboutItem);
        }
    }
}
