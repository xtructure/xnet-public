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
package com.xtructure.xneat.network.impl;

import static com.xtructure.xutil.test.TestUtils.createData;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.node.NodeType;
import com.xtructure.xutil.test.AbstractXmlFormatTest;

/**
 * @author Luis Guimbarda
 */
@Test(groups = { "xml:xneat" })
public final class UTestNeuron_XML extends AbstractXmlFormatTest<Neuron> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = createData(//
				new Neuron(0, NodeType.BIAS, -1.0),//
				new Neuron(2, NodeType.INPUT, 0.1),//
				new Neuron(3, NodeType.OUTPUT, 1.0),//
				new Neuron(5, NodeType.HIDDEN, -99.001));
	}

	public UTestNeuron_XML() {
		super(Neuron.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(Neuron t) {
		return new StringBuilder()//
				.append(XML_HEADER)//
				.append(String.format("<%s id=\"%d\" activation=\"%s\" type=\"%s\"/>",//
						Neuron.class.getSimpleName(),//
						t.getId(),//
						Double.toString(t.getActivationSlope()),//
						t.getNodeType().name()))//
				.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
