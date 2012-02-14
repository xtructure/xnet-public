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

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

import com.xtructure.xevolution.gui.XEvolutionGui;

/**
 * {@link StatusBar} is a {@link JPanel} that acts as the status bar for
 * {@link XEvolutionGui}
 * 
 * @author Luis Guimbarda
 * 
 */
public class StatusBar extends JPanel {
	private static final long	serialVersionUID	= 1L;

	/** the {@link JLabel} for messages in this {@link StatusBar} */
	private final JLabel		messageLabel;
	/** the {@link JProgressBar} to indicate progress in this {@link StatusBar} */
	private final JProgressBar	progressBar;

	/**
	 * Creates a new {@link StatusBar}
	 */
	public StatusBar() {
		setLayout(new GridLayout(0, 2));
		setPreferredSize(new Dimension(100, 23));
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		messageLabel = new JLabel();
		progressBar = new JProgressBar();

		add(messageLabel);
		add(progressBar);

		clearMessage();
		clearProgressBar();
	}

	/**
	 * Clears the text message from this {@link StatusBar}
	 */
	public void clearMessage() {
		messageLabel.setVisible(false);
	}

	/**
	 * Clears the progress bar from this {@link StatusBar}
	 */
	public void clearProgressBar() {
		progressBar.setVisible(false);
	}

	/**
	 * Sets the text message of this {@link StatusBar} to the given text.
	 * 
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		messageLabel.setText(message);
		messageLabel.setVisible(true);
	}

	/**
	 * Sets the range of the progress bar to the given values
	 * 
	 * @param min
	 *            the min to set in the progress bar
	 * @param max
	 *            the max to set in the progress bar
	 */
	public void startProgressBar(int min, int max) {
		progressBar.setMinimum(min);
		progressBar.setMaximum(max);
		progressBar.setValue(min);
		progressBar.setVisible(true);
	}

	/**
	 * Sets the value of the progress bar to that given
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setProgressBar(int value) {
		progressBar.setValue(value);
		repaint();
	}
}
