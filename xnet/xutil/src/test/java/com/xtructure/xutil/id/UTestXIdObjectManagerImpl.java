/*
 * Copyright 2012 Michael Roberts
 * All rights reserved.
 *
 *
 * This file is part of xutil.
 *
 * xutil is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 *
 * xutil is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with xutil.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtructure.xutil.id;

import static com.xtructure.xutil.valid.ValidateUtils.assertThat;
import static com.xtructure.xutil.valid.ValidateUtils.containsElement;
import static com.xtructure.xutil.valid.ValidateUtils.containsElements;
import static com.xtructure.xutil.valid.ValidateUtils.containsValue;
import static com.xtructure.xutil.valid.ValidateUtils.hasSize;
import static com.xtructure.xutil.valid.ValidateUtils.isEmpty;
import static com.xtructure.xutil.valid.ValidateUtils.isEqualTo;
import static com.xtructure.xutil.valid.ValidateUtils.isNotNull;
import static com.xtructure.xutil.valid.ValidateUtils.isSameAs;
import static com.xtructure.xutil.valid.ValidateUtils.not;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.Test;

import com.xtructure.xutil.coll.MapBuilder;
import com.xtructure.xutil.coll.XIdObjectTransform;

/**
 * @author Luis Guimbarda
 * 
 */
@Test(groups = { "unit:xutil" })
public final class UTestXIdObjectManagerImpl {
	public void constructorSucceeds() {
		assertThat("",//
				new XIdObjectManagerImpl<XIdObject>(), isNotNull());
		assertThat("",//
				new XIdObjectManagerImpl<XIdObject>(new HashMap<XId, XIdObject>()), isNotNull());
	}

	public void getIdsAndClearBehaveAsExpected() {
		Map<XId, XIdObject> map = new MapBuilder<XId, XIdObject>()//
				.putAll(XIdObjectTransform.getInstance(),//
						Arrays.asList(//
								new DummyXIdObject(XId.newId()),//
								new DummyXIdObject(XId.newId()),//
								new DummyXIdObject(XId.newId())))//
				.newInstance();
		assertThat("",//
				map.keySet(), hasSize(3));
		XIdObjectManagerImpl<XIdObject> man = new XIdObjectManagerImpl<XIdObject>(map);
		assertThat("",//
				man.getIds(), isEqualTo(map.keySet()));
		man.clear();
		assertThat("",//
				map, isEmpty());
		assertThat("",//
				man.getIds(), isEmpty());
		map = new MapBuilder<XId, XIdObject>(new TreeMap<XId, XIdObject>())//
				.putAll(XIdObjectTransform.getInstance(),//
						Arrays.asList(//
								new DummyXIdObject(XId.newId()),//
								new DummyXIdObject(XId.newId()),//
								new DummyXIdObject(XId.newId())))//
				.newInstance();
		assertThat("",//
				map.keySet(), hasSize(3));
		man = new XIdObjectManagerImpl<XIdObject>(map);
		assertThat("",//
				man.getIds(), isEqualTo(map.keySet()));
		man.clear();
		assertThat("",//
				map, isEmpty());
		assertThat("",//
				man.getIds(), isEmpty());
	}

	public void getObjectReturnsExpectedObject() {
		Map<XId, XIdObject> map = new MapBuilder<XId, XIdObject>()//
				.putAll(XIdObjectTransform.getInstance(),//
						Arrays.asList(//
								new DummyXIdObject(XId.newId()),//
								new DummyXIdObject(XId.newId()),//
								new DummyXIdObject(XId.newId())))//
				.newInstance();
		XIdObjectManagerImpl<XIdObject> man = new XIdObjectManagerImpl<XIdObject>(map);
		assertThat("",//
				man.getIds(), hasSize(3));
		for (XId id : man.getIds()) {
			assertThat("",//
					man.getObject(id), isSameAs(map.get(id)));
		}
	}

