/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xneat.
 *
 * xneat is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xneat is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xneat.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xneat.operators.impl;

import static com.xtructure.xutil.valid.ValidateUtils.isTrue;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;

import java.util.ArrayList;
import java.util.List;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xneat.genetics.Gene;
import com.xtructure.xneat.genetics.GeneMap;
import com.xtructure.xneat.genetics.NEATGeneticsFactory;
import com.xtructure.xneat.genetics.NEATGenome;
import com.xtructure.xneat.genetics.link.LinkGene;
import com.xtructure.xneat.genetics.node.NodeGene;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.Range;
import com.xtructure.xutil.ValueType;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.RangeXParameter;
import com.xtructure.xutil.config.XField;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * {@link AdjustAttributesMutateOperator} is a {@link MutateOperator} that
 * changes the values of fields in a {@link GeneMap}'s genes, rather than it's
 * topological configuration.
 * 
 * @author Luis Guimbarda
 * 
 */
public class AdjustAttributesMutateOperator extends AbstractNEATOperator<GeneMap> implements MutateOperator<GeneMap> {
	/** the {@link XValId} of the field this {@link MutateOperator} will adjust */
	private final XValId<?>	fieldId;
	/** indicates that this {@link MutateOperator} should try to mutate links */
	private final boolean	mutateLink;
	/** indicates that this {@link MutateOperator} should try to mutate nodes */
	private final boolean	mutateNode;
	/** the probability that any attribute in a gene will be adjusted */
	private final Double	mutateAttributeProbability;
	/** the probability that any gene will be selected for mutation */
	private final Double	mutateGeneProbability;

	/**
	 * Creates a new {@link AdjustAttributesMutateOperator}. It selects
	 * {@link Gene}s to mutate (link or node gene as indication by the given
	 * flags) with the given mutateGeneProbability, but always mutates at least
	 * one gene. Selected {@link Gene}s will have their fields adjusted with the
	 * given mutateAttributeProbability, but always adjusting at least one
	 * field.
	 * 
	 * @param mutateLink
	 *            indicates that this {@link MutateOperator} should try to
	 *            mutate links
	 * @param mutateNode
	 *            indicates that this {@link MutateOperator} should try to
	 *            mutate nodes
	 * @param mutateAttributeProbability
	 *            the probability that any attribute in a gene will be adjusted
	 * @param mutateGeneProbability
	 *            the probability that any gene will be selected for mutation
	 * @param geneticsFactory
	 *            the {@link NEATGeneticsFactory} used by this
	 *            {@link AdjustAttributesMutateOperator}
	 * @throws IllegalArgumentException
	 *             If neither mutateLink or mutateNode are true
	 */
	public AdjustAttributesMutateOperator(boolean mutateLink, boolean mutateNode, double mutateAttributeProbability, double mutateGeneProbability, NEATGeneticsFactory<GeneMap> geneticsFactory) {
		super(geneticsFactory);
		validateArg("mutateLink or mutateNode", mutateLink || mutateNode, isTrue());
		this.fieldId = null;
		this.mutateLink = mutateLink;
		this.mutateNode = mutateNode;
		this.mutateAttributeProbability = mutateAttributeProbability;
		this.mutateGeneProbability = mutateGeneProbability;
	}

	/**
	 * Creates a new {@link AdjustAttributesMutateOperator}. It mutates all
	 * {@link Gene} in the given {@link Genome}'s {@link GeneMap}, but only
	 * adjusts a single field as specified by the given fieldId.
	 * 
	 * @param mutateLink
	 *            indicates that this {@link MutateOperator} should try to
	 *            mutate links
	 * @param mutateNode
	 *            indicates that this {@link MutateOperator} should try to
	 *            mutate nodes
	 * @param fieldId
	 *            the {@link XValId} of the field whose value is to be adjusted
	 * @param geneticsFactory
	 *            the {@link NEATGeneticsFactory} used by this
	 *            {@link AdjustAttributesMutateOperator}
	 * @throws IllegalArgumentException
	 *             If neither mutateLink or mutateNode are true
	 */
	public AdjustAttributesMutateOperator(boolean mutateLink, boolean mutateNode, XValId<?> fieldId, NEATGeneticsFactory<GeneMap> geneticsFactory) {
		super(geneticsFactory);
		validateArg("mutateLink or mutateNode", mutateLink || mutateNode, isTrue());
		this.fieldId = fieldId;
		this.mutateLink = mutateLink;
		this.mutateNode = mutateNode;
		this.mutateAttributeProbability = null;
		this.mutateGeneProbability = null;
	}

