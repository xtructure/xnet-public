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
import com.xtructure.xsim.impl.AbstractSimpleStandardXComponent;
import com.xtructure.xsim.impl.StandardXClock;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;

/**
 * A base class for visualizations that use a {@link StandardXClock} and process
 * only one update per target.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractSimpleStandardXVisualization
        extends AbstractSimpleStandardXComponent
        implements XVisualization<StandardXClock.StandardTimePhase>
{
    /**
     * Creates a new simple standard visualization.
     * 
     * @param id
     *            the id of this visualization
     * 
     * @param targetIds
     *            the set of target addresses in this visualization
     */
    protected AbstractSimpleStandardXVisualization(
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
     * {@link AbstractSimpleStandardXComponent#prepare()}, which gets foreign
     * data.
     * </p>
     */
    @Override
    protected void prepare()
    {
        // do NOT get foreign data yet...
    }

    /**
     * A processing hook for {@link #update(XTime)} in the
     * {@link StandardTimePhase#UPDATE} phase.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations.
     * Overrides {@link AbstractSimpleStandardXComponent#update()}, which
     * informs targets.
     * </p>
     */
    @Override
    protected void update()
    {
        // do NOT inform targets yet...
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

    /**
     * A processing hook for {@link #update(XTime)} in the
     * {@link StandardTimePhase#CLEAN_UP} phase.
     * 
     * <p>
     * This method calls {@link #setData(XId, Object)} on every target for which
     * data was received, and optinally calls {@link #blankTarget(XId)} for
     * every target for which no data was received.
     * </p>
     */
    @Override
    protected void cleanUp()
    {
        informNonBlankTargets();
        informBlankTargets();
    }
}
