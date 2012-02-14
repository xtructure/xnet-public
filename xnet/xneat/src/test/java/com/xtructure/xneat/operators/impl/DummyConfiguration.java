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

import com.xtructure.xutil.Range;
import com.xtructure.xutil.coll.SetBuilder;
import com.xtructure.xutil.config.AbstractRangeXParameter;
import com.xtructure.xutil.config.AbstractXConfiguration;
import com.xtructure.xutil.config.AbstractXField;
import com.xtructure.xutil.config.AbstractXParameter;
import com.xtructure.xutil.config.FieldMap;
import com.xtructure.xutil.config.FieldMapImpl;
import com.xtructure.xutil.config.XField;
import com.xtructure.xutil.config.XParameter;
import com.xtructure.xutil.id.XId;

public class DummyConfiguration extends AbstractXConfiguration<DummyConfiguration> {
	public static final DummyConfiguration	INSTANCE	= new DummyConfiguration();

	private DummyConfiguration() {
		super(//
				XId.newId("DummyConfiguration"),//
				new SetBuilder<XParameter<?>>()//
						.add(new DummyBooleanParameter())//
						.add(new DummyByteParameter())//
						.add(new DummyDoubleParameter())//
						.add(new DummyFloatParameter(Range.getInstance(0.0f, 1.0f)))//
						.add(new DummyFloatParameter(Range.getInstance((Float) null, null)))//
						.add(new DummyIntegerParameter())//
						.add(new DummyLongParameter())//
						.add(new DummyShortParameter())//
						.add(new DummyStringParameter())//
						.newImmutableInstance());
	}

	@Override
	public FieldMap newFieldMap() {
		return new FieldMapImpl(this);
	}

	public static final class DummyFloatParameter extends AbstractRangeXParameter<Float> {
		protected DummyFloatParameter(Range<Float> range) {
			super(XId.newId("FLOAT"), "", false, true, range, range, Float.class);
		}

		@Override
		public XField<Float> newField() {
			return new AbstractXField<Float>(this, getFieldVettingStrategy(0.0f), 0.0f) {};
		}
	}

	public static final class DummyDoubleParameter extends AbstractRangeXParameter<Double> {
		protected DummyDoubleParameter() {
			super(XId.newId("DOUBLE"), "", false, true, Range.getInstance((Double) null, null), Range.getInstance((Double) null, null), Double.class);
		}

		@Override
		public XField<Double> newField() {
			return new AbstractXField<Double>(this, getFieldVettingStrategy(0.0), 0.0) {};
		}
	}

	public static final class DummyLongParameter extends AbstractRangeXParameter<Long> {
		protected DummyLongParameter() {
			super(XId.newId("LONG"), "", false, true, Range.getInstance(0l, null), Range.getInstance(0l, null), Long.class);
		}

		@Override
		public XField<Long> newField() {
			return new AbstractXField<Long>(this, getFieldVettingStrategy(0l), 0l) {};
		}
	}

	public static final class DummyIntegerParameter extends AbstractRangeXParameter<Integer> {
		protected DummyIntegerParameter() {
			super(XId.newId("INTEGER"), "", false, true, Range.getInstance(0, null), Range.getInstance(0, null), Integer.class);
		}

		@Override
		public XField<Integer> newField() {
			return new AbstractXField<Integer>(this, getFieldVettingStrategy(0), 0) {};
		}
	}

	public static final class DummyShortParameter extends AbstractRangeXParameter<Short> {
		protected DummyShortParameter() {
			super(XId.newId("SHORT"), "", false, true, Range.getInstance((short) 0, null), Range.getInstance((short) 0, null), Short.class);
		}

		@Override
		public XField<Short> newField() {
			return new AbstractXField<Short>(this, getFieldVettingStrategy((short) 0), (short) 0) {};
		}
	}

	public static final class DummyByteParameter extends AbstractRangeXParameter<Byte> {
		protected DummyByteParameter() {
			super(XId.newId("BYTE"), "", false, true, Range.getInstance((byte) 0, null), Range.getInstance((byte) 0, null), Byte.class);
		}

		@Override
		public XField<Byte> newField() {
			return new AbstractXField<Byte>(this, getFieldVettingStrategy((byte) 0), (byte) 0) {};
		}
	}

	public static final class DummyBooleanParameter extends AbstractRangeXParameter<Boolean> {
		protected DummyBooleanParameter() {
			super(XId.newId("BOOLEAN"), "", false, true, Range.getInstance(false, true), Range.getInstance(false, true), Boolean.class);
		}

		@Override
		public XField<Boolean> newField() {
			return new AbstractXField<Boolean>(this, getFieldVettingStrategy(false), false) {};
		}
	}

	public static final class DummyStringParameter extends AbstractXParameter<String> {
		protected DummyStringParameter() {
			super(XId.newId("STRING"), "", false, true);
		}

		@Override
		public XField<String> newField() {
			return new AbstractXField<String>(this, getFieldVettingStrategy("STRING"), "STRING") {};
		}
	}
}
