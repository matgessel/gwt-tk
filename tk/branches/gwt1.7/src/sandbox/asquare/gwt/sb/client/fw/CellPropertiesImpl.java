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

import asquare.gwt.sb.client.util.Properties;

public class CellPropertiesImpl implements CellProperties
{
	private final Properties m_impl = new Properties();
	
	public final boolean getBoolean(String name)
	{
		return m_impl.getBoolean(name);
	}
	
	protected final void setBoolean(String name, boolean value)
	{
		m_impl.set(name, value);
	}
	
	public final int getInt(String name)
	{
		return m_impl.getInt(name);
	}
	
	protected final void setInt(String name, int value)
	{
		m_impl.set(name, value);
	}
	
	public String getString(String name)
	{
		return m_impl.getString(name);
	}
	
	protected final void setString(String name, String value)
	{
		m_impl.set(name, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String name)
	{
		return (T) m_impl.get(name);
	}
	
	protected final void setObject(String name, Object value)
	{
		m_impl.set(name, value);
	}
	
	public boolean isSelected()
	{
		return m_impl.getBoolean(SELECTED);
	}
	
	public void setSelected(boolean selected)
	{
		m_impl.set(SELECTED, selected);
	}
	
	public boolean isHover()
	{
		return m_impl.getBoolean(HOVER);
	}
	
	public void setHover(boolean hover)
	{
		m_impl.set(HOVER, hover);
	}
	
	public boolean isActive()
	{
		return m_impl.getBoolean(ACTIVE);
	}
	
	public void setActive(boolean active)
	{
		m_impl.set(ACTIVE, active);
	}
	
	public boolean isDisabled()
	{
		return m_impl.getBoolean(DISABLED);
	}
	
	public void setDisabled(boolean disabled)
	{
		m_impl.set(DISABLED, disabled);
	}
	
	@Override
	public String toString()
	{
		return m_impl.toString();
	}
}
