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

package com.xtructure.xsim.gui.impl;

import java.util.Set;

import com.xtructure.xsim.XTime;
import com.xtructure.xsim.gui.XVisualization;
import com.xtructure.xsim.impl.AbstractStandardXComponent;
import com.xtructure.xsim.impl.StandardXClock;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;

/**
 * A base class for visualization components that use a {@link StandardXClock}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractStandardXVisualization
        extends AbstractStandardXComponent
        implements XVisualization<StandardXClock.StandardTimePhase>
{
    /**
     * Creates a new standard visualization.
     * 
     * @param id
     *            the id of this component
     * 
     * @param targetIds
     *            the set of target addresses in this component
     */
    protected AbstractStandardXVisualization(
            final XId id,
            final Set<XId> targetIds)
    {
        super(id, null, targetIds);
    }

    /** {@inheritDoc} */
    @Override
    public final Object getData(
            final XId partId)
    {
        throw new IllegalStateException("cannot get data from a visualization");
    }

    /**
     * A processing hook for {@link #update(XTime)} in the
     * {@link StandardTimePhase#PREPARE} phase.
     * 
     * <p>
     * By default, does, but may be overriden by specializations. Overrides
     * {@link AbstractStandardXComponent#prepare()}, which gets foreign data.
     * </p>
     */
    @Override
    protected void prepare()
    {
        // do NOT get foreign data yet...
    }

    /**
     * A pre-processing hook for {@link #cleanUp()}.
     * 
     * <p>
     * By default, retrieves foreign data, but may be overriden by
     * specializations.
     * </p>
     */
    @Override
    protected void cleanUpBeforeHook()
    {
        retrieveForeignData();
    }
}
