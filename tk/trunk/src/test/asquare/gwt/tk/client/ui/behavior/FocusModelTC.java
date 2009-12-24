/*
 * Copyright 2006 Mat Gessel <mat.gessel@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package asquare.gwt.tk.client.ui.behavior;

import asquare.gwt.tk.client.Tests;
import asquare.gwt.tk.client.ui.behavior.event.FocusModelHandler;
import asquare.gwt.tk.testutil.TkTestUtil;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Focusable;

public class FocusModelTC extends GWTTestCase
{
	private FocusModel m_model;
	private Button m_a, m_b, m_c, m_d, m_e;
	private FocusModelHandlerStub m_l1;
	
    @Override
	public String getModuleName()
	{
		return Tests.getModuleName();
	}
	
	protected void setUpImpl()
	{
		m_model = new FocusModel();
		m_l1 = new FocusModelHandlerStub();
		m_model.addHandler(m_l1);
		m_a = new Button("a");
		m_b = new Button("b");
		m_c = new Button("c");
		m_d = new Button("d");
		m_e = new Button("e");
	}
	
	public void testAddRemoveListener()
	{
		FocusModelHandlerStub l2 = new FocusModelHandlerStub();
		
		setUpImpl();
		m_model.add(m_a);
		assertTrue(m_l1.isChanged());
		m_l1.init();
		m_model.removeHandler(m_l1);
		m_model.add(m_b);
		assertFalse(m_l1.isChanged());
		
		// multiple listeners
		setUpImpl();
		m_model.addHandler(l2);
		m_model.add(m_a);
		assertTrue(m_l1.isChanged());
		assertTrue(l2.isChanged());
		m_model.removeHandler(m_l1);
		m_model.removeHandler(l2);
		m_l1.init();
		l2.init();
		m_model.remove(m_a);
		assertFalse(m_l1.isChanged());
		assertFalse(l2.isChanged());
		
		// remove listener during notification
		setUpImpl();
		m_model.addHandler(new FocusModelHandler()
		{
			public void widgetsAdded(FocusModel model, Focusable[] added)
			{
				m_model.removeHandler(this);
			}
			public void widgetsRemoved(FocusModel model, Focusable[] removed) {}
			public void focusChanged(FocusModel model, Focusable previous, Focusable current) {}
		});
		m_model.addHandler(l2);
		m_model.add(m_a);
		assertTrue(m_l1.isChanged());
		assertTrue(l2.isChanged());
	}
	
	public void testGetSize()
	{
		setUpImpl();
		
		// empty
		assertEquals(0, m_model.getSize());
		m_model.add(m_a);
		
		// not empty
		assertEquals(1, m_model.getSize());
		m_model.add(m_b);
		assertEquals(2, m_model.getSize());
		
		// empty again
		m_model.clear();
		assertEquals(0, m_model.getSize());
	}
	
	public void testInsert()
	{
		// initial insert
		setUpImpl();
		m_model.insert(m_a, 0);
		assertSame(m_a, m_model.getWidgetAt(0));
		
		// insert before (current, blur)
		setUpImpl();
		m_model.insert(m_c, 0);
		m_model.insert(m_b, 0);
		m_model.setFocusWidget(m_b);
		m_model.setFocusWidget(m_c);
		m_model.insert(m_a, 0);
		assertSame(m_a, m_model.getWidgetAt(0));
		assertSame(m_b, m_model.getWidgetAt(1));
		assertSame(m_b, m_model.getBlurWidget());
		assertSame(m_c, m_model.getCurrentWidget());
		
		// insert after
		setUpImpl();
		m_model.insert(m_a, 0);
		m_model.insert(m_b, 1);
		assertSame(m_a, m_model.getWidgetAt(0));
		assertSame(m_b, m_model.getWidgetAt(1));
		
		// listener notified
		setUpImpl();
		m_model.insert(m_a, 0);
		m_l1.init();
		m_model.insert(m_b, 0);
		assertTrue(m_l1.isChanged());
		assertEquals(m_b, m_l1.getAdded()[0]);
		m_l1.init();
		m_model.insert(m_c, 0);
		assertTrue(m_l1.isChanged());
		assertEquals(m_c, m_l1.getAdded()[0]);
		
		// tabIndex = 0
		setUpImpl();
		m_a.setTabIndex(0);
		m_model.insert(m_a, 0);
		assertSame(m_a, m_model.getWidgetAt(0));
		
		// tabIndex < 0
		setUpImpl();
		m_a.setTabIndex(-1);
		m_model.insert(m_a, 0);
		m_a.setTabIndex(-100);
		m_model.insert(m_a, 0);
		assertEquals(0, m_model.getSize());
		assertFalse(m_l1.isChanged());
		
		// tabIndex > 0
		setUpImpl();
		m_a.setTabIndex(1);
		m_model.insert(m_a, 0);
		assertSame(m_a, m_model.getWidgetAt(0));
		m_b.setTabIndex(100);
		m_model.insert(m_b, 1);
		assertSame(m_b, m_model.getWidgetAt(1));
		
		// tabIndex max
		setUpImpl();
		m_b.setTabIndex(32767);
		m_model.insert(m_b, 0);
		assertSame(m_b, m_model.getWidgetAt(0));
		
		// disabled FocusWidget
		setUpImpl();
		Button button = new Button("button");
		button.setEnabled(false);
		m_model.insert(button, 0);
		assertEquals(1, m_model.getSize());
		
		// null widget
		setUpImpl();
		try
		{
			m_model.insert(null, 0);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// widget already present
		setUpImpl();
		m_model.insert(m_a, 0);
		try
		{
			m_model.insert(m_a, 0);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// negative index
		setUpImpl();
		try
		{
			m_model.insert(m_a, -1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// invalid index
		setUpImpl();
		try
		{
			m_model.insert(m_a, m_model.getSize() + 1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
	}
	
	public void testAdd()
	{
		// basic test
		setUpImpl();
		m_model.add(m_a);
		assertSame(m_a, m_model.getWidgetAt(0));
		m_model.add(m_b);
		assertSame(m_a, m_model.getWidgetAt(0));
		assertSame(m_b, m_model.getWidgetAt(1));
		
		// listener notified
		setUpImpl();
		m_model.add(m_a);
		m_l1.init();
		m_model.add(m_b);
		assertTrue(m_l1.isChanged());
		assertEquals(m_b, m_l1.getAdded()[0]);
		m_l1.init();
		m_model.add(m_c);
		assertTrue(m_l1.isChanged());
		assertEquals(m_c, m_l1.getAdded()[0]);
		
		// null widget
		setUpImpl();
		try
		{
			m_model.add((Focusable) null);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// widget already present
		setUpImpl();
		m_model.add(m_a);
		try
		{
			m_model.add(m_a);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
	}
	
	public void testAdd2()
	{
		// basic test
		setUpImpl();
		Focusable[] a1 = {m_a, m_b, m_c};
		Focusable[] a2 = {m_d, m_e};
		m_model.add(a1);
		assertEquals(3, m_model.getSize());
		m_model.add(a2);
		assertSame(m_a, m_model.getWidgetAt(0));
		assertSame(m_b, m_model.getWidgetAt(1));
		assertSame(m_c, m_model.getWidgetAt(2));
		assertSame(m_d, m_model.getWidgetAt(3));
		assertSame(m_e, m_model.getWidgetAt(4));
		assertEquals(5, m_model.getSize());
		
		// listener notified
		setUpImpl();
		a1 = new Focusable[] {m_a, m_b, m_c};
		a2 = new Focusable[] {m_d, m_e};
		m_model.add(a1);
		m_l1.init();
		m_model.add(a2);
		TkTestUtil.assertSameElements(a2, m_l1.getAdded());
		assertTrue(m_l1.isChanged());
		
		// tabIndex < 0
		setUpImpl();
		m_b.setTabIndex(-1);
		a1 = new Focusable[] {m_a, m_b, m_c};
		a2 = new Focusable[] {m_b};
		m_model.add(a1);
		assertEquals(2, m_l1.getAdded().length);
		m_l1.init();
		m_model.add(a2);
		assertFalse(m_l1.isChanged());		
		
		// null array
		setUpImpl();
		try
		{
			m_model.add((Focusable[]) null);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// null widget
		setUpImpl();
		a1 = new Focusable[] {m_a, null, m_b};
		try
		{
			m_model.add(a1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// widget already present
		setUpImpl();
		a1 = new Focusable[] {m_a, m_b, m_a};
		a2 = new Focusable[] {m_c, m_a, m_d};
		m_model.add(m_a);
		try
		{
			m_model.add(a1);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		try
		{
			m_model.add(a2);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
	}
	
	public void testClear()
	{
		setUpImpl();
		Focusable[] a = {m_a, m_b, m_c};
		
		// basic test
		m_model.clear();
		assertEquals(0, m_model.getSize());
		
		// listeners notified
		m_model.add(a);
		assertEquals(3, m_model.getSize());
		m_l1.init();
		m_model.clear();
		assertEquals(0, m_model.getSize());
		TkTestUtil.assertSameElements(a, m_l1.getRemoved());
		
		// reset current & blur 
		m_model.add(a);
		m_model.setFocusWidget(m_a);
		m_model.setFocusWidget(m_b);
		m_model.clear();
		assertSame(null, m_model.getCurrentWidget());
		assertEquals(-1, m_model.getCurrentIndex());
		assertEquals(-1, m_model.getBlurIndex());
	}
	
	public void testGetCurrentIndex()
	{
		setUpImpl();
		assertEquals(-1, m_model.getCurrentIndex());
	}
	
	public void testGetIndexOf()
	{
		setUpImpl();
		assertEquals(-1, m_model.getIndexOf(m_a));
		m_model.add(m_a);
		assertEquals(0, m_model.getIndexOf(m_a));
		m_model.add(m_b);
		assertEquals(0, m_model.getIndexOf(m_a));
		assertEquals(1, m_model.getIndexOf(m_b));
		assertEquals(-1, m_model.getIndexOf(m_c));
		
		// null argument
		setUpImpl();
		try
		{
			m_model.getIndexOf(null);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
	}
	
	public void testGetWidgetAt()
	{
		// basic test
		setUpImpl();
		m_model.add(m_a);
		assertSame(m_a, m_model.getWidgetAt(0));
		m_model.add(m_b);
		assertSame(m_a, m_model.getWidgetAt(0));
		assertSame(m_b, m_model.getWidgetAt(1));
		
		// negative index
		setUpImpl();
		try
		{
			m_model.getWidgetAt(-1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// invalid index
		setUpImpl();
		try
		{
			m_model.getWidgetAt(0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		try
		{
			m_model.getWidgetAt(1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
	}
	
	public void testRemove()
	{
		// basic test
		setUpImpl();
		m_model.add(m_a);
		m_model.remove(m_a);
		assertEquals(0, m_model.getSize());
		
		// first, middle, last
		setUpImpl();
		m_model.add(new Focusable[] {m_a, m_b, m_c, m_d, m_e});
		assertEquals(5, m_model.getSize());
		m_model.remove(m_a);
		m_model.remove(m_c);
		m_model.remove(m_e);
		assertEquals(2, m_model.getSize());
		
		// remove widget not in model
		setUpImpl();
		m_model.remove(m_a);
		assertEquals(0, m_model.getSize());
		
		// listeners notified
		setUpImpl();
		m_model.add(new Focusable[] {m_a, m_b, m_c, m_d, m_e});
		m_l1.init();
		m_model.remove(m_a);
		assertSame(m_a, m_l1.getRemoved()[0]);
		m_l1.init();
		m_model.remove(m_c);
		assertSame(m_c, m_l1.getRemoved()[0]);
		
		// remove focused widget
		setUpImpl();
		m_model.add(new Focusable[] {m_a, m_b, m_c, m_d, m_e});
		m_model.setFocusWidget(m_d);
		m_model.remove(m_d);
		assertNull(m_model.getCurrentWidget());
		
		// remove blur widget
		setUpImpl();
		m_model.add(new Focusable[] {m_a, m_b, m_c, m_d, m_e});
		m_model.setFocusWidget(m_c);
		m_model.setFocusWidget(m_d);
		m_model.remove(m_c);
		assertSame(m_d, m_model.getCurrentWidget());
		assertNull(m_model.getBlurWidget());
		
		// shift down (current, blur index)
		setUpImpl();
		m_model.add(new Focusable[] {m_a, m_b, m_c, m_d, m_e});
		m_model.setFocusWidget(m_c);
		m_model.setFocusWidget(m_d);
		m_model.remove(m_b);
		assertSame(m_c, m_model.getBlurWidget());
		assertSame(m_d, m_model.getCurrentWidget());
	}
	
	public void testGetCurrentWidget()
	{
		// initial state
		setUpImpl();
		assertSame(null, m_model.getCurrentWidget());
		
		// basic test
		setUpImpl();
		m_model.add(m_a);
		m_model.setFocusWidget(m_a);
		assertSame(m_a, m_model.getCurrentWidget());

		// no widget focused
		setUpImpl();
		m_model.add(m_a);
		m_model.setFocusWidget(null);
		assertSame(null, m_model.getCurrentWidget());
	}
	
	public void testBlurWidget()
	{
		// initial state
		setUpImpl();
		assertSame(null, m_model.getBlurWidget());
		
		// basic test
		setUpImpl();
		m_model.add(m_a);
		m_model.setFocusWidget(m_a);
		m_model.setFocusWidget(null);
		assertSame(m_a, m_model.getBlurWidget());
	}
	
	public void testSetFocusWidget()
	{
		// basic test
		setUpImpl();
		m_model.add(new Focusable[] {m_a, m_b, m_c});
		m_l1.init();
		
		m_model.setFocusWidget(m_a);
		assertSame(m_a, m_model.getCurrentWidget());
		assertSame(null, m_l1.getPrevious());
		assertSame(m_a, m_l1.getCurrent());
		assertEquals(1, m_l1.getNotificationCount());
		
		m_model.setFocusWidget(m_b);
		assertSame(m_b, m_model.getCurrentWidget());
		assertSame(m_a, m_l1.getPrevious());
		assertSame(m_b, m_l1.getCurrent());
		assertEquals(2, m_l1.getNotificationCount());
		
		m_model.setFocusWidget(m_c);
		assertSame(m_c, m_model.getCurrentWidget());
		assertSame(m_b, m_l1.getPrevious());
		assertSame(m_c, m_l1.getCurrent());
		assertEquals(3, m_l1.getNotificationCount());
		
		// set null
		setUpImpl();
		m_model.add(new Focusable[] {m_a, m_b, m_c});
		m_model.setFocusWidget(m_a);
		m_l1.init();
		m_model.setFocusWidget(null);
		assertSame(null, m_model.getCurrentWidget());
		assertSame(m_a, m_model.getBlurWidget());
		assertSame(null, m_l1.getCurrent());
		assertEquals(1, m_l1.getNotificationCount());

		// set null twice
		setUpImpl();
		m_model.add(m_a);
		m_model.setFocusWidget(m_a);
		m_model.setFocusWidget(null);
		m_model.setFocusWidget(null);
		assertSame(m_a, m_model.getBlurWidget());
		
		// set null w/ empty model
		setUpImpl();
		m_model.setFocusWidget(null);
		assertSame(null, m_model.getCurrentWidget());
		assertEquals(0, m_l1.getNotificationCount());
		
		// set to widget not in model
		try
		{
			m_model.setFocusWidget(m_a);
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// unchanged
		setUpImpl();
		m_model.add(new Focusable[] {m_a, m_b, m_c});
		m_model.setFocusWidget(m_b);
		m_l1.init();
		m_model.setFocusWidget(m_b);
		assertEquals(0, m_l1.getNotificationCount());
	}
	
	public void testGetNextWidget()
	{
		// basic test
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		assertSame(m_a, m_model.getNextWidget());
		m_model.setFocusWidget(m_a);
		assertSame(m_b, m_model.getNextWidget());
		m_model.setFocusWidget(m_b);
		assertSame(m_c, m_model.getNextWidget());
		m_model.setFocusWidget(m_c);
		assertSame(m_a, m_model.getNextWidget());
		
		// single widget, already focused
		setUpImpl();
		m_model.add(m_a);
		m_model.setFocusWidget(m_a);
		assertSame(m_a, m_model.getNextWidget());
		
		// start from blur widget if no focus
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		m_model.setFocusWidget(m_b);
		m_model.setFocusWidget(null);
		assertEquals(m_c, m_model.getNextWidget());
		
		// first widget disabled
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		m_a.setEnabled(false);
		m_model.setFocusWidget(m_c);
		assertSame(m_b, m_model.getNextWidget());
		
		// middle widget disabled
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		m_b.setEnabled(false);
		m_model.setFocusWidget(m_a);
		assertSame(m_c, m_model.getNextWidget());
		
		// last widget disabled
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		m_c.setEnabled(false);
		m_model.setFocusWidget(m_b);
		assertSame(m_a, m_model.getNextWidget());
		
		// no focus, first widget disabled
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b});
		m_a.setEnabled(false);
		assertSame(m_b, m_model.getNextWidget());
		
		// no focus, single disabled widget
		setUpImpl();
		m_a.setEnabled(false);
		m_model.add(m_a);
		assertSame(null, m_model.getNextWidget());	
		
		// single widget is focused and disabled
		setUpImpl();
		m_a.setEnabled(false);
		m_model.add(m_a);
		m_model.setFocusWidget(m_a);
		assertSame(m_a, m_model.getNextWidget());
		
		// multiple disabled widgets
		setUpImpl();
		m_b.setEnabled(false);
		m_c.setEnabled(false);
		m_model.add(new Focusable[]{m_a, m_b, m_c, m_d});
		m_model.setFocusWidget(m_a);
		assertSame(m_d, m_model.getNextWidget());
		
		// multiple disabled widgets (across boundary)
		setUpImpl();
		m_a.setEnabled(false);
		m_d.setEnabled(false);
		m_model.add(new Focusable[]{m_a, m_b, m_c, m_d});
		m_model.setFocusWidget(m_c);
		assertSame(m_b, m_model.getNextWidget());
		
		// no widgets
		setUpImpl();
		try
		{
			m_model.getNextWidget();
			fail();
		}
		catch (IllegalStateException e)
		{
			// EXPECTED
		}
	}
	
	public void testGetPreviousWidget()
	{
		// basic test
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		assertSame(m_c, m_model.getPreviousWidget());
		m_model.setFocusWidget(m_c);
		assertSame(m_b, m_model.getPreviousWidget());
		m_model.setFocusWidget(m_b);
		assertSame(m_a, m_model.getPreviousWidget());
		m_model.setFocusWidget(m_a);
		assertSame(m_c, m_model.getPreviousWidget());
		
		// single widget, already focused
		setUpImpl();
		m_model.add(m_a);
		m_model.setFocusWidget(m_a);
		assertSame(m_a, m_model.getPreviousWidget());
		
		// start from blur widget if no focus
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		m_model.setFocusWidget(m_b);
		m_model.setFocusWidget(null);
		assertEquals(m_a, m_model.getPreviousWidget());
		
		// first widget disabled
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		m_a.setEnabled(false);
		m_model.setFocusWidget(m_b);
		assertSame(m_c, m_model.getPreviousWidget());
		
		// middle widget disabled
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		m_b.setEnabled(false);
		m_model.setFocusWidget(m_c);
		assertSame(m_a, m_model.getPreviousWidget());
		
		// last widget disabled
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b, m_c});
		m_c.setEnabled(false);
		m_model.setFocusWidget(m_a);
		assertSame(m_b, m_model.getPreviousWidget());
		
		// no focus, last widget disabled
		setUpImpl();
		m_model.add(new Focusable[]{m_a, m_b});
		m_b.setEnabled(false);
		assertSame(m_a, m_model.getPreviousWidget());
		
		// no focus, single disabled widget
		setUpImpl();
		m_a.setEnabled(false);
		m_model.add(m_a);
		assertSame(null, m_model.getPreviousWidget());	
		
		// single widget is focused and disabled
		setUpImpl();
		m_a.setEnabled(false);
		m_model.add(m_a);
		m_model.setFocusWidget(m_a);
		assertSame(m_a, m_model.getPreviousWidget());
		
		// multiple disabled widgets
		setUpImpl();
		m_b.setEnabled(false);
		m_c.setEnabled(false);
		m_model.add(new Focusable[]{m_a, m_b, m_c, m_d});
		m_model.setFocusWidget(m_d);
		assertSame(m_a, m_model.getPreviousWidget());
		
		// multiple disabled widgets (across boundary)
		setUpImpl();
		m_a.setEnabled(false);
		m_d.setEnabled(false);
		m_model.add(new Focusable[]{m_a, m_b, m_c, m_d});
		m_model.setFocusWidget(m_b);
		assertSame(m_c, m_model.getPreviousWidget());
		
		// no widgets
		setUpImpl();
		try
		{
			m_model.getPreviousWidget();
			fail();
		}
		catch (IllegalStateException e)
		{
			// EXPECTED
		}
	}
}
