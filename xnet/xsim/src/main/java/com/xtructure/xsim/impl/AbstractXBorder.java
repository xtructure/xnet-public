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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xtructure.xsim.XAddress;
import com.xtructure.xsim.XBorder;
import com.xtructure.xsim.XComponent;
import com.xtructure.xutil.id.XId;

/**
 * A base implementation of {@link XBorder}.
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public abstract class AbstractXBorder
        implements XBorder
{
    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(AbstractXBorder.class);

    /**
     * A map from individual targets to sets of corresponding source/transform
     * pairs.
     */
    private final Map<XAddress, List<TransformAddressPair>> _targetToSourceMap = new HashMap<XAddress, List<TransformAddressPair>>();

    /**
     * Creates a new border.
     */
    protected AbstractXBorder()
    {
        super();
    }

    /** {@inheritDoc} */
    @Override
	public final XBorder associate(
            final XAddress source,
            final Transform transform,
            final XAddress target)
    {
        LOGGER.trace("begin {}.associate({},{},{})", new Object[] {
                getClass().getSimpleName(), source, transform, target });

        if (!_targetToSourceMap.containsKey(target))
        {
            _targetToSourceMap.put(target,
                new ArrayList<TransformAddressPair>());
        }

        _targetToSourceMap.get(target).add(
            new TransformAddressPair(transform, source));

        LOGGER.trace("end {}.associate()", getClass().getSimpleName());

        return this;
    }

    /**
     * Associates the given source to the given target.
     * 
     * @param source
     *            the source to be associated
     * 
     * @param target
     *            the target to be associated
     * 
     * @return this border
     */
    public final XBorder associate(
            final XAddress source,
            final XAddress target)
    {
        return associate(source, null, target);
    }

    /**
     * Associates the given sources to the given targets.
     * 
     * @param sources
     *            the sources to be associated
     * 
     * @param targets
     *            the targets to be associated
     * 
     * @return this border
     */
    public final XBorder associate(
            final Set<XAddress> sources,
            final Set<XAddress> targets)
    {
        LOGGER.trace("begin {}.associate({},{})", new Object[] {
                getClass().getSimpleName(), sources, targets });

        for (final XAddress sourceAddress : sources)
        {
            for (final XAddress targetAddress : targets)
            {
                associate(sourceAddress, targetAddress);
            }
        }

        LOGGER.trace("end {}.associate()", getClass().getSimpleName());

        return this;
    }

    /** {@inheritDoc} */
    @Override
	public final Map<XId, Map<XAddress, Object>> getData(
            final XComponent<?> targetComponent)
    {
        LOGGER.trace("begin {}.getData({},{})", new Object[] {
                getClass().getSimpleName(), targetComponent });

        final Map<XId, Map<XAddress, Object>> allData = new HashMap<XId, Map<XAddress, Object>>();

        for (final XAddress targetAddress : _targetToSourceMap.keySet())
        {
            LOGGER.debug("testing {} for match", targetAddress);
            if (targetAddress.matches(targetComponent, null))
            {
                LOGGER.debug("{} matches", targetAddress);
                final Map<XAddress, Object> targetData = new HashMap<XAddress, Object>();
                for (final TransformAddressPair sourceTransformPair : _targetToSourceMap
                    .get(targetAddress))
                {
                    LOGGER.debug("processing {}", sourceTransformPair);
                    final XAddress sourceAddress = sourceTransformPair._address;
                    final Transform transform = sourceTransformPair._transform;
                    final Object rawData = sourceAddress.getComponent()
                        .getData(sourceAddress.getPartId());
                    final Object transformedData = ((transform != null)
                            ? transform.transform(rawData)
                            : rawData);
                    LOGGER.debug("data {} -> {}", rawData, transformedData);
                    targetData.put(sourceAddress, transformedData);
                }
                allData.put(targetAddress.getPartId(), targetData);
            }
        }

        LOGGER.trace("will return: {}", allData);
        LOGGER.trace("end {}.getData()", getClass().getSimpleName());

        return allData;
    }

    /**
     * A transform / address pair.
     */
    private final class TransformAddressPair
    {
        /**
         * A transform.
         */
        private final Transform _transform;

        /**
         * An address.
         */
        private final XAddress _address;

        /**
         * Creates a new transform / address pair.
         * 
         * @param transform
         *            a transform
         * 
         * @param address
         *            an address
         */
        private TransformAddressPair(
                final Transform transform,
                final XAddress address)
        {
            super();

            _transform = transform;
            _address = address;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public final String toString()
        {
            return String.format("%s / %s", _address, _transform);
        }
    }
}
