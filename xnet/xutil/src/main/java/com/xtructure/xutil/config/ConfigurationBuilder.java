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
package com.xtructure.xutil.config;

/**
 * 
 * The {@link ConfigurationBuilder} interface describes classes that collect the
 * arguments necessary to construct a {@link XConfiguration}s.
 * 
 * @author Luis Guimbarda
 * 
 * @param <C>
 */
public interface ConfigurationBuilder<C extends XConfiguration> extends XBuilder<C> {
	/**
	 * Sets the given configuration to be the template for this
	 * {@link ConfigurationBuilder}. That is, where parameters haven't been
	 * specified, the corresponding parameter in the template will be used.
	 * 
	 * @param configuration
	 *            the configuration to use as template
	 * @return this {@link ConfigurationBuilder}
	 */
	public ConfigurationBuilder<C> setTemplate(C configuration);
}
