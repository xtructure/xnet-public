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
package com.xtructure.xevolution.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.json.simple.JSONArray;

import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.gui.components.GenealogyPanel;
import com.xtructure.xevolution.gui.components.GenomePanel;
import com.xtructure.xevolution.gui.components.Graph;
import com.xtructure.xevolution.gui.components.GuiListener;
import com.xtructure.xevolution.gui.components.MenuBar;
import com.xtructure.xevolution.gui.components.PopulationPanel;
import com.xtructure.xevolution.gui.components.StatusBar;
import com.xtructure.xevolution.tool.PopulationData;
import com.xtructure.xevolution.tool.data.DataTracker;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.opt.FileXOption;
import com.xtructure.xutil.opt.XOption;

/**
 * The Class XEvolutionGui.
 * <P>
 * {@link XEvolutionGui} provides a gui interface for general xevolution
 * experiments. It relies on {@link Population} xml files output while running
 * {@link EvolutionStrategy#start(Population)} an experiment, and continuously
 * loads population files as they are produced.
 * <P>
 * {@link XEvolutionGui} provides a tabbed environment to view the progress of
 * an experiment. The first tab (called "Graphs") plots the fitness, complexity,
 * etc. of genomes in the population versus the age of the population. Each
 * graph shows the highest, lowest, and average value of each measure. The next
 * tab (called "Generations") lists the populations loaded so far. Selecting one
 * will show its attributes and then list its genomes, which may then be sorted
 * by any of thier attributes.
 * 
 * @author Luis Guimbarda
 */
public class XEvolutionGui {

	/** The Constant popLock. {@link Lock} for accessing population files */
	private static final ReentrantLock popLock = new ReentrantLock();

	/**
	 * The Constant POPULATION_FILE_PATTERN. {@link Pattern} for population xml
	 * files
	 */
	private static final Pattern POPULATION_FILE_PATTERN = Pattern
			.compile("^\\D*(\\d+)\\.xml$");

	/** number of times to try and read population xml files before giving up. */
	private static final int RETRIES = 5;

	/** list of {@link XOption} needed when loading an experiment. */
	public static final List<XOption<?>> OPTIONS;

	/** The Constant OUTPUT_OPTION. {@link FileXOption} for the output directory */
	public static final FileXOption OUTPUT_OPTION;
	static {
		OUTPUT_OPTION = new FileXOption("output directory", "o", "outputDir",
				"directory to which population xml files are output");
		OPTIONS = new ListBuilder<XOption<?>>()//
				.add(OUTPUT_OPTION)//
				.newImmutableInstance();
	}

	/** index of the graphs tab. */
	public static final int GRAPHS_INDEX = 0;

	/** index of the generations tab. */
	public static final int GENERATIONS_INDEX = 1;

	/** index of the genealogy tab. */
	public static final int GENEALOGY_INDEX = 2;

	/** the base string for the title of the gui. */
	private final String title;

	/** the {@link JFrame} of the gui. */
	private final JFrame frame;

	/** the list of observed population files. */
	private final List<File> populationFiles;

	/** the {@link StatusBar} of the gui. */
	private final StatusBar statusBar;

	/** the {@link MenuBar} of the gui. */
	private final MenuBar menuBar;

	/** the {@link JTabbedPane} of the gui. */
	private final JTabbedPane tabbedPane;

	/** the {@link JPanel} for the gui's {@link Graph}s. */
	private final JPanel graphPanel;

	/** the number of data points to display in the gui's {@link Graph}s. */
	private final int bufferSize;

	/** The buffer count. */
	private final int bufferCount;

	/** map of attribute {@link XValId}s to their {@link Graph}s. */
	private final Map<XValId<?>, Graph> graphsMap;

	/** the {@link JPanel} for the population and genome lists. */
	private final JPanel generationsPanel;

	/** the {@link PopulationPanel} for the population lists. */
	private final PopulationPanel populationPanel;

	/** the {@link GenomePanel} for the genome lists. */
	private final GenomePanel genomePanel;

	/** the {@link GenealogyPanel} for the genealogy. */
	private final GenealogyPanel genealogyPanel;

	/** The data tracker. */
	private final DataTracker<?, ?> dataTracker;

