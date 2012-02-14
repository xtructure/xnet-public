/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xevolution.
 *
 * xevolution is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xevolution is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xevolution.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xevolution.genetics.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.UUID;

import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.xml.XmlReader;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestPopulationImpl {
	public void constructorSucceeds() {
		assertThat("",//
				new PopulationImpl(0), isNotNull());
	}

	public void writeAndGetInstanceBehaveAsExpected() throws IOException, XMLStreamException {
		PopulationImpl population = new PopulationImpl(0);
		GenomeImpl genome = new GenomeImpl(0, "");
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		population.add(genome);
		population.refreshStats();

		StringWriter out = new StringWriter();
		XMLObjectWriter writer = XMLObjectWriter.newInstance(out).setIndentation("\t");
		writer.write(population);
		writer.close();
		String expected = out.toString();

		out = new StringWriter();
		XmlWriter.write(out, population);
		out.close();

		assertThat("",//
				out.toString(), isEqualTo(expected));

		StringReader in = new StringReader(expected);
		PopulationImpl pop = XmlReader.read(in);
		in.close();

		expected = population.toString().substring(population.toString().indexOf("\n") + 1);
		String got = pop.toString().substring(pop.toString().indexOf("\n") + 1);
		assertThat("",//
				got, isNotEqualTo(expected));
		pop.refreshStats();
		got = pop.toString().substring(pop.toString().indexOf("\n") + 1);
		assertThat("",//
				got, isEqualTo(expected));

		String filename = String.format("Population%d.xml", population.getAge());
		File outputDir = new File(".");
		File populationFile = new File(outputDir, filename);
		try {
			assertThat("",//
					populationFile.exists(), isFalse());
			population.write(outputDir);
			assertThat("",//
					populationFile.exists(), isTrue());
			pop = XmlReader.read(populationFile);
			pop.refreshStats();
			got = pop.toString().substring(pop.toString().indexOf("\n") + 1);
			assertThat("",//
					got, isEqualTo(expected));
		} finally {
			populationFile.delete();
		}
	}

	@Test(expectedExceptions = { XMLStreamException.class })
	public void getInstanceThrowExceptionOnBadXMLFile() throws IOException, XMLStreamException {
		File file = new File(UUID.randomUUID().toString());
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write("gobbledygook");
		bw.close();
		try {
			XmlReader.read(file);
		} finally {
			file.delete();
		}
	}

	@Test(expectedExceptions = { XMLStreamException.class })
	public void getInstanceThrowsExceptionOnBadReader() throws XMLStreamException {
		XmlReader.read(new Reader() {
			@Override
			public int read(char[] cbuf, int off, int len) throws IOException {
				throw new IOException("read");
			}

			@Override
			public void close() throws IOException {}
		});
	}

	// @Test(expectedExceptions = { XMLStreamException.class })
	// public void writeThrowsExceptionBadWriter() throws XMLStreamException {
	// new PopulationImpl(0).write(new Writer() {
	// @Override
	// public void write(char[] cbuf, int off, int len) throws IOException {
	// throw new IOException("write()");
	// }
	//
	// @Override
	// public void flush() throws IOException {}
	//
	// @Override
	// public void close() throws IOException {}
	// });
	// }
}
