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

import java.util.EventListener;

import asquare.gwt.sb.client.util.Properties;

public class PropertyModelLazy implements ExplicitUpdateModel
{
	private final ModelChangeSupportBase m_changeSupport = new ChangeSupport();
	private final Properties m_impl = new Properties();
	
	public void addListener(PropertyModelListener listener)
	{
		m_changeSupport.addListener(listener);
	}
	
	public void removeListener(PropertyModelListener listener)
	{
		m_changeSupport.removeListener(listener);
	}
	
	public void clear()
	{
		m_impl.clear();
		m_changeSupport.setChanged();
	}
	
	public Object get(String name)
	{
		return m_impl.get(name);
	}
	
	public boolean getBoolean(String name)
	{
		return m_impl.getBoolean(name);
	}
	
	public String getString(String name)
	{
		return m_impl.getString(name);
	}
	
	public void set(String name, boolean value)
	{
		if (m_impl.getBoolean(name) != value)
		{
			m_impl.set(name, value);
			m_changeSupport.setChanged();
		}
	}
	
	public void set(String name, Object value)
	{
		Object old = m_impl.get(name);
		if (old == value || old != null && old.equals(value))
			return;
		
		m_impl.set(name, value);
		m_changeSupport.setChanged();
	}
	
	public void update()
	{
		m_changeSupport.update();
	}
	
	private class ChangeSupport extends ModelChangeSupportLight
	{
		protected void notifyListener(EventListener listener)
		{
			((PropertyModelListener) listener).modelChanged(PropertyModelLazy.this);
		}
	}
}
