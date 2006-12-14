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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class RowPanelTC extends GWTTestCase
{
	private RowPanel m_panel;
	private Element m_tbody;
	private Widget m_a, m_b, m_c, m_d, m_e;
	
	public String getModuleName()
	{
		return "asquare.gwt.tk.TkTc";
	}
	
	protected void setUpImpl()
	{
		m_panel = new RowPanel();
		Element table = m_panel.getElement();
		m_tbody = DOM.getFirstChild(table);
		m_a = new Label("a"); 
		m_b = new Label("b"); 
		m_c = new Label("c"); 
		m_d = new Label("d"); 
		m_e = new Label("e");
	}
	
	public void testGetCellCount()
	{
		setUpImpl();
		
		assertEquals(0, m_panel.getCellCount());
		m_panel.insertCell(0);
		assertEquals(1, m_panel.getCellCount());
		m_panel.insertCell(1);
		assertEquals(2, m_panel.getCellCount());
		m_panel.addCell();
		assertEquals(3, m_panel.getCellCount());
		m_panel.removeCell(0);
		assertEquals(2, m_panel.getCellCount());
		m_panel.removeCell(1);
		assertEquals(1, m_panel.getCellCount());
		m_panel.removeCell(0);
		assertEquals(0, m_panel.getCellCount());
		try
		{
			m_panel.removeCell(0);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		assertEquals(0, m_panel.getCellCount());
	}
	
	public void testAddCell()
	{
		setUpImpl();
		
		assertEquals(0, m_panel.getCellCount());
		m_panel.addCell();
		assertEquals(1, m_panel.getCellCount());
		m_panel.addCell();
		assertEquals(2, m_panel.getCellCount());
	}
	
	public void testInsertCell()
	{
		setUpImpl();
		
		// insert into empty
		m_panel.insertCell(0); // {A}
		Element rowA = DOM.getFirstChild(m_tbody);
		
		// insert first
		m_panel.insertCell(0); // {B, A}
		Element rowB = DOM.getFirstChild(m_tbody);
		assertEquals(rowA, DOM.getChild(m_tbody, 1));
		
		// insert between
		m_panel.insertCell(1); // {B, C, A}
		assertEquals(rowA, DOM.getChild(m_tbody, 2));
		assertEquals(rowB, DOM.getChild(m_tbody, 0));
		
		// insert last
		m_panel.insertCell(3); // {B, C, A, D}
		assertEquals(4, m_panel.getCellCount());
		
		try
		{
			m_panel.insertCell(-1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		try
		{
			m_panel.insertCell(5);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
	}
	
	public void testRemoveCell()
	{
		setUpImpl();
		
		// 0 when no rows
		try
		{
			m_panel.removeCell(0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// negative index
		try
		{
			m_panel.removeCell(-1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// non-existant row when other rows exist
		m_panel.addCell();
		try
		{
			m_panel.removeCell(1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		m_panel.addCell();
		m_panel.addCell();
		Element rowA = DOM.getChild(m_tbody, 0);
		Element rowB = DOM.getChild(m_tbody, 1);
		Element rowC = DOM.getChild(m_tbody, 2);
		
		// assert order of remaining items
		m_panel.removeCell(1);
		assertEquals(rowA, DOM.getChild(m_tbody, 0));
		assertEquals(rowC, DOM.getChild(m_tbody, 1));
		m_panel.removeCell(0);
		assertEquals(rowC, DOM.getChild(m_tbody, 0));
		m_panel.removeCell(0);
		assertEquals(0, m_panel.getCellCount());
		
		// row removal with child widgets
		m_panel.addCell();
		m_panel.addWidget(m_a, false);
		m_panel.addWidget(m_b, false);
		m_panel.addCell();
		m_panel.addWidget(m_c, false);
		m_panel.addWidget(m_d, false);
		m_panel.removeCell(0);
		assertEquals(1, m_panel.getCellCount());
		assertSame(m_c, m_panel.getWidgetAt(0, 0));
		assertSame(m_d, m_panel.getWidgetAt(0, 1));
	}
	
	public void testClear()
	{
		setUpImpl();
		
		m_panel.add(m_a);
		m_panel.add(m_b);
		m_panel.addCell();
		m_panel.add(m_c);
		m_panel.addWidgetTo(m_d, 0);
		m_panel.insertCell(1);
		m_panel.insertWidgetAt(m_e, 1, 0);
		
		m_panel.clear();
		assertEquals(0, m_panel.getCellCount());
		assertEquals(0, m_panel.getWidgetCount());
		
		m_panel.clear();
	}
	
	public void testClearCell()
	{
		setUpImpl();
		
		// negative index
		try
		{
			m_panel.clearCell(-1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// invalid index
		try
		{
			m_panel.clearCell(0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// basic test
		m_panel.addCell();
		DOM.setInnerHTML(m_panel.getCellElement(0), "<b>spongebob</b>");
		m_panel.addWidgetTo(m_a, 0);
		m_panel.clearCell(0);
		assertEquals("", DOM.getInnerHTML(m_panel.getCellElement(0)));
		
		// clear an empty cell
		m_panel.addCell();
		m_panel.clearCell(0);
	}
	
	public void testGetWidgetCount()
	{
		setUpImpl();
		
		assertEquals(0, m_panel.getWidgetCount());
		assertEquals(0, m_panel.getCellCount());
		m_panel.addCell();
		assertEquals(0, m_panel.getWidgetCount());
		assertEquals(1, m_panel.getCellCount());
		m_panel.add(m_a);
		assertEquals(1, m_panel.getWidgetCount());
		assertEquals(2, m_panel.getCellCount());
		m_panel.addCell();
		assertEquals(1, m_panel.getWidgetCount());
		assertEquals(3, m_panel.getCellCount());
		m_panel.add(m_a);
		assertEquals(1, m_panel.getWidgetCount());
		assertEquals(3, m_panel.getCellCount());
		m_panel.remove(m_a);
		assertEquals(0, m_panel.getWidgetCount());
		assertEquals(2, m_panel.getCellCount());
	}
	
	public void testGetWidgetAt()
	{
		setUpImpl();
		
		// negative row
		try
		{
			m_panel.getWidgetAt(-1, 0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// invalid row
		try
		{
			m_panel.getWidgetAt(0, 0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// negative index
		m_panel.addCell();
		try
		{
			m_panel.getWidgetAt(0, -1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// invalid index
		m_panel.addCell();
		try
		{
			m_panel.getWidgetAt(0, 0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
	}
	
	public void testGetCellIndexOf()
	{
		setUpImpl();
		
		// not present
		assertEquals(-1, m_panel.getCellIndexOf(m_a));
		
		// basic test
		m_panel.add(m_a);
		assertEquals(0, m_panel.getCellIndexOf(m_a));
		
		// two in same
		m_panel.insertWidgetAt(m_b, 0, 1);
		assertEquals(0, m_panel.getCellIndexOf(m_a));
		assertEquals(0, m_panel.getCellIndexOf(m_b));
		
		// blank rows
		m_panel.insertCell(0);
		m_panel.addCell();
		m_panel.add(m_c);
		m_panel.addCell();
		assertEquals(1, m_panel.getCellIndexOf(m_a));
		assertEquals(1, m_panel.getCellIndexOf(m_b));
		assertEquals(3, m_panel.getCellIndexOf(m_c));
		
		m_panel.clear();
		assertEquals(-1, m_panel.getCellIndexOf(m_a));
		assertEquals(-1, m_panel.getCellIndexOf(m_b));
		assertEquals(-1, m_panel.getCellIndexOf(m_c));
	}
	
	public void testAdd()
	{
		setUpImpl();
		
		// should automatically create new row
		assertEquals(0, m_panel.getCellCount());
		assertEquals(0, m_panel.getWidgetCount());
		m_panel.add(m_a);
		assertEquals(1, m_panel.getCellCount());
		assertEquals(1, m_panel.getWidgetCount());
		m_panel.add(m_b);
		assertEquals(2, m_panel.getCellCount());
		assertEquals(2, m_panel.getWidgetCount());
		assertSame(m_a, m_panel.getWidgetAt(0, 0));
		assertSame(m_b, m_panel.getWidgetAt(1, 0));
		
		// add same widget again
		m_panel.add(m_a);
		assertEquals(2, m_panel.getCellCount());
		assertEquals(2, m_panel.getWidgetCount());
		assertSame(m_b, m_panel.getWidgetAt(0, 0));
		assertSame(m_a, m_panel.getWidgetAt(1, 0));
	}
	
	public void testAddWidget()
	{
		setUpImpl();
		
		// create new row automatically
		m_panel.addWidget(m_a, true);
		assertSame(m_a, m_panel.getWidgetAt(0, 0));
		
		// test widget order
		m_panel.addWidget(m_b, false);
		assertSame(m_a, m_panel.getWidgetAt(0, 0));
		assertSame(m_b, m_panel.getWidgetAt(0, 1));
		
		// add a second row
		m_panel.addCell();
		m_panel.addWidget(m_c, false);
		m_panel.addWidget(m_d, false);
		assertSame(m_a, m_panel.getWidgetAt(0, 0));
		assertSame(m_b, m_panel.getWidgetAt(0, 1));
		assertSame(m_c, m_panel.getWidgetAt(1, 0));
		assertSame(m_d, m_panel.getWidgetAt(1, 1));
		
		// automatic row removal--side-effect of add
		m_panel.clear();
		m_panel.addWidget(m_a, true);
		m_panel.addWidget(m_b, true);
		m_panel.addWidget(m_a, true);
		assertSame(m_b, m_panel.getWidgetAt(0, 0));
		assertSame(m_a, m_panel.getWidgetAt(1, 0));
		
		m_panel.clear();
		assertEquals(0, m_panel.getWidgetCount());
		assertEquals(0, m_panel.getCellCount());
		m_panel.addWidget(m_a, true);
		assertEquals(1, m_panel.getWidgetCount());
		assertEquals(1, m_panel.getCellCount());
		m_panel.addWidget(m_a, true);
		assertEquals(1, m_panel.getWidgetCount());
		assertEquals(1, m_panel.getCellCount());
		
		// add with no rows
		m_panel.clear();
		try
		{
			m_panel.addWidget(m_a, false);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
	}
	
	public void testAddWidgetTo()
	{
		setUpImpl();
		
		// negative index
		try
		{
			m_panel.addWidgetTo(m_a, -1);
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		// invalid index
		try
		{
			m_panel.addWidgetTo(m_a, 0);
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		m_panel.addCell();

		// invalid index > 0
		try
		{
			m_panel.addWidgetTo(m_a, 1);
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		m_panel.clear();
		m_panel.addCell();
		m_panel.addWidgetTo(m_a, 0);
		m_panel.addWidgetTo(m_b, 0);
		m_panel.addCell();
		m_panel.addWidgetTo(m_c, 1);
		m_panel.addWidgetTo(m_d, 1);
		m_panel.addCell();
		m_panel.addWidgetTo(m_e, 0);
		
		assertSame(m_a, m_panel.getWidgetAt(0, 0));
		assertSame(m_b, m_panel.getWidgetAt(0, 1));
		assertSame(m_e, m_panel.getWidgetAt(0, 2));
		assertSame(m_c, m_panel.getWidgetAt(1, 0));
		assertSame(m_d, m_panel.getWidgetAt(1, 1));
		assertEquals(3, m_panel.getCellCount());
		assertEquals(5, m_panel.getWidgetCount());
	}
	
	public void testInsert()
	{
		setUpImpl();
		
		// negative row
		try
		{
			m_panel.insert(m_a, -1);
			fail();
		}
		catch (IndexOutOfBoundsException e) {
			// EXPECTED
		}
		
		// invalid row
		try
		{
			m_panel.insert(m_a, 1);
			fail();
		}
		catch (IndexOutOfBoundsException e) {
			// EXPECTED
		}
		
		// core functionality
		m_panel.insert(m_a, 0); // {{A}}
		m_panel.insert(m_b, 0); // {{B},{A}}
		m_panel.insert(m_c, 2); // {{B},{A},{C}}
		m_panel.insertCell(0);   // {{ },{B},{A},{C}}
		m_panel.insert(m_d, 0); // {{D},{ },{B},{A},{C}}
		assertSame(m_d, m_panel.getWidgetAt(0, 0));
		assertSame(m_b, m_panel.getWidgetAt(2, 0));
		assertSame(m_a, m_panel.getWidgetAt(3, 0));
		assertSame(m_c, m_panel.getWidgetAt(4, 0));

		// automatic row removal
		m_panel.insert(m_b, 1); // {{D},{B},{ },{A},{C}}
		m_panel.insert(m_c, 4); // {{D},{B},{ },{A},{C}}
		m_panel.insert(m_d, 0); // {{D},{B},{ },{A},{C}}
		assertSame(m_d, m_panel.getWidgetAt(0, 0));
		assertSame(m_b, m_panel.getWidgetAt(1, 0));
		assertSame(m_a, m_panel.getWidgetAt(3, 0));
		assertSame(m_c, m_panel.getWidgetAt(4, 0));
	}
	
	public void testInsertWidgetAt()
	{
		setUpImpl();
		
		// negative rowIndex
		try
		{
			m_panel.insertWidgetAt(m_a, -1, 0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// invalid rowIndex
		try
		{
			m_panel.insertWidgetAt(m_a, 0, 0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		m_panel.addCell();
		
		// negative wIndex
		try
		{
			m_panel.insertWidgetAt(m_a, 0, -1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// invalid wIndex
		try
		{
			m_panel.insertWidgetAt(m_a, 0, 1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// basic tests
		m_panel.clear();
		m_panel.addCell();
		m_panel.insertWidgetAt(m_a, 0, 0);
		assertSame(m_a, m_panel.getWidgetAt(0, 0));
		
		m_panel.insertWidgetAt(m_b, 0, 0);
		assertSame(m_a, m_panel.getWidgetAt(0, 1));
		assertSame(m_b, m_panel.getWidgetAt(0, 0));
		
		m_panel.insertWidgetAt(m_c, 0, 2);
		assertSame(m_b, m_panel.getWidgetAt(0, 0));
		assertSame(m_a, m_panel.getWidgetAt(0, 1));
		assertSame(m_c, m_panel.getWidgetAt(0, 2));
		
		m_panel.addCell();
		m_panel.insertWidgetAt(m_d, 1, 0);
		assertSame(m_b, m_panel.getWidgetAt(0, 0));
		assertSame(m_a, m_panel.getWidgetAt(0, 1));
		assertSame(m_c, m_panel.getWidgetAt(0, 2));
		assertSame(m_d, m_panel.getWidgetAt(1, 0));
		
		m_panel.insertCell(0);
		m_panel.insertWidgetAt(m_e, 0, 0);
		assertSame(m_e, m_panel.getWidgetAt(0, 0));
		assertSame(m_b, m_panel.getWidgetAt(1, 0));
		assertSame(m_a, m_panel.getWidgetAt(1, 1));
		assertSame(m_c, m_panel.getWidgetAt(1, 2));
		assertSame(m_d, m_panel.getWidgetAt(2, 0));
		
		/*
		 * automatic row removal
		 * (we're skirting the issue where the rowIndex
		 * changes when w is removed)
		 */
		m_panel.clear();
		m_panel.addCell();
		m_panel.addCell();
		m_panel.addCell();
		m_panel.insertWidgetAt(m_a, 0, 0); // {A}
		m_panel.insertWidgetAt(m_b, 1, 0); // {A,B}
		m_panel.insertWidgetAt(m_c, 2, 0); // {A,B,C}
		m_panel.insertWidgetAt(m_a, 0, 0); // {AB,C}
		m_panel.insertWidgetAt(m_c, 0, 2); // {ABC}
		assertSame(m_a, m_panel.getWidgetAt(0, 0));
		assertSame(m_b, m_panel.getWidgetAt(0, 1));
		assertSame(m_c, m_panel.getWidgetAt(0, 2));
		assertEquals(1, m_panel.getCellCount());
		assertEquals(3, m_panel.getWidgetCount());
		
		// row index falls out of range due to automatic row removal
		m_panel.clear();
		m_panel.add(m_a);
		m_panel.add(m_b);
		try
		{
			m_panel.insertWidgetAt(m_a, 1, 1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		assertEquals(1, m_panel.getWidgetCount());
		assertEquals(1, m_panel.getCellCount());
		assertSame(m_b, m_panel.getWidgetAt(0, 0));
		
		// w index falls out of range due to automatic removal
		m_panel.clear();
		m_panel.add(m_a);
		m_panel.addWidgetTo(m_b, 0);
		m_panel.addWidgetTo(m_c, 0); // {A,B,C}
		try
		{
			m_panel.insertWidgetAt(m_a, 0, 3);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		assertEquals(2, m_panel.getWidgetCount());
		assertSame(m_b, m_panel.getWidgetAt(0, 0));
		assertSame(m_c, m_panel.getWidgetAt(0, 1));
	}
	
	public void testRemove()
	{
		setUpImpl();
		
		// automatic row removal
		m_panel.clear();
		m_panel.addWidget(m_a, true);
		m_panel.addWidget(m_b, false);
		m_panel.addWidget(m_c, false);
		assertEquals(1, m_panel.getCellCount());
		m_panel.remove(m_a);
		assertEquals(1, m_panel.getCellCount());
		m_panel.remove(m_c);
		assertEquals(1, m_panel.getCellCount());
		m_panel.remove(m_b);
		assertEquals(0, m_panel.getCellCount());
		
		// return value
		m_panel.clear();
		m_panel.addWidget(m_a, true);
		boolean contained = m_panel.remove(m_a);
		assertTrue(contained);
		contained = m_panel.remove(m_a);
		assertFalse(contained);
	}
	
	public void testRemove2()
	{
		setUpImpl();
		
		// clearing TD
		m_panel.addWidget(m_a, true);
		m_panel.remove(m_a, false);
		assertEquals(0, DOM.getChildCount(m_panel.getCellElement(0)));
	}
	
	public void testGetCellElement()
	{
		setUpImpl();
		
		// negative index
		try
		{
			m_panel.getCellElement(-1);
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		// invalid index
		try
		{
			m_panel.getCellElement(0);
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
	}
	
	public void testGetCellStyleName()
	{
		setUpImpl();
		
		// negative index
		try
		{
			m_panel.getCellStyleName(-1);
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		// invalid index
		try
		{
			m_panel.getCellStyleName(0);
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		m_panel.addCell();
		assertEquals("", m_panel.getCellStyleName(0));
	}
	
	public void testAddCellStyleName()
	{
		setUpImpl();
		
		// no rows
		try
		{
			m_panel.addCellStyleName("foo");
			fail();
		}
		catch (IllegalStateException ex)
		{
			// EXPECTED
		}
		
		m_panel.addCell();

		// empty string
		try
		{
			m_panel.addCellStyleName("");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// EXPECTED
		}
		
		// basic test
		m_panel.addCellStyleName("foo");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		
		// duplicate name
		m_panel.addCellStyleName("foo");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		
		// second name
		m_panel.addCellStyleName("bar");
		assertEquals("foo bar", m_panel.getCellStyleName(0).trim());
	}
	
	public void testAddCellStyleName2()
	{
		setUpImpl();
		
		// negative index
		try
		{
			m_panel.addCellStyleName(-1, "foo");
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		// invalid index
		try
		{
			m_panel.addCellStyleName(0, "foo");
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		m_panel.addCell();
		m_panel.addCell();
		
		// empty string
		try
		{
			m_panel.addCellStyleName("");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// EXPECTED
		}
		
		// basic test
		m_panel.addCellStyleName(0, "foo");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		
		// second row
		m_panel.addCellStyleName(1, "bar");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		assertEquals("bar", m_panel.getCellStyleName(1).trim());
	}
	
	public void testSetCellStyleName()
	{
		setUpImpl();
		
		// no rows
		try
		{
			m_panel.setCellStyleName("foo");
			fail();
		}
		catch (IllegalStateException ex)
		{
			// EXPECTED
		}
		
		m_panel.addCell();

		// empty string
		m_panel.setCellStyleName("");
		assertEquals("", m_panel.getCellStyleName(0).trim());
		
		// basic test
		m_panel.setCellStyleName("foo");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		
		// duplicate name
		m_panel.setCellStyleName("foo");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		
		// second name
		m_panel.setCellStyleName("bar");
		assertEquals("bar", m_panel.getCellStyleName(0).trim());
	}
	
	public void testSetCellStyleName2()
	{
		setUpImpl();
		
		// negative index
		try
		{
			m_panel.setCellStyleName(-1, "foo");
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		// invalid index
		try
		{
			m_panel.setCellStyleName(0, "foo");
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		m_panel.addCell();
		m_panel.addCell();

		// empty string
		m_panel.setCellStyleName(0, "");
		assertEquals("", m_panel.getCellStyleName(0).trim());
		
		// basic test
		m_panel.setCellStyleName(0, "foo");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		
		// second row
		m_panel.setCellStyleName(1, "bar");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		assertEquals("bar", m_panel.getCellStyleName(1).trim());
	}
	
	public void testRemoveCellStyleName()
	{
		setUpImpl();
		
		// no rows
		try
		{
			m_panel.removeCellStyleName("foo");
			fail();
		}
		catch (IllegalStateException ex)
		{
			// EXPECTED
		}
		
		m_panel.addCell();
		
		// empty string
		try
		{
			m_panel.removeCellStyleName("");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// EXPECTED
		}
		
		// basic test
		m_panel.setCellStyleName("foo");
		m_panel.removeCellStyleName("foo");
		assertEquals("", m_panel.getCellStyleName(0).trim());
		
		// remove 1st of 2
		m_panel.setCellStyleName("foo bar");
		m_panel.removeCellStyleName("foo");
		assertEquals("bar", m_panel.getCellStyleName(0).trim());

		// remove 2nd of 2
		m_panel.setCellStyleName("foo bar");
		m_panel.removeCellStyleName("bar");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		
		// duplicate name
		m_panel.setCellStyleName("foo foo");
		m_panel.removeCellStyleName("foo");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		
		// invalid name
		m_panel.setCellStyleName("foo bar");
		m_panel.removeCellStyleName("baz");
		assertEquals("foo bar", m_panel.getCellStyleName(0).trim());
		
		// invalid name empty string
		m_panel.setCellStyleName("");
		m_panel.removeCellStyleName("foo");
		assertEquals("", m_panel.getCellStyleName(0).trim());
	}
	
	public void testRemoveCellStyleName2()
	{
		setUpImpl();
		
		// negative index
		try
		{
			m_panel.removeCellStyleName(-1, "foo");
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		// invalid index
		try
		{
			m_panel.removeCellStyleName(0, "foo");
			fail();
		}
		catch (IndexOutOfBoundsException ex)
		{
			// EXPECTED
		}
		
		m_panel.addCell();
		m_panel.addCell();
		
		// empty string
		try
		{
			m_panel.removeCellStyleName("");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// EXPECTED
		}
		
		// basic test
		m_panel.setCellStyleName(0, "foo");
		m_panel.removeCellStyleName(0, "foo");
		assertEquals("", m_panel.getCellStyleName(0).trim());
		
		// second row
		m_panel.setCellStyleName(0, "foo");
		m_panel.setCellStyleName(1, "bar");
		m_panel.removeCellStyleName(1, "bar");
		assertEquals("foo", m_panel.getCellStyleName(0).trim());
		assertEquals("", m_panel.getCellStyleName(1).trim());
	}
}
