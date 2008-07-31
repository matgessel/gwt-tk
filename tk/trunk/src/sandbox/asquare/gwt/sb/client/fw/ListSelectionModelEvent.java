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

import asquare.gwt.sb.client.util.*;

public class ListSelectionModelEvent extends ModelChangeEvent
{
	private final RangeCollection m_selected = new RangeCollection();
	private final RangeCollection m_deselected = new RangeCollection();
	
	public ListSelectionModelEvent(ListSelectionModel source)
	{
		super(source);
	}
	
	public ListSelectionModel getListSelectionModel()
	{
		return (ListSelectionModel) getSource();
	}
	
	public void selectionAdded(Range range)
	{
		m_selected.add(range);
		sweep(range, m_deselected);
	}
	
	public void selectionRemoved(Range range)
	{
		m_deselected.add(range);
		sweep(range, m_selected);
	}

	/**
	 * Handle the case where one or more indices are selected/deselected in same
	 * operation. We don't want clients to be responsible for sorting out
	 * duplicate state changes for the same index.
	 */
	private void sweep(Range range, RangeCollection ranges)
	{
		for (int i = 0, size = ranges.getSize(); i < size; i++)
		{
			Range candidate = ranges.get(i);
			Range intersection = range.intersect(candidate);
			if (intersection != null)
			{
				m_selected.remove(intersection);
				m_deselected.remove(intersection);
			}
		}
	}
	
	public RangeCollection getSelectedRanges()
	{
		return m_selected.duplicate();
	}
	
	public RangeCollection getDeselectedRanges()
	{
		return m_deselected.duplicate();
	}
	
	public RangeCollection getAllRanges()
	{
		RangeCollection result = getSelectedRanges();
		result.addAll(getDeselectedRanges());
		return result;
	}
	
	public int getMinChangedIndex()
	{
		int minSelected = m_selected.getMinValue();
		int minDeselected = m_deselected.getMinValue();
		if (minSelected == -1)
		{
			return minDeselected;
		}
		if (minDeselected == -1)
		{
			return minSelected;
		}
		return Math.min(minSelected, minDeselected);
	}
	
	public int getMaxChangedIndex()
	{
		int maxSelected = m_selected.getMaxValue();
		int maxDeselected = m_deselected.getMaxValue();
		if (maxSelected == -1)
		{
			return maxDeselected;
		}
		if (maxDeselected == -1)
		{
			return maxSelected;
		}
		return Math.max(maxSelected, maxDeselected);
	}
	
	public boolean hasChanges()
	{
		return m_selected.getSize() > 0 || m_deselected.getSize() > 0;
	}
}
