/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.genetics.link;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.io.IOException;

import javolution.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xutil.config.DoubleXParameter;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.id.XId;

@Test(groups = { "xml:xneat" })
public class UTestLinkGeneConfiguration {
	public void constructorSucceeds() throws XMLStreamException, IOException {
		LinkGeneConfiguration config = LinkGeneConfiguration.builder(XId.newId("UTestNodeGeneConfiguration", 0)).newInstance();
		assertThat("",//
				config, isNotNull());
		config = LinkGeneConfiguration.builder(XId.newId("UTestLinkGeneConfiguration", 1)).newInstance();
		assertThat("",//
				config, isNotNull());
	}

	public void newFieldMapReturnsExpectedFieldMap() {
		LinkGeneConfiguration config = LinkGeneConfiguration.builder(XId.newId("UTestLinkGeneConfiguration", 2)).newInstance();
		FieldMap fieldMap = config.newFieldMap();
		assertThat("",//
				fieldMap, isNotNull());
		assertThat("",//
				fieldMap.getFieldIds(), hasSize(1), containsElement(LinkGene.WEIGHT_ID));
		DoubleXParameter prm = (DoubleXParameter) config.getParameter(LinkGene.WEIGHT_ID);
		double weight = fieldMap.get(LinkGene.WEIGHT_ID);
		assertThat("",//
				prm.getLifetimeRange().contains(weight), isTrue());
	}
}
