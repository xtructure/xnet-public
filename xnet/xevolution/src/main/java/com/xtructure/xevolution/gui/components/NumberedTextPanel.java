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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * {@link NumberedTextPanel} is a custom panel that displays text with line
 * numbers.
 * 
 * @author Luis Guimbarda
 * 
 */
public class NumberedTextPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	/** the {@link JScrollPane} containing the text and line numbers */
	private final JScrollPane	scrollPane;
	/** the {@link JTextArea} containing the text */
	private final JTextArea		textArea			= new JTextArea();
	/** the {@link JTextArea} containing the line numbers */
	private final JTextArea		lineNumberArea		= new JTextArea();
	/** flag to check the font height only once */
	private boolean				fontChecked			= false;

	/**
	 * Creates a new {@link NumberedTextPanel} with an empty string as text.
	 */
	public NumberedTextPanel() {
		this("");
	}

	/**
	 * Creates a new {@link NumberedTextPanel} with the given string as text.
	 * 
	 * @param text
	 *            the initial text with which to populate this
	 *            {@link NumberedTextPanel}
	 */
	public NumberedTextPanel(String text) {
		setLayout(new GridLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		textArea.setEditable(true);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		lineNumberArea.setEnabled(false);
		lineNumberArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		lineNumberArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		panel.add(lineNumberArea, BorderLayout.LINE_START);
		panel.add(textArea, BorderLayout.CENTER);
		scrollPane = new JScrollPane(panel);
		add(scrollPane);
		setText(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!fontChecked) {
			int x = g.getFontMetrics(textArea.getFont()).getHeight();
			scrollPane.getVerticalScrollBar().setUnitIncrement(x);
			fontChecked = true;
		}
	}

	/**
	 * Sets the text of this {@link NumberedTextPanel} and updates the line
	 * numbers
	 * 
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		textArea.setText(text);
		String lineNumbers = "";
		for (int i = 1; i <= textArea.getLineCount(); i++) {
			lineNumbers += Integer.toString(i) + " \n";
		}
		lineNumberArea.setText(lineNumbers);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		textArea.setEnabled(enabled);
	}

	/**
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		textArea.setEditable(editable);
	}
}
