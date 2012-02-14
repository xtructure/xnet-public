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

import com.xtructure.xutil.id.XId;

/**
 * An address is an {@link XComponent} and a part {@link XId}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public interface XAddress
{
    /**
     * Returns the component of this address.
     * 
     * @return the component of this address
     */
    XComponent<?> getComponent();

    /**
     * Returns the id of the part of this address.
     * 
     * @return the id of the part of this address
     */
    XId getPartId();

    /**
     * Indicates whether the given component and/or part id match this address.
     * 
     * @param component
     *            the component to match, or <code>null</code> if this address
     *            should match any component
     * 
     * @param partId
     *            the id of the part to match, or <code>null</code> if this
     *            address should match any part
     * 
     * @return <code>true</code> if the given component and/or part id match
     *         this address; <code>false</code> otherwise
     */
    boolean matches(
            XComponent<?> component,
            XId partId);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	boolean equals(Object obj);

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode();
}
