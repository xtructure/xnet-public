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
package com.xtructure.art.model.node;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xtructure.art.model.node.Node.Energies;
import com.xtructure.art.model.node.Node.Fragment;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.test.AbstractXmlFormatTest;
import com.xtructure.xutil.test.TestUtils;
import com.xtructure.xutil.xml.XmlReader;
import com.xtructure.xutil.xml.XmlWriter;

@Test(groups = { "xml:xart" })
public class UTestNodeImpl_XML extends AbstractXmlFormatTest<NodeImpl> {
	private static final Object[][]	INSTANCES;
	static {
		INSTANCES = TestUtils.createData(//
				new NodeImpl(XId.newId(),//
						NodeConfiguration.builder()//
								.setDelay(Range.getInstance(1), null)//
								.setEnergy(Range.getInstance(2.0F), null)//
								.setEnergyDecay(Range.getInstance(3.0F), null)//
								.setExcitatoryScale(Range.getInstance(4.0F), null)//
								.setInhibitoryScale(Range.getInstance(5.0F), null)//
								.setOscillationMinimum(Range.getInstance(6.0F), null)//
								.setOscillationMaximum(Range.getInstance(7.0F), null)//
								.setOscillationOffset(Range.getInstance(8), null)//
								.setOscillationPeriod(Range.getInstance(9), null)//
								.setTwitchMinimum(Range.getInstance(10.0F), null)//
								.setTwitchMaximum(Range.getInstance(11.0F), null)//
								.setTwitchProbability(Range.getInstance(12.0F), null)//
								.setShift(Range.getInstance(13.0F), null)//
								.setScale(Range.getInstance(14.0F), null)//
								.setInvertFlag(Range.getInstance(false), null)//
								.newInstance()));
	}

	protected UTestNodeImpl_XML() {
		super(NodeImpl.XML_BINDING);
	}

	@Override
	protected String generateExpectedXMLString(NodeImpl t) {
		StringBuilder sb = new StringBuilder().append(XML_HEADER);
		sb.append(String.format("<NodeImpl id=\"%s\" config=\"%s\">\n", t.getId(), t.getConfiguration().getId()));
		for (Fragment frag : Fragment.values()) {
			switch (frag) {
				case ENERGY:
					break;
				case ENERGIES:
					sb.append(String.format(INDENT + "<%s frontEnergy=\"%s\" backEnergy=\"%s\"/>\n", frag,//
							((Energies) frag.getValue(t)).getFrontEnergy(), ((Energies) frag.getValue(t)).getBackEnergy()));
					break;
				default:
					sb.append(String.format(INDENT + "<%s value=\"%s\"/>\n", frag, frag.getValue(t)));
			}
		}
		sb.append("</NodeImpl>");
		return sb.toString();
	}

	public void newInstanceWithoutUnRegisteredConfigUsesDefaultConfig() {
		NodeConfiguration nodeConfiguration = NodeConfiguration.builder().newInstance();
		NodeConfiguration.getManager().unregister(nodeConfiguration);
		NodeImpl node = new NodeImpl(XId.newId(), nodeConfiguration);
		assertThat("",//
				node.getConfiguration(), isSameAs(nodeConfiguration));
		String xml = XmlWriter.write(node, getXmlBinding());
		node = XmlReader.read(xml, getXmlBinding(), null);
		assertThat("",//
				node.getConfiguration(), isSameAs(NodeConfiguration.DEFAULT_CONFIGURATION));
	}

	@Override
	@DataProvider
	protected Object[][] instances() {
		return INSTANCES;
	}
}
