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

package com.xtructure.xsim;

import java.util.Set;

import com.xtructure.xutil.id.XIdObject;

/**
 * A simulation.
 * 
 * @param <F>
 *            the type of phase of the time of this simulation
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XSimulation<F extends XTime.Phase<F>>
        extends XIdObject
{
    /** The default number of milliseconds to delay between ticks. */
    int DEFAULT_TICK_DELAY = 500;

    /**
     * Returns the components in this simulation.
     * 
     * @return the components in this simulation
     */
    Set<XComponent<F>> getComponents();

    /**
     * Adds the given component to this simulation.
     * 
     * @param component
     *            the component to add
     * 
     * @throws IllegalArgumentException
     *             if the given component is <code>null</code>
     */
    void addComponent(
            XComponent<F> component);

    /**
     * Removes the given component from this simulation.
     * 
     * @param component
     *            the component to remove
     * 
     * @throws IllegalArgumentException
     *             if the given component is <code>null</code>
     */
    void removeComponent(
            XComponent<F> component);

    /**
     * Returns the number of milliseconds to delay betwen ticks.
     * 
     * @return the number of milliseconds to delay betwen ticks
     */
    int getTickDelay();

    /**
     * Sets the delay between ticks while this simulation is in the
     * {@link SimulationState#RUNNING} state.
     * 
     * @param millis
     *            the number of milliseconds to delay betwen ticks
     */
    void setTickDelay(
            int millis);

    /**
     * Returns the current time of this simulation.
     * 
     * @return the current time of this simulation
     */
    XTime<F> getTime();

    /**
     * Sets the time on the simulation clock.
     * 
     * @param time
     *            the time to set
     * 
     * @return the new time on the simulation clock
     * 
     * @throws IllegalArgumentException
     *             if the given time is <code>null</code>
     */
    XTime<F> setTime(
            XTime<F> time);

    /**
     * Returns the state of this simulation.
     * 
     * @return the state of this simulation
     */
    SimulationState getSimulationState();

    /**
     * Initializes this simulation.
     * 
     * <p>
     * Moves the state of this simulation from {@link SimulationState#INITIAL}
     * to {@link SimulationState#READY}.
     * </p>
     * 
     * @throws IllegalStateException
     *             if the state of this simulation is not
     *             {@link SimulationState#INITIAL}
     */
    void init();

    /**
     * Steps this simulation.
     * 
     * <p>
     * Moves the state of this simulation from {@link SimulationState#READY} or
     * {@link SimulationState#RUNNING} to {@link SimulationState#STEPPING}.
     * </p>
     * 
     * @throws IllegalStateException
     *             if the state of this simulation is neither
     *             {@link SimulationState#STEPPING} nor
     *             {@link SimulationState#READY} nor
     *             {@link SimulationState#RUNNING}
     */
    void step();

    /**
     * Runs this simulation.
     * 
     * <p>
     * Moves the state of this simulation from {@link SimulationState#READY} or
     * {@link SimulationState#STEPPING} to {@link SimulationState#RUNNING}.
     * </p>
     * 
     * @throws IllegalStateException
     *             if the state of this simulation is neither
     *             {@link SimulationState#RUNNING} nor
     *             {@link SimulationState#READY} nor
     *             {@link SimulationState#STEPPING}
     */
    void run();

    /**
     * Pauses this simulation.
     * 
     * <p>
     * Moves the state of this simulation from {@link SimulationState#STEPPING}
     * or {@link SimulationState#RUNNING} to {@link SimulationState#READY}.
     * </p>
     * 
     * @throws IllegalStateException
     *             if the state of this simulation is neither
     *             {@link SimulationState#READY} nor
     *             {@link SimulationState#STEPPING} nor
     *             {@link SimulationState#RUNNING}
     */
    void pause();

    /**
     * Pauses this simulation.
     * 
     * <p>
     * Moves the state of this simulation from {@link SimulationState#READY},
     * {@link SimulationState#STEPPING} or {@link SimulationState#RUNNING} to
     * {@link SimulationState#FINISHED}.
     * </p>
     * 
     * @throws IllegalStateException
     *             if the state of this simulation is neither
     *             {@link SimulationState#READY} nor
     *             {@link SimulationState#STEPPING} nor
     *             {@link SimulationState#RUNNING}
     */
    void finish();

    /**
     * Adds the given listener to this simulation.
     * 
     * @param listener
     *            the listener to add
     * 
     * @throws IllegalArgumentException
     *             if the given listener is <code>null</code>
     */
    void addListener(
            Listener listener);

    /**
     * Removes the given listener from this simulation.
     * 
     * @param listener
     *            the listener to remove
     * 
     * @throws IllegalArgumentException
     *             if the given listener is <code>null</code>
     */
    void removeListener(
            Listener listener);

    /** An enumeration of simulation states. */
    public enum SimulationState
    {
        /** The initial state. */
        INITIAL,

        /** The ready state. */
        READY,

        /** The stepping state. */
        STEPPING,

        /** The running state. */
        RUNNING,

        /** The finished state. */
        FINISHED;
    }

    /** A listener to a simulation. */
    public interface Listener
    {
        /**
         * Notifies this listener that a component has been added to a
         * simulation.
         * 
         * @param sim
         *            the simulation to which the given component has been added
         * 
         * @param component
         *            the component that has been added to given simulation
         */
        void simulationComponentAdded(
                XSimulation<?> sim,
                XComponent<?> component);

        /**
         * Notifies this listener that a component has been removed from a
         * simulation.
         * 
         * @param sim
         *            the simulation from which the given component has been
         *            removed
         * 
         * @param component
         *            the component that has been removed from given simulation
         */
        void simulationComponentRemoved(
                XSimulation<?> sim,
                XComponent<?> component);

        /**
         * Notifies this listener that a simulation's tick delay has changed.
         * 
         * @param sim
         *            the simulation, the tick delay of which has changed
         * 
         * @param delay
         *            the new tick delay of the given simulation
         */
        void simulationTickDelayChanged(
                XSimulation<?> sim,
                int delay);

        /**
         * Notifies this listener that a simulation's time has changed.
         * 
         * @param sim
         *            the simulation, the time of which has changed
         * 
         * @param time
         *            the new time of the given simulation
         */
        void simulationTimeChanged(
                XSimulation<?> sim,
                XTime<?> time);

        /**
         * Notifies this listener that a simulation's state has changed.
         * 
         * @param sim
         *            the simulation, the state of which has changed
         * 
         * @param state
         *            the new state of the given simulation
         */
        void simulationStateChanged(
                XSimulation<?> sim,
                SimulationState state);
    }
}
