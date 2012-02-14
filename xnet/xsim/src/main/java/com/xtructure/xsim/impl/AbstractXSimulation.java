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
package com.xtructure.xsim.impl;

import static com.xtructure.xutil.valid.ValidateUtils.everyElement;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.or;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XClock;
import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XTerminator;
import com.xtructure.xsim.XTime;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;

/**
 * A base {@link XSimulation} implementation.
 * 
 * @param <F>
 *            the type of phase of the time of this simulation
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * 
 * @version 0.9.6
 */
public abstract class AbstractXSimulation<F extends XTime.Phase<F>> extends AbstractXIdObject implements XSimulation<F> {
	/**
	 * The logger for this class.
	 */
	private static final Logger			LOGGER			= LoggerFactory.getLogger(AbstractXSimulation.class);
	/**
	 * The components in this simulation.
	 */
	private final Set<XComponent<F>>	_components		= new HashSet<XComponent<F>>();
	/**
	 * The lock protecting {@link #_components}.
	 */
	private final Lock					_componentsLock	= new ReentrantLock();
	/**
	 * The number of milliseconds to delay betwen steps.
	 */
	private int							_tickDelay		= DEFAULT_TICK_DELAY;
	/**
	 * The lock protecting {@link #_tickDelay}.
	 */
	private final Lock					_tickDelayLock	= new ReentrantLock();
	/**
	 * The clock used by this simulation.
	 */
	private final XClock<F>				_clock;
	/**
	 * The lock protecting {@link #_state}.
	 */
	private final Lock					_clockLock		= new ReentrantLock();
	/**
	 * The state of this simulation.
	 */
	private SimulationState				_state			= SimulationState.INITIAL;
	/**
	 * The lock protecting {@link #_state}.
	 */
	private final Lock					_stateLock		= new ReentrantLock();
	/**
	 * A condition signalling that the simulation thread can proceed.
	 */
	private final Condition				_stateProceed	= _stateLock.newCondition();
	/**
	 * The listeners to this simulation.
	 */
	private final List<Listener>		_listeners		= new ArrayList<Listener>();
	/**
	 * The lock protecting {@link #_listeners}.
	 */
	private final Lock					_listenersLock	= new ReentrantLock();
	/**
	 * The thread that runs this simulation.
	 */
	private final SimulationThread		_thread			= new SimulationThread();

	/**
	 * Creates a new simulation.
	 * 
	 * @param id
	 *            the id of this simulation
	 * 
	 * @param clock
	 *            the clock used by this simulation
	 * 
	 * @param components
	 *            the components in this simulation
	 */
	protected AbstractXSimulation(final XId id, final XClock<F> clock, final Collection<XComponent<F>> components) {
		super(id);
		validateArg("clock", clock, isNotNull());
		validateArg("components", components, or(isNull(), everyElement(isNotNull())));
		_clock = clock;
		if (components != null) {
			_components.addAll(components);
		}
	}

