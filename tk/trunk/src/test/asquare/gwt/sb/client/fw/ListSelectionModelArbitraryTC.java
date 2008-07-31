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

public class ListSelectionModelArbitraryTC extends TestCase
{
	private ListSelectionModelArbitrary m_model;
	private ListSelectionModelListenerStub m_l1;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_model = new ListSelectionModelArbitrary();
		m_l1 = new ListSelectionModelListenerStub();
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
		m_model.addSelectionRange(3, 3);
		TkTestUtil.assertEqualValues(new int[] {3}, m_model.getSelectedIndices());
		m_model.addSelectionRange(0, 0);
		TkTestUtil.assertEqualValues(new int[] {0, 3}, m_model.getSelectedIndices());
		m_model.addSelectionRange(5, 5);
		TkTestUtil.assertEqualValues(new int[] {0, 3, 5}, m_model.getSelectedIndices());
		
		// range > 1
		setupImpl();
		m_model.addSelectionRange(1, 2);
		TkTestUtil.assertEqualValues(new int[] {1, 2}, m_model.getSelectedIndices());
		m_model.addSelectionRange(0, 3);
		TkTestUtil.assertEqualValues(new int[] {0, 1, 2, 3}, m_model.getSelectedIndices());
		
		// 2nd index < first index
		setupImpl();
		m_model.addSelectionRange(2, 1);
		TkTestUtil.assertEqualValues(new int[] {1, 2}, m_model.getSelectedIndices());

		// update
		setupImpl();
		m_model.addListener(m_l1);
		m_model.addSelectionRange(0, 0);
		assertTrue(m_l1.isChanged());
		m_l1.init();
		m_model.addSelectionRange(0, 0);
		assertFalse(m_l1.isChanged());
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
		m_model.setSelectionRange(0, 0);
		m_model.removeSelectionRange(0, 0);
		assertEquals(0, m_model.getSelectionSize());
		m_model.setSelectionRange(1, 1);
		m_model.removeSelectionRange(1, 1);
		assertEquals(0, m_model.getSelectionSize());
		
		// range > 1
		setupImpl();
		m_model.setSelectionRange(3, 3);
		m_model.removeSelectionRange(0, 5);
		assertEquals(0, m_model.getSelectionSize());
		
		// 2nd index < first index
		setupImpl();
		m_model.setSelectionRange(3, 3);
		m_model.removeSelectionRange(5, 0);
		assertEquals(0, m_model.getSelectionSize());
		
		// miss by 1
		setupImpl();
		m_model.setSelectionRange(3, 3);
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
		TkTestUtil.assertEqualValues(new int[] {0}, m_model.getSelectedIndices());
		m_model.setSelectionRange(1, 1);
		TkTestUtil.assertEqualValues(new int[] {1}, m_model.getSelectedIndices());
		
		// range > 1
		setupImpl();
		m_model.setSelectionRange(1, 2);
		TkTestUtil.assertEqualValues(new int[] {1, 2}, m_model.getSelectedIndices());
		
		// 2nd index < first index
		setupImpl();
		m_model.setSelectionRange(2, 1);
		TkTestUtil.assertEqualValues(new int[] {1, 2}, m_model.getSelectedIndices());
		
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
	}
	
