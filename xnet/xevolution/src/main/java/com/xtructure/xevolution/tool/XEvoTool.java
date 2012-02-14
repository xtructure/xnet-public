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
package com.xtructure.xevolution.tool;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.Options;

import com.xtructure.xevolution.tool.delegate.XEvoToolDelegate;
import com.xtructure.xutil.opt.XOption;

/**
 * XEvoTool is a base class for tools intended to be launched by an
 * {@link XEvoToolDelegate}.
 * <P>
 * The tool excepts the following command line arguments:
 * 
 * @author Luis Guimbarda
 * 
 */
public abstract class XEvoTool {
	private final String	name;
	private final Options	options;

	public XEvoTool(String name, XOption<?>... options) {
		this(name, Arrays.asList(options));
	}

	public XEvoTool(String name, List<XOption<?>> options) {
		this.name = name;
		this.options = new Options();
		for (XOption<?> option : options) {
			this.options.addOption(option);
		}
	}

	public String getName() {
		return name;
	}

	public Options getOptions() {
		return options;
	}

	@SuppressWarnings("unchecked")
	public <O extends XOption<?>> O getOption(String name) {
		return (O) XOption.getOption(name, options);
	}

	public abstract void launch(String[] args);
}
