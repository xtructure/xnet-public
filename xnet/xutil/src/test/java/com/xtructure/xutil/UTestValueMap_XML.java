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
package com.xtructure.xutil;


/**
 * @author Luis Guimbarda
 * 
 */
//@Test(groups = { "xml:xutil" })
public class UTestValueMap_XML {//extends AbstractXMLTest<ValueMap> {
//	private static final Object[][]	INSTANCES;
//	static {
//		ValueMap map = new ValueMap();
//		map.set(XValId.newId("intVal", Integer.class), 100);
//		map.set(XValId.newId("stringVal", String.class), "word");
//		map.set(XValId.newId("boolVal", Boolean.class), true);
//		INSTANCES = TestUtils.createData(//
//				new ValueMap(),//
//				map);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.xtructure.xutil.AbstractXMLTest#generateExpectedXMLString(java.lang
//	 * .Object)
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	protected String generateExpectedXMLString(ValueMap t) {
//		StringBuilder sb = new StringBuilder().append(XML_HEADER);
//		if (t.size() == 0) {
//			sb.append(String.format("<%s/>", rootName()));
//		} else {
//			sb.append(String.format("<%s>\n", rootName()));
//			List<XValId<?>> ids = new ArrayList<XValId<?>>(t.keySet());
//			Collections.sort(ids);
//			for (@SuppressWarnings("rawtypes") XValId id : ids) {
//				sb.append(String.format(INDENT + "<entry name=\"%s\" value=\"%s\"/>\n", id.toString(), t.get(id)));
//			}
//			sb.append(String.format("</%s>", rootName()));
//		}
//		return sb.toString();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.xtructure.xutil.AbstractXMLTest#getTestClass()
//	 */
//	@Override
//	protected Class<ValueMap> getTestClass() {
//		return ValueMap.class;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.xtructure.xutil.AbstractXMLTest#instances()
//	 */
//	@Override
//	@DataProvider(name = "instances")
//	protected Object[][] instances() {
//		return INSTANCES;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.xtructure.xutil.AbstractXMLTest#rootName()
//	 */
//	@Override
//	protected String rootName() {
//		return ValueMap.class.getName();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.xtructure.xutil.AbstractXMLTest#xmlBinding()
//	 */
//	@Override
//	protected XMLBinding xmlBinding() {
//		return new XMLBinding();
//	}
//
}
