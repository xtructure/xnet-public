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
package com.xtructure.xnet.demos.oned.components;

import static com.xtructure.xutil.valid.ValidateUtils.isGreaterThanOrEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import com.xtructure.xsim.impl.AbstractStandardXSimulation;
import com.xtructure.xsim.impl.DataXTerminator;
import com.xtructure.xsim.impl.SimpleXBorder;
import com.xtructure.xsim.impl.TickXTerminator;
import com.xtructure.xsim.impl.XAddressImpl;
import com.xtructure.xutil.id.XId;

// TODO: Auto-generated Javadoc
/**
 * Simulation class for the 1D world.
 *
 * @author Luis Guimbarda
 */
public final class OneDSimulation extends AbstractStandardXSimulation {
	
	/** The Constant TICK_TERMINATOR_ID. */
	private static final XId TICK_TERMINATOR_ID;
	
	/** The Constant CATCH_TERMINATOR_ID. */
	private static final XId CATCH_TERMINATOR_ID;
	static {
		TICK_TERMINATOR_ID = XId.newId("OneDSimulation-TickTerminator");
		CATCH_TERMINATOR_ID = XId.newId("OneDSimulation-CatchTerminator");
	}

	/** The tick terminator. */
	private TickXTerminator tickTerminator = null;
	
	/** The catch terminator. */
	private DataXTerminator catchTerminator = null;
	
	/** The catch term border. */
	private SimpleXBorder catchTermBorder = null;
	
	/** The fitness. */
	private final OneDFitness fitness;

	/**
	 * Gets the sim.
	 *
	 * @param id the id for this simulation
	 * @param tickDelay the speed of simulations
	 * @param displayInterval the display interval
	 * @param fitness the fitness object to use in this simulation
	 * @param critter the critter object to use in this simulation
	 * @param world the world object to use in this simulation
	 * @return a new OneDSimulation sim
	 */
	public static OneDSimulation getSim(//
			XId id,//
			int tickDelay, long displayInterval,//
			final OneDFitness fitness, final OneDCritter critter, final OneDWorld world) {
		validateArg("tick delay", tickDelay, isGreaterThanOrEqualTo(0));
		validateArg("fitness", fitness, isNotNull());
		validateArg("critter", critter, isNotNull());
		validateArg("world", world, isNotNull());

		return new OneDSimulation(//
				id, tickDelay,//
				fitness, critter, world);
	}

	/**
	 * Creates a new OneDSimulation console sim.
	 * 
	 * @param id
	 *            the id for this simulation
	 * @param tickDelay
	 *            the speed of simulations
	 * @param fitness
	 *            the fitness object to use in this simulation
	 * @param critter
	 *            the critter object to use in this simulation
	 * @param world
	 *            the world object to use in this simulation
	 */
	private OneDSimulation(//
			XId id, int tickDelay,//
			final OneDFitness fitness, final OneDCritter critter, final OneDWorld world) {
		super(id);

		this.fitness = fitness;

		addComponent(critter);
		addComponent(world);
		addComponent(fitness);

		CritterWorldBorder.getInstance(critter, world);
		FitnessWorldBorder.getInstance(fitness, world);

		setTickDelay(tickDelay);
	}

	/**
	 * Sets the tick bound.
	 *
	 * @param tickBound the tick bound
	 * @return the one d simulation
	 */
	public OneDSimulation setTickBound(Long tickBound) {
		if (tickTerminator != null) {
			removeComponent(tickTerminator);
		}
		if (tickBound != null) {
			tickTerminator = TickXTerminator.getInstance(TICK_TERMINATOR_ID,
					this, tickBound);
			addComponent(tickTerminator);
		} else {
			tickTerminator = null;
		}
		return this;
	}

	/**
	 * Sets the catch bound.
	 *
	 * @param catchBound the catch bound
	 * @return the one d simulation
	 */
	public OneDSimulation setCatchBound(Long catchBound) {
		if (catchTerminator != null) {
			removeComponent(catchTerminator);
			fitness.removeBorder(catchTermBorder);
		}
		if (catchBound != null) {
			catchTerminator = DataXTerminator.getInstance(CATCH_TERMINATOR_ID,
					OneDFitnessImpl.CATCH_COUNT_ID,
					isGreaterThanOrEqualTo(catchBound));
			addComponent(catchTerminator);

			catchTermBorder = new SimpleXBorder();
			catchTermBorder.addComponent(fitness);
			catchTermBorder.addComponent(catchTerminator);
			catchTermBorder.associate(//
					new XAddressImpl(fitness, OneDFitnessImpl.CATCH_COUNT_ID),//
					new XAddressImpl(catchTerminator,
							OneDFitnessImpl.CATCH_COUNT_ID));
		} else {
			catchTerminator = null;
			catchTermBorder = null;
		}
		return this;
	}
}
