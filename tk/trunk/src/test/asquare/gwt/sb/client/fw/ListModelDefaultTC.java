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

public class ListModelDefaultTC extends TestCase
{
	private ListModelDefault m_model;
	private ListModelListenerStub m_l1;
	private Object m_a1;
	private Object m_b2;
	private Object m_c1;
	private Object[] m_array;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_model = new ListModelDefault(new ListSelectionModelSingle());
		m_l1 = new ListModelListenerStub();
		m_a1 = new Integer(1);
		m_b2 = new Integer(2);
		m_c1 = new Integer(1);
		m_array = new Object[] {m_a1, m_b2, m_c1};
	}
	
	public void testSet()
	{
		setupImpl();
		
		// null, null
		assertFalse(m_model.isChanged());
		m_model.add(null);
		m_model.update();
		m_model.set(0, null);
		assertFalse(m_model.isChanged());
		
		// null, a1
		assertFalse(m_model.isChanged());
		m_model.set(0, m_a1);
		assertTrue(m_model.isChanged());
		m_model.update();
		
		// a1, null
		assertFalse(m_model.isChanged());
		m_model.set(0, null);
		assertTrue(m_model.isChanged());
		m_model.update();
		
		// a1, a1
		assertFalse(m_model.isChanged());
		m_model.set(0, m_a1);
		m_model.update();
		m_model.set(0, m_a1);
		assertFalse(m_model.isChanged());
		
		// a1, b2
		assertFalse(m_model.isChanged());
		m_model.set(0, m_b2);
		assertTrue(m_model.isChanged());
		m_model.update();
		
		// a1, c1
		assertFalse(m_model.isChanged());
		m_model.set(0, m_a1);
		m_model.update();
		m_model.set(0, m_c1);
		assertFalse(m_model.isChanged());
		m_model.update();
	}
	
	public void testInsert()
	{
		// update selection
		setupImpl();
		m_model.add(m_b2);
		m_model.getSelectionModel().setSelectionRange(0, 0);
		m_model.update();
		assertTrue(m_model.isIndexSelected(0));
		m_model.addListener(m_l1);
		m_model.insert(0, m_a1);
		m_model.update();
		assertEquals(1, m_model.getSelectionModel().getSelectionSize());
		assertEquals(1, m_model.getSelectionModel().getSelectedIndices()[0]);
		assertEquals(1, m_l1.getItemPropertyChangeCount(ListModel.ITEM_PROPERTY_SELECTION));
	}
	
	public void testClear()
	{
		// reset selection
		setupImpl();
		m_model.add(m_a1);
		m_model.getSelectionModel().setSelectionRange(0, 0);
		m_model.update();
		assertTrue(m_model.isIndexSelected(0));
		m_model.addListener(m_l1);
		m_model.clear();
		m_model.update();
		assertEquals(0, m_model.getSelectionModel().getSelectionSize());
		assertEquals(1, m_l1.getItemPropertyChangeCount(ListModel.ITEM_PROPERTY_SELECTION));
	}
	
	public void testRemove()
	{
		// update/reset selection
		setupImpl();
		m_model.addAll(m_array);
		m_model.getSelectionModel().setSelectionRange(2, 2);
		m_model.update();
		assertTrue(m_model.isIndexSelected(2));
		m_model.remove(0);
		m_model.update();
		assertEquals(1, m_model.getSelectionModel().getSelectionSize());
		assertEquals(1, m_model.getSelectionModel().getSelectedIndices()[0]);
		m_model.addListener(m_l1);
		m_model.remove(1);
		m_model.update();
		assertEquals(0, m_model.getSelectionModel().getSelectionSize());
		assertEquals(1, m_l1.getItemPropertyChangeCount(ListModel.ITEM_PROPERTY_SELECTION));
		
		// null selection model
		ListModelDefault model = new ListModelDefault(null);
		model.add(m_a1);
		model.remove(0);
		assertFalse(model.isIndexSelected(0));
	}
	
	public void testSelection()
	{
		setupImpl();
		m_model.getSelectionModel().setSelectionRange(0, 2);
		m_model.resetChanges();
		m_model.addListener(m_l1);
		m_model.getSelectionModel().setSelectionRange(2, 3);
		m_model.update();
		assertEquals(1, m_l1.getItemPropertyChangeCount(ListModel.ITEM_PROPERTY_SELECTION));
		m_l1.init();
		m_model.getSelectionModel().setSelectionRange(0, 1);
		m_model.update();
		assertEquals(2, m_l1.getItemPropertyChangeCount(ListModel.ITEM_PROPERTY_SELECTION));
	}
}
