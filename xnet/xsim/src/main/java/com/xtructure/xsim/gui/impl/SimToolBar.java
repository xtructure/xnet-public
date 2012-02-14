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
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.XSimulation.SimulationState;

/**
 * A toolbar for controlling simulations.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class SimToolBar
        extends JToolBar
        implements XSimulation.Listener
{
    /** The minimum tick delay. */
    private static final int MINIMUM_TICK_DELAY = convertMillisToSlider(10);

    /** The maximum tick delay. */
    private static final int MAXIMUM_TICK_DELAY = convertMillisToSlider(10000);

    /** The default tick delay. */
    private static final int DEFAULT_TICK_DELAY = convertMillisToSlider(XSimulation.DEFAULT_TICK_DELAY);

    /** The labels for the tick delay slider. */
    private static final Dictionary<Object, Object> TICK_DELAY_LABELS = new Hashtable<Object, Object>();
    static
    {
        TICK_DELAY_LABELS.put(MINIMUM_TICK_DELAY, new JLabel("fast"));
        TICK_DELAY_LABELS.put(DEFAULT_TICK_DELAY, new JLabel("default"));
        TICK_DELAY_LABELS.put(MAXIMUM_TICK_DELAY, new JLabel("slow"));
    }

    /** The serial version UID of this class (FIXME). */
    private static final long serialVersionUID = 1L;

    /**
     * Returns the millisecond delay corresponding to the given slider value.
     * 
     * @param slider
     *            the value of the slider to convert
     * 
     * @return the millisecond delay corresponding to the given slider value
     */
    private static final int convertSliderToMillis(
            final int slider)
    {
        return (int)Math.round(Math.pow(10.0, (slider / 100.0)) * 1000.0);
    }

    /**
     * Returns the slider value corresponding to the given millisecond delay.
     * 
     * @param millis
     *            the millisecond delay to convert
     * 
     * @return the slider value corresponding to the given millisecond delay
     */
    private static final int convertMillisToSlider(
            final int millis)
    {
        return (int)Math.round(Math.log10(millis / 1000.0) * 100.0);
    }

    /** The GUI using this menu bar. */
    private final AbstractXSimulationGui<?> _gui;

    /** The label holding the tick number. */
    private final JLabel _tickLabel = new JLabel();

    /** An init button. */
    private final JButton _initButton = new JButton("Init");

    /** A step button. */
    private final JButton _stepButton = new JButton("Step");

    /** A run button. */
    private final JButton _runButton = new JButton("Run");

    /** A pause button. */
    private final JButton _pauseButton = new JButton("Pause");

    /** A finish button. */
    private final JButton _finishButton = new JButton("Finish");

    /**
     * Creates a simulation tool bar.
     * 
     * @param gui
     *            the GUI using this menu bar
     */
    public SimToolBar(
            final AbstractXSimulationGui<?> gui)
    {
        super(String.format("XSimulation Tools - %s", gui.getSimulation()
            .getId()));

        _gui = gui;
        _gui.getSimulation().addListener(this);

        _tickLabel.setText(String.format("Tick: %d", gui.getSimulation()
            .getTime().getTick()));
        _initButton.addActionListener(new ActionListener()
        {
            @Override
            public final void actionPerformed(
                    final ActionEvent event)
            {
                _gui.getSimulation().init();
            }
        });
        _stepButton.addActionListener(new ActionListener()
        {
            @Override
            public final void actionPerformed(
                    final ActionEvent event)
            {
                _gui.getSimulation().step();
            }
        });
        _runButton.addActionListener(new ActionListener()
        {
            @Override
            public final void actionPerformed(
                    final ActionEvent event)
            {
                _gui.getSimulation().run();
            }
        });
        _pauseButton.addActionListener(new ActionListener()
        {
            @Override
            public final void actionPerformed(
                    final ActionEvent event)
            {
                _gui.getSimulation().pause();
            }
        });
        _finishButton.addActionListener(new ActionListener()
        {
            @Override
            public final void actionPerformed(
                    final ActionEvent event)
            {
                _gui.getSimulation().finish();
            }
        });

        add(_tickLabel);
        add(_initButton);
        add(_stepButton);
        add(_runButton);
        add(_pauseButton);
        add(_finishButton);
        add(new TickDelaySlider());

        updateButtons(_gui.getSimulation().getSimulationState());
    }

    /** {@inheritDoc} */
    @Override
	public final void simulationComponentAdded(
            final XSimulation<?> sim,
            final XComponent<?> component)
    {
        // ignore
    }

    /** {@inheritDoc} */
    @Override
	public final void simulationComponentRemoved(
            final XSimulation<?> sim,
            final XComponent<?> component)
    {
        // ignore
    }

    /** {@inheritDoc} */
    @Override
	public final void simulationStateChanged(
            final XSimulation<?> sim,
            final SimulationState state)
    {
        updateButtons(state);
    }

    /** {@inheritDoc} */
    @Override
    public final void simulationTickDelayChanged(
            final XSimulation<?> sim,
            final int delay)
    {
        // FIXME: could check if the new delay is the same as the slider
    }

    /** {@inheritDoc} */
    @Override
	public final void simulationTimeChanged(
            final XSimulation<?> sim,
            final XTime<?> time)
    {
        _tickLabel.setText(String.format("Tick: %d", time.getTick()));
    }

    /**
     * Updates the buttons of this tool bar based on the given state.
     * 
     * @param state
     *            the state to dictate the buttons on this tool bar
     */
    private final void updateButtons(
            final XSimulation.SimulationState state)
    {
        switch (state)
        {
            case INITIAL:
                _initButton.setVisible(true);
                _initButton.setEnabled(true);

                _stepButton.setVisible(false);
                _pauseButton.setVisible(false);
                _runButton.setVisible(false);
                _finishButton.setVisible(false);
                break;

            case READY:
                _stepButton.setVisible(true);
                _stepButton.setEnabled(true);
                _runButton.setVisible(true);
                _runButton.setEnabled(true);
                _finishButton.setVisible(true);
                _finishButton.setEnabled(true);

                _initButton.setVisible(false);
                _pauseButton.setVisible(false);
                break;

            case STEPPING:
                _stepButton.setVisible(true);
                _stepButton.setEnabled(false);
                _runButton.setVisible(true);
                _runButton.setEnabled(false);

                _initButton.setVisible(false);
                _pauseButton.setVisible(false);
                _finishButton.setVisible(false);
                break;

            case RUNNING:
                _stepButton.setVisible(true);
                _stepButton.setEnabled(false);
                _pauseButton.setVisible(true);
                _pauseButton.setEnabled(true);

                _initButton.setVisible(false);
                _runButton.setVisible(false);
                _finishButton.setVisible(false);
                break;

            case FINISHED:
                _initButton.setVisible(false);
                _stepButton.setVisible(false);
                _pauseButton.setVisible(false);
                _runButton.setVisible(false);
                _finishButton.setVisible(false);
                break;

            default:
                throw new AssertionError("unknown simulation state " + state);
        }
    }

    /** A tick delay slider. */
    private final class TickDelaySlider
            extends JSlider
            implements ChangeListener
    {
        /** The serial version UID of this class (FIXME). */
        private static final long serialVersionUID = 1L;

        /** Creates a tick delay slider. */
        private TickDelaySlider()
        {
            super(MINIMUM_TICK_DELAY, MAXIMUM_TICK_DELAY, DEFAULT_TICK_DELAY);

            setLabelTable(TICK_DELAY_LABELS);
            setPaintLabels(true);

            addChangeListener(this);
        }

        /** {@inheritDoc} */
        @Override
		public final void stateChanged(
                final ChangeEvent event)
        {
            final JSlider slider = (JSlider)event.getSource();
            if (!slider.getValueIsAdjusting())
            {
                _gui.getSimulation().setTickDelay( //
                    convertSliderToMillis(slider.getValue()));
            }
        }
    }
}
