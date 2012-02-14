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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.id.AbstractXIdObject;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XIdObjectManager;
import com.xtructure.xutil.id.XIdObjectManagerImpl;
import com.xtructure.xutil.id.XValId;

/**
 * @author Luis Guimbarda
 * 
 */
public class GenealogyData extends AbstractXIdObject {
	private static final XIdObjectManagerImpl<GenealogyData>	MANAGER	= new XIdObjectManagerImpl<GenealogyData>() {};

	public static XIdObjectManager<GenealogyData> getManager() {
		return MANAGER;
	}

	public static GenealogyData getInstance(Genome<?> genome, File populationFile) {
		GenealogyData data = MANAGER.getObject(genome.getId());
		if (data == null) {
			data = new GenealogyData(genome, populationFile);
		}
		return data;
	}

	private final File		populationFile;
	private final double	fitness;
	private final XId		parent1Id;
	private final XId		parent2Id;
	private final String	appliedOp;
	private final List<XId>	children;

	@SuppressWarnings("unchecked")
	private GenealogyData(Genome<?> genome, File populationFile) {
		super(genome.getId(), MANAGER);
		this.populationFile = populationFile;
		this.fitness = genome.getFitness();
		this.parent1Id = genome.getAttribute(Genome.PARENT1_ID);
		this.parent2Id = genome.getAttribute(Genome.PARENT2_ID);
		XValId<String> opId = Genome.APPLIED_OP_ID;
		for (XValId<?> id : genome.getAttributes().keySet()) {
			if (id.getBase().contains("@")) {
				opId = (XValId<String>) id;
				break;
			}
		}
		this.appliedOp = genome.getAttribute(opId);
		this.children = new ArrayList<XId>();
		GenealogyData parent1Data = MANAGER.getObject(parent1Id);
		GenealogyData parent2Data = MANAGER.getObject(parent2Id);
		if (parent1Data != null) {
			parent1Data.addChildren(getId());
		}
		if (parent2Data != null) {
			parent2Data.addChildren(getId());
		}
	}

	public double getFitness() {
		return this.fitness;
	}

	public File getPopulationFile() {
		return this.populationFile;
	}

	public XId getParent1Id() {
		return this.parent1Id;
	}

	public XId getParent2Id() {
		return this.parent2Id;
	}

	public String getAppliedOp() {
		return this.appliedOp;
	}

	public List<XId> getChildren() {
		return Collections.unmodifiableList(this.children);
	}

	public void addChildren(XId... ids) {
		for (XId id : ids) {
			GenealogyData childData = MANAGER.getObject(id);
			if (childData != null) {
				children.add(id);
			}
		}
	}

	@Override
	public String toString() {
		return getId().toString();
	}
}
