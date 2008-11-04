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

import java.util.ArrayList;
import java.util.EventListener;

import asquare.gwt.tk.client.util.GwtUtil;

public abstract class ChangeSupportBase
{
	private final Object m_source;
	
	private ArrayList<EventListener> m_listeners = null;
	
	public ChangeSupportBase(Object source)
	{
		m_source = source;
	}
	
	protected void addListenerImpl(EventListener listener)
	{
		if (listener == null)
			throw new NullPointerException();
		
		if (m_listeners == null)
		{
			m_listeners = new ArrayList<EventListener>();
		}
		m_listeners.add(listener);
	}
	
	protected void removeListenerImpl(EventListener listener)
	{
		if (listener == null)
			throw new NullPointerException();
		
		if (m_listeners != null)
		{
			m_listeners.remove(listener);
		}
	}
	
	public Object getSource()
	{
		return m_source;
	}
	
	protected boolean hasListeners()
	{
		return m_listeners != null && m_listeners.size() > 0;
	}
	
	protected EventListener[] getListeners()
	{
		EventListener[] result = new EventListener[m_listeners.size()];
		GwtUtil.toArray(m_listeners, result);
		return result;
	}
}
