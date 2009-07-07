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
package asquare.gwt.sb.client.util;

import asquare.gwt.tk.client.util.*;

public class ObjectArrayRange extends Range
{
	private Object[] m_data;
	
	public ObjectArrayRange(int startIndex, Object[] data)
	{
		super(startIndex);
		
		if (data == null)
			throw new IllegalArgumentException();
		
		m_data = data;
	}
	
	public int getLength()
	{
		return m_data.length;
	}
	
	/**
	 * Gets a value using an absolute index
	 * @param index an index relative to the parent range
	 * @throws IndexOutOfBoundsException if <code>index</code> is out of bounds
	 */
	public Object get(int index)
	{
		// required for web mode
		if (index < 0 || index - getStartIndex() >= m_data.length)
			throw new IndexOutOfBoundsException(Integer.toString(index));
		
		return m_data[index - getStartIndex()];
	}
	
	/**
	 * Gets a range of values using an absolute index
	 * @param index an index relative to the parent range
	 * @throws IllegalArgumentException if <code>count</code> if <code>count &lt;= 0</code>
	 * @throws IndexOutOfBoundsException if the specified range is out of bounds
	 */
	public Object[] get(int index, int count)
	{
		if (count <= 0)
			throw new IllegalArgumentException();
		
		int startIndex = getStartIndex();
		
		if (index < 0 || index < startIndex || index - startIndex + count > m_data.length)
			throw new IndexOutOfBoundsException("[" + index + '-' + (index + count - 1) + "]");
		
		Object[] result = new Object[count];
		for (int i = 0; i < result.length; i++)
		{
			result[i] = m_data[index - startIndex + i];
		}
		return result;
	}
	
	protected Range subRangeImpl(int startIndex, int length)
	{
		Object[] resultData = new Object[length];
		GwtUtil.arrayCopy(m_data, startIndex - getStartIndex(), resultData, 0, resultData.length);
		return new ObjectArrayRange(startIndex, resultData);
	}
	
	public void add(Range range)
	{
		add((ObjectArrayRange) range);
	}
	
	public void add(ObjectArrayRange range)
	{
		add(range.getStartIndex(), range.m_data);
	}
	
	public void add(int startIndex, Object[] data)
	{
		if (startIndex < 0 || data == null)
			throw new IllegalArgumentException();
		
		if (data.length == 0)
			return;
		
		Object[] oldData = m_data;
		int oldStartIndex = getStartIndex();
		int newStartIndex = Math.min(oldStartIndex, startIndex);
		setStartIndex(newStartIndex);
		int newLength = Math.max(oldStartIndex + oldData.length, startIndex + data.length) - newStartIndex;
		m_data = new Object[newLength];
		// TODO: avoid copying old items which will be clobbered by new items
		GwtUtil.arrayCopy(oldData, 0, m_data, oldStartIndex - newStartIndex, oldData.length);
		GwtUtil.arrayCopy(data, 0, m_data, startIndex - newStartIndex, data.length);
	}
	
	protected void subtractImpl(int startIndex, int length)
	{
		int oldStartIndex = getStartIndex();
		if (startIndex - oldStartIndex > 0)
		{
			Object[] oldData = m_data;
			m_data = new Object[startIndex - getStartIndex()];
			GwtUtil.arrayCopy(oldData, 0, m_data, 0, m_data.length);
		}
		else
		{
			int maybeLength = oldStartIndex + m_data.length - (startIndex + length);
			if (maybeLength > 0)
			{
				int newStartIndex = startIndex + length;
				setStartIndex(newStartIndex);
				Object[] oldData = m_data;
				m_data = new Object[maybeLength];
				GwtUtil.arrayCopy(oldData, newStartIndex - oldStartIndex, m_data, 0, m_data.length);
			}
			else
			{
				m_data = new Object[0];
			}
		}
	}
	
	public Object[] toArray()
	{
		Object[] result = new Object[m_data.length];
		GwtUtil.arrayCopy(m_data, 0, result, 0, m_data.length);
		return result;
	}
	
	public Object[] toArray(Object[] result, int offset)
	{
		GwtUtil.arrayCopy(m_data, 0, result, offset, m_data.length);
		return result;
	}
	
	public Range duplicate()
	{
		Object[] data = new Object[m_data.length];
		GwtUtil.arrayCopy(m_data, 0, data, 0, m_data.length);
		return new ObjectArrayRange(getStartIndex(), m_data);
	}
}
