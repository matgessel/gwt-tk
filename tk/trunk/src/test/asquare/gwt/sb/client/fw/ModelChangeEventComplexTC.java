/*
 * Copyright 2008 Mat Gessel <mat.gessel@gmail.com>
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
import asquare.gwt.sb.client.fw.ModelChangeEventComplex.ChangeBase;

public class ModelChangeEventComplexTC extends TestCase
{
	private ModelChangeEventComplex m_event;
	
	public String getModuleName()
	{
		return "asquare.gwt.sb.SbTC";
	}
	
	protected void setupImpl()
	{
		m_event = new ModelChangeEventComplex(new SourcesModelChangeEventComplex() {});
	}
	
	public void testAddChange()
	{
		setupImpl();
		m_event.addChange(new DontEat());
        assertEquals(1, m_event.getChangeCount());
		m_event.addChange(new DontEat());
        assertEquals(2, m_event.getChangeCount());
		m_event.addChange(new Eat());
        assertEquals(1, m_event.getChangeCount());
		m_event.addChange(new DontEat());
        assertEquals(1, m_event.getChangeCount());
		m_event.addChange(new Eat());
        assertEquals(1, m_event.getChangeCount());
	}
	
	public void testGetPropertyChange()
	{
		// basic test
		setupImpl();
		m_event.addPropertyChange("name", true, false);
		assertTrue(m_event.hasPropertyChange("name"));
		assertEquals("name", m_event.getPropertyChange("name").getName());
		assertEquals(true, m_event.getPropertyChangeBoolean("name").getOldValue());
		assertEquals(false, m_event.getPropertyChangeBoolean("name").getNewValue());
		
		// miss property
		setupImpl();
		assertNull(m_event.getPropertyChange("name"));
		m_event.addPropertyChange("name", true, false);
		assertNull(m_event.getPropertyChange("date"));
	}
    
	public static class Eat extends ModelChangeEventComplex.ChangeBase
    {
	    private static final long serialVersionUID = 1L;

        public Eat()
        {
            super(Eat.class);
        }
        
	    @Override
        public boolean addChange(ChangeBase change)
        {
            return true;
        }
    }

	public static class DontEat extends ModelChangeEventComplex.ChangeBase
	{
		private static final long serialVersionUID = 1L;
		
		public DontEat()
		{
			super(DontEat.class);
		}
		
	    @Override
		public boolean addChange(ChangeBase change)
		{
			return false;
		}
	}
}
