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
	private final ArrayList<Element<E>> m_items = new ArrayList<Element<E>>();
	
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
		return m_items.get(index).m_value;
	}
	
	public int getIndexOf(E o)
	{
		for (int i = 0, size = m_items.size(); i < size; i++)
		{
			E candidate = m_items.get(i).m_value;
			if (o != null && o.equals(candidate) || candidate == null)
			{
				return i;
			}
		}
		return -1;
	}
	
	public int getSelectionSize()
	{
		int result = 0;
		int[] selectedIndices = getSelectionModel().getSelectedIndices();
		for (int i = 0, size = getSize(); i < selectedIndices.length; i++)
		{
			if (selectedIndices[i] < size && isIndexEnabled(selectedIndices[i]))
			{
				result++;
			}
		}
		return result;
	}
	
	public int[] getSelectedIndices()
	{
		int[] selectedIndices = getSelectionModel().getSelectedIndices();
		int[] enabled = new int[selectedIndices.length];
		int total = 0;
		for (int i = 0, size = getSize(); i < selectedIndices.length; i++)
		{
			if (selectedIndices[i] < size && isIndexEnabled(selectedIndices[i]))
			{
				enabled[total++] = selectedIndices[i];
			}
		}
		int[] result = new int[total];
		System.arraycopy(enabled, 0, result, 0, total);
		return result;
	}
	
	/**
	 * @throws IllegalStateException if the selection model has indices which are out of the bounds of this model
	 */
	public Object[] getSelectedItems()
	{
		return getSelectedItems(null);
	}
	
	/**
	 * @param dest an array large enough to hold the selection, or <code>null</code> to have an array of type Object[] created
	 * @throws IllegalArgumentException if <code>dest</code> is too small to hold the selected items
	 */
	public Object[] getSelectedItems(Object[] dest)
	{
		int[] selectedIndices = getSelectedIndices();
		
		if (selectedIndices.length > 0 && selectedIndices[selectedIndices.length - 1] >= m_items.size())
			throw new IllegalStateException();
		
		if (dest == null)
		{
			dest = new Object[selectedIndices.length];
		}
		
		if (dest.length < selectedIndices.length)
			throw new IllegalArgumentException();
		
		for (int i = 0; i < selectedIndices.length; i++)
		{
			dest[i] = m_items.get(selectedIndices[i]).m_value;
		}
		
		if (dest.length > selectedIndices.length)
		{
			dest[selectedIndices.length] = null;
		}
		
		return dest;
	}
	
	/**
	 * @throws IllegalStateException if the selection model has indices which are out of the bounds of this model
	 */
	public Object[] getUnselectedItems()
	{
		return getUnselectedItems(null);
	}
	
	/**
	 * @param dest an array large enough to hold the selection, or <code>null</code> to have an array of type Object[] created
	 * @throws IllegalArgumentException if <code>dest</code> is too small to hold the selected items
	 */
	public Object[] getUnselectedItems(Object[] dest)
	{
		int[] selectedIndices = getSelectedIndices();
		int count = m_items.size() - selectedIndices.length;
		
		if (selectedIndices.length > 0 && selectedIndices[selectedIndices.length - 1] >= m_items.size())
			throw new IllegalStateException();
		
		if (dest == null)
		{
			dest = new Object[count];
		}
		
		if (dest.length < count)
			throw new IllegalArgumentException();
		
		int nextCandidate = 0, nextSelected = 0, nextResult = 0;
		while (nextResult < count)
		{
			if (nextSelected < selectedIndices.length && nextCandidate == selectedIndices[nextSelected])
			{
				nextSelected++;
			}
			else
			{
				dest[nextResult++] = m_items.get(nextCandidate).m_value;
			}
			nextCandidate++;
		}
		
		if (dest.length > count)
		{
			dest[count] = null;
		}
		
		return dest;
	}
	
	@Override
	public boolean isIndexEnabled(int index)
	{
		return isEnabled() && m_items.get(index).m_enabled;
	}
	
	public void setIndexEnabled(int index, boolean enabled)
	{
		Element<E> element = m_items.get(index);
		if (element.m_enabled != enabled)
		{
			element.m_enabled = enabled;
			if (isEnabled())
			{
				addItemPropertyChange(ITEM_PROPERTY_ENABLED, index, 1);
			}
		}
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
		m_items.add(index, new Element<E>(o));
		addChange(new ListModelEvent.ListChangeItemInsertion(index, 1));
		getSelectionModel().adjustForItemsInserted(index, 1);
	}
	
	public void set(int index, E o)
	{
		Element<E> element = m_items.get(index);
		if (! GwtUtil.equals(element.m_value, o))
		{
			element.m_value = o;
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
		E result = m_items.remove(index).m_value;
		addChange(new ListModelEvent.ListChangeItemRemoval(index, 1));
		getSelectionModel().adjustForItemsRemoved(index, 1);
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
			getSelectionModel().clearSelection();
		}
	}
	
	public int getSize()
	{
		return m_items.size();
	}
	
	private static class Element<E>
	{
		E m_value;
		boolean m_enabled = true;
		
		public Element(E value)
		{
			m_value = value;
		}
		
		public String toString()
		{
			return String.valueOf(m_value);
		}
	}
}
