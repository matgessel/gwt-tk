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

public class ListModelDefaultTC extends GWTTestCase
{
	private ListModelDefault m_model;
	private Object m_a1;
	private Object m_b2;
	private Object m_c1;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_model = new ListModelDefault(new ListSelectionModelSingle());
		m_a1 = new Integer(1);
		m_b2 = new Integer(2);
		m_c1 = new Integer(1);
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
}
