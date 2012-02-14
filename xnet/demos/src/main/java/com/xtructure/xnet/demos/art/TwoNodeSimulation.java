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
package com.xtructure.xnet.demos.art;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javolution.xml.stream.XMLStreamException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.xtructure.art.model.link.Link;
import com.xtructure.art.model.link.LinkConfiguration;
import com.xtructure.art.model.link.LinkImpl;
import com.xtructure.art.model.node.Node;
import com.xtructure.art.model.node.NodeConfiguration;
import com.xtructure.art.model.node.NodeImpl;
import com.xtructure.xsim.XSimulation;
import com.xtructure.xsim.XTime;
import com.xtructure.xsim.gui.impl.AbstractXSimulationGui;
import com.xtructure.xsim.impl.AbstractStandardXSimulation;
import com.xtructure.xsim.impl.AbstractXSimulationListener;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.StandardXClock;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.xml.XmlBinding;
import com.xtructure.xutil.xml.XmlReader;

/**
 * The Class TwoNodeSimulation.
 * 
 * @author Luis Guimbarda
 */
public final class TwoNodeSimulation extends AbstractStandardXSimulation {
	/** The Id for simulation objects. */
	private static final XId SIM_ID;

	/** The directory where default configuration files are located. */
	private static final File RESOURCE_DIR;
	/** A lock protecting {@link #DEMO_COMPLETE}. Used in test (console) mode. */
	private static final Lock PROGRESS_LOCK;
	/**
	 * A condition signaling that the demo is completed. Used in test (console)
	 * mode.
	 */
	private static final Condition DEMO_COMPLETE;
	static {
		SIM_ID = XId.newId("TwoNodeSimulation");
		RESOURCE_DIR = new File(TwoNodeSimulation.class.getResource("config")
				.getFile());
		PROGRESS_LOCK = new ReentrantLock();
		DEMO_COMPLETE = PROGRESS_LOCK.newCondition();
	}

	/** The network. */
	private final NetworkImpl network;

	/** The inputs. */
	private final NetworkImpl inputs;

	/** The network node viz. */
	private NodeVisualization networkNodeViz = null;

	/** The network link viz. */
	private LinkVisualization networkLinkViz = null;

	/** The inputs node viz. */
	private NodeVisualization inputsNodeViz = null;

