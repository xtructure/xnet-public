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
package com.xtructure.xevolution.evolution.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import javolution.xml.stream.XMLStreamException;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.BasicConfigurator;

import com.xtructure.xevolution.config.EvolutionConfiguration;
import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.EvolutionExperiment;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.WormField;
import com.xtructure.xutil.opt.BooleanXOption;
import com.xtructure.xutil.opt.FileXOption;
import com.xtructure.xutil.opt.XOption;
import com.xtructure.xutil.xml.XmlReader;

/**
 * @author Luis Guimbarda
 * 
 * @param <D>
 * @param <T>
 */
public abstract class AbstractEvolutionExperiment<D, T> implements EvolutionExperiment<D, T> {
	protected static final String	PARAMETERS_FILE_OPTION	= "parametersFile";
	protected static final String	OUTPUT_DIR_OPTION		= "outputDir";
	protected static final String	HELP_OPTION				= "help";
	private final Options			options;

	public AbstractEvolutionExperiment(Collection<? extends XOption<?>> options) {
		this.options = new Options();
		for (XOption<?> option : options) {
			this.options.addOption(option);
		}
		this.options.addOption(new FileXOption(PARAMETERS_FILE_OPTION, "p", "parametersFile", "xml file containing the evolution parameters"));
		this.options.addOption(new FileXOption(OUTPUT_DIR_OPTION, "o", "outputDir", "directory to which output is written"));
		this.options.addOption(new BooleanXOption(HELP_OPTION, "h", "help", "prints usage"));
		BasicConfigurator.configure();
	}

	@Override
	public XOption<?> getOption(String name) {
		return XOption.getOption(name, options);
	}

