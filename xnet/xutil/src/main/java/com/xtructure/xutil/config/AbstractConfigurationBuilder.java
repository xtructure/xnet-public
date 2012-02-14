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

import static com.xtructure.xutil.valid.ValidateUtils.containsKey;
import static com.xtructure.xutil.valid.ValidateUtils.not;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.xtructure.xutil.Range;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.valid.Condition;

public abstract class AbstractConfigurationBuilder<C extends XConfiguration, B extends AbstractConfigurationBuilder<C, B>> implements ConfigurationBuilder<C> {
	private final XId				configurationId;
	private Map<XId, XParameter<?>>	parameterMap;
	private C						template	= null;

	public AbstractConfigurationBuilder(XId configurationId) {
		this.configurationId = configurationId;
		this.parameterMap = new HashMap<XId, XParameter<?>>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public B setTemplate(C configuration) {
		template = configuration;
		return (B) this;
	}

	protected XId getConfigurationId() {
		return configurationId;
	}

	protected Collection<XParameter<?>> getParameters() {
		return Collections.unmodifiableCollection(parameterMap.values());
	}

	public C getTemplate() {
		return template;
	}

	protected boolean isSet(XId parameterId) {
		return parameterMap.containsKey(parameterId);
	}

	protected abstract void setRemainingToDefaults();

	protected B setIntegerXParameter(XId id, String description, Boolean nullable, Boolean mutable, Integer value) {
		return addParameter(new IntegerXParameter(id, description, nullable, mutable, value));
	}

	protected B setIntegerXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Integer> lifetimeRange, Range<Integer> initialRange) {
		return addParameter(new IntegerXParameter(id, description, nullable, mutable, lifetimeRange, initialRange));
	}

	protected B setLongXParameter(XId id, String description, Boolean nullable, Boolean mutable, Long value) {
		return addParameter(new LongXParameter(id, description, nullable, mutable, value));
	}

	protected B setLongXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Long> lifetimeRange, Range<Long> initialRange) {
		return addParameter(new LongXParameter(id, description, nullable, mutable, lifetimeRange, initialRange));
	}

	protected B setFloatXParameter(XId id, String description, Boolean nullable, Boolean mutable, Float value) {
		return addParameter(new FloatXParameter(id, description, nullable, mutable, value));
	}

	protected B setFloatXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Float> lifetimeRange, Range<Float> initialRange) {
		return addParameter(new FloatXParameter(id, description, nullable, mutable, lifetimeRange, initialRange));
	}

	protected B setDoubleXParameter(XId id, String description, Boolean nullable, Boolean mutable, Double value) {
		return addParameter(new DoubleXParameter(id, description, nullable, mutable, value));
	}

	protected B setDoubleXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Double> lifetimeRange, Range<Double> initialRange) {
		return addParameter(new DoubleXParameter(id, description, nullable, mutable, lifetimeRange, initialRange));
	}

	protected B setBooleanXParameter(XId id, String description, Boolean nullable, Boolean mutable, Boolean value) {
		return addParameter(new BooleanXParameter(id, description, nullable, mutable, value));
	}

	protected B setBooleanXParameter(XId id, String description, Boolean nullable, Boolean mutable, Range<Boolean> lifetimeRange, Range<Boolean> initialRange) {
		return addParameter(new BooleanXParameter(id, description, nullable, mutable, lifetimeRange, initialRange));
	}

	protected B setConditionXParameter(XId id, String description, Boolean nullable, Boolean mutable, Condition value) {
		return addParameter(new ConditionXParameter(id, description, nullable, mutable, value));
	}

	protected B setCollectionXParameter(XId id, String description, Boolean nullable, Boolean mutable, Collection<?> value) {
		return addParameter(new CollectionXParameter(id, description, nullable, mutable, value));
	}

	@SuppressWarnings("unchecked")
	protected B addParameter(XParameter<?> parameter) {
		validateArg("builder has already set id", parameterMap, not(containsKey(parameter.getId())));
		parameterMap.put(parameter.getId(), parameter);
		return (B) this;
	}
}
