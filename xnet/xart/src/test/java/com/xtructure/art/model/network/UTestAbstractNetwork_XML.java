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
package com.xtructure.art.model.network;

import java.util.Collections;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.link.LinkImpl;
import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.art.model.node.NodeImpl;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

@Test(groups = { "xml:xart" })
public class UTestAbstractNetwork_XML extends AbstractXmlFormatTest<DummyNetwork> {
	private static final Object[][]	INSTANCES;
	static {
		Set<Node> emptyNodes = Collections.emptySet();
		Set<Link> emptyLinks = Collections.emptySet();
		INSTANCES = TestUtils.createData(//
				new DummyNetwork(XId.newId(), emptyNodes, emptyLinks),//
				new DummyNetwork(XId.newId(),//
						Collections.singleton(new NodeImpl(XId.newId("N"), NodeConfiguration.DEFAULT_CONFIGURATION)),//
						Collections.singleton(new LinkImpl(XId.newId(), XId.newId("N"), XId.newId("N"), LinkConfiguration.DEFAULT_CONFIGURATION))));
	}

	protected UTestAbstractNetwork_XML() {
		super(DummyNetwork.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(DummyNetwork t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		if (t.getLinkIds().isEmpty() && t.getNodeIds().isEmpty()) {
			sb.append(String.format("<DummyNetwork id=\"%s\"/>", t.getId()));
		} else {
			sb.append(String.format("<DummyNetwork id=\"%s\">\n", t.getId()));
			for (XId nodeId : t.getNodeIds()) {
				Wrapper wrapper = new Wrapper(t.getData(nodeId));
				String[] lines = XmlWriter.write(wrapper, getXmlBinding()).split("\n");
				sb.append(Wrapper.removeClassAttribute(Wrapper.replaceHook(lines[2], "node"))).append("\n");
				for (int i = 3; i < lines.length - 1; i++) {
					sb.append(Wrapper.replaceHook(lines[i], "node")).append("\n");
				}
			}
			for (XId linkId : t.getLinkIds()) {
				Wrapper wrapper = new Wrapper(t.getData(linkId));
				String[] lines = XmlWriter.write(wrapper, getXmlBinding()).split("\n");
				sb.append(Wrapper.removeClassAttribute(Wrapper.replaceHook(lines[2], "link"))).append("\n");
				for (int i = 3; i < lines.length - 1; i++) {
					sb.append(Wrapper.replaceHook(lines[i], "link")).append("\n");
				}
			}
			sb.append("</DummyNetwork>");
		}
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
