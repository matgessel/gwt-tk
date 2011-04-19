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

import asquare.gwt.sb.client.util.Properties;

/**
 * A model of properties. Properties do not take up memory until set. 
 */
public class PropertyModelLazy implements ExplicitUpdateModel, SourcesModelChangeEventComplex
{
	private final ModelChangeSupportComplex m_changeSupport;
	private final Properties m_impl = new Properties();
	
	public PropertyModelLazy()
	{
		this(null);
	}
	
	public PropertyModelLazy(ModelChangeSupportComplex changeSupport)
	{
		m_changeSupport = (changeSupport != null) ? changeSupport : createChangeSupport();
	}
	
	protected ModelChangeSupportComplex createChangeSupport()
	{
		return new ModelChangeSupportComplex(this);
	}
	
	public void addListener(ModelListenerComplex listener)
	{
		m_changeSupport.addListener(listener);
	}
	
	public void removeListener(ModelListenerComplex listener)
	{
		m_changeSupport.removeListener(listener);
	}
	
	public boolean getBoolean(String name)
	{
		return m_impl.getBoolean(name);
	}
	
	public int getInt(String name)
	{
		return m_impl.getInt(name);
	}
	
	public String getString(String name)
	{
		return m_impl.getString(name);
	}
	
	public Object getObject(String name)
	{
		return m_impl.get(name);
	}
	
	public boolean set(String name, boolean value)
	{
		if (m_changeSupport.propertyChanged(name, m_impl.getBoolean(name), value))
		{
			m_impl.set(name, value);
			return true;
		}
		return false;
	}
	
	public boolean set(String name, int value)
	{
		if (m_changeSupport.propertyChanged(name, m_impl.getInt(name), value))
		{
			m_impl.set(name, value);
			return true;
		}
		return false;
	}
	
	public boolean set(String name, String value)
	{
		if (m_changeSupport.propertyChanged(name, m_impl.getString(name), value))
		{
			m_impl.set(name, value);
			return true;
		}
		return false;
	}
	
	public boolean set(String name, Object value)
	{
		if (m_changeSupport.propertyChanged(name, m_impl.get(name), value))
		{
			m_impl.set(name, value);
			return true;
		}
		return false;
	}
	
	public void resetChanges()
	{
		m_changeSupport.resetChanges();
	}
	
	public void update()
	{
		m_changeSupport.update();
	}
	
    @Override
	public String toString()
	{
		return m_impl.toString();
	}
}
