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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XComponent;
import com.xtructure.xutil.id.XId;

/**
 * A simple implementation of {@link XAddress} that supports limited wildrcard
 * matching.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class XAddressImpl
        implements XAddress
{
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(XAddressImpl.class);

    /**
     * The component of this address.
     */
    private final XComponent<?> _component;

    /**
     * The part id of this address.
     */
    private final XId _partId;

    /**
     * Creates a new address.
     * 
     * @param component
     *            the component of this address
     * 
     * @param partId
     *            the part id of this address
     */
    public XAddressImpl(
            final XComponent<?> component,
            final XId partId)
    {
        super();

        _component = component;
        _partId = partId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final XComponent<?> getComponent()
    {
        LOGGER.trace("begin {}.getComponent()", getClass().getSimpleName());
        LOGGER.trace("will return: {}", _component);
        LOGGER.trace("end {}.getComponent()", getClass().getSimpleName());
        return _component;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final XId getPartId()
    {
        LOGGER.trace("begin {}.getPartId()", getClass().getSimpleName());
        LOGGER.trace("will return: {}", _partId);
        LOGGER.trace("end {}.getPartId()", getClass().getSimpleName());
        return _partId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final boolean matches(
            final XComponent<?> component,
            final XId partId)
    {
        LOGGER.trace("begin {}.matches({},{})", new Object[] { getClass().getSimpleName(),
                component, partId });

        final boolean rval;
        if ((component != null) && (_component != null)
                && !component.equals(_component))
        {
            rval = false;
        }
        else if ((partId != null) && (_partId != null)
                && !partId.equals(_partId))
        {
            rval = false;
        }
        else
        {
            rval = true;
        }

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.matches()", getClass().getSimpleName());

        return rval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString()
    {
        LOGGER.trace("begin {}.toString()", getClass().getSimpleName());

        final String rval = String.format("%s.%s", _component.getId(), _partId);

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.toString()", getClass().getSimpleName());

        return rval;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    @Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof XAddressImpl)) {
			return false;
		}
		XAddress addr = (XAddress) obj;
		return this.matches(addr.getComponent(), addr.getPartId());
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    	return toString().hashCode();
    }
}
