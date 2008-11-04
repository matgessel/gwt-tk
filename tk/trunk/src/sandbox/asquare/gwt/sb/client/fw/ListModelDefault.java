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

public class ListModelDefault<E> extends ListModelBase<E> implements MutableListModel<E>
{
	private final ArrayList<E> m_items = new ArrayList<E>();
	
	public ListModelDefault(ListSelectionModel selectionModel)
	{
		this(selectionModel, null);
	}
	
	public ListModelDefault(ListSelectionModel selectionModel, ListModelChangeSupport changeSupport)
	{
		super(selectionModel, changeSupport);
	}
	
	public E get(int index)
	{
		return m_items.get(index);
	}
	
	public int getIndexOf(Object o)
	{
		return m_items.indexOf(o);
	}
	
	/**
	 * @throws IllegalStateException if the selection model has indices which are out of the bounds of this model
	 */
	public Object[] getSelectedItems()
	{
		return getSelectedItems(new Object[getSelectionModel().getSelectionSize()]);
	}
	
	/**
	 * @throws IllegalStateException if the selection model has indices which are out of the bounds of this model
	 * @throws IllegalArgumentException if <code>dest</code> is <code>null</code>
	 * @throws IllegalArgumentException if <code>dest</code> is too small to hold the selected items
	 */
	public Object[] getSelectedItems(Object[] dest)
	{
		if (getSelectionModel().getMaxSelectedIndex() >= m_items.size())
			throw new IllegalStateException();
		
		int[] indices = getSelectionModel().getSelectedIndices();
		
		if (dest == null || dest.length < indices.length)
			throw new IllegalArgumentException();
		
		for (int i = 0; i < dest.length; i++)
		{
			dest[i] = m_items.get(indices[i]);
		}
		return dest;
	}
	
	/**
	 * @throws IllegalStateException if the selection model has indices which are out of the bounds of this model
	 */
	public Object[] getUnselectedItems()
	{
		return getUnselectedItems(new Object[m_items.size() - getSelectionModel().getSelectionSize()]);
	}
	
	/**
	 * @throws IllegalStateException if the selection model has indices which are out of the bounds of this model
	 * @throws IllegalArgumentException if <code>dest</code> is <code>null</code>
	 * @throws IllegalArgumentException if <code>dest</code> is too small to hold the selected items
	 */
	public Object[] getUnselectedItems(Object[] dest)
	{
		if (getSelectionModel().getMaxSelectedIndex() >= m_items.size())
			throw new IllegalStateException();
		
		if ( dest == null || dest.length < m_items.size() - getSelectionModel().getSelectionSize())
			throw new IllegalArgumentException();
			
		int nextCandidate = 0, nextSelected = 0, nextResult = 0;
		int[] selectedIndices = getSelectionModel().getSelectedIndices();
		while (nextResult < dest.length)
		{
			if (nextSelected < selectedIndices.length && nextCandidate == selectedIndices[nextSelected])
			{
				nextSelected++;
			}
			else
			{
				dest[nextResult++] = m_items.get(nextCandidate);
			}
			nextCandidate++;
		}
		return dest;
	}
	
	public void add(E o)
	{
		insert(m_items.size(), o);
	}
	
	public void addAll(E[] items)
	{
		for (int i = 0; i < items.length; i++)
		{
			add(items[i]);
		}
	}
	
	public void addAll(List<E> items)
	{
		for (int i = 0, size = items.size(); i < size; i++)
		{
			add(items.get(i));
		}
	}
	
	public void setItems(E[] items)
	{
		clear();
		addAll(items);
	}
	
	public void setItems(List<E> items)
	{
		clear();
		addAll(items);
	}
	
	public void insert(int index, E o)
	{
		m_items.add(index, o);
		addChange(new ListModelEvent.ListChangeItemInsertion(index, 1));
		ListSelectionModel selectionModel = getSelectionModel();
		if (selectionModel != null)
		{
			selectionModel.adjustForItemsInserted(index, 1);
		}
	}
	
	public void set(int index, E o)
	{
		Object old = m_items.set(index, o);
		if (! GwtUtil.equals(old, o))
		{
			setChanged(index);
		}
	}
	
	/**
	 * Indicates that the value at the specified index has changed and needs to
	 * be redrawn.
	 */
	public void setChanged(int index)
	{
		addItemPropertyChange(ITEM_PROPERTY_VALUE, index, 1);
	}
	
	public E remove(int index)
	{
		E result = m_items.remove(index);
		addChange(new ListModelEvent.ListChangeItemRemoval(index, 1));
		ListSelectionModel selectionModel = getSelectionModel();
		if (selectionModel != null)
		{
			selectionModel.adjustForItemsRemoved(index, 1);
		}
		return result;
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
