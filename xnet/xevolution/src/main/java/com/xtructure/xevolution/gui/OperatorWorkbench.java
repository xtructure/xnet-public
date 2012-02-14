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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xevolution.operator.OperatorSelecter;
import com.xtructure.xevolution.operator.impl.CopyMutateOperator;
import com.xtructure.xevolution.operator.impl.MutateOperatorSelecterImpl;
import com.xtructure.xevolution.tool.data.DataTracker;
import com.xtructure.xutil.XLogger;
import com.xtructure.xutil.coll.ListBuilder;
import com.xtructure.xutil.coll.Transform;

/**
 * {@link OperatorWorkbench} provides a gui interface for manually testing
 * {@link Operator}s.
 * 
 * @author Luis Guimbarda
 * 
 */
public class OperatorWorkbench<D> {
	private static final int	PAD	= 5;

	public static void main(String[] args) {
		EvolutionFieldMap evolutionFieldMap = EvolutionConfigurationImpl.DEFAULT_CONFIGURATION.newFieldMap();
		GeneticsFactoryImpl geneticsFactory = new GeneticsFactoryImpl(evolutionFieldMap, "defaultData");
		MutateOperatorSelecterImpl opselecter = new MutateOperatorSelecterImpl();
		opselecter.add(//
				new CopyMutateOperator<String>(geneticsFactory),//
				1.0);
		VisualizeData visualizeData = new VisualizeData() {
			@Override
			public JFrame createSimVisualization(Genome<?> genome) {
				return null;
			}

			@Override
			public JPanel createGenomeVisualization(Genome<?>... genomes) {
				JPanel panel = new JPanel();
				panel.add(new JLabel(genomes[0].getData().toString()));
				return panel;
			}

			@Override
			public JFrame createSimVisualization(DataTracker<?, ?> dataTracker, Genome<?> genome) {
				return null;
			}

			@Override
			public JPanel createGenomeVisualization(DataTracker<?, ?> dataTracker, Genome<?>... genomes) {
				return null;
			}
		};
		new OperatorWorkbench<String>("Test Workbench", "initialData", visualizeData, opselecter);
	}

	private final JFrame										frame;
	@SuppressWarnings("unused")
	// TODO: make unused
	private final VisualizeData									visualizeData;
	@SuppressWarnings("unused")
	// TODO: make unused
	private final Object										data;
	@SuppressWarnings("unused")
	// TODO: make unused
	private final OperatorSelecter<? extends MutateOperator<D>>	mutators;
	private final JList											opsList;
	private final JPanel										viewPanel0;
	private final JPanel										viewPanel1;

	public OperatorWorkbench(//
			String title,//
			D data,//
			VisualizeData visualizeData,//
			OperatorSelecter<MutateOperator<D>> mutators) {
		this.visualizeData = visualizeData;
		this.data = data;
		this.mutators = mutators;
		this.frame = new JFrame(title);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(PAD, PAD, PAD, PAD);
		opsList = new JList(//
				new ListBuilder<MutateOperatorAlias<D>>()//
						.addAll(new Transform<MutateOperatorAlias<D>, MutateOperator<D>>() {
							@Override
							public MutateOperatorAlias<D> transform(MutateOperator<D> value) {
								return new MutateOperatorAlias<D>(value);
							}
						}, mutators.opSet())//
						.newImmutableInstance().toArray());
		JScrollPane scrollPane = new JScrollPane(opsList);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Mutators"));
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = Double.MIN_VALUE;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		frame.add(scrollPane, c);
		JButton opButton = new JButton(new AbstractAction("apply") {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		c.insets = new Insets(0, PAD, PAD, PAD);
		c.gridy = 1;
		c.weighty = Double.MIN_VALUE;
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.add(opButton, c);
		this.viewPanel0 = new JPanel();
		viewPanel0.setLayout(new GridLayout());
		scrollPane = new JScrollPane(viewPanel0);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Current"));
		viewPanel0.add(new JLabel("viewPanel0"));
		c.insets = new Insets(PAD, 0, PAD, PAD);
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		frame.add(scrollPane, c);
		this.viewPanel1 = new JPanel();
		viewPanel1.setLayout(new GridLayout());
		scrollPane = new JScrollPane(viewPanel1);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Previous"));
		viewPanel1.add(new JLabel("viewPanel1"));
		c.gridx = 2;
		frame.add(scrollPane, c);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	private static final class MutateOperatorAlias<D> implements MutateOperator<D> {
		private final MutateOperator<D>	op;

		private MutateOperatorAlias(MutateOperator<D> op) {
			this.op = op;
		}

		@Override
		public Genome<D> mutate(int idNumber, Genome<D> genome) throws OperationFailedException {
			return op.mutate(idNumber, genome);
		}

		@Override
		public GeneticsFactory<D> getGeneticsFactory() {
			return op.getGeneticsFactory();
		}

		@Override
		public XLogger getLogger() {
			return op.getLogger();
		}

		@Override
		public String toString() {
			return op.getClass().getSimpleName();
		}

		@Override
		public AppliedOperatorInfo getAppliedOperatorInfo() {
			return null;
		}
	}
}
