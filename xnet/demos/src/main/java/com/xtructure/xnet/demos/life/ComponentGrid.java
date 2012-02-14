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

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import javolution.xml.XMLBinding;
import javolution.xml.XMLFormat;
import javolution.xml.XMLObjectReader;
import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

import com.xtructure.xsim.impl.AbstractSimpleStandardXComponent;
import com.xtructure.xutil.XmlUtils;
import com.xtructure.xutil.id.XId;

/**
 * A grid of squares that is also a standard simulation component.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class ComponentGrid extends AbstractSimpleStandardXComponent {

	/**
	 * The XML format of a component grid.
	 */
	public static final XMLFormat<ComponentGrid> XML_FORMAT = new XmlFormat();

	/**
	 * An XML binding.
	 */
	public static final XMLBinding XML_BINDING = new XmlBinding();

	/**
	 * A constant 1.
	 */
	private static final Integer ONE = Integer.valueOf(1);

	/**
	 * A constant 0.
	 */
	private static final Integer ZERO = Integer.valueOf(0);

	/** The source id for the total number of populated squares in the grid. */
	public static final XId POPULATION_ID = XId.newId("population");

	/**
	 * Returns a component grid.
	 * 
	 * @param id
	 *            the id of this component
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
	 * 
	 * @return a component grid
	 */
	public static final ComponentGrid getInstance(final XId id,
			final int width, final int height, final float populatedProbability) {
		Set<XId> sourceIds = calculateBorderIds(width, height);
		sourceIds.add(POPULATION_ID);
		return new ComponentGrid(id, sourceIds, width, height,
				populatedProbability);
	}

	/**
	 * Returns a component grid.
	 * 
	 * @param id
	 *            the id of this component
	 * 
	 * @param states
	 *            the states of the squares of the grid
	 * 
	 * @return a component grid
	 */
	static final ComponentGrid getInstance(final XId id,
			final List<List<Square.State>> states) {
		final int height = states.size();
		int width = 0;
		for (final List<Square.State> row : states) {
			width = Math.max(width, row.size());
		}
		Set<XId> sourceIds = calculateBorderIds(width, height);
		sourceIds.add(POPULATION_ID);
		return new ComponentGrid(id, sourceIds, states);
	}

	/**
	 * Returns the component grid read from the given input.
	 * 
	 * @param in
	 *            the input from which the component grid should be read
	 * 
	 * @return the component grid read from the given input
	 * 
	 * @throws XMLStreamException
	 *             if the read failed
	 */
	static final ComponentGrid getInstance(final Reader in)
			throws XMLStreamException {
		final XMLObjectReader reader = XMLObjectReader.newInstance(in);
		reader.setBinding(XML_BINDING);
		final ComponentGrid grid = reader.read();
		reader.close();
		return grid;
	}

	/**
	 * Extracts x and y coordinates from an id.
	 * 
	 * @param id
	 *            the id to parse
	 * 
	 * @return the x and y coordinates encoded in the given id
	 */
	static final int[] parseId(final XId id) {
		final int[] rval = new int[2];
		final String[] parts = id.toString().split("\\s+");
		rval[0] = Integer.parseInt(parts[0]);
		rval[1] = Integer.parseInt(parts[1]);
		return rval;
	}

	/**
	 * Encodes the given coordinates into an id.
	 * 
	 * @param x
	 *            the x coordinate to encode
	 * 
	 * @param y
	 *            the y coordinate to encode
	 * 
	 * @return an id with the given coordinates encoded
	 */
	static final XId generateId(final int x, final int y) {
		return XId.newId(String.format("%d %d", x, y));
	}

	/**
	 * Calculate the set of border ids of a grid.
	 * 
	 * @param width
	 *            the width of the grid
	 * 
	 * @param height
	 *            the height of the grid
	 * 
	 * @return the set of border ids of a grid
	 */
	private static final Set<XId> calculateBorderIds(final int width,
			final int height) {
		final Set<XId> borderIds = new HashSet<XId>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				borderIds.add(generateId(x, y));
			}
		}
		return borderIds;
	}

	/** The grid delegate. */
	private final GridDelegate _gridDelegate;

	/** A map of external data. */
	private final Map<XId, Integer> _externalData = new HashMap<XId, Integer>();

	/**
	 * Creates a new component grid.
	 * 
	 * @param id
	 *            the id of this component
	 * 
	 * @param borderIds
	 *            the set of border ids in this component
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
	private ComponentGrid(final XId id, final Set<XId> borderIds,
			final int width, final int height, final float populatedProbability) {
		super(id, borderIds, borderIds);

		_gridDelegate = new GridDelegate(width, height, populatedProbability);

		clearInformBlankTargets();
		for (final XId borderId : borderIds) {
			forbidNull(borderId);
			setDataType(borderId, Integer.class);
		}
	}

	/**
	 * Creates a new component grid.
	 * 
	 * @param id
	 *            the id of this component
	 * 
	 * @param borderIds
	 *            the set of border ids in this component
	 * 
	 * @param states
	 *            the states of the squares of the grid
	 */
	private ComponentGrid(final XId id, final Set<XId> borderIds,
			final List<List<Square.State>> states) {
		super(id, borderIds, borderIds);

		_gridDelegate = new GridDelegate(states);

		clearInformBlankTargets();
		for (final XId borderId : borderIds) {
			forbidNull(borderId);
			setDataType(borderId, Integer.class);
		}
	}

	/** {@inheritDoc} */
	@Override
	public final Object getData(final XId partId) {
		if (POPULATION_ID.equals(partId)) {
			return _gridDelegate.getPopulationCount();
		}
		final int[] coords = parseId(partId);
		switch (_gridDelegate.getSquare(coords[0], coords[1]).getState()) {
		case POPULATED:
			return ONE;

		case UNPOPULATED:
			return ZERO;
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	protected final Object processForeignData(final XId targetId,
			final Object newData) {
		if (!hasForeignData(targetId)) {
			return newData;
		}

		return ((Integer) getForeignData(targetId)) + ((Integer) newData);
	}

	/** {@inheritDoc} */
	@Override
	protected final void prepareAfterHook() {
		super.prepareAfterHook();
	}

	/** Calculate grid after grid message passing. */
	@Override
	protected final void calculateAfterHook() {
		super.calculateAfterHook();
		_gridDelegate.calculate();
	}

	/** {@inheritDoc} */
	@Override
	protected final void updateAfterHook() {
		super.updateAfterHook();
		_gridDelegate.update();
		_externalData.clear();
	}

	/** {@inheritDoc} */
	@Override
	protected final void setData(final XId partId, final Object data) {
		if (!_externalData.containsKey(partId)) {
			_externalData.put(partId, 0);
		}
		_externalData.put(partId, //
				_externalData.get(partId) + (Integer) data);
	}

	/**
	 * Returns the width of this grid.
	 * 
	 * @return the width of this grid
	 */
	final int getGridWidth() {
		return _gridDelegate.getWidth();
	}

	/**
	 * Returns the height of this grid.
	 * 
	 * @return the height of this grid
	 */
	final int getGridHeight() {
		return _gridDelegate.getHeight();
	}

	/**
	 * Writes this configuration to the given output.
	 * 
	 * @param out
	 *            the output to which to write this configuration
	 * 
	 * @throws XMLStreamException
	 *             if the write failed
	 */
	final void write(final Writer out) throws XMLStreamException {
		final XMLObjectWriter writer = XMLObjectWriter.newInstance(out);
		writer.setBinding(XML_BINDING);
		writer.setIndentation("   ");
		writer.write(this);
		writer.close();
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return _gridDelegate.toString();
	}

	/** A grid delegate. */
	private final class GridDelegate extends AbstractGrid {
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
		protected GridDelegate(final int width, final int height,
				final float populatedProbability) {
			super(width, height, populatedProbability);
		}

		/**
		 * Creates a new grid.
		 * 
		 * @param states
		 *            the states of the squares of the grid
		 */
		protected GridDelegate(final List<List<Square.State>> states) {
			super(states);
		}

		/** {@inheritDoc} */
		@Override
		protected final void update() {
			for (int x = 0; x < getWidth(); x++) {
				for (int y = 0; y < getHeight(); y++) {
					final XId id = generateId(x, y);
					if (_externalData.containsKey(id)) {
						getSquare(x, y).update(_externalData.get(id));
					} else {
						getSquare(x, y).update();
					}
				}
			}
		}

		/**
		 * Gets the population count.
		 * 
		 * @return the population count
		 */
		public final int getPopulationCount() {
			int count = 0;
			for (int x = 0; x < getWidth(); x++) {
				for (int y = 0; y < getHeight(); y++) {
					if (Square.State.POPULATED.equals(getSquare(x, y)
							.getState())) {
						count++;
					}
				}
			}
			return count;
		}
	}

	/** The XML format of a component grid. */
	private static final class XmlFormat extends XMLFormat<ComponentGrid> {
		/** The id attribute name. */
		private static final String ID_ATTR_NAME = "id";

		/** The state attribute name. */
		private static final String STATE_ATTR_NAME = "state";

		/** Creates a new XML format. */
		private XmlFormat() {
			super(ComponentGrid.class);
		}

		/** {@inheritDoc} */
		@Override
		public final void write(final ComponentGrid grid,
				final OutputElement xml) throws XMLStreamException {
			xml.setAttribute(ID_ATTR_NAME, grid.getId());

			final StringBuilder tmp = new StringBuilder();
			for (int y = 0; y < grid._gridDelegate.getHeight(); y++) {
				tmp.append("[");
				for (int x = 0; x < grid._gridDelegate.getWidth(); x++) {
					tmp.append(grid._gridDelegate.getSquare(x, y).getState());
				}
				tmp.append("]");
			}
			xml.setAttribute(STATE_ATTR_NAME, tmp.toString());
		}

		/** {@inheritDoc} */
		@Override
		public final ComponentGrid newInstance(final Class<ComponentGrid> type,
				final InputElement xml) throws XMLStreamException {
			final XId id = XmlUtils.getAttribute(xml, ID_ATTR_NAME, XId.class);
			final String str = xml.getAttribute(STATE_ATTR_NAME).toString();

			final List<List<Square.State>> states = new ArrayList<List<Square.State>>();
			List<Square.State> row = null;
			for (int i = 0; i < str.length(); i++) {
				switch (str.charAt(i)) {
				case '[':
					row = new ArrayList<Square.State>();
					states.add(row);
					break;

				case '*':
					if (row != null) {
						row.add(Square.State.POPULATED);
					}
					break;

				case '.':
					if (row != null) {
						row.add(Square.State.UNPOPULATED);
					}
					break;

				default:
					// ignore
					break;
				}
			}

			return ComponentGrid.getInstance(id, states);
		}

		/** {@inheritDoc} */
		@Override
		public final void read(final InputElement xml, final ComponentGrid grid)
				throws XMLStreamException {
			// do nothing - instances are (at least partially) immutable
		}
	}

	/** An XML binding. */
	private static final class XmlBinding extends XMLBinding {
		/** The serial version UID of this class (FIXME). */
		private static final long serialVersionUID = 1L;

		/** Creates a new binding. */
		private XmlBinding() {
			super();

			setAlias(ComponentGrid.class, "componentGrid");
		}
	}
}