	public void registerAndUnregisterBehaveAsExpected() {
		Map<XId, DummyXIdObject> map = new HashMap<XId, DummyXIdObject>();
		XIdObjectManagerImpl<DummyXIdObject> man = new XIdObjectManagerImpl<DummyXIdObject>(map);
		DummyXIdObject dummy = new DummyXIdObject(XId.newId());
		assertThat("",//
				map, not(containsValue(dummy)));
		assertThat("",//
				man.getIds(), not(containsElement(dummy.getId())));
		man.register(dummy);
		assertThat("",//
				map, containsValue(dummy));
		assertThat("",//
				man.getIds(), containsElement(dummy.getId()));
		man.unregister(dummy);
		assertThat("",//
				map, not(containsValue(dummy)));
		assertThat("",//
				man.getIds(), not(containsElement(dummy.getId())));
		dummy = new DummyXIdObject(XId.newId(), man);
		assertThat("",//
				map, containsValue(dummy));
		assertThat("",//
				man.getIds(), containsElement(dummy.getId()));
		man.unregister(dummy.getId());
		assertThat("",//
				map, not(containsValue(dummy)));
		assertThat("",//
				man.getIds(), not(containsElement(dummy.getId())));
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void registerNullThrowsException() {
		new XIdObjectManagerImpl<XIdObject>().register(null);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void registerTwiceThrowsException() {
		XIdObjectManager<XIdObject> man = new XIdObjectManagerImpl<XIdObject>();
		XIdObject obj = new DummyXIdObject(XId.newId());
		man.register(obj);
		man.register(obj);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void unregisterNullThrowsException() {
		new XIdObjectManagerImpl<XIdObject>().unregister((XId) null);
	}

	public void getAncestorsAndDescendentsReturnExpectedObject() {
		XId id = XId.newId();
		XId id1 = id.createChild(1);
		XId id2 = id.createChild(2);
		XId id11 = id1.createChild(1);
		XId id12 = id1.createChild(2);
		XId id21 = id2.createChild(1);
		XId id22 = id2.createChild(2);
		DummyXIdObject d = new DummyXIdObject(id);
		DummyXIdObject d1 = new DummyXIdObject(id1);
		DummyXIdObject d2 = new DummyXIdObject(id2);
		DummyXIdObject d11 = new DummyXIdObject(id11);
		DummyXIdObject d12 = new DummyXIdObject(id12);
		DummyXIdObject d21 = new DummyXIdObject(id21);
		DummyXIdObject d22 = new DummyXIdObject(id22);
		XIdObjectManagerImpl<DummyXIdObject> man = new XIdObjectManagerImpl<DummyXIdObject>();
		for (DummyXIdObject dummy : Arrays.asList(d, d1, d2, d11, d12, d21, d22)) {
			man.register(dummy);
		}
		// getDescendents(id)
		assertThat("",//
				man.getDescendents(id),//
				hasSize(6), containsElements(d1, d2, d11, d12, d21, d22));
		assertThat("",//
				man.getDescendents(id1),//
				hasSize(2), containsElements(d11, d12));
		assertThat("",//
				man.getDescendents(id2),//
				hasSize(2), containsElements(d21, d22));
		assertThat("",//
				man.getDescendents(id11), isEmpty());
		assertThat("",//
				man.getDescendents(id12), isEmpty());
		assertThat("",//
				man.getDescendents(id21), isEmpty());
		assertThat("",//
				man.getDescendents(id22), isEmpty());
		// getDescendents(object)
		assertThat("",//
				man.getDescendents(d),//
				hasSize(6), containsElements(d1, d2, d11, d12, d21, d22));
		assertThat("",//
				man.getDescendents(d1),//
				hasSize(2), containsElements(d11, d12));
		assertThat("",//
				man.getDescendents(d2),//
				hasSize(2), containsElements(d21, d22));
		assertThat("",//
				man.getDescendents(d11), isEmpty());
		assertThat("",//
				man.getDescendents(d12), isEmpty());
		assertThat("",//
				man.getDescendents(d21), isEmpty());
		assertThat("",//
				man.getDescendents(d22), isEmpty());
		// getAncestors(id)
		assertThat("",//
				man.getAncestors(id), isEmpty());
		assertThat("",//
				man.getAncestors(id1),//
				hasSize(1), containsElement(d));
		assertThat("",//
				man.getAncestors(id2),//
				hasSize(1), containsElement(d));
		assertThat("",//
				man.getAncestors(id11),//
				hasSize(2), containsElements(d, d1));
		assertThat("",//
				man.getAncestors(id12),//
				hasSize(2), containsElements(d, d1));
		assertThat("",//
				man.getAncestors(id21),//
				hasSize(2), containsElements(d, d2));
		assertThat("",//
				man.getAncestors(id22),//
				hasSize(2), containsElements(d, d2));
		// getAncestors(object)
		assertThat("",//
				man.getAncestors(d), isEmpty());
		assertThat("",//
				man.getAncestors(d1),//
				hasSize(1), containsElement(d));
		assertThat("",//
				man.getAncestors(d2),//
				hasSize(1), containsElement(d));
		assertThat("",//
				man.getAncestors(d11),//
				hasSize(2), containsElements(d, d1));
		assertThat("",//
				man.getAncestors(d12),//
				hasSize(2), containsElements(d, d1));
		assertThat("",//
				man.getAncestors(d21),//
				hasSize(2), containsElements(d, d2));
		assertThat("",//
				man.getAncestors(d22),//
				hasSize(2), containsElements(d, d2));
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void getAncestorsWithNullIdThrowsException() {
		new XIdObjectManagerImpl<XIdObject>().getAncestors((XId) null);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void getAncestorsWithNullThrowsException() {
		new XIdObjectManagerImpl<XIdObject>().getAncestors((XIdObject) null);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void getDescendentsWithNullIdThrowsException() {
		new XIdObjectManagerImpl<XIdObject>().getDescendents((XId) null);
	}

	@Test(expectedExceptions = { IllegalArgumentException.class })
	public void getDescendentsWithNullThrowsException() {
		new XIdObjectManagerImpl<XIdObject>().getDescendents((XIdObject) null);
	}
}
