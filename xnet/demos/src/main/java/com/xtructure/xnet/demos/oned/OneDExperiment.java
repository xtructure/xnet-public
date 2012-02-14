/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.xtructure.xnet.demos.oned;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import javolution.xml.stream.XMLStreamException;

import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.xbatch.batch.XBatch;
import com.xtructure.xbatch.world.Fitness;
import com.xtructure.xbatch.world.Fitness.FitnessFactory;
import com.xtructure.xbatch.world.World.WorldFactory;
import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.evolution.EvaluationStrategy;
import com.xtructure.xevolution.evolution.EvolutionStrategy;
import com.xtructure.xevolution.evolution.ReproductionStrategy;
import com.xtructure.xevolution.evolution.SurvivalFilter;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.GenomeDecoder;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xevolution.gui.VisualizeData;
import com.xtructure.xevolution.gui.XEvolutionGui;
import com.xtructure.xevolution.operator.CrossoverOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.tool.data.DataTracker;
import com.xtructure.xneat.evolution.NEATSpeciationStrategy;
import com.xtructure.xneat.evolution.config.impl.NEATEvolutionConfigurationImpl;
import com.xtructure.xneat.evolution.impl.AbstractNEATEvolutionExperiment;
import com.xtructure.xneat.evolution.impl.NEATEvolutionStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATReproductionStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATSpeciationStrategyImpl;
import com.xtructure.xneat.evolution.impl.NEATSurvivalFilterImpl;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.operators.impl.AddLinkMutateOperator;
import com.xtructure.xneat.operators.impl.AddNodeMutateOperator;
import com.xtructure.xneat.operators.impl.AdjustAttributesMutateOperator;
import com.xtructure.xneat.operators.impl.NEATCrossoverOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.NEATMutateOperatorSelecterImpl;
import com.xtructure.xneat.operators.impl.RemoveLinkMutateOperator;
import com.xtructure.xneat.operators.impl.StandardCrossoverOperator;
import com.xtructure.xnet.demos.oned.art.ARTGeneticsFactory;
import com.xtructure.xnet.demos.oned.art.ARTGeneticsFactoryImpl;
import com.xtructure.xnet.demos.oned.components.OneDCritter;
import com.xtructure.xnet.demos.oned.components.OneDFitness;
import com.xtructure.xnet.demos.oned.components.OneDFitnessImpl;
import com.xtructure.xnet.demos.oned.components.OneDFitnessImpl.OneDFitnessFactory;
import com.xtructure.xnet.demos.oned.components.OneDSimulation;
import com.xtructure.xnet.demos.oned.components.OneDWorld;
import com.xtructure.xnet.demos.oned.components.OnedWorldImpl;
import com.xtructure.xnet.demos.oned.components.OnedWorldImpl.WorldImplFactory;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XSimulation.SimulationState;
import com.xtructure.xsim.gui.XVisualization;
import com.xtructure.xsim.gui.impl.AbstractXSimulationGui;
import com.xtructure.xsim.impl.AbstractXSimulationListener;
import com.xtructure.xsim.impl.StandardXClock.StandardTimePhase;
import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.opt.BooleanXOption;
import com.xtructure.xutil.opt.FileXOption;
import com.xtructure.xutil.opt.XOption;
import com.xtructure.xutil.xml.XmlReader;

/**
 * The Class OneDExperiment.
 * 
 * @author Luis Guimbarda
 */
