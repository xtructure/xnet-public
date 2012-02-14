/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xbatch.
 *
 * xbatch is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xbatch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xbatch.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xbatch.process;

import com.xtructure.xbatch.batch.XBatch;

/**
 * @author Luis Guimbarda
 * 
 */
public interface XBatchProcessor {
	/**
	 * Start processing batched simulations.
	 * 
	 * @param batch
	 *            the batch from which simulations are processed
	 * @param blocking
	 *            boolean indicating whether to block while processing
	 *            simulations.
	 */
	public void start(XBatch batch, boolean blocking);
}
