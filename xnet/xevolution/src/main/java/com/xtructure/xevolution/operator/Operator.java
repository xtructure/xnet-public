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
package com.xtructure.xevolution.operator;

import java.io.IOException;

import javolution.text.Cursor;
import javolution.text.TextFormat;

import com.xtructure.xevolution.evolution.EvolutionObject;
import com.xtructure.xevolution.genetics.GeneticsFactory;
import com.xtructure.xevolution.genetics.Genome;
import com.xtructure.xutil.format.XTextFormat;

/**
 * The {@link Operator} interface describes the basic functionality of
 * reproductive operations used in evolution. It defines
 * {@link OperationFailedException} for use when operations fail in an expected
 * way (e.g., removing a link when no links are present).
 * 
 * @author Luis Guimbarda
 * 
 * @param <D>
 *            type of data used by {@link Genome}s on which this operator is
 *            applied.
 */
public interface Operator<D> extends EvolutionObject {
	/**
	 * Returns the {@link GeneticsFactory} used by this {@link Operator}
	 * 
	 * @return the {@link GeneticsFactory} used by this {@link Operator}
	 */
	public GeneticsFactory<D> getGeneticsFactory();

	public AppliedOperatorInfo getAppliedOperatorInfo();

	/** exception class for {@link Operator}s */
	public static final class OperationFailedException extends Exception {
		private static final long	serialVersionUID	= 1L;

		public OperationFailedException() {
			this(null);
		}

		public OperationFailedException(String msg) {
			super(msg);
		}
	}

	/** base class for describing communication of operator events */
	public static class AppliedOperatorInfo {
		public static final TextFormat<AppliedOperatorInfo>	TEXT_FORMAT	= new InfoTextFormat();
		private final Class<? extends Operator<?>>			operatorClass;

		public AppliedOperatorInfo(Class<? extends Operator<?>> operatorClass) {
			this.operatorClass = operatorClass;
		}

		public Class<? extends Operator<?>> getOperatorClass() {
			return operatorClass;
		}

		@Override
		public String toString() {
			return TextFormat.getInstance(getClass()).format(this).toString();
		}

		private static final class InfoTextFormat extends XTextFormat<AppliedOperatorInfo> {
			public InfoTextFormat() {
				super(AppliedOperatorInfo.class);
			}

			@Override
			public Appendable format(AppliedOperatorInfo info, Appendable appendable) throws IOException {
				return appendable.append(info.operatorClass.getName());
			}

			@SuppressWarnings("unchecked")
			@Override
			public AppliedOperatorInfo parse(CharSequence chars, Cursor cursor) throws IllegalArgumentException {
				String className = chars.toString();
				cursor.increment(chars.length());
				try {
					return new AppliedOperatorInfo((Class<? extends Operator<?>>) Class.forName(className));
				} catch (ClassNotFoundException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			}
		}
	}
}
