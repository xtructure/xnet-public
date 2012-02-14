/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xart.
 *
 * xart is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xart is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xart.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.art.model.link;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import org.testng.annotations.Test;

import com.xtructure.xutil.Range;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.RangeXParameter;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xart" })
public class UTestLinkConfiguration {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void newFieldMapReturnsExpectedObject() {
		LinkConfiguration config = LinkConfiguration.DEFAULT_CONFIGURATION;
		FieldMap map = config.newFieldMap();
		assertThat("",//
				map.getFieldIds(), isEqualTo(config.getParameterIds()));
		for (XId id : config.getParameterIds()) {
			assertThat("",//
					map.getField(id).getParameter(), isEqualTo(config.getParameter(id)));
			Range ir = ((RangeXParameter) config.getParameter(id)).getInitialRange();
			Comparable val = (Comparable) map.getField(id).getValue();
			assertThat("",//
					ir.contains(val),//
					isTrue());
		}
	}
}
