/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.tool.delegate;

/**
 * XEvoToolDelegate is the base class for the tool delegates for specific
 * problem domains. Such a tool delegate defines an enum implementing the
 * AvailableTool interface, listing the tools that may be launched from the
 * delegate.
 * <p>
 * To use the delegate, it would be run from the command line with the following
 * arguments:
 * <p>
 * <code>toolName [toolArgs]</code>
 * <p>
 * Where toolName is the name of the specific tool to launch (the string
 * representation of its corresponding AvailableTool enum), and toolArgs are the
 * command line arguments as defined and expected by the launched tool. Thus it
 * is unnecessary for tools to define exclusive (or even consistent) command
 * line interface schemes.
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class XEvoToolDelegate {
	/**
	 * Takes the rawArgs command line arguments and launches the appropriate
	 * tool.
	 * 
	 * @param rawArgs
	 *            command line arguments
	 */
	public final void entry(String[] rawArgs) {
		if (rawArgs.length < 1) {
			System.out.println("must specify a tool to launch:");
			for (AvailableTool name : availableToolNames()) {
				System.out.println("\t" + name);
			}
			System.exit(1);
		}
		String name = rawArgs[0];
		String[] args = new String[rawArgs.length - 1];
		System.arraycopy(rawArgs, 1, args, 0, args.length);
		launchTool(name, args);
	}

	/**
	 * Launches the tool in this delegate with the given name, using the given
	 * args.
	 * 
	 * @param toolName
	 *            name of the tool to launch
	 * @param args
	 *            tool's command line arguments
	 */
	protected abstract void launchTool(String toolName, String[] args);

	/**
	 * Returns the list of tools available to this delegate.
	 * 
	 * @return the list of tools available to this delegate.
	 */
	public abstract AvailableTool[] availableToolNames();

	/** interface for enums listing tools available to the delegate */
	public interface AvailableTool {
		// nothing
	}
}