	@Override
	public void setArgs(String[] args) {
		try {
			XOption.parseArgs(options, args);
			if (getOption(HELP_OPTION).hasValue()) {
				new HelpFormatter().printHelp(getClass().getSimpleName(), options, true);
				System.exit(0);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			new HelpFormatter().printHelp(getClass().getSimpleName(), options, true);
			System.exit(1);
		}
	}

	@Override
	public void startExperiment() {
		try {
			getEvolutionStrategy().start(getPopulation());
		} catch (Exception e) {
			e.printStackTrace();
			File out = getOutputDir();
			if (out == null) {
				out = new File(".");
			}
			File file = new File(//
					out,//
					String.format(//
							"%s-%s.err",//
							getClass().getSimpleName(),//
							System.currentTimeMillis()));
			try {
				file.createNewFile();
				e.printStackTrace(new PrintStream(new FileOutputStream(file)));
			} catch (Exception e1) {
				e.printStackTrace();
			}
			System.exit(2);
		}
	}

	protected EvolutionFieldMap createEvolutionFieldMap() {
		File configurationFile = (File) (getOption(PARAMETERS_FILE_OPTION).hasValue() ? getOption(PARAMETERS_FILE_OPTION).processValue() : null);
		EvolutionConfiguration config = null;
		if (configurationFile != null) {
			config = XmlReader.read(configurationFile, EvolutionConfigurationImpl.XML_BINDING, EvolutionConfigurationImpl.DEFAULT_CONFIGURATION);
		} else {
			config = EvolutionConfigurationImpl.DEFAULT_CONFIGURATION;
		}
		return config.newFieldMap();
	}

	protected File createOutputDir() {
		File outputDir = (File) (getOption(OUTPUT_DIR_OPTION).hasValue() ? getOption(OUTPUT_DIR_OPTION).processValue() : null);
		if (outputDir != null) {
			outputDir.mkdirs();
		}
		return outputDir;
	}

	protected abstract GenomeDecoder<D, T> createGenomeDecoder();

	protected abstract GeneticsFactory<D> createGeneticsFactory();

	protected abstract ReproductionStrategy<D> createReproductionStrategy();

	protected abstract EvaluationStrategy<D, T> createEvaluationStrategy();

	protected abstract SurvivalFilter createSurvivalFilter();

	protected abstract EvolutionStrategy<D, T> createEvolutionStrategy();

	protected Population<D> createPopulation(int idNumber) throws IOException, XMLStreamException {
		if (getOutputDir() == null) {
			return getGeneticsFactory().createPopulation(idNumber);
		}
		File populationFile = null;
		long mod = Long.MIN_VALUE;
		for (File file : getOutputDir().listFiles()) {
			if (file.lastModified() > mod && file.getName().endsWith(".xml")) {
				mod = file.lastModified();
				populationFile = file;
			}
		}
		if (populationFile == null) {
			return getGeneticsFactory().createPopulation(idNumber);
		}
		return XmlReader.read(populationFile);
	}

	private final WormField<EvaluationStrategy<D, T>>	evaluationStrategy		= new WormField<EvaluationStrategy<D, T>>();
	private final WormField<EvolutionFieldMap>			evolutionFieldMap		= new WormField<EvolutionFieldMap>();
	private final WormField<EvolutionStrategy<D, T>>	evolutionStrategy		= new WormField<EvolutionStrategy<D, T>>();
	private final WormField<GenomeDecoder<D, T>>		genomeDecoder			= new WormField<GenomeDecoder<D, T>>();
	private final WormField<GeneticsFactory<D>>			geneticsFactory			= new WormField<GeneticsFactory<D>>();
	private final WormField<File>						outputDir				= new WormField<File>();
	private final WormField<ReproductionStrategy<D>>	reproductionStrategy	= new WormField<ReproductionStrategy<D>>();
	private final WormField<SurvivalFilter>				survivalFilter			= new WormField<SurvivalFilter>();
	private final WormField<Population<D>>				population				= new WormField<Population<D>>();

	@Override
	public EvaluationStrategy<D, T> getEvaluationStrategy() {
		if (!evaluationStrategy.isInitialized()) {
			evaluationStrategy.initValue(createEvaluationStrategy());
		}
		return evaluationStrategy.getValue();
	}

	@Override
	public EvolutionFieldMap getEvolutionFieldMap() {
		if (!evolutionFieldMap.isInitialized()) {
			evolutionFieldMap.initValue(createEvolutionFieldMap());
		}
		return evolutionFieldMap.getValue();
	}

	@Override
	public EvolutionStrategy<D, T> getEvolutionStrategy() {
		if (!evolutionStrategy.isInitialized()) {
			evolutionStrategy.initValue(createEvolutionStrategy());
		}
		return evolutionStrategy.getValue();
	}

	@Override
	public GenomeDecoder<D, T> getGenomeDecoder() {
		if (!genomeDecoder.isInitialized()) {
			genomeDecoder.initValue(createGenomeDecoder());
		}
		return genomeDecoder.getValue();
	}

	@Override
	public GeneticsFactory<D> getGeneticsFactory() {
		if (!geneticsFactory.isInitialized()) {
			geneticsFactory.initValue(createGeneticsFactory());
		}
		return geneticsFactory.getValue();
	}

	@Override
	public File getOutputDir() {
		if (!outputDir.isInitialized()) {
			outputDir.initValue(createOutputDir());
		}
		return outputDir.getValue();
	}

	@Override
	public ReproductionStrategy<D> getReproductionStrategy() {
		if (!reproductionStrategy.isInitialized()) {
			reproductionStrategy.initValue(createReproductionStrategy());
		}
		return reproductionStrategy.getValue();
	}

	@Override
	public SurvivalFilter getSurvivalFilter() {
		if (!survivalFilter.isInitialized()) {
			survivalFilter.initValue(createSurvivalFilter());
		}
		return survivalFilter.getValue();
	}

	@Override
	public Population<D> getPopulation() {
		if (!population.isInitialized()) {
			try {
				population.initValue(createPopulation(0));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
		return population.getValue();
	}
}
