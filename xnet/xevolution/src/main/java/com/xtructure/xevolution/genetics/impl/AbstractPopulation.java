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

import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.hasElements;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.validateArg;
import static com.xtructure.xutil.valid.ValidateUtils.validateState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xtructure.xevolution.genetics.GeneticsObject;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xevolution.genetics.Population;
import com.xtructure.xutil.ValueMap;
import com.xtructure.xutil.ValueType;
import com.xtructure.xutil.id.XId;
import com.xtructure.xutil.id.XValId;
import com.xtructure.xutil.xml.XmlUnit.Element;
import com.xtructure.xutil.xml.XmlWriter;

/**
 * {@link AbstractPopulation} implements the getters for the {@link Population}
 * interface and the {@link Collection} methods. It also includes the
 * functionality for removing dead genomes, writing the population to a file,
 * and refreshing the {@link Population}'s statistics. The statistics maintained
 * include the genomes with the highest or lowest value of any genome (number)
 * attribute present, the highest or lowest ever such genomes (which may or may
 * not belong to the current population), and the average of those attributes
 * from among those genomes currently in the population.
 * <P>
 * The implementation of validate ensures that the statistics lowest/highest
 * genome statistics point to genomes currently present in the population, and
 * that the population keys genomes by their own id.
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used in {@link Genome}s
 */
public abstract class AbstractPopulation<D> extends AbstractGeneticsObject implements Population<D> {
	/** base id for {@link Population} objects */
	public static final XId						POPULATION_BASE_ID	= XId.newId("Population");
	/**
	 * map to all {@link Genome}s in this {@link Population} from their
	 * {@link XId}s
	 */
	private final Map<XId, Genome<D>>			genomes;
	/**
	 * map from attribute {@link XValId}s to those {@link Genome}s in this
	 * {@link Population} with the highest such attribute
	 */
	protected final Map<XValId<?>, Genome<D>>	highestGenomes;
	/**
	 * map from attribute {@link XValId}s to those {@link Genome}s in this
	 * {@link Population} with the lowest such attribute
	 */
	protected final Map<XValId<?>, Genome<D>>	lowestGenomes;
	/**
	 * map from attribute {@link XValId} to those {@link Genome}s ever in this
	 * {@link Population} with the highest attribute
	 */
	protected final Map<XValId<?>, Genome<D>>	highestEverGenomes;
	/**
	 * map from attribute {@link XValId} to those {@link Genome}s ever in this
	 * {@link Population} with the lowest attribute
	 */
	private final Map<XValId<?>, Genome<D>>		lowestEverGenomes;
	/**
	 * map from attribute {@link XValId} to the average value over all
	 * {@link Genome}s in this {@link Population} with such attribute
	 */
	protected final Map<XValId<?>, Double>		averageAttributes;

	/**
	 * Creates a new {@link AbstractPopulation}.
	 * 
	 * @param instanceNum
	 *            the instance number of the id for this {@link Population}
	 */
	public AbstractPopulation(int instanceNum) {
		this(POPULATION_BASE_ID.createChild(instanceNum));
	}

