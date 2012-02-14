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
package com.xtructure.xneat.genetics.node;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.io.IOException;

import javolution.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.node.config.NodeGeneConfiguration;
import com.xtructure.xutil.config.DoubleXParameter;
import com.xtructure.xutil.config.FieldMap;

@Test(groups = { "xml:xneat" })
public class UTestNodeGeneConfiguration {
	public void constructorSucceeds() throws IOException, XMLStreamException {
		NodeGeneConfiguration config = NodeGeneConfiguration.builder(null).newInstance();
		assertThat("",//
				config, isNotNull());
	}

	public void newFieldMapReturnsExpectedFieldMap() {
		NodeGeneConfiguration config = NodeGeneConfiguration.builder(null).newInstance();
		FieldMap fieldMap = config.newFieldMap();
		assertThat("",//
				fieldMap, isNotNull());
		assertThat("",//
				fieldMap.getFieldIds(), hasSize(1), containsElement(NodeGene.ACTIVATION_ID));
		DoubleXParameter prm = (DoubleXParameter) config.getParameter(NodeGene.ACTIVATION_ID);
		double weight = fieldMap.get(NodeGene.ACTIVATION_ID);
		assertThat("",//
				prm.getLifetimeRange().contains(weight), isTrue());
	}
}
