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
import java.util.List;

import asquare.gwt.tk.client.util.GwtUtil;

public class ListModelDefault extends ListModelBase implements MutableIndexedDataSource
{
	private final ArrayList m_items = new ArrayList();
	
	public ListModelDefault(ListSelectionModel selectionModel)
	{
		this(selectionModel, null);
	}
	
	public ListModelDefault(ListSelectionModel selectionModel, ListModelChangeSupport changeSupport)
	{
		super(selectionModel, changeSupport);
	}
	
	public Object get(int index)
	{
		return m_items.get(index);
	}
	
	public int getIndexOf(Object o)
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
	
	public void addAll(List items)
	{
		for (int i = 0, size = items.size(); i < size; i++)
		{
			add(items.get(i));
		}
	}
	
	public void setItems(Object[] items)
	{
		clear();
		addAll(items);
	}
	
	public void setItems(List items)
	{
		clear();
		addAll(items);
	}
	
	public void insert(int index, Object o)
	{
		m_items.add(index, o);
		addChange(new ListModelEvent.ListChangeItemInsertion(index, 1));
		ListSelectionModel selectionModel = getSelectionModel();
		if (selectionModel != null)
		{
			selectionModel.adjustForItemsInserted(index, 1);
		}
	}
	
	public void set(int index, Object o)
	{
		Object old = m_items.set(index, o);
		if (! GwtUtil.equals(old, o))
		{
			addItemPropertyChange(ITEM_PROPERTY_VALUE, index, 1);
		}
	}
	
	public void remove(int index)
	{
		m_items.remove(index);
		addChange(new ListModelEvent.ListChangeItemRemoval(index, 1));
		ListSelectionModel selectionModel = getSelectionModel();
		if (selectionModel != null)
		{
			selectionModel.adjustForItemsRemoved(index, 1);
		}
	}
	
	/**
	 * Removes all items in the model and clears the selection. 
	 */
	public void clear()
	{
		if (m_items.size() > 0)
		{
			addChange(new ListModelEvent.ListChangeItemRemoval(0, m_items.size()));
			m_items.clear();
			ListSelectionModel selectionModel = getSelectionModel();
			if (selectionModel != null)
			{
				selectionModel.clearSelection();
			}
		}
	}
	
	public int getSize()
	{
		return m_items.size();
	}
}