	/** The gui. */
	private SimGui gui = null;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("n", "networkFile", true,//
				"the xml file from which the network is loaded");
		options.addOption("i", "inputFile", true,//
				"the xml file from which the input is loaded");
		options.addOption("test", false,
				"run integration test (non-gui, ignores networkFile and inputFile options)");
		BasicParser bp = new BasicParser();
		try {
			CommandLine cl = bp.parse(options, args);
			if (cl.hasOption("test")) {
				new TwoNodeSimulation();
			} else {
				File networkFile = new File(RESOURCE_DIR, "default.network.xml");
				File inputFile = new File(RESOURCE_DIR, "default.input.xml");
				loadConfigFiles(RESOURCE_DIR, inputFile, networkFile);
				if (cl.hasOption("n")) {
					networkFile = new File(cl.getOptionValue("n"))
							.getAbsoluteFile();
				}
				if (cl.hasOption("i")) {
					inputFile = new File(cl.getOptionValue("i"))
							.getAbsoluteFile();
				}
				new TwoNodeSimulation(networkFile, inputFile);
			}
		} catch (ParseException e) {
			System.err.println(e.getMessage());
			HelpFormatter hf = new HelpFormatter();
			hf.printHelp("Usage:", options);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load config files.
	 * 
	 * @param dir
	 *            the dir
	 * @param ignoreFiles
	 *            the ignore files
	 */
	private static void loadConfigFiles(File dir, File... ignoreFiles) {
		if (dir == null) {
			return;
		}
		PROCESS: for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			for (File iFile : ignoreFiles) {
				if (file.equals(iFile)) {
					continue PROCESS;
				}
			}
			System.out.println("Checking : " + file.getName());
			try {
				try {
					XmlReader
							.read(file,
									XmlBinding.builder()
											.add(NodeConfiguration.XML_BINDING)
											.add(LinkConfiguration.XML_BINDING)
											.add(NetworkImpl.XML_BINDING)
											.newInstance());
					System.out.println("Loaded configuration.");
					continue;
				} catch (XMLStreamException e) {
				}
				System.out
						.println("Skipped. (Not a recognized node or link configuration.)");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a new Main simulation object and starts the simulation. A gui is
	 * generated. The network and inputs used in the simulation are specified by
	 * the given networkFile and inputFile.
	 * 
	 * @param networkFile
	 *            xml file specifying the network this simulation will use.
	 * @param inputFile
	 *            xml file specifying the input this simulation will use.
	 * @throws XMLStreamException
	 *             the xML stream exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public TwoNodeSimulation(File networkFile, File inputFile)
			throws XMLStreamException, IOException {
		super(SIM_ID);
		File networkDir = networkFile.getParentFile();
		File inputDir = inputFile.getParentFile();
		if (!networkDir.equals(RESOURCE_DIR)) {
			loadConfigFiles(networkDir, inputFile, networkFile);
		}
		if (!inputDir.equals(RESOURCE_DIR) && !inputDir.equals(networkDir)) {
			loadConfigFiles(inputDir, inputFile, networkFile);
		}
		network = XmlReader.read(networkFile, NetworkImpl.XML_BINDING);
		networkNodeViz = network.getNodeVisualization();
		networkLinkViz = network.getLinkVisualization();
		inputs = XmlReader.read(inputFile, NetworkImpl.XML_BINDING);
		inputsNodeViz = inputs.getNodeVisualization();
		addComponent(network);
		addComponent(networkNodeViz);
		addComponent(networkLinkViz);
		addComponent(inputs);
		addComponent(inputsNodeViz);
		XId[] outputSourceIds = inputs.getSourceIds().toArray(new XId[0]);
		XId[] inputTargetIds = network.getTargetIds().toArray(new XId[0]);
		Map<XId, XId> outputInputMap = new HashMap<XId, XId>();
		for (int i = 0; i < outputSourceIds.length; i++) {
			outputInputMap.put(outputSourceIds[i], inputTargetIds[i]);
		}
		new Border(inputs, network, outputInputMap);
		gui = new SimGui(this);
		gui.setTitle("Two Node Simulation");
		gui.pack();
		gui.setVisible(true);
	}

	/**
	 * Creates a new Main simulation object in test mode and starts the
	 * simulation. No gui is used; this simulation runs in console mode and for
	 * 10 time steps. It uses a hard coded 2-node network with no inputs, and
	 * traces the network, ensuring that various node/link fields contain
	 * expected values.
	 */
	public TwoNodeSimulation() {
		super(SIM_ID);
		// build a two node network, with an excitatory link from node 1 to node
		// 2, and an inhibitory link from node 2 to node one.
		final int NODE_INSTANCES = 0;
		final int LINK_INSTANCES = 1;
		NodeConfiguration nodeConfig = NodeConfiguration
				.builder()
				//
				.setEnergy(Range.getInstance(0.0f, 6.0f),
						Range.getInstance(3.0f, 3.0f))//
				.setEnergyDecay(Range.getInstance(0.3f, 0.3f), null)//
				.setExcitatoryScale(Range.getInstance(0.16666f, 0.16666f), null)//
				.setInhibitoryScale(Range.getInstance(0.08333f, 0.08333f), null)//
				.newInstance();
		LinkConfiguration exLinkConfig = LinkConfiguration
				.builder()
				//
				.setCapacity(Range.getInstance(0.0f, 3.0f),
						Range.getInstance(1.0f, 1.0f))//
				.setCapacityAttack(Range.getInstance(0.005f, 0.005f), null)//
				.setCapacityDecay(Range.getInstance(0.001f, 0.001f), null)//
				.setStrength(Range.getInstance(0.0f, 3.0f),
						Range.getInstance(1.0f, 1.0f))//
				.setStrengthAttack(Range.getInstance(0.05f, 0.05f), null)//
				.setStrengthDecay(Range.getInstance(0.1f, 0.1f), null)//
				.setInhibitoryFlag(Range.FALSE_BOOLEAN_RANGE, null)//
				.newInstance();
		LinkConfiguration inLinkConfig = LinkConfiguration
				.builder()
				//
				.setCapacity(Range.getInstance(0.0f, 3.0f),
						Range.getInstance(1.0f, 1.0f))//
				.setCapacityAttack(Range.getInstance(0.005f, 0.005f), null)//
				.setCapacityDecay(Range.getInstance(0.001f, 0.001f), null)//
				.setStrength(Range.getInstance(0.0f, 3.0f),
						Range.getInstance(1.0f, 1.0f))//
				.setStrengthAttack(Range.getInstance(0.05f, 0.05f), null)//
				.setStrengthDecay(Range.getInstance(0.1f, 0.1f), null)//
				.setInhibitoryFlag(Range.TRUE_BOOLEAN_RANGE, null)//
				.newInstance();
		XId node1Id = SIM_ID.createChild(NODE_INSTANCES).createChild(1);
		XId node2Id = SIM_ID.createChild(NODE_INSTANCES).createChild(2);
		Node node1 = new NodeImpl(node1Id, nodeConfig);
		Node node2 = new NodeImpl(node2Id, nodeConfig);
		Set<Node> nodes = new SetBuilder<Node>()//
				.add(node1)//
				.add(node2)//
				.newImmutableInstance();
		XId link12Id = SIM_ID.createChild(LINK_INSTANCES).createChild(1);
		XId link21Id = SIM_ID.createChild(LINK_INSTANCES).createChild(2);
		Link link12 = new LinkImpl(link12Id, node1Id, node2Id, exLinkConfig);
		Link link21 = new LinkImpl(link21Id, node2Id, node1Id, inLinkConfig);
		Set<Link> links = new SetBuilder<Link>()//
				.add(link12)//
				.add(link21)//
				.newImmutableInstance();
		XId node1EnergyId = Node.Fragment.ENERGIES.generateExtendedId(node1Id);
		XId node2EnergyId = Node.Fragment.ENERGIES.generateExtendedId(node2Id);
		XId link12CapacityId = Link.Fragment.CAPACITY
				.generateExtendedId(link12Id);
		XId link12StrengthId = Link.Fragment.STRENGTH
				.generateExtendedId(link12Id);
		XId link21CapacityId = Link.Fragment.CAPACITY
				.generateExtendedId(link21Id);
		XId link21StrengthId = Link.Fragment.STRENGTH
				.generateExtendedId(link21Id);
		network = new NetworkImpl(SIM_ID, nodes, links);
		Set<Node> emptyNodes = Collections.emptySet();
		Set<Link> emptyLinks = Collections.emptySet();
		inputs = new NetworkImpl(SIM_ID.createChild(0), emptyNodes, emptyLinks);
		addComponent(network);
		addComponent(inputs);
		XId nodeTracerId = XId.newId("nodeTracer");
		XId linkTracerId = XId.newId("linkTracer");
		List<Float> node1Energy = Arrays.asList(3.000000f, 1.850010f,
				1.132515f, 0.688972f, 0.412555f, 0.239586f, 0.131735f,
				0.065323f, 0.025398f, 0.002367f);
		List<Float> node2Energy = Arrays.asList(3.000000f, 2.599980f,
				2.051228f, 1.561082f, 1.166436f, 0.861288f, 0.629996f,
				0.456727f, 0.327996f, 0.233027f);
		List<Float> link12Capacity = Arrays.asList(1.000000f, 1.044000f,
				1.067006f, 1.077554f, 1.081854f, 1.083179f, 1.083127f,
				1.082459f, 1.081526f, 1.080486f);
		List<Float> link12Strength = Arrays.asList(1.000000f, 0.750000f,
				0.663449f, 0.641663f, 0.651332f, 0.678554f, 0.716455f,
				0.761173f, 0.810324f, 0.862342f);
		List<Float> link21Capacity = Arrays.asList(1.000000f, 1.044000f,
				1.067006f, 1.077554f, 1.081854f, 1.083179f, 1.083127f,
				1.082459f, 1.081526f, 1.080486f);
		List<Float> link21Strength = Arrays.asList(1.000000f, 0.750000f,
				0.607202f, 0.536001f, 0.506204f, 0.501252f, 0.512238f,
				0.534124f, 0.563852f, 0.599434f);
		NodeTracer nt1 = NodeTracer.getInstance(nodeTracerId.createChild(1),
				this, node1, node1Energy, 0.000001f);
		NodeTracer nt2 = NodeTracer.getInstance(nodeTracerId.createChild(2),
				this, node2, node2Energy, 0.000001f);
		LinkTracer lt121 = LinkTracer.getInstance(
				linkTracerId.createChild(121), this, link12, link12Capacity,
				0.000001f);
		LinkTracer lt122 = LinkTracer.getInstance(
				linkTracerId.createChild(122), this, link12, link12Strength,
				0.000001f);
		LinkTracer lt211 = LinkTracer.getInstance(
				linkTracerId.createChild(211), this, link21, link21Capacity,
				0.000001f);
		LinkTracer lt212 = LinkTracer.getInstance(
				linkTracerId.createChild(212), this, link21, link21Strength,
				0.000001f);
		addComponent(nt1);
		addComponent(nt2);
		addComponent(lt121);
		addComponent(lt122);
		addComponent(lt211);
		addComponent(lt212);
		SimpleXBorder border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(nt1);
		border.associate(//
				new XAddressImpl(network, node1EnergyId), //
				new XAddressImpl(nt1, nt1.getId()));
		border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(nt2);
		border.associate(//
				new XAddressImpl(network, node2EnergyId), //
				new XAddressImpl(nt2, nt2.getId()));
		border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(lt121);
		border.associate(//
				new XAddressImpl(network, link12CapacityId), //
				new XAddressImpl(lt121, lt121.getId()));
		border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(lt122);
		border.associate(//
				new XAddressImpl(network, link12StrengthId), //
				new XAddressImpl(lt122, lt122.getId()));
		border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(lt211);
		border.associate(//
				new XAddressImpl(network, link21CapacityId), //
				new XAddressImpl(lt211, lt211.getId()));
		border = new SimpleXBorder();
		border.addComponent(network);
		border.addComponent(lt212);
		border.associate(//
				new XAddressImpl(network, link21StrengthId), //
				new XAddressImpl(lt212, lt212.getId()));
		addListener(new AbstractXSimulationListener() {
			@Override
			public final void simulationTimeChanged(final XSimulation<?> sim,
					final XTime<?> time) {
				PROGRESS_LOCK.lock();
				try {
					if (time.getTick() >= 10L) {
						pause();
						DEMO_COMPLETE.signalAll();
					}
				} finally {
					PROGRESS_LOCK.unlock();
				}
			}
		});
		setTickDelay(0);
		init();
		run();
		PROGRESS_LOCK.lock();
		try {
			DEMO_COMPLETE.await();
		} catch (InterruptedException interruptedEx) {
			interruptedEx.printStackTrace();
		} finally {
			finish();
		}
	}

	/** SimGui handles the gui generation for Main simulation objects. */
	private static class SimGui extends
			AbstractXSimulationGui<StandardXClock.StandardTimePhase> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new SimGui object.
		 * 
		 * @param sim
		 *            the Main simulation object for which to create a gui
		 */
		protected SimGui(TwoNodeSimulation sim) {
			super(sim);
		}
	}
}
