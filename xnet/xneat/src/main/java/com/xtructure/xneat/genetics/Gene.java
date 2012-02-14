/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.genetics;

import javax.security.auth.login.Configuration;

import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.XConfiguration;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObject;

/**
 * The {@link Gene} interface describes the basic genetic unit in the NEAT
 * algorithm. A {@link Gene} has an associated {@link Innovation} object, which
 * is descriptive of where it belongs in the genome as a whole. It also has a
 * {@link FieldMap} and a {@link Configuration}, used to specify the attributes
 * specific to the gene.
 * 
 * @author Luis Guimbarda
 * 
 */
public interface Gene extends XIdObject {
	/**
	 * Returns the {@link Innovation} for this {@link Gene}
	 * 
	 * @return the {@link Innovation} for this {@link Gene}
	 */
	public Innovation getInnovation();

	/**
	 * Returns the {@link FieldMap} for this {@link Gene}
	 * 
	 * @return the {@link FieldMap} for this {@link Gene}
	 */
	public FieldMap getFieldMap();

	/**
	 * Returns the value of the parameter in this gene with the given id.
	 * 
	 * @param <V>
	 *            type of the paramter's value
	 * @param parameterId
	 *            the id of the parameter whose value to get
	 * @return the value of the parameter in this gene with the given id.
	 */
	public <V> V getParameter(XId parameterId);

	/**
	 * Sets the parameter in this gene with the given id to the given value.
	 * 
	 * @param <V>
	 *            type of the paramter's value
	 * @param parameterId
	 *            the id of the parameter whose value to set
	 * @param value
	 *            the value to set
	 * @return the actual new value of the parameter
	 */
	public <V> V setParameter(XId parameterId, V value);

	/**
	 * Returns the {@link XConfiguration} for this {@link Gene}
	 * 
	 * @return the {@link XConfiguration} for this {@link Gene}
	 */
	public XConfiguration getConfiguration();
}
