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
package com.xtructure.xevolution.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.xtructure.xutil.opt.XOption;

/**
 * {@link CollectArgsDialog} is a JDialog that automatically formats a form for
 * user input from a {@link Collection} of {@link XOption}s.
 * 
 * @author Luis Guimbarda
 * 
 */
public class CollectArgsDialog extends JDialog {
	private static final long		serialVersionUID	= 1L;

	/** the list of {@link JComponent} used to collect user input */
	private final List<JComponent>	argComponents;
	/** indicates that the user input was successfully parsed */
	private boolean					success				= false;

	/**
	 * Creates a new {@link CollectArgsDialog}
	 * 
	 * @param frame
	 *            the parent JFrame for the new {@link CollectArgsDialog}
	 * @param statusBar
	 *            the {@link StatusBar} to update (can be null)
	 * @param title
	 *            the title of the new {@link CollectArgsDialog}
	 * @param xOptions
	 *            the {@link Collection} of {@link XOption}s for which to
	 *            collect user input
	 */
	public CollectArgsDialog(JFrame frame, final StatusBar statusBar, String title, final Collection<XOption<?>> xOptions) {
		super(frame, title, true);

		argComponents = new ArrayList<JComponent>();

		final CollectArgsDialog dialog = this;
		JPanel panel = new JPanel(new GridBagLayout());
		getContentPane().add(panel);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 3, 3, 3);
		c.fill = GridBagConstraints.BOTH;

		int row = 0;
		for (XOption<?> xOption : xOptions) {
			if (xOption.getName() == null) {
				continue;
			}
			if (xOption.hasArg()) {
				JLabel label = new JLabel(xOption.getName());
				JTextField textField = new JTextField();
				textField.setName(xOption.getOpt());
				textField.setToolTipText(xOption.getDescription());
				argComponents.add(textField);
				c.gridx = 0;
				c.gridy = row;
				panel.add(label, c);
				c.gridx = 1;
				c.gridy = row;
				panel.add(textField, c);
			} else {
				JCheckBox checkBox = new JCheckBox(xOption.getName());
				checkBox.setName(xOption.getOpt());
				checkBox.setToolTipText(xOption.getDescription());
				argComponents.add(checkBox);
				c.gridx = 0;
				c.gridy = row;
				panel.add(checkBox, c);
			}
			row++;
		}

		JPanel buttonPanel = new JPanel();
		c.gridx = 1;
		c.gridy = row;
		panel.add(buttonPanel, c);

		JButton okButton = new JButton(new AbstractAction("OK") {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				if (statusBar != null) {
					statusBar.setMessage("building args...");
				}
				ArrayList<String> args = new ArrayList<String>();
				for (JComponent component : argComponents) {
					if (component instanceof JCheckBox) {
						JCheckBox checkbox = (JCheckBox) component;
						String opt = checkbox.getName();
						if (checkbox.isSelected()) {
							args.add("-" + opt);
						}
					}
					if (component instanceof JTextField) {
						JTextField textField = (JTextField) component;
						String opt = textField.getName();
						String text = textField.getText().trim();
						if (!text.isEmpty()) {
							args.add("-" + opt);
							args.add("\"" + text + "\"");
						}
					}
				}
				if (statusBar != null) {
					statusBar.setMessage("parsing args...");
				}
				try {
					Options options = new Options();
					for (XOption<?> xOpt : xOptions) {
						options.addOption(xOpt);
					}
					XOption.parseArgs(options, args.toArray(new String[0]));
					dialog.success = true;
				} catch (ParseException e1) {
					e1.printStackTrace();
					dialog.success = false;
				}
				if (statusBar != null) {
					statusBar.clearMessage();
				}
			}
		});
		buttonPanel.add(okButton);
		getRootPane().setDefaultButton(okButton);

		buttonPanel.add(new JButton(new AbstractAction("Cancel") {
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				if (statusBar != null) {
					statusBar.clearMessage();
				}
			}
		}));
		pack();
		setLocationRelativeTo(frame);
		setVisible(true);
	}

	/**
	 * Indicates whether collection of user input was successful.
	 * 
	 * @return true is collection of user input was successful, false otherwise.
	 */
	public boolean succeeded() {
		return success;
	}
}
