/*
 * Copyright 2007 Mat Gessel <mat.gessel@gmail.com>
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
package asquare.gwt.sb.client.fw;

import junit.framework.TestCase;
import asquare.gwt.sb.client.util.IntRange;
import asquare.gwt.tk.testutil.TkTestUtil;

public class ListSelectionModelSingleTC extends TestCase
{
	private ListSelectionModelSingle m_model;
	private ListSelectionModelListenerStub m_l1;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_model = new ListSelectionModelSingle();
		m_l1 = new ListSelectionModelListenerStub();
	}
	
	public void testIsIndexSelected()
	{
		// negative index
		setupImpl();
		try
		{
			m_model.isIndexSelected(-1);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// empty model
		setupImpl();
		assertFalse(m_model.isIndexSelected(0));
		assertFalse(m_model.isIndexSelected(1));
		
		// basic test
		setupImpl();
		m_model.setSelectedIndex(0);
		assertTrue(m_model.isIndexSelected(0));
		m_model.setSelectedIndex(1);
		assertTrue(m_model.isIndexSelected(1));
	}
	
	public void testHasSelection()
	{
		setupImpl();
		assertFalse(m_model.hasSelection());
	}
	
	public void testGetSelectedIndex()
	{
		// empty model
		setupImpl();
		assertEquals(-1, m_model.getSelectedIndex());
		
		// basic test
		setupImpl();
		m_model.setSelectedIndex(0);
		assertEquals(0, m_model.getSelectedIndex());
		m_model.setSelectedIndex(1);
		assertEquals(1, m_model.getSelectedIndex());
	}
	
	public void testGetSelectedIndices()
	{
		// empty model
		setupImpl();
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		// basic test
		setupImpl();
		m_model.setSelectedIndex(0);
		TkTestUtil.assertEqualValues(new int[] {0}, m_model.getSelectedIndices());
		m_model.setSelectedIndex(1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
	}
	
	public void testGetMinSelectedIndex()
	{
		// empty model
		setupImpl();
		assertEquals(-1, m_model.getMinSelectedIndex());
		
		// basic test
		setupImpl();
		m_model.setSelectedIndex(0);
		assertEquals(0, m_model.getMinSelectedIndex());
		m_model.setSelectedIndex(1);
		assertEquals(1, m_model.getMinSelectedIndex());
	}
	
	public void testGetMaxSelectedIndex()
	{
		// empty model
		setupImpl();
		assertEquals(-1, m_model.getMaxSelectedIndex());
		
		// basic test
		setupImpl();
		m_model.setSelectedIndex(0);
		assertEquals(0, m_model.getMaxSelectedIndex());
		m_model.setSelectedIndex(1);
		assertEquals(1, m_model.getMaxSelectedIndex());
	}
	
	public void testSetSelectedIndex()
	{
		// negative index
		setupImpl();
		try
		{
			m_model.setSelectedIndex(-1);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// bounds
		setupImpl();
		m_model.setSelectedIndex(0);
		assertEquals(0, m_model.getSelectedIndex());
		m_model.setSelectedIndex(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, m_model.getSelectedIndex());
		
		// update
		setupImpl();
		m_model.addListener(m_l1);
		m_model.setSelectedIndex(0);
		assertTrue(m_l1.isChanged());
		m_l1.init();
		m_model.setSelectedIndex(0);
		assertFalse(m_l1.isChanged());
		m_model.setSelectedIndex(1);
		assertTrue(m_l1.isChanged());
		m_l1.init();
		m_model.setSelectedIndex(-1);
		assertTrue(m_l1.isChanged());
	}
	
	public void testClearSelection()
	{
		setupImpl();
		m_model.setSelectedIndex(10);
		assertEquals(10, m_model.getSelectedIndex());
		m_model.clearSelection();
		assertEquals(-1, m_model.getSelectedIndex());
		
		// update
		setupImpl();
		m_model.addSelectionRange(0, 0);
		m_model.addListener(m_l1);
		m_model.clearSelection();
		assertTrue(m_l1.isChanged());
		
		// anchor & lead indices updated
		setupImpl();
		m_model.addSelectionRange(0, 0);
		m_model.clearSelection();
		assertEquals(-1, m_model.getAnchorIndex());
		assertEquals(-1, m_model.getLeadIndex());
	}
	
	public void testGetSelectionSize()
	{
		// empty model
		setupImpl();
		assertEquals(0, m_model.getSelectionSize());
		
		// basic test
		setupImpl();
		m_model.setSelectedIndex(0);
		assertEquals(1, m_model.getSelectionSize());
		m_model.setSelectedIndex(1);
		assertEquals(1, m_model.getSelectionSize());
		m_model.clearSelection();
		assertEquals(0, m_model.getSelectionSize());
	}
	
	public void testAddSelectionRange()
	{
		// negative from
		setupImpl();
		try
		{
			m_model.addSelectionRange(-1, 1);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// negative to
		setupImpl();
		try
		{
			m_model.addSelectionRange(1, -1);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// range = 1
		setupImpl();
		m_model.addSelectionRange(0, 0);
		assertEquals(0, m_model.getSelectedIndex());
		m_model.addSelectionRange(1, 1);
		assertEquals(1, m_model.getSelectedIndex());
		
		// range > 1
		setupImpl();
		m_model.addSelectionRange(1, 2);
		assertEquals(2, m_model.getSelectedIndex());
		
		// 2nd index < first index
		setupImpl();
		m_model.addSelectionRange(2, 1);
		assertEquals(1, m_model.getSelectedIndex());
		
		// update
		setupImpl();
		m_model.addListener(m_l1);
		m_model.addSelectionRange(0, 0);
		assertTrue(m_l1.isChanged());
		m_l1.init();
		m_model.addSelectionRange(0, 0);
		assertFalse(m_l1.isChanged());
		
		// anchor & lead indices updated
		setupImpl();
		m_model.addSelectionRange(0, 0);
		assertEquals(0, m_model.getAnchorIndex());
		assertEquals(0, m_model.getLeadIndex());
		m_model.addSelectionRange(2, 5);
		assertEquals(5, m_model.getAnchorIndex());
		assertEquals(5, m_model.getLeadIndex());
		m_model.addSelectionRange(5, 2);
		assertEquals(2, m_model.getAnchorIndex());
		assertEquals(2, m_model.getLeadIndex());
	}
	
	public void testSetSelectionRange()
	{
		// negative from
		setupImpl();
		try
		{
			m_model.setSelectionRange(-1, 1);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// negative to
		setupImpl();
		try
		{
			m_model.setSelectionRange(1, -1);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// range = 1
		setupImpl();
		m_model.setSelectionRange(0, 0);
		assertEquals(0, m_model.getSelectedIndex());
		m_model.setSelectionRange(1, 1);
		assertEquals(1, m_model.getSelectedIndex());
		
		// range > 1
		setupImpl();
		m_model.setSelectionRange(1, 2);
		assertEquals(2, m_model.getSelectedIndex());
		
		// 2nd index < first index
		setupImpl();
		m_model.setSelectionRange(2, 1);
		assertEquals(1, m_model.getSelectedIndex());
		
		// update
		setupImpl();
		m_model.addListener(m_l1);
		m_model.setSelectionRange(0, 0);
		assertTrue(m_l1.isChanged());
		m_l1.init();
		m_model.setSelectionRange(0, 0);
		assertFalse(m_l1.isChanged());
		m_model.setSelectionRange(1, 1);
		assertTrue(m_l1.isChanged());
		
		// anchor & lead indices updated
		setupImpl();
		m_model.setSelectionRange(0, 0);
		assertEquals(0, m_model.getAnchorIndex());
		assertEquals(0, m_model.getLeadIndex());
		m_model.setSelectionRange(2, 5);
		assertEquals(5, m_model.getAnchorIndex());
		assertEquals(5, m_model.getLeadIndex());
		m_model.setSelectionRange(5, 2);
		assertEquals(2, m_model.getAnchorIndex());
		assertEquals(2, m_model.getLeadIndex());
	}
	
	public void testRemoveSelectionRange()
	{
		// negative from
		setupImpl();
		try
		{
			m_model.removeSelectionRange(-1, 1);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// negative to
		setupImpl();
		try
		{
			m_model.removeSelectionRange(1, -1);
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// empty model
		setupImpl();
		assertEquals(0, m_model.getSelectionSize());
		m_model.removeSelectionRange(0, 1);
		assertEquals(0, m_model.getSelectionSize());
		
		// range = 1
		setupImpl();
		m_model.setSelectedIndex(0);
		m_model.removeSelectionRange(0, 0);
		assertEquals(0, m_model.getSelectionSize());
		m_model.setSelectedIndex(1);
		m_model.removeSelectionRange(1, 1);
		assertEquals(0, m_model.getSelectionSize());
		
		// range > 1
		setupImpl();
		m_model.setSelectedIndex(3);
		m_model.removeSelectionRange(0, 5);
		assertEquals(0, m_model.getSelectionSize());
		
		// 2nd index < first index
		setupImpl();
		m_model.setSelectedIndex(3);
		m_model.removeSelectionRange(5, 0);
		assertEquals(0, m_model.getSelectionSize());
		
		// miss by 1
		setupImpl();
		m_model.setSelectedIndex(3);
		m_model.removeSelectionRange(0, 2);
		m_model.removeSelectionRange(2, 0);
		m_model.removeSelectionRange(4, 5);
		m_model.removeSelectionRange(5, 4);
		assertEquals(1, m_model.getSelectionSize());
		
		// update
		setupImpl();
		m_model.addListener(m_l1);
		m_model.addSelectionRange(0, 0);
		m_l1.init();
		m_model.removeSelectionRange(1, 1);
		assertFalse(m_l1.isChanged());
		m_model.removeSelectionRange(0, 0);
		assertTrue(m_l1.isChanged());
		
		// anchor & lead indices updated
		setupImpl();
		m_model.setSelectionRange(0, 0);
		m_model.removeSelectionRange(0, 0);
		assertEquals(-1, m_model.getAnchorIndex());
		assertEquals(-1, m_model.getLeadIndex());
		m_model.setSelectionRange(2, 2);
		m_model.removeSelectionRange(2, 5);
		assertEquals(-1, m_model.getAnchorIndex());
		assertEquals(-1, m_model.getLeadIndex());
		m_model.setSelectionRange(2, 2);
		m_model.removeSelectionRange(5, 2);
		assertEquals(-1, m_model.getAnchorIndex());
		assertEquals(-1, m_model.getLeadIndex());
	}
	
	public void testAdjustForItemsInserted()
	{
		// insert before selected index
		setupImpl();
		m_model.setSelectedIndex(1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		m_model.adjustForItemsInserted(0, 1);
		TkTestUtil.assertEqualValues(new int[] {2}, m_model.getSelectedIndices());
		
		// insert at selected index
		setupImpl();
		m_model.setSelectedIndex(1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		m_model.adjustForItemsInserted(1, 1);
		TkTestUtil.assertEqualValues(new int[] {2}, m_model.getSelectedIndices());
		
		// insert after selected index
		setupImpl();
		m_model.setSelectedIndex(1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		m_model.adjustForItemsInserted(2, 1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		
		// insert multiple values
		setupImpl();
		m_model.setSelectedIndex(1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		m_model.adjustForItemsInserted(0, 2);
		TkTestUtil.assertEqualValues(new int[] {3}, m_model.getSelectedIndices());
		
		// empty selection
		setupImpl();
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		m_model.adjustForItemsInserted(2, 1);
		m_model.adjustForItemsInserted(1, 2);
		m_model.adjustForItemsInserted(0, 1);
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		// update
		setupImpl();
		m_model.addListener(m_l1);
		m_model.adjustForItemsInserted(0, 1);
		assertFalse(m_l1.isChanged());
		
		m_model.setSelectedIndex(0);
		m_model.adjustForItemsInserted(0, 2);
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(0, 1)}, m_l1.getDeselectedRanges().toArray());
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(2, 1)}, m_l1.getSelectedRanges().toArray());
		
		// negative index
		setupImpl();
		try
		{
			m_model.adjustForItemsInserted(-1, 1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// 0 length
		setupImpl();
		try
		{
			m_model.adjustForItemsInserted(0, 0);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// anchor & lead indices updated
		setupImpl();
		m_model.setSelectionRange(0, 0);
		m_model.adjustForItemsInserted(0, 1);
		assertEquals(1, m_model.getAnchorIndex());
		assertEquals(1, m_model.getLeadIndex());
		m_model.adjustForItemsInserted(0, 1);
		assertEquals(2, m_model.getAnchorIndex());
		assertEquals(2, m_model.getLeadIndex());
		m_model.adjustForItemsInserted(3, 1);
		assertEquals(2, m_model.getAnchorIndex());
		assertEquals(2, m_model.getLeadIndex());
	}
	
	public void testAdjustForItemsRemoved()
	{
		// remove before selected index
		setupImpl();
		m_model.setSelectedIndex(1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(0, 1);
		TkTestUtil.assertEqualValues(new int[] {0}, m_model.getSelectedIndices());
		
		// remove selected index (via individual and range removal)
		setupImpl();
		m_model.setSelectedIndex(1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(1, 1);
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		m_model.setSelectedIndex(1);
		m_model.adjustForItemsRemoved(0, 2);
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		m_model.setSelectedIndex(1);
		m_model.adjustForItemsRemoved(1, 2);
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		m_model.setSelectedIndex(0);
		m_model.adjustForItemsRemoved(0, 2);
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		// remove after selected index
		setupImpl();
		m_model.setSelectedIndex(1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(2, 1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		
		// remove multiple values
		setupImpl();
		m_model.setSelectedIndex(2);
		TkTestUtil.assertEqualValues(new int[] {2}, m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(0, 2);
		TkTestUtil.assertEqualValues(new int[] {0}, m_model.getSelectedIndices());
		
		// empty selection
		setupImpl();
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(2, 1);
		m_model.adjustForItemsRemoved(1, 2);
		m_model.adjustForItemsRemoved(0, 1);
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		// update
		setupImpl();
		m_model.addListener(m_l1);
		m_model.adjustForItemsRemoved(0, 1);
		assertFalse(m_l1.isChanged());
		
		m_model.setSelectedIndex(1);
		m_model.adjustForItemsRemoved(0, 1);
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(1, 1)}, m_l1.getDeselectedRanges().toArray());
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(0, 1)}, m_l1.getSelectedRanges().toArray());
		
		m_l1.init();
		m_model.adjustForItemsRemoved(0, 1);
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(0, 1)}, m_l1.getDeselectedRanges().toArray());
		TkTestUtil.assertEqualValues(new IntRange[0], m_l1.getSelectedRanges().toArray());
		
		// negative index
		setupImpl();
		try
		{
			m_model.adjustForItemsRemoved(-1, 1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			// EXPECTED
		}
		
		// 0 length
		setupImpl();
		try
		{
			m_model.adjustForItemsRemoved(0, 0);
			fail();
		}
		catch (IllegalArgumentException e)
		{
			// EXPECTED
		}
		
		// anchor & lead indices updated
		setupImpl();
		m_model.setSelectionRange(1, 1);
		m_model.adjustForItemsRemoved(0, 1);
		assertEquals(0, m_model.getAnchorIndex());
		assertEquals(0, m_model.getLeadIndex());
		m_model.adjustForItemsRemoved(1, 1);
		assertEquals(0, m_model.getAnchorIndex());
		assertEquals(0, m_model.getLeadIndex());
		m_model.adjustForItemsRemoved(0, 1);
		assertEquals(-1, m_model.getAnchorIndex());
		assertEquals(-1, m_model.getLeadIndex());
	}
}
