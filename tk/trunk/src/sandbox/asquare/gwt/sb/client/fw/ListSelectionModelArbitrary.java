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

import asquare.gwt.tk.client.util.GwtUtil;

public class ListSelectionModelArbitrary extends ListSelectionModelBase
{
	private static final Object SELECTED = Boolean.TRUE;
	private static final Object NOTSELECTED = null;
	
	/*
	 * Acts as an int to object map. Int = index, Boolean.TRUE = selected. 
	 */
	private Vector m_selection = new Vector();
	
	public boolean hasSelection()
	{
		return getMinSelectedIndex() != -1;
	}
	
	public boolean isIndexSelected(int index)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		return m_selection.size() > index && m_selection.get(index) == SELECTED;
	}
	
	public int[] getSelectedIndices()
	{
		int[] temp = new int[m_selection.size()];
		int count = 0;
		for (int i = 0, size = m_selection.size(); i < size; i++)
		{
			if (m_selection.get(i) == SELECTED)
			{
				temp[count++] = i;
			}
		}
		int[] result = new int[count];
		GwtUtil.arrayCopy(temp, 0, result, 0, count);
		return result;
	}
	
	public int getMinSelectedIndex()
	{
		for (int i = 0, size = m_selection.size(); i < size; i++)
		{
			if (m_selection.get(i) == SELECTED)
				return i;
		}
		return -1;
	}
	
	public int getMaxSelectedIndex()
	{
		for (int i = m_selection.size() - 1; i >= 0; i--)
		{
			if (m_selection.get(i) == SELECTED)
				return i;
		}
		return -1;
	}
	
	public void setIndexSelected(int index, boolean selected)
	{
		if (selected)
		{
			if (m_selection.size() <= index)
			{
				m_selection.setSize(index + 1);
			}
			if (m_selection.get(index) != SELECTED)
			{
				m_selection.set(index, SELECTED);
				fireSelectionChange(index);
			}
		}
		else 
		{
			if (isIndexSelected(index))
			{
				// must call set(). remove() will shift elements down. 
				m_selection.set(index, NOTSELECTED);
				fireSelectionChange(index);
			}
		}
	}
	
	public void clearSelection()
	{
		for (int i = m_selection.size() - 1; i >=0; i--)
		{
			if (m_selection.remove(i) == SELECTED)
			{
				fireSelectionChange(i);
			}
		}
	}
	
	public int getSelectionSize()
	{
		int result = 0;
		for (int i = 0, size = m_selection.size(); i < size; i++)
		{
			if (m_selection.get(i) == SELECTED)
			{
				result++;
			}
		}
		return result;
	}
	
	public void addSelectionRange(int from, int to)
	{
		setRangeSelected(from, to, true);
	}
	
	public void setSelectionRange(int from, int to)
	{
		if (from < 0)
			throw new IndexOutOfBoundsException(String.valueOf(from));
		
		if (to < 0)
			throw new IndexOutOfBoundsException(String.valueOf(to));
		
		clearSelection();
		setRangeSelected(from, to, true);
	}
	
	public void removeSelectionRange(int from, int to)
	{
		setRangeSelected(from, to, false);
	}

	private void setRangeSelected(int from, int to, boolean selected)
	{
		if (from < 0)
			throw new IndexOutOfBoundsException(String.valueOf(from));
		
		if (to < 0)
			throw new IndexOutOfBoundsException(String.valueOf(to));
		
		for (int i = Math.min(from, to), max = Math.max(from, to); i <= max; i++)
		{
			setIndexSelected(i, selected);
		}
	}
}
