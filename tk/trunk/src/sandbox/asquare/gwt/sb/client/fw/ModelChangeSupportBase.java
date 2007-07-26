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
import java.util.Vector;

import asquare.gwt.tk.client.util.GwtUtil;

public abstract class ModelChangeSupportBase
{
	private Vector m_listeners = null;
	private boolean m_changed = false;
	
	public void addListener(EventListener listener)
	{
		if (listener == null)
			throw new NullPointerException();
		
		if (m_listeners == null)
		{
			m_listeners = new Vector();
		}
		m_listeners.add(listener);
	}
	
	public void removeListener(EventListener listener)
	{
		if (listener == null)
			throw new NullPointerException();
		
		if (m_listeners != null)
		{
			m_listeners.remove(listener);
		}
	}
	
	/**
	 * If you override this you must also override {@link #resetChanges()}
	 */
	public boolean isChanged()
	{
		return m_changed;
	}
	
	public void setChanged()
	{
		m_changed = true;
	}
	
	protected void resetChanges()
	{
		m_changed = false;
	}
	
	public void update()
	{
		if (isChanged())
		{
			if (m_listeners != null && m_listeners.size() > 0)
			{
				EventListener[] listeners = new EventListener[m_listeners.size()];
				GwtUtil.toArray(m_listeners, listeners);
				notifyListeners(listeners);
			}
			resetChanges();
		}
	}
	
	protected abstract void notifyListeners(EventListener[] listeners);
}
