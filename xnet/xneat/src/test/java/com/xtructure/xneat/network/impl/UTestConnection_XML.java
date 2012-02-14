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

import com.xtructure.xutil.test.AbstractXmlFormatTest;

/**
 * @author Luis Guimbarda
 */
@Test(groups = { "xml:xneat" })
public class UTestConnection_XML extends AbstractXmlFormatTest<Connection> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = createData(//
				new Connection(0, 2, 1.0),//
				new Connection(1, 1, 0.0),//
				new Connection(2, 0, -1.0));
	}

	public UTestConnection_XML() {
		super(Connection.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(Connection t) {
		StringBuilder sb = new StringBuilder()//
				.append(XML_HEADER)//
				.append(String.format("<%s src=\"%d\" tgt=\"%d\" weight=\"%s\"/>",//
						Connection.class.getSimpleName(),//
						t.getSourceNeuronIndex(),//
						t.getTargetNeuronIndex(),//
						Double.toString(t.getWeight())));
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