	/**
	 * Creates a new simulation.
	 * 
	 * @param id
	 *            the id of this simulation
	 * 
	 * @param clock
	 *            the clock used by this simulation
	 */
	protected AbstractXSimulation(final XId id, final XClock<F> clock) {
		this(id, clock, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Set<XComponent<F>> getComponents() {
		LOGGER.trace("begin {}.getComponents()", getClass().getSimpleName());
		_componentsLock.lock();
		try {
			final Set<XComponent<F>> rval = Collections.unmodifiableSet(new HashSet<XComponent<F>>(_components));
			LOGGER.trace("will return: {}", rval);
			LOGGER.trace("end {}.getComponents()", getClass().getSimpleName());
			return rval;
		} finally {
			_componentsLock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void addComponent(final XComponent<F> component) {
		LOGGER.trace("begin {}.addComponent({})", new Object[] { getClass().getSimpleName(), component });
		validateArg("component", component, isNotNull());
		_componentsLock.lock();
		try {
			final boolean added = _components.add(component);
			if (added) {
				notifyComponentAdded(component);
			}
		} finally {
			_componentsLock.unlock();
		}
		LOGGER.trace("end {}.addComponent()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void removeComponent(final XComponent<F> component) {
		LOGGER.trace("begin {}.removeComponent({})", new Object[] { getClass().getSimpleName(), component });
		validateArg("component", component, isNotNull());
		_componentsLock.lock();
		try {
			final boolean removed = _components.remove(component);
			if (removed) {
				notifyComponentRemoved(component);
			}
		} finally {
			_componentsLock.unlock();
		}
		LOGGER.trace("end {}.removeComponent()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getTickDelay() {
		LOGGER.trace("begin {}.getTickDelay()", getClass().getSimpleName());
		_tickDelayLock.lock();
		try {
			LOGGER.trace("will return: {}", _tickDelay);
			LOGGER.trace("end {}.getTickDelay()", getClass().getSimpleName());
			return _tickDelay;
		} finally {
			_tickDelayLock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setTickDelay(int millis) {
		LOGGER.trace("begin {}.removeComponent({})", new Object[] { getClass().getSimpleName(), millis });
		final int effectiveMillis = ((millis >= 0) ? millis : DEFAULT_TICK_DELAY);
		_tickDelayLock.lock();
		try {
			final boolean delta = (effectiveMillis != _tickDelay);
			_tickDelay = effectiveMillis;
			if (delta) {
				notifyTickDelayChanged(_tickDelay);
			}
		} finally {
			_tickDelayLock.unlock();
		}
		LOGGER.trace("end {}.setTickDelay()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final XTime<F> getTime() {
		LOGGER.trace("begin {}.getTime()", getClass().getSimpleName());
		_clockLock.lock();
		try {
			final XTime<F> rval = _clock.getTime();
			LOGGER.trace("will return: {}", rval);
			LOGGER.trace("end {}.getTime()", getClass().getSimpleName());
			return rval;
		} finally {
			_clockLock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final XTime<F> setTime(final XTime<F> time) {
		LOGGER.trace("begin {}.setTime({})", new Object[] { getClass().getSimpleName(), time });
		validateArg("time", time, isNotNull());
		_clockLock.lock();
		try {
			if (!_clock.getTime().equals(time)) {
				try {
					_clock.setTime(time);
				} catch (IllegalArgumentException illegalArgumentEx) {
					LOGGER.warn("could not set time to given value '%s' - %s", time, illegalArgumentEx.getMessage());
				}
				notifyTimeChanged(_clock.getTime());
			}
			LOGGER.trace("end {}.setTime()", getClass().getSimpleName());
			return _clock.getTime();
		} finally {
			_clockLock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final SimulationState getSimulationState() {
		LOGGER.trace("begin {}.getSimulationState()", getClass().getSimpleName());
		_stateLock.lock();
		try {
			LOGGER.trace("will return: {}", _state);
			LOGGER.trace("end {}.getSimulationState()", getClass().getSimpleName());
			return _state;
		} finally {
			_stateLock.unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void init() {
		LOGGER.trace("begin {}.init()", getClass().getSimpleName());
		_stateLock.lock();
		try {
			validateState("state", _state, //
					isEqualTo(SimulationState.INITIAL));
			_thread.start();
			_state = SimulationState.READY;
			notifyStateChanged(_state);
		} finally {
			_stateLock.unlock();
		}
		LOGGER.trace("end {}.init()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void step() {
		LOGGER.trace("begin {}.step()", getClass().getSimpleName());
		_stateLock.lock();
		try {
			if (!_state.equals(SimulationState.STEPPING)) {
				validateState("state", _state, or( //
						isEqualTo(SimulationState.READY), //
						isEqualTo(SimulationState.RUNNING)));
				_state = SimulationState.STEPPING;
				_stateProceed.signal();
				notifyStateChanged(_state);
			}
		} finally {
			_stateLock.unlock();
		}
		LOGGER.trace("end {}.step()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void run() {
		LOGGER.trace("begin {}.run()", getClass().getSimpleName());
		_stateLock.lock();
		try {
			if (!_state.equals(SimulationState.RUNNING)) {
				validateState("state", _state, or( //
						isEqualTo(SimulationState.READY), //
						isEqualTo(SimulationState.STEPPING)));
				_state = SimulationState.RUNNING;
				_stateProceed.signal();
				notifyStateChanged(_state);
			}
		} finally {
			_stateLock.unlock();
		}
		LOGGER.trace("end {}.run()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void pause() {
		LOGGER.trace("begin {}.pause()", getClass().getSimpleName());
		_stateLock.lock();
		try {
			if (!_state.equals(SimulationState.READY)) {
				validateState("state", _state, or( //
						isEqualTo(SimulationState.STEPPING), //
						isEqualTo(SimulationState.RUNNING)));
				_state = SimulationState.READY;
				notifyStateChanged(_state);
			}
		} finally {
			_stateLock.unlock();
		}
		LOGGER.trace("end {}.pause()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void finish() {
		LOGGER.trace("begin {}.finish()", getClass().getSimpleName());
		_stateLock.lock();
		try {
			validateState("state", _state, or( //
					isEqualTo(SimulationState.READY), //
					isEqualTo(SimulationState.STEPPING), //
					isEqualTo(SimulationState.RUNNING)));
			_thread.interrupt();
			_state = SimulationState.FINISHED;
			notifyStateChanged(_state);
		} finally {
			_stateLock.unlock();
		}
		LOGGER.trace("end {}.finish()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void addListener(final Listener listener) {
		LOGGER.trace("begin {}.addListener({})", new Object[] { getClass().getSimpleName(), listener });
		validateArg("listener", listener, isNotNull());
		_listenersLock.lock();
		try {
			_listeners.add(listener);
		} finally {
			_listenersLock.unlock();
		}
		LOGGER.trace("end {}.addListener()", getClass().getSimpleName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void removeListener(final Listener listener) {
		LOGGER.trace("begin {}.removeListener({})", new Object[] { getClass().getSimpleName(), listener });
		validateArg("listener", listener, isNotNull());
		_listenersLock.lock();
		try {
			_listeners.remove(listener);
		} finally {
			_listenersLock.unlock();
		}
		LOGGER.trace("end {}.removeListener()", getClass().getSimpleName());
	}

	/**
	 * Notifies listeners that a component has been added to this simulation.
	 * 
	 * @param component
	 *            the component that has been added
	 */
	private final void notifyComponentAdded(final XComponent<F> component) {
		LOGGER.trace("begin {}.notifyComponentAdded({})", new Object[] { getClass().getSimpleName(), component });
		_listenersLock.lock();
		try {
			for (final Listener listener : _listeners) {
				listener.simulationComponentAdded(this, component);
			}
		} finally {
			_listenersLock.unlock();
		}
		LOGGER.trace("end {}.notifyComponentAdded()", getClass().getSimpleName());
	}

	/**
	 * Notifies listeners that a component has been removed from this
	 * simulation.
	 * 
	 * @param component
	 *            the component that has been removed
	 */
	private final void notifyComponentRemoved(final XComponent<F> component) {
		LOGGER.trace("begin {}.notifyComponentRemoved({})", new Object[] { getClass().getSimpleName(), component });
		_listenersLock.lock();
		try {
			for (final Listener listener : _listeners) {
				listener.simulationComponentRemoved(this, component);
			}
		} finally {
			_listenersLock.unlock();
		}
		LOGGER.trace("end {}.notifyComponentRemoved()", getClass().getSimpleName());
	}

	/**
	 * Notifies listeners that the tick delay of this simulation has changed.
	 * 
	 * @param delay
	 *            the new tick delay time of this simulation
	 */
	private final void notifyTickDelayChanged(final int delay) {
		LOGGER.trace("begin {}.notifyTickDelayChanged({})", new Object[] { getClass().getSimpleName(), delay });
		_listenersLock.lock();
		try {
			for (final Listener listener : _listeners) {
				listener.simulationTickDelayChanged(this, delay);
			}
		} finally {
			_listenersLock.unlock();
		}
		LOGGER.trace("end {}.notifyTickDelayChanged()", getClass().getSimpleName());
	}

	/**
	 * Notifies listeners that the time on this simulation's clock has changed.
	 * 
	 * @param time
	 *            the new time on this simulation's clock
	 */
	private final void notifyTimeChanged(final XTime<F> time) {
		LOGGER.trace("begin {}.notifyTimeChanged({})", new Object[] { getClass().getSimpleName(), time });
		_listenersLock.lock();
		try {
			for (final Listener listener : _listeners) {
				listener.simulationTimeChanged(this, time);
			}
		} finally {
			_listenersLock.unlock();
		}
		LOGGER.trace("end {}.notifyTimeChanged()", getClass().getSimpleName());
	}

	/**
	 * Notifies listeners that the state of this simulation has changed.
	 * 
	 * @param state
	 *            the new state of this simulation
	 */
	private final void notifyStateChanged(final SimulationState state) {
		LOGGER.trace("begin {}.notifyStateChanged({})", new Object[] { getClass().getSimpleName(), state });
		_listenersLock.lock();
		try {
			for (final Listener listener : _listeners) {
				listener.simulationStateChanged(this, state);
			}
		} finally {
			_listenersLock.unlock();
		}
		LOGGER.trace("end {}.notifyStateChanged()", getClass().getSimpleName());
	}

	/**
	 * Updates the components of this simulation until the next tick.
	 */
	private final void update() {
		LOGGER.trace("begin {}.update()", getClass().getSimpleName());
		_clockLock.lock();
		try {
			final long startTick = _clock.getTime().getTick();
			while (_clock.getTime().getTick() == startTick) {
				// update(_clock.getTime());
				incrementPhase();
			}
		} finally {
			_clockLock.unlock();
		}
		LOGGER.trace("end {}.update()", getClass().getSimpleName());
	}

	/**
	 * Updates the components of this simulation to the given time.
	 * 
	 * @param time
	 *            the time to which the components of this simulation should be
	 *            updated
	 */
	private final void update(final XTime<F> time) {
		LOGGER.trace("begin {}.update({})", new Object[] { getClass().getSimpleName(), time });
		_componentsLock.lock();
		try {
			for (final XComponent<F> component : _components) {
				component.update(time);
				if (component instanceof XTerminator<?> //
						&& ((XTerminator<?>) component).terminalConditionReached() //
						&& !SimulationState.FINISHED.equals(getSimulationState())) {
					finish();
				}
			}
		} finally {
			_componentsLock.unlock();
		}
		LOGGER.trace("end {}.update()", getClass().getSimpleName());
	}

	/**
	 * Advances the clock one phase.
	 * 
	 * @return the time after the increment
	 */
	private final XTime<F> incrementPhase() {
		LOGGER.trace("begin {}.incrementPhase()", getClass().getSimpleName());
		_clockLock.lock();
		try {
			update(_clock.getTime());
			_clock.increment();
			notifyTimeChanged(_clock.getTime());
			final XTime<F> rval = _clock.getTime();
			LOGGER.trace("will return: {}", rval);
			LOGGER.trace("end {}.incrementPhase()", getClass().getSimpleName());
			return rval;
		} finally {
			_clockLock.unlock();
		}
	}

	/**
	 * A simulation thread.
	 */
	private final class SimulationThread extends Thread {
		/**
		 * Creates a new simulation thread.
		 */
		private SimulationThread() {
			super();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * <p>
		 * This method consists of a loop. In this loop, the thread awaits on
		 * {@link AbstractXSimulation#_stateProceed} for a state in which the
		 * thread is allolwed to proceed. It then updates the simulation. If the
		 * simulation is is stepping state, the simulation is paused. Otherwise,
		 * the thread sleeps for the tick delay.
		 * </p>
		 * 
		 * <p>
		 * This loop is executed until the thread is interrupted, either
		 * externally or via {@link AbstractXSimulation#finish()}.
		 * </p>
		 */
		@Override
		public final void run() {
			LOGGER.trace("begin {}.run()", getClass().getSimpleName());
			updateloop: while (true) {
				try {
					awaitProceed();
					update();
					final SimulationState state = getSimulationState();
					switch (state) {
						case STEPPING:
							pause();
							break;
						case RUNNING:
							sleep(_tickDelay);
							break;
						case FINISHED:
							// should never happen, but just in case
							break updateloop;
						default:
							// no need to do anything
					}
				} catch (InterruptedException interruptedEx) {
					// ok, clean up and exit
					break;
				}
			}
			LOGGER.trace("end {}.run()", getClass().getSimpleName());
		}

		/**
		 * Awaits a state in which the simualtion can proceed.
		 * 
		 * @throws InterruptedException
		 *             if this thread was interrupted while waiting for a
		 *             proceedable state
		 */
		private final void awaitProceed() throws InterruptedException {
			LOGGER.trace("begin {}.awaitProceed()", getClass().getSimpleName());
			try {
				_stateLock.lock();
				while (!_state.equals(SimulationState.RUNNING) && !_state.equals(SimulationState.STEPPING)) {
					_stateProceed.await();
				}
			} finally {
				_stateLock.unlock();
			}
			LOGGER.trace("end {}.awaitProceed()", getClass().getSimpleName());
		}
	}
	// /** A listener to a simulation. */
	// interface Listener
	// {
	// /**
	// * Notifies this listener that a simulation's state has changed.
	// *
	// * @param sim
	// * the simulation, the state of which has changed
	// *
	// * @param state
	// * the new state of the given simulation
	// */
	// void simulationStateChanged(
	// XSimulation<?> sim,
	// SimulationState state);
	//
	// /**
	// * Notifies this listener that a simulation's time has changed.
	// *
	// * @param sim
	// * the simulation, the time of which has changed
	// *
	// * @param time
	// * the new time of the given simulation
	// */
	// void simulationTimeChanged(
	// XSimulation<?> sim,
	// XTime<?> time);
	//
	// /**
	// * Notifies this listener that a simulation's step delay has changed.
	// *
	// * @param sim
	// * the simulation, the step delay of which has changed
	// *
	// * @param delay
	// * the new step delay of the given simulation
	// */
	// void simulationStepDelayChanged(
	// XSimulation<?> sim,
	// int delay);
	//
	// /**
	// * Notifies this listener that a component has been added to a
	// * simulation.
	// *
	// * @param sim
	// * the simulation to which the given component has been added
	// *
	// * @param component
	// * the component that has been added to given simulation
	// */
	// void simulationComponentAdded(
	// XSimulation<?> sim,
	// XComponent<?> component);
	//
	// /**
	// * Notifies this listener that a component has been removed from a
	// * simulation.
	// *
	// * @param sim
	// * the simulation from which the given component has been
	// * removed
	// *
	// * @param component
	// * the component that has been removed from given simulation
	// */
	// void simulationComponentRemoved(
	// XSimulation<?> sim,
	// XComponent<?> component);
	// }
}
