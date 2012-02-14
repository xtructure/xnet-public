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
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.link.Link.Fragment;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlReader;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xart" })
public class UTestLinkImpl_XML extends AbstractXmlFormatTest<LinkImpl> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				new LinkImpl(XId.newId(), XId.newId(), XId.newId(),//
						LinkConfiguration.builder()//
								.setCapacity(Range.getInstance(1.0f), null)//
								.setCapacityAttack(Range.getInstance(2.0f), null)//
								.setCapacityDecay(Range.getInstance(3.0f), null)//
								.setStrength(Range.getInstance(4.0f), null)//
								.setStrengthAttack(Range.getInstance(5.0f), null)//
								.setStrengthDecay(Range.getInstance(6.0f), null)//
								.newInstance()));
	}

	protected UTestLinkImpl_XML() {
		super(LinkImpl.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(LinkImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<LinkImpl id=\"%s\" source=\"%s\" target=\"%s\" config=\"%s\">\n", t.getId(), t.getSourceId(), t.getTargetId(), t.getConfiguration().getId()));
		for (Fragment frag : Fragment.values()) {
			switch (frag) {
				case OUTPUT_ENERGY:
					break;
				default:
					sb.append(String.format(INDENT + "<%s value=\"%s\"/>\n", frag, frag.getValue(t)));
			}
		}
		sb.append("</LinkImpl>");
		return sb.toString();
	}

	public void newInstanceWithoutUnRegisteredConfigUsesDefaultConfig() {
		LinkConfiguration linkConfiguration = LinkConfiguration.builder().newInstance();
		LinkConfiguration.getManager().unregister(linkConfiguration);
		LinkImpl link = new LinkImpl(XId.newId(), XId.newId(), XId.newId(), linkConfiguration);
		assertThat("",//
				link.getConfiguration(), isSameAs(linkConfiguration));
		String xml = XmlWriter.write(link, getXmlBinding());
		link = XmlReader.read(xml, getXmlBinding(), null);
		assertThat("",//
				link.getConfiguration(), isSameAs(LinkConfiguration.DEFAULT_CONFIGURATION));
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
