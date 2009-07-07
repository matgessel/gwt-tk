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
package asquare.gwt.sb.client.util;

import java.util.ArrayList;

public class RangeCollection
{
	// an ordered list of ranges sorted by range index
	private final ArrayList<Range> m_ranges = new ArrayList<Range>();
	
	public int getMinValue()
	{
		return (m_ranges.size() > 0) ? get(0).getStartIndex() : -1;
	}
	
	public int getMaxValue()
	{
		int size = m_ranges.size();
		return (size > 0) ? get(size - 1).getEndIndex() : -1;
	}
	
	public boolean contains(int index, int length)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		if (length <= 0)
			throw new IllegalArgumentException(String.valueOf(length));
		
		for (int i = 0, size = m_ranges.size(); i < size; i++)
		{
			if (m_ranges.get(i).contains(index, length))
			{
				return true;
			}
		}
		return false;
	}
	
	public Range get(int index)
	{
		return m_ranges.get(index);
	}
	
	/**
	 * Get a range object in this collection which contains the specified range.
	 * 
	 * @param startIndex
	 * @param length
	 * @return a range, or <code>null</code>
	 * @throws IndexOutOfBoundsException if <code>startIndex < 0</code>
	 * @throws IllegalArgumentException if <code>length <= 0</code>
	 */
	public Range get(int startIndex, int length)
	{
		if (startIndex < 0)
			throw new IndexOutOfBoundsException(String.valueOf(startIndex));
		
		if (length <= 0)
			throw new IllegalArgumentException();
		
		Range range = null;
		
		// search for range containing index
		for (int i = 0, size = m_ranges.size(); i < size; i++)
		{
			Range candidate = m_ranges.get(i);
			if (candidate.getStartIndex() > startIndex + length)
			{
				break;
			}
			if (candidate.contains(startIndex, length))
			{
				range = candidate;
				break;
			}
		}
		
		return range;
	}
	
	/**
	 * Adds a range to the collection, merging with overlapping and neighboring
	 * ranges.
	 * <p>
	 * For data-backed ranges, existing values within the specified range will
	 * be overwritten.
	 */
	public void add(Range range)
	{
		if (range == null)
			throw new IllegalArgumentException();
		
		if (range.getLength() == 0)
			return;
		
		ArrayList<Range> toMerge = new ArrayList<Range>();
		int rangeCount = m_ranges.size();
		for (int i = 0; i < rangeCount; i++)
		{
			Range candidate = m_ranges.get(i);
			if (candidate.getStartIndex() > range.getStartIndex() + range.getLength())
			{
				/*
				 * Optimization. If the new range exceeds the candidate's range
				 * then all subsequent candidates will also be less than the new
				 * range.
				 */
				break;
			}
			if (candidate.intersectsOrNeighbors(range))
			{
				toMerge.add(candidate);
			}
		}
		
		if (toMerge.size() > 0)
		{
			// overlaps detected, merge new data and existing range(s) into the low range
			Range primary = toMerge.get(0);
			primary.add(range);
			for (int j = toMerge.size() - 1; j >= 1; j--)
			{
				Range temp = toMerge.remove(j);
				primary.add(temp);
				m_ranges.remove(temp);
			}
		}
		else
		{
			// insert range in order
			int insertIndex = 0;
			while (insertIndex < rangeCount)
			{
				if (m_ranges.get(insertIndex).getStartIndex() > range.getStartIndex())
					break;
				
				insertIndex++;
			}
			m_ranges.add(insertIndex, range);
		}
	}
	
	public void addAll(RangeCollection ranges)
	{
		if (ranges == null)
			throw new IllegalArgumentException();
		
		for (int i = 0, size = ranges.getSize(); i < size; i++)
		{
			add(ranges.get(i));
		}
	}
	
	public void remove(Range range)
	{
		if (range == null)
			throw new IllegalArgumentException();
		
		remove(range.getStartIndex(), range.getLength());
	}
	
	public void removeAll(RangeCollection ranges)
	{
		if (ranges == null)
			throw new IllegalArgumentException();
		
		for (int i = ranges.getSize() - 1; i >= 0; i--)
		{
			remove(ranges.get(i));
		}
	}
	
	public void remove(int startIndex, int length)
	{
		if (startIndex < 0)
			throw new IndexOutOfBoundsException(String.valueOf(startIndex));
		
		if (length < 0)
			throw new IllegalArgumentException(String.valueOf(length));
		
		for (int i = getSize() - 1; i >= 0; i--)
		{
			Range candidate = get(i);
			if (candidate.intersects(startIndex, length))
			{
				if (Range.contains(startIndex, length, candidate.getStartIndex(), candidate.getLength()))
				{
					m_ranges.remove(candidate);
				}
				else if (startIndex > candidate.getStartIndex() && startIndex + length < candidate.getStartIndex() + candidate.getLength())
				{
					/*
					 * If the candidate encircles the target range we'll need to split the candidate. 
					 * candidate -> [low][removed][high]
					 */
					int highStartIndex = startIndex + length;
					int candidateTerminus = candidate.getStartIndex() + candidate.getLength();
					Range high = candidate.subRange(highStartIndex, candidateTerminus - highStartIndex);
					candidate.subtract(startIndex, candidateTerminus - startIndex);
					add(high);
					break;
				}
				else
				{
					candidate.subtract(startIndex, length);
				}
			}
		}
	}
	
	/**
	 * Removes any entries after <code>size</code>
	 * 
	 * @param size
	 */
	public void truncate(int size)
	{
		for (int i = m_ranges.size() - 1; i >= 0; i--)
		{
			Range range = m_ranges.get(i);
			if (range.getTerminus() <= size)
			{
				break;
			}
			if (range.getStartIndex() >= size)
			{
				m_ranges.remove(i);
			}
			else if (range.contains(size))
			{
				range.subtract(size, range.getLength() - size);
			}
		}
	}
	
	public void clear()
	{
		m_ranges.clear();
	}
	
	/**
	 * Shifts a portion of the range up or down. Range elements have their start
	 * index adjusted appropriately. A range is split if <code>index</code> is
	 * contained by the range.
	 * <p>
	 * For data-backed ranges, shifting down overwrites any data from 
	 * <code>startIndex-count</code> to <code>startIndex</code>; shifting
	 * up populates the newly created gap with <code>null</code> values.
	 * 
	 * @param index an index in the range of numbers/data represented by this
	 *            collection. The index and all subsequent values will be
	 *            shifted.
	 * @param count a positive or negative amount to shift by
	 * @throws IndexOutOfBoundsException if <code>index < 0</code>
	 * @throws IndexOutOfBoundsException if <code>index + count < 0</code>
	 */
	public void shift(int index, int count)
	{
		if (index < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index));
		
		if (index + count < 0)
			throw new IndexOutOfBoundsException(String.valueOf(index + count));
		
		if (count == 0)
			return;
		
		if (count > 0)
		{
			shiftUp(index, count);
		}
		else if (count < 0)
		{
			shiftDown(index, -count);
		}
	}
	
	/**
	 * @param count a positive quantity
	 */
	private void shiftUp(int index, int count)
	{
		for (int i = m_ranges.size() - 1; i >=0; i--)
		{
			Range range = m_ranges.get(i);
			
			if (range.getStartIndex() + range.getLength() - 1 < index)
				break;
			
			if (index > range.getStartIndex() && index < range.getTerminus())
			{
				/*
				 * If the range contains the start index we'll need to split it. 
				 * range -> [range][new gap][high]
				 */
				int rangeTerminus = range.getStartIndex() + range.getLength();
				Range high = range.subRange(index, rangeTerminus - index);
				range.subtract(index, rangeTerminus - index);
				high.setStartIndex(index + count);
				add(high);
			}
			else
			{
				range.setStartIndex(range.getStartIndex() + count);
			}
		}
	}
	
	/**
	 * @param count a positive quantity
	 */
	private void shiftDown(int index, int count)
	{
		remove(index - count, count);
		
		for (int i = m_ranges.size() - 1; i >=0; i--)
		{
			Range range = m_ranges.get(i);
			
			if (range.getStartIndex() + range.getLength() - 1 < index)
				break;
			
			range.setStartIndex(range.getStartIndex() - count);
			
			/*
			 * Handle the case where a range neighbors the previous range
			 * after the shift. Merge the two. 
			 */
			if (i > 0 && range.getStartIndex() == index - count)
			{
				Range mergeCandidate = m_ranges.get(i - 1);
				if (mergeCandidate.intersectsOrNeighbors(range))
				{
					m_ranges.remove(i);
					mergeCandidate.add(range);
					break;
				}
			}
		}
	}
	
	/**
	 * @return a collection of <code>0</code> or more ranges
	 * @throws IllegalArgumentException if <code>target</code> is <code>null</code>
	 */
	public RangeCollection intersect(Range target)
	{
		if (target == null)
			throw new IllegalArgumentException();
		
		return intersect(target.getStartIndex(), target.getLength());
	}
	
	/**
	 * @return a collection of <code>0</code> or more ranges
	 * @throws IndexOutOfBoundsException if <code>startIndex < 0</code>
	 */
	public RangeCollection intersect(int startIndex, int length)
	{
		if (startIndex < 0)
			throw new IndexOutOfBoundsException();
		
		RangeCollection result = new RangeCollection();
		for (int reference = 0, refSize = getSize(); reference < refSize; reference++)
		{
			Range range = m_ranges.get(reference).intersect(startIndex, length);
			if (range != null)
			{
				result.add(range);
			}
		}
		return result;
	}
	
	/**
	 * @return a collection of <code>0</code> or more ranges
	 * @throws IllegalArgumentException if <code>ranges</code> is <code>null</code>
	 */
	public RangeCollection intersect(RangeCollection ranges)
	{
		if (ranges == null)
			throw new IllegalArgumentException();
		
		RangeCollection result = new RangeCollection();
		for (int reference = 0, refSize = m_ranges.size(); reference < refSize; reference++)
		{
			for (int target = 0, targetSize = ranges.getSize(); target < targetSize; target++)
			{
				Range range = m_ranges.get(reference).intersect(ranges.get(target));
				if (range != null)
				{
					result.add(range);
				}
			}
		}
		return result;
	}
	
	public int getSize()
	{
		return m_ranges.size();
	}
	
	public int getTotalCount()
	{
		int result = 0;
		for (int i = 0, size = m_ranges.size(); i < size; i++)
		{
			result += m_ranges.get(i).getLength();
		}
		return result;
	}
	
	public boolean isEmpty()
	{
		return m_ranges.size() == 0;
	}
	
	public Range[] toArray()
	{
		Range[] result = new Range[m_ranges.size()];
		for (int i = 0; i < result.length; i++)
		{
			result[i] = m_ranges.get(i);
		}
		return result;
	}
	
	public RangeCollection duplicate()
	{
		RangeCollection result = new RangeCollection();
		for (int i = 0, size = m_ranges.size(); i < size; i++)
		{
			result.add(m_ranges.get(i).duplicate());
		}
		return result;
	}
	
	public String toString()
	{
		return m_ranges.toString();
	}
}
