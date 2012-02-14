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

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.xtructure.xevolution.gui.components.Graph.TestFrame;

/**
 * @author Luis Guimbarda
 * 
 */
public class GuiCapture implements Runnable {
	/** frames per second */
	private static final double	FPS		= 30;
	/** frames per millisecond */
	private static final double	FPMS	= FPS / 1000;
	/** milliseconds per frame */
	private static final long	MSPF	= (long) (1 / FPMS);

	public static void main(String[] args) {
		File outDir = new File("tmp/capture");
		outDir.mkdirs();
		TestFrame tf = new TestFrame(new Graph("TEST", 100, 2));
		GuiCapture capture = new GuiCapture(outDir, ImageFormat.PNG);
		capture.setComponent(tf);
		capture.start();
	}

	private final File			outDir;
	private final ImageFormat	imageFormat;
	private boolean				stopped;
	private Component			component	= null;
	private Thread				thread		= null;

	public GuiCapture(File outDir, ImageFormat imageFormat) {
		this.outDir = outDir;
		this.imageFormat = imageFormat;
		this.stopped = true;
	}

	public Component getComponent() {
		return this.component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public File getOutDir() {
		return this.outDir;
	}

	public ImageFormat getImageFormat() {
		return this.imageFormat;
	}

	public boolean isStopped() {
		return this.stopped;
	}

	public void start() {
		stopped = false;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		stopped = true;
		try {
			thread.join();
		} catch (InterruptedException e) {}
	}

	@Override
	public void run() {
		while (!stopped) {
			long s = System.currentTimeMillis();

			try {
				BufferedImage img = new BufferedImage(//
						component.getWidth(),//
						component.getHeight(),//
						BufferedImage.TYPE_INT_RGB);
				component.paint(img.getGraphics());
				ImageIO.write(img, imageFormat.toString(), new File(outDir, String.format("test%d.%s", s, imageFormat)));

			} catch (IOException e) {
				e.printStackTrace();
			}

			long t = System.currentTimeMillis() - s;
			try {
				Thread.sleep(Math.max(2, MSPF - t));
			} catch (InterruptedException e) {}
		}
	}

	public enum ImageFormat {
		BMP, GIF, JPG, PNG;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}
}
