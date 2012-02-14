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

package com.xtructure.xsim.impl;

import static com.xtructure.xutil.valid.ValidateUtils.everyElement;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.or;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.Collection;
import java.util.HashSet;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XComponent;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;

/**
 * A set of {@link XAddress}es.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class XAddressSet
        extends HashSet<XAddress>
{
    /**
     * The serial version UID for this class (FIXME).
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new empty address set.
     */
    public XAddressSet()
    {
        super();
    }

    /**
     * Creates a new address set.
     * 
     * @param addresses
     *            the initial members of this set
     */
    public XAddressSet(
            final Collection<? extends XAddress> addresses)
    {
        super();

        validateArg("addresses", addresses, or(isNull(),
        		everyElement(isNotNull())));

        if (addresses != null)
        {
            addAll(addresses);
        }
    }

    /**
     * Creates a new address set.
     * 
     * @param addresses
     *            the initial members of this set
     */
    public XAddressSet(
            final XAddress[] addresses)
    {
        this(new SetBuilder<XAddress>().addAll(addresses).newInstance());
    }

    /**
     * Returns a subset of this set filtered by the given component and part id.
     * 
     * @param component
     *            the component of each member of the returned subset, or
     *            <code>null</code> if any component is acceptable
     * 
     * @param partId
     *            the part id of each member of the returned subset, or
     *            <code>null</code> if any part id is acceptable
     * 
     * @return a subset of this set filtered by the given component and part id
     */
    public XAddressSet subset(
            final XComponent<?> component,
            final XId partId)
    {
        final XAddressSet subset = new XAddressSet();

        for (final XAddress addr : this)
        {
            if (addr.matches(component, partId))
            {
                subset.add(addr);
            }
        }

        return subset;
    }
}