	/**
	 * Creates a child {@link NEATGenome} by selecting {@link Gene}s in the
	 * given {@link Genome}'s {@link GeneMap} and adjusting them using
	 * {@link RandomUtil#randomlyAdjustParameterGaussian(double, Range)}.
	 * (Integer type data is converted to double before adjustment.)
	 * <P>
	 * 
	 * 
	 * @throws OperationFailedException
	 *             If there are no genes to mutate
	 * @see com.xtructure.xevolution.operator.MutateOperator#mutate(int,
	 *      com.xtructure.xevolution.genetics.Genome)
	 */
	@Override
	public NEATGenome<GeneMap> mutate(int idNumber, Genome<GeneMap> genome) throws OperationFailedException {
		getLogger().trace("begin %s.mutate(%s, %s)", getClass().getSimpleName(), idNumber, genome);
		boolean canMutateLinks = mutateLink && genome.getData().getLinkCount() > 0;
		boolean canMutateNodes = mutateNode && genome.getData().getNodeCount() > 0;
		if (!canMutateLinks && !canMutateNodes) {
			getLogger().trace("throwing exception: %s(\"%s\")", OperationFailedException.class.getName(), "no links or nodes to mutate");
			throw new OperationFailedException("no links or nodes to mutate");
		}
		List<LinkGene> links = genome.getData().getLinks();
		List<NodeGene> nodes = genome.getData().getNodes();
		boolean mutationOccurred = false;
		if (canMutateLinks) {
			mutationOccurred |= mutateLinks(links);
		}
		if (canMutateNodes) {
			mutationOccurred |= mutateNodes(nodes);
		}
		if (!mutationOccurred) {
			getLogger().trace("throwing exception: %s(\"%s\")", OperationFailedException.class.getName(), "mutation didn't occur");
			throw new OperationFailedException("mutation didn't occur");
		}
		NEATGenome<GeneMap> newGenome = (NEATGenome<GeneMap>) getGeneticsFactory().createGenome(idNumber, new GeneMap(nodes, links));
		newGenome.validate();
		getLogger().trace("will return: %s", newGenome);
		getLogger().trace("end %s.mutate()", getClass().getSimpleName());
		return newGenome;
	}

	/**
	 * Goes through the list of {@link LinkGene}s and selects links to mutate.
	 * 
	 * @param links
	 *            the {@link LinkGene}s to (potentially) mutate
	 * @return true if any of the {@link LinkGene}s were changed (whether it was
	 *         selected for mutation or not), false otherwise
	 */
	private boolean mutateLinks(List<LinkGene> links) {
		getLogger().trace("begin %s.mutateLinks(%s)", getClass().getSimpleName(), links);
		List<LinkGene> linksToRemove = new ArrayList<LinkGene>();
		List<LinkGene> linksToAdd = new ArrayList<LinkGene>();
		boolean mutationOccurred = false;
		for (LinkGene link : links) {
			if (mutateGeneProbability == null || RandomUtil.eventOccurs(mutateGeneProbability)) {
				LinkGene newLink = getGeneticsFactory().copyLinkGene(link.getId().getInstanceNum(), link);
				mutationOccurred = mutateAttributes(newLink);
				linksToRemove.add(link);
				linksToAdd.add(newLink);
			}
		}
		if (linksToAdd.isEmpty()) {
			// mutate at least one gene
			LinkGene link = RandomUtil.select(links);
			LinkGene newLink = getGeneticsFactory().copyLinkGene(link.getId().getInstanceNum(), link);
			mutationOccurred = mutateAttributes(newLink);
			linksToRemove.add(link);
			linksToAdd.add(newLink);
		}
		for (LinkGene link : linksToRemove) {
			links.remove(link);
		}
		for (LinkGene link : linksToAdd) {
			links.add(link);
		}
		getLogger().trace("will return: %s", mutationOccurred);
		getLogger().trace("end %s.mutateLinks()", getClass().getSimpleName());
		return mutationOccurred;
	}

