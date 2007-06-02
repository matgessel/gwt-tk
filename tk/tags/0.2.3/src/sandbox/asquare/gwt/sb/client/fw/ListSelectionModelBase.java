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

import java.util.Vector;

public abstract class ListSelectionModelBase implements ListSelectionModel
{
	private Vector m_listeners = null;
	
	/**
	 * Tracks whether listeners are being notified. The notification method does
	 * not create a copy of the listener list, so adding/removing a listener
	 * during notification would have unpredictable results. 
	 */
	private boolean m_firingInProgress = false;
	
	/**
	 * @throws IllegalStateException if a listener is added while a notification is underway
	 */
	public void addListener(ListSelectionModelListener listener)
	{
		if (m_firingInProgress)
			throw new IllegalStateException();
		
		if (m_listeners == null)
		{
			m_listeners = new Vector();
		}
		m_listeners.add(listener);
	}
	
	/**
	 * @throws IllegalStateException if a listener is removed while a notification is underway
	 */
	public void removeListener(ListSelectionModelListener listener)
	{
		if (m_firingInProgress)
			throw new IllegalStateException();
		
		if (m_listeners != null)
		{
			m_listeners.remove(listener);
		}
	}
	
	protected void fireSelectionChange(int index)
	{
		if (m_listeners != null)
		{
			m_firingInProgress = true;
			try
			{
				for (int i = 0, size = m_listeners.size(); i < size; i++)
				{
					((ListSelectionModelListener) m_listeners.get(i)).listSelectionModelChanged(index);
				}
			}
			finally
			{
				m_firingInProgress = false;
			}
		}
	}
}
