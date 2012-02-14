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

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

/**
 * A specialized component of {@link XSimulation} that prepares aggregated
 * statistics for output.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface XReporter<F extends XTime.Phase<F>> extends XComponent<F> {

	/**
	 * @param writer
	 * @throws IOException 
	 */
	public void write(Writer writer) throws IOException;

	/**
	 * @param stream
	 */
	public void write(PrintStream stream);
}
