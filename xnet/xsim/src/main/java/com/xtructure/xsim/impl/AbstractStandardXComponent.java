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

import static com.xtructure.xutil.valid.ValidateUtils.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XBorder;
import com.xtructure.xsim.XComponent;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;

/**
 * A base class for components that use a {@link StandardXClock}.
 * 
 * @author Peter N&uuml;rnberg
 * @author Luis Guimbarda
 * 
 * @version 0.9.6
 */
public abstract class AbstractStandardXComponent
		extends AbstractXIdObject
        implements XComponent<StandardXClock.StandardTimePhase>
{
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(AbstractStandardXComponent.class);

    /**
     * The set of source ids in this component.
     */
    private final Set<XId> _sourceIds;

    /**
     * The set of target addresses in this component.
     */
    private final Set<XId> _targetIds;

    /**
     * The borders of this component.
     */
    private final Set<XBorder> _borders = new HashSet<XBorder>();

    /**
     * Creates a new standard component.
     * 
     * @param id
     *            the id of this component
     * 
     * @param sourceIds
     *            the set of source ids in this component
     * 
     * @param targetIds
     *            the set of target addresses in this component
     */
    protected AbstractStandardXComponent(
            final XId id,
            final Set<XId> sourceIds,
            final Set<XId> targetIds)
    {
        super(id);
        
        _sourceIds = new SetBuilder<XId>().addAll(sourceIds)
            .newImmutableInstance();
        _targetIds = new SetBuilder<XId>().addAll(targetIds)
            .newImmutableInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final Set<XId> getSourceIds()
    {
        LOGGER.trace("begin {}.getSourceIds()", getClass().getSimpleName());

        final Set<XId> rval = _sourceIds;

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.getSourceIds()", getClass().getSimpleName());

        return rval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final Set<XId> getTargetIds()
    {
        LOGGER.trace("begin {}.getTargetIds()", getClass().getSimpleName());

        final Set<XId> rval = _targetIds;

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.getTargetIds()", getClass().getSimpleName());

        return rval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final void addBorder(
            final XBorder border)
    {
        LOGGER.trace("begin {}.addBorder({})", getClass().getSimpleName(),
            border);

        validateArg("border", border, isNotNull());
        _borders.add(border);

        LOGGER.trace("end {}.addBorder()", getClass().getSimpleName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final void removeBorder(
            final XBorder border)
    {
        LOGGER.trace("begin {}.removeBorder({})", getClass().getSimpleName(),
            border);

        validateArg("border", border, isNotNull());
        _borders.remove(border);

        LOGGER.trace("end {}.removeBorder()", getClass().getSimpleName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public final void update(
            final XTime<StandardTimePhase> time)
    {
        LOGGER.trace("begin {}.prepareBeforeHook({})", getClass()
            .getSimpleName(), time);

        validateArg("time", time, isNotNull());

        switch (time.getPhase())
        {
            case PREPARE:
                prepareBeforeHook();
                prepare();
                prepareAfterHook();
                break;

            case CALCULATE:
                calculateBeforeHook();
                calculate();
                calculateAfterHook();
                break;

            case UPDATE:
                updateBeforeHook();
                update();
                updateAfterHook();
                break;

            case CLEAN_UP:
                cleanUpBeforeHook();
                cleanUp();
                cleanUpAfterHook();
                break;

            default:
                throw new AssertionError("unknown clock phase '"
                        + time.getPhase() + "'");
        }

        LOGGER.trace("end {}.prepareBeforeHook()", getClass().getSimpleName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        LOGGER.trace("begin {}.toString()", getClass().getSimpleName());

        final String rval = String.format("%s %s", //
            ClassUtils.getShortClassName(getClass().getSimpleName()), getId());

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.toString()", getClass().getSimpleName());

        return rval;
    }

    /**
     * A pre-processing hook for {@link #prepare()}.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void prepareBeforeHook()
    {
        LOGGER
            .trace("begin {}.prepareBeforeHook()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.prepareBeforeHook()", getClass().getSimpleName());
    }

    /**
     * A processing hook for {@link #update(XTime)} in the
     * {@link StandardTimePhase#PREPARE} phase.
     * 
     * <p>
     * By default, gets all foreign data, but may be overriden by
     * specializations of {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void prepare()
    {
        LOGGER.trace("begin {}.prepare()", getClass().getSimpleName());

        retrieveForeignData();

        LOGGER.trace("end {}.prepare()", getClass().getSimpleName());
    }

    /**
     * A post-processing hook for {@link #prepare()}.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void prepareAfterHook()
    {
        LOGGER.trace("begin {}.prepareAfterHook()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.prepareAfterHook()", getClass().getSimpleName());
    }

    /**
     * A pre-processing hook for {@link #calculate()}.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void calculateBeforeHook()
    {
        LOGGER.trace("begin {}.calculateBeforeHook()", getClass()
            .getSimpleName());

        // does nothing, but may be overridden

        LOGGER
            .trace("end {}.calculateBeforeHook()", getClass().getSimpleName());
    }

    /**
     * A processing hook for {@link #update(XTime)} in the
     * {@link StandardTimePhase#CALCULATE} phase.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void calculate()
    {
        LOGGER.trace("begin {}.calculate()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.calculate()", getClass().getSimpleName());
    }

    /**
     * A post-processing hook for {@link #calculate()}.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void calculateAfterHook()
    {
        LOGGER.trace("begin {}.calculateAfterHook()", getClass()
            .getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.calculateAfterHook()", getClass().getSimpleName());
    }

    /**
     * A pre-processing hook for {@link #update()}.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void updateBeforeHook()
    {
        LOGGER.trace("begin {}.updateBeforeHook()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.updateBeforeHook()", getClass().getSimpleName());
    }

    /**
     * A processing hook for {@link #update(XTime)} in the
     * {@link StandardTimePhase#UPDATE} phase.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void update()
    {
        LOGGER.trace("begin {}.update()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.update()", getClass().getSimpleName());
    }

    /**
     * A post-processing hook for {@link #update()}.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void updateAfterHook()
    {
        LOGGER.trace("begin {}.updateAfterHook()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.updateAfterHook()", getClass().getSimpleName());
    }

    /**
     * A pre-processing hook for {@link #cleanUp()}.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void cleanUpBeforeHook()
    {
        LOGGER
            .trace("begin {}.cleanUpBeforeHook()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.cleanUpBeforeHook()", getClass().getSimpleName());
    }

    /**
     * A processing hook for {@link #update(XTime)} in the
     * {@link StandardTimePhase#CLEAN_UP} phase.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void cleanUp()
    {
        LOGGER.trace("begin {}.cleanUp()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.cleanUp()", getClass().getSimpleName());
    }

    /**
     * A post-processing hook for {@link #cleanUp()}.
     * 
     * <p>
     * By default, does nothing, but may be overriden by specializations of
     * {@link AbstractStandardXComponent}.
     * </p>
     */
    protected void cleanUpAfterHook()
    {
        LOGGER.trace("begin {}.cleanUpAfterHook()", getClass().getSimpleName());

        // does nothing, but may be overridden

        LOGGER.trace("end {}.cleanUpAfterHook()", getClass().getSimpleName());
    }

    /**
     * Returns the borders of this component.
     * 
     * @return the borders of this component
     */
    protected final Set<XBorder> getBorders()
    {
        LOGGER.trace("begin {}.getBorders()", getClass().getSimpleName());

        final Set<XBorder> rval = _borders;

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.getBorders()", getClass().getSimpleName());

        return rval;
    }

    /** Retrieves all data from borders. */
    protected final void retrieveForeignData()
    {
        LOGGER.trace("begin {}.retrieveForeignData()", getClass()
            .getSimpleName());

        for (final XBorder border : _borders)
        {
            final Map<XId, Map<XAddress, Object>> allData = border
                .getData(this);
            
            //FIXME: what if allData is null?
            
            for (final XId targetId : allData.keySet())
            {
                final Map<XAddress, Object> targetData = allData.get(targetId);
                for (final XAddress sourceAddress : targetData.keySet())
                {
                    addForeignData(targetId, sourceAddress, //
                        targetData.get(sourceAddress));
                }
            }
        }

        LOGGER
            .trace("end {}.retrieveForeignData()", getClass().getSimpleName());
    }

    /**
     * Logs the data associated with the identified parts.
     * 
     * @param label
     *            a description of the parts, the data of which should be logged
     * 
     * @param ids
     *            the ids of the parts, the data of which should be logged
     */
    protected final void logData(
            final String label,
            final Set<XId> ids)
    {
        LOGGER.trace("begin {}.logData({},{})", new Object[] {
                getClass().getSimpleName(), label, ids });

        if ((ids == null) || (ids.isEmpty()))
        {
            LOGGER.info("no ids in set {}", label);
            return;
        }
        for (final XId partId : ids)
        {
            LOGGER.info("component {}::{}::{}: {}", new Object[] { getId(),
            		label, partId, getData(partId) });
        }

        LOGGER.trace("end {}.logData()", getClass().getSimpleName());
    }

    /**
     * Logs the data associated with the sources of this component.
     */
    protected final void logSourceData()
    {
        LOGGER
            .trace("begin {}.prepareBeforeHook()", getClass().getSimpleName());

        logData("source", _sourceIds);

        LOGGER.trace("end {}.prepareBeforeHook()", getClass().getSimpleName());
    }

    /**
     * Logs the data associated with the targets of this component.
     */
    protected final void logTargetData()
    {
        LOGGER
            .trace("begin {}.prepareBeforeHook()", getClass().getSimpleName());

        logData("target", _targetIds);

        LOGGER.trace("end {}.prepareBeforeHook()", getClass().getSimpleName());
    }

    /**
     * Adds the given data from the identified source to the identified target.
     * 
     * @param targetId
     *            the id of the target to which this data should be added
     * 
     * @param sourceAddress
     *            the address from which the given data originated
     * 
     * @param data
     *            the data to add
     */
    protected abstract void addForeignData(
            XId targetId,
            XAddress sourceAddress,
            Object data);
}
