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

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsKey;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import org.testng.annotations.Test;

@Test(groups = { "unit:xutil" })
public final class UTestXmlBinding {
	public void constructorSucceeds() {
		assertThat("",//
				new XmlBinding(getClass()), isNotNull());
	}

	public void builderAddBehavesAsExpected() {
		XmlBinding.Builder builder = XmlBinding.builder();
		XmlBinding binding = builder.newInstance();
		assertThat("",//
				binding.getAliasMap().keySet(), isEmpty());
		XmlBinding.Builder got = builder.add(Integer.class);
		assertThat("",//
				got, isSameAs(builder));
		binding = builder.newInstance();
		assertThat("",//
				binding.getAliasMap(), containsKey(Integer.class.getSimpleName()));
		assertThat("",//
				binding.getAliasMap().get(Integer.class.getSimpleName()), isEqualTo(Integer.class));
		got = builder.add(Double.class, "double string");
		assertThat("",//
				got, isSameAs(builder));
		binding = builder.newInstance();
		assertThat("",//
				binding.getAliasMap(), containsKey(Integer.class.getSimpleName()));
		assertThat("",//
				binding.getAliasMap().get(Integer.class.getSimpleName()), isEqualTo(Integer.class));
		assertThat("",//
				binding.getAliasMap(), containsKey("double string"));
		assertThat("",//
				binding.getAliasMap().get("double string"), isEqualTo(Double.class));
		got = builder.add(new XmlBinding(String.class));
		assertThat("",//
				got, isSameAs(builder));
		binding = builder.newInstance();
		assertThat("",//
				binding.getAliasMap(), containsKey(Integer.class.getSimpleName()));
		assertThat("",//
				binding.getAliasMap().get(Integer.class.getSimpleName()), isEqualTo(Integer.class));
		assertThat("",//
				binding.getAliasMap(), containsKey("double string"));
		assertThat("",//
				binding.getAliasMap().get("double string"), isEqualTo(Double.class));
		assertThat("",//
				binding.getAliasMap(), containsKey(String.class.getSimpleName()));
		assertThat("",//
				binding.getAliasMap().get(String.class.getSimpleName()), isEqualTo(String.class));
	}
}
