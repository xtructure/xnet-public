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
package com.xtructure.xevolution.tool.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.HelpFormatter;

import com.xtructure.xevolution.evolution.EvolutionExperiment;
import com.xtructure.xevolution.tool.XEvoTool;
import com.xtructure.xevolution.tool.data.DataTracker;
import com.xtructure.xutil.opt.BooleanXOption;
import com.xtructure.xutil.opt.FileXOption;
import com.xtructure.xutil.opt.XOption;

/**
 * ReadPopulationsTool is an XEvoTool which reads the population xml files
 * dumped by an {@link EvolutionExperiment} and uses a given {@link DataTracker}
 * to extract the desired statistics for analysis. The data read by the
 * {@link DataTracker} is written to file after processing each population file.
 * <P>
 * The tool accepts the following command line arguments:
 * <dl>
 * <dt> <code>-d --data</code>
 * <dd>(required) The directory to which data files are written. If the
 * directory doesn't exist, it will be created.
 * <dt> <code>-p --populations</code>
 * <dd>(required) The directory containing the population xml files to read.
 * <dt> <code>-r --resume</code>
 * <dd>Keep any data written so far and append new data. Population files that
 * have been read will be skipped. By default, data in the data directory is
 * deleted and all populations are read.
 * <dt> <code>-c --continuous</code>
 * <dd>Continuously read new population files as they are written. Instead of
 * exiting after the last population file is read, the population directory is
 * polled every 2 seconds for new population files.
 * <dt><code>-v --verbose</code>
 * <dd>Print trace messages to standard out.
 * <dt><code>-h --help</code>
 * <dd>Print usage.
 * </dl>
 * 
 * @author Luis Guimbarda
 * 
 */
public class ReadPopulationsTool extends XEvoTool {
	/** file containing the latest read population file's last modified date */
	public static final File			LATEST					= new File("latest.dat");
	/** number of times to try and read population xml files before giving up */
	private static final int			RETRIES					= 5;
	/** {@link Lock} for accessing population files */
	private static final ReentrantLock	popLock					= new ReentrantLock();
	/** {@link Pattern} for population xml files */
	private static final Pattern		POPULATION_FILE_PATTERN	= Pattern.compile("^\\D*(\\d+)\\.xml$");
	/** name of the Population Directory option */
	public static final String			POPULATION_DIRECTORY	= "Population Directory";
	/** name of the Data Directory option */
	public static final String			DATA_DIRECTORY			= "Data Directory";
	/** name of the Continuous Read option */
	public static final String			CONTINUOUS_READ			= "Continuous Read";
	/** name of the Rebuild option */
	public static final String			RESUME					= "Resume";
	/** name of the Verbose option */
	public static final String			VERBOSE					= "Verbose";
	/** name of the Help option */
	public static final String			HELP					= "Help";

