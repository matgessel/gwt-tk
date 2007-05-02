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

public abstract class ListModelBase implements ListModel, ListSelectionModelListener
{
	private final ListSelectionModel m_selectionModel;
	
	private Vector m_listeners;
	private int m_activeIndex = -1;
	private int m_changeStart = -1;
	private int m_changeEnd = -1;
	
	public ListModelBase(ListSelectionModel selectionModel)
	{
		m_selectionModel = selectionModel;
		m_selectionModel.addListener(this);
	}
	
	public void addListener(ListModelListener listener)
	{
		if (m_listeners == null)
		{
			m_listeners = new Vector();
		}
		m_listeners.add(listener);
	}
	
	public void removeListener(ListModelListener listener)
	{
		if (m_listeners != null)
		{
			m_listeners.remove(listener);
		}
	}
	
	public boolean isChanged()
	{
		return m_changeEnd > -1;
	}
	
	protected void addChange(int index)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		if (index < m_changeStart || m_changeStart == -1)
		{
			m_changeStart = index;
		}
		if (index > m_changeEnd)
		{
			m_changeEnd = index;
		}
	}
	
	public int getChangeStartIndex()
	{
		return m_changeStart;
	}
	
	public int getChangeEndIndex()
	{
		return m_changeEnd;
	}
	
	public ListSelectionModel getSelectionModel()
	{
		return m_selectionModel;
	}
	
	public boolean isIndexSelected(int index)
	{
		return m_selectionModel.isIndexSelected(index);
	}
	
	public boolean isIndexDisabled(int index)
	{
		return false;
	}
	
	public boolean isIndexActive(int index)
	{
		return index == m_activeIndex;
	}
	
	public void setActiveIndex(int index)
	{
		if (m_activeIndex != index)
		{
			if (m_activeIndex > -1)
			{
				addChange(m_activeIndex);
			}
			m_activeIndex = index;
			if (m_activeIndex > -1)
			{
				addChange(m_activeIndex);
			}
		}
	}
	
	public void update()
	{
		if (isChanged() && m_listeners != null && m_listeners.size() > 0)
		{
			Object[] listeners = m_listeners.toArray();
			ListModelEvent e = new ListModelEvent(this, m_changeStart, m_changeEnd);
			for (int i = 0; i < listeners.length; i++)
			{
				((ListModelListener) listeners[i]).listModelChanged(e);
			}
		}
		m_changeStart = -1;
		m_changeEnd = -1;
	}
	
	// ListSelectionModelListener method
	public void listSelectionModelChanged(int index)
	{
		addChange(index);
	}
}
