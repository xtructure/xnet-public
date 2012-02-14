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

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.xtructure.xutil.coll.MapBuilder;

/**
 * A square in a game of life board.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
final class Square {
	/** A random number generator. */
	private static final Random RANDOM = new Random();

	/**
	 * Returns a new square with a random initial state.
	 * 
	 * @param populatedProbability
	 *            the probability that this square will be initially populated
	 * 
	 * @return a new square
	 */
	public static final Square getInstance(final float populatedProbability) {
		return new Square(
				(RANDOM.nextFloat() <= populatedProbability) ? State.POPULATED
						: State.UNPOPULATED);
	}

	/**
	 * Returns a new square with the given state.
	 * 
	 * @param state
	 *            the state of the new square
	 * 
	 * @return a new square
	 */
	public static final Square getInstance(final State state) {
		return new Square(state);
	}

	/** The current state of this square. */
	private State _currentState;

	/** The states of known neighbors of this square. */
	private Map<State, Integer> _knownNeighborStates = null;

	/** The known neighbors of this square. */
	private final Set<Square> _neighbors = new HashSet<Square>();

	/**
	 * Creates a new square.
	 * 
	 * @param initialState
	 *            the initial state of this square
	 */
	Square(final State initialState) {
		super();

		_currentState = initialState;
	}

	/**
	 * Adds the given square as a neighbor of this square.
	 * 
	 * @param neighbor
	 *            the neighbor to add
	 */
	void addNeighbor(final Square neighbor) {
		_neighbors.add(neighbor);
	}

	/**
	 * Returns the current state of this square.
	 * 
	 * @return the current state of this square
	 */
	final State getState() {
		return _currentState;
	}

	/** Records the states of the neighbors of this square. */
	final void calculate() {
		// initialize map
		_knownNeighborStates = createStateMap();

		// record neighbor states
		for (final Square neighbor : _neighbors) {
			final State neighborState = neighbor._currentState;
			_knownNeighborStates.put(neighborState, //
					_knownNeighborStates.get(neighborState) + 1);
		}
	}

	/**
	 * Updates the state of this square.
	 * 
	 * @param unknownNeighborStates
	 *            states of &quot;external&quot squares
	 */
	final void update(final Map<State, Integer> unknownNeighborStates) {
		final Map<State, Integer> allNeighborStates = createStateMap();
		allNeighborStates.putAll(_knownNeighborStates);

		// add external information to existing information
		if (unknownNeighborStates != null) {
			for (final State state : unknownNeighborStates.keySet()) {
				allNeighborStates.put(
						state, //
						allNeighborStates.get(state)
								+ unknownNeighborStates.get(state));
			}
		}

		// should we live or die?
		_currentState = nextState(allNeighborStates);
	}

	/**
	 * Updates the state of this square.
	 * 
	 * @param numPopulatedNeighbors
	 *            the number of unknown populated neighbors
	 */
	final void update(final int numPopulatedNeighbors) {
		update(new MapBuilder<Square.State, Integer>() //
				.put(Square.State.POPULATED, numPopulatedNeighbors) //
				.newImmutableInstance());
	}

	/** Updates the state of this square. */
	final void update() {
		update(null);
	}

	/**
	 * Calculates the next state of this square based on the states of all
	 * neighbors and the 23/3 rule.
	 * 
	 * @param allNeighborStates
	 *            the states of all neighbors
	 * 
	 * @return the next state of this square
	 */
	private final State nextState(final Map<State, Integer> allNeighborStates) {
		switch (allNeighborStates.get(State.POPULATED)) {
		case 2:
			// 2 = current state is correct
			return getState();

		case 3:
			// 3 = life!
			return State.POPULATED;

		default:
			// 0-1 = death (loneliness); 4+ = death (crowding)
			return State.UNPOPULATED;
		}
	}

	/**
	 * Creates a state map with initialized (0) values.
	 * 
	 * @return a state map with initialized (0) values
	 */
	private final Map<State, Integer> createStateMap() {
		final Map<State, Integer> rval = new EnumMap<State, Integer>(
				State.class);
		for (final State state : State.values()) {
			rval.put(state, 0);
		}
		return rval;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return _currentState.toString();
	}

	/** An enumeration of square states. */
	static enum State {
		/** The populated state. */
		POPULATED("*"),

		/** The unpopulated state. */
		UNPOPULATED(".");

		/**
		 * Returns the state corresponding to the given string.
		 * 
		 * @param str
		 *            the string corresponding to the state to be returned
		 * 
		 * @return the state corresponding to the given string
		 */
		public static final State getInstance(final String str) {
			return (POPULATED._str.equals(str) ? POPULATED : UNPOPULATED);
		}

		/** The string representing this state. */
		private final String _str;

		/**
		 * Creates a new state.
		 * 
		 * @param str
		 *            the string representing this state
		 */
		private State(final String str) {
			_str = str;
		}

		/** {@inheritDoc} */
		@Override
		public final String toString() {
			return _str;
		}
	}
}
