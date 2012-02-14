/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xutil.valid.strategy;

import java.util.Arrays;
import java.util.List;

import com.xtructure.xutil.valid.Condition;

/**
 * A strategy for validating arguments based on {@link Condition}s.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.5
 */
public final class ArgumentValidationStrategy<T> extends AbstractValidationStrategy<T> {
	/**
	 * Creates a new argument validation strategy.
	 * 
	 * @param conditions
	 */
	public ArgumentValidationStrategy(Condition... conditions) {
		this(Arrays.asList(conditions));
	}

	/**
	 * Creates a new argument validation strategy.
	 * 
	 * @param conditions
	 */
	public ArgumentValidationStrategy(List<Condition> conditions) {
		super(conditions);
	}

	/** {@inheritDoc} */
	@Override
	public final void processSuccess(final Object object, final String msg, final Condition condition) {
		// do nothing
	}

	/** {@inheritDoc} */
	@Override
	public final void processFailure(final Object object, final String msg, final Condition condition) {
		throw new IllegalArgumentException( //
				String.format("%s (%s): %s", msg, object, condition));
	}
}
