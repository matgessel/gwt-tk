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

import java.util.EventListener;

import asquare.gwt.sb.client.fw.ModelChangeSupportLight;
import asquare.gwt.tk.client.util.GwtUtil;

public abstract class ActionBase extends UICommandBase implements Action
{
	private ChangeSupport m_changeSupport;
	private boolean m_enabled = true;
	
	public ActionBase()
	{
		this(null);
	}
	
	public ActionBase(String uiString)
	{
		super(uiString);
	}
	
	public void addListener(ActionPropertyListener listener)
	{
		if (m_changeSupport == null)
		{
			m_changeSupport = new ChangeSupport();
		}
		m_changeSupport.addListener(listener);
	}
	
	public void removeListener(ActionPropertyListener listener)
	{
		if (m_changeSupport != null)
		{
			m_changeSupport.removeListener(listener);
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
			if (m_changeSupport != null)
			{
				m_changeSupport.setChanged();
				m_changeSupport.update();
			}
		}
	}
	
	public void setUiString(String uiString)
	{
		if (! GwtUtil.equals(getUIString(), uiString))
        {
            super.setUiString(uiString);
            if (m_changeSupport != null)
            {
                m_changeSupport.setChanged();
                m_changeSupport.update();
            }
        }
	}
	
	private class ChangeSupport extends ModelChangeSupportLight
	{
		public ChangeSupport()
		{
			super(ActionBase.this);
		}
		
		protected void notifyListener(Object source, EventListener listener)
		{
			((ActionPropertyListener) listener).actionPropertiesChanged((ActionBase) source);
		}
	}
}
