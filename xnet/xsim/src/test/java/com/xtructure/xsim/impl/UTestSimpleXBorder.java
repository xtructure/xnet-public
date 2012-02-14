/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xsim.
 *
 * xsim is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xsim is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xsim.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xsim.impl;
import static com.xtructure.xutil.valid.ValidateUtils.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

import com.xtructure.xsim.impl.UTestXAddressImpl.DummyXComponent;
/**
 * @author Luis Guimbarda
 *
 */
@Test(groups = { "unit:xsim" })
public final class UTestSimpleXBorder {
	public final void constructorSucceeds(){
		assertThat("",//
				new SimpleXBorder(), isNotNull());
	}
	
	public final void addComponentSucceeds(){
		SimpleXBorder brdr = new SimpleXBorder();
		DummyXComponent comp = new DummyXComponent();
		brdr.addComponent(comp);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public final void addComponentOnNullThrowsException(){
		new SimpleXBorder().addComponent(null);
	}
	
	public final void nameComponentSucceeds(){
		SimpleXBorder brdr = new SimpleXBorder();
		DummyXComponent comp = new DummyXComponent();
		brdr.nameComponent("comp", comp);
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	public final void loadAssociationsOnNullStringThrowsException() throws IOException{
		new SimpleXBorder().loadAssociations(null);
	}
	
	@Test(expectedExceptions = FileNotFoundException.class)
	public final void loadAssociationsOnNonExistantFileThrowsException() throws IOException{
		new SimpleXBorder().loadAssociations("nonexistantfilename");
	}
	
	public final void loadAssociationsSucceeds() throws IOException{
		SimpleXBorder brdr = new SimpleXBorder();
		DummyXComponent comp = new DummyXComponent();
		brdr.addComponent(comp);
		brdr.loadAssociations("src/test/resources/test_assoc.dat");
	}
}








