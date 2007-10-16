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

public class ListSelectionModelSingle extends ListSelectionModelBase
{
	private int m_selectedIndex = -1;
	
	public boolean isIndexSelected(int index)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		return index == m_selectedIndex;
	}
	
	public boolean hasSelection()
	{
		return m_selectedIndex != -1;
	}
	
	public int getSelectedIndex()
	{
		return m_selectedIndex;
	}
	
	public int[] getSelectedIndices()
	{
		return m_selectedIndex == -1 ? new int[0] : new int[] {m_selectedIndex};
	}
	
	public int getMinSelectedIndex()
	{
		return m_selectedIndex;
	}
	
	public int getMaxSelectedIndex()
	{
		return m_selectedIndex;
	}
	
	/**
	 * 
	 * @param index a valid index in the corresponding list, or -1
	 */
	public void setSelectedIndex(int index)
	{
        if (index < -1)
            throw new IndexOutOfBoundsException(String.valueOf(index));
        
		if (m_selectedIndex != index)
		{
			if (m_selectedIndex != -1)
			{
				fireSelectionChange(m_selectedIndex);
			}
			m_selectedIndex = index;
			if (m_selectedIndex != -1)
			{
				fireSelectionChange(m_selectedIndex);
			}
		}
	}
	
	public void clearSelection()
	{
		if (m_selectedIndex != -1)
		{
			int previouslySelectedIndex = m_selectedIndex;
			m_selectedIndex = -1;
			fireSelectionChange(previouslySelectedIndex);
		}
	}
	
	public int getSelectionSize()
	{
		return m_selectedIndex == -1 ? 0 : 1;
	}
	
	public void addSelectionRange(int from, int to)
	{
		setSelectionRange(from, to);
	}
	
	public void setSelectionRange(int from, int to)
	{
		if (from < 0)
			throw new IndexOutOfBoundsException(String.valueOf(from));
		
		if (to < 0)
			throw new IndexOutOfBoundsException(String.valueOf(to));
		
		setSelectedIndex(to);
	}
	
	public void removeSelectionRange(int from, int to)
	{
		if (from < 0)
			throw new IndexOutOfBoundsException(String.valueOf(from));
		
		if (to < 0)
			throw new IndexOutOfBoundsException(String.valueOf(to));
		
		if (m_selectedIndex >= Math.min(from, to) && m_selectedIndex <= Math.max(from, to))
		{
			clearSelection();
		}
	}
}
