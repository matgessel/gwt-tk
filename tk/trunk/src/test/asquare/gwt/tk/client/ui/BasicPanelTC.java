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
package asquare.gwt.tk.client.ui;

import asquare.gwt.tk.client.Tests;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class BasicPanelTC extends GWTTestCase
{
	private BasicPanel m_panel;
	private Widget m_a, m_b, m_c;
	
	public String getModuleName()
	{
		return Tests.getModuleName();
	}
	
	protected void setUpImpl()
	{
		m_panel = new BasicPanel();
		m_a = new Label("a");
		m_b = new Label("b");
		m_c = new Label("c");
	}
	
	public void testGetWidget()
	{
		setUpImpl();
		
		// negative index
		try
		{
			m_panel.getWidget(-1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// index > # children
		try
		{
			m_panel.getWidget(0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		m_panel.add(m_a);
		assertEquals(m_a, m_panel.getWidget(0));
		
		m_panel.add(m_b);
		m_panel.add(m_c);
		assertEquals(m_a, m_panel.getWidget(0));
		assertEquals(m_b, m_panel.getWidget(1));
		assertEquals(m_c, m_panel.getWidget(2));
	}
	
	public void testGetWidgetIndex()
	{
		setUpImpl();
		
		// invalid widget
		assertEquals(-1, m_panel.getWidgetIndex(m_a));
		
		m_panel.add(m_a);
		assertEquals(0, m_panel.getWidgetIndex(m_a));
		
		m_panel.add(m_b);
		m_panel.add(m_c);
		assertEquals(0, m_panel.getWidgetIndex(m_a));
		assertEquals(1, m_panel.getWidgetIndex(m_b));
		assertEquals(2, m_panel.getWidgetIndex(m_c));
	}
	
	public void testRemove_int()
	{
		setUpImpl();
		
		// negative index
		assertFalse(m_panel.remove(-1));
		
		// index > # children
		assertFalse(m_panel.remove(0));
		
		m_panel.add(m_a);
		assertTrue(m_panel.remove(0));
		assertEquals(-1, m_panel.getWidgetIndex(m_a));
		
		m_panel.add(m_a);
		m_panel.add(m_b);
		m_panel.add(m_c);
		assertTrue(m_panel.remove(0));
		assertTrue(m_panel.remove(1));
		assertTrue(m_panel.remove(0));
		assertEquals(-1, m_panel.getWidgetIndex(m_a));
		assertEquals(-1, m_panel.getWidgetIndex(m_b));
		assertEquals(-1, m_panel.getWidgetIndex(m_c));
	}
}
