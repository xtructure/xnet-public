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
package com.xtructure.xutil.xml;

import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javolution.xml.XMLBinding;

import com.xtructure.xutil.config.XBuilder;

/**
 * An extension of {@link XMLBinding} which makes defining bindings for multiple
 * class more convenient.
 * <P>
 * The constructor may be used to simply bind the given classes to their simple
 * names. The {@link Builder} class allows for specific binding definitions and
 * merging of {@link XmlBinding}s.
 * 
 * @author Luis Guimbarda
 * 
 */
public final class XmlBinding extends XMLBinding {
	private static final long	serialVersionUID	= 1L;

	/**
	 * Returns a new {@link Builder}
	 * 
	 * @return a new {@link Builder}
	 */
	public static Builder builder() {
		return new Builder();
	}

	/** map from aliases to their classes */
	private final Map<String, Class<?>>	aliasMap	= new HashMap<String, Class<?>>();

	/**
	 * Creates a new {@link XmlBinding} for the given classes.
	 * 
	 * @param classes
	 */
	public XmlBinding(Class<?>... classes) {
		super();
		for (Class<?> cls : classes) {
			setAlias(cls, cls.getSimpleName());
			aliasMap.put(cls.getSimpleName(), cls);
		}
	}

	/**
	 * Creates a new {@link XmlBinding} using the given aliasMap
	 * 
	 * @param aliasMap
	 */
	private XmlBinding(Map<String, Class<?>> aliasMap) {
		this.aliasMap.putAll(aliasMap);
		for (String alias : aliasMap.keySet()) {
			setAlias(aliasMap.get(alias), alias);
		}
	}

	/**
	 * Returns this bindings alias map
	 * 
	 * @return this bindings alias map
	 */
	final Map<String, Class<?>> getAliasMap() {
		return Collections.unmodifiableMap(aliasMap);
	}

	/** builder class for {@link XmlBinding}s */
	public static final class Builder implements XBuilder<XmlBinding> {
		/** map from aliases to their classes */
		private final Map<String, Class<?>>	aliasMap	= new HashMap<String, Class<?>>();

		/**
		 * Binds the given class's simple name to it in the built
		 * {@link XmlBinding}
		 * 
		 * @param cls
		 *            the class to bind
		 * @return this {@link Builder}
		 */
		public Builder add(Class<?> cls) {
			return add(cls, cls.getSimpleName());
		}

		/**
		 * Binds the given alias to the given class in the built
		 * {@link XmlBinding}
		 * 
		 * @param cls
		 *            the class to bind
		 * @param alias
		 *            the alias to bind to it
		 * @return this {@link Builder}
		 */
		public Builder add(Class<?> cls, String alias) {
			validateArg("cls", cls, isNotNull());
			validateArg("alias", alias, isNotNull());
			aliasMap.put(alias, cls);
			return this;
		}

		/**
		 * Adds the bindings in the given {@link XmlBinding} to the one being
		 * built.
		 * 
		 * @param binding
		 *            the bindings to add
		 * @return this {@link Builder}
		 */
		public Builder add(XmlBinding binding) {
			validateArg("binding", binding, isNotNull());
			aliasMap.putAll(binding.aliasMap);
			return this;
		}

		/** {@inheritDoc} */
		@Override
		public XmlBinding newInstance() {
			return new XmlBinding(aliasMap);
		}
	}
}
