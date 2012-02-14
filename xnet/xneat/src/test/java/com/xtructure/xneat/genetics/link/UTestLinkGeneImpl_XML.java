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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.xneat.genetics.link.config.LinkGeneConfiguration;
import com.xtructure.xneat.genetics.link.impl.LinkGeneImpl;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlWriter;

@Test(groups = { "xml:xneat" })
public class UTestLinkGeneImpl_XML extends AbstractXmlFormatTest<LinkGeneImpl> {
	private static final Object[][]	INSTANCES;
	static {
		XId srcId = XId.newId("node", 0);
		XId tgtId = XId.newId("node", 1);
		LinkGeneConfiguration config = LinkGeneConfiguration.builder(XId.newId("UTestLinkGeneImpl_XML", 0)).newInstance();
		LinkGeneImpl linkGene0 = new LinkGeneImpl(0, srcId, tgtId, config);
		linkGene0.setWeight(2.5);
		LinkGeneImpl linkGene1 = new LinkGeneImpl(1, srcId, tgtId, config);
		linkGene1.setWeight(1.5);
		linkGene1.setAttribute(XValId.newId("attr", Double.class), 2.0);
		INSTANCES = TestUtils.createData(linkGene0, linkGene1);
	}

	public UTestLinkGeneImpl_XML() {
		super(LinkGeneImpl.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(LinkGeneImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		if (t.getAttributes().size() > 0) {
			sb.append(String.format("<%s id=\"%s\" innovation=\"%s\" configurationId=\"%s\" sourceId=\"%s\" targetId=\"%s\" weight=\"%s\">\n", LinkGeneImpl.class.getSimpleName(), t.getId(), t.getInnovation(), t.getConfiguration().getId(), t.getSourceId(), t.getTargetId(), t.getWeight()));
			sb.append(INDENT + "<attributes>\n");
			String[] lines = XmlWriter.write(t.getAttributes()).split("\n");
			for (int i = 2; i < lines.length - 1; i++) {
				sb.append(INDENT + lines[i] + "\n");
			}
			sb.append(INDENT + "</attributes>\n");
			sb.append(String.format("</%s>", LinkGeneImpl.class.getSimpleName()));
		} else {
			sb.append(String.format("<%s id=\"%s\" innovation=\"%s\" configurationId=\"%s\" sourceId=\"%s\" targetId=\"%s\" weight=\"%s\"/>", LinkGeneImpl.class.getSimpleName(), t.getId(), t.getInnovation(), t.getConfiguration().getId(), t.getSourceId(), t.getTargetId(), t.getWeight()));
		}
		return sb.toString();
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
