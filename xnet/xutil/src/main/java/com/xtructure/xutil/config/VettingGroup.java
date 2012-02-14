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
import com.xtructure.xutil.valid.Condition;

/**
 * A group of conditions used to vet a field value.
 * 
 * @param <V>
 *            the type of value vetted by this group
 * 
 * @author Peter N&uuml;rnberg
 * 
 * @version 0.9.6
 */
public final class VettingGroup<V>
{
    /**
     * An indication of whether or not an invalid value passed to
     * {@link #accept(Object)} should result in an exception.
     */
    private final boolean _strict;

    /** A default value. */
    private final V _defaultValue;

    /** A list of conditions. */
    private final List<Condition> _conditions;

    /**
     * Returns a vetting group.
     * 
     * @param <V>
     *            the type of value vetted by the returned group
     * 
     * @param defaultValue
     *            a default value
     * 
     * @param condition1
     *            a condition
     * 
     * @param otherConditions
     *            other conditions
     * 
     * @return a vetting group
     */
    public static final <V> VettingGroup<V> getLenientInstance(
            final V defaultValue,
            final Condition condition1,
            final Condition... otherConditions)
    {
        return new VettingGroup<V>(false, defaultValue, condition1,
            otherConditions);
    }

    /**
     * Returns a vetting group.
     * 
     * @param <V>
     *            the type of value vetted by the returned group
     * 
     * @param type
     *            the type of value vetted by the returned group
     * 
     * @param condition1
     *            a condition
     * 
     * @param otherConditions
     *            other conditions
     * 
     * @return a vetting group
     */
    public static final <V> VettingGroup<V> getStrictInstance(
            final Class<V> type,
            final Condition condition1,
            final Condition... otherConditions)
    {
        return new VettingGroup<V>(true, null, condition1,
            otherConditions);
    }

    /**
     * Returns a vetting group.
     * 
     * @param <V>
     *            the type of value vetted by the returned group
     * 
     * @param condition1
     *            a condition
     * 
     * @param otherConditions
     *            other conditions
     * 
     * @return a vetting group
     */
    public static final <V> VettingGroup<V> getStrictInstance(
            final Condition condition1,
            final Condition... otherConditions)
    {
        return new VettingGroup<V>(true, null, condition1,
            otherConditions);
    }

    /**
     * Creates a new vetting group.
     * 
     * @param strict
     *            an indication of whether or not an invalid value passed to
     *            {@link #accept(Object)} should result in an exception
     * 
     * @param defaultValue
     *            a default value
     * 
     * @param condition1
     *            a condition
     * 
     * @param otherConditions
     *            other conditions
     */
    private VettingGroup(
            final boolean strict,
            final V defaultValue,
            final Condition condition1,
            final Condition... otherConditions)
    {
        super();

        _strict = strict;
        _defaultValue = defaultValue;
        _conditions = new ListBuilder<Condition>() //
            .add(condition1) //
            .addAll(otherConditions) //
            .newImmutableInstance();
    }

    /**
     * Returns the default value associated with this group.
     * 
     * @return the default value associated with this group
     */
    public final V getDefaultValue()
    {
        return _defaultValue;
    }

    /**
     * Vets the given value against this vetting group.
     * 
     * @param value
     *            the value to vet
     * 
     * @return <code>true</code> if all conditions in this group were satisfied;
     *         <code>false</code> otherwise
     * 
     * @throws XConfigurationException
     *             if the given value is invalid, and no default value is
     *             appropriate
     */
    public final boolean accept(
            final V value)
        throws XConfigurationException
    {
        for (final Condition condition : _conditions)
        {
            if (!condition.isSatisfiedBy(value))
            {
                if (!_strict)
                {
                    return false;
                }
                throw new XConfigurationException("value '%s' in invalid",
                    value);
            }
        }
        return true;
    }
}
