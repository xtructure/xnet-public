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

import java.util.List;

import com.xtructure.xutil.coll.ListBuilder;

/**
 * A vetting strategy based on {@link VettingGroup}s.
 * 
 * @param <V>
 *            the type of value vetted by this strategy
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class VettingStrategyImpl<V>
        implements VettingStrategy<V>
{
    /**
     * Returns a vetting strategy.
     * 
     * @param <V>
     *            the type of value vetted by this strategy
     * 
     * @param vettingGroups
     *            the vetting groups associated with this vetting strategy
     * 
     * @param successor
     *            the successor to this strategy
     * 
     * @return a vetting strategy
     */
    public static final <V> VettingStrategyImpl<V> getInstance(
            final List<VettingGroup<V>> vettingGroups,
            final VettingStrategy<V> successor)
    {
        return new VettingStrategyImpl<V>(vettingGroups, successor);
    }

    /**
     * Returns a vetting strategy.
     * 
     * @param <V>
     *            the type of value vetted by this strategy
     * 
     * @param vettingGroups
     *            the vetting groups associated with this vetting strategy
     * 
     * @return a vetting strategy
     */
    public static final <V> VettingStrategyImpl<V> getInstance(
            final List<VettingGroup<V>> vettingGroups)
    {
        return getInstance(vettingGroups, null);
    }

    /** The condition groups associated with this parameter. */
    private final List<VettingGroup<V>> _vettingGroups;

    /** The successor to this strategy. */
    private final VettingStrategy<V> _successor;

    /**
     * Creates a new vetting strategy.
     * 
     * @param vettingGroups
     *            the vetting groups associated with this vetting strategy
     * 
     * @param successor
     *            the successor to this strategy
     */
    private VettingStrategyImpl(
            final List<VettingGroup<V>> vettingGroups,
            final VettingStrategy<V> successor)
    {
        super();

        _vettingGroups = new ListBuilder<VettingGroup<V>>() //
            .addAll(vettingGroups) //
            .newImmutableInstance();
        _successor = successor;
    }

    /** {@inheritDoc} */
    @Override
	public final V vetValue(
            final V value)
    {
        for (final VettingGroup<V> vettingGroup : _vettingGroups)
        {
            if (!vettingGroup.accept(value))
            {
                return vettingGroup.getDefaultValue();
            }
        }
        return ((_successor != null)
                ? _successor.vetValue(value)
                : value);
    }
}
