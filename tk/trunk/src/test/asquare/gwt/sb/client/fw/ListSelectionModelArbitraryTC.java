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

import asquare.gwt.tk.testutil.TkTestUtil;

import com.google.gwt.junit.client.GWTTestCase;

public class ListSelectionModelArbitraryTC extends GWTTestCase
{
	private ListSelectionModelArbitrary m_model;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_model = new ListSelectionModelArbitrary();
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
		TkTestUtil.assertSameValues(new int[] {3}, m_model.getSelectedIndices());
		m_model.addSelectionRange(0, 0);
		TkTestUtil.assertSameValues(new int[] {0, 3}, m_model.getSelectedIndices());
		m_model.addSelectionRange(5, 5);
		TkTestUtil.assertSameValues(new int[] {0, 3, 5}, m_model.getSelectedIndices());
		
		// range > 1
		setupImpl();
		m_model.addSelectionRange(1, 2);
		TkTestUtil.assertSameValues(new int[] {1, 2}, m_model.getSelectedIndices());
		m_model.addSelectionRange(0, 3);
		TkTestUtil.assertSameValues(new int[] {0, 1, 2, 3}, m_model.getSelectedIndices());
		
		// 2nd index < first index
		setupImpl();
		m_model.addSelectionRange(2, 1);
		TkTestUtil.assertSameValues(new int[] {1, 2}, m_model.getSelectedIndices());
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
		TkTestUtil.assertSameValues(new int[] {0}, m_model.getSelectedIndices());
		m_model.setSelectionRange(1, 1);
		TkTestUtil.assertSameValues(new int[] {1}, m_model.getSelectedIndices());
		
		// range > 1
		setupImpl();
		m_model.setSelectionRange(1, 2);
		TkTestUtil.assertSameValues(new int[] {1, 2}, m_model.getSelectedIndices());
		
		// 2nd index < first index
		setupImpl();
		m_model.setSelectionRange(2, 1);
		TkTestUtil.assertSameValues(new int[] {1, 2}, m_model.getSelectedIndices());
	}
}
