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
package com.xtructure.xevolution.genetics.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.containsElements;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isFalse;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.isTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.testng.annotations.Test;

import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.RandomUtil;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestAbstractPopulation {
	public void constructorSucceeds() {
		assertThat("",//
				new DummyPopulation(1), isNotNull());
	}

	public void addAndGetBehaveAsExpected() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome = new DummyGenome(1, "data");
		assertThat("",//
				pop.get(genome.getId()), isNull());
		pop.add(genome);
		assertThat("",//
				pop.get(genome.getId()), isSameAs(genome));
	}

	public void addAllBehavesAsExpected() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome = new DummyGenome(1, "data");
		assertThat("",//
				pop.get(genome.getId()), isNull());
		pop.addAll(Collections.singleton(genome));
		assertThat("",//
				pop.get(genome.getId()), isSameAs(genome));
	}

	public void clearAndIsEmptyBehaveAsExpected() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome = new DummyGenome(1, "data");
		assertThat("",//
				pop.isEmpty(), isTrue());
		pop.add(genome);
		assertThat("",//
				pop.isEmpty(), isFalse());
		pop.clear();
		assertThat("",//
				pop.isEmpty(), isTrue());
	}

	public void containsReturnsExpectedBoolean() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome = new DummyGenome(1, "data");
		assertThat("",//
				pop.contains(genome), isFalse());
		pop.add(genome);
		assertThat("",//
				pop.contains(genome), isTrue());
	}

	public void containsAllReturnsExpectedBoolean() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome = new DummyGenome(1, "data");
		assertThat("",//
				pop.containsAll(Collections.singleton(genome)), isFalse());
		pop.add(genome);
		assertThat("",//
				pop.containsAll(Collections.singleton(genome)), isTrue());
	}

	public void getAndIncrementAgeReturnsExpectedLong() {
		DummyPopulation pop = new DummyPopulation(1);
		assertThat("",//
				pop.getAge(), isEqualTo(0l));
		pop.incrementAge();
		assertThat("",//
				pop.getAge(), isEqualTo(1l));
	}

	public void getAgeLastImprovedReturnsExpectedLong() {
		// see refreshStatsBehavesAsExpected()
	}

	public void getBaseId() {
		assertThat("",//
				new DummyPopulation(1).getBaseId(),//
				isEqualTo(AbstractPopulation.POPULATION_BASE_ID));
	}

	public void getGenomeAttributeIds() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome = new DummyGenome(1, "data");
		assertThat("",//
				pop.getGenomeAttributeIds(), isEmpty());
		pop.add(genome);
		assertThat("",//
				pop.getGenomeAttributeIds(), isEmpty());
		pop.refreshStats();
		assertThat("",//
				pop.getGenomeAttributeIds(),//
				hasSize(3),//
				containsElements(//
						Genome.AGE_ATTRIBUTE_ID,//
						Genome.EVAL_COUNT_ATTRIBUTE_ID,//
						Genome.FITNESS_ATTRIBUTE_ID));
	}

	public void getGenomeIdNumberReturnsExpectedInt() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome = new DummyGenome(57, "data");
		assertThat("",//
				pop.getGenomeIdNumber(), isEqualTo(0));
		pop.add(genome);
		assertThat("",//
				pop.getGenomeIdNumber(), isEqualTo(58));
		pop.addAll(Collections.singleton(new DummyGenome(89, "data")));
		assertThat("",//
				pop.getGenomeIdNumber(), isEqualTo(90));
	}

	public void getHighestEverGenomeByAttributeReturnsExpectedGenome() {//
		// see refreshStatsBehavesAsExpected()
	}

	public void getHighestGenomeByAttriubuteReturnsExpectedGenome() {//
		// see refreshStatsBehavesAsExpected()
	}

	public void getLowestEverGenomeByAttributeReturnsExpectedGenome() {//
		// see refreshStatsBehavesAsExpected()
	}

	public void getLowestGenomeByAttriubuteReturnsExpectedGenome() {//
		// see refreshStatsBehavesAsExpected()
	}

	@SuppressWarnings("unchecked")
	public void iteratorReturnsExpectedIterator() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		Genome<String> genome1 = new DummyGenome(RandomUtil.nextInteger(100) + 100, "data");
		Genome<String> genome2 = new DummyGenome(RandomUtil.nextInteger(100) + 200, "data");
		pop.addAll(Arrays.asList(genome0, genome1, genome2));
		Set<Genome<String>> gotSet = new HashSet<Genome<String>>();
		Iterator<Genome<String>> iter = pop.iterator();
		while (iter.hasNext()) {
			gotSet.add(iter.next());
		}
		assertThat("",//
				gotSet.containsAll(pop), isTrue());
		assertThat("",//
				pop.containsAll(gotSet), isTrue());
	}

	@SuppressWarnings("unchecked")
	public void keySetReturnsExpectedKeySet() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		Genome<String> genome1 = new DummyGenome(RandomUtil.nextInteger(100) + 100, "data");
		Genome<String> genome2 = new DummyGenome(RandomUtil.nextInteger(100) + 200, "data");
		pop.addAll(Arrays.asList(genome0, genome1, genome2));
		Set<XId> ids = new HashSet<XId>();
		ids.addAll(Arrays.asList(genome0.getId(), genome1.getId(), genome2.getId()));
		assertThat("",//
				pop.keySet(), isEqualTo(ids));
	}

	private static final XValId<Float>		FLOAT_ID	= XValId.newId("float", Float.class);
	private static final XValId<Long>		LONG_ID		= XValId.newId("long", Long.class);
	private static final XValId<Integer>	INTEGER_ID	= XValId.newId("integer", Integer.class);
	private static final XValId<Byte>		BYTE_ID		= XValId.newId("byte", Byte.class);
	private static final XValId<Short>		SHORT_ID	= XValId.newId("short", Short.class);

	public void refreshStatsBehavesAsExpected() {
		DummyPopulation population = new DummyPopulation(0);
		Map<XValId<?>, Genome<?>> highGenomes = new HashMap<XValId<?>, Genome<?>>();
		Map<XValId<?>, Genome<?>> lowGenomes = new HashMap<XValId<?>, Genome<?>>();
		Map<XValId<?>, Double> averageMeasures = new HashMap<XValId<?>, Double>();
		ValueMap attributes = new ValueMap();
		attributes.set(Population.AGE_ATTRIBUTE_ID, 0l);
		attributes.set(Population.AGE_LI_ATTRIBUTE_ID, 0l);
		attributes.set(Population.GENOME_NUM_ATTRIBUTE_ID, 0);
		population.refreshStats();
		String expected = new ToStringBuilder(population, ToStringStyle.MULTI_LINE_STYLE)//
				.append("id", population.getId())//
				.append("size", 0)//
				.append("attributes", attributes)//
				.append("highGenomes", mapToString(highGenomes))//
				.append("lowGenomes", mapToString(lowGenomes))//
				.append("averageMeasures", mapToString(averageMeasures))//
				.toString();
		assertThat("",//
				population.toString(), isEqualTo(expected));
		assertThat("",//
				population.getAgeLastImproved(), isEqualTo(0l));
		assertThat("",//
				population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		assertThat("",//
				population.getLowestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		assertThat("",//
				population.getHighestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		assertThat("",//
				population.getLowestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		assertThat("",//
				population.getAverageGenomeAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		population.validate();
		population.incrementAge();
		population.incrementAge();
		attributes.set(Population.AGE_ATTRIBUTE_ID, 2l);
		attributes.set(Population.GENOME_NUM_ATTRIBUTE_ID, 2);
		GenomeImpl genome0 = new GenomeImpl(0, "");
		GenomeImpl genome1 = new GenomeImpl(1, "");
		genome1.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		genome1.setAttribute(FLOAT_ID, 1.0f);
		genome1.setAttribute(LONG_ID, 1l);
		genome1.setAttribute(INTEGER_ID, 1);
		genome1.setAttribute(SHORT_ID, (short) 1);
		genome1.setAttribute(BYTE_ID, (byte) 1);
		population.add(genome0);
		population.add(genome1);
		expected = new ToStringBuilder(population, ToStringStyle.MULTI_LINE_STYLE)//
				.append("id", population.getId())//
				.append("size", 2)//
				.append("attributes", attributes)//
				.append("highGenomes", mapToString(highGenomes))//
				.append("lowGenomes", mapToString(lowGenomes))//
				.append("averageMeasures", mapToString(averageMeasures))//
				.toString();
		assertThat("",//
				population.toString(), isEqualTo(expected));
		assertThat("",//
				population.getAgeLastImproved(), isEqualTo(0l));
		assertThat("",//
				population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		assertThat("",//
				population.getLowestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		assertThat("",//
				population.getHighestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		assertThat("",//
				population.getLowestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		assertThat("",//
				population.getAverageGenomeAttribute(Genome.FITNESS_ATTRIBUTE_ID), isNull());
		population.validate();
		population.refreshStats();
		attributes.set(Population.AGE_LI_ATTRIBUTE_ID, 2l);
		highGenomes.put(Genome.FITNESS_ATTRIBUTE_ID, genome1);
		highGenomes.put(Genome.AGE_ATTRIBUTE_ID, genome1);
		highGenomes.put(FLOAT_ID, genome1);
		highGenomes.put(LONG_ID, genome1);
		highGenomes.put(INTEGER_ID, genome1);
		highGenomes.put(SHORT_ID, genome1);
		highGenomes.put(BYTE_ID, genome1);
		highGenomes.put(Genome.COMPLEXITY_ATTRIBUTE_ID, genome1);
		highGenomes.put(Genome.EVAL_COUNT_ATTRIBUTE_ID, genome1);
		lowGenomes.put(Genome.FITNESS_ATTRIBUTE_ID, genome0);
		lowGenomes.put(Genome.AGE_ATTRIBUTE_ID, genome0);
		lowGenomes.put(FLOAT_ID, genome0);
		lowGenomes.put(LONG_ID, genome0);
		lowGenomes.put(INTEGER_ID, genome0);
		lowGenomes.put(SHORT_ID, genome0);
		lowGenomes.put(BYTE_ID, genome0);
		lowGenomes.put(Genome.COMPLEXITY_ATTRIBUTE_ID, genome0);
		lowGenomes.put(Genome.EVAL_COUNT_ATTRIBUTE_ID, genome0);
		averageMeasures.put(Genome.FITNESS_ATTRIBUTE_ID, 0.5);
		averageMeasures.put(Genome.AGE_ATTRIBUTE_ID, 0.0);
		averageMeasures.put(Genome.EVAL_COUNT_ATTRIBUTE_ID, 0.0);
		averageMeasures.put(Genome.COMPLEXITY_ATTRIBUTE_ID, 0.0);
		averageMeasures.put(FLOAT_ID, 0.5);
		averageMeasures.put(LONG_ID, 0.5);
		averageMeasures.put(INTEGER_ID, 0.5);
		averageMeasures.put(SHORT_ID, 0.5);
		averageMeasures.put(BYTE_ID, 0.5);
		expected = new ToStringBuilder(population, ToStringStyle.MULTI_LINE_STYLE)//
				.append("id", population.getId())//
				.append("size", 2)//
				.append("attributes", attributes)//
				.append("highGenomes", mapToString(highGenomes))//
				.append("lowGenomes", mapToString(lowGenomes))//
				.append("averageMeasures", mapToString(averageMeasures))//
				.toString();
		assertThat("",//
				population.toString(), isEqualTo(expected));
		assertThat("",//
				population.getAgeLastImproved(), isEqualTo(2l));
		assertThat("",//
				population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome1));
		assertThat("",//
				population.getLowestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome0));
		assertThat("",//
				population.getHighestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome1));
		assertThat("",//
				population.getLowestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome0));
		assertThat("",//
				population.getAverageGenomeAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(0.5));
		population.validate();
		GenomeImpl genome2 = new GenomeImpl(2, "");
		genome2.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, 5.0);
		genome2.setAttribute(FLOAT_ID, 5.0f);
		genome2.setAttribute(LONG_ID, 5l);
		genome2.setAttribute(INTEGER_ID, 5);
		genome2.setAttribute(SHORT_ID, (short) 0);
		genome2.setAttribute(BYTE_ID, (byte) 0);
		population.add(genome2);
		population.incrementAge();
		population.refreshStats();
		attributes.set(Population.AGE_ATTRIBUTE_ID, 3l);
		attributes.set(Population.AGE_LI_ATTRIBUTE_ID, 3l);
		attributes.set(Population.GENOME_NUM_ATTRIBUTE_ID, 3);
		highGenomes.put(Genome.FITNESS_ATTRIBUTE_ID, genome2);
		highGenomes.put(Genome.AGE_ATTRIBUTE_ID, genome2);
		highGenomes.put(LONG_ID, genome2);
		highGenomes.put(FLOAT_ID, genome2);
		highGenomes.put(INTEGER_ID, genome2);
		highGenomes.put(Genome.COMPLEXITY_ATTRIBUTE_ID, genome2);
		highGenomes.put(Genome.EVAL_COUNT_ATTRIBUTE_ID, genome2);
		averageMeasures.put(Genome.FITNESS_ATTRIBUTE_ID, 2.0);
		averageMeasures.put(Genome.AGE_ATTRIBUTE_ID, 1.0);
		averageMeasures.put(LONG_ID, 2.0);
		averageMeasures.put(FLOAT_ID, 2.0);
		averageMeasures.put(INTEGER_ID, 2.0);
		averageMeasures.put(SHORT_ID, 1 / 3.0);
		averageMeasures.put(BYTE_ID, 1 / 3.0);
		expected = new ToStringBuilder(population, ToStringStyle.MULTI_LINE_STYLE)//
				.append("id", population.getId())//
				.append("size", 3)//
				.append("attributes", attributes)//
				.append("highGenomes", mapToString(highGenomes))//
				.append("lowGenomes", mapToString(lowGenomes))//
				.append("averageMeasures", mapToString(averageMeasures))//
				.toString();
		assertThat("",//
				population.toString(), isEqualTo(expected));
		assertThat("",//
				population.getAgeLastImproved(), isEqualTo(3l));
		assertThat("",//
				population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome2));
		assertThat("",//
				population.getLowestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome0));
		assertThat("",//
				population.getHighestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome2));
		assertThat("",//
				population.getLowestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome0));
		assertThat("",//
				population.getAverageGenomeAttribute(Genome.FITNESS_ATTRIBUTE_ID), isEqualTo(2.0));
		population.validate();
		population.add(genome2);
		population.refreshStats();
		assertThat("",//
				population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome2));
		assertThat("",//
				population.getLowestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome0));
		assertThat("",//
				population.getHighestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome2));
		assertThat("",//
				population.getLowestEverGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome0));
	}

	private static String mapToString(Map<XValId<?>, ?> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		List<XValId<?>> ids = Arrays.asList(map.keySet().toArray(new XValId<?>[0]));
		Collections.sort(ids);
		if (ids.size() > 0) {
			sb.append(String.format("%s=%s", ids.get(0).toString(), map.get(ids.get(0))));
			for (int i = 1; i < ids.size(); i++) {
				sb.append(String.format(", %s=%s", ids.get(i).toString(), map.get(ids.get(i))));
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public void removeBehavesAsExpected() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		pop.add(genome0);
		assertThat("",//
				pop.contains(genome0), isTrue());
		boolean changed = pop.remove(genome0);
		assertThat("",//
				pop.contains(genome0), isFalse());
		assertThat("",//
				changed, isTrue());
		changed = pop.remove(genome0);
		assertThat("",//
				pop.contains(genome0), isFalse());
		assertThat("",//
				changed, isFalse());
		pop.add(genome0);
		assertThat("",//
				pop.contains(genome0), isTrue());
		changed = pop.remove(genome0.getId());
		assertThat("",//
				pop.contains(genome0), isFalse());
		assertThat("",//
				changed, isTrue());
		changed = pop.remove(genome0.getId());
		assertThat("",//
				pop.contains(genome0), isFalse());
		assertThat("",//
				changed, isFalse());
	}

	@SuppressWarnings("unchecked")
	public void removeAllBehavesAsExpected() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		Genome<String> genome1 = new DummyGenome(RandomUtil.nextInteger(100) + 100, "data");
		Genome<String> genome2 = new DummyGenome(RandomUtil.nextInteger(100) + 200, "data");
		boolean changed = pop.addAll(Arrays.asList(genome0, genome1, genome2));
		assertThat("",//
				pop.containsAll(Arrays.asList(genome0, genome1, genome2)), isTrue());
		assertThat("",//
				changed, isTrue());
		changed = pop.addAll(Arrays.asList(genome0, genome1, genome2));
		assertThat("",//
				pop.containsAll(Arrays.asList(genome0, genome1, genome2)), isTrue());
		assertThat("",//
				changed, isFalse());
		changed = pop.removeAll(Arrays.asList(genome0, genome1));
		assertThat("",//
				pop.contains(genome0), isFalse());
		assertThat("",//
				pop.contains(genome1), isFalse());
		assertThat("",//
				pop.contains(genome2), isTrue());
		assertThat("",//
				changed, isTrue());
		changed = pop.removeAll(Arrays.asList(genome0, genome1));
		assertThat("",//
				pop.contains(genome0), isFalse());
		assertThat("",//
				pop.contains(genome1), isFalse());
		assertThat("",//
				pop.contains(genome2), isTrue());
		assertThat("",//
				changed, isFalse());
	}

	@SuppressWarnings("unchecked")
	public void retainAllBehavesAsExpected() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		Genome<String> genome1 = new DummyGenome(RandomUtil.nextInteger(100) + 100, "data");
		Genome<String> genome2 = new DummyGenome(RandomUtil.nextInteger(100) + 200, "data");
		pop.addAll(Arrays.asList(genome0, genome1, genome2));
		assertThat("",//
				pop, hasSize(3), containsElements(genome0, genome1, genome2));
		pop.retainAll(Collections.singleton(genome0));
		assertThat("",//
				pop, hasSize(1), containsElement(genome0));
	}

	@SuppressWarnings("unchecked")
	public void removeDeadGenomesBehavesAsExpected() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		Genome<String> genome1 = new DummyGenome(RandomUtil.nextInteger(100) + 100, "data");
		Genome<String> genome2 = new DummyGenome(RandomUtil.nextInteger(100) + 200, "data");
		pop.addAll(Arrays.asList(genome0, genome1, genome2));
		assertThat("",//
				pop.containsAll(Arrays.asList(genome0, genome1, genome2)), isTrue());
		genome1.markForDeath();
		genome2.markForDeath();
		assertThat("",//
				pop.containsAll(Arrays.asList(genome0, genome1, genome2)), isTrue());
		pop.removeDeadGenomes();
		assertThat("",//
				pop.contains(genome0), isTrue());
		assertThat("",//
				pop.contains(genome1), isFalse());
		assertThat("",//
				pop.contains(genome2), isFalse());
	}

	@SuppressWarnings("unchecked")
	public void sizeReturnsExpectedInt() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		Genome<String> genome1 = new DummyGenome(RandomUtil.nextInteger(100) + 100, "data");
		Genome<String> genome2 = new DummyGenome(RandomUtil.nextInteger(100) + 200, "data");
		assertThat("",//
				pop.size(), isEqualTo(0));
		pop.addAll(Arrays.asList(genome0, genome1, genome2));
		assertThat("",//
				pop.size(), isEqualTo(3));
	}

	@SuppressWarnings("unchecked")
	public void toArrayReturnsExpectedArray() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		Genome<String> genome1 = new DummyGenome(RandomUtil.nextInteger(100) + 100, "data");
		Genome<String> genome2 = new DummyGenome(RandomUtil.nextInteger(100) + 200, "data");
		pop.addAll(Arrays.asList(genome0, genome1, genome2));
		{
			Object[] array = pop.toArray();
			assertThat("",//
					pop, hasSize(array.length));
			Set<Object> seen = new HashSet<Object>();
			for (Object o : array) {
				assertThat("",//
						pop, containsElement(o));
				seen.add(o);
			}
		}
		{
			Genome<?>[] array = pop.toArray(new Genome<?>[0]);
			assertThat("",//
					pop, hasSize(array.length));
			Set<Object> seen = new HashSet<Object>();
			for (Object o : array) {
				assertThat("",//
						pop, containsElement(o));
				seen.add(o);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void toStringReturnsExpectedString() {
		DummyPopulation pop = new DummyPopulation(1);
		Genome<String> genome0 = new DummyGenome(RandomUtil.nextInteger(100), "data");
		Genome<String> genome1 = new DummyGenome(RandomUtil.nextInteger(100) + 100, "data");
		genome1.setFitness(1.0);
		genome1.incrementEvaluationCount();
		genome1.incrementAge();
		Genome<String> genome2 = new DummyGenome(RandomUtil.nextInteger(100) + 200, "data");
		genome2.setFitness(2.0);
		genome2.incrementEvaluationCount();
		genome2.incrementEvaluationCount();
		genome2.incrementAge();
		genome2.incrementAge();
		pop.addAll(Arrays.asList(genome0, genome1, genome2));
		pop.incrementAge();
		pop.refreshStats();
		ValueMap attributes = new ValueMap();
		attributes.setAll(pop.getAttributes());
		Map<XValId<?>, Genome<?>> highest = new HashMap<XValId<?>, Genome<?>>();
		Map<XValId<?>, Genome<?>> lowest = new HashMap<XValId<?>, Genome<?>>();
		Map<XValId<?>, Double> averages = new HashMap<XValId<?>, Double>();
		highest.put(Genome.FITNESS_ATTRIBUTE_ID, genome2);
		highest.put(Genome.EVAL_COUNT_ATTRIBUTE_ID, genome2);
		highest.put(Genome.AGE_ATTRIBUTE_ID, genome2);
		lowest.put(Genome.FITNESS_ATTRIBUTE_ID, genome0);
		lowest.put(Genome.EVAL_COUNT_ATTRIBUTE_ID, genome0);
		lowest.put(Genome.AGE_ATTRIBUTE_ID, genome0);
		averages.put(Genome.FITNESS_ATTRIBUTE_ID, 1.0);
		averages.put(Genome.EVAL_COUNT_ATTRIBUTE_ID, 1.0);
		averages.put(Genome.AGE_ATTRIBUTE_ID, 2.0);
		String expected = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)//
				.append("id", AbstractPopulation.POPULATION_BASE_ID.createChild(1))//
				.append("size", 3)//
				.append("attributes", attributes)//
				.append("highGenomes", mapToString(highest))//
				.append("lowGenomes", mapToString(lowest))//
				.append("averageMeasures", mapToString(averages))//
				.toString();
		expected = expected.substring(expected.indexOf('\n'));
		String popString = pop.toString();
		popString = popString.substring(popString.indexOf('\n'));
		assertThat("",//
				popString, isEqualTo(expected));
	}

	@Test(expectedExceptions = { IllegalStateException.class })
	public void validateThrowsExceptionWhenHighestGenomeIsNotInPopulation() {
		DummyPopulation population = new DummyPopulation(0);
		GenomeImpl genome = new GenomeImpl(0, "");
		population.add(genome);
		population.refreshStats();
		assertThat("",//
				population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome));
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, RandomUtil.nextDouble());
		population.refreshStats();
		assertThat("",//
				population.getHighestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome));
		population.remove(genome);
		population.validate();
	}

	@Test(expectedExceptions = { IllegalStateException.class })
	public void validateThrowsExceptionWhenLowestGenomeIsNotInPopulation() {
		DummyPopulation population = new DummyPopulation(0);
		GenomeImpl genome = new GenomeImpl(0, "");
		population.add(genome);
		population.refreshStats();
		assertThat("",//
				population.getLowestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome));
		genome.setAttribute(Genome.FITNESS_ATTRIBUTE_ID, RandomUtil.nextDouble());
		population.refreshStats();
		assertThat("",//
				population.getLowestGenomeByAttribute(Genome.FITNESS_ATTRIBUTE_ID), isSameAs(genome));
		population.remove(genome);
		population.validate();
	}

	public void writeBehavesAsExpected() {
		// see UTestPopulationImpl
	}
}