	/**
	 * Goes through the list of {@link NodeGene} and selects nodes to mutate.
	 * 
	 * @param nodes
	 *            the {@link NodeGene}s to (potentially) mutate
	 * @return true if any of the {@link NodeGene} were changed (whether it was
	 *         selected for mutation or not), false otherwise
	 */
	private boolean mutateNodes(List<NodeGene> nodes) {
		getLogger().trace("begin %s.mutateNodes(%s)", getClass().getSimpleName(), nodes);
		List<NodeGene> nodesToAdd = new ArrayList<NodeGene>();
		List<NodeGene> nodesToRemove = new ArrayList<NodeGene>();
		boolean mutationOccurred = false;
		for (NodeGene node : nodes) {
			if (mutateGeneProbability == null || RandomUtil.eventOccurs(mutateGeneProbability)) {
				NodeGene newNode = getGeneticsFactory().copyNodeGene(node.getId().getInstanceNum(), node);
				mutationOccurred = mutateAttributes(newNode);
				nodesToRemove.add(node);
				nodesToAdd.add(newNode);
			}
		}
		if (nodesToAdd.isEmpty()) {
			// mutate at least one gene
			NodeGene node = RandomUtil.select(nodes);
			NodeGene newNode = getGeneticsFactory().copyNodeGene(node.getId().getInstanceNum(), node);
			mutationOccurred = mutateAttributes(newNode);
			nodesToRemove.add(node);
			nodesToAdd.add(newNode);
		}
		for (NodeGene node : nodesToRemove) {
			nodes.remove(node);
		}
		for (NodeGene node : nodesToAdd) {
			nodes.add(node);
		}
		getLogger().trace("will return: %s", mutationOccurred);
		getLogger().trace("end %s.mutateLinks()", getClass().getSimpleName());
		return mutationOccurred;
	}

	/**
	 * Goes throw {@link FieldMap} in the given {@link Gene} and selects fields
	 * to adjust.
	 * 
	 * @param gene
	 *            the Gene whose fields are to be adjusted
	 * @return true if any of the {@link XField}s were changed (whether it was
	 *         selected for mutation or not), false otherwise
	 */
	private boolean mutateAttributes(Gene gene) {
		getLogger().trace("begin %s.mutateAttibutes(%s)", getClass().getSimpleName(), gene);
		boolean mutationOccurred = false;
		if (fieldId == null) {
			// mutate multiple parameters
			for (XId fieldId : gene.getFieldMap().getFieldIds()) {
				if (RandomUtil.eventOccurs(mutateAttributeProbability)) {
					mutationOccurred |= adjust(gene, fieldId);
				}
			}
			if (!mutationOccurred) {
				// mutate at least one parameter
				XId fieldId = RandomUtil.select(gene.getFieldMap().getFieldIds());
				mutationOccurred = adjust(gene, fieldId);
			}
		} else {
			// mutate single specific parameter
			mutationOccurred = adjust(gene, fieldId);
		}
		getLogger().trace("will return: %s", mutationOccurred);
		getLogger().trace("end %s.mutateLinks()", getClass().getSimpleName());
		return mutationOccurred;
	}

