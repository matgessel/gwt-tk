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

import asquare.gwt.sb.client.util.IntRange;
import asquare.gwt.sb.client.util.IntRangeCollection;
import asquare.gwt.sb.client.util.RangeCollection;

public class ListSelectionModelArbitrary implements ListSelectionModel
{
	private final ListSelectionModelChangeSupport m_changeSupport = new ListSelectionModelChangeSupport(this);
	private final IntRangeCollection m_selectedRanges = new IntRangeCollection();
	
	private int m_anchorIndex = -1;
	private int m_leadIndex = -1;
	
	public void addListener(ListSelectionModelListener listener)
	{
		m_changeSupport.addListener(listener);
	}
	
	public void removeListener(ListSelectionModelListener listener)
	{
		m_changeSupport.removeListener(listener);
	}
	
	public boolean hasSelection()
	{
		return m_selectedRanges.getSize() > 0;
	}
	
	public boolean isIndexSelected(int index)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		return m_selectedRanges.get(index, 1) != null;
	}
	
	public int[] getSelectedIndices()
	{
		return m_selectedRanges.toIntArray();
	}
	
	public int getMinSelectedIndex()
	{
		if (m_selectedRanges.getSize() > 0)
		{
			return m_selectedRanges.get(0).getStartIndex();
		}
		return -1;
	}
	
	public int getMaxSelectedIndex()
	{
		int size = m_selectedRanges.getSize();
		if (size > 0)
		{
			return m_selectedRanges.get(size - 1).getEndIndex();
		}
		return -1;
	}
	
	public int getAnchorIndex()
	{
		return m_anchorIndex;
	}
	
	private void setAnchorIndexImpl(int anchorIndex)
	{
		if (m_changeSupport.propertyChanged(PROPERTY_ANCHORINDEX, m_anchorIndex, anchorIndex))
		{
			m_anchorIndex = anchorIndex;
		}
	}
	
	public int getLeadIndex()
	{
		return m_leadIndex;
	}
	
	private void setLeadIndexImpl(int leadIndex)
	{
		if (m_changeSupport.propertyChanged(PROPERTY_LEADINDEX, m_leadIndex, leadIndex))
		{
			m_leadIndex = leadIndex;
		}
	}
	
	public void clearSelection()
	{
		clearSelectionImpl();
		setAnchorIndexImpl(-1);
		setLeadIndexImpl(-1);
		m_changeSupport.update();
	}
	
	private void clearSelectionImpl()
	{
		if (m_selectedRanges.getSize() > 0)
		{
			for (int i = 0, size = m_selectedRanges.getSize(); i < size; i++)
			{
				m_changeSupport.selectionRemoved(m_selectedRanges.get(i));
			}
			m_selectedRanges.clear();
		}
	}
	
	public int getSelectionSize()
	{
		return m_selectedRanges.getTotalCount();
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
		
		clearSelectionImpl();
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
		
		int min = Math.min(from, to);
		int max = Math.max(from, to);
		int length = max - min + 1;
		if (selected)
		{
			RangeCollection rangesToSelect = new RangeCollection();
			rangesToSelect.add(new IntRange(min, length));
			rangesToSelect.removeAll(m_selectedRanges);
			if (! rangesToSelect.isEmpty())
			{
				m_selectedRanges.addAll(rangesToSelect);
				m_changeSupport.selectionAdded(rangesToSelect);
			}
		}
		else
		{
			RangeCollection rangesToDeselect = m_selectedRanges.intersect(min, length);
			if (! rangesToDeselect.isEmpty())
			{
				m_selectedRanges.removeAll(rangesToDeselect);
				m_changeSupport.selectionRemoved(rangesToDeselect);
			}
		}
		setAnchorIndexImpl(from);
		setLeadIndexImpl(to);
		m_changeSupport.update();
	}
	
	public void adjustForItemsInserted(int index, int count)
	{
		if (count <= 0)
			throw new IllegalArgumentException(String.valueOf(index));
		
		if (m_anchorIndex >= index)
		{
			setAnchorIndexImpl(m_anchorIndex + count);
		}
		if (m_leadIndex >= index)
		{
			setLeadIndexImpl(m_leadIndex + count);
		}
		
		updateForItemsShifted(index, count);
	}
	
	public void adjustForItemsRemoved(int index, int count)
	{
		if (count <= 0)
			throw new IllegalArgumentException(String.valueOf(index));
		
		if (m_anchorIndex > index)
		{
			setAnchorIndexImpl(Math.max(-1, m_anchorIndex - count));
		}
		if (m_leadIndex > index)
		{
			setLeadIndexImpl(Math.max(-1, m_leadIndex - count));
		}
		
		updateForItemsShifted(index + count, -count);
	}
	
	private void updateForItemsShifted(int index, int count)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		RangeCollection deselectedChanges = (RangeCollection) m_selectedRanges.duplicate();
		m_selectedRanges.shift(index, count);
		RangeCollection selectedChanges = (RangeCollection) m_selectedRanges.duplicate();
		selectedChanges.removeAll(deselectedChanges);
		deselectedChanges.removeAll(m_selectedRanges);
		m_changeSupport.selectionAdded(selectedChanges);
		m_changeSupport.selectionRemoved(deselectedChanges);
		m_changeSupport.update();
	}
}