	/**
	 * Creates a new {@link XEvolutionGui}.
	 * 
	 * @param title
	 *            the base string for the title of the gui
	 * @param visualizeData
	 *            the visualize data
	 * @param dataTracker
	 *            the data tracker
	 */
	public XEvolutionGui(String title, VisualizeData visualizeData,
			DataTracker<?, ?> dataTracker) {
		this.title = title;
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		populationFiles = new ArrayList<File>();
		menuBar = new MenuBar(this, frame, title);
		frame.setJMenuBar(menuBar);
		tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(600, 400));
		frame.getContentPane().add(tabbedPane);
		statusBar = new StatusBar();
		frame.add(statusBar, BorderLayout.PAGE_END);
		graphPanel = new JPanel();
		graphPanel.setLayout(new GridLayout(0, 1));
		bufferSize = (int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth();
		bufferCount = 3; // max,avg,min
		graphsMap = new HashMap<XValId<?>, Graph>();
		tabbedPane.addTab("Graphs", graphPanel);
		generationsPanel = new JPanel();
		generationsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		populationPanel = new PopulationPanel(popLock);
		genomePanel = new GenomePanel();
		genomePanel.addSortByAttributeId(Genome.FITNESS_ATTRIBUTE_ID);
		genomePanel.addSortByAttributeId(Genome.COMPLEXITY_ATTRIBUTE_ID);
		genomePanel.getSortComboBox().setSelectedItem(
				Genome.FITNESS_ATTRIBUTE_ID);
		c.gridx = 0;
		c.gridy = 0;
		generationsPanel.add(populationPanel, c);
		c.gridx = 1;
		c.gridy = 0;
		generationsPanel.add(genomePanel, c);
		tabbedPane.addTab("Generations", generationsPanel);
		genealogyPanel = new GenealogyPanel(dataTracker);
		tabbedPane.addTab("Genealogy", genealogyPanel);
		addGraph(Genome.FITNESS_ATTRIBUTE_ID);
		addGraph(Genome.COMPLEXITY_ATTRIBUTE_ID);
		this.dataTracker = dataTracker;
		new GuiListener(populationPanel, genomePanel, genealogyPanel,
				visualizeData, dataTracker);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
		catchUp();
	}