	private static long readLatest() {
		if (LATEST.exists()) {
			try {
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(LATEST));
					String line = in.readLine();
					if (line != null) {
						return Long.parseLong(line);
					}
				} finally {
					if (in != null) {
						in.close();
					}
				}
			} catch (FileNotFoundException e) {
				// nothing
			} catch (IOException e) {
				// nothing
			} catch (NumberFormatException e) {
				// nothing
			}
		}
		return 0;
	}

	private static void writeLatest(long latest) {
		try {
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new FileWriter(LATEST, false));
				out.write(Long.toString(latest));
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (IOException e) {
			// nothing
		}
	}

	/** worker thread from reading populations */
	private Thread					worker			= null;
	/** used to track data from read populations */
	private final DataTracker<?, ?>	dataTracker;
	/**
	 * indicates whether this tool should poll population directory for new
	 * population files
	 */
	private boolean					continuousRead	= false;
	/** indicates whether this tool should output progress */
	private boolean					verbose			= false;
	/** directory in which to look for population files */
	private File					populationDir	= null;
	/** directory in which data is to be written */
	private File					dataDir			= null;

	/**
	 * Creates a new {@link ReadPopulationsTool}.
	 * 
	 * @param dataTracker
	 */
	public ReadPopulationsTool(DataTracker<?, ?> dataTracker) {
		super("readPopulations",//
				new BooleanXOption(HELP, "h", "help", "print usage"),//
				new BooleanXOption(CONTINUOUS_READ, "c", "continuous", "continue reading files"),//
				new BooleanXOption(VERBOSE, "v", "verbose", "print progess"),//
				new BooleanXOption(RESUME, "r", "resume", "resume from previously built data (otherwise, all files in data directory will be erased)"),//
				new FileXOption(POPULATION_DIRECTORY, "p", "populations", "directory containing population files"),//
				new FileXOption(DATA_DIRECTORY, "d", "data", "directory to which data is written"));
		this.dataTracker = dataTracker;
		this.worker = new Thread(new Runnable() {
			private long	latest;

			@Override
			public void run() {
				latest = readLatest();
				try {
					// load existing data
					if (verbose) {
						System.out.println("Loading data...");
					}
					ReadPopulationsTool.this.dataTracker.load(dataDir);
					if (verbose) {
						System.out.println("\tdone.");
					}
					while (true) {
						// collect population files
						File[] newFiles = populationDir.listFiles(new FileFilter() {
							@Override
							public boolean accept(File pathname) {
								return pathname.lastModified() > latest && pathname.getName().endsWith(".xml");
							}
						});
						if (newFiles.length == 0) {
							if (verbose) {
								System.out.println("no new files, waiting...");
							}
							try {
								// polling for now...
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
								break;
							}
							if (continuousRead) {
								continue;
							} else {
								break;
							}
						}
						Arrays.sort(newFiles, new Comparator<File>() {
							@Override
							public int compare(File o1, File o2) {
								Matcher matcher = POPULATION_FILE_PATTERN.matcher(o1.getName());
								if (matcher.matches()) {
									int age1 = Integer.parseInt(matcher.group(1));
									matcher = POPULATION_FILE_PATTERN.matcher(o2.getName());
									if (matcher.matches()) {
										int age2 = Integer.parseInt(matcher.group(1));
										return age1 - age2;
									}
								}
								return o1.compareTo(o2);
							}
						});
						// process population files
						for (File populationFile : newFiles) {
							latest = Math.max(latest, populationFile.lastModified());
							if (verbose) {
								System.out.println("Processing population : " + populationFile.getName());
							}
							for (int j = 0; j < RETRIES; j++) {
								popLock.lock();
								try {
									if (ReadPopulationsTool.this.dataTracker.processPopulation(populationFile) != null) {
										// success
										break;
									}
								} finally {
									popLock.unlock();
								}
								// failed to read population, wait and try again
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							try {
								ReadPopulationsTool.this.dataTracker.write(dataDir);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						writeLatest(latest);
						if (!continuousRead) {
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (org.json.simple.parser.ParseException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.tool.XEvoTool#launch(java.lang.String[])
	 */
	@Override
	public void launch(String[] args) {
		try {
			XOption.parseArgs(getOptions(), args);
		} catch (org.apache.commons.cli.ParseException e) {
			e.printStackTrace();
			new HelpFormatter().printHelp(getClass().getSimpleName(), getOptions(), true);
			return;
		}
		// process help option
		if ((Boolean) getOption(HELP).processValue()) {
			new HelpFormatter().printHelp(getClass().getSimpleName(), getOptions(), true);
			return;
		}
		// get options
		FileXOption popDirOption = getOption(POPULATION_DIRECTORY);
		FileXOption dataDirOption = getOption(DATA_DIRECTORY);
		BooleanXOption continueOption = getOption(CONTINUOUS_READ);
		BooleanXOption verboseOption = getOption(VERBOSE);
		// set options
		continuousRead = continueOption.processValue();
		verbose = verboseOption.processValue();
		if (popDirOption.hasValue()) {
			populationDir = popDirOption.processValue();
		} else {
			populationDir = null;
		}
		if (dataDirOption.hasValue()) {
			dataDir = dataDirOption.processValue();
		} else {
			dataDir = null;
		}
		// check for required args
		if (populationDir == null || dataDir == null) {
			System.out.printf("%s and %s are required\n", POPULATION_DIRECTORY, DATA_DIRECTORY);
			System.out.printf("%s : %s\n", POPULATION_DIRECTORY, populationDir);
			System.out.printf("%s : %s\n", DATA_DIRECTORY, dataDir);
			new HelpFormatter().printHelp(getClass().getSimpleName(), getOptions(), true);
			return;
		}
		dataDir.mkdirs();
		// process rebuild option
		if (!(Boolean) getOption(RESUME).processValue()) {
			if (verbose) {
				System.out.println("Deleting old data... ");
			}
			if (LATEST.delete() && verbose) {
				System.out.printf("\t...%s deleted...\n", LATEST);
			}
			if (dataDir.exists()) {
				for (File file : dataDir.listFiles()) {
					if (file.delete() && verbose) {
						System.out.printf("\t...%s deleted...\n", file);
					}
				}
			}
			if (verbose) {
				System.out.println("\tdone.");
			}
		}
		// run tool
		if (verbose) {
			System.out.println("Start reading population files.");
		}
		try {
			// for console tool, using a thread is unnecessary, but may be
			// useful when integrating with gui tools
			worker.start();
			worker.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (verbose) {
			System.out.println("Finished reading population files.");
		}
	}
}
