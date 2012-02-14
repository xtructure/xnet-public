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
package com.xtructure.xevolution.operator.impl;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.or;

import org.testng.annotations.Test;

import com.xtructure.xevolution.config.EvolutionFieldMap;
import com.xtructure.xevolution.config.impl.EvolutionConfigurationImpl;
import com.xtructure.xevolution.genetics.impl.GeneticsFactoryImpl;
import com.xtructure.xevolution.operator.DummyMutateOperator;
import com.xtructure.xevolution.operator.MutateOperator;
import com.xtructure.xevolution.operator.Operator;
import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.id.XId;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xevolution" })
public class UTestAbstractOperatorSelecter {
	private static final EvolutionFieldMap									EVOLUTION_FIELD_MAP;
	private static final MutateOperator<String>								OP1, OP2, OP3, OP4;
	private static final AbstractOperatorSelecter<MutateOperator<String>>	OP_SELECTER;
	static {
		EVOLUTION_FIELD_MAP = EvolutionConfigurationImpl//
				.builder(XId.newId("default.evolution.config"))//
				.setPopulationSize(1000)//
				.setMutationProbability(1.0)//
				.newInstance().newFieldMap();
		GeneticsFactoryImpl geneticsFactory = new GeneticsFactoryImpl(EVOLUTION_FIELD_MAP, "asdf");
		OP1 = new DummyMutateOperator(geneticsFactory);
		OP2 = new DummyMutateOperator(geneticsFactory);
		OP3 = new DummyMutateOperator(geneticsFactory);
		OP4 = new DummyMutateOperator(geneticsFactory);
		OP_SELECTER = new MutateOperatorSelecterImpl();
		OP_SELECTER.add(OP1, 1.0);
		OP_SELECTER.add(OP2, 1.0);
		OP_SELECTER.add(OP3, 0.0);
		OP_SELECTER.add(OP4, 0.0);
	}

	public void constructorSucceeds() {
		assertThat("",//
				new MutateOperatorSelecterImpl(), isNotNull());
		assertThat("",//
				new MutateOperatorSelecterImpl((MutateOperatorSelecterImpl) OP_SELECTER), isNotNull());
		assertThat("",//
				new MutateOperatorSelecterImpl(new MapBuilder<MutateOperator<String>, Double>().put(OP1, 1.0).put(OP2, 0.5).newImmutableInstance()), isNotNull());
	}

	public void addAndSizeBehaveAsExpected() {
		MutateOperatorSelecterImpl opSelecter = new MutateOperatorSelecterImpl();
		assertThat("",//
				opSelecter.available(), isEqualTo(0));
		opSelecter.add(new DummyMutateOperator(null), 0.0);
		assertThat("",//
				opSelecter.available(), isEqualTo(1));
		opSelecter.add(new DummyMutateOperator(null), 0.0);
		assertThat("",//
				opSelecter.available(), isEqualTo(2));
	}

	public void removeAndResetBehavesAsExpected() {
		MutateOperatorSelecterImpl opSelecter = new MutateOperatorSelecterImpl();
		DummyMutateOperator op = new DummyMutateOperator(null);
		opSelecter.add(op, 1.0);
		assertThat("",//
				opSelecter.available(), isEqualTo(1));
		opSelecter.remove(op);
		assertThat("",//
				opSelecter.available(), isEqualTo(0));
		opSelecter.add(op, 1.0);
		assertThat("",//
				opSelecter.available(), isEqualTo(1));
		opSelecter.select();
		assertThat("",//
				opSelecter.available(), isEqualTo(0));
		opSelecter.remove(op);
		opSelecter.reset();
		assertThat("",//
				opSelecter.available(), isEqualTo(0));
	}

	public void selectAndResetBehaveAsExpected() {
		MutateOperatorSelecterImpl opSelecter = new MutateOperatorSelecterImpl();
		DummyMutateOperator op = new DummyMutateOperator(null);
		opSelecter.add(op, 1.0);
		assertThat("",//
				opSelecter.available(), isEqualTo(1));
		assertThat("",//
				opSelecter.select(), isSameAs(op));
		assertThat("",//
				opSelecter.available(), isEqualTo(0));
		opSelecter.reset();
		assertThat("",//
				opSelecter.available(), isEqualTo(1));
		OP_SELECTER.reset();
		assertThat("",//
				OP_SELECTER.available(), isEqualTo(4));
		Operator<String> operator = OP_SELECTER.select();
		assertThat("",//
				operator, or(isSameAs(OP1), isSameAs(OP2)));
		assertThat("",//
				OP_SELECTER.available(), isEqualTo(3));
		if (operator == OP1) {
			assertThat("",//
					OP_SELECTER.select(), isSameAs(OP2));
		} else {
			assertThat("",//
					OP_SELECTER.select(), isSameAs(OP1));
		}
		assertThat("",//
				OP_SELECTER.available(), isEqualTo(2));
		operator = OP_SELECTER.select();
		assertThat("",//
				operator, or(isSameAs(OP3), isSameAs(OP4)));
		assertThat("",//
				OP_SELECTER.available(), isEqualTo(1));
		if (operator == OP3) {
			assertThat("",//
					OP_SELECTER.select(), isSameAs(OP4));
		} else {
			assertThat("",//
					OP_SELECTER.select(), isSameAs(OP3));
		}
		assertThat("",//
				OP_SELECTER.available(), isEqualTo(0));
		assertThat("",//
				OP_SELECTER.select(), isNull());
	}
}
