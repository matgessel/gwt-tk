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

import asquare.gwt.sb.client.fw.ModelChangeEventComplex.ChangeBase;
import asquare.gwt.sb.client.fw.ModelChangeEventComplex.PropertyChangeBase;
import asquare.gwt.sb.client.util.TypedList;

public class ModelListenerComplexStub extends ModelListenerStub implements ModelListenerComplex
{
    private TypedList m_changes;
	
	public ModelListenerComplexStub()
	{
        m_changes = new TypedList();
	}
	
	protected TypedList getChangeList()
	{
		return m_changes;
	}
	
	public void init()
	{
		super.init();
		m_changes.clear();
	}
	
	public void modelChanged(ModelChangeEventComplex event)
	{
		modelChanged((ModelChangeEvent) event);
        for (int i = 0, size = event.getChangeCount(); i < size; i++)
        {
            ChangeBase change = event.getChangeAt(i);
            m_changes.add(change.getType(), change);
        }
	}
	
    public int getChangeCount()
    {
        return m_changes.getSize();
    }
    
    public int getChangeCount(Class type)
    {
        return m_changes.getValueCountFor(type);
    }
    
    public boolean hasPropertyChange(String name)
    {
        int searchIndex = m_changes.getIndexOfType(PropertyChangeBase.class);
		while (searchIndex != -1)
		{
            if (((PropertyChangeBase) m_changes.getValue(searchIndex)).getName().equals(name))
            {
            	return true;
            }
            searchIndex = m_changes.getIndexOfType(PropertyChangeBase.class, searchIndex + 1);
		}
    	return false;
    }
    
    public ChangeBase getChange(Class type)
    {
        return (ChangeBase) m_changes.getValue(type);
    }
    
    public ChangeBase[] getChanges()
    {
    	ChangeBase[] result = new ChangeBase[m_changes.getSize()];
        for (int i = 0, size = m_changes.getSize(); i < size; i++)
        {
            result[i] = (ChangeBase) m_changes.getValue(i);
        }
        return result;
    }
}