public class OneDExperiment extends
		AbstractNEATEvolutionExperiment<GeneMap, OneDCritter> {

	/** The Constant GUI_OPTION_NAME. */
	public static final String GUI_OPTION_NAME = "gui";

	/** The Constant LOOSE_OPTION_NAME. */
	public static final String LOOSE_OPTION_NAME = "looseDecode";

	/** The Constant FIXED_CONFIG_NAME. */
	public static final String FIXED_CONFIG_NAME = "fixedConfig";

	/** The Constant LOOSE_CONFIG_NAME. */
	public static final String LOOSE_CONFIG_NAME = "looseConfig";

	/** The Constant LINK_CONFIG_NAME. */
	public static final String LINK_CONFIG_NAME = "linkConfig";

	/** The Constant NODE_CONFIG_NAME. */
	public static final String NODE_CONFIG_NAME = "nodeConfig";

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		OneDExperiment experiment = new OneDExperiment();
		experiment.setArgs(args);
		experiment.startExperiment();

		Genome<?> fittest = experiment.getPopulation()
				.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID);
		System.out.println(fittest.getData());
	}

	/**
	 * Instantiates a new one d experiment.
	 */
	public OneDExperiment() {
		super(
				new SetBuilder<XOption<?>>()
						//
						.add(new BooleanXOption(GUI_OPTION_NAME, "g", "gui",
								"show gui"))//
						.add(new BooleanXOption(LOOSE_OPTION_NAME, "L",
								"looseDecode", "use a loose decoder"))//
						.add(new BooleanXOption(FIXED_CONFIG_NAME, "f",
								"fixed", "use fixed value gene configuration"))//
						.add(new BooleanXOption(LOOSE_CONFIG_NAME, "l",
								"loose", "use loose value gene configuration"))//
						.add(new FileXOption(LINK_CONFIG_NAME, "lc",
								"linkConfig", "link configuration file"))//
						.add(new FileXOption(NODE_CONFIG_NAME, "nc",
								"nodeConfig", "node configuration file"))//
						.newImmutableInstance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * startExperiment()
	 */
	@Override
	public void startExperiment() {
		BooleanXOption guiOption = (BooleanXOption) getOption(GUI_OPTION_NAME);
		if (guiOption != null && guiOption.processValue()) {
			new XEvolutionGui("1D Experiment", new VisualizeOneD(
					getGenomeDecoder()), DataTracker.newInstance());
		}
		super.startExperiment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createGenomeDecoder()
	 */
	@Override
	protected GenomeDecoder<GeneMap, OneDCritter> createGenomeDecoder() {
		boolean useFixed = ((BooleanXOption) getOption(FIXED_CONFIG_NAME))
				.processValue();
		boolean useLoose = ((BooleanXOption) getOption(LOOSE_CONFIG_NAME))
				.processValue();
		boolean useLooseDecoder = ((BooleanXOption) getOption(LOOSE_OPTION_NAME))
				.processValue() || useFixed || useLoose;
		return new OneDGenomeDecoderImpl(useLooseDecoder);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createGeneticsFactory()
	 */
	@Override
	protected GeneticsFactory<GeneMap> createGeneticsFactory() {
		LinkConfiguration linkConfiguration = null;
		NodeConfiguration nodeConfiguration = null;
		File linkConfigFile = ((FileXOption) getOption(LINK_CONFIG_NAME))
				.processValue();
		File nodeConfigFile = ((FileXOption) getOption(NODE_CONFIG_NAME))
				.processValue();
		boolean useFixed = ((BooleanXOption) getOption(FIXED_CONFIG_NAME))
				.processValue();
		boolean useLoose = ((BooleanXOption) getOption(LOOSE_CONFIG_NAME))
				.processValue();
		if (linkConfigFile == null) {
			if (useFixed) {
				linkConfiguration = ARTGeneticsFactory.FIXED_LINK_CONFIGURATION;
			} else if (useLoose) {
				linkConfiguration = ARTGeneticsFactory.LOOSE_LINK_CONFIGURATION;
			} else {
				linkConfiguration = ARTGeneticsFactory.RANDOM_LINK_CONFIGURATION;
			}
		} else {
			try {
				linkConfiguration = XmlReader.read(linkConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
		if (nodeConfigFile == null) {
			if (useFixed) {
				nodeConfiguration = ARTGeneticsFactory.FIXED_NODE_CONFIGURATION;
			} else if (useLoose) {
				nodeConfiguration = ARTGeneticsFactory.LOOSE_NODE_CONFIGURATION;
			} else {
				nodeConfiguration = ARTGeneticsFactory.RANDOM_NODE_CONFIGURATION;
			}
		} else {
			try {
				nodeConfiguration = XmlReader.read(nodeConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
		return new ARTGeneticsFactoryImpl(//
				getEvolutionFieldMap(),//
				linkConfiguration,//
				nodeConfiguration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createReproductionStrategy()
	 */
	@Override
	protected ReproductionStrategy<GeneMap> createReproductionStrategy() {
		OperatorSelecter<CrossoverOperator<GeneMap>> crossoverOperatorSelecter = new NEATCrossoverOperatorSelecterImpl(//
				new MapBuilder<CrossoverOperator<GeneMap>, Double>()//
						.put(//
						new StandardCrossoverOperator(getGeneticsFactory()),//
						1.0)//
						.newImmutableInstance());
		OperatorSelecter<MutateOperator<GeneMap>> mutateOperatorSelecter = new NEATMutateOperatorSelecterImpl(//
				new MapBuilder<MutateOperator<GeneMap>, Double>()//
						.put(//
						new AddLinkMutateOperator(getGeneticsFactory()),//
						0.01)//
						.put(//
						new AddNodeMutateOperator(getGeneticsFactory()),//
						0.005)//
						.put(//
						new RemoveLinkMutateOperator(getGeneticsFactory()),//
						0.005)//
						.newImmutableInstance());
		boolean useFixed = ((BooleanXOption) getOption(FIXED_CONFIG_NAME))
				.processValue();
		boolean useLoose = ((BooleanXOption) getOption(LOOSE_CONFIG_NAME))
				.processValue();
		if (!useFixed && !useLoose) {
			mutateOperatorSelecter.add(//
					new AdjustAttributesMutateOperator(//
							true,// mutate links
							true,// mutate nodes
							0.5,// adjust half the attributes
							0.5,// adjust half the genes
							getGeneticsFactory()),//
					0.98);
		}
		return new NEATReproductionStrategyImpl(//
				getEvolutionFieldMap(),//
				getGeneticsFactory(),//
				crossoverOperatorSelecter,//
				mutateOperatorSelecter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createEvaluationStrategy()
	 */
	@Override
	protected EvaluationStrategy<GeneMap, OneDCritter> createEvaluationStrategy() {
		return new OneDEvaluationStrategyImpl(getGenomeDecoder(),
				new OneDFitnessFactory(), new WorldImplFactory(10.0, 1.0, 2.0,
						0.0015), 16, 2000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createSurvivalFilter()
	 */
	@Override
	protected SurvivalFilter createSurvivalFilter() {
		return new NEATSurvivalFilterImpl(getEvolutionFieldMap());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.evolution.impl.AbstractEvolutionExperiment#
	 * createEvolutionStrategy()
	 */
	@Override
	protected EvolutionStrategy<GeneMap, OneDCritter> createEvolutionStrategy() {
		return new NEATEvolutionStrategyImpl<OneDCritter>(//
				getEvolutionFieldMap(),//
				getReproductionStrategy(),//
				getEvaluationStrategy(),//
				getSurvivalFilter(),//
				getSpeciationStrategy(),//
				getOutputDir());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.evolution.impl.AbstractNEATEvolutionExperiment#
	 * createSpeciationStrategy()
	 */
	@Override
	protected NEATSpeciationStrategy<GeneMap> createSpeciationStrategy() {
		return new NEATSpeciationStrategyImpl(getEvolutionFieldMap());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xneat.evolution.impl.AbstractNEATEvolutionExperiment#
	 * createEvolutionFieldMap()
	 */
	@Override
	protected EvolutionFieldMap createEvolutionFieldMap() {
		return NEATEvolutionConfigurationImpl//
				.builder(null)//
				.setPopulationSize(60)//
				.setMutationProbability(0.8)//
				.setInputNodeCount(3)//
				.setOutputNodeCount(2)//
				.setBiasNodeCount(0)//
				.setInitialConnectionProbability(0.75)//
				.newInstance()//
				.newFieldMap();
	}

	/**
	 * The Class VisualizeOneD.
	 */
	public static final class VisualizeOneD implements VisualizeData {

		/** The decoder. */
		private final GenomeDecoder<GeneMap, OneDCritter> decoder;

		/**
		 * Instantiates a new visualize one d.
		 * 
		 * @param decoder
		 *            the decoder
		 */
		public VisualizeOneD(GenomeDecoder<GeneMap, OneDCritter> decoder) {
			this.decoder = decoder;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xevolution.gui.VisualizeData#createSimVisualization
		 * (com.xtructure.xevolution.genetics.Genome)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public JFrame createSimVisualization(Genome<?> genome) {
			return new SimGui(//
					decoder.decode((Genome<GeneMap>) genome),//
					OnedWorldImpl.getInstance());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xevolution.gui.VisualizeData#createGenomeVisualization
		 * (com.xtructure.xevolution.genetics.Genome<?>[])
		 */
		@Override
		public JPanel createGenomeVisualization(Genome<?>... genomes) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xevolution.gui.VisualizeData#createSimVisualization
		 * (com.xtructure.xevolution.tool.data.DataTracker,
		 * com.xtructure.xevolution.genetics.Genome)
		 */
		@Override
		public JFrame createSimVisualization(DataTracker<?, ?> dataTracker,
				Genome<?> genome) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.xtructure.xevolution.gui.VisualizeData#createGenomeVisualization
		 * (com.xtructure.xevolution.tool.data.DataTracker,
		 * com.xtructure.xevolution.genetics.Genome<?>[])
		 */
		@Override
		public JPanel createGenomeVisualization(DataTracker<?, ?> dataTracker,
				Genome<?>... genomes) {
			return null;
		}
	}

	/**
	 * The Class SimGui.
	 */
	public static final class SimGui extends
			AbstractXSimulationGui<StandardTimePhase> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		/** Space to leave between the visualizations. */
		private static final int VIZ_PAD = 0;

		/** The link viz. */
		private final XVisualization<StandardTimePhase> worldViz, netViz,
				nodeViz, linkViz;

		/** The link viz rect. */
		private Rectangle worldVizRect, netVizRect, nodeVizRect, linkVizRect;

		/**
		 * Instantiates a new sim gui.
		 * 
		 * @param critter
		 *            the critter
		 * @param world
		 *            the world
		 */
		public SimGui(OneDCritter critter, OneDWorld world) {
			super(OneDSimulation.getSim(//
					XId.newId("1D Sim"),//
					OneDSimulation.DEFAULT_TICK_DELAY, //
					0,//
					new OneDFitnessImpl(XId.newId("fitness")),//
					critter, //
					world));
			setTitle("1D Demo Simulation");
			setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
			setPreferredSize(new Dimension(1000, 600));
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setResizable(true);
			setVisible(true);
			this.worldViz = ((OnedWorldImpl) world).getWorldVisualization(400);
			this.netViz = critter.getNetworkVisualization();
			this.nodeViz = critter.getNodeVisualization();
			this.linkViz = critter.getLinkVisualization();
			addVisualization(worldViz);
			addVisualization(netViz);
			addVisualization(nodeViz);
			addVisualization(linkViz);
			placeVisualizations();
			// Make the gui just large enough to show all the components
			setSize(//
			Math.round(3 * VIZ_PAD + (float) worldVizRect.getWidth()
					+ (float) netVizRect.getWidth() + 5),//
					Math.round(3 * VIZ_PAD + (float) worldVizRect.getHeight()
							+ (float) nodeVizRect.getHeight() + 90));
			// add listener to replace visualizations on resize();
			addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}

				@Override
				public void componentResized(ComponentEvent e) {
					placeVisualizations();
				}

				@Override
				public void componentMoved(ComponentEvent e) {
				}

				@Override
				public void componentHidden(ComponentEvent e) {
				}
			});
		}

		/**
		 * Place visualizations.
		 */
		private void placeVisualizations() {
			final int minVizHeight = 400;
			final int toolbarHeight = 95;
			final int tableHeight = 100;
			final int widthHammer = 10;
			final int heightHammer = -7;
			// world visualization goes at the top left
			JInternalFrame iFrame = worldViz.getInternalFrame();
			worldVizRect = new Rectangle(//
					VIZ_PAD,//
					VIZ_PAD,//
					(int) iFrame.getPreferredSize().getWidth(),//
					(int) Math.max(
							minVizHeight,
							Math.min(iFrame.getPreferredSize().getHeight(),
									this.getHeight())
									- tableHeight
									- toolbarHeight));
			// network visualization goes just to the right of the world
			// visualization
			iFrame = netViz.getInternalFrame();
			netVizRect = new Rectangle(//
					Math.round(2 * VIZ_PAD + (float) worldVizRect.getWidth()),//
					VIZ_PAD,//
					(int) Math.max(worldVizRect.getWidth(), this.getWidth()
							- worldVizRect.getWidth())
							- 3 * VIZ_PAD - widthHammer,//
					(int) Math.max(
							minVizHeight,
							Math.min(iFrame.getPreferredSize().getHeight(),
									this.getHeight())
									- tableHeight
									- toolbarHeight - 3 * VIZ_PAD));
			iFrame.setBounds(netVizRect);
			// make world viz as tall as net viz
			iFrame = worldViz.getInternalFrame();
			worldVizRect = new Rectangle(//
					VIZ_PAD,//
					VIZ_PAD,//
					iFrame.getWidth(),//
					(int) netVizRect.getHeight());
			iFrame.setBounds(worldVizRect);
			// calculate node/link viz bounds
			int nodeVizX = 0;
			int nodeVizY = 2 * VIZ_PAD + (int) worldVizRect.getHeight();
			int nodeVizHeight = Math.max(tableHeight, this.getHeight()
					- nodeVizY)
					- toolbarHeight - 3 * VIZ_PAD - heightHammer;
			int nodeVizWidth = (int) worldVizRect.getWidth();
			int linkVizX = (int) netVizRect.getX();
			int linkVizY = nodeVizY;
			int linkVizHeight = nodeVizHeight;
			int linkVizWidth = (int) netVizRect.getWidth();
			// Place node visualization below the world visualization
			iFrame = nodeViz.getInternalFrame();
			nodeVizRect = new Rectangle(nodeVizX, nodeVizY, nodeVizWidth,
					nodeVizHeight);
			iFrame.setBounds(nodeVizRect);
			// Place link visualization just to the right of the node
			// visualization
			iFrame = linkViz.getInternalFrame();
			linkVizRect = new Rectangle(linkVizX, linkVizY, linkVizWidth,
					linkVizHeight);
			iFrame.setBounds(linkVizRect);
		}
	}

	/**
	 * The Class OneDPopulationBatch.
	 * 
	 * @param <D>
	 *            the generic type
	 */
	public static class OneDPopulationBatch<D> implements XBatch {

		/** The fitness map. */
		private final Map<XId, Fitness<?>> fitnessMap = new HashMap<XId, Fitness<?>>();

		/** The fitness lock. */
		private final ReentrantLock fitnessLock = new ReentrantLock();

		/** The critter lock. */
		private final ReentrantLock critterLock = new ReentrantLock();

		/** The simulation length. */
		private final long simulationLength;

		/** The simulation count. */
		private final int simulationCount;

		/** The iterator. */
		private final Iterator<Genome<D>> iterator;

		/** The current genome. */
		private Genome<D> currentGenome = null;

		/** The decoder. */
		private final GenomeDecoder<D, OneDCritter> decoder;

		/** The sim index. */
		private int simIndex;

		/** The remaining. */
		private int remaining;

		/** The fitness factory. */
		private final FitnessFactory<?> fitnessFactory;

		/** The world factory. */
		private final WorldFactory<?> worldFactory;

		/**
		 * Instantiates a new one d population batch.
		 * 
		 * @param simulationCount
		 *            the simulation count
		 * @param simulationLength
		 *            the simulation length
		 * @param population
		 *            the population
		 * @param decoder
		 *            the decoder
		 * @param fitnessFactory
		 *            the fitness factory
		 * @param worldFactory
		 *            the world factory
		 */
		public OneDPopulationBatch(//
				int simulationCount, long simulationLength,//
				Population<D> population,//
				GenomeDecoder<D, OneDCritter> decoder,//
				FitnessFactory<?> fitnessFactory,//
				WorldFactory<?> worldFactory) {
			this.simulationCount = simulationCount;
			this.simulationLength = simulationLength;
			this.iterator = population.iterator();
			this.decoder = decoder;
			this.fitnessFactory = fitnessFactory;
			this.worldFactory = worldFactory;
			this.remaining = population.size() * simulationCount;
			this.simIndex = simulationCount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.xtructure.xbatch.batch.XBatch#getNextSimulation()
		 */
		@Override
		public XSimulation<?> getNextSimulation() {
			critterLock.lock();
			try {
				final XId id = currentGenome.getId();
				final OneDFitness fitness = (OneDFitness) fitnessFactory
						.newInstance(XId.newId("fitness"));
				final OneDCritter critter = decoder.decode(currentGenome);
				final OnedWorldImpl world = (OnedWorldImpl) worldFactory
						.newInstance(XId.newId("world"));
				final OneDSimulation sim = OneDSimulation
						//
						.getSim(id, 0, 0, fitness, critter, world)
						.setTickBound(simulationLength);
				sim.addListener(new AbstractXSimulationListener() {
					@Override
					public void simulationStateChanged(XSimulation<?> sim,
							SimulationState state) {
						if (SimulationState.FINISHED.equals(state)) {
							fitnessLock.lock();
							try {
								if (fitnessMap.containsKey(critter.getId())) {
									((OneDFitness) fitnessMap.get(critter
											.getId())).addStats(fitness);
								} else {
									fitnessMap.put(critter.getId(), fitness);
								}
							} finally {
								fitnessLock.unlock();
							}
						}
					}
				});
				simIndex++;
				remaining--;
				return sim;
			} finally {
				critterLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.xtructure.xbatch.batch.XBatch#hasMoreSimulations()
		 */
		@Override
		public boolean hasMoreSimulations() {
			critterLock.lock();
			try {
				settle();
				return remaining > 0;
			} finally {
				critterLock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.xtructure.xbatch.batch.XBatch#remaining()
		 */
		@Override
		public int remaining() {
			critterLock.lock();
			try {
				settle();
				return remaining;
			} finally {
				critterLock.unlock();
			}
		}

		/**
		 * Settle.
		 */
		private void settle() {
			if (simIndex >= simulationCount) {
				simIndex = 0;
				while (iterator.hasNext()) {
					currentGenome = iterator.next();
					if (currentGenome.getEvaluationCount() <= 0) {
						return;
					}
					remaining -= simulationCount;
				}
				currentGenome = null;
			}
		}

		/**
		 * Gets the fitness map.
		 * 
		 * @return the fitness map
		 */
		public Map<XId, Fitness<?>> getFitnessMap() {
			return Collections.unmodifiableMap(this.fitnessMap);
		}
	}
}
