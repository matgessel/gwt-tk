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
package asquare.gwt.sb.client.util;

import java.util.Vector;

public abstract class ActionBase implements Action
{
	private Vector m_listeners;
	private String m_uiString;
	private boolean m_enabled = true;
	
	public ActionBase()
	{
	}
	
	public ActionBase(String uiString)
	{
		m_uiString = uiString;
	}
	
	public void addListener(ActionPropertyListener listener)
	{
		if (m_listeners == null)
		{
			m_listeners = new Vector();
		}
		m_listeners.add(listener);
	}
	
	public void removeListener(ActionPropertyListener listener)
	{
		if (m_listeners != null)
		{
			m_listeners.remove(listener);
		}
	}
	
	public boolean isEnabled()
	{
		return m_enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		if (m_enabled != enabled)
		{
			m_enabled = enabled;
			fireEnabledChanged();
		}
	}
	
	public String getUIString()
	{
		return m_uiString;
	}
	
	public void setUiString(String uiString)
	{
		m_uiString = uiString;
	}
	
	public String toString()
	{
		return getUIString();
	}
	
	private void fireEnabledChanged()
	{
		if (m_listeners == null || m_listeners.size() == 0)
			return;
		
		Object[] listeners = m_listeners.toArray();
		for (int i = 0; i < listeners.length; i++)
		{
			((ActionPropertyListener) listeners[i]).actionPropertiesChanged(this);
		}
	}
}
