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
package com.xtructure.xutil.opt;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Parser;

/**
 * XOption extends {@link Option} with a name and argument processing.
 * 
 * @author Luis Guimbarda
 * 
 * @param <T>
 *            the type of data this option represents
 */
public abstract class XOption<T> extends Option {
	/** the name of this option */
	private final String	name;
	/** indicates that this option has been passed an argument */
	private boolean			hasValue	= false;

	/**
	 * Creates a new {@link XOption}
	 * 
	 * @param name
	 * @param opt
	 * @param longOpt
	 * @param hasArg
	 * @param description
	 */
	public XOption(String name, String opt, String longOpt, boolean hasArg, String description) {
		super(opt, longOpt, hasArg, description);
		this.name = name;
	}

	/**
	 * Returns an object of the appropriate type, based on the argument passed
	 * to this option.
	 * 
	 * @return an object of the appropriate type, based on the argument passed
	 *         to this option.
	 */
	public abstract T processValue();

	/**
	 * Parses the given args array with a basic parser, passing the arguments to
	 * the given options
	 * 
	 * @param options
	 * @param args
	 * @throws ParseException
	 */
	public static final void parseArgs(Options options, String[] args) throws ParseException {
		parseArgs(options, args, new BasicParser());
	}

	/**
	 * Parses the given args array with the given parser, passing the arguments
	 * to the given options
	 * 
	 * @param options
	 * @param args
	 * @param parser
	 * @throws ParseException
	 */
	public static final void parseArgs(Options options, String[] args, Parser parser) throws ParseException {
		CommandLine cl = parser.parse(options, args);
		for (Object obj : options.getOptions()) {
			if (obj instanceof XOption<?>) {
				XOption<?> xOption = (XOption<?>) obj;
				xOption.setHasValue(cl.hasOption(xOption.getOpt()));
			}
		}
	}

	/**
	 * Sets the hasValue for this option
	 * 
	 * @param hasValue
	 */
	private void setHasValue(boolean hasValue) {
		this.hasValue = hasValue;
	}

	/**
	 * Indicates whether this option has been passed a value
	 * 
	 * @return true if this option has been passed a value; false otherwise
	 */
	public boolean hasValue() {
		return hasValue;
	}

	/**
	 * Returns the name of this option
	 * 
	 * @return the name of this option
	 */
	public String getName() {
		return name;
	}

	/**
	 * Finds the {@link XOption} in the given {@link Options} with the given
	 * name.
	 * 
	 * @param name
	 *            the name of the {@link XOption} to find
	 * @param options
	 *            the {@link Options} to look through
	 * @return the found {@link XOption}, or null if there is no {@link XOption}
	 *         with the given name
	 */
	public static XOption<?> getOption(String name, Options options) {
		for (Object obj : options.getOptions()) {
			if (obj instanceof XOption<?>) {
				XOption<?> option = (XOption<?>) obj;
				if (option.getName().equals(name)) {
					return option;
				}
			}
		}
		return null;
	}
}
