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

public class ListModelDefault extends ListModelBase
{
	private final Vector m_items = new Vector();
	
	public ListModelDefault(ListSelectionModel selectionModel)
	{
		super(selectionModel);
	}
	
	public Object get(int index)
	{
		return m_items.get(index);
	}
	
	public int indexOf(Object o)
	{
		return m_items.indexOf(o);
	}
	
	public void add(Object o)
	{
		insert(m_items.size(), o);
	}
	
	public void addAll(Object[] items)
	{
		for (int i = 0; i < items.length; i++)
		{
			add(items[i]);
		}
	}
	
	public void insert(int index, Object o)
	{
		m_items.add(index, o);
		addChange(index);
	}
	
	public void set(int index, Object o)
	{
		Object old = m_items.set(index, o);
		if (old != o && (old != null && ! old.equals(o) || ! o.equals(old)))
		{
			addChange(index);
		}
	}
	
	public void remove(int index)
	{
		addChange(index);
		addChange(m_items.size());
		m_items.remove(index);
	}
	
	public void clear()
	{
		if (m_items.size() > 0)
		{
			addChange(0);
			addChange(m_items.size());
			m_items.clear();
		}
	}
	
	public int getSize()
	{
		return m_items.size();
	}
	
	public void setSize(int size)
	{
		if (size != m_items.size())
		{
			addChange(size - 1);
			addChange(m_items.size() - 1);
			m_items.setSize(size);
		}
	}
}