//	public void testSetIndexSelected()
//	{
//		setupImpl();
//		m_model.setIndexSelected(1, true);
//		assertFalse(m_model.isIndexSelected(0));
//		assertTrue(m_model.isIndexSelected(1));
//		m_model.setIndexSelected(1, false);
//		assertFalse(m_model.isIndexSelected(1));
//		m_model.setIndexSelected(2, true);
//		assertTrue(m_model.isIndexSelected(2));
//	}
	
	public void testClear()
	{
		setupImpl();
		
		// update
		setupImpl();
		m_model.addSelectionRange(0, 0);
		m_model.addListener(m_l1);
		m_model.clearSelection();
		assertTrue(m_l1.isChanged());
	}
	
	public void testAdjustForItemsInserted()
	{
		// insert before selected index
		setupImpl();
		m_model.setSelectionRange(1, 1);
		m_model.addSelectionRange(3, 4);
		TkTestUtil.assertEqualValues(new int[] {1, 3, 4}, m_model.getSelectedIndices());
		m_model.adjustForItemsInserted(0, 1);
		TkTestUtil.assertEqualValues(new int[] {2, 4, 5}, m_model.getSelectedIndices());
		
		// insert at selected index
		m_model.adjustForItemsInserted(2, 2);
		TkTestUtil.assertEqualValues(new int[] {4, 6, 7}, m_model.getSelectedIndices());
		
		// insert after selected index
		m_model.adjustForItemsInserted(8, 1);
		TkTestUtil.assertEqualValues(new int[] {4, 6, 7}, m_model.getSelectedIndices());
		
		// insert between selected values
		m_model.adjustForItemsInserted(7, 1);
		TkTestUtil.assertEqualValues(new int[] {4, 6, 8}, m_model.getSelectedIndices());
		
		// insert multiple values
		setupImpl();
		m_model.setSelectionRange(1, 2);
		m_model.adjustForItemsInserted(2, 4);
		TkTestUtil.assertEqualValues(new int[] {1, 6}, m_model.getSelectedIndices());
		
		// empty selection
		setupImpl();
		assertEquals(0, m_model.getSelectionSize());
		m_model.adjustForItemsInserted(2, 1);
		m_model.adjustForItemsInserted(1, 2);
		m_model.adjustForItemsInserted(0, 1);
		assertEquals(0, m_model.getSelectionSize());
		
		// update w/ empty model
		setupImpl();
		m_model.addListener(m_l1);
		m_model.adjustForItemsInserted(0, 1);
		assertFalse(m_l1.isChanged());
		
		// update not affecting selection
		setupImpl();
		m_model.addListener(m_l1);
		m_model.setSelectionRange(0, 2);
		m_l1.init();
		m_model.adjustForItemsInserted(3, 1);
		assertFalse(m_l1.isChanged());
		
		// update changes selection
		setupImpl();
		m_model.addListener(m_l1);
		m_model.setSelectionRange(0, 2);
		m_l1.init();
		m_model.adjustForItemsInserted(1, 2);
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(1, 2)}, m_l1.getDeselectedRanges().toArray());
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(3, 2)}, m_l1.getSelectedRanges().toArray());
		
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
	}
	
	public void testAdjustForItemsRemoved()
	{
		// remove before selected index
		setupImpl();
		m_model.setSelectionRange(3, 3);
		m_model.addSelectionRange(6, 7);
		TkTestUtil.assertEqualValues(new int[] {3, 6, 7}, m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(0, 1);
		TkTestUtil.assertEqualValues(new int[] {2, 5, 6}, m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(0, 1);
		TkTestUtil.assertEqualValues(new int[] {1, 4, 5}, m_model.getSelectedIndices());
		
		// remove after selected index
		setupImpl();
		m_model.setSelectionRange(1, 1);
		m_model.addSelectionRange(2, 3);
		m_model.adjustForItemsRemoved(4, 1);
		TkTestUtil.assertEqualValues(new int[] {1, 2, 3}, m_model.getSelectedIndices());
		
		// remove between selected indices
		setupImpl();
		m_model.setSelectionRange(0, 1);
		m_model.addSelectionRange(2, 1);
		m_model.adjustForItemsRemoved(1, 1);
		TkTestUtil.assertEqualValues(new int[] {0, 1}, m_model.getSelectedIndices());
		
		// remove selected indices
		setupImpl();
		m_model.setSelectionRange(0, 3);
		m_model.adjustForItemsRemoved(1, 2);
		TkTestUtil.assertEqualValues(new int[] {0, 1}, m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(0, 2);
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		// empty selection
		setupImpl();
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		m_model.adjustForItemsRemoved(2, 1);
		m_model.adjustForItemsRemoved(1, 2);
		m_model.adjustForItemsRemoved(0, 1);
		TkTestUtil.assertEqualValues(new int[0], m_model.getSelectedIndices());
		
		// update w/ empty model
		setupImpl();
		m_model.addListener(m_l1);
		m_model.adjustForItemsRemoved(0, 1);
		assertFalse(m_l1.isChanged());
		
		// update not affecting selection
		setupImpl();
		m_model.setSelectionRange(0, 2);
		m_model.addListener(m_l1);
		m_model.adjustForItemsRemoved(3, 1);
		assertFalse(m_l1.isChanged());
		
		// update changes selection
		setupImpl();
		m_model.setSelectionRange(0, 0);
		m_model.addSelectionRange(2, 2);
		m_model.addSelectionRange(4, 4);
		m_model.addListener(m_l1);
		m_model.adjustForItemsRemoved(2, 2);
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(4, 1)}, m_l1.getDeselectedRanges().toArray());
		TkTestUtil.assertEqualValues(new IntRange[0], m_l1.getSelectedRanges().toArray());
		m_l1.init();
		m_model.adjustForItemsRemoved(1, 1);
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(2, 1)}, m_l1.getDeselectedRanges().toArray());
		TkTestUtil.assertEqualValues(new IntRange[] {new IntRange(1, 1)}, m_l1.getSelectedRanges().toArray());
		
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
	}
}
