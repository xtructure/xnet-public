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
package com.xtructure.xevolution.tool.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

import org.json.simple.JSONAware;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.tool.DataXIdObject;
import com.xtructure.xevolution.tool.DataXIdObject.DataXIdObjectFactory;
import com.xtructure.xevolution.tool.GenealogyData;
import com.xtructure.xevolution.tool.GenealogyData.GenealogyDataFactory;
import com.xtructure.xevolution.tool.PopulationData;
import com.xtructure.xevolution.tool.PopulationData.PopulationDataFactory;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.id.XIdObjectManagerImpl;
import com.xtructure.xutil.xml.XmlReader;

/**
 * The Class DataTracker.
 *
 * @param <P> the generic type
 * @param <G> the generic type
 * @author Luis Guimbarda
 */
public final class DataTracker<P extends PopulationData<P>, G extends GenealogyData<G>> {
	
	/** parser for reading json data files. */
	private static final JSONParser	JSON_PARSER			= new JSONParser();
	
	/** name of file to write population data. */
	public static final String		POPULATION_FILENAME	= "population.dat";
	
	/** name of file to write genealogy data. */
	public static final String		GENEALOGY_FILENAME	= "genealogy.dat";

	/**
	 * Returns a new {@link DataTracker}.
	 * 
	 * @return a new {@link DataTracker}.
	 */
	public static DataTracker<PopulationDataImpl, GenealogyDataImpl> newInstance() {
		return new DataTracker<PopulationDataImpl, GenealogyDataImpl>(PopulationDataImpl.FACTORY, GenealogyDataImpl.FACTORY);
	}

	/**
	 * Returns a new {@link DataTracker} instance with the given data factories.
	 *
	 * @param <P> the generic type
	 * @param <G> the generic type
	 * @param populationDataFactory data factory for population data
	 * @param genealogyDataFactory data factory for genealogy data
	 * @return a new {@link DataTracker} instance with the given data factories.
	 */
	public static <P extends PopulationData<P>, G extends GenealogyData<G>> DataTracker<P, G> newInstance(//
			PopulationDataFactory<P> populationDataFactory,//
			GenealogyDataFactory<G> genealogyDataFactory) {
		return new DataTracker<P, G>(populationDataFactory, genealogyDataFactory);
	}

	/** manager for population data. */
	private final XIdObjectManager<P>		populationDataManager	= new XIdObjectManagerImpl<P>(new TreeMap<XId, P>());
	
	/** manager for genealogy data. */
	private final XIdObjectManager<G>		genealogyDataManager	= new XIdObjectManagerImpl<G>(new TreeMap<XId, G>());
	
	/** data factory for population data. */
	private final PopulationDataFactory<P>	populationDataFactory;
	
	/** data factory for genealogy data. */
	private final GenealogyDataFactory<G>	genealogyDataFactory;

	/**
	 * Creates a new {@link DataTracker} with the given data factories.
	 *
	 * @param populationDataFactory the population data factory
	 * @param genealogyDataFactory the genealogy data factory
	 */
	private DataTracker(PopulationDataFactory<P> populationDataFactory, GenealogyDataFactory<G> genealogyDataFactory) {
		this.populationDataFactory = populationDataFactory;
		this.genealogyDataFactory = genealogyDataFactory;
	}

	/**
	 * Returns the manager for population data in this data tracker.
	 *
	 * @return the population data manager
	 */
	public XIdObjectManager<P> getPopulationDataManager() {
		return populationDataManager;
	}

	/**
	 * Returns the manager for genealogy data in this data tracker.
	 *
	 * @return the genealogy data manager
	 */
	public XIdObjectManager<G> getGenealogyDataManager() {
		return genealogyDataManager;
	}

	/**
	 * Returns the population data factory for this {@link DataTracker}.
	 *
	 * @return the population data factory for this {@link DataTracker}
	 */
	public PopulationDataFactory<P> getPopulationDataFactory() {
		return populationDataFactory;
	}

	/**
	 * Returns the genealogy data factory for this {@link DataTracker}.
	 *
	 * @return the genealogy data factory for this {@link DataTracker}
	 */
	public GenealogyDataFactory<G> getGenealogyDataFactory() {
		return genealogyDataFactory;
	}

	/**
	 * Reads the population in the given populationFile and adds its data to
	 * this {@link DataTracker}.
	 *
	 * @param populationFile the population file
	 * @return the read population, or null if the populationFile could not be
	 * read
	 */
	public Population<?> processPopulation(File populationFile) {
		Population<?> population = XmlReader.read(populationFile, (Population<?>) null);
		if (population != null) {
			population.refreshStats();
			PopulationData<?> popData = populationDataFactory.getOrCreateInstance(population, populationDataManager);
			popData.put(PopulationData.FILENAME, populationFile.toString());
			for (Genome<?> genome : population) {
				genealogyDataFactory.getOrCreateInstance(genome, populationFile, genealogyDataManager);
			}
		}
		return population;
	}

	/**
	 * Loads the {@link DataXIdObject}s from files in the given directory and
	 * adds them to this {@link DataTracker}.
	 *
	 * @param directory the directory
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public void load(File directory) throws IOException, ParseException {
		load(new File(directory, POPULATION_FILENAME), populationDataManager, populationDataFactory);
		load(new File(directory, GENEALOGY_FILENAME), genealogyDataManager, genealogyDataFactory);
	}

	/**
	 * Loads the {@link DataXIdObject}s from the given file and registers them
	 * to the given manager.
	 *
	 * @param <D> the generic type
	 * @param file the file from which {@link DataXIdObject}s are read
	 * @param manager the manager to which the read {@link DataXIdObject} are
	 * registered
	 * @param factory creates the appropriate {@link DataXIdObject} instances
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	protected <D extends DataXIdObject<D>> void load(File file, XIdObjectManager<D> manager, DataXIdObjectFactory<D> factory) throws IOException, ParseException {
		if (file.exists()) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = in.readLine()) != null) {
				factory.createInstance((JSONAware) JSON_PARSER.parse(line), manager);
			}
		}
	}

	/**
	 * Write the data collected by this {@link DataTracker} to files in the
	 * given directory. Data will be appended to existing files.
	 *
	 * @param directory the directory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void write(File directory) throws IOException {
		write(new File(directory, POPULATION_FILENAME), populationDataManager);
		write(new File(directory, GENEALOGY_FILENAME), genealogyDataManager);
	}

	/**
	 * Write the data collected in the given manager to the given file. Data
	 * will be appended to the file, if it already exists.
	 *
	 * @param <D> the generic type
	 * @param file the file
	 * @param manager the manager
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected <D extends DataXIdObject<D>> void write(File file, XIdObjectManager<D> manager) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, true));
			for (XId id : manager.getIds()) {
				D data = manager.getObject(id);
				if (!data.isWritten()) {
					out.write(data.toJSON().toJSONString() + "\n");
					data.setWritten(true);
				}
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
