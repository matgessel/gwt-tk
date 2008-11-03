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

import asquare.gwt.tk.client.util.GwtUtil;

public abstract class ModelChangeSupportBase extends ChangeSupportBase
{
	private boolean m_changed = false;
	
	public ModelChangeSupportBase(Object source)
	{
		super(source);
	}
	
	public void addListener(EventListener listener)
	{
		addListenerImpl(listener);
	}
	
	public void removeListener(EventListener listener)
	{
		removeListenerImpl(listener);
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
	
	public boolean setChanged(int oldValue, int newValue)
	{
		if (oldValue != newValue)
		{
			setChanged();
			return true;
		}
		return false;
	}
	
	public boolean setChanged(float oldValue, float newValue)
	{
		if (oldValue != newValue)
		{
			setChanged();
			return true;
		}
		return false;
	}
	
	public boolean setChanged(boolean oldValue, boolean newValue)
	{
		if (oldValue != newValue)
		{
			setChanged();
			return true;
		}
		return false;
	}
	
	public boolean setChanged(Object oldValue, Object newValue)
	{
		if (! GwtUtil.equals(oldValue, newValue))
		{
			setChanged();
			return true;
		}
		return false;
	}
	
	public void resetChanges()
	{
		m_changed = false;
	}
	
	public void update()
	{
		if (isChanged())
		{
			if (hasListeners())
			{
				notifyListeners(getListeners());
			}
			resetChanges();
		}
	}
	
	protected abstract void notifyListeners(EventListener[] listeners);
}
