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

import com.google.gwt.junit.client.GWTTestCase;

public class ListUpdateControllerTC extends GWTTestCase
{
	private ListModelDefault m_model;
	private ListViewStub m_view;
	private ListUpdateController m_controller;
	private Object m_0;
	private Object m_1;
	private Object m_2;
	private Object m_3;
	private Object m_4;
	private Object[] m_array;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_model = new ListModelDefault(new ListSelectionModelArbitrary());
		m_view = new ListViewStub();
		if (m_controller != null)
		{
			m_controller.dispose();
		}
		m_controller = new ListUpdateController(m_model, m_view);
		m_controller.init();
		m_0 = "0";
		m_1 = "1";
		m_2 = "2";
		m_3 = "3";
		m_4 = "4";
		m_array = new Object[] {m_0, m_1, m_2, m_3, m_4};
		m_model.addAll(m_array);
		m_model.update();
	}
	
	public void testRemove()
	{
		// remove lone selected item
		setupImpl();
		m_model.getSelectionModel().setSelectionRange(1, 1);
		m_model.update();
		assertEquals(Boolean.TRUE, m_view.getProperty(1, ListCellRenderer.PROPERTY_SELECTED));
		m_model.remove(1);
		m_model.update();
		assertEquals(m_2, m_view.getModelElement(1));
		assertEquals(Boolean.FALSE, m_view.getProperty(0, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.FALSE, m_view.getProperty(1, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.FALSE, m_view.getProperty(2, ListCellRenderer.PROPERTY_SELECTED));
		
		// remove surrounded selected item
		setupImpl();
		m_model.getSelectionModel().setSelectionRange(1, 3);
		m_model.update();
		assertEquals(Boolean.FALSE, m_view.getProperty(0, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.TRUE, m_view.getProperty(1, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.TRUE, m_view.getProperty(2, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.TRUE, m_view.getProperty(3, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.FALSE, m_view.getProperty(4, ListCellRenderer.PROPERTY_SELECTED));
		m_model.remove(2);
		m_model.update();
		assertEquals(m_1, m_view.getModelElement(1));
		assertEquals(m_3, m_view.getModelElement(2));
		assertEquals(Boolean.FALSE, m_view.getProperty(0, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.TRUE, m_view.getProperty(1, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.TRUE, m_view.getProperty(2, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.FALSE, m_view.getProperty(3, ListCellRenderer.PROPERTY_SELECTED));
		
		// remove item w/ last item selected
		setupImpl();
		m_model.getSelectionModel().setSelectionRange(4, 4);
		m_model.update();
		assertEquals(Boolean.TRUE, m_view.getProperty(4, ListCellRenderer.PROPERTY_SELECTED));
		m_model.remove(3);
		m_model.update();
		assertEquals(m_4, m_view.getModelElement(3));
		assertEquals(4, m_view.getSize());
		assertEquals(Boolean.FALSE, m_view.getProperty(0, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.FALSE, m_view.getProperty(1, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.FALSE, m_view.getProperty(2, ListCellRenderer.PROPERTY_SELECTED));
		assertEquals(Boolean.TRUE, m_view.getProperty(3, ListCellRenderer.PROPERTY_SELECTED));
	}
}