	/**
	 * Creates a new {@link AbstractPopulation}.
	 * 
	 * @param id
	 *            the id for this {@link Population}
	 */
	public AbstractPopulation(XId id) {
		super(id);
		this.genomes = new TreeMap<XId, Genome<D>>();
		this.averageAttributes = new HashMap<XValId<?>, Double>();
		this.highestGenomes = new HashMap<XValId<?>, Genome<D>>();
		this.lowestGenomes = new HashMap<XValId<?>, Genome<D>>();
		this.highestEverGenomes = new HashMap<XValId<?>, Genome<D>>();
		this.lowestEverGenomes = new HashMap<XValId<?>, Genome<D>>();
		setAttribute(AGE_ATTRIBUTE_ID, 0l);
		setAttribute(AGE_LI_ATTRIBUTE_ID, 0l);
		setAttribute(GENOME_NUM_ATTRIBUTE_ID, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(Genome<D> genome) {
		getLogger().trace("begin %s.add(%s)", getClass().getSimpleName(), genome);
		validateArg("genome", genome, isNotNull());
		int genomeIdNumber = getGenomeIdNumber();
		genomeIdNumber = Math.max(genome.getId().getInstanceNum() + 1, genomeIdNumber);
		boolean changed = !contains(genome);
		genomes.put(genome.getId(), genome);
		setAttribute(GENOME_NUM_ATTRIBUTE_ID, genomeIdNumber);
		getLogger().trace("will return: %s", changed);
		getLogger().trace("end %s.add()", getClass().getSimpleName());
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Genome<D>> genomes) {
		getLogger().trace("begin %s.addAll(%s)", getClass().getSimpleName(), genomes);
		validateArg("genome", genomes, isNotNull(), hasElements(-1, isNotNull()));
		boolean changed = false;
		for (Genome<D> genome : genomes) {
			changed |= add(genome);
		}
		getLogger().trace("will return: %s", changed);
		getLogger().trace("end %s.addAll()", getClass().getSimpleName());
		return changed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		getLogger().trace("begin %s.clear()", getClass().getSimpleName());
		genomes.clear();
		getLogger().trace("end %s.clear()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		getLogger().trace("begin %s.contains(%s)", getClass().getSimpleName(), o);
		boolean rVal = genomes.values().contains(o);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.contains()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		getLogger().trace("begin %s.containsAll(%s)", getClass().getSimpleName(), c);
		boolean rVal = genomes.values().containsAll(c);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.containsAll()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		getLogger().trace("begin %s.isEmpty()", getClass().getSimpleName());
		boolean rVal = genomes.isEmpty();
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.isEmpty()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<Genome<D>> iterator() {
		getLogger().trace("begin %s.iterator()", getClass().getSimpleName());
		Iterator<Genome<D>> rVal = genomes.values().iterator();
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.iterator()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		getLogger().trace("begin %s.remove(%s)", getClass().getSimpleName(), o);
		boolean rVal = o instanceof XId ? genomes.remove(o) != null : genomes.values().remove(o);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.remove()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		getLogger().trace("begin %s.removeAll(%s)", getClass().getSimpleName(), c);
		boolean rVal = genomes.values().removeAll(c);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.removeAll()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		getLogger().trace("begin %s.retainAll(%s)", getClass().getSimpleName(), c);
		boolean rVal = genomes.values().retainAll(c);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.retainAll()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		getLogger().trace("begin %s.size()", getClass().getSimpleName());
		int rVal = genomes.size();
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.size()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		getLogger().trace("begin %s.toArray()", getClass().getSimpleName());
		Object[] rVal = genomes.values().toArray();
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.toArray()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		getLogger().trace("begin %s.toArray(%s)", getClass().getSimpleName(), a);
		T[] rVal = genomes.values().toArray(a);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.toArray()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.Population#get(com.xtructure.xutil.
	 * id.XId)
	 */
	@Override
	public Genome<D> get(XId id) {
		getLogger().trace("begin %s.get(%s)", getClass().getSimpleName(), id);
		Genome<D> rVal = genomes.get(id);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.get()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.Population#getAll(java.util.Collection)
	 */
	@Override
	public Set<Genome<D>> getAll(Collection<XId> ids) {
		getLogger().trace("begin %s.getAll(%s)", getClass().getSimpleName(), ids);
		Set<Genome<D>> genomes = new HashSet<Genome<D>>();
		for (XId id : ids) {
			Genome<D> genome = get(id);
			if (genome != null) {
				genomes.add(get(id));
			}
		}
		getLogger().trace("will return: %s", genomes);
		getLogger().trace("end %s.getAll()", getClass().getSimpleName());
		return genomes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Population#keySet()
	 */
	@Override
	public Set<XId> keySet() {
		getLogger().trace("begin %s.keySet()", getClass().getSimpleName());
		Set<XId> rVal = Collections.unmodifiableSet(genomes.keySet());
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.keySet()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Population#getAgeLastImproved()
	 */
	@Override
	public long getAgeLastImproved() {
		return getAttribute(AGE_LI_ATTRIBUTE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.Population#getAverageGenomeAttribute
	 * (com.xtructure.xutil.id.ValueId)
	 */
	@Override
	public Double getAverageGenomeAttribute(XValId<?> valueId) {
		getLogger().trace("begin %s.getAverageGenomeAttribute(%s)", getClass().getSimpleName(), valueId);
		Double rVal = averageAttributes.get(valueId);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getAverageGenomeAttribute()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Population#getGenomeAttributeIds()
	 */
	@Override
	public Set<XValId<?>> getGenomeAttributeIds() {
		getLogger().trace("begin %s.getGenomeAttributeIds()", getClass().getSimpleName());
		Set<XValId<?>> rVal = averageAttributes.keySet();
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getGenomeAttributeIds()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Population#getGenomeIdNumber()
	 */
	@Override
	public int getGenomeIdNumber() {
		return getAttribute(GENOME_NUM_ATTRIBUTE_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.Population#getHighestEverGenomeByAttribute
	 * (com.xtructure.xutil.id.ValueId)
	 */
	@Override
	public Genome<D> getHighestEverGenomeByAttribute(XValId<?> valueId) {
		getLogger().trace("begin %s.getHighestEverGenomeByAttribute(%s)", getClass().getSimpleName(), valueId);
		Genome<D> rVal = highestEverGenomes.get(valueId);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getHighestEverGenomeByAttribute()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.Population#getHighestGenomeByAttribute
	 * (com.xtructure.xutil.id.ValueId)
	 */
	@Override
	public Genome<D> getHighestGenomeByAttribute(XValId<?> valueId) {
		getLogger().trace("begin %s.getHighestGenomeByAttribute(%s)", getClass().getSimpleName(), valueId);
		Genome<D> rVal = highestGenomes.get(valueId);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getHighestGenomeByAttribute()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.Population#getLowestEverGenomeByAttribute
	 * (com.xtructure.xutil.id.ValueId)
	 */
	@Override
	public Genome<D> getLowestEverGenomeByAttribute(XValId<?> valueId) {
		getLogger().trace("begin %s.getLowestEverGenomeByAttribute(%s)", getClass().getSimpleName(), valueId);
		Genome<D> rVal = lowestEverGenomes.get(valueId);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getLowestEverGenomeByAttribute()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.Population#getLowestGenomeByAttribute
	 * (com.xtructure.xutil.id.ValueId)
	 */
	@Override
	public Genome<D> getLowestGenomeByAttribute(XValId<?> valueId) {
		getLogger().trace("begin %s.getLowestGenomeByAttribute(%s)", getClass().getSimpleName(), valueId);
		Genome<D> rVal = lowestGenomes.get(valueId);
		getLogger().trace("will return: %s", rVal);
		getLogger().trace("end %s.getLowestGenomeByAttribute()", getClass().getSimpleName());
		return rVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtructure.xevolution.genetics.impl.AbstractGeneticsObject#incrementAge
	 * ()
	 */
	@Override
	public void incrementAge() {
		super.incrementAge();
		for (Genome<?> genome : this) {
			genome.incrementAge();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Population#removeDeadGenomes()
	 */
	@Override
	public void removeDeadGenomes() {
		getLogger().trace("begin %s.removeDeadGenomes()", getClass().getSimpleName());
		Iterator<Genome<D>> iter = iterator();
		while (iter.hasNext()) {
			if (iter.next().isMarkedForDeath()) {
				iter.remove();
			}
		}
		getLogger().trace("end %s.removeDeadGenomes()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Population#refreshStats()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void refreshStats() {
		getLogger().trace("begin %s.refreshStats()", getClass().getSimpleName());
		if (isEmpty()) {
			return;
		}
		// calculate averages
		List<Genome<D>> genomes = new ArrayList<Genome<D>>(this);
		ValueMap accMap = new ValueMap();
		for (Genome<D> genome : genomes) {
			ValueMap measures = genome.getAttributes();
			for (@SuppressWarnings("rawtypes")
			XValId id : measures.keySet()) {
				updateAccMap(id, accMap, genome);
			}
		}
		averageAttributes.clear();
		for (@SuppressWarnings("rawtypes")
		XValId valueId : accMap.keySet()) {
			if (Number.class.isAssignableFrom(valueId.getType())) {
				double average = ((Number) accMap.get(valueId)).doubleValue() / size();
				averageAttributes.put(valueId, average);
			}
		}
		// get low/high genomes by measure
		highestGenomes.clear();
		lowestGenomes.clear();
		for (XValId<?> valueId : averageAttributes.keySet()) {
			@SuppressWarnings({ "rawtypes" })
			Comparator<Genome<?>> byMeasure = new GeneticsObject.ByAttribute(valueId, true);
			Collections.sort(genomes, byMeasure);
			Genome<D> genome = genomes.get(0);
			if (highestEverGenomes.get(valueId) == null || byMeasure.compare(genome, highestEverGenomes.get(valueId)) < 0) {
				highestEverGenomes.put(valueId, genome);
				if (Genome.FITNESS_ATTRIBUTE_ID.equals(valueId)) {
					setAttribute(AGE_LI_ATTRIBUTE_ID, getAge());
				}
			}
			highestGenomes.put(valueId, genome);
			genome = genomes.get(genomes.size() - 1);
			if (lowestEverGenomes.get(valueId) == null || byMeasure.compare(genome, lowestEverGenomes.get(valueId)) > 0) {
				lowestEverGenomes.put(valueId, genome);
			}
			lowestGenomes.put(valueId, genome);
		}
		getLogger().trace("end %s.refreshStats()", getClass().getSimpleName());
	}

	/**
	 * Adds the attribute in the given {@link Genome} with the given
	 * {@link XValId} to the corresponding value in the accumulation map.
	 * 
	 * @param <V>
	 *            the type of the attribute
	 * @param valueId
	 *            the id of the attribute
	 * @param accMap
	 *            the accumulation map
	 * @param genome
	 *            the {@link Genome} from which to get the attribute
	 */
	private <V extends Comparable<V>> void updateAccMap(XValId<V> valueId, ValueMap accMap, Genome<D> genome) {
		getLogger().trace("begin %s.updateAccMap(%s, %s, %s)", getClass().getSimpleName(), valueId, accMap, genome);
		if (Number.class.isAssignableFrom(valueId.getType())) {
			if (accMap.get(valueId) == null) {
				accMap.set(//
						valueId,//
						genome.getAttribute(valueId));
			} else {
				Number acc = (Number) accMap.get(valueId);
				Number nxt = (Number) genome.getAttribute(valueId);
				Number sum = null;
				ValueType type = ValueType.getValueType(valueId.getType());
				if (type != null) {
					switch (type) {
						case DOUBLE:
							sum = acc.doubleValue() + nxt.doubleValue();
							break;
						case FLOAT:
							sum = acc.floatValue() + nxt.floatValue();
							break;
						case LONG:
							sum = acc.longValue() + nxt.longValue();
							break;
						case INTEGER:
							sum = acc.intValue() + nxt.intValue();
							break;
						case SHORT:
							sum = acc.shortValue() + nxt.shortValue();
							break;
						case BYTE:
							sum = acc.byteValue() + nxt.byteValue();
							break;
						default:
							break;
					}
					@SuppressWarnings("unchecked")
					V v = (V) sum;
					accMap.set(valueId, v);
				}
			}
		}
		getLogger().trace("end %s.updateAccMap()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.Population#write(java.io.File)
	 */
	@Override
	public void write(File outputDir) throws IOException, XMLStreamException {
		String filename = "Population" + getAge();
		File temp = new File(outputDir, filename + ".tmp");
		FileWriter out = null;
		try {
			out = new FileWriter(temp);
			XmlWriter.write(out, this);
			temp.renameTo(new File(outputDir, filename + ".xml"));
		} finally {
			if (out != null) {
				out.close();
			}
			temp.delete();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#getBaseId()
	 */
	@Override
	public XId getBaseId() {
		return POPULATION_BASE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtructure.xevolution.genetics.GeneticsObject#validate()
	 */
	@Override
	public void validate() throws IllegalStateException {
		getLogger().trace("begin %s.validate()", getClass().getSimpleName());
		for (XId id : genomes.keySet()) {
			Genome<?> genome = genomes.get(id);
			validateState("genome", genome, isNotNull());
			validateState("genome id", genome.getId(), isEqualTo(id));
		}
		for (Genome<?> genome : highestGenomes.values()) {
			validateState("high genome in pop",//
					this, containsElement(genome));
		}
		for (Genome<?> genome : lowestGenomes.values()) {
			validateState("low genome in pop",//
					this, containsElement(genome));
		}
		getLogger().trace("end %s.validate()", getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)//
				.append("id", getId())//
				.append("size", size())//
				.append("attributes", getAttributes())//
				.append("highGenomes", mapToString(highestGenomes))//
				.append("lowGenomes", mapToString(lowestGenomes))//
				.append("averageMeasures", mapToString(averageAttributes))//
				.toString();
	}

	/**
	 * Convert the given map to a string.
	 * 
	 * @param map
	 *            the map to convert
	 * @return the converted string
	 */
	protected static String mapToString(Map<XValId<?>, ?> map) {
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

	/** xml format for {@link AbstractPopulation} */
	protected static abstract class AbstractXmlFormat<D, P extends AbstractPopulation<D>> extends AbstractGeneticsObject.AbstractXmlFormat<P> {
		protected AbstractXmlFormat(Class<P> cls) {
			super(cls);
		}

		protected abstract Element<Genome<D>> getGenomeElement();

		@Override
		protected void writeElements(P obj, OutputElement xml) throws XMLStreamException {
			super.writeElements(obj, xml);
			ArrayList<Genome<D>> genomes = new ArrayList<Genome<D>>(obj);
			Collections.sort(genomes);
			for (Genome<D> genome : genomes) {
				getGenomeElement().write(xml, genome);
			}
		}
	}
}
