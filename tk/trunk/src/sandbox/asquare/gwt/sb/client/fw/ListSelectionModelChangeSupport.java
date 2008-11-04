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

import java.util.ArrayList;

import asquare.gwt.sb.client.util.IntRange;
import asquare.gwt.sb.client.util.Range;
import asquare.gwt.sb.client.util.RangeCollection;

public class ListSelectionModelChangeSupport extends ModelChangeSupportComplex
{
	private ArrayList<ListSelectionModelListener> m_listeners = null;
	
	private ListSelectionModelEvent m_event = null;
	
	/**
	 * Tracks whether listeners are being notified. The notification method does
	 * not create a copy of the listener list, so adding/removing a listener
	 * during notification would result in unpredictable behavior. 
	 */
	private boolean m_firingInProgress = false;
	
	public ListSelectionModelChangeSupport(ListSelectionModel source)
	{
		super(source);
	}
	
	/**
	 * @throws IllegalStateException if a listener is added while a notification is underway
	 */
	public void addListener(ListSelectionModelListener listener)
	{
		if (m_firingInProgress)
			throw new IllegalStateException();
		
		if (m_listeners == null)
		{
			m_listeners = new ArrayList<ListSelectionModelListener>();
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
	
	private ListSelectionModelEvent getEvent0()
	{
		if (m_event == null)
		{
			m_event = new ListSelectionModelEvent((ListSelectionModel) getSource());
		}
		return m_event;
	}
	
	public boolean isChanged()
	{
		return m_event != null && m_event.hasChanges();
	}
	
	public void resetChanges()
	{
		m_event = null;
	}
	
	public void selectionAdded(RangeCollection collection)
	{
		ListSelectionModelEvent event = getEvent0();
		for (int i = 0, size = collection.getSize(); i < size; i++)
		{
			event.selectionAdded(collection.get(i));
		}
	}

	public void selectionAdded(Range range)
	{
		getEvent0().selectionAdded(range);
	}
	
	public void selectionAdded(int startIndex, int length)
	{
		getEvent0().selectionAdded(new IntRange(startIndex, length));
	}

	public void selectionRemoved(RangeCollection collection)
	{
		ListSelectionModelEvent event = getEvent0();
		for (int i = 0, size = collection.getSize(); i < size; i++)
		{
			event.selectionRemoved(collection.get(i));
		}
	}

	public void selectionRemoved(Range range)
	{
		getEvent0().selectionRemoved(range);
	}

	public void selectionRemoved(int startIndex, int length)
	{
		getEvent0().selectionRemoved(new IntRange(startIndex, length));
	}

	public void update()
	{
		if (isChanged())
		{
			if (m_listeners != null)
			{
				m_firingInProgress = true;
				try
				{
					for (int i = 0, size = m_listeners.size(); i < size; i++)
					{
						((ListSelectionModelListener) m_listeners.get(i)).listSelectionModelChanged(m_event);
					}
				}
				finally
				{
					m_firingInProgress = false;
				}
			}
			resetChanges();
		}
	}
}