	private void catchUp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				XEvolutionGui.this.menuBar.setLoadable(false);
				List<XId> popIds = new ArrayList<XId>(dataTracker
						.getPopulationDataManager().getIds());
				Collections.sort(popIds);
				XEvolutionGui.this.statusBar.startProgressBar(0, popIds.size());
				int i = 0;
				for (XId id : popIds) {
					popLock.lock();
					try {
						PopulationData<?> popData = dataTracker
								.getPopulationDataManager().getObject(id);
						// TODO add population files
						populationFiles.add(popData.getPopulationFile());
						statusBar.setProgressBar(i++);
						statusBar.setMessage("Loading population : "
								+ popData.getId());
						// + populationFile.getName());
						for (Object key : popData.keySet()) {
							Object val = popData.get(key);
							if (!JSONArray.class.equals(val.getClass())) {
								continue;
							}
							XValId<?> keyId = XValId.TEXT_FORMAT.parse(key
									.toString());
							Graph graph = graphsMap.get(keyId);
							if (graph == null) {
								graph = addGraph(keyId);
							}
							graph.addData(Double.valueOf(((JSONArray) val).get(
									0).toString()), Double
									.valueOf(((JSONArray) val).get(1)
											.toString()), Double
									.valueOf(((JSONArray) val).get(2)
											.toString()));
						}
					} finally {
						popLock.unlock();
					}
					frame.repaint();
				}
				populationPanel.addAllItems(populationFiles);
				statusBar.clearMessage();
				statusBar.clearProgressBar();
				XEvolutionGui.this.menuBar.setLoadable(true);
				frame.repaint();
			}
		}).run();
	}

	/** The watch dir thread. {@link Thread} used to read population xml files */
	private Thread watchDirThread;

	/**
	 * Creates a background thread to watch the output directory where.
	 * 
	 * {@link Population} xml files are written. The thread polls the file
	 * system once every two seconds for new files.
	 */
	public void watchDir() {
		watchDirThread = new Thread(new Runnable() {
			private long newest = 0l;

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				if (OUTPUT_OPTION.hasValue()) {
					File watchDir = OUTPUT_OPTION.processValue();
					populationFiles.clear();
					populationPanel.removeAllItems();
					while (true) {
						File[] newFiles = watchDir.listFiles(new FileFilter() {
							@Override
							public boolean accept(File pathname) {
								return pathname.lastModified() > newest
										&& pathname.getName().endsWith(".xml");
							}
						});
						if (newFiles.length == 0) {
							try {
								// polling for now...
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
								break;
							}
							continue;
						}
						List<File> newFilesList = Arrays.asList(newFiles);
						Collections.sort(newFilesList, new Comparator<File>() {
							@Override
							public int compare(File o1, File o2) {
								Matcher matcher = POPULATION_FILE_PATTERN
										.matcher(o1.getName());
								if (matcher.matches()) {
									int age1 = Integer.parseInt(matcher
											.group(1));
									matcher = POPULATION_FILE_PATTERN
											.matcher(o2.getName());
									if (matcher.matches()) {
										int age2 = Integer.parseInt(matcher
												.group(1));
										return age1 - age2;
									}
								}
								return o1.compareTo(o2);
							}
						});
						populationFiles.addAll(newFilesList);
						populationPanel.addAllItems(newFilesList);
						XEvolutionGui.this.statusBar.startProgressBar(0,
								newFilesList.size());
						int i = 0;
						for (File populationFile : newFilesList) {
							newest = Math.max(newest,
									populationFile.lastModified());
							statusBar.setProgressBar(i++);
							statusBar.setMessage("Loading population : "
									+ populationFile.getName());
							Population<?> population = null;
							for (int j = 0; j < RETRIES; j++) {
								popLock.lock();
								try {
									population = dataTracker
											.processPopulation(populationFile);
									for (@SuppressWarnings("rawtypes")
									final XValId valueId : population
											.getGenomeAttributeIds()) {
										Graph graph = graphsMap.get(valueId);
										if (graph == null) {
											graph = addGraph(valueId);
										}
										graph.addData(
												//
												((Number) population
														.getHighestGenomeByAttribute(
																valueId)
														.getAttribute(valueId))
														.doubleValue(),//
												population
														.getAverageGenomeAttribute(valueId),//
												((Number) population
														.getLowestGenomeByAttribute(
																valueId)
														.getAttribute(valueId))
														.doubleValue());
									}
									// success
									break;
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									popLock.unlock();
								}
								// failed to read population, wait and try again
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							if (population == null) {
								// failed to read population. we know there was
								// a population, so add dummy data as a place
								// holder
								for (XValId<?> valueId : graphsMap.keySet()) {
									Graph graph = graphsMap.get(valueId);
									if (graph == null) {
										graph = new Graph(valueId.getBase(),
												bufferSize, 3);
										graphsMap.put(valueId, graph);
										menuBar.addGraphCheckbox(graph,
												graphPanel, false);
									}
									graph.addData(0.0, 0.0, 0.0);
								}
							}
							frame.repaint();
						}
						statusBar.clearMessage();
						statusBar.clearProgressBar();
					}
				}
			}
		});
		watchDirThread.start();
	}

	/**
	 * Returns the base title string of this {@link XEvolutionGui}.
	 * 
	 * @return the base title string of this {@link XEvolutionGui}
	 */
	public String getBaseTitle() {
		return title;
	}

	/**
	 * Adds the graph.
	 * 
	 * @param valueId
	 *            the value id
	 * @return the graph
	 */
	private Graph addGraph(final XValId<?> valueId) {
		final Graph g = new Graph(valueId.getBase(), bufferSize, bufferCount);
		graphsMap.put(valueId, g);
		g.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() instanceof Graph) {
					Graph g = (Graph) e.getSource();
					int index = g.getHighlightIndex();
					if (index >= 0) {
						tabbedPane.setSelectedIndex(GENERATIONS_INDEX);
						populationPanel.getComboBox().setSelectedIndex(index);
						genomePanel.sortBy(valueId);
					}
				}
			}
		});
		// just show the first graph
		menuBar.addGraphCheckbox(g, graphPanel, graphsMap.size() == 1);
		genomePanel.addSortByAttributeId(valueId);
		return g;
	}
}
