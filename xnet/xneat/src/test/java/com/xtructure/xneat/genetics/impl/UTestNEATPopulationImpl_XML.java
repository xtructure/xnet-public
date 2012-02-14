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
package com.xtructure.xneat.genetics.impl;

import org.testng.annotations.Test;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "xml:xneat" })
public class UTestNEATPopulationImpl_XML {//extends AbstractXMLTest<NEATPopulationImpl> {
//	private static final Object[][]	INSTANCES;
//	static {
////		NEATSpeciesImpl species = new NEATSpeciesImpl(0);
//		NEATGeneticsFactoryImpl geneticsFactory = new NEATGeneticsFactoryImpl(NEATEvolutionConfigurationImpl.DEFAULT_CONFIGURATION.newFieldMap());
//		for (int i = 0; i < 5; i++) {
//			species.add(geneticsFactory.createGenome(i));
//		}
//		species.setEliteSize(1);
//		species.setTargetSize(2);
//		NEATPopulationImpl population = new NEATPopulationImpl(0);
//		population.addSpecies(species);
//		for (int i = 5; i < 10; i++) {
//			population.add(geneticsFactory.createGenome(i));
//		}
//		population.setAttribute(NEATPopulation.AGE_ATTRIBUTE_ID, 1l);
//		population.setAttribute(NEATPopulation.AGE_LI_ATTRIBUTE_ID, 1l);
//		INSTANCES = TestUtils.createData(population);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.xtructure.xutil.AbstractXMLTest#generateExpectedXMLString(java.lang
//	 * .Object)
//	 */
//	@Override
//	protected String generateExpectedXMLString(NEATPopulationImpl t) {
//		StringBuilder sb = new StringBuilder().append(XML_HEADER);
//		sb.append(String.format("<%s id=\"%s\">\n", rootName(), t.getId()));
//
//		sb.append(INDENT + "<attributes>\n");
//		{
//			try {
//				StringWriter out = new StringWriter();
//				XmlWriter.write(out, t.getAttributes(), xmlBinding());
//				String[] lines = out.toString().split("\n");
//				for (int i = 2; i + 1 < lines.length; i++) {
//					sb.append(INDENT + lines[i]).append("\n");
//				}
//			} catch (XMLStreamException e) {
//				e.printStackTrace();
//			}
//		}
//		sb.append(INDENT + "</attributes>\n");
//
//		List<NEATSpecies<?>> species = new ArrayList<NEATSpecies<?>>(t.getAllSpecies());
//		Collections.sort(species, XIdObject.BY_XID);
//		List<Genome<?>> looseGenomes = new ArrayList<Genome<?>>(t);
//		for (NEATSpecies<?> specie : species) {
//			looseGenomes.removeAll(specie);
//			{
//				try {
//					StringWriter out = new StringWriter();
//					XmlWriter.write(out, specie, xmlBinding());
//					String[] lines = out.toString().split("\n");
//					for (int i = 1; i < lines.length; i++) {
//						sb.append(INDENT + lines[i]).append("\n");
//					}
//				} catch (XMLStreamException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		{
//			try {
//				Collections.sort(looseGenomes, XIdObject.BY_XID);
//				StringWriter out = new StringWriter();
//				XmlWriter.write(out, looseGenomes, xmlBinding());
//				String[] lines = out.toString().split("\n");
//				for (int i = 2; i + 1 < lines.length; i++) {
//					sb.append(lines[i]).append("\n");
//				}
//			} catch (XMLStreamException e) {
//				e.printStackTrace();
//			}
//		}
//		sb.append(String.format("</%s>", rootName()));
//		return sb.toString();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.xtructure.xutil.AbstractXMLTest#getTestClass()
//	 */
//	@Override
//	protected Class<NEATPopulationImpl> getTestClass() {
//		return NEATPopulationImpl.class;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.xtructure.xutil.AbstractXMLTest#instances()
//	 */
//	@DataProvider(name = "instances")
//	@Override
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
//		return NEATPopulationImpl.class.getSimpleName();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.xtructure.xutil.AbstractXMLTest#xmlBinding()
//	 */
//	@Override
//	protected XMLBinding xmlBinding() {
//		return NEATPopulationImpl.XML_BINDING;
//	}
}
