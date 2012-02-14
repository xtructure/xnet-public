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

import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isOfCompatibleType;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.id.XId;

/**
 * A base class for components that use a {@link StandardXClock} and process
 * only one update per target.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractSimpleStandardXComponent
        extends AbstractStandardXComponent
{
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(AbstractSimpleStandardXComponent.class);

    /**
     * A map from target ids to data types.
     */
    private final Map<XId, Class<?>> _dataTypes = new HashMap<XId, Class<?>>();

    /**
     * A set of targets that do not accept <code>null</code> data.
     */
    private final Set<XId> _nonNullDataTargetIds = new HashSet<XId>();

    /**
     * A map from target ids to data.
     */
    private final Map<XId, Object> _foreignData = new HashMap<XId, Object>();

    /**
     * An indication of whether blank targets should be informed on update.
     */
    private boolean _informBlankTargets = false;

    /**
     * Creates a new simple standard component.
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
    protected AbstractSimpleStandardXComponent(
            final XId id,
            final Set<XId> sourceIds,
            final Set<XId> targetIds)
    {
        super(id, sourceIds, targetIds);
    }

    /**
     * Sets the data type bound associated with the identified target.
     * 
     * @param targetId
     *            the id of the target for which the given data type bound
     *            should be applied
     * 
     * @param type
     *            the data type bound to apply
     * 
     * @throws IllegalArgumentException
     *             if the given target id is <code>null</code>
     * 
     * @throws IllegalArgumentException
     *             if the given id is not a target id in this component
     * 
     * @throws IllegalArgumentException
     *             if the given data type bound is <code>null</code>
     */
    protected final void setDataType(
            final XId targetId,
            final Class<?> type)
    {
        LOGGER.trace("begin {}.setDataType({},{})", new Object[] {
                getClass().getSimpleName(), targetId, type });

        validateArg("targetId", targetId, isNotNull());
        validateArg("targetId", getTargetIds(), containsElement(targetId));
        validateArg("type", type, isNotNull());

        _dataTypes.put(targetId, type);

        LOGGER.trace("end {}.setDataType()", getClass().getSimpleName());
    }

    /**
     * Clears the data type bound associated with the identified target.
     * 
     * @param targetId
     *            the id of the target for which any type bound should be
     *            cleared
     * 
     * @throws IllegalArgumentException
     *             if the given target id is <code>null</code>
     * 
     * @throws IllegalArgumentException
     *             if the given id is not a target id in this component
     */
    protected final void clearDataType(
            final XId targetId)
    {
        LOGGER.trace("begin {}.clearDataType({})", new Object[] {
                getClass().getSimpleName(), targetId });

        validateArg("targetId", targetId, isNotNull());
        validateArg("targetId", getTargetIds(), containsElement(targetId));
        _dataTypes.keySet().remove(targetId);

        LOGGER.trace("end {}.clearDataType()", getClass().getSimpleName());
    }

    /**
     * Adds the restiction that <code>null</code> data should not be accepted
     * for the identified target.
     * 
     * @param targetId
     *            the id of the target for which <code>null</code> data should
     *            not be accepted
     */
    protected final void forbidNull(
            final XId targetId)
    {
        LOGGER.trace("begin {}.forbidNull({})", new Object[] {
                getClass().getSimpleName(), targetId });

        validateArg("targetId", targetId, isNotNull());
        validateArg("targetId", getTargetIds(), containsElement(targetId));
        _nonNullDataTargetIds.add(targetId);

        LOGGER.trace("end {}.forbidNull()", getClass().getSimpleName());
    }

    /**
     * Removes the restiction that <code>null</code> data should not be accepted
     * for the identified target.
     * 
     * @param targetId
     *            the id of the target for which <code>null</code> data may be
     *            accepted
     */
    protected final void allowNull(
            final XId targetId)
    {
        LOGGER.trace("begin {}.allowNull({})", new Object[] {
                getClass().getSimpleName(), targetId });

        validateArg("targetId", targetId, isNotNull());
        validateArg("targetId", getTargetIds(), containsElement(targetId));
        _nonNullDataTargetIds.remove(targetId);

        LOGGER.trace("end {}.allowNull()", getClass().getSimpleName());
    }

    /**
     * Sets the indication of whether blank targets should be informed during
     * updates.
     */
    protected final void setInformBlankTargets()
    {
        LOGGER.trace("begin {}.setInformBlankTargets()", getClass()
            .getSimpleName());

        _informBlankTargets = true;

        LOGGER.trace("end {}.setInformBlankTargets()", getClass()
            .getSimpleName());
    }

    /**
     * Clears the indication of whether blank targets should be informed during
     * updates.
     */
    protected final void clearInformBlankTargets()
    {
        LOGGER.trace("begin {}.clearInformBlankTargets()", getClass()
            .getSimpleName());

        _informBlankTargets = false;

        LOGGER.trace("end {}.clearInformBlankTargets()", getClass()
            .getSimpleName());
    }

    /** {@inheritDoc} */
    @Override
    protected final void addForeignData(
            final XId targetId,
            final XAddress sourceAddress,
            final Object data)
    {
        LOGGER.trace("begin {}.addForeignData({},{},{})", new Object[] {
                getClass().getSimpleName(), targetId, sourceAddress, data });

        // NOTE: this method ignores source addresses

        // is this a valid target?
        validateState("targetId", getTargetIds(), containsElement(targetId));

        // if there is a null restriction, is it satisfied?
        if (_nonNullDataTargetIds.contains(targetId))
        {
            validateState("data is not null", data, isNotNull());
        }

        // if there is a type bound, is it satisfied?
        if ((data != null) && _dataTypes.containsKey(targetId))
        {
            validateState("data is of correct type", data, //
                isOfCompatibleType(_dataTypes.get(targetId)));
        }

        _foreignData.put(targetId, processForeignData(targetId, data));

        LOGGER.trace("end {}.addForeignData()", getClass().getSimpleName());
    }

    /**
     * Indicates if there is any data for the identified target.
     * 
     * @param targetId
     *            the id of the target for which the existence of data should be
     *            checked
     * 
     * @return <code>true</code> if there is data for this taregt;
     *         <code>false</code> otherwise
     */
    protected final boolean hasForeignData(
            final XId targetId)
    {
        LOGGER.trace("begin {}.hasForeignData({})", new Object[] {
                getClass().getSimpleName(), targetId });

        final boolean rval = _foreignData.containsKey(targetId);

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.hasForeignData()", getClass().getSimpleName());

        return rval;
    }

    /**
     * Returns the current calculate data for the identified target.
     * 
     * @param targetId
     *            the id of the target, the calculate data for which should be
     *            returned
     * 
     * @return the current calculate data for the identified target
     */
    protected final Object getForeignData(
            final XId targetId)
    {
        LOGGER.trace("begin {}.clearDataType({})", new Object[] {
                getClass().getSimpleName(), targetId });

        final Object rval = _foreignData.get(targetId);

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.clearDataType()", getClass().getSimpleName());

        return rval;
    }

    /**
     * A pre-processing hook for {@link #prepare()}.
     * 
     * <p>
     * This method clears the data map. If a specialization of this type
     * overrides this method, it should take care to call the overriden version.
     * </p>
     */
    @Override
    protected void prepareBeforeHook()
    {
        LOGGER
            .trace("begin {}.prepareBeforeHook()", getClass().getSimpleName());

        _foreignData.clear();

        LOGGER.trace("end {}.prepareBeforeHook()", getClass().getSimpleName());
    }

    /**
     * A processing hook for {@link #update(XTime)} in the
     * {@link StandardTimePhase#UPDATE} phase.
     * 
     * <p>
     * This method calls {@link #setData(XId, Object)} on every target for which
     * data was received, and optinally calls {@link #blankTarget(XId)} for
     * every target for which no data was received.
     * </p>
     */
    @Override
    protected void update()
    {
        LOGGER.trace("begin {}.update()", getClass().getSimpleName());

        super.update();
        informNonBlankTargets();
        informBlankTargets();

        LOGGER.trace("end {}.update()", getClass().getSimpleName());
    }

    /**
     * Informs non-blank targets of data.
     */
    protected final void informNonBlankTargets()
    {
        LOGGER.trace("begin {}.informNonBlankTargets()", getClass()
            .getSimpleName());

        for (final XId targetId : _foreignData.keySet())
        {
            setData(targetId, _foreignData.get(targetId));
        }

        LOGGER.trace("end {}.informNonBlankTargets()", getClass()
            .getSimpleName());
    }

    /**
     * Informs blank targets of no data.
     */
    protected final void informBlankTargets()
    {
        LOGGER.trace("begin {}.informBlankTargets()", getClass()
            .getSimpleName());

        if (_informBlankTargets)
        {
            for (final XId targetId : getTargetIds())
            {
                if (!_foreignData.containsKey(targetId))
                {
                    blankTarget(targetId);
                }
            }
        }

        LOGGER.trace("end {}.informBlankTargets()", getClass().getSimpleName());
    }

    /**
     * Reflects that the identified target received no data during the last
     * update cycle.
     * 
     * @param targetId
     *            the id of the blank target
     */
    protected void blankTarget(
            final XId targetId)
    {
        LOGGER.trace("begin {}.blankTarget({})", new Object[] {
                getClass().getSimpleName(), targetId });

        // does nothing, but may be overridden

        LOGGER.trace("end {}.blankTarget()", getClass().getSimpleName());
    }

    /**
     * Processes the given calculate data.
     * 
     * <p>
     * By default, this method throws an IllegalStateException if there has
     * already been calculate data associated with the given target data.
     * Otherwise, the given data is returned as is.
     * </p>
     * 
     * @param targetId
     *            the id of the target for which the given data is intended
     * 
     * @param newData
     *            the data for the identified target
     * 
     * @return a processed form of the given data
     */
    protected Object processForeignData(
            final XId targetId,
            final Object newData)
    {
        LOGGER.trace("begin {}.processForeignData({},{})", new Object[] {
                getClass().getSimpleName(), targetId, newData });

        // is there already data for this target
        validateState("target has no previous data on this tick", //
            hasForeignData(targetId), isFalse());

        // ok, put the data
        final Object rval = newData;

        LOGGER.trace("will return: {}", rval);
        LOGGER.trace("end {}.processForeignData()", getClass().getSimpleName());

        return rval;
    }

    /**
     * Sets the data associated with the identified part.
     * 
     * @param partId
     *            the id of the part, the data of which should be set
     * 
     * @param data
     *            the data to be set
     */
    protected abstract void setData(
            XId partId,
            Object data);
}
