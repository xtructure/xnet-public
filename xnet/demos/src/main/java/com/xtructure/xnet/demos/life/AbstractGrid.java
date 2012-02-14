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

import java.util.List;

/**
 * A grid of {@link Square}s.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
abstract class AbstractGrid {
	/** The width of this grid. */
	private final int _width;

	/** The height of this grid. */
	private final int _height;

	/** The squares oif this grid. */
	private final Square[][] _squares;

	/**
	 * Creates a new grid.
	 * 
	 * @param width
	 *            the width of this grid
	 * 
	 * @param height
	 *            the height of this grid
	 * 
	 * @param populatedProbability
	 *            the probability that any given square will be initially
	 *            populated
	 */
	protected AbstractGrid(final int width, final int height,
			final float populatedProbability) {
		super();

		_width = width;
		_height = height;

		// create grid
		_squares = new Square[_width][];
		for (int x = 0; x < _width; x++) {
			_squares[x] = new Square[_height];
		}

		// populate grid
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				_squares[x][y] = Square.getInstance(populatedProbability);
			}
		}

		// attach neighbors
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				final Square square = _squares[x][y];
				xdiffloop: for (int xdiff = -1; xdiff <= 1; xdiff++) {
					final int xn = x + xdiff;
					if ((xn < 0) || (xn >= _width)) {
						continue xdiffloop;
					}
					ydiffloop: for (int ydiff = -1; ydiff <= 1; ydiff++) {
						if ((xdiff == 0) && (ydiff == 0)) {
							continue ydiffloop;
						}
						final int yn = y + ydiff;
						if ((yn < 0) || (yn >= _height)) {
							continue ydiffloop;
						}
						square.addNeighbor(_squares[xn][yn]);
					}
				}
			}
		}
	}

	/**
	 * Creates a new grid.
	 * 
	 * @param states
	 *            the states of the squares of the grid
	 */
	protected AbstractGrid(final List<List<Square.State>> states) {
		super();

		_height = states.size();
		int width = 0;
		for (final List<Square.State> row : states) {
			width = Math.max(width, row.size());
		}
		_width = width;

		// create grid
		_squares = new Square[_width][];
		for (int x = 0; x < _width; x++) {
			_squares[x] = new Square[_height];
		}

		// populate grid
		for (int y = 0; y < _height; y++) {
			final List<Square.State> row = states.get(y);
			for (int x = 0; x < _width; x++) {
				_squares[x][y] = Square.getInstance((x < row.size()) ? row
						.get(x) : Square.State.UNPOPULATED);
			}
		}

		// attach neighbors
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				final Square square = _squares[x][y];
				xdiffloop: for (int xdiff = -1; xdiff <= 1; xdiff++) {
					final int xn = x + xdiff;
					if ((xn < 0) || (xn >= _width)) {
						continue xdiffloop;
					}
					ydiffloop: for (int ydiff = -1; ydiff <= 1; ydiff++) {
						if ((xdiff == 0) && (ydiff == 0)) {
							continue ydiffloop;
						}
						final int yn = y + ydiff;
						if ((yn < 0) || (yn >= _height)) {
							continue ydiffloop;
						}
						square.addNeighbor(_squares[xn][yn]);
					}
				}
			}
		}
	}

	/**
	 * Returns the width of this grid.
	 * 
	 * @return the width of this grid
	 */
	final int getWidth() {
		return _width;
	}

	/**
	 * Returns the height of this grid.
	 * 
	 * @return the height of this grid
	 */
	final int getHeight() {
		return _height;
	}

	/**
	 * Returns the square in this grid at the given coordinates.
	 * 
	 * @param x
	 *            the x coordinate of the square to return
	 * 
	 * @param y
	 *            the y coordinate of the square to return
	 * 
	 * @return the square in this grid at the given coordinates
	 */
	final Square getSquare(final int x, final int y) {
		return _squares[x][y];
	}

	/** Instructs each square to record the states of its neighbors. */
	final void calculate() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				getSquare(x, y).calculate();
			}
		}
	}

	/** Instructs each square to update its state. */
	protected abstract void update();

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		final StringBuilder tmp = new StringBuilder();

		for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
				tmp.append(_squares[x][y]);
			}
			tmp.append("\n");
		}

		return tmp.toString();
	}
}
