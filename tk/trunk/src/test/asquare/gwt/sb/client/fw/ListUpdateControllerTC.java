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
	private ListView m_view;
	private ListUpdateController m_controller;
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
		m_view = new ListViewBasic();
		m_controller = new ListUpdateController(m_model, m_view);
		m_a1 = new Integer(1);
		m_b2 = new Integer(2);
		m_c1 = new Integer(1);
		m_array = new Object[] {m_a1, m_b2, m_c1};
	}
	
	public void testRemove()
	{
		// selection
		setupImpl();
		m_model.addAll(m_array);
		m_model.getSelectionModel().setSelectionRange(0, 0);
		m_model.update();
		assertTrue(m_model.isIndexSelected(0));
		m_model.remove(0);
		m_model.update();
		
		m_model.getSelectionModel().setSelectionRange(1, 1);
		m_model.update();
		assertTrue(m_model.isIndexSelected(1));
		m_model.remove(1);
		m_model.update();
	}
}