	/**
	 * Adjusts the field with the given fieldId in the given gene. Currently,
	 * only adjusts boolean and floating point values.
	 * 
	 * @param gene
	 *            the gene to adjust
	 * @param fieldId
	 *            the id of the field in the gene to adjust
	 * @return true if the field changed, false otherwise
	 */
	@SuppressWarnings("unchecked")
	private boolean adjust(Gene gene, XId fieldId) {
		getLogger().trace("begin %s.adjust(%s, %s)", getClass().getSimpleName(), gene, fieldId);
		if (!(gene.getConfiguration().getParameter(fieldId) instanceof RangeXParameter)) {
			return false;
		}
		RangeXParameter<?> parameter = (RangeXParameter<?>) gene.getConfiguration().getParameter(fieldId);
		XField<?> field = gene.getFieldMap().getField(fieldId);
		if (field == null) {
			return false;
		}
		Object current = field.getValue();
		boolean mutationOccurred = false;
		switch (ValueType.getValueType(current.getClass())) {
			case FLOAT: {
				Range<Float> r = (Range<Float>) parameter.getLifetimeRange();
				Range<Double> range = Range.getInstance(//
						r == null || r.getMinimum() == null ? -Float.MAX_VALUE : r.getMinimum().doubleValue(),//
						r == null || r.getMaximum() == null ? Float.MAX_VALUE : r.getMaximum().doubleValue());
				Float f = (Float) current;
				float next = Double.valueOf(RandomUtil.randomlyAdjustParameterGaussian(f, range)).floatValue();
				mutationOccurred = !((XField<Float>) gene.getFieldMap().getField(fieldId)).setValue(next).equals(current);
				break;
			}
			case DOUBLE: {
				Range<Double> r = (Range<Double>) parameter.getLifetimeRange();
				Range<Double> range = Range.getInstance(//
						r == null || r.getMinimum() == null ? -Double.MAX_VALUE : r.getMinimum().doubleValue(),//
						r == null || r.getMaximum() == null ? Double.MAX_VALUE : r.getMaximum().doubleValue());
				Double d = (Double) current;
				double next = RandomUtil.randomlyAdjustParameterGaussian(d, range);
				mutationOccurred = !((XField<Double>) gene.getFieldMap().getField(fieldId)).setValue(next).equals(current);
				break;
			}
			case BYTE: {
				// Range<Byte> r = (Range<Byte>) parameter.getLifetimeRange();
				// Range<Double> range = Range.getInstance(//
				// r == null || r.getMinimum() == null ? Byte.MIN_VALUE :
				// r.getMinimum().doubleValue(),//
				// r == null || r.getMaximum() == null ? Byte.MAX_VALUE :
				// r.getMaximum().doubleValue());
				// Byte b = (Byte) current;
				// byte next =
				// Double.valueOf(RandomUtil.randomlyAdjustParameterUniform(b,
				// range)).byteValue();
				// mutationOccurred = ((XField<Byte>)
				// gene.getFieldMap().getField(fieldId)).setValue(next) !=
				// current;
				break;
			}
			case SHORT: {
				// Range<Short> r = (Range<Short>) parameter.getLifetimeRange();
				// Range<Double> range = Range.getInstance(//
				// r == null || r.getMinimum() == null ? Short.MIN_VALUE :
				// r.getMinimum().doubleValue(),//
				// r == null || r.getMaximum() == null ? Short.MAX_VALUE :
				// r.getMaximum().doubleValue());
				// Short s = (Short) current;
				// short next =
				// Double.valueOf(RandomUtil.randomlyAdjustParameterUniform(s,
				// range)).shortValue();
				// mutationOccurred = ((XField<Short>)
				// gene.getFieldMap().getField(fieldId)).setValue(next) !=
				// current;
				break;
			}
			case INTEGER: {
				// Range<Integer> r = (Range<Integer>)
				// parameter.getLifetimeRange();
				// Range<Double> range = Range.getInstance(//
				// r == null || r.getMinimum() == null ? Integer.MIN_VALUE :
				// r.getMinimum().doubleValue(),//
				// r == null || r.getMaximum() == null ? Integer.MAX_VALUE :
				// r.getMaximum().doubleValue());
				// Integer i = (Integer) current;
				// int next =
				// Double.valueOf(RandomUtil.randomlyAdjustParameterUniform(i,
				// range)).intValue();
				// mutationOccurred = ((XField<Integer>)
				// gene.getFieldMap().getField(fieldId)).setValue(next) !=
				// current;
				break;
			}
			case LONG: {
				// Range<Long> r = (Range<Long>) parameter.getLifetimeRange();
				// Range<Double> range = Range.getInstance(//
				// r == null || r.getMinimum() == null ? Long.MIN_VALUE :
				// r.getMinimum().doubleValue(),//
				// r == null || r.getMaximum() == null ? Long.MAX_VALUE :
				// r.getMaximum().doubleValue());
				// Long l = (Long) current;
				// long next =
				// Double.valueOf(RandomUtil.randomlyAdjustParameterUniform(l,
				// range)).longValue();
				// mutationOccurred = ((XField<Long>)
				// gene.getFieldMap().getField(fieldId)).setValue(next) !=
				// current;
				break;
			}
			case BOOLEAN: {
				mutationOccurred = ((XField<Boolean>) gene.getFieldMap().getField(fieldId)).setValue(!((Boolean) current)) != current;
				break;
			}
			default: {
				// do nothing
			}
		}
		getLogger().trace("will return: %s", mutationOccurred);
		getLogger().trace("end %s.mutateLinks()", getClass().getSimpleName());
		return mutationOccurred;
	}
}
